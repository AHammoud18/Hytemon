/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import com.google.common.flogger.backend.LogData;
/*    */ import com.google.common.flogger.backend.Platform;
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
/*    */ public class SimpleLoggerBackend
/*    */   extends AbstractBackend
/*    */ {
/*    */   public SimpleLoggerBackend(Logger logger) {
/* 27 */     super(logger);
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(LogData data) {
/* 32 */     log(SimpleLogRecord.create(data, Platform.getInjectedMetadata()), data.wasForced());
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleError(RuntimeException error, LogData badData) {
/* 37 */     log(SimpleLogRecord.error(error, badData, Platform.getInjectedMetadata()), badData.wasForced());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\SimpleLoggerBackend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */