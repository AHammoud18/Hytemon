/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.AttributeMap;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Channel
/*     */   extends AttributeMap, ChannelOutboundInvoker, Comparable<Channel>
/*     */ {
/*     */   default boolean isWritable() {
/* 165 */     ChannelOutboundBuffer buf = unsafe().outboundBuffer();
/* 166 */     return (buf != null && buf.isWritable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long bytesBeforeUnwritable() {
/* 176 */     ChannelOutboundBuffer buf = unsafe().outboundBuffer();
/*     */ 
/*     */     
/* 179 */     return (buf != null) ? buf.bytesBeforeUnwritable() : 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long bytesBeforeWritable() {
/* 189 */     ChannelOutboundBuffer buf = unsafe().outboundBuffer();
/*     */ 
/*     */     
/* 192 */     return (buf != null) ? buf.bytesBeforeWritable() : Long.MAX_VALUE;
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
/*     */   default ByteBufAllocator alloc() {
/* 209 */     return config().getAllocator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T> T getOption(ChannelOption<T> option) {
/* 216 */     return config().getOption(option);
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
/*     */   default <T> boolean setOption(ChannelOption<T> option, T value) {
/* 240 */     return config().setOption(option, value);
/*     */   }
/*     */ 
/*     */   
/*     */   default Channel read() {
/* 245 */     pipeline().read();
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default Channel flush() {
/* 251 */     pipeline().flush();
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg) {
/* 257 */     return pipeline().writeAndFlush(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 262 */     return pipeline().writeAndFlush(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg, ChannelPromise promise) {
/* 267 */     return pipeline().write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg) {
/* 272 */     return pipeline().write(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister(ChannelPromise promise) {
/* 277 */     return pipeline().deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close(ChannelPromise promise) {
/* 282 */     return pipeline().close(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect(ChannelPromise promise) {
/* 287 */     return pipeline().disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 292 */     return pipeline().connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 297 */     return pipeline().connect(remoteAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/* 302 */     return pipeline().bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture deregister() {
/* 307 */     return pipeline().deregister();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture close() {
/* 312 */     return pipeline().close();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture disconnect() {
/* 317 */     return pipeline().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 322 */     return pipeline().connect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture connect(SocketAddress remoteAddress) {
/* 327 */     return pipeline().connect(remoteAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture bind(SocketAddress localAddress) {
/* 332 */     return pipeline().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise newPromise() {
/* 337 */     return pipeline().newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelProgressivePromise newProgressivePromise() {
/* 342 */     return pipeline().newProgressivePromise();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newSucceededFuture() {
/* 347 */     return pipeline().newSucceededFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelFuture newFailedFuture(Throwable cause) {
/* 352 */     return pipeline().newFailedFuture(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   default ChannelPromise voidPromise() {
/* 357 */     return pipeline().voidPromise();
/*     */   }
/*     */   
/*     */   ChannelId id();
/*     */   
/*     */   EventLoop eventLoop();
/*     */   
/*     */   Channel parent();
/*     */   
/*     */   ChannelConfig config();
/*     */   
/*     */   boolean isOpen();
/*     */   
/*     */   boolean isRegistered();
/*     */   
/*     */   boolean isActive();
/*     */   
/*     */   ChannelMetadata metadata();
/*     */   
/*     */   SocketAddress localAddress();
/*     */   
/*     */   SocketAddress remoteAddress();
/*     */   
/*     */   ChannelFuture closeFuture();
/*     */   
/*     */   Unsafe unsafe();
/*     */   
/*     */   ChannelPipeline pipeline();
/*     */   
/*     */   public static interface Unsafe {
/*     */     RecvByteBufAllocator.Handle recvBufAllocHandle();
/*     */     
/*     */     SocketAddress localAddress();
/*     */     
/*     */     SocketAddress remoteAddress();
/*     */     
/*     */     void register(EventLoop param1EventLoop, ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void bind(SocketAddress param1SocketAddress, ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void connect(SocketAddress param1SocketAddress1, SocketAddress param1SocketAddress2, ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void disconnect(ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void close(ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void closeForcibly();
/*     */     
/*     */     void deregister(ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void beginRead();
/*     */     
/*     */     void write(Object param1Object, ChannelPromise param1ChannelPromise);
/*     */     
/*     */     void flush();
/*     */     
/*     */     ChannelPromise voidPromise();
/*     */     
/*     */     ChannelOutboundBuffer outboundBuffer();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\Channel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */