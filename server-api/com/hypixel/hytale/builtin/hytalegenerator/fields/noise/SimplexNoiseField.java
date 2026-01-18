/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.noise;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class SimplexNoiseField
/*     */   extends NoiseField
/*     */ {
/*     */   private final long seed;
/*     */   @Nonnull
/*     */   private final double[] offsetX;
/*     */   @Nonnull
/*     */   private final double[] offsetY;
/*     */   @Nonnull
/*     */   private final double[] offsetZ;
/*     */   @Nonnull
/*     */   private final double[] offsetW;
/*     */   private final int numberOfOctaves;
/*     */   @Nonnull
/*     */   private final double[] octaveFrequency;
/*     */   @Nonnull
/*     */   private final double[] octaveAmplitude;
/*     */   private final double normalizer;
/*     */   
/*     */   public SimplexNoiseField(long seed, double octaveAmplitudeMultiplier, double octaveFrequencyMultiplier, int numberOfOctaves) {
/*  57 */     if (numberOfOctaves <= 0)
/*  58 */       throw new IllegalArgumentException("octaves can't be smaller than 1"); 
/*  59 */     this.seed = seed;
/*  60 */     this.numberOfOctaves = numberOfOctaves;
/*     */ 
/*     */     
/*  63 */     Random rand = new Random(seed);
/*  64 */     this.offsetX = new double[numberOfOctaves];
/*  65 */     this.offsetY = new double[numberOfOctaves];
/*  66 */     this.offsetZ = new double[numberOfOctaves];
/*  67 */     this.offsetW = new double[numberOfOctaves];
/*  68 */     for (int i = 0; i < numberOfOctaves; i++) {
/*  69 */       this.offsetX[i] = rand.nextDouble() * 256.0D;
/*  70 */       this.offsetY[i] = rand.nextDouble() * 256.0D;
/*  71 */       this.offsetZ[i] = rand.nextDouble() * 256.0D;
/*  72 */       this.offsetW[i] = rand.nextDouble() * 256.0D;
/*     */     } 
/*     */ 
/*     */     
/*  76 */     this.octaveAmplitude = new double[numberOfOctaves];
/*  77 */     this.octaveFrequency = new double[numberOfOctaves];
/*  78 */     double frequency = 1.0D, amplitude = 1.0D;
/*  79 */     double maxAmplitude = 0.0D;
/*  80 */     for (int j = 0; j < numberOfOctaves; j++) {
/*  81 */       this.octaveAmplitude[j] = amplitude;
/*  82 */       this.octaveFrequency[j] = frequency;
/*  83 */       maxAmplitude += amplitude;
/*  84 */       amplitude *= octaveAmplitudeMultiplier;
/*  85 */       frequency *= octaveFrequencyMultiplier;
/*     */     } 
/*     */ 
/*     */     
/*  89 */     this.normalizer = 1.0D / maxAmplitude;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Builder builder() {
/*  94 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*  99 */     private long seed = 1L;
/* 100 */     private double octaveAmplitudeMultiplier = 0.5D;
/* 101 */     private double octaveFrequencyMultiplier = 2.0D;
/* 102 */     private int numberOfOctaves = 4;
/*     */     
/*     */     private double scaleX;
/*     */     private double scaleY;
/*     */     private double scaleZ;
/*     */     private double scaleW;
/*     */     
/*     */     @Nonnull
/*     */     public SimplexNoiseField build() {
/* 111 */       SimplexNoiseField g = new SimplexNoiseField(this.seed, this.octaveAmplitudeMultiplier, this.octaveFrequencyMultiplier, this.numberOfOctaves);
/*     */ 
/*     */       
/* 114 */       g.setScale(this.scaleX, this.scaleY, this.scaleZ, this.scaleW);
/* 115 */       return g;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withScale(double s) {
/* 120 */       this.scaleX = s;
/* 121 */       this.scaleY = s;
/* 122 */       this.scaleZ = s;
/* 123 */       this.scaleW = s;
/* 124 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withScale(double x, double y, double z, double w) {
/* 129 */       this.scaleX = x;
/* 130 */       this.scaleY = y;
/* 131 */       this.scaleZ = z;
/* 132 */       this.scaleW = w;
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withNumberOfOctaves(int n) {
/* 138 */       if (n <= 0) throw new IllegalArgumentException("invalid number"); 
/* 139 */       this.numberOfOctaves = n;
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withFrequencyMultiplier(double f) {
/* 145 */       this.octaveFrequencyMultiplier = f;
/* 146 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withAmplitudeMultiplier(double a) {
/* 151 */       this.octaveAmplitudeMultiplier = a;
/* 152 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withSeed(long s) {
/* 157 */       this.seed = s;
/* 158 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double y, double z, double w) {
/* 166 */     x /= this.scaleX;
/* 167 */     y /= this.scaleY;
/* 168 */     z /= this.scaleZ;
/* 169 */     w /= this.scaleW;
/*     */     
/* 171 */     double octaveX = 0.0D;
/* 172 */     double octaveY = 0.0D;
/* 173 */     double octaveZ = 0.0D;
/* 174 */     double octaveW = 0.0D;
/*     */ 
/*     */     
/* 177 */     double value = 0.0D;
/* 178 */     for (int i = 0; i < this.numberOfOctaves; i++) {
/* 179 */       octaveX = x + this.offsetX[i];
/* 180 */       octaveY = y + this.offsetY[i];
/* 181 */       octaveZ = z + this.offsetZ[i];
/* 182 */       octaveW = w + this.offsetW[i];
/*     */       
/* 184 */       value += Simplex.noise(octaveX * this.octaveFrequency[i], octaveY * this.octaveFrequency[i], octaveZ * this.octaveFrequency[i], octaveW * this.octaveFrequency[i]) * this.octaveAmplitude[i];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     value *= this.normalizer;
/* 193 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double y, double z) {
/* 200 */     x /= this.scaleX;
/* 201 */     y /= this.scaleY;
/* 202 */     z /= this.scaleZ;
/*     */     
/* 204 */     double octaveX = 0.0D;
/* 205 */     double octaveY = 0.0D;
/* 206 */     double octaveZ = 0.0D;
/*     */ 
/*     */     
/* 209 */     double value = 0.0D;
/* 210 */     for (int i = 0; i < this.numberOfOctaves; i++) {
/* 211 */       octaveX = x + this.offsetX[i];
/* 212 */       octaveY = y + this.offsetY[i];
/* 213 */       octaveZ = z + this.offsetZ[i];
/*     */       
/* 215 */       value += Simplex.noise(octaveX * this.octaveFrequency[i], octaveY * this.octaveFrequency[i], octaveZ * this.octaveFrequency[i]) * this.octaveAmplitude[i];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     value *= this.normalizer;
/* 223 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double y) {
/* 230 */     x /= this.scaleX;
/* 231 */     y /= this.scaleY;
/*     */     
/* 233 */     double octaveX = 0.0D;
/* 234 */     double octaveY = 0.0D;
/*     */ 
/*     */     
/* 237 */     double value = 0.0D;
/* 238 */     for (int i = 0; i < this.numberOfOctaves; i++) {
/* 239 */       octaveX = x + this.offsetX[i];
/* 240 */       octaveY = y + this.offsetY[i];
/*     */       
/* 242 */       value += Simplex.noise(octaveX * this.octaveFrequency[i], octaveY * this.octaveFrequency[i]) * this.octaveAmplitude[i];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 248 */     value *= this.normalizer;
/* 249 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x) {
/* 256 */     x /= this.scaleX;
/*     */     
/* 258 */     double octaveX = 0.0D;
/*     */ 
/*     */     
/* 261 */     double value = 0.0D;
/* 262 */     for (int i = 0; i < this.numberOfOctaves; i++) {
/* 263 */       octaveX = x + this.offsetX[i];
/*     */       
/* 265 */       value += Simplex.noise(octaveX * this.octaveFrequency[i], 0.0D) * this.octaveAmplitude[i];
/*     */     } 
/*     */ 
/*     */     
/* 269 */     value *= this.normalizer;
/* 270 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed() {
/* 275 */     return this.seed;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\noise\SimplexNoiseField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */