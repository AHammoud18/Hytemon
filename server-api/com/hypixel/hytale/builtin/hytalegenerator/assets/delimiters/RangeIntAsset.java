/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.delimiters;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeInt;
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
/*    */ public class RangeIntAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, RangeIntAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, RangeIntAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(RangeIntAsset.class, RangeIntAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("MinInclusive", (Codec)Codec.INTEGER, true), (t, value) -> t.minInclusive = value.intValue(), t -> Integer.valueOf(t.minInclusive)).add()).append(new KeyedCodec("MaxExclusive", (Codec)Codec.INTEGER, true), (t, value) -> t.maxExclusive = value.intValue(), t -> Integer.valueOf(t.maxExclusive)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 38 */   private int minInclusive = 0;
/* 39 */   private int maxExclusive = 0;
/*    */   
/*    */   @Nonnull
/*    */   public RangeInt build() {
/* 43 */     return new RangeInt(this.minInclusive, this.maxExclusive);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getId() {
/* 49 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\delimiters\RangeIntAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */