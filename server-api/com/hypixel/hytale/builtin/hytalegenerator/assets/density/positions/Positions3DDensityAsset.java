/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ListPositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.PositionsDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.DistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.EuclideanDistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.CurveReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
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
/*    */ public class Positions3DDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<Positions3DDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Positions3DDensityAsset.class, Positions3DDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).append(new KeyedCodec("DistanceCurve", (Codec)CurveAsset.CODEC, true), (asset, v) -> asset.curveAsset = v, asset -> asset.curveAsset).add()).append(new KeyedCodec("MaxDistance", (Codec)Codec.DOUBLE, false), (asset, v) -> asset.maxDistance = v.doubleValue(), asset -> Double.valueOf(asset.maxDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 40 */   private PositionProviderAsset positionProviderAsset = (PositionProviderAsset)new ListPositionProviderAsset();
/* 41 */   private CurveAsset curveAsset = (CurveAsset)new ConstantCurveAsset();
/* 42 */   private double maxDistance = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 46 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 47 */     PositionProvider positionsField = this.positionProviderAsset.build(new PositionProviderAsset.Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer));
/* 48 */     Double2DoubleFunction curve = this.curveAsset.build();
/* 49 */     CurveReturnType returnType = new CurveReturnType(curve);
/* 50 */     return (Density)new PositionsDensity(positionsField, (ReturnType)returnType, (DistanceFunction)new EuclideanDistanceFunction(), this.maxDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 55 */     cleanUpInputs();
/* 56 */     this.positionProviderAsset.cleanUp();
/* 57 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\Positions3DDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */