/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.handler.ssl.OpenSslCertificateException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ final class BoringSSLCertificateVerifyCallback
/*     */ {
/*     */   private static final boolean TRY_USING_EXTENDED_TRUST_MANAGER;
/*     */   private final QuicheQuicSslEngineMap engineMap;
/*     */   private final X509TrustManager manager;
/*     */   
/*     */   static {
/*     */     boolean tryUsingExtendedTrustManager;
/*     */     try {
/*  35 */       Class.forName(X509ExtendedTrustManager.class.getName());
/*  36 */       tryUsingExtendedTrustManager = true;
/*  37 */     } catch (Throwable cause) {
/*  38 */       tryUsingExtendedTrustManager = false;
/*     */     } 
/*  40 */     TRY_USING_EXTENDED_TRUST_MANAGER = tryUsingExtendedTrustManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BoringSSLCertificateVerifyCallback(QuicheQuicSslEngineMap engineMap, @Nullable X509TrustManager manager) {
/*  47 */     this.engineMap = engineMap;
/*  48 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */   
/*     */   int verify(long ssl, byte[][] x509, String authAlgorithm) {
/*  53 */     QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/*  54 */     if (engine == null)
/*     */     {
/*  56 */       return BoringSSL.X509_V_ERR_UNSPECIFIED;
/*     */     }
/*     */     
/*  59 */     if (this.manager == null) {
/*  60 */       this.engineMap.remove(ssl);
/*  61 */       return BoringSSL.X509_V_ERR_UNSPECIFIED;
/*     */     } 
/*     */     
/*  64 */     X509Certificate[] peerCerts = BoringSSL.certificates(x509);
/*     */     try {
/*  66 */       if (engine.getUseClientMode()) {
/*  67 */         if (TRY_USING_EXTENDED_TRUST_MANAGER && this.manager instanceof X509ExtendedTrustManager) {
/*  68 */           ((X509ExtendedTrustManager)this.manager).checkServerTrusted(peerCerts, authAlgorithm, engine);
/*     */         } else {
/*  70 */           this.manager.checkServerTrusted(peerCerts, authAlgorithm);
/*     */         }
/*     */       
/*  73 */       } else if (TRY_USING_EXTENDED_TRUST_MANAGER && this.manager instanceof X509ExtendedTrustManager) {
/*  74 */         ((X509ExtendedTrustManager)this.manager).checkClientTrusted(peerCerts, authAlgorithm, engine);
/*     */       } else {
/*  76 */         this.manager.checkClientTrusted(peerCerts, authAlgorithm);
/*     */       } 
/*     */       
/*  79 */       return BoringSSL.X509_V_OK;
/*  80 */     } catch (Throwable cause) {
/*  81 */       this.engineMap.remove(ssl);
/*     */       
/*  83 */       if (cause instanceof OpenSslCertificateException)
/*     */       {
/*     */         
/*  86 */         return ((OpenSslCertificateException)cause).errorCode();
/*     */       }
/*  88 */       if (cause instanceof java.security.cert.CertificateExpiredException) {
/*  89 */         return BoringSSL.X509_V_ERR_CERT_HAS_EXPIRED;
/*     */       }
/*  91 */       if (cause instanceof java.security.cert.CertificateNotYetValidException) {
/*  92 */         return BoringSSL.X509_V_ERR_CERT_NOT_YET_VALID;
/*     */       }
/*  94 */       return translateToError(cause);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int translateToError(Throwable cause) {
/*  99 */     if (cause instanceof java.security.cert.CertificateRevokedException) {
/* 100 */       return BoringSSL.X509_V_ERR_CERT_REVOKED;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     Throwable wrapped = cause.getCause();
/* 107 */     while (wrapped != null) {
/* 108 */       if (wrapped instanceof CertPathValidatorException) {
/* 109 */         CertPathValidatorException ex = (CertPathValidatorException)wrapped;
/* 110 */         CertPathValidatorException.Reason reason = ex.getReason();
/* 111 */         if (reason == CertPathValidatorException.BasicReason.EXPIRED) {
/* 112 */           return BoringSSL.X509_V_ERR_CERT_HAS_EXPIRED;
/*     */         }
/* 114 */         if (reason == CertPathValidatorException.BasicReason.NOT_YET_VALID) {
/* 115 */           return BoringSSL.X509_V_ERR_CERT_NOT_YET_VALID;
/*     */         }
/* 117 */         if (reason == CertPathValidatorException.BasicReason.REVOKED) {
/* 118 */           return BoringSSL.X509_V_ERR_CERT_REVOKED;
/*     */         }
/*     */       } 
/* 121 */       wrapped = wrapped.getCause();
/*     */     } 
/* 123 */     return BoringSSL.X509_V_ERR_UNSPECIFIED;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLCertificateVerifyCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */