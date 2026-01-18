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
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedLookupTableAssetMap<K, T extends JsonAssetWithMap<K, IndexedLookupTableAssetMap<K, T>>>
/*     */   extends AssetMapWithIndexes<K, T>
/*     */ {
/*  27 */   private final AtomicInteger nextIndex = new AtomicInteger();
/*  28 */   private final StampedLock keyToIndexLock = new StampedLock();
/*  29 */   private final Object2IntMap<K> keyToIndex = (Object2IntMap<K>)new Object2IntOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   
/*     */   @Nonnull
/*     */   private final IntFunction<T[]> arrayProvider;
/*  33 */   private final ReentrantLock arrayLock = new ReentrantLock();
/*     */   private T[] array;
/*     */   
/*     */   public IndexedLookupTableAssetMap(@Nonnull IntFunction<T[]> arrayProvider) {
/*  37 */     this.arrayProvider = arrayProvider;
/*     */ 
/*     */     
/*  40 */     this.array = arrayProvider.apply(0);
/*     */     
/*  42 */     this.keyToIndex.defaultReturnValue(-2147483648);
/*     */   }
/*     */   
/*     */   public int getIndex(K key) {
/*  46 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  47 */     int value = this.keyToIndex.getInt(key);
/*  48 */     if (this.keyToIndexLock.validate(stamp)) {
/*  49 */       return value;
/*     */     }
/*  51 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  53 */       return this.keyToIndex.getInt(key);
/*     */     } finally {
/*  55 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getIndexOrDefault(K key, int def) {
/*  60 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  61 */     int value = this.keyToIndex.getOrDefault(key, def);
/*  62 */     if (this.keyToIndexLock.validate(stamp)) {
/*  63 */       return value;
/*     */     }
/*  65 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  67 */       return this.keyToIndex.getOrDefault(key, def);
/*     */     } finally {
/*  69 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNextIndex() {
/*  74 */     return this.nextIndex.get();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getAsset(int index) {
/*  79 */     if (index < 0 || index >= this.array.length) return null; 
/*  80 */     return this.array[index];
/*     */   }
/*     */   
/*     */   public T getAssetOrDefault(int index, T def) {
/*  84 */     if (index < 0 || index >= this.array.length) return def; 
/*  85 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  90 */     super.clear();
/*  91 */     long stamp = this.keyToIndexLock.writeLock();
/*  92 */     this.arrayLock.lock();
/*     */     try {
/*  94 */       this.keyToIndex.clear();
/*  95 */       this.array = this.arrayProvider.apply(0);
/*     */     } finally {
/*  97 */       this.arrayLock.unlock();
/*  98 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/* 104 */     super.putAll(packKey, codec, loadedAssets, loadedKeyToPathMap, loadedAssetChildren);
/* 105 */     putAll0(codec, loadedAssets);
/*     */   }
/*     */   
/*     */   private void putAll0(@Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets) {
/* 109 */     long stamp = this.keyToIndexLock.writeLock();
/* 110 */     this.arrayLock.lock();
/*     */     try {
/* 112 */       int highestIndex = 0;
/* 113 */       for (K key : loadedAssets.keySet()) {
/* 114 */         int index = this.keyToIndex.getInt(key);
/* 115 */         if (index == Integer.MIN_VALUE) {
/* 116 */           this.keyToIndex.put(key, index = this.nextIndex.getAndIncrement());
/*     */         }
/* 118 */         if (index < 0) throw new IllegalArgumentException("Index can't be less than zero!"); 
/* 119 */         if (index > highestIndex) highestIndex = index;
/*     */       
/*     */       } 
/*     */       
/* 123 */       int length = highestIndex + 1;
/* 124 */       if (length < 0) throw new IllegalArgumentException("Highest index can't be less than zero!"); 
/* 125 */       if (length > this.array.length) {
/* 126 */         JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 127 */         System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, this.array.length);
/* 128 */         this.array = (T[])arrayOfJsonAssetWithMap;
/*     */       } 
/*     */       
/* 131 */       for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/* 132 */         K key = entry.getKey();
/* 133 */         int index = this.keyToIndex.getInt(key);
/* 134 */         if (index < 0) throw new IllegalArgumentException("Index can't be less than zero!"); 
/* 135 */         JsonAssetWithMap jsonAssetWithMap = (JsonAssetWithMap)entry.getValue();
/* 136 */         this.array[index] = (T)jsonAssetWithMap;
/*     */         
/* 138 */         putAssetTag(codec, key, index, (T)jsonAssetWithMap);
/*     */       } 
/*     */     } finally {
/* 141 */       this.arrayLock.unlock();
/* 142 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/* 148 */     Set<K> remove = super.remove(keys);
/* 149 */     remove0(keys);
/* 150 */     return remove;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull String packKey, @Nonnull Set<K> keys, @Nonnull List<Map.Entry<String, Object>> pathsToReload) {
/* 155 */     Set<K> remove = super.remove(packKey, keys, pathsToReload);
/* 156 */     remove0(keys);
/* 157 */     return remove;
/*     */   }
/*     */   
/*     */   private void remove0(@Nonnull Set<K> keys) {
/* 161 */     long stamp = this.keyToIndexLock.writeLock();
/* 162 */     this.arrayLock.lock();
/*     */     try {
/* 164 */       for (K key : keys) {
/* 165 */         int blockId = this.keyToIndex.getInt(key);
/* 166 */         if (blockId != Integer.MIN_VALUE) {
/* 167 */           this.array[blockId] = null;
/*     */ 
/*     */           
/* 170 */           this.indexedTagStorage.forEachWithInt((_k, value, id) -> value.remove(id), blockId);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 175 */       int i = this.array.length - 1;
/* 176 */       for (; i > 0 && 
/* 177 */         this.array[i] == null; i--);
/*     */ 
/*     */       
/* 180 */       int length = i + 1;
/* 181 */       if (length != this.array.length) {
/* 182 */         JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 183 */         System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, arrayOfJsonAssetWithMap.length);
/* 184 */         this.array = (T[])arrayOfJsonAssetWithMap;
/*     */       } 
/*     */     } finally {
/* 187 */       this.arrayLock.unlock();
/* 188 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\IndexedLookupTableAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */