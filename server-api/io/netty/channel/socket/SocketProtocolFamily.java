/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import java.net.ProtocolFamily;
/*    */ import java.net.StandardProtocolFamily;
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
/*    */ public enum SocketProtocolFamily
/*    */   implements ProtocolFamily
/*    */ {
/* 28 */   INET,
/*    */ 
/*    */ 
/*    */   
/* 32 */   INET6,
/*    */ 
/*    */ 
/*    */   
/* 36 */   UNIX;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ProtocolFamily toJdkFamily() {
/* 46 */     switch (this) {
/*    */       case INET:
/* 48 */         return StandardProtocolFamily.INET;
/*    */       case INET6:
/* 50 */         return StandardProtocolFamily.INET6;
/*    */ 
/*    */       
/*    */       case null:
/* 54 */         return StandardProtocolFamily.valueOf("UNIX");
/*    */     } 
/* 56 */     throw new UnsupportedOperationException("ProtocolFamily cant be converted to something that is known by the JDKi: " + this);
/*    */   }
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
/*    */   public static SocketProtocolFamily of(ProtocolFamily family) {
/* 69 */     if (family instanceof StandardProtocolFamily) {
/* 70 */       switch ((StandardProtocolFamily)family) {
/*    */         case INET:
/* 72 */           return INET;
/*    */         case INET6:
/* 74 */           return INET6;
/*    */       } 
/*    */       
/* 77 */       if (UNIX.name().equals(family.name())) {
/* 78 */         return UNIX;
/*    */       
/*    */       }
/*    */     }
/* 82 */     else if (family instanceof SocketProtocolFamily) {
/* 83 */       return (SocketProtocolFamily)family;
/*    */     } 
/* 85 */     throw new UnsupportedOperationException("ProtocolFamily is not supported: " + family);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\SocketProtocolFamily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */