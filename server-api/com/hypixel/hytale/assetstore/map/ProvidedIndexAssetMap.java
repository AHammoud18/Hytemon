/*     */ package com.hypixel.hytale.assetstore.map;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.ToIntBiFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProvidedIndexAssetMap<K, T extends JsonAssetWithMap<K, ProvidedIndexAssetMap<K, T>>>
/*     */   extends AssetMapWithIndexes<K, T>
/*     */ {
/*  23 */   private final StampedLock keyToIndexLock = new StampedLock();
/*  24 */   private final Object2IntMap<K> keyToIndex = (Object2IntMap<K>)new Object2IntOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   private final ToIntBiFunction<K, T> indexGetter;
/*     */   
/*     */   public ProvidedIndexAssetMap(ToIntBiFunction<K, T> indexGetter) {
/*  28 */     this.indexGetter = indexGetter;
/*  29 */     this.keyToIndex.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   public int getIndex(K key) {
/*  33 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  34 */     int value = this.keyToIndex.getInt(key);
/*  35 */     if (this.keyToIndexLock.validate(stamp)) {
/*  36 */       return value;
/*     */     }
/*  38 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  40 */       return this.keyToIndex.getInt(key);
/*     */     } finally {
/*  42 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getIndexOrDefault(K key, int def) {
/*  47 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  48 */     int value = this.keyToIndex.getOrDefault(key, def);
/*  49 */     if (this.keyToIndexLock.validate(stamp)) {
/*  50 */       return value;
/*     */     }
/*  52 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  54 */       return this.keyToIndex.getOrDefault(key, def);
/*     */     } finally {
/*  56 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  62 */     super.clear();
/*  63 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/*  65 */       this.keyToIndex.clear();
/*     */     } finally {
/*  67 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/*  73 */     super.putAll(packKey, codec, loadedAssets, loadedKeyToPathMap, loadedAssetChildren);
/*  74 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/*  76 */       for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/*  77 */         K key = entry.getKey();
/*  78 */         JsonAssetWithMap jsonAssetWithMap = (JsonAssetWithMap)entry.getValue();
/*     */         
/*     */         int index;
/*  81 */         if ((index = this.keyToIndex.getInt(key)) == Integer.MIN_VALUE) {
/*  82 */           this.keyToIndex.put(key, index = this.indexGetter.applyAsInt(key, (K)jsonAssetWithMap));
/*     */         }
/*     */         
/*  85 */         putAssetTag(codec, key, index, (T)jsonAssetWithMap);
/*     */       } 
/*     */     } finally {
/*  88 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/*  94 */     Set<K> remove = super.remove(keys);
/*  95 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/*  97 */       for (K key : keys) {
/*  98 */         int index = this.keyToIndex.removeInt(key);
/*     */ 
/*     */         
/* 101 */         this.indexedTagStorage.forEachWithInt((_k, value, idx) -> value.remove(idx), index);
/*     */       } 
/*     */     } finally {
/* 104 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/* 106 */     return remove;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requireReplaceOnRemove() {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\ProvidedIndexAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */