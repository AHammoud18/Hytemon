/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import java.util.List;
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.Supplier;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ abstract class Http3UnidirectionalStreamInboundHandler
/*     */   extends ByteToMessageDecoder
/*     */ {
/*  43 */   private static final AttributeKey<Boolean> REMOTE_CONTROL_STREAM = AttributeKey.valueOf("H3_REMOTE_CONTROL_STREAM");
/*     */   
/*  45 */   private static final AttributeKey<Boolean> REMOTE_QPACK_DECODER_STREAM = AttributeKey.valueOf("H3_REMOTE_QPACK_DECODER_STREAM");
/*     */   
/*  47 */   private static final AttributeKey<Boolean> REMOTE_QPACK_ENCODER_STREAM = AttributeKey.valueOf("H3_REMOTE_QPACK_ENCODER_STREAM");
/*     */   
/*     */   final Http3FrameCodec.Http3FrameCodecFactory codecFactory;
/*     */   
/*     */   final Http3ControlStreamInboundHandler localControlStreamHandler;
/*     */   
/*     */   final Http3ControlStreamOutboundHandler remoteControlStreamHandler;
/*     */   
/*     */   final Supplier<ChannelHandler> qpackEncoderHandlerFactory;
/*     */   
/*     */   final Supplier<ChannelHandler> qpackDecoderHandlerFactory;
/*     */   
/*     */   final LongFunction<ChannelHandler> unknownStreamHandlerFactory;
/*     */   
/*     */   Http3UnidirectionalStreamInboundHandler(Http3FrameCodec.Http3FrameCodecFactory codecFactory, Http3ControlStreamInboundHandler localControlStreamHandler, Http3ControlStreamOutboundHandler remoteControlStreamHandler, @Nullable LongFunction<ChannelHandler> unknownStreamHandlerFactory, Supplier<ChannelHandler> qpackEncoderHandlerFactory, Supplier<ChannelHandler> qpackDecoderHandlerFactory) {
/*  62 */     this.codecFactory = codecFactory;
/*  63 */     this.localControlStreamHandler = localControlStreamHandler;
/*  64 */     this.remoteControlStreamHandler = remoteControlStreamHandler;
/*  65 */     this.qpackEncoderHandlerFactory = qpackEncoderHandlerFactory;
/*  66 */     this.qpackDecoderHandlerFactory = qpackDecoderHandlerFactory;
/*  67 */     if (unknownStreamHandlerFactory == null)
/*     */     {
/*  69 */       unknownStreamHandlerFactory = (type -> ReleaseHandler.INSTANCE);
/*     */     }
/*  71 */     this.unknownStreamHandlerFactory = unknownStreamHandlerFactory;
/*     */   }
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
/*     */     int pushIdLen;
/*     */     long pushId;
/*  76 */     if (!in.isReadable()) {
/*     */       return;
/*     */     }
/*  79 */     int len = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/*  80 */     if (in.readableBytes() < len) {
/*     */       return;
/*     */     }
/*  83 */     long type = Http3CodecUtils.readVariableLengthInteger(in, len);
/*  84 */     switch ((int)type) {
/*     */       case 0:
/*  86 */         initControlStream(ctx);
/*     */         return;
/*     */       case 1:
/*  89 */         pushIdLen = Http3CodecUtils.numBytesForVariableLengthInteger(in.getByte(in.readerIndex()));
/*  90 */         if (in.readableBytes() < pushIdLen) {
/*     */           return;
/*     */         }
/*  93 */         pushId = Http3CodecUtils.readVariableLengthInteger(in, pushIdLen);
/*  94 */         initPushStream(ctx, pushId);
/*     */         return;
/*     */       
/*     */       case 2:
/*  98 */         initQpackEncoderStream(ctx);
/*     */         return;
/*     */       
/*     */       case 3:
/* 102 */         initQpackDecoderStream(ctx);
/*     */         return;
/*     */     } 
/* 105 */     initUnknownStream(ctx, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initControlStream(ChannelHandlerContext ctx) {
/* 115 */     if (ctx.channel().parent().attr(REMOTE_CONTROL_STREAM).setIfAbsent(Boolean.valueOf(true)) == null) {
/* 116 */       ctx.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)this.localControlStreamHandler });
/*     */       
/* 118 */       ctx.pipeline().replace((ChannelHandler)this, null, this.codecFactory
/* 119 */           .newCodec(Http3ControlStreamFrameTypeValidator.INSTANCE, Http3RequestStreamCodecState.NO_STATE, Http3RequestStreamCodecState.NO_STATE));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 124 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Received multiple control streams.", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean ensureStreamNotExistsYet(ChannelHandlerContext ctx, AttributeKey<Boolean> key) {
/* 130 */     return (ctx.channel().parent().attr(key).setIfAbsent(Boolean.valueOf(true)) == null);
/*     */   }
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
/*     */   private void initQpackEncoderStream(ChannelHandlerContext ctx) {
/* 145 */     if (ensureStreamNotExistsYet(ctx, REMOTE_QPACK_ENCODER_STREAM)) {
/*     */       
/* 147 */       ctx.pipeline().replace((ChannelHandler)this, null, this.qpackEncoderHandlerFactory.get());
/*     */     }
/*     */     else {
/*     */       
/* 151 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Received multiple QPACK encoder streams.", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initQpackDecoderStream(ChannelHandlerContext ctx) {
/* 161 */     if (ensureStreamNotExistsYet(ctx, REMOTE_QPACK_DECODER_STREAM)) {
/* 162 */       ctx.pipeline().replace((ChannelHandler)this, null, this.qpackDecoderHandlerFactory.get());
/*     */     }
/*     */     else {
/*     */       
/* 166 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Received multiple QPACK decoder streams.", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void initPushStream(ChannelHandlerContext paramChannelHandlerContext, long paramLong);
/*     */ 
/*     */   
/*     */   private void initUnknownStream(ChannelHandlerContext ctx, long streamType) {
/* 176 */     ctx.pipeline().replace((ChannelHandler)this, null, this.unknownStreamHandlerFactory.apply(streamType));
/*     */   }
/*     */   
/*     */   static final class ReleaseHandler extends ChannelInboundHandlerAdapter {
/* 180 */     static final ReleaseHandler INSTANCE = new ReleaseHandler();
/*     */ 
/*     */     
/*     */     public boolean isSharable() {
/* 184 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 189 */       ReferenceCountUtil.release(msg);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3UnidirectionalStreamInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */