/*    */ package com.hypixel.hytale.logger.sentry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkipSentryException
/*    */   extends RuntimeException
/*    */ {
/*    */   public SkipSentryException() {}
/*    */   
/*    */   public SkipSentryException(Throwable cause) {
/* 12 */     super(cause);
/*    */   }
/*    */   
/*    */   public SkipSentryException(String message, Throwable cause) {
/* 16 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public static boolean hasSkipSentry(Throwable thrown) {
/* 20 */     Throwable throwable = thrown;
/* 21 */     while (throwable != null) {
/* 22 */       if (throwable instanceof SkipSentryException) {
/* 23 */         return true;
/*    */       }
/*    */       
/* 26 */       if (throwable.getCause() == throwable) {
/* 27 */         return false;
/*    */       }
/* 29 */       throwable = throwable.getCause();
/*    */     } 
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\sentry\SkipSentryException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */