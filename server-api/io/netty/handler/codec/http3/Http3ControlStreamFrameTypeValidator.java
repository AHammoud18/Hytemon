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
/*    */ final class Http3ControlStreamFrameTypeValidator
/*    */   implements Http3FrameTypeValidator
/*    */ {
/* 23 */   static final Http3ControlStreamFrameTypeValidator INSTANCE = new Http3ControlStreamFrameTypeValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validate(long type, boolean first) throws Http3Exception {
/* 29 */     switch ((int)type) {
/*    */       case 0:
/*    */       case 1:
/*    */       case 5:
/* 33 */         if (first) {
/* 34 */           throw new Http3Exception(Http3ErrorCode.H3_MISSING_SETTINGS, "Missing settings frame.");
/*    */         }
/*    */         
/* 37 */         throw new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Unexpected frame type '" + type + "' received");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ControlStreamFrameTypeValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */