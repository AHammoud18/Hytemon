/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class AbstractDistortedExtrusion
/*    */   extends AbstractDistortedShape
/*    */ {
/*    */   protected final GeneralNoise.InterpolationFunction interpolation;
/*    */   
/*    */   public AbstractDistortedExtrusion(@Nonnull Vector3d o, @Nonnull Vector3d v, double width, double height, GeneralNoise.InterpolationFunction interpolation) {
/* 15 */     super(o, v, width, height);
/* 16 */     this.interpolation = interpolation;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract double getDistanceSq(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   protected abstract double getHeightComponent(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   public double getHeightAtProjection(int seed, double x, double z, double t, double centerY, @Nonnull CaveType caveType, @Nonnull ShapeDistortion distortion) {
/* 25 */     double width = getWidthAt(t);
/*    */ 
/*    */     
/* 28 */     width *= caveType.getHeightRadiusFactor(seed, x, z, MathUtil.floor(centerY));
/*    */ 
/*    */     
/* 31 */     double dist2 = getDistanceSq(x, z, t);
/* 32 */     double width2 = width * width;
/*    */     
/* 34 */     if (dist2 > width2)
/*    */     {
/* 36 */       return 0.0D;
/*    */     }
/*    */ 
/*    */     
/* 40 */     width *= distortion.getWidthFactor(seed, x, z);
/* 41 */     width2 = width * width;
/*    */     
/* 43 */     if (dist2 > width2)
/*    */     {
/* 45 */       return 0.0D;
/*    */     }
/*    */ 
/*    */     
/* 49 */     double height = getHeightAt(t);
/* 50 */     if (height == 0.0D) {
/* 51 */       return 0.0D;
/*    */     }
/*    */ 
/*    */     
/* 55 */     double alpha = getHeightComponent(width, width2, dist2);
/* 56 */     alpha = MathUtil.clamp(alpha, 0.0D, 1.0D);
/*    */ 
/*    */     
/* 59 */     alpha = this.interpolation.interpolate(alpha);
/*    */     
/* 61 */     return height * alpha;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\AbstractDistortedExtrusion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */