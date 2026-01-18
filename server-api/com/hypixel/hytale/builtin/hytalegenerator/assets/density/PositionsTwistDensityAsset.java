/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.ListPositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.PositionsTwistDensity;
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
/*    */ public class PositionsTwistDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<PositionsTwistDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PositionsTwistDensityAsset.class, PositionsTwistDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).append(new KeyedCodec("TwistCurve", (Codec)CurveAsset.CODEC, true), (asset, v) -> asset.pinchCurveAsset = v, asset -> asset.pinchCurveAsset).add()).append(new KeyedCodec("TwistAxis", (Codec)Vector3d.CODEC, true), (asset, v) -> asset.twistAxis = v, asset -> asset.twistAxis).add()).append(new KeyedCodec("MaxDistance", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.maxDistance = v.doubleValue(), asset -> Double.valueOf(asset.maxDistance)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("NormalizeDistance", (Codec)Codec.BOOLEAN, true), (asset, v) -> asset.normalizeDistance = v.booleanValue(), asset -> Boolean.valueOf(asset.normalizeDistance)).add()).append(new KeyedCodec("ZeroPositionsY", (Codec)Codec.BOOLEAN, true), (asset, v) -> asset.zeroPositionsY = v.booleanValue(), asset -> Boolean.valueOf(asset.zeroPositionsY)).add()).build();
/*    */   }
/* 53 */   private PositionProviderAsset positionProviderAsset = (PositionProviderAsset)new ListPositionProviderAsset();
/* 54 */   private CurveAsset pinchCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 55 */   private Vector3d twistAxis = new Vector3d();
/* 56 */   private double maxDistance = 0.0D;
/*    */   private boolean normalizeDistance = false;
/*    */   private boolean zeroPositionsY = false;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 62 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 64 */     return (Density)new PositionsTwistDensity(
/* 65 */         buildFirstInput(argument), this.positionProviderAsset
/* 66 */         .build(new PositionProviderAsset.Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer)), this.pinchCurveAsset
/* 67 */         .build(), this.twistAxis, this.maxDistance, this.normalizeDistance, this.zeroPositionsY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 77 */     cleanUpInputs();
/* 78 */     this.positionProviderAsset.cleanUp();
/* 79 */     this.pinchCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\PositionsTwistDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */