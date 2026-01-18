/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CeilingDensity
/*    */   extends Density {
/*    */   private double limit;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public CeilingDensity(double limit, Density input) {
/* 14 */     this.limit = limit;
/* 15 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 20 */     if (this.input == null) return 0.0D;
/*    */     
/* 22 */     return Math.min(this.input.process(context), this.limit);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 27 */     if (inputs.length == 0) this.input = null; 
/* 28 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\CeilingDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */