/*    */ package io.netty.channel.epoll;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class VSockAddress
/*    */   extends SocketAddress
/*    */ {
/*    */   private static final long serialVersionUID = 8600894096347158429L;
/*    */   public static final int VMADDR_CID_ANY = -1;
/*    */   public static final int VMADDR_CID_HYPERVISOR = 0;
/*    */   public static final int VMADDR_CID_LOCAL = 1;
/*    */   public static final int VMADDR_CID_HOST = 2;
/*    */   public static final int VMADDR_PORT_ANY = -1;
/*    */   private final int cid;
/*    */   private final int port;
/*    */   
/*    */   public VSockAddress(int cid, int port) {
/* 40 */     this.cid = cid;
/* 41 */     this.port = port;
/*    */   }
/*    */   
/*    */   public int getCid() {
/* 45 */     return this.cid;
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 49 */     return this.port;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "VSockAddress{cid=" + this.cid + ", port=" + this.port + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 62 */     if (this == o) {
/* 63 */       return true;
/*    */     }
/* 65 */     if (!(o instanceof VSockAddress)) {
/* 66 */       return false;
/*    */     }
/*    */     
/* 69 */     VSockAddress that = (VSockAddress)o;
/*    */     
/* 71 */     return (this.cid == that.cid && this.port == that.port);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     int result = this.cid;
/* 77 */     result = 31 * result + this.port;
/* 78 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\VSockAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */