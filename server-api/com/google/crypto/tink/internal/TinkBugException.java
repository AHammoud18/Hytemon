/*    */ package com.google.crypto.tink.internal;
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
/*    */ public final class TinkBugException
/*    */   extends RuntimeException
/*    */ {
/*    */   public TinkBugException(String message) {
/* 28 */     super(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public TinkBugException(String message, Throwable cause) {
/* 33 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public TinkBugException(Throwable cause) {
/* 38 */     super(cause);
/*    */   }
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
/*    */   public static <T> T exceptionIsBug(ThrowingSupplier<T> t) {
/*    */     try {
/* 62 */       return t.get();
/* 63 */     } catch (Exception e) {
/* 64 */       throw new TinkBugException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void exceptionIsBug(ThrowingRunnable v) {
/*    */     try {
/* 71 */       v.run();
/* 72 */     } catch (Exception e) {
/* 73 */       throw new TinkBugException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface ThrowingSupplier<T> {
/*    */     T get() throws Exception;
/*    */   }
/*    */   
/*    */   public static interface ThrowingRunnable {
/*    */     void run() throws Exception;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\TinkBugException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */