/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ManhattanDistanceFunction
/*    */   extends DistanceFunction
/*    */ {
/*    */   public double getDistance(@Nonnull Vector3d point) {
/* 11 */     return Math.abs(point.x) + Math.abs(point.y) + Math.abs(point.z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\distancefunctions\ManhattanDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */