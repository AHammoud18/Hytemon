/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.tintproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters.RangeDoubleAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.DelimiterDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.DensityDelimitedTintProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class DensityDelimitedTintProviderAsset
/*    */   extends TintProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<DensityDelimitedTintProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityDelimitedTintProviderAsset.class, DensityDelimitedTintProviderAsset::new, TintProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, k -> k.delimiterAssets).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, value) -> t.densityAsset = value, t -> t.densityAsset).add()).build();
/*    */   }
/* 38 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/* 39 */   private DensityAsset densityAsset = DensityAsset.getFallbackAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public TintProvider build(@Nonnull TintProviderAsset.Argument argument) {
/* 44 */     if (isSkipped()) return TintProvider.noTintProvider();
/*    */     
/* 46 */     List<DelimiterDouble<TintProvider>> delimiters = new ArrayList<>(this.delimiterAssets.length);
/* 47 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/* 48 */       delimiters.add(delimiterAsset.build(argument));
/*    */     }
/*    */     
/* 51 */     Density density = this.densityAsset.build(DensityAsset.from(argument));
/*    */     
/* 53 */     return (TintProvider)new DensityDelimitedTintProvider(delimiters, density);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class DelimiterAsset
/*    */     implements JsonAssetWithMap<String, DefaultAssetMap<String, DelimiterAsset>>
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
/*    */ 
/*    */     
/*    */     static {
/* 76 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Range", (Codec)RangeDoubleAsset.CODEC, true), (t, value) -> t.rangeAsset = value, t -> t.rangeAsset).add()).append(new KeyedCodec("Tint", (Codec)TintProviderAsset.CODEC, true), (t, value) -> t.tintProviderAsset = value, t -> t.tintProviderAsset).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 81 */     private RangeDoubleAsset rangeAsset = new RangeDoubleAsset();
/* 82 */     private TintProviderAsset tintProviderAsset = TintProviderAsset.getFallbackAsset();
/*    */     
/*    */     @Nonnull
/*    */     public DelimiterDouble<TintProvider> build(@Nonnull TintProviderAsset.Argument argument) {
/* 86 */       RangeDouble range = this.rangeAsset.build();
/* 87 */       TintProvider environmentProvider = this.tintProviderAsset.build(argument);
/* 88 */       return new DelimiterDouble(range, environmentProvider);
/*    */     }
/*    */ 
/*    */     
/*    */     public String getId() {
/* 93 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\tintproviders\DensityDelimitedTintProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */