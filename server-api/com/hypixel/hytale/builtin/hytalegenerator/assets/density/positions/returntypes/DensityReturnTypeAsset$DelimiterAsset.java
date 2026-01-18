/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.FieldFunctionMaterialProviderAsset;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
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
/*     */ public class DelimiterAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, FieldFunctionMaterialProviderAsset.DelimiterAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/*  92 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("From", (Codec)Codec.DOUBLE, true), (t, y) -> t.from = y.doubleValue(), t -> Double.valueOf(t.from)).add()).append(new KeyedCodec("To", (Codec)Codec.DOUBLE, true), (t, out) -> t.to = out.doubleValue(), t -> Double.valueOf(t.to)).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, out) -> t.densityAsset = out, t -> t.densityAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  97 */   private double from = 0.0D;
/*  98 */   private double to = 0.0D;
/*  99 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*     */ 
/*     */   
/*     */   public String getId() {
/* 103 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\returntypes\DensityReturnTypeAsset$DelimiterAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */