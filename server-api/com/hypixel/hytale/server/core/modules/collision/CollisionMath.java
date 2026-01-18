/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class CollisionMath
/*     */ {
/*  12 */   public static final ThreadLocal<Vector2d> MIN_MAX = ThreadLocal.withInitial(Vector2d::new);
/*     */   
/*     */   public CollisionMath() {
/*  15 */     throw new IllegalStateException("CollisionMath can't be instantiated");
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int DISJOINT = 0;
/*     */   
/*     */   public static final int TOUCH_X = 1;
/*     */   
/*     */   public static final int TOUCH_Y = 2;
/*     */   
/*     */   public static final int TOUCH_Z = 4;
/*     */   
/*     */   public static final int TOUCH_ANY = 7;
/*     */   
/*     */   public static final int OVERLAP_X = 8;
/*     */   public static final int OVERLAP_Y = 16;
/*     */   public static final int OVERLAP_Z = 32;
/*     */   public static final int OVERLAP_ANY = 56;
/*     */   public static final int OVERLAP_ALL = 56;
/*     */   
/*     */   public static boolean intersectVectorAABB(@Nonnull Vector3d pos, @Nonnull Vector3d vec, double x, double y, double z, @Nonnull Box box, @Nonnull Vector2d minMax) {
/*  36 */     return (intersectRayAABB(pos, vec, x, y, z, box, minMax) && minMax.x <= 1.0D);
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
/*     */   public static boolean intersectRayAABB(@Nonnull Vector3d pos, @Nonnull Vector3d ray, double x, double y, double z, @Nonnull Box box, @Nonnull Vector2d minMax) {
/*  52 */     minMax.x = 0.0D;
/*  53 */     minMax.y = Double.MAX_VALUE;
/*     */     
/*  55 */     Vector3d min = box.getMin();
/*  56 */     Vector3d max = box.getMax();
/*  57 */     return (intersect1D(pos.x, ray.getX(), x + min.x, x + max.x, minMax) && 
/*  58 */       intersect1D(pos.y, ray.getY(), y + min.y, y + max.y, minMax) && 
/*  59 */       intersect1D(pos.z, ray.getZ(), z + min.z, z + max.z, minMax) && minMax.x >= 0.0D);
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
/*     */   public static double intersectRayAABB(@Nonnull Vector3d pos, @Nonnull Vector3d ray, double x, double y, double z, @Nonnull Box box) {
/*  77 */     Vector2d minMax = MIN_MAX.get();
/*     */     
/*  79 */     return intersectRayAABB(pos, ray, x, y, z, box, minMax) ? minMax.x : -1.7976931348623157E308D;
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
/*     */   public static boolean intersectVectorAABB(@Nonnull Vector3d pos, @Nonnull Vector3d vec, double x, double y, double z, double radius, double height, @Nonnull Vector2d minMax) {
/*  96 */     return (intersectRayAABB(pos, vec, x, y, z, radius, height, minMax) && minMax.x <= 1.0D);
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
/*     */   public static boolean intersectRayAABB(@Nonnull Vector3d pos, @Nonnull Vector3d ray, double x, double y, double z, double radius, double height, @Nonnull Vector2d minMax) {
/* 113 */     minMax.x = 0.0D;
/* 114 */     minMax.y = Double.MAX_VALUE;
/*     */     
/* 116 */     return (intersect1D(pos.x, ray.getX(), x - radius, x + radius, minMax) && 
/* 117 */       intersect1D(pos.y, ray.getY(), y, y + height, minMax) && 
/* 118 */       intersect1D(pos.z, ray.getZ(), z - radius, z + radius, minMax) && minMax.x >= 0.0D);
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
/*     */   public static boolean intersectSweptAABBs(@Nonnull Vector3d posP, @Nonnull Vector3d vP, @Nonnull Box p, @Nonnull Vector3d posQ, @Nonnull Box q, @Nonnull Vector2d minMax, @Nonnull Box temp) {
/* 135 */     return intersectSweptAABBs(posP, vP, p, posQ.x, posQ.y, posQ.z, q, minMax, temp);
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
/*     */   public static boolean intersectSweptAABBs(@Nonnull Vector3d posP, @Nonnull Vector3d vP, @Nonnull Box p, double qx, double qy, double qz, @Nonnull Box q, @Nonnull Vector2d minMax, @Nonnull Box temp) {
/* 153 */     temp.assign(q).minkowskiSum(p);
/* 154 */     return intersectVectorAABB(posP, vP, qx, qy, qz, temp, minMax);
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
/*     */   public static boolean intersect1D(double p, double s, double min, double max, @Nonnull Vector2d minMax) {
/* 172 */     if (Math.abs(s) < 1.0E-5D) {
/* 173 */       return (p >= min && p <= max);
/*     */     }
/* 175 */     double t1 = (min - p) / s;
/* 176 */     double t2 = (max - p) / s;
/*     */     
/* 178 */     if (t2 >= t1) {
/* 179 */       if (t1 > minMax.x) {
/* 180 */         minMax.x = t1;
/*     */       }
/* 182 */       if (t2 < minMax.y) {
/* 183 */         minMax.y = t2;
/*     */       }
/*     */     } else {
/* 186 */       if (t2 > minMax.x) {
/* 187 */         minMax.x = t2;
/*     */       }
/* 189 */       if (t1 < minMax.y) {
/* 190 */         minMax.y = t1;
/*     */       }
/*     */     } 
/* 193 */     return (minMax.x <= minMax.y);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDisjoint(int code) {
/* 245 */     return (code == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOverlapping(int code) {
/* 255 */     return (code == 56);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTouching(int code) {
/* 265 */     return ((code & 0x7) != 0);
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
/*     */   public static int intersectAABBs(@Nonnull Vector3d p, @Nonnull Box bbP, @Nonnull Vector3d q, @Nonnull Box bbQ) {
/* 280 */     return intersectAABBs(p.x, p.y, p.z, bbP, q.x, q.y, q.z, bbQ);
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
/*     */   public static int intersectAABBs(double px, double py, double pz, @Nonnull Box bbP, double qx, double qy, double qz, @Nonnull Box bbQ) {
/* 301 */     int x = intersect1D(px, bbP.min.x, bbP.max.x, qx, bbQ.min.x, bbQ.max.x);
/* 302 */     if (x == 0) return 0; 
/* 303 */     x &= 0x9;
/* 304 */     int y = intersect1D(py, bbP.min.y, bbP.max.y, qy, bbQ.min.y, bbQ.max.y);
/* 305 */     if (y == 0) return 0; 
/* 306 */     y &= 0x12;
/* 307 */     int z = intersect1D(pz, bbP.min.z, bbP.max.z, qz, bbQ.min.z, bbQ.max.z);
/* 308 */     if (z == 0) return 0; 
/* 309 */     z &= 0x24;
/*     */     
/* 311 */     return x | y | z;
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
/*     */   public static int intersect1D(double p, double pMin, double pMax, double q, double qMin, double qMax) {
/* 328 */     return intersect1D(p, pMin, pMax, q, qMin, qMax, 1.0E-5D);
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
/*     */   
/*     */   public static int intersectAABBs(double px, double py, double pz, @Nonnull Box bbP, double qx, double qy, double qz, @Nonnull Box bbQ, double thickness) {
/* 350 */     int x = intersect1D(px, bbP.min.x, bbP.max.x, qx, bbQ.min.x, bbQ.max.x, thickness);
/* 351 */     if (x == 0) return 0; 
/* 352 */     x &= 0x9;
/* 353 */     int y = intersect1D(py, bbP.min.y, bbP.max.y, qy, bbQ.min.y, bbQ.max.y, thickness);
/* 354 */     if (y == 0) return 0; 
/* 355 */     y &= 0x12;
/* 356 */     int z = intersect1D(pz, bbP.min.z, bbP.max.z, qz, bbQ.min.z, bbQ.max.z, thickness);
/* 357 */     if (z == 0) return 0; 
/* 358 */     z &= 0x24;
/*     */     
/* 360 */     return x | y | z;
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
/*     */   public static int intersect1D(double p, double pMin, double pMax, double q, double qMin, double qMax, double thickness) {
/* 378 */     double offset = q - p;
/*     */ 
/*     */     
/* 381 */     double dist = pMin - qMax - offset;
/* 382 */     if (dist > thickness) return 0; 
/* 383 */     if (dist > -thickness) return 7;
/*     */     
/* 385 */     dist = qMin - pMax + offset;
/* 386 */     if (dist > thickness) return 0; 
/* 387 */     if (dist > -thickness) return 7;
/*     */     
/* 389 */     return 56;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionMath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */