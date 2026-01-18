/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ final class Http3RequestStreamValidationUtils
/*     */ {
/*     */   static final long CONTENT_LENGTH_NOT_MODIFIED = -1L;
/*     */   static final long INVALID_FRAME_READ = -2L;
/*     */   
/*     */   static boolean validateClientWrite(Http3RequestStreamFrame frame, ChannelPromise promise, ChannelHandlerContext ctx, BooleanSupplier goAwayReceivedSupplier, Http3RequestStreamCodecState encodeState) {
/*  55 */     if (goAwayReceivedSupplier.getAsBoolean() && !encodeState.started()) {
/*  56 */       String type = StringUtil.simpleClassName(frame);
/*  57 */       ReferenceCountUtil.release(frame);
/*  58 */       promise.setFailure(new Http3Exception(Http3ErrorCode.H3_FRAME_UNEXPECTED, "Frame of type " + type + " unexpected as we received a GOAWAY already."));
/*     */       
/*  60 */       ctx.close();
/*  61 */       return false;
/*     */     } 
/*  63 */     if (frame instanceof Http3PushPromiseFrame) {
/*     */ 
/*     */       
/*  66 */       Http3FrameValidationUtils.frameTypeUnexpected(promise, frame);
/*  67 */       return false;
/*     */     } 
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static long validateHeaderFrameRead(Http3HeadersFrame headersFrame, ChannelHandlerContext ctx, Http3RequestStreamCodecState decodeState) {
/*  74 */     if (headersFrame.headers().contains(HttpHeaderNames.CONNECTION)) {
/*  75 */       headerUnexpected(ctx, headersFrame, "connection header included");
/*  76 */       return -2L;
/*     */     } 
/*  78 */     CharSequence value = (CharSequence)headersFrame.headers().get(HttpHeaderNames.TE);
/*  79 */     if (value != null && !HttpHeaderValues.TRAILERS.equals(value)) {
/*  80 */       headerUnexpected(ctx, headersFrame, "te header field included with invalid value: " + value);
/*  81 */       return -2L;
/*     */     } 
/*  83 */     if (decodeState.receivedFinalHeaders()) {
/*  84 */       long length = HttpUtil.normalizeAndGetContentLength(headersFrame
/*  85 */           .headers().getAll(HttpHeaderNames.CONTENT_LENGTH), false, true);
/*  86 */       if (length != -1L) {
/*  87 */         headersFrame.headers().setLong(HttpHeaderNames.CONTENT_LENGTH, length);
/*     */       }
/*  89 */       return length;
/*     */     } 
/*  91 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   static long validateDataFrameRead(Http3DataFrame dataFrame, ChannelHandlerContext ctx, long expectedLength, long seenLength, boolean clientHeadRequest) {
/*     */     try {
/*  97 */       return verifyContentLength(dataFrame.content().readableBytes(), expectedLength, seenLength, false, clientHeadRequest);
/*     */     }
/*  99 */     catch (Http3Exception e) {
/* 100 */       ReferenceCountUtil.release(dataFrame);
/* 101 */       failStream(ctx, e);
/* 102 */       return -2L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean validateOnStreamClosure(ChannelHandlerContext ctx, long expectedLength, long seenLength, boolean clientHeadRequest) {
/*     */     try {
/* 109 */       verifyContentLength(0, expectedLength, seenLength, true, clientHeadRequest);
/* 110 */       return true;
/* 111 */     } catch (Http3Exception e) {
/* 112 */       ctx.fireExceptionCaught(e);
/* 113 */       Http3CodecUtils.streamError(ctx, e.errorCode());
/* 114 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void sendStreamAbandonedIfRequired(ChannelHandlerContext ctx, QpackAttributes qpackAttributes, QpackDecoder qpackDecoder, Http3RequestStreamCodecState decodeState) {
/* 120 */     if (!qpackAttributes.dynamicTableDisabled() && !decodeState.terminated()) {
/* 121 */       long streamId = ((QuicStreamChannel)ctx.channel()).streamId();
/* 122 */       if (qpackAttributes.decoderStreamAvailable()) {
/* 123 */         qpackDecoder.streamAbandoned(qpackAttributes.decoderStream(), streamId);
/*     */       } else {
/* 125 */         qpackAttributes.whenDecoderStreamAvailable(future -> {
/*     */               if (future.isSuccess()) {
/*     */                 qpackDecoder.streamAbandoned(qpackAttributes.decoderStream(), streamId);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void headerUnexpected(ChannelHandlerContext ctx, Http3RequestStreamFrame frame, String msg) {
/* 137 */     ReferenceCountUtil.release(frame);
/* 138 */     failStream(ctx, new Http3Exception(Http3ErrorCode.H3_MESSAGE_ERROR, msg));
/*     */   }
/*     */   
/*     */   private static void failStream(ChannelHandlerContext ctx, Http3Exception cause) {
/* 142 */     ctx.fireExceptionCaught(cause);
/* 143 */     Http3CodecUtils.streamError(ctx, cause.errorCode());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long verifyContentLength(int length, long expectedLength, long seenLength, boolean end, boolean clientHeadRequest) throws Http3Exception {
/* 149 */     seenLength += length;
/* 150 */     if (expectedLength != -1L && (seenLength > expectedLength || (!clientHeadRequest && end && seenLength != expectedLength)))
/*     */     {
/* 152 */       throw new Http3Exception(Http3ErrorCode.H3_MESSAGE_ERROR, "Expected content-length " + expectedLength + " != " + seenLength + ".");
/*     */     }
/*     */ 
/*     */     
/* 156 */     return seenLength;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamValidationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */