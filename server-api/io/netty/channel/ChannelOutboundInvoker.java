/*     */ package io.netty.channel;
/*     */ 
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
/*     */ public interface ChannelOutboundInvoker
/*     */ {
/*     */   default ChannelFuture bind(SocketAddress localAddress) {
/*  36 */     return bind(localAddress, newPromise());
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
/*     */   default ChannelFuture connect(SocketAddress remoteAddress) {
/*  53 */     return connect(remoteAddress, newPromise());
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
/*     */   default ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  67 */     return connect(remoteAddress, localAddress, newPromise());
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
/*     */   default ChannelFuture disconnect() {
/*  80 */     return disconnect(newPromise());
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
/*     */   default ChannelFuture close() {
/*  96 */     return close(newPromise());
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
/*     */   default ChannelFuture deregister() {
/* 111 */     return deregister(newPromise());
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
/*     */   ChannelFuture bind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture connect(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture disconnect(ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture close(ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture deregister(ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelOutboundInvoker read();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChannelFuture write(Object msg) {
/* 221 */     return write(msg, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture write(Object paramObject, ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelOutboundInvoker flush();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture writeAndFlush(Object paramObject, ChannelPromise paramChannelPromise);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChannelFuture writeAndFlush(Object msg) {
/* 245 */     return writeAndFlush(msg, newPromise());
/*     */   }
/*     */   
/*     */   ChannelPromise newPromise();
/*     */   
/*     */   ChannelProgressivePromise newProgressivePromise();
/*     */   
/*     */   ChannelFuture newSucceededFuture();
/*     */   
/*     */   ChannelFuture newFailedFuture(Throwable paramThrowable);
/*     */   
/*     */   ChannelPromise voidPromise();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelOutboundInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */