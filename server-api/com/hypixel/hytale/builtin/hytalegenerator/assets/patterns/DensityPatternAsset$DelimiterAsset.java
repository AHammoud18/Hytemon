/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ public class DelimiterAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, DensityPatternAsset.DelimiterAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   private double min;
/*    */   private double max;
/*    */   
/*    */   static {
/* 74 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Min", (Codec)Codec.DOUBLE, true), (t, v) -> t.min = v.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("Max", (Codec)Codec.DOUBLE, true), (t, v) -> t.max = v.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 84 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\DensityPatternAsset$DelimiterAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */