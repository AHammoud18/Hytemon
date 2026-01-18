/*    */ package com.hypixel.hytale.logger.backend;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public class HytaleUncaughtExceptionHandler
/*    */   implements Thread.UncaughtExceptionHandler {
/*  8 */   public static final HytaleUncaughtExceptionHandler INSTANCE = new HytaleUncaughtExceptionHandler();
/*    */   
/*    */   public static void setup() {
/* 11 */     Thread.setDefaultUncaughtExceptionHandler(INSTANCE);
/* 12 */     System.setProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler", HytaleUncaughtExceptionHandler.class.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void uncaughtException(Thread t, Throwable e) {
/* 17 */     ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Exception in thread: %s", t);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleUncaughtExceptionHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */