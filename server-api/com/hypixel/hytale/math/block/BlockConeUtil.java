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
/*     */ 
/*     */ 
/*     */ public class BlockConeUtil
/*     */ {
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  27 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  28 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  29 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  31 */     float radiusXAdjusted = radiusX + 0.41F;
/*  32 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */ 
/*     */ 
/*     */     
/*  36 */     for (int y = height - 1; y >= 0; y--) {
/*  37 */       double rf = 1.0D - y / height;
/*  38 */       double dx = radiusXAdjusted * rf;
/*  39 */       int maxX, minX = -(maxX = (int)dx);
/*  40 */       for (int x = minX; x <= maxX; x++) {
/*  41 */         double qx = 1.0D - (x * x) / dx * dx;
/*  42 */         double dz = Math.sqrt(qx) * radiusZAdjusted * rf;
/*  43 */         int maxZ, minZ = -(maxZ = (int)dz);
/*  44 */         for (int z = minZ; z <= maxZ; z++) {
/*  45 */           if (!consumer.test(originX + x, originY + y, originZ + z, t)) {
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
/*  65 */     forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/*  83 */     if (thickness < 1) {
/*  84 */       forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */       
/*     */       return;
/*     */     } 
/*  88 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  89 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  90 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  92 */     float radiusXAdjusted = radiusX + 0.41F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     for (int y = height - 1; y >= 0; y--) {
/*  99 */       boolean cap = (capped && y < thickness);
/* 100 */       double rf = 1.0D - y / height;
/*     */       
/* 102 */       double dx = radiusXAdjusted * rf;
/* 103 */       double dxInvSqr = 1.0D / dx * dx;
/*     */ 
/*     */       
/* 106 */       double innerDx = (dx > thickness) ? (dx - thickness) : 0.0D;
/* 107 */       double innerDxInvSqr = (innerDx > 0.0D) ? (1.0D / innerDx * innerDx) : 0.0D;
/*     */       
/* 109 */       int maxX, minX = -(maxX = (int)dx);
/* 110 */       for (int x = minX; x <= maxX; x++) {
/* 111 */         double dz = Math.sqrt(1.0D - (x * x) * dxInvSqr) * dx;
/* 112 */         int maxZ, minZ = -(maxZ = (int)dz);
/*     */         
/* 114 */         double innerMaxZ = cap ? 0.0D : (Math.sqrt(1.0D - (x * x) * innerDxInvSqr) * innerDx);
/* 115 */         double innerMinZ = cap ? 0.0D : -innerMaxZ;
/* 116 */         for (int z = minZ; z <= maxZ; z++) {
/* 117 */           if ((z <= innerMinZ || z >= innerMaxZ) && 
/* 118 */             !consumer.test(originX + x, originY + y, originZ + z, t)) {
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
/* 137 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/* 138 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/* 139 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/* 141 */     float radiusXAdjusted = radiusX + 0.41F;
/* 142 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */ 
/*     */ 
/*     */     
/* 146 */     for (int y = height - 1; y >= 0; y--) {
/* 147 */       double rf = 1.0D - y / height;
/* 148 */       double dx = radiusXAdjusted * rf;
/* 149 */       int maxX, minX = -(maxX = (int)dx);
/* 150 */       for (int x = minX; x <= maxX; x++) {
/* 151 */         double qx = 1.0D - (x * x) / dx * dx;
/* 152 */         double dz = Math.sqrt(qx) * radiusZAdjusted * rf;
/* 153 */         int maxZ, minZ = -(maxZ = (int)dz);
/* 154 */         for (int z = minZ; z <= maxZ; z++) {
/* 155 */           if (!consumer.test(originX + x, originY + height - 1 - y, originZ + z, t))
/*     */             return; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static <T> void forEachBlockInverted(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 162 */     forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/* 180 */     if (thickness < 1) {
/* 181 */       forEachBlockInverted(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */       
/*     */       return;
/*     */     } 
/* 185 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/* 186 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/* 187 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/* 189 */     float radiusXAdjusted = radiusX + 0.41F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     for (int y = height - 1; y >= 0; y--) {
/* 196 */       boolean cap = (capped && y < thickness);
/* 197 */       double rf = 1.0D - y / height;
/*     */       
/* 199 */       double dx = radiusXAdjusted * rf;
/* 200 */       double dxInvSqr = 1.0D / dx * dx;
/*     */ 
/*     */       
/* 203 */       double innerDx = (dx > thickness) ? (dx - thickness) : 0.0D;
/* 204 */       double innerDxInvSqr = (innerDx > 0.0D) ? (1.0D / innerDx * innerDx) : 0.0D;
/*     */       
/* 206 */       int maxX, minX = -(maxX = (int)dx);
/* 207 */       for (int x = minX; x <= maxX; x++) {
/* 208 */         double dz = Math.sqrt(1.0D - (x * x) * dxInvSqr) * dx;
/* 209 */         int maxZ, minZ = -(maxZ = (int)dz);
/*     */         
/* 211 */         double innerMaxZ = cap ? 0.0D : (Math.sqrt(1.0D - (x * x) * innerDxInvSqr) * innerDx);
/* 212 */         double innerMinZ = cap ? 0.0D : -innerMaxZ;
/* 213 */         for (int z = minZ; z <= maxZ; z++) {
/* 214 */           if ((z <= innerMinZ || z >= innerMaxZ) && 
/* 215 */             !consumer.test(originX + x, originY + height - 1 - y, originZ + z, t))
/*     */             return; 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockConeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */