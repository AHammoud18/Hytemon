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
/*    */ public final class SslCloseCompletionEvent
/*    */   extends SslCompletionEvent
/*    */ {
/* 23 */   public static final SslCloseCompletionEvent SUCCESS = new SslCloseCompletionEvent();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SslCloseCompletionEvent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SslCloseCompletionEvent(Throwable cause) {
/* 35 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslCloseCompletionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */