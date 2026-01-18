/*     */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultDoubleCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.DefaultCellJitter;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface PointEvaluator
/*     */ {
/*     */   default CellJitter getJitter() {
/*  23 */     return DefaultCellJitter.DEFAULT_ONE;
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
/*     */   void evalPoint(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, int paramInt4, double paramDouble3, double paramDouble4, ResultBuffer.ResultBuffer2d paramResultBuffer2d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void evalPoint2(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, int paramInt4, double paramDouble3, double paramDouble4, ResultBuffer.ResultBuffer2d paramResultBuffer2d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void evalPoint(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble4, double paramDouble5, double paramDouble6, ResultBuffer.ResultBuffer3d paramResultBuffer3d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void evalPoint2(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble4, double paramDouble5, double paramDouble6, ResultBuffer.ResultBuffer3d paramResultBuffer3d);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T> void collectPoint(int cellHash, int cellX, int cellY, double cellCentreX, double cellCentreY, T ctx, @Nonnull PointConsumer<T> consumer) {
/* 103 */     consumer.accept(cellCentreX, cellCentreY, ctx);
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
/*     */   static PointEvaluator of(PointDistanceFunction distanceFunction, @Nullable IDoubleCondition density, @Nullable IDoubleRange distanceMod, CellJitter jitter) {
/* 118 */     return of(distanceFunction, density, distanceMod, 0, SkipCellPointEvaluator.DEFAULT_MODE, jitter);
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
/*     */   static PointEvaluator of(PointDistanceFunction distanceFunction, @Nullable IDoubleCondition density, @Nullable IDoubleRange distanceMod, int skipCount, @Nonnull SkipCellPointEvaluator.Mode skipMode, CellJitter jitter) {
/* 135 */     PointEvaluator pointEvaluator = NormalPointEvaluator.of(distanceFunction);
/*     */     
/* 137 */     if (distanceMod != null) pointEvaluator = new DistancePointEvaluator(distanceFunction, distanceMod);
/*     */     
/* 139 */     if (density != null && density != DefaultDoubleCondition.DEFAULT_TRUE) pointEvaluator = new DensityPointEvaluator(pointEvaluator, density);
/*     */     
/* 141 */     if (skipCount > 0) pointEvaluator = new SkipCellPointEvaluator(pointEvaluator, skipMode, skipCount);
/*     */     
/* 143 */     if (jitter != DefaultCellJitter.DEFAULT_ONE) pointEvaluator = new JitterPointEvaluator(pointEvaluator, jitter);
/*     */     
/* 145 */     return pointEvaluator;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\PointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */