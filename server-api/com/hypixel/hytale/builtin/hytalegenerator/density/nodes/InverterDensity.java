/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class InverterDensity
/*    */   extends Density {
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public InverterDensity(Density input) {
/* 13 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     if (this.input == null) return 0.0D; 
/* 19 */     return -this.input.process(context);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 24 */     if (inputs.length == 0) this.input = null; 
/* 25 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\InverterDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */