/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.material;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.FluidMaterial;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
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
/*    */ 
/*    */ public class MaterialAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, MaterialAsset>>, Cleanable
/*    */ {
/*    */   public static final AssetBuilderCodec<String, MaterialAsset> CODEC;
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(MaterialAsset.class, MaterialAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Solid", (Codec)Codec.STRING, true), (t, value) -> t.solidName = value, t -> t.solidName).add()).append(new KeyedCodec("Fluid", (Codec)Codec.STRING, true), (t, value) -> t.fluidName = value, t -> t.fluidName).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private String solidName = "";
/*    */   
/*    */   @Nonnull
/* 45 */   private String fluidName = "";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MaterialAsset(@Nonnull String solidName, @Nonnull String fluidName) {
/* 51 */     this.solidName = solidName;
/* 52 */     this.fluidName = fluidName;
/*    */   }
/*    */   
/*    */   public Material build(@Nonnull MaterialCache materialCache) {
/* 56 */     SolidMaterial solid = materialCache.EMPTY_AIR;
/* 57 */     if (!this.solidName.isEmpty()) {
/* 58 */       solid = materialCache.getSolidMaterial(this.solidName);
/*    */     }
/*    */     
/* 61 */     FluidMaterial fluid = materialCache.EMPTY_FLUID;
/* 62 */     if (!this.fluidName.isEmpty()) {
/* 63 */       fluid = materialCache.getFluidMaterial(this.fluidName);
/*    */     }
/*    */     
/* 66 */     return new Material(solid, fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 71 */     return this.id;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */   
/*    */   public MaterialAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\material\MaterialAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */