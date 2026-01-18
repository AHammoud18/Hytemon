/*    */ package io.netty.handler.ipfilter;
/*    */ 
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelFutureListener;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ @Sharable
/*    */ public class UniqueIpFilter
/*    */   extends AbstractRemoteAddressFilter<InetSocketAddress>
/*    */ {
/* 36 */   private final Set<InetAddress> connected = ConcurrentHashMap.newKeySet();
/*    */ 
/*    */   
/*    */   protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
/* 40 */     final InetAddress remoteIp = remoteAddress.getAddress();
/* 41 */     if (!this.connected.add(remoteIp)) {
/* 42 */       return false;
/*    */     }
/* 44 */     ctx.channel().closeFuture().addListener((GenericFutureListener)new ChannelFutureListener()
/*    */         {
/*    */           public void operationComplete(ChannelFuture future) throws Exception {
/* 47 */             UniqueIpFilter.this.connected.remove(remoteIp);
/*    */           }
/*    */         });
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\UniqueIpFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */