/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeightedPropAsset
/*     */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, ClusterPropAsset.WeightedPropAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, WeightedPropAsset> CODEC;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/* 120 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedPropAsset.class, WeightedPropAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, w) -> t.weight = w.doubleValue(), t -> Double.valueOf(t.weight)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ColumnProp", (Codec)PropAsset.CODEC, true), (t, out) -> t.propAsset = out, t -> t.propAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 125 */   private double weight = 1.0D;
/*     */   
/*     */   private PropAsset propAsset;
/*     */   
/*     */   public String getId() {
/* 130 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 135 */     this.propAsset.cleanUp();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\ClusterPropAsset$WeightedPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */