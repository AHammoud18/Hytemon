/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.KeyException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkSslContext
/*     */   extends SslContext
/*     */ {
/*     */   static final String PROTOCOL = "TLS";
/*     */   private static final String[] DEFAULT_PROTOCOLS;
/*     */   private static final List<String> DEFAULT_CIPHERS;
/*     */   private static final List<String> DEFAULT_CIPHERS_NON_TLSV13;
/*     */   private static final Set<String> SUPPORTED_CIPHERS;
/*     */   private static final Set<String> SUPPORTED_CIPHERS_NON_TLSV13;
/*     */   private static final Provider DEFAULT_PROVIDER;
/*     */   private final String[] protocols;
/*  62 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
/*     */   private final String[] cipherSuites;
/*     */   private final List<String> unmodifiableCipherSuites;
/*     */   private final JdkApplicationProtocolNegotiator apn;
/*     */   private final ClientAuth clientAuth;
/*     */   private final SSLContext sslContext;
/*     */   private final boolean isClient;
/*     */   private final String endpointIdentificationAlgorithm;
/*     */   private final List<SNIServerName> serverNames;
/*     */   
/*     */   static {
/*  73 */     Defaults defaults = new Defaults();
/*  74 */     defaults.init();
/*     */     
/*  76 */     DEFAULT_PROVIDER = defaults.defaultProvider;
/*  77 */     DEFAULT_PROTOCOLS = defaults.defaultProtocols;
/*  78 */     SUPPORTED_CIPHERS = defaults.supportedCiphers;
/*  79 */     DEFAULT_CIPHERS = defaults.defaultCiphers;
/*  80 */     DEFAULT_CIPHERS_NON_TLSV13 = defaults.defaultCiphersNonTLSv13;
/*  81 */     SUPPORTED_CIPHERS_NON_TLSV13 = defaults.supportedCiphersNonTLSv13;
/*     */     
/*  83 */     if (logger.isDebugEnabled()) {
/*  84 */       logger.debug("Default protocols (JDK): {} ", Arrays.asList(DEFAULT_PROTOCOLS));
/*  85 */       logger.debug("Default cipher suites (JDK): {}", DEFAULT_CIPHERS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Defaults
/*     */   {
/*     */     String[] defaultProtocols;
/*     */     
/*     */     List<String> defaultCiphers;
/*     */     List<String> defaultCiphersNonTLSv13;
/*     */     
/*     */     void init() {
/*     */       SSLContext context;
/*     */       try {
/* 100 */         context = SSLContext.getInstance("TLS");
/* 101 */         context.init(null, null, null);
/* 102 */       } catch (Exception e) {
/* 103 */         throw new Error("failed to initialize the default SSL context", e);
/*     */       } 
/*     */       
/* 106 */       this.defaultProvider = context.getProvider();
/*     */       
/* 108 */       SSLEngine engine = context.createSSLEngine();
/* 109 */       this.defaultProtocols = JdkSslContext.defaultProtocols(context, engine);
/*     */       
/* 111 */       this.supportedCiphers = Collections.unmodifiableSet(JdkSslContext.supportedCiphers(engine));
/* 112 */       this.defaultCiphers = Collections.unmodifiableList(JdkSslContext.defaultCiphers(engine, this.supportedCiphers));
/*     */       
/* 114 */       List<String> ciphersNonTLSv13 = new ArrayList<>(this.defaultCiphers);
/* 115 */       ciphersNonTLSv13.removeAll(Arrays.asList((Object[])SslUtils.DEFAULT_TLSV13_CIPHER_SUITES));
/* 116 */       this.defaultCiphersNonTLSv13 = Collections.unmodifiableList(ciphersNonTLSv13);
/*     */       
/* 118 */       Set<String> suppertedCiphersNonTLSv13 = new LinkedHashSet<>(this.supportedCiphers);
/* 119 */       suppertedCiphersNonTLSv13.removeAll(Arrays.asList((Object[])SslUtils.DEFAULT_TLSV13_CIPHER_SUITES));
/* 120 */       this.supportedCiphersNonTLSv13 = Collections.unmodifiableSet(suppertedCiphersNonTLSv13);
/*     */     }
/*     */     Set<String> supportedCiphers; Set<String> supportedCiphersNonTLSv13; Provider defaultProvider;
/*     */     private Defaults() {} }
/*     */   
/*     */   private static String[] defaultProtocols(SSLContext context, SSLEngine engine) {
/* 126 */     String[] supportedProtocols = context.getDefaultSSLParameters().getProtocols();
/* 127 */     Set<String> supportedProtocolsSet = new HashSet<>(supportedProtocols.length);
/* 128 */     Collections.addAll(supportedProtocolsSet, supportedProtocols);
/* 129 */     List<String> protocols = new ArrayList<>();
/* 130 */     SslUtils.addIfSupported(supportedProtocolsSet, protocols, new String[] { "TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (!protocols.isEmpty()) {
/* 136 */       return protocols.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */     }
/* 138 */     return engine.getEnabledProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<String> supportedCiphers(SSLEngine engine) {
/* 143 */     String[] supportedCiphers = engine.getSupportedCipherSuites();
/* 144 */     Set<String> supportedCiphersSet = new LinkedHashSet<>(supportedCiphers.length);
/* 145 */     for (int i = 0; i < supportedCiphers.length; i++) {
/* 146 */       String supportedCipher = supportedCiphers[i];
/* 147 */       supportedCiphersSet.add(supportedCipher);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       if (supportedCipher.startsWith("SSL_")) {
/* 158 */         String tlsPrefixedCipherName = "TLS_" + supportedCipher.substring("SSL_".length());
/*     */         try {
/* 160 */           engine.setEnabledCipherSuites(new String[] { tlsPrefixedCipherName });
/* 161 */           supportedCiphersSet.add(tlsPrefixedCipherName);
/* 162 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 167 */     return supportedCiphersSet;
/*     */   }
/*     */   
/*     */   private static List<String> defaultCiphers(SSLEngine engine, Set<String> supportedCiphers) {
/* 171 */     List<String> ciphers = new ArrayList<>();
/* 172 */     SslUtils.addIfSupported(supportedCiphers, ciphers, SslUtils.DEFAULT_CIPHER_SUITES);
/* 173 */     SslUtils.useFallbackCiphersIfDefaultIsEmpty(ciphers, engine.getEnabledCipherSuites());
/* 174 */     return ciphers;
/*     */   }
/*     */   
/*     */   private static boolean isTlsV13Supported(String[] protocols) {
/* 178 */     for (String protocol : protocols) {
/* 179 */       if ("TLSv1.3".equals(protocol)) {
/* 180 */         return true;
/*     */       }
/*     */     } 
/* 183 */     return false;
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
/*     */   public JdkSslContext(SSLContext sslContext, boolean isClient, ClientAuth clientAuth) {
/* 209 */     this(sslContext, isClient, (Iterable<String>)null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth, (String[])null, false);
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
/*     */   @Deprecated
/*     */   public JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, ClientAuth clientAuth) {
/* 229 */     this(sslContext, isClient, ciphers, cipherFilter, apn, clientAuth, (String[])null, false);
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
/*     */   public JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, ClientAuth clientAuth, String[] protocols, boolean startTls) {
/* 252 */     this(sslContext, isClient, ciphers, cipherFilter, 
/*     */ 
/*     */ 
/*     */         
/* 256 */         toNegotiator(apn, !isClient), clientAuth, 
/*     */         
/* 258 */         (protocols == null) ? null : (String[])protocols.clone(), startTls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, ClientAuth clientAuth, String[] protocols, boolean startTls) {
/* 265 */     this(sslContext, isClient, ciphers, cipherFilter, apn, clientAuth, protocols, startTls, (String)null, (List<SNIServerName>)null, (ResumptionController)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JdkSslContext(SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, JdkApplicationProtocolNegotiator apn, ClientAuth clientAuth, String[] protocols, boolean startTls, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames, ResumptionController resumptionController) {
/* 273 */     super(startTls, resumptionController); List<String> defaultCiphers; Set<String> supportedCiphers;
/* 274 */     this.apn = (JdkApplicationProtocolNegotiator)ObjectUtil.checkNotNull(apn, "apn");
/* 275 */     this.clientAuth = (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth");
/* 276 */     this.sslContext = (SSLContext)ObjectUtil.checkNotNull(sslContext, "sslContext");
/* 277 */     this.endpointIdentificationAlgorithm = endpointIdentificationAlgorithm;
/* 278 */     this.serverNames = serverNames;
/*     */ 
/*     */ 
/*     */     
/* 282 */     if (DEFAULT_PROVIDER.equals(sslContext.getProvider())) {
/* 283 */       this.protocols = (protocols == null) ? DEFAULT_PROTOCOLS : protocols;
/* 284 */       if (isTlsV13Supported(this.protocols)) {
/* 285 */         supportedCiphers = SUPPORTED_CIPHERS;
/* 286 */         defaultCiphers = DEFAULT_CIPHERS;
/*     */       } else {
/*     */         
/* 289 */         supportedCiphers = SUPPORTED_CIPHERS_NON_TLSV13;
/* 290 */         defaultCiphers = DEFAULT_CIPHERS_NON_TLSV13;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 296 */       SSLEngine engine = sslContext.createSSLEngine();
/*     */       try {
/* 298 */         if (protocols == null) {
/* 299 */           this.protocols = defaultProtocols(sslContext, engine);
/*     */         } else {
/* 301 */           this.protocols = protocols;
/*     */         } 
/* 303 */         supportedCiphers = supportedCiphers(engine);
/* 304 */         defaultCiphers = defaultCiphers(engine, supportedCiphers);
/* 305 */         if (!isTlsV13Supported(this.protocols))
/*     */         {
/* 307 */           for (String cipher : SslUtils.DEFAULT_TLSV13_CIPHER_SUITES) {
/* 308 */             supportedCiphers.remove(cipher);
/* 309 */             defaultCiphers.remove(cipher);
/*     */           } 
/*     */         }
/*     */       } finally {
/* 313 */         ReferenceCountUtil.release(engine);
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     this.cipherSuites = ((CipherSuiteFilter)ObjectUtil.checkNotNull(cipherFilter, "cipherFilter")).filterCipherSuites(ciphers, defaultCiphers, supportedCiphers);
/*     */ 
/*     */     
/* 320 */     this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
/* 321 */     this.isClient = isClient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SSLContext context() {
/* 328 */     return this.sslContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isClient() {
/* 333 */     return this.isClient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SSLSessionContext sessionContext() {
/* 341 */     if (isServer()) {
/* 342 */       return context().getServerSessionContext();
/*     */     }
/* 344 */     return context().getClientSessionContext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<String> cipherSuites() {
/* 350 */     return this.unmodifiableCipherSuites;
/*     */   }
/*     */ 
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator alloc) {
/* 355 */     return configureAndWrapEngine(context().createSSLEngine(), alloc);
/*     */   }
/*     */ 
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
/* 360 */     return configureAndWrapEngine(context().createSSLEngine(peerHost, peerPort), alloc);
/*     */   }
/*     */ 
/*     */   
/*     */   private SSLEngine configureAndWrapEngine(SSLEngine engine, ByteBufAllocator alloc) {
/* 365 */     engine.setEnabledCipherSuites(this.cipherSuites);
/* 366 */     engine.setEnabledProtocols(this.protocols);
/* 367 */     engine.setUseClientMode(isClient());
/* 368 */     if (isServer()) {
/* 369 */       switch (this.clientAuth) {
/*     */         case NONE:
/* 371 */           engine.setWantClientAuth(true);
/*     */           break;
/*     */         case ALPN:
/* 374 */           engine.setNeedClientAuth(true);
/*     */           break;
/*     */         case null:
/*     */           break;
/*     */         default:
/* 379 */           throw new Error("Unexpected auth " + this.clientAuth);
/*     */       } 
/*     */     }
/* 382 */     configureSSLParameters(engine);
/* 383 */     JdkApplicationProtocolNegotiator.SslEngineWrapperFactory factory = this.apn.wrapperFactory();
/* 384 */     if (factory instanceof JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory) {
/* 385 */       return ((JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory)factory)
/* 386 */         .wrapSslEngine(engine, alloc, this.apn, isServer());
/*     */     }
/* 388 */     return factory.wrapSslEngine(engine, this.apn, isServer());
/*     */   }
/*     */   
/*     */   private void configureSSLParameters(SSLEngine engine) {
/* 392 */     SSLParameters params = engine.getSSLParameters();
/* 393 */     params.setEndpointIdentificationAlgorithm(this.endpointIdentificationAlgorithm);
/* 394 */     if (this.serverNames != null && !this.serverNames.isEmpty()) {
/* 395 */       params.setServerNames(this.serverNames);
/*     */     }
/* 397 */     engine.setSSLParameters(params);
/*     */   }
/*     */ 
/*     */   
/*     */   public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
/* 402 */     return this.apn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config, boolean isServer) {
/* 413 */     if (config == null) {
/* 414 */       return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
/*     */     }
/*     */     
/* 417 */     switch (config.protocol()) {
/*     */       case NONE:
/* 419 */         return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
/*     */       case ALPN:
/* 421 */         if (isServer) {
/* 422 */           switch (config.selectorFailureBehavior()) {
/*     */             case NONE:
/* 424 */               return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
/*     */             case ALPN:
/* 426 */               return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
/*     */           } 
/* 428 */           throw new UnsupportedOperationException("JDK provider does not support " + config
/* 429 */               .selectorFailureBehavior() + " failure behavior");
/*     */         } 
/*     */         
/* 432 */         switch (config.selectedListenerFailureBehavior()) {
/*     */           case NONE:
/* 434 */             return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
/*     */           case ALPN:
/* 436 */             return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
/*     */         } 
/* 438 */         throw new UnsupportedOperationException("JDK provider does not support " + config
/* 439 */             .selectedListenerFailureBehavior() + " failure behavior");
/*     */     } 
/*     */ 
/*     */     
/* 443 */     throw new UnsupportedOperationException("JDK provider does not support " + config
/* 444 */         .protocol() + " protocol");
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
/*     */   static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf, String keyStore) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
/* 463 */     String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
/* 464 */     if (algorithm == null) {
/* 465 */       algorithm = "SunX509";
/*     */     }
/* 467 */     return buildKeyManagerFactory(certChainFile, algorithm, keyFile, keyPassword, kmf, keyStore);
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
/*     */   @Deprecated
/*     */   protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
/* 486 */     return buildKeyManagerFactory(certChainFile, keyFile, keyPassword, kmf, KeyStore.getDefaultType());
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
/*     */   static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf, String keyStore) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
/* 509 */     return buildKeyManagerFactory(toX509Certificates(certChainFile), keyAlgorithm, 
/* 510 */         toPrivateKey(keyFile, keyPassword), keyPassword, kmf, keyStore);
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
/*     */   protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
/* 534 */     return buildKeyManagerFactory(toX509Certificates(certChainFile), keyAlgorithm, 
/* 535 */         toPrivateKey(keyFile, keyPassword), keyPassword, kmf, KeyStore.getDefaultType());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkSslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */