/*    */ package com.hypixel.hytale.server.core.meta;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PersistentMetaKey<T>
/*    */   extends MetaKey<T> {
/*    */   private final String key;
/*    */   private final Codec<T> codec;
/*    */   
/*    */   PersistentMetaKey(int id, String key, Codec<T> codec) {
/* 12 */     super(id);
/* 13 */     this.key = key;
/* 14 */     this.codec = codec;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 18 */     return this.key;
/*    */   }
/*    */   
/*    */   public Codec<T> getCodec() {
/* 22 */     return this.codec;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "PersistentMetaKey{key=" + this.key + "codec=" + String.valueOf(this.codec) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\PersistentMetaKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */