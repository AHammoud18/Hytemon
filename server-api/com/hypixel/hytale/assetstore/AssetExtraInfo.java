/*     */ package com.hypixel.hytale.assetstore;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetExtraInfo<K> extends ExtraInfo {
/*     */   @Nullable
/*     */   private final Path assetPath;
/*     */   
/*     */   public AssetExtraInfo(Data data) {
/*  26 */     super(2147483647, AssetValidationResults::new);
/*  27 */     this.assetPath = null;
/*  28 */     this.data = data;
/*     */   }
/*     */   private final Data data;
/*     */   public AssetExtraInfo(Path assetPath, Data data) {
/*  32 */     super(2147483647, AssetValidationResults::new);
/*  33 */     this.assetPath = assetPath;
/*  34 */     this.data = data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String generateKey() {
/*  39 */     return "*" + String.valueOf(getKey()) + "_" + peekKey('_');
/*     */   }
/*     */ 
/*     */   
/*     */   public K getKey() {
/*  44 */     return (K)getData().getKey();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path getAssetPath() {
/*  49 */     return this.assetPath;
/*     */   }
/*     */   
/*     */   public Data getData() {
/*  53 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendDetailsTo(@Nonnull StringBuilder sb) {
/*  58 */     sb.append("Id: ").append(getKey()).append("\n");
/*  59 */     if (this.assetPath != null) sb.append("Path: ").append(this.assetPath).append("\n");
/*     */   
/*     */   }
/*     */   
/*     */   public AssetValidationResults getValidationResults() {
/*  64 */     return (AssetValidationResults)super.getValidationResults();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  70 */     return "AssetExtraInfo{assetPath=" + String.valueOf(this.assetPath) + ", data=" + String.valueOf(this.data) + "} " + super
/*     */ 
/*     */       
/*  73 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Data
/*     */   {
/*     */     public static final char TAG_VALUE_SEPARATOR = '=';
/*     */     
/*     */     private Map<Class<? extends JsonAssetWithMap>, List<Object>> containedAssets;
/*     */     
/*     */     private Map<Class<? extends JsonAssetWithMap>, List<RawAsset<Object>>> containedRawAssets;
/*     */     
/*     */     @Nullable
/*     */     private Data containerData;
/*     */     private Class<? extends JsonAsset<?>> assetClass;
/*     */     private Object key;
/*     */     private Object parentKey;
/*  90 */     private final Map<String, String[]> rawTags = (Map)new HashMap<>(0);
/*     */     
/*  92 */     private final Int2ObjectMap<IntSet> tagStorage = (Int2ObjectMap<IntSet>)new Int2ObjectOpenHashMap(0);
/*  93 */     private final Int2ObjectMap<IntSet> unmodifiableTagStorage = (Int2ObjectMap<IntSet>)new Int2ObjectOpenHashMap(0);
/*  94 */     private final IntSet expandedTagStorage = (IntSet)new IntOpenHashSet(0);
/*  95 */     private final IntSet unmodifiableExpandedTagStorage = IntSets.unmodifiable(this.expandedTagStorage);
/*     */     
/*     */     public <K> Data(Class<? extends JsonAsset<K>> assetClass, K key, K parentKey) {
/*  98 */       this.assetClass = assetClass;
/*  99 */       this.key = key;
/* 100 */       this.parentKey = parentKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <K> Data(@Nullable Data containerData, Class<? extends JsonAsset<K>> aClass, K key, K parentKey, boolean inheritContainerTags) {
/* 108 */       this(aClass, key, parentKey);
/* 109 */       this.containerData = containerData;
/* 110 */       if (containerData != null && inheritContainerTags) putTags(containerData.rawTags); 
/*     */     }
/*     */     
/*     */     public Class<? extends JsonAsset<?>> getAssetClass() {
/* 114 */       return this.assetClass;
/*     */     }
/*     */     
/*     */     public Object getKey() {
/* 118 */       return this.key;
/*     */     }
/*     */     
/*     */     public Object getParentKey() {
/* 122 */       return this.parentKey;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Data getRootContainerData() {
/* 127 */       Data temp = this;
/* 128 */       while (temp.containerData != null) {
/* 129 */         temp = temp.containerData;
/*     */       }
/* 131 */       return temp;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Data getContainerData() {
/* 136 */       return this.containerData;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public <K> K getContainerKey(Class<? extends JsonAsset<K>> aClass) {
/* 142 */       if (this.containerData == null) {
/* 143 */         return null;
/*     */       }
/* 145 */       if (this.containerData.assetClass.equals(aClass)) {
/* 146 */         return (K)this.containerData.key;
/*     */       }
/* 148 */       return this.containerData.getContainerKey(aClass);
/*     */     }
/*     */     
/*     */     public void putTags(@Nonnull Map<String, String[]> tags) {
/* 152 */       for (Map.Entry<String, String[]> entry : tags.entrySet()) {
/* 153 */         String tag = ((String)entry.getKey()).intern();
/* 154 */         this.rawTags.merge(tag, entry.getValue(), ArrayUtil::combine);
/* 155 */         IntSet tagIndexes = ensureTag(tag);
/* 156 */         for (String value : (String[])entry.getValue()) {
/* 157 */           tagIndexes.add(AssetRegistry.getOrCreateTagIndex(value));
/*     */ 
/*     */           
/* 160 */           ensureTag(value);
/* 161 */           this.rawTags.putIfAbsent(value, ArrayUtil.EMPTY_STRING_ARRAY);
/*     */           
/* 163 */           String valueTag = (tag + "=" + tag).intern();
/* 164 */           this.rawTags.putIfAbsent(valueTag, ArrayUtil.EMPTY_STRING_ARRAY);
/* 165 */           ensureTag(valueTag);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Map<String, String[]> getRawTags() {
/* 172 */       return (Map)Collections.unmodifiableMap((Map)this.rawTags);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Int2ObjectMap<IntSet> getTags() {
/* 177 */       return Int2ObjectMaps.unmodifiable(this.unmodifiableTagStorage);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public IntSet getExpandedTagIndexes() {
/* 182 */       return this.unmodifiableExpandedTagStorage;
/*     */     }
/*     */     
/*     */     public IntSet getTag(int tagIndex) {
/* 186 */       return (IntSet)this.unmodifiableTagStorage.getOrDefault(tagIndex, IntSets.EMPTY_SET);
/*     */     }
/*     */     
/*     */     public <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> void addContainedAsset(Class<T> assetClass, T asset) {
/* 190 */       if (this.containedAssets == null) this.containedAssets = new HashMap<>(); 
/* 191 */       ((List<T>)this.containedAssets.computeIfAbsent(assetClass, k -> new ArrayList())).add(asset);
/*     */     }
/*     */     
/*     */     public <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> void addContainedAsset(Class<T> assetClass, RawAsset<K> rawAsset) {
/* 195 */       if (this.containedRawAssets == null) this.containedRawAssets = new HashMap<>();
/*     */       
/* 197 */       ((List<RawAsset<K>>)this.containedRawAssets.computeIfAbsent(assetClass, k -> new ArrayList())).add(rawAsset);
/*     */     }
/*     */     
/*     */     public <K> void fetchContainedAssets(K key, @Nonnull Map<Class<? extends JsonAssetWithMap>, Map<K, List<Object>>> containedAssets) {
/* 201 */       if (this.containedAssets == null)
/* 202 */         return;  for (Map.Entry<Class<? extends JsonAssetWithMap>, List<Object>> entry : this.containedAssets.entrySet()) {
/* 203 */         ((List)((Map<K, List>)containedAssets.computeIfAbsent(entry.getKey(), k -> new HashMap<>()))
/* 204 */           .computeIfAbsent(key, k -> new ArrayList(3)))
/* 205 */           .addAll(entry.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */     public <K> void fetchContainedRawAssets(K key, @Nonnull Map<Class<? extends JsonAssetWithMap>, Map<K, List<RawAsset<Object>>>> containedAssets) {
/* 210 */       if (this.containedRawAssets == null)
/* 211 */         return;  for (Map.Entry<Class<? extends JsonAssetWithMap>, List<RawAsset<Object>>> entry : this.containedRawAssets.entrySet()) {
/* 212 */         ((List)((Map<K, List>)containedAssets.computeIfAbsent(entry.getKey(), k -> new HashMap<>()))
/* 213 */           .computeIfAbsent(key, k -> new ArrayList(3)))
/* 214 */           .addAll(entry.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */     public <K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>> boolean containsAsset(Class<T> tClass, K key) {
/* 219 */       if (this.containedAssets != null) {
/*     */         
/* 221 */         List<T> assets = (List<T>)this.containedAssets.get(tClass);
/* 222 */         if (assets != null) {
/* 223 */           Function<T, K> keyFunction = AssetRegistry.getAssetStore(tClass).getKeyFunction();
/* 224 */           for (JsonAssetWithMap jsonAssetWithMap : assets) {
/* 225 */             if (key.equals(keyFunction.apply((T)jsonAssetWithMap))) return true; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 229 */       if (this.containedRawAssets != null) {
/*     */         
/* 231 */         List<RawAsset<T>> rawAssets = (List<RawAsset<T>>)this.containedRawAssets.get(tClass);
/* 232 */         if (rawAssets != null)
/* 233 */           for (RawAsset<T> rawAsset : rawAssets) {
/* 234 */             if (key.equals(rawAsset.getKey())) return true;
/*     */           
/*     */           }  
/*     */       } 
/* 238 */       return false;
/*     */     }
/*     */     
/*     */     public void loadContainedAssets(boolean reloading) {
/* 242 */       if (this.containedAssets != null) {
/* 243 */         for (Map.Entry<Class<? extends JsonAssetWithMap>, List<Object>> entry : this.containedAssets.entrySet())
/*     */         {
/* 245 */           AssetRegistry.getAssetStore((Class<JsonAssetWithMap>)entry.getKey()).loadAssets("Hytale:Hytale", entry.getValue(), AssetUpdateQuery.DEFAULT, reloading);
/*     */         }
/*     */       }
/* 248 */       if (this.containedRawAssets != null) {
/* 249 */         for (Map.Entry<Class<? extends JsonAssetWithMap>, List<RawAsset<Object>>> entry : this.containedRawAssets.entrySet())
/*     */         {
/* 251 */           AssetRegistry.getAssetStore((Class<JsonAssetWithMap>)entry.getKey()).loadBuffersWithKeys("Hytale:Hytale", entry.getValue(), AssetUpdateQuery.DEFAULT, reloading);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private IntSet ensureTag(@Nonnull String tag) {
/* 258 */       int idx = AssetRegistry.getOrCreateTagIndex(tag);
/* 259 */       this.expandedTagStorage.add(idx);
/* 260 */       return (IntSet)this.tagStorage.computeIfAbsent(idx, k -> {
/*     */             IntOpenHashSet intOpenHashSet = new IntOpenHashSet(3);
/*     */             this.unmodifiableTagStorage.put(k, IntSets.unmodifiable((IntSet)intOpenHashSet));
/*     */             return (IntSet)intOpenHashSet;
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 270 */       return "Data{containedAssets=" + String.valueOf(this.containedRawAssets) + ", rawTags=" + String.valueOf(this.rawTags) + ", parent=" + String.valueOf(this.containerData) + ", assetClass=" + String.valueOf(this.assetClass) + ", key=" + String.valueOf(this.key) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\AssetExtraInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */