/*     */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SeedGenerator
/*     */ {
/*     */   @Nonnull
/*     */   private final long[] coPrimes;
/*     */   private static final long FLOOR = 10000000L;
/*     */   
/*     */   public SeedGenerator(long seed) {
/*  19 */     this
/*  20 */       .coPrimes = CoPrimeGenerator.generateCoPrimes(seed, 100, 7, 10000000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(long x, long y, long z, long w, long k, long t) {
/*  26 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3] + k * this.coPrimes[4] + t * this.coPrimes[5]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(long x, long y, long z, long w, long k) {
/*  37 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3] + k * this.coPrimes[4]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(long x, long y, long z, long w) {
/*  46 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(long x, long y, long z) {
/*  54 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(long x, long y) {
/*  61 */     return (x * this.coPrimes[0] + y * this.coPrimes[1]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(double xd, double yd, double zd, double wd, double kd, double td, double resolution) {
/*  68 */     int x = (int)(xd * resolution);
/*  69 */     int y = (int)(yd * resolution);
/*  70 */     int z = (int)(zd * resolution);
/*  71 */     int w = (int)(wd * resolution);
/*  72 */     int k = (int)(kd * resolution);
/*  73 */     int t = (int)(td * resolution);
/*     */     
/*  75 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3] + k * this.coPrimes[4] + t * this.coPrimes[5]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(double xd, double yd, double zd, double wd, double kd, double resolution) {
/*  86 */     int x = (int)(xd * resolution);
/*  87 */     int y = (int)(yd * resolution);
/*  88 */     int z = (int)(zd * resolution);
/*  89 */     int w = (int)(wd * resolution);
/*  90 */     int k = (int)(kd * resolution);
/*     */     
/*  92 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3] + k * this.coPrimes[4]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(double xd, double yd, double zd, double wd, double resolution) {
/* 102 */     int x = (int)(xd * resolution);
/* 103 */     int y = (int)(yd * resolution);
/* 104 */     int z = (int)(zd * resolution);
/* 105 */     int w = (int)(wd * resolution);
/*     */     
/* 107 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2] + w * this.coPrimes[3]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(double xd, double yd, double zd, double resolution) {
/* 115 */     int x = (int)(xd * resolution);
/* 116 */     int y = (int)(yd * resolution);
/* 117 */     int z = (int)(zd * resolution);
/*     */     
/* 119 */     return (x * this.coPrimes[0] + y * this.coPrimes[1] + z * this.coPrimes[2]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long seedAt(double xd, double yd, double resolution) {
/* 126 */     int x = (int)(xd * resolution);
/* 127 */     int y = (int)(yd * resolution);
/*     */     
/* 129 */     return (x * this.coPrimes[0] + y * this.coPrimes[1]) % this.coPrimes[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 137 */     return "SeedGenerator{coPrimes=" + Arrays.toString(this.coPrimes) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\SeedGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */