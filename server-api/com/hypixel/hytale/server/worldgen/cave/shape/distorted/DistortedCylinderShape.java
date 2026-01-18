/*     */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShapeUtils;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistortedCylinderShape
/*     */   extends AbstractDistortedExtrusion
/*     */ {
/*     */   protected static final double PITCH_COMPENSATION_MIN = 1.0D;
/*     */   protected static final double PITCH_COMPENSATION_RANGE = 3.0D;
/*     */   @Nonnull
/*     */   protected final Vector3d o;
/*     */   @Nonnull
/*     */   protected final Vector3d v;
/*     */   protected final double startWidth;
/*     */   
/*     */   public DistortedCylinderShape(@Nonnull Vector3d o, @Nonnull Vector3d v, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/*  24 */     this(o, v, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, MathUtil.maxValue(startWidth, midWidth, endWidth), MathUtil.maxValue(startHeight, midHeight, endHeight), interpolation);
/*     */   }
/*     */   protected final double startHeight; protected final double midWidth; protected final double midHeight; protected final double endWidth; protected final double endHeight;
/*     */   public DistortedCylinderShape(@Nonnull Vector3d o, @Nonnull Vector3d v, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, double maxWidth, double maxHeight, GeneralNoise.InterpolationFunction interpolation) {
/*  28 */     super(o, v, maxWidth, maxHeight, interpolation);
/*  29 */     this.o = o;
/*  30 */     this.v = v;
/*  31 */     this.startWidth = startWidth;
/*  32 */     this.startHeight = startHeight;
/*  33 */     this.midWidth = midWidth;
/*  34 */     this.midHeight = midHeight;
/*  35 */     this.endWidth = endWidth;
/*  36 */     this.endHeight = endHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  42 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  49 */     double x = this.o.x + this.v.x;
/*  50 */     double y = this.o.y + this.v.y;
/*  51 */     double z = this.o.z + this.v.z;
/*  52 */     return new Vector3d(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double t, double tv, double th) {
/*  58 */     double radiusY = getHeightAt(t);
/*  59 */     double radiusXZ = getWidthAt(t);
/*  60 */     return CaveNodeShapeUtils.getPipeAnchor(vector, this.o, this.v, radiusXZ, radiusY, radiusXZ, t, tv, th);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getProjection(double x, double z) {
/*  65 */     double t = (x - this.o.x) * this.v.x + (z - this.o.z) * this.v.z;
/*  66 */     t /= this.v.x * this.v.x + this.v.z * this.v.z;
/*  67 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidProjection(double t) {
/*  72 */     return (t > 0.0D && t < 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getYAt(double t) {
/*  77 */     return this.o.y + this.v.y * t;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWidthAt(double t) {
/*  82 */     return getDimAt(t, this.startWidth, this.midWidth, this.endWidth, this.interpolation);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeightAt(double t) {
/*  87 */     return getDimAt(t, this.startHeight, this.midHeight, this.endHeight, this.interpolation);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistanceSq(double x, double z, double t) {
/*  92 */     if (t <= 0.0D) {
/*     */       
/*  94 */       x -= this.o.x;
/*  95 */       z -= this.o.z;
/*  96 */     } else if (t >= 1.0D) {
/*     */       
/*  98 */       x -= this.o.x + this.v.x;
/*  99 */       z -= this.o.z + this.v.z;
/*     */     } else {
/*     */       
/* 102 */       x -= this.o.x + this.v.x * t;
/* 103 */       z -= this.o.z + this.v.z * t;
/*     */     } 
/* 105 */     return x * x + z * z;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeightComponent(double width, double width2, double dist2) {
/* 110 */     return Math.sqrt(width2 - dist2) / width;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 116 */     return "DistortedCylinderShape{origin=" + String.valueOf(this.o) + ", direction=" + String.valueOf(this.v) + ", startWidth=" + this.startWidth + ", startHeight=" + this.startHeight + ", midWidth=" + this.midWidth + ", midHeight=" + this.midHeight + ", endWidth=" + this.endWidth + ", endHeight=" + this.endHeight + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double getDimAt(double t, double startDim, double midDim, double endDim, @Nonnull GeneralNoise.InterpolationFunction interpolation) {
/* 139 */     if (t <= 0.0D)
/* 140 */       return startDim; 
/* 141 */     if (t >= 1.0D)
/* 142 */       return endDim; 
/* 143 */     if (t <= 0.5D) {
/*     */       
/* 145 */       t = interpolation.interpolate(t * 2.0D);
/*     */       
/* 147 */       return MathUtil.lerpUnclamped(startDim, midDim, t);
/*     */     } 
/*     */     
/* 150 */     t = interpolation.interpolate((t - 0.5D) * 2.0D);
/*     */     
/* 152 */     return MathUtil.lerpUnclamped(midDim, endDim, t);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static double getCompensationFactor(@Nonnull Vector3d direction) {
/* 157 */     double ny = direction.y / direction.length();
/* 158 */     double pitch = TrigMathUtil.asin(-ny);
/* 159 */     return Math.abs(pitch) / 1.5707963705062866D;
/*     */   }
/*     */   
/*     */   protected static double getHeightCompensation(double factor) {
/* 163 */     return 1.0D + factor * factor * factor * 3.0D;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements DistortedShape.Factory {
/*     */     @Nonnull
/*     */     public DistortedShape create(@Nonnull Vector3d origin, @Nonnull Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 170 */       double comp = DistortedCylinderShape.getCompensationFactor(direction);
/* 171 */       double scale = DistortedCylinderShape.getHeightCompensation(comp);
/* 172 */       startHeight *= scale;
/* 173 */       midHeight *= scale;
/* 174 */       endHeight *= scale;
/* 175 */       return new DistortedCylinderShape(origin, direction, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, interpolation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\DistortedCylinderShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */