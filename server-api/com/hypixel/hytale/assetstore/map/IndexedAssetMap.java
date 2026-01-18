/*     */ package com.hypixel.hytale.assetstore.map;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedAssetMap<K, T extends JsonAssetWithMap<K, IndexedAssetMap<K, T>>>
/*     */   extends AssetMapWithIndexes<K, T>
/*     */ {
/*  24 */   private final AtomicInteger nextIndex = new AtomicInteger();
/*  25 */   private final StampedLock keyToIndexLock = new StampedLock();
/*  26 */   private final Object2IntMap<K> keyToIndex = (Object2IntMap<K>)new Object2IntOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   
/*     */   public IndexedAssetMap() {
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
/*     */   public int getNextIndex() {
/*  61 */     return this.nextIndex.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  66 */     super.clear();
/*  67 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/*  69 */       this.keyToIndex.clear();
/*     */     } finally {
/*  71 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/*  77 */     super.putAll(packKey, codec, loadedAssets, loadedKeyToPathMap, loadedAssetChildren);
/*  78 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/*  80 */       for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/*  81 */         K key = entry.getKey();
/*     */         int index;
/*  83 */         if ((index = this.keyToIndex.getInt(key)) == Integer.MIN_VALUE) {
/*  84 */           this.keyToIndex.put(key, index = this.nextIndex.getAndIncrement());
/*     */         }
/*     */         
/*  87 */         JsonAssetWithMap jsonAssetWithMap = (JsonAssetWithMap)entry.getValue();
/*     */         
/*  89 */         putAssetTag(codec, key, index, (T)jsonAssetWithMap);
/*     */       } 
/*     */     } finally {
/*  92 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/*  98 */     Set<K> remove = super.remove(keys);
/*  99 */     remove0(keys);
/* 100 */     return remove;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull String packKey, @Nonnull Set<K> keys, @Nonnull List<Map.Entry<String, Object>> pathsToReload) {
/* 105 */     Set<K> remove = super.remove(packKey, keys, pathsToReload);
/* 106 */     remove0(keys);
/* 107 */     return remove;
/*     */   }
/*     */   
/*     */   private void remove0(@Nonnull Set<K> keys) {
/* 111 */     long stamp = this.keyToIndexLock.writeLock();
/*     */     try {
/* 113 */       for (K key : keys) {
/* 114 */         int index = this.keyToIndex.removeInt(key);
/*     */ 
/*     */         
/* 117 */         this.indexedTagStorage.forEachWithInt((_k, value, idx) -> value.remove(idx), index);
/*     */       } 
/*     */     } finally {
/* 120 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\IndexedAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */