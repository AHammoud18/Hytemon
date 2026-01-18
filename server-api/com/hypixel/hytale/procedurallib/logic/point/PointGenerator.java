/*     */ package com.hypixel.hytale.procedurallib.logic.point;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PointGenerator
/*     */   implements IPointGenerator
/*     */ {
/*     */   protected final int seedOffset;
/*     */   protected final CellDistanceFunction cellDistanceFunction;
/*     */   protected final PointEvaluator pointEvaluator;
/*     */   
/*     */   public PointGenerator(int seedOffset, CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator) {
/*  23 */     this.seedOffset = seedOffset;
/*  24 */     this.cellDistanceFunction = cellDistanceFunction;
/*  25 */     this.pointEvaluator = pointEvaluator;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.Bounds2d localBounds2d() {
/*  30 */     return ResultBuffer.bounds2d;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/*  35 */     return ResultBuffer.buffer2d;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/*  40 */     return ResultBuffer.buffer3d;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer2d nearest2D(int seed, double x, double y) {
/*  46 */     x = this.cellDistanceFunction.scale(x);
/*  47 */     y = this.cellDistanceFunction.scale(y);
/*     */     
/*  49 */     int xr = this.cellDistanceFunction.getCellX(x, y);
/*  50 */     int yr = this.cellDistanceFunction.getCellY(x, y);
/*     */     
/*  52 */     ResultBuffer.ResultBuffer2d buffer = localBuffer2d();
/*  53 */     buffer.distance = Double.POSITIVE_INFINITY;
/*     */     
/*  55 */     this.cellDistanceFunction.nearest2D(seed + this.seedOffset, x, y, xr, yr, buffer, this.pointEvaluator);
/*  56 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer3d nearest3D(int seed, double x, double y, double z) {
/*  62 */     x = this.cellDistanceFunction.scale(x);
/*  63 */     y = this.cellDistanceFunction.scale(y);
/*  64 */     z = this.cellDistanceFunction.scale(z);
/*     */     
/*  66 */     int xr = this.cellDistanceFunction.getCellX(x, y, z);
/*  67 */     int yr = this.cellDistanceFunction.getCellY(x, y, z);
/*  68 */     int zr = this.cellDistanceFunction.getCellZ(x, y, z);
/*     */     
/*  70 */     ResultBuffer.ResultBuffer3d buffer = localBuffer3d();
/*  71 */     buffer.distance = Double.POSITIVE_INFINITY;
/*     */     
/*  73 */     this.cellDistanceFunction.nearest3D(seed + this.seedOffset, x, y, z, xr, yr, zr, buffer, this.pointEvaluator);
/*  74 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer2d transition2D(int seed, double x, double y) {
/*  80 */     x = this.cellDistanceFunction.scale(x);
/*  81 */     y = this.cellDistanceFunction.scale(y);
/*     */     
/*  83 */     int xr = this.cellDistanceFunction.getCellX(x, y);
/*  84 */     int yr = this.cellDistanceFunction.getCellY(x, y);
/*     */     
/*  86 */     ResultBuffer.ResultBuffer2d buffer = localBuffer2d();
/*  87 */     buffer.distance = Double.POSITIVE_INFINITY;
/*  88 */     buffer.distance2 = Double.POSITIVE_INFINITY;
/*     */     
/*  90 */     this.cellDistanceFunction.transition2D(seed + this.seedOffset, x, y, xr, yr, buffer, this.pointEvaluator);
/*  91 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer3d transition3D(int seed, double x, double y, double z) {
/*  97 */     x = this.cellDistanceFunction.scale(x);
/*  98 */     y = this.cellDistanceFunction.scale(y);
/*  99 */     z = this.cellDistanceFunction.scale(z);
/*     */     
/* 101 */     int xr = this.cellDistanceFunction.getCellX(x, y, z);
/* 102 */     int yr = this.cellDistanceFunction.getCellY(x, y, z);
/* 103 */     int zr = this.cellDistanceFunction.getCellZ(x, y, z);
/*     */     
/* 105 */     ResultBuffer.ResultBuffer3d buffer = localBuffer3d();
/* 106 */     buffer.distance = Double.POSITIVE_INFINITY;
/* 107 */     buffer.distance2 = Double.POSITIVE_INFINITY;
/*     */     
/* 109 */     this.cellDistanceFunction.transition3D(seed + this.seedOffset, x, y, z, xr, yr, zr, buffer, this.pointEvaluator);
/* 110 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getInterval() {
/* 115 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void collect(int seed, double minX, double minY, double maxX, double maxY, IPointGenerator.PointConsumer2d consumer) {
/* 120 */     collect0(seed, minX, minY, maxX, maxY, (x, y, t) -> t.accept(x, y), consumer);
/*     */   }
/*     */   
/*     */   public void collect0(int seed, double minX, double minY, double maxX, double maxY, PointConsumer<IPointGenerator.PointConsumer2d> pointConsumer, IPointGenerator.PointConsumer2d consumer) {
/* 124 */     minX = this.cellDistanceFunction.scale(minX);
/* 125 */     minY = this.cellDistanceFunction.scale(minY);
/* 126 */     maxX = this.cellDistanceFunction.scale(maxX);
/* 127 */     maxY = this.cellDistanceFunction.scale(maxY);
/*     */     
/* 129 */     int x0 = this.cellDistanceFunction.getCellX(minX, minY);
/* 130 */     int y0 = this.cellDistanceFunction.getCellY(minX, minY);
/* 131 */     int x1 = this.cellDistanceFunction.getCellX(maxX, maxY);
/* 132 */     int y1 = this.cellDistanceFunction.getCellY(maxX, maxY);
/*     */     
/* 134 */     ResultBuffer.Bounds2d bounds = localBounds2d();
/* 135 */     bounds.assign(minX, minY, maxX, maxY);
/*     */     
/* 137 */     this.cellDistanceFunction.collect(seed, seed + this.seedOffset, x0, y0, x1, y1, bounds, consumer, pointConsumer, this.pointEvaluator);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 142 */     if (this == o) return true; 
/* 143 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 145 */     PointGenerator that = (PointGenerator)o;
/*     */     
/* 147 */     if (this.seedOffset != that.seedOffset) return false; 
/* 148 */     if (!this.cellDistanceFunction.equals(that.cellDistanceFunction)) return false; 
/* 149 */     return this.pointEvaluator.equals(that.pointEvaluator);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     int result = this.seedOffset;
/* 155 */     result = 31 * result + this.cellDistanceFunction.hashCode();
/* 156 */     result = 31 * result + this.pointEvaluator.hashCode();
/* 157 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 163 */     return "PointGenerator{seedOffset=" + this.seedOffset + ", cellDistanceFunction=" + String.valueOf(this.cellDistanceFunction) + ", pointEvaluator=" + String.valueOf(this.pointEvaluator) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\point\PointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */