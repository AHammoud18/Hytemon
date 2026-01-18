/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexNoise
/*     */   implements NoiseFunction
/*     */ {
/*  14 */   public static final SimplexNoise INSTANCE = new SimplexNoise();
/*     */   
/*     */   private static final double F2 = 0.5D;
/*     */   
/*     */   private static final double P1_F2 = -0.5D;
/*     */   
/*     */   private static final double G2 = 0.25D;
/*     */   
/*     */   private static final double F3 = 0.3333333333333333D;
/*     */   private static final double G3 = 0.16666666666666666D;
/*     */   private static final double G33 = -0.5D;
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y) {
/*     */     int i1, j1;
/*  28 */     double n0, n1, n2, t = (x + y) * 0.5D;
/*  29 */     int i = GeneralNoise.fastFloor(x + t);
/*  30 */     int j = GeneralNoise.fastFloor(y + t);
/*     */     
/*  32 */     t = (i + j) * 0.25D;
/*  33 */     double X0 = i - t;
/*  34 */     double Y0 = j - t;
/*     */     
/*  36 */     double x0 = x - X0;
/*  37 */     double y0 = y - Y0;
/*     */ 
/*     */     
/*  40 */     if (x0 > y0) {
/*  41 */       i1 = 1;
/*  42 */       j1 = 0;
/*     */     } else {
/*  44 */       i1 = 0;
/*  45 */       j1 = 1;
/*     */     } 
/*     */ 
/*     */     
/*  49 */     t = 0.5D - x0 * x0 - y0 * y0;
/*  50 */     if (t < 0.0D) {
/*  51 */       n0 = 0.0D;
/*     */     } else {
/*  53 */       t *= t;
/*  54 */       n0 = t * t * GeneralNoise.gradCoord2D(offsetSeed, i, j, x0, y0);
/*     */     } 
/*     */     
/*  57 */     double x1 = x0 - i1 + 0.25D;
/*  58 */     double y1 = y0 - j1 + 0.25D;
/*  59 */     t = 0.5D - x1 * x1 - y1 * y1;
/*  60 */     if (t < 0.0D) {
/*  61 */       n1 = 0.0D;
/*     */     } else {
/*  63 */       t *= t;
/*  64 */       n1 = t * t * GeneralNoise.gradCoord2D(offsetSeed, i + i1, j + j1, x1, y1);
/*     */     } 
/*     */     
/*  67 */     double x2 = x0 + -0.5D;
/*  68 */     double y2 = y0 + -0.5D;
/*  69 */     t = 0.5D - x2 * x2 - y2 * y2;
/*  70 */     if (t < 0.0D) {
/*  71 */       n2 = 0.0D;
/*     */     } else {
/*  73 */       t *= t;
/*  74 */       n2 = t * t * GeneralNoise.gradCoord2D(offsetSeed, i + 1, j + 1, x2, y2);
/*     */     } 
/*     */     
/*  77 */     return 50.0D * (n0 + n1 + n2);
/*     */   }
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/*     */     int i1, j1, k1, i2, j2, k2;
/*  82 */     double n0, n1, n2, n3, t = (x + y + z) * 0.3333333333333333D;
/*  83 */     int i = GeneralNoise.fastFloor(x + t);
/*  84 */     int j = GeneralNoise.fastFloor(y + t);
/*  85 */     int k = GeneralNoise.fastFloor(z + t);
/*     */     
/*  87 */     t = (i + j + k) * 0.16666666666666666D;
/*  88 */     double x0 = x - i - t;
/*  89 */     double y0 = y - j - t;
/*  90 */     double z0 = z - k - t;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (x0 >= y0) {
/*  96 */       if (y0 >= z0) {
/*  97 */         i1 = 1;
/*  98 */         j1 = 0;
/*  99 */         k1 = 0;
/* 100 */         i2 = 1;
/* 101 */         j2 = 1;
/* 102 */         k2 = 0;
/* 103 */       } else if (x0 >= z0) {
/* 104 */         i1 = 1;
/* 105 */         j1 = 0;
/* 106 */         k1 = 0;
/* 107 */         i2 = 1;
/* 108 */         j2 = 0;
/* 109 */         k2 = 1;
/*     */       } else {
/*     */         
/* 112 */         i1 = 0;
/* 113 */         j1 = 0;
/* 114 */         k1 = 1;
/* 115 */         i2 = 1;
/* 116 */         j2 = 0;
/* 117 */         k2 = 1;
/*     */       }
/*     */     
/*     */     }
/* 121 */     else if (y0 < z0) {
/* 122 */       i1 = 0;
/* 123 */       j1 = 0;
/* 124 */       k1 = 1;
/* 125 */       i2 = 0;
/* 126 */       j2 = 1;
/* 127 */       k2 = 1;
/* 128 */     } else if (x0 < z0) {
/* 129 */       i1 = 0;
/* 130 */       j1 = 1;
/* 131 */       k1 = 0;
/* 132 */       i2 = 0;
/* 133 */       j2 = 1;
/* 134 */       k2 = 1;
/*     */     } else {
/*     */       
/* 137 */       i1 = 0;
/* 138 */       j1 = 1;
/* 139 */       k1 = 0;
/* 140 */       i2 = 1;
/* 141 */       j2 = 1;
/* 142 */       k2 = 0;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     double x1 = x0 - i1 + 0.16666666666666666D;
/* 147 */     double y1 = y0 - j1 + 0.16666666666666666D;
/* 148 */     double z1 = z0 - k1 + 0.16666666666666666D;
/* 149 */     double x2 = x0 - i2 + 0.3333333333333333D;
/* 150 */     double y2 = y0 - j2 + 0.3333333333333333D;
/* 151 */     double z2 = z0 - k2 + 0.3333333333333333D;
/* 152 */     double x3 = x0 + -0.5D;
/* 153 */     double y3 = y0 + -0.5D;
/* 154 */     double z3 = z0 + -0.5D;
/*     */ 
/*     */ 
/*     */     
/* 158 */     t = 0.6D - x0 * x0 - y0 * y0 - z0 * z0;
/* 159 */     if (t < 0.0D) { n0 = 0.0D; }
/*     */     else
/* 161 */     { t *= t;
/* 162 */       n0 = t * t * GeneralNoise.gradCoord3D(offsetSeed, i, j, k, x0, y0, z0); }
/*     */ 
/*     */     
/* 165 */     t = 0.6D - x1 * x1 - y1 * y1 - z1 * z1;
/* 166 */     if (t < 0.0D) { n1 = 0.0D; }
/*     */     else
/* 168 */     { t *= t;
/* 169 */       n1 = t * t * GeneralNoise.gradCoord3D(offsetSeed, i + i1, j + j1, k + k1, x1, y1, z1); }
/*     */ 
/*     */     
/* 172 */     t = 0.6D - x2 * x2 - y2 * y2 - z2 * z2;
/* 173 */     if (t < 0.0D) { n2 = 0.0D; }
/*     */     else
/* 175 */     { t *= t;
/* 176 */       n2 = t * t * GeneralNoise.gradCoord3D(offsetSeed, i + i2, j + j2, k + k2, x2, y2, z2); }
/*     */ 
/*     */     
/* 179 */     t = 0.6D - x3 * x3 - y3 * y3 - z3 * z3;
/* 180 */     if (t < 0.0D) { n3 = 0.0D; }
/*     */     else
/* 182 */     { t *= t;
/* 183 */       n3 = t * t * GeneralNoise.gradCoord3D(offsetSeed, i + 1, j + 1, k + 1, x3, y3, z3); }
/*     */ 
/*     */     
/* 186 */     return 32.0D * (n0 + n1 + n2 + n3);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 192 */     return "SimplexNoise{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\SimplexNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */