/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.net.Socket;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
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
/*     */ final class ResumptionController
/*     */ {
/*  37 */   private final Set<SSLEngine> confirmedValidations = Collections.synchronizedSet(
/*  38 */       Collections.newSetFromMap(new WeakHashMap<>()));
/*  39 */   private final AtomicReference<ResumableX509ExtendedTrustManager> resumableTm = new AtomicReference<>();
/*     */ 
/*     */   
/*     */   public TrustManager wrapIfNeeded(TrustManager tm) {
/*  43 */     if (tm instanceof ResumableX509ExtendedTrustManager) {
/*  44 */       if (!(tm instanceof X509ExtendedTrustManager)) {
/*  45 */         throw new IllegalStateException("ResumableX509ExtendedTrustManager implementation must be a subclass of X509ExtendedTrustManager, found: " + (
/*  46 */             (tm == null) ? null : tm.getClass()));
/*     */       }
/*  48 */       if (!this.resumableTm.compareAndSet(null, (ResumableX509ExtendedTrustManager)tm)) {
/*  49 */         throw new IllegalStateException("Only one ResumableX509ExtendedTrustManager can be configured for resumed sessions");
/*     */       }
/*     */       
/*  52 */       return new X509ExtendedWrapTrustManager((X509ExtendedTrustManager)tm, this.confirmedValidations);
/*     */     } 
/*  54 */     return tm;
/*     */   }
/*     */   
/*     */   public void remove(SSLEngine engine) {
/*  58 */     if (this.resumableTm.get() != null) {
/*  59 */       this.confirmedValidations.remove(unwrapEngine(engine));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateResumeIfNeeded(SSLEngine engine) throws CertificateException, SSLPeerUnverifiedException {
/*  66 */     SSLSession session = engine.getSession();
/*  67 */     boolean valid = session.isValid();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ResumableX509ExtendedTrustManager tm;
/*     */ 
/*     */ 
/*     */     
/*  76 */     if (valid && (engine.getUseClientMode() || engine.getNeedClientAuth() || engine.getWantClientAuth()) && (
/*  77 */       tm = this.resumableTm.get()) != null) {
/*     */       
/*  79 */       engine = unwrapEngine(engine);
/*     */       
/*  81 */       if (!this.confirmedValidations.remove(engine)) {
/*     */         Certificate[] peerCertificates;
/*     */         try {
/*  84 */           peerCertificates = session.getPeerCertificates();
/*  85 */         } catch (SSLPeerUnverifiedException e) {
/*  86 */           if (engine.getUseClientMode() || engine.getNeedClientAuth())
/*     */           {
/*  88 */             throw e;
/*     */           }
/*     */           
/*  91 */           return false;
/*     */         } 
/*     */ 
/*     */         
/*  95 */         if (engine.getUseClientMode()) {
/*     */           
/*  97 */           tm.resumeServerTrusted(chainOf(peerCertificates), engine);
/*     */         } else {
/*     */           
/* 100 */           tm.resumeClientTrusted(chainOf(peerCertificates), engine);
/*     */         } 
/* 102 */         return true;
/*     */       } 
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   private static SSLEngine unwrapEngine(SSLEngine engine) {
/* 109 */     if (engine instanceof JdkSslEngine) {
/* 110 */       return ((JdkSslEngine)engine).getWrappedEngine();
/*     */     }
/* 112 */     return engine;
/*     */   }
/*     */   
/*     */   private static X509Certificate[] chainOf(Certificate[] peerCertificates) {
/* 116 */     if (peerCertificates instanceof X509Certificate[])
/*     */     {
/* 118 */       return (X509Certificate[])peerCertificates;
/*     */     }
/* 120 */     X509Certificate[] chain = new X509Certificate[peerCertificates.length];
/* 121 */     for (int i = 0; i < peerCertificates.length; i++) {
/* 122 */       Certificate cert = peerCertificates[i];
/* 123 */       if (cert instanceof X509Certificate || cert == null) {
/* 124 */         chain[i] = (X509Certificate)cert;
/*     */       } else {
/* 126 */         throw new IllegalArgumentException("Only X509Certificates are supported, found: " + cert.getClass());
/*     */       } 
/*     */     } 
/* 129 */     return chain;
/*     */   }
/*     */   
/*     */   private static final class X509ExtendedWrapTrustManager extends X509ExtendedTrustManager {
/*     */     private final X509ExtendedTrustManager trustManager;
/*     */     private final Set<SSLEngine> confirmedValidations;
/*     */     
/*     */     X509ExtendedWrapTrustManager(X509ExtendedTrustManager trustManager, Set<SSLEngine> confirmedValidations) {
/* 137 */       this.trustManager = trustManager;
/* 138 */       this.confirmedValidations = confirmedValidations;
/*     */     }
/*     */     
/*     */     private static void unsupported() throws CertificateException {
/* 142 */       throw new CertificateException(new UnsupportedOperationException("Resumable trust managers require the SSLEngine parameter"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
/* 149 */       unsupported();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
/* 155 */       unsupported();
/*     */     }
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/* 160 */       unsupported();
/*     */     }
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
/* 165 */       unsupported();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
/* 171 */       this.trustManager.checkClientTrusted(chain, authType, engine);
/* 172 */       this.confirmedValidations.add(engine);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
/* 178 */       this.trustManager.checkServerTrusted(chain, authType, engine);
/* 179 */       this.confirmedValidations.add(engine);
/*     */     }
/*     */ 
/*     */     
/*     */     public X509Certificate[] getAcceptedIssuers() {
/* 184 */       return this.trustManager.getAcceptedIssuers();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ResumptionController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */