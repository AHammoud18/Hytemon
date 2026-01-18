/*     */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Calculator
/*     */ {
/*     */   public static int toIntFloored(double d) {
/*  17 */     d = Math.floor(d);
/*  18 */     return (int)d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean perfectDiv(int x, int divisor) {
/*  29 */     return (x % divisor == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double max(@Nonnull double... n) {
/*  39 */     Objects.requireNonNull(n);
/*  40 */     if (n.length <= 0)
/*  41 */       throw new IllegalArgumentException("array can't be empty"); 
/*  42 */     double max = Double.NEGATIVE_INFINITY;
/*  43 */     for (double value : n) {
/*  44 */       if (max < value)
/*  45 */         max = value; 
/*  46 */     }  return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double min(@Nonnull double... n) {
/*  56 */     Objects.requireNonNull(n);
/*  57 */     if (n.length <= 0)
/*  58 */       throw new IllegalArgumentException("array can't be empty"); 
/*  59 */     double min = Double.POSITIVE_INFINITY;
/*  60 */     for (double value : n) {
/*  61 */       if (min > value)
/*  62 */         min = value; 
/*  63 */     }  return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int max(@Nonnull int... n) {
/*  73 */     Objects.requireNonNull(n);
/*  74 */     if (n.length <= 0)
/*  75 */       throw new IllegalArgumentException("array can't be empty"); 
/*  76 */     int max = Integer.MIN_VALUE;
/*  77 */     for (int value : n) {
/*  78 */       if (max < value)
/*  79 */         max = value; 
/*  80 */     }  return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int min(@Nonnull int... n) {
/*  90 */     Objects.requireNonNull(n);
/*  91 */     if (n.length <= 0)
/*  92 */       throw new IllegalArgumentException("array can't be empty"); 
/*  93 */     int min = Integer.MAX_VALUE;
/*  94 */     for (int value : n) {
/*  95 */       if (min > value)
/*  96 */         min = value; 
/*  97 */     }  return min;
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
/*     */ 
/*     */   
/*     */   public static int limit(int value, int floor, int ceil) {
/* 111 */     if (floor >= ceil)
/* 112 */       throw new IllegalArgumentException("floor must be smaller than ceil"); 
/* 113 */     if (value < floor)
/* 114 */       return floor; 
/* 115 */     return Math.min(value, ceil);
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
/*     */ 
/*     */   
/*     */   public static double limit(double value, double floor, double ceil) {
/* 129 */     if (floor >= ceil)
/* 130 */       throw new IllegalArgumentException("floor must be smaller than ceil"); 
/* 131 */     if (value < floor)
/* 132 */       return floor; 
/* 133 */     if (value > ceil)
/* 134 */       return ceil; 
/* 135 */     return value;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 151 */     return Math.sqrt(Math.pow(x2 - x1, 2.0D) + 
/* 152 */         Math.pow(y2 - y1, 2.0D) + 
/* 153 */         Math.pow(z2 - z1, 2.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distance(@Nonnull Vector3d a, @Nonnull Vector3d b) {
/* 164 */     return Math.sqrt(Math.pow(b.x - a.x, 2.0D) + 
/* 165 */         Math.pow(b.y - a.y, 2.0D) + 
/* 166 */         Math.pow(b.z - a.z, 2.0D));
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
/*     */   
/*     */   public static double distance(double x1, double y1, double x2, double y2) {
/* 179 */     return Math.sqrt(Math.pow(x2 - x1, 2.0D) + Math.pow(y2 - y1, 2.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDivisibleBy(int number, int divisor) {
/* 190 */     if (number == 0)
/* 191 */       return false; 
/* 192 */     while (number != 1) {
/* 193 */       if (number % 4 != 0)
/* 194 */         return false; 
/* 195 */       number >>= 2;
/*     */     } 
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double clamp(double wallA, double value, double wallB) {
/*     */     double floor;
/*     */     double ceil;
/* 207 */     if (wallA > wallB) {
/* 208 */       ceil = wallA;
/* 209 */       floor = wallB;
/* 210 */     } else if (wallA < wallB) {
/* 211 */       ceil = wallB;
/* 212 */       floor = wallA;
/*     */     }
/*     */     else {
/*     */       
/* 216 */       return wallA;
/*     */     } 
/*     */     
/* 219 */     if (value < floor) {
/* 220 */       value = floor;
/* 221 */     } else if (value > ceil || Double.isInfinite(value)) {
/* 222 */       value = ceil;
/*     */     } 
/* 224 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clamp(int wallA, int value, int wallB) {
/*     */     int floor;
/*     */     int ceil;
/* 234 */     if (wallA > wallB) {
/* 235 */       ceil = wallA;
/* 236 */       floor = wallB;
/* 237 */     } else if (wallA < wallB) {
/* 238 */       ceil = wallB;
/* 239 */       floor = wallA;
/*     */     }
/*     */     else {
/*     */       
/* 243 */       return wallA;
/*     */     } 
/*     */     
/* 246 */     if (value < floor) {
/* 247 */       value = floor;
/* 248 */     } else if (value > ceil) {
/* 249 */       value = ceil;
/*     */     } 
/* 251 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int toNearestInt(double input) {
/* 256 */     return (int)Math.round(input);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double smoothMin(double range, double a, double b) {
/* 264 */     if (range < 0.0D)
/* 265 */       throw new IllegalArgumentException("negative range"); 
/* 266 */     if (range == 0.0D) return Math.min(a, b);
/*     */     
/* 268 */     if (Math.abs(a - b) >= range)
/* 269 */       return Math.min(a, b); 
/* 270 */     double weight = clamp(0.0D, 0.5D + 0.5D * (b - a) / range, 1.0D);
/* 271 */     return Interpolation.linear(b, a, weight) - range * weight * (1.0D - weight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double smoothMax(double range, double a, double b) {
/* 279 */     if (range < 0.0D)
/* 280 */       throw new IllegalArgumentException("negative range"); 
/* 281 */     if (range == 0.0D) return Math.max(a, b);
/*     */     
/* 283 */     if (Math.abs(a - b) > range)
/* 284 */       return Math.max(a, b); 
/* 285 */     double weight = clamp(0.0D, 0.5D + 0.5D * (b - a) / range, 1.0D);
/* 286 */     return Interpolation.linear(a, b, weight) + range * weight * (1.0D - weight);
/*     */   }
/*     */   
/*     */   public static int wrap(int value, int max) {
/* 290 */     value %= max;
/* 291 */     return (value < 0) ? (value + max) : value;
/*     */   }
/*     */   
/*     */   public static int floor(int value, int gridSize) {
/* 295 */     return (value < 0) ? (
/* 296 */       value / gridSize * gridSize - ((value % gridSize != 0) ? gridSize : 0)) : (
/* 297 */       value / gridSize * gridSize);
/*     */   }
/*     */   
/*     */   public static int ceil(int value, int gridSize) {
/* 301 */     return (value >= 0) ? ((
/* 302 */       value + gridSize - 1) / gridSize * gridSize) : (
/* 303 */       value / gridSize * gridSize);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Calculator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */