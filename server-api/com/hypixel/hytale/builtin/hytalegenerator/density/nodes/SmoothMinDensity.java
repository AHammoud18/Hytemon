/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SmoothMinDensity
/*    */   extends Density
/*    */ {
/*    */   private final double range;
/*    */   @Nullable
/*    */   private Density inputA;
/*    */   @Nullable
/*    */   private Density inputB;
/*    */   
/*    */   public SmoothMinDensity(double range, Density inputA, Density inputB) {
/* 18 */     this.range = range;
/* 19 */     this.inputA = inputA;
/* 20 */     this.inputB = inputB;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 25 */     if (this.inputA == null || this.inputB == null) return 0.0D; 
/* 26 */     return Calculator.smoothMin(this.range, this.inputA.process(context), this.inputB.process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 31 */     if (inputs.length < 2) {
/* 32 */       this.inputA = null;
/* 33 */       this.inputB = null;
/*    */     } 
/* 35 */     this.inputA = inputs[0];
/* 36 */     this.inputB = inputs[1];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SmoothMinDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */