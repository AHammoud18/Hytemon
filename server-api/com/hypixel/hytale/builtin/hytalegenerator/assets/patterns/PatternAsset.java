/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality.DirectionalityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class PatternAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, PatternAsset>> {
/*     */   public static final AssetCodecMapCodec<String, PatternAsset> CODEC;
/*     */   
/*     */   static {
/*  28 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final Map<String, PatternAsset> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  37 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(PatternAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<PatternAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/*  38 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  62 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(PatternAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean skip = false;
/*     */   
/*  68 */   private String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSkipped() {
/*  75 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static PatternAsset getExportedAsset(@Nonnull String name) {
/*  79 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  84 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Argument argumentFrom(@Nonnull DirectionalityAsset.Argument argument) {
/*  93 */     return new Argument(argument.parentSeed, argument.materialCache, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument argumentFrom(@Nonnull PropAsset.Argument argument) {
/*  98 */     return new Argument(argument.parentSeed, argument.materialCache, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Pattern build(@Nonnull Argument paramArgument);
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     public MaterialCache materialCache;
/*     */     public ReferenceBundle referenceBundle;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 112 */       this.parentSeed = parentSeed;
/* 113 */       this.materialCache = materialCache;
/* 114 */       this.referenceBundle = referenceBundle;
/* 115 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 119 */       this.parentSeed = argument.parentSeed;
/* 120 */       this.materialCache = argument.materialCache;
/* 121 */       this.referenceBundle = argument.referenceBundle;
/* 122 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\PatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */