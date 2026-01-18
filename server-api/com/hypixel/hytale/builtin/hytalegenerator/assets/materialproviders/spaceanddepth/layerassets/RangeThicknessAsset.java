/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers.RangedThicknessLayer;
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
/*    */ 
/*    */ public class RangeThicknessAsset
/*    */   extends LayerAsset
/*    */ {
/*    */   public static final BuilderCodec<RangeThicknessAsset> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RangeThicknessAsset.class, RangeThicknessAsset::new, LayerAsset.ABSTRACT_CODEC).append(new KeyedCodec("RangeMin", (Codec)Codec.INTEGER, true), (t, k) -> t.rangeMin = k.intValue(), k -> Integer.valueOf(k.rangeMin)).add()).append(new KeyedCodec("RangeMax", (Codec)Codec.INTEGER, true), (t, k) -> t.rangeMax = k.intValue(), k -> Integer.valueOf(k.rangeMax)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (t, k) -> t.seed = k, k -> k.seed).add()).afterDecode(asset -> asset.rangeMax = Math.max(asset.rangeMin, asset.rangeMax))).build();
/*    */   }
/* 42 */   private MaterialProviderAsset materialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/* 43 */   private String seed = "";
/* 44 */   private int rangeMin = 0;
/* 45 */   private int rangeMax = 0;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Layer<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 50 */     MaterialProvider<Material> materialProvider = this.materialProviderAsset.build(argument);
/* 51 */     return (SpaceAndDepthMaterialProvider.Layer<Material>)new RangedThicknessLayer(this.rangeMin, this.rangeMax, argument.parentSeed.child(this.seed), materialProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 56 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\layerassets\RangeThicknessAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */