/*     */ package io.netty.handler.ipfilter;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public abstract class AbstractRemoteAddressFilter<T extends SocketAddress>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/*  42 */     handleNewChannel(ctx);
/*  43 */     ctx.fireChannelRegistered();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/*  48 */     if (!handleNewChannel(ctx)) {
/*  49 */       throw new IllegalStateException("cannot determine to accept or reject a channel: " + ctx.channel());
/*     */     }
/*  51 */     ctx.fireChannelActive();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean handleNewChannel(ChannelHandlerContext ctx) throws Exception {
/*  57 */     SocketAddress socketAddress = ctx.channel().remoteAddress();
/*     */ 
/*     */     
/*  60 */     if (socketAddress == null) {
/*  61 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  66 */     ctx.pipeline().remove((ChannelHandler)this);
/*     */     
/*  68 */     if (accept(ctx, (T)socketAddress)) {
/*  69 */       channelAccepted(ctx, (T)socketAddress);
/*     */     } else {
/*  71 */       ChannelFuture rejectedFuture = channelRejected(ctx, (T)socketAddress);
/*  72 */       if (rejectedFuture != null) {
/*  73 */         rejectedFuture.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */       } else {
/*  75 */         ctx.close();
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean accept(ChannelHandlerContext paramChannelHandlerContext, T paramT) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void channelAccepted(ChannelHandlerContext ctx, T remoteAddress) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelFuture channelRejected(ChannelHandlerContext ctx, T remoteAddress) {
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\AbstractRemoteAddressFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */