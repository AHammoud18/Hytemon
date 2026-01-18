/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import java.net.InetAddress;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InetNameResolver
/*    */   extends SimpleNameResolver<InetAddress>
/*    */ {
/*    */   private volatile AddressResolver<InetSocketAddress> addressResolver;
/*    */   
/*    */   protected InetNameResolver(EventExecutor executor) {
/* 35 */     super(executor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AddressResolver<InetSocketAddress> asAddressResolver() {
/* 43 */     AddressResolver<InetSocketAddress> result = this.addressResolver;
/* 44 */     if (result == null) {
/* 45 */       synchronized (this) {
/* 46 */         result = this.addressResolver;
/* 47 */         if (result == null) {
/* 48 */           this.addressResolver = result = new InetSocketAddressResolver(executor(), this);
/*    */         }
/*    */       } 
/*    */     }
/* 52 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\InetNameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */