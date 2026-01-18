/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.handler.ssl.util.KeyManagerFactoryWrapper;
/*     */ import io.netty.handler.ssl.util.TrustManagerFactoryWrapper;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLException;
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
/*     */ 
/*     */ public final class SslContextBuilder
/*     */ {
/*  55 */   private static final Map.Entry[] EMPTY_ENTRIES = new Map.Entry[0];
/*     */   
/*     */   private final boolean forServer;
/*     */   private SslProvider provider;
/*     */   
/*     */   public static SslContextBuilder forClient() {
/*  61 */     return new SslContextBuilder(false);
/*     */   }
/*     */   private Provider sslContextProvider; private X509Certificate[] trustCertCollection;
/*     */   private TrustManagerFactory trustManagerFactory;
/*     */   private X509Certificate[] keyCertChain;
/*     */   private PrivateKey key;
/*     */   private String keyPassword;
/*     */   private KeyManagerFactory keyManagerFactory;
/*     */   private Iterable<String> ciphers;
/*     */   
/*     */   public static SslContextBuilder forServer(File keyCertChainFile, File keyFile) {
/*  72 */     return (new SslContextBuilder(true)).keyManager(keyCertChainFile, keyFile);
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
/*     */   public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream) {
/*  88 */     return (new SslContextBuilder(true)).keyManager(keyCertChainInputStream, keyInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SslContextBuilder forServer(PrivateKey key, X509Certificate... keyCertChain) {
/*  99 */     return (new SslContextBuilder(true)).keyManager(key, keyCertChain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SslContextBuilder forServer(PrivateKey key, Iterable<? extends X509Certificate> keyCertChain) {
/* 110 */     return forServer(key, toArray(keyCertChain, EmptyArrays.EMPTY_X509_CERTIFICATES));
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
/*     */   public static SslContextBuilder forServer(File keyCertChainFile, File keyFile, String keyPassword) {
/* 124 */     return (new SslContextBuilder(true)).keyManager(keyCertChainFile, keyFile, keyPassword);
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
/*     */   public static SslContextBuilder forServer(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword) {
/* 142 */     return (new SslContextBuilder(true)).keyManager(keyCertChainInputStream, keyInputStream, keyPassword);
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
/*     */   public static SslContextBuilder forServer(PrivateKey key, String keyPassword, X509Certificate... keyCertChain) {
/* 156 */     return (new SslContextBuilder(true)).keyManager(key, keyPassword, keyCertChain);
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
/*     */   public static SslContextBuilder forServer(PrivateKey key, String keyPassword, Iterable<? extends X509Certificate> keyCertChain) {
/* 170 */     return forServer(key, keyPassword, toArray(keyCertChain, EmptyArrays.EMPTY_X509_CERTIFICATES));
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
/*     */   public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory) {
/* 183 */     return (new SslContextBuilder(true)).keyManager(keyManagerFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SslContextBuilder forServer(KeyManager keyManager) {
/* 192 */     return (new SslContextBuilder(true)).keyManager(keyManager);
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
/* 205 */   private CipherSuiteFilter cipherFilter = IdentityCipherSuiteFilter.INSTANCE;
/*     */   private ApplicationProtocolConfig apn;
/*     */   private long sessionCacheSize;
/*     */   private long sessionTimeout;
/* 209 */   private ClientAuth clientAuth = ClientAuth.NONE;
/*     */   private String[] protocols;
/*     */   private boolean startTls;
/*     */   private boolean enableOcsp;
/*     */   private SecureRandom secureRandom;
/* 214 */   private String keyStoreType = KeyStore.getDefaultType();
/*     */   private String endpointIdentificationAlgorithm;
/* 216 */   private final Map<SslContextOption<?>, Object> options = new HashMap<>();
/*     */   private final List<SNIServerName> serverNames;
/*     */   
/*     */   private SslContextBuilder(boolean forServer) {
/* 220 */     this.forServer = forServer;
/* 221 */     if (!forServer) {
/* 222 */       this.endpointIdentificationAlgorithm = SslUtils.defaultEndpointVerificationAlgorithm;
/*     */     }
/* 224 */     this.serverNames = forServer ? null : new ArrayList<>(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> SslContextBuilder option(SslContextOption<T> option, T value) {
/* 231 */     if (value == null) {
/* 232 */       this.options.remove(option);
/*     */     } else {
/* 234 */       this.options.put(option, value);
/*     */     } 
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder sslProvider(SslProvider provider) {
/* 243 */     this.provider = provider;
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder keyStoreType(String keyStoreType) {
/* 251 */     this.keyStoreType = keyStoreType;
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder sslContextProvider(Provider sslContextProvider) {
/* 260 */     this.sslContextProvider = sslContextProvider;
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(File trustCertCollectionFile) {
/*     */     try {
/* 270 */       return trustManager(SslContext.toX509Certificates(trustCertCollectionFile));
/* 271 */     } catch (Exception e) {
/* 272 */       throw new IllegalArgumentException("File does not contain valid certificates: " + trustCertCollectionFile, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(InputStream trustCertCollectionInputStream) {
/*     */     try {
/* 285 */       return trustManager(SslContext.toX509Certificates(trustCertCollectionInputStream));
/* 286 */     } catch (Exception e) {
/* 287 */       throw new IllegalArgumentException("Input stream does not contain valid certificates.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(X509Certificate... trustCertCollection) {
/* 295 */     this.trustCertCollection = (trustCertCollection != null) ? (X509Certificate[])trustCertCollection.clone() : null;
/* 296 */     this.trustManagerFactory = null;
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(Iterable<? extends X509Certificate> trustCertCollection) {
/* 304 */     return trustManager(toArray(trustCertCollection, EmptyArrays.EMPTY_X509_CERTIFICATES));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory) {
/* 311 */     this.trustCertCollection = null;
/* 312 */     this.trustManagerFactory = trustManagerFactory;
/* 313 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder trustManager(TrustManager trustManager) {
/* 324 */     if (trustManager != null) {
/* 325 */       this.trustManagerFactory = (TrustManagerFactory)new TrustManagerFactoryWrapper(trustManager);
/*     */     } else {
/* 327 */       this.trustManagerFactory = null;
/*     */     } 
/* 329 */     this.trustCertCollection = null;
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder keyManager(File keyCertChainFile, File keyFile) {
/* 341 */     return keyManager(keyCertChainFile, keyFile, (String)null);
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
/*     */   public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream) {
/* 356 */     return keyManager(keyCertChainInputStream, keyInputStream, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder keyManager(PrivateKey key, X509Certificate... keyCertChain) {
/* 367 */     return keyManager(key, (String)null, keyCertChain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder keyManager(PrivateKey key, Iterable<? extends X509Certificate> keyCertChain) {
/* 378 */     return keyManager(key, toArray(keyCertChain, EmptyArrays.EMPTY_X509_CERTIFICATES));
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
/*     */   public SslContextBuilder keyManager(File keyCertChainFile, File keyFile, String keyPassword) {
/*     */     X509Certificate[] keyCertChain;
/*     */     PrivateKey key;
/*     */     try {
/* 394 */       keyCertChain = SslContext.toX509Certificates(keyCertChainFile);
/* 395 */     } catch (Exception e) {
/* 396 */       throw new IllegalArgumentException("File does not contain valid certificates: " + keyCertChainFile, e);
/*     */     } 
/*     */     try {
/* 399 */       key = SslContext.toPrivateKey(keyFile, keyPassword);
/* 400 */     } catch (Exception e) {
/* 401 */       throw new IllegalArgumentException("File does not contain valid private key: " + keyFile, e);
/*     */     } 
/* 403 */     return keyManager(key, keyPassword, keyCertChain);
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
/*     */   public SslContextBuilder keyManager(InputStream keyCertChainInputStream, InputStream keyInputStream, String keyPassword) {
/*     */     X509Certificate[] keyCertChain;
/*     */     PrivateKey key;
/*     */     try {
/* 424 */       keyCertChain = SslContext.toX509Certificates(keyCertChainInputStream);
/* 425 */     } catch (Exception e) {
/* 426 */       throw new IllegalArgumentException("Input stream not contain valid certificates.", e);
/*     */     } 
/*     */     try {
/* 429 */       key = SslContext.toPrivateKey(keyInputStream, keyPassword);
/* 430 */     } catch (Exception e) {
/* 431 */       throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
/*     */     } 
/* 433 */     return keyManager(key, keyPassword, keyCertChain);
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
/*     */   public SslContextBuilder keyManager(PrivateKey key, String keyPassword, X509Certificate... keyCertChain) {
/* 446 */     if (this.forServer) {
/* 447 */       ObjectUtil.checkNonEmpty((Object[])keyCertChain, "keyCertChain");
/* 448 */       ObjectUtil.checkNotNull(key, "key required for servers");
/*     */     } 
/* 450 */     if (keyCertChain == null || keyCertChain.length == 0) {
/* 451 */       this.keyCertChain = null;
/*     */     } else {
/* 453 */       for (X509Certificate cert : keyCertChain) {
/* 454 */         ObjectUtil.checkNotNullWithIAE(cert, "cert");
/*     */       }
/* 456 */       this.keyCertChain = (X509Certificate[])keyCertChain.clone();
/*     */     } 
/* 458 */     this.key = key;
/* 459 */     this.keyPassword = keyPassword;
/* 460 */     this.keyManagerFactory = null;
/* 461 */     return this;
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
/*     */   public SslContextBuilder keyManager(PrivateKey key, String keyPassword, Iterable<? extends X509Certificate> keyCertChain) {
/* 475 */     return keyManager(key, keyPassword, toArray(keyCertChain, EmptyArrays.EMPTY_X509_CERTIFICATES));
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
/*     */   public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory) {
/* 490 */     if (this.forServer) {
/* 491 */       ObjectUtil.checkNotNull(keyManagerFactory, "keyManagerFactory required for servers");
/*     */     }
/* 493 */     this.keyCertChain = null;
/* 494 */     this.key = null;
/* 495 */     this.keyPassword = null;
/* 496 */     this.keyManagerFactory = keyManagerFactory;
/* 497 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder keyManager(KeyManager keyManager) {
/* 508 */     if (this.forServer) {
/* 509 */       ObjectUtil.checkNotNull(keyManager, "keyManager required for servers");
/*     */     }
/* 511 */     if (keyManager != null) {
/* 512 */       this.keyManagerFactory = (KeyManagerFactory)new KeyManagerFactoryWrapper(keyManager);
/*     */     } else {
/* 514 */       this.keyManagerFactory = null;
/*     */     } 
/* 516 */     this.keyCertChain = null;
/* 517 */     this.key = null;
/* 518 */     this.keyPassword = null;
/* 519 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder ciphers(Iterable<String> ciphers) {
/* 527 */     return ciphers(ciphers, IdentityCipherSuiteFilter.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder ciphers(Iterable<String> ciphers, CipherSuiteFilter cipherFilter) {
/* 536 */     this.cipherFilter = (CipherSuiteFilter)ObjectUtil.checkNotNull(cipherFilter, "cipherFilter");
/* 537 */     this.ciphers = ciphers;
/* 538 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig apn) {
/* 545 */     this.apn = apn;
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder sessionCacheSize(long sessionCacheSize) {
/* 554 */     this.sessionCacheSize = sessionCacheSize;
/* 555 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder sessionTimeout(long sessionTimeout) {
/* 563 */     this.sessionTimeout = sessionTimeout;
/* 564 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder clientAuth(ClientAuth clientAuth) {
/* 571 */     this.clientAuth = (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth");
/* 572 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder protocols(String... protocols) {
/* 581 */     this.protocols = (protocols == null) ? null : (String[])protocols.clone();
/* 582 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder protocols(Iterable<String> protocols) {
/* 591 */     return protocols(toArray(protocols, EmptyArrays.EMPTY_STRINGS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder startTls(boolean startTls) {
/* 598 */     this.startTls = startTls;
/* 599 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContextBuilder enableOcsp(boolean enableOcsp) {
/* 610 */     this.enableOcsp = enableOcsp;
/* 611 */     return this;
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
/*     */   public SslContextBuilder secureRandom(SecureRandom secureRandom) {
/* 625 */     this.secureRandom = secureRandom;
/* 626 */     return this;
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
/*     */   public SslContextBuilder endpointIdentificationAlgorithm(String algorithm) {
/* 640 */     this.endpointIdentificationAlgorithm = algorithm;
/* 641 */     return this;
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
/*     */   public SslContextBuilder serverName(SNIServerName serverName) {
/* 654 */     if (this.forServer) {
/* 655 */       throw new UnsupportedOperationException("Cannot add Server Name Indication extension, because this is a server context builder.");
/*     */     }
/*     */     
/* 658 */     ObjectUtil.checkNotNull(serverName, "serverName");
/* 659 */     if (!(serverName instanceof javax.net.ssl.SNIHostName)) {
/* 660 */       throw new IllegalArgumentException("Only SNIHostName is supported. The given SNIServerName type was " + serverName
/* 661 */           .getClass().getName());
/*     */     }
/* 663 */     this.serverNames.add(serverName);
/* 664 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SslContext build() throws SSLException {
/* 673 */     if (this.forServer) {
/* 674 */       return SslContext.newServerContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout, this.clientAuth, this.protocols, this.startTls, this.enableOcsp, this.secureRandom, this.keyStoreType, 
/*     */ 
/*     */           
/* 677 */           toArray(this.options.entrySet(), (Map.Entry<SslContextOption<?>, Object>[])EMPTY_ENTRIES));
/*     */     }
/* 679 */     return SslContext.newClientContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.protocols, this.sessionCacheSize, this.sessionTimeout, this.enableOcsp, this.secureRandom, this.keyStoreType, this.endpointIdentificationAlgorithm, this.serverNames, 
/*     */ 
/*     */ 
/*     */         
/* 683 */         toArray(this.options.entrySet(), (Map.Entry<SslContextOption<?>, Object>[])EMPTY_ENTRIES));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] toArray(Iterable<? extends T> iterable, T[] prototype) {
/* 688 */     if (iterable == null) {
/* 689 */       return null;
/*     */     }
/* 691 */     List<T> list = new ArrayList<>();
/* 692 */     for (T element : iterable) {
/* 693 */       list.add(element);
/*     */     }
/* 695 */     return list.toArray(prototype);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslContextBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */