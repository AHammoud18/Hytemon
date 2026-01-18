/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistanceReturnType
/*    */   extends ReturnType
/*    */ {
/*    */   public double get(double distance0, double distance1, @Nonnull Vector3d samplePosition, @Nullable Vector3d closestPoint0, Vector3d closestPoint1, @Nullable Density.Context context) {
/* 17 */     if (this.maxDistance <= 0.0D) return 0.0D; 
/* 18 */     if (closestPoint0 == null) return 1.0D; 
/* 19 */     return distance0 / this.maxDistance * 2.0D - 1.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\DistanceReturnType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */