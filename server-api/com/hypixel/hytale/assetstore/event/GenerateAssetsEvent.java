/*     */ package com.hypixel.hytale.assetstore.event;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.JsonAsset;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.event.IProcessedEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class GenerateAssetsEvent<K, T extends JsonAssetWithMap<K, M>, M extends AssetMap<K, T>>
/*     */   extends AssetsEvent<K, T>
/*     */   implements IProcessedEvent
/*     */ {
/*     */   private final Class<T> tClass;
/*     */   private final M assetMap;
/*     */   @Nonnull
/*     */   private final Map<K, T> loadedAssets;
/*     */   private final Map<K, Set<K>> assetChildren;
/*     */   @Nonnull
/*     */   private final Map<K, T> unmodifiableLoadedAssets;
/*  32 */   private final Map<K, T> addedAssets = new ConcurrentHashMap<>();
/*  33 */   private final Map<K, Set<K>> addedAssetChildren = new ConcurrentHashMap<>();
/*  34 */   private final Map<Class<? extends JsonAssetWithMap<?, ?>>, Map<?, Set<K>>> addedChildAssetsMap = new ConcurrentHashMap<>();
/*     */   
/*     */   private long before;
/*     */   
/*     */   public GenerateAssetsEvent(Class<T> tClass, M assetMap, @Nonnull Map<K, T> loadedAssets, Map<K, Set<K>> assetChildren) {
/*  39 */     this.tClass = tClass;
/*  40 */     this.assetMap = assetMap;
/*  41 */     this.loadedAssets = loadedAssets;
/*  42 */     this.assetChildren = assetChildren;
/*     */     
/*  44 */     this.unmodifiableLoadedAssets = Collections.unmodifiableMap(loadedAssets);
/*  45 */     this.before = System.nanoTime();
/*     */   }
/*     */   
/*     */   public Class<T> getAssetClass() {
/*  49 */     return this.tClass;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<K, T> getLoadedAssets() {
/*  54 */     return this.unmodifiableLoadedAssets;
/*     */   }
/*     */   
/*     */   public M getAssetMap() {
/*  58 */     return this.assetMap;
/*     */   }
/*     */   
/*     */   public void addChildAsset(K childKey, T asset, @Nonnull K parent) {
/*  62 */     if (!this.loadedAssets.containsKey(parent) && this.assetMap.getAsset(parent) == null) {
/*  63 */       throw new IllegalArgumentException("Parent '" + String.valueOf(parent) + "' doesn't exist!");
/*     */     }
/*  65 */     if (parent.equals(childKey)) throw new IllegalArgumentException("Unable to to add asset '" + String.valueOf(parent) + "' because it is its own parent!");
/*     */     
/*  67 */     AssetStore<K, T, M> assetStore = AssetRegistry.getAssetStore(this.tClass);
/*  68 */     AssetExtraInfo<K> extraInfo = new AssetExtraInfo(assetStore.getCodec().getData((JsonAsset)asset));
/*  69 */     assetStore.getCodec().validate(asset, (ExtraInfo)extraInfo);
/*  70 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(assetStore.getLogger());
/*     */     
/*  72 */     this.addedAssets.put(childKey, asset);
/*     */     
/*  74 */     ((Set<K>)this.addedAssetChildren.computeIfAbsent(parent, k -> new HashSet())).add(childKey);
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final void addChildAsset(K childKey, T asset, @Nonnull K... parents) {
/*  79 */     for (int i = 0; i < parents.length; i++) {
/*  80 */       K parent = parents[i];
/*  81 */       if (!this.loadedAssets.containsKey(parent) && this.assetMap.getAsset(parent) == null) {
/*  82 */         throw new IllegalArgumentException("Parent at " + i + " '" + String.valueOf(parent) + "' doesn't exist!");
/*     */       }
/*  84 */       if (parent.equals(childKey)) throw new IllegalArgumentException("Unable to to add asset '" + String.valueOf(parent) + "' because it is its own parent!");
/*     */     
/*     */     } 
/*  87 */     AssetStore<K, T, M> assetStore = AssetRegistry.getAssetStore(this.tClass);
/*  88 */     AssetExtraInfo<K> extraInfo = new AssetExtraInfo(assetStore.getCodec().getData((JsonAsset)asset));
/*  89 */     assetStore.getCodec().validate(asset, (ExtraInfo)extraInfo);
/*  90 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(assetStore.getLogger());
/*     */     
/*  92 */     this.addedAssets.put(childKey, asset);
/*     */     
/*  94 */     for (K parent : parents) {
/*  95 */       ((Set<K>)this.addedAssetChildren.computeIfAbsent(parent, k -> new HashSet())).add(childKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public <P extends JsonAssetWithMap<PK, ?>, PK> void addChildAssetWithReference(K childKey, T asset, Class<P> parentAssetClass, @Nonnull PK parentKey) {
/* 100 */     Class<P> kc = parentAssetClass;
/*     */     
/* 102 */     if (AssetRegistry.getAssetStore(kc).getAssetMap().getAsset(parentKey) == null) {
/* 103 */       throw new IllegalArgumentException("Parent '" + String.valueOf(parentKey) + "' from " + String.valueOf(parentAssetClass) + " doesn't exist!");
/*     */     }
/* 105 */     if (parentKey.equals(childKey)) throw new IllegalArgumentException("Unable to to add asset '" + String.valueOf(parentKey) + "' because it is its own parent!");
/*     */     
/* 107 */     AssetStore<K, T, M> assetStore = AssetRegistry.getAssetStore(this.tClass);
/* 108 */     AssetExtraInfo<K> extraInfo = new AssetExtraInfo(assetStore.getCodec().getData((JsonAsset)asset));
/* 109 */     assetStore.getCodec().validate(asset, (ExtraInfo)extraInfo);
/* 110 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(assetStore.getLogger());
/*     */     
/* 112 */     this.addedAssets.put(childKey, asset);
/*     */ 
/*     */     
/* 115 */     ((Set<K>)((Map<PK, Set<K>>)this.addedChildAssetsMap.computeIfAbsent(parentAssetClass, k -> new ConcurrentHashMap<>()))
/* 116 */       .computeIfAbsent(parentKey, k -> new HashSet()))
/* 117 */       .add(childKey);
/*     */   }
/*     */   
/*     */   public void addChildAssetWithReferences(K childKey, T asset, @Nonnull ParentReference<?, ?>... parents) {
/* 121 */     for (int i = 0; i < parents.length; i++) {
/* 122 */       ParentReference<?, ?> parent = parents[i];
/*     */       
/* 124 */       if (AssetRegistry.getAssetStore(parent.getParentAssetClass()).getAssetMap().getAsset(parent.getParentKey()) == null) {
/* 125 */         throw new IllegalArgumentException("Parent at " + i + " '" + String.valueOf(parent) + "' doesn't exist!");
/*     */       }
/* 127 */       if (parent.parentKey.equals(childKey)) throw new IllegalArgumentException("Unable to to add asset '" + String.valueOf(parent.parentKey) + "' because it is its own parent!");
/*     */     
/*     */     } 
/* 130 */     AssetStore<K, T, M> assetStore = AssetRegistry.getAssetStore(this.tClass);
/* 131 */     AssetExtraInfo<K> extraInfo = new AssetExtraInfo(assetStore.getCodec().getData((JsonAsset)asset));
/* 132 */     assetStore.getCodec().validate(asset, (ExtraInfo)extraInfo);
/* 133 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(assetStore.getLogger());
/*     */     
/* 135 */     this.addedAssets.put(childKey, asset);
/*     */     
/* 137 */     for (ParentReference<?, ?> parent : parents)
/*     */     {
/* 139 */       ((Set<K>)((Map<PK, Set<K>>)this.addedChildAssetsMap.computeIfAbsent(parent.parentAssetClass, k -> new ConcurrentHashMap<>()))
/* 140 */         .computeIfAbsent(parent.parentKey, k -> new HashSet()))
/* 141 */         .add(childKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEvent(@Nonnull String hookName) {
/* 147 */     HytaleLogger.getLogger().at(Level.INFO).log("Generated %d of %s from %s in %s", Integer.valueOf(this.addedAssets.size()), this.tClass.getSimpleName(), hookName, FormatUtil.nanosToString(System.nanoTime() - this.before));
/*     */     
/* 149 */     this.loadedAssets.putAll(this.addedAssets);
/* 150 */     this.addedAssets.clear();
/*     */     
/* 152 */     for (Map.Entry<K, Set<K>> entry : this.addedAssetChildren.entrySet()) {
/* 153 */       K parent = entry.getKey();
/* 154 */       ((Set)this.assetChildren.computeIfAbsent(parent, k -> ConcurrentHashMap.newKeySet())).addAll(entry.getValue());
/*     */     } 
/* 156 */     this.addedAssetChildren.clear();
/*     */ 
/*     */     
/* 159 */     for (Map.Entry<Class<? extends JsonAssetWithMap<?, ?>>, Map<?, Set<K>>> entry : this.addedChildAssetsMap.entrySet()) {
/* 160 */       Class k = entry.getKey();
/*     */       
/* 162 */       AssetStore assetStore = AssetRegistry.getAssetStore(k);
/* 163 */       for (Map.Entry<?, Set<K>> childEntry : (Iterable<Map.Entry<?, Set<K>>>)((Map)entry.getValue()).entrySet())
/*     */       {
/* 165 */         assetStore.addChildAssetReferences(childEntry.getKey(), this.tClass, childEntry.getValue());
/*     */       }
/*     */     } 
/* 168 */     this.addedChildAssetsMap.clear();
/*     */     
/* 170 */     this.before = System.nanoTime();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 176 */     return "GenerateAssetsEvent{tClass=" + String.valueOf(this.tClass) + ", loadedAssets.size()=" + this.loadedAssets
/*     */       
/* 178 */       .size() + ", " + super
/* 179 */       .toString() + "}";
/*     */   }
/*     */   
/*     */   public static class ParentReference<P extends JsonAssetWithMap<PK, ?>, PK> {
/*     */     private final Class<P> parentAssetClass;
/*     */     private final PK parentKey;
/*     */     
/*     */     public ParentReference(Class<P> parentAssetClass, PK parentKey) {
/* 187 */       this.parentAssetClass = parentAssetClass;
/* 188 */       this.parentKey = parentKey;
/*     */     }
/*     */     
/*     */     public Class<P> getParentAssetClass() {
/* 192 */       return this.parentAssetClass;
/*     */     }
/*     */     
/*     */     public PK getParentKey() {
/* 196 */       return this.parentKey;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 202 */       return "ParentReference{parentAssetClass=" + String.valueOf(this.parentAssetClass) + ", parentKey=" + String.valueOf(this.parentKey) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\event\GenerateAssetsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */