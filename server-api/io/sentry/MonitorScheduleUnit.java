/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum MonitorScheduleUnit
/*    */ {
/*  8 */   MINUTE,
/*  9 */   HOUR,
/* 10 */   DAY,
/* 11 */   WEEK,
/* 12 */   MONTH,
/* 13 */   YEAR;
/*    */   @NotNull
/*    */   public String apiName() {
/* 16 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MonitorScheduleUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */