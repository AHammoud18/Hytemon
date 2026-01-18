/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
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
/*     */ public class WeightedMaterialAsset
/*     */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, WeightedMaterialProviderAsset.WeightedMaterialAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, WeightedMaterialAsset> CODEC;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/*  85 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedMaterialAsset.class, WeightedMaterialAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, y) -> t.weight = y.doubleValue(), t -> Double.valueOf(t.weight)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, out) -> t.materialProviderAsset = out, t -> t.materialProviderAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  90 */   private double weight = 1.0D;
/*     */   
/*     */   private MaterialProviderAsset materialProviderAsset;
/*     */   
/*     */   public String getId() {
/*  95 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 100 */     this.materialProviderAsset.cleanUp();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\WeightedMaterialProviderAsset$WeightedMaterialAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */