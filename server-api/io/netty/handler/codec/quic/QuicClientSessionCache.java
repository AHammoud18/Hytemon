/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.AsciiString;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ final class QuicClientSessionCache
/*     */ {
/*     */   private static final int DEFAULT_CACHE_SIZE;
/*     */   
/*     */   static {
/*  32 */     int cacheSize = SystemPropertyUtil.getInt("javax.net.ssl.sessionCacheSize", 20480);
/*  33 */     if (cacheSize >= 0) {
/*  34 */       DEFAULT_CACHE_SIZE = cacheSize;
/*     */     } else {
/*  36 */       DEFAULT_CACHE_SIZE = 20480;
/*     */     } 
/*     */   }
/*     */   
/*  40 */   private final AtomicInteger maximumCacheSize = new AtomicInteger(DEFAULT_CACHE_SIZE);
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final AtomicInteger sessionTimeout = new AtomicInteger(300);
/*     */   
/*     */   private int sessionCounter;
/*  47 */   private final Map<HostPort, SessionHolder> sessions = new LinkedHashMap<HostPort, SessionHolder>()
/*     */     {
/*     */       private static final long serialVersionUID = -7773696788135734448L;
/*     */ 
/*     */ 
/*     */       
/*     */       protected boolean removeEldestEntry(Map.Entry<QuicClientSessionCache.HostPort, QuicClientSessionCache.SessionHolder> eldest) {
/*  54 */         int maxSize = QuicClientSessionCache.this.maximumCacheSize.get();
/*  55 */         return (maxSize >= 0 && size() > maxSize);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   void saveSession(@Nullable String host, int port, long creationTime, long timeout, byte[] session, boolean isSingleUse) {
/*  61 */     HostPort hostPort = keyFor(host, port);
/*  62 */     if (hostPort != null) {
/*  63 */       synchronized (this.sessions) {
/*     */ 
/*     */         
/*  66 */         if (++this.sessionCounter == 255) {
/*  67 */           this.sessionCounter = 0;
/*  68 */           expungeInvalidSessions();
/*     */         } 
/*     */         
/*  71 */         this.sessions.put(hostPort, new SessionHolder(creationTime, timeout, session, isSingleUse));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasSession(@Nullable String host, int port) {
/*  78 */     HostPort hostPort = keyFor(host, port);
/*  79 */     if (hostPort != null) {
/*  80 */       synchronized (this.sessions) {
/*  81 */         return this.sessions.containsKey(hostPort);
/*     */       } 
/*     */     }
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   byte[] getSession(@Nullable String host, int port) {
/*  88 */     HostPort hostPort = keyFor(host, port);
/*  89 */     if (hostPort != null) {
/*     */       SessionHolder sessionHolder;
/*  91 */       synchronized (this.sessions) {
/*  92 */         sessionHolder = this.sessions.get(hostPort);
/*  93 */         if (sessionHolder == null) {
/*  94 */           return null;
/*     */         }
/*  96 */         if (sessionHolder.isSingleUse())
/*     */         {
/*  98 */           this.sessions.remove(hostPort);
/*     */         }
/*     */       } 
/* 101 */       if (sessionHolder.isValid()) {
/* 102 */         return sessionHolder.sessionBytes();
/*     */       }
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   void removeSession(@Nullable String host, int port) {
/* 109 */     HostPort hostPort = keyFor(host, port);
/* 110 */     if (hostPort != null) {
/* 111 */       synchronized (this.sessions) {
/* 112 */         this.sessions.remove(hostPort);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void setSessionTimeout(int seconds) {
/* 118 */     int oldTimeout = this.sessionTimeout.getAndSet(seconds);
/* 119 */     if (oldTimeout > seconds)
/*     */     {
/*     */       
/* 122 */       clear();
/*     */     }
/*     */   }
/*     */   
/*     */   int getSessionTimeout() {
/* 127 */     return this.sessionTimeout.get();
/*     */   }
/*     */   
/*     */   void setSessionCacheSize(int size) {
/* 131 */     long oldSize = this.maximumCacheSize.getAndSet(size);
/* 132 */     if (oldSize > size || size == 0)
/*     */     {
/* 134 */       clear();
/*     */     }
/*     */   }
/*     */   
/*     */   int getSessionCacheSize() {
/* 139 */     return this.maximumCacheSize.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clear() {
/* 146 */     synchronized (this.sessions) {
/* 147 */       this.sessions.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void expungeInvalidSessions() {
/* 152 */     assert Thread.holdsLock(this.sessions);
/*     */     
/* 154 */     if (this.sessions.isEmpty()) {
/*     */       return;
/*     */     }
/* 157 */     long now = System.currentTimeMillis();
/* 158 */     Iterator<Map.Entry<HostPort, SessionHolder>> iterator = this.sessions.entrySet().iterator();
/* 159 */     while (iterator.hasNext()) {
/* 160 */       SessionHolder sessionHolder = (SessionHolder)((Map.Entry)iterator.next()).getValue();
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (sessionHolder.isValid(now)) {
/*     */         break;
/*     */       }
/* 167 */       iterator.remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static HostPort keyFor(@Nullable String host, int port) {
/* 173 */     if (host == null && port < 1) {
/* 174 */       return null;
/*     */     }
/* 176 */     return new HostPort(host, port);
/*     */   }
/*     */   
/*     */   private static final class SessionHolder {
/*     */     private final long creationTime;
/*     */     private final long timeout;
/*     */     private final byte[] sessionBytes;
/*     */     private final boolean isSingleUse;
/*     */     
/*     */     SessionHolder(long creationTime, long timeout, byte[] session, boolean isSingleUse) {
/* 186 */       this.creationTime = creationTime;
/* 187 */       this.timeout = timeout;
/* 188 */       this.sessionBytes = session;
/* 189 */       this.isSingleUse = isSingleUse;
/*     */     }
/*     */     
/*     */     boolean isValid() {
/* 193 */       return isValid(System.currentTimeMillis());
/*     */     }
/*     */     
/*     */     boolean isValid(long current) {
/* 197 */       return (current <= this.creationTime + this.timeout);
/*     */     }
/*     */     
/*     */     boolean isSingleUse() {
/* 201 */       return this.isSingleUse;
/*     */     }
/*     */     
/*     */     byte[] sessionBytes() {
/* 205 */       return this.sessionBytes;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class HostPort
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */     private final String host;
/*     */     private final int port;
/*     */     
/*     */     HostPort(@Nullable String host, int port) {
/* 218 */       this.host = host;
/* 219 */       this.port = port;
/*     */       
/* 221 */       this.hash = 31 * AsciiString.hashCode(host) + port;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 226 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 231 */       if (!(obj instanceof HostPort)) {
/* 232 */         return false;
/*     */       }
/* 234 */       HostPort other = (HostPort)obj;
/* 235 */       return (this.port == other.port && this.host.equalsIgnoreCase(other.host));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 240 */       return "HostPort{host='" + this.host + '\'' + ", port=" + this.port + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicClientSessionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */