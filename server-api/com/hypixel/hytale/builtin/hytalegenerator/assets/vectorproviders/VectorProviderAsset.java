/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class VectorProviderAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, VectorProviderAsset>> {
/*     */   public static final AssetCodecMapCodec<String, VectorProviderAsset> CODEC;
/*     */   
/*     */   static {
/*  26 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Exported
/*     */   {
/*     */     public boolean singleInstance;
/*     */     
/*     */     public VectorProviderAsset asset;
/*     */     
/*     */     public VectorProvider builtInstance;
/*     */   }
/*     */   
/*  40 */   private static final Map<String, Exported> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  42 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(VectorProviderAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<VectorProviderAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/*  43 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  76 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(VectorProviderAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  Exported exported = new Exported(); exported.asset = asset; if (asset instanceof ExportedVectorProviderAsset) { ExportedVectorProviderAsset exportedAsset = (ExportedVectorProviderAsset)asset; exported.singleInstance = exportedAsset.isSingleInstance(); } else { exported.singleInstance = false; }  exportedNodes.put(asset.exportName, exported); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean skip = false;
/*     */   
/*  82 */   protected String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSkipped() {
/*  89 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static Exported getExportedAsset(@Nonnull String name) {
/*  93 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  98 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     
/*     */     public ReferenceBundle referenceBundle;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 110 */       this.parentSeed = parentSeed;
/* 111 */       this.referenceBundle = referenceBundle;
/* 112 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 116 */       this.parentSeed = argument.parentSeed;
/* 117 */       this.referenceBundle = argument.referenceBundle;
/* 118 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull DensityAsset.Argument argument) {
/* 122 */       this.parentSeed = argument.parentSeed;
/* 123 */       this.referenceBundle = argument.referenceBundle;
/* 124 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanUp() {}
/*     */   
/*     */   public abstract VectorProvider build(@Nonnull Argument paramArgument);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\VectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */