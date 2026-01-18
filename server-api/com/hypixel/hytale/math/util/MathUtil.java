/*     */ package com.hypixel.hytale.math.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MathUtil
/*     */ {
/*  11 */   public static final double EPSILON_DOUBLE = Math.ulp(1.0D);
/*  12 */   public static final float EPSILON_FLOAT = Math.ulp(1.0F);
/*     */   
/*  14 */   public static float PITCH_EDGE_PADDING = 0.01F;
/*     */   
/*     */   public static int abs(int i) {
/*  17 */     int mask = i >> 31;
/*  18 */     return i + mask ^ mask;
/*     */   }
/*     */   
/*     */   public static int floor(double d) {
/*  22 */     int i = (int)d;
/*  23 */     if (i <= d) return i; 
/*  24 */     if (d < -2.147483648E9D) return Integer.MIN_VALUE; 
/*  25 */     return i - 1;
/*     */   }
/*     */   
/*     */   public static int ceil(double d) {
/*  29 */     int i = (int)d;
/*  30 */     if (d > 0.0D && d != i) {
/*  31 */       if (d > 2.147483647E9D) return Integer.MAX_VALUE; 
/*  32 */       return i + 1;
/*     */     } 
/*  34 */     return i;
/*     */   }
/*     */   
/*     */   public static int randomInt(int min, int max) {
/*  38 */     return ThreadLocalRandom.current().nextInt(min, max);
/*     */   }
/*     */   
/*     */   public static double randomDouble(double min, double max) {
/*  42 */     return min + Math.random() * (max - min);
/*     */   }
/*     */   
/*     */   public static float randomFloat(float min, float max) {
/*  46 */     return min + (float)Math.random() * (max - min);
/*     */   }
/*     */   
/*     */   public static double round(double d, int p) {
/*  50 */     double pow = Math.pow(10.0D, p);
/*  51 */     return Math.round(d * pow) / pow;
/*     */   }
/*     */   
/*     */   public static boolean within(double val, double min, double max) {
/*  55 */     return (val >= min && val <= max);
/*     */   }
/*     */   
/*     */   public static double minValue(double v, double a, double c) {
/*  59 */     if (a < v) v = a; 
/*  60 */     if (c < v) v = c; 
/*  61 */     return v;
/*     */   }
/*     */   
/*     */   public static int minValue(int v, int a, int c) {
/*  65 */     if (a < v) v = a; 
/*  66 */     if (c < v) v = c; 
/*  67 */     return v;
/*     */   }
/*     */   
/*     */   public static double maxValue(double v, double a, double b, double c) {
/*  71 */     if (a > v) v = a; 
/*  72 */     if (b > v) v = b; 
/*  73 */     if (c > v) v = c; 
/*  74 */     return v;
/*     */   }
/*     */   
/*     */   public static double maxValue(double v, double a, double b) {
/*  78 */     if (a > v) v = a; 
/*  79 */     if (b > v) v = b; 
/*  80 */     return v;
/*     */   }
/*     */   
/*     */   public static byte maxValue(byte v, byte a, byte b) {
/*  84 */     if (a > v) v = a; 
/*  85 */     if (b > v) v = b; 
/*  86 */     return v;
/*     */   }
/*     */   
/*     */   public static byte maxValue(byte v, byte a, byte b, byte c) {
/*  90 */     if (a > v) v = a; 
/*  91 */     if (b > v) v = b; 
/*  92 */     if (c > v) v = c; 
/*  93 */     return v;
/*     */   }
/*     */   
/*     */   public static int maxValue(int v, int a, int b) {
/*  97 */     if (a > v) v = a; 
/*  98 */     if (b > v) v = b; 
/*  99 */     return v;
/*     */   }
/*     */   
/*     */   public static double lengthSquared(double x, double y) {
/* 103 */     return x * x + y * y;
/*     */   }
/*     */   
/*     */   public static double length(double x, double y) {
/* 107 */     return Math.sqrt(lengthSquared(x, y));
/*     */   }
/*     */   
/*     */   public static double lengthSquared(double x, double y, double z) {
/* 111 */     return x * x + y * y + z * z;
/*     */   }
/*     */   
/*     */   public static double length(double x, double y, double z) {
/* 115 */     return Math.sqrt(lengthSquared(x, y, z));
/*     */   }
/*     */   
/*     */   public static double maxValue(double v, double a) {
/* 119 */     return (a > v) ? a : v;
/*     */   }
/*     */   
/*     */   public static double clipToZero(double v) {
/* 123 */     return clipToZero(v, EPSILON_DOUBLE);
/*     */   }
/*     */   
/*     */   public static double clipToZero(double v, double epsilon) {
/* 127 */     return (v >= -epsilon && v <= epsilon) ? 0.0D : v;
/*     */   }
/*     */   
/*     */   public static float clipToZero(float v) {
/* 131 */     return clipToZero(v, EPSILON_FLOAT);
/*     */   }
/*     */   
/*     */   public static float clipToZero(float v, float epsilon) {
/* 135 */     return (v >= -epsilon && v <= epsilon) ? 0.0F : v;
/*     */   }
/*     */   
/*     */   public static boolean closeToZero(double v) {
/* 139 */     return closeToZero(v, EPSILON_DOUBLE);
/*     */   }
/*     */   
/*     */   public static boolean closeToZero(double v, double epsilon) {
/* 143 */     return (v >= -epsilon && v <= epsilon);
/*     */   }
/*     */   
/*     */   public static boolean closeToZero(float v) {
/* 147 */     return closeToZero(v, EPSILON_FLOAT);
/*     */   }
/*     */   
/*     */   public static boolean closeToZero(float v, float epsilon) {
/* 151 */     return (v >= -epsilon && v <= epsilon);
/*     */   }
/*     */   
/*     */   public static double clamp(double v, double min, double max) {
/* 155 */     if (v > max) return (v < min) ? min : max; 
/* 156 */     return (v < min) ? min : v;
/*     */   }
/*     */   
/*     */   public static float clamp(float v, float min, float max) {
/* 160 */     if (v > max) return (v < min) ? min : max; 
/* 161 */     return (v < min) ? min : v;
/*     */   }
/*     */   
/*     */   public static int clamp(int v, int min, int max) {
/* 165 */     if (v > max) return (v < min) ? min : max; 
/* 166 */     return (v < min) ? min : v;
/*     */   }
/*     */   
/*     */   public static long clamp(long v, long min, long max) {
/* 170 */     if (v > max) return (v < min) ? min : max; 
/* 171 */     return (v < min) ? min : v;
/*     */   }
/*     */   
/*     */   public static int getPercentageOf(int index, int max) {
/* 175 */     return (int)(index / (max - 1.0D) * 100.0D);
/*     */   }
/*     */   
/*     */   public static double percent(int v, int total) {
/* 179 */     if (total == 0) return 0.0D; 
/* 180 */     return v * 100.0D / total;
/*     */   }
/*     */   
/*     */   public static int fastRound(float f) {
/* 184 */     return fastFloor(f + 0.5F);
/*     */   }
/*     */   
/*     */   public static long fastRound(double d) {
/* 188 */     return fastFloor(d + 0.5D);
/*     */   }
/*     */   
/*     */   public static int fastFloor(float f) {
/* 192 */     int i = (int)f;
/* 193 */     if (i <= f) return i; 
/* 194 */     if (f < -2.1474836E9F) return Integer.MIN_VALUE; 
/* 195 */     return i - 1;
/*     */   }
/*     */   
/*     */   public static long fastFloor(double d) {
/* 199 */     long i = (long)d;
/* 200 */     if (i <= d) return i; 
/* 201 */     if (d < -9.223372036854776E18D) return Long.MIN_VALUE; 
/* 202 */     return i - 1L;
/*     */   }
/*     */   
/*     */   public static int fastCeil(float f) {
/* 206 */     int i = (int)f;
/* 207 */     if (f > 0.0F && f != i) {
/* 208 */       if (f > 2.1474836E9F) return Integer.MAX_VALUE; 
/* 209 */       return i + 1;
/*     */     } 
/* 211 */     return i;
/*     */   }
/*     */   
/*     */   public static long fastCeil(double d) {
/* 215 */     long i = (long)d;
/* 216 */     if (d > 0.0D && d != i) {
/* 217 */       if (d > 9.223372036854776E18D) return Long.MAX_VALUE; 
/* 218 */       return i + 1L;
/*     */     } 
/* 220 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float halfFloatToFloat(int hbits) {
/* 229 */     int mant = hbits & 0x3FF;
/* 230 */     int exp = hbits & 0x7C00;
/* 231 */     if (exp == 31744) {
/*     */       
/* 233 */       exp = 261120;
/* 234 */     } else if (exp != 0) {
/*     */       
/* 236 */       exp += 114688;
/* 237 */       if (mant == 0 && exp > 115712)
/*     */       {
/* 239 */         return Float.intBitsToFloat((hbits & 0x8000) << 16 | exp << 13 | 0x3FF);
/*     */       }
/*     */     }
/* 242 */     else if (mant != 0) {
/*     */       
/* 244 */       exp = 115712;
/*     */       while (true) {
/* 246 */         mant <<= 1;
/* 247 */         exp -= 1024;
/* 248 */         if ((mant & 0x400) != 0)
/* 249 */         { mant &= 0x3FF; break; } 
/*     */       } 
/* 251 */     }  return Float.intBitsToFloat((hbits & 0x8000) << 16 | (exp | mant) << 13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int halfFloatFromFloat(float fval) {
/* 259 */     int fbits = Float.floatToIntBits(fval);
/* 260 */     int sign = fbits >>> 16 & 0x8000;
/* 261 */     int val = (fbits & Integer.MAX_VALUE) + 4096;
/*     */     
/* 263 */     if (val >= 1199570944) {
/*     */       
/* 265 */       if ((fbits & Integer.MAX_VALUE) >= 1199570944) {
/* 266 */         if (val < 2139095040)
/*     */         {
/* 268 */           return sign | 0x7C00;
/*     */         }
/* 270 */         return sign | 0x7C00 | (fbits & 0x7FFFFF) >>> 13;
/*     */       } 
/*     */       
/* 273 */       return sign | 0x7BFF;
/*     */     } 
/* 275 */     if (val >= 947912704)
/*     */     {
/* 277 */       return sign | val - 939524096 >>> 13;
/*     */     }
/* 279 */     if (val < 855638016)
/*     */     {
/* 281 */       return sign;
/*     */     }
/* 283 */     val = (fbits & Integer.MAX_VALUE) >>> 23;
/* 284 */     return sign | (fbits & 0x7FFFFF | 0x800000) + (8388608 >>> val - 102) >>> 126 - val;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int byteCount(int i) {
/* 290 */     if (i > 65535) return 4; 
/* 291 */     if (i > 255) return 2; 
/* 292 */     if (i > 0) return 1; 
/* 293 */     return 0;
/*     */   }
/*     */   
/*     */   public static int packInt(int x, int z) {
/* 297 */     return x << 16 | z & 0xFFFF;
/*     */   }
/*     */   
/*     */   public static int unpackLeft(int packed) {
/* 301 */     int i = packed >> 16 & 0xFFFF;
/* 302 */     if ((i & 0x8000) != 0) i |= 0xFFFF0000; 
/* 303 */     return i;
/*     */   }
/*     */   
/*     */   public static int unpackRight(int packed) {
/* 307 */     int i = packed & 0xFFFF;
/* 308 */     if ((i & 0x8000) != 0) i |= 0xFFFF0000; 
/* 309 */     return i;
/*     */   }
/*     */   
/*     */   public static long packLong(int left, int right) {
/* 313 */     return left << 32L | right & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   public static int unpackLeft(long packed) {
/* 317 */     return (int)(packed >> 32L);
/*     */   }
/*     */   
/*     */   public static int unpackRight(long packed) {
/* 321 */     return (int)packed;
/*     */   }
/*     */   @Nonnull
/*     */   public static Vector3i rotateVectorYAxis(@Nonnull Vector3i vector, int angle, boolean clockwise) {
/*     */     int x1, z1;
/* 326 */     float radAngle = 0.017453292F * angle;
/*     */ 
/*     */     
/* 329 */     if (clockwise) {
/* 330 */       x1 = (int)(vector.x * TrigMathUtil.cos(radAngle) - vector.z * TrigMathUtil.sin(radAngle));
/* 331 */       z1 = (int)(vector.x * TrigMathUtil.sin(radAngle) + vector.z * TrigMathUtil.cos(radAngle));
/*     */     } else {
/* 333 */       x1 = (int)(vector.x * TrigMathUtil.cos(radAngle) + vector.z * TrigMathUtil.sin(radAngle));
/* 334 */       z1 = (int)(-vector.x * TrigMathUtil.sin(radAngle) + vector.z * TrigMathUtil.cos(radAngle));
/*     */     } 
/*     */     
/* 337 */     return new Vector3i(x1, vector.y, z1);
/*     */   }
/*     */   @Nonnull
/*     */   public static Vector3d rotateVectorYAxis(@Nonnull Vector3d vector, int angle, boolean clockwise) {
/*     */     double x1, z1;
/* 342 */     float radAngle = 0.017453292F * angle;
/*     */ 
/*     */     
/* 345 */     if (clockwise) {
/* 346 */       x1 = vector.x * TrigMathUtil.cos(radAngle) - vector.z * TrigMathUtil.sin(radAngle);
/* 347 */       z1 = vector.x * TrigMathUtil.sin(radAngle) + vector.z * TrigMathUtil.cos(radAngle);
/*     */     } else {
/* 349 */       x1 = vector.x * TrigMathUtil.cos(radAngle) + vector.z * TrigMathUtil.sin(radAngle);
/* 350 */       z1 = -vector.x * TrigMathUtil.sin(radAngle) + vector.z * TrigMathUtil.cos(radAngle);
/*     */     } 
/*     */     
/* 353 */     return new Vector3d(x1, vector.y, z1);
/*     */   }
/*     */   
/*     */   public static float wrapAngle(float angle) {
/* 357 */     angle %= 6.2831855F;
/* 358 */     if (angle <= -3.1415927F) {
/* 359 */       angle += 6.2831855F;
/*     */     }
/* 361 */     else if (angle > 3.1415927F) {
/* 362 */       angle -= 6.2831855F;
/*     */     } 
/*     */     
/* 365 */     return angle;
/*     */   }
/*     */   
/*     */   public static float lerp(float a, float b, float t) {
/* 369 */     return lerpUnclamped(a, b, clamp(t, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public static float lerpUnclamped(float a, float b, float t) {
/* 373 */     return a + t * (b - a);
/*     */   }
/*     */   
/*     */   public static double lerp(double a, double b, double t) {
/* 377 */     return lerpUnclamped(a, b, clamp(t, 0.0D, 1.0D));
/*     */   }
/*     */   
/*     */   public static double lerpUnclamped(double a, double b, double t) {
/* 381 */     return a + t * (b - a);
/*     */   }
/*     */   
/*     */   public static float shortAngleDistance(float a, float b) {
/* 385 */     float distance = (b - a) % 6.2831855F;
/* 386 */     return 2.0F * distance % 6.2831855F - distance;
/*     */   }
/*     */   
/*     */   public static float lerpAngle(float a, float b, float t) {
/* 390 */     return a + shortAngleDistance(a, b) * t;
/*     */   }
/*     */   
/*     */   public static double floorMod(double x, double y) {
/* 394 */     return x - Math.floor(x / y) * y;
/*     */   }
/*     */   
/*     */   public static double compareAngle(double a, double b) {
/* 398 */     double diff = b - a;
/* 399 */     return floorMod(diff + Math.PI, 6.283185307179586D) - Math.PI;
/*     */   }
/*     */   
/*     */   public static double percentile(@Nonnull long[] sortedData, double percentile) {
/*     */     long left, right;
/* 404 */     if (sortedData.length == 1) return sortedData[0]; 
/* 405 */     if (percentile >= 1.0D) return sortedData[sortedData.length - 1];
/*     */     
/* 407 */     double position = (sortedData.length + 1) * percentile;
/* 408 */     double n = percentile * (sortedData.length - 1) + 1.0D;
/*     */ 
/*     */     
/* 411 */     if (position >= 1.0D) {
/* 412 */       left = sortedData[floor(n) - 1];
/* 413 */       right = sortedData[floor(n)];
/*     */     } else {
/* 415 */       left = sortedData[0];
/* 416 */       right = sortedData[1];
/*     */     } 
/*     */     
/* 419 */     if (left == right) return left;
/*     */     
/* 421 */     double part = n - floor(n);
/* 422 */     return left + part * (right - left);
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
/*     */   public static double distanceToLineSq(double x, double y, double ax, double ay, double bx, double by) {
/* 438 */     double dx0 = x - ax;
/* 439 */     double dy0 = y - ay;
/*     */     
/* 441 */     double dx1 = bx - ax;
/* 442 */     double dy1 = by - ay;
/* 443 */     return distanceToLineSq(x, y, ax, ay, bx, by, dx0, dy0, dx1, dy1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distanceToLineSq(double x, double y, double ax, double ay, double bx, double by, double dxAx, double dyAy, double dBxAx, double dByAy) {
/* 464 */     double t = dxAx * dBxAx + dyAy * dByAy;
/* 465 */     t /= dBxAx * dBxAx + dByAy * dByAy;
/*     */ 
/*     */     
/* 468 */     double px = ax;
/* 469 */     double py = ay;
/* 470 */     if (t > 1.0D) {
/* 471 */       px = bx;
/* 472 */       py = by;
/* 473 */     } else if (t > 0.0D) {
/* 474 */       px = ax + t * dBxAx;
/* 475 */       py = ay + t * dByAy;
/*     */     } 
/*     */     
/* 478 */     dBxAx = x - px;
/* 479 */     dByAy = y - py;
/*     */     
/* 481 */     return dBxAx * dBxAx + dByAy * dByAy;
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
/*     */   public static double distanceToInfLineSq(double x, double y, double ax, double ay, double bx, double by) {
/* 497 */     double dx0 = x - ax;
/* 498 */     double dy0 = y - ay;
/*     */     
/* 500 */     double dx1 = bx - ax;
/* 501 */     double dy1 = by - ay;
/*     */     
/* 503 */     return distanceToInfLineSq(x, y, ax, ay, dx0, dy0, dx1, dy1);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distanceToInfLineSq(double x, double y, double ax, double ay, double dxAx, double dyAy, double dBxAx, double dByAy) {
/* 522 */     double t = dxAx * dBxAx + dyAy * dByAy;
/* 523 */     t /= dBxAx * dBxAx + dByAy * dByAy;
/*     */ 
/*     */     
/* 526 */     double px = ax + t * dBxAx;
/* 527 */     double py = ay + t * dByAy;
/*     */     
/* 529 */     dBxAx = x - px;
/* 530 */     dByAy = y - py;
/*     */     
/* 532 */     return dBxAx * dBxAx + dByAy * dByAy;
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
/*     */   public static int sideOfLine(double x, double y, double ax, double ay, double bx, double by) {
/* 547 */     return ((ax - x) * (by - y) - (ay - y) * (bx - x) >= 0.0D) ? 1 : -1;
/*     */   }
/*     */   
/*     */   public static Vector3f getRotationForHitNormal(Vector3f normal) {
/* 551 */     if (normal == null) return Vector3f.ZERO; 
/* 552 */     if (normal.y == 1.0F) return Vector3f.ZERO; 
/* 553 */     if (normal.y == -1.0F) return new Vector3f(0.0F, 0.0F, 3.1415927F); 
/* 554 */     if (normal.x == 1.0F) return new Vector3f(0.0F, 0.0F, -1.5707964F); 
/* 555 */     if (normal.x == -1.0F) return new Vector3f(0.0F, 0.0F, 1.5707964F); 
/* 556 */     if (normal.z == 1.0F) return new Vector3f(1.5707964F, 0.0F, 0.0F); 
/* 557 */     if (normal.z == -1.0F) return new Vector3f(-1.5707964F, 0.0F, 0.0F);
/*     */     
/* 559 */     return Vector3f.ZERO;
/*     */   }
/*     */   
/*     */   public static String getNameForHitNormal(Vector3f normal) {
/* 563 */     if (normal == null) return "UP"; 
/* 564 */     if (normal.y == 1.0F) return "UP"; 
/* 565 */     if (normal.y == -1.0F) return "DOWN"; 
/* 566 */     if (normal.x == 1.0F) return "WEST"; 
/* 567 */     if (normal.x == -1.0F) return "EAST"; 
/* 568 */     if (normal.z == 1.0F) return "NORTH"; 
/* 569 */     if (normal.z == -1.0F) return "SOUTH"; 
/* 570 */     return "UP";
/*     */   }
/*     */   
/*     */   public static float mapToRange(float value, float valueMin, float valueMax, float rangeMin, float rangeMax) {
/* 574 */     float alpha = (value - valueMin) / (valueMax - valueMin);
/* 575 */     return rangeMin + alpha * (rangeMax - rangeMin);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\MathUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */