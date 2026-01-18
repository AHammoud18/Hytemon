/*     */ package com.hypixel.hytale.math.block;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockDomeUtil
/*     */ {
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  28 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  29 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  30 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  32 */     float radiusXAdjusted = radiusX + 0.41F;
/*  33 */     float radiusYAdjusted = radiusY + 0.41F;
/*  34 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  36 */     float invRadiusXSqr = 1.0F / radiusXAdjusted * radiusXAdjusted;
/*  37 */     float invRadiusYSqr = 1.0F / radiusYAdjusted * radiusYAdjusted;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     for (int x = 0; x <= radiusX; x++) {
/*  43 */       float qx = 1.0F - (x * x) * invRadiusXSqr;
/*  44 */       double dy = Math.sqrt(qx) * radiusYAdjusted;
/*  45 */       int maxY = (int)dy;
/*     */       
/*  47 */       for (int y = 0; y <= maxY; y++) {
/*  48 */         double dz = Math.sqrt((qx - (y * y) * invRadiusYSqr)) * radiusZAdjusted;
/*  49 */         int maxZ = (int)dz;
/*  50 */         for (int z = 0; z <= maxZ; z++) {
/*  51 */           if (!test(originX, originY, originZ, x, y, z, t, consumer)) {
/*  52 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  57 */     return true;
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, int thickness, boolean capped, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  77 */     if (thickness < 1) {
/*  78 */       return forEachBlock(originX, originY, originZ, radiusX, radiusY, radiusZ, t, consumer);
/*     */     }
/*     */     
/*  81 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  82 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  83 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  85 */     float radiusXAdjusted = radiusX + 0.41F;
/*  86 */     float radiusYAdjusted = radiusY + 0.41F;
/*  87 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  89 */     float innerRadiusXAdjusted = radiusXAdjusted - thickness;
/*  90 */     float innerRadiusYAdjusted = radiusYAdjusted - thickness;
/*  91 */     float innerRadiusZAdjusted = radiusZAdjusted - thickness;
/*     */     
/*  93 */     float invRadiusX2 = 1.0F / radiusXAdjusted * radiusXAdjusted;
/*  94 */     float invRadiusY2 = 1.0F / radiusYAdjusted * radiusYAdjusted;
/*  95 */     float invRadiusZ2 = 1.0F / radiusZAdjusted * radiusZAdjusted;
/*     */     
/*  97 */     float invInnerRadiusX2 = 1.0F / innerRadiusXAdjusted * innerRadiusXAdjusted;
/*  98 */     float invInnerRadiusY2 = 1.0F / innerRadiusYAdjusted * innerRadiusYAdjusted;
/*  99 */     float invInnerRadiusZ2 = 1.0F / innerRadiusZAdjusted * innerRadiusZAdjusted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     for (int y = 0, y1 = 1; y <= radiusY; y++, y1++) {
/* 113 */       float qy = (y * y) * invRadiusY2;
/* 114 */       double dx = Math.sqrt((1.0F - qy)) * radiusXAdjusted;
/* 115 */       int maxX = (int)dx;
/*     */       
/* 117 */       float innerQy = (y * y) * invInnerRadiusY2;
/* 118 */       float outerQy = (y1 * y1) * invRadiusY2;
/*     */ 
/*     */       
/* 121 */       boolean isAtBase = (y == 0 && capped);
/*     */       
/* 123 */       for (int x = 0, x1 = 1; x <= maxX; x++, x1++) {
/* 124 */         float qx = (x * x) * invRadiusX2;
/* 125 */         double dz = Math.sqrt((1.0F - qx - qy)) * radiusZAdjusted;
/* 126 */         int maxZ = (int)dz;
/*     */         
/* 128 */         float innerQx = (x * x) * invInnerRadiusX2;
/* 129 */         float outerQx = (x1 * x1) * invRadiusX2;
/*     */         
/* 131 */         for (int z = 0, z1 = 1; z <= maxZ; z++, z1++) {
/* 132 */           float innerQz = (z * z) * invInnerRadiusZ2;
/*     */ 
/*     */           
/* 135 */           if (isAtBase) {
/* 136 */             if (!test(originX, originY, originZ, x, y, z, t, consumer)) {
/* 137 */               return false;
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 143 */           if (innerQx + innerQy + innerQz < 1.0F) {
/* 144 */             float outerQz = (z1 * z1) * invRadiusZ2;
/*     */ 
/*     */             
/* 147 */             if (outerQx + outerQy + outerQz < 1.0F) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 152 */           if (!test(originX, originY, originZ, x, y, z, t, consumer))
/* 153 */             return false; 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> boolean test(int originX, int originY, int originZ, int x, int y, int z, T context, @Nonnull TriIntObjPredicate<T> consumer) {
/* 169 */     if (!consumer.test(originX + x, originY + y, originZ + z, context)) return false;
/*     */     
/* 171 */     if (x > 0) {
/*     */       
/* 173 */       if (!consumer.test(originX - x, originY + y, originZ + z, context)) return false;
/*     */       
/* 175 */       if (z > 0 && !consumer.test(originX - x, originY + y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 178 */     if (z > 0)
/*     */     {
/* 180 */       return consumer.test(originX + x, originY + y, originZ - z, context);
/*     */     }
/*     */     
/* 183 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockDomeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */