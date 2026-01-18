/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.noise;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
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
/*     */ public class CellNoiseField
/*     */   extends NoiseField
/*     */ {
/*     */   private FastNoiseLite cellNoise;
/*     */   private int seed;
/*     */   private boolean doDomainWarp;
/*     */   private double scaleX;
/*     */   private double scaleY;
/*     */   private double scaleZ;
/*     */   
/*     */   public CellNoiseField(int seed, double scaleX, double scaleY, double scaleZ, double jitter, int octaves, @Nonnull FastNoiseLite.CellularReturnType cellType, @Nonnull FastNoiseLite.DomainWarpType domainWarpType, double warpAmount, double warpScale) {
/*  25 */     if (octaves < 1 || warpAmount <= 0.0D || warpScale <= 0.0D)
/*  26 */       throw new IllegalArgumentException(); 
/*  27 */     this.seed = seed;
/*  28 */     this.scaleX = scaleX;
/*  29 */     this.scaleY = scaleY;
/*  30 */     this.scaleZ = scaleZ;
/*     */     
/*  32 */     this.cellNoise = new FastNoiseLite();
/*     */     
/*  34 */     float frequency = 1.0F;
/*  35 */     float warpFrequency = 1.0F / (float)warpScale;
/*  36 */     this.doDomainWarp = true;
/*     */     
/*  38 */     jitter *= 2.0D;
/*     */     
/*  40 */     this.cellNoise.setNoiseType(FastNoiseLite.NoiseType.Cellular);
/*  41 */     this.cellNoise.setCellularReturnType(cellType);
/*  42 */     this.cellNoise.setFractalOctaves(octaves);
/*  43 */     this.cellNoise.setFractalType(FastNoiseLite.FractalType.FBm);
/*  44 */     this.cellNoise.setCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
/*  45 */     this.cellNoise.setSeed(seed);
/*  46 */     this.cellNoise.setFrequency(frequency);
/*  47 */     this.cellNoise.setDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
/*  48 */     this.cellNoise.setDomainWarpAmp((float)warpAmount);
/*  49 */     this.cellNoise.setDomainWarpFreq(warpFrequency);
/*  50 */     this.cellNoise.setCellularJitter((float)jitter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CellNoiseField(int seed, double scaleX, double scaleY, double scaleZ, double jitter, int octaves, @Nonnull FastNoiseLite.CellularReturnType cellType) {
/*  60 */     if (octaves < 1)
/*  61 */       throw new IllegalArgumentException(); 
/*  62 */     this.seed = seed;
/*  63 */     this.scaleX = scaleX;
/*  64 */     this.scaleY = scaleY;
/*  65 */     this.scaleZ = scaleZ;
/*     */     
/*  67 */     this.cellNoise = new FastNoiseLite();
/*  68 */     float frequency = 1.0F;
/*  69 */     this.doDomainWarp = false;
/*     */     
/*  71 */     jitter *= 2.0D;
/*     */     
/*  73 */     this.cellNoise.setNoiseType(FastNoiseLite.NoiseType.Cellular);
/*  74 */     this.cellNoise.setCellularReturnType(cellType);
/*  75 */     this.cellNoise.setFractalOctaves(octaves);
/*  76 */     this.cellNoise.setFractalType(FastNoiseLite.FractalType.FBm);
/*  77 */     this.cellNoise.setCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Euclidean);
/*  78 */     this.cellNoise.setSeed(seed);
/*  79 */     this.cellNoise.setFrequency(frequency);
/*  80 */     this.cellNoise.setCellularJitter((float)jitter);
/*     */   }
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double y, double z, double w) {
/*  85 */     x /= this.scaleX;
/*  86 */     y /= this.scaleY;
/*  87 */     z /= this.scaleZ;
/*  88 */     if (this.doDomainWarp) {
/*  89 */       FastNoiseLite.Vector3 point = new FastNoiseLite.Vector3((float)x, (float)y, (float)z);
/*  90 */       this.cellNoise.DomainWarp(point);
/*  91 */       return this.cellNoise.getNoise(point.x, point.y, point.z);
/*     */     } 
/*     */     
/*  94 */     return this.cellNoise.getNoise(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double y, double z) {
/* 100 */     x /= this.scaleX;
/* 101 */     y /= this.scaleY;
/* 102 */     z /= this.scaleZ;
/* 103 */     if (this.doDomainWarp) {
/* 104 */       FastNoiseLite.Vector3 point = new FastNoiseLite.Vector3((float)x, (float)y, (float)z);
/* 105 */       this.cellNoise.DomainWarp(point);
/* 106 */       return this.cellNoise.getNoise(point.x, point.y, point.z);
/*     */     } 
/*     */     
/* 109 */     return this.cellNoise.getNoise(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x, double z) {
/* 115 */     x /= this.scaleX;
/* 116 */     z /= this.scaleZ;
/* 117 */     if (this.doDomainWarp) {
/* 118 */       FastNoiseLite.Vector2 point = new FastNoiseLite.Vector2((float)x, (float)z);
/* 119 */       this.cellNoise.DomainWarp(point);
/* 120 */       return this.cellNoise.getNoise(point.x, point.y);
/*     */     } 
/*     */     
/* 123 */     return this.cellNoise.getNoise(x, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueAt(double x) {
/* 129 */     x /= this.scaleX;
/* 130 */     return this.cellNoise.getNoise((float)x, 0.0D);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\noise\CellNoiseField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */