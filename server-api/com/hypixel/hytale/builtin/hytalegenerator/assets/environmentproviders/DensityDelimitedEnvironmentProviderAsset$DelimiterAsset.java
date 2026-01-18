/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters.RangeDoubleAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.DelimiterDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
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
/*    */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, DensityDelimitedEnvironmentProviderAsset.DelimiterAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 77 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Range", (Codec)RangeDoubleAsset.CODEC, true), (t, value) -> t.rangeAsset = value, t -> t.rangeAsset).add()).append(new KeyedCodec("Environment", (Codec)EnvironmentProviderAsset.CODEC, true), (t, value) -> t.environmentProviderAsset = value, t -> t.environmentProviderAsset).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 82 */   private RangeDoubleAsset rangeAsset = new RangeDoubleAsset();
/* 83 */   private EnvironmentProviderAsset environmentProviderAsset = EnvironmentProviderAsset.getFallbackAsset();
/*    */   
/*    */   @Nonnull
/*    */   public DelimiterDouble<EnvironmentProvider> build(@Nonnull EnvironmentProviderAsset.Argument argument) {
/* 87 */     RangeDouble range = this.rangeAsset.build();
/* 88 */     EnvironmentProvider environmentProvider = this.environmentProviderAsset.build(argument);
/* 89 */     return new DelimiterDouble(range, environmentProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 94 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 99 */     this.environmentProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\environmentproviders\DensityDelimitedEnvironmentProviderAsset$DelimiterAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */