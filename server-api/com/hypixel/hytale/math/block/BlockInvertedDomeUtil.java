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
/*     */ public class BlockInvertedDomeUtil
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
/*     */           
/*  52 */           if (!test(originX, originY, originZ, x, -y, z, t, consumer)) {
/*  53 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, int thickness, boolean capped, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  68 */     if (thickness < 1) {
/*  69 */       return forEachBlock(originX, originY, originZ, radiusX, radiusY, radiusZ, t, consumer);
/*     */     }
/*     */     
/*  72 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  73 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  74 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  76 */     float radiusXAdjusted = radiusX + 0.41F;
/*  77 */     float radiusYAdjusted = radiusY + 0.41F;
/*  78 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  80 */     float innerRadiusXAdjusted = radiusXAdjusted - thickness;
/*  81 */     float innerRadiusYAdjusted = radiusYAdjusted - thickness;
/*  82 */     float innerRadiusZAdjusted = radiusZAdjusted - thickness;
/*     */     
/*  84 */     float invRadiusX2 = 1.0F / radiusXAdjusted * radiusXAdjusted;
/*  85 */     float invRadiusY2 = 1.0F / radiusYAdjusted * radiusYAdjusted;
/*  86 */     float invRadiusZ2 = 1.0F / radiusZAdjusted * radiusZAdjusted;
/*     */     
/*  88 */     float invInnerRadiusX2 = 1.0F / innerRadiusXAdjusted * innerRadiusXAdjusted;
/*  89 */     float invInnerRadiusY2 = 1.0F / innerRadiusYAdjusted * innerRadiusYAdjusted;
/*  90 */     float invInnerRadiusZ2 = 1.0F / innerRadiusZAdjusted * innerRadiusZAdjusted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     for (int y = 0, y1 = 1; y <= radiusY; y++, y1++) {
/* 101 */       float qy = (y * y) * invRadiusY2;
/* 102 */       double dx = Math.sqrt((1.0F - qy)) * radiusXAdjusted;
/* 103 */       int maxX = (int)dx;
/*     */       
/* 105 */       float innerQy = (y * y) * invInnerRadiusY2;
/* 106 */       float outerQy = (y1 * y1) * invRadiusY2;
/*     */ 
/*     */       
/* 109 */       boolean isAtTop = (y == 0 && capped);
/*     */       
/* 111 */       for (int x = 0, x1 = 1; x <= maxX; x++, x1++) {
/* 112 */         float qx = (x * x) * invRadiusX2;
/* 113 */         double dz = Math.sqrt((1.0F - qx - qy)) * radiusZAdjusted;
/* 114 */         int maxZ = (int)dz;
/*     */         
/* 116 */         float innerQx = (x * x) * invInnerRadiusX2;
/* 117 */         float outerQx = (x1 * x1) * invRadiusX2;
/*     */         
/* 119 */         for (int z = 0, z1 = 1; z <= maxZ; z++, z1++) {
/* 120 */           float innerQz = (z * z) * invInnerRadiusZ2;
/*     */           
/* 122 */           if (isAtTop) {
/* 123 */             if (!test(originX, originY, originZ, x, -y, z, t, consumer)) {
/* 124 */               return false;
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/* 129 */           if (innerQx + innerQy + innerQz < 1.0F) {
/* 130 */             float outerQz = (z1 * z1) * invRadiusZ2;
/*     */             
/* 132 */             if (outerQx + outerQy + outerQz < 1.0F) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 137 */           if (!test(originX, originY, originZ, x, -y, z, t, consumer))
/* 138 */             return false; 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> boolean test(int originX, int originY, int originZ, int x, int y, int z, T context, @Nonnull TriIntObjPredicate<T> consumer) {
/* 152 */     if (!consumer.test(originX + x, originY + y, originZ + z, context)) return false;
/*     */     
/* 154 */     if (x > 0) {
/*     */       
/* 156 */       if (!consumer.test(originX - x, originY + y, originZ + z, context)) return false;
/*     */       
/* 158 */       if (z > 0 && !consumer.test(originX - x, originY + y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 161 */     if (z > 0)
/*     */     {
/* 163 */       return consumer.test(originX + x, originY + y, originZ - z, context);
/*     */     }
/*     */     
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockInvertedDomeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */