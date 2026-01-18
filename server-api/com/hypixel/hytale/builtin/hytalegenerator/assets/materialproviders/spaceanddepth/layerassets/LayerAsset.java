/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class LayerAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, LayerAsset>> {
/* 19 */   private static final LayerAsset[] EMPTY_INPUTS = new LayerAsset[0];
/*    */   static {
/* 21 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, LayerAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 29 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(LayerAsset.class, (AssetCodec)CODEC); static {
/* 30 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 31 */   } public static final BuilderCodec<LayerAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(LayerAsset.class)
/* 32 */     .build();
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
/* 43 */     return this.id;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */   
/*    */   public abstract SpaceAndDepthMaterialProvider.Layer<Material> build(@Nonnull MaterialProviderAsset.Argument paramArgument);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\layerassets\LayerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */