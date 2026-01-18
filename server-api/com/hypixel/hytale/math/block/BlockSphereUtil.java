/*     */ package com.hypixel.hytale.math.block;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
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
/*     */ public class BlockSphereUtil
/*     */ {
/*     */   public static <T> void forEachBlockExact(int originX, int originY, int originZ, double radius, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  28 */     if (radius <= 0.0D) throw new IllegalArgumentException(String.valueOf(radius));
/*     */     
/*  30 */     int ceiledRadius = MathUtil.ceil(radius);
/*     */     
/*  32 */     double invRadiusXSqr = 1.0D / (ceiledRadius * ceiledRadius);
/*  33 */     double invRadiusYSqr = 1.0D / (ceiledRadius * ceiledRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     for (int x = -ceiledRadius; x <= ceiledRadius; x++) {
/*  39 */       double qx = 1.0D - (x * x) * invRadiusXSqr;
/*  40 */       double dy = Math.sqrt(qx) * ceiledRadius;
/*  41 */       int maxY, minY = -(maxY = (int)dy);
/*  42 */       for (int y = maxY; y >= minY; y--) {
/*  43 */         double dz = Math.sqrt(qx - (y * y) * invRadiusYSqr) * ceiledRadius;
/*  44 */         int maxZ, minZ = -(maxZ = (int)dz);
/*  45 */         for (int z = minZ; z <= maxZ; z++) {
/*  46 */           if (!consumer.test(originX + x, originY + y, originZ + z, t)) {
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
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radius, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  64 */     forEachBlock(originX, originY, originZ, radius, radius, radius, t, consumer);
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  82 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  83 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  84 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  86 */     float radiusXAdjusted = radiusX + 0.41F;
/*  87 */     float radiusYAdjusted = radiusY + 0.41F;
/*  88 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  90 */     float invRadiusXSqr = 1.0F / radiusXAdjusted * radiusXAdjusted;
/*  91 */     float invRadiusYSqr = 1.0F / radiusYAdjusted * radiusYAdjusted;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     for (int x = 0; x <= radiusX; x++) {
/*  97 */       float qx = 1.0F - (x * x) * invRadiusXSqr;
/*  98 */       double dy = Math.sqrt(qx) * radiusYAdjusted;
/*  99 */       int maxY = (int)dy;
/* 100 */       for (int y = 0; y <= maxY; y++) {
/* 101 */         double dz = Math.sqrt((qx - (y * y) * invRadiusYSqr)) * radiusZAdjusted;
/* 102 */         int maxZ = (int)dz;
/* 103 */         for (int z = 0; z <= maxZ; z++) {
/* 104 */           if (!test(originX, originY, originZ, x, y, z, t, consumer)) {
/* 105 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 110 */     return true;
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
/*     */   public static <T> void forEachBlock(int originX, int originY, int originZ, int radius, int thickness, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 126 */     forEachBlock(originX, originY, originZ, radius, radius, radius, thickness, t, consumer);
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, int thickness, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 144 */     if (thickness < 1) {
/* 145 */       return forEachBlock(originX, originY, originZ, radiusX, radiusY, radiusZ, t, consumer);
/*     */     }
/*     */     
/* 148 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/* 149 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/* 150 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/* 152 */     float radiusXAdjusted = radiusX + 0.41F;
/* 153 */     float radiusYAdjusted = radiusY + 0.41F;
/* 154 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/* 156 */     float innerRadiusXAdjusted = radiusXAdjusted - thickness;
/* 157 */     float innerRadiusYAdjusted = radiusYAdjusted - thickness;
/* 158 */     float innerRadiusZAdjusted = radiusZAdjusted - thickness;
/*     */     
/* 160 */     float invRadiusX2 = 1.0F / radiusXAdjusted * radiusXAdjusted;
/* 161 */     float invRadiusY2 = 1.0F / radiusYAdjusted * radiusYAdjusted;
/* 162 */     float invRadiusZ2 = 1.0F / radiusZAdjusted * radiusZAdjusted;
/*     */     
/* 164 */     float invInnerRadiusX2 = 1.0F / innerRadiusXAdjusted * innerRadiusXAdjusted;
/* 165 */     float invInnerRadiusY2 = 1.0F / innerRadiusYAdjusted * innerRadiusYAdjusted;
/* 166 */     float invInnerRadiusZ2 = 1.0F / innerRadiusZAdjusted * innerRadiusZAdjusted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     for (int y = 0, y1 = 1; y <= radiusY; y++, y1++) {
/* 181 */       float qy = (y * y) * invRadiusY2;
/* 182 */       double dx = Math.sqrt((1.0F - qy)) * radiusXAdjusted;
/* 183 */       int maxX = (int)dx;
/*     */       
/* 185 */       float innerQy = (y * y) * invInnerRadiusY2;
/* 186 */       float outerQy = (y1 * y1) * invRadiusY2;
/*     */       
/* 188 */       for (int x = 0, x1 = 1; x <= maxX; x++, x1++) {
/* 189 */         float qx = (x * x) * invRadiusX2;
/* 190 */         double dz = Math.sqrt((1.0F - qx - qy)) * radiusZAdjusted;
/* 191 */         int maxZ = (int)dz;
/*     */         
/* 193 */         float innerQx = (x * x) * invInnerRadiusX2;
/* 194 */         float outerQx = (x1 * x1) * invRadiusX2;
/*     */         
/* 196 */         for (int z = 0, z1 = 1; z <= maxZ; z++, z1++) {
/* 197 */           float innerQz = (z * z) * invInnerRadiusZ2;
/*     */ 
/*     */           
/* 200 */           if (innerQx + innerQy + innerQz < 1.0F) {
/* 201 */             float outerQz = (z1 * z1) * invRadiusZ2;
/*     */ 
/*     */             
/* 204 */             if (outerQx + outerQy + outerQz < 1.0F) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 209 */           if (!test(originX, originY, originZ, x, y, z, t, consumer))
/* 210 */             return false; 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 215 */     return true;
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
/*     */   private static <T> boolean test(int originX, int originY, int originZ, int x, int y, int z, T context, @Nonnull TriIntObjPredicate<T> consumer) {
/* 238 */     if (!consumer.test(originX + x, originY + y, originZ + z, context)) return false;
/*     */     
/* 240 */     if (x > 0) {
/*     */       
/* 242 */       if (!consumer.test(originX - x, originY + y, originZ + z, context)) return false;
/*     */       
/* 244 */       if (y > 0 && !consumer.test(originX - x, originY - y, originZ + z, context)) return false;
/*     */       
/* 246 */       if (z > 0 && !consumer.test(originX - x, originY + y, originZ - z, context)) return false;
/*     */       
/* 248 */       if (y > 0 && z > 0 && !consumer.test(originX - x, originY - y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 251 */     if (y > 0) {
/*     */       
/* 253 */       if (!consumer.test(originX + x, originY - y, originZ + z, context)) return false;
/*     */       
/* 255 */       if (z > 0 && !consumer.test(originX + x, originY - y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 258 */     if (z > 0)
/*     */     {
/* 260 */       return consumer.test(originX + x, originY + y, originZ - z, context);
/*     */     }
/*     */     
/* 263 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockSphereUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */