/*    */ package io.netty.bootstrap;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.resolver.AddressResolverGroup;
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
/*    */ public final class BootstrapConfig
/*    */   extends AbstractBootstrapConfig<Bootstrap, Channel>
/*    */ {
/*    */   BootstrapConfig(Bootstrap bootstrap) {
/* 29 */     super(bootstrap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocketAddress remoteAddress() {
/* 36 */     return this.bootstrap.remoteAddress();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AddressResolverGroup<?> resolver() {
/* 44 */     return this.bootstrap.resolver();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder buf = new StringBuilder(super.toString());
/* 50 */     buf.setLength(buf.length() - 1);
/* 51 */     AddressResolverGroup<?> resolver = resolver();
/* 52 */     if (resolver != null) {
/* 53 */       buf.append(", resolver: ")
/* 54 */         .append(resolver);
/*    */     }
/* 56 */     SocketAddress remoteAddress = remoteAddress();
/* 57 */     if (remoteAddress != null) {
/* 58 */       buf.append(", remoteAddress: ")
/* 59 */         .append(remoteAddress);
/*    */     }
/* 61 */     return buf.append(')').toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\BootstrapConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */