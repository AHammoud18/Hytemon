/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.points;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PointField
/*     */   implements PointProvider
/*     */ {
/*  21 */   protected double scaleX = 1.0D, scaleY = 1.0D, scaleZ = 1.0D, scaleW = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Vector3i> points3i(@Nonnull Vector3i min, @Nonnull Vector3i max) {
/*  29 */     ArrayList<Vector3i> list = new ArrayList<>();
/*  30 */     Objects.requireNonNull(list); points3i(min, max, list::add);
/*  31 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Vector2i> points2i(@Nonnull Vector2i min, @Nonnull Vector2i max) {
/*  38 */     ArrayList<Vector2i> list = new ArrayList<>();
/*  39 */     Objects.requireNonNull(list); points2i(min, max, list::add);
/*  40 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Integer> points1i(int min, int max) {
/*  47 */     ArrayList<Integer> list = new ArrayList<>();
/*  48 */     Objects.requireNonNull(list); points1i(min, max, list::add);
/*  49 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Vector3d> points3d(@Nonnull Vector3d min, @Nonnull Vector3d max) {
/*  57 */     ArrayList<Vector3d> list = new ArrayList<>();
/*  58 */     Objects.requireNonNull(list); points3d(min, max, list::add);
/*  59 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Vector2d> points2d(@Nonnull Vector2d min, @Nonnull Vector2d max) {
/*  66 */     ArrayList<Vector2d> list = new ArrayList<>();
/*  67 */     Objects.requireNonNull(list); points2d(min, max, list::add);
/*  68 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Double> points1d(double min, double max) {
/*  75 */     ArrayList<Double> list = new ArrayList<>();
/*  76 */     Objects.requireNonNull(list); points1d(min, max, list::add);
/*  77 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointField setScale(double scaleX, double scaleY, double scaleZ, double scaleW) {
/*  86 */     this.scaleX = scaleX;
/*  87 */     this.scaleY = scaleY;
/*  88 */     this.scaleZ = scaleZ;
/*  89 */     this.scaleW = scaleW;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PointField setScale(double scale) {
/*  99 */     setScale(scale, scale, scale, scale);
/* 100 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\points\PointField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */