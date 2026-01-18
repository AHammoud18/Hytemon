/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DistanceDensity
/*    */   extends Density
/*    */ {
/*    */   public static final double ZERO_DELTA = 1.0E-9D;
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction falloffFunction;
/*    */   
/*    */   public DistanceDensity(@Nonnull Double2DoubleFunction falloffFunction) {
/* 16 */     this.falloffFunction = falloffFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 21 */     double distance = Calculator.distance(context.position.x, context.position.y, context.position.z, 0.0D, 0.0D, 0.0D);
/* 22 */     return this.falloffFunction.get(distance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\DistanceDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */