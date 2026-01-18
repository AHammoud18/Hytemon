/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ListPositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.PositionsHorizontalPinchDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.PositionsPinchDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class PositionsPinchDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<PositionsPinchDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 56 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PositionsPinchDensityAsset.class, PositionsPinchDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).append(new KeyedCodec("PinchCurve", (Codec)CurveAsset.CODEC, true), (asset, v) -> asset.pinchCurveAsset = v, asset -> asset.pinchCurveAsset).add()).append(new KeyedCodec("MaxDistance", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.maxDistance = v.doubleValue(), asset -> Double.valueOf(asset.maxDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("NormalizeDistance", (Codec)Codec.BOOLEAN, true), (asset, v) -> asset.normalizeDistance = v.booleanValue(), asset -> Boolean.valueOf(asset.normalizeDistance)).add()).append(new KeyedCodec("HorizontalPinch", (Codec)Codec.BOOLEAN, false), (asset, v) -> asset.isHorizontal = v.booleanValue(), asset -> Boolean.valueOf(asset.isHorizontal)).add()).append(new KeyedCodec("PositionsMinY", (Codec)Codec.DOUBLE, false), (asset, v) -> asset.positionsMinY = v.doubleValue(), asset -> Double.valueOf(asset.positionsMinY)).add()).append(new KeyedCodec("PositionsMaxY", (Codec)Codec.DOUBLE, false), (asset, v) -> asset.positionsMaxY = v.doubleValue(), asset -> Double.valueOf(asset.positionsMaxY)).add()).build();
/*    */   }
/* 58 */   private PositionProviderAsset positionProviderAsset = (PositionProviderAsset)new ListPositionProviderAsset();
/* 59 */   private CurveAsset pinchCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 60 */   private double maxDistance = 0.0D;
/*    */   private boolean normalizeDistance = false;
/*    */   private boolean isHorizontal = false;
/* 63 */   private double positionsMinY = 0.0D;
/* 64 */   private double positionsMaxY = 1.0E-6D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 68 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 70 */     if (this.isHorizontal) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 79 */       PositionsHorizontalPinchDensity node = new PositionsHorizontalPinchDensity(buildFirstInput(argument), this.positionProviderAsset.build(new PositionProviderAsset.Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer)), this.pinchCurveAsset.build(), this.maxDistance, this.normalizeDistance, this.positionsMinY, this.positionsMaxY, argument.workerIndexer.getWorkerCount());
/*    */       
/* 81 */       return (Density)node;
/*    */     } 
/*    */     
/* 84 */     return (Density)new PositionsPinchDensity(
/* 85 */         buildFirstInput(argument), this.positionProviderAsset
/* 86 */         .build(new PositionProviderAsset.Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer)), this.pinchCurveAsset
/* 87 */         .build(), this.maxDistance, this.normalizeDistance);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 95 */     cleanUpInputs();
/* 96 */     this.positionProviderAsset.cleanUp();
/* 97 */     this.pinchCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\PositionsPinchDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */