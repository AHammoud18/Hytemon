/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.basic;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.biomes.BiomeAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.rangemaps.DoubleRange;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class BiomeRangeAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BiomeRangeAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, BiomeRangeAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BiomeRangeAsset.class, BiomeRangeAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Biome", (Codec)new ContainedAssetCodec(BiomeAsset.class, (AssetCodec)BiomeAsset.CODEC), true), (t, k) -> t.biomeAssetId = k, t -> t.biomeAssetId).addValidatorLate(() -> BiomeAsset.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("Min", (Codec)Codec.DOUBLE, true), (t, k) -> t.min = k.doubleValue(), t -> Double.valueOf(t.min)).add()).append(new KeyedCodec("Max", (Codec)Codec.DOUBLE, true), (t, k) -> t.max = k.doubleValue(), t -> Double.valueOf(t.max)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 46 */   private double min = -1.0D;
/* 47 */   private double max = 1.0D;
/* 48 */   private String biomeAssetId = "";
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public DoubleRange getRange() {
/* 54 */     return DoubleRange.inclusive(this.min, this.max);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BiomeAsset getBiomeAsset() {
/* 59 */     return (BiomeAsset)((DefaultAssetMap)BiomeAsset.getAssetStore().getAssetMap()).getAsset(this.biomeAssetId);
/*    */   }
/*    */   
/*    */   public String getBiomeAssetId() {
/* 63 */     return this.biomeAssetId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 68 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\basic\BiomeRangeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */