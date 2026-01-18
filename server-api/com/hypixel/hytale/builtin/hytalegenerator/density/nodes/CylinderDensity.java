/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CylinderDensity
/*    */   extends Density
/*    */ {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction radialCurve;
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction axialCurve;
/*    */   
/*    */   public CylinderDensity(@Nonnull Double2DoubleFunction radialCurve, @Nonnull Double2DoubleFunction axialCurve) {
/* 17 */     this.radialCurve = radialCurve;
/* 18 */     this.axialCurve = axialCurve;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 23 */     double radialDistance = Calculator.distance(context.position.x, context.position.z, 0.0D, 0.0D);
/* 24 */     return this.axialCurve.applyAsDouble(context.position.y) * this.radialCurve.applyAsDouble(radialDistance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\CylinderDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */