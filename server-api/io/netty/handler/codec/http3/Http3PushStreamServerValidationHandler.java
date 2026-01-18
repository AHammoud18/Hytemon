/*    */ package io.netty.handler.codec.http3;
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
/*    */ final class Http3PushStreamServerValidationHandler
/*    */   extends Http3FrameTypeOutboundValidationHandler<Http3PushStreamFrame>
/*    */ {
/* 24 */   static final Http3PushStreamServerValidationHandler INSTANCE = new Http3PushStreamServerValidationHandler();
/*    */   
/*    */   private Http3PushStreamServerValidationHandler() {
/* 27 */     super(Http3PushStreamFrame.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSharable() {
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushStreamServerValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */