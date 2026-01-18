/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.cache.CachedObject;
/*     */ import com.nimbusds.jose.util.events.EventListener;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
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
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class RefreshAheadCachingJWKSetSource<C extends SecurityContext>
/*     */   extends CachingJWKSetSource<C>
/*     */ {
/*     */   private final long refreshAheadTime;
/*     */   
/*     */   public static class RefreshScheduledEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     public RefreshScheduledEvent(RefreshAheadCachingJWKSetSource<C> source, C context) {
/*  59 */       super(source, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RefreshNotScheduledEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     public RefreshNotScheduledEvent(RefreshAheadCachingJWKSetSource<C> source, C context) {
/*  70 */       super(source, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ScheduledRefreshFailed<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     private final Exception exception;
/*     */ 
/*     */ 
/*     */     
/*     */     public ScheduledRefreshFailed(CachingJWKSetSource<C> source, Exception exception, C context) {
/*  85 */       super(source, context);
/*  86 */       Objects.requireNonNull(exception);
/*  87 */       this.exception = exception;
/*     */     }
/*     */ 
/*     */     
/*     */     public Exception getException() {
/*  92 */       return this.exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ScheduledRefreshInitiatedEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     private ScheduledRefreshInitiatedEvent(CachingJWKSetSource<C> source, C context) {
/* 103 */       super(source, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ScheduledRefreshCompletedEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     private final JWKSet jwkSet;
/*     */ 
/*     */ 
/*     */     
/*     */     private ScheduledRefreshCompletedEvent(CachingJWKSetSource<C> source, JWKSet jwkSet, C context) {
/* 118 */       super(source, context);
/* 119 */       Objects.requireNonNull(jwkSet);
/* 120 */       this.jwkSet = jwkSet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JWKSet getJWKSet() {
/* 130 */       return this.jwkSet;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UnableToRefreshAheadOfExpirationEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<CachingJWKSetSource<C>, C>
/*     */   {
/*     */     public UnableToRefreshAheadOfExpirationEvent(CachingJWKSetSource<C> source, C context) {
/* 142 */       super(source, context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExecutorService createDefaultExecutorService() {
/* 152 */     return Executors.newSingleThreadExecutor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScheduledExecutorService createDefaultScheduledExecutorService() {
/* 162 */     return Executors.newSingleThreadScheduledExecutor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   private final ReentrantLock lazyLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ExecutorService executorService;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean shutdownExecutorOnClose;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService scheduledExecutorService;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean shutdownScheduledExecutorOnClose;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long cacheExpiration;
/*     */ 
/*     */ 
/*     */   
/*     */   private ScheduledFuture<?> scheduledRefreshFuture;
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventListener<CachingJWKSetSource<C>, C> eventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RefreshAheadCachingJWKSetSource(JWKSetSource<C> source, long timeToLive, long cacheRefreshTimeout, long refreshAheadTime, boolean scheduled, EventListener<CachingJWKSetSource<C>, C> eventListener) {
/* 207 */     this(source, timeToLive, cacheRefreshTimeout, refreshAheadTime, 
/* 208 */         createDefaultExecutorService(), true, eventListener, 
/* 209 */         scheduled ? createDefaultScheduledExecutorService() : null, scheduled);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RefreshAheadCachingJWKSetSource(JWKSetSource<C> source, long timeToLive, long cacheRefreshTimeout, long refreshAheadTime, boolean scheduled, ExecutorService executorService, boolean shutdownExecutorOnClose, EventListener<CachingJWKSetSource<C>, C> eventListener) {
/* 244 */     this(source, timeToLive, cacheRefreshTimeout, refreshAheadTime, executorService, shutdownExecutorOnClose, eventListener, scheduled ? createDefaultScheduledExecutorService() : null, scheduled);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RefreshAheadCachingJWKSetSource(JWKSetSource<C> source, long timeToLive, long cacheRefreshTimeout, long refreshAheadTime, ExecutorService executorService, boolean shutdownExecutorOnClose, EventListener<CachingJWKSetSource<C>, C> eventListener, ScheduledExecutorService scheduledExecutorService, boolean shutdownScheduledExecutorOnClose) {
/* 300 */     super(source, timeToLive, cacheRefreshTimeout, eventListener);
/*     */     
/* 302 */     if (refreshAheadTime + cacheRefreshTimeout > timeToLive) {
/* 303 */       throw new IllegalArgumentException("The sum of the refresh-ahead time (" + refreshAheadTime + "ms) and the cache refresh timeout (" + cacheRefreshTimeout + "ms) must not exceed the time-to-lived time (" + timeToLive + "ms)");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 308 */     this.refreshAheadTime = refreshAheadTime;
/*     */     
/* 310 */     Objects.requireNonNull(executorService, "The executor service must not be null");
/* 311 */     this.executorService = executorService;
/*     */     
/* 313 */     this.shutdownExecutorOnClose = shutdownExecutorOnClose;
/* 314 */     this.shutdownScheduledExecutorOnClose = shutdownScheduledExecutorOnClose;
/*     */     
/* 316 */     this.scheduledExecutorService = scheduledExecutorService;
/*     */     
/* 318 */     this.eventListener = eventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/* 324 */     CachedObject<JWKSet> cache = getCachedJWKSet();
/* 325 */     if (cache == null) {
/* 326 */       return loadJWKSetBlocking(JWKSetCacheRefreshEvaluator.noRefresh(), currentTime, context);
/*     */     }
/*     */     
/* 329 */     JWKSet jwkSet = (JWKSet)cache.get();
/* 330 */     if (refreshEvaluator.requiresRefresh(jwkSet)) {
/* 331 */       return loadJWKSetBlocking(refreshEvaluator, currentTime, context);
/*     */     }
/*     */     
/* 334 */     if (cache.isExpired(currentTime)) {
/* 335 */       return loadJWKSetBlocking(JWKSetCacheRefreshEvaluator.referenceComparison(jwkSet), currentTime, context);
/*     */     }
/*     */     
/* 338 */     refreshAheadOfExpiration(cache, false, currentTime, context);
/*     */     
/* 340 */     return (JWKSet)cache.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CachedObject<JWKSet> loadJWKSetNotThreadSafe(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/* 347 */     CachedObject<JWKSet> cache = super.loadJWKSetNotThreadSafe(refreshEvaluator, currentTime, context);
/*     */     
/* 349 */     if (this.scheduledExecutorService != null) {
/* 350 */       scheduleRefreshAheadOfExpiration(cache, currentTime, context);
/*     */     }
/*     */     
/* 353 */     return cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void scheduleRefreshAheadOfExpiration(final CachedObject<JWKSet> cache, long currentTime, final C context) {
/* 362 */     if (this.scheduledRefreshFuture != null) {
/* 363 */       this.scheduledRefreshFuture.cancel(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 368 */     long delay = cache.getExpirationTime() - currentTime - this.refreshAheadTime - getCacheRefreshTimeout();
/* 369 */     if (delay > 0L) {
/* 370 */       final RefreshAheadCachingJWKSetSource<C> that = this;
/* 371 */       Runnable command = new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*     */             try {
/* 377 */               RefreshAheadCachingJWKSetSource.this.refreshAheadOfExpiration(cache, true, System.currentTimeMillis(), context);
/* 378 */             } catch (Exception e) {
/* 379 */               if (RefreshAheadCachingJWKSetSource.this.eventListener != null) {
/* 380 */                 RefreshAheadCachingJWKSetSource.this.eventListener.notify(new RefreshAheadCachingJWKSetSource.ScheduledRefreshFailed<>(that, e, context));
/*     */               }
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 386 */       this.scheduledRefreshFuture = this.scheduledExecutorService.schedule(command, delay, TimeUnit.MILLISECONDS);
/*     */       
/* 388 */       if (this.eventListener != null) {
/* 389 */         this.eventListener.notify(new RefreshScheduledEvent<>(this, context));
/*     */       
/*     */       }
/*     */     }
/* 393 */     else if (this.eventListener != null) {
/* 394 */       this.eventListener.notify(new RefreshNotScheduledEvent<>(this, context));
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
/*     */   void refreshAheadOfExpiration(CachedObject<JWKSet> cache, boolean forceRefresh, long currentTime, C context) {
/* 410 */     if (cache.isExpired(currentTime + this.refreshAheadTime) || forceRefresh)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 415 */       if (this.cacheExpiration < cache.getExpirationTime())
/*     */       {
/* 417 */         if (this.lazyLock.tryLock()) {
/*     */           try {
/* 419 */             lockedRefresh(cache, currentTime, context);
/*     */           } finally {
/* 421 */             this.lazyLock.unlock();
/*     */           } 
/*     */         }
/*     */       }
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
/*     */   void lockedRefresh(CachedObject<JWKSet> cache, final long currentTime, final C context) {
/* 438 */     if (this.cacheExpiration < cache.getExpirationTime()) {
/*     */ 
/*     */       
/* 441 */       this.cacheExpiration = cache.getExpirationTime();
/*     */       
/* 443 */       final RefreshAheadCachingJWKSetSource<C> that = this;
/*     */       
/* 445 */       Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/* 450 */               if (RefreshAheadCachingJWKSetSource.this.eventListener != null) {
/* 451 */                 RefreshAheadCachingJWKSetSource.this.eventListener.notify(new RefreshAheadCachingJWKSetSource.ScheduledRefreshInitiatedEvent<>(that, context));
/*     */               }
/*     */               
/* 454 */               JWKSet jwkSet = RefreshAheadCachingJWKSetSource.this.loadJWKSetBlocking(JWKSetCacheRefreshEvaluator.forceRefresh(), currentTime, context);
/*     */               
/* 456 */               if (RefreshAheadCachingJWKSetSource.this.eventListener != null) {
/* 457 */                 RefreshAheadCachingJWKSetSource.this.eventListener.notify(new RefreshAheadCachingJWKSetSource.ScheduledRefreshCompletedEvent<>(that, jwkSet, context));
/*     */               
/*     */               }
/*     */             }
/* 461 */             catch (Throwable e) {
/*     */               
/* 463 */               RefreshAheadCachingJWKSetSource.this.cacheExpiration = -1L;
/*     */ 
/*     */               
/* 466 */               if (RefreshAheadCachingJWKSetSource.this.eventListener != null) {
/* 467 */                 RefreshAheadCachingJWKSetSource.this.eventListener.notify(new RefreshAheadCachingJWKSetSource.UnableToRefreshAheadOfExpirationEvent<>(that, context));
/*     */               }
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 473 */       this.executorService.execute(runnable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExecutorService getExecutorService() {
/* 484 */     return this.executorService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledExecutorService getScheduledExecutorService() {
/* 494 */     return this.scheduledExecutorService;
/*     */   }
/*     */ 
/*     */   
/*     */   ReentrantLock getLazyLock() {
/* 499 */     return this.lazyLock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFuture<?> getScheduledRefreshFuture() {
/* 509 */     return this.scheduledRefreshFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 516 */     ScheduledFuture<?> currentScheduledRefreshFuture = this.scheduledRefreshFuture;
/* 517 */     if (currentScheduledRefreshFuture != null) {
/* 518 */       currentScheduledRefreshFuture.cancel(true);
/*     */     }
/*     */     
/* 521 */     super.close();
/*     */     
/* 523 */     if (this.shutdownExecutorOnClose) {
/* 524 */       this.executorService.shutdownNow();
/*     */       try {
/* 526 */         this.executorService.awaitTermination(getCacheRefreshTimeout(), TimeUnit.MILLISECONDS);
/* 527 */       } catch (InterruptedException e) {
/*     */         
/* 529 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     } 
/* 532 */     if (this.scheduledExecutorService != null && this.shutdownScheduledExecutorOnClose) {
/* 533 */       this.scheduledExecutorService.shutdownNow();
/*     */       try {
/* 535 */         this.scheduledExecutorService.awaitTermination(getCacheRefreshTimeout(), TimeUnit.MILLISECONDS);
/* 536 */       } catch (InterruptedException e) {
/*     */         
/* 538 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\RefreshAheadCachingJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */