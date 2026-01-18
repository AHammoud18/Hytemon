/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.handler.ssl.ClientAuth;
/*     */ import io.netty.handler.ssl.util.LazyJavaxX509Certificate;
/*     */ import io.netty.handler.ssl.util.LazyX509Certificate;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongFunction;
/*     */ import javax.net.ssl.SNIHostName;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionBindingEvent;
/*     */ import javax.net.ssl.SSLSessionBindingListener;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
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
/*     */ final class QuicheQuicSslEngine
/*     */   extends QuicSslEngine
/*     */ {
/*     */   QuicheQuicSslContext ctx;
/*     */   private final String peerHost;
/*     */   private final int peerPort;
/*  51 */   private final QuicheQuicSslSession session = new QuicheQuicSslSession();
/*     */   
/*     */   private volatile Certificate[] localCertificateChain;
/*     */   private List<SNIServerName> sniHostNames;
/*     */   private boolean handshakeFinished;
/*     */   private String applicationProtocol;
/*     */   private boolean sessionReused;
/*     */   final String tlsHostName;
/*     */   volatile QuicheQuicConnection connection;
/*     */   volatile Consumer<String> sniSelectedCallback;
/*     */   
/*     */   QuicheQuicSslEngine(QuicheQuicSslContext ctx, @Nullable String peerHost, int peerPort) {
/*  63 */     this.ctx = ctx;
/*  64 */     this.peerHost = peerHost;
/*  65 */     this.peerPort = peerPort;
/*     */ 
/*     */     
/*  68 */     if (ctx.isClient() && isValidHostNameForSNI(peerHost)) {
/*  69 */       this.tlsHostName = peerHost;
/*  70 */       this.sniHostNames = Collections.singletonList(new SNIHostName(this.tlsHostName));
/*     */     } else {
/*  72 */       this.tlsHostName = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   long moveTo(String hostname, QuicheQuicSslContext ctx) {
/*  78 */     this.ctx.remove(this);
/*  79 */     this.ctx = ctx;
/*  80 */     long added = ctx.add(this);
/*  81 */     Consumer<String> sniSelectedCallback = this.sniSelectedCallback;
/*  82 */     if (sniSelectedCallback != null) {
/*  83 */       sniSelectedCallback.accept(hostname);
/*     */     }
/*  85 */     return added;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QuicheQuicConnection createConnection(LongFunction<Long> connectionCreator) {
/*  90 */     return this.ctx.createConnection(connectionCreator, this);
/*     */   }
/*     */   
/*     */   void setLocalCertificateChain(Certificate[] localCertificateChain) {
/*  94 */     this.localCertificateChain = localCertificateChain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValidHostNameForSNI(@Nullable String hostname) {
/* 101 */     return (hostname != null && hostname
/* 102 */       .indexOf('.') > 0 && 
/* 103 */       !hostname.endsWith(".") && 
/* 104 */       !NetUtil.isValidIpV4Address(hostname) && 
/* 105 */       !NetUtil.isValidIpV6Address(hostname));
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLParameters getSSLParameters() {
/* 110 */     SSLParameters parameters = super.getSSLParameters();
/* 111 */     parameters.setServerNames(this.sniHostNames);
/* 112 */     return parameters;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getApplicationProtocol() {
/* 118 */     return this.applicationProtocol;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getHandshakeApplicationProtocol() {
/* 124 */     return this.applicationProtocol;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) {
/* 129 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Runnable getDelegatedTask() {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInbound() {
/* 145 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInboundDone() {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeOutbound() {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutboundDone() {
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedCipherSuites() {
/* 165 */     return this.ctx.cipherSuites().<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledCipherSuites() {
/* 170 */     return getSupportedCipherSuites();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledCipherSuites(String[] suites) {
/* 175 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSupportedProtocols() {
/* 181 */     return new String[] { "TLSv1.3" };
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledProtocols() {
/* 186 */     return getSupportedProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledProtocols(String[] protocols) {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getSession() {
/* 196 */     return this.session;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SSLSession getHandshakeSession() {
/* 202 */     if (this.handshakeFinished) {
/* 203 */       return null;
/*     */     }
/* 205 */     return this.session;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginHandshake() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
/* 215 */     if (this.handshakeFinished) {
/* 216 */       return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*     */     }
/* 218 */     return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseClientMode(boolean clientMode) {
/* 223 */     if (clientMode != this.ctx.isClient()) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUseClientMode() {
/* 230 */     return this.ctx.isClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean b) {
/* 235 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 240 */     return (this.ctx.clientAuth == ClientAuth.REQUIRE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean b) {
/* 245 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 250 */     return (this.ctx.clientAuth == ClientAuth.OPTIONAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnableSessionCreation(boolean flag) {
/* 255 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnableSessionCreation() {
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void handshakeFinished(byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout, byte[] applicationProtocol, boolean sessionReused) {
/* 267 */     if (applicationProtocol == null) {
/* 268 */       this.applicationProtocol = null;
/*     */     } else {
/* 270 */       this.applicationProtocol = new String(applicationProtocol);
/*     */     } 
/* 272 */     this.session.handshakeFinished(id, cipher, protocol, peerCertificate, peerCertificateChain, creationTime, timeout);
/* 273 */     this.sessionReused = sessionReused;
/* 274 */     this.handshakeFinished = true;
/*     */   }
/*     */   
/*     */   void removeSessionFromCacheIfInvalid() {
/* 278 */     this.session.removeFromCacheIfInvalid();
/*     */   }
/*     */   
/*     */   synchronized boolean isSessionReused() {
/* 282 */     return this.sessionReused;
/*     */   }
/*     */   
/*     */   private final class QuicheQuicSslSession implements SSLSession {
/*     */     private X509Certificate[] x509PeerCerts;
/*     */     private Certificate[] peerCerts;
/*     */     private String protocol;
/*     */     private String cipher;
/*     */     private byte[] id;
/* 291 */     private long creationTime = -1L;
/* 292 */     private long timeout = -1L;
/*     */     private boolean invalid;
/* 294 */     private long lastAccessedTime = -1L;
/*     */     
/*     */     private Map<String, Object> values;
/*     */ 
/*     */     
/*     */     private boolean isEmpty(Object[] arr) {
/* 300 */       return (arr == null || arr.length == 0);
/*     */     }
/*     */     private boolean isEmpty(byte[] arr) {
/* 303 */       return (arr == null || arr.length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     void handshakeFinished(byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout) {
/* 308 */       synchronized (QuicheQuicSslEngine.this) {
/* 309 */         initPeerCerts(peerCertificateChain, peerCertificate);
/* 310 */         this.id = id;
/* 311 */         this.cipher = cipher;
/* 312 */         this.protocol = protocol;
/* 313 */         this.creationTime = creationTime * 1000L;
/* 314 */         this.timeout = timeout * 1000L;
/* 315 */         this.lastAccessedTime = System.currentTimeMillis();
/*     */       } 
/*     */     }
/*     */     
/*     */     void removeFromCacheIfInvalid() {
/* 320 */       if (!isValid())
/*     */       {
/* 322 */         removeFromCache();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void removeFromCache() {
/* 328 */       QuicClientSessionCache cache = QuicheQuicSslEngine.this.ctx.getSessionCache();
/* 329 */       if (cache != null) {
/* 330 */         cache.removeSession(getPeerHost(), getPeerPort());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void initPeerCerts(byte[][] chain, byte[] clientCert) {
/* 340 */       if (QuicheQuicSslEngine.this.getUseClientMode()) {
/* 341 */         if (isEmpty((Object[])chain)) {
/* 342 */           this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
/* 343 */           this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
/*     */         } else {
/* 345 */           this.peerCerts = new Certificate[chain.length];
/* 346 */           this.x509PeerCerts = new X509Certificate[chain.length];
/* 347 */           initCerts(chain, 0);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 355 */       else if (isEmpty(clientCert)) {
/* 356 */         this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
/* 357 */         this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
/*     */       }
/* 359 */       else if (isEmpty((Object[])chain)) {
/* 360 */         this.peerCerts = new Certificate[] { (Certificate)new LazyX509Certificate(clientCert) };
/* 361 */         this.x509PeerCerts = new X509Certificate[] { (X509Certificate)new LazyJavaxX509Certificate(clientCert) };
/*     */       } else {
/* 363 */         this.peerCerts = new Certificate[chain.length + 1];
/* 364 */         this.x509PeerCerts = new X509Certificate[chain.length + 1];
/* 365 */         this.peerCerts[0] = (Certificate)new LazyX509Certificate(clientCert);
/* 366 */         this.x509PeerCerts[0] = (X509Certificate)new LazyJavaxX509Certificate(clientCert);
/* 367 */         initCerts(chain, 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void initCerts(byte[][] chain, int startPos) {
/* 374 */       for (int i = 0; i < chain.length; i++) {
/* 375 */         int certPos = startPos + i;
/* 376 */         this.peerCerts[certPos] = (Certificate)new LazyX509Certificate(chain[i]);
/* 377 */         this.x509PeerCerts[certPos] = (X509Certificate)new LazyJavaxX509Certificate(chain[i]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] getId() {
/* 383 */       synchronized (this) {
/* 384 */         if (this.id == null) {
/* 385 */           return EmptyArrays.EMPTY_BYTES;
/*     */         }
/* 387 */         return (byte[])this.id.clone();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public SSLSessionContext getSessionContext() {
/* 393 */       return QuicheQuicSslEngine.this.ctx.sessionContext();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getCreationTime() {
/* 398 */       synchronized (QuicheQuicSslEngine.this) {
/* 399 */         return this.creationTime;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLastAccessedTime() {
/* 405 */       return this.lastAccessedTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void invalidate() {
/*     */       boolean removeFromCache;
/* 411 */       synchronized (this) {
/* 412 */         removeFromCache = !this.invalid;
/* 413 */         this.invalid = true;
/*     */       } 
/* 415 */       if (removeFromCache) {
/* 416 */         removeFromCache();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 422 */       synchronized (QuicheQuicSslEngine.this) {
/* 423 */         return (!this.invalid && System.currentTimeMillis() - this.timeout < this.creationTime);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putValue(String name, Object value) {
/*     */       Object old;
/* 429 */       ObjectUtil.checkNotNull(name, "name");
/* 430 */       ObjectUtil.checkNotNull(value, "value");
/*     */ 
/*     */       
/* 433 */       synchronized (this) {
/* 434 */         Map<String, Object> values = this.values;
/* 435 */         if (values == null)
/*     */         {
/* 437 */           values = this.values = new HashMap<>(2);
/*     */         }
/* 439 */         old = values.put(name, value);
/*     */       } 
/*     */       
/* 442 */       if (value instanceof SSLSessionBindingListener)
/*     */       {
/* 444 */         ((SSLSessionBindingListener)value).valueBound(newSSLSessionBindingEvent(name));
/*     */       }
/* 446 */       notifyUnbound(old, name);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Object getValue(String name) {
/* 452 */       ObjectUtil.checkNotNull(name, "name");
/* 453 */       synchronized (this) {
/* 454 */         if (this.values == null) {
/* 455 */           return null;
/*     */         }
/* 457 */         return this.values.get(name);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeValue(String name) {
/*     */       Object old;
/* 463 */       ObjectUtil.checkNotNull(name, "name");
/*     */ 
/*     */       
/* 466 */       synchronized (this) {
/* 467 */         Map<String, Object> values = this.values;
/* 468 */         if (values == null) {
/*     */           return;
/*     */         }
/* 471 */         old = values.remove(name);
/*     */       } 
/*     */       
/* 474 */       notifyUnbound(old, name);
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getValueNames() {
/* 479 */       synchronized (this) {
/* 480 */         Map<String, Object> values = this.values;
/* 481 */         if (values == null || values.isEmpty()) {
/* 482 */           return EmptyArrays.EMPTY_STRINGS;
/*     */         }
/* 484 */         return (String[])values.keySet().toArray((Object[])new String[0]);
/*     */       } 
/*     */     }
/*     */     
/*     */     private SSLSessionBindingEvent newSSLSessionBindingEvent(String name) {
/* 489 */       return new SSLSessionBindingEvent(QuicheQuicSslEngine.this.session, name);
/*     */     }
/*     */     
/*     */     private void notifyUnbound(@Nullable Object value, String name) {
/* 493 */       if (value instanceof SSLSessionBindingListener)
/*     */       {
/* 495 */         ((SSLSessionBindingListener)value).valueUnbound(newSSLSessionBindingEvent(name));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
/* 501 */       synchronized (QuicheQuicSslEngine.this) {
/* 502 */         if (isEmpty((Object[])this.peerCerts)) {
/* 503 */           throw new SSLPeerUnverifiedException("peer not verified");
/*     */         }
/* 505 */         return (Certificate[])this.peerCerts.clone();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate[] getLocalCertificates() {
/* 511 */       Certificate[] localCerts = QuicheQuicSslEngine.this.localCertificateChain;
/* 512 */       if (localCerts == null) {
/* 513 */         return null;
/*     */       }
/* 515 */       return (Certificate[])localCerts.clone();
/*     */     }
/*     */ 
/*     */     
/*     */     public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
/* 520 */       synchronized (QuicheQuicSslEngine.this) {
/* 521 */         if (isEmpty((Object[])this.x509PeerCerts)) {
/* 522 */           throw new SSLPeerUnverifiedException("peer not verified");
/*     */         }
/* 524 */         return (X509Certificate[])this.x509PeerCerts.clone();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/* 530 */       Certificate[] peer = getPeerCertificates();
/*     */ 
/*     */       
/* 533 */       return ((X509Certificate)peer[0]).getSubjectX500Principal();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Principal getLocalPrincipal() {
/* 539 */       Certificate[] local = QuicheQuicSslEngine.this.localCertificateChain;
/* 540 */       if (local == null || local.length == 0) {
/* 541 */         return null;
/*     */       }
/* 543 */       return ((X509Certificate)local[0]).getIssuerX500Principal();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCipherSuite() {
/* 548 */       return this.cipher;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProtocol() {
/* 553 */       return this.protocol;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getPeerHost() {
/* 559 */       return QuicheQuicSslEngine.this.peerHost;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPeerPort() {
/* 564 */       return QuicheQuicSslEngine.this.peerPort;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPacketBufferSize() {
/* 569 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getApplicationBufferSize() {
/* 574 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 579 */       if (this == o) {
/* 580 */         return true;
/*     */       }
/* 582 */       if (o == null || getClass() != o.getClass()) {
/* 583 */         return false;
/*     */       }
/* 585 */       QuicheQuicSslSession that = (QuicheQuicSslSession)o;
/* 586 */       return Arrays.equals(getId(), that.getId());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 591 */       return Arrays.hashCode(getId());
/*     */     }
/*     */     
/*     */     private QuicheQuicSslSession() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */