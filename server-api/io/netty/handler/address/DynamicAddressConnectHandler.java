/*    */ package io.netty.handler.address;
/*    */ 
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelFutureListener;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DynamicAddressConnectHandler
/*    */   extends ChannelOutboundHandlerAdapter
/*    */ {
/*    */   public final void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*    */     SocketAddress remote, local;
/*    */     try {
/* 43 */       remote = remoteAddress(remoteAddress, localAddress);
/* 44 */       local = localAddress(remoteAddress, localAddress);
/* 45 */     } catch (Exception e) {
/* 46 */       promise.setFailure(e);
/*    */       return;
/*    */     } 
/* 49 */     ctx.connect(remote, local, promise).addListener((GenericFutureListener)new ChannelFutureListener()
/*    */         {
/*    */           public void operationComplete(ChannelFuture future) {
/* 52 */             if (future.isSuccess())
/*    */             {
/*    */               
/* 55 */               future.channel().pipeline().remove((ChannelHandler)DynamicAddressConnectHandler.this);
/*    */             }
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SocketAddress localAddress(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 69 */     return localAddress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SocketAddress remoteAddress(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 80 */     return remoteAddress;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\address\DynamicAddressConnectHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */