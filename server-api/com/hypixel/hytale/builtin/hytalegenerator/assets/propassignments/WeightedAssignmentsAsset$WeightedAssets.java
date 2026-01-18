/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
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
/*    */ public class WeightedAssets
/*    */   implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, WeightedAssignmentsAsset.WeightedAssets>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, WeightedAssets> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 84 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedAssets.class, WeightedAssets::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, v) -> t.weight = v.doubleValue(), t -> Double.valueOf(t.weight)).add()).append(new KeyedCodec("Assignments", (Codec)AssignmentsAsset.CODEC, true), (t, v) -> t.assignmentsAsset = v, t -> t.assignmentsAsset).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 89 */   private double weight = 1.0D;
/* 90 */   private AssignmentsAsset assignmentsAsset = new ConstantAssignmentsAsset();
/*    */ 
/*    */   
/*    */   public String getId() {
/* 94 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 99 */     this.assignmentsAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\WeightedAssignmentsAsset$WeightedAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */