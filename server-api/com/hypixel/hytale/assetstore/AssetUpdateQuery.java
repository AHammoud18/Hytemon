/*     */ package com.hypixel.hytale.assetstore;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetUpdateQuery
/*     */ {
/*  11 */   public static final AssetUpdateQuery DEFAULT = new AssetUpdateQuery(RebuildCache.DEFAULT);
/*  12 */   public static final AssetUpdateQuery DEFAULT_NO_REBUILD = new AssetUpdateQuery(RebuildCache.NO_REBUILD);
/*     */   
/*     */   private final boolean disableAssetCompare;
/*     */   private final RebuildCache rebuildCache;
/*     */   
/*     */   public AssetUpdateQuery(boolean disableAssetCompare, RebuildCache rebuildCache) {
/*  18 */     this.disableAssetCompare = disableAssetCompare;
/*  19 */     this.rebuildCache = rebuildCache;
/*     */   }
/*     */   
/*     */   public AssetUpdateQuery(RebuildCache rebuildCache) {
/*  23 */     this(AssetStore.DISABLE_ASSET_COMPARE, rebuildCache);
/*     */   }
/*     */   
/*     */   public boolean isDisableAssetCompare() {
/*  27 */     return this.disableAssetCompare;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public RebuildCache getRebuildCache() {
/*  32 */     return this.rebuildCache;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  38 */     return "AssetUpdateQuery{rebuildCache=" + String.valueOf(this.rebuildCache) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RebuildCache
/*     */   {
/*  47 */     public static final RebuildCache DEFAULT = new RebuildCache(true, true, true, true, true, true);
/*  48 */     public static final RebuildCache NO_REBUILD = new RebuildCache(false, false, false, false, false, false);
/*     */     
/*     */     private final boolean blockTextures;
/*     */     private final boolean models;
/*     */     private final boolean modelTextures;
/*     */     private final boolean mapGeometry;
/*     */     private final boolean itemIcons;
/*     */     private final boolean commonAssetsRebuild;
/*     */     
/*     */     public RebuildCache(boolean blockTextures, boolean models, boolean modelTextures, boolean mapGeometry, boolean itemIcons, boolean commonAssetsRebuild) {
/*  58 */       this.blockTextures = blockTextures;
/*  59 */       this.models = models;
/*  60 */       this.modelTextures = modelTextures;
/*  61 */       this.mapGeometry = mapGeometry;
/*  62 */       this.itemIcons = itemIcons;
/*  63 */       this.commonAssetsRebuild = commonAssetsRebuild;
/*     */     }
/*     */     
/*     */     public boolean isBlockTextures() {
/*  67 */       return this.blockTextures;
/*     */     }
/*     */     
/*     */     public boolean isModels() {
/*  71 */       return this.models;
/*     */     }
/*     */     
/*     */     public boolean isModelTextures() {
/*  75 */       return this.modelTextures;
/*     */     }
/*     */     
/*     */     public boolean isMapGeometry() {
/*  79 */       return this.mapGeometry;
/*     */     }
/*     */     
/*     */     public boolean isItemIcons() {
/*  83 */       return this.itemIcons;
/*     */     }
/*     */     
/*     */     public boolean isCommonAssetsRebuild() {
/*  87 */       return this.commonAssetsRebuild;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public AssetUpdateQuery.RebuildCacheBuilder toBuilder() {
/*  92 */       return new AssetUpdateQuery.RebuildCacheBuilder(this.blockTextures, this.models, this.modelTextures, this.mapGeometry, this.itemIcons, this.commonAssetsRebuild);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public static AssetUpdateQuery.RebuildCacheBuilder builder() {
/*  97 */       return new AssetUpdateQuery.RebuildCacheBuilder();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 103 */       return "RebuildCache{blockTextures=" + this.blockTextures + ", models=" + this.models + ", modelTextures=" + this.modelTextures + ", mapGeometry=" + this.mapGeometry + ", icons=" + this.itemIcons + ", commonAssetsRebuild=" + this.commonAssetsRebuild + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RebuildCacheBuilder
/*     */   {
/*     */     private boolean blockTextures;
/*     */     
/*     */     private boolean models;
/*     */     
/*     */     private boolean modelTextures;
/*     */     
/*     */     private boolean mapGeometry;
/*     */     
/*     */     private boolean itemIcons;
/*     */     
/*     */     private boolean commonAssetsRebuild;
/*     */ 
/*     */     
/*     */     RebuildCacheBuilder() {}
/*     */     
/*     */     RebuildCacheBuilder(boolean blockTextures, boolean models, boolean modelTextures, boolean mapGeometry, boolean itemIcons, boolean commonAssetsRebuild) {
/* 126 */       this.blockTextures = blockTextures;
/* 127 */       this.models = models;
/* 128 */       this.modelTextures = modelTextures;
/* 129 */       this.mapGeometry = mapGeometry;
/* 130 */       this.itemIcons = itemIcons;
/* 131 */       this.commonAssetsRebuild = commonAssetsRebuild;
/*     */     }
/*     */     
/*     */     public void setBlockTextures(boolean blockTextures) {
/* 135 */       this.blockTextures = blockTextures;
/*     */     }
/*     */     
/*     */     public void setModels(boolean models) {
/* 139 */       this.models = models;
/*     */     }
/*     */     
/*     */     public void setModelTextures(boolean modelTextures) {
/* 143 */       this.modelTextures = modelTextures;
/*     */     }
/*     */     
/*     */     public void setMapGeometry(boolean mapGeometry) {
/* 147 */       this.mapGeometry = mapGeometry;
/*     */     }
/*     */     
/*     */     public void setItemIcons(boolean itemIcons) {
/* 151 */       this.itemIcons = itemIcons;
/*     */     }
/*     */     
/*     */     public void setCommonAssetsRebuild(boolean commonAssetsRebuild) {
/* 155 */       this.commonAssetsRebuild = commonAssetsRebuild;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public AssetUpdateQuery.RebuildCache build() {
/* 160 */       return new AssetUpdateQuery.RebuildCache(this.blockTextures, this.models, this.modelTextures, this.mapGeometry, this.itemIcons, this.commonAssetsRebuild);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 166 */       return "RebuildCache{blockTextures=" + this.blockTextures + ", models=" + this.models + ", modelTextures=" + this.modelTextures + ", mapGeometry=" + this.mapGeometry + ", icons=" + this.itemIcons + ", commonAssetsRebuild=" + this.commonAssetsRebuild + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetUpdateQuery.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */