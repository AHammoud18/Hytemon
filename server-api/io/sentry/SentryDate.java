/*    */ package io.sentry;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SentryDate
/*    */   implements Comparable<SentryDate>
/*    */ {
/*    */   public long laterDateNanosTimestampByDiff(@Nullable SentryDate otherDate) {
/* 23 */     if (otherDate != null && compareTo(otherDate) < 0) {
/* 24 */       return otherDate.nanoTimestamp();
/*    */     }
/* 26 */     return nanoTimestamp();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long diff(@NotNull SentryDate otherDate) {
/* 37 */     return nanoTimestamp() - otherDate.nanoTimestamp();
/*    */   }
/*    */   
/*    */   public final boolean isBefore(@NotNull SentryDate otherDate) {
/* 41 */     return (diff(otherDate) < 0L);
/*    */   }
/*    */   
/*    */   public final boolean isAfter(@NotNull SentryDate otherDate) {
/* 45 */     return (diff(otherDate) > 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(@NotNull SentryDate otherDate) {
/* 50 */     return Long.valueOf(nanoTimestamp()).compareTo(Long.valueOf(otherDate.nanoTimestamp()));
/*    */   }
/*    */   
/*    */   public abstract long nanoTimestamp();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */