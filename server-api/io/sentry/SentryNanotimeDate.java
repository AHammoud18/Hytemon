/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Date;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SentryNanotimeDate
/*    */   extends SentryDate
/*    */ {
/*    */   @NotNull
/*    */   private final Date date;
/*    */   private final long nanos;
/*    */   
/*    */   public SentryNanotimeDate() {
/* 25 */     this(DateUtils.getCurrentDateTime(), System.nanoTime());
/*    */   }
/*    */   
/*    */   public SentryNanotimeDate(@NotNull Date date, long nanos) {
/* 29 */     this.date = date;
/* 30 */     this.nanos = nanos;
/*    */   }
/*    */ 
/*    */   
/*    */   public long diff(@NotNull SentryDate otherDate) {
/* 35 */     if (otherDate instanceof SentryNanotimeDate) {
/* 36 */       SentryNanotimeDate otherNanoDate = (SentryNanotimeDate)otherDate;
/* 37 */       return this.nanos - otherNanoDate.nanos;
/*    */     } 
/* 39 */     return super.diff(otherDate);
/*    */   }
/*    */ 
/*    */   
/*    */   public long nanoTimestamp() {
/* 44 */     return DateUtils.dateToNanos(this.date);
/*    */   }
/*    */ 
/*    */   
/*    */   public long laterDateNanosTimestampByDiff(@Nullable SentryDate otherDate) {
/* 49 */     if (otherDate != null && otherDate instanceof SentryNanotimeDate) {
/* 50 */       SentryNanotimeDate otherNanoDate = (SentryNanotimeDate)otherDate;
/* 51 */       if (compareTo(otherDate) < 0) {
/* 52 */         return nanotimeDiff(this, otherNanoDate);
/*    */       }
/* 54 */       return nanotimeDiff(otherNanoDate, this);
/*    */     } 
/*    */     
/* 57 */     return super.laterDateNanosTimestampByDiff(otherDate);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(@NotNull SentryDate otherDate) {
/* 64 */     if (otherDate instanceof SentryNanotimeDate) {
/* 65 */       SentryNanotimeDate otherNanoDate = (SentryNanotimeDate)otherDate;
/* 66 */       long thisDateMillis = this.date.getTime();
/* 67 */       long otherDateMillis = otherNanoDate.date.getTime();
/* 68 */       if (thisDateMillis == otherDateMillis) {
/* 69 */         return Long.valueOf(this.nanos).compareTo(Long.valueOf(otherNanoDate.nanos));
/*    */       }
/* 71 */       return Long.valueOf(thisDateMillis).compareTo(Long.valueOf(otherDateMillis));
/*    */     } 
/*    */     
/* 74 */     return super.compareTo(otherDate);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private long nanotimeDiff(@NotNull SentryNanotimeDate earlierDate, @NotNull SentryNanotimeDate laterDate) {
/* 80 */     long nanoDiff = laterDate.nanos - earlierDate.nanos;
/* 81 */     return earlierDate.nanoTimestamp() + nanoDiff;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryNanotimeDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */