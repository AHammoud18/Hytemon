/*    */ package com.hypixel.hytale.server.core.meta;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class MetaKey<T> {
/*    */   private final int id;
/*    */   
/*    */   MetaKey(int id) {
/* 10 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 14 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 19 */     if (this == o) return true; 
/* 20 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 22 */     MetaKey<?> metaKey = (MetaKey)o;
/*    */     
/* 24 */     return (this.id == metaKey.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 29 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "MetaKey{id=" + this.id + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\MetaKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */