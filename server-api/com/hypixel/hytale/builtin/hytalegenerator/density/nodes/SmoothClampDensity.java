/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SmoothClampDensity
/*    */   extends Density {
/*    */   private final double max;
/*    */   private final double min;
/*    */   private final double range;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public SmoothClampDensity(double min, double max, double range, Density input) {
/* 17 */     if (range <= 0.0D) throw new IllegalArgumentException("invalid range + range"); 
/* 18 */     if (max < min) throw new IllegalArgumentException("max smaller than min");
/*    */     
/* 20 */     this.max = max;
/* 21 */     this.min = min;
/* 22 */     this.range = range;
/* 23 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 28 */     if (this.input == null) return 0.0D;
/*    */     
/* 30 */     double value = this.input.process(context);
/* 31 */     value = Calculator.smoothMin(this.range, this.max, value);
/* 32 */     value = Calculator.smoothMax(this.range, this.min, value);
/* 33 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 38 */     if (inputs.length == 0) this.input = null; 
/* 39 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SmoothClampDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */