/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.noise;
/*     */ 
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
/*     */ public class Builder
/*     */ {
/*  99 */   private long seed = 1L;
/* 100 */   private double octaveAmplitudeMultiplier = 0.5D;
/* 101 */   private double octaveFrequencyMultiplier = 2.0D;
/* 102 */   private int numberOfOctaves = 4;
/*     */   
/*     */   private double scaleX;
/*     */   private double scaleY;
/*     */   private double scaleZ;
/*     */   private double scaleW;
/*     */   
/*     */   @Nonnull
/*     */   public SimplexNoiseField build() {
/* 111 */     SimplexNoiseField g = new SimplexNoiseField(this.seed, this.octaveAmplitudeMultiplier, this.octaveFrequencyMultiplier, this.numberOfOctaves);
/*     */ 
/*     */     
/* 114 */     g.setScale(this.scaleX, this.scaleY, this.scaleZ, this.scaleW);
/* 115 */     return g;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withScale(double s) {
/* 120 */     this.scaleX = s;
/* 121 */     this.scaleY = s;
/* 122 */     this.scaleZ = s;
/* 123 */     this.scaleW = s;
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withScale(double x, double y, double z, double w) {
/* 129 */     this.scaleX = x;
/* 130 */     this.scaleY = y;
/* 131 */     this.scaleZ = z;
/* 132 */     this.scaleW = w;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withNumberOfOctaves(int n) {
/* 138 */     if (n <= 0) throw new IllegalArgumentException("invalid number"); 
/* 139 */     this.numberOfOctaves = n;
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withFrequencyMultiplier(double f) {
/* 145 */     this.octaveFrequencyMultiplier = f;
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withAmplitudeMultiplier(double a) {
/* 151 */     this.octaveAmplitudeMultiplier = a;
/* 152 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withSeed(long s) {
/* 157 */     this.seed = s;
/* 158 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\noise\SimplexNoiseField$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */