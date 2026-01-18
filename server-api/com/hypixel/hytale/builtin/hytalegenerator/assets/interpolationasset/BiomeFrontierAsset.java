/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.interpolationasset;
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
/*    */ public class BiomeFrontierAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BiomeFrontierAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, BiomeFrontierAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BiomeFrontierAsset.class, BiomeFrontierAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("InterpolationRadius", (Codec)Codec.INTEGER, true), (t, k) -> t.interpolationRadius = k.intValue(), t -> Integer.valueOf(t.interpolationRadius)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 30 */   private int interpolationRadius = 1;
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInterpolationRadius() {
/* 35 */     return this.interpolationRadius;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 40 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\interpolationasset\BiomeFrontierAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */