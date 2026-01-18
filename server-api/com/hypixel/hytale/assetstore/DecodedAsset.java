/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DecodedAsset<K, T extends JsonAsset<K>> implements AssetHolder<K> {
/*    */   private final K key;
/*    */   private final T asset;
/*    */   
/*    */   public DecodedAsset(K key, T asset) {
/* 11 */     this.key = key;
/* 12 */     this.asset = asset;
/*    */   }
/*    */   
/*    */   public K getKey() {
/* 16 */     return this.key;
/*    */   }
/*    */   
/*    */   public T getAsset() {
/* 20 */     return this.asset;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 25 */     if (this == o) return true; 
/* 26 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 28 */     DecodedAsset<?, ?> that = (DecodedAsset<?, ?>)o;
/*    */     
/* 30 */     if ((this.key != null) ? !this.key.equals(that.key) : (that.key != null)) return false; 
/* 31 */     return (this.asset != null) ? this.asset.equals(that.asset) : ((that.asset == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     int result = (this.key != null) ? this.key.hashCode() : 0;
/* 37 */     result = 31 * result + ((this.asset != null) ? this.asset.hashCode() : 0);
/* 38 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 44 */     return "DecodedAsset{key=" + String.valueOf(this.key) + ", asset=" + String.valueOf(this.asset) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\DecodedAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */