/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.blockset;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
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
/*    */ public class MaterialSetAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, MaterialSetAsset>>, Cleanable
/*    */ {
/*    */   public static final AssetBuilderCodec<String, MaterialSetAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(MaterialSetAsset.class, MaterialSetAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Inclusive", (Codec)Codec.BOOLEAN, false), (t, k) -> t.inclusive = k.booleanValue(), t -> Boolean.valueOf(t.inclusive)).add()).append(new KeyedCodec("Materials", (Codec)new ArrayCodec((Codec)MaterialAsset.CODEC, x$0 -> new MaterialAsset[x$0]), true), (asset, value) -> asset.materialAssets = value, asset -> asset.materialAssets).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean inclusive = true;
/*    */   
/* 44 */   private MaterialAsset[] materialAssets = new MaterialAsset[0];
/*    */ 
/*    */ 
/*    */   
/*    */   public MaterialSet build(@Nonnull MaterialCache materialCache) {
/* 49 */     ArrayList<Material> materials = new ArrayList<>(this.materialAssets.length);
/*    */     
/* 51 */     for (MaterialAsset materialAsset : this.materialAssets) {
/* 52 */       if (materialAsset != null) {
/* 53 */         materials.add(materialAsset.build(materialCache));
/*    */       }
/*    */     } 
/* 56 */     return new MaterialSet(this.inclusive, materials);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 61 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 66 */     for (MaterialAsset materialAsset : this.materialAssets)
/* 67 */       materialAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\blockset\MaterialSetAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */