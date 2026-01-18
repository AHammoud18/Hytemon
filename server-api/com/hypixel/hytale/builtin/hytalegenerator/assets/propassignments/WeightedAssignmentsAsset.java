/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.WeightedAssignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
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
/*    */ public class WeightedAssignmentsAsset
/*    */   extends AssignmentsAsset
/*    */ {
/*    */   public static final BuilderCodec<WeightedAssignmentsAsset> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeightedAssignmentsAsset.class, WeightedAssignmentsAsset::new, AssignmentsAsset.ABSTRACT_CODEC).append(new KeyedCodec("SkipChance", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.skipChance = v.doubleValue(), asset -> Double.valueOf(asset.skipChance)).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, v) -> asset.seed = v, asset -> asset.seed).add()).append(new KeyedCodec("WeightedAssignments", (Codec)new ArrayCodec((Codec)WeightedAssets.CODEC, x$0 -> new WeightedAssets[x$0]), true), (asset, v) -> asset.weightedAssets = v, asset -> asset.weightedAssets).add()).build();
/*    */   }
/* 38 */   private WeightedAssets[] weightedAssets = new WeightedAssets[0];
/* 39 */   private String seed = "";
/* 40 */   private double skipChance = 0.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Assignments build(@Nonnull AssignmentsAsset.Argument argument) {
/* 45 */     if (skip()) {
/* 46 */       return Assignments.noPropDistribution(argument.runtime);
/*    */     }
/*    */     
/* 49 */     WeightedMap<Assignments> weightMap = new WeightedMap();
/* 50 */     for (WeightedAssets asset : this.weightedAssets) {
/* 51 */       weightMap.add(asset.assignmentsAsset.build(argument), asset.weight);
/*    */     }
/* 53 */     SeedBox childSeed = argument.parentSeed.child(this.seed);
/* 54 */     return (Assignments)new WeightedAssignments(weightMap, ((Integer)childSeed.createSupplier().get()).intValue(), this.skipChance, argument.runtime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 59 */     for (WeightedAssets weightedAsset : this.weightedAssets) {
/* 60 */       weightedAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class WeightedAssets
/*    */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, WeightedAssets>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, WeightedAssets> CODEC;
/*    */ 
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 84 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedAssets.class, WeightedAssets::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, v) -> t.weight = v.doubleValue(), t -> Double.valueOf(t.weight)).add()).append(new KeyedCodec("Assignments", (Codec)AssignmentsAsset.CODEC, true), (t, v) -> t.assignmentsAsset = v, t -> t.assignmentsAsset).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 89 */     private double weight = 1.0D;
/* 90 */     private AssignmentsAsset assignmentsAsset = new ConstantAssignmentsAsset();
/*    */ 
/*    */     
/*    */     public String getId() {
/* 94 */       return this.id;
/*    */     }
/*    */ 
/*    */     
/*    */     public void cleanUp() {
/* 99 */       this.assignmentsAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\WeightedAssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */