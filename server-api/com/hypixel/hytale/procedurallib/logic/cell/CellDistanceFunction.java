/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CellDistanceFunction
/*    */ {
/*    */   default double scale(double value) {
/* 16 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default double invScale(double value) {
/* 26 */     return value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getCellX(double x, double y) {
/* 37 */     return MathUtil.floor(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getCellY(double x, double y) {
/* 48 */     return MathUtil.floor(y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getCellX(double x, double y, double z) {
/* 60 */     return MathUtil.floor(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getCellY(double x, double y, double z) {
/* 72 */     return MathUtil.floor(y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getCellZ(double x, double y, double z) {
/* 84 */     return MathUtil.floor(z);
/*    */   }
/*    */   
/*    */   void nearest2D(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, ResultBuffer.ResultBuffer2d paramResultBuffer2d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void nearest3D(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, ResultBuffer.ResultBuffer3d paramResultBuffer3d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void transition2D(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, ResultBuffer.ResultBuffer2d paramResultBuffer2d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void transition3D(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, ResultBuffer.ResultBuffer3d paramResultBuffer3d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void evalPoint(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, ResultBuffer.ResultBuffer2d paramResultBuffer2d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void evalPoint(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, ResultBuffer.ResultBuffer3d paramResultBuffer3d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void evalPoint2(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, int paramInt3, ResultBuffer.ResultBuffer2d paramResultBuffer2d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   void evalPoint2(int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2, int paramInt3, int paramInt4, ResultBuffer.ResultBuffer3d paramResultBuffer3d, PointEvaluator paramPointEvaluator);
/*    */   
/*    */   <T> void collect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, ResultBuffer.Bounds2d paramBounds2d, T paramT, PointConsumer<T> paramPointConsumer, PointEvaluator paramPointEvaluator);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\CellDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */