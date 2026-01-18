/*     */ package com.hypixel.hytale.procedurallib.property;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction2d;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements FractalNoiseProperty.FractalFunction
/*     */ {
/*     */   public double get(int seed, int offsetSeed, double x, double y, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction2d noise) {
/* 129 */     double sum = Math.abs(noise.get(seed, offsetSeed, x, y)) * 2.0D - 1.0D;
/* 130 */     double amp = 1.0D;
/*     */     
/* 132 */     for (int i = 1; i < octaves; i++) {
/* 133 */       x *= lacunarity;
/* 134 */       y *= lacunarity;
/*     */       
/* 136 */       amp *= persistence;
/* 137 */       sum += (Math.abs(noise.get(seed, ++offsetSeed, x, y)) * 2.0D - 1.0D) * amp;
/*     */     } 
/*     */     
/* 140 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction3d noise) {
/* 145 */     double sum = Math.abs(noise.get(seed, offsetSeed, x, y, z)) * 2.0D - 1.0D;
/* 146 */     double amp = 1.0D;
/*     */     
/* 148 */     for (int i = 1; i < octaves; i++) {
/* 149 */       x *= lacunarity;
/* 150 */       y *= lacunarity;
/* 151 */       z *= lacunarity;
/*     */       
/* 153 */       amp *= persistence;
/* 154 */       sum += (Math.abs(noise.get(seed, ++offsetSeed, x, y, z)) * 2.0D - 1.0D) * amp;
/*     */     } 
/* 156 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 162 */     return "BillowFractalFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\FractalNoiseProperty$FractalMode$2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */