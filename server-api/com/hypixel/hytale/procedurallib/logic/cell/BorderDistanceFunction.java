/*     */ package com.hypixel.hytale.procedurallib.logic.cell;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.DensityPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.JitterPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.NormalPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BorderDistanceFunction
/*     */   implements CellDistanceFunction {
/*     */   protected final CellDistanceFunction distanceFunction;
/*     */   @Nonnull
/*     */   protected final PointEvaluator cellEvaluator;
/*     */   @Nonnull
/*     */   protected final PointEvaluator borderEvaluator;
/*     */   @Nonnull
/*     */   protected final IIntCondition density;
/*     */   
/*     */   public BorderDistanceFunction(CellDistanceFunction distanceFunction, @Nonnull PointEvaluator borderEvaluator, IDoubleCondition density) {
/*  24 */     this.distanceFunction = distanceFunction;
/*  25 */     this.borderEvaluator = borderEvaluator;
/*  26 */     this.cellEvaluator = (PointEvaluator)new JitterPointEvaluator(NormalPointEvaluator.EUCLIDEAN, borderEvaluator.getJitter());
/*  27 */     this.density = DensityPointEvaluator.getDensityCondition(density);
/*     */   }
/*     */ 
/*     */   
/*     */   public double scale(double value) {
/*  32 */     return this.distanceFunction.scale(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double invScale(double value) {
/*  37 */     return this.distanceFunction.invScale(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellX(double x, double y) {
/*  42 */     return this.distanceFunction.getCellX(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellY(double x, double y) {
/*  47 */     return this.distanceFunction.getCellY(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void nearest2D(int seed, double x, double y, int cellX, int cellY, @Nonnull ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {
/*  52 */     transition2D(seed, x, y, cellX, cellY, buffer, pointEvaluator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void transition2D(int seed, double x, double y, int cellX, int cellY, @Nonnull ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {
/*  58 */     this.distanceFunction.nearest2D(seed, x, y, cellX, cellY, buffer, this.cellEvaluator);
/*     */     
/*  60 */     if (!this.density.eval(buffer.hash)) {
/*  61 */       buffer.distance = 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     cellX = buffer.ix;
/*  66 */     cellY = buffer.iy;
/*     */     
/*  68 */     buffer.ix2 = cellX;
/*  69 */     buffer.iy2 = cellY;
/*  70 */     buffer.x2 = buffer.x;
/*  71 */     buffer.y2 = buffer.y;
/*  72 */     buffer.distance = Double.POSITIVE_INFINITY;
/*  73 */     buffer.distance2 = Double.POSITIVE_INFINITY;
/*     */     
/*  75 */     int dx = (this.borderEvaluator.getJitter().getMaxX() > 0.5D) ? 2 : 1;
/*  76 */     int dy = (this.borderEvaluator.getJitter().getMaxY() > 0.5D) ? 2 : 1;
/*     */     
/*  78 */     for (int cy = cellY - dy; cy <= cellY + dy; cy++) {
/*  79 */       for (int cx = cellX - dx; cx <= cellX + dx; cx++) {
/*  80 */         this.distanceFunction.evalPoint2(seed, x, y, cx, cy, buffer, this.borderEvaluator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void nearest3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void transition3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/*  92 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {
/* 102 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 107 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void collect(int originalSeed, int seed, int minX, int minY, int maxX, int maxY, ResultBuffer.Bounds2d bounds, T ctx, PointConsumer<T> collector, PointEvaluator pointEvaluator) {
/* 117 */     this.distanceFunction.collect(originalSeed, seed, minX, minY, maxX, maxY, bounds, ctx, collector, pointEvaluator);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 123 */     return "BorderDistanceFunction{distanceFunction=" + String.valueOf(this.distanceFunction) + ", cellEvaluator=" + String.valueOf(this.cellEvaluator) + ", borderEvaluator=" + String.valueOf(this.borderEvaluator) + ", density=" + String.valueOf(this.density) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\BorderDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */