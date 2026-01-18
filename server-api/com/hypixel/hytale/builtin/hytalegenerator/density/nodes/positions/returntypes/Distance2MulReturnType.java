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
/*    */ 
/*    */ 
/*    */ public class Distance2MulReturnType
/*    */   extends ReturnType
/*    */ {
/*    */   public double get(double distance0, double distance1, @Nonnull Vector3d samplePosition, @Nullable Vector3d closestPoint0, Vector3d closestPoint1, @Nullable Density.Context context) {
/* 19 */     if (this.maxDistance <= 0.0D) return 0.0D; 
/* 20 */     if (closestPoint0 == null) return 1.0D; 
/* 21 */     distance0 /= this.maxDistance;
/* 22 */     distance1 /= this.maxDistance;
/* 23 */     return distance1 * distance0 * 2.0D - 1.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\Distance2MulReturnType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */