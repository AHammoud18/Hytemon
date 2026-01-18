/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamType;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import java.util.Objects;
/*     */ import java.util.function.LongFunction;
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
/*     */ public abstract class Http3ConnectionHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   final Http3FrameCodec.Http3FrameCodecFactory codecFactory;
/*     */   final LongFunction<ChannelHandler> unknownInboundStreamHandlerFactory;
/*     */   final boolean disableQpackDynamicTable;
/*     */   final Http3ControlStreamInboundHandler localControlStreamHandler;
/*     */   final Http3ControlStreamOutboundHandler remoteControlStreamHandler;
/*     */   final QpackDecoder qpackDecoder;
/*     */   final QpackEncoder qpackEncoder;
/*     */   private boolean controlStreamCreationInProgress;
/*     */   final long maxTableCapacity;
/*     */   
/*     */   Http3ConnectionHandler(boolean server, @Nullable ChannelHandler inboundControlStreamHandler, @Nullable LongFunction<ChannelHandler> unknownInboundStreamHandlerFactory, @Nullable Http3SettingsFrame localSettings, boolean disableQpackDynamicTable) {
/*  65 */     this.unknownInboundStreamHandlerFactory = unknownInboundStreamHandlerFactory;
/*  66 */     this.disableQpackDynamicTable = disableQpackDynamicTable;
/*  67 */     if (localSettings == null) {
/*  68 */       localSettings = new DefaultHttp3SettingsFrame();
/*     */     } else {
/*  70 */       localSettings = DefaultHttp3SettingsFrame.copyOf(localSettings);
/*     */     } 
/*  72 */     Long maxFieldSectionSize = localSettings.get(Http3SettingsFrame.HTTP3_SETTINGS_MAX_FIELD_SECTION_SIZE);
/*  73 */     if (maxFieldSectionSize == null)
/*     */     {
/*  75 */       maxFieldSectionSize = Long.valueOf(Long.MAX_VALUE);
/*     */     }
/*  77 */     this.maxTableCapacity = localSettings.getOrDefault(Http3SettingsFrame.HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY, 0L).longValue();
/*  78 */     int maxBlockedStreams = Math.toIntExact(localSettings.getOrDefault(Http3SettingsFrame.HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS, 0L).longValue());
/*  79 */     this.qpackDecoder = new QpackDecoder(this.maxTableCapacity, maxBlockedStreams);
/*  80 */     this.qpackEncoder = new QpackEncoder();
/*  81 */     this.codecFactory = Http3FrameCodec.newFactory(this.qpackDecoder, maxFieldSectionSize.longValue(), this.qpackEncoder);
/*  82 */     this
/*  83 */       .remoteControlStreamHandler = new Http3ControlStreamOutboundHandler(server, localSettings, this.codecFactory.newCodec(Http3FrameTypeValidator.NO_VALIDATION, Http3RequestStreamCodecState.NO_STATE, Http3RequestStreamCodecState.NO_STATE));
/*  84 */     this.localControlStreamHandler = new Http3ControlStreamInboundHandler(server, inboundControlStreamHandler, this.qpackEncoder, this.remoteControlStreamHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createControlStreamIfNeeded(ChannelHandlerContext ctx) {
/*  89 */     if (!this.controlStreamCreationInProgress && Http3.getLocalControlStream(ctx.channel()) == null) {
/*  90 */       this.controlStreamCreationInProgress = true;
/*  91 */       QuicChannel channel = (QuicChannel)ctx.channel();
/*     */ 
/*     */ 
/*     */       
/*  95 */       channel.createStream(QuicStreamType.UNIDIRECTIONAL, (ChannelHandler)this.remoteControlStreamHandler)
/*  96 */         .addListener(f -> {
/*     */             if (!f.isSuccess()) {
/*     */               ctx.fireExceptionCaught(new Http3Exception(Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Unable to open control stream", f.cause()));
/*     */               ctx.close();
/*     */             } else {
/*     */               Http3.setLocalControlStream((Channel)channel, (QuicStreamChannel)f.getNow());
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isGoAwayReceived() {
/* 113 */     return this.localControlStreamHandler.isGoAwayReceived();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final ChannelHandler newCodec(Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/* 123 */     return this.codecFactory.newCodec(Http3RequestStreamFrameTypeValidator.INSTANCE, encodeState, decodeState);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final ChannelHandler newRequestStreamValidationHandler(QuicStreamChannel forStream, Http3RequestStreamCodecState encodeState, Http3RequestStreamCodecState decodeState) {
/* 129 */     QpackAttributes qpackAttributes = Http3.getQpackAttributes((Channel)forStream.parent());
/* 130 */     assert qpackAttributes != null;
/* 131 */     if (this.localControlStreamHandler.isServer()) {
/* 132 */       return Http3RequestStreamValidationHandler.newServerValidator(qpackAttributes, this.qpackDecoder, encodeState, decodeState);
/*     */     }
/*     */     
/* 135 */     Objects.requireNonNull(this.localControlStreamHandler); return Http3RequestStreamValidationHandler.newClientValidator(this.localControlStreamHandler::isGoAwayReceived, qpackAttributes, this.qpackDecoder, encodeState, decodeState);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final ChannelHandler newPushStreamValidationHandler(QuicStreamChannel forStream, Http3RequestStreamCodecState decodeState) {
/* 141 */     if (this.localControlStreamHandler.isServer()) {
/* 142 */       return (ChannelHandler)Http3PushStreamServerValidationHandler.INSTANCE;
/*     */     }
/* 144 */     QpackAttributes qpackAttributes = Http3.getQpackAttributes((Channel)forStream.parent());
/* 145 */     assert qpackAttributes != null;
/* 146 */     return (ChannelHandler)new Http3PushStreamClientValidationHandler(qpackAttributes, this.qpackDecoder, decodeState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/* 151 */     QuicChannel channel = (QuicChannel)ctx.channel();
/* 152 */     Http3.setQpackAttributes((Channel)channel, new QpackAttributes(channel, this.disableQpackDynamicTable));
/* 153 */     if (ctx.channel().isActive()) {
/* 154 */       createControlStreamIfNeeded(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) {
/* 160 */     createControlStreamIfNeeded(ctx);
/*     */     
/* 162 */     ctx.fireChannelActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 167 */     if (msg instanceof QuicStreamChannel) {
/* 168 */       QuicStreamChannel channel = (QuicStreamChannel)msg;
/* 169 */       switch (channel.type()) {
/*     */         case BIDIRECTIONAL:
/* 171 */           initBidirectionalStream(ctx, channel);
/*     */           break;
/*     */         case UNIDIRECTIONAL:
/* 174 */           initUnidirectionalStream(ctx, channel);
/*     */           break;
/*     */         default:
/* 177 */           throw new Error("Unexpected channel type: " + channel.type());
/*     */       } 
/*     */     } 
/* 180 */     ctx.fireChannelRead(msg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long maxTableCapacity() {
/* 200 */     return this.maxTableCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   abstract void initBidirectionalStream(ChannelHandlerContext paramChannelHandlerContext, QuicStreamChannel paramQuicStreamChannel);
/*     */   
/*     */   abstract void initUnidirectionalStream(ChannelHandlerContext paramChannelHandlerContext, QuicStreamChannel paramQuicStreamChannel);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */