/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.io.File;
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
/*    */ public class DomainSocketAddress
/*    */   extends SocketAddress
/*    */ {
/*    */   private static final long serialVersionUID = -6934618000832236893L;
/*    */   private final String socketPath;
/*    */   
/*    */   public DomainSocketAddress(String socketPath) {
/* 32 */     this.socketPath = (String)ObjectUtil.checkNotNull(socketPath, "socketPath");
/*    */   }
/*    */   
/*    */   public DomainSocketAddress(File file) {
/* 36 */     this(file.getPath());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String path() {
/* 43 */     return this.socketPath;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return path();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 53 */     if (this == o) {
/* 54 */       return true;
/*    */     }
/* 56 */     if (!(o instanceof DomainSocketAddress)) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     return ((DomainSocketAddress)o).socketPath.equals(this.socketPath);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     return this.socketPath.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\DomainSocketAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */