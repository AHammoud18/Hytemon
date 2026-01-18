/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ final class Http3ControlStreamOutboundHandler
/*     */   extends Http3FrameTypeDuplexValidationHandler<Http3ControlStreamFrame>
/*     */ {
/*     */   private final boolean server;
/*     */   private final ChannelHandler codec;
/*     */   private Long sentMaxPushId;
/*     */   private Long sendGoAwayId;
/*     */   private Http3SettingsFrame localSettings;
/*     */   
/*     */   Http3ControlStreamOutboundHandler(boolean server, Http3SettingsFrame localSettings, ChannelHandler codec) {
/*  38 */     super(Http3ControlStreamFrame.class);
/*  39 */     this.server = server;
/*  40 */     this.localSettings = (Http3SettingsFrame)ObjectUtil.checkNotNull(localSettings, "localSettings");
/*  41 */     this.codec = (ChannelHandler)ObjectUtil.checkNotNull(codec, "codec");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Long sentMaxPushId() {
/*  51 */     return this.sentMaxPushId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) {
/*  59 */     ByteBuf buffer = ctx.alloc().buffer(8);
/*  60 */     Http3CodecUtils.writeVariableLengthInteger(buffer, 0L);
/*  61 */     ctx.write(buffer);
/*     */ 
/*     */     
/*  64 */     ctx.pipeline().addFirst(new ChannelHandler[] { this.codec });
/*     */     
/*  66 */     assert this.localSettings != null;
/*     */     
/*  68 */     Http3CodecUtils.closeOnFailure(ctx.writeAndFlush(this.localSettings));
/*     */ 
/*     */     
/*  71 */     this.localSettings = null;
/*     */     
/*  73 */     ctx.fireChannelActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
/*  78 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent)
/*     */     {
/*  80 */       Http3CodecUtils.criticalStreamClosed(ctx);
/*     */     }
/*  82 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) {
/*  88 */     Http3CodecUtils.criticalStreamClosed(ctx);
/*  89 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   void write(ChannelHandlerContext ctx, Http3ControlStreamFrame msg, ChannelPromise promise) {
/*  94 */     if (msg instanceof Http3MaxPushIdFrame && !handleHttp3MaxPushIdFrame(promise, (Http3MaxPushIdFrame)msg)) {
/*  95 */       ReferenceCountUtil.release(msg); return;
/*     */     } 
/*  97 */     if (msg instanceof Http3GoAwayFrame && !handleHttp3GoAwayFrame(promise, (Http3GoAwayFrame)msg)) {
/*  98 */       ReferenceCountUtil.release(msg);
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     ctx.write(msg, promise);
/*     */   }
/*     */   
/*     */   private boolean handleHttp3MaxPushIdFrame(ChannelPromise promise, Http3MaxPushIdFrame maxPushIdFrame) {
/* 106 */     long id = maxPushIdFrame.id();
/*     */ 
/*     */     
/* 109 */     if (this.sentMaxPushId != null && id < this.sentMaxPushId.longValue()) {
/* 110 */       promise.setFailure(new Http3Exception(Http3ErrorCode.H3_ID_ERROR, "MAX_PUSH_ID reduced limit."));
/* 111 */       return false;
/*     */     } 
/*     */     
/* 114 */     this.sentMaxPushId = Long.valueOf(maxPushIdFrame.id());
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleHttp3GoAwayFrame(ChannelPromise promise, Http3GoAwayFrame goAwayFrame) {
/* 119 */     long id = goAwayFrame.id();
/*     */ 
/*     */     
/* 122 */     if (this.server && id % 4L != 0L) {
/* 123 */       promise.setFailure(new Http3Exception(Http3ErrorCode.H3_ID_ERROR, "GOAWAY id not valid : " + id));
/*     */       
/* 125 */       return false;
/*     */     } 
/*     */     
/* 128 */     if (this.sendGoAwayId != null && id > this.sendGoAwayId.longValue()) {
/* 129 */       promise.setFailure(new Http3Exception(Http3ErrorCode.H3_ID_ERROR, "GOAWAY id is bigger then the last sent: " + id + " > " + this.sendGoAwayId));
/*     */       
/* 131 */       return false;
/*     */     } 
/*     */     
/* 134 */     this.sendGoAwayId = Long.valueOf(id);
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSharable() {
/* 141 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ControlStreamOutboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */