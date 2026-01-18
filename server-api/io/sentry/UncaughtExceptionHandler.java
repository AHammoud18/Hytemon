/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ interface UncaughtExceptionHandler
/*    */ {
/*    */   Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler();
/*    */   
/*    */   void setDefaultUncaughtExceptionHandler(@Nullable Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler);
/*    */   
/*    */   public static final class Adapter
/*    */     implements UncaughtExceptionHandler {
/*    */     static UncaughtExceptionHandler getInstance() {
/* 14 */       return INSTANCE;
/*    */     }
/*    */     
/* 17 */     private static final Adapter INSTANCE = new Adapter();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
/* 23 */       return Thread.getDefaultUncaughtExceptionHandler();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void setDefaultUncaughtExceptionHandler(@Nullable Thread.UncaughtExceptionHandler handler) {
/* 29 */       Thread.setDefaultUncaughtExceptionHandler(handler);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\UncaughtExceptionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */