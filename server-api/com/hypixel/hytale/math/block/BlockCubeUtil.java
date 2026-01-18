/*     */ package com.hypixel.hytale.math.block;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
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
/*     */ public class BlockCubeUtil
/*     */ {
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  22 */     int radiusY = height / 2;
/*  23 */     for (int dx = -radiusX; dx <= radiusX; dx++) {
/*  24 */       for (int dz = -radiusZ; dz <= radiusZ; dz++) {
/*  25 */         for (int dy = -radiusY; dy <= radiusY; dy++) {
/*  26 */           if (!consumer.test(originX + dx, originY + dy, originZ + dz, t)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/*  30 */     return true;
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  47 */     return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, boolean capped, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  65 */     return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, capped, capped, false, t, consumer);
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, boolean cappedTop, boolean cappedBottom, boolean hollow, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  85 */     if (thickness < 1) {
/*  86 */       return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */     }
/*     */     
/*  89 */     int radiusY = height / 2;
/*     */     
/*  91 */     int innerMinX = -radiusX + thickness;
/*  92 */     int innerMaxX = radiusX - thickness;
/*  93 */     int innerMinZ = -radiusZ + thickness;
/*  94 */     int innerMaxZ = radiusZ - thickness;
/*  95 */     int innerMinY = cappedBottom ? (-radiusY + thickness) : -height;
/*  96 */     int innerMaxY = cappedTop ? (radiusY - thickness) : height;
/*     */     
/*  98 */     for (int dx = -radiusX; dx <= radiusX; dx++) {
/*  99 */       for (int dz = -radiusZ; dz <= radiusZ; dz++) {
/* 100 */         for (int dy = -radiusY; dy <= radiusY; dy++) {
/* 101 */           if (dy < innerMinY || dy > innerMaxY || dx < innerMinX || dx > innerMaxX || dz < innerMinZ || dz > innerMaxZ)
/* 102 */           { if (!consumer.test(originX + dx, originY + dy, originZ + dz, t)) return false;  }
/* 103 */           else if (hollow && 
/* 104 */             !consumer.test(originX + dx, originY + dy, originZ + dz, t)) { return false; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 109 */     return true;
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
/*     */   public static <T> boolean forEachBlock(@Nonnull Vector3i pointOne, @Nonnull Vector3i pointTwo, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 121 */     Vector3i min = Vector3i.min(pointOne, pointTwo);
/* 122 */     Vector3i max = Vector3i.max(pointOne, pointTwo);
/*     */     
/* 124 */     for (int x = min.x; x <= max.x; x++) {
/* 125 */       for (int z = min.z; z <= max.z; z++) {
/* 126 */         for (int y = min.y; y <= max.y; y++) {
/* 127 */           if (!consumer.test(x, y, z, t)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     return true;
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
/*     */   public static <T> boolean forEachBlock(@Nonnull Vector3i pointOne, @Nonnull Vector3i pointTwo, int thickness, boolean cappedTop, boolean cappedBottom, boolean hollow, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 147 */     if (thickness < 1) {
/* 148 */       return forEachBlock(pointOne, pointTwo, t, consumer);
/*     */     }
/*     */     
/* 151 */     Vector3i min = Vector3i.min(pointOne, pointTwo);
/* 152 */     Vector3i max = Vector3i.max(pointOne, pointTwo);
/*     */     
/* 154 */     int innerMinX = min.x + thickness;
/* 155 */     int innerMaxX = max.x - thickness;
/* 156 */     int innerMinZ = min.z + thickness;
/* 157 */     int innerMaxZ = max.z - thickness;
/* 158 */     int innerMinY = cappedBottom ? (min.y + thickness) : min.y;
/* 159 */     int innerMaxY = cappedTop ? (max.y - thickness) : max.y;
/*     */     
/* 161 */     for (int x = min.x; x <= max.x; x++) {
/* 162 */       for (int z = min.z; z <= max.z; z++) {
/* 163 */         for (int y = min.y; y <= max.y; y++) {
/* 164 */           if (hollow)
/* 165 */           { if (y >= innerMinY && y <= innerMaxY && x >= innerMinX && x <= innerMaxX && z >= innerMinZ && z <= innerMaxZ && 
/* 166 */               !consumer.test(x, y, z, t)) return false;
/*     */              }
/*     */           
/* 169 */           else if ((y < innerMinY || y > innerMaxY || x < innerMinX || x > innerMaxX || z < innerMinZ || z > innerMaxZ) && 
/* 170 */             !consumer.test(x, y, z, t)) { return false; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockCubeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */