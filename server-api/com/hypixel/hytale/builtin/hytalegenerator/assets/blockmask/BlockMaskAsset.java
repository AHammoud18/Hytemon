/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockset.MaterialSetAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockMaskAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockMaskAsset>>, Cleanable {
/*  22 */   private static final Map<String, Exported> exportedNodes = new HashMap<>();
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
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, BlockMaskAsset> CODEC;
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
/*     */   
/*     */   private String id;
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
/*     */   
/*     */   private AssetExtraInfo.Data data;
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
/*     */ 
/*     */   
/*     */   static {
/*  73 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockMaskAsset.class, BlockMaskAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("DontPlace", (Codec)MaterialSetAsset.CODEC, false), (t, k) -> t.dontPlaceMaterialSetAsset = k, t -> t.dontPlaceMaterialSetAsset).add()).append(new KeyedCodec("DontReplace", (Codec)MaterialSetAsset.CODEC, false), (t, k) -> t.dontReplaceMaterialSetAsset = k, t -> t.dontReplaceMaterialSetAsset).add()).append(new KeyedCodec("Advanced", (Codec)new ArrayCodec((Codec)BlockMaskEntryAsset.CODEC, x$0 -> new BlockMaskEntryAsset[x$0]), false), (t, k) -> t.blockMaskEntries = k, t -> t.blockMaskEntries).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).append(new KeyedCodec("Import", (Codec)Codec.STRING, false), (t, k) -> t.importName = k, t -> t.importName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  Exported exported = new Exported(); exported.asset = asset; exportedNodes.put(asset.exportName, exported); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Exported
/*     */   {
/*     */     public BlockMaskAsset asset;
/*     */   }
/*     */   
/*  82 */   protected String exportName = "";
/*  83 */   protected String importName = "";
/*  84 */   private MaterialSetAsset dontPlaceMaterialSetAsset = new MaterialSetAsset();
/*  85 */   private MaterialSetAsset dontReplaceMaterialSetAsset = new MaterialSetAsset();
/*  86 */   private BlockMaskEntryAsset[] blockMaskEntries = new BlockMaskEntryAsset[0];
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMask build(@Nonnull MaterialCache materialCache) {
/*  91 */     if (this.importName != null && !this.importName.isEmpty()) {
/*  92 */       Exported importedAssetEntry = exportedNodes.get(this.importName);
/*  93 */       if (importedAssetEntry == null || importedAssetEntry.asset == null) {
/*  94 */         LoggerUtil.getLogger().warning("Imported BlockMask asset with name '" + this.importName + "' not found");
/*  95 */         return new BlockMask();
/*     */       } 
/*     */       
/*  98 */       return importedAssetEntry.asset.build(materialCache);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 103 */     MaterialSet dontPlaceBlockSet = (this.dontPlaceMaterialSetAsset == null) ? new MaterialSet() : this.dontPlaceMaterialSetAsset.build(materialCache);
/*     */ 
/*     */     
/* 106 */     MaterialSet dontReplaceBlockSet = (this.dontReplaceMaterialSetAsset == null) ? new MaterialSet() : this.dontReplaceMaterialSetAsset.build(materialCache);
/*     */     
/* 108 */     BlockMask blockMask = new BlockMask();
/* 109 */     blockMask.setSkippedBlocks(dontPlaceBlockSet);
/* 110 */     blockMask.setDefaultMask(dontReplaceBlockSet);
/* 111 */     for (BlockMaskEntryAsset entry : this.blockMaskEntries) {
/* 112 */       blockMask.putBlockMaskEntry(entry.getPropBlockSet(materialCache), entry.getReplacesBlockSet(materialCache));
/*     */     }
/* 114 */     return blockMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 119 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 124 */     this.dontPlaceMaterialSetAsset.cleanUp();
/* 125 */     this.dontReplaceMaterialSetAsset.cleanUp();
/* 126 */     for (BlockMaskEntryAsset blockMaskEntryAsset : this.blockMaskEntries)
/* 127 */       blockMaskEntryAsset.cleanUp(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\blockmask\BlockMaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */