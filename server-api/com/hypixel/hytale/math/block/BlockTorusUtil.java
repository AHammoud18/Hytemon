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
/*     */ public class BlockTorusUtil
/*     */ {
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int outerRadius, int minorRadius, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  27 */     if (outerRadius <= 0) throw new IllegalArgumentException(String.valueOf(outerRadius)); 
/*  28 */     if (minorRadius <= 0) throw new IllegalArgumentException(String.valueOf(minorRadius));
/*     */ 
/*     */     
/*  31 */     int majorRadius = Math.max(1, outerRadius - minorRadius);
/*     */     
/*  33 */     int sizeXZ = majorRadius + minorRadius;
/*  34 */     float minorRadiusAdjusted = minorRadius + 0.41F;
/*     */     
/*  36 */     for (int x = -sizeXZ; x <= sizeXZ; x++) {
/*  37 */       for (int z = -sizeXZ; z <= sizeXZ; z++) {
/*     */         
/*  39 */         double distFromCenter = Math.sqrt((x * x + z * z));
/*     */ 
/*     */         
/*  42 */         double distFromRing = distFromCenter - majorRadius;
/*     */         
/*  44 */         for (int y = -minorRadius; y <= minorRadius; y++) {
/*     */           
/*  46 */           double distFromTube = Math.sqrt(distFromRing * distFromRing + (y * y));
/*     */           
/*  48 */           if (distFromTube <= minorRadiusAdjusted && 
/*  49 */             !consumer.test(originX + x, originY + y, originZ + z, t)) {
/*  50 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     return true;
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
/*     */   public static <T> boolean forEachBlock(int originX, int originY, int originZ, int outerRadius, int minorRadius, int thickness, boolean capped, @Nullable T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  74 */     if (thickness < 1) {
/*  75 */       return forEachBlock(originX, originY, originZ, outerRadius, minorRadius, t, consumer);
/*     */     }
/*     */     
/*  78 */     if (outerRadius <= 0) throw new IllegalArgumentException(String.valueOf(outerRadius)); 
/*  79 */     if (minorRadius <= 0) throw new IllegalArgumentException(String.valueOf(minorRadius));
/*     */ 
/*     */     
/*  82 */     int majorRadius = Math.max(1, outerRadius - minorRadius);
/*     */     
/*  84 */     int sizeXZ = majorRadius + minorRadius;
/*  85 */     float minorRadiusAdjusted = minorRadius + 0.41F;
/*  86 */     float innerMinorRadius = Math.max(0.0F, minorRadiusAdjusted - thickness);
/*     */     
/*  88 */     for (int x = -sizeXZ; x <= sizeXZ; x++) {
/*  89 */       for (int z = -sizeXZ; z <= sizeXZ; z++) {
/*  90 */         double distFromCenter = Math.sqrt((x * x + z * z));
/*  91 */         double distFromRing = distFromCenter - majorRadius;
/*     */         
/*  93 */         for (int y = -minorRadius; y <= minorRadius; y++) {
/*  94 */           double distFromTube = Math.sqrt(distFromRing * distFromRing + (y * y));
/*     */           
/*  96 */           boolean inOuter = (distFromTube <= minorRadiusAdjusted);
/*  97 */           if (inOuter) {
/*     */             
/*  99 */             boolean inInner = (distFromTube < innerMinorRadius);
/* 100 */             if (!inInner)
/*     */             {
/* 102 */               if (!consumer.test(originX + x, originY + y, originZ + z, t))
/* 103 */                 return false;  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 108 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\block\BlockTorusUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */