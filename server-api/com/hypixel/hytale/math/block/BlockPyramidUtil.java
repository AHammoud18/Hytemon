/*     */ package com.hypixel.hytale.math.block;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
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
/*     */ public class BlockPyramidUtil
/*     */ {
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  25 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  26 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  27 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  32 */     for (int y = height - 1; y >= 0; y--) {
/*  33 */       double rf = 1.0D - y / height;
/*  34 */       double dx = radiusX * rf;
/*  35 */       int maxX, minX = -(maxX = (int)dx);
/*  36 */       for (int x = minX; x <= maxX; x++) {
/*  37 */         double dz = radiusZ * rf;
/*  38 */         int maxZ, minZ = -(maxZ = (int)dz);
/*  39 */         for (int z = minZ; z <= maxZ; z++) {
/*  40 */           if (!consumer.test(originX + x, originY + y, originZ + z, t)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  60 */     forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, boolean capped, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  78 */     if (thickness < 1) {
/*  79 */       forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  84 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  85 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */ 
/*     */     
/*  88 */     double df = 1.0D / height;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     for (int y = height - 1; y >= 0; y--) {
/*  96 */       boolean cap = (capped && y < thickness);
/*     */       
/*  98 */       double rf = 1.0D - y * df;
/*  99 */       double dx = rf * radiusX;
/* 100 */       double dz = rf * radiusZ;
/* 101 */       int maxX, minX = -(maxX = (int)dx);
/* 102 */       int maxZ, minZ = -(maxZ = (int)dz);
/*     */       
/* 104 */       double innerRf = rf - df;
/* 105 */       double innerDx = innerRf * radiusX;
/* 106 */       double innerDz = innerRf * radiusZ;
/*     */ 
/*     */       
/* 109 */       int innerMinX = cap ? 1 : (-((int)innerDx) + thickness);
/* 110 */       int innerMaxX = cap ? 0 : ((int)innerDx - thickness);
/* 111 */       int innerMinZ = cap ? 1 : (-((int)innerDz) + thickness);
/* 112 */       int innerMaxZ = cap ? 0 : ((int)innerDz - thickness);
/*     */       
/* 114 */       for (int x = minX; x <= maxX; x++) {
/* 115 */         for (int z = minZ; z <= maxZ; z++) {
/* 116 */           if ((x < innerMinX || x > innerMaxX || z < innerMinZ || z > innerMaxZ) && 
/* 117 */             !consumer.test(originX + x, originY + y, originZ + z, t)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public static <T> void forEachBlockInverted(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 136 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/* 137 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/* 138 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     for (int y = height - 1; y >= 0; y--) {
/* 144 */       double rf = 1.0D - y / height;
/* 145 */       double dx = radiusX * rf;
/* 146 */       int maxX, minX = -(maxX = (int)dx);
/* 147 */       for (int x = minX; x <= maxX; x++) {
/* 148 */         double dz = radiusZ * rf;
/* 149 */         int maxZ, minZ = -(maxZ = (int)dz);
/* 150 */         for (int z = minZ; z <= maxZ; z++) {
/* 151 */           if (!consumer.test(originX + x, originY + height - 1 - y, originZ + z, t)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public static <T> void forEachBlockInverted(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 171 */     forEachBlockInverted(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/*     */   public static <T> void forEachBlockInverted(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, boolean capped, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 189 */     if (thickness < 1) {
/* 190 */       forEachBlockInverted(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/* 195 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/* 196 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */ 
/*     */     
/* 199 */     double df = 1.0D / height;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     for (int y = height - 1; y >= 0; y--) {
/* 207 */       boolean cap = (capped && y < thickness);
/*     */       
/* 209 */       double rf = 1.0D - y * df;
/* 210 */       double dx = rf * radiusX;
/* 211 */       double dz = rf * radiusZ;
/* 212 */       int maxX, minX = -(maxX = (int)dx);
/* 213 */       int maxZ, minZ = -(maxZ = (int)dz);
/*     */       
/* 215 */       double innerRf = rf - df;
/* 216 */       double innerDx = innerRf * radiusX;
/* 217 */       double innerDz = innerRf * radiusZ;
/*     */ 
/*     */       
/* 220 */       int innerMinX = cap ? 1 : (-((int)innerDx) + thickness);
/* 221 */       int innerMaxX = cap ? 0 : ((int)innerDx - thickness);
/* 222 */       int innerMinZ = cap ? 1 : (-((int)innerDz) + thickness);
/* 223 */       int innerMaxZ = cap ? 0 : ((int)innerDz - thickness);
/*     */       
/* 225 */       for (int x = minX; x <= maxX; x++) {
/* 226 */         for (int z = minZ; z <= maxZ; z++) {
/* 227 */           if ((x < innerMinX || x > innerMaxX || z < innerMinZ || z > innerMaxZ) && 
/* 228 */             !consumer.test(originX + x, originY + height - 1 - y, originZ + z, t))
/*     */             return; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockPyramidUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */