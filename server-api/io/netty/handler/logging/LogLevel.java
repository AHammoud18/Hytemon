/*    */ package io.netty.handler.logging;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogLevel;
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
/*    */ public enum LogLevel
/*    */ {
/* 24 */   TRACE(InternalLogLevel.TRACE),
/* 25 */   DEBUG(InternalLogLevel.DEBUG),
/* 26 */   INFO(InternalLogLevel.INFO),
/* 27 */   WARN(InternalLogLevel.WARN),
/* 28 */   ERROR(InternalLogLevel.ERROR);
/*    */   
/*    */   private final InternalLogLevel internalLevel;
/*    */   
/*    */   LogLevel(InternalLogLevel internalLevel) {
/* 33 */     this.internalLevel = internalLevel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InternalLogLevel toInternalLevel() {
/* 44 */     return this.internalLevel;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\logging\LogLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */