/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import java.util.function.BooleanSupplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Http3RequestStreamValidationHandler
/*     */   extends Http3FrameTypeDuplexValidationHandler<Http3RequestStreamFrame>
/*     */ {
/*     */   private final boolean server;
/*     */   private final BooleanSupplier goAwayReceivedSupplier;
/*     */   private final QpackAttributes qpackAttributes;
/*     */   private final QpackDecoder qpackDecoder;
/*     */   private final Http3RequestStreamCodecState decodeState;
/*     */   private final Http3RequestStreamCodecState encodeState;
/*     */   private boolean clientHeadRequest;
/*  43 */   private long expectedLength = -1L;
/*     */   
/*     */   private long seenLength;
/*     */ 
/*     */   
/*     */   static ChannelHandler newServerValidator(QpackAttributes qpackAttributes, QpackDecoder decoder, Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/*  49 */     return (ChannelHandler)new Http3RequestStreamValidationHandler(true, () -> false, qpackAttributes, decoder, encodeState, decodeState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ChannelHandler newClientValidator(BooleanSupplier goAwayReceivedSupplier, QpackAttributes qpackAttributes, QpackDecoder decoder, Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/*  56 */     return (ChannelHandler)new Http3RequestStreamValidationHandler(false, goAwayReceivedSupplier, qpackAttributes, decoder, encodeState, decodeState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Http3RequestStreamValidationHandler(boolean server, BooleanSupplier goAwayReceivedSupplier, QpackAttributes qpackAttributes, QpackDecoder qpackDecoder, Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/*  64 */     super(Http3RequestStreamFrame.class);
/*  65 */     this.server = server;
/*  66 */     this.goAwayReceivedSupplier = goAwayReceivedSupplier;
/*  67 */     this.qpackAttributes = qpackAttributes;
/*  68 */     this.qpackDecoder = qpackDecoder;
/*  69 */     this.decodeState = decodeState;
/*  70 */     this.encodeState = encodeState;
/*     */   }
/*     */ 
/*     */   
/*     */   void write(ChannelHandlerContext ctx, Http3RequestStreamFrame frame, ChannelPromise promise) {
/*  75 */     if (!this.server) {
/*  76 */       if (!Http3RequestStreamValidationUtils.validateClientWrite(frame, promise, ctx, this.goAwayReceivedSupplier, this.encodeState)) {
/*     */         return;
/*     */       }
/*  79 */       if (frame instanceof Http3HeadersFrame) {
/*  80 */         this.clientHeadRequest = HttpMethod.HEAD.asciiName().equals(((Http3HeadersFrame)frame).headers().method());
/*     */       }
/*     */     } 
/*  83 */     ctx.write(frame, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   void channelRead(ChannelHandlerContext ctx, Http3RequestStreamFrame frame) {
/*  88 */     if (frame instanceof Http3PushPromiseFrame) {
/*  89 */       if (this.server) {
/*     */ 
/*     */         
/*  92 */         Http3FrameValidationUtils.frameTypeUnexpected(ctx, frame);
/*     */       } else {
/*  94 */         ctx.fireChannelRead(frame);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     if (frame instanceof Http3HeadersFrame) {
/* 100 */       Http3HeadersFrame headersFrame = (Http3HeadersFrame)frame;
/* 101 */       long maybeContentLength = Http3RequestStreamValidationUtils.validateHeaderFrameRead(headersFrame, ctx, this.decodeState);
/* 102 */       if (maybeContentLength >= 0L) {
/* 103 */         this.expectedLength = maybeContentLength;
/* 104 */       } else if (maybeContentLength == -2L) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     if (frame instanceof Http3DataFrame) {
/* 110 */       Http3DataFrame dataFrame = (Http3DataFrame)frame;
/* 111 */       long maybeContentLength = Http3RequestStreamValidationUtils.validateDataFrameRead(dataFrame, ctx, this.expectedLength, this.seenLength, this.clientHeadRequest);
/*     */       
/* 113 */       if (maybeContentLength >= 0L) {
/* 114 */         this.seenLength = maybeContentLength;
/* 115 */       } else if (maybeContentLength == -2L) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     ctx.fireChannelRead(frame);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 125 */     if (evt == ChannelInputShutdownReadComplete.INSTANCE) {
/* 126 */       Http3RequestStreamValidationUtils.sendStreamAbandonedIfRequired(ctx, this.qpackAttributes, this.qpackDecoder, this.decodeState);
/* 127 */       if (!Http3RequestStreamValidationUtils.validateOnStreamClosure(ctx, this.expectedLength, this.seenLength, this.clientHeadRequest)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 131 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamValidationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */