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
/*    */ public class SmoothCeilingCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<SmoothCeilingCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SmoothCeilingCurveAsset.class, SmoothCeilingCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, true), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Ceiling", (Codec)Codec.DOUBLE, true), (t, k) -> t.limit = k.doubleValue(), k -> Double.valueOf(k.limit)).add()).build();
/*    */   }
/* 32 */   private CurveAsset curveAsset = new ConstantCurveAsset();
/* 33 */   private double range = 0.0D;
/* 34 */   private double limit = 0.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 39 */     if (this.curveAsset == null) {
/* 40 */       return in -> 0.0D;
/*    */     }
/* 42 */     Double2DoubleFunction curve = this.curveAsset.build();
/*    */     
/* 44 */     return in -> Calculator.smoothMin(this.range, this.limit, curve.applyAsDouble(in));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 49 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\SmoothCeilingCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */