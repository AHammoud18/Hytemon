/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask.BlockMaskAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.DirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.StaticDirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.OriginScannerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.prefab.MoldingDirection;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.prefab.PrefabMoldingConfiguration;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.prefab.PrefabProp;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
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
/*     */ public class PrefabPropAsset
/*     */   extends PropAsset
/*     */ {
/*     */   public static final BuilderCodec<PrefabPropAsset> CODEC;
/*     */   
/*     */   static {
/*  92 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabPropAsset.class, PrefabPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("WeightedPrefabPaths", (Codec)new ArrayCodec((Codec)WeightedPathAsset.CODEC, x$0 -> new WeightedPathAsset[x$0]), true), (asset, v) -> asset.weightedPrefabPathAssets = v, asset -> asset.weightedPrefabPathAssets).add()).append(new KeyedCodec("LegacyPath", (Codec)Codec.BOOLEAN, false), (asset, v) -> asset.legacyPath = v.booleanValue(), asset -> Boolean.valueOf(asset.legacyPath)).add()).append(new KeyedCodec("Directionality", (Codec)DirectionalityAsset.CODEC, true), (asset, v) -> asset.directionalityAsset = v, asset -> asset.directionalityAsset).add()).append(new KeyedCodec("Scanner", (Codec)ScannerAsset.CODEC, true), (asset, v) -> asset.scannerAsset = v, asset -> asset.scannerAsset).add()).append(new KeyedCodec("BlockMask", (Codec)BlockMaskAsset.CODEC, false), (asset, v) -> asset.blockMaskAsset = v, asset -> asset.blockMaskAsset).add()).append(new KeyedCodec("MoldingDirection", MoldingDirection.CODEC, false), (t, k) -> t.moldingDirectionName = k, k -> k.moldingDirectionName).add()).append(new KeyedCodec("MoldingPattern", (Codec)PatternAsset.CODEC, false), (asset, v) -> asset.moldingPatternAsset = v, asset -> asset.moldingPatternAsset).add()).append(new KeyedCodec("MoldingScanner", (Codec)ScannerAsset.CODEC, false), (asset, v) -> asset.moldingScannerAsset = v, asset -> asset.moldingScannerAsset).add()).append(new KeyedCodec("MoldingChildren", (Codec)Codec.BOOLEAN, false), (asset, v) -> asset.moldChildren = v.booleanValue(), asset -> Boolean.valueOf(asset.moldChildren)).add()).append(new KeyedCodec("LoadEntities", (Codec)Codec.BOOLEAN, false), (asset, v) -> asset.loadEntities = v.booleanValue(), asset -> Boolean.valueOf(asset.loadEntities)).add()).build();
/*     */   }
/*  94 */   private WeightedPathAsset[] weightedPrefabPathAssets = new WeightedPathAsset[0];
/*     */   private boolean legacyPath = false;
/*     */   private boolean loadEntities = true;
/*  97 */   private DirectionalityAsset directionalityAsset = (DirectionalityAsset)new StaticDirectionalityAsset();
/*  98 */   private ScannerAsset scannerAsset = (ScannerAsset)new OriginScannerAsset();
/*  99 */   private BlockMaskAsset blockMaskAsset = new BlockMaskAsset();
/*     */   
/* 101 */   private MoldingDirection moldingDirectionName = MoldingDirection.NONE;
/* 102 */   private ScannerAsset moldingScannerAsset = (ScannerAsset)new OriginScannerAsset();
/* 103 */   private PatternAsset moldingPatternAsset = (PatternAsset)new ConstantPatternAsset();
/*     */   
/*     */   private boolean moldChildren = false;
/*     */   
/*     */   public void cleanUp() {
/* 108 */     this.directionalityAsset.cleanUp();
/* 109 */     this.scannerAsset.cleanUp();
/* 110 */     this.blockMaskAsset.cleanUp();
/* 111 */     this.moldingScannerAsset.cleanUp();
/* 112 */     this.moldingPatternAsset.cleanUp();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Prop build(@Nonnull PropAsset.Argument argument) {
/*     */     BlockMask blockMask;
/* 118 */     if (skip() || this.weightedPrefabPathAssets.length == 0) {
/* 119 */       return Prop.noProp();
/*     */     }
/*     */ 
/*     */     
/* 123 */     WeightedMap<List<PrefabBuffer>> prefabWeightedMap = new WeightedMap();
/* 124 */     for (WeightedPathAsset pathAsset : this.weightedPrefabPathAssets) {
/* 125 */       List<PrefabBuffer> pathPrefabs = loadPrefabBuffersFrom(pathAsset.path);
/* 126 */       if (pathPrefabs != null) {
/* 127 */         prefabWeightedMap.add(pathPrefabs, pathAsset.weight);
/*     */       }
/*     */     } 
/* 130 */     if (prefabWeightedMap.size() == 0) return Prop.noProp();
/*     */     
/* 132 */     MaterialCache voxelCache = argument.materialCache;
/*     */ 
/*     */     
/* 135 */     if (this.blockMaskAsset == null) {
/* 136 */       blockMask = new BlockMask();
/*     */     } else {
/* 138 */       blockMask = this.blockMaskAsset.build(voxelCache);
/*     */     } 
/* 140 */     Scanner scanner = this.scannerAsset.build(ScannerAsset.argumentFrom(argument));
/* 141 */     Directionality directionality = this.directionalityAsset.build(DirectionalityAsset.argumentFrom(argument));
/*     */ 
/*     */     
/* 144 */     MoldingDirection moldingDirection = this.moldingDirectionName;
/* 145 */     PrefabMoldingConfiguration moldingConfiguration = null;
/* 146 */     if (moldingDirection == MoldingDirection.DOWN || moldingDirection == MoldingDirection.UP) {
/*     */ 
/*     */       
/* 149 */       Scanner moldingScanner = (this.moldingScannerAsset == null) ? Scanner.noScanner() : this.moldingScannerAsset.build(ScannerAsset.argumentFrom(argument));
/*     */ 
/*     */       
/* 152 */       Pattern moldingPattern = (this.moldingPatternAsset == null) ? Pattern.noPattern() : this.moldingPatternAsset.build(PatternAsset.argumentFrom(argument));
/*     */       
/* 154 */       moldingConfiguration = new PrefabMoldingConfiguration(moldingScanner, moldingPattern, moldingDirection, this.moldChildren);
/*     */     } else {
/*     */       
/* 157 */       moldingConfiguration = PrefabMoldingConfiguration.none();
/*     */     } 
/*     */     
/* 160 */     return (Prop)new PrefabProp(prefabWeightedMap, scanner, directionality, voxelCache, blockMask, moldingConfiguration, this::loadPrefabBuffersFrom, argument.parentSeed, this.loadEntities);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private List<PrefabBuffer> loadPrefabBuffersFrom(@Nonnull String path) {
/* 165 */     List<PrefabBuffer> pathPrefabs = new ArrayList<>();
/*     */     
/* 167 */     for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/*     */       
/* 169 */       Path fullPath = pack.getRoot().resolve("Server");
/* 170 */       if (this.legacyPath) {
/*     */ 
/*     */         
/* 173 */         fullPath = fullPath.resolve("World").resolve("Default").resolve("Prefabs");
/*     */       } else {
/* 175 */         fullPath = fullPath.resolve("Prefabs");
/*     */       } 
/* 177 */       fullPath = fullPath.resolve(path);
/*     */       
/*     */       try {
/* 180 */         PrefabLoader.loadAllPrefabBuffersUnder(fullPath, pathPrefabs);
/* 181 */       } catch (Exception e) {
/* 182 */         String msg = "Couldn't load prefab with path: " + path;
/* 183 */         msg = msg + "\n";
/* 184 */         msg = msg + msg;
/* 185 */         LoggerUtil.getLogger().severe(msg);
/*     */         
/* 187 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     if (pathPrefabs.isEmpty()) {
/* 192 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("This prefab path contains no prefabs: " + path);
/* 193 */       return null;
/*     */     } 
/*     */     
/* 196 */     return pathPrefabs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WeightedPathAsset
/*     */     implements JsonAssetWithMap<String, DefaultAssetMap<String, WeightedPathAsset>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, WeightedPathAsset> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String id;
/*     */ 
/*     */ 
/*     */     
/*     */     private AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 221 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedPathAsset.class, WeightedPathAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, y) -> t.weight = y.doubleValue(), t -> Double.valueOf(t.weight)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Path", (Codec)Codec.STRING, true), (t, out) -> t.path = out, t -> t.path).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 226 */     private double weight = 1.0D;
/*     */     
/*     */     private String path;
/*     */     
/*     */     public String getId() {
/* 231 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\PrefabPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */