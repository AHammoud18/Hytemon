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
/*    */ final class Http3RequestStreamFrameTypeValidator
/*    */   implements Http3FrameTypeValidator
/*    */ {
/* 23 */   static final Http3RequestStreamFrameTypeValidator INSTANCE = new Http3RequestStreamFrameTypeValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(long type, boolean first) throws Http3Exception {
/* 29 */     switch ((int)type) {
/*    */       case 3:
/*    */       case 4:
/*    */       case 7:
/*    */       case 13:
/* 34 */         throw new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Unexpected frame type '" + type + "' received");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamFrameTypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */