/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers.WeightedThicknessLayer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ 
/*    */ public class WeightedThicknessLayerAsset
/*    */   extends LayerAsset
/*    */ {
/*    */   public static final BuilderCodec<WeightedThicknessLayerAsset> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeightedThicknessLayerAsset.class, WeightedThicknessLayerAsset::new, LayerAsset.ABSTRACT_CODEC).append(new KeyedCodec("PossibleThicknesses", (Codec)new ArrayCodec((Codec)WeightedThicknessAsset.CODEC, x$0 -> new WeightedThicknessAsset[x$0]), true), (t, k) -> t.possibleThicknessAssets = k, k -> k.possibleThicknessAssets).addValidator((Validator)Validators.nonNullArrayElements()).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (t, k) -> t.seed = k, k -> k.seed).add()).build();
/*    */   }
/* 43 */   private MaterialProviderAsset materialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/* 44 */   private String seed = "";
/* 45 */   private WeightedThicknessAsset[] possibleThicknessAssets = new WeightedThicknessAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Layer<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 50 */     WeightedMap<Integer> pool = new WeightedMap();
/* 51 */     for (WeightedThicknessAsset asset : this.possibleThicknessAssets) {
/* 52 */       pool.add(Integer.valueOf(asset.thickness), asset.weight);
/*    */     }
/* 54 */     return (SpaceAndDepthMaterialProvider.Layer<Material>)new WeightedThicknessLayer(pool, this.materialProviderAsset.build(argument), argument.parentSeed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 59 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class WeightedThicknessAsset
/*    */     implements JsonAssetWithMap<String, DefaultAssetMap<String, WeightedThicknessAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, WeightedThicknessAsset> CODEC;
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */     
/*    */     private double weight;
/*    */     
/*    */     private int thickness;
/*    */ 
/*    */     
/*    */     static {
/* 82 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(WeightedThicknessAsset.class, WeightedThicknessAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (t, y) -> t.weight = y.doubleValue(), t -> Double.valueOf(t.weight)).add()).append(new KeyedCodec("Thickness", (Codec)Codec.INTEGER, true), (t, out) -> t.thickness = out.intValue(), t -> Integer.valueOf(t.thickness)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getId() {
/* 92 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\layerassets\WeightedThicknessLayerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */