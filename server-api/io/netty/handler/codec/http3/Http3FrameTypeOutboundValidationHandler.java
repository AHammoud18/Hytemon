/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ class Http3FrameTypeOutboundValidationHandler<T extends Http3Frame>
/*    */   extends ChannelOutboundHandlerAdapter
/*    */ {
/*    */   private final Class<T> frameType;
/*    */   
/*    */   Http3FrameTypeOutboundValidationHandler(Class<T> frameType) {
/* 31 */     this.frameType = (Class<T>)ObjectUtil.checkNotNull(frameType, "frameType");
/*    */   }
/*    */ 
/*    */   
/*    */   public final void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
/* 36 */     Http3Frame http3Frame = Http3FrameValidationUtils.<Http3Frame>validateFrameWritten(this.frameType, msg);
/* 37 */     if (http3Frame != null) {
/* 38 */       write(ctx, (T)http3Frame, promise);
/*    */     } else {
/* 40 */       writeFrameDiscarded(msg, promise);
/*    */     } 
/*    */   }
/*    */   
/*    */   void write(ChannelHandlerContext ctx, T msg, ChannelPromise promise) {
/* 45 */     ctx.write(msg, promise);
/*    */   }
/*    */   
/*    */   void writeFrameDiscarded(Object discardedFrame, ChannelPromise promise) {
/* 49 */     Http3FrameValidationUtils.frameTypeUnexpected(promise, discardedFrame);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameTypeOutboundValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */