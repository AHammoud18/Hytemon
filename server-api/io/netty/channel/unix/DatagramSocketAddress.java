/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import java.net.Inet6Address;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.UnknownHostException;
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
/*    */ public final class DatagramSocketAddress
/*    */   extends InetSocketAddress
/*    */ {
/*    */   private static final long serialVersionUID = 3094819287843178401L;
/*    */   private final int receivedAmount;
/*    */   private final DatagramSocketAddress localAddress;
/*    */   
/*    */   DatagramSocketAddress(byte[] addr, int scopeId, int port, int receivedAmount, DatagramSocketAddress local) throws UnknownHostException {
/* 38 */     super(newAddress(addr, scopeId), port);
/* 39 */     this.receivedAmount = receivedAmount;
/* 40 */     this.localAddress = local;
/*    */   }
/*    */   
/*    */   public DatagramSocketAddress localAddress() {
/* 44 */     return this.localAddress;
/*    */   }
/*    */   
/*    */   public int receivedAmount() {
/* 48 */     return this.receivedAmount;
/*    */   }
/*    */   
/*    */   private static InetAddress newAddress(byte[] bytes, int scopeId) throws UnknownHostException {
/* 52 */     if (bytes.length == 4) {
/* 53 */       return InetAddress.getByAddress(bytes);
/*    */     }
/* 55 */     return Inet6Address.getByAddress((String)null, bytes, scopeId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\DatagramSocketAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */