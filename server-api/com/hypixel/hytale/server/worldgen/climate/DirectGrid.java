/*    */ package com.hypixel.hytale.server.worldgen.climate;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*    */ 
/*    */ public class DirectGrid implements CellDistanceFunction {
/*  9 */   public static final DirectGrid INSTANCE = new DirectGrid();
/*    */ 
/*    */   
/*    */   public void nearest2D(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {
/* 13 */     buffer.x = x;
/* 14 */     buffer.y = y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void nearest3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 19 */     buffer.x = x;
/* 20 */     buffer.y = y;
/* 21 */     buffer.z = z;
/*    */   }
/*    */   
/*    */   public void transition2D(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public void transition3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {}
/*    */   
/*    */   public <T> void collect(int originalSeed, int seed, int minX, int minY, int maxX, int maxY, ResultBuffer.Bounds2d bounds, T ctx, PointConsumer<T> collector, PointEvaluator pointEvaluator) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\DirectGrid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */