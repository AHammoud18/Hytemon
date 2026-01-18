/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.AbstractDistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.ShapeDistortion;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class DistortedCaveNodeShapeGenerator
/*     */   implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */ {
/*     */   private final DistortedShape.Factory shapeFactory;
/*     */   private final IDoubleRange widthRange;
/*     */   private final IDoubleRange midWidthRange;
/*     */   private final IDoubleRange heightRange;
/*     */   private final IDoubleRange midHeightRange;
/*     */   private final IDoubleRange lengthRange;
/*     */   private final ShapeDistortion distortion;
/*     */   private final boolean inheritParentRadius;
/*     */   private final GeneralNoise.InterpolationFunction interpolation;
/*     */   
/*     */   public DistortedCaveNodeShapeGenerator(DistortedShape.Factory shapeFactory, IDoubleRange widthRange, IDoubleRange heightRange, @Nullable IDoubleRange midWidthRange, @Nullable IDoubleRange midHeightRange, @Nullable IDoubleRange lengthRange, boolean inheritParentRadius, ShapeDistortion distortion, @Nullable GeneralNoise.InterpolationFunction interpolation) {
/* 275 */     this.shapeFactory = shapeFactory;
/* 276 */     this.widthRange = widthRange;
/* 277 */     this.heightRange = heightRange;
/* 278 */     this.midWidthRange = midWidthRange;
/* 279 */     this.midHeightRange = midHeightRange;
/* 280 */     this.lengthRange = lengthRange;
/* 281 */     this.distortion = distortion;
/* 282 */     this.inheritParentRadius = inheritParentRadius;
/* 283 */     this.interpolation = interpolation;
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
/*     */   @Nonnull
/*     */   public CaveNodeShape generateCaveNodeShape(Random random, CaveType caveType, @Nullable CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d position, float yaw, float pitch) {
/* 296 */     double length = getLength(this.lengthRange, random);
/* 297 */     Vector3d origin = getOrigin(position, parentNode, childEntry);
/* 298 */     Vector3d direction = getDirection(yaw, pitch, length);
/*     */     
/* 300 */     double startWidth = getStartWidth(this.inheritParentRadius, parentNode, this.widthRange, random);
/* 301 */     double startHeight = getStartHeight(this.inheritParentRadius, parentNode, this.heightRange, random);
/* 302 */     double endWidth = this.widthRange.getValue(random);
/* 303 */     double endHeight = this.heightRange.getValue(random);
/* 304 */     double midWidth = getMiddleRadius(startWidth, endWidth, this.midWidthRange, random);
/* 305 */     double midHeight = getMiddleRadius(startHeight, endHeight, this.midHeightRange, random);
/*     */     
/* 307 */     DistortedShape shape = this.shapeFactory.create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, this.interpolation);
/*     */     
/* 309 */     return new DistortedCaveNodeShape(caveType, shape, this.distortion);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static Vector3d getOrigin(@Nonnull Vector3d origin, @Nullable CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry) {
/* 314 */     if (parentNode == null) {
/* 315 */       return origin;
/*     */     }
/*     */     
/* 318 */     Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 319 */     origin.add(offset);
/*     */     
/* 321 */     return origin.add(offset);
/*     */   }
/*     */   
/*     */   private static double getLength(@Nullable IDoubleRange lengthRange, Random random) {
/* 325 */     if (lengthRange == null) {
/* 326 */       return 0.0D;
/*     */     }
/* 328 */     return lengthRange.getValue(random);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static Vector3d getDirection(double yaw, double pitch, double length) {
/* 333 */     if (length == 0.0D) {
/* 334 */       return Vector3d.ZERO;
/*     */     }
/*     */     
/* 337 */     pitch = AbstractDistortedShape.clampPitch(pitch);
/*     */     
/* 339 */     return (new Vector3d((
/* 340 */         TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), 
/* 341 */         TrigMathUtil.cos(pitch), (
/* 342 */         TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw))))
/* 343 */       .scale(length);
/*     */   }
/*     */   
/*     */   private static double getStartWidth(boolean inheritParentRadius, @Nullable CaveNode parentNode, @Nonnull IDoubleRange fallback, Random random) {
/* 347 */     if (inheritParentRadius) {
/* 348 */       return CaveNodeShapeUtils.getEndWidth(parentNode, fallback, random);
/*     */     }
/* 350 */     return fallback.getValue(random);
/*     */   }
/*     */   
/*     */   private static double getStartHeight(boolean inheritParentRadius, @Nullable CaveNode parentNode, @Nonnull IDoubleRange fallback, Random random) {
/* 354 */     if (inheritParentRadius) {
/* 355 */       return CaveNodeShapeUtils.getEndHeight(parentNode, fallback, random);
/*     */     }
/* 357 */     return fallback.getValue(random);
/*     */   }
/*     */   
/*     */   private static double getMiddleRadius(double start, double end, @Nullable IDoubleRange range, Random random) {
/* 361 */     return (range == null) ? ((start - end) * 0.5D + start) : range.getValue(random);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\DistortedCaveNodeShape$DistortedCaveNodeShapeGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */