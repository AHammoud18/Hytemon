/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SystemClock
/*    */   extends Clock
/*    */ {
/* 27 */   private static final SystemClock INSTANCE = new SystemClock();
/*    */ 
/*    */   
/*    */   public static SystemClock getInstance() {
/* 31 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCurrentTimeNanos() {
/* 38 */     return TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return "Default millisecond precision clock";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\SystemClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */