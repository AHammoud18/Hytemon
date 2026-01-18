/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetLoadResult<K, T>
/*    */ {
/*    */   private final Map<K, T> loadedAssets;
/*    */   private final Map<K, Path> loadedKeyToPathMap;
/*    */   private final Set<K> failedToLoadKeys;
/*    */   private final Set<Path> failedToLoadPaths;
/*    */   private final Map<Class<? extends JsonAssetWithMap>, AssetLoadResult> childAssetResults;
/*    */   
/*    */   public AssetLoadResult(Map<K, T> loadedAssets, Map<K, Path> loadedKeyToPathMap, Set<K> failedToLoadKeys, Set<Path> failedToLoadPaths, Map<Class<? extends JsonAssetWithMap>, AssetLoadResult> childAssetResults) {
/* 23 */     this.loadedAssets = loadedAssets;
/* 24 */     this.loadedKeyToPathMap = loadedKeyToPathMap;
/* 25 */     this.failedToLoadKeys = failedToLoadKeys;
/* 26 */     this.failedToLoadPaths = failedToLoadPaths;
/* 27 */     this.childAssetResults = childAssetResults;
/*    */   }
/*    */   
/*    */   public Map<K, T> getLoadedAssets() {
/* 31 */     return this.loadedAssets;
/*    */   }
/*    */   
/*    */   public Map<K, Path> getLoadedKeyToPathMap() {
/* 35 */     return this.loadedKeyToPathMap;
/*    */   }
/*    */   
/*    */   public Set<K> getFailedToLoadKeys() {
/* 39 */     return this.failedToLoadKeys;
/*    */   }
/*    */   
/*    */   public Set<Path> getFailedToLoadPaths() {
/* 43 */     return this.failedToLoadPaths;
/*    */   }
/*    */   
/*    */   public boolean hasFailed() {
/* 47 */     if (!this.failedToLoadKeys.isEmpty() || !this.failedToLoadPaths.isEmpty()) {
/* 48 */       return true;
/*    */     }
/* 50 */     for (AssetLoadResult result : this.childAssetResults.values()) {
/* 51 */       if (result.hasFailed()) return true; 
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetLoadResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */