/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelInboundHandlerAdapter;
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
/*    */ class Http3FrameTypeInboundValidationHandler<T extends Http3Frame>
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/*    */   protected final Class<T> frameType;
/*    */   
/*    */   Http3FrameTypeInboundValidationHandler(Class<T> frameType) {
/* 30 */     this.frameType = (Class<T>)ObjectUtil.checkNotNull(frameType, "frameType");
/*    */   }
/*    */ 
/*    */   
/*    */   public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 35 */     Http3Frame http3Frame = Http3FrameValidationUtils.<Http3Frame>validateFrameRead(this.frameType, msg);
/* 36 */     if (http3Frame != null) {
/* 37 */       channelRead(ctx, (T)http3Frame);
/*    */     } else {
/* 39 */       readFrameDiscarded(ctx, msg);
/*    */     } 
/*    */   }
/*    */   
/*    */   void channelRead(ChannelHandlerContext ctx, T frame) throws Exception {
/* 44 */     ctx.fireChannelRead(frame);
/*    */   }
/*    */   
/*    */   void readFrameDiscarded(ChannelHandlerContext ctx, Object discardedFrame) {
/* 48 */     Http3FrameValidationUtils.frameTypeUnexpected(ctx, discardedFrame);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameTypeInboundValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */