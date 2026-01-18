/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MaxDensity
/*    */   extends Density {
/*    */   public Density[] inputs;
/*    */   
/*    */   public MaxDensity(@Nonnull List<Density> inputs) {
/* 12 */     this.inputs = new Density[inputs.size()];
/* 13 */     inputs.toArray(this.inputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     if (this.inputs.length == 0) return 0.0D;
/*    */     
/* 20 */     double max = Double.NEGATIVE_INFINITY;
/*    */     
/* 22 */     for (Density input : this.inputs) {
/* 23 */       double value = input.process(context);
/* 24 */       if (max < value)
/* 25 */         max = value; 
/*    */     } 
/* 27 */     return max;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 32 */     this.inputs = inputs;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MaxDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */