/*    */ package com.google.common.flogger;
/*    */ 
/*    */ import com.google.common.flogger.util.Checks;
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
/*    */ public final class LazyArgs
/*    */ {
/*    */   public static <T> LazyArg<T> lazy(LazyArg<T> lambdaOrMethodReference) {
/* 54 */     return (LazyArg<T>)Checks.checkNotNull(lambdaOrMethodReference, "lazy arg");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LazyArgs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */