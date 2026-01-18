/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.biomemap.BiomeMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class WorldStructureAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, WorldStructureAsset>> {
/*    */   static {
/* 21 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, WorldStructureAsset> CODEC;
/*    */   
/*    */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*    */   
/* 29 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(WorldStructureAsset.class, (AssetCodec)CODEC); static {
/* 30 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/* 31 */   } public static final BuilderCodec<WorldStructureAsset> ABSTRACT_CODEC = BuilderCodec.abstractBuilder(WorldStructureAsset.class)
/* 32 */     .build();
/*    */ 
/*    */ 
/*    */   
/*    */   private String id;
/*    */ 
/*    */ 
/*    */   
/*    */   private AssetExtraInfo.Data data;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Argument
/*    */   {
/*    */     public MaterialCache materialCache;
/*    */     
/*    */     public SeedBox parentSeed;
/*    */     public WorkerIndexer workerIndexer;
/*    */     
/*    */     public Argument(@Nonnull MaterialCache materialCache, @Nonnull SeedBox parentSeed, @Nonnull WorkerIndexer workerIndexer) {
/* 58 */       this.materialCache = materialCache;
/* 59 */       this.parentSeed = parentSeed;
/* 60 */       this.workerIndexer = workerIndexer;
/*    */     }
/*    */     
/*    */     public Argument(@Nonnull Argument argument) {
/* 64 */       this.materialCache = argument.materialCache;
/* 65 */       this.parentSeed = argument.parentSeed;
/* 66 */       this.workerIndexer = argument.workerIndexer;
/*    */     }
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */   
/*    */   public abstract BiomeMap<SolidMaterial> buildBiomeMap(@Nonnull Argument paramArgument);
/*    */   
/*    */   public abstract int getBiomeTransitionDistance();
/*    */   
/*    */   public abstract int getMaxBiomeEdgeDistance();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\WorldStructureAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */