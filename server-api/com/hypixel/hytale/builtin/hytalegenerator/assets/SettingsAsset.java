/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.List;
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
/*    */ public class SettingsAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, SettingsAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, SettingsAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 50 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SettingsAsset.class, SettingsAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("StatsCheckpoints", (Codec)new ArrayCodec((Codec)Codec.INTEGER, x$0 -> new Integer[x$0]), true), (t, k) -> t.checkpoints = k, t -> t.checkpoints).add()).append(new KeyedCodec("CustomConcurrency", (Codec)Codec.INTEGER, true), (t, k) -> t.customConcurrency = k.intValue(), t -> Integer.valueOf(t.customConcurrency)).addValidator(Validators.greaterThan(Integer.valueOf(-2))).add()).append(new KeyedCodec("BufferCapacityFactor", (Codec)Codec.DOUBLE, true), (asset, value) -> asset.bufferCapacityFactor = value.doubleValue(), asset -> Double.valueOf(asset.bufferCapacityFactor)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("TargetViewDistance", (Codec)Codec.DOUBLE, true), (asset, value) -> asset.targetViewDistance = value.doubleValue(), asset -> Double.valueOf(asset.targetViewDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("TargetPlayerCount", (Codec)Codec.DOUBLE, true), (asset, value) -> asset.targetPlayerCount = value.doubleValue(), asset -> Double.valueOf(asset.targetPlayerCount)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 55 */   private Integer[] checkpoints = new Integer[0];
/* 56 */   private int customConcurrency = -1;
/* 57 */   private double bufferCapacityFactor = 0.3D;
/* 58 */   private double targetViewDistance = 512.0D;
/* 59 */   private double targetPlayerCount = 3.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Integer> getStatsCheckpoints() {
/* 65 */     return List.of(this.checkpoints);
/*    */   }
/*    */   
/*    */   public int getCustomConcurrency() {
/* 69 */     return this.customConcurrency;
/*    */   }
/*    */   public double getBufferCapacityFactor() {
/* 72 */     return this.bufferCapacityFactor;
/*    */   }
/*    */   public double getTargetViewDistance() {
/* 75 */     return this.targetViewDistance;
/*    */   }
/*    */   public double getTargetPlayerCount() {
/* 78 */     return this.targetPlayerCount;
/*    */   }
/*    */   
/*    */   public static int getSampleBits(int v) {
/* 82 */     switch (v) { case 2: case 4: case 8:  }  return 
/*    */ 
/*    */ 
/*    */       
/* 86 */       0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 92 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\SettingsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */