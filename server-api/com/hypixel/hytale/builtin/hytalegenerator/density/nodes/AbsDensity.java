/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AbsDensity
/*    */   extends Density
/*    */ {
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public AbsDensity(Density input) {
/* 14 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 19 */     if (this.input == null) return 0.0D; 
/* 20 */     return Math.abs(this.input.process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 25 */     if (inputs.length == 0) this.input = null; 
/* 26 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AbsDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */