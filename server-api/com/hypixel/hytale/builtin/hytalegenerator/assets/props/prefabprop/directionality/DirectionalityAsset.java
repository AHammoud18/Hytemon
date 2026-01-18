/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class DirectionalityAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, DirectionalityAsset>> {
/*     */   public static final AssetCodecMapCodec<String, DirectionalityAsset> CODEC;
/*     */   
/*     */   static {
/*  26 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final Map<String, DirectionalityAsset> exportedNodes = new HashMap<>();
/*     */   
/*  35 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(DirectionalityAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<DirectionalityAsset> ABSTRACT_CODEC; static {
/*  36 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  50 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(DirectionalityAsset.class).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported position provider asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */   
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*  55 */   private String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DirectionalityAsset getExportedAsset(@Nonnull String name) {
/*  67 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  72 */     return this.id;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument argumentFrom(@Nonnull PropAsset.Argument argument) {
/*  77 */     return new Argument(argument.parentSeed, argument.materialCache, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Directionality build(@Nonnull Argument paramArgument);
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     public MaterialCache materialCache;
/*     */     public ReferenceBundle referenceBundle;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/*  91 */       this.parentSeed = parentSeed;
/*  92 */       this.materialCache = materialCache;
/*  93 */       this.referenceBundle = referenceBundle;
/*  94 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/*  98 */       this.parentSeed = argument.parentSeed;
/*  99 */       this.materialCache = argument.materialCache;
/* 100 */       this.referenceBundle = argument.referenceBundle;
/* 101 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\directionality\DirectionalityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */