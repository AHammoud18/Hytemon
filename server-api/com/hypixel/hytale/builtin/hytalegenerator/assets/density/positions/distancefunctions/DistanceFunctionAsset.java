/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.distancefunctions;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.DistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class DistanceFunctionAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, DistanceFunctionAsset>> {
/*    */   static {
/* 17 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, DistanceFunctionAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 25 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(DistanceFunctionAsset.class, (AssetCodec)CODEC); static {
/* 26 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 27 */   } public static final BuilderCodec<DistanceFunctionAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(DistanceFunctionAsset.class)
/* 28 */     .build();
/*    */ 
/*    */ 
/*    */   
/*    */   private String id;
/*    */ 
/*    */   
/*    */   private AssetExtraInfo.Data data;
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract DistanceFunction build(@Nonnull SeedBox paramSeedBox, double paramDouble);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\distancefunctions\DistanceFunctionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */