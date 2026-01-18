/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamType;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import java.nio.channels.ClosedChannelException;
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
/*     */ final class Http3ControlStreamInboundHandler
/*     */   extends Http3FrameTypeInboundValidationHandler<Http3ControlStreamFrame>
/*     */ {
/*     */   final boolean server;
/*     */   private final ChannelHandler controlFrameHandler;
/*     */   private final QpackEncoder qpackEncoder;
/*     */   private final Http3ControlStreamOutboundHandler remoteControlStreamHandler;
/*     */   private boolean firstFrameRead;
/*     */   private Long receivedGoawayId;
/*     */   private Long receivedMaxPushId;
/*     */   
/*     */   Http3ControlStreamInboundHandler(boolean server, @Nullable ChannelHandler controlFrameHandler, QpackEncoder qpackEncoder, Http3ControlStreamOutboundHandler remoteControlStreamHandler) {
/*  57 */     super(Http3ControlStreamFrame.class);
/*  58 */     this.server = server;
/*  59 */     this.controlFrameHandler = controlFrameHandler;
/*  60 */     this.qpackEncoder = qpackEncoder;
/*  61 */     this.remoteControlStreamHandler = remoteControlStreamHandler;
/*     */   }
/*     */   
/*     */   boolean isServer() {
/*  65 */     return this.server;
/*     */   }
/*     */   
/*     */   boolean isGoAwayReceived() {
/*  69 */     return (this.receivedGoawayId != null);
/*     */   }
/*     */   
/*     */   long maxPushIdReceived() {
/*  73 */     return (this.receivedMaxPushId == null) ? -1L : this.receivedMaxPushId.longValue();
/*     */   }
/*     */   
/*     */   private boolean forwardControlFrames() {
/*  77 */     return (this.controlFrameHandler != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  82 */     super.handlerAdded(ctx);
/*     */     
/*  84 */     if (this.controlFrameHandler != null) {
/*  85 */       ctx.pipeline().addLast(new ChannelHandler[] { this.controlFrameHandler });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void readFrameDiscarded(ChannelHandlerContext ctx, Object discardedFrame) {
/*  91 */     if (!this.firstFrameRead && !(discardedFrame instanceof Http3SettingsFrame)) {
/*  92 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_MISSING_SETTINGS, "Missing settings frame.", forwardControlFrames());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void channelRead(ChannelHandlerContext ctx, Http3ControlStreamFrame frame) throws QpackException {
/*  98 */     boolean valid, isSettingsFrame = frame instanceof Http3SettingsFrame;
/*  99 */     if (!this.firstFrameRead && !isSettingsFrame) {
/* 100 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_MISSING_SETTINGS, "Missing settings frame.", forwardControlFrames());
/* 101 */       ReferenceCountUtil.release(frame);
/*     */       return;
/*     */     } 
/* 104 */     if (this.firstFrameRead && isSettingsFrame) {
/* 105 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_FRAME_UNEXPECTED, "Second settings frame received.", forwardControlFrames());
/* 106 */       ReferenceCountUtil.release(frame);
/*     */       return;
/*     */     } 
/* 109 */     this.firstFrameRead = true;
/*     */ 
/*     */     
/* 112 */     if (isSettingsFrame) {
/* 113 */       valid = handleHttp3SettingsFrame(ctx, (Http3SettingsFrame)frame);
/* 114 */     } else if (frame instanceof Http3GoAwayFrame) {
/* 115 */       valid = handleHttp3GoAwayFrame(ctx, (Http3GoAwayFrame)frame);
/* 116 */     } else if (frame instanceof Http3MaxPushIdFrame) {
/* 117 */       valid = handleHttp3MaxPushIdFrame(ctx, (Http3MaxPushIdFrame)frame);
/* 118 */     } else if (frame instanceof Http3CancelPushFrame) {
/* 119 */       valid = handleHttp3CancelPushFrame(ctx, (Http3CancelPushFrame)frame);
/*     */     }
/*     */     else {
/*     */       
/* 123 */       assert frame instanceof Http3UnknownFrame;
/* 124 */       valid = true;
/*     */     } 
/*     */     
/* 127 */     if (!valid || this.controlFrameHandler == null) {
/* 128 */       ReferenceCountUtil.release(frame);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 134 */     ctx.fireChannelRead(frame);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean handleHttp3SettingsFrame(ChannelHandlerContext ctx, Http3SettingsFrame settingsFrame) throws QpackException {
/* 139 */     QuicChannel quicChannel = (QuicChannel)ctx.channel().parent();
/* 140 */     QpackAttributes qpackAttributes = Http3.getQpackAttributes((Channel)quicChannel);
/* 141 */     assert qpackAttributes != null;
/* 142 */     GenericFutureListener<Future<? super QuicStreamChannel>> closeOnFailure = future -> {
/*     */         if (!future.isSuccess()) {
/*     */           Http3CodecUtils.criticalStreamClosed(ctx);
/*     */         }
/*     */       };
/* 147 */     if (qpackAttributes.dynamicTableDisabled()) {
/* 148 */       this.qpackEncoder.configureDynamicTable(qpackAttributes, 0L, 0);
/* 149 */       return true;
/*     */     } 
/* 151 */     quicChannel.createStream(QuicStreamType.UNIDIRECTIONAL, (ChannelHandler)new QPackEncoderStreamInitializer(this.qpackEncoder, qpackAttributes, settingsFrame
/*     */           
/* 153 */           .getOrDefault(Http3SettingsFrame.HTTP3_SETTINGS_QPACK_MAX_TABLE_CAPACITY, 0L).longValue(), settingsFrame
/* 154 */           .getOrDefault(Http3SettingsFrame.HTTP3_SETTINGS_QPACK_BLOCKED_STREAMS, 0L).longValue()))
/* 155 */       .addListener(closeOnFailure);
/* 156 */     quicChannel.createStream(QuicStreamType.UNIDIRECTIONAL, (ChannelHandler)new QPackDecoderStreamInitializer(qpackAttributes))
/* 157 */       .addListener(closeOnFailure);
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleHttp3GoAwayFrame(ChannelHandlerContext ctx, Http3GoAwayFrame goAwayFrame) {
/* 162 */     long id = goAwayFrame.id();
/* 163 */     if (!this.server && id % 4L != 0L) {
/* 164 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_FRAME_UNEXPECTED, "GOAWAY received with ID of non-request stream.", 
/* 165 */           forwardControlFrames());
/* 166 */       return false;
/*     */     } 
/* 168 */     if (this.receivedGoawayId != null && id > this.receivedGoawayId.longValue()) {
/* 169 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "GOAWAY received with ID larger than previously received.", 
/* 170 */           forwardControlFrames());
/* 171 */       return false;
/*     */     } 
/* 173 */     this.receivedGoawayId = Long.valueOf(id);
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleHttp3MaxPushIdFrame(ChannelHandlerContext ctx, Http3MaxPushIdFrame frame) {
/* 178 */     long id = frame.id();
/* 179 */     if (!this.server) {
/* 180 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_FRAME_UNEXPECTED, "MAX_PUSH_ID received by client.", 
/* 181 */           forwardControlFrames());
/* 182 */       return false;
/*     */     } 
/* 184 */     if (this.receivedMaxPushId != null && id < this.receivedMaxPushId.longValue()) {
/* 185 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "MAX_PUSH_ID reduced limit.", forwardControlFrames());
/* 186 */       return false;
/*     */     } 
/* 188 */     this.receivedMaxPushId = Long.valueOf(id);
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleHttp3CancelPushFrame(ChannelHandlerContext ctx, Http3CancelPushFrame cancelPushFrame) {
/* 193 */     Long maxPushId = this.server ? this.receivedMaxPushId : this.remoteControlStreamHandler.sentMaxPushId();
/* 194 */     if (maxPushId == null || maxPushId.longValue() < cancelPushFrame.id()) {
/* 195 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "CANCEL_PUSH received with an ID greater than MAX_PUSH_ID.", 
/* 196 */           forwardControlFrames());
/* 197 */       return false;
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) {
/* 204 */     ctx.fireChannelReadComplete();
/*     */ 
/*     */ 
/*     */     
/* 208 */     Http3CodecUtils.readIfNoAutoRead(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 219 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */     {
/* 221 */       Http3CodecUtils.criticalStreamClosed(ctx);
/*     */     }
/* 223 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */   
/*     */   private static abstract class AbstractQPackStreamInitializer extends ChannelInboundHandlerAdapter {
/*     */     private final int streamType;
/*     */     protected final QpackAttributes attributes;
/*     */     
/*     */     AbstractQPackStreamInitializer(int streamType, QpackAttributes attributes) {
/* 231 */       this.streamType = streamType;
/* 232 */       this.attributes = attributes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void channelActive(ChannelHandlerContext ctx) {
/* 240 */       ByteBuf buffer = ctx.alloc().buffer(8);
/* 241 */       Http3CodecUtils.writeVariableLengthInteger(buffer, this.streamType);
/* 242 */       Http3CodecUtils.closeOnFailure(ctx.writeAndFlush(buffer));
/* 243 */       streamAvailable(ctx);
/* 244 */       ctx.fireChannelActive();
/*     */     }
/*     */ 
/*     */     
/*     */     public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/* 249 */       streamClosed(ctx);
/* 250 */       if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */       {
/* 252 */         Http3CodecUtils.criticalStreamClosed(ctx);
/*     */       }
/* 254 */       ctx.fireUserEventTriggered(evt);
/*     */     }
/*     */ 
/*     */     
/*     */     public void channelInactive(ChannelHandlerContext ctx) {
/* 259 */       streamClosed(ctx);
/*     */       
/* 261 */       Http3CodecUtils.criticalStreamClosed(ctx);
/* 262 */       ctx.fireChannelInactive();
/*     */     }
/*     */     
/*     */     protected abstract void streamAvailable(ChannelHandlerContext param1ChannelHandlerContext);
/*     */     
/*     */     protected abstract void streamClosed(ChannelHandlerContext param1ChannelHandlerContext);
/*     */   }
/*     */   
/*     */   private static final class QPackEncoderStreamInitializer
/*     */     extends AbstractQPackStreamInitializer {
/* 272 */     private static final ClosedChannelException ENCODER_STREAM_INACTIVE = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), ClosedChannelException.class, "streamClosed()");
/*     */     
/*     */     private final QpackEncoder encoder;
/*     */     private final long maxTableCapacity;
/*     */     private final long blockedStreams;
/*     */     
/*     */     QPackEncoderStreamInitializer(QpackEncoder encoder, QpackAttributes attributes, long maxTableCapacity, long blockedStreams) {
/* 279 */       super(2, attributes);
/* 280 */       this.encoder = encoder;
/* 281 */       this.maxTableCapacity = maxTableCapacity;
/* 282 */       this.blockedStreams = blockedStreams;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void streamAvailable(ChannelHandlerContext ctx) {
/* 287 */       QuicStreamChannel stream = (QuicStreamChannel)ctx.channel();
/* 288 */       this.attributes.encoderStream(stream);
/*     */       
/*     */       try {
/* 291 */         this.encoder.configureDynamicTable(this.attributes, this.maxTableCapacity, QpackUtil.toIntOrThrow(this.blockedStreams));
/* 292 */       } catch (QpackException e) {
/* 293 */         Http3CodecUtils.connectionError(ctx, new Http3Exception(Http3ErrorCode.QPACK_ENCODER_STREAM_ERROR, "Dynamic table configuration failed.", e), true);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void streamClosed(ChannelHandlerContext ctx) {
/* 300 */       this.attributes.encoderStreamInactive(ENCODER_STREAM_INACTIVE);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class QPackDecoderStreamInitializer
/*     */     extends AbstractQPackStreamInitializer {
/* 306 */     private static final ClosedChannelException DECODER_STREAM_INACTIVE = (ClosedChannelException)ThrowableUtil.unknownStackTrace(new ClosedChannelException(), ClosedChannelException.class, "streamClosed()");
/*     */     private QPackDecoderStreamInitializer(QpackAttributes attributes) {
/* 308 */       super(3, attributes);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void streamAvailable(ChannelHandlerContext ctx) {
/* 313 */       this.attributes.decoderStream((QuicStreamChannel)ctx.channel());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void streamClosed(ChannelHandlerContext ctx) {
/* 318 */       this.attributes.decoderStreamInactive(DECODER_STREAM_INACTIVE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ControlStreamInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */