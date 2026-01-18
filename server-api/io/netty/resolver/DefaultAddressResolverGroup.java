/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public final class DefaultAddressResolverGroup
/*    */   extends AddressResolverGroup<InetSocketAddress>
/*    */ {
/* 28 */   public static final DefaultAddressResolverGroup INSTANCE = new DefaultAddressResolverGroup();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AddressResolver<InetSocketAddress> newResolver(EventExecutor executor) throws Exception {
/* 34 */     return (new DefaultNameResolver(executor)).asAddressResolver();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\DefaultAddressResolverGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */