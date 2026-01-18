/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
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
/*    */ public class SmoothClampCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<SmoothClampCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SmoothClampCurveAsset.class, SmoothClampCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, false), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).append(new KeyedCodec("WallA", (Codec)Codec.DOUBLE, false), (t, k) -> t.wallA = k.doubleValue(), k -> Double.valueOf(k.wallA)).add()).append(new KeyedCodec("WallB", (Codec)Codec.DOUBLE, false), (t, k) -> t.wallB = k.doubleValue(), k -> Double.valueOf(k.wallB)).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, false), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 37 */   private CurveAsset curveAsset = new ConstantCurveAsset();
/* 38 */   private double wallA = 1.0D;
/* 39 */   private double wallB = -1.0D;
/* 40 */   private double range = 0.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 45 */     double defaultValue = (this.wallA + this.wallB) / 2.0D;
/* 46 */     if (this.curveAsset == null) {
/* 47 */       return in -> defaultValue;
/*    */     }
/* 49 */     double min = Math.min(this.wallA, this.wallB);
/* 50 */     double max = Math.max(this.wallA, this.wallB);
/* 51 */     Double2DoubleFunction inputCurve = this.curveAsset.build();
/*    */     
/* 53 */     if (this.range == 0.0D) {
/* 54 */       return in -> Calculator.clamp(this.wallA, inputCurve.applyAsDouble(in), this.wallB);
/*    */     }
/*    */     
/* 57 */     return in -> {
/*    */         double value = inputCurve.applyAsDouble(in);
/*    */         double smoothedMax = Calculator.smoothMax(this.range, min, value);
/*    */         double smoothedMin = Calculator.smoothMin(this.range, max, value);
/*    */         return (smoothedMin + smoothedMax) / 2.0D;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 67 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\SmoothClampCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */