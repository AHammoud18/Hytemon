/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
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
/*     */ public class SwitchCaseAsset
/*     */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, SwitchDensityAsset.SwitchCaseAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, SwitchCaseAsset> CODEC;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/*  90 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SwitchCaseAsset.class, SwitchCaseAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("CaseState", (Codec)Codec.STRING, true), (t, y) -> t.caseState = y, t -> t.caseState).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, out) -> t.densityAsset = out, t -> t.densityAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  95 */   private String caseState = "";
/*     */   
/*     */   private DensityAsset densityAsset;
/*     */   
/*     */   public String getId() {
/* 100 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 105 */     this.densityAsset.cleanUp();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SwitchDensityAsset$SwitchCaseAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */