/*    */ package com.hypixel.hytale.procedurallib.logic.cell.jitter;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ public interface CellJitter
/*    */ {
/*    */   double getMaxX();
/*    */   
/*    */   double getMaxY();
/*    */   
/*    */   double getMaxZ();
/*    */   
/*    */   double getPointX(int paramInt, DoubleArray.Double2 paramDouble2);
/*    */   
/*    */   double getPointY(int paramInt, DoubleArray.Double2 paramDouble2);
/*    */   
/*    */   double getPointX(int paramInt, DoubleArray.Double3 paramDouble3);
/*    */   
/*    */   double getPointY(int paramInt, DoubleArray.Double3 paramDouble3);
/*    */   
/*    */   double getPointZ(int paramInt, DoubleArray.Double3 paramDouble3);
/*    */   
/*    */   @Nonnull
/*    */   static CellJitter of(double x, double y, double z) {
/* 76 */     if (x == 1.0D && y == 1.0D && z == 1.0D) return DefaultCellJitter.DEFAULT_ONE; 
/* 77 */     return new ConstantCellJitter(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\jitter\CellJitter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */