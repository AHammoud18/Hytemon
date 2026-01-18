/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.handler.ssl.ApplicationProtocolNegotiator;
/*     */ import io.netty.handler.ssl.ClientAuth;
/*     */ import io.netty.handler.ssl.SslContextOption;
/*     */ import io.netty.handler.ssl.SslHandler;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.Mapping;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.LongFunction;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509ExtendedKeyManager;
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
/*     */ final class QuicheQuicSslContext
/*     */   extends QuicSslContext
/*     */ {
/*  64 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(QuicheQuicSslContext.class);
/*     */   
/*     */   private static final String[] NAMED_GROUPS;
/*     */   final ClientAuth clientAuth;
/*     */   private final boolean server;
/*  69 */   private static final String[] DEFAULT_NAMED_GROUPS = new String[] { "x25519", "secp256r1", "secp384r1", "secp521r1" }; private final ApplicationProtocolNegotiator apn;
/*     */   private long sessionCacheSize;
/*     */   
/*     */   static {
/*  73 */     String[] namedGroups = DEFAULT_NAMED_GROUPS;
/*  74 */     Set<String> defaultConvertedNamedGroups = new LinkedHashSet<>(namedGroups.length);
/*  75 */     for (int i = 0; i < namedGroups.length; i++) {
/*  76 */       defaultConvertedNamedGroups.add(GroupsConverter.toBoringSSL(namedGroups[i]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  81 */     if (Quic.isAvailable()) {
/*  82 */       long sslCtx = BoringSSL.SSLContext_new();
/*     */       
/*     */       try {
/*  85 */         Iterator<String> defaultGroupsIter = defaultConvertedNamedGroups.iterator();
/*  86 */         while (defaultGroupsIter.hasNext()) {
/*  87 */           if (BoringSSL.SSLContext_set1_groups_list(sslCtx, new String[] { defaultGroupsIter.next() }) == 0)
/*     */           {
/*     */ 
/*     */             
/*  91 */             defaultGroupsIter.remove();
/*     */           }
/*     */         } 
/*     */         
/*  95 */         String groups = SystemPropertyUtil.get("jdk.tls.namedGroups", null);
/*  96 */         if (groups != null) {
/*  97 */           String[] nGroups = groups.split(",");
/*  98 */           Set<String> supportedNamedGroups = new LinkedHashSet<>(nGroups.length);
/*  99 */           Set<String> supportedConvertedNamedGroups = new LinkedHashSet<>(nGroups.length);
/*     */           
/* 101 */           Set<String> unsupportedNamedGroups = new LinkedHashSet<>();
/* 102 */           for (String namedGroup : nGroups) {
/* 103 */             String converted = GroupsConverter.toBoringSSL(namedGroup);
/*     */             
/* 105 */             if (BoringSSL.SSLContext_set1_groups_list(sslCtx, new String[] { converted }) == 0) {
/* 106 */               unsupportedNamedGroups.add(namedGroup);
/*     */             } else {
/* 108 */               supportedConvertedNamedGroups.add(converted);
/* 109 */               supportedNamedGroups.add(namedGroup);
/*     */             } 
/*     */           } 
/*     */           
/* 113 */           if (supportedNamedGroups.isEmpty()) {
/* 114 */             namedGroups = defaultConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 115 */             LOGGER.info("All configured namedGroups are not supported: {}. Use default: {}.", 
/* 116 */                 Arrays.toString(unsupportedNamedGroups.toArray((Object[])EmptyArrays.EMPTY_STRINGS)), 
/* 117 */                 Arrays.toString((Object[])DEFAULT_NAMED_GROUPS));
/*     */           } else {
/* 119 */             String[] groupArray = supportedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 120 */             if (unsupportedNamedGroups.isEmpty()) {
/* 121 */               LOGGER.info("Using configured namedGroups -D 'jdk.tls.namedGroup': {} ", 
/* 122 */                   Arrays.toString((Object[])groupArray));
/*     */             } else {
/* 124 */               LOGGER.info("Using supported configured namedGroups: {}. Unsupported namedGroups: {}. ", 
/* 125 */                   Arrays.toString((Object[])groupArray), 
/* 126 */                   Arrays.toString(unsupportedNamedGroups.toArray((Object[])EmptyArrays.EMPTY_STRINGS)));
/*     */             } 
/* 128 */             namedGroups = supportedConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */           } 
/*     */         } else {
/* 131 */           namedGroups = defaultConvertedNamedGroups.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */         } 
/*     */       } finally {
/* 134 */         BoringSSL.SSLContext_free(sslCtx);
/*     */       } 
/*     */     } 
/* 137 */     NAMED_GROUPS = namedGroups;
/*     */   }
/*     */   private long sessionTimeout;
/*     */   private final QuicheQuicSslSessionContext sessionCtx;
/*     */   private final QuicheQuicSslEngineMap engineMap;
/*     */   
/*     */   QuicheQuicSslContext(boolean server, long sessionTimeout, long sessionCacheSize, ClientAuth clientAuth, @Nullable TrustManagerFactory trustManagerFactory, @Nullable KeyManagerFactory keyManagerFactory, String password, @Nullable Mapping<? super String, ? extends QuicSslContext> mapping, @Nullable Boolean earlyData, @Nullable BoringSSLKeylog keylog, String[] applicationProtocols, Map.Entry<SslContextOption<?>, Object>... ctxOptions) {
/*     */     X509TrustManager trustManager;
/*     */     X509ExtendedKeyManager keyManager;
/*     */     BoringSSLPrivateKeyMethod privateKeyMethod;
/* 147 */     this.engineMap = new QuicheQuicSslEngineMap();
/*     */ 
/*     */     
/* 150 */     this.sessionTicketCallback = new BoringSSLSessionTicketCallback();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     Quic.ensureAvailability();
/* 161 */     this.server = server;
/* 162 */     this.clientAuth = server ? (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
/*     */     
/* 164 */     if (trustManagerFactory == null) {
/*     */       
/*     */       try {
/* 167 */         trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 168 */         trustManagerFactory.init((KeyStore)null);
/* 169 */         trustManager = chooseTrustManager(trustManagerFactory);
/* 170 */       } catch (Exception e) {
/* 171 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } else {
/* 174 */       trustManager = chooseTrustManager(trustManagerFactory);
/*     */     } 
/*     */     
/* 177 */     if (keyManagerFactory == null) {
/* 178 */       if (server) {
/* 179 */         throw new IllegalArgumentException("No KeyManagerFactory");
/*     */       }
/* 181 */       keyManager = null;
/*     */     } else {
/* 183 */       keyManager = chooseKeyManager(keyManagerFactory);
/*     */     } 
/* 185 */     String[] groups = NAMED_GROUPS;
/* 186 */     String[] sigalgs = EmptyArrays.EMPTY_STRINGS;
/* 187 */     Map<String, String> serverKeyTypes = null;
/* 188 */     Set<String> clientKeyTypes = null;
/*     */     
/* 190 */     if (ctxOptions != null) {
/* 191 */       for (Map.Entry<SslContextOption<?>, Object> ctxOpt : ctxOptions) {
/* 192 */         SslContextOption<?> option = ctxOpt.getKey();
/*     */         
/* 194 */         if (option == BoringSSLContextOption.GROUPS) {
/* 195 */           String[] groupsArray = (String[])ctxOpt.getValue();
/* 196 */           Set<String> groupsSet = new LinkedHashSet<>(groupsArray.length);
/* 197 */           for (String group : groupsArray) {
/* 198 */             groupsSet.add(GroupsConverter.toBoringSSL(group));
/*     */           }
/* 200 */           groups = groupsSet.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 201 */         } else if (option == BoringSSLContextOption.SIGNATURE_ALGORITHMS) {
/* 202 */           String[] sigalgsArray = (String[])ctxOpt.getValue();
/* 203 */           Set<String> sigalgsSet = new LinkedHashSet<>(sigalgsArray.length);
/* 204 */           for (String sigalg : sigalgsArray) {
/* 205 */             sigalgsSet.add(sigalg);
/*     */           }
/* 207 */           sigalgs = sigalgsSet.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/* 208 */         } else if (option == BoringSSLContextOption.CLIENT_KEY_TYPES) {
/* 209 */           clientKeyTypes = (Set<String>)ctxOpt.getValue();
/* 210 */         } else if (option == BoringSSLContextOption.SERVER_KEY_TYPES) {
/* 211 */           serverKeyTypes = (Map<String, String>)ctxOpt.getValue();
/*     */         } else {
/* 213 */           LOGGER.debug("Skipping unsupported " + SslContextOption.class.getSimpleName() + ": " + ctxOpt
/* 214 */               .getKey());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 219 */     if (keyManagerFactory instanceof BoringSSLKeylessManagerFactory) {
/* 220 */       privateKeyMethod = new BoringSSLAsyncPrivateKeyMethodAdapter(this.engineMap, ((BoringSSLKeylessManagerFactory)keyManagerFactory).privateKeyMethod);
/*     */     } else {
/*     */       
/* 223 */       privateKeyMethod = null;
/*     */     } 
/* 225 */     this.sessionCache = server ? null : new QuicClientSessionCache();
/* 226 */     int verifyMode = server ? boringSSLVerifyModeForServer(this.clientAuth) : BoringSSL.SSL_VERIFY_PEER;
/* 227 */     this.nativeSslContext = new NativeSslContext(BoringSSL.SSLContext_new(server, applicationProtocols, new BoringSSLHandshakeCompleteCallback(this.engineMap), new BoringSSLCertificateCallback(this.engineMap, keyManager, password, serverKeyTypes, clientKeyTypes), new BoringSSLCertificateVerifyCallback(this.engineMap, trustManager), 
/*     */ 
/*     */ 
/*     */           
/* 231 */           (mapping == null) ? null : new BoringSSLTlsextServernameCallback(this.engineMap, mapping), 
/* 232 */           (keylog == null) ? null : new BoringSSLKeylogCallback(this.engineMap, keylog), 
/* 233 */           server ? null : new BoringSSLSessionCallback(this.engineMap, this.sessionCache), privateKeyMethod, this.sessionTicketCallback, verifyMode, 
/*     */           
/* 235 */           BoringSSL.subjectNames(trustManager.getAcceptedIssuers())));
/* 236 */     boolean success = false;
/*     */     try {
/* 238 */       if (groups.length > 0 && BoringSSL.SSLContext_set1_groups_list(this.nativeSslContext.ctx, groups) == 0) {
/* 239 */         String msg = "failed to set curves / groups list: " + Arrays.toString((Object[])groups);
/* 240 */         String lastError = BoringSSL.ERR_last_error();
/* 241 */         if (lastError != null)
/*     */         {
/* 243 */           msg = msg + ". " + lastError;
/*     */         }
/* 245 */         throw new IllegalStateException(msg);
/*     */       } 
/*     */       
/* 248 */       if (sigalgs.length > 0 && BoringSSL.SSLContext_set1_sigalgs_list(this.nativeSslContext.ctx, sigalgs) == 0) {
/* 249 */         String msg = "failed to set signature algorithm list: " + Arrays.toString((Object[])sigalgs);
/* 250 */         String lastError = BoringSSL.ERR_last_error();
/* 251 */         if (lastError != null)
/*     */         {
/* 253 */           msg = msg + ". " + lastError;
/*     */         }
/* 255 */         throw new IllegalStateException(msg);
/*     */       } 
/*     */       
/* 258 */       this.apn = new QuicheQuicApplicationProtocolNegotiator(applicationProtocols);
/* 259 */       if (this.sessionCache != null) {
/*     */         
/* 261 */         this.sessionCache.setSessionCacheSize((int)sessionCacheSize);
/* 262 */         this.sessionCache.setSessionTimeout((int)sessionTimeout);
/*     */       } else {
/*     */         
/* 265 */         BoringSSL.SSLContext_setSessionCacheSize(this.nativeSslContext
/* 266 */             .address(), sessionCacheSize);
/* 267 */         this.sessionCacheSize = sessionCacheSize;
/*     */         
/* 269 */         BoringSSL.SSLContext_setSessionCacheTimeout(this.nativeSslContext
/* 270 */             .address(), sessionTimeout);
/* 271 */         this.sessionTimeout = sessionTimeout;
/*     */       } 
/* 273 */       if (earlyData != null) {
/* 274 */         BoringSSL.SSLContext_set_early_data_enabled(this.nativeSslContext.address(), earlyData.booleanValue());
/*     */       }
/* 276 */       this.sessionCtx = new QuicheQuicSslSessionContext(this);
/* 277 */       success = true;
/*     */     } finally {
/* 279 */       if (!success)
/* 280 */         this.nativeSslContext.release(); 
/*     */     } 
/*     */   }
/*     */   private final QuicClientSessionCache sessionCache; private final BoringSSLSessionTicketCallback sessionTicketCallback; final NativeSslContext nativeSslContext;
/*     */   
/*     */   private X509ExtendedKeyManager chooseKeyManager(KeyManagerFactory keyManagerFactory) {
/* 286 */     for (KeyManager manager : keyManagerFactory.getKeyManagers()) {
/* 287 */       if (manager instanceof X509ExtendedKeyManager) {
/* 288 */         return (X509ExtendedKeyManager)manager;
/*     */       }
/*     */     } 
/* 291 */     throw new IllegalArgumentException("No X509ExtendedKeyManager included");
/*     */   }
/*     */   
/*     */   private static X509TrustManager chooseTrustManager(TrustManagerFactory trustManagerFactory) {
/* 295 */     for (TrustManager manager : trustManagerFactory.getTrustManagers()) {
/* 296 */       if (manager instanceof X509TrustManager) {
/* 297 */         return (X509TrustManager)manager;
/*     */       }
/*     */     } 
/* 300 */     throw new IllegalArgumentException("No X509TrustManager included");
/*     */   }
/*     */   
/*     */   static X509Certificate[] toX509Certificates0(@Nullable File file) throws CertificateException {
/* 304 */     return toX509Certificates(file);
/*     */   }
/*     */   
/*     */   static PrivateKey toPrivateKey0(@Nullable File keyFile, @Nullable String keyPassword) throws Exception {
/* 308 */     return toPrivateKey(keyFile, keyPassword);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static TrustManagerFactory buildTrustManagerFactory0(X509Certificate[] certCollection) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
/* 314 */     return buildTrustManagerFactory(certCollection, null, null);
/*     */   }
/*     */   
/*     */   private static int boringSSLVerifyModeForServer(ClientAuth mode) {
/* 318 */     switch (mode) {
/*     */       case NONE:
/* 320 */         return BoringSSL.SSL_VERIFY_NONE;
/*     */       case REQUIRE:
/* 322 */         return BoringSSL.SSL_VERIFY_PEER | BoringSSL.SSL_VERIFY_FAIL_IF_NO_PEER_CERT;
/*     */       case OPTIONAL:
/* 324 */         return BoringSSL.SSL_VERIFY_PEER;
/*     */     } 
/* 326 */     throw new Error("Unexpected mode: " + mode);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   QuicheQuicConnection createConnection(LongFunction<Long> connectionCreator, QuicheQuicSslEngine engine) {
/* 332 */     this.nativeSslContext.retain();
/* 333 */     long ssl = BoringSSL.SSL_new(this.nativeSslContext.address(), isServer(), engine.tlsHostName);
/* 334 */     this.engineMap.put(ssl, engine);
/* 335 */     long connection = ((Long)connectionCreator.apply(ssl)).longValue();
/* 336 */     if (connection == -1L) {
/* 337 */       this.engineMap.remove(ssl);
/*     */ 
/*     */       
/* 340 */       this.nativeSslContext.release();
/* 341 */       return null;
/*     */     } 
/*     */     
/* 344 */     return new QuicheQuicConnection(connection, ssl, engine, (ReferenceCounted)this.nativeSslContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long add(QuicheQuicSslEngine engine) {
/* 354 */     this.nativeSslContext.retain();
/* 355 */     engine.connection.reattach((ReferenceCounted)this.nativeSslContext);
/* 356 */     this.engineMap.put(engine.connection.ssl, engine);
/* 357 */     return this.nativeSslContext.address();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(QuicheQuicSslEngine engine) {
/* 366 */     QuicheQuicSslEngine removed = this.engineMap.remove(engine.connection.ssl);
/* 367 */     assert removed == null || removed == engine;
/* 368 */     engine.removeSessionFromCacheIfInvalid();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QuicClientSessionCache getSessionCache() {
/* 373 */     return this.sessionCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClient() {
/* 378 */     return !this.server;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> cipherSuites() {
/* 383 */     return Arrays.asList(new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384" });
/*     */   }
/*     */ 
/*     */   
/*     */   public long sessionCacheSize() {
/* 388 */     if (this.sessionCache != null) {
/* 389 */       return this.sessionCache.getSessionCacheSize();
/*     */     }
/* 391 */     synchronized (this) {
/* 392 */       return this.sessionCacheSize;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long sessionTimeout() {
/* 399 */     if (this.sessionCache != null) {
/* 400 */       return this.sessionCache.getSessionTimeout();
/*     */     }
/* 402 */     synchronized (this) {
/* 403 */       return this.sessionTimeout;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
/* 410 */     return this.apn;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicSslEngine newEngine(ByteBufAllocator alloc) {
/* 415 */     return new QuicheQuicSslEngine(this, null, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicSslEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
/* 420 */     return new QuicheQuicSslEngine(this, peerHost, peerPort);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicSslSessionContext sessionContext() {
/* 425 */     return this.sessionCtx;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SslHandler newHandler(ByteBufAllocator alloc, boolean startTls) {
/* 430 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SslHandler newHandler(ByteBufAllocator alloc, Executor delegatedTaskExecutor) {
/* 435 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SslHandler newHandler(ByteBufAllocator alloc, boolean startTls, Executor executor) {
/* 440 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls) {
/* 445 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, Executor delegatedTaskExecutor) {
/* 451 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SslHandler newHandler(ByteBufAllocator alloc, String peerHost, int peerPort, boolean startTls, Executor delegatedTaskExecutor) {
/* 457 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 463 */       this.nativeSslContext.release();
/*     */     } finally {
/* 465 */       super.finalize();
/*     */     } 
/*     */   }
/*     */   
/*     */   void setSessionTimeout(int seconds) throws IllegalArgumentException {
/* 470 */     if (this.sessionCache != null) {
/* 471 */       this.sessionCache.setSessionTimeout(seconds);
/*     */     } else {
/* 473 */       BoringSSL.SSLContext_setSessionCacheTimeout(this.nativeSslContext.address(), seconds);
/* 474 */       this.sessionTimeout = seconds;
/*     */     } 
/*     */   }
/*     */   
/*     */   void setSessionCacheSize(int size) throws IllegalArgumentException {
/* 479 */     if (this.sessionCache != null) {
/* 480 */       this.sessionCache.setSessionCacheSize(size);
/*     */     } else {
/* 482 */       BoringSSL.SSLContext_setSessionCacheSize(this.nativeSslContext.address(), size);
/* 483 */       this.sessionCacheSize = size;
/*     */     } 
/*     */   }
/*     */   
/*     */   void setSessionTicketKeys(SslSessionTicketKey[] ticketKeys) {
/* 488 */     this.sessionTicketCallback.setSessionTicketKeys(ticketKeys);
/* 489 */     BoringSSL.SSLContext_setSessionTicketKeys(this.nativeSslContext
/* 490 */         .address(), (ticketKeys != null && ticketKeys.length != 0));
/*     */   }
/*     */   
/*     */   private static final class QuicheQuicApplicationProtocolNegotiator
/*     */     implements ApplicationProtocolNegotiator {
/*     */     private final List<String> protocols;
/*     */     
/*     */     QuicheQuicApplicationProtocolNegotiator(String... protocols) {
/* 498 */       if (protocols == null) {
/* 499 */         this.protocols = Collections.emptyList();
/*     */       } else {
/* 501 */         this.protocols = Collections.unmodifiableList(Arrays.asList(protocols));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> protocols() {
/* 507 */       return this.protocols;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class QuicheQuicSslSessionContext implements QuicSslSessionContext {
/*     */     private final QuicheQuicSslContext context;
/*     */     
/*     */     QuicheQuicSslSessionContext(QuicheQuicSslContext context) {
/* 515 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SSLSession getSession(byte[] sessionId) {
/* 521 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Enumeration<byte[]> getIds() {
/* 526 */       return new Enumeration<byte[]>()
/*     */         {
/*     */           public boolean hasMoreElements() {
/* 529 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public byte[] nextElement() {
/* 534 */             throw new NoSuchElementException();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSessionTimeout(int seconds) throws IllegalArgumentException {
/* 541 */       this.context.setSessionTimeout(seconds);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSessionTimeout() {
/* 546 */       return (int)this.context.sessionTimeout();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSessionCacheSize(int size) throws IllegalArgumentException {
/* 551 */       this.context.setSessionCacheSize(size);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSessionCacheSize() {
/* 556 */       return (int)this.context.sessionCacheSize();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setTicketKeys(SslSessionTicketKey... keys) {
/* 561 */       this.context.setSessionTicketKeys(keys);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class NativeSslContext extends AbstractReferenceCounted {
/*     */     private final long ctx;
/*     */     
/*     */     NativeSslContext(long ctx) {
/* 569 */       this.ctx = ctx;
/*     */     }
/*     */     
/*     */     long address() {
/* 573 */       return this.ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void deallocate() {
/* 578 */       BoringSSL.SSLContext_free(this.ctx);
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCounted touch(Object hint) {
/* 583 */       return (ReferenceCounted)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 588 */       return "NativeSslContext{ctx=" + this.ctx + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class BoringSSLAsyncPrivateKeyMethodAdapter
/*     */     implements BoringSSLPrivateKeyMethod
/*     */   {
/*     */     private final QuicheQuicSslEngineMap engineMap;
/*     */     private final BoringSSLAsyncPrivateKeyMethod privateKeyMethod;
/*     */     
/*     */     BoringSSLAsyncPrivateKeyMethodAdapter(QuicheQuicSslEngineMap engineMap, BoringSSLAsyncPrivateKeyMethod privateKeyMethod) {
/* 600 */       this.engineMap = engineMap;
/* 601 */       this.privateKeyMethod = privateKeyMethod;
/*     */     }
/*     */ 
/*     */     
/*     */     public void sign(long ssl, int signatureAlgorithm, byte[] input, BiConsumer<byte[], Throwable> callback) {
/* 606 */       QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/* 607 */       if (engine == null) {
/*     */         
/* 609 */         callback.accept(null, null);
/*     */       } else {
/* 611 */         this.privateKeyMethod.sign(engine, signatureAlgorithm, input).addListener(f -> {
/*     */               Throwable cause = f.cause();
/*     */               if (cause != null) {
/*     */                 callback.accept(null, cause);
/*     */               } else {
/*     */                 callback.accept(f.getNow(), null);
/*     */               } 
/*     */             });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void decrypt(long ssl, byte[] input, BiConsumer<byte[], Throwable> callback) {
/* 624 */       QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/* 625 */       if (engine == null) {
/*     */         
/* 627 */         callback.accept(null, null);
/*     */       } else {
/* 629 */         this.privateKeyMethod.decrypt(engine, input).addListener(f -> {
/*     */               Throwable cause = f.cause();
/*     */               if (cause != null) {
/*     */                 callback.accept(null, cause);
/*     */               } else {
/*     */                 callback.accept(f.getNow(), null);
/*     */               } 
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicSslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */