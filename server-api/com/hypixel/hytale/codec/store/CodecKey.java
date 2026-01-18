/*    */ package com.hypixel.hytale.codec.store;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodecKey<T>
/*    */ {
/*    */   private final String id;
/*    */   
/*    */   public CodecKey(String id) {
/* 15 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 19 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 24 */     if (this == o) return true; 
/* 25 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 27 */     CodecKey<?> codecKey = (CodecKey)o;
/*    */     
/* 29 */     return (this.id != null) ? this.id.equals(codecKey.id) : ((codecKey.id == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 34 */     return (this.id != null) ? this.id.hashCode() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "CodecKey{id='" + this.id + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\store\CodecKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */