/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import com.google.common.flogger.LogContext;
/*    */ import com.google.common.flogger.backend.LogData;
/*    */ import com.google.common.flogger.backend.Metadata;
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
/*    */ 
/*    */ public final class SimpleLogRecord
/*    */   extends AbstractLogRecord
/*    */ {
/*    */   public static SimpleLogRecord create(LogData data, Metadata scope) {
/* 31 */     return new SimpleLogRecord(data, scope);
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SimpleLogRecord create(LogData data) {
/* 37 */     return create(data, Metadata.empty());
/*    */   }
/*    */ 
/*    */   
/*    */   public static SimpleLogRecord error(RuntimeException error, LogData data, Metadata scope) {
/* 42 */     return new SimpleLogRecord(error, data, scope);
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SimpleLogRecord error(RuntimeException error, LogData data) {
/* 48 */     return error(error, data, Metadata.empty());
/*    */   }
/*    */   
/*    */   private SimpleLogRecord(LogData data, Metadata scope) {
/* 52 */     super(data, scope);
/* 53 */     setThrown((Throwable)getMetadataProcessor().getSingleValue(LogContext.Key.LOG_CAUSE));
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
/* 70 */     getMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   private SimpleLogRecord(RuntimeException error, LogData data, Metadata scope) {
/* 75 */     super(error, data, scope);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\SimpleLogRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */