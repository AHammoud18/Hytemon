/*     */ package com.nimbusds.jose.jwk.source;
/*     */ 
/*     */ import com.nimbusds.jose.jwk.JWKSet;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ @Deprecated
/*     */ public class DefaultJWKSetCache
/*     */   implements JWKSetCache
/*     */ {
/*     */   public static final long DEFAULT_LIFESPAN_MINUTES = 15L;
/*     */   public static final long DEFAULT_REFRESH_TIME_MINUTES = 5L;
/*     */   private final long lifespan;
/*     */   private final long refreshTime;
/*     */   private final TimeUnit timeUnit;
/*     */   private volatile JWKSetWithTimestamp jwkSetWithTimestamp;
/*     */   
/*     */   public DefaultJWKSetCache() {
/*  86 */     this(15L, 5L, TimeUnit.MINUTES);
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
/*     */   public DefaultJWKSetCache(long lifespan, long refreshTime, TimeUnit timeUnit) {
/* 103 */     this.lifespan = lifespan;
/* 104 */     this.refreshTime = refreshTime;
/*     */     
/* 106 */     if ((lifespan > -1L || refreshTime > -1L) && timeUnit == null) {
/* 107 */       throw new IllegalArgumentException("A time unit must be specified for non-negative lifespans or refresh times");
/*     */     }
/*     */     
/* 110 */     this.timeUnit = timeUnit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(JWKSet jwkSet) {
/*     */     JWKSetWithTimestamp updatedJWKSetWithTs;
/* 118 */     if (jwkSet != null) {
/* 119 */       updatedJWKSetWithTs = new JWKSetWithTimestamp(jwkSet);
/*     */     } else {
/*     */       
/* 122 */       updatedJWKSetWithTs = null;
/*     */     } 
/*     */     
/* 125 */     this.jwkSetWithTimestamp = updatedJWKSetWithTs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWKSet get() {
/* 132 */     if (this.jwkSetWithTimestamp == null || isExpired()) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     return this.jwkSetWithTimestamp.getJWKSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requiresRefresh() {
/* 143 */     return (this.jwkSetWithTimestamp != null && this.refreshTime > -1L && (new Date())
/*     */       
/* 145 */       .getTime() > this.jwkSetWithTimestamp.getDate().getTime() + TimeUnit.MILLISECONDS.convert(this.refreshTime, this.timeUnit));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPutTimestamp() {
/* 156 */     return (this.jwkSetWithTimestamp != null) ? this.jwkSetWithTimestamp.getDate().getTime() : -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExpired() {
/* 167 */     return (this.jwkSetWithTimestamp != null && this.lifespan > -1L && (new Date())
/*     */       
/* 169 */       .getTime() > this.jwkSetWithTimestamp.getDate().getTime() + TimeUnit.MILLISECONDS.convert(this.lifespan, this.timeUnit));
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
/*     */   public long getLifespan(TimeUnit timeUnit) {
/* 182 */     if (this.lifespan < 0L) {
/* 183 */       return this.lifespan;
/*     */     }
/*     */     
/* 186 */     return timeUnit.convert(this.lifespan, this.timeUnit);
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
/*     */   public long getRefreshTime(TimeUnit timeUnit) {
/* 199 */     if (this.refreshTime < 0L) {
/* 200 */       return this.refreshTime;
/*     */     }
/*     */     
/* 203 */     return timeUnit.convert(this.refreshTime, this.timeUnit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\DefaultJWKSetCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */