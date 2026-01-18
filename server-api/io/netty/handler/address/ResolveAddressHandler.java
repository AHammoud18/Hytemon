/*    */ package io.netty.handler.address;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.resolver.AddressResolver;
/*    */ import io.netty.resolver.AddressResolverGroup;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.FutureListener;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ @Sharable
/*    */ public class ResolveAddressHandler
/*    */   extends ChannelOutboundHandlerAdapter
/*    */ {
/*    */   private final AddressResolverGroup<? extends SocketAddress> resolverGroup;
/*    */   
/*    */   public ResolveAddressHandler(AddressResolverGroup<? extends SocketAddress> resolverGroup) {
/* 41 */     this.resolverGroup = (AddressResolverGroup<? extends SocketAddress>)ObjectUtil.checkNotNull(resolverGroup, "resolverGroup");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void connect(final ChannelHandlerContext ctx, SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/* 47 */     AddressResolver<? extends SocketAddress> resolver = this.resolverGroup.getResolver(ctx.executor());
/* 48 */     if (resolver.isSupported(remoteAddress) && !resolver.isResolved(remoteAddress)) {
/* 49 */       resolver.resolve(remoteAddress).addListener((GenericFutureListener)new FutureListener<SocketAddress>()
/*    */           {
/*    */             public void operationComplete(Future<SocketAddress> future) {
/* 52 */               Throwable cause = future.cause();
/* 53 */               if (cause != null) {
/* 54 */                 promise.setFailure(cause);
/*    */               } else {
/* 56 */                 ctx.connect((SocketAddress)future.getNow(), localAddress, promise);
/*    */               } 
/* 58 */               ctx.pipeline().remove((ChannelHandler)ResolveAddressHandler.this);
/*    */             }
/*    */           });
/*    */     } else {
/* 62 */       ctx.connect(remoteAddress, localAddress, promise);
/* 63 */       ctx.pipeline().remove((ChannelHandler)this);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\address\ResolveAddressHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */