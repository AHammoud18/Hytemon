/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.FieldFunctionMaterialProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
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
/*     */ public class FieldFunctionMaterialProviderAsset
/*     */   extends MaterialProviderAsset
/*     */ {
/*     */   public static final BuilderCodec<FieldFunctionMaterialProviderAsset> CODEC;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FieldFunctionMaterialProviderAsset.class, FieldFunctionMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("FieldFunction", (Codec)DensityAsset.CODEC, true), (t, k) -> t.densityAsset = k, t -> t.densityAsset).add()).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, k -> k.delimiterAssets).add()).build();
/*     */   }
/*  36 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*  37 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/*  42 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*     */     
/*  44 */     Density functionTree = this.densityAsset.build(DensityAsset.from(argument));
/*     */     
/*  46 */     ArrayList<FieldFunctionMaterialProvider.FieldDelimiter<Material>> delimitersList = new ArrayList<>(this.delimiterAssets.length);
/*  47 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/*  48 */       MaterialProvider<Material> materialProvider = delimiterAsset.materialProviderAsset.build(argument);
/*  49 */       FieldFunctionMaterialProvider.FieldDelimiter<Material> delimiter = new FieldFunctionMaterialProvider.FieldDelimiter(materialProvider, delimiterAsset.from, delimiterAsset.to);
/*  50 */       delimitersList.add(delimiter);
/*     */     } 
/*     */     
/*  53 */     return (MaterialProvider<Material>)new FieldFunctionMaterialProvider(functionTree, delimitersList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/*  58 */     this.densityAsset.cleanUp();
/*  59 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/*  60 */       delimiterAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
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
/*     */     
/*     */     private String id;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  89 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("From", (Codec)Codec.DOUBLE, true), (t, y) -> t.from = y.doubleValue(), t -> Double.valueOf(t.from)).add()).append(new KeyedCodec("To", (Codec)Codec.DOUBLE, true), (t, out) -> t.to = out.doubleValue(), t -> Double.valueOf(t.to)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, out) -> t.materialProviderAsset = out, t -> t.materialProviderAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  94 */     private double from = 0.0D;
/*  95 */     private double to = 0.0D;
/*  96 */     private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/*     */ 
/*     */     
/*     */     public String getId() {
/* 100 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanUp() {
/* 105 */       this.materialProviderAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\FieldFunctionMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */