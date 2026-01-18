/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.distancefunctions.DistanceFunctionAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.distancefunctions.EuclideanDistanceFunctionAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes.CurveReturnTypeAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes.ReturnTypeAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ListPositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.PositionsDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.DistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
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
/*    */ public class PositionsCellNoiseDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<PositionsCellNoiseDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PositionsCellNoiseDensityAsset.class, PositionsCellNoiseDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).append(new KeyedCodec("ReturnType", (Codec)ReturnTypeAsset.CODEC, true), (asset, v) -> asset.returnTypeAsset = v, asset -> asset.returnTypeAsset).add()).append(new KeyedCodec("DistanceFunction", (Codec)DistanceFunctionAsset.CODEC, true), (asset, v) -> asset.distanceFunctionAsset = v, asset -> asset.distanceFunctionAsset).add()).append(new KeyedCodec("MaxDistance", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.maxDistance = v.doubleValue(), asset -> Double.valueOf(asset.maxDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 45 */   private PositionProviderAsset positionProviderAsset = (PositionProviderAsset)new ListPositionProviderAsset();
/* 46 */   private ReturnTypeAsset returnTypeAsset = (ReturnTypeAsset)new CurveReturnTypeAsset();
/* 47 */   private DistanceFunctionAsset distanceFunctionAsset = (DistanceFunctionAsset)new EuclideanDistanceFunctionAsset();
/* 48 */   private double maxDistance = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 52 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 54 */     PositionProvider positionsField = this.positionProviderAsset.build(new PositionProviderAsset.Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer));
/* 55 */     ReturnType returnType = this.returnTypeAsset.build(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/* 56 */     returnType.setMaxDistance(this.maxDistance);
/* 57 */     DistanceFunction distanceFunction = this.distanceFunctionAsset.build(argument.parentSeed, this.maxDistance);
/* 58 */     return (Density)new PositionsDensity(positionsField, returnType, distanceFunction, this.maxDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 63 */     cleanUpInputs();
/* 64 */     this.positionProviderAsset.cleanUp();
/* 65 */     this.returnTypeAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\PositionsCellNoiseDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */