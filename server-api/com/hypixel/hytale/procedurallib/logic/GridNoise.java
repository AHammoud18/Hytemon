/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class GridNoise implements NoiseFunction {
/*    */   protected final double thicknessX;
/*    */   protected final double thicknessY;
/*    */   protected final double thicknessZ;
/*    */   protected final double thicknessX_m1;
/*    */   protected final double thicknessY_m1;
/*    */   protected final double thicknessZ_m1;
/*    */   
/*    */   public GridNoise(double thicknessX, double thicknessY, double thicknessZ) {
/* 16 */     this.thicknessX = thicknessX;
/* 17 */     this.thicknessY = thicknessY;
/* 18 */     this.thicknessZ = thicknessZ;
/* 19 */     this.thicknessX_m1 = 1.0D - thicknessX;
/* 20 */     this.thicknessY_m1 = 1.0D - thicknessY;
/* 21 */     this.thicknessZ_m1 = 1.0D - thicknessZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y) {
/* 26 */     x -= MathUtil.floor(x);
/* 27 */     y -= MathUtil.floor(y);
/*    */     
/* 29 */     double d = 1.0D;
/* 30 */     if (x < this.thicknessX) {
/* 31 */       d = x / this.thicknessX;
/* 32 */     } else if (x > this.thicknessX_m1) {
/* 33 */       d = (1.0D - x) / this.thicknessX;
/*    */     } 
/* 35 */     if (y < this.thicknessY) {
/* 36 */       double t = y / this.thicknessY;
/* 37 */       if (t < d) d = t; 
/* 38 */     } else if (y > this.thicknessY_m1) {
/* 39 */       double t = (1.0D - y) / this.thicknessY;
/* 40 */       if (t < d) d = t; 
/*    */     } 
/* 42 */     return d * 2.0D - 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 47 */     x -= MathUtil.floor(x);
/* 48 */     y -= MathUtil.floor(y);
/* 49 */     z -= MathUtil.floor(z);
/*    */     
/* 51 */     double d = 1.0D;
/* 52 */     if (x < this.thicknessX) {
/* 53 */       d = x / this.thicknessX;
/* 54 */     } else if (x > this.thicknessX_m1) {
/* 55 */       d = (1.0D - x) / this.thicknessX;
/*    */     } 
/* 57 */     if (y < this.thicknessY) {
/* 58 */       double t = y / this.thicknessY;
/* 59 */       if (t < d) d = t; 
/* 60 */     } else if (y > this.thicknessY_m1) {
/* 61 */       double t = (1.0D - y) / this.thicknessY;
/* 62 */       if (t < d) d = t; 
/*    */     } 
/* 64 */     if (z < this.thicknessZ) {
/* 65 */       double t = z / this.thicknessZ;
/* 66 */       if (t < d) d = t; 
/* 67 */     } else if (z > this.thicknessZ_m1) {
/* 68 */       double t = (1.0D - z) / this.thicknessZ;
/* 69 */       if (t < d) d = t; 
/*    */     } 
/* 71 */     return d * 2.0D - 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "GridNoise{thicknessX=" + this.thicknessX + ", thicknessY=" + this.thicknessY + ", thicknessZ=" + this.thicknessZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\GridNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */