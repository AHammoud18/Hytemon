/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelInboundHandlerAdapter;
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
/*    */ final class Http3RequestStreamDecodeStateValidator
/*    */   extends ChannelInboundHandlerAdapter
/*    */   implements Http3RequestStreamCodecState
/*    */ {
/* 30 */   private Http3RequestStreamEncodeStateValidator.State state = Http3RequestStreamEncodeStateValidator.State.None;
/*    */ 
/*    */   
/*    */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 34 */     if (!(msg instanceof Http3RequestStreamFrame)) {
/* 35 */       super.channelRead(ctx, msg);
/*    */       return;
/*    */     } 
/* 38 */     Http3RequestStreamFrame frame = (Http3RequestStreamFrame)msg;
/* 39 */     Http3RequestStreamEncodeStateValidator.State nextState = Http3RequestStreamEncodeStateValidator.evaluateFrame(this.state, frame);
/* 40 */     if (nextState == null) {
/* 41 */       Http3FrameValidationUtils.frameTypeUnexpected(ctx, msg);
/*    */       return;
/*    */     } 
/* 44 */     this.state = nextState;
/* 45 */     super.channelRead(ctx, msg);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean started() {
/* 50 */     return Http3RequestStreamEncodeStateValidator.isStreamStarted(this.state);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean receivedFinalHeaders() {
/* 55 */     return Http3RequestStreamEncodeStateValidator.isFinalHeadersReceived(this.state);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean terminated() {
/* 60 */     return Http3RequestStreamEncodeStateValidator.isTrailersReceived(this.state);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamDecodeStateValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */