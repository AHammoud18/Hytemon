/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.Collections;
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
/*    */ public class NoopAddressResolver
/*    */   extends AbstractAddressResolver<SocketAddress>
/*    */ {
/*    */   public NoopAddressResolver(EventExecutor executor) {
/* 33 */     super(executor, SocketAddress.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean doIsResolved(SocketAddress address) {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doResolve(SocketAddress unresolvedAddress, Promise<SocketAddress> promise) throws Exception {
/* 43 */     promise.setSuccess(unresolvedAddress);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doResolveAll(SocketAddress unresolvedAddress, Promise<List<SocketAddress>> promise) throws Exception {
/* 49 */     promise.setSuccess(Collections.singletonList(unresolvedAddress));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\NoopAddressResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */