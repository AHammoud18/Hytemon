/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.WeightedMaterialProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class WeightedMaterialProviderAsset
/*     */   extends MaterialProviderAsset
/*     */ {
/*     */   public static final BuilderCodec<WeightedMaterialProviderAsset> CODEC;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeightedMaterialProviderAsset.class, WeightedMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("WeightedMaterials", (Codec)new ArrayCodec((Codec)WeightedMaterialAsset.CODEC, x$0 -> new WeightedMaterialAsset[x$0]), true), (t, k) -> t.weighedMapEntries = k, k -> k.weighedMapEntries).add()).append(new KeyedCodec("SkipChance", (Codec)Codec.DOUBLE, true), (t, k) -> t.skipChance = k.doubleValue(), k -> Double.valueOf(k.skipChance)).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (t, k) -> t.seed = k, k -> k.seed).add()).build();
/*     */   }
/*  40 */   private WeightedMaterialAsset[] weighedMapEntries = new WeightedMaterialAsset[0];
/*  41 */   private double skipChance = 0.0D;
/*  42 */   private String seed = "";
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/*  47 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*     */     
/*  49 */     WeightedMap<MaterialProvider<Material>> weightMap = new WeightedMap();
/*  50 */     for (WeightedMaterialAsset entry : this.weighedMapEntries) {
/*  51 */       weightMap.add(entry.materialProviderAsset.build(argument), entry.weight);
/*     */     }
/*  53 */     return (MaterialProvider<Material>)new WeightedMaterialProvider(weightMap, argument.parentSeed.child(this.seed), this.skipChance);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  58 */     for (WeightedMaterialAsset weightedMaterialAsset : this.weighedMapEntries) {
/*  59 */       weightedMaterialAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WeightedMaterialAsset
/*     */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, WeightedMaterialAsset>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, WeightedMaterialAsset> CODEC;
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
/*  85 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedMaterialAsset.class, WeightedMaterialAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, y) -> t.weight = y.doubleValue(), t -> Double.valueOf(t.weight)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, out) -> t.materialProviderAsset = out, t -> t.materialProviderAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     private double weight = 1.0D;
/*     */     
/*     */     private MaterialProviderAsset materialProviderAsset;
/*     */     
/*     */     public String getId() {
/*  95 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanUp() {
/* 100 */       this.materialProviderAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\WeightedMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */