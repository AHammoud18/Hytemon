/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.tintproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters.RangeDoubleAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.DelimiterDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DelimiterAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, DensityDelimitedTintProviderAsset.DelimiterAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 76 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Range", (Codec)RangeDoubleAsset.CODEC, true), (t, value) -> t.rangeAsset = value, t -> t.rangeAsset).add()).append(new KeyedCodec("Tint", (Codec)TintProviderAsset.CODEC, true), (t, value) -> t.tintProviderAsset = value, t -> t.tintProviderAsset).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 81 */   private RangeDoubleAsset rangeAsset = new RangeDoubleAsset();
/* 82 */   private TintProviderAsset tintProviderAsset = TintProviderAsset.getFallbackAsset();
/*    */   
/*    */   @Nonnull
/*    */   public DelimiterDouble<TintProvider> build(@Nonnull TintProviderAsset.Argument argument) {
/* 86 */     RangeDouble range = this.rangeAsset.build();
/* 87 */     TintProvider environmentProvider = this.tintProviderAsset.build(argument);
/* 88 */     return new DelimiterDouble(range, environmentProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 93 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\tintproviders\DensityDelimitedTintProviderAsset$DelimiterAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */