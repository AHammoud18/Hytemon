/*    */ package com.hypixel.hytale.procedurallib.logic.cell.evaluator;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BorderPointEvaluator
/*    */   implements PointEvaluator {
/*  9 */   public static final BorderPointEvaluator INSTANCE = new BorderPointEvaluator();
/*    */ 
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 14 */     if (isOrigin(cellX, cellY, buffer))
/*    */       return; 
/* 16 */     double distance = getBorderDistance(x, y, buffer.x2, buffer.y2, cellPointX, cellPointY);
/* 17 */     if (distance < buffer.distance) {
/* 18 */       buffer.distance = distance;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, int cellHash, int cellX, int cellY, double cellPointX, double cellPointY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 25 */     if (isOrigin(cellX, cellY, buffer))
/*    */       return; 
/* 27 */     double distance = getBorderDistance(x, y, buffer.x2, buffer.y2, cellPointX, cellPointY);
/* 28 */     if (distance < buffer.distance) {
/* 29 */       buffer.distance2 = buffer.distance;
/* 30 */       buffer.distance = distance;
/* 31 */     } else if (distance < buffer.distance2) {
/* 32 */       buffer.distance2 = distance;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 38 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void evalPoint2(int seed, double x, double y, double z, int cellHash, int cellX, int cellY, int cellZ, double cellPointX, double cellPointY, double cellPointZ, ResultBuffer.ResultBuffer3d buffer) {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "BorderPointEvaluator{}";
/*    */   }
/*    */   
/*    */   protected static boolean isOrigin(int cellX, int cellY, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 53 */     return (cellX == buffer.ix2 && cellY == buffer.iy2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static double getBorderDistance(double x, double y, double originX, double originY, double cellPointX, double cellPointY) {
/* 58 */     double ax = (cellPointX + originX) * 0.5D;
/* 59 */     double ay = (cellPointY + originY) * 0.5D;
/*    */ 
/*    */     
/* 62 */     double normX = -(cellPointY - originY);
/* 63 */     double normY = cellPointX - originX;
/*    */     
/* 65 */     double bx = ax + normX;
/* 66 */     double by = ay + normY;
/*    */     
/* 68 */     return MathUtil.distanceToInfLineSq(x, y, ax, ay, bx, by);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\evaluator\BorderPointEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */