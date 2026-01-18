/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.internal.tcnative.SSLSession;
/*     */ import io.netty.internal.tcnative.SSLSessionCache;
/*     */ import io.netty.util.ResourceLeakDetector;
/*     */ import io.netty.util.ResourceLeakDetectorFactory;
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OpenSslSessionCache
/*     */   implements SSLSessionCache
/*     */ {
/*  40 */   private static final OpenSslInternalSession[] EMPTY_SESSIONS = new OpenSslInternalSession[0];
/*     */   private static final int DEFAULT_CACHE_SIZE;
/*     */   private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*     */   
/*     */   static {
/*  45 */     int cacheSize = SystemPropertyUtil.getInt("javax.net.ssl.sessionCacheSize", 20480);
/*  46 */     if (cacheSize >= 0) {
/*  47 */       DEFAULT_CACHE_SIZE = cacheSize;
/*     */     } else {
/*  49 */       DEFAULT_CACHE_SIZE = 20480;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  54 */   private final Map<OpenSslSessionId, NativeSslSession> sessions = new LinkedHashMap<OpenSslSessionId, NativeSslSession>()
/*     */     {
/*     */       private static final long serialVersionUID = -7773696788135734448L;
/*     */ 
/*     */ 
/*     */       
/*     */       protected boolean removeEldestEntry(Map.Entry<OpenSslSessionId, OpenSslSessionCache.NativeSslSession> eldest) {
/*  61 */         int maxSize = OpenSslSessionCache.this.maximumCacheSize.get();
/*  62 */         if (maxSize >= 0 && size() > maxSize) {
/*  63 */           OpenSslSessionCache.this.removeSessionWithId(eldest.getKey());
/*     */         }
/*     */         
/*  66 */         return false;
/*     */       }
/*     */     };
/*     */   
/*  70 */   private final AtomicInteger maximumCacheSize = new AtomicInteger(DEFAULT_CACHE_SIZE);
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final AtomicInteger sessionTimeout = new AtomicInteger(300);
/*     */   private int sessionCounter;
/*     */   
/*     */   OpenSslSessionCache(Map<Long, ReferenceCountedOpenSslEngine> engines) {
/*  78 */     this.engines = engines;
/*     */   }
/*     */   
/*     */   final void setSessionTimeout(int seconds) {
/*  82 */     int oldTimeout = this.sessionTimeout.getAndSet(seconds);
/*  83 */     if (oldTimeout > seconds)
/*     */     {
/*     */       
/*  86 */       clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final int getSessionTimeout() {
/*  91 */     return this.sessionTimeout.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean sessionCreated(NativeSslSession session) {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sessionRemoved(NativeSslSession session) {}
/*     */ 
/*     */ 
/*     */   
/*     */   final void setSessionCacheSize(int size) {
/* 112 */     long oldSize = this.maximumCacheSize.getAndSet(size);
/* 113 */     if (oldSize > size || size == 0)
/*     */     {
/* 115 */       clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final int getSessionCacheSize() {
/* 120 */     return this.maximumCacheSize.get();
/*     */   }
/*     */   
/*     */   private void expungeInvalidSessions() {
/* 124 */     if (this.sessions.isEmpty()) {
/*     */       return;
/*     */     }
/* 127 */     long now = System.currentTimeMillis();
/* 128 */     Iterator<Map.Entry<OpenSslSessionId, NativeSslSession>> iterator = this.sessions.entrySet().iterator();
/* 129 */     while (iterator.hasNext()) {
/* 130 */       NativeSslSession session = (NativeSslSession)((Map.Entry)iterator.next()).getValue();
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (session.isValid(now)) {
/*     */         break;
/*     */       }
/* 137 */       iterator.remove();
/*     */       
/* 139 */       notifyRemovalAndFree(session);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sessionCreated(long ssl, long sslSession) {
/* 145 */     ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/* 146 */     if (engine == null)
/*     */     {
/* 148 */       return false;
/*     */     }
/* 150 */     OpenSslInternalSession openSslSession = (OpenSslInternalSession)engine.getSession();
/*     */ 
/*     */ 
/*     */     
/* 154 */     NativeSslSession session = new NativeSslSession(sslSession, engine.getPeerHost(), engine.getPeerPort(), getSessionTimeout() * 1000L, openSslSession.keyValueStorage());
/*     */     
/* 156 */     openSslSession.setSessionDetails(session
/* 157 */         .creationTime, session.lastAccessedTime, session.sessionId(), session.keyValueStorage);
/* 158 */     synchronized (this) {
/*     */ 
/*     */       
/* 161 */       if (++this.sessionCounter == 255) {
/* 162 */         this.sessionCounter = 0;
/* 163 */         expungeInvalidSessions();
/*     */       } 
/*     */       
/* 166 */       if (!sessionCreated(session)) {
/*     */ 
/*     */         
/* 169 */         session.close();
/* 170 */         return false;
/*     */       } 
/* 172 */       NativeSslSession old = this.sessions.put(session.sessionId(), session);
/* 173 */       if (old != null) {
/* 174 */         notifyRemovalAndFree(old);
/*     */       }
/*     */     } 
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   public final long getSession(long ssl, byte[] sessionId) {
/*     */     NativeSslSession session;
/* 182 */     OpenSslSessionId id = new OpenSslSessionId(sessionId);
/*     */     
/* 184 */     synchronized (this) {
/* 185 */       session = this.sessions.get(id);
/* 186 */       if (session == null) {
/* 187 */         return -1L;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 192 */       if (!session.isValid() || 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 197 */         !session.upRef()) {
/*     */         
/* 199 */         removeSessionWithId(session.sessionId());
/* 200 */         return -1L;
/*     */       } 
/*     */ 
/*     */       
/* 204 */       if (session.shouldBeSingleUse())
/*     */       {
/*     */         
/* 207 */         removeSessionWithId(session.sessionId());
/*     */       }
/*     */     } 
/* 210 */     session.setLastAccessedTime(System.currentTimeMillis());
/* 211 */     ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/* 212 */     if (engine != null) {
/* 213 */       OpenSslInternalSession sslSession = (OpenSslInternalSession)engine.getSession();
/* 214 */       sslSession.setSessionDetails(session.getCreationTime(), session
/* 215 */           .getLastAccessedTime(), session.sessionId(), session.keyValueStorage);
/*     */     } 
/*     */     
/* 218 */     return session.session();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean setSession(long ssl, OpenSslInternalSession session, String host, int port) {
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized void removeSessionWithId(OpenSslSessionId id) {
/* 230 */     NativeSslSession sslSession = this.sessions.remove(id);
/* 231 */     if (sslSession != null) {
/* 232 */       notifyRemovalAndFree(sslSession);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized boolean containsSessionWithId(OpenSslSessionId id) {
/* 240 */     return this.sessions.containsKey(id);
/*     */   }
/*     */   
/*     */   private void notifyRemovalAndFree(NativeSslSession session) {
/* 244 */     sessionRemoved(session);
/* 245 */     session.free();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized OpenSslInternalSession getSession(OpenSslSessionId id) {
/* 252 */     NativeSslSession session = this.sessions.get(id);
/* 253 */     if (session != null && !session.isValid()) {
/*     */ 
/*     */       
/* 256 */       removeSessionWithId(session.sessionId());
/* 257 */       return null;
/*     */     } 
/* 259 */     return session;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final List<OpenSslSessionId> getIds() {
/*     */     OpenSslInternalSession[] sessionsArray;
/* 267 */     synchronized (this) {
/* 268 */       sessionsArray = (OpenSslInternalSession[])this.sessions.values().toArray((Object[])EMPTY_SESSIONS);
/*     */     } 
/* 270 */     List<OpenSslSessionId> ids = new ArrayList<>(sessionsArray.length);
/* 271 */     for (OpenSslInternalSession session : sessionsArray) {
/* 272 */       if (session.isValid()) {
/* 273 */         ids.add(session.sessionId());
/*     */       }
/*     */     } 
/* 276 */     return ids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void clear() {
/* 283 */     Iterator<Map.Entry<OpenSslSessionId, NativeSslSession>> iterator = this.sessions.entrySet().iterator();
/* 284 */     while (iterator.hasNext()) {
/* 285 */       NativeSslSession session = (NativeSslSession)((Map.Entry)iterator.next()).getValue();
/* 286 */       iterator.remove();
/*     */ 
/*     */       
/* 289 */       notifyRemovalAndFree(session);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static final class NativeSslSession
/*     */     implements OpenSslInternalSession
/*     */   {
/* 297 */     static final ResourceLeakDetector<NativeSslSession> LEAK_DETECTOR = ResourceLeakDetectorFactory.instance()
/* 298 */       .newResourceLeakDetector(NativeSslSession.class);
/*     */     
/*     */     private final ResourceLeakTracker<NativeSslSession> leakTracker;
/*     */     
/*     */     final Map<String, Object> keyValueStorage;
/*     */     private final long session;
/*     */     private final String peerHost;
/*     */     private final int peerPort;
/*     */     private final OpenSslSessionId id;
/*     */     private final long timeout;
/* 308 */     private final long creationTime = System.currentTimeMillis();
/* 309 */     private volatile long lastAccessedTime = this.creationTime;
/*     */     
/*     */     private volatile boolean valid = true;
/*     */     private boolean freed;
/*     */     
/*     */     NativeSslSession(long session, String peerHost, int peerPort, long timeout, Map<String, Object> keyValueStorage) {
/* 315 */       this.session = session;
/* 316 */       this.peerHost = peerHost;
/* 317 */       this.peerPort = peerPort;
/* 318 */       this.timeout = timeout;
/* 319 */       this.id = new OpenSslSessionId(SSLSession.getSessionId(session));
/* 320 */       this.keyValueStorage = keyValueStorage;
/* 321 */       this.leakTracker = LEAK_DETECTOR.track(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, Object> keyValueStorage() {
/* 326 */       return this.keyValueStorage;
/*     */     }
/*     */ 
/*     */     
/*     */     public void prepareHandshake() {
/* 331 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSessionDetails(long creationTime, long lastAccessedTime, OpenSslSessionId id, Map<String, Object> keyValueStorage) {
/* 337 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     boolean shouldBeSingleUse() {
/* 341 */       assert !this.freed;
/* 342 */       return SSLSession.shouldBeSingleUse(this.session);
/*     */     }
/*     */     
/*     */     long session() {
/* 346 */       assert !this.freed;
/* 347 */       return this.session;
/*     */     }
/*     */     
/*     */     boolean upRef() {
/* 351 */       assert !this.freed;
/* 352 */       return SSLSession.upRef(this.session);
/*     */     }
/*     */     
/*     */     synchronized void free() {
/* 356 */       close();
/* 357 */       SSLSession.free(this.session);
/*     */     }
/*     */     
/*     */     void close() {
/* 361 */       assert !this.freed;
/* 362 */       this.freed = true;
/* 363 */       invalidate();
/* 364 */       if (this.leakTracker != null) {
/* 365 */         this.leakTracker.close(this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public OpenSslSessionId sessionId() {
/* 371 */       return this.id;
/*     */     }
/*     */     
/*     */     boolean isValid(long now) {
/* 375 */       return (this.creationTime + this.timeout >= now && this.valid);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setLocalCertificate(Certificate[] localCertificate) {
/* 380 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public OpenSslSessionContext getSessionContext() {
/* 385 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tryExpandApplicationBufferSize(int packetLengthDataOnly) {
/* 390 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handshakeFinished(byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout) {
/* 396 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] getId() {
/* 401 */       return this.id.cloneBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getCreationTime() {
/* 406 */       return this.creationTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setLastAccessedTime(long time) {
/* 411 */       this.lastAccessedTime = time;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLastAccessedTime() {
/* 416 */       return this.lastAccessedTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void invalidate() {
/* 421 */       this.valid = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 426 */       return isValid(System.currentTimeMillis());
/*     */     }
/*     */ 
/*     */     
/*     */     public void putValue(String name, Object value) {
/* 431 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValue(String name) {
/* 436 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeValue(String name) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public String[] getValueNames() {
/* 446 */       return EmptyArrays.EMPTY_STRINGS;
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate[] getPeerCertificates() {
/* 451 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPeerCertificates() {
/* 456 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate[] getLocalCertificates() {
/* 461 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public X509Certificate[] getPeerCertificateChain() {
/* 466 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Principal getPeerPrincipal() {
/* 471 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Principal getLocalPrincipal() {
/* 476 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCipherSuite() {
/* 481 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProtocol() {
/* 486 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPeerHost() {
/* 491 */       return this.peerHost;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPeerPort() {
/* 496 */       return this.peerPort;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPacketBufferSize() {
/* 501 */       return ReferenceCountedOpenSslEngine.MAX_RECORD_SIZE;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getApplicationBufferSize() {
/* 506 */       return ReferenceCountedOpenSslEngine.MAX_PLAINTEXT_LENGTH;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 511 */       return this.id.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 516 */       if (this == o) {
/* 517 */         return true;
/*     */       }
/* 519 */       if (!(o instanceof OpenSslInternalSession)) {
/* 520 */         return false;
/*     */       }
/* 522 */       OpenSslInternalSession session1 = (OpenSslInternalSession)o;
/* 523 */       return this.id.equals(session1.sessionId());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslSessionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */