/*    */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NormalPointEvaluator
/*    */   implements PointEvaluator
/*    */ {
/* 13 */   public static final PointEvaluator EUCLIDEAN = new NormalPointEvaluator(DistanceCalculationMode.EUCLIDEAN.getFunction());
/* 14 */   public static final PointEvaluator MANHATTAN = new NormalPointEvaluator(DistanceCalculationMode.MANHATTAN.getFunction());
/* 15 */   public static final PointEvaluator NATURAL = new NormalPointEvaluator(DistanceCalculationMode.NATURAL.getFunction());
/* 16 */   public static final PointEvaluator MAX = new NormalPointEvaluator(DistanceCalculationMode.MAX.getFunction());
/*    */   
/*    */   protected final PointDistanceFunction distanceFunction;
/*    */   
/*    */   public NormalPointEvaluator(PointDistanceFunction distanceFunction) {
/* 21 */     this.distanceFunction = distanceFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 26 */     double distance = this.distanceFunction.distance2D(seed, cellX, cellY, cellPointX, cellPointY, cellPointX - x, cellPointY - y);
/* 27 */     buffer.register(cellHash, cellX, cellY, distance, cellPointX, cellPointY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 32 */     double distance = this.distanceFunction.distance2D(seed, cellX, cellY, cellPointX, cellPointY, cellPointX - x, cellPointY - y);
/* 33 */     buffer.register2(cellHash, cellX, cellY, distance, cellPointX, cellPointY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, @Nonnull ResultBuffer.ResultBuffer3d buffer) {
/* 38 */     double distance = this.distanceFunction.distance3D(seed, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, cellPointX - x, cellPointY - y, cellPointZ - z);
/* 39 */     buffer.register(cellHash, cellX, cellY, cellZ, distance, cellPointX, cellPointY, cellPointZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, @Nonnull ResultBuffer.ResultBuffer3d buffer) {
/* 44 */     double distance = this.distanceFunction.distance3D(seed, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, cellPointX - x, cellPointY - y, cellPointZ - z);
/* 45 */     buffer.register2(cellHash, cellX, cellY, cellZ, distance, cellPointX, cellPointY, cellPointZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 51 */     return "NormalPointEvaluator{distanceFunction=" + String.valueOf(this.distanceFunction) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PointEvaluator of(PointDistanceFunction distanceFunction) {
/* 57 */     DistanceCalculationMode mode = DistanceCalculationMode.from(distanceFunction);
/* 58 */     if (mode == null) return new NormalPointEvaluator(distanceFunction);
/*    */     
/* 60 */     switch (mode) { default: throw new MatchException(null, null);case EUCLIDEAN: case MANHATTAN: case NATURAL: case MAX: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 64 */       MAX;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\NormalPointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */