/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AxisDensity
/*    */   extends Density {
/*    */   public static final double ZERO_DELTA = 1.0E-9D;
/* 12 */   private static final Vector3d ZERO_VECTOR = new Vector3d();
/*    */   
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction distanceCurve;
/*    */   
/*    */   @Nonnull
/*    */   private final Vector3d axis;
/*    */   
/*    */   private final boolean isAnchored;
/*    */   
/*    */   public AxisDensity(@Nonnull Double2DoubleFunction distanceCurve, @Nonnull Vector3d axis, boolean isAnchored) {
/* 23 */     this.distanceCurve = distanceCurve;
/* 24 */     this.axis = axis;
/* 25 */     this.isAnchored = isAnchored;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 30 */     if (this.axis.length() == 0.0D) {
/* 31 */       return 0.0D;
/*    */     }
/* 33 */     if (this.isAnchored) {
/* 34 */       return processAnchored(context);
/*    */     }
/* 36 */     double distance = VectorUtil.distanceToLine3d(context.position, ZERO_VECTOR, this.axis);
/* 37 */     return this.distanceCurve.get(distance);
/*    */   }
/*    */   
/*    */   private double processAnchored(@Nonnull Density.Context context) {
/* 41 */     if (context == null) {
/* 42 */       return 0.0D;
/*    */     }
/* 44 */     Vector3d anchor = context.densityAnchor;
/* 45 */     if (anchor == null) {
/* 46 */       return 0.0D;
/*    */     }
/* 48 */     Vector3d position = context.position.clone().subtract(anchor);
/* 49 */     double distance = VectorUtil.distanceToLine3d(position, ZERO_VECTOR, this.axis);
/* 50 */     return this.distanceCurve.get(distance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AxisDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */