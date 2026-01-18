/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.InterpolatedCurve;
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
/*    */ public class DistanceSCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<DistanceSCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DistanceSCurveAsset.class, DistanceSCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("ExponentA", (Codec)Codec.DOUBLE, true), (t, k) -> t.exponentA = k.doubleValue(), k -> Double.valueOf(k.exponentA)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ExponentB", (Codec)Codec.DOUBLE, true), (t, k) -> t.exponentB = k.doubleValue(), k -> Double.valueOf(k.exponentB)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Transition", (Codec)Codec.DOUBLE, false), (t, k) -> t.transition = k.doubleValue(), k -> Double.valueOf(k.transition)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, true), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("TransitionSmooth", (Codec)Codec.DOUBLE, false), (t, k) -> t.transitionSmooth = k.doubleValue(), k -> Double.valueOf(k.transitionSmooth)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).build();
/*    */   }
/* 42 */   private double exponentA = 1.0D;
/* 43 */   private double exponentB = 1.0D;
/* 44 */   private double range = 1.0D;
/* 45 */   private double transition = 1.0D;
/* 46 */   private double transitionSmooth = 1.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 51 */     Double2DoubleFunction functionA = in -> {
/*    */         if (in >= this.range)
/*    */           return 0.0D;  in /= this.range;
/*    */         in *= -1.0D;
/*    */         in++;
/*    */         return Math.pow(in, this.exponentA);
/*    */       };
/* 58 */     Double2DoubleFunction functionB = in -> {
/*    */         if (in >= this.range)
/*    */           return 0.0D; 
/*    */         in /= this.range;
/*    */         in *= -1.0D;
/*    */         in++;
/*    */         return Math.pow(in, this.exponentB);
/*    */       };
/* 66 */     double transitionDistance = this.transition * this.range;
/* 67 */     double positionA = this.range / 2.0D - transitionDistance / 2.0D;
/* 68 */     double positionB = positionA + transitionDistance;
/* 69 */     return (Double2DoubleFunction)new InterpolatedCurve(positionA, positionB, this.transitionSmooth, functionA, functionB);
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\DistanceSCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */