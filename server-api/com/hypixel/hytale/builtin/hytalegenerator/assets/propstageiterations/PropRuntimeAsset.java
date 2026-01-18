/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propstageiterations;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ListPositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments.AssignmentsAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments.ConstantAssignmentsAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropRuntimeAsset
/*    */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, PropRuntimeAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, PropRuntimeAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 54 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(PropRuntimeAsset.class, PropRuntimeAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Runtime", (Codec)Codec.INTEGER, true), (t, k) -> t.runtime = k.intValue(), t -> Integer.valueOf(t.runtime)).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (t, k) -> t.positionProviderAsset = k, t -> t.positionProviderAsset).add()).append(new KeyedCodec("Assignments", (Codec)AssignmentsAsset.CODEC, true), (t, k) -> t.assignmentsAsset = k, t -> t.assignmentsAsset).add()).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean skip = false;
/*    */   
/* 60 */   private int runtime = 0;
/* 61 */   private PositionProviderAsset positionProviderAsset = (PositionProviderAsset)new ListPositionProviderAsset();
/* 62 */   private AssignmentsAsset assignmentsAsset = (AssignmentsAsset)new ConstantAssignmentsAsset();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSkip() {
/* 67 */     return this.skip;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 72 */     this.positionProviderAsset.cleanUp();
/* 73 */     this.assignmentsAsset.cleanUp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PositionProvider buildPositionProvider(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 79 */     return this.positionProviderAsset.build(new PositionProviderAsset.Argument(parentSeed, referenceBundle, workerIndexer));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Assignments buildPropDistribution(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, int runtime, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 87 */     return this.assignmentsAsset.build(new AssignmentsAsset.Argument(parentSeed, materialCache, referenceBundle, runtime, workerIndexer));
/*    */   }
/*    */   
/*    */   public int getRuntime() {
/* 91 */     return this.runtime;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 96 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propstageiterations\PropRuntimeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */