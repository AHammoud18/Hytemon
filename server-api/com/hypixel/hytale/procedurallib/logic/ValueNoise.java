/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueNoise
/*    */   implements NoiseFunction
/*    */ {
/*    */   protected final GeneralNoise.InterpolationFunction interpolationFunction;
/*    */   
/*    */   public ValueNoise(GeneralNoise.InterpolationFunction interpolationFunction) {
/* 19 */     this.interpolationFunction = interpolationFunction;
/*    */   }
/*    */   
/*    */   public GeneralNoise.InterpolationFunction getInterpolationFunction() {
/* 23 */     return this.interpolationFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y) {
/* 28 */     int x0 = GeneralNoise.fastFloor(x);
/* 29 */     int y0 = GeneralNoise.fastFloor(y);
/* 30 */     int x1 = x0 + 1;
/* 31 */     int y1 = y0 + 1;
/*    */ 
/*    */     
/* 34 */     double xs = this.interpolationFunction.interpolate(x - x0);
/* 35 */     double ys = this.interpolationFunction.interpolate(y - y0);
/*    */     
/* 37 */     double xf0 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y0), HashUtil.random(offsetSeed, x1, y0), xs);
/* 38 */     double xf1 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y1), HashUtil.random(offsetSeed, x1, y1), xs);
/*    */     
/* 40 */     return GeneralNoise.lerp(xf0, xf1, ys) * 2.0D - 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 45 */     int x0 = GeneralNoise.fastFloor(x);
/* 46 */     int y0 = GeneralNoise.fastFloor(y);
/* 47 */     int z0 = GeneralNoise.fastFloor(z);
/* 48 */     int x1 = x0 + 1;
/* 49 */     int y1 = y0 + 1;
/* 50 */     int z1 = z0 + 1;
/*    */ 
/*    */     
/* 53 */     double xs = this.interpolationFunction.interpolate(x - x0);
/* 54 */     double ys = this.interpolationFunction.interpolate(y - y0);
/* 55 */     double zs = this.interpolationFunction.interpolate(z - z0);
/*    */     
/* 57 */     double xf00 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y0, z0), HashUtil.random(offsetSeed, x1, y0, z0), xs);
/* 58 */     double xf10 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y1, z0), HashUtil.random(offsetSeed, x1, y1, z0), xs);
/* 59 */     double xf01 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y0, z1), HashUtil.random(offsetSeed, x1, y0, z1), xs);
/* 60 */     double xf11 = GeneralNoise.lerp(HashUtil.random(offsetSeed, x0, y1, z1), HashUtil.random(offsetSeed, x1, y1, z1), xs);
/*    */     
/* 62 */     double yf0 = GeneralNoise.lerp(xf00, xf10, ys);
/* 63 */     double yf1 = GeneralNoise.lerp(xf01, xf11, ys);
/*    */     
/* 65 */     return GeneralNoise.lerp(yf0, yf1, zs) * 2.0D - 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 71 */     return "ValueNoise{interpolationFunction=" + String.valueOf(this.interpolationFunction) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\ValueNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */