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
/*     */ public final class OpenSslClientContext
/*     */   extends OpenSslContext
/*     */ {
/*     */   private final OpenSslSessionContext sessionContext;
/*     */   
/*     */   @Deprecated
/*     */   public OpenSslClientContext() throws SSLException {
/*  49 */     this((File)null, (TrustManagerFactory)null, (File)null, (File)null, (String)null, (KeyManagerFactory)null, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
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
/*     */   public OpenSslClientContext(File certChainFile) throws SSLException {
/*  61 */     this(certChainFile, (TrustManagerFactory)null);
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
/*     */   public OpenSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
/*  74 */     this((File)null, trustManagerFactory);
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
/*     */   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  89 */     this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
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
/*     */   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 113 */     this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
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
/*     */   public OpenSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 138 */     this(certChainFile, trustManagerFactory, (File)null, (File)null, (String)null, (KeyManagerFactory)null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
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
/*     */   @Deprecated
/*     */   public OpenSslClientContext(File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 180 */     this(toX509CertificatesInternal(trustCertCollectionFile), trustManagerFactory, 
/* 181 */         toX509CertificatesInternal(keyCertChainFile), toPrivateKeyInternal(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, (String[])null, sessionCacheSize, sessionTimeout, false, 
/*     */         
/* 183 */         KeyStore.getDefaultType(), (String)null, (List<SNIServerName>)null, (ResumptionController)null, (Map.Entry<SslContextOption<?>, Object>[])new Map.Entry[0]);
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
/*     */   OpenSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp, String keyStore, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/* 195 */     super(ciphers, cipherFilter, apn, 0, (Certificate[])keyCertChain, ClientAuth.NONE, protocols, false, endpointIdentificationAlgorithm, enableOcsp, serverNames, resumptionController, options);
/*     */     
/* 197 */     boolean success = false;
/* 198 */     boolean supportJdkSignatureFallback = isJdkSignatureFallbackEnabled(options);
/*     */     try {
/* 200 */       OpenSslKeyMaterialProvider.validateKeyMaterialSupported(keyCertChain, key, keyPassword, supportJdkSignatureFallback);
/*     */       
/* 202 */       this.sessionContext = ReferenceCountedOpenSslClientContext.newSessionContext(this, this.ctx, this.engines, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, keyStore, sessionCacheSize, sessionTimeout, resumptionController, supportJdkSignatureFallback);
/*     */ 
/*     */ 
/*     */       
/* 206 */       success = true;
/*     */     } finally {
/* 208 */       if (!success) {
/* 209 */         release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OpenSslSessionContext sessionContext() {
/* 216 */     return this.sessionContext;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslClientContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */