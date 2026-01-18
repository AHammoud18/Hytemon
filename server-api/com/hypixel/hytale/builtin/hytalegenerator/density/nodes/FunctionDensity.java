/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FunctionDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction function;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public FunctionDensity(@Nonnull Double2DoubleFunction function, Density input) {
/* 16 */     this.function = function;
/* 17 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 22 */     if (this.input == null) return 0.0D; 
/* 23 */     return this.function.get(this.input.process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 28 */     if (inputs.length == 0) this.input = null; 
/* 29 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\FunctionDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */