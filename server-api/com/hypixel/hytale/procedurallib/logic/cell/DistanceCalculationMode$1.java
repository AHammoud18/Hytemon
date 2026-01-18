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
/*    */ class null
/*    */   implements PointDistanceFunction
/*    */ {
/*    */   public double distance2D(double deltaX, double deltaY) {
/* 19 */     return deltaX * deltaX + deltaY * deltaY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance3D(double deltaX, double deltaY, double deltaZ) {
/* 24 */     return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 30 */     return "EuclideanPointDistanceFunction{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\DistanceCalculationMode$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */