/*     */ package com.nimbusds.jose.util.cache;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class CachedObject<V>
/*     */ {
/*     */   private final V object;
/*     */   private final long timestamp;
/*     */   private final long expirationTime;
/*     */   
/*     */   public static long computeExpirationTime(long currentTime, long timeToLive) {
/*  53 */     long expirationTime = currentTime + timeToLive;
/*     */     
/*  55 */     if (expirationTime < 0L)
/*     */     {
/*  57 */       return Long.MAX_VALUE;
/*     */     }
/*     */     
/*  60 */     return expirationTime;
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
/*     */   public CachedObject(V object, long timestamp, long expirationTime) {
/*  74 */     this.object = Objects.requireNonNull(object);
/*  75 */     this.timestamp = timestamp;
/*  76 */     this.expirationTime = expirationTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get() {
/*  86 */     return this.object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimestamp() {
/*  96 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getExpirationTime() {
/* 106 */     return this.expirationTime;
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
/*     */   public boolean isValid(long currentTime) {
/* 120 */     return (currentTime < this.expirationTime);
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
/*     */   public boolean isExpired(long currentTime) {
/* 134 */     return !isValid(currentTime);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\cache\CachedObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */