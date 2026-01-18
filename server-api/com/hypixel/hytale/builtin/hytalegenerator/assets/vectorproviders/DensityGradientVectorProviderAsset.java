/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.ConstantVectorProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.DensityGradientVectorProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ public class DensityGradientVectorProviderAsset
/*    */   extends VectorProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<DensityGradientVectorProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityGradientVectorProviderAsset.class, DensityGradientVectorProviderAsset::new, VectorProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (asset, value) -> asset.densityAsset = value, asset -> asset.densityAsset).add()).append(new KeyedCodec("SampleDistance", (Codec)BuilderCodec.DOUBLE, true), (asset, value) -> asset.sampleDistance = value.doubleValue(), asset -> Double.valueOf(asset.sampleDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 33 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/* 34 */   private double sampleDistance = 1.0D;
/*    */ 
/*    */   
/*    */   public VectorProvider build(@Nonnull VectorProviderAsset.Argument argument) {
/* 38 */     if (isSkipped()) return (VectorProvider)new ConstantVectorProvider(new Vector3d());
/*    */     
/* 40 */     Density density = this.densityAsset.build(DensityAsset.from(argument));
/* 41 */     return (VectorProvider)new DensityGradientVectorProvider(density, this.sampleDistance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\DensityGradientVectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */