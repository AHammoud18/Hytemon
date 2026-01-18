/*    */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ import com.hypixel.hytale.procedurallib.supplier.ISeedDoubleRange;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistancePointEvaluator
/*    */   implements PointEvaluator
/*    */ {
/*    */   protected final PointDistanceFunction distanceFunction;
/*    */   protected final ISeedDoubleRange distanceMod;
/*    */   
/*    */   public DistancePointEvaluator(PointDistanceFunction distanceFunction, IDoubleRange distanceMod) {
/* 20 */     this(distanceFunction, getDistanceModifier(distanceMod));
/*    */   }
/*    */   
/*    */   public DistancePointEvaluator(PointDistanceFunction distanceFunction, ISeedDoubleRange distanceMod) {
/* 24 */     this.distanceFunction = distanceFunction;
/* 25 */     this.distanceMod = distanceMod;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 30 */     double distance = this.distanceFunction.distance2D(seed, cellX, cellY, cellPointX, cellPointY, cellPointX - x, cellPointY - y);
/* 31 */     distance = this.distanceMod.getValue(cellHash, distance);
/* 32 */     buffer.register(cellHash, cellX, cellY, distance, cellPointX, cellPointY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 37 */     double distance = this.distanceFunction.distance2D(seed, cellX, cellY, cellPointX, cellPointY, cellPointX - x, cellPointY - y);
/* 38 */     distance = this.distanceMod.getValue(cellHash, distance);
/* 39 */     buffer.register2(cellHash, cellX, cellY, distance, cellPointX, cellPointY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, @Nonnull ResultBuffer.ResultBuffer3d buffer) {
/* 44 */     double distance = this.distanceFunction.distance3D(seed, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, cellPointX - x, cellPointY - y, cellPointZ - z);
/* 45 */     distance = this.distanceMod.getValue(cellHash, distance);
/* 46 */     buffer.register(cellHash, cellX, cellY, cellZ, distance, cellPointX, cellPointY, cellPointZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, @Nonnull ResultBuffer.ResultBuffer3d buffer) {
/* 51 */     double distance = this.distanceFunction.distance3D(seed, cellX, cellY, cellZ, cellPointX, cellPointY, cellPointZ, cellPointX - x, cellPointY - y, cellPointZ - z);
/* 52 */     distance = this.distanceMod.getValue(cellHash, distance);
/* 53 */     buffer.register2(cellHash, cellX, cellY, cellZ, distance, cellPointX, cellPointY, cellPointZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "DistancePointEvaluator{distanceFunction=" + String.valueOf(this.distanceFunction) + ", distanceMod=" + String.valueOf(this.distanceMod) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static ISeedDoubleRange getDistanceModifier(@Nullable IDoubleRange range) {
/* 67 */     if (range == null) return ISeedDoubleRange.DIRECT;
/*    */     
/* 69 */     return (seed, value) -> value * range.getValue(randomDistanceModification(seed));
/*    */   }
/*    */   
/*    */   public static double randomDistanceModification(int seed) {
/* 73 */     return HashUtil.random(seed, 1495661265L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\DistancePointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */