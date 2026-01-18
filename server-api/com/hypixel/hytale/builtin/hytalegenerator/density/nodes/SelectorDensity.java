/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Normalizer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SelectorDensity
/*    */   extends Density {
/*    */   private final double fromMin;
/*    */   private final double fromMax;
/*    */   private final double toMin;
/*    */   private final double toMax;
/*    */   private final double smoothRange;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public SelectorDensity(double fromMin, double fromMax, double toMin, double toMax, double smoothRange, Density input) {
/* 20 */     if (fromMin > fromMax || toMin > toMax || smoothRange < 0.0D)
/* 21 */       throw new IllegalArgumentException("min larger than max"); 
/* 22 */     this.fromMin = fromMin;
/* 23 */     this.fromMax = fromMax;
/* 24 */     this.toMin = toMin;
/* 25 */     this.toMax = toMax;
/* 26 */     this.smoothRange = smoothRange;
/* 27 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 32 */     double v = 0.0D;
/* 33 */     if (this.input != null) v = this.input.process(context); 
/* 34 */     v = Normalizer.normalize(this.fromMin, this.fromMax, this.toMin, this.toMax, v);
/* 35 */     if (this.smoothRange == 0.0D) {
/* 36 */       v = Math.max(this.toMin, v);
/* 37 */       v = Math.min(this.toMax, v);
/* 38 */       return v;
/*    */     } 
/* 40 */     v = Calculator.smoothMax(this.smoothRange, this.toMin, v);
/* 41 */     v = Calculator.smoothMin(this.smoothRange, v, this.toMax);
/* 42 */     return v;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 47 */     if (inputs.length == 0) this.input = null; 
/* 48 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SelectorDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */