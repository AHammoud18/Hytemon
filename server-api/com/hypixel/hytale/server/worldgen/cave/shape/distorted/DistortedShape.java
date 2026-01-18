/*     */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
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
/*     */ public interface DistortedShape
/*     */   extends IWorldBounds
/*     */ {
/*     */   Vector3d getStart();
/*     */   
/*     */   Vector3d getEnd();
/*     */   
/*     */   Vector3d getAnchor(Vector3d paramVector3d, double paramDouble1, double paramDouble2, double paramDouble3);
/*     */   
/*     */   default boolean hasGeometry() {
/*  35 */     return (getHighBoundX() > getLowBoundX() && getHighBoundY() > getLowBoundY() && getHighBoundZ() > getLowBoundZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getProjection(double paramDouble1, double paramDouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isValidProjection(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getYAt(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getWidthAt(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getHeightAt(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double getHeightAtProjection(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, CaveType paramCaveType, ShapeDistortion paramShapeDistortion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double getCeiling(double x, double z, double centerY, double height) {
/*  94 */     return centerY + height;
/*     */   }
/*     */   
/*     */   default double getFloor(double x, double z, double centerY, double height) {
/*  98 */     return centerY - height;
/*     */   }
/*     */   
/*     */   public static interface Factory
/*     */   {
/* 103 */     public static final GeneralNoise.InterpolationFunction DEFAULT_INTERPOLATION = GeneralNoise.InterpolationMode.LINEAR.function;
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
/*     */     DistortedShape create(Vector3d param1Vector3d1, Vector3d param1Vector3d2, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, GeneralNoise.InterpolationFunction param1InterpolationFunction);
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
/*     */     default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight) {
/* 126 */       return create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, DEFAULT_INTERPOLATION);
/*     */     }
/*     */     
/*     */     default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 130 */       double midWidth = (startWidth + endWidth) * 0.5D;
/* 131 */       double midHeight = (startHeight + endHeight) * 0.5D;
/* 132 */       return create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, interpolation);
/*     */     }
/*     */     
/*     */     default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double endWidth, double endHeight) {
/* 136 */       return create(origin, direction, length, startWidth, startHeight, endWidth, endHeight, DEFAULT_INTERPOLATION);
/*     */     }
/*     */     
/*     */     default DistortedShape create(Vector3d origin, Vector3d direction, double length, double width, double height, GeneralNoise.InterpolationFunction interpolation) {
/* 140 */       return create(origin, direction, length, width, height, width, height, width, height, interpolation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\DistortedShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */