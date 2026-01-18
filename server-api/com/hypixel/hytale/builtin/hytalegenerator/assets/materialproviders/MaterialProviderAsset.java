/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
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
/*     */ public abstract class MaterialProviderAsset
/*     */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, MaterialProviderAsset>> {
/*  29 */   private static final MaterialProviderAsset[] EMPTY_INPUTS = new MaterialProviderAsset[0]; public static final AssetCodecMapCodec<String, MaterialProviderAsset> CODEC;
/*     */   static {
/*  31 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final Map<String, MaterialProviderAsset> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  40 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(MaterialProviderAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<MaterialProviderAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/*  41 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  65 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(MaterialProviderAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean skip = false;
/*     */   
/*  71 */   private String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean skip() {
/*  78 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static MaterialProviderAsset getExportedAsset(@Nonnull String name) {
/*  82 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  87 */     return this.id;
/*     */   }
/*     */   
/*     */   public static Argument argumentFrom(@Nonnull DensityAsset.Argument argument, @Nonnull MaterialCache materialCache) {
/*  91 */     return new Argument(argument.parentSeed, materialCache, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   public static Argument argumentFrom(@Nonnull PropAsset.Argument argument) {
/*  95 */     return new Argument(argument.parentSeed, argument.materialCache, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     
/*     */     public MaterialCache materialCache;
/*     */     
/*     */     public ReferenceBundle referenceBundle;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 109 */       this.parentSeed = parentSeed;
/* 110 */       this.materialCache = materialCache;
/* 111 */       this.referenceBundle = referenceBundle;
/* 112 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 116 */       this.parentSeed = argument.parentSeed;
/* 117 */       this.materialCache = argument.materialCache;
/* 118 */       this.referenceBundle = argument.referenceBundle;
/* 119 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanUp() {}
/*     */   
/*     */   public abstract MaterialProvider<Material> build(@Nonnull Argument paramArgument);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\MaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */