/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Stepinizer implements Function<Double, Double>, Double2DoubleFunction {
/*    */   private double stepSize;
/*    */   private double stepSizeHalf;
/*    */   private double slope;
/*    */   private double topSmooth;
/*    */   private double bottomSmooth;
/*    */   
/*    */   public Stepinizer() {
/* 15 */     setStep(1.0D);
/* 16 */     setEdgeSlope(1.0D);
/* 17 */     setSmooth(1.0D, 1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Stepinizer setSmooth(double top, double bottom) {
/* 24 */     if (top <= 0.0D || bottom <= 0.0D) throw new IllegalArgumentException("invalid values provided"); 
/* 25 */     this.topSmooth = top;
/* 26 */     this.bottomSmooth = bottom;
/* 27 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Stepinizer setEdgeSlope(double slope) {
/* 32 */     if (slope < 0.0D) throw new IllegalArgumentException("negative slope"); 
/* 33 */     this.slope = slope;
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Stepinizer setStep(double size) {
/* 39 */     if (size < 0.0D) throw new IllegalArgumentException("negative size"); 
/* 40 */     this.stepSize = size;
/* 41 */     this.stepSizeHalf = size / 2.0D;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double apply(double x) {
/* 48 */     return get(x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(double x) {
/* 53 */     double polarity = polarity(x);
/* 54 */     double steepness = steepness(polarity);
/* 55 */     double bottomStep = bottomStep(x);
/* 56 */     double topStep = topStep(x);
/*    */     
/* 58 */     if (polarity < 0.0D) { result = Calculator.smoothMax(this.bottomSmooth, steepness, -1.0D); }
/* 59 */     else { result = Calculator.smoothMin(this.topSmooth, steepness, 1.0D); }
/* 60 */      double result = Normalizer.normalize(-1.0D, 1.0D, bottomStep, topStep, result);
/* 61 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private double closestStep(double x) {
/* 67 */     double remainder = x % this.stepSize;
/* 68 */     if (remainder < this.stepSizeHalf) return x - remainder; 
/* 69 */     return x - remainder + this.stepSize;
/*    */   }
/*    */   
/*    */   private double topStep(double x) {
/* 73 */     return x - x % this.stepSize + this.stepSize;
/*    */   }
/*    */   
/*    */   private double bottomStep(double x) {
/* 77 */     return x - x % this.stepSize;
/*    */   }
/*    */   
/*    */   private double polarity(double x) {
/* 81 */     double midPoint = bottomStep(x) + this.stepSizeHalf;
/* 82 */     return (x - midPoint) / this.stepSizeHalf;
/*    */   }
/*    */   
/*    */   private double steepness(double x) {
/* 86 */     return this.slope * x;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Stepinizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */