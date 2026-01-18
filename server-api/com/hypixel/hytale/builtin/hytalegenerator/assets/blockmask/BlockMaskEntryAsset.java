/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockset.MaterialSetAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
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
/*    */ public class BlockMaskEntryAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockMaskEntryAsset>>, Cleanable
/*    */ {
/*    */   public static final AssetBuilderCodec<String, BlockMaskEntryAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockMaskEntryAsset.class, BlockMaskEntryAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Source", (Codec)MaterialSetAsset.CODEC, true), (t, k) -> t.propBlockSet = k, t -> t.propBlockSet).add()).append(new KeyedCodec("CanReplace", (Codec)MaterialSetAsset.CODEC, true), (t, k) -> t.replacesBlockSet = k, t -> t.replacesBlockSet).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   private MaterialSetAsset propBlockSet = new MaterialSetAsset();
/* 41 */   private MaterialSetAsset replacesBlockSet = new MaterialSetAsset();
/*    */ 
/*    */ 
/*    */   
/*    */   public MaterialSet getPropBlockSet(@Nonnull MaterialCache materialCache) {
/* 46 */     return this.propBlockSet.build(materialCache);
/*    */   }
/*    */   
/*    */   public MaterialSet getReplacesBlockSet(@Nonnull MaterialCache materialCache) {
/* 50 */     return this.replacesBlockSet.build(materialCache);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 55 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 60 */     this.propBlockSet.cleanUp();
/* 61 */     this.replacesBlockSet.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\blockmask\BlockMaskEntryAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */