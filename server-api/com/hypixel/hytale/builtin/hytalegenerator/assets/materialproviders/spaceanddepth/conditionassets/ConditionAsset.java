/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ 
/*    */ public abstract class ConditionAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, ConditionAsset>> {
/* 14 */   private static final ConditionAsset[] EMPTY_INPUTS = new ConditionAsset[0];
/*    */   static {
/* 16 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, ConditionAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 24 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(ConditionAsset.class, (AssetCodec)CODEC); static {
/* 25 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 26 */   } public static final BuilderCodec<ConditionAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(ConditionAsset.class)
/* 27 */     .build();
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
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract SpaceAndDepthMaterialProvider.Condition build();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\ConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */