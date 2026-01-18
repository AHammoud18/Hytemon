/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DistortedPipeShape
/*    */   extends DistortedCylinderShape {
/*    */   private final double compensation;
/*    */   
/*    */   public DistortedPipeShape(@Nonnull Vector3d o, @Nonnull Vector3d v, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, double maxWidth, double maxHeight, double compensation, GeneralNoise.InterpolationFunction interpolation) {
/* 13 */     super(o, v, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, maxWidth, maxHeight, interpolation);
/* 14 */     this.compensation = compensation;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getWidthAt(double t) {
/* 19 */     return getCompensatedDim(t, this.startWidth, this.midWidth, this.endWidth, this.compensation, this.interpolation);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getHeightAt(double t) {
/* 24 */     return getCompensatedDim(t, this.startHeight, this.midHeight, this.endHeight, this.compensation, this.interpolation);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidProjection(double t) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "DistortedPipeShape{origin=" + String.valueOf(this.o) + ", direction=" + String.valueOf(this.v) + ", startWidth=" + this.startWidth + ", startHeight=" + this.startHeight + ", midWidth=" + this.midWidth + ", midHeight=" + this.midHeight + ", endWidth=" + this.endWidth + ", endHeight=" + this.endHeight + "}";
/*    */   }
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
/*    */   protected static double getCompensatedDim(double t, double startDim, double midDim, double endDim, double compensation, @Nonnull GeneralNoise.InterpolationFunction interpolation) {
/* 48 */     if (t <= 0.0D) {
/*    */       
/* 50 */       double fade = 1.0D - MathUtil.clamp(t, -0.5D, 0.0D) * -2.0D;
/* 51 */       fade = interpolation.interpolate(fade);
/* 52 */       return MathUtil.lerp(startDim, startDim * fade, compensation);
/* 53 */     }  if (t >= 1.0D) {
/*    */       
/* 55 */       double fade = 1.0D - MathUtil.clamp(t - 1.0D, 0.0D, 0.5D) * 2.0D;
/* 56 */       fade = interpolation.interpolate(fade);
/* 57 */       return MathUtil.lerp(endDim, endDim * fade, compensation);
/* 58 */     }  if (t <= 0.5D) {
/*    */       
/* 60 */       t = interpolation.interpolate(t * 2.0D);
/*    */       
/* 62 */       return MathUtil.lerpUnclamped(startDim, midDim, t);
/*    */     } 
/*    */     
/* 65 */     t = interpolation.interpolate((t - 0.5D) * 2.0D);
/*    */     
/* 67 */     return MathUtil.lerpUnclamped(midDim, endDim, t);
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements DistortedShape.Factory
/*    */   {
/*    */     @Nonnull
/*    */     public DistortedShape create(@Nonnull Vector3d origin, @Nonnull Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 75 */       double compensation = DistortedCylinderShape.getCompensationFactor(direction);
/* 76 */       double scale = DistortedCylinderShape.getHeightCompensation(compensation);
/* 77 */       startHeight *= scale;
/* 78 */       midHeight *= scale;
/* 79 */       endHeight *= scale;
/* 80 */       double maxWidth = MathUtil.maxValue(startWidth, midWidth, endWidth);
/* 81 */       double maxHeight = MathUtil.maxValue(startHeight, midHeight, endHeight);
/* 82 */       return new DistortedPipeShape(origin, direction, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, maxWidth, maxHeight, compensation, interpolation);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\DistortedPipeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */