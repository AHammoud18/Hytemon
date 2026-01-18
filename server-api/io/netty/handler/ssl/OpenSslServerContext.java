/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
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
/*     */ 
/*     */ public final class OpenSslServerContext
/*     */   extends OpenSslContext
/*     */ {
/*     */   private final OpenSslServerSessionContext sessionContext;
/*     */   
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile) throws SSLException {
/*  51 */     this(certChainFile, keyFile, (String)null);
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
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
/*  65 */     this(certChainFile, keyFile, keyPassword, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0L, 0L);
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
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  90 */     this(certChainFile, keyFile, keyPassword, ciphers, IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
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
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 116 */     this(certChainFile, keyFile, keyPassword, ciphers, 
/* 117 */         toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 141 */     this(certChainFile, keyFile, keyPassword, trustManagerFactory, ciphers, 
/* 142 */         toNegotiator(config), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 166 */     this((File)null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, (CipherSuiteFilter)null, apn, sessionCacheSize, sessionTimeout);
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
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 192 */     this((File)null, (TrustManagerFactory)null, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
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
/*     */   public OpenSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 231 */     this(trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, 
/* 232 */         toNegotiator(config), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig config, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 257 */     this((File)null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, cipherFilter, 
/* 258 */         toNegotiator(config), sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 283 */     this((File)null, trustManagerFactory, certChainFile, keyFile, keyPassword, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
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
/*     */   public OpenSslServerContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 323 */     this(toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, 
/* 324 */         toX509CertificatesInternal(keyCertChainFile), toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE, (String[])null, false, false, 
/*     */         
/* 326 */         KeyStore.getDefaultType(), (ResumptionController)null, (Map.Entry<SslContextOption<?>, Object>[])new Map.Entry[0]);
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
/*     */   OpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, String keyStore, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/* 338 */     this(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, 
/* 339 */         toNegotiator(apn), sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp, keyStore, resumptionController, options);
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
/*     */   private OpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, String keyStore, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/* 352 */     super(ciphers, cipherFilter, apn, 1, (Certificate[])keyCertChain, clientAuth, protocols, startTls, enableOcsp, (List<SNIServerName>)null, resumptionController, options);
/*     */ 
/*     */ 
/*     */     
/* 356 */     boolean success = false;
/* 357 */     boolean fallbackToJdkSignatureProviders = isJdkSignatureFallbackEnabled(options);
/*     */     try {
/* 359 */       OpenSslKeyMaterialProvider.validateKeyMaterialSupported(keyCertChain, key, keyPassword, fallbackToJdkSignatureProviders);
/*     */       
/* 361 */       this.sessionContext = ReferenceCountedOpenSslServerContext.newSessionContext(this, this.ctx, this.engines, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, keyStore, sessionCacheSize, sessionTimeout, resumptionController, fallbackToJdkSignatureProviders);
/*     */ 
/*     */ 
/*     */       
/* 365 */       success = true;
/*     */     } finally {
/* 367 */       if (!success) {
/* 368 */         release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OpenSslServerSessionContext sessionContext() {
/* 375 */     return this.sessionContext;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslServerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */