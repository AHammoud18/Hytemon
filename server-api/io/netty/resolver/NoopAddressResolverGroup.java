/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
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
/*    */ public final class NoopAddressResolverGroup
/*    */   extends AddressResolverGroup<SocketAddress>
/*    */ {
/* 28 */   public static final NoopAddressResolverGroup INSTANCE = new NoopAddressResolverGroup();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AddressResolver<SocketAddress> newResolver(EventExecutor executor) throws Exception {
/* 34 */     return new NoopAddressResolver(executor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\NoopAddressResolverGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */