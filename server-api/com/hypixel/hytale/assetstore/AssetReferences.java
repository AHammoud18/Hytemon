/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetReferences<CK, C extends JsonAssetWithMap<CK, ?>>
/*    */ {
/*    */   private final Class<C> parentAssetClass;
/*    */   private final Set<CK> parentKeys;
/*    */   
/*    */   public AssetReferences(Class<C> parentAssetClass, Set<CK> parentKeys) {
/* 19 */     this.parentAssetClass = parentAssetClass;
/* 20 */     this.parentKeys = parentKeys;
/*    */   }
/*    */   
/*    */   public Class<C> getParentAssetClass() {
/* 24 */     return this.parentAssetClass;
/*    */   }
/*    */   
/*    */   public Set<CK> getParentKeys() {
/* 28 */     return this.parentKeys;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends JsonAssetWithMap<K, ?>, K> void addChildAssetReferences(Class<T> tClass, K childKey) {
/* 33 */     Class<C> parentAssetClass = this.parentAssetClass;
/* 34 */     AssetStore<CK, C, ?> assetStore = AssetRegistry.getAssetStore(parentAssetClass);
/* 35 */     for (CK parentKey : this.parentKeys) {
/* 36 */       assetStore.addChildAssetReferences(parentKey, tClass, Collections.singleton(childKey));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 43 */     return "AssetReferences{parentAssetClass=" + String.valueOf(this.parentAssetClass) + ", parentKeys=" + String.valueOf(this.parentKeys) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetReferences.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */