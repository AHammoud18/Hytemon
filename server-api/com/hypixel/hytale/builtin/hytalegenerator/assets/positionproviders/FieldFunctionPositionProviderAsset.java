/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.FieldFunctionPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FieldFunctionPositionProviderAsset extends PositionProviderAsset {
/* 19 */   private static final DelimiterAsset[] EMPTY_DELIMITER_ASSETS = new DelimiterAsset[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<FieldFunctionPositionProviderAsset> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FieldFunctionPositionProviderAsset.class, FieldFunctionPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (asset, v) -> asset.delimiterAssets = v, asset -> asset.delimiterAssets).add()).append(new KeyedCodec("FieldFunction", (Codec)DensityAsset.CODEC, true), (asset, v) -> asset.densityAsset = v, asset -> asset.densityAsset).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).build();
/*    */   }
/* 39 */   private DelimiterAsset[] delimiterAssets = EMPTY_DELIMITER_ASSETS;
/* 40 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/* 41 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 46 */     if (skip()) {
/* 47 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 50 */     Density functionTree = this.densityAsset.build(DensityAsset.from(argument));
/* 51 */     PositionProvider positionProvider = this.positionProviderAsset.build(argument);
/* 52 */     FieldFunctionPositionProvider out = new FieldFunctionPositionProvider(functionTree, positionProvider);
/* 53 */     for (DelimiterAsset asset : this.delimiterAssets) {
/* 54 */       out.addDelimiter(asset.min, asset.max);
/*    */     }
/* 56 */     return (PositionProvider)out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 61 */     this.densityAsset.cleanUp();
/* 62 */     this.positionProviderAsset.cleanUp();
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
/* 85 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Min", (Codec)Codec.DOUBLE, true), (t, v) -> t.min = v.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("Max", (Codec)Codec.DOUBLE, true), (t, v) -> t.max = v.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 90 */     private double min = 0.0D;
/* 91 */     private double max = 0.0D;
/*    */ 
/*    */     
/*    */     public String getId() {
/* 95 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\FieldFunctionPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */