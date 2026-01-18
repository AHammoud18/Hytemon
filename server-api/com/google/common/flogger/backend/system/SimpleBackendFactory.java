/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import com.google.common.flogger.backend.LoggerBackend;
/*    */ import java.util.logging.Logger;
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
/*    */ public final class SimpleBackendFactory
/*    */   extends BackendFactory
/*    */ {
/* 28 */   private static final BackendFactory INSTANCE = new SimpleBackendFactory();
/*    */ 
/*    */   
/*    */   public static BackendFactory getInstance() {
/* 32 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LoggerBackend create(String loggingClass) {
/* 40 */     Logger logger = Logger.getLogger(loggingClass.replace('$', '.'));
/* 41 */     return new SimpleLoggerBackend(logger);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "Default logger backend factory";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\SimpleBackendFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */