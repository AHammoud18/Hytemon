/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.UUID;
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
/*    */ public final class LocalAddress
/*    */   extends SocketAddress
/*    */   implements Comparable<LocalAddress>
/*    */ {
/*    */   private static final long serialVersionUID = 4644331421130916435L;
/* 33 */   public static final LocalAddress ANY = new LocalAddress("ANY");
/*    */ 
/*    */   
/*    */   private final String id;
/*    */ 
/*    */   
/*    */   private final String strVal;
/*    */ 
/*    */ 
/*    */   
/*    */   LocalAddress(Channel channel) {
/* 44 */     StringBuilder buf = new StringBuilder(16);
/* 45 */     buf.append("local:E");
/* 46 */     buf.append(Long.toHexString(channel.hashCode() & 0xFFFFFFFFL | 0x100000000L));
/* 47 */     buf.setCharAt(7, ':');
/* 48 */     this.id = buf.substring(6);
/* 49 */     this.strVal = buf.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalAddress(String id) {
/* 56 */     this.id = ObjectUtil.checkNonEmptyAfterTrim(id, "id").toLowerCase();
/* 57 */     this.strVal = "local:" + this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalAddress(Class<?> cls) {
/* 64 */     this(cls.getSimpleName() + '/' + UUID.randomUUID());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String id() {
/* 71 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return this.id.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 81 */     if (!(o instanceof LocalAddress)) {
/* 82 */       return false;
/*    */     }
/*    */     
/* 85 */     return this.id.equals(((LocalAddress)o).id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(LocalAddress o) {
/* 90 */     return this.id.compareTo(o.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     return this.strVal;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\local\LocalAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */