/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.security.KeyStore;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ public final class FingerprintTrustManagerFactory
/*     */   extends SimpleTrustManagerFactory
/*     */ {
/*  84 */   private static final Pattern FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
/*  85 */   private static final Pattern FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
/*     */   
/*     */   private final FastThreadLocal<MessageDigest> tlmd;
/*     */   
/*     */   private final TrustManager tm;
/*     */   
/*     */   private final byte[][] fingerprints;
/*     */   
/*     */   public static FingerprintTrustManagerFactoryBuilder builder(String algorithm) {
/*  94 */     return new FingerprintTrustManagerFactoryBuilder(algorithm);
/*     */   }
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
/*     */   @Deprecated
/*     */   public FingerprintTrustManagerFactory(Iterable<String> fingerprints) {
/* 153 */     this("SHA1", toFingerprintArray(fingerprints));
/*     */   }
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
/*     */   @Deprecated
/*     */   public FingerprintTrustManagerFactory(String... fingerprints) {
/* 167 */     this("SHA1", toFingerprintArray(Arrays.asList(fingerprints)));
/*     */   }
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
/*     */   @Deprecated
/*     */   public FingerprintTrustManagerFactory(byte[]... fingerprints) {
/* 181 */     this("SHA1", fingerprints);
/*     */   }
/*     */   FingerprintTrustManagerFactory(final String algorithm, byte[][] fingerprints) { MessageDigest md; this.tm = new X509TrustManager() {
/*     */         public void checkClientTrusted(X509Certificate[] chain, String s) throws CertificateException { checkTrusted("client", chain); }
/*     */         public void checkServerTrusted(X509Certificate[] chain, String s) throws CertificateException { checkTrusted("server", chain); } private void checkTrusted(String type, X509Certificate[] chain) throws CertificateException { X509Certificate cert = chain[0]; byte[] fingerprint = fingerprint(cert); boolean found = false; for (byte[] allowedFingerprint : FingerprintTrustManagerFactory.this.fingerprints) {
/*     */             if (Arrays.equals(fingerprint, allowedFingerprint)) {
/*     */               found = true; break;
/*     */             } 
/*     */           }  if (!found)
/*     */             throw new CertificateException(type + " certificate with unknown fingerprint: " + cert.getSubjectDN());  } private byte[] fingerprint(X509Certificate cert) throws CertificateEncodingException { MessageDigest md = (MessageDigest)FingerprintTrustManagerFactory.this.tlmd.get(); md.reset(); return md.digest(cert.getEncoded()); } public X509Certificate[] getAcceptedIssuers() { return EmptyArrays.EMPTY_X509_CERTIFICATES; }
/* 191 */       }; ObjectUtil.checkNotNull(algorithm, "algorithm");
/* 192 */     ObjectUtil.checkNotNull(fingerprints, "fingerprints");
/*     */     
/* 194 */     if (fingerprints.length == 0) {
/* 195 */       throw new IllegalArgumentException("No fingerprints provided");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 201 */       md = MessageDigest.getInstance(algorithm);
/* 202 */     } catch (NoSuchAlgorithmException e) {
/* 203 */       throw new IllegalArgumentException(
/* 204 */           String.format("Unsupported hash algorithm: %s", new Object[] { algorithm }), e);
/*     */     } 
/*     */     
/* 207 */     int hashLength = md.getDigestLength();
/* 208 */     List<byte[]> list = (List)new ArrayList<>(fingerprints.length);
/* 209 */     for (byte[] f : fingerprints) {
/* 210 */       if (f == null) {
/*     */         break;
/*     */       }
/* 213 */       if (f.length != hashLength)
/* 214 */         throw new IllegalArgumentException(
/* 215 */             String.format("malformed fingerprint (length is %d but expected %d): %s", new Object[] {
/* 216 */                 Integer.valueOf(f.length), Integer.valueOf(hashLength), ByteBufUtil.hexDump(Unpooled.wrappedBuffer(f))
/*     */               })); 
/* 218 */       list.add((byte[])f.clone());
/*     */     } 
/*     */     
/* 221 */     this.tlmd = new FastThreadLocal<MessageDigest>()
/*     */       {
/*     */         protected MessageDigest initialValue()
/*     */         {
/*     */           try {
/* 226 */             return MessageDigest.getInstance(algorithm);
/* 227 */           } catch (NoSuchAlgorithmException e) {
/* 228 */             throw new IllegalArgumentException(
/* 229 */                 String.format("Unsupported hash algorithm: %s", new Object[] { this.val$algorithm }), e);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 234 */     this.fingerprints = list.<byte[]>toArray(new byte[0][]); }
/*     */ 
/*     */   
/*     */   static byte[][] toFingerprintArray(Iterable<String> fingerprints) {
/* 238 */     ObjectUtil.checkNotNull(fingerprints, "fingerprints");
/*     */     
/* 240 */     List<byte[]> list = (List)new ArrayList<>();
/* 241 */     for (String f : fingerprints) {
/* 242 */       if (f == null) {
/*     */         break;
/*     */       }
/*     */       
/* 246 */       if (!FINGERPRINT_PATTERN.matcher(f).matches()) {
/* 247 */         throw new IllegalArgumentException("malformed fingerprint: " + f);
/*     */       }
/* 249 */       f = FINGERPRINT_STRIP_PATTERN.matcher(f).replaceAll("");
/*     */       
/* 251 */       list.add(StringUtil.decodeHexDump(f));
/*     */     } 
/*     */     
/* 254 */     return list.<byte[]>toArray(new byte[0][]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineInit(KeyStore keyStore) throws Exception {}
/*     */ 
/*     */   
/*     */   protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {}
/*     */ 
/*     */   
/*     */   protected TrustManager[] engineGetTrustManagers() {
/* 265 */     return new TrustManager[] { this.tm };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\FingerprintTrustManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */