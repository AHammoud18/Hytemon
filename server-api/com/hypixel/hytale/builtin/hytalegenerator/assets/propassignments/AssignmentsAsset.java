/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
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
/*     */ public abstract class AssignmentsAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, AssignmentsAsset>> {
/*     */   public static final AssetCodecMapCodec<String, AssignmentsAsset> CODEC;
/*     */   
/*     */   static {
/*  26 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final Map<String, AssignmentsAsset> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  35 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(AssignmentsAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<AssignmentsAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
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
/*  60 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(AssignmentsAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported position provider asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean skip = false;
/*     */   
/*  66 */   private String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean skip() {
/*  73 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static AssignmentsAsset getExportedAsset(@Nonnull String name) {
/*  77 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  82 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {}
/*     */ 
/*     */   
/*     */   public abstract Assignments build(@Nonnull Argument paramArgument);
/*     */ 
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     
/*     */     public MaterialCache materialCache;
/*     */     public ReferenceBundle referenceBundle;
/*     */     public int runtime;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, int runtime, @Nonnull WorkerIndexer workerIndexer) {
/* 102 */       this.parentSeed = parentSeed;
/* 103 */       this.materialCache = materialCache;
/* 104 */       this.referenceBundle = referenceBundle;
/* 105 */       this.runtime = runtime;
/* 106 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 110 */       this.parentSeed = argument.parentSeed;
/* 111 */       this.materialCache = argument.materialCache;
/* 112 */       this.referenceBundle = argument.referenceBundle;
/* 113 */       this.runtime = argument.runtime;
/* 114 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\AssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */