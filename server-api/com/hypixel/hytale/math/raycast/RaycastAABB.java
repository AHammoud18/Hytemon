/*     */ package com.hypixel.hytale.math.raycast;
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
/*     */ public class RaycastAABB
/*     */ {
/*     */   public static final double EPSILON = -1.0E-8D;
/*     */   
/*     */   public static double intersect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double ox, double oy, double oz, double dx, double dy, double dz) {
/*  18 */     double tNear = Double.POSITIVE_INFINITY;
/*     */     
/*  20 */     double t = (minX - ox) / dx;
/*  21 */     if (t < tNear && t > -1.0E-8D) {
/*  22 */       double u = oz + dz * t;
/*  23 */       double v = oy + dy * t;
/*  24 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY)
/*     */       {
/*  26 */         tNear = t;
/*     */       }
/*     */     } 
/*  29 */     t = (maxX - ox) / dx;
/*  30 */     if (t < tNear && t > -1.0E-8D) {
/*  31 */       double u = oz + dz * t;
/*  32 */       double v = oy + dy * t;
/*  33 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY)
/*     */       {
/*  35 */         tNear = t;
/*     */       }
/*     */     } 
/*  38 */     t = (minY - oy) / dy;
/*  39 */     if (t < tNear && t > -1.0E-8D) {
/*  40 */       double u = ox + dx * t;
/*  41 */       double v = oz + dz * t;
/*  42 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ)
/*     */       {
/*  44 */         tNear = t;
/*     */       }
/*     */     } 
/*  47 */     t = (maxY - oy) / dy;
/*  48 */     if (t < tNear && t > -1.0E-8D) {
/*  49 */       double u = ox + dx * t;
/*  50 */       double v = oz + dz * t;
/*  51 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ)
/*     */       {
/*  53 */         tNear = t;
/*     */       }
/*     */     } 
/*  56 */     t = (minZ - oz) / dz;
/*  57 */     if (t < tNear && t > -1.0E-8D) {
/*  58 */       double u = ox + dx * t;
/*  59 */       double v = oy + dy * t;
/*  60 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY)
/*     */       {
/*  62 */         tNear = t;
/*     */       }
/*     */     } 
/*  65 */     t = (maxZ - oz) / dz;
/*  66 */     if (t < tNear && t > -1.0E-8D) {
/*  67 */       double u = ox + dx * t;
/*  68 */       double v = oy + dy * t;
/*  69 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY)
/*     */       {
/*  71 */         tNear = t;
/*     */       }
/*     */     } 
/*  74 */     return tNear;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void intersect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double ox, double oy, double oz, double dx, double dy, double dz, @Nonnull RaycastConsumer consumer) {
/*  83 */     double tNear = Double.POSITIVE_INFINITY;
/*  84 */     double nx = 0.0D, ny = 0.0D, nz = 0.0D;
/*     */     
/*  86 */     double t = (minX - ox) / dx;
/*  87 */     if (t < tNear && t > -1.0E-8D) {
/*  88 */       double u = oz + dz * t;
/*  89 */       double v = oy + dy * t;
/*  90 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/*  92 */         tNear = t;
/*  93 */         nx = -1.0D;
/*     */       } 
/*     */     } 
/*  96 */     t = (maxX - ox) / dx;
/*  97 */     if (t < tNear && t > -1.0E-8D) {
/*  98 */       double u = oz + dz * t;
/*  99 */       double v = oy + dy * t;
/* 100 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 102 */         tNear = t;
/* 103 */         nx = 1.0D;
/*     */       } 
/*     */     } 
/* 106 */     t = (minY - oy) / dy;
/* 107 */     if (t < tNear && t > -1.0E-8D) {
/* 108 */       double u = ox + dx * t;
/* 109 */       double v = oz + dz * t;
/* 110 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 112 */         tNear = t;
/* 113 */         ny = -1.0D;
/*     */       } 
/*     */     } 
/* 116 */     t = (maxY - oy) / dy;
/* 117 */     if (t < tNear && t > -1.0E-8D) {
/* 118 */       double u = ox + dx * t;
/* 119 */       double v = oz + dz * t;
/* 120 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 122 */         tNear = t;
/* 123 */         ny = 1.0D;
/*     */       } 
/*     */     } 
/* 126 */     t = (minZ - oz) / dz;
/* 127 */     if (t < tNear && t > -1.0E-8D) {
/* 128 */       double u = ox + dx * t;
/* 129 */       double v = oy + dy * t;
/* 130 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 132 */         tNear = t;
/* 133 */         nz = -1.0D;
/*     */       } 
/*     */     } 
/* 136 */     t = (maxZ - oz) / dz;
/* 137 */     if (t < tNear && t > -1.0E-8D) {
/* 138 */       double u = ox + dx * t;
/* 139 */       double v = oy + dy * t;
/* 140 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 142 */         tNear = t;
/* 143 */         nz = 1.0D;
/*     */       } 
/*     */     } 
/* 146 */     consumer.accept((tNear != Double.POSITIVE_INFINITY), ox, oy, oz, dx, dy, dz, tNear, nx, ny, nz);
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
/*     */   public static <T> void intersect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double ox, double oy, double oz, double dx, double dy, double dz, @Nonnull RaycastConsumerPlus1<T> consumer, T obj1) {
/* 162 */     double tNear = Double.POSITIVE_INFINITY;
/* 163 */     double nx = 0.0D, ny = 0.0D, nz = 0.0D;
/*     */     
/* 165 */     double t = (minX - ox) / dx;
/* 166 */     if (t < tNear && t > -1.0E-8D) {
/* 167 */       double u = oz + dz * t;
/* 168 */       double v = oy + dy * t;
/* 169 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 171 */         tNear = t;
/* 172 */         nx = -1.0D;
/*     */       } 
/*     */     } 
/* 175 */     t = (maxX - ox) / dx;
/* 176 */     if (t < tNear && t > -1.0E-8D) {
/* 177 */       double u = oz + dz * t;
/* 178 */       double v = oy + dy * t;
/* 179 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 181 */         tNear = t;
/* 182 */         nx = 1.0D;
/*     */       } 
/*     */     } 
/* 185 */     t = (minY - oy) / dy;
/* 186 */     if (t < tNear && t > -1.0E-8D) {
/* 187 */       double u = ox + dx * t;
/* 188 */       double v = oz + dz * t;
/* 189 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 191 */         tNear = t;
/* 192 */         ny = -1.0D;
/*     */       } 
/*     */     } 
/* 195 */     t = (maxY - oy) / dy;
/* 196 */     if (t < tNear && t > -1.0E-8D) {
/* 197 */       double u = ox + dx * t;
/* 198 */       double v = oz + dz * t;
/* 199 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 201 */         tNear = t;
/* 202 */         ny = 1.0D;
/*     */       } 
/*     */     } 
/* 205 */     t = (minZ - oz) / dz;
/* 206 */     if (t < tNear && t > -1.0E-8D) {
/* 207 */       double u = ox + dx * t;
/* 208 */       double v = oy + dy * t;
/* 209 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 211 */         tNear = t;
/* 212 */         nz = -1.0D;
/*     */       } 
/*     */     } 
/* 215 */     t = (maxZ - oz) / dz;
/* 216 */     if (t < tNear && t > -1.0E-8D) {
/* 217 */       double u = ox + dx * t;
/* 218 */       double v = oy + dy * t;
/* 219 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 221 */         tNear = t;
/* 222 */         nz = 1.0D;
/*     */       } 
/*     */     } 
/* 225 */     consumer.accept((tNear != Double.POSITIVE_INFINITY), ox, oy, oz, dx, dy, dz, tNear, nx, ny, nz, obj1);
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
/*     */   public static <T, K> void intersect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double ox, double oy, double oz, double dx, double dy, double dz, @Nonnull RaycastConsumerPlus2<T, K> consumer, T obj1, K obj2) {
/* 242 */     double tNear = Double.POSITIVE_INFINITY;
/* 243 */     double nx = 0.0D, ny = 0.0D, nz = 0.0D;
/*     */     
/* 245 */     double t = (minX - ox) / dx;
/* 246 */     if (t < tNear && t > -1.0E-8D) {
/* 247 */       double u = oz + dz * t;
/* 248 */       double v = oy + dy * t;
/* 249 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 251 */         tNear = t;
/* 252 */         nx = -1.0D;
/*     */       } 
/*     */     } 
/* 255 */     t = (maxX - ox) / dx;
/* 256 */     if (t < tNear && t > -1.0E-8D) {
/* 257 */       double u = oz + dz * t;
/* 258 */       double v = oy + dy * t;
/* 259 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 261 */         tNear = t;
/* 262 */         nx = 1.0D;
/*     */       } 
/*     */     } 
/* 265 */     t = (minY - oy) / dy;
/* 266 */     if (t < tNear && t > -1.0E-8D) {
/* 267 */       double u = ox + dx * t;
/* 268 */       double v = oz + dz * t;
/* 269 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 271 */         tNear = t;
/* 272 */         ny = -1.0D;
/*     */       } 
/*     */     } 
/* 275 */     t = (maxY - oy) / dy;
/* 276 */     if (t < tNear && t > -1.0E-8D) {
/* 277 */       double u = ox + dx * t;
/* 278 */       double v = oz + dz * t;
/* 279 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 281 */         tNear = t;
/* 282 */         ny = 1.0D;
/*     */       } 
/*     */     } 
/* 285 */     t = (minZ - oz) / dz;
/* 286 */     if (t < tNear && t > -1.0E-8D) {
/* 287 */       double u = ox + dx * t;
/* 288 */       double v = oy + dy * t;
/* 289 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 291 */         tNear = t;
/* 292 */         nz = -1.0D;
/*     */       } 
/*     */     } 
/* 295 */     t = (maxZ - oz) / dz;
/* 296 */     if (t < tNear && t > -1.0E-8D) {
/* 297 */       double u = ox + dx * t;
/* 298 */       double v = oy + dy * t;
/* 299 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 301 */         tNear = t;
/* 302 */         nz = 1.0D;
/*     */       } 
/*     */     } 
/* 305 */     consumer.accept((tNear != Double.POSITIVE_INFINITY), ox, oy, oz, dx, dy, dz, tNear, nx, ny, nz, obj1, obj2);
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
/*     */   public static <T, K, L> void intersect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double ox, double oy, double oz, double dx, double dy, double dz, @Nonnull RaycastConsumerPlus3<T, K, L> consumer, T obj1, K obj2, L obj3) {
/* 322 */     double tNear = Double.POSITIVE_INFINITY;
/* 323 */     double nx = 0.0D, ny = 0.0D, nz = 0.0D;
/*     */     
/* 325 */     double t = (minX - ox) / dx;
/* 326 */     if (t < tNear && t > -1.0E-8D) {
/* 327 */       double u = oz + dz * t;
/* 328 */       double v = oy + dy * t;
/* 329 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 331 */         tNear = t;
/* 332 */         nx = -1.0D;
/*     */       } 
/*     */     } 
/* 335 */     t = (maxX - ox) / dx;
/* 336 */     if (t < tNear && t > -1.0E-8D) {
/* 337 */       double u = oz + dz * t;
/* 338 */       double v = oy + dy * t;
/* 339 */       if (u >= minZ && u <= maxZ && v >= minY && v <= maxY) {
/*     */         
/* 341 */         tNear = t;
/* 342 */         nx = 1.0D;
/*     */       } 
/*     */     } 
/* 345 */     t = (minY - oy) / dy;
/* 346 */     if (t < tNear && t > -1.0E-8D) {
/* 347 */       double u = ox + dx * t;
/* 348 */       double v = oz + dz * t;
/* 349 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 351 */         tNear = t;
/* 352 */         ny = -1.0D;
/*     */       } 
/*     */     } 
/* 355 */     t = (maxY - oy) / dy;
/* 356 */     if (t < tNear && t > -1.0E-8D) {
/* 357 */       double u = ox + dx * t;
/* 358 */       double v = oz + dz * t;
/* 359 */       if (u >= minX && u <= maxX && v >= minZ && v <= maxZ) {
/*     */         
/* 361 */         tNear = t;
/* 362 */         ny = 1.0D;
/*     */       } 
/*     */     } 
/* 365 */     t = (minZ - oz) / dz;
/* 366 */     if (t < tNear && t > -1.0E-8D) {
/* 367 */       double u = ox + dx * t;
/* 368 */       double v = oy + dy * t;
/* 369 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 371 */         tNear = t;
/* 372 */         nz = -1.0D;
/*     */       } 
/*     */     } 
/* 375 */     t = (maxZ - oz) / dz;
/* 376 */     if (t < tNear && t > -1.0E-8D) {
/* 377 */       double u = ox + dx * t;
/* 378 */       double v = oy + dy * t;
/* 379 */       if (u >= minX && u <= maxX && v >= minY && v <= maxY) {
/*     */         
/* 381 */         tNear = t;
/* 382 */         nz = 1.0D;
/*     */       } 
/*     */     } 
/* 385 */     consumer.accept((tNear != Double.POSITIVE_INFINITY), ox, oy, oz, dx, dy, dz, tNear, nx, ny, nz, obj1, obj2, obj3);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RaycastConsumer {
/*     */     void accept(boolean param1Boolean, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RaycastConsumerPlus1<T> {
/*     */     void accept(boolean param1Boolean, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RaycastConsumerPlus2<T, K> {
/*     */     void accept(boolean param1Boolean, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, T param1T, K param1K);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RaycastConsumerPlus3<T, K, L> {
/*     */     void accept(boolean param1Boolean, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, T param1T, K param1K, L param1L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\raycast\RaycastAABB.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */