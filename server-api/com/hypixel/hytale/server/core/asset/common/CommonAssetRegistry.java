/*     */ package com.hypixel.hytale.server.core.asset.common;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.common.util.PatternUtil;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class CommonAssetRegistry
/*     */ {
/*  22 */   private static final Map<String, List<PackAsset>> assetByNameMap = new ConcurrentHashMap<>();
/*  23 */   private static final Map<String, List<PackAsset>> assetByHashMap = new ConcurrentHashMap<>();
/*     */   
/*  25 */   private static final AtomicInteger duplicateAssetCount = new AtomicInteger();
/*     */   
/*  27 */   private static final Collection<List<PackAsset>> unmodifiableAssetByNameMapValues = Collections.unmodifiableCollection(assetByNameMap.values());
/*     */   
/*     */   public static int getDuplicateAssetCount() {
/*  30 */     return duplicateAssetCount.get();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<String, List<PackAsset>> getDuplicatedAssets() {
/*  35 */     Object2ObjectOpenHashMap<String, ObjectArrayList> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*  36 */     for (Map.Entry<String, List<PackAsset>> entry : assetByHashMap.entrySet()) {
/*  37 */       if (((List)entry.getValue()).size() > 1) {
/*  38 */         object2ObjectOpenHashMap.put(entry.getKey(), new ObjectArrayList(entry.getValue()));
/*     */       }
/*     */     } 
/*  41 */     return (Map)object2ObjectOpenHashMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Collection<List<PackAsset>> getAllAssets() {
/*  46 */     return unmodifiableAssetByNameMapValues;
/*     */   }
/*     */   
/*     */   public static void clearAllAssets() {
/*  50 */     assetByNameMap.clear();
/*  51 */     assetByHashMap.clear();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AddCommonAssetResult addCommonAsset(String pack, @Nonnull CommonAsset asset) {
/*  56 */     AddCommonAssetResult result = new AddCommonAssetResult();
/*     */     
/*  58 */     result.newPackAsset = new PackAsset(pack, asset);
/*     */     
/*  60 */     List<PackAsset> list = assetByNameMap.computeIfAbsent(asset.getName(), v -> new CopyOnWriteArrayList());
/*  61 */     boolean added = false;
/*  62 */     boolean addHash = true;
/*  63 */     for (int i = 0; i < list.size(); i++) {
/*  64 */       PackAsset e = list.get(i);
/*  65 */       if (e.pack().equals(pack)) {
/*  66 */         result.previousNameAsset = e;
/*     */         
/*  68 */         if (i == list.size() - 1) {
/*  69 */           ((List)assetByHashMap.get(e.asset.getHash())).remove(e);
/*  70 */           assetByHashMap.compute(e.asset.getHash(), (k, v) -> (v == null || v.isEmpty()) ? null : v);
/*     */         } else {
/*     */           
/*  73 */           addHash = false;
/*     */         } 
/*  75 */         list.set(i, result.newPackAsset);
/*  76 */         added = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  80 */     if (!added) {
/*  81 */       if (!list.isEmpty()) {
/*  82 */         PackAsset e = list.getLast();
/*  83 */         ((List)assetByHashMap.get(e.asset.getHash())).remove(e);
/*  84 */         assetByHashMap.compute(e.asset.getHash(), (k, v) -> (v == null || v.isEmpty()) ? null : v);
/*  85 */         result.previousNameAsset = e;
/*     */       } 
/*  87 */       list.add(result.newPackAsset);
/*     */     } 
/*     */     
/*  90 */     if (addHash) {
/*  91 */       List<PackAsset> commonAssets = assetByHashMap.computeIfAbsent(asset.getHash(), k -> new CopyOnWriteArrayList());
/*  92 */       if (!commonAssets.isEmpty()) result.previousHashAssets = (PackAsset[])commonAssets.toArray(x$0 -> new PackAsset[x$0]); 
/*  93 */       commonAssets.add(result.newPackAsset);
/*     */     } 
/*     */     
/*  96 */     if (result.previousHashAssets != null || result.previousNameAsset != null) {
/*  97 */       result.duplicateAssetId = duplicateAssetCount.getAndIncrement();
/*     */     }
/*     */     
/* 100 */     result.activeAsset = list.getLast();
/*     */     
/* 102 */     return result;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BooleanObjectPair<PackAsset> removeCommonAssetByName(String pack, String name) {
/* 107 */     name = PatternUtil.replaceBackslashWithForwardSlash(name);
/* 108 */     List<PackAsset> oldAssets = assetByNameMap.get(name);
/* 109 */     if (oldAssets == null) return null; 
/* 110 */     PackAsset previousCurrent = oldAssets.getLast();
/* 111 */     oldAssets.removeIf(v -> v.pack().equals(pack));
/* 112 */     assetByNameMap.compute(name, (k, v) -> (v == null || v.isEmpty()) ? null : v);
/* 113 */     if (oldAssets.isEmpty()) {
/*     */       
/* 115 */       removeCommonAssetByHash0(previousCurrent);
/* 116 */       return BooleanObjectPair.of(false, previousCurrent);
/*     */     } 
/*     */     
/* 119 */     PackAsset newCurrent = oldAssets.getLast();
/* 120 */     if (newCurrent.equals(previousCurrent)) return null; 
/* 121 */     removeCommonAssetByHash0(previousCurrent);
/* 122 */     ((List<PackAsset>)assetByHashMap.computeIfAbsent(newCurrent.asset.getHash(), v -> new CopyOnWriteArrayList())).add(newCurrent);
/* 123 */     return BooleanObjectPair.of(true, newCurrent);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static List<CommonAsset> getCommonAssetsStartingWith(String pack, String name) {
/* 128 */     ObjectArrayList<CommonAsset> objectArrayList = new ObjectArrayList();
/* 129 */     for (List<PackAsset> assets : assetByNameMap.values()) {
/* 130 */       for (PackAsset asset : assets) {
/* 131 */         if (asset.asset().getName().startsWith(name) && asset.pack().equals(pack)) {
/* 132 */           objectArrayList.add(asset.asset());
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return (List<CommonAsset>)objectArrayList;
/*     */   }
/*     */   
/*     */   public static boolean hasCommonAsset(String name) {
/* 140 */     return assetByNameMap.containsKey(name);
/*     */   }
/*     */   
/*     */   public static boolean hasCommonAsset(AssetPack pack, String name) {
/* 144 */     List<PackAsset> packAssets = assetByNameMap.get(name);
/* 145 */     if (packAssets != null)
/* 146 */       for (PackAsset packAsset : packAssets) {
/* 147 */         if (packAsset.pack.equals(pack.getName())) return true;
/*     */       
/*     */       }  
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static CommonAsset getByName(String name) {
/* 155 */     name = PatternUtil.replaceBackslashWithForwardSlash(name);
/* 156 */     List<PackAsset> asset = assetByNameMap.get(name);
/* 157 */     return (asset == null) ? null : ((PackAsset)asset.getLast()).asset();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static CommonAsset getByHash(@Nonnull String hash) {
/* 162 */     List<PackAsset> assets = assetByHashMap.get(hash.toLowerCase());
/* 163 */     return (assets != null && !assets.isEmpty()) ? ((PackAsset)assets.getFirst()).asset() : null;
/*     */   }
/*     */   
/*     */   private static void removeCommonAssetByHash0(@Nonnull PackAsset oldAsset) {
/* 167 */     List<PackAsset> commonAssets = assetByHashMap.get(oldAsset.asset().getHash());
/* 168 */     if (commonAssets != null && commonAssets.remove(oldAsset) && commonAssets.isEmpty()) {
/* 169 */       assetByHashMap.compute(oldAsset.asset().getHash(), (key, assets) -> 
/* 170 */           (assets == null || assets.isEmpty()) ? null : assets);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AddCommonAssetResult
/*     */   {
/*     */     private CommonAssetRegistry.PackAsset newPackAsset;
/*     */     private CommonAssetRegistry.PackAsset previousNameAsset;
/*     */     private CommonAssetRegistry.PackAsset activeAsset;
/*     */     private CommonAssetRegistry.PackAsset[] previousHashAssets;
/*     */     private int duplicateAssetId;
/*     */     
/*     */     public CommonAssetRegistry.PackAsset getNewPackAsset() {
/* 184 */       return this.newPackAsset;
/*     */     }
/*     */     
/*     */     public CommonAssetRegistry.PackAsset getPreviousNameAsset() {
/* 188 */       return this.previousNameAsset;
/*     */     }
/*     */     
/*     */     public CommonAssetRegistry.PackAsset getActiveAsset() {
/* 192 */       return this.activeAsset;
/*     */     }
/*     */     
/*     */     public CommonAssetRegistry.PackAsset[] getPreviousHashAssets() {
/* 196 */       return this.previousHashAssets;
/*     */     }
/*     */     
/*     */     public int getDuplicateAssetId() {
/* 200 */       return this.duplicateAssetId;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 206 */       return "AddCommonAssetResult{previousNameAsset=" + String.valueOf(this.previousNameAsset) + ", previousHashAssets=" + 
/*     */         
/* 208 */         Arrays.toString((Object[])this.previousHashAssets) + ", duplicateAssetId=" + this.duplicateAssetId + "}";
/*     */     }
/*     */   } public static final class PackAsset extends Record { private final String pack; private final CommonAsset asset;
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/asset/common/CommonAssetRegistry$PackAsset;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #214	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/asset/common/CommonAssetRegistry$PackAsset;
/*     */     }
/* 214 */     public PackAsset(String pack, CommonAsset asset) { this.pack = pack; this.asset = asset; } public String pack() { return this.pack; } public CommonAsset asset() { return this.asset; }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 218 */       if (this == o) return true; 
/* 219 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 221 */       PackAsset packAsset = (PackAsset)o;
/*     */       
/* 223 */       if (!this.pack.equals(packAsset.pack)) return false; 
/* 224 */       return this.asset.equals(packAsset.asset);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 230 */       return "PackAsset{pack='" + this.pack + "', asset=" + String.valueOf(this.asset) + "}";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\common\CommonAssetRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */