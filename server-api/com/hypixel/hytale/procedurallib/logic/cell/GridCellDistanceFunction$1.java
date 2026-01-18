/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.CellularNoise;
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
/*    */   public int getHash(int seed, int cellX, int cellY) {
/* 19 */     return GridCellDistanceFunction.getHash(seed, cellX, cellY);
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleArray.Double2 getOffsets(int hash) {
/* 24 */     return CellularNoise.CELL_2D[hash & 0xFF];
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX(double x, double y) {
/* 29 */     return x;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY(double x, double y) {
/* 34 */     return y;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\GridCellDistanceFunction$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */