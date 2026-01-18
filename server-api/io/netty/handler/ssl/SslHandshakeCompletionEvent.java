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
/*    */ 
/*    */ 
/*    */ public final class SslHandshakeCompletionEvent
/*    */   extends SslCompletionEvent
/*    */ {
/* 25 */   public static final SslHandshakeCompletionEvent SUCCESS = new SslHandshakeCompletionEvent();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SslHandshakeCompletionEvent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SslHandshakeCompletionEvent(Throwable cause) {
/* 37 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslHandshakeCompletionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */