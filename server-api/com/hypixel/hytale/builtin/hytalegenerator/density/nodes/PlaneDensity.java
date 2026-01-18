/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PlaneDensity
/*    */   extends Density {
/*    */   public static final double ZERO_DELTA = 1.0E-9D;
/* 13 */   private static final Vector3d ZERO_VECTOR = new Vector3d();
/*    */   
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction distanceCurve;
/*    */   
/*    */   @Nonnull
/*    */   private final Vector3d planeNormal;
/*    */   
/*    */   private final boolean isPlaneHorizontal;
/*    */   private final boolean isAnchored;
/*    */   
/*    */   public PlaneDensity(@Nonnull Double2DoubleFunction distanceCurve, @Nonnull Vector3d planeNormal, boolean isAnchored) {
/* 25 */     this.distanceCurve = distanceCurve;
/* 26 */     this.planeNormal = planeNormal;
/* 27 */     this.isPlaneHorizontal = (planeNormal.x == 0.0D && planeNormal.z == 0.0D);
/* 28 */     this.isAnchored = isAnchored;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 33 */     if (this.planeNormal.length() == 0.0D) {
/* 34 */       return 0.0D;
/*    */     }
/* 36 */     if (this.isAnchored) {
/* 37 */       return processAnchored(context.position.x, context.position.y, context.position.z, context);
/*    */     }
/* 39 */     double distance = 0.0D;
/* 40 */     if (this.isPlaneHorizontal) {
/* 41 */       distance = context.position.y;
/*    */     } else {
/*    */       
/* 44 */       Vector3d nearestPoint = VectorUtil.nearestPointOnLine3d(context.position, ZERO_VECTOR, this.planeNormal);
/* 45 */       distance = nearestPoint.length();
/*    */     } 
/*    */     
/* 48 */     return this.distanceCurve.get(distance);
/*    */   }
/*    */   
/*    */   private double processAnchored(double x, double y, double z, @Nullable Density.Context context) {
/* 52 */     if (context == null) {
/* 53 */       return 0.0D;
/*    */     }
/* 55 */     Vector3d position = new Vector3d(x, y, z);
/*    */ 
/*    */     
/* 58 */     Vector3d p0 = context.densityAnchor;
/* 59 */     if (p0 == null) {
/* 60 */       return 0.0D;
/*    */     }
/* 62 */     double distance = 0.0D;
/* 63 */     if (this.isPlaneHorizontal)
/*    */     {
/* 65 */       distance = Math.abs(p0.y - position.y);
/*    */     }
/*    */     
/* 68 */     Vector3d pos = position.clone().addScaled(p0, -1.0D);
/*    */     
/* 70 */     Vector3d vectorFromPlane = VectorUtil.nearestPointOnLine3d(pos, ZERO_VECTOR, this.planeNormal);
/* 71 */     distance = vectorFromPlane.length();
/*    */     
/* 73 */     return this.distanceCurve.get(distance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\PlaneDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */