/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.FieldFunctionPattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
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
/*    */ public class DensityPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<DensityPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityPatternAsset.class, DensityPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, k -> k.delimiterAssets).add()).append(new KeyedCodec("FieldFunction", (Codec)DensityAsset.CODEC, true), (t, k) -> t.densityAsset = k, k -> k.densityAsset).add()).build();
/*    */   }
/* 33 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/* 34 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 39 */     if (isSkipped()) return Pattern.noPattern();
/*    */     
/* 41 */     Density field = this.densityAsset.build(DensityAsset.from(argument));
/* 42 */     FieldFunctionPattern out = new FieldFunctionPattern(field);
/* 43 */     for (DelimiterAsset asset : this.delimiterAssets) {
/* 44 */       out.addDelimiter(asset.min, asset.max);
/*    */     }
/* 46 */     return (Pattern)out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 51 */     this.densityAsset.cleanUp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class DelimiterAsset
/*    */     implements JsonAssetWithMap<String, DefaultAssetMap<String, DelimiterAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */     
/*    */     private double min;
/*    */     
/*    */     private double max;
/*    */ 
/*    */     
/*    */     static {
/* 74 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Min", (Codec)Codec.DOUBLE, true), (t, v) -> t.min = v.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("Max", (Codec)Codec.DOUBLE, true), (t, v) -> t.max = v.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getId() {
/* 84 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\DensityPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */