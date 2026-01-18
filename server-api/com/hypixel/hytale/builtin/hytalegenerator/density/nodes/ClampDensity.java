/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ClampDensity
/*    */   extends Density {
/*    */   private final double wallA;
/*    */   private final double wallB;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public ClampDensity(double wallA, double wallB, Density input) {
/* 16 */     this.wallA = wallA;
/* 17 */     this.wallB = wallB;
/* 18 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 23 */     if (this.input == null) return 0.0D; 
/* 24 */     return Calculator.clamp(this.wallA, this.input.process(context), this.wallB);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 29 */     if (inputs.length == 0) this.input = null; 
/* 30 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\ClampDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */