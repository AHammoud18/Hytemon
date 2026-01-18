/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
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
/*    */ public class Factory
/*    */   implements DistortedShape.Factory
/*    */ {
/*    */   @Nonnull
/*    */   public DistortedShape create(@Nonnull Vector3d origin, @Nonnull Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 75 */     double compensation = DistortedCylinderShape.getCompensationFactor(direction);
/* 76 */     double scale = DistortedCylinderShape.getHeightCompensation(compensation);
/* 77 */     startHeight *= scale;
/* 78 */     midHeight *= scale;
/* 79 */     endHeight *= scale;
/* 80 */     double maxWidth = MathUtil.maxValue(startWidth, midWidth, endWidth);
/* 81 */     double maxHeight = MathUtil.maxValue(startHeight, midHeight, endHeight);
/* 82 */     return new DistortedPipeShape(origin, direction, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, maxWidth, maxHeight, compensation, interpolation);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\DistortedPipeShape$Factory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */