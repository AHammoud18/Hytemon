/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import java.util.List;
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
/*     */ final class QpackDecoderHandler
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private boolean discard;
/*     */   private final QpackEncoder qpackEncoder;
/*     */   
/*     */   QpackDecoderHandler(QpackEncoder qpackEncoder) {
/*  35 */     this.qpackEncoder = qpackEncoder;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  40 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*  43 */     if (this.discard) {
/*  44 */       in.skipBytes(in.readableBytes());
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     byte b = in.getByte(in.readerIndex());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     if ((b & 0x80) == 128) {
/*  57 */       long streamId = QpackUtil.decodePrefixedInteger(in, 7);
/*  58 */       if (streamId < 0L) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/*  63 */         this.qpackEncoder.sectionAcknowledgment(streamId);
/*  64 */       } catch (QpackException e) {
/*  65 */         Http3CodecUtils.connectionError(ctx, new Http3Exception(Http3ErrorCode.QPACK_DECODER_STREAM_ERROR, "Section acknowledgment decode failed.", e), true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     if ((b & 0xC0) == 64) {
/*  78 */       long streamId = QpackUtil.decodePrefixedInteger(in, 6);
/*  79 */       if (streamId < 0L) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/*  84 */         this.qpackEncoder.streamCancellation(streamId);
/*  85 */       } catch (QpackException e) {
/*  86 */         Http3CodecUtils.connectionError(ctx, new Http3Exception(Http3ErrorCode.QPACK_DECODER_STREAM_ERROR, "Stream cancellation decode failed.", e), true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if ((b & 0xC0) == 0) {
/*  99 */       int increment = QpackUtil.decodePrefixedIntegerAsInt(in, 6);
/* 100 */       if (increment == 0) {
/* 101 */         this.discard = true;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 106 */         Http3CodecUtils.connectionError(ctx, Http3ErrorCode.QPACK_DECODER_STREAM_ERROR, "Invalid increment '" + increment + "'.", false);
/*     */         
/*     */         return;
/*     */       } 
/* 110 */       if (increment < 0) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 115 */         this.qpackEncoder.insertCountIncrement(increment);
/* 116 */       } catch (QpackException e) {
/* 117 */         Http3CodecUtils.connectionError(ctx, new Http3Exception(Http3ErrorCode.QPACK_DECODER_STREAM_ERROR, "Insert count increment decode failed.", e), true);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 123 */     this.discard = true;
/* 124 */     Http3CodecUtils.connectionError(ctx, Http3ErrorCode.QPACK_DECODER_STREAM_ERROR, "Unknown decoder instruction '" + b + "'.", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) {
/* 130 */     ctx.fireChannelReadComplete();
/*     */ 
/*     */ 
/*     */     
/* 134 */     Http3CodecUtils.readIfNoAutoRead(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 139 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */     {
/* 141 */       Http3CodecUtils.criticalStreamClosed(ctx);
/*     */     }
/* 143 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) {
/* 149 */     Http3CodecUtils.criticalStreamClosed(ctx);
/* 150 */     ctx.fireChannelInactive();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackDecoderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */