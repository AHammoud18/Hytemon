/*     */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
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
/*     */ public interface Factory
/*     */ {
/* 103 */   public static final GeneralNoise.InterpolationFunction DEFAULT_INTERPOLATION = GeneralNoise.InterpolationMode.LINEAR.function;
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
/*     */   DistortedShape create(Vector3d paramVector3d1, Vector3d paramVector3d2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, GeneralNoise.InterpolationFunction paramInterpolationFunction);
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
/*     */   default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight) {
/* 126 */     return create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, DEFAULT_INTERPOLATION);
/*     */   }
/*     */   
/*     */   default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 130 */     double midWidth = (startWidth + endWidth) * 0.5D;
/* 131 */     double midHeight = (startHeight + endHeight) * 0.5D;
/* 132 */     return create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, interpolation);
/*     */   }
/*     */   
/*     */   default DistortedShape create(Vector3d origin, Vector3d direction, double length, double startWidth, double startHeight, double endWidth, double endHeight) {
/* 136 */     return create(origin, direction, length, startWidth, startHeight, endWidth, endHeight, DEFAULT_INTERPOLATION);
/*     */   }
/*     */   
/*     */   default DistortedShape create(Vector3d origin, Vector3d direction, double length, double width, double height, GeneralNoise.InterpolationFunction interpolation) {
/* 140 */     return create(origin, direction, length, width, height, width, height, width, height, interpolation);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\DistortedShape$Factory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */