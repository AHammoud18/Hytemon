/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters.RangeDoubleAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.DelimiterDouble;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.DensityDelimitedEnvironmentProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class DensityDelimitedEnvironmentProviderAsset
/*     */   extends EnvironmentProviderAsset
/*     */ {
/*     */   public static final BuilderCodec<DensityDelimitedEnvironmentProviderAsset> CODEC;
/*     */   
/*     */   static {
/*  37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityDelimitedEnvironmentProviderAsset.class, DensityDelimitedEnvironmentProviderAsset::new, EnvironmentProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, k -> k.delimiterAssets).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, value) -> t.densityAsset = value, t -> t.densityAsset).add()).build();
/*     */   }
/*  39 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*  40 */   private DensityAsset densityAsset = DensityAsset.getFallbackAsset();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EnvironmentProvider build(@Nonnull EnvironmentProviderAsset.Argument argument) {
/*  45 */     if (isSkipped()) return EnvironmentProvider.noEnvironmentProvider();
/*     */     
/*  47 */     List<DelimiterDouble<EnvironmentProvider>> delimiters = new ArrayList<>(this.delimiterAssets.length);
/*  48 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/*  49 */       delimiters.add(delimiterAsset.build(argument));
/*     */     }
/*     */     
/*  52 */     Density density = this.densityAsset.build(DensityAsset.from(argument));
/*     */     
/*  54 */     return (EnvironmentProvider)new DensityDelimitedEnvironmentProvider(delimiters, density);
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
/*     */ 
/*     */     
/*     */     static {
/*  77 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Range", (Codec)RangeDoubleAsset.CODEC, true), (t, value) -> t.rangeAsset = value, t -> t.rangeAsset).add()).append(new KeyedCodec("Environment", (Codec)EnvironmentProviderAsset.CODEC, true), (t, value) -> t.environmentProviderAsset = value, t -> t.environmentProviderAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  82 */     private RangeDoubleAsset rangeAsset = new RangeDoubleAsset();
/*  83 */     private EnvironmentProviderAsset environmentProviderAsset = EnvironmentProviderAsset.getFallbackAsset();
/*     */     
/*     */     @Nonnull
/*     */     public DelimiterDouble<EnvironmentProvider> build(@Nonnull EnvironmentProviderAsset.Argument argument) {
/*  87 */       RangeDouble range = this.rangeAsset.build();
/*  88 */       EnvironmentProvider environmentProvider = this.environmentProviderAsset.build(argument);
/*  89 */       return new DelimiterDouble(range, environmentProvider);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getId() {
/*  94 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanUp() {
/*  99 */       this.environmentProviderAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 105 */     this.densityAsset.cleanUp();
/* 106 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets)
/* 107 */       delimiterAsset.cleanUp(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\environmentproviders\DensityDelimitedEnvironmentProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */