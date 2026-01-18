/*    */ package com.google.common.flogger.context;
/*    */ 
/*    */ import com.google.errorprone.annotations.CheckReturnValue;
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
/*    */ public final class ScopedLoggingContexts
/*    */ {
/*    */   @CheckReturnValue
/*    */   public static ScopedLoggingContext.Builder newContext() {
/* 42 */     return ScopedLoggingContext.getInstance().newContext();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\ScopedLoggingContexts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */