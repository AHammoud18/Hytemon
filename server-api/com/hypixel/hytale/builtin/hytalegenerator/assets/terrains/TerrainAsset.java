/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.terrains;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class TerrainAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, TerrainAsset>> {
/* 20 */   private static final TerrainAsset[] EMPTY_INPUTS = new TerrainAsset[0];
/*    */   static {
/* 22 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, TerrainAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 30 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(TerrainAsset.class, (AssetCodec)CODEC); static {
/* 31 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 32 */   } public static final BuilderCodec<TerrainAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(TerrainAsset.class)
/* 33 */     .build();
/*    */   
/*    */   private String id;
/*    */   
/*    */   private AssetExtraInfo.Data data;
/* 38 */   private TerrainAsset[] inputs = EMPTY_INPUTS;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean skip = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 49 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 54 */     for (TerrainAsset asset : this.inputs)
/* 55 */       asset.cleanUp(); 
/*    */   }
/*    */   
/*    */   public abstract Density buildDensity(@Nonnull SeedBox paramSeedBox, @Nonnull ReferenceBundle paramReferenceBundle, @Nonnull WorkerIndexer paramWorkerIndexer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\terrains\TerrainAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */