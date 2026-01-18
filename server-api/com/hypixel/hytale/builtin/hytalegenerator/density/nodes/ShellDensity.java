/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShellDensity
/*    */   extends Density
/*    */ {
/*    */   public static final double ZERO_DELTA = 1.0E-9D;
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction angleCurve;
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction distanceCurve;
/*    */   @Nonnull
/*    */   private final Vector3d axis;
/*    */   private final boolean isMirrored;
/*    */   
/*    */   public ShellDensity(@Nonnull Double2DoubleFunction angleCurve, @Nonnull Double2DoubleFunction distanceCurve, @Nonnull Vector3d axis, boolean isMirrored) {
/* 26 */     this.angleCurve = angleCurve;
/* 27 */     this.distanceCurve = distanceCurve;
/* 28 */     this.axis = axis;
/* 29 */     this.isMirrored = isMirrored;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 34 */     double distance = Calculator.distance(context.position, Vector3d.ZERO);
/* 35 */     if (this.axis.length() == 0.0D) {
/* 36 */       return 0.0D;
/*    */     }
/* 38 */     Vector3d radialVector = context.position.clone();
/*    */     
/* 40 */     double amplitude = this.distanceCurve.applyAsDouble(distance);
/* 41 */     if (amplitude == 0.0D)
/* 42 */       return 0.0D; 
/* 43 */     if (radialVector.length() <= 1.0E-9D) {
/* 44 */       return amplitude;
/*    */     }
/* 46 */     double angle = VectorUtil.angle(radialVector, this.axis);
/*    */ 
/*    */     
/* 49 */     angle /= Math.PI;
/* 50 */     angle *= 180.0D;
/*    */     
/* 52 */     if (this.isMirrored && angle > 90.0D) {
/* 53 */       angle = 180.0D - angle;
/*    */     }
/* 55 */     return amplitude * this.angleCurve.applyAsDouble(angle);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\ShellDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */