/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
/*    */ import java.util.Arrays;
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
/*    */ final class OpenSslSessionId
/*    */ {
/*    */   private final byte[] id;
/*    */   private final int hashCode;
/* 30 */   static final OpenSslSessionId NULL_ID = new OpenSslSessionId(EmptyArrays.EMPTY_BYTES);
/*    */ 
/*    */   
/*    */   OpenSslSessionId(byte[] id) {
/* 34 */     this.id = id;
/*    */     
/* 36 */     this.hashCode = Arrays.hashCode(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 41 */     if (this == o) {
/* 42 */       return true;
/*    */     }
/* 44 */     if (!(o instanceof OpenSslSessionId)) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     return Arrays.equals(this.id, ((OpenSslSessionId)o).id);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 53 */     return "OpenSslSessionId{id=" + 
/* 54 */       Arrays.toString(this.id) + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return this.hashCode;
/*    */   }
/*    */   
/*    */   byte[] cloneBytes() {
/* 64 */     return (byte[])this.id.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslSessionId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */