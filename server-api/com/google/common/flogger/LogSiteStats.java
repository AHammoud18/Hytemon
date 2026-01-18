/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.backend.Metadata;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LogSiteStats
/*     */ {
/*     */   static RateLimitPeriod newRateLimitPeriod(int n, TimeUnit unit) {
/*  34 */     return new RateLimitPeriod(n, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class RateLimitPeriod
/*     */   {
/*     */     private final int n;
/*     */ 
/*     */     
/*     */     private final TimeUnit unit;
/*     */ 
/*     */     
/*  47 */     private int skipCount = -1;
/*     */ 
/*     */     
/*     */     private RateLimitPeriod(int n, TimeUnit unit) {
/*  51 */       if (n <= 0) {
/*  52 */         throw new IllegalArgumentException("time period must be positive: " + n);
/*     */       }
/*  54 */       this.n = n;
/*  55 */       this.unit = (TimeUnit)Checks.checkNotNull(unit, "time unit");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long toNanos() {
/*  63 */       return this.unit.toNanos(this.n);
/*     */     }
/*     */     
/*     */     private void setSkipCount(int skipCount) {
/*  67 */       this.skipCount = skipCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  76 */       StringBuilder out = (new StringBuilder()).append(this.n).append(' ').append(this.unit);
/*  77 */       if (this.skipCount > 0) {
/*  78 */         out.append(" [skipped: ").append(this.skipCount).append(']');
/*     */       }
/*  80 */       return out.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  86 */       return this.n * 37 ^ this.unit.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  91 */       if (obj instanceof RateLimitPeriod) {
/*  92 */         RateLimitPeriod that = (RateLimitPeriod)obj;
/*  93 */         return (this.n == that.n && this.unit == that.unit);
/*     */       } 
/*  95 */       return false;
/*     */     }
/*     */   }
/*     */   
/*  99 */   private static final LogSiteMap<LogSiteStats> map = new LogSiteMap<LogSiteStats>()
/*     */     {
/*     */       protected LogSiteStats initialValue() {
/* 102 */         return new LogSiteStats();
/*     */       }
/*     */     };
/*     */   
/*     */   static LogSiteStats getStatsForKey(LogSiteKey logSiteKey, Metadata metadata) {
/* 107 */     return map.get(logSiteKey, metadata);
/*     */   }
/*     */   
/* 110 */   private final AtomicLong invocationCount = new AtomicLong();
/* 111 */   private final AtomicLong lastTimestampNanos = new AtomicLong();
/* 112 */   private final AtomicInteger skippedLogStatements = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean incrementAndCheckInvocationCount(int rateLimitCount) {
/* 120 */     return (this.invocationCount.getAndIncrement() % rateLimitCount == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkLastTimestamp(long timestampNanos, RateLimitPeriod period) {
/* 129 */     long lastNanos = this.lastTimestampNanos.get();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     long deadlineNanos = lastNanos + period.toNanos();
/* 135 */     if (deadlineNanos >= 0L && (timestampNanos >= deadlineNanos || lastNanos == 0L) && this.lastTimestampNanos
/*     */       
/* 137 */       .compareAndSet(lastNanos, timestampNanos)) {
/* 138 */       period.setSkipCount(this.skippedLogStatements.getAndSet(0));
/* 139 */       return true;
/*     */     } 
/* 141 */     this.skippedLogStatements.incrementAndGet();
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LogSiteStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */