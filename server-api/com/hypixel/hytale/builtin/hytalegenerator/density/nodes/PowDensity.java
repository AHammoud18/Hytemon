/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PowDensity
/*    */   extends Density {
/*    */   private final double exponent;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public PowDensity(double exponent, Density input) {
/* 14 */     this.exponent = exponent;
/* 15 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 20 */     if (this.input == null) return 0.0D; 
/* 21 */     double value = this.input.process(context);
/* 22 */     if (value < 0.0D) {
/* 23 */       return -Math.pow(-value, this.exponent);
/*    */     }
/* 25 */     return Math.pow(value, this.exponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 30 */     if (inputs.length == 0) this.input = null; 
/* 31 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\PowDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */