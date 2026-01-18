/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ final class Http3FrameValidationUtils
/*    */ {
/*    */   private static <T> T cast(Object msg) {
/* 32 */     return (T)msg;
/*    */   }
/*    */   
/*    */   private static <T> boolean isValid(Class<T> frameType, Object msg) {
/* 36 */     return frameType.isInstance(msg);
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
/*    */   @Nullable
/*    */   static <T> T validateFrameWritten(Class<T> expectedFrameType, Object msg) {
/* 50 */     if (isValid(expectedFrameType, msg)) {
/* 51 */       return cast(msg);
/*    */     }
/* 53 */     return null;
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
/*    */   @Nullable
/*    */   static <T> T validateFrameRead(Class<T> expectedFrameType, Object msg) {
/* 67 */     if (isValid(expectedFrameType, msg)) {
/* 68 */       return cast(msg);
/*    */     }
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void frameTypeUnexpected(ChannelPromise promise, Object frame) {
/* 80 */     String type = StringUtil.simpleClassName(frame);
/* 81 */     ReferenceCountUtil.release(frame);
/* 82 */     promise.setFailure(new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Frame of type " + type + " unexpected"));
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
/*    */   static void frameTypeUnexpected(ChannelHandlerContext ctx, Object frame) {
/* 94 */     ReferenceCountUtil.release(frame);
/* 95 */     Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_FRAME_UNEXPECTED, "Frame type unexpected", true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameValidationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */