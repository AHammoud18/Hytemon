/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum MonitorScheduleType
/*    */ {
/*  8 */   CRONTAB,
/*  9 */   INTERVAL;
/*    */   @NotNull
/*    */   public String apiName() {
/* 12 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MonitorScheduleType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */