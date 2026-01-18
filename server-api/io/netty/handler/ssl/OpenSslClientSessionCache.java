/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.util.AsciiString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OpenSslClientSessionCache
/*     */   extends OpenSslSessionCache
/*     */ {
/*  32 */   private final Map<HostPort, Set<OpenSslSessionCache.NativeSslSession>> sessions = new HashMap<>();
/*     */   
/*     */   OpenSslClientSessionCache(Map<Long, ReferenceCountedOpenSslEngine> engines) {
/*  35 */     super(engines);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean sessionCreated(OpenSslSessionCache.NativeSslSession session) {
/*  40 */     assert Thread.holdsLock(this);
/*  41 */     HostPort hostPort = keyFor(session.getPeerHost(), session.getPeerPort());
/*  42 */     if (hostPort == null) {
/*  43 */       return false;
/*     */     }
/*  45 */     Set<OpenSslSessionCache.NativeSslSession> sessionsForHost = this.sessions.get(hostPort);
/*  46 */     if (sessionsForHost == null) {
/*     */ 
/*     */       
/*  49 */       sessionsForHost = new HashSet<>(4);
/*  50 */       this.sessions.put(hostPort, sessionsForHost);
/*     */     } 
/*  52 */     sessionsForHost.add(session);
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sessionRemoved(OpenSslSessionCache.NativeSslSession session) {
/*  58 */     assert Thread.holdsLock(this);
/*  59 */     HostPort hostPort = keyFor(session.getPeerHost(), session.getPeerPort());
/*  60 */     if (hostPort == null) {
/*     */       return;
/*     */     }
/*  63 */     Set<OpenSslSessionCache.NativeSslSession> sessionsForHost = this.sessions.get(hostPort);
/*  64 */     if (sessionsForHost != null) {
/*  65 */       sessionsForHost.remove(session);
/*  66 */       if (sessionsForHost.isEmpty()) {
/*  67 */         this.sessions.remove(hostPort);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean setSession(long ssl, OpenSslInternalSession session, String host, int port) {
/*     */     boolean reused;
/*  74 */     HostPort hostPort = keyFor(host, port);
/*  75 */     if (hostPort == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     OpenSslSessionCache.NativeSslSession nativeSslSession = null;
/*     */     
/*  80 */     boolean singleUsed = false;
/*  81 */     synchronized (this) {
/*  82 */       Set<OpenSslSessionCache.NativeSslSession> sessionsForHost = this.sessions.get(hostPort);
/*  83 */       if (sessionsForHost == null) {
/*  84 */         return false;
/*     */       }
/*  86 */       if (sessionsForHost.isEmpty()) {
/*  87 */         this.sessions.remove(hostPort);
/*     */         
/*  89 */         return false;
/*     */       } 
/*     */       
/*  92 */       List<OpenSslSessionCache.NativeSslSession> toBeRemoved = null;
/*     */       
/*  94 */       for (OpenSslSessionCache.NativeSslSession sslSession : sessionsForHost) {
/*  95 */         if (sslSession.isValid()) {
/*  96 */           nativeSslSession = sslSession;
/*     */           break;
/*     */         } 
/*  99 */         if (toBeRemoved == null) {
/* 100 */           toBeRemoved = new ArrayList<>(2);
/*     */         }
/* 102 */         toBeRemoved.add(sslSession);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       if (toBeRemoved != null) {
/* 108 */         for (OpenSslSessionCache.NativeSslSession sslSession : toBeRemoved) {
/* 109 */           removeSessionWithId(sslSession.sessionId());
/*     */         }
/*     */       }
/* 112 */       if (nativeSslSession == null)
/*     */       {
/* 114 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 119 */       reused = SSL.setSession(ssl, nativeSslSession.session());
/* 120 */       if (reused) {
/* 121 */         singleUsed = nativeSslSession.shouldBeSingleUse();
/*     */       }
/*     */     } 
/*     */     
/* 125 */     if (reused) {
/* 126 */       if (singleUsed) {
/*     */         
/* 128 */         nativeSslSession.invalidate();
/* 129 */         session.invalidate();
/*     */       } 
/* 131 */       nativeSslSession.setLastAccessedTime(System.currentTimeMillis());
/* 132 */       session.setSessionDetails(nativeSslSession.getCreationTime(), nativeSslSession.getLastAccessedTime(), nativeSslSession
/* 133 */           .sessionId(), nativeSslSession.keyValueStorage);
/*     */     } 
/* 135 */     return reused;
/*     */   }
/*     */   
/*     */   private static HostPort keyFor(String host, int port) {
/* 139 */     if (host == null && port < 1) {
/* 140 */       return null;
/*     */     }
/* 142 */     return new HostPort(host, port);
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void clear() {
/* 147 */     super.clear();
/* 148 */     this.sessions.clear();
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
/*     */     HostPort(String host, int port) {
/* 160 */       this.host = host;
/* 161 */       this.port = port;
/*     */       
/* 163 */       this.hash = 31 * AsciiString.hashCode(host) + port;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 168 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 173 */       if (!(obj instanceof HostPort)) {
/* 174 */         return false;
/*     */       }
/* 176 */       HostPort other = (HostPort)obj;
/* 177 */       return (this.port == other.port && this.host.equalsIgnoreCase(other.host));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 182 */       return "HostPort{host='" + this.host + '\'' + ", port=" + this.port + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslClientSessionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */