/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Normalizer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class NormalizerDensity
/*    */   extends Density {
/*    */   private final double fromMin;
/*    */   private final double fromMax;
/*    */   private final double toMin;
/*    */   private final double toMax;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public NormalizerDensity(double fromMin, double fromMax, double toMin, double toMax, Density input) {
/* 18 */     if (fromMin > fromMax || toMin > toMax)
/* 19 */       throw new IllegalArgumentException("min larger than max"); 
/* 20 */     this.fromMin = fromMin;
/* 21 */     this.fromMax = fromMax;
/* 22 */     this.toMin = toMin;
/* 23 */     this.toMax = toMax;
/* 24 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 29 */     return Normalizer.normalize(this.fromMin, this.fromMax, this.toMin, this.toMax, this.input.process(context));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 34 */     if (inputs.length == 0) this.input = null; 
/* 35 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\NormalizerDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */