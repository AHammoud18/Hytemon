/*    */ package com.hypixel.hytale.procedurallib.logic.cell;
/*    */ 
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
/*    */ class null
/*    */   implements PointDistanceFunction
/*    */ {
/*    */   public double distance2D(double deltaX, double deltaY) {
/* 41 */     return Math.abs(deltaX) + Math.abs(deltaY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance3D(double deltaX, double deltaY, double deltaZ) {
/* 46 */     return Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "ManhattanPointDistanceFunction{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\DistanceCalculationMode$2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */