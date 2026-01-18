/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import java.time.DateTimeException;
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import java.time.temporal.ChronoField;
/*    */ import java.time.temporal.ChronoUnit;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeUtil
/*    */ {
/*    */   public static int compareDifference(@Nonnull Instant from, @Nonnull Instant to, @Nonnull Duration duration) {
/* 29 */     if (from.equals(Instant.MIN) && !to.equals(Instant.MIN) && !duration.isZero()) {
/* 30 */       return 1;
/*    */     }
/*    */     try {
/* 33 */       long diff = from.until(to, ChronoUnit.NANOS);
/* 34 */       return Long.compare(diff, duration.toNanos());
/* 35 */     } catch (DateTimeException|ArithmeticException e) {
/* 36 */       long nanos, seconds = from.until(to, ChronoUnit.SECONDS);
/*    */       
/*    */       try {
/* 39 */         nanos = to.getLong(ChronoField.NANO_OF_SECOND) - from.getLong(ChronoField.NANO_OF_SECOND);
/* 40 */         if (seconds > 0L && nanos < 0L) {
/* 41 */           seconds++;
/* 42 */         } else if (seconds < 0L && nanos > 0L) {
/* 43 */           seconds--;
/*    */         } 
/* 45 */       } catch (DateTimeException e2) {
/* 46 */         nanos = 0L;
/*    */       } 
/* 48 */       long durSeconds = duration.getSeconds();
/* 49 */       int durNanos = duration.getNano();
/*    */       
/* 51 */       int res = Long.compare(seconds, durSeconds);
/* 52 */       if (res == 0) res = Integer.compare((int)nanos, durNanos); 
/* 53 */       return res;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\TimeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */