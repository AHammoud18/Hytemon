/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CurveReturnType
/*    */   extends ReturnType {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction curve;
/*    */   
/*    */   public CurveReturnType(@Nonnull Double2DoubleFunction curve) {
/* 15 */     this.curve = curve;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double get(double distance0, double distance1, @Nonnull Vector3d samplePosition, @Nullable Vector3d closestPoint0, Vector3d closestPoint1, @Nullable Density.Context context) {
/* 26 */     return this.curve.get(distance0);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\CurveReturnType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */