/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.FieldFunctionAssignments;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldFunctionAssignmentsAsset
/*     */   extends AssignmentsAsset
/*     */ {
/*     */   public static final BuilderCodec<FieldFunctionAssignmentsAsset> CODEC;
/*     */   
/*     */   static {
/*  33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FieldFunctionAssignmentsAsset.class, FieldFunctionAssignmentsAsset::new, AssignmentsAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (asset, v) -> asset.delimiterAssets = v, asset -> asset.delimiterAssets).add()).append(new KeyedCodec("FieldFunction", (Codec)DensityAsset.CODEC, true), (asset, v) -> asset.densityAsset = v, asset -> asset.densityAsset).add()).build();
/*     */   }
/*  35 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*  36 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Assignments build(@Nonnull AssignmentsAsset.Argument argument) {
/*  41 */     if (skip()) {
/*  42 */       return Assignments.noPropDistribution(argument.runtime);
/*     */     }
/*     */     
/*  45 */     Density functionTree = this.densityAsset.build(DensityAsset.from(argument));
/*     */     
/*  47 */     ArrayList<FieldFunctionAssignments.FieldDelimiter> delimiterList = new ArrayList<>();
/*  48 */     for (DelimiterAsset asset : this.delimiterAssets) {
/*  49 */       Assignments propDistribution = asset.assignmentsAsset.build(argument);
/*  50 */       FieldFunctionAssignments.FieldDelimiter delimiter = new FieldFunctionAssignments.FieldDelimiter(propDistribution, asset.min, asset.max);
/*  51 */       delimiterList.add(delimiter);
/*     */     } 
/*     */     
/*  54 */     return (Assignments)new FieldFunctionAssignments(functionTree, delimiterList, argument.runtime);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  59 */     this.densityAsset.cleanUp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DelimiterAsset
/*     */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, DelimiterAsset>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
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
/*     */     private double min;
/*     */ 
/*     */     
/*     */     private double max;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  87 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Assignments", (Codec)AssignmentsAsset.CODEC, true), (t, v) -> t.assignmentsAsset = v, t -> t.assignmentsAsset).add()).append(new KeyedCodec("Min", (Codec)Codec.DOUBLE, true), (t, v) -> t.min = v.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("Max", (Codec)Codec.DOUBLE, true), (t, v) -> t.max = v.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     private AssignmentsAsset assignmentsAsset = new ConstantAssignmentsAsset();
/*     */ 
/*     */     
/*     */     public String getId() {
/*  98 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanUp() {
/* 103 */       this.assignmentsAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\FieldFunctionAssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */