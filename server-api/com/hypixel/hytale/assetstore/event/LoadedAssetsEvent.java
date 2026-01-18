/*     */ package com.hypixel.hytale.assetstore.event;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*     */ import com.hypixel.hytale.assetstore.JsonAsset;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class LoadedAssetsEvent<K, T extends JsonAsset<K>, M extends AssetMap<K, T>>
/*     */   extends AssetsEvent<K, T>
/*     */ {
/*     */   @Nonnull
/*     */   private final Class<T> tClass;
/*     */   @Nonnull
/*     */   private final M assetMap;
/*     */   @Nonnull
/*     */   private final Map<K, T> loadedAssets;
/*     */   private final boolean initial;
/*     */   @Nonnull
/*     */   private final AssetUpdateQuery query;
/*     */   
/*     */   public LoadedAssetsEvent(@Nonnull Class<T> tClass, @Nonnull M assetMap, @Nonnull Map<K, T> loadedAssets, boolean initial, @Nonnull AssetUpdateQuery query) {
/*  59 */     this.tClass = tClass;
/*  60 */     this.assetMap = assetMap;
/*  61 */     this.loadedAssets = Collections.unmodifiableMap(loadedAssets);
/*  62 */     this.initial = initial;
/*  63 */     this.query = query;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getAssetClass() {
/*  70 */     return this.tClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public M getAssetMap() {
/*  77 */     return this.assetMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<K, T> getLoadedAssets() {
/*  85 */     return this.loadedAssets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitial() {
/*  92 */     return this.initial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetUpdateQuery getQuery() {
/* 100 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 106 */     return "LoadedAssetsEvent{loadedAssets=" + String.valueOf(this.loadedAssets) + ", initial=" + this.initial + ", query=" + String.valueOf(this.query) + "} " + super
/*     */ 
/*     */ 
/*     */       
/* 110 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\LoadedAssetsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */