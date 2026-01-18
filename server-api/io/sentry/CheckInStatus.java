/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public enum CheckInStatus
/*    */ {
/*  8 */   IN_PROGRESS,
/*  9 */   OK,
/* 10 */   ERROR;
/*    */   @NotNull
/*    */   public String apiName() {
/* 13 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CheckInStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */