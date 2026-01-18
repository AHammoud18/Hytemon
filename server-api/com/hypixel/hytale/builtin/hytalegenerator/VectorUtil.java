/*     */ package com.hypixel.hytale.builtin.hytalegenerator;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleObjectPair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VectorUtil
/*     */ {
/*     */   public static boolean areasOverlap(@Nonnull Vector3d minA, @Nonnull Vector3d maxA, @Nonnull Vector3d minB, @Nonnull Vector3d maxB) {
/*  22 */     return (isAnyGreater(maxA, minB) && 
/*  23 */       isAnySmaller(minA, maxB));
/*     */   }
/*     */   
/*     */   public static double distanceToSegment3d(@Nonnull Vector3d point, @Nonnull Vector3d p0, @Nonnull Vector3d p1) {
/*  27 */     Vector3d lineVec = p1.clone().addScaled(p0, -1.0D);
/*  28 */     Vector3d pointVec = point.clone().addScaled(p0, -1.0D);
/*     */     
/*  30 */     double lineLength = lineVec.length();
/*  31 */     Vector3d lineUnitVec = lineVec.clone().setLength(1.0D);
/*     */     
/*  33 */     Vector3d pointVecScaled = pointVec.clone().scale(1.0D / lineLength);
/*  34 */     double t = lineUnitVec.dot(pointVecScaled);
/*  35 */     t = Calculator.clamp(0.0D, t, 1.0D);
/*     */     
/*  37 */     Vector3d nearestPoint = lineVec.clone().scale(t);
/*     */     
/*  39 */     return nearestPoint.distanceTo(pointVec);
/*     */   }
/*     */   
/*     */   public static double distanceToLine3d(@Nonnull Vector3d point, @Nonnull Vector3d p0, @Nonnull Vector3d p1) {
/*  43 */     Vector3d lineVec = p1.clone().addScaled(p0, -1.0D);
/*  44 */     Vector3d pointVec = point.clone().addScaled(p0, -1.0D);
/*     */     
/*  46 */     double lineLength = lineVec.length();
/*  47 */     Vector3d lineUnitVec = lineVec.clone().setLength(1.0D);
/*     */     
/*  49 */     Vector3d pointVecScaled = pointVec.clone().scale(1.0D / lineLength);
/*  50 */     double t = lineUnitVec.dot(pointVecScaled);
/*     */     
/*  52 */     Vector3d nearestPoint = lineVec.clone().scale(t);
/*  53 */     return nearestPoint.distanceTo(pointVec);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d nearestPointOnSegment3d(@Nonnull Vector3d point, @Nonnull Vector3d p0, @Nonnull Vector3d p1) {
/*  58 */     Vector3d lineVec = p1.clone().addScaled(p0, -1.0D);
/*  59 */     Vector3d pointVec = point.clone().addScaled(p0, -1.0D);
/*     */     
/*  61 */     double lineLength = lineVec.length();
/*  62 */     Vector3d lineUnitVec = lineVec.clone().setLength(1.0D);
/*     */     
/*  64 */     Vector3d pointVecScaled = pointVec.clone().scale(1.0D / lineLength);
/*  65 */     double t = lineUnitVec.dot(pointVecScaled);
/*  66 */     t = Calculator.clamp(0.0D, t, 1.0D);
/*     */     
/*  68 */     Vector3d nearestPoint = lineVec.clone().scale(t);
/*  69 */     return nearestPoint.add(p0);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d nearestPointOnLine3d(@Nonnull Vector3d point, @Nonnull Vector3d p0, @Nonnull Vector3d p1) {
/*  74 */     Vector3d lineVec = p1.clone().addScaled(p0, -1.0D);
/*  75 */     Vector3d pointVec = point.clone().addScaled(p0, -1.0D);
/*     */     
/*  77 */     double lineLength = lineVec.length();
/*  78 */     Vector3d lineUnitVec = lineVec.clone().setLength(1.0D);
/*     */     
/*  80 */     Vector3d pointVecScaled = pointVec.clone().scale(1.0D / lineLength);
/*  81 */     double t = lineUnitVec.dot(pointVecScaled);
/*     */     
/*  83 */     Vector3d nearestPoint = lineVec.clone().scale(t);
/*  84 */     return nearestPoint.add(p0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] shortestSegmentBetweenTwoSegments(@Nonnull Vector3d a0, @Nonnull Vector3d a1, @Nonnull Vector3d b0, @Nonnull Vector3d b1, boolean clamp, @Nonnull Vector3d p0Out, @Nonnull Vector3d p1Out) {
/*  95 */     boolean[] flags = new boolean[2];
/*     */ 
/*     */     
/*  98 */     Vector3d A = a1.clone().addScaled(a0, -1.0D);
/*  99 */     Vector3d B = b1.clone().addScaled(b0, -1.0D);
/* 100 */     double magA = A.length();
/* 101 */     double magB = B.length();
/*     */     
/* 103 */     Vector3d _A = A.clone().scale(1.0D / magA);
/* 104 */     Vector3d _B = B.clone().scale(1.0D / magB);
/*     */     
/* 106 */     Vector3d cross = _A.cross(_B);
/* 107 */     double denom = Math.pow(cross.length(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     if (denom == 0.0D) {
/* 115 */       flags[0] = true;
/* 116 */       double d0 = _A.dot(b0.clone().addScaled(a0, -1.0D));
/*     */ 
/*     */       
/* 119 */       if (clamp) {
/* 120 */         double d1 = _A.dot(b1.clone().addScaled(a0, -1.0D));
/*     */ 
/*     */         
/* 123 */         if (d0 <= 0.0D && d1 <= 0.0D) {
/* 124 */           if (Math.abs(d0) < Math.abs(d1)) {
/* 125 */             p0Out.assign(a0);
/* 126 */             p1Out.assign(b0);
/* 127 */             flags[1] = true;
/* 128 */             return flags;
/*     */           } 
/*     */           
/* 131 */           p0Out.assign(a0);
/* 132 */           p1Out.assign(b1);
/* 133 */           flags[1] = true;
/* 134 */           return flags;
/*     */         } 
/*     */ 
/*     */         
/* 138 */         if (d0 >= magA && d1 >= magA) {
/* 139 */           if (Math.abs(d0) < Math.abs(d1)) {
/* 140 */             p0Out.assign(a1);
/* 141 */             p1Out.assign(b0);
/* 142 */             flags[1] = true;
/* 143 */             return flags;
/*     */           } 
/*     */           
/* 146 */           p0Out.assign(a1);
/* 147 */           p1Out.assign(b1);
/* 148 */           flags[1] = true;
/* 149 */           return flags;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 154 */       return flags;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     Vector3d t = b0.clone().addScaled(a0, -1.0D);
/* 159 */     double detA = determinant(t, _B, cross);
/* 160 */     double detB = determinant(t, _A, cross);
/*     */     
/* 162 */     double t0 = detA / denom;
/* 163 */     double t1 = detB / denom;
/*     */     
/* 165 */     Vector3d pA = _A.clone().scale(t0).add(a0);
/* 166 */     Vector3d pB = _B.clone().scale(t1).add(b0);
/*     */ 
/*     */     
/* 169 */     if (clamp) {
/* 170 */       if (t0 < 0.0D) {
/* 171 */         pA = a0.clone();
/* 172 */       } else if (t0 > magA) {
/* 173 */         pA = a1.clone();
/*     */       } 
/*     */       
/* 176 */       if (t1 < 0.0D) {
/* 177 */         pB = b0.clone();
/* 178 */       } else if (t1 > magB) {
/* 179 */         pB = b1.clone();
/*     */       } 
/*     */ 
/*     */       
/* 183 */       if (t0 < 0.0D || t0 > magA) {
/* 184 */         double dot = _B.dot(pA.clone().addScaled(b0, -1.0D));
/* 185 */         if (dot < 0.0D) {
/* 186 */           dot = 0.0D;
/* 187 */         } else if (dot > magB) {
/* 188 */           dot = magB;
/*     */         } 
/* 190 */         pB = b0.clone().add(_B.clone().scale(dot));
/*     */       } 
/*     */ 
/*     */       
/* 194 */       if (t1 < 0.0D || t1 > magA) {
/* 195 */         double dot = _A.dot(pB.clone().addScaled(a0, -1.0D));
/* 196 */         if (dot < 0.0D) {
/* 197 */           dot = 0.0D;
/* 198 */         } else if (dot > magA) {
/* 199 */           dot = magA;
/*     */         } 
/* 201 */         pA = a0.clone().add(_A.clone().scale(dot));
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     p0Out.assign(pA);
/* 206 */     p1Out.assign(pB);
/* 207 */     flags[1] = true;
/* 208 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double shortestDistanceBetweenTwoSegments(@Nonnull Vector3d a0, @Nonnull Vector3d a1, @Nonnull Vector3d b0, @Nonnull Vector3d b1, boolean clamp) {
/* 218 */     Vector3d A = a1.clone().addScaled(a0, -1.0D);
/* 219 */     Vector3d B = b1.clone().addScaled(b0, -1.0D);
/* 220 */     double magA = A.length();
/* 221 */     double magB = B.length();
/*     */     
/* 223 */     Vector3d _A = A.clone().scale(1.0D / magA);
/* 224 */     Vector3d _B = B.clone().scale(1.0D / magB);
/*     */     
/* 226 */     Vector3d cross = _A.cross(_B);
/* 227 */     double denom = Math.pow(cross.length(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (denom == 0.0D) {
/* 233 */       double d0 = _A.dot(b0.clone().addScaled(a0, -1.0D));
/*     */ 
/*     */       
/* 236 */       if (clamp) {
/* 237 */         double d1 = _A.dot(b1.clone().addScaled(a0, -1.0D));
/*     */ 
/*     */         
/* 240 */         if (d0 <= 0.0D && d1 <= 0.0D) {
/* 241 */           if (Math.abs(d0) < Math.abs(d1)) {
/* 242 */             return a0.distanceTo(b0);
/*     */           }
/*     */           
/* 245 */           return a0.distanceTo(b1);
/*     */         } 
/*     */ 
/*     */         
/* 249 */         if (d0 >= magA && d1 >= magA) {
/* 250 */           if (Math.abs(d0) < Math.abs(d1)) {
/* 251 */             return a1.distanceTo(b0);
/*     */           }
/*     */           
/* 254 */           return a1.distanceTo(b1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 259 */       return distanceToLine3d(a0, b0, b1);
/*     */     } 
/*     */ 
/*     */     
/* 263 */     Vector3d t = b0.clone().addScaled(a0, -1.0D);
/* 264 */     double detA = determinant(t, _B, cross);
/* 265 */     double detB = determinant(t, _A, cross);
/*     */     
/* 267 */     double t0 = detA / denom;
/* 268 */     double t1 = detB / denom;
/*     */     
/* 270 */     Vector3d pA = _A.clone().scale(t0).add(a0);
/* 271 */     Vector3d pB = _B.clone().scale(t1).add(b0);
/*     */ 
/*     */     
/* 274 */     if (clamp) {
/* 275 */       if (t0 < 0.0D) {
/* 276 */         pA = a0.clone();
/* 277 */       } else if (t0 > magA) {
/* 278 */         pA = a1.clone();
/*     */       } 
/*     */       
/* 281 */       if (t1 < 0.0D) {
/* 282 */         pB = b0.clone();
/* 283 */       } else if (t1 > magB) {
/* 284 */         pB = b1.clone();
/*     */       } 
/*     */ 
/*     */       
/* 288 */       if (t0 < 0.0D || t0 > magA) {
/* 289 */         double dot = _B.dot(pA.clone().addScaled(b0, -1.0D));
/* 290 */         if (dot < 0.0D) {
/* 291 */           dot = 0.0D;
/* 292 */         } else if (dot > magB) {
/* 293 */           dot = magB;
/*     */         } 
/* 295 */         pB = b0.clone().add(_B.clone().scale(dot));
/*     */       } 
/*     */ 
/*     */       
/* 299 */       if (t1 < 0.0D || t1 > magA) {
/* 300 */         double dot = _A.dot(pB.clone().addScaled(a0, -1.0D));
/* 301 */         if (dot < 0.0D) {
/* 302 */           dot = 0.0D;
/* 303 */         } else if (dot > magA) {
/* 304 */           dot = magA;
/*     */         } 
/* 306 */         pA = a0.clone().add(_A.clone().scale(dot));
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     return pA.distanceTo(pB);
/*     */   }
/*     */   
/*     */   public static double determinant(@Nonnull Vector3d v1, @Nonnull Vector3d v2) {
/* 314 */     Vector3d crossProduct = v1.cross(v2);
/* 315 */     return crossProduct.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double determinant(@Nonnull Vector3d a, @Nonnull Vector3d b, @Nonnull Vector3d c) {
/* 322 */     double det = a.x * b.y * c.z + b.x * c.y * a.z + c.x * a.y * b.z;
/* 323 */     det -= a.z * b.y * c.x + b.z * c.y * a.x + c.z * a.y * b.x;
/* 324 */     return det;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleObjectPair<Vector3d> distanceAndNearestPointOnSegment3d(@Nonnull Vector3d point, @Nonnull Vector3d p0, @Nonnull Vector3d p1) {
/* 329 */     Vector3d lineVec = p1.clone().addScaled(p0, -1.0D);
/* 330 */     Vector3d pointVec = point.clone().addScaled(p0, -1.0D);
/*     */     
/* 332 */     double lineLength = lineVec.length();
/* 333 */     Vector3d lineUnitVec = lineVec.clone().setLength(1.0D);
/*     */     
/* 335 */     Vector3d pointVecScaled = pointVec.clone().scale(1.0D / lineLength);
/* 336 */     double t = lineUnitVec.dot(pointVecScaled);
/* 337 */     t = Calculator.clamp(0.0D, t, 1.0D);
/*     */     
/* 339 */     Vector3d nearestPoint = lineVec.clone().scale(t);
/* 340 */     return DoubleObjectPair.of(nearestPoint.distanceTo(pointVec), nearestPoint.add(p0));
/*     */   }
/*     */   
/*     */   public static double angle(@Nonnull Vector3d a, @Nonnull Vector3d b) {
/* 344 */     double top = a.x * b.x + a.y * b.y + a.z * b.z;
/* 345 */     double bottomLeft = Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
/* 346 */     double bottomRight = Math.sqrt(b.x * b.x + b.y * b.y + b.z * b.z);
/* 347 */     return Math.acos(top / bottomLeft * bottomRight);
/*     */   }
/*     */   
/*     */   public static void rotateAroundAxis(@Nonnull Vector3d vec, @Nonnull Vector3d axis, double theta) {
/* 351 */     Vector3d unitAxis = new Vector3d(axis);
/* 352 */     unitAxis.normalize();
/*     */ 
/*     */ 
/*     */     
/* 356 */     double xPrime = unitAxis.x * (unitAxis.x * vec.x + unitAxis.y * vec.y + unitAxis.z * vec.z) * (1.0D - Math.cos(theta)) + vec.x * Math.cos(theta) + (-unitAxis.z * vec.y + unitAxis.y * vec.z) * Math.sin(theta);
/*     */ 
/*     */     
/* 359 */     double yPrime = unitAxis.y * (unitAxis.x * vec.x + unitAxis.y * vec.y + unitAxis.z * vec.z) * (1.0D - Math.cos(theta)) + vec.y * Math.cos(theta) + (unitAxis.z * vec.x - unitAxis.x * vec.z) * Math.sin(theta);
/*     */ 
/*     */     
/* 362 */     double zPrime = unitAxis.z * (unitAxis.x * vec.x + unitAxis.y * vec.y + unitAxis.z * vec.z) * (1.0D - Math.cos(theta)) + vec.z * Math.cos(theta) + (-unitAxis.y * vec.x + unitAxis.x * vec.y) * Math.sin(theta);
/*     */     
/* 364 */     vec.x = xPrime;
/* 365 */     vec.y = yPrime;
/* 366 */     vec.z = zPrime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rotateVectorByAxisAngle(@Nonnull Vector3d vec, @Nonnull Vector3d axis, double angle) {
/* 373 */     Vector3d crossProd = axis.cross(vec);
/* 374 */     double cosAngle = Math.cos(angle);
/* 375 */     double sinAngle = Math.sin(angle);
/*     */     
/* 377 */     double x = vec.x * cosAngle + crossProd.x * sinAngle + axis.x * axis.dot(vec) * (1.0D - cosAngle);
/* 378 */     double y = vec.y * cosAngle + crossProd.y * sinAngle + axis.y * axis.dot(vec) * (1.0D - cosAngle);
/* 379 */     double z = vec.z * cosAngle + crossProd.z * sinAngle + axis.z * axis.dot(vec) * (1.0D - cosAngle);
/* 380 */     vec.x = x;
/* 381 */     vec.y = y;
/* 382 */     vec.z = z;
/*     */   }
/*     */   
/*     */   public static boolean isInside(@Nonnull Vector3i point, @Nonnull Vector3i min, @Nonnull Vector3i max) {
/* 386 */     return (point.x >= min.x && point.x < max.x && point.y >= min.y && point.y < max.y && point.z >= min.z && point.z < max.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInside(@Nonnull Vector3d point, @Nonnull Vector3d min, @Nonnull Vector3d max) {
/* 392 */     return (!isAnySmaller(point, min) && 
/* 393 */       isSmaller(point, max));
/*     */   }
/*     */   
/*     */   public static boolean isAnySmaller(@Nonnull Vector3d point, @Nonnull Vector3d limit) {
/* 397 */     return (point.x < limit.x || point.y < limit.y || point.z < limit.z);
/*     */   }
/*     */   
/*     */   public static boolean isSmaller(@Nonnull Vector3d point, @Nonnull Vector3d limit) {
/* 401 */     return (point.x < limit.x && point.y < limit.y && point.z < limit.z);
/*     */   }
/*     */   
/*     */   public static boolean isAnyGreater(@Nonnull Vector3d point, @Nonnull Vector3d limit) {
/* 405 */     return (point.x > limit.x || point.y > limit.y || point.z > limit.z);
/*     */   }
/*     */   
/*     */   public static boolean isAnySmaller(@Nonnull Vector3i point, @Nonnull Vector3i limit) {
/* 409 */     return (point.x < limit.x || point.y < limit.y || point.z < limit.z);
/*     */   }
/*     */   
/*     */   public static boolean isAnyGreater(@Nonnull Vector3i point, @Nonnull Vector3i limit) {
/* 413 */     return (point.x > limit.x || point.y > limit.y || point.z > limit.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInside(@Nonnull Vector2d point, @Nonnull Vector2d min, @Nonnull Vector2d max) {
/* 419 */     return (!isAnySmaller(point, min) && 
/* 420 */       isSmaller(point, max));
/*     */   }
/*     */   
/*     */   public static boolean isAnySmaller(@Nonnull Vector2d point, @Nonnull Vector2d limit) {
/* 424 */     return (point.x < limit.x || point.y < limit.y);
/*     */   }
/*     */   
/*     */   public static boolean isSmaller(@Nonnull Vector2d point, @Nonnull Vector2d limit) {
/* 428 */     return (point.x < limit.x && point.y < limit.y);
/*     */   }
/*     */   
/*     */   public static boolean isAnyGreater(@Nonnull Vector2d point, @Nonnull Vector2d limit) {
/* 432 */     return (point.x > limit.x || point.y > limit.y);
/*     */   }
/*     */   
/*     */   public static boolean isAnySmaller(@Nonnull Vector2i point, @Nonnull Vector2i limit) {
/* 436 */     return (point.x < limit.x || point.y < limit.y);
/*     */   }
/*     */   
/*     */   public static boolean isSmaller(@Nonnull Vector2i point, @Nonnull Vector2i limit) {
/* 440 */     return (point.x < limit.x && point.y < limit.y);
/*     */   }
/*     */   
/*     */   public static boolean isAnyGreater(@Nonnull Vector2i point, @Nonnull Vector2i limit) {
/* 444 */     return (point.x > limit.x || point.y > limit.y);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3i fromOperation(@Nonnull Vector3i v1, @Nonnull Vector3i v2, @Nonnull BiOperation3i operation) {
/* 449 */     return new Vector3i(operation
/* 450 */         .run(v1.x, v2.x, Retriever.ofIndex(0)), operation
/* 451 */         .run(v1.y, v2.y, Retriever.ofIndex(1)), operation
/* 452 */         .run(v1.z, v2.z, Retriever.ofIndex(2)));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3i fromOperation(@Nonnull NakedOperation3i operation) {
/* 457 */     return new Vector3i(operation
/* 458 */         .run(Retriever.ofIndex(0)), operation
/* 459 */         .run(Retriever.ofIndex(1)), operation
/* 460 */         .run(Retriever.ofIndex(2)));
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
/*     */   public static class Retriever
/*     */   {
/*     */     private int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Retriever(int index) {
/* 486 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 490 */       return this.index;
/*     */     }
/*     */     
/*     */     public int from(@Nonnull Vector3i vec) {
/* 494 */       switch (this.index) { case 0: 
/*     */         case 1:
/*     */         
/*     */         case 2:
/* 498 */          }  throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int from(@Nonnull Vector2i vec) {
/* 503 */       switch (this.index) { case 0:
/*     */         
/*     */         case 1:
/* 506 */          }  throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double from(@Nonnull Vector3d vec) {
/* 511 */       switch (this.index) { case 0: 
/*     */         case 1:
/*     */         
/*     */         case 2:
/* 515 */          }  throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double from(@Nonnull Vector2d vec) {
/* 520 */       switch (this.index) { case 0:
/*     */         
/*     */         case 1:
/* 523 */          }  throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static Retriever ofIndex(int index) {
/* 529 */       return new Retriever(index);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bitShiftRight(int shift, @Nonnull Vector3i vector) {
/* 536 */     if (shift < 0)
/* 537 */       throw new IllegalArgumentException("negative shift"); 
/* 538 */     vector.x >>= shift;
/* 539 */     vector.y >>= shift;
/* 540 */     vector.z >>= shift;
/* 541 */     vector.dropHash();
/*     */   }
/*     */   
/*     */   public static void bitShiftLeft(int shift, @Nonnull Vector3i vector) {
/* 545 */     if (shift < 0)
/* 546 */       throw new IllegalArgumentException("negative shift"); 
/* 547 */     vector.x <<= shift;
/* 548 */     vector.y <<= shift;
/* 549 */     vector.z <<= shift;
/* 550 */     vector.dropHash();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static List<Vector2i> orderByDistanceFrom(@Nonnull Vector2i origin, @Nonnull List<Vector2i> vectors) {
/* 558 */     ArrayList<Pair<Double, Vector2i>> distances = new ArrayList<>(vectors.size());
/*     */     
/* 560 */     for (int i = 0; i < vectors.size(); i++) {
/* 561 */       Vector2i vec = vectors.get(i);
/* 562 */       double distance = Calculator.distance(vec.x, vec.y, origin.x, origin.y);
/* 563 */       distances.add(Pair.of(Double.valueOf(distance), vec));
/*     */     } 
/*     */     
/* 566 */     distances.sort(Comparator.comparingDouble(Pair::first));
/* 567 */     ArrayList<Vector2i> sorted = new ArrayList<>(distances.size());
/* 568 */     for (Pair<Double, Vector2i> pair : distances) {
/* 569 */       sorted.add((Vector2i)pair.second());
/*     */     }
/*     */     
/* 572 */     return sorted;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BiOperation3i {
/*     */     int run(int param1Int1, int param1Int2, @Nonnull VectorUtil.Retriever param1Retriever);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface NakedOperation3i {
/*     */     int run(@Nonnull VectorUtil.Retriever param1Retriever);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Operation3i {
/*     */     int run(int param1Int, @Nonnull VectorUtil.Retriever param1Retriever);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\VectorUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */