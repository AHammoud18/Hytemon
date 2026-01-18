/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ 
/*    */ public class PointNoise implements NoiseFunction {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final double innerRadius2;
/*    */   private final double outerRadius2;
/*    */   private final transient double invRange2;
/*    */   
/*    */   public PointNoise(double x, double y, double z, double innerRadius, double outerRadius) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/* 17 */     this.z = z;
/* 18 */     this.innerRadius2 = innerRadius * innerRadius;
/* 19 */     this.outerRadius2 = outerRadius * outerRadius;
/* 20 */     double range = this.outerRadius2 - this.innerRadius2;
/* 21 */     this.invRange2 = (range == 0.0D) ? 1.0D : (1.0D / range);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int seedOffset, double x, double y) {
/* 26 */     double dist2 = MathUtil.lengthSquared(x - this.x, y - this.y);
/*    */     
/* 28 */     if (dist2 <= this.innerRadius2) {
/* 29 */       return -1.0D;
/*    */     }
/*    */     
/* 32 */     if (dist2 >= this.outerRadius2) {
/* 33 */       return 1.0D;
/*    */     }
/*    */     
/* 36 */     return -1.0D + 2.0D * (dist2 - this.innerRadius2) * this.invRange2;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int seedOffset, double x, double y, double z) {
/* 41 */     double dist2 = MathUtil.lengthSquared(x - this.x, y - this.y, this.z - z);
/*    */     
/* 43 */     if (dist2 <= this.innerRadius2) {
/* 44 */       return -1.0D;
/*    */     }
/*    */     
/* 47 */     if (dist2 >= this.outerRadius2) {
/* 48 */       return 1.0D;
/*    */     }
/*    */     
/* 51 */     return -1.0D + 2.0D * (dist2 - this.innerRadius2) * this.invRange2;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\PointNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */