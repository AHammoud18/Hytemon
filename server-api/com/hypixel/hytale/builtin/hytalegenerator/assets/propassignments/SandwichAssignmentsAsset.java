/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.SandwichAssignments;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SandwichAssignmentsAsset
/*    */   extends AssignmentsAsset
/*    */ {
/*    */   public static final BuilderCodec<SandwichAssignmentsAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SandwichAssignmentsAsset.class, SandwichAssignmentsAsset::new, AssignmentsAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (asset, v) -> asset.delimiterAssets = v, asset -> asset.delimiterAssets).add()).build();
/*    */   }
/* 28 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Assignments build(@Nonnull AssignmentsAsset.Argument argument) {
/* 33 */     if (skip()) {
/* 34 */       return Assignments.noPropDistribution(argument.runtime);
/*    */     }
/*    */     
/* 37 */     ArrayList<SandwichAssignments.VerticalDelimiter> delimiterList = new ArrayList<>();
/* 38 */     for (DelimiterAsset asset : this.delimiterAssets) {
/* 39 */       Assignments propDistribution = asset.assignmentsAsset.build(argument);
/* 40 */       SandwichAssignments.VerticalDelimiter delimiter = new SandwichAssignments.VerticalDelimiter(propDistribution, asset.min, asset.max);
/* 41 */       delimiterList.add(delimiter);
/*    */     } 
/*    */     
/* 44 */     return (Assignments)new SandwichAssignments(delimiterList, argument.runtime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 49 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/* 50 */       delimiterAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class DelimiterAsset
/*    */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, DelimiterAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
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
/*    */     private double min;
/*    */ 
/*    */     
/*    */     private double max;
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 79 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Assignments", (Codec)AssignmentsAsset.CODEC, true), (t, v) -> t.assignmentsAsset = v, t -> t.assignmentsAsset).add()).append(new KeyedCodec("MinY", (Codec)Codec.DOUBLE, true), (t, v) -> t.min = v.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("MaxY", (Codec)Codec.DOUBLE, true), (t, v) -> t.max = v.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     private AssignmentsAsset assignmentsAsset = new ConstantAssignmentsAsset();
/*    */ 
/*    */     
/*    */     public String getId() {
/* 90 */       return this.id;
/*    */     }
/*    */ 
/*    */     
/*    */     public void cleanUp() {
/* 95 */       this.assignmentsAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\SandwichAssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */