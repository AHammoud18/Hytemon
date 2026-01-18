/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import com.google.common.flogger.AbstractLogger;
/*    */ import com.google.common.flogger.LogSite;
/*    */ import com.google.common.flogger.backend.Platform;
/*    */ import com.google.common.flogger.util.CallerFinder;
/*    */ import com.google.common.flogger.util.StackBasedLogSite;
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
/*    */ public final class StackBasedCallerFinder
/*    */   extends Platform.LogCallerFinder
/*    */ {
/* 31 */   private static final Platform.LogCallerFinder INSTANCE = new StackBasedCallerFinder();
/*    */ 
/*    */   
/*    */   public static Platform.LogCallerFinder getInstance() {
/* 35 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String findLoggingClass(Class<? extends AbstractLogger<?>> loggerClass) {
/* 41 */     StackTraceElement caller = CallerFinder.findCallerOf(loggerClass, new Throwable(), 1);
/* 42 */     if (caller != null)
/*    */     {
/* 44 */       return caller.getClassName();
/*    */     }
/* 46 */     throw new IllegalStateException("no caller found on the stack for: " + loggerClass.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LogSite findLogSite(Class<?> loggerApi, int stackFramesToSkip) {
/* 55 */     StackTraceElement caller = CallerFinder.findCallerOf(loggerApi, new Throwable(), stackFramesToSkip + 1);
/* 56 */     return (caller != null) ? (LogSite)new StackBasedLogSite(caller) : LogSite.INVALID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return "Default stack-based caller finder";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\StackBasedCallerFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */