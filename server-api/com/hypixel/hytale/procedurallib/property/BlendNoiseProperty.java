/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ 
/*    */ public class BlendNoiseProperty implements NoiseProperty {
/*    */   private final NoiseProperty alpha;
/*    */   private final NoiseProperty[] noises;
/*    */   private final double[] thresholds;
/*    */   private final transient double[] normalize;
/*    */   
/*    */   public BlendNoiseProperty(NoiseProperty alpha, NoiseProperty[] noises, double[] thresholds) {
/* 12 */     this.alpha = alpha;
/* 13 */     this.noises = noises;
/* 14 */     this.thresholds = thresholds;
/* 15 */     this.normalize = new double[thresholds.length];
/*    */ 
/*    */     
/* 18 */     for (int i = 1; i < thresholds.length; i++) {
/* 19 */       if (thresholds[i] <= thresholds[i - 1]) {
/* 20 */         throw new IllegalStateException("Thresholds must be in ascending order");
/*    */       }
/* 22 */       this.normalize[i] = 1.0D / (thresholds[i] - thresholds[i - 1]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 28 */     if (this.noises.length == 0) {
/* 29 */       return 0.0D;
/*    */     }
/*    */     
/* 32 */     double alpha = this.alpha.get(seed, x, y);
/*    */     
/* 34 */     if (alpha <= this.thresholds[0]) {
/* 35 */       return this.noises[0].get(seed, x, y);
/*    */     }
/*    */     
/* 38 */     if (alpha >= this.thresholds[this.thresholds.length - 1]) {
/* 39 */       return this.noises[this.noises.length - 1].get(seed, x, y);
/*    */     }
/*    */     
/* 42 */     for (int i = 1; i < this.noises.length; i++) {
/* 43 */       if (alpha <= this.thresholds[i]) {
/* 44 */         double t = (alpha - this.thresholds[i - 1]) * this.normalize[i];
/* 45 */         double lower = this.noises[i - 1].get(seed, x, y);
/* 46 */         double upper = this.noises[i].get(seed, x, y);
/* 47 */         return MathUtil.lerp(lower, upper, t);
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 56 */     if (this.noises.length == 0) {
/* 57 */       return 0.0D;
/*    */     }
/*    */     
/* 60 */     double alpha = this.alpha.get(seed, x, y, z);
/*    */     
/* 62 */     if (alpha <= this.thresholds[0]) {
/* 63 */       return this.noises[0].get(seed, x, y, z);
/*    */     }
/*    */     
/* 66 */     if (alpha >= this.thresholds[this.thresholds.length - 1]) {
/* 67 */       return this.noises[this.noises.length - 1].get(seed, x, y, z);
/*    */     }
/*    */     
/* 70 */     for (int i = 1; i < this.noises.length; i++) {
/* 71 */       if (alpha <= this.thresholds[i]) {
/* 72 */         double t = (alpha - this.thresholds[i - 1]) * this.normalize[i];
/* 73 */         double lower = this.noises[i - 1].get(seed, x, y, z);
/* 74 */         double upper = this.noises[i + 0].get(seed, x, y, z);
/* 75 */         return MathUtil.lerp(lower, upper, t);
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\BlendNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */