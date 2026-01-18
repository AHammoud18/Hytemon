/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SmoothMaxDensity
/*    */   extends Density
/*    */ {
/*    */   private final double range;
/*    */   @Nullable
/*    */   private Density inputA;
/*    */   @Nullable
/*    */   private Density inputB;
/*    */   
/*    */   public SmoothMaxDensity(double range, Density inputA, Density inputB) {
/* 18 */     this.range = range;
/* 19 */     this.inputA = inputA;
/* 20 */     this.inputB = inputB;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 25 */     if (this.inputA == null || this.inputB == null) return 0.0D; 
/* 26 */     return Calculator.smoothMax(this.range, this.inputA
/* 27 */         .process(context), this.inputB
/* 28 */         .process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 33 */     if (inputs.length < 2) {
/* 34 */       this.inputA = null;
/* 35 */       this.inputB = null;
/*    */     } 
/* 37 */     this.inputA = inputs[0];
/* 38 */     this.inputB = inputs[1];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SmoothMaxDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */