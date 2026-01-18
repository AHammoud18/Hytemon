/*    */ package com.hypixel.hytale.assetstore;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AssetMap<K, T extends JsonAsset<K>>
/*    */ {
/*    */   @Nullable
/*    */   public abstract T getAsset(K paramK);
/*    */   
/*    */   @Nullable
/*    */   public abstract T getAsset(@Nonnull String paramString, K paramK);
/*    */   
/*    */   @Nullable
/*    */   public abstract Path getPath(K paramK);
/*    */   
/*    */   @Nullable
/*    */   public abstract String getAssetPack(K paramK);
/*    */   
/*    */   public abstract Set<K> getKeys(Path paramPath);
/*    */   
/*    */   public abstract Set<K> getChildren(K paramK);
/*    */   
/*    */   public abstract int getAssetCount();
/*    */   
/*    */   public abstract Map<K, T> getAssetMap();
/*    */   
/*    */   public abstract Map<K, Path> getPathMap(@Nonnull String paramString);
/*    */   
/*    */   public abstract Set<K> getKeysForTag(int paramInt);
/*    */   
/*    */   public abstract IntSet getTagIndexes();
/*    */   
/*    */   public abstract int getTagCount();
/*    */   
/*    */   protected abstract void clear();
/*    */   
/*    */   protected abstract void putAll(@Nonnull String paramString, AssetCodec<K, T> paramAssetCodec, Map<K, T> paramMap, Map<K, Path> paramMap1, Map<K, Set<K>> paramMap2);
/*    */   
/*    */   protected abstract Set<K> remove(Set<K> paramSet);
/*    */   
/*    */   protected abstract Set<K> remove(@Nonnull String paramString, Set<K> paramSet, List<Map.Entry<String, Object>> paramList);
/*    */   
/*    */   public boolean requireReplaceOnRemove() {
/* 56 */     return false;
/*    */   }
/*    */   
/*    */   public abstract Set<K> getKeysForPack(@Nonnull String paramString);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */