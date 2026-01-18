/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RootSolver
/*     */ {
/*     */   public static final double M_PI = 3.141592653589793D;
/*     */   public static final double EQN_EPS = 1.0E-15D;
/*     */   
/*     */   protected static boolean isZero(double x) {
/*  54 */     return (x > -1.0E-15D && x < 1.0E-15D);
/*     */   }
/*     */   
/*     */   protected static double cubicRoot(double x) {
/*  58 */     return (x > 0.0D) ? Math.pow(x, 0.3333333333333333D) : (
/*  59 */       (x < 0.0D) ? -Math.pow(-x, 0.3333333333333333D) : 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int solveQuadric(double c2, double c1, double c0, double[] results, int resultIndex) {
/*  67 */     double p = c1 / 2.0D * c2;
/*  68 */     double q = c0 / c2;
/*     */     
/*  70 */     double D = p * p - q;
/*     */     
/*  72 */     if (isZero(D)) {
/*  73 */       results[resultIndex] = -p;
/*  74 */       return 1;
/*  75 */     }  if (D < 0.0D) {
/*  76 */       return 0;
/*     */     }
/*  78 */     double sqrt_D = Math.sqrt(D);
/*     */     
/*  80 */     results[resultIndex] = sqrt_D - p;
/*  81 */     results[resultIndex + 1] = -sqrt_D - p;
/*  82 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int solveCubic(double c3, double c2, double c1, double c0, double[] results) {
/*     */     int num;
/*  95 */     double A = c2 / c3;
/*  96 */     double B = c1 / c3;
/*  97 */     double C = c0 / c3;
/*     */ 
/*     */     
/* 100 */     double sq_A = A * A;
/* 101 */     double p = 0.3333333333333333D * (-0.3333333333333333D * sq_A + B);
/* 102 */     double q = 0.5D * (0.07407407407407407D * A * sq_A - 0.3333333333333333D * A * B + C);
/*     */ 
/*     */     
/* 105 */     double cb_p = p * p * p;
/* 106 */     double D = q * q + cb_p;
/*     */     
/* 108 */     if (isZero(D)) {
/* 109 */       if (isZero(q)) {
/* 110 */         results[0] = 0.0D;
/* 111 */         num = 1;
/*     */       } else {
/* 113 */         double u = cubicRoot(-q);
/* 114 */         results[0] = 2.0D * u;
/* 115 */         results[1] = -u;
/* 116 */         num = 2;
/*     */       } 
/* 118 */     } else if (D < 0.0D) {
/* 119 */       double phi = 0.3333333333333333D * Math.acos(-q / Math.sqrt(-cb_p));
/* 120 */       double t = 2.0D * Math.sqrt(-p);
/*     */       
/* 122 */       results[0] = t * Math.cos(phi);
/* 123 */       results[1] = -t * Math.cos(phi + 1.0471975511965976D);
/* 124 */       results[2] = -t * Math.cos(phi - 1.0471975511965976D);
/* 125 */       num = 3;
/*     */     } else {
/* 127 */       double sqrt_D = Math.sqrt(D);
/* 128 */       double u = cubicRoot(sqrt_D - q);
/* 129 */       double v = -cubicRoot(sqrt_D + q);
/*     */       
/* 131 */       results[0] = u + v;
/* 132 */       num = 1;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     double sub = 0.3333333333333333D * A;
/* 137 */     for (int i = 0; i < num; i++) {
/* 138 */       results[i] = results[i] - sub;
/*     */     }
/* 140 */     return num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int solveQuartic(double c4, double c3, double c2, double c1, double c0, double[] results) {
/*     */     int num;
/* 153 */     double A = c3 / c4;
/* 154 */     double B = c2 / c4;
/* 155 */     double C = c1 / c4;
/* 156 */     double D = c0 / c4;
/*     */ 
/*     */     
/* 159 */     double sq_A = A * A;
/* 160 */     double p = -0.375D * sq_A + B;
/* 161 */     double q = 0.125D * sq_A * A - 0.5D * A * B + C;
/* 162 */     double r = -0.01171875D * sq_A * sq_A + 0.0625D * sq_A * B - 0.25D * A * C + D;
/*     */     
/* 164 */     if (isZero(r)) {
/*     */       
/* 166 */       double coeff0 = q;
/* 167 */       double coeff1 = p;
/* 168 */       double coeff2 = 0.0D;
/* 169 */       double coeff3 = 1.0D;
/*     */       
/* 171 */       num = solveCubic(coeff3, coeff2, coeff1, coeff0, results);
/*     */       
/* 173 */       results[num++] = 0.0D;
/*     */     } else {
/*     */       
/* 176 */       double coeff0 = 0.5D * r * p - 0.125D * q * q;
/* 177 */       double coeff1 = -r;
/* 178 */       double coeff2 = -0.5D * p;
/* 179 */       double coeff3 = 1.0D;
/*     */       
/* 181 */       solveCubic(coeff3, coeff2, coeff1, coeff0, results);
/*     */ 
/*     */       
/* 184 */       double z = results[0];
/*     */ 
/*     */       
/* 187 */       double u = z * z - r;
/* 188 */       double v = 2.0D * z - p;
/*     */       
/* 190 */       if (isZero(u)) {
/* 191 */         u = 0.0D;
/* 192 */       } else if (u > 0.0D) {
/* 193 */         u = Math.sqrt(u);
/*     */       } else {
/* 195 */         return 0;
/*     */       } 
/* 197 */       if (isZero(v)) {
/* 198 */         v = 0.0D;
/* 199 */       } else if (v > 0.0D) {
/* 200 */         v = Math.sqrt(v);
/*     */       } else {
/* 202 */         return 0;
/*     */       } 
/* 204 */       coeff0 = z - u;
/* 205 */       coeff1 = (q < 0.0D) ? -v : v;
/* 206 */       coeff2 = 1.0D;
/*     */       
/* 208 */       num = solveQuadric(coeff2, coeff1, coeff0, results, 0);
/*     */       
/* 210 */       coeff0 = z + u;
/* 211 */       coeff1 = (q < 0.0D) ? v : -v;
/* 212 */       coeff2 = 1.0D;
/*     */       
/* 214 */       num += solveQuadric(coeff2, coeff1, coeff0, results, num);
/*     */     } 
/*     */ 
/*     */     
/* 218 */     double sub = 0.25D * A;
/* 219 */     for (int i = 0; i < num; i++) {
/* 220 */       results[i] = results[i] - sub;
/*     */     }
/* 222 */     return num;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\RootSolver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */