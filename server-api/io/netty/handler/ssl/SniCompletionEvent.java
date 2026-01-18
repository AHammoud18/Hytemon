/*    */ package io.netty.handler.ssl;
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
/*    */ public final class SniCompletionEvent
/*    */   extends SslCompletionEvent
/*    */ {
/*    */   private final String hostname;
/*    */   
/*    */   public SniCompletionEvent(String hostname) {
/* 26 */     this.hostname = hostname;
/*    */   }
/*    */   
/*    */   public SniCompletionEvent(String hostname, Throwable cause) {
/* 30 */     super(cause);
/* 31 */     this.hostname = hostname;
/*    */   }
/*    */   
/*    */   public SniCompletionEvent(Throwable cause) {
/* 35 */     this(null, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String hostname() {
/* 42 */     return this.hostname;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     Throwable cause = cause();
/* 48 */     return (cause == null) ? (getClass().getSimpleName() + "(SUCCESS='" + this.hostname + "'\")") : (
/* 49 */       getClass().getSimpleName() + '(' + cause + ')');
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SniCompletionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */