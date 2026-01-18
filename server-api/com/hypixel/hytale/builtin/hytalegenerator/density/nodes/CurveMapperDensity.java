/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CurveMapperDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction curveFunction;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public CurveMapperDensity(@Nonnull Double2DoubleFunction curveFunction, Density input) {
/* 16 */     this.curveFunction = curveFunction;
/* 17 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 22 */     if (this.input == null) {
/* 23 */       return this.curveFunction.get(0.0D);
/*    */     }
/* 25 */     return this.curveFunction.applyAsDouble(this.input.process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 30 */     if (inputs.length == 0) this.input = null; 
/* 31 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\CurveMapperDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */