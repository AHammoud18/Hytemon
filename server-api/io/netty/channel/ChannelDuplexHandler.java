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
/*     */ 
/*     */ 
/*     */ public class ChannelDuplexHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   @Skip
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  41 */     ctx.bind(localAddress, promise);
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
/*     */   @Skip
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  54 */     ctx.connect(remoteAddress, localAddress, promise);
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
/*     */   @Skip
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  67 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Skip
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  79 */     ctx.close(promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Skip
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  91 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Skip
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 103 */     ctx.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Skip
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 115 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Skip
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 127 */     ctx.flush();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelDuplexHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */