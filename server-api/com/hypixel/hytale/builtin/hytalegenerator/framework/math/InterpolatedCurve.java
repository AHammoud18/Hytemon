/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InterpolatedCurve
/*    */   implements Double2DoubleFunction
/*    */ {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction functionA;
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction functionB;
/*    */   private final double positionA;
/*    */   private final double positionB;
/*    */   private final double distance;
/*    */   private final double smoothTransition;
/*    */   
/*    */   public InterpolatedCurve(double positionA, double positionB, double smoothTransition, @Nonnull Double2DoubleFunction functionA, @Nonnull Double2DoubleFunction functionB) {
/* 22 */     if (smoothTransition < 0.0D || smoothTransition > 1.0D) {
/* 23 */       throw new IllegalArgumentException();
/*    */     }
/* 25 */     this.smoothTransition = smoothTransition;
/* 26 */     this.positionA = Math.min(positionA, positionB);
/* 27 */     this.positionB = Math.max(positionA, positionB);
/* 28 */     this.distance = positionB - positionA;
/* 29 */     this.functionA = functionA;
/* 30 */     this.functionB = functionB;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(double x) {
/* 35 */     if (x < this.positionA) return this.functionA.get(x); 
/* 36 */     if (x > this.positionB) return this.functionB.get(x); 
/* 37 */     if (this.distance == 0.0D) return (this.functionA.get(x) + this.functionB.get(x)) * 0.5D;
/*    */     
/* 39 */     double bRatio = transitionCurve((x - this.positionA) / this.distance);
/* 40 */     double aRatio = 1.0D - bRatio;
/* 41 */     return aRatio * this.functionA.get(x) + bRatio * this.functionB.get(x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double transitionCurve(double ratio) {
/* 46 */     double a = ratio * Math.PI;
/* 47 */     double v = Math.cos(a);
/* 48 */     v++;
/* 49 */     v /= 2.0D;
/* 50 */     v = 1.0D - v;
/* 51 */     v = v * this.smoothTransition + ratio * (1.0D - this.smoothTransition);
/* 52 */     return v;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\InterpolatedCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */