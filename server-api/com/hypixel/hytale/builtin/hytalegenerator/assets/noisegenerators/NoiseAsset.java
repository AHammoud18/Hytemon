/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.noisegenerators;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class NoiseAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, NoiseAsset>> {
/*    */   static {
/* 17 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, NoiseAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 25 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(NoiseAsset.class, (AssetCodec)CODEC); static {
/* 26 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 27 */   } public static final BuilderCodec<NoiseAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(NoiseAsset.class)
/* 28 */     .build();
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
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract NoiseField build(@Nonnull SeedBox paramSeedBox);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\noisegenerators\NoiseAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */