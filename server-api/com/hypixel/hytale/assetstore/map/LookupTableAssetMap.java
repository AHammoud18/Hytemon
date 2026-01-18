/*     */ package com.hypixel.hytale.assetstore.map;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntSupplier;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LookupTableAssetMap<K, T extends JsonAssetWithMap<K, LookupTableAssetMap<K, T>>>
/*     */   extends AssetMapWithIndexes<K, T>
/*     */ {
/*     */   @Nonnull
/*     */   private final IntFunction<T[]> arrayProvider;
/*     */   private final ToIntFunction<K> indexGetter;
/*     */   private final IntSupplier maxIndexGetter;
/*  33 */   private final ReentrantLock arrayLock = new ReentrantLock();
/*     */   private T[] array;
/*     */   
/*     */   public LookupTableAssetMap(@Nonnull IntFunction<T[]> arrayProvider, ToIntFunction<K> indexGetter, IntSupplier maxIndexGetter) {
/*  37 */     this.arrayProvider = arrayProvider;
/*  38 */     this.indexGetter = indexGetter;
/*  39 */     this.maxIndexGetter = maxIndexGetter;
/*     */ 
/*     */     
/*  42 */     this.array = arrayProvider.apply(0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getAsset(int index) {
/*  47 */     if (index < 0 || index >= this.array.length) return null; 
/*  48 */     return this.array[index];
/*     */   }
/*     */   
/*     */   public T getAssetOrDefault(int index, T def) {
/*  52 */     if (index < 0 || index >= this.array.length) return def; 
/*  53 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clear() {
/*  58 */     super.clear();
/*  59 */     this.arrayLock.lock();
/*     */     try {
/*  61 */       this.array = this.arrayProvider.apply(0);
/*     */     } finally {
/*  63 */       this.arrayLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putAll(@Nonnull String packKey, @Nonnull AssetCodec<K, T> codec, @Nonnull Map<K, T> loadedAssets, @Nonnull Map<K, Path> loadedKeyToPathMap, @Nonnull Map<K, Set<K>> loadedAssetChildren) {
/*  69 */     super.putAll(packKey, codec, loadedAssets, loadedKeyToPathMap, loadedAssetChildren);
/*  70 */     this.arrayLock.lock();
/*     */     try {
/*  72 */       resize();
/*     */       
/*  74 */       for (Map.Entry<K, T> entry : loadedAssets.entrySet()) {
/*  75 */         K key = entry.getKey();
/*  76 */         int index = this.indexGetter.applyAsInt(key);
/*  77 */         if (index < 0) throw new IllegalArgumentException("Index can't be less than zero!"); 
/*  78 */         if (index >= this.array.length) throw new IllegalArgumentException("Index can't be higher than the max index!"); 
/*  79 */         JsonAssetWithMap jsonAssetWithMap = (JsonAssetWithMap)entry.getValue();
/*  80 */         this.array[index] = (T)jsonAssetWithMap;
/*     */         
/*  82 */         putAssetTag(codec, key, index, (T)jsonAssetWithMap);
/*     */       } 
/*     */     } finally {
/*  85 */       this.arrayLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull Set<K> keys) {
/*  91 */     Set<K> remove = super.remove(keys);
/*  92 */     remove0(keys);
/*  93 */     return remove;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<K> remove(@Nonnull String packKey, @Nonnull Set<K> keys, @Nonnull List<Map.Entry<String, Object>> pathsToReload) {
/*  98 */     Set<K> remove = super.remove(packKey, keys, pathsToReload);
/*  99 */     remove0(keys);
/* 100 */     return remove;
/*     */   }
/*     */   
/*     */   private void remove0(@Nonnull Set<K> keys) {
/* 104 */     this.arrayLock.lock();
/*     */     try {
/* 106 */       for (Iterator<K> iterator = keys.iterator(); iterator.hasNext(); ) { K key = iterator.next();
/* 107 */         int blockId = this.indexGetter.applyAsInt(key);
/* 108 */         if (blockId != Integer.MIN_VALUE) {
/* 109 */           this.array[blockId] = null;
/*     */ 
/*     */           
/* 112 */           this.indexedTagStorage.forEachWithInt((_k, value, id) -> value.remove(id), blockId);
/*     */         }  }
/*     */ 
/*     */       
/* 116 */       resize();
/*     */     } finally {
/* 118 */       this.arrayLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resize() {
/* 124 */     int length = this.maxIndexGetter.getAsInt();
/* 125 */     if (length < 0) throw new IllegalArgumentException("max index can't be less than zero!"); 
/* 126 */     if (length > this.array.length) {
/* 127 */       JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 128 */       System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, this.array.length);
/* 129 */       this.array = (T[])arrayOfJsonAssetWithMap;
/* 130 */     } else if (length < this.array.length) {
/* 131 */       for (int i = length; i < this.array.length; i++) {
/* 132 */         if (this.array[i] != null) throw new IllegalArgumentException("Assets exist in the array outside of the max index!"); 
/*     */       } 
/* 134 */       JsonAssetWithMap[] arrayOfJsonAssetWithMap = (JsonAssetWithMap[])this.arrayProvider.apply(length);
/* 135 */       System.arraycopy(this.array, 0, arrayOfJsonAssetWithMap, 0, arrayOfJsonAssetWithMap.length);
/* 136 */       this.array = (T[])arrayOfJsonAssetWithMap;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requireReplaceOnRemove() {
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\LookupTableAssetMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */