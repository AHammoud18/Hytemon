/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
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
/*    */ public interface PointDistanceFunction
/*    */ {
/*    */   double distance2D(double paramDouble1, double paramDouble2);
/*    */   
/*    */   double distance3D(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   default double distance2D(int seed, int cellX, int cellY, double cellCentreX, double cellCentreY, double deltaX, double deltaY) {
/* 36 */     return distance2D(deltaX, deltaY);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default double distance3D(int seed, int cellX, int cellY, int cellZ, double cellCentreX, double cellCentreY, double cellCentreZ, double deltaX, double deltaY, double deltaZ) {
/* 55 */     return distance3D(deltaX, deltaY, deltaZ);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\PointDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */