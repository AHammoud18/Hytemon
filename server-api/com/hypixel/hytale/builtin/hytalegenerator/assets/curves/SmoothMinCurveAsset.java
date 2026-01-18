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
/*    */ public class SmoothMinCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<SmoothMinCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SmoothMinCurveAsset.class, SmoothMinCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("CurveA", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveAAsset = k, k -> k.curveAAsset).add()).append(new KeyedCodec("CurveB", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveBAsset = k, k -> k.curveBAsset).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, true), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 32 */   private CurveAsset curveAAsset = new ConstantCurveAsset();
/* 33 */   private CurveAsset curveBAsset = new ConstantCurveAsset();
/* 34 */   private double range = 0.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 39 */     if (this.curveAAsset == null || this.curveBAsset == null) {
/* 40 */       return in -> 0.0D;
/*    */     }
/* 42 */     Double2DoubleFunction curveA = this.curveAAsset.build();
/* 43 */     Double2DoubleFunction curveB = this.curveBAsset.build();
/*    */     
/* 45 */     return in -> Calculator.smoothMin(this.range, curveA.applyAsDouble(in), curveB.applyAsDouble(in));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 50 */     this.curveAAsset.cleanUp();
/* 51 */     this.curveBAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\SmoothMinCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */