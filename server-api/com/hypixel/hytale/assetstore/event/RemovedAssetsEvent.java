/*    */ package com.hypixel.hytale.assetstore.event;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.JsonAsset;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemovedAssetsEvent<K, T extends JsonAsset<K>, M extends AssetMap<K, T>>
/*    */   extends AssetsEvent<K, T>
/*    */ {
/*    */   private final Class<T> tClass;
/*    */   private final M assetMap;
/*    */   @Nonnull
/*    */   private final Set<K> removedAssets;
/*    */   private final boolean replaced;
/*    */   
/*    */   public RemovedAssetsEvent(Class<T> tClass, M assetMap, @Nonnull Set<K> removedAssets, boolean replaced) {
/* 23 */     this.tClass = tClass;
/* 24 */     this.assetMap = assetMap;
/* 25 */     this.removedAssets = Collections.unmodifiableSet(removedAssets);
/* 26 */     this.replaced = replaced;
/*    */   }
/*    */   
/*    */   public Class<T> getAssetClass() {
/* 30 */     return this.tClass;
/*    */   }
/*    */   
/*    */   public M getAssetMap() {
/* 34 */     return this.assetMap;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<K> getRemovedAssets() {
/* 39 */     return this.removedAssets;
/*    */   }
/*    */   
/*    */   public boolean isReplaced() {
/* 43 */     return this.replaced;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "RemovedAssetsEvent{removedAssets=" + String.valueOf(this.removedAssets) + ", replaced=" + this.replaced + "} " + super
/*    */ 
/*    */       
/* 52 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\RemovedAssetsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */