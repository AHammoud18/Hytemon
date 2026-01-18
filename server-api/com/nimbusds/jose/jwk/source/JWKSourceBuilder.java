/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.util.DefaultResourceRetriever;
/*     */ import com.nimbusds.jose.util.ResourceRetriever;
/*     */ import com.nimbusds.jose.util.events.EventListener;
/*     */ import com.nimbusds.jose.util.health.HealthReportListener;
/*     */ import java.net.URL;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JWKSourceBuilder<C extends SecurityContext>
/*     */ {
/*     */   public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 500;
/*     */   public static final int DEFAULT_HTTP_READ_TIMEOUT = 500;
/*     */   public static final int DEFAULT_HTTP_SIZE_LIMIT = 51200;
/*     */   public static final long DEFAULT_CACHE_TIME_TO_LIVE = 300000L;
/*     */   public static final long DEFAULT_CACHE_REFRESH_TIMEOUT = 15000L;
/*     */   public static final long DEFAULT_REFRESH_AHEAD_TIME = 30000L;
/*     */   public static final long DEFAULT_RATE_LIMIT_MIN_INTERVAL = 30000L;
/*     */   private final JWKSetSource<C> jwkSetSource;
/*     */   
/*     */   public static <C extends SecurityContext> JWKSourceBuilder<C> create(URL jwkSetURL) {
/* 114 */     DefaultResourceRetriever retriever = new DefaultResourceRetriever(500, 500, 51200);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     JWKSetSource<C> jwkSetSource = new URLBasedJWKSetSource<>(jwkSetURL, (ResourceRetriever)retriever);
/*     */     
/* 121 */     return new JWKSourceBuilder<>(jwkSetSource);
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
/*     */   public static <C extends SecurityContext> JWKSourceBuilder<C> create(URL jwkSetURL, ResourceRetriever retriever) {
/* 133 */     return new JWKSourceBuilder<>(new URLBasedJWKSetSource<>(jwkSetURL, retriever));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <C extends SecurityContext> JWKSourceBuilder<C> create(JWKSetSource<C> source) {
/* 143 */     return new JWKSourceBuilder<>(source);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean caching = true;
/*     */ 
/*     */   
/* 151 */   private long cacheTimeToLive = 300000L;
/* 152 */   private long cacheRefreshTimeout = 15000L;
/*     */   
/*     */   private EventListener<CachingJWKSetSource<C>, C> cachingEventListener;
/*     */   private boolean refreshAhead = true;
/* 156 */   private long refreshAheadTime = 30000L;
/*     */   
/*     */   private boolean refreshAheadScheduled = false;
/*     */   
/*     */   private ExecutorService executorService;
/*     */   private boolean shutdownExecutorOnClose = true;
/*     */   private ScheduledExecutorService scheduledExecutorService;
/*     */   private boolean shutdownScheduledExecutorOnClose = true;
/*     */   private boolean rateLimited = true;
/* 165 */   private long minTimeInterval = 30000L;
/*     */   
/*     */   private EventListener<RateLimitedJWKSetSource<C>, C> rateLimitedEventListener;
/*     */   
/*     */   private boolean retrying = false;
/*     */   
/*     */   private EventListener<RetryingJWKSetSource<C>, C> retryingEventListener;
/*     */   
/*     */   private boolean outageTolerant = false;
/* 174 */   private long outageCacheTimeToLive = -1L;
/*     */ 
/*     */ 
/*     */   
/*     */   private EventListener<OutageTolerantJWKSetSource<C>, C> outageEventListener;
/*     */ 
/*     */ 
/*     */   
/*     */   private HealthReportListener<JWKSetSourceWithHealthStatusReporting<C>, C> healthReportListener;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JWKSource<C> failover;
/*     */ 
/*     */ 
/*     */   
/*     */   private JWKSourceBuilder(JWKSetSource<C> jwkSetSource) {
/* 191 */     Objects.requireNonNull(jwkSetSource);
/* 192 */     this.jwkSetSource = jwkSetSource;
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
/*     */   public JWKSourceBuilder<C> cache(boolean enable) {
/* 204 */     this.caching = enable;
/* 205 */     return this;
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
/*     */   public JWKSourceBuilder<C> cache(long timeToLive, long cacheRefreshTimeout) {
/* 220 */     this.caching = true;
/* 221 */     this.cacheTimeToLive = timeToLive;
/* 222 */     this.cacheRefreshTimeout = cacheRefreshTimeout;
/* 223 */     return this;
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
/*     */   public JWKSourceBuilder<C> cache(long timeToLive, long cacheRefreshTimeout, EventListener<CachingJWKSetSource<C>, C> eventListener) {
/* 242 */     this.caching = true;
/* 243 */     this.cacheTimeToLive = timeToLive;
/* 244 */     this.cacheRefreshTimeout = cacheRefreshTimeout;
/* 245 */     this.cachingEventListener = eventListener;
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSourceBuilder<C> cacheForever() {
/* 256 */     this.caching = true;
/* 257 */     this.cacheTimeToLive = Long.MAX_VALUE;
/* 258 */     this.refreshAhead = false;
/* 259 */     return this;
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
/*     */   public JWKSourceBuilder<C> refreshAheadCache(boolean enable) {
/* 272 */     if (enable) {
/* 273 */       this.caching = true;
/*     */     }
/* 275 */     this.refreshAhead = enable;
/* 276 */     return this;
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
/*     */   public JWKSourceBuilder<C> refreshAheadCache(long refreshAheadTime, boolean scheduled) {
/* 290 */     this.caching = true;
/* 291 */     this.refreshAhead = true;
/* 292 */     this.refreshAheadTime = refreshAheadTime;
/* 293 */     this.refreshAheadScheduled = scheduled;
/* 294 */     return this;
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
/*     */   public JWKSourceBuilder<C> refreshAheadCache(long refreshAheadTime, boolean scheduled, EventListener<CachingJWKSetSource<C>, C> eventListener) {
/* 312 */     this.caching = true;
/* 313 */     this.refreshAhead = true;
/* 314 */     this.refreshAheadTime = refreshAheadTime;
/* 315 */     this.refreshAheadScheduled = scheduled;
/* 316 */     this.cachingEventListener = eventListener;
/* 317 */     return this;
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
/*     */   public JWKSourceBuilder<C> refreshAheadCache(long refreshAheadTime, EventListener<CachingJWKSetSource<C>, C> eventListener, ExecutorService executorService, boolean shutdownExecutorOnClose, ScheduledExecutorService scheduledExecutorService, boolean shutdownScheduledExecutorOnClose) {
/* 350 */     this.caching = true;
/* 351 */     this.refreshAhead = true;
/* 352 */     this.refreshAheadTime = refreshAheadTime;
/* 353 */     this.refreshAheadScheduled = (scheduledExecutorService != null);
/* 354 */     this.cachingEventListener = eventListener;
/* 355 */     this.executorService = executorService;
/* 356 */     this.shutdownExecutorOnClose = shutdownExecutorOnClose;
/* 357 */     this.scheduledExecutorService = scheduledExecutorService;
/* 358 */     this.shutdownScheduledExecutorOnClose = shutdownScheduledExecutorOnClose;
/* 359 */     return this;
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
/*     */   public JWKSourceBuilder<C> rateLimited(boolean enable) {
/* 371 */     this.rateLimited = enable;
/* 372 */     return this;
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
/*     */   public JWKSourceBuilder<C> rateLimited(long minTimeInterval) {
/* 385 */     this.rateLimited = true;
/* 386 */     this.minTimeInterval = minTimeInterval;
/* 387 */     return this;
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
/*     */   public JWKSourceBuilder<C> rateLimited(long minTimeInterval, EventListener<RateLimitedJWKSetSource<C>, C> eventListener) {
/* 403 */     this.rateLimited = true;
/* 404 */     this.minTimeInterval = minTimeInterval;
/* 405 */     this.rateLimitedEventListener = eventListener;
/* 406 */     return this;
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
/*     */   public JWKSourceBuilder<C> failover(JWKSource<C> failover) {
/* 418 */     this.failover = failover;
/* 419 */     return this;
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
/*     */   public JWKSourceBuilder<C> retrying(boolean enable) {
/* 432 */     this.retrying = enable;
/* 433 */     return this;
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
/*     */   public JWKSourceBuilder<C> retrying(EventListener<RetryingJWKSetSource<C>, C> eventListener) {
/* 447 */     this.retrying = true;
/* 448 */     this.retryingEventListener = eventListener;
/* 449 */     return this;
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
/*     */   public JWKSourceBuilder<C> healthReporting(HealthReportListener<JWKSetSourceWithHealthStatusReporting<C>, C> listener) {
/* 462 */     this.healthReportListener = listener;
/* 463 */     return this;
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
/*     */   public JWKSourceBuilder<C> outageTolerant(boolean enable) {
/* 476 */     this.outageTolerant = enable;
/* 477 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSourceBuilder<C> outageTolerantForever() {
/* 488 */     this.outageTolerant = true;
/* 489 */     this.outageCacheTimeToLive = Long.MAX_VALUE;
/* 490 */     return this;
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
/*     */   public JWKSourceBuilder<C> outageTolerant(long timeToLive) {
/* 504 */     this.outageTolerant = true;
/* 505 */     this.outageCacheTimeToLive = timeToLive;
/* 506 */     return this;
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
/*     */   public JWKSourceBuilder<C> outageTolerant(long timeToLive, EventListener<OutageTolerantJWKSetSource<C>, C> eventListener) {
/* 523 */     this.outageTolerant = true;
/* 524 */     this.outageCacheTimeToLive = timeToLive;
/* 525 */     this.outageEventListener = eventListener;
/* 526 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSource<C> build() {
/* 537 */     if (!this.caching && this.rateLimited)
/* 538 */       throw new IllegalStateException("Rate limiting requires caching"); 
/* 539 */     if (!this.caching && this.refreshAhead) {
/* 540 */       throw new IllegalStateException("Refresh-ahead caching requires general caching");
/*     */     }
/*     */     
/* 543 */     if (this.caching && this.rateLimited && this.cacheTimeToLive <= this.minTimeInterval) {
/* 544 */       throw new IllegalStateException("The rate limiting min time interval between requests must be less than the cache time-to-live");
/*     */     }
/*     */     
/* 547 */     if (this.caching && this.outageTolerant && this.cacheTimeToLive == Long.MAX_VALUE && this.outageCacheTimeToLive == Long.MAX_VALUE)
/*     */     {
/* 549 */       throw new IllegalStateException("Outage tolerance not necessary with a non-expiring cache");
/*     */     }
/*     */     
/* 552 */     if (this.caching && this.refreshAhead && this.cacheTimeToLive == Long.MAX_VALUE)
/*     */     {
/* 554 */       throw new IllegalStateException("Refresh-ahead caching not necessary with a non-expiring cache");
/*     */     }
/*     */     
/* 557 */     JWKSetSource<C> source = this.jwkSetSource;
/*     */     
/* 559 */     if (this.retrying) {
/* 560 */       source = new RetryingJWKSetSource<>(source, this.retryingEventListener);
/*     */     }
/*     */     
/* 563 */     if (this.outageTolerant) {
/* 564 */       if (this.outageCacheTimeToLive == -1L) {
/* 565 */         if (this.caching) {
/* 566 */           this.outageCacheTimeToLive = this.cacheTimeToLive * 10L;
/*     */         } else {
/* 568 */           this.outageCacheTimeToLive = 3000000L;
/*     */         } 
/*     */       }
/* 571 */       source = new OutageTolerantJWKSetSource<>(source, this.outageCacheTimeToLive, this.outageEventListener);
/*     */     } 
/*     */     
/* 574 */     if (this.healthReportListener != null) {
/* 575 */       source = new JWKSetSourceWithHealthStatusReporting<>(source, this.healthReportListener);
/*     */     }
/*     */     
/* 578 */     if (this.rateLimited) {
/* 579 */       source = new RateLimitedJWKSetSource<>(source, this.minTimeInterval, this.rateLimitedEventListener);
/*     */     }
/*     */     
/* 582 */     if (this.refreshAhead) {
/* 583 */       if (this.refreshAheadScheduled && 
/* 584 */         this.scheduledExecutorService == null) {
/* 585 */         this.scheduledExecutorService = RefreshAheadCachingJWKSetSource.createDefaultScheduledExecutorService();
/*     */       }
/*     */       
/* 588 */       if (this.executorService == null) {
/* 589 */         this.executorService = RefreshAheadCachingJWKSetSource.createDefaultExecutorService();
/*     */       }
/* 591 */       source = new RefreshAheadCachingJWKSetSource<>(source, this.cacheTimeToLive, this.cacheRefreshTimeout, this.refreshAheadTime, this.executorService, this.shutdownExecutorOnClose, this.cachingEventListener, this.scheduledExecutorService, this.shutdownScheduledExecutorOnClose);
/* 592 */     } else if (this.caching) {
/* 593 */       source = new CachingJWKSetSource<>(source, this.cacheTimeToLive, this.cacheRefreshTimeout, this.cachingEventListener);
/*     */     } 
/*     */     
/* 596 */     JWKSource<C> jwkSource = new JWKSetBasedJWKSource<>(source);
/* 597 */     if (this.failover != null) {
/* 598 */       return new JWKSourceWithFailover<>(jwkSource, this.failover);
/*     */     }
/* 600 */     return jwkSource;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSourceBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */