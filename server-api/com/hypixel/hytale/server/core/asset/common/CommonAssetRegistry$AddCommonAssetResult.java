/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class AddCommonAssetResult
/*     */ {
/*     */   private CommonAssetRegistry.PackAsset newPackAsset;
/*     */   private CommonAssetRegistry.PackAsset previousNameAsset;
/*     */   private CommonAssetRegistry.PackAsset activeAsset;
/*     */   private CommonAssetRegistry.PackAsset[] previousHashAssets;
/*     */   private int duplicateAssetId;
/*     */   
/*     */   public CommonAssetRegistry.PackAsset getNewPackAsset() {
/* 184 */     return this.newPackAsset;
/*     */   }
/*     */   
/*     */   public CommonAssetRegistry.PackAsset getPreviousNameAsset() {
/* 188 */     return this.previousNameAsset;
/*     */   }
/*     */   
/*     */   public CommonAssetRegistry.PackAsset getActiveAsset() {
/* 192 */     return this.activeAsset;
/*     */   }
/*     */   
/*     */   public CommonAssetRegistry.PackAsset[] getPreviousHashAssets() {
/* 196 */     return this.previousHashAssets;
/*     */   }
/*     */   
/*     */   public int getDuplicateAssetId() {
/* 200 */     return this.duplicateAssetId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "AddCommonAssetResult{previousNameAsset=" + String.valueOf(this.previousNameAsset) + ", previousHashAssets=" + 
/*     */       
/* 208 */       Arrays.toString((Object[])this.previousHashAssets) + ", duplicateAssetId=" + this.duplicateAssetId + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\CommonAssetRegistry$AddCommonAssetResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */