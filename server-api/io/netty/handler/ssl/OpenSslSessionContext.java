/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.internal.tcnative.SSLContext;
/*     */ import io.netty.internal.tcnative.SessionTicketKey;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import javax.net.ssl.SSLSession;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OpenSslSessionContext
/*     */   implements SSLSessionContext
/*     */ {
/*     */   private final OpenSslSessionStats stats;
/*     */   private final OpenSslKeyMaterialProvider provider;
/*     */   final ReferenceCountedOpenSslContext context;
/*     */   private final OpenSslSessionCache sessionCache;
/*     */   private final long mask;
/*     */   
/*     */   OpenSslSessionContext(ReferenceCountedOpenSslContext context, OpenSslKeyMaterialProvider provider, long mask, OpenSslSessionCache cache) {
/*  53 */     this.context = context;
/*  54 */     this.provider = provider;
/*  55 */     this.mask = mask;
/*  56 */     this.stats = new OpenSslSessionStats(context);
/*  57 */     this.sessionCache = cache;
/*  58 */     SSLContext.setSSLSessionCache(context.ctx, cache);
/*     */   }
/*     */   
/*     */   final boolean useKeyManager() {
/*  62 */     return (this.provider != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSessionCacheSize(int size) {
/*  67 */     ObjectUtil.checkPositiveOrZero(size, "size");
/*  68 */     this.sessionCache.setSessionCacheSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSessionCacheSize() {
/*  73 */     return this.sessionCache.getSessionCacheSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSessionTimeout(int seconds) {
/*  78 */     ObjectUtil.checkPositiveOrZero(seconds, "seconds");
/*     */     
/*  80 */     Lock writerLock = this.context.ctxLock.writeLock();
/*  81 */     writerLock.lock();
/*     */     try {
/*  83 */       SSLContext.setSessionCacheTimeout(this.context.ctx, seconds);
/*  84 */       this.sessionCache.setSessionTimeout(seconds);
/*     */     } finally {
/*  86 */       writerLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSessionTimeout() {
/*  92 */     return this.sessionCache.getSessionTimeout();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getSession(byte[] bytes) {
/*  97 */     return this.sessionCache.getSession(new OpenSslSessionId(bytes));
/*     */   }
/*     */ 
/*     */   
/*     */   public Enumeration<byte[]> getIds() {
/* 102 */     return new Enumeration<byte[]>() {
/* 103 */         private final Iterator<OpenSslSessionId> ids = OpenSslSessionContext.this.sessionCache.getIds().iterator();
/*     */         
/*     */         public boolean hasMoreElements() {
/* 106 */           return this.ids.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public byte[] nextElement() {
/* 111 */           return ((OpenSslSessionId)this.ids.next()).cloneBytes();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setTicketKeys(byte[] keys) {
/* 122 */     if (keys.length % 48 != 0) {
/* 123 */       throw new IllegalArgumentException("keys.length % 48 != 0");
/*     */     }
/* 125 */     SessionTicketKey[] tickets = new SessionTicketKey[keys.length / 48];
/* 126 */     for (int i = 0, a = 0; i < tickets.length; i++) {
/* 127 */       byte[] name = Arrays.copyOfRange(keys, a, 16);
/* 128 */       a += 16;
/* 129 */       byte[] hmacKey = Arrays.copyOfRange(keys, a, 16);
/* 130 */       i += 16;
/* 131 */       byte[] aesKey = Arrays.copyOfRange(keys, a, 16);
/* 132 */       a += 16;
/* 133 */       tickets[i] = new SessionTicketKey(name, hmacKey, aesKey);
/*     */     } 
/* 135 */     Lock writerLock = this.context.ctxLock.writeLock();
/* 136 */     writerLock.lock();
/*     */     try {
/* 138 */       SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
/* 139 */       SSLContext.setSessionTicketKeys(this.context.ctx, tickets);
/*     */     } finally {
/* 141 */       writerLock.unlock();
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
/*     */   public void setTicketKeys(OpenSslSessionTicketKey... keys) {
/* 153 */     ObjectUtil.checkNotNull(keys, "keys");
/* 154 */     SessionTicketKey[] ticketKeys = new SessionTicketKey[keys.length];
/* 155 */     for (int i = 0; i < ticketKeys.length; i++) {
/* 156 */       ticketKeys[i] = (keys[i]).key;
/*     */     }
/* 158 */     Lock writerLock = this.context.ctxLock.writeLock();
/* 159 */     writerLock.lock();
/*     */     try {
/* 161 */       SSLContext.clearOptions(this.context.ctx, SSL.SSL_OP_NO_TICKET);
/* 162 */       if (ticketKeys.length > 0) {
/* 163 */         SSLContext.setSessionTicketKeys(this.context.ctx, ticketKeys);
/*     */       }
/*     */     } finally {
/* 166 */       writerLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionCacheEnabled(boolean enabled) {
/* 175 */     long mode = enabled ? (this.mask | SSL.SSL_SESS_CACHE_NO_INTERNAL_LOOKUP | SSL.SSL_SESS_CACHE_NO_INTERNAL_STORE) : SSL.SSL_SESS_CACHE_OFF;
/* 176 */     Lock writerLock = this.context.ctxLock.writeLock();
/* 177 */     writerLock.lock();
/*     */     try {
/* 179 */       SSLContext.setSessionCacheMode(this.context.ctx, mode);
/* 180 */       if (!enabled) {
/* 181 */         this.sessionCache.clear();
/*     */       }
/*     */     } finally {
/* 184 */       writerLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSessionCacheEnabled() {
/* 192 */     Lock readerLock = this.context.ctxLock.readLock();
/* 193 */     readerLock.lock();
/*     */     try {
/* 195 */       return ((SSLContext.getSessionCacheMode(this.context.ctx) & this.mask) != 0L);
/*     */     } finally {
/* 197 */       readerLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenSslSessionStats stats() {
/* 205 */     return this.stats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void removeFromCache(OpenSslSessionId id) {
/* 212 */     this.sessionCache.removeSessionWithId(id);
/*     */   }
/*     */   
/*     */   final boolean isInCache(OpenSslSessionId id) {
/* 216 */     return this.sessionCache.containsSessionWithId(id);
/*     */   }
/*     */   
/*     */   boolean setSessionFromCache(long ssl, OpenSslInternalSession session, String host, int port) {
/* 220 */     return this.sessionCache.setSession(ssl, session, host, port);
/*     */   }
/*     */   
/*     */   final void destroy() {
/* 224 */     if (this.provider != null) {
/* 225 */       this.provider.destroy();
/*     */     }
/* 227 */     this.sessionCache.clear();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslSessionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */