/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MixDensity
/*    */   extends Density {
/*    */   private Density densityA;
/*    */   private Density densityB;
/*    */   private Density influenceDensity;
/*    */   
/*    */   public MixDensity(@Nonnull Density densityA, @Nonnull Density densityB, @Nonnull Density influenceDensity) {
/* 13 */     this.densityA = densityA;
/* 14 */     this.densityB = densityB;
/* 15 */     this.influenceDensity = influenceDensity;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 20 */     double THRESHOLD_INPUT_A = 0.0D;
/* 21 */     double THRESHOLD_INPUT_B = 1.0D;
/*    */     
/* 23 */     double influence = this.influenceDensity.process(context);
/*    */     
/* 25 */     if (influence <= 0.0D) {
/* 26 */       return this.densityA.process(context);
/*    */     }
/* 28 */     if (influence >= 1.0D) {
/* 29 */       return this.densityB.process(context);
/*    */     }
/*    */     
/* 32 */     double valueA = this.densityA.process(context);
/* 33 */     double valueB = this.densityB.process(context);
/*    */     
/* 35 */     double mixedValue = valueA * (1.0D - influence) + valueB * influence;
/* 36 */     return mixedValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 41 */     if (inputs.length != 3) {
/* 42 */       throw new IllegalArgumentException("inputs.length != 3");
/*    */     }
/*    */     
/* 45 */     this.densityA = inputs[0];
/* 46 */     this.densityB = inputs[1];
/* 47 */     this.influenceDensity = inputs[2];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MixDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */