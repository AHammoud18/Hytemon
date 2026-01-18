/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.proc.SecurityContext;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.cache.CachedObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ abstract class AbstractCachingJWKSetSource<C extends SecurityContext>
/*     */   extends JWKSetSourceWrapper<C>
/*     */ {
/*     */   private volatile CachedObject<JWKSet> cachedJWKSet;
/*     */   private final long timeToLive;
/*     */   
/*     */   AbstractCachingJWKSetSource(JWKSetSource<C> source, long timeToLive) {
/*  49 */     super(source);
/*  50 */     this.timeToLive = timeToLive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CachedObject<JWKSet> getCachedJWKSet() {
/*  60 */     return this.cachedJWKSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCachedJWKSet(CachedObject<JWKSet> cachedJWKSet) {
/*  70 */     this.cachedJWKSet = cachedJWKSet;
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
/*     */   CachedObject<JWKSet> getCachedJWKSetIfValid(long currentTime) {
/*  83 */     CachedObject<JWKSet> threadSafeCache = getCachedJWKSet();
/*  84 */     if (threadSafeCache != null && threadSafeCache.isValid(currentTime)) {
/*  85 */       return threadSafeCache;
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeToLive() {
/*  97 */     return this.timeToLive;
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
/*     */   CachedObject<JWKSet> cacheJWKSet(JWKSet jwkSet, long fetchTime) {
/* 111 */     long currentTime = currentTimeMillis();
/* 112 */     CachedObject<JWKSet> cachedJWKSet = new CachedObject(jwkSet, currentTime, CachedObject.computeExpirationTime(fetchTime, getTimeToLive()));
/* 113 */     setCachedJWKSet(cachedJWKSet);
/* 114 */     return cachedJWKSet;
/*     */   }
/*     */ 
/*     */   
/*     */   long currentTimeMillis() {
/* 119 */     return System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\AbstractCachingJWKSetSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */