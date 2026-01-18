/*     */ package com.hypixel.hytale.assetstore;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RebuildCache
/*     */ {
/*  47 */   public static final RebuildCache DEFAULT = new RebuildCache(true, true, true, true, true, true);
/*  48 */   public static final RebuildCache NO_REBUILD = new RebuildCache(false, false, false, false, false, false);
/*     */   
/*     */   private final boolean blockTextures;
/*     */   private final boolean models;
/*     */   private final boolean modelTextures;
/*     */   private final boolean mapGeometry;
/*     */   private final boolean itemIcons;
/*     */   private final boolean commonAssetsRebuild;
/*     */   
/*     */   public RebuildCache(boolean blockTextures, boolean models, boolean modelTextures, boolean mapGeometry, boolean itemIcons, boolean commonAssetsRebuild) {
/*  58 */     this.blockTextures = blockTextures;
/*  59 */     this.models = models;
/*  60 */     this.modelTextures = modelTextures;
/*  61 */     this.mapGeometry = mapGeometry;
/*  62 */     this.itemIcons = itemIcons;
/*  63 */     this.commonAssetsRebuild = commonAssetsRebuild;
/*     */   }
/*     */   
/*     */   public boolean isBlockTextures() {
/*  67 */     return this.blockTextures;
/*     */   }
/*     */   
/*     */   public boolean isModels() {
/*  71 */     return this.models;
/*     */   }
/*     */   
/*     */   public boolean isModelTextures() {
/*  75 */     return this.modelTextures;
/*     */   }
/*     */   
/*     */   public boolean isMapGeometry() {
/*  79 */     return this.mapGeometry;
/*     */   }
/*     */   
/*     */   public boolean isItemIcons() {
/*  83 */     return this.itemIcons;
/*     */   }
/*     */   
/*     */   public boolean isCommonAssetsRebuild() {
/*  87 */     return this.commonAssetsRebuild;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public AssetUpdateQuery.RebuildCacheBuilder toBuilder() {
/*  92 */     return new AssetUpdateQuery.RebuildCacheBuilder(this.blockTextures, this.models, this.modelTextures, this.mapGeometry, this.itemIcons, this.commonAssetsRebuild);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetUpdateQuery.RebuildCacheBuilder builder() {
/*  97 */     return new AssetUpdateQuery.RebuildCacheBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 103 */     return "RebuildCache{blockTextures=" + this.blockTextures + ", models=" + this.models + ", modelTextures=" + this.modelTextures + ", mapGeometry=" + this.mapGeometry + ", icons=" + this.itemIcons + ", commonAssetsRebuild=" + this.commonAssetsRebuild + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetUpdateQuery$RebuildCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */