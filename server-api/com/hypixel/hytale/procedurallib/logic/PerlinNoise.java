/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerlinNoise
/*    */   implements NoiseFunction
/*    */ {
/*    */   protected final GeneralNoise.InterpolationFunction interpolationFunction;
/*    */   
/*    */   public PerlinNoise(GeneralNoise.InterpolationFunction interpolationFunction) {
/* 17 */     this.interpolationFunction = interpolationFunction;
/*    */   }
/*    */   
/*    */   public GeneralNoise.InterpolationFunction getInterpolationFunction() {
/* 21 */     return this.interpolationFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y) {
/* 26 */     int x0 = GeneralNoise.fastFloor(x);
/* 27 */     int y0 = GeneralNoise.fastFloor(y);
/* 28 */     int x1 = x0 + 1;
/* 29 */     int y1 = y0 + 1;
/*    */     
/* 31 */     double xs = this.interpolationFunction.interpolate(x - x0);
/* 32 */     double ys = this.interpolationFunction.interpolate(y - y0);
/*    */     
/* 34 */     double xd0 = x - x0;
/* 35 */     double yd0 = y - y0;
/* 36 */     double xd1 = xd0 - 1.0D;
/* 37 */     double yd1 = yd0 - 1.0D;
/*    */     
/* 39 */     double xf0 = GeneralNoise.lerp(GeneralNoise.gradCoord2D(offsetSeed, x0, y0, xd0, yd0), GeneralNoise.gradCoord2D(offsetSeed, x1, y0, xd1, yd0), xs);
/* 40 */     double xf1 = GeneralNoise.lerp(GeneralNoise.gradCoord2D(offsetSeed, x0, y1, xd0, yd1), GeneralNoise.gradCoord2D(offsetSeed, x1, y1, xd1, yd1), xs);
/*    */     
/* 42 */     return GeneralNoise.lerp(xf0, xf1, ys);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 47 */     int x0 = GeneralNoise.fastFloor(x);
/* 48 */     int y0 = GeneralNoise.fastFloor(y);
/* 49 */     int z0 = GeneralNoise.fastFloor(z);
/* 50 */     int x1 = x0 + 1;
/* 51 */     int y1 = y0 + 1;
/* 52 */     int z1 = z0 + 1;
/*    */     
/* 54 */     double xs = this.interpolationFunction.interpolate(x - x0);
/* 55 */     double ys = this.interpolationFunction.interpolate(y - y0);
/* 56 */     double zs = this.interpolationFunction.interpolate(z - z0);
/*    */     
/* 58 */     double xd0 = x - x0;
/* 59 */     double yd0 = y - y0;
/* 60 */     double zd0 = z - z0;
/* 61 */     double xd1 = xd0 - 1.0D;
/* 62 */     double yd1 = yd0 - 1.0D;
/* 63 */     double zd1 = zd0 - 1.0D;
/*    */     
/* 65 */     double xf00 = GeneralNoise.lerp(GeneralNoise.gradCoord3D(offsetSeed, x0, y0, z0, xd0, yd0, zd0), GeneralNoise.gradCoord3D(offsetSeed, x1, y0, z0, xd1, yd0, zd0), xs);
/* 66 */     double xf10 = GeneralNoise.lerp(GeneralNoise.gradCoord3D(offsetSeed, x0, y1, z0, xd0, yd1, zd0), GeneralNoise.gradCoord3D(offsetSeed, x1, y1, z0, xd1, yd1, zd0), xs);
/* 67 */     double xf01 = GeneralNoise.lerp(GeneralNoise.gradCoord3D(offsetSeed, x0, y0, z1, xd0, yd0, zd1), GeneralNoise.gradCoord3D(offsetSeed, x1, y0, z1, xd1, yd0, zd1), xs);
/* 68 */     double xf11 = GeneralNoise.lerp(GeneralNoise.gradCoord3D(offsetSeed, x0, y1, z1, xd0, yd1, zd1), GeneralNoise.gradCoord3D(offsetSeed, x1, y1, z1, xd1, yd1, zd1), xs);
/*    */     
/* 70 */     double yf0 = GeneralNoise.lerp(xf00, xf10, ys);
/* 71 */     double yf1 = GeneralNoise.lerp(xf01, xf11, ys);
/*    */     
/* 73 */     return GeneralNoise.lerp(yf0, yf1, zs);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 79 */     return "PerlinNoise{interpolationFunction=" + String.valueOf(this.interpolationFunction) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\PerlinNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */