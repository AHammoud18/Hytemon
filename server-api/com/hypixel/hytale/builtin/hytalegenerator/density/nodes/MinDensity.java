/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MinDensity
/*    */   extends Density {
/*    */   private Density[] inputs;
/*    */   
/*    */   public MinDensity(@Nonnull List<Density> inputs) {
/* 12 */     this.inputs = new Density[inputs.size()];
/* 13 */     inputs.toArray(this.inputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     if (this.inputs.length == 0) return 0.0D;
/*    */     
/* 20 */     double min = Double.POSITIVE_INFINITY;
/*    */     
/* 22 */     for (Density input : this.inputs) {
/* 23 */       double value = input.process(context);
/* 24 */       if (min > value)
/* 25 */         min = value; 
/*    */     } 
/* 27 */     return min;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 32 */     this.inputs = inputs;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MinDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */