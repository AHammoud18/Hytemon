/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.handler.codec.quic.QuicException;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class Http3RequestStreamInboundHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  33 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http3RequestStreamInboundHandler.class);
/*     */ 
/*     */   
/*     */   public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  37 */     if (msg instanceof Http3UnknownFrame) {
/*  38 */       channelRead(ctx, (Http3UnknownFrame)msg);
/*  39 */     } else if (msg instanceof Http3HeadersFrame) {
/*  40 */       channelRead(ctx, (Http3HeadersFrame)msg);
/*  41 */     } else if (msg instanceof Http3DataFrame) {
/*  42 */       channelRead(ctx, (Http3DataFrame)msg);
/*     */     } else {
/*  44 */       super.channelRead(ctx, msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/*  50 */     if (evt == ChannelInputShutdownEvent.INSTANCE) {
/*  51 */       channelInputClosed(ctx);
/*     */     }
/*  53 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
/*  58 */     if (cause instanceof QuicException) {
/*  59 */       handleQuicException(ctx, (QuicException)cause);
/*  60 */     } else if (cause instanceof Http3Exception) {
/*  61 */       handleHttp3Exception(ctx, (Http3Exception)cause);
/*     */     } else {
/*  63 */       ctx.fireExceptionCaught(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void channelRead(ChannelHandlerContext paramChannelHandlerContext, Http3HeadersFrame paramHttp3HeadersFrame) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void channelRead(ChannelHandlerContext paramChannelHandlerContext, Http3DataFrame paramHttp3DataFrame) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void channelInputClosed(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void channelRead(ChannelHandlerContext ctx, Http3UnknownFrame frame) {
/* 102 */     frame.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleQuicException(ChannelHandlerContext ctx, QuicException exception) {
/* 112 */     logger.debug("Caught QuicException on channel {}", ctx.channel(), exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleHttp3Exception(ChannelHandlerContext ctx, Http3Exception exception) {
/* 123 */     logger.error("Caught Http3Exception on channel {}", ctx.channel(), exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected final QuicStreamChannel controlStream(ChannelHandlerContext ctx) {
/* 135 */     return Http3.getLocalControlStream(ctx.channel().parent());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */