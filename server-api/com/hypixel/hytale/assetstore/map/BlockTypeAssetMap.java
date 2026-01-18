/*     */ package com.hypixel.hytale.assetstore.map;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BlockTypeAssetMap<K, T extends JsonAssetWithMap<K, BlockTypeAssetMap<K, T>>>
/*     */   extends AssetMapWithIndexes<K, T>
/*     */ {
/*  27 */   private final AtomicInteger nextIndex = new AtomicInteger();
/*  28 */   private final StampedLock keyToIndexLock = new StampedLock();
/*  29 */   private final Object2IntMap<K> keyToIndex = (Object2IntMap<K>)new Object2IntOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   
/*     */   @Nonnull
/*     */   private final IntFunction<T[]> arrayProvider;
/*  33 */   private final ReentrantLock arrayLock = new ReentrantLock();
/*     */   
/*     */   private T[] array;
/*  36 */   private final Map<K, ObjectSet<K>> subKeyMap = (Map<K, ObjectSet<K>>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   
/*     */   @Deprecated
/*     */   private final Function<T, String> groupGetter;
/*     */   @Deprecated
/*  41 */   private final Object2IntMap<String> groupMap = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */ 
/*     */   
/*     */   public BlockTypeAssetMap(@Nonnull IntFunction<T[]> arrayProvider, Function<T, String> groupGetter) {
/*  45 */     this.arrayProvider = arrayProvider;
/*  46 */     this.groupGetter = groupGetter;
/*     */ 
/*     */     
/*  49 */     this.array = arrayProvider.apply(0);
/*     */     
/*  51 */     this.keyToIndex.defaultReturnValue(-2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(K key) {
/*  59 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  60 */     int value = this.keyToIndex.getInt(key);
/*  61 */     if (this.keyToIndexLock.validate(stamp)) {
/*  62 */       return value;
/*     */     }
/*  64 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  66 */       return this.keyToIndex.getInt(key);
/*     */     } finally {
/*  68 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexOrDefault(K key, int def) {
/*  78 */     long stamp = this.keyToIndexLock.tryOptimisticRead();
/*  79 */     int value = this.keyToIndex.getOrDefault(key, def);
/*  80 */     if (this.keyToIndexLock.validate(stamp)) {
/*  81 */       return value;
/*     */     }
/*  83 */     stamp = this.keyToIndexLock.readLock();
/*     */     try {
/*  85 */       return this.keyToIndex.getOrDefault(key, def);
/*     */     } finally {
/*  87 */       this.keyToIndexLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNextIndex() {
/*  92 */     this.arrayLock.lock();
/*     */     try {
/*  94 */       return this.array.length;
/*     */     } finally {
/*  96 */       this.arrayLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getAsset(int index) {
/* 102 */     if (index < 0 || index >= this.array.length) return null; 
/* 103 */     return this.array[index];
/*     */   }
/*     */   
/*     */   public T getAssetOrDefault(int index, T def) {
/* 107 */     if (index < 0 || index >= this.array.length) return def; 
/* 108 */     return this.array[index];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ObjectSet<K> getSubKeys(K key) {
/* 113 */     ObjectSet<K> subKeySet = this.subKeyMap.get(key);
/* 114 */     return (subKeySet != null) ? ObjectSets.unmodifiable(subKeySet) : ObjectSets.singleton(key);
/*     */   }
/*     */   
/*     */   public int getGroupId(String group) {
/* 118 */     return this.groupMap.getInt(group);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String[] getGroups() {
/* 123 */     return (String[])this.groupMap.keySet().toArray(x$0 -> new String[x$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/* 128 */     super.clear();
/* 129 */     long stamp = this.keyToIndexLock.writeLock();
/* 130 */     this.arrayLock.lock();
/*     */     try {
/* 132 */       this.keyToIndex.clear();
/* 133 */       this.array = this.arrayProvider.apply(0);
/*     */     } finally {
/* 135 */       this.arrayLock.unlock();
/* 136 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/* 142 */     super.putAll(packKey, codec, loadedAssets, loadedKeyToPathMap, loadedAssetChildren);
/* 143 */     putAll0(codec, loadedAssets);
/*     */   }
/*     */   
/*     */   private void putAll0(@Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets) {
/* 147 */     long stamp = this.keyToIndexLock.writeLock();
/* 148 */     this.arrayLock.lock();
/*     */     try {
/* 150 */       int highestIndex = 0;
/* 151 */       for (K key : loadedAssets.keySet()) {
/* 152 */         int index = this.keyToIndex.getInt(key);
/* 153 */         if (index == Integer.MIN_VALUE) {
/* 154 */           this.keyToIndex.put(key, index = this.nextIndex.getAndIncrement());
/*     */         }
/* 156 */         if (index < 0) throw new IllegalArgumentException("Index can't be less than zero!"); 
/* 157 */         if (index > highestIndex) highestIndex = index;
/*     */       
/*     */       } 
/*     */       
/* 161 */       int length = highestIndex + 1;
/* 162 */       if (length < 0) throw new IllegalArgumentException("Highest index can't be less than zero!"); 
/* 163 */       if (length > this.array.length) {
/* 164 */         JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 165 */         System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, this.array.length);
/* 166 */         this.array = (T[])arrayOfJsonAssetWithMap;
/*     */       } 
/*     */       
/* 169 */       for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/* 170 */         K key = entry.getKey();
/* 171 */         int index = this.keyToIndex.getInt(key);
/* 172 */         if (index < 0) throw new IllegalArgumentException("Index can't be less than zero!"); 
/* 173 */         JsonAssetWithMap jsonAssetWithMap = (JsonAssetWithMap)entry.getValue();
/* 174 */         this.array[index] = (T)jsonAssetWithMap;
/*     */         
/* 176 */         ObjectSet<K> subKeySet = this.subKeyMap.get(key);
/* 177 */         if (subKeySet != null) {
/* 178 */           subKeySet.add(key);
/*     */         }
/*     */ 
/*     */         
/* 182 */         String group = this.groupGetter.apply((T)jsonAssetWithMap);
/* 183 */         if (!this.groupMap.containsKey(group)) {
/* 184 */           int groupIndex = this.groupMap.size();
/* 185 */           this.groupMap.put(group, groupIndex);
/*     */         } 
/*     */         
/* 188 */         putAssetTag(codec, key, index, (T)jsonAssetWithMap);
/*     */       } 
/*     */     } finally {
/* 191 */       this.arrayLock.unlock();
/* 192 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/* 198 */     Set<K> remove = super.remove(keys);
/* 199 */     remove0(keys);
/* 200 */     return remove;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull String packKey, @Nonnull Set<K> keys, @Nonnull List<Map.Entry<String, Object>> pathsToReload) {
/* 205 */     Set<K> remove = super.remove(packKey, keys, pathsToReload);
/* 206 */     remove0(keys);
/* 207 */     return remove;
/*     */   }
/*     */   
/*     */   private void remove0(@Nonnull Set<K> keys) {
/* 211 */     long stamp = this.keyToIndexLock.writeLock();
/* 212 */     this.arrayLock.lock();
/*     */     try {
/* 214 */       for (K key : keys) {
/* 215 */         int blockId = this.keyToIndex.getInt(key);
/* 216 */         if (blockId != Integer.MIN_VALUE) {
/* 217 */           this.array[blockId] = null;
/*     */ 
/*     */           
/* 220 */           this.indexedTagStorage.forEachWithInt((_k, value, id) -> value.remove(id), blockId);
/*     */         } 
/*     */ 
/*     */         
/* 224 */         ObjectSet<K> subKeySet = this.subKeyMap.get(key);
/* 225 */         if (subKeySet != null) {
/* 226 */           subKeySet.remove(key);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 231 */       int i = this.array.length - 1;
/* 232 */       for (; i > 0 && 
/* 233 */         this.array[i] == null; i--);
/*     */ 
/*     */       
/* 236 */       int length = i + 1;
/* 237 */       if (length != this.array.length) {
/* 238 */         JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 239 */         System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, arrayOfJsonAssetWithMap.length);
/* 240 */         this.array = (T[])arrayOfJsonAssetWithMap;
/*     */       } 
/*     */     } finally {
/* 243 */       this.arrayLock.unlock();
/* 244 */       this.keyToIndexLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\BlockTypeAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */