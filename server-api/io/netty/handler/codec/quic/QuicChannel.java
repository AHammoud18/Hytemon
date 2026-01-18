/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOutboundInvoker;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import java.net.SocketAddress;
/*     */ import javax.net.ssl.SSLEngine;
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
/*     */ public interface QuicChannel
/*     */   extends Channel
/*     */ {
/*     */   default ChannelFuture bind(SocketAddress localAddress) {
/*  38 */     return pipeline().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress) {
/*  43 */     return pipeline().connect(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  48 */     return pipeline().connect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect() {
/*  53 */     return pipeline().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close() {
/*  58 */     return pipeline().close();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister() {
/*  63 */     return pipeline().deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/*  68 */     return pipeline().bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  73 */     return pipeline().connect(remoteAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  78 */     return pipeline().connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect(ChannelPromise promise) {
/*  83 */     return pipeline().disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close(ChannelPromise promise) {
/*  88 */     return pipeline().close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister(ChannelPromise promise) {
/*  93 */     return pipeline().deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg) {
/*  98 */     return pipeline().write(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg, ChannelPromise promise) {
/* 103 */     return pipeline().write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 108 */     return pipeline().writeAndFlush(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg) {
/* 113 */     return pipeline().writeAndFlush(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise newPromise() {
/* 118 */     return pipeline().newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelProgressivePromise newProgressivePromise() {
/* 123 */     return pipeline().newProgressivePromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newSucceededFuture() {
/* 128 */     return pipeline().newSucceededFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newFailedFuture(Throwable cause) {
/* 133 */     return pipeline().newFailedFuture(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise voidPromise() {
/* 138 */     return pipeline().voidPromise();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Future<QuicStreamChannel> createStream(QuicStreamType type, @Nullable ChannelHandler handler) {
/* 238 */     return createStream(type, handler, eventLoop().newPromise());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default QuicStreamChannelBootstrap newStreamBootstrap() {
/* 264 */     return new QuicStreamChannelBootstrap(this);
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
/*     */   default ChannelFuture close(boolean applicationClose, int error, ByteBuf reason) {
/* 277 */     return close(applicationClose, error, reason, newPromise());
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
/*     */   
/*     */   default Future<QuicConnectionStats> collectStats() {
/* 298 */     return collectStats(eventLoop().newPromise());
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
/*     */   default Future<QuicConnectionPathStats> collectPathStats(int pathIdx) {
/* 315 */     return collectPathStats(pathIdx, eventLoop().newPromise());
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
/*     */   static QuicChannelBootstrap newBootstrap(Channel channel) {
/* 334 */     return new QuicChannelBootstrap(channel);
/*     */   }
/*     */   
/*     */   QuicChannel read();
/*     */   
/*     */   QuicChannel flush();
/*     */   
/*     */   QuicChannelConfig config();
/*     */   
/*     */   @Nullable
/*     */   SSLEngine sslEngine();
/*     */   
/*     */   long peerAllowedStreams(QuicStreamType paramQuicStreamType);
/*     */   
/*     */   boolean isTimedOut();
/*     */   
/*     */   @Nullable
/*     */   QuicTransportParameters peerTransportParameters();
/*     */   
/*     */   @Nullable
/*     */   QuicConnectionAddress localAddress();
/*     */   
/*     */   @Nullable
/*     */   QuicConnectionAddress remoteAddress();
/*     */   
/*     */   @Nullable
/*     */   SocketAddress localSocketAddress();
/*     */   
/*     */   @Nullable
/*     */   SocketAddress remoteSocketAddress();
/*     */   
/*     */   Future<QuicStreamChannel> createStream(QuicStreamType paramQuicStreamType, @Nullable ChannelHandler paramChannelHandler, Promise<QuicStreamChannel> paramPromise);
/*     */   
/*     */   ChannelFuture close(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf, ChannelPromise paramChannelPromise);
/*     */   
/*     */   Future<QuicConnectionStats> collectStats(Promise<QuicConnectionStats> paramPromise);
/*     */   
/*     */   Future<QuicConnectionPathStats> collectPathStats(int paramInt, Promise<QuicConnectionPathStats> paramPromise);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */