/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*    */ 
/*    */ public interface CellPointFunction {
/*    */   default double scale(double value) {
/*  7 */     return value;
/*    */   }
/*    */   
/*    */   default double normalize(double value) {
/* 11 */     return value;
/*    */   }
/*    */   
/*    */   int getHash(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   double getX(double paramDouble1, double paramDouble2);
/*    */   
/*    */   double getY(double paramDouble1, double paramDouble2);
/*    */   
/*    */   DoubleArray.Double2 getOffsets(int paramInt);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\CellPointFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */