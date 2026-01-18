/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SmoothCeilingDensity
/*    */   extends Density {
/*    */   private double limit;
/*    */   private double smoothRange;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public SmoothCeilingDensity(double limit, double smoothRange, Density input) {
/* 16 */     if (smoothRange < 0.0D)
/* 17 */       throw new IllegalArgumentException(); 
/* 18 */     this.limit = limit;
/* 19 */     this.smoothRange = smoothRange;
/* 20 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 25 */     if (this.input == null) return 0.0D;
/*    */     
/* 27 */     return Calculator.smoothMin(this.smoothRange, this.input.process(context), this.limit);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 32 */     if (inputs.length == 0) this.input = null; 
/* 33 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SmoothCeilingDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */