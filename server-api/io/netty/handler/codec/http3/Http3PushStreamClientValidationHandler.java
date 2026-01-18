/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
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
/*    */ 
/*    */ final class Http3PushStreamClientValidationHandler
/*    */   extends Http3FrameTypeInboundValidationHandler<Http3RequestStreamFrame>
/*    */ {
/*    */   private final QpackAttributes qpackAttributes;
/*    */   private final QpackDecoder qpackDecoder;
/*    */   private final Http3RequestStreamCodecState decodeState;
/* 33 */   private long expectedLength = -1L;
/*    */   
/*    */   private long seenLength;
/*    */   
/*    */   Http3PushStreamClientValidationHandler(QpackAttributes qpackAttributes, QpackDecoder qpackDecoder, Http3RequestStreamCodecState decodeState) {
/* 38 */     super(Http3RequestStreamFrame.class);
/* 39 */     this.qpackAttributes = qpackAttributes;
/* 40 */     this.qpackDecoder = qpackDecoder;
/* 41 */     this.decodeState = decodeState;
/*    */   }
/*    */ 
/*    */   
/*    */   void channelRead(ChannelHandlerContext ctx, Http3RequestStreamFrame frame) {
/* 46 */     if (frame instanceof Http3PushPromiseFrame) {
/* 47 */       ctx.fireChannelRead(frame);
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     if (frame instanceof Http3HeadersFrame) {
/* 52 */       Http3HeadersFrame headersFrame = (Http3HeadersFrame)frame;
/* 53 */       long maybeContentLength = Http3RequestStreamValidationUtils.validateHeaderFrameRead(headersFrame, ctx, this.decodeState);
/* 54 */       if (maybeContentLength >= 0L) {
/* 55 */         this.expectedLength = maybeContentLength;
/* 56 */       } else if (maybeContentLength == -2L) {
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 61 */     if (frame instanceof Http3DataFrame) {
/* 62 */       Http3DataFrame dataFrame = (Http3DataFrame)frame;
/* 63 */       long maybeContentLength = Http3RequestStreamValidationUtils.validateDataFrameRead(dataFrame, ctx, this.expectedLength, this.seenLength, false);
/* 64 */       if (maybeContentLength >= 0L) {
/* 65 */         this.seenLength = maybeContentLength;
/* 66 */       } else if (maybeContentLength == -2L) {
/*    */         return;
/*    */       } 
/*    */     } 
/* 70 */     ctx.fireChannelRead(frame);
/*    */   }
/*    */ 
/*    */   
/*    */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 75 */     if (evt == ChannelInputShutdownReadComplete.INSTANCE) {
/* 76 */       Http3RequestStreamValidationUtils.sendStreamAbandonedIfRequired(ctx, this.qpackAttributes, this.qpackDecoder, this.decodeState);
/* 77 */       if (!Http3RequestStreamValidationUtils.validateOnStreamClosure(ctx, this.expectedLength, this.seenLength, false)) {
/*    */         return;
/*    */       }
/*    */     } 
/* 81 */     ctx.fireUserEventTriggered(evt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSharable() {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushStreamClientValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */