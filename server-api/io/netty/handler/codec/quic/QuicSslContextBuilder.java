/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.handler.ssl.ClientAuth;
/*     */ import io.netty.handler.ssl.SslContextOption;
/*     */ import io.netty.handler.ssl.util.KeyManagerFactoryWrapper;
/*     */ import io.netty.handler.ssl.util.TrustManagerFactoryWrapper;
/*     */ import io.netty.util.Mapping;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.File;
/*     */ import java.net.Socket;
/*     */ import java.security.KeyStore;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509ExtendedKeyManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class QuicSslContextBuilder
/*     */ {
/*  55 */   private static final X509ExtendedKeyManager SNI_KEYMANAGER = new X509ExtendedKeyManager() {
/*  56 */       private final X509Certificate[] emptyCerts = new X509Certificate[0];
/*  57 */       private final String[] emptyStrings = new String[0];
/*     */ 
/*     */       
/*     */       public String[] getClientAliases(String keyType, Principal[] issuers) {
/*  61 */         return this.emptyStrings;
/*     */       }
/*     */ 
/*     */       
/*     */       @Nullable
/*     */       public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
/*  67 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public String[] getServerAliases(String keyType, Principal[] issuers) {
/*  72 */         return this.emptyStrings;
/*     */       }
/*     */ 
/*     */       
/*     */       @Nullable
/*     */       public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
/*  78 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public X509Certificate[] getCertificateChain(String alias) {
/*  83 */         return this.emptyCerts;
/*     */       }
/*     */ 
/*     */       
/*     */       @Nullable
/*     */       public PrivateKey getPrivateKey(String alias) {
/*  89 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QuicSslContextBuilder forClient() {
/*  97 */     return new QuicSslContextBuilder(false);
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
/*     */   public static QuicSslContextBuilder forServer(File keyFile, @Nullable String keyPassword, File certChainFile) {
/* 111 */     return (new QuicSslContextBuilder(true)).keyManager(keyFile, keyPassword, certChainFile);
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
/*     */   public static QuicSslContextBuilder forServer(PrivateKey key, @Nullable String keyPassword, X509Certificate... certChain) {
/* 125 */     return (new QuicSslContextBuilder(true)).keyManager(key, keyPassword, certChain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QuicSslContextBuilder forServer(KeyManagerFactory keyManagerFactory, @Nullable String password) {
/* 135 */     return (new QuicSslContextBuilder(true)).keyManager(keyManagerFactory, password);
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
/*     */   public static QuicSslContextBuilder forServer(KeyManager keyManager, @Nullable String keyPassword) {
/* 147 */     return (new QuicSslContextBuilder(true)).keyManager(keyManager, keyPassword);
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
/*     */   public static QuicSslContext buildForServerWithSni(Mapping<? super String, ? extends QuicSslContext> mapping) {
/* 160 */     return forServer(SNI_KEYMANAGER, (String)null).sni(mapping).build();
/*     */   }
/*     */   
/* 163 */   private static final Map.Entry[] EMPTY_ENTRIES = new Map.Entry[0];
/*     */   
/*     */   private final boolean forServer;
/* 166 */   private final Map<SslContextOption<?>, Object> options = new HashMap<>();
/*     */   private TrustManagerFactory trustManagerFactory;
/*     */   private String keyPassword;
/*     */   private KeyManagerFactory keyManagerFactory;
/* 170 */   private long sessionCacheSize = 20480L;
/* 171 */   private long sessionTimeout = 300L;
/* 172 */   private ClientAuth clientAuth = ClientAuth.NONE;
/*     */   private String[] applicationProtocols;
/*     */   private Boolean earlyData;
/*     */   private BoringSSLKeylog keylog;
/*     */   private Mapping<? super String, ? extends QuicSslContext> mapping;
/*     */   
/*     */   private QuicSslContextBuilder(boolean forServer) {
/* 179 */     this.forServer = forServer;
/*     */   }
/*     */   
/*     */   private QuicSslContextBuilder sni(Mapping<? super String, ? extends QuicSslContext> mapping) {
/* 183 */     this.mapping = (Mapping<? super String, ? extends QuicSslContext>)ObjectUtil.checkNotNull(mapping, "mapping");
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> QuicSslContextBuilder option(SslContextOption<T> option, T value) {
/* 191 */     if (value == null) {
/* 192 */       this.options.remove(option);
/*     */     } else {
/* 194 */       this.options.put(option, value);
/*     */     } 
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder earlyData(boolean enabled) {
/* 203 */     this.earlyData = Boolean.valueOf(enabled);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder keylog(boolean enabled) {
/* 215 */     keylog(enabled ? BoringSSLLoggingKeylog.INSTANCE : null);
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder keylog(@Nullable BoringSSLKeylog keylog) {
/* 226 */     this.keylog = keylog;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder trustManager(@Nullable File trustCertCollectionFile) {
/*     */     try {
/* 239 */       return trustManager(QuicheQuicSslContext.toX509Certificates0(trustCertCollectionFile));
/* 240 */     } catch (Exception e) {
/* 241 */       throw new IllegalArgumentException("File does not contain valid certificates: " + trustCertCollectionFile, e);
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
/*     */   public QuicSslContextBuilder trustManager(X509Certificate... trustCertCollection) {
/*     */     try {
/* 254 */       return trustManager(QuicheQuicSslContext.buildTrustManagerFactory0(trustCertCollection));
/* 255 */     } catch (Exception e) {
/* 256 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder trustManager(@Nullable TrustManagerFactory trustManagerFactory) {
/* 267 */     this.trustManagerFactory = trustManagerFactory;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder trustManager(TrustManager trustManager) {
/* 279 */     return trustManager((TrustManagerFactory)new TrustManagerFactoryWrapper(trustManager));
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
/*     */   public QuicSslContextBuilder keyManager(@Nullable File keyFile, @Nullable String keyPassword, @Nullable File keyCertChainFile) {
/*     */     X509Certificate[] keyCertChain;
/*     */     PrivateKey key;
/*     */     try {
/* 296 */       keyCertChain = QuicheQuicSslContext.toX509Certificates0(keyCertChainFile);
/* 297 */     } catch (Exception e) {
/* 298 */       throw new IllegalArgumentException("File does not contain valid certificates: " + keyCertChainFile, e);
/*     */     } 
/*     */     try {
/* 301 */       key = QuicheQuicSslContext.toPrivateKey0(keyFile, keyPassword);
/* 302 */     } catch (Exception e) {
/* 303 */       throw new IllegalArgumentException("File does not contain valid private key: " + keyFile, e);
/*     */     } 
/* 305 */     return keyManager(key, keyPassword, keyCertChain);
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
/*     */   public QuicSslContextBuilder keyManager(@Nullable PrivateKey key, @Nullable String keyPassword, X509Certificate... certChain) {
/*     */     try {
/* 320 */       KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
/* 321 */       ks.load(null);
/* 322 */       char[] pass = (keyPassword == null) ? new char[0] : keyPassword.toCharArray();
/* 323 */       ks.setKeyEntry("alias", key, pass, (Certificate[])certChain);
/* 324 */       KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
/* 325 */           KeyManagerFactory.getDefaultAlgorithm());
/* 326 */       keyManagerFactory.init(ks, pass);
/* 327 */       return keyManager(keyManagerFactory, keyPassword);
/* 328 */     } catch (Exception e) {
/* 329 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder keyManager(@Nullable KeyManagerFactory keyManagerFactory, @Nullable String keyPassword) {
/* 339 */     this.keyPassword = keyPassword;
/* 340 */     this.keyManagerFactory = keyManagerFactory;
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder keyManager(KeyManager keyManager, @Nullable String password) {
/* 352 */     return keyManager((KeyManagerFactory)new KeyManagerFactoryWrapper(keyManager), password);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder applicationProtocols(String... applicationProtocols) {
/* 359 */     this.applicationProtocols = applicationProtocols;
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder sessionCacheSize(long sessionCacheSize) {
/* 368 */     this.sessionCacheSize = sessionCacheSize;
/* 369 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder sessionTimeout(long sessionTimeout) {
/* 377 */     this.sessionTimeout = sessionTimeout;
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContextBuilder clientAuth(ClientAuth clientAuth) {
/* 385 */     if (!this.forServer) {
/* 386 */       throw new UnsupportedOperationException("Only supported for server");
/*     */     }
/* 388 */     this.clientAuth = (ClientAuth)ObjectUtil.checkNotNull(clientAuth, "clientAuth");
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicSslContext build() {
/* 397 */     if (this.forServer) {
/* 398 */       return new QuicheQuicSslContext(true, this.sessionTimeout, this.sessionCacheSize, this.clientAuth, this.trustManagerFactory, this.keyManagerFactory, this.keyPassword, this.mapping, this.earlyData, this.keylog, this.applicationProtocols, 
/*     */           
/* 400 */           toArray(this.options.entrySet(), (Map.Entry<SslContextOption<?>, Object>[])EMPTY_ENTRIES));
/*     */     }
/* 402 */     return new QuicheQuicSslContext(false, this.sessionTimeout, this.sessionCacheSize, this.clientAuth, this.trustManagerFactory, this.keyManagerFactory, this.keyPassword, this.mapping, this.earlyData, this.keylog, this.applicationProtocols, 
/*     */         
/* 404 */         toArray(this.options.entrySet(), (Map.Entry<SslContextOption<?>, Object>[])EMPTY_ENTRIES));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] toArray(Iterable<? extends T> iterable, T[] prototype) {
/* 409 */     if (iterable == null) {
/* 410 */       return null;
/*     */     }
/* 412 */     List<T> list = new ArrayList<>();
/* 413 */     for (T element : iterable) {
/* 414 */       list.add(element);
/*     */     }
/* 416 */     return list.toArray(prototype);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicSslContextBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */