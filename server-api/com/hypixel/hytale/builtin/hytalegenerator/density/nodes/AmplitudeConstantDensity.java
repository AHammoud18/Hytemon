/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AmplitudeConstantDensity
/*    */   extends Density {
/*    */   private final double amplitude;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public AmplitudeConstantDensity(double amplitude, Density input) {
/* 14 */     this.amplitude = amplitude;
/* 15 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 20 */     if (this.input == null) return 0.0D; 
/* 21 */     return this.input.process(context) * this.amplitude;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 26 */     if (inputs.length == 0) this.input = null; 
/* 27 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AmplitudeConstantDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */