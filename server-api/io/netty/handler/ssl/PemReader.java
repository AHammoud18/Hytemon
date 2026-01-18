/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.KeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ final class PemReader
/*     */ {
/*  42 */   private static final Pattern CERT_HEADER = Pattern.compile("-+BEGIN\\s[^-\\r\\n]*CERTIFICATE[^-\\r\\n]*-+(?:\\s|\\r|\\n)+");
/*     */   
/*  44 */   private static final Pattern CERT_FOOTER = Pattern.compile("-+END\\s[^-\\r\\n]*CERTIFICATE[^-\\r\\n]*-+(?:\\s|\\r|\\n)*");
/*     */   
/*  46 */   private static final Pattern KEY_HEADER = Pattern.compile("-+BEGIN\\s[^-\\r\\n]*PRIVATE\\s+KEY[^-\\r\\n]*-+(?:\\s|\\r|\\n)+");
/*     */   
/*  48 */   private static final Pattern KEY_FOOTER = Pattern.compile("-+END\\s[^-\\r\\n]*PRIVATE\\s+KEY[^-\\r\\n]*-+(?:\\s|\\r|\\n)*");
/*     */   
/*  50 */   private static final Pattern BODY = Pattern.compile("[a-z0-9+/=][a-z0-9+/=\\r\\n]*", 2);
/*     */   static ByteBuf[] readCertificates(File file) throws CertificateException {
/*     */     
/*  53 */     try { InputStream in = new FileInputStream(file); 
/*  54 */       try { ByteBuf[] arrayOfByteBuf = readCertificates(in);
/*  55 */         in.close(); return arrayOfByteBuf; } catch (Throwable throwable) { try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  56 */     { throw new CertificateException("could not find certificate file: " + file); }
/*     */   
/*     */   }
/*     */   
/*     */   static ByteBuf[] readCertificates(InputStream in) throws CertificateException {
/*     */     String content;
/*     */     try {
/*  63 */       content = readContent(in);
/*  64 */     } catch (IOException e) {
/*  65 */       throw new CertificateException("failed to read certificate input stream", e);
/*     */     } 
/*     */     
/*  68 */     List<ByteBuf> certs = new ArrayList<>();
/*  69 */     Matcher m = CERT_HEADER.matcher(content);
/*  70 */     int start = 0;
/*     */     
/*  72 */     while (m.find(start)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       start = m.end();
/*  81 */       m.usePattern(BODY);
/*  82 */       if (!m.find(start)) {
/*     */         break;
/*     */       }
/*     */       
/*  86 */       ByteBuf base64 = Unpooled.copiedBuffer(m.group(0), CharsetUtil.US_ASCII);
/*  87 */       start = m.end();
/*  88 */       m.usePattern(CERT_FOOTER);
/*  89 */       if (!m.find(start)) {
/*     */         break;
/*     */       }
/*     */       
/*  93 */       ByteBuf der = Base64.decode(base64);
/*  94 */       base64.release();
/*  95 */       certs.add(der);
/*     */       
/*  97 */       start = m.end();
/*  98 */       m.usePattern(CERT_HEADER);
/*     */     } 
/*     */     
/* 101 */     if (certs.isEmpty()) {
/* 102 */       throw new CertificateException("found no certificates in input stream");
/*     */     }
/*     */     
/* 105 */     return certs.<ByteBuf>toArray(new ByteBuf[0]);
/*     */   }
/*     */   static ByteBuf readPrivateKey(File file) throws KeyException {
/*     */     
/* 109 */     try { InputStream in = new FileInputStream(file); 
/* 110 */       try { ByteBuf byteBuf = readPrivateKey(in);
/* 111 */         in.close(); return byteBuf; } catch (Throwable throwable) { try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 112 */     { throw new KeyException("could not find key file: " + file); }
/*     */   
/*     */   }
/*     */   
/*     */   static ByteBuf readPrivateKey(InputStream in) throws KeyException {
/*     */     String content;
/*     */     try {
/* 119 */       content = readContent(in);
/* 120 */     } catch (IOException e) {
/* 121 */       throw new KeyException("failed to read key input stream", e);
/*     */     } 
/* 123 */     int start = 0;
/* 124 */     Matcher m = KEY_HEADER.matcher(content);
/* 125 */     if (!m.find(start)) {
/* 126 */       throw keyNotFoundException();
/*     */     }
/* 128 */     start = m.end();
/* 129 */     m.usePattern(BODY);
/* 130 */     if (!m.find(start)) {
/* 131 */       throw keyNotFoundException();
/*     */     }
/*     */     
/* 134 */     ByteBuf base64 = Unpooled.copiedBuffer(m.group(0), CharsetUtil.US_ASCII);
/* 135 */     start = m.end();
/* 136 */     m.usePattern(KEY_FOOTER);
/* 137 */     if (!m.find(start))
/*     */     {
/* 139 */       throw keyNotFoundException();
/*     */     }
/* 141 */     ByteBuf der = Base64.decode(base64);
/* 142 */     base64.release();
/* 143 */     return der;
/*     */   }
/*     */   
/*     */   private static KeyException keyNotFoundException() {
/* 147 */     return new KeyException("could not find a PKCS #8 private key in input stream (see https://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readContent(InputStream in) throws IOException {
/* 152 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 153 */     byte[] buf = new byte[8192];
/*     */     while (true) {
/* 155 */       int ret = in.read(buf);
/* 156 */       if (ret < 0) {
/*     */         break;
/*     */       }
/* 159 */       out.write(buf, 0, ret);
/*     */     } 
/* 161 */     return out.toString(CharsetUtil.US_ASCII.name());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\PemReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */