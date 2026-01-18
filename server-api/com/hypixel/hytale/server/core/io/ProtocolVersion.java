/*    */ package com.hypixel.hytale.server.core.io;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProtocolVersion
/*    */ {
/*    */   private final String hash;
/*    */   
/*    */   public ProtocolVersion(String hash) {
/* 15 */     this.hash = hash;
/*    */   }
/*    */   
/*    */   public String getHash() {
/* 19 */     return this.hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 24 */     if (this == o) return true; 
/* 25 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 27 */     ProtocolVersion that = (ProtocolVersion)o;
/*    */     
/* 29 */     return (this.hash != null) ? this.hash.equals(that.hash) : ((that.hash == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 34 */     int result = 31 * ((this.hash != null) ? this.hash.hashCode() : 0);
/* 35 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "ProtocolVersion{hash='" + this.hash + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\ProtocolVersion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */