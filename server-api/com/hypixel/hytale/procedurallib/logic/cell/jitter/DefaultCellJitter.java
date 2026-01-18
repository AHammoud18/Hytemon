/*    */ package com.hypixel.hytale.procedurallib.logic.cell.jitter;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DefaultCellJitter
/*    */   implements CellJitter {
/*  8 */   public static final CellJitter DEFAULT_ONE = new DefaultCellJitter();
/*    */ 
/*    */   
/*    */   public double getMaxX() {
/* 12 */     return 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxY() {
/* 17 */     return 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxZ() {
/* 22 */     return 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointX(int cx, @Nonnull DoubleArray.Double2 vec) {
/* 27 */     return cx + vec.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointY(int cy, @Nonnull DoubleArray.Double2 vec) {
/* 32 */     return cy + vec.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointX(int cx, @Nonnull DoubleArray.Double3 vec) {
/* 37 */     return cx + vec.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointY(int cy, @Nonnull DoubleArray.Double3 vec) {
/* 42 */     return cy + vec.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointZ(int cz, @Nonnull DoubleArray.Double3 vec) {
/* 47 */     return cz + vec.z;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "DefaultCellJitter{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\jitter\DefaultCellJitter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */