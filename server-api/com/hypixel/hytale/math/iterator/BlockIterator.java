/*     */ package com.hypixel.hytale.math.iterator;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*     */ public final class BlockIterator
/*     */ {
/*     */   private BlockIterator() {
/*  36 */     throw new UnsupportedOperationException("This is a utilitiy class. Do not instantiate.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterateFromTo(@Nonnull Vector3d origin, @Nonnull Vector3d target, @Nonnull BlockIteratorProcedure procedure) {
/*  47 */     return iterateFromTo(origin.x, origin.y, origin.z, target.x, target.y, target.z, procedure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean iterateFromTo(@Nonnull Vector3i origin, @Nonnull Vector3i target, @Nonnull BlockIteratorProcedure procedure) {
/*  58 */     return iterateFromTo(origin.x, origin.y, origin.z, target.x, target.y, target.z, procedure);
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
/*     */   public static boolean iterateFromTo(double sx, double sy, double sz, double tx, double ty, double tz, @Nonnull BlockIteratorProcedure procedure) {
/*  74 */     double dx = tx - sx;
/*  75 */     double dy = ty - sy;
/*  76 */     double dz = tz - sz;
/*  77 */     double maxDistance = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*  78 */     return iterate(sx, sy, sz, dx, dy, dz, maxDistance, procedure);
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
/*     */   public static <T> boolean iterateFromTo(double sx, double sy, double sz, double tx, double ty, double tz, @Nonnull BlockIteratorProcedurePlus1<T> procedure, T t) {
/*  94 */     double dx = tx - sx;
/*  95 */     double dy = ty - sy;
/*  96 */     double dz = tz - sz;
/*  97 */     double maxDistance = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*  98 */     return iterate(sx, sy, sz, dx, dy, dz, maxDistance, procedure, t);
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
/*     */   public static boolean iterate(@Nonnull Vector3d origin, @Nonnull Vector3d direction, double maxDistance, @Nonnull BlockIteratorProcedure procedure) {
/* 111 */     return iterate(origin.x, origin.y, origin.z, direction.x, direction.y, direction.z, maxDistance, procedure);
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
/*     */   public static boolean iterate(double sx, double sy, double sz, double dx, double dy, double dz, double maxDistance, @Nonnull BlockIteratorProcedure procedure) {
/* 130 */     checkParameters(sx, sy, sz, dx, dy, dz);
/* 131 */     return iterate0(sx, sy, sz, dx, dy, dz, maxDistance, procedure);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean iterate0(double sx, double sy, double sz, double dx, double dy, double dz, double maxDistance, @Nonnull BlockIteratorProcedure procedure) {
/* 137 */     maxDistance /= Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     
/* 139 */     int bx = (int)FastMath.fastFloor(sx), by = (int)FastMath.fastFloor(sy), bz = (int)FastMath.fastFloor(sz);
/* 140 */     double px = sx - bx, py = sy - by, pz = sz - bz;
/* 141 */     double pt = 0.0D;
/*     */     
/* 143 */     while (pt <= maxDistance) {
/* 144 */       double t = intersection(px, py, pz, dx, dy, dz);
/*     */       
/* 146 */       double qx = px + t * dx;
/* 147 */       double qy = py + t * dy;
/* 148 */       double qz = pz + t * dz;
/*     */       
/* 150 */       if (!procedure.accept(bx, by, bz, px, py, pz, qx, qy, qz)) return false;
/*     */       
/* 152 */       if (dx < 0.0D && FastMath.sEq(qx, 0.0D)) {
/* 153 */         qx++;
/* 154 */         bx--;
/* 155 */       } else if (dx > 0.0D && FastMath.gEq(qx, 1.0D)) {
/* 156 */         qx--;
/* 157 */         bx++;
/*     */       } 
/* 159 */       if (dy < 0.0D && FastMath.sEq(qy, 0.0D)) {
/* 160 */         qy++;
/* 161 */         by--;
/* 162 */       } else if (dy > 0.0D && FastMath.gEq(qy, 1.0D)) {
/* 163 */         qy--;
/* 164 */         by++;
/*     */       } 
/* 166 */       if (dz < 0.0D && FastMath.sEq(qz, 0.0D)) {
/* 167 */         qz++;
/* 168 */         bz--;
/* 169 */       } else if (dz > 0.0D && FastMath.gEq(qz, 1.0D)) {
/* 170 */         qz--;
/* 171 */         bz++;
/*     */       } 
/*     */       
/* 174 */       pt += t;
/*     */       
/* 176 */       px = qx;
/* 177 */       py = qy;
/* 178 */       pz = qz;
/*     */     } 
/* 180 */     return true;
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
/*     */   public static <T> boolean iterate(double sx, double sy, double sz, double dx, double dy, double dz, double maxDistance, @Nonnull BlockIteratorProcedurePlus1<T> procedure, T obj1) {
/* 200 */     checkParameters(sx, sy, sz, dx, dy, dz);
/* 201 */     return iterate0(sx, sy, sz, dx, dy, dz, maxDistance, procedure, obj1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> boolean iterate0(double sx, double sy, double sz, double dx, double dy, double dz, double maxDistance, @Nonnull BlockIteratorProcedurePlus1<T> procedure, T obj1) {
/* 208 */     maxDistance /= Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     
/* 210 */     int bx = (int)FastMath.fastFloor(sx), by = (int)FastMath.fastFloor(sy), bz = (int)FastMath.fastFloor(sz);
/* 211 */     double px = sx - bx, py = sy - by, pz = sz - bz;
/* 212 */     double pt = 0.0D;
/*     */     
/* 214 */     while (pt <= maxDistance) {
/* 215 */       double t = intersection(px, py, pz, dx, dy, dz);
/*     */       
/* 217 */       double qx = px + t * dx;
/* 218 */       double qy = py + t * dy;
/* 219 */       double qz = pz + t * dz;
/*     */       
/* 221 */       if (!procedure.accept(bx, by, bz, px, py, pz, qx, qy, qz, obj1)) return false;
/*     */       
/* 223 */       if (dx < 0.0D && FastMath.sEq(qx, 0.0D)) {
/* 224 */         qx++;
/* 225 */         bx--;
/* 226 */       } else if (dx > 0.0D && FastMath.gEq(qx, 1.0D)) {
/* 227 */         qx--;
/* 228 */         bx++;
/*     */       } 
/* 230 */       if (dy < 0.0D && FastMath.sEq(qy, 0.0D)) {
/* 231 */         qy++;
/* 232 */         by--;
/* 233 */       } else if (dy > 0.0D && FastMath.gEq(qy, 1.0D)) {
/* 234 */         qy--;
/* 235 */         by++;
/*     */       } 
/* 237 */       if (dz < 0.0D && FastMath.sEq(qz, 0.0D)) {
/* 238 */         qz++;
/* 239 */         bz--;
/* 240 */       } else if (dz > 0.0D && FastMath.gEq(qz, 1.0D)) {
/* 241 */         qz--;
/* 242 */         bz++;
/*     */       } 
/*     */       
/* 245 */       pt += t;
/*     */       
/* 247 */       px = qx;
/* 248 */       py = qy;
/* 249 */       pz = qz;
/*     */     } 
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkParameters(double sx, double sy, double sz, double dx, double dy, double dz) {
/* 256 */     if (isNonValidNumber(sx)) throw new IllegalArgumentException("sx is a non-valid number! Given: " + sx); 
/* 257 */     if (isNonValidNumber(sy)) throw new IllegalArgumentException("sy is a non-valid number! Given: " + sy); 
/* 258 */     if (isNonValidNumber(sz)) throw new IllegalArgumentException("sz is a non-valid number! Given: " + sz);
/*     */     
/* 260 */     if (isNonValidNumber(dx)) throw new IllegalArgumentException("dx is a non-valid number! Given: " + dx); 
/* 261 */     if (isNonValidNumber(dy)) throw new IllegalArgumentException("dy is a non-valid number! Given: " + dy); 
/* 262 */     if (isNonValidNumber(dz)) throw new IllegalArgumentException("dz is a non-valid number! Given: " + dz);
/*     */     
/* 264 */     if (isZeroDirection(dx, dy, dz)) {
/* 265 */       throw new IllegalArgumentException("Direction is ZERO! Given: (" + dx + ", " + dy + ", " + dz + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNonValidNumber(double d) {
/* 276 */     return (Double.isNaN(d) || Double.isInfinite(d));
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
/*     */   public static boolean isZeroDirection(double dx, double dy, double dz) {
/* 289 */     return (FastMath.eq(dx, 0.0D) && FastMath.eq(dy, 0.0D) && FastMath.eq(dz, 0.0D));
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
/*     */   private static double intersection(double px, double py, double pz, double dx, double dy, double dz) {
/* 306 */     double tFar = 0.0D;
/*     */ 
/*     */     
/* 309 */     if (dx < 0.0D) {
/* 310 */       double t = -px / dx;
/* 311 */       double u = pz + dz * t;
/* 312 */       double v = py + dy * t;
/* 313 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 314 */         tFar = t;
/*     */       }
/* 316 */     } else if (dx > 0.0D) {
/* 317 */       double t = (1.0D - px) / dx;
/* 318 */       double u = pz + dz * t;
/* 319 */       double v = py + dy * t;
/* 320 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 321 */         tFar = t;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     if (dy < 0.0D) {
/* 326 */       double t = -py / dy;
/* 327 */       double u = px + dx * t;
/* 328 */       double v = pz + dz * t;
/* 329 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 330 */         tFar = t;
/*     */       }
/* 332 */     } else if (dy > 0.0D) {
/* 333 */       double t = (1.0D - py) / dy;
/* 334 */       double u = px + dx * t;
/* 335 */       double v = pz + dz * t;
/* 336 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 337 */         tFar = t;
/*     */       }
/*     */     } 
/*     */     
/* 341 */     if (dz < 0.0D) {
/* 342 */       double t = -pz / dz;
/* 343 */       double u = px + dx * t;
/* 344 */       double v = py + dy * t;
/* 345 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 346 */         tFar = t;
/*     */       }
/* 348 */     } else if (dz > 0.0D) {
/* 349 */       double t = (1.0D - pz) / dz;
/* 350 */       double u = px + dx * t;
/* 351 */       double v = py + dy * t;
/* 352 */       if (t > tFar && FastMath.gEq(u, 0.0D) && FastMath.sEq(u, 1.0D) && FastMath.gEq(v, 0.0D) && FastMath.sEq(v, 1.0D)) {
/* 353 */         tFar = t;
/*     */       }
/*     */     } 
/*     */     
/* 357 */     return tFar;
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
/*     */   @FunctionalInterface
/*     */   public static interface BlockIteratorProcedure
/*     */   {
/*     */     boolean accept(int param1Int1, int param1Int2, int param1Int3, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockIteratorProcedurePlus1<T>
/*     */   {
/*     */     boolean accept(int param1Int1, int param1Int2, int param1Int3, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, T param1T);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class FastMath
/*     */   {
/*     */     static final double TWO_POWER_52 = 4.503599627370496E15D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final double ROUNDING_ERROR = 1.0E-15D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean eq(double a, double b) {
/* 414 */       return (abs(a - b) < 1.0E-15D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean sEq(double a, double b) {
/* 425 */       return (a <= b + 1.0E-15D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean gEq(double a, double b) {
/* 436 */       return (a >= b - 1.0E-15D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static double abs(double x) {
/* 446 */       return (x < 0.0D) ? -x : x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static long fastFloor(double x) {
/* 457 */       if (x >= 4.503599627370496E15D || x <= -4.503599627370496E15D) {
/* 458 */         return (long)x;
/*     */       }
/*     */       
/* 461 */       long y = (long)x;
/* 462 */       if (x < 0.0D && y != x) {
/* 463 */         y--;
/*     */       }
/*     */       
/* 466 */       if (y == 0L) {
/* 467 */         return (long)(x * y);
/*     */       }
/*     */       
/* 470 */       return y;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\BlockIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */