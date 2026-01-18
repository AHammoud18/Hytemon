/*    */ package com.hypixel.hytale.procedurallib.logic.cell.jitter;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantCellJitter
/*    */   implements CellJitter {
/*    */   protected final double jitterX;
/*    */   
/*    */   public ConstantCellJitter(double jitterX, double jitterY, double jitterZ) {
/* 11 */     this.jitterX = jitterX;
/* 12 */     this.jitterY = jitterY;
/* 13 */     this.jitterZ = jitterZ;
/*    */   }
/*    */   protected final double jitterY; protected final double jitterZ;
/*    */   
/*    */   public double getMaxX() {
/* 18 */     return this.jitterX;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxY() {
/* 23 */     return this.jitterY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxZ() {
/* 28 */     return this.jitterZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointX(int cx, @Nonnull DoubleArray.Double2 vec) {
/* 33 */     return cx + vec.x * this.jitterX;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointY(int cy, @Nonnull DoubleArray.Double2 vec) {
/* 38 */     return cy + vec.y * this.jitterY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointX(int cx, @Nonnull DoubleArray.Double3 vec) {
/* 43 */     return cx + vec.x * this.jitterX;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointY(int cy, @Nonnull DoubleArray.Double3 vec) {
/* 48 */     return cy + vec.y * this.jitterY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPointZ(int cz, @Nonnull DoubleArray.Double3 vec) {
/* 53 */     return cz + vec.z * this.jitterZ;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "ConstantCellJitter{jitterX=" + this.jitterX + ", jitterY=" + this.jitterY + ", jitterZ=" + this.jitterZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\jitter\ConstantCellJitter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */