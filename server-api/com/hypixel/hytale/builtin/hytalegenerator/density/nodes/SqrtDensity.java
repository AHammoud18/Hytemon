/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SqrtDensity
/*    */   extends Density {
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public SqrtDensity(Density input) {
/* 13 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     if (this.input == null) return 0.0D;
/*    */     
/* 20 */     double v = this.input.process(context);
/* 21 */     if (v < 0.0D) return -Math.sqrt(-v); 
/* 22 */     return Math.sqrt(v);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 27 */     if (inputs.length == 0) this.input = null; 
/* 28 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SqrtDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */