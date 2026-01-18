/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelOutboundHandler;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import java.net.SocketAddress;
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
/*    */ class Http3FrameTypeDuplexValidationHandler<T extends Http3Frame>
/*    */   extends Http3FrameTypeInboundValidationHandler<T>
/*    */   implements ChannelOutboundHandler
/*    */ {
/*    */   Http3FrameTypeDuplexValidationHandler(Class<T> frameType) {
/* 31 */     super(frameType);
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
/*    */ 
/*    */   
/*    */   public void flush(ChannelHandlerContext ctx) {
/* 54 */     ctx.flush();
/*    */   }
/*    */ 
/*    */   
/*    */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
/* 59 */     ctx.bind(localAddress, promise);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 65 */     ctx.connect(remoteAddress, localAddress, promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 70 */     ctx.disconnect(promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 75 */     ctx.close(promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 80 */     ctx.deregister(promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 85 */     ctx.read();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3FrameTypeDuplexValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */