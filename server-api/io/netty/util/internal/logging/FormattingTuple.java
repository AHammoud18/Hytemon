/*    */ package io.netty.util.internal.logging;
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
/*    */ public final class FormattingTuple
/*    */ {
/*    */   private final String message;
/*    */   private final Throwable throwable;
/*    */   
/*    */   public FormattingTuple(String message, Throwable throwable) {
/* 51 */     this.message = message;
/* 52 */     this.throwable = throwable;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 56 */     return this.message;
/*    */   }
/*    */   
/*    */   public Throwable getThrowable() {
/* 60 */     return this.throwable;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\FormattingTuple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */