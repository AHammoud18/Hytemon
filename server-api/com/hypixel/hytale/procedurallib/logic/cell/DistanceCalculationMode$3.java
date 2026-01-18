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
/* 62 */     return Math.abs(deltaX) + Math.abs(deltaY) + deltaX * deltaX + deltaY * deltaY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance3D(double deltaX, double deltaY, double deltaZ) {
/* 67 */     return Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ) + deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 73 */     return "NaturalPointDistanceFunction{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\DistanceCalculationMode$3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */