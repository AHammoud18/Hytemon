/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.FutureListener;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class InetSocketAddressResolver
/*    */   extends AbstractAddressResolver<InetSocketAddress>
/*    */ {
/*    */   final NameResolver<InetAddress> nameResolver;
/*    */   
/*    */   public InetSocketAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
/* 41 */     super(executor, InetSocketAddress.class);
/* 42 */     this.nameResolver = nameResolver;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doIsResolved(InetSocketAddress address) {
/* 47 */     return !address.isUnresolved();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doResolve(final InetSocketAddress unresolvedAddress, final Promise<InetSocketAddress> promise) throws Exception {
/* 55 */     this.nameResolver.resolve(unresolvedAddress.getHostName())
/* 56 */       .addListener((GenericFutureListener)new FutureListener<InetAddress>()
/*    */         {
/*    */           public void operationComplete(Future<InetAddress> future) throws Exception {
/* 59 */             if (future.isSuccess()) {
/* 60 */               promise.setSuccess(new InetSocketAddress((InetAddress)future.getNow(), unresolvedAddress.getPort()));
/*    */             } else {
/* 62 */               promise.setFailure(future.cause());
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doResolveAll(final InetSocketAddress unresolvedAddress, final Promise<List<InetSocketAddress>> promise) throws Exception {
/* 73 */     this.nameResolver.resolveAll(unresolvedAddress.getHostName())
/* 74 */       .addListener((GenericFutureListener)new FutureListener<List<InetAddress>>()
/*    */         {
/*    */           public void operationComplete(Future<List<InetAddress>> future) throws Exception {
/* 77 */             if (future.isSuccess()) {
/* 78 */               List<InetAddress> inetAddresses = (List<InetAddress>)future.getNow();
/*    */               
/* 80 */               List<InetSocketAddress> socketAddresses = new ArrayList<>(inetAddresses.size());
/* 81 */               for (InetAddress inetAddress : inetAddresses) {
/* 82 */                 socketAddresses.add(new InetSocketAddress(inetAddress, unresolvedAddress.getPort()));
/*    */               }
/* 84 */               promise.setSuccess(socketAddresses);
/*    */             } else {
/* 86 */               promise.setFailure(future.cause());
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 94 */     this.nameResolver.close();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\InetSocketAddressResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */