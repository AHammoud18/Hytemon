/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Factory
/*    */   implements DistortedShape.Factory
/*    */ {
/*    */   public DistortedShape create(Vector3d origin, @Nonnull Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 92 */     double scale = 1.0D / direction.length();
/* 93 */     double nx = direction.x * scale;
/* 94 */     double ny = direction.y * scale;
/* 95 */     double nz = direction.z * scale;
/* 96 */     double yaw = TrigMathUtil.atan2(nx, nz);
/* 97 */     double pitch = TrigMathUtil.asin(-ny);
/* 98 */     return createShape(origin, direction, yaw, pitch, startWidth, startHeight, length, interpolation);
/*    */   }
/*    */   
/*    */   protected abstract DistortedShape createShape(Vector3d paramVector3d1, Vector3d paramVector3d2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, GeneralNoise.InterpolationFunction paramInterpolationFunction);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\AbstractDistortedBody$Factory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */