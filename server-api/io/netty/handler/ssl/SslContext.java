/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufInputStream;
/*      */ import io.netty.handler.ssl.util.BouncyCastleUtil;
/*      */ import io.netty.util.AttributeMap;
/*      */ import io.netty.util.DefaultAttributeMap;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.security.AlgorithmParameters;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.KeyException;
/*      */ import java.security.KeyFactory;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.Provider;
/*      */ import java.security.SecureRandom;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.spec.InvalidKeySpecException;
/*      */ import java.security.spec.PKCS8EncodedKeySpec;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Executor;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.EncryptedPrivateKeyInfo;
/*      */ import javax.crypto.NoSuchPaddingException;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.SecretKeyFactory;
/*      */ import javax.crypto.spec.PBEKeySpec;
/*      */ import javax.net.ssl.KeyManagerFactory;
/*      */ import javax.net.ssl.SNIServerName;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLSessionContext;
/*      */ import javax.net.ssl.TrustManagerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SslContext
/*      */ {
/*      */   static final String ALIAS = "key";
/*      */   static final CertificateFactory X509_CERT_FACTORY;
/*      */   private final boolean startTls;
/*      */   
/*      */   static {
/*      */     try {
/*  102 */       X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
/*  103 */     } catch (CertificateException e) {
/*  104 */       throw new IllegalStateException("unable to instance X.509 CertificateFactory", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  109 */   private final AttributeMap attributes = (AttributeMap)new DefaultAttributeMap();
/*      */ 
/*      */   
/*      */   final ResumptionController resumptionController;
/*      */   
/*      */   private static final String OID_PKCS5_PBES2 = "1.2.840.113549.1.5.13";
/*      */   
/*      */   private static final String PBES2 = "PBES2";
/*      */ 
/*      */   
/*      */   public static SslProvider defaultServerProvider() {
/*  120 */     return defaultProvider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SslProvider defaultClientProvider() {
/*  129 */     return defaultProvider();
/*      */   }
/*      */   
/*      */   private static SslProvider defaultProvider() {
/*  133 */     if (OpenSsl.isAvailable()) {
/*  134 */       return SslProvider.OPENSSL;
/*      */     }
/*  136 */     return SslProvider.JDK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(File certChainFile, File keyFile) throws SSLException {
/*  150 */     return newServerContext(certChainFile, keyFile, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
/*  166 */     return newServerContext(null, certChainFile, keyFile, keyPassword);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  193 */     return newServerContext((SslProvider)null, certChainFile, keyFile, keyPassword, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  221 */     return newServerContext((SslProvider)null, certChainFile, keyFile, keyPassword, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile) throws SSLException {
/*  239 */     return newServerContext(provider, certChainFile, keyFile, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword) throws SSLException {
/*  257 */     return newServerContext(provider, certChainFile, keyFile, keyPassword, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig)null, 0L, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  287 */     return newServerContext(provider, certChainFile, keyFile, keyPassword, ciphers, IdentityCipherSuiteFilter.INSTANCE, 
/*      */         
/*  289 */         toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  322 */     return newServerContext(provider, null, trustManagerFactory, certChainFile, keyFile, keyPassword, null, ciphers, IdentityCipherSuiteFilter.INSTANCE, 
/*      */ 
/*      */         
/*  325 */         toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  354 */     return newServerContext(provider, null, null, certChainFile, keyFile, keyPassword, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 
/*  355 */         KeyStore.getDefaultType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newServerContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  398 */     return newServerContext(provider, trustCertCollectionFile, trustManagerFactory, keyCertChainFile, keyFile, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 
/*      */         
/*  400 */         KeyStore.getDefaultType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static SslContext newServerContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, String keyStore) throws SSLException {
/*      */     try {
/*  443 */       return newServerContextInternal(provider, null, toX509Certificates(trustCertCollectionFile), trustManagerFactory, 
/*  444 */           toX509Certificates(keyCertChainFile), 
/*  445 */           toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, ClientAuth.NONE, null, false, false, null, keyStore, (Map.Entry<SslContextOption<?>, Object>[])new Map.Entry[0]);
/*      */ 
/*      */     
/*      */     }
/*  449 */     catch (Exception e) {
/*  450 */       if (e instanceof SSLException) {
/*  451 */         throw (SSLException)e;
/*      */       }
/*  453 */       throw new SSLException("failed to initialize the server-side SSL context", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static SslContext newServerContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, SecureRandom secureRandom, String keyStoreType, Map.Entry<SslContextOption<?>, Object>... ctxOptions) throws SSLException {
/*  468 */     if (provider == null) {
/*  469 */       provider = defaultServerProvider();
/*      */     }
/*      */     
/*  472 */     ResumptionController resumptionController = new ResumptionController();
/*      */     
/*  474 */     switch (provider) {
/*      */       case JDK:
/*  476 */         if (enableOcsp) {
/*  477 */           throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider);
/*      */         }
/*  479 */         return new JdkSslServerContext(sslContextProvider, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, secureRandom, keyStoreType, resumptionController);
/*      */ 
/*      */ 
/*      */       
/*      */       case OPENSSL:
/*  484 */         verifyNullSslContextProvider(provider, sslContextProvider);
/*  485 */         return new OpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp, keyStoreType, resumptionController, ctxOptions);
/*      */ 
/*      */ 
/*      */       
/*      */       case OPENSSL_REFCNT:
/*  490 */         verifyNullSslContextProvider(provider, sslContextProvider);
/*  491 */         return new ReferenceCountedOpenSslServerContext(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp, keyStoreType, resumptionController, ctxOptions);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  496 */     throw new Error("Unexpected provider: " + provider);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void verifyNullSslContextProvider(SslProvider provider, Provider sslContextProvider) {
/*  501 */     if (sslContextProvider != null) {
/*  502 */       throw new IllegalArgumentException("Java Security Provider unsupported for SslProvider: " + provider);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext() throws SSLException {
/*  514 */     return newClientContext(null, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(File certChainFile) throws SSLException {
/*  527 */     return newClientContext((SslProvider)null, certChainFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
/*  542 */     return newClientContext(null, null, trustManagerFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  560 */     return newClientContext(null, certChainFile, trustManagerFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  588 */     return newClientContext((SslProvider)null, certChainFile, trustManagerFactory, ciphers, nextProtocols, sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  618 */     return newClientContext(null, certChainFile, trustManagerFactory, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider) throws SSLException {
/*  634 */     return newClientContext(provider, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, File certChainFile) throws SSLException {
/*  650 */     return newClientContext(provider, certChainFile, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  668 */     return newClientContext(provider, null, trustManagerFactory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  688 */     return newClientContext(provider, certChainFile, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, null, 0L, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  720 */     return newClientContext(provider, certChainFile, trustManagerFactory, null, null, null, null, ciphers, IdentityCipherSuiteFilter.INSTANCE, 
/*      */ 
/*      */         
/*  723 */         toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*  755 */     return newClientContext(provider, certChainFile, trustManagerFactory, null, null, null, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static SslContext newClientContext(SslProvider provider, File trustCertCollectionFile, TrustManagerFactory trustManagerFactory, File keyCertChainFile, File keyFile, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout) throws SSLException {
/*      */     try {
/*  806 */       return newClientContextInternal(provider, null, 
/*  807 */           toX509Certificates(trustCertCollectionFile), trustManagerFactory, 
/*  808 */           toX509Certificates(keyCertChainFile), toPrivateKey(keyFile, keyPassword), keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, null, sessionCacheSize, sessionTimeout, false, null, 
/*      */ 
/*      */           
/*  811 */           KeyStore.getDefaultType(), SslUtils.defaultEndpointVerificationAlgorithm, 
/*      */           
/*  813 */           Collections.emptyList(), (Map.Entry<SslContextOption<?>, Object>[])new Map.Entry[0]);
/*  814 */     } catch (Exception e) {
/*  815 */       if (e instanceof SSLException) {
/*  816 */         throw (SSLException)e;
/*      */       }
/*  818 */       throw new SSLException("failed to initialize the client-side SSL context", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static SslContext newClientContextInternal(SslProvider provider, Provider sslContextProvider, X509Certificate[] trustCert, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp, SecureRandom secureRandom, String keyStoreType, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/*  832 */     if (provider == null) {
/*  833 */       provider = defaultClientProvider();
/*      */     }
/*      */     
/*  836 */     ResumptionController resumptionController = new ResumptionController();
/*      */     
/*  838 */     switch (provider) {
/*      */       case JDK:
/*  840 */         if (enableOcsp) {
/*  841 */           throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + provider);
/*      */         }
/*  843 */         return new JdkSslClientContext(sslContextProvider, trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, secureRandom, keyStoreType, endpointIdentificationAlgorithm, serverNames, resumptionController);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case OPENSSL:
/*  849 */         verifyNullSslContextProvider(provider, sslContextProvider);
/*  850 */         OpenSsl.ensureAvailability();
/*  851 */         return new OpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp, keyStoreType, endpointIdentificationAlgorithm, serverNames, resumptionController, options);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case OPENSSL_REFCNT:
/*  857 */         verifyNullSslContextProvider(provider, sslContextProvider);
/*  858 */         OpenSsl.ensureAvailability();
/*  859 */         return new ReferenceCountedOpenSslClientContext(trustCert, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, apn, protocols, sessionCacheSize, sessionTimeout, enableOcsp, keyStoreType, endpointIdentificationAlgorithm, serverNames, resumptionController, options);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     throw new Error("Unexpected provider: " + provider);
/*      */   }
/*      */ 
/*      */   
/*      */   static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable<String> nextProtocols) {
/*      */     ApplicationProtocolConfig apn;
/*  871 */     if (nextProtocols == null) {
/*  872 */       apn = ApplicationProtocolConfig.DISABLED;
/*      */     } else {
/*  874 */       apn = new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, nextProtocols);
/*      */     } 
/*      */ 
/*      */     
/*  878 */     return apn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslContext() {
/*  885 */     this(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslContext(boolean startTls) {
/*  892 */     this(startTls, null);
/*      */   }
/*      */   
/*      */   SslContext(boolean startTls, ResumptionController resumptionController) {
/*  896 */     this.startTls = startTls;
/*  897 */     this.resumptionController = resumptionController;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final AttributeMap attributes() {
/*  904 */     return this.attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isServer() {
/*  911 */     return !isClient();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long sessionCacheSize() {
/*  928 */     return sessionContext().getSessionCacheSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long sessionTimeout() {
/*  935 */     return sessionContext().getSessionTimeout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final List<String> nextProtocols() {
/*  943 */     return applicationProtocolNegotiator().protocols();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SslHandler newHandler(ByteBufAllocator alloc) {
/*  981 */     return newHandler(alloc, this.startTls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, boolean startTls) {
/*  989 */     return new SslHandler(newEngine(alloc), startTls, (Executor)ImmediateExecutor.INSTANCE, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler newHandler(ByteBufAllocator alloc, Executor delegatedTaskExecutor) {
/* 1018 */     return newHandler(alloc, this.startTls, delegatedTaskExecutor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, boolean startTls, Executor executor) {
/* 1026 */     return new SslHandler(newEngine(alloc), startTls, executor, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort) {
/* 1035 */     return newHandler(alloc, peerHost, peerPort, this.startTls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls) {
/* 1043 */     return new SslHandler(newEngine(alloc, peerHost, peerPort), startTls, (Executor)ImmediateExecutor.INSTANCE, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, Executor delegatedTaskExecutor) {
/* 1077 */     return newHandler(alloc, peerHost, peerPort, this.startTls, delegatedTaskExecutor);
/*      */   }
/*      */ 
/*      */   
/*      */   protected SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls, Executor delegatedTaskExecutor) {
/* 1082 */     return new SslHandler(newEngine(alloc, peerHost, peerPort), startTls, delegatedTaskExecutor, this.resumptionController);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
/* 1107 */     if (password == null) {
/* 1108 */       return new PKCS8EncodedKeySpec(key);
/*      */     }
/*      */     
/* 1111 */     EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
/* 1112 */     String pbeAlgorithm = getPBEAlgorithm(encryptedPrivateKeyInfo);
/* 1113 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(pbeAlgorithm);
/* 1114 */     PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
/* 1115 */     SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
/*      */     
/* 1117 */     Cipher cipher = Cipher.getInstance(pbeAlgorithm);
/* 1118 */     cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
/*      */     
/* 1120 */     return encryptedPrivateKeyInfo.getKeySpec(cipher);
/*      */   }
/*      */   
/*      */   private static String getPBEAlgorithm(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo) {
/* 1124 */     AlgorithmParameters parameters = encryptedPrivateKeyInfo.getAlgParameters();
/* 1125 */     String algName = encryptedPrivateKeyInfo.getAlgName();
/*      */ 
/*      */     
/* 1128 */     if (parameters != null && ("1.2.840.113549.1.5.13".equals(algName) || "PBES2".equals(algName)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1139 */       return parameters.toString();
/*      */     }
/* 1141 */     return encryptedPrivateKeyInfo.getAlgName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static KeyStore buildKeyStore(X509Certificate[] certChain, PrivateKey key, char[] keyPasswordChars, String keyStoreType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
/* 1158 */     if (keyStoreType == null) {
/* 1159 */       keyStoreType = KeyStore.getDefaultType();
/*      */     }
/* 1161 */     KeyStore ks = KeyStore.getInstance(keyStoreType);
/* 1162 */     ks.load(null, null);
/* 1163 */     ks.setKeyEntry("key", key, keyPasswordChars, (Certificate[])certChain);
/* 1164 */     return ks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static PrivateKey toPrivateKey(File keyFile, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
/* 1171 */     return toPrivateKey(keyFile, keyPassword, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static PrivateKey toPrivateKey(File keyFile, String keyPassword, boolean tryBouncyCastle) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
/* 1178 */     if (keyFile == null) {
/* 1179 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1183 */     if (tryBouncyCastle && BouncyCastleUtil.isBcPkixAvailable()) {
/* 1184 */       PrivateKey pk = BouncyCastlePemReader.getPrivateKey(keyFile, keyPassword);
/* 1185 */       if (pk != null) {
/* 1186 */         return pk;
/*      */       }
/*      */     } 
/*      */     
/* 1190 */     return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyFile), keyPassword);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static PrivateKey toPrivateKey(InputStream keyInputStream, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
/* 1198 */     if (keyInputStream == null) {
/* 1199 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1203 */     if (BouncyCastleUtil.isBcPkixAvailable()) {
/* 1204 */       if (!keyInputStream.markSupported())
/*      */       {
/* 1206 */         keyInputStream = new BufferedInputStream(keyInputStream);
/*      */       }
/* 1208 */       keyInputStream.mark(1048576);
/* 1209 */       PrivateKey pk = BouncyCastlePemReader.getPrivateKey(keyInputStream, keyPassword);
/* 1210 */       if (pk != null) {
/* 1211 */         return pk;
/*      */       }
/*      */       
/* 1214 */       keyInputStream.reset();
/*      */     } 
/*      */     
/* 1217 */     return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(keyInputStream), keyPassword);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf encodedKeyBuf, String keyPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
/* 1224 */     byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
/* 1225 */     encodedKeyBuf.readBytes(encodedKey).release();
/*      */     
/* 1227 */     PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(
/* 1228 */         (keyPassword == null) ? null : keyPassword.toCharArray(), encodedKey);
/*      */     try {
/* 1230 */       return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec);
/* 1231 */     } catch (InvalidKeySpecException ignore) {
/*      */       try {
/* 1233 */         return KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
/* 1234 */       } catch (InvalidKeySpecException ignore2) {
/*      */         try {
/* 1236 */           return KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
/* 1237 */         } catch (InvalidKeySpecException e) {
/* 1238 */           throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected static TrustManagerFactory buildTrustManagerFactory(File certChainFile, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
/* 1254 */     return buildTrustManagerFactory(certChainFile, trustManagerFactory, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static TrustManagerFactory buildTrustManagerFactory(File certChainFile, TrustManagerFactory trustManagerFactory, String keyType) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
/* 1267 */     X509Certificate[] x509Certs = toX509Certificates(certChainFile);
/*      */     
/* 1269 */     return buildTrustManagerFactory(x509Certs, trustManagerFactory, keyType);
/*      */   }
/*      */   
/*      */   protected static X509Certificate[] toX509Certificates(File file) throws CertificateException {
/* 1273 */     if (file == null) {
/* 1274 */       return null;
/*      */     }
/* 1276 */     return getCertificatesFromBuffers(PemReader.readCertificates(file));
/*      */   }
/*      */   
/*      */   protected static X509Certificate[] toX509Certificates(InputStream in) throws CertificateException {
/* 1280 */     if (in == null) {
/* 1281 */       return null;
/*      */     }
/* 1283 */     return getCertificatesFromBuffers(PemReader.readCertificates(in));
/*      */   }
/*      */   
/*      */   private static X509Certificate[] getCertificatesFromBuffers(ByteBuf[] certs) throws CertificateException {
/* 1287 */     CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 1288 */     X509Certificate[] x509Certs = new X509Certificate[certs.length];
/*      */     
/*      */     try {
/* 1291 */       for (int i = 0; i < certs.length; i++) { 
/* 1292 */         try { ByteBufInputStream byteBufInputStream = new ByteBufInputStream(certs[i], false); 
/* 1293 */           try { x509Certs[i] = (X509Certificate)cf.generateCertificate((InputStream)byteBufInputStream);
/* 1294 */             byteBufInputStream.close(); } catch (Throwable throwable) { try { byteBufInputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*      */         
/* 1296 */         { throw new RuntimeException(e); }
/*      */          }
/*      */     
/*      */     } finally {
/* 1300 */       for (ByteBuf buf : certs) {
/* 1301 */         buf.release();
/*      */       }
/*      */     } 
/* 1304 */     return x509Certs;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] certCollection, TrustManagerFactory trustManagerFactory, String keyStoreType) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
/* 1310 */     if (keyStoreType == null) {
/* 1311 */       keyStoreType = KeyStore.getDefaultType();
/*      */     }
/* 1313 */     KeyStore ks = KeyStore.getInstance(keyStoreType);
/* 1314 */     ks.load(null, null);
/*      */     
/* 1316 */     int i = 1;
/* 1317 */     for (X509Certificate cert : certCollection) {
/* 1318 */       String alias = Integer.toString(i);
/* 1319 */       ks.setCertificateEntry(alias, cert);
/* 1320 */       i++;
/*      */     } 
/*      */ 
/*      */     
/* 1324 */     if (trustManagerFactory == null) {
/* 1325 */       trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*      */     }
/* 1327 */     trustManagerFactory.init(ks);
/*      */     
/* 1329 */     return trustManagerFactory;
/*      */   }
/*      */   
/*      */   static PrivateKey toPrivateKeyInternal(File keyFile, String keyPassword) throws SSLException {
/*      */     try {
/* 1334 */       return toPrivateKey(keyFile, keyPassword);
/* 1335 */     } catch (Exception e) {
/* 1336 */       throw new SSLException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   static X509Certificate[] toX509CertificatesInternal(File file) throws SSLException {
/*      */     try {
/* 1342 */       return toX509Certificates(file);
/* 1343 */     } catch (CertificateException e) {
/* 1344 */       throw new SSLException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] certChainFile, String keyAlgorithm, PrivateKey key, String keyPassword, KeyManagerFactory kmf, String keyStore) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
/* 1354 */     if (keyAlgorithm == null) {
/* 1355 */       keyAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
/*      */     }
/* 1357 */     char[] keyPasswordChars = keyStorePassword(keyPassword);
/* 1358 */     KeyStore ks = buildKeyStore(certChainFile, key, keyPasswordChars, keyStore);
/* 1359 */     return buildKeyManagerFactory(ks, keyAlgorithm, keyPasswordChars, kmf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static KeyManagerFactory buildKeyManagerFactory(KeyStore ks, String keyAlgorithm, char[] keyPasswordChars, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 1367 */     if (kmf == null) {
/* 1368 */       if (keyAlgorithm == null) {
/* 1369 */         keyAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
/*      */       }
/* 1371 */       kmf = KeyManagerFactory.getInstance(keyAlgorithm);
/*      */     } 
/* 1373 */     kmf.init(ks, keyPasswordChars);
/*      */     
/* 1375 */     return kmf;
/*      */   }
/*      */   
/*      */   static char[] keyStorePassword(String keyPassword) {
/* 1379 */     return (keyPassword == null) ? EmptyArrays.EMPTY_CHARS : keyPassword.toCharArray();
/*      */   }
/*      */   
/*      */   public abstract boolean isClient();
/*      */   
/*      */   public abstract List<String> cipherSuites();
/*      */   
/*      */   public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();
/*      */   
/*      */   public abstract SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator);
/*      */   
/*      */   public abstract SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt);
/*      */   
/*      */   public abstract SSLSessionContext sessionContext();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */