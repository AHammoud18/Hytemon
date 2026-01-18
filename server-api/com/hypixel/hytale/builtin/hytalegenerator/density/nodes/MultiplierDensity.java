/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MultiplierDensity
/*    */   extends Density {
/*    */   private Density[] inputs;
/*    */   
/*    */   public MultiplierDensity(@Nonnull List<Density> inputs) {
/* 12 */     this.inputs = new Density[inputs.size()];
/* 13 */     inputs.toArray(this.inputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     double multiply = (this.inputs.length == 0) ? 0.0D : 1.0D;
/* 19 */     for (Density input : this.inputs) {
/* 20 */       multiply *= input.process(context);
/* 21 */       if (multiply == 0.0D) return 0.0D; 
/*    */     } 
/* 23 */     return multiply;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 28 */     this.inputs = inputs;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MultiplierDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */