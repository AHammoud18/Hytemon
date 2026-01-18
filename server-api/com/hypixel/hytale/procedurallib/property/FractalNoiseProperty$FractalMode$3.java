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
/* 167 */     double sum = 1.0D - Math.abs(noise.get(seed, offsetSeed, x, y));
/* 168 */     double amp = 1.0D;
/*     */     
/* 170 */     for (int i = 1; i < octaves; i++) {
/* 171 */       x *= lacunarity;
/* 172 */       y *= lacunarity;
/*     */       
/* 174 */       amp *= persistence;
/* 175 */       sum -= (1.0D - Math.abs(noise.get(seed, ++offsetSeed, x, y))) * amp;
/*     */     } 
/* 177 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction3d noise) {
/* 182 */     double sum = 1.0D - Math.abs(noise.get(seed, offsetSeed, x, y, z));
/* 183 */     float amp = 1.0F;
/*     */     
/* 185 */     for (int i = 1; i < octaves; i++) {
/* 186 */       x *= lacunarity;
/* 187 */       y *= lacunarity;
/* 188 */       z *= lacunarity;
/*     */       
/* 190 */       amp = (float)(amp * persistence);
/* 191 */       sum -= (1.0D - Math.abs(noise.get(seed, ++offsetSeed, x, y, z))) * amp;
/*     */     } 
/* 193 */     return GeneralNoise.limit(sum * 0.5D + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 199 */     return "MultiRigidFractalFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\FractalNoiseProperty$FractalMode$3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */