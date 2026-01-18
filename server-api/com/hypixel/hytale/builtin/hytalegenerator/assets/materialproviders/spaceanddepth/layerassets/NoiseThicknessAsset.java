/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers.NoiseThickness;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseThicknessAsset
/*    */   extends LayerAsset
/*    */ {
/*    */   public static final BuilderCodec<NoiseThicknessAsset> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NoiseThicknessAsset.class, NoiseThicknessAsset::new, LayerAsset.ABSTRACT_CODEC).append(new KeyedCodec("ThicknessFunctionXZ", (Codec)DensityAsset.CODEC, true), (asset, k) -> asset.densityAsset = k, asset -> asset.densityAsset).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).build();
/*    */   }
/* 30 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/* 31 */   private MaterialProviderAsset materialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Layer<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 36 */     MaterialProvider<Material> materialProvider = this.materialProviderAsset.build(argument);
/* 37 */     Density functionTree = this.densityAsset.build(DensityAsset.from(argument));
/* 38 */     return (SpaceAndDepthMaterialProvider.Layer<Material>)new NoiseThickness(functionTree, materialProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 43 */     this.densityAsset.cleanUp();
/* 44 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\layerassets\NoiseThicknessAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */