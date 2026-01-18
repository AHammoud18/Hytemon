/*     */ package com.hypixel.hytale.procedurallib.property;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction2d;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction3d;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 204 */     double maxAmp = 0.0D;
/* 205 */     double amp = 1.0D;
/* 206 */     int freq = 1;
/* 207 */     double sum = 0.0D;
/* 208 */     seed--;
/* 209 */     for (int i = 0; i < octaves; i++) {
/* 210 */       sum += noise.get(seed, offsetSeed++, x * freq, y * freq) * amp;
/* 211 */       maxAmp += amp;
/* 212 */       amp *= persistence;
/* 213 */       freq <<= 1;
/*     */     } 
/*     */     
/* 216 */     sum /= maxAmp;
/* 217 */     sum *= 0.5D;
/* 218 */     return sum + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z, int octaves, double lacunarity, double persistence, @Nonnull NoiseFunction3d noise) {
/* 223 */     double maxAmp = 0.0D;
/* 224 */     double amp = 1.0D;
/* 225 */     int freq = 1;
/* 226 */     double sum = 0.0D;
/*     */     
/* 228 */     seed--;
/* 229 */     for (int i = 0; i < octaves; i++) {
/* 230 */       sum += noise.get(seed, offsetSeed++, x * freq, y * freq, z * freq) * amp;
/* 231 */       maxAmp += amp;
/* 232 */       amp *= persistence;
/* 233 */       freq <<= 1;
/*     */     } 
/*     */     
/* 236 */     sum /= maxAmp;
/* 237 */     sum *= 0.5D;
/* 238 */     return sum + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 244 */     return "OldschoolFractalFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\FractalNoiseProperty$FractalMode$4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */