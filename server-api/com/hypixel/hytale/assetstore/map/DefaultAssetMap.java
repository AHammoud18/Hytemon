/*     */ package com.hypixel.hytale.assetstore.map;
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.JsonAsset;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class DefaultAssetMap<K, T extends JsonAsset<K>> extends AssetMap<K, T> {
/*  29 */   public static final AssetRef[] EMPTY_PAIR_ARRAY = new AssetRef[0];
/*     */   
/*     */   public static final String DEFAULT_PACK_KEY = "Hytale:Hytale";
/*  32 */   protected final StampedLock assetMapLock = new StampedLock();
/*     */   @Nonnull
/*     */   protected final Map<K, T> assetMap;
/*     */   protected final Map<K, AssetRef<T>[]> assetChainMap;
/*  36 */   protected final Map<String, ObjectSet<K>> packAssetKeys = new ConcurrentHashMap<>();
/*     */   
/*  38 */   protected final Map<Path, ObjectSet<K>> pathToKeyMap = new ConcurrentHashMap<>();
/*     */   
/*     */   protected final Map<K, ObjectSet<K>> assetChildren;
/*  41 */   protected final Int2ObjectConcurrentHashMap<Set<K>> tagStorage = new Int2ObjectConcurrentHashMap();
/*  42 */   protected final Int2ObjectConcurrentHashMap<Set<K>> unmodifiableTagStorage = new Int2ObjectConcurrentHashMap();
/*  43 */   protected final IntSet unmodifiableTagKeys = IntSets.unmodifiable((IntSet)this.tagStorage.keySet());
/*     */   
/*     */   public DefaultAssetMap() {
/*  46 */     this.assetMap = (Map<K, T>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*  47 */     this.assetChainMap = (Map<K, AssetRef<T>[]>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*  48 */     this.assetChildren = (Map<K, ObjectSet<K>>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   }
/*     */   
/*     */   public DefaultAssetMap(@Nonnull Map<K, T> assetMap) {
/*  52 */     this.assetMap = assetMap;
/*  53 */     this.assetChainMap = (Map<K, AssetRef<T>[]>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*  54 */     this.assetChildren = (Map<K, ObjectSet<K>>)new Object2ObjectOpenCustomHashMap(CaseInsensitiveHashStrategy.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getAsset(K key) {
/*  60 */     long stamp = this.assetMapLock.tryOptimisticRead();
/*  61 */     JsonAsset jsonAsset = (JsonAsset)this.assetMap.get(key);
/*  62 */     if (this.assetMapLock.validate(stamp)) {
/*  63 */       return (T)jsonAsset;
/*     */     }
/*  65 */     stamp = this.assetMapLock.readLock();
/*     */     try {
/*  67 */       return this.assetMap.get(key);
/*     */     } finally {
/*  69 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getAsset(@Nonnull String packKey, K key) {
/*  76 */     long stamp = this.assetMapLock.tryOptimisticRead();
/*  77 */     T result = getAssetForPack0(packKey, key);
/*  78 */     if (this.assetMapLock.validate(stamp)) {
/*  79 */       return result;
/*     */     }
/*  81 */     stamp = this.assetMapLock.readLock();
/*     */     try {
/*  83 */       return getAssetForPack0(packKey, key);
/*     */     } finally {
/*  85 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   private T getAssetForPack0(@Nonnull String packKey, K key) {
/*  90 */     AssetRef[] arrayOfAssetRef = (AssetRef[])this.assetChainMap.get(key);
/*  91 */     if (arrayOfAssetRef == null) return null; 
/*  92 */     for (int i = 0; i < arrayOfAssetRef.length; i++) {
/*  93 */       AssetRef<T> pair = arrayOfAssetRef[i];
/*  94 */       if (Objects.equals(pair.pack, packKey)) {
/*  95 */         if (i == 0) return null; 
/*  96 */         return (arrayOfAssetRef[i - 1]).value;
/*     */       } 
/*     */     } 
/*  99 */     return this.assetMap.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path getPath(K key) {
/* 105 */     long stamp = this.assetMapLock.tryOptimisticRead();
/* 106 */     Path result = getPath0(key);
/* 107 */     if (this.assetMapLock.validate(stamp)) {
/* 108 */       return result;
/*     */     }
/* 110 */     stamp = this.assetMapLock.readLock();
/*     */     try {
/* 112 */       return getPath0(key);
/*     */     } finally {
/* 114 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getAssetPack(K key) {
/* 121 */     long stamp = this.assetMapLock.tryOptimisticRead();
/* 122 */     String result = getAssetPack0(key);
/* 123 */     if (this.assetMapLock.validate(stamp)) {
/* 124 */       return result;
/*     */     }
/* 126 */     stamp = this.assetMapLock.readLock();
/*     */     try {
/* 128 */       return getAssetPack0(key);
/*     */     } finally {
/* 130 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Path getPath0(K key) {
/* 136 */     AssetRef<T> result = getAssetRef(key);
/* 137 */     return (result != null) ? result.path : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String getAssetPack0(K key) {
/* 142 */     AssetRef<T> result = getAssetRef(key);
/* 143 */     return (result != null) ? result.pack : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private AssetRef<T> getAssetRef(K key) {
/* 148 */     AssetRef[] arrayOfAssetRef = (AssetRef[])this.assetChainMap.get(key);
/* 149 */     if (arrayOfAssetRef == null) return null; 
/* 150 */     return arrayOfAssetRef[arrayOfAssetRef.length - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> getKeys(@Nonnull Path path) {
/* 155 */     ObjectSet<K> set = this.pathToKeyMap.get(path);
/*     */     
/* 157 */     return (set == null) ? (Set<K>)ObjectSets.emptySet() : (Set<K>)ObjectSets.unmodifiable(set);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> getChildren(K key) {
/* 162 */     long stamp = this.assetMapLock.tryOptimisticRead();
/* 163 */     ObjectSet<K> children = this.assetChildren.get(key);
/* 164 */     ObjectSet objectSet = (children == null) ? ObjectSets.emptySet() : ObjectSets.unmodifiable(children);
/* 165 */     if (this.assetMapLock.validate(stamp)) {
/* 166 */       return (Set<K>)objectSet;
/*     */     }
/* 168 */     stamp = this.assetMapLock.readLock();
/*     */     try {
/* 170 */       children = this.assetChildren.get(key);
/* 171 */       return (Set<K>)((children == null) ? ObjectSets.emptySet() : ObjectSets.unmodifiable(children));
/*     */     } finally {
/* 173 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAssetCount() {
/* 179 */     return this.assetMap.size();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<K, T> getAssetMap() {
/* 185 */     return Collections.unmodifiableMap(this.assetMap);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<K, Path> getPathMap(@Nonnull String packKey) {
/* 191 */     long stamp = this.assetMapLock.readLock();
/*     */     try {
/* 193 */       return (Map)this.assetChainMap.entrySet()
/* 194 */         .stream()
/* 195 */         .map(e -> Map.entry(e.getKey(), Arrays.<AssetRef>stream((AssetRef[])e.getValue()).filter(()).findFirst()))
/*     */ 
/*     */ 
/*     */         
/* 199 */         .filter(e -> ((Optional)e.getValue()).isPresent())
/* 200 */         .filter(e -> (((AssetRef)((Optional)e.getValue()).get()).path != null))
/* 201 */         .collect(Collectors.toMap(Map.Entry::getKey, e -> ((AssetRef)((Optional)e.getValue()).get()).path));
/*     */     } finally {
/* 203 */       this.assetMapLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> getKeysForTag(int tagIndex) {
/* 209 */     return (Set<K>)this.unmodifiableTagStorage.getOrDefault(tagIndex, ObjectSets.emptySet());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IntSet getTagIndexes() {
/* 215 */     return this.unmodifiableTagKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagCount() {
/* 220 */     return this.tagStorage.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/* 225 */     long stamp = this.assetMapLock.writeLock();
/*     */     try {
/* 227 */       this.assetChildren.clear();
/* 228 */       this.assetChainMap.clear();
/* 229 */       this.pathToKeyMap.clear();
/* 230 */       this.assetMap.clear();
/* 231 */       this.tagStorage.clear();
/* 232 */       this.unmodifiableTagStorage.clear();
/*     */     } finally {
/* 234 */       this.assetMapLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/* 241 */     long stamp = this.assetMapLock.writeLock();
/*     */     try {
/* 243 */       for (Map.Entry<K, Set<K>> entry : loadedAssetChildren.entrySet()) {
/* 244 */         ((ObjectSet)this.assetChildren.computeIfAbsent(entry.getKey(), k -> new ObjectOpenHashSet(3))).addAll(entry.getValue());
/*     */       }
/* 246 */       for (Map.Entry<K, Path> entry : loadedKeyToPathMap.entrySet()) {
/* 247 */         ((ObjectSet)this.pathToKeyMap.computeIfAbsent(entry.getValue(), k -> new ObjectOpenHashSet(1))).add(entry.getKey());
/*     */       }
/*     */       
/* 250 */       for (Map.Entry<K, T> e : loadedAssets.entrySet()) {
/* 251 */         K key = e.getKey();
/* 252 */         ((ObjectSet)this.packAssetKeys.computeIfAbsent(packKey, v -> new ObjectOpenHashSet())).add(key);
/*     */         
/* 254 */         AssetRef[] arrayOfAssetRef = (AssetRef[])this.assetChainMap.get(key);
/* 255 */         if (arrayOfAssetRef == null) {
/* 256 */           arrayOfAssetRef = EMPTY_PAIR_ARRAY;
/*     */         }
/* 258 */         boolean found = false;
/* 259 */         for (AssetRef<T> pair : arrayOfAssetRef) {
/* 260 */           if (Objects.equals(pair.pack, packKey)) {
/* 261 */             pair.value = e.getValue();
/* 262 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 266 */         if (!found) {
/* 267 */           arrayOfAssetRef = Arrays.<AssetRef>copyOf(arrayOfAssetRef, arrayOfAssetRef.length + 1);
/* 268 */           arrayOfAssetRef[arrayOfAssetRef.length - 1] = new AssetRef<>(packKey, loadedKeyToPathMap.get(e.getKey()), (JsonAsset)e.getValue());
/* 269 */           this.assetChainMap.put(key, arrayOfAssetRef);
/*     */         } 
/* 271 */         JsonAsset jsonAsset = (JsonAsset)(arrayOfAssetRef[arrayOfAssetRef.length - 1]).value;
/* 272 */         this.assetMap.put(key, (T)jsonAsset);
/*     */       } 
/*     */     } finally {
/* 275 */       this.assetMapLock.unlockWrite(stamp);
/*     */     } 
/*     */     
/* 278 */     putAssetTags(codec, loadedAssets);
/*     */   }
/*     */   
/*     */   protected void putAssetTags(@Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets) {
/* 282 */     for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/* 283 */       AssetExtraInfo.Data data = codec.getData((JsonAsset)entry.getValue());
/* 284 */       if (data == null)
/*     */         continue; 
/* 286 */       K key = entry.getKey();
/*     */       
/* 288 */       IntIterator iterator = data.getExpandedTagIndexes().iterator();
/* 289 */       while (iterator.hasNext()) {
/* 290 */         int tag = iterator.nextInt();
/* 291 */         putAssetTag(key, tag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void putAssetTag(K key, int tag) {
/* 297 */     ((Set<K>)this.tagStorage.computeIfAbsent(tag, k -> {
/*     */           ObjectOpenHashSet<K> set = new ObjectOpenHashSet(3);
/*     */           this.unmodifiableTagStorage.put(k, ObjectSets.unmodifiable((ObjectSet)set));
/*     */           return (Set)set;
/* 301 */         })).add(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<K> getKeysForPack(@Nonnull String name) {
/* 306 */     return (Set<K>)this.packAssetKeys.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/* 311 */     long stamp = this.assetMapLock.writeLock();
/*     */     try {
/* 313 */       Set<K> children = new HashSet<>();
/* 314 */       for (K key : keys) {
/* 315 */         AssetRef[] arrayOfAssetRef = (AssetRef[])this.assetChainMap.remove(key);
/* 316 */         if (arrayOfAssetRef == null)
/* 317 */           continue;  AssetRef<T> info = arrayOfAssetRef[arrayOfAssetRef.length - 1];
/* 318 */         if (info.path != null) {
/* 319 */           this.pathToKeyMap.computeIfPresent(info.path, (p, list) -> {
/*     */                 list.remove(key);
/*     */                 
/*     */                 return list.isEmpty() ? null : list;
/*     */               });
/*     */         }
/* 325 */         this.assetMap.remove(key);
/*     */         
/* 327 */         for (AssetRef<T> c : arrayOfAssetRef) {
/* 328 */           ((ObjectSet)this.packAssetKeys.get(Objects.requireNonNullElse(c.pack, "Hytale:Hytale"))).remove(key);
/*     */         }
/*     */ 
/*     */         
/* 332 */         for (ObjectSet<K> objectSet : this.assetChildren.values()) {
/* 333 */           objectSet.remove(key);
/*     */         }
/*     */ 
/*     */         
/* 337 */         ObjectSet<K> child = this.assetChildren.remove(key);
/* 338 */         if (child != null) children.addAll((Collection<? extends K>)child);
/*     */       
/*     */       } 
/*     */       
/* 342 */       this.tagStorage.forEach((_k, value, removedKeys) -> value.removeAll(removedKeys), keys);
/*     */ 
/*     */       
/* 345 */       children.removeAll(keys);
/* 346 */       return children;
/*     */     } finally {
/* 348 */       this.assetMapLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull String packKey, @Nonnull Set<K> keys, @Nonnull List<Map.Entry<String, Object>> pathsToReload) {
/* 354 */     long stamp = this.assetMapLock.writeLock();
/*     */     try {
/* 356 */       Set<K> children = new HashSet<>();
/* 357 */       ObjectSet<K> packKeys = this.packAssetKeys.get(Objects.requireNonNullElse(packKey, "Hytale:Hytale"));
/* 358 */       if (packKeys == null) return (Set)Collections.emptySet();
/*     */       
/* 360 */       for (Iterator<K> iterator = keys.iterator(); iterator.hasNext(); ) {
/* 361 */         K key = iterator.next();
/* 362 */         packKeys.remove(key);
/* 363 */         AssetRef[] arrayOfAssetRef1 = (AssetRef[])this.assetChainMap.remove(key);
/* 364 */         if (arrayOfAssetRef1.length == 1) {
/* 365 */           AssetRef<T> info = arrayOfAssetRef1[0];
/* 366 */           if (!Objects.equals(info.pack, packKey)) {
/* 367 */             iterator.remove();
/* 368 */             this.assetChainMap.put(key, arrayOfAssetRef1);
/*     */             continue;
/*     */           } 
/* 371 */           if (info.path != null) {
/* 372 */             this.pathToKeyMap.computeIfPresent(info.path, (p, list) -> {
/*     */                   list.remove(key);
/*     */                   
/*     */                   return list.isEmpty() ? null : list;
/*     */                 });
/*     */           }
/* 378 */           this.assetMap.remove(key);
/*     */ 
/*     */           
/* 381 */           for (ObjectSet<K> objectSet : this.assetChildren.values()) {
/* 382 */             objectSet.remove(key);
/*     */           }
/*     */ 
/*     */           
/* 386 */           ObjectSet<K> child = this.assetChildren.remove(key);
/* 387 */           if (child != null) children.addAll((Collection<? extends K>)child);  continue;
/*     */         } 
/* 389 */         iterator.remove();
/* 390 */         AssetRef[] arrayOfAssetRef2 = new AssetRef[arrayOfAssetRef1.length - 1];
/* 391 */         int offset = 0;
/* 392 */         for (int i = 0; i < arrayOfAssetRef1.length; i++) {
/* 393 */           AssetRef<T> pair = arrayOfAssetRef1[i];
/* 394 */           if (Objects.equals(pair.pack, packKey)) {
/* 395 */             if (pair.path != null) {
/* 396 */               this.pathToKeyMap.computeIfPresent(pair.path, (p, list) -> {
/*     */                     list.remove(key);
/*     */ 
/*     */                     
/*     */                     return list.isEmpty() ? null : list;
/*     */                   });
/*     */             }
/*     */           } else {
/* 404 */             arrayOfAssetRef2[offset++] = pair;
/* 405 */             if (pair.path != null) {
/* 406 */               pathsToReload.add(Map.entry(pair.pack, pair.path));
/*     */             } else {
/* 408 */               pathsToReload.add(Map.entry(pair.pack, pair.value));
/*     */             } 
/*     */           } 
/* 411 */         }  this.assetChainMap.put(key, arrayOfAssetRef2);
/* 412 */         AssetRef<T> newAsset = arrayOfAssetRef2[arrayOfAssetRef2.length - 1];
/* 413 */         this.assetMap.put(key, newAsset.value);
/* 414 */         if (newAsset.path != null) {
/* 415 */           ((ObjectSet)this.pathToKeyMap.computeIfAbsent(newAsset.path, k -> new ObjectOpenHashSet(1))).add(key);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 421 */       this.tagStorage.forEach((_k, value, removedKeys) -> value.removeAll(removedKeys), keys);
/*     */ 
/*     */       
/* 424 */       children.removeAll(keys);
/* 425 */       return children;
/*     */     } finally {
/* 427 */       this.assetMapLock.unlockWrite(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static class AssetRef<T> {
/*     */     protected final String pack;
/*     */     protected final Path path;
/*     */     protected T value;
/*     */     
/*     */     protected AssetRef(String pack, Path path, T value) {
/* 437 */       this.pack = pack;
/* 438 */       this.path = path;
/* 439 */       this.value = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\DefaultAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */