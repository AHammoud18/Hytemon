/*    */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class JitterPointEvaluator
/*    */   implements PointEvaluator {
/*    */   protected final PointEvaluator pointEvaluator;
/*    */   protected final CellJitter jitter;
/*    */   
/*    */   public JitterPointEvaluator(PointEvaluator pointEvaluator, CellJitter jitter) {
/* 14 */     this.pointEvaluator = pointEvaluator;
/* 15 */     this.jitter = jitter;
/*    */   }
/*    */ 
/*    */   
/*    */   public CellJitter getJitter() {
/* 20 */     return this.jitter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, ResultBuffer.ResultBuffer2d buffer) {
/* 25 */     this.pointEvaluator.evalPoint(seed, x, y, cellHash, cellX, cellY, cellPointX, cellPointY, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, ResultBuffer.ResultBuffer2d buffer) {
/* 30 */     this.pointEvaluator.evalPoint2(seed, x, y, cellHash, cellX, cellY, cellPointX, cellPointY, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 35 */     this.pointEvaluator.evalPoint(seed, x, y, z, cellHash, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 40 */     this.pointEvaluator.evalPoint2(seed, x, y, z, cellHash, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> void collectPoint(int cellHash, int cellX, int cellY, double cellCentreX, double cellCentreY, T ctx, @Nonnull PointConsumer<T> consumer) {
/* 45 */     this.pointEvaluator.collectPoint(cellHash, cellX, cellY, cellCentreX, cellCentreY, ctx, consumer);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 51 */     return "JitterPointEvaluator{pointEvaluator=" + String.valueOf(this.pointEvaluator) + ", jitter=" + String.valueOf(this.jitter) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\JitterPointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */