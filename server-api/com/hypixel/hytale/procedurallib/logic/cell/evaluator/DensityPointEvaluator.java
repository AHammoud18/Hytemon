/*    */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DensityPointEvaluator
/*    */   implements PointEvaluator
/*    */ {
/*    */   protected final PointEvaluator pointEvaluator;
/*    */   protected final IIntCondition density;
/*    */   
/*    */   public DensityPointEvaluator(PointEvaluator pointEvaluator, IDoubleCondition density) {
/* 21 */     this(pointEvaluator, getDensityCondition(density));
/*    */   }
/*    */   
/*    */   public DensityPointEvaluator(PointEvaluator pointEvaluator, IIntCondition density) {
/* 25 */     this.pointEvaluator = pointEvaluator;
/* 26 */     this.density = density;
/*    */   }
/*    */ 
/*    */   
/*    */   public CellJitter getJitter() {
/* 31 */     return this.pointEvaluator.getJitter();
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, ResultBuffer.ResultBuffer2d buffer) {
/* 36 */     if (!this.density.eval(cellHash))
/*    */       return; 
/* 38 */     this.pointEvaluator.evalPoint(seed, x, y, cellHash, cellX, cellY, cellPointX, cellPointY, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, ResultBuffer.ResultBuffer2d buffer) {
/* 43 */     if (!this.density.eval(cellHash))
/*    */       return; 
/* 45 */     this.pointEvaluator.evalPoint2(seed, x, y, cellHash, cellX, cellY, cellPointX, cellPointY, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 50 */     if (!this.density.eval(cellHash))
/*    */       return; 
/* 52 */     this.pointEvaluator.evalPoint(seed, x, y, z, cellHash, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 57 */     if (!this.density.eval(cellHash))
/*    */       return; 
/* 59 */     this.pointEvaluator.evalPoint2(seed, x, y, z, cellHash, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> void collectPoint(int cellHash, int cellX, int cellY, double x, double y, T t, @Nonnull PointConsumer<T> consumer) {
/* 64 */     if (!this.density.eval(cellHash))
/*    */       return; 
/* 66 */     this.pointEvaluator.collectPoint(cellHash, cellX, cellY, x, y, t, consumer);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "DensityPointEvaluator{pointEvaluator=" + String.valueOf(this.pointEvaluator) + ", density=" + String.valueOf(this.density) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static IIntCondition getDensityCondition(@Nullable IDoubleCondition threshold) {
/* 80 */     if (threshold == null) {
/* 81 */       return seed -> true;
/*    */     }
/* 83 */     return seed -> threshold.eval(randomDensityCondition(seed));
/*    */   }
/*    */ 
/*    */   
/*    */   public static double randomDensityCondition(int seed) {
/* 88 */     return HashUtil.random(seed, -1694747730L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\DensityPointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */