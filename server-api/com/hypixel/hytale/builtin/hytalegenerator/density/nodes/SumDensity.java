/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SumDensity
/*    */   extends Density {
/*    */   private Density[] inputs;
/*    */   
/*    */   public SumDensity(@Nonnull List<Density> inputs) {
/* 13 */     this.inputs = new Density[inputs.size()];
/* 14 */     inputs.toArray(this.inputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 19 */     if (this.inputs.length == 0) return 0.0D;
/*    */     
/* 21 */     double sum = 0.0D;
/* 22 */     for (Density input : this.inputs) sum += input.process(context); 
/* 23 */     return sum;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 28 */     this.inputs = Arrays.<Density>copyOf(inputs, inputs.length);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SumDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */