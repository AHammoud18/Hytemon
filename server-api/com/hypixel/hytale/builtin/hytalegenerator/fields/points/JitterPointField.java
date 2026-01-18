/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.points;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class JitterPointField
/*     */   extends PointField
/*     */ {
/*     */   @Nonnull
/*     */   private final FastNoiseLite noise;
/*     */   private final int seed;
/*     */   private final double jitter;
/*     */   @Nonnull
/*     */   private final Vector3d scaleDown3d;
/*     */   @Nonnull
/*     */   private final Vector3d scaleUp3d;
/*     */   @Nonnull
/*     */   private final Vector2d scaleDown2d;
/*     */   @Nonnull
/*     */   private final Vector2d scaleUp2d;
/*     */   
/*     */   public JitterPointField(int seed, double jitter) {
/*  30 */     this.jitter = jitter;
/*  31 */     this.seed = seed;
/*  32 */     this.noise = new FastNoiseLite();
/*  33 */     this.scaleDown3d = new Vector3d(1.0D, 1.0D, 1.0D);
/*  34 */     this.scaleUp3d = new Vector3d(1.0D, 1.0D, 1.0D);
/*  35 */     this.scaleDown2d = new Vector2d(1.0D, 1.0D);
/*  36 */     this.scaleUp2d = new Vector2d(1.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public PointField setScale(double scaleX, double scaleY, double scaleZ, double scaleW) {
/*  41 */     this.scaleDown3d.x = 1.0D / scaleX;
/*  42 */     this.scaleDown3d.y = 1.0D / scaleY;
/*  43 */     this.scaleDown3d.z = 1.0D / scaleZ;
/*  44 */     this.scaleUp3d.x = scaleX;
/*  45 */     this.scaleUp3d.y = scaleY;
/*  46 */     this.scaleUp3d.z = scaleZ;
/*  47 */     this.scaleDown2d.x = 1.0D / scaleX;
/*  48 */     this.scaleDown2d.y = 1.0D / scaleZ;
/*  49 */     this.scaleUp2d.x = scaleX;
/*  50 */     this.scaleUp2d.y = scaleZ;
/*  51 */     return super.setScale(scaleX, scaleY, scaleZ, scaleW);
/*     */   }
/*     */ 
/*     */   
/*     */   public void points3i(@Nonnull Vector3i min, @Nonnull Vector3i max, @Nonnull Consumer<Vector3i> pointsOut) {
/*  56 */     points3d(min.toVector3d(), max.toVector3d(), p -> pointsOut.accept(p.toVector3i()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void points2i(@Nonnull Vector2i min, @Nonnull Vector2i max, @Nonnull Consumer<Vector2i> pointsOut) {
/*  61 */     points2d(new Vector2d(min.x, min.y), new Vector2d(max.x, max.y), p -> pointsOut.accept(new Vector2i((int)p.x, (int)p.y)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void points1i(int min, int max, @Nonnull Consumer<Integer> pointsOut) {
/*  66 */     points1d(min, max, p -> pointsOut.accept(Integer.valueOf(FastNoiseLite.fastRound(p.doubleValue()))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void points3d(@Nonnull Vector3d min, @Nonnull Vector3d max, @Nonnull Consumer<Vector3d> pointsOut) {
/*  72 */     Vector3d cellMin = min.clone().scale(this.scaleDown3d);
/*  73 */     Vector3d cellMax = max.clone().scale(this.scaleDown3d);
/*     */ 
/*     */     
/*  76 */     cellMin.x = FastNoiseLite.fastRound(cellMin.x);
/*  77 */     cellMin.y = FastNoiseLite.fastRound(cellMin.y);
/*  78 */     cellMin.z = FastNoiseLite.fastRound(cellMin.z);
/*  79 */     cellMax.x = FastNoiseLite.fastRound(cellMax.x);
/*  80 */     cellMax.y = FastNoiseLite.fastRound(cellMax.y);
/*  81 */     cellMax.z = FastNoiseLite.fastRound(cellMax.z);
/*     */     
/*     */     double x;
/*  84 */     for (x = cellMin.x; x <= cellMax.x + 0.25D; x++) {
/*  85 */       double y; for (y = cellMin.y; y <= cellMax.y + 0.25D; y++) {
/*  86 */         double z; for (z = cellMin.z; z <= cellMax.z + 0.25D; z++) {
/*     */           
/*  88 */           Vector3d point = this.noise.pointFor(this.seed, this.jitter, x, y, z);
/*  89 */           point.scale(this.scaleUp3d);
/*  90 */           if (VectorUtil.isInside(point, min, max)) {
/*  91 */             pointsOut.accept(point);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void points2d(@Nonnull Vector2d min, @Nonnull Vector2d max, @Nonnull Consumer<Vector2d> pointsOut) {
/* 102 */     Vector2d cellMin = min.clone().scale(this.scaleDown2d);
/* 103 */     Vector2d cellMax = max.clone().scale(this.scaleDown2d);
/*     */ 
/*     */     
/* 106 */     cellMin.x = FastNoiseLite.fastRound(cellMin.x);
/* 107 */     cellMin.y = FastNoiseLite.fastRound(cellMin.y);
/* 108 */     cellMax.x = FastNoiseLite.fastRound(cellMax.x);
/* 109 */     cellMax.y = FastNoiseLite.fastRound(cellMax.y);
/*     */     
/*     */     double x;
/* 112 */     for (x = cellMin.x; x <= cellMax.x + 0.25D; x++) {
/* 113 */       double z; for (z = cellMin.y; z <= cellMax.y + 0.25D; z++) {
/*     */         
/* 115 */         Vector2d point = this.noise.pointFor(this.seed, this.jitter, x, z);
/* 116 */         point.scale(this.scaleUp2d);
/* 117 */         if (VectorUtil.isInside(point, min, max)) {
/* 118 */           pointsOut.accept(point);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void points1d(double min, double max, @Nonnull Consumer<Double> pointsOut) {
/*     */     double x;
/* 127 */     for (x = min - this.scaleX; x < max + this.scaleX; x += this.scaleX) {
/*     */       
/* 129 */       double point = this.noise.pointFor(this.seed, this.jitter, x);
/* 130 */       if (point >= min || point < max)
/*     */       {
/*     */         
/* 133 */         pointsOut.accept(Double.valueOf(point));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\points\JitterPointField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */