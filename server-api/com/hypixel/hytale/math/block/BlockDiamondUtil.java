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
/*     */ public class BlockDiamondUtil
/*     */ {
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  28 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  29 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  30 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  32 */     float radiusXAdjusted = radiusX + 0.41F;
/*  33 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  35 */     for (int y = 0; y <= radiusY; y++) {
/*     */       
/*  37 */       float normalizedY = y / radiusY;
/*  38 */       float currentRadiusX = radiusXAdjusted * (1.0F - normalizedY);
/*  39 */       float currentRadiusZ = radiusZAdjusted * (1.0F - normalizedY);
/*     */       
/*  41 */       int maxX = (int)currentRadiusX;
/*  42 */       int maxZ = (int)currentRadiusZ;
/*     */       
/*  44 */       for (int x = 0; x <= maxX; x++) {
/*  45 */         for (int z = 0; z <= maxZ; z++) {
/*     */           
/*  47 */           if (Math.abs(x) <= currentRadiusX && Math.abs(z) <= currentRadiusZ && 
/*  48 */             !test(originX, originY, originZ, x, y, z, t, consumer)) {
/*  49 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int radiusX, int radiusY, int radiusZ, int thickness, boolean capped, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  65 */     if (thickness < 1) {
/*  66 */       return forEachBlock(originX, originY, originZ, radiusX, radiusY, radiusZ, t, consumer);
/*     */     }
/*     */     
/*  69 */     if (radiusX <= 0) throw new IllegalArgumentException(String.valueOf(radiusX)); 
/*  70 */     if (radiusY <= 0) throw new IllegalArgumentException(String.valueOf(radiusY)); 
/*  71 */     if (radiusZ <= 0) throw new IllegalArgumentException(String.valueOf(radiusZ));
/*     */     
/*  73 */     float radiusXAdjusted = radiusX + 0.41F;
/*  74 */     float radiusZAdjusted = radiusZ + 0.41F;
/*     */     
/*  76 */     for (int y = 0; y <= radiusY; y++) {
/*  77 */       float normalizedY = y / radiusY;
/*  78 */       float currentRadiusX = radiusXAdjusted * (1.0F - normalizedY);
/*  79 */       float currentRadiusZ = radiusZAdjusted * (1.0F - normalizedY);
/*  80 */       float innerRadiusX = Math.max(0.0F, currentRadiusX - thickness);
/*  81 */       float innerRadiusZ = Math.max(0.0F, currentRadiusZ - thickness);
/*     */       
/*  83 */       int maxX = (int)currentRadiusX;
/*  84 */       int maxZ = (int)currentRadiusZ;
/*     */       
/*  86 */       for (int x = 0; x <= maxX; x++) {
/*  87 */         for (int z = 0; z <= maxZ; z++) {
/*  88 */           boolean inOuter = (Math.abs(x) <= currentRadiusX && Math.abs(z) <= currentRadiusZ);
/*  89 */           if (inOuter) {
/*     */             
/*  91 */             boolean inInner = (Math.abs(x) < innerRadiusX && Math.abs(z) < innerRadiusZ);
/*  92 */             if (!inInner)
/*     */             {
/*  94 */               if (!test(originX, originY, originZ, x, y, z, t, consumer))
/*  95 */                 return false;  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> boolean test(int originX, int originY, int originZ, int x, int y, int z, T context, @Nonnull TriIntObjPredicate<T> consumer) {
/* 108 */     if (!consumer.test(originX + x, originY + y, originZ + z, context)) return false;
/*     */ 
/*     */     
/* 111 */     if (y > 0)
/*     */     {
/* 113 */       if (!consumer.test(originX + x, originY - y, originZ + z, context)) return false;
/*     */     
/*     */     }
/* 116 */     if (x > 0) {
/*     */       
/* 118 */       if (!consumer.test(originX - x, originY + y, originZ + z, context)) return false;
/*     */       
/* 120 */       if (y > 0 && !consumer.test(originX - x, originY - y, originZ + z, context)) return false;
/*     */       
/* 122 */       if (z > 0 && !consumer.test(originX - x, originY + y, originZ - z, context)) return false;
/*     */       
/* 124 */       if (y > 0 && z > 0 && !consumer.test(originX - x, originY - y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 127 */     if (z > 0) {
/*     */       
/* 129 */       if (!consumer.test(originX + x, originY + y, originZ - z, context)) return false;
/*     */       
/* 131 */       if (y > 0 && !consumer.test(originX + x, originY - y, originZ - z, context)) return false;
/*     */     
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockDiamondUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */