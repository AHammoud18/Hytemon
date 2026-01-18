/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.NodeFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AmplitudeDensity
/*    */   extends Density
/*    */ {
/*    */   public static final double ZERO_DELTA = 1.0E-9D;
/*    */   private NodeFunction amplitudeFunc;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public AmplitudeDensity(@Nonnull NodeFunction offsetFunction, Density input) {
/* 17 */     this.amplitudeFunc = offsetFunction;
/* 18 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 23 */     if (this.input == null || skipInputs(context.position.y)) return 0.0D; 
/* 24 */     return this.input.process(context) * this.amplitudeFunc.get(context.position.y);
/*    */   }
/*    */   
/*    */   public boolean skipInputs(double y) {
/* 28 */     double v = this.amplitudeFunc.get(y);
/* 29 */     return (v < 1.0E-9D && v > -1.0E-9D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 34 */     if (inputs.length == 0) this.input = null; 
/* 35 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AmplitudeDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */