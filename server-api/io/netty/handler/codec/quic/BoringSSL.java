/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.handler.ssl.util.LazyX509Certificate;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ final class BoringSSL
/*     */ {
/*  28 */   static final int SSL_VERIFY_NONE = BoringSSLNativeStaticallyReferencedJniMethods.ssl_verify_none();
/*     */   
/*  30 */   static final int SSL_VERIFY_FAIL_IF_NO_PEER_CERT = BoringSSLNativeStaticallyReferencedJniMethods.ssl_verify_fail_if_no_peer_cert();
/*  31 */   static final int SSL_VERIFY_PEER = BoringSSLNativeStaticallyReferencedJniMethods.ssl_verify_peer();
/*  32 */   static final int X509_V_OK = BoringSSLNativeStaticallyReferencedJniMethods.x509_v_ok();
/*     */   
/*  34 */   static final int X509_V_ERR_CERT_HAS_EXPIRED = BoringSSLNativeStaticallyReferencedJniMethods.x509_v_err_cert_has_expired();
/*     */   
/*  36 */   static final int X509_V_ERR_CERT_NOT_YET_VALID = BoringSSLNativeStaticallyReferencedJniMethods.x509_v_err_cert_not_yet_valid();
/*  37 */   static final int X509_V_ERR_CERT_REVOKED = BoringSSLNativeStaticallyReferencedJniMethods.x509_v_err_cert_revoked();
/*  38 */   static final int X509_V_ERR_UNSPECIFIED = BoringSSLNativeStaticallyReferencedJniMethods.x509_v_err_unspecified();
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
/*     */   static long SSLContext_new(boolean server, String[] applicationProtocols, BoringSSLHandshakeCompleteCallback handshakeCompleteCallback, BoringSSLCertificateCallback certificateCallback, BoringSSLCertificateVerifyCallback verifyCallback, @Nullable BoringSSLTlsextServernameCallback servernameCallback, @Nullable BoringSSLKeylogCallback keylogCallback, @Nullable BoringSSLSessionCallback sessionCallback, @Nullable BoringSSLPrivateKeyMethod privateKeyMethod, BoringSSLSessionTicketCallback sessionTicketCallback, int verifyMode, byte[][] subjectNames) {
/*  51 */     return SSLContext_new0(server, toWireFormat(applicationProtocols), handshakeCompleteCallback, certificateCallback, verifyCallback, servernameCallback, keylogCallback, sessionCallback, privateKeyMethod, sessionTicketCallback, verifyMode, subjectNames);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] toWireFormat(String[] applicationProtocols) {
/*  57 */     if (applicationProtocols == null)
/*  58 */       return null; 
/*     */     
/*  60 */     try { ByteArrayOutputStream out = new ByteArrayOutputStream(); 
/*  61 */       try { for (String p : applicationProtocols) {
/*  62 */           byte[] bytes = p.getBytes(StandardCharsets.US_ASCII);
/*  63 */           out.write(bytes.length);
/*  64 */           out.write(bytes);
/*     */         } 
/*  66 */         byte[] arrayOfByte = out.toByteArray();
/*  67 */         out.close(); return arrayOfByte; } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  68 */     { throw new IllegalStateException(e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   static native long SSLContext_new();
/*     */ 
/*     */   
/*     */   private static native long SSLContext_new0(boolean paramBoolean, byte[] paramArrayOfbyte, Object paramObject1, Object paramObject2, Object paramObject3, @Nullable Object paramObject4, @Nullable Object paramObject5, @Nullable Object paramObject6, @Nullable Object paramObject7, Object paramObject8, int paramInt, byte[][] paramArrayOfbyte1);
/*     */ 
/*     */   
/*     */   static native void SSLContext_set_early_data_enabled(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   static native long SSLContext_setSessionCacheSize(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long SSLContext_setSessionCacheTimeout(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SSLContext_setSessionTicketKeys(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static int SSLContext_set1_groups_list(long ctx, String... groups) {
/*  89 */     if (groups == null) {
/*  90 */       throw new NullPointerException("curves");
/*     */     }
/*  92 */     if (groups.length == 0) {
/*  93 */       throw new IllegalArgumentException();
/*     */     }
/*  95 */     StringBuilder sb = new StringBuilder();
/*  96 */     for (String group : groups) {
/*  97 */       sb.append(group);
/*     */       
/*  99 */       sb.append(':');
/*     */     } 
/* 101 */     sb.setLength(sb.length() - 1);
/* 102 */     return SSLContext_set1_groups_list(ctx, sb.toString());
/*     */   }
/*     */   
/*     */   static int SSLContext_set1_sigalgs_list(long ctx, String... sigalgs) {
/* 106 */     if (sigalgs.length == 0) {
/* 107 */       throw new IllegalArgumentException();
/*     */     }
/* 109 */     StringBuilder sb = new StringBuilder();
/* 110 */     for (String sigalg : sigalgs) {
/* 111 */       sb.append(sigalg);
/*     */       
/* 113 */       sb.append(':');
/*     */     } 
/* 115 */     sb.setLength(sb.length() - 1);
/* 116 */     return SSLContext_set1_sigalgs_list(ctx, sb.toString());
/*     */   }
/*     */   private static native int SSLContext_set1_sigalgs_list(long paramLong, String paramString);
/*     */   private static native int SSLContext_set1_groups_list(long paramLong, String paramString);
/*     */   
/*     */   static native void SSLContext_free(long paramLong);
/*     */   
/*     */   static long SSL_new(long context, boolean server, String hostname) {
/* 124 */     return SSL_new0(context, server, tlsExtHostName(hostname));
/*     */   }
/*     */   static native long SSL_new0(long paramLong, boolean paramBoolean, @Nullable String paramString);
/*     */   static native void SSL_free(long paramLong);
/*     */   static native Runnable SSL_getTask(long paramLong);
/*     */   static native void SSL_cleanup(long paramLong);
/*     */   
/*     */   static native long EVP_PKEY_parse(byte[] paramArrayOfbyte, String paramString);
/*     */   
/*     */   static native void EVP_PKEY_free(long paramLong);
/*     */   
/*     */   static native long CRYPTO_BUFFER_stack_new(long paramLong, byte[][] paramArrayOfbyte);
/*     */   
/*     */   static native void CRYPTO_BUFFER_stack_free(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   static native String ERR_last_error();
/*     */   
/*     */   @Nullable
/*     */   private static String tlsExtHostName(@Nullable String hostname) {
/* 144 */     if (hostname != null && hostname.endsWith("."))
/*     */     {
/*     */       
/* 147 */       hostname = hostname.substring(0, hostname.length() - 1);
/*     */     }
/* 149 */     return hostname;
/*     */   }
/*     */   
/*     */   static X509Certificate[] certificates(byte[][] chain) {
/* 153 */     X509Certificate[] peerCerts = new X509Certificate[chain.length];
/* 154 */     for (int i = 0; i < peerCerts.length; i++) {
/* 155 */       peerCerts[i] = (X509Certificate)new LazyX509Certificate(chain[i]);
/*     */     }
/* 157 */     return peerCerts;
/*     */   }
/*     */   
/*     */   static byte[][] subjectNames(X509Certificate[] certificates) {
/* 161 */     byte[][] subjectNames = new byte[certificates.length][];
/* 162 */     for (int i = 0; i < certificates.length; i++) {
/* 163 */       subjectNames[i] = certificates[i].getSubjectX500Principal().getEncoded();
/*     */     }
/* 165 */     return subjectNames;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */