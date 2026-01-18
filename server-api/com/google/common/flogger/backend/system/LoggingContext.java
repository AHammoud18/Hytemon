/*    */ package com.google.common.flogger.backend.system;
/*    */ 
/*    */ import com.google.common.flogger.context.ContextDataProvider;
/*    */ import com.google.common.flogger.context.LogLevelMap;
/*    */ import com.google.common.flogger.context.ScopedLoggingContext;
/*    */ import com.google.common.flogger.context.Tags;
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
/*    */ @Deprecated
/*    */ public abstract class LoggingContext
/*    */   extends ContextDataProvider
/*    */ {
/* 31 */   private static final ScopedLoggingContext NO_OP_API = new NoOpScopedLoggingContext();
/*    */ 
/*    */   
/*    */   public ScopedLoggingContext getContextApiSingleton() {
/* 35 */     return NO_OP_API;
/*    */   }
/*    */   
/*    */   private static final class NoOpScopedLoggingContext extends ScopedLoggingContext implements ScopedLoggingContext.LoggingContextCloseable {
/*    */     private NoOpScopedLoggingContext() {}
/*    */     
/*    */     public ScopedLoggingContext.Builder newContext() {
/* 42 */       return new ScopedLoggingContext.Builder()
/*    */         {
/*    */           public ScopedLoggingContext.LoggingContextCloseable install() {
/* 45 */             return LoggingContext.NoOpScopedLoggingContext.this;
/*    */           }
/*    */         };
/*    */     }
/*    */ 
/*    */     
/*    */     public void close() {}
/*    */ 
/*    */     
/*    */     public boolean addTags(Tags tags) {
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean applyLogLevelMap(LogLevelMap m) {
/* 60 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\LoggingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */