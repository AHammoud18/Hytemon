/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
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
/*    */ public class RangeDoubleAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, RangeDoubleAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, RangeDoubleAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(RangeDoubleAsset.class, RangeDoubleAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("MinInclusive", (Codec)Codec.DOUBLE, true), (t, value) -> t.minInclusive = value.doubleValue(), t -> Double.valueOf(t.minInclusive)).add()).append(new KeyedCodec("MaxExclusive", (Codec)Codec.DOUBLE, true), (t, value) -> t.maxExclusive = value.doubleValue(), t -> Double.valueOf(t.maxExclusive)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   private double minInclusive = 0.0D;
/* 39 */   private double maxExclusive = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public RangeDouble build() {
/* 43 */     return new RangeDouble(this.minInclusive, this.maxExclusive);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getId() {
/* 49 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\delimiters\RangeDoubleAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */