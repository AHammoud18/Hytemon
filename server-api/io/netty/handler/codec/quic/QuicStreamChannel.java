/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelOutboundInvoker;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.socket.DuplexChannel;
/*     */ import java.net.SocketAddress;
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
/*     */ public interface QuicStreamChannel
/*     */   extends DuplexChannel
/*     */ {
/*     */   public static final ChannelFutureListener SHUTDOWN_OUTPUT;
/*     */   
/*     */   static {
/*  36 */     SHUTDOWN_OUTPUT = (f -> ((QuicStreamChannel)f.channel()).shutdownOutput());
/*     */   }
/*     */   
/*     */   default ChannelFuture bind(SocketAddress socketAddress) {
/*  40 */     return pipeline().bind(socketAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress) {
/*  45 */     return pipeline().connect(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  50 */     return pipeline().connect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect() {
/*  55 */     return pipeline().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close() {
/*  60 */     return pipeline().close();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister() {
/*  65 */     return pipeline().deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture bind(SocketAddress localAddress, ChannelPromise channelPromise) {
/*  70 */     return pipeline().bind(localAddress, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise channelPromise) {
/*  75 */     return pipeline().connect(remoteAddress, channelPromise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise channelPromise) {
/*  81 */     return pipeline().connect(remoteAddress, localAddress, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect(ChannelPromise channelPromise) {
/*  86 */     return pipeline().disconnect(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close(ChannelPromise channelPromise) {
/*  91 */     return pipeline().close(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister(ChannelPromise channelPromise) {
/*  96 */     return pipeline().deregister(channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg) {
/* 101 */     return pipeline().write(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg, ChannelPromise channelPromise) {
/* 106 */     return pipeline().write(msg, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg, ChannelPromise channelPromise) {
/* 111 */     return pipeline().writeAndFlush(msg, channelPromise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg) {
/* 116 */     return pipeline().writeAndFlush(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise newPromise() {
/* 121 */     return pipeline().newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelProgressivePromise newProgressivePromise() {
/* 126 */     return pipeline().newProgressivePromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newSucceededFuture() {
/* 131 */     return pipeline().newSucceededFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newFailedFuture(Throwable cause) {
/* 136 */     return pipeline().newFailedFuture(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise voidPromise() {
/* 141 */     return pipeline().voidPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture shutdownInput() {
/* 146 */     return shutdownInput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture shutdownInput(ChannelPromise promise) {
/* 151 */     return shutdownInput(0, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture shutdownOutput() {
/* 156 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture shutdown() {
/* 161 */     return shutdown(newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChannelFuture shutdown(int error) {
/* 171 */     return shutdown(error, newPromise());
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
/*     */   default ChannelFuture shutdownInput(int error) {
/* 191 */     return shutdownInput(error, newPromise());
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
/*     */   default ChannelFuture shutdownOutput(int error) {
/* 216 */     return shutdownOutput(error, newPromise());
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
/*     */   default ChannelFuture updatePriority(QuicStreamPriority priority) {
/* 277 */     return updatePriority(priority, newPromise());
/*     */   }
/*     */   
/*     */   ChannelFuture shutdown(int paramInt, ChannelPromise paramChannelPromise);
/*     */   
/*     */   ChannelFuture shutdownInput(int paramInt, ChannelPromise paramChannelPromise);
/*     */   
/*     */   ChannelFuture shutdownOutput(int paramInt, ChannelPromise paramChannelPromise);
/*     */   
/*     */   QuicStreamAddress localAddress();
/*     */   
/*     */   QuicStreamAddress remoteAddress();
/*     */   
/*     */   boolean isLocalCreated();
/*     */   
/*     */   QuicStreamType type();
/*     */   
/*     */   long streamId();
/*     */   
/*     */   @Nullable
/*     */   QuicStreamPriority priority();
/*     */   
/*     */   ChannelFuture updatePriority(QuicStreamPriority paramQuicStreamPriority, ChannelPromise paramChannelPromise);
/*     */   
/*     */   QuicChannel parent();
/*     */   
/*     */   QuicStreamChannel read();
/*     */   
/*     */   QuicStreamChannel flush();
/*     */   
/*     */   QuicStreamChannelConfig config();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */