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
/*     */ class null
/*     */   implements FractalNoiseProperty.FractalFunction
/*     */ {
/*     */   public double get(int seed, int offsetSeed, double x, double y, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction2d noise) {
/*  92 */     double sum = noise.get(seed, offsetSeed, x, y);
/*  93 */     double amp = 1.0D;
/*     */     
/*  95 */     for (int i = 1; i < octaves; i++) {
/*  96 */       x *= lacunarity;
/*  97 */       y *= lacunarity;
/*     */       
/*  99 */       amp *= persistence;
/* 100 */       sum += noise.get(seed, ++offsetSeed, x, y) * amp;
/*     */     } 
/* 102 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction3d noise) {
/* 107 */     double sum = noise.get(seed, offsetSeed, x, y, z);
/* 108 */     double amp = 1.0D;
/*     */     
/* 110 */     for (int i = 1; i < octaves; i++) {
/* 111 */       x *= lacunarity;
/* 112 */       y *= lacunarity;
/* 113 */       z *= lacunarity;
/*     */       
/* 115 */       amp *= persistence;
/* 116 */       sum += noise.get(seed, ++offsetSeed, x, y, z) * amp;
/*     */     } 
/* 118 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 124 */     return "FbmFractalFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\FractalNoiseProperty$FractalMode$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */