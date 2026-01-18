/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import io.netty.util.NetUtil;
/*    */ import java.net.Inet4Address;
/*    */ import java.net.Inet6Address;
/*    */ import java.net.InetAddress;
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
/*    */ @Deprecated
/*    */ public enum InternetProtocolFamily
/*    */ {
/* 31 */   IPv4((Class)Inet4Address.class, 1),
/* 32 */   IPv6((Class)Inet6Address.class, 2);
/*    */   
/*    */   private final Class<? extends InetAddress> addressType;
/*    */   private final int addressNumber;
/*    */   
/*    */   InternetProtocolFamily(Class<? extends InetAddress> addressType, int addressNumber) {
/* 38 */     this.addressType = addressType;
/* 39 */     this.addressNumber = addressNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<? extends InetAddress> addressType() {
/* 46 */     return this.addressType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int addressNumber() {
/* 55 */     return this.addressNumber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InetAddress localhost() {
/* 62 */     switch (this) {
/*    */       case IPv4:
/* 64 */         return NetUtil.LOCALHOST4;
/*    */       case IPv6:
/* 66 */         return NetUtil.LOCALHOST6;
/*    */     } 
/* 68 */     throw new IllegalStateException("Unsupported family " + this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternetProtocolFamily of(InetAddress address) {
/* 76 */     if (address instanceof Inet4Address) {
/* 77 */       return IPv4;
/*    */     }
/* 79 */     if (address instanceof Inet6Address) {
/* 80 */       return IPv6;
/*    */     }
/* 82 */     throw new IllegalArgumentException("address " + address + " not supported");
/*    */   }
/*    */   
/*    */   public SocketProtocolFamily toSocketProtocolFamily() {
/* 86 */     switch (this) {
/*    */       case IPv4:
/* 88 */         return SocketProtocolFamily.INET;
/*    */       case IPv6:
/* 90 */         return SocketProtocolFamily.INET6;
/*    */     } 
/* 92 */     throw new IllegalStateException();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\InternetProtocolFamily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */