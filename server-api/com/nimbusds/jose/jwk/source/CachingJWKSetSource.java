/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.cache.CachedObject;
/*     */ import com.nimbusds.jose.util.events.EventListener;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ @ThreadSafe
/*     */ public class CachingJWKSetSource<C extends SecurityContext>
/*     */   extends AbstractCachingJWKSetSource<C>
/*     */ {
/*     */   static class AbstractCachingJWKSetSourceEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     private final int threadQueueLength;
/*     */     
/*     */     public AbstractCachingJWKSetSourceEvent(CachingJWKSetSource<C> source, int threadQueueLength, C context) {
/*  52 */       super(source, context);
/*  53 */       this.threadQueueLength = threadQueueLength;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getThreadQueueLength() {
/*  63 */       return this.threadQueueLength;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RefreshInitiatedEvent<C extends SecurityContext>
/*     */     extends AbstractCachingJWKSetSourceEvent<C>
/*     */   {
/*     */     private RefreshInitiatedEvent(CachingJWKSetSource<C> source, int queueLength, C context) {
/*  74 */       super(source, queueLength, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RefreshCompletedEvent<C extends SecurityContext>
/*     */     extends AbstractCachingJWKSetSourceEvent<C>
/*     */   {
/*     */     private final JWKSet jwkSet;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private RefreshCompletedEvent(CachingJWKSetSource<C> source, JWKSet jwkSet, int queueLength, C context) {
/*  90 */       super(source, queueLength, context);
/*  91 */       Objects.requireNonNull(jwkSet);
/*  92 */       this.jwkSet = jwkSet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JWKSet getJWKSet() {
/* 102 */       return this.jwkSet;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WaitingForRefreshEvent<C extends SecurityContext>
/*     */     extends AbstractCachingJWKSetSourceEvent<C>
/*     */   {
/*     */     private WaitingForRefreshEvent(CachingJWKSetSource<C> source, int queueLength, C context) {
/* 114 */       super(source, queueLength, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UnableToRefreshEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     private UnableToRefreshEvent(CachingJWKSetSource<C> source, C context) {
/* 125 */       super(source, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RefreshTimedOutEvent<C extends SecurityContext>
/*     */     extends AbstractCachingJWKSetSourceEvent<C>
/*     */   {
/*     */     private RefreshTimedOutEvent(CachingJWKSetSource<C> source, int queueLength, C context) {
/* 136 */       super(source, queueLength, context);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 141 */   private final ReentrantLock lock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long cacheRefreshTimeout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventListener<CachingJWKSetSource<C>, C> eventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachingJWKSetSource(JWKSetSource<C> source, long timeToLive, long cacheRefreshTimeout, EventListener<CachingJWKSetSource<C>, C> eventListener) {
/* 164 */     super(source, timeToLive);
/* 165 */     this.cacheRefreshTimeout = cacheRefreshTimeout;
/* 166 */     this.eventListener = eventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/* 172 */     CachedObject<JWKSet> cache = getCachedJWKSet();
/* 173 */     if (cache == null) {
/* 174 */       return loadJWKSetBlocking(JWKSetCacheRefreshEvaluator.noRefresh(), currentTime, context);
/*     */     }
/*     */     
/* 177 */     JWKSet jwkSet = (JWKSet)cache.get();
/* 178 */     if (refreshEvaluator.requiresRefresh(jwkSet)) {
/* 179 */       return loadJWKSetBlocking(refreshEvaluator, currentTime, context);
/*     */     }
/*     */     
/* 182 */     if (cache.isExpired(currentTime)) {
/* 183 */       return loadJWKSetBlocking(JWKSetCacheRefreshEvaluator.referenceComparison(jwkSet), currentTime, context);
/*     */     }
/*     */     
/* 186 */     return (JWKSet)cache.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCacheRefreshTimeout() {
/* 196 */     return this.cacheRefreshTimeout;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JWKSet loadJWKSetBlocking(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*     */     try {
/*     */       CachedObject<JWKSet> cache;
/* 229 */       if (this.lock.tryLock()) {
/*     */ 
/*     */         
/*     */         try {
/* 233 */           CachedObject<JWKSet> cachedJWKSet = getCachedJWKSet();
/* 234 */           if (cachedJWKSet == null || refreshEvaluator.requiresRefresh((JWKSet)cachedJWKSet.get())) {
/*     */             
/* 236 */             if (this.eventListener != null) {
/* 237 */               this.eventListener.notify(new RefreshInitiatedEvent<>(this, this.lock.getQueueLength(), (SecurityContext)context));
/*     */             }
/*     */             
/* 240 */             CachedObject<JWKSet> result = loadJWKSetNotThreadSafe(refreshEvaluator, currentTime, context);
/*     */             
/* 242 */             if (this.eventListener != null) {
/* 243 */               this.eventListener.notify(new RefreshCompletedEvent<>(this, (JWKSet)result.get(), this.lock.getQueueLength(), (SecurityContext)context));
/*     */             }
/*     */             
/* 246 */             cache = result;
/*     */           } else {
/*     */             
/* 249 */             cache = cachedJWKSet;
/*     */           } 
/*     */         } finally {
/*     */           
/* 253 */           this.lock.unlock();
/*     */         } 
/*     */       } else {
/*     */         
/* 257 */         if (this.eventListener != null) {
/* 258 */           this.eventListener.notify(new WaitingForRefreshEvent<>(this, this.lock.getQueueLength(), (SecurityContext)context));
/*     */         }
/*     */         
/* 261 */         if (this.lock.tryLock(getCacheRefreshTimeout(), TimeUnit.MILLISECONDS)) {
/*     */           
/*     */           try {
/* 264 */             CachedObject<JWKSet> cachedJWKSet = getCachedJWKSet();
/* 265 */             if (cachedJWKSet == null || refreshEvaluator.requiresRefresh((JWKSet)cachedJWKSet.get())) {
/*     */ 
/*     */               
/* 268 */               if (this.eventListener != null) {
/* 269 */                 this.eventListener.notify(new RefreshInitiatedEvent<>(this, this.lock.getQueueLength(), (SecurityContext)context));
/*     */               }
/*     */               
/* 272 */               cache = loadJWKSetNotThreadSafe(refreshEvaluator, currentTime, context);
/*     */               
/* 274 */               if (this.eventListener != null) {
/* 275 */                 this.eventListener.notify(new RefreshCompletedEvent<>(this, (JWKSet)cache.get(), this.lock.getQueueLength(), (SecurityContext)context));
/*     */               }
/*     */             } else {
/*     */               
/* 279 */               cache = cachedJWKSet;
/*     */             } 
/*     */           } finally {
/* 282 */             this.lock.unlock();
/*     */           } 
/*     */         } else {
/*     */           
/* 286 */           if (this.eventListener != null) {
/* 287 */             this.eventListener.notify(new RefreshTimedOutEvent<>(this, this.lock.getQueueLength(), (SecurityContext)context));
/*     */           }
/*     */           
/* 290 */           throw new JWKSetUnavailableException("Timeout while waiting for cache refresh (" + this.cacheRefreshTimeout + "ms exceeded)");
/*     */         } 
/*     */       } 
/*     */       
/* 294 */       if (cache != null && cache.isValid(currentTime)) {
/* 295 */         return (JWKSet)cache.get();
/*     */       }
/*     */       
/* 298 */       if (this.eventListener != null) {
/* 299 */         this.eventListener.notify(new UnableToRefreshEvent<>(this, (SecurityContext)context));
/*     */       }
/*     */       
/* 302 */       throw new JWKSetUnavailableException("Unable to refresh cache");
/*     */     }
/* 304 */     catch (InterruptedException e) {
/*     */       
/* 306 */       Thread.currentThread().interrupt();
/*     */       
/* 308 */       throw new JWKSetUnavailableException("Interrupted while waiting for cache refresh", e);
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
/*     */   CachedObject<JWKSet> loadJWKSetNotThreadSafe(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/* 330 */     JWKSet jwkSet = getSource().getJWKSet(refreshEvaluator, currentTime, context);
/*     */     
/* 332 */     return cacheJWKSet(jwkSet, currentTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReentrantLock getLock() {
/* 342 */     return this.lock;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\CachingJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */