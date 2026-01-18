/*     */ package com.hypixel.hytale.math.block;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
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
/*     */ public class BlockCylinderUtil
/*     */ {
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  28 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  29 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  30 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  32 */     float radiusXAdjusted = radiusX + 0.41F;
/*  33 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  35 */     double invRadiusXSqr = 1.0D / (radiusXAdjusted * radiusXAdjusted);
/*     */ 
/*     */ 
/*     */     
/*  39 */     for (int x = -radiusX; x <= radiusX; x++) {
/*  40 */       double qx = 1.0D - (x * x) * invRadiusXSqr;
/*  41 */       double dz = Math.sqrt(qx) * radiusZAdjusted;
/*  42 */       int maxZ, minZ = -(maxZ = (int)dz);
/*  43 */       for (int z = minZ; z <= maxZ; z++) {
/*  44 */         for (int y = height - 1; y >= 0; y--) {
/*  45 */           if (!consumer.test(originX + x, originY + y, originZ + z, t)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/*  49 */     return true;
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  65 */     return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, thickness, false, t, consumer);
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int height, int radiusZ, int thickness, boolean capped, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  82 */     if (thickness < 1) {
/*  83 */       return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */     }
/*     */     
/*  86 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  87 */     if (height <= 0) throw new IllegalArgumentException(String.valueOf(height)); 
/*  88 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  90 */     float radiusXAdjusted = radiusX + 0.41F;
/*  91 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  93 */     float innerRadiusXAdjusted = radiusXAdjusted - thickness;
/*  94 */     float innerRadiusZAdjusted = radiusZAdjusted - thickness;
/*     */ 
/*     */     
/*  97 */     if (innerRadiusXAdjusted <= 0.0F || innerRadiusZAdjusted <= 0.0F) {
/*  98 */       return forEachBlock(originX, originY, originZ, radiusX, height, radiusZ, t, consumer);
/*     */     }
/*     */     
/* 101 */     double invRadiusXSqr = 1.0D / (radiusXAdjusted * radiusXAdjusted);
/* 102 */     double invInnerRadiusXSqr = 1.0D / (innerRadiusXAdjusted * innerRadiusXAdjusted);
/*     */ 
/*     */ 
/*     */     
/* 106 */     int innerMinY = thickness, innerMaxY = height - thickness;
/*     */     
/* 108 */     for (int y = height - 1; y >= 0; y--) {
/* 109 */       boolean cap = (capped && (y < innerMinY || y > innerMaxY));
/* 110 */       for (int x = -radiusX; x <= radiusX; x++) {
/* 111 */         double qx = 1.0D - (x * x) * invRadiusXSqr;
/* 112 */         double dz = Math.sqrt(qx) * radiusZAdjusted;
/* 113 */         int maxZ = (int)dz;
/*     */         
/* 115 */         double innerQx = (x < innerRadiusXAdjusted) ? (1.0D - (x * x) * invInnerRadiusXSqr) : 0.0D;
/* 116 */         double innerDZ = (innerQx > 0.0D) ? (Math.sqrt(innerQx) * innerRadiusZAdjusted) : 0.0D;
/* 117 */         int minZ = cap ? 0 : MathUtil.ceil(innerDZ);
/*     */         
/* 119 */         int z = minZ;
/* 120 */         if (z == 0) {
/* 121 */           if (!consumer.test(originX + x, originY + y, originZ, t)) return false; 
/* 122 */           z++;
/*     */         } 
/* 124 */         for (; z <= maxZ; z++) {
/* 125 */           if (!consumer.test(originX + x, originY + y, originZ + z, t)) return false; 
/* 126 */           if (!consumer.test(originX + x, originY + y, originZ - z, t)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockCylinderUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */