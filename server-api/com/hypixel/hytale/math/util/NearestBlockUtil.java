/*     */ package com.hypixel.hytale.math.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.DoubleUnaryOperator;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NearestBlockUtil
/*     */ {
/*     */   public static final IterationElement[] DEFAULT_ELEMENTS;
/*     */   
/*     */   private NearestBlockUtil() {
/*  18 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   static {
/*  21 */     DEFAULT_ELEMENTS = new IterationElement[] { new IterationElement(-1, 0, 0, x -> 0.0D, y -> y, z -> z), new IterationElement(1, 0, 0, x -> 1.0D, y -> y, z -> z), new IterationElement(0, -1, 0, x -> x, y -> 0.0D, z -> z), new IterationElement(0, 1, 0, x -> x, y -> 1.0D, z -> z), new IterationElement(0, 0, -1, x -> x, y -> y, z -> 0.0D), new IterationElement(0, 0, 1, x -> x, y -> y, z -> 1.0D) };
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
/*     */   @Nullable
/*     */   public static <T> Vector3i findNearestBlock(@Nonnull Vector3d position, @Nonnull BiPredicate<Vector3i, T> validBlock, T t) {
/*  59 */     return findNearestBlock(DEFAULT_ELEMENTS, position.getX(), position.getY(), position.getZ(), validBlock, t);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> Vector3i findNearestBlock(@Nonnull IterationElement[] elements, @Nonnull Vector3d position, @Nonnull BiPredicate<Vector3i, T> validBlock, T t) {
/*  64 */     return findNearestBlock(elements, position.getX(), position.getY(), position.getZ(), validBlock, t);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> Vector3i findNearestBlock(double x, double y, double z, @Nonnull BiPredicate<Vector3i, T> validBlock, T t) {
/*  69 */     return findNearestBlock(DEFAULT_ELEMENTS, x, y, z, validBlock, t);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> Vector3i findNearestBlock(@Nonnull IterationElement[] elements, double x, double y, double z, @Nonnull BiPredicate<Vector3i, T> validBlock, T t) {
/*  74 */     int blockX = MathUtil.floor(x);
/*  75 */     int blockY = MathUtil.floor(y);
/*  76 */     int blockZ = MathUtil.floor(z);
/*     */     
/*  78 */     double rx = x % 1.0D;
/*  79 */     double ry = y % 1.0D;
/*  80 */     double rz = z % 1.0D;
/*     */ 
/*     */     
/*  83 */     Vector3i nearest = null, tmp = new Vector3i();
/*  84 */     double nearestDist = Double.POSITIVE_INFINITY;
/*  85 */     for (IterationElement element : elements) {
/*  86 */       double dx = rx - element.getX().applyAsDouble(rx);
/*  87 */       double dy = ry - element.getY().applyAsDouble(ry);
/*  88 */       double dz = rz - element.getZ().applyAsDouble(rz);
/*  89 */       double dist = dx * dx + dy * dy + dz * dz;
/*  90 */       tmp.assign(blockX + element.getOffsetX(), blockY + element.getOffsetY(), blockZ + element.getOffsetZ());
/*  91 */       if (dist < nearestDist && validBlock.test(tmp, t)) {
/*  92 */         nearestDist = dist;
/*  93 */         if (nearest == null) nearest = new Vector3i(); 
/*  94 */         nearest.assign(tmp);
/*     */       } 
/*     */     } 
/*  97 */     return nearest;
/*     */   }
/*     */   
/*     */   public static class IterationElement { private final int ox;
/*     */     private final int oy;
/*     */     private final int oz;
/*     */     
/*     */     public IterationElement(int ox, int oy, int oz, DoubleUnaryOperator x, DoubleUnaryOperator y, DoubleUnaryOperator z) {
/* 105 */       this.ox = ox;
/* 106 */       this.oy = oy;
/* 107 */       this.oz = oz;
/* 108 */       this.x = x;
/* 109 */       this.y = y;
/* 110 */       this.z = z;
/*     */     }
/*     */     private final DoubleUnaryOperator x; private final DoubleUnaryOperator y; private final DoubleUnaryOperator z;
/*     */     public int getOffsetX() {
/* 114 */       return this.ox;
/*     */     }
/*     */     
/*     */     public int getOffsetY() {
/* 118 */       return this.oy;
/*     */     }
/*     */     
/*     */     public int getOffsetZ() {
/* 122 */       return this.oz;
/*     */     }
/*     */     
/*     */     public DoubleUnaryOperator getX() {
/* 126 */       return this.x;
/*     */     }
/*     */     
/*     */     public DoubleUnaryOperator getY() {
/* 130 */       return this.y;
/*     */     }
/*     */     
/*     */     public DoubleUnaryOperator getZ() {
/* 134 */       return this.z;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\NearestBlockUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */