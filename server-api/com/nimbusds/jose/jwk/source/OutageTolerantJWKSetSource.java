/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.KeySourceException;
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.cache.CachedObject;
/*     */ import com.nimbusds.jose.util.events.EventListener;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class OutageTolerantJWKSetSource<C extends SecurityContext>
/*     */   extends AbstractCachingJWKSetSource<C>
/*     */ {
/*     */   private final EventListener<OutageTolerantJWKSetSource<C>, C> eventListener;
/*     */   
/*     */   public static class OutageEvent<C extends SecurityContext>
/*     */     extends AbstractJWKSetSourceEvent<OutageTolerantJWKSetSource<C>, C>
/*     */   {
/*     */     private final Exception exception;
/*     */     private final long remainingTime;
/*     */     
/*     */     private OutageEvent(OutageTolerantJWKSetSource<C> source, Exception exception, long remainingTime, C context) {
/*  60 */       super(source, context);
/*  61 */       Objects.requireNonNull(exception);
/*  62 */       this.exception = exception;
/*  63 */       this.remainingTime = remainingTime;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Exception getException() {
/*  73 */       return this.exception;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getRemainingTime() {
/*  83 */       return this.remainingTime;
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
/*     */   public OutageTolerantJWKSetSource(JWKSetSource<C> source, long timeToLive, EventListener<OutageTolerantJWKSetSource<C>, C> eventListener) {
/* 104 */     super(source, timeToLive);
/* 105 */     this.eventListener = eventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet getJWKSet(JWKSetCacheRefreshEvaluator refreshEvaluator, long currentTime, C context) throws KeySourceException {
/*     */     try {
/* 113 */       JWKSet jwkSet = getSource().getJWKSet(refreshEvaluator, currentTime, context);
/* 114 */       cacheJWKSet(jwkSet, currentTime);
/* 115 */       return jwkSet;
/*     */     }
/* 117 */     catch (JWKSetUnavailableException e) {
/*     */       
/* 119 */       CachedObject<JWKSet> cache = getCachedJWKSet();
/* 120 */       if (cache != null && cache.isValid(currentTime)) {
/* 121 */         long remainingTime = cache.getExpirationTime() - currentTime;
/* 122 */         if (this.eventListener != null) {
/* 123 */           this.eventListener.notify(new OutageEvent<>(this, (Exception)e, remainingTime, (SecurityContext)context));
/*     */         }
/* 125 */         JWKSet jwkSet = (JWKSet)cache.get();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 130 */         JWKSet jwkSetClone = new JWKSet(jwkSet.getKeys());
/* 131 */         if (!refreshEvaluator.requiresRefresh(jwkSetClone)) {
/* 132 */           return jwkSetClone;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       throw e;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\OutageTolerantJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */