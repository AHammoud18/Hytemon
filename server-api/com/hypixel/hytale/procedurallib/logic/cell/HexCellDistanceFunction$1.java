/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements CellPointFunction
/*    */ {
/*    */   public double scale(double value) {
/* 18 */     return value * HexCellDistanceFunction.SCALE;
/*    */   }
/*    */ 
/*    */   
/*    */   public double normalize(double value) {
/* 23 */     return value * 0.3333333333333333D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHash(int seed, int cellX, int cellY) {
/* 28 */     return HexCellDistanceFunction.getHash(seed, cellX, cellY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX(double x, double y) {
/* 33 */     return HexCellDistanceFunction.toHexX(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY(double x, double y) {
/* 38 */     return HexCellDistanceFunction.toHexY(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleArray.Double2 getOffsets(int hash) {
/* 43 */     return HexCellDistanceFunction.HEX_CELL_2D[hash & 0xFF];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\HexCellDistanceFunction$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */