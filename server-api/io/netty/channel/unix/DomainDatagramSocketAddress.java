/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public final class DomainDatagramSocketAddress
/*    */   extends DomainSocketAddress
/*    */ {
/*    */   private static final long serialVersionUID = -5925732678737768223L;
/*    */   private final DomainDatagramSocketAddress localAddress;
/*    */   private final int receivedAmount;
/*    */   
/*    */   public DomainDatagramSocketAddress(byte[] socketPath, int receivedAmount, DomainDatagramSocketAddress localAddress) {
/* 36 */     super(new String(socketPath, CharsetUtil.UTF_8));
/* 37 */     this.localAddress = localAddress;
/* 38 */     this.receivedAmount = receivedAmount;
/*    */   }
/*    */   
/*    */   public DomainDatagramSocketAddress localAddress() {
/* 42 */     return this.localAddress;
/*    */   }
/*    */   
/*    */   public int receivedAmount() {
/* 46 */     return this.receivedAmount;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\DomainDatagramSocketAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */