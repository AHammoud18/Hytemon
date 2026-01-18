/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class ReturnType
/*    */ {
/* 10 */   protected double maxDistance = Double.MAX_VALUE;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double get(double paramDouble1, double paramDouble2, @Nonnull Vector3d paramVector3d1, @Nullable Vector3d paramVector3d2, @Nullable Vector3d paramVector3d3, @Nullable Density.Context paramContext);
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMaxDistance(double maxDistance) {
/* 20 */     if (maxDistance < 0.0D)
/* 21 */       throw new IllegalArgumentException("negative distance"); 
/* 22 */     this.maxDistance = maxDistance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\ReturnType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */