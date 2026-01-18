/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.mapcontentfield;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ 
/*    */ public abstract class ContentFieldAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, ContentFieldAsset>> {
/*    */   static {
/* 15 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, ContentFieldAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 23 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(ContentFieldAsset.class, (AssetCodec)CODEC); static {
/* 24 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 25 */   } public static final BuilderCodec<ContentFieldAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(ContentFieldAsset.class)
/* 26 */     .build();
/*    */ 
/*    */   
/*    */   private String id;
/*    */   
/*    */   private AssetExtraInfo.Data data;
/*    */ 
/*    */   
/*    */   public String getId() {
/* 35 */     return this.id;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\mapcontentfield\ContentFieldAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */