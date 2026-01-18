/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.events.EventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class RateLimitedJWKSetSource<C extends SecurityContext>
/*     */   extends JWKSetSourceWrapper<C>
/*     */ {
/*     */   private final long minTimeInterval;
/*     */   
/*     */   public static class RateLimitedEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<RateLimitedJWKSetSource<C>, C>
/*     */   {
/*     */     private RateLimitedEvent(RateLimitedJWKSetSource<C> source, C securityContext) {
/*  51 */       super(source, securityContext);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  57 */   private long nextOpeningTime = -1L;
/*  58 */   private int counter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventListener<RateLimitedJWKSetSource<C>, C> eventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RateLimitedJWKSetSource(JWKSetSource<C> source, long minTimeInterval, EventListener<RateLimitedJWKSetSource<C>, C> eventListener) {
/*  75 */     super(source);
/*  76 */     this.minTimeInterval = minTimeInterval;
/*  77 */     this.eventListener = eventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*     */     boolean rateLimitHit;
/*  89 */     synchronized (this) {
/*  90 */       if (this.nextOpeningTime <= currentTime) {
/*  91 */         this.nextOpeningTime = currentTime + this.minTimeInterval;
/*  92 */         this.counter = 1;
/*  93 */         rateLimitHit = false;
/*     */       } else {
/*  95 */         rateLimitHit = (this.counter <= 0);
/*  96 */         if (!rateLimitHit) {
/*  97 */           this.counter--;
/*     */         }
/*     */       } 
/*     */     } 
/* 101 */     if (rateLimitHit) {
/* 102 */       if (this.eventListener != null) {
/* 103 */         this.eventListener.notify(new RateLimitedEvent<>(this, (SecurityContext)context));
/*     */       }
/* 105 */       throw new RateLimitReachedException();
/*     */     } 
/* 107 */     return getSource().getJWKSet(refreshEvaluator, currentTime, context);
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
/*     */   public long getMinTimeInterval() {
/* 119 */     return this.minTimeInterval;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\RateLimitedJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */