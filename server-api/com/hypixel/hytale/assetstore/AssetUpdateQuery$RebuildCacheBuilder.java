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
/*     */ public class RebuildCacheBuilder
/*     */ {
/*     */   private boolean blockTextures;
/*     */   private boolean models;
/*     */   private boolean modelTextures;
/*     */   private boolean mapGeometry;
/*     */   private boolean itemIcons;
/*     */   private boolean commonAssetsRebuild;
/*     */   
/*     */   RebuildCacheBuilder() {}
/*     */   
/*     */   RebuildCacheBuilder(boolean blockTextures, boolean models, boolean modelTextures, boolean mapGeometry, boolean itemIcons, boolean commonAssetsRebuild) {
/* 126 */     this.blockTextures = blockTextures;
/* 127 */     this.models = models;
/* 128 */     this.modelTextures = modelTextures;
/* 129 */     this.mapGeometry = mapGeometry;
/* 130 */     this.itemIcons = itemIcons;
/* 131 */     this.commonAssetsRebuild = commonAssetsRebuild;
/*     */   }
/*     */   
/*     */   public void setBlockTextures(boolean blockTextures) {
/* 135 */     this.blockTextures = blockTextures;
/*     */   }
/*     */   
/*     */   public void setModels(boolean models) {
/* 139 */     this.models = models;
/*     */   }
/*     */   
/*     */   public void setModelTextures(boolean modelTextures) {
/* 143 */     this.modelTextures = modelTextures;
/*     */   }
/*     */   
/*     */   public void setMapGeometry(boolean mapGeometry) {
/* 147 */     this.mapGeometry = mapGeometry;
/*     */   }
/*     */   
/*     */   public void setItemIcons(boolean itemIcons) {
/* 151 */     this.itemIcons = itemIcons;
/*     */   }
/*     */   
/*     */   public void setCommonAssetsRebuild(boolean commonAssetsRebuild) {
/* 155 */     this.commonAssetsRebuild = commonAssetsRebuild;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public AssetUpdateQuery.RebuildCache build() {
/* 160 */     return new AssetUpdateQuery.RebuildCache(this.blockTextures, this.models, this.modelTextures, this.mapGeometry, this.itemIcons, this.commonAssetsRebuild);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 166 */     return "RebuildCache{blockTextures=" + this.blockTextures + ", models=" + this.models + ", modelTextures=" + this.modelTextures + ", mapGeometry=" + this.mapGeometry + ", icons=" + this.itemIcons + ", commonAssetsRebuild=" + this.commonAssetsRebuild + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetUpdateQuery$RebuildCacheBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */