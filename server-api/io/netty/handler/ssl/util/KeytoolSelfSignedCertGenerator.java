/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class KeytoolSelfSignedCertGenerator
/*     */ {
/*  44 */   private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.ROOT);
/*     */   
/*     */   private static final String ALIAS = "alias";
/*     */   private static final String PASSWORD = "insecurepassword";
/*     */   private static final Path KEYTOOL;
/*     */   
/*     */   static {
/*  51 */     String home = System.getProperty("java.home");
/*  52 */     if (home == null) {
/*  53 */       KEYTOOL = null;
/*     */     } else {
/*  55 */       Path likely = Paths.get(home, new String[0]).resolve("bin").resolve("keytool");
/*  56 */       if (Files.exists(likely, new java.nio.file.LinkOption[0])) {
/*  57 */         KEYTOOL = likely;
/*     */       } else {
/*  59 */         KEYTOOL = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  64 */   private static final String KEY_STORE_TYPE = (PlatformDependent.javaVersion() >= 11) ? "PKCS12" : "JKS";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isAvailable() {
/*  71 */     return (KEYTOOL != null);
/*     */   }
/*     */ 
/*     */   
/*     */   static void generate(SelfSignedCertificate.Builder builder) throws IOException, GeneralSecurityException {
/*  76 */     String dirFqdn = builder.fqdn.replaceAll("[^\\w.-]", "x");
/*     */     
/*  78 */     Path directory = Files.createTempDirectory("keytool_" + dirFqdn, (FileAttribute<?>[])new FileAttribute[0]);
/*  79 */     Path keyStore = directory.resolve("keystore.jks");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  99 */       Process process = (new ProcessBuilder(new String[0])).command(new String[] { KEYTOOL.toAbsolutePath().toString(), "-genkeypair", "-keyalg", builder.algorithm, "-keysize", String.valueOf(builder.bits), "-startdate", DATE_FORMAT.format(builder.notBefore.toInstant().atZone(ZoneId.systemDefault())), "-validity", String.valueOf(builder.notBefore.toInstant().until(builder.notAfter.toInstant(), ChronoUnit.DAYS)), "-keystore", keyStore.toString(), "-alias", "alias", "-keypass", "insecurepassword", "-storepass", "insecurepassword", "-dname", "CN=" + builder.fqdn, "-storetype", KEY_STORE_TYPE }).redirectErrorStream(true).start();
/*     */       try {
/* 101 */         if (!process.waitFor(60L, TimeUnit.SECONDS)) {
/* 102 */           process.destroyForcibly();
/* 103 */           throw new IOException("keytool timeout");
/*     */         } 
/* 105 */       } catch (InterruptedException e) {
/* 106 */         process.destroyForcibly();
/* 107 */         Thread.currentThread().interrupt();
/* 108 */         throw new InterruptedIOException();
/*     */       } 
/*     */       
/* 111 */       if (process.exitValue() != 0) {
/* 112 */         ByteBuf buffer = Unpooled.buffer();
/*     */         try {
/* 114 */           InputStream stream = process.getInputStream(); try { do {
/*     */             
/* 116 */             } while (buffer.writeBytes(stream, 4096) != -1);
/*     */ 
/*     */ 
/*     */             
/* 120 */             if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/* 121 */               try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  String log = buffer.toString(StandardCharsets.UTF_8);
/* 122 */           throw new IOException("Keytool exited with status " + process.exitValue() + ": " + log);
/*     */         } finally {
/* 124 */           buffer.release();
/*     */         } 
/*     */       } 
/*     */       
/* 128 */       KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
/* 129 */       InputStream is = Files.newInputStream(keyStore, new java.nio.file.OpenOption[0]); 
/* 130 */       try { ks.load(is, "insecurepassword".toCharArray());
/* 131 */         if (is != null) is.close();  } catch (Throwable throwable) { if (is != null)
/* 132 */           try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)ks.getEntry("alias", new KeyStore.PasswordProtection("insecurepassword"
/* 133 */             .toCharArray()));
/* 134 */       builder.paths = SelfSignedCertificate.newSelfSignedCertificate(builder.fqdn, entry
/* 135 */           .getPrivateKey(), (X509Certificate)entry.getCertificate());
/* 136 */       builder.privateKey = entry.getPrivateKey();
/*     */     } finally {
/* 138 */       Files.deleteIfExists(keyStore);
/* 139 */       Files.delete(directory);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\KeytoolSelfSignedCertGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */