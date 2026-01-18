/*    */ package com.hypixel.hytale.assetstore.map;
/*    */ 
/*    */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.JsonAsset;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class AssetMapWithIndexes<K, T extends JsonAsset<K>>
/*    */   extends DefaultAssetMap<K, T> {
/*    */   public static final int NOT_FOUND = -2147483648;
/* 16 */   protected final Int2ObjectConcurrentHashMap<IntSet> indexedTagStorage = new Int2ObjectConcurrentHashMap();
/* 17 */   protected final Int2ObjectConcurrentHashMap<IntSet> unmodifiableIndexedTagStorage = new Int2ObjectConcurrentHashMap();
/*    */ 
/*    */   
/*    */   protected void clear() {
/* 21 */     super.clear();
/* 22 */     this.indexedTagStorage.clear();
/* 23 */     this.unmodifiableIndexedTagStorage.clear();
/*    */   }
/*    */   
/*    */   public IntSet getIndexesForTag(int index) {
/* 27 */     return (IntSet)this.unmodifiableIndexedTagStorage.getOrDefault(index, IntSets.EMPTY_SET);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void putAssetTags(AssetCodec<K, T> codec, Map<K, T> loadedAssets) {}
/*    */ 
/*    */   
/*    */   protected void putAssetTag(@Nonnull AssetCodec<K, T> codec, K key, int index, T value) {
/* 36 */     AssetExtraInfo.Data data = codec.getData((JsonAsset)value);
/* 37 */     if (data == null)
/*    */       return; 
/* 39 */     IntIterator iterator = data.getExpandedTagIndexes().iterator();
/* 40 */     while (iterator.hasNext()) {
/* 41 */       int tag = iterator.nextInt();
/* 42 */       putAssetTag(key, index, tag);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void putAssetTag(K key, int index, int tag) {
/* 47 */     putAssetTag(key, tag);
/* 48 */     ((IntSet)this.indexedTagStorage.computeIfAbsent(tag, k -> {
/*    */           Int2ObjectConcurrentHashMap.KeySetView keySetView = Int2ObjectConcurrentHashMap.newKeySet(3);
/*    */           this.unmodifiableIndexedTagStorage.put(k, IntSets.unmodifiable((IntSet)keySetView));
/*    */           return (IntSet)keySetView;
/* 52 */         })).add(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requireReplaceOnRemove() {
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\AssetMapWithIndexes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */