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
/*    */ final class Http3PushStreamFrameTypeValidator
/*    */   implements Http3FrameTypeValidator
/*    */ {
/* 23 */   static final Http3PushStreamFrameTypeValidator INSTANCE = new Http3PushStreamFrameTypeValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(long type, boolean first) throws Http3Exception {
/* 29 */     switch ((int)type) {
/*    */       case 3:
/*    */       case 4:
/*    */       case 5:
/*    */       case 7:
/*    */       case 13:
/* 35 */         throw new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Unexpected frame type '" + type + "' received");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushStreamFrameTypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */