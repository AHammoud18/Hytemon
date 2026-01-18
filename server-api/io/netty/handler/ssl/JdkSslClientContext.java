/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
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
/*     */ @Deprecated
/*     */ public final class JdkSslClientContext
/*     */   extends JdkSslContext
/*     */ {
/*     */   @Deprecated
/*     */   public JdkSslClientContext() throws SSLException {
/*  51 */     this((File)null, (TrustManagerFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File certChainFile) throws SSLException {
/*  63 */     this(certChainFile, (TrustManagerFactory)null);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
/*  76 */     this((File)null, trustManagerFactory);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  91 */     this(certChainFile, trustManagerFactory, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0L, 0L);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 118 */     this(certChainFile, trustManagerFactory, ciphers, IdentityCipherSuiteFilter.INSTANCE, 
/* 119 */         toNegotiator(toApplicationProtocolConfig(nextProtocols), false), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 145 */     this(certChainFile, trustManagerFactory, ciphers, cipherFilter, 
/* 146 */         toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 172 */     this((Provider)null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JdkSslClientContext(Provider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 179 */     super(newSSLContext(provider, toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, (X509Certificate[])null, (PrivateKey)null, (String)null, (KeyManagerFactory)null, sessionCacheSize, sessionTimeout, (SecureRandom)null, 
/*     */           
/* 181 */           KeyStore.getDefaultType(), (ResumptionController)null), true, ciphers, cipherFilter, apn, ClientAuth.NONE, (String[])null, false);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 221 */     this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, 
/* 222 */         toNegotiator(apn, false), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public JdkSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 261 */     super(newSSLContext((Provider)null, toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, 
/*     */           
/* 263 */           toX509CertificatesInternal(keyCertChainFile), toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout, (SecureRandom)null, 
/*     */           
/* 265 */           KeyStore.getDefaultType(), (ResumptionController)null), true, ciphers, cipherFilter, apn, ClientAuth.NONE, (String[])null, false);
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
/*     */   JdkSslClientContext(Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, SecureRandom secureRandom, String keyStoreType, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames, ResumptionController resumptionController) throws SSLException {
/* 277 */     super(newSSLContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, sessionCacheSize, sessionTimeout, secureRandom, keyStoreType, resumptionController), true, ciphers, cipherFilter, 
/*     */ 
/*     */         
/* 280 */         toNegotiator(apn, false), ClientAuth.NONE, protocols, false, endpointIdentificationAlgorithm, serverNames, resumptionController);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SSLContext newSSLContext(Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, long sessionCacheSize, long sessionTimeout, SecureRandom secureRandom, String keyStore, ResumptionController resumptionController) throws SSLException {
/*     */     try {
/* 292 */       if (trustCertCollection != null) {
/* 293 */         trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory, keyStore);
/*     */       }
/* 295 */       if (keyCertChain != null) {
/* 296 */         keyManagerFactory = buildKeyManagerFactory(keyCertChain, (String)null, key, keyPassword, keyManagerFactory, keyStore);
/*     */       }
/*     */ 
/*     */       
/* 300 */       SSLContext ctx = (sslContextProvider == null) ? SSLContext.getInstance("TLS") : SSLContext.getInstance("TLS", sslContextProvider);
/* 301 */       ctx.init((keyManagerFactory == null) ? null : keyManagerFactory.getKeyManagers(), 
/* 302 */           (trustManagerFactory == null) ? null : 
/* 303 */           wrapIfNeeded(trustManagerFactory.getTrustManagers(), resumptionController), secureRandom);
/*     */ 
/*     */       
/* 306 */       SSLSessionContext sessCtx = ctx.getClientSessionContext();
/* 307 */       if (sessionCacheSize > 0L) {
/* 308 */         sessCtx.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
/*     */       }
/* 310 */       if (sessionTimeout > 0L) {
/* 311 */         sessCtx.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
/*     */       }
/* 313 */       return ctx;
/* 314 */     } catch (Exception e) {
/* 315 */       if (e instanceof SSLException) {
/* 316 */         throw (SSLException)e;
/*     */       }
/* 318 */       throw new SSLException("failed to initialize the client-side SSL context", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static TrustManager[] wrapIfNeeded(TrustManager[] tms, ResumptionController resumptionController) {
/* 323 */     if (resumptionController != null) {
/* 324 */       for (int i = 0; i < tms.length; i++) {
/* 325 */         tms[i] = resumptionController.wrapIfNeeded(tms[i]);
/*     */       }
/*     */     }
/* 328 */     return tms;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkSslClientContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */