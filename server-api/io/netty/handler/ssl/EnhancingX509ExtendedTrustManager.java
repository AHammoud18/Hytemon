/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.net.Socket;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
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
/*     */ final class EnhancingX509ExtendedTrustManager
/*     */   extends X509ExtendedTrustManager
/*     */ {
/*     */   private final X509ExtendedTrustManager wrapped;
/*     */   
/*     */   EnhancingX509ExtendedTrustManager(X509TrustManager wrapped) {
/*  36 */     this.wrapped = (X509ExtendedTrustManager)wrapped;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
/*  42 */     this.wrapped.checkClientTrusted(chain, authType, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
/*     */     try {
/*  49 */       this.wrapped.checkServerTrusted(chain, authType, socket);
/*  50 */     } catch (CertificateException e) {
/*  51 */       throwEnhancedCertificateException(chain, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
/*  58 */     this.wrapped.checkClientTrusted(chain, authType, engine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
/*     */     try {
/*  65 */       this.wrapped.checkServerTrusted(chain, authType, engine);
/*  66 */     } catch (CertificateException e) {
/*  67 */       throwEnhancedCertificateException(chain, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/*  74 */     this.wrapped.checkClientTrusted(chain, authType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/*     */     try {
/*  81 */       this.wrapped.checkServerTrusted(chain, authType);
/*  82 */     } catch (CertificateException e) {
/*  83 */       throwEnhancedCertificateException(chain, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Certificate[] getAcceptedIssuers() {
/*  89 */     return this.wrapped.getAcceptedIssuers();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwEnhancedCertificateException(X509Certificate[] chain, CertificateException e) throws CertificateException {
/*  95 */     String message = e.getMessage();
/*  96 */     if (message != null && e.getMessage().startsWith("No subject alternative DNS name matching")) {
/*  97 */       StringBuilder names = new StringBuilder(64);
/*  98 */       for (int i = 0; i < chain.length; i++) {
/*  99 */         X509Certificate cert = chain[i];
/* 100 */         Collection<List<?>> collection = cert.getSubjectAlternativeNames();
/* 101 */         if (collection != null) {
/* 102 */           for (List<?> altNames : collection) {
/*     */             
/* 104 */             if (altNames.size() >= 2 && ((Integer)altNames.get(0)).intValue() == 2) {
/* 105 */               names.append((String)altNames.get(1)).append(",");
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 110 */       if (names.length() != 0) {
/*     */         
/* 112 */         names.setLength(names.length() - 1);
/* 113 */         throw new CertificateException(message + " Subject alternative DNS names in the certificate chain of " + chain.length + " certificate(s): " + names, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     throw e;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\EnhancingX509ExtendedTrustManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */