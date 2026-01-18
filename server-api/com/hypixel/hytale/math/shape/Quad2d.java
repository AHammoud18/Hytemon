/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class Quad2d
/*     */ {
/*     */   private Vector2d a;
/*     */   private Vector2d b;
/*     */   private Vector2d c;
/*     */   private Vector2d d;
/*     */   
/*     */   public Quad2d(Vector2d a, Vector2d b, Vector2d c, Vector2d d) {
/*  16 */     this.a = a;
/*  17 */     this.b = b;
/*  18 */     this.c = c;
/*  19 */     this.d = d;
/*     */   }
/*     */   
/*     */   public Quad2d() {
/*  23 */     this(new Vector2d(), new Vector2d(), new Vector2d(), new Vector2d());
/*     */   }
/*     */   
/*     */   public Quad2d(@Nonnull Vector2d[] points) {
/*  27 */     this(points, 0, 1, 2, 3);
/*     */   }
/*     */   
/*     */   public Quad2d(@Nonnull Vector2d[] points, int a, int b, int c, int d) {
/*  31 */     this(points[a], points[b], points[c], points[d]);
/*     */   }
/*     */   
/*     */   public Vector2d getA() {
/*  35 */     return this.a;
/*     */   }
/*     */   
/*     */   public Vector2d getB() {
/*  39 */     return this.b;
/*     */   }
/*     */   
/*     */   public Vector2d getC() {
/*  43 */     return this.c;
/*     */   }
/*     */   
/*     */   public Vector2d getD() {
/*  47 */     return this.d;
/*     */   }
/*     */   
/*     */   public double getMinX() {
/*  51 */     double min = this.a.x;
/*  52 */     if (min > this.b.x) min = this.b.x; 
/*  53 */     if (min > this.c.x) min = this.c.x; 
/*  54 */     if (min > this.d.x) min = this.d.x; 
/*  55 */     return min;
/*     */   }
/*     */   
/*     */   public double getMinY() {
/*  59 */     double min = this.a.y;
/*  60 */     if (min > this.b.y) min = this.b.y; 
/*  61 */     if (min > this.c.y) min = this.c.y; 
/*  62 */     if (min > this.d.y) min = this.d.y; 
/*  63 */     return min;
/*     */   }
/*     */   
/*     */   public double getMaxX() {
/*  67 */     double max = this.a.x;
/*  68 */     if (max < this.b.x) max = this.b.x; 
/*  69 */     if (max < this.c.x) max = this.c.x; 
/*  70 */     if (max < this.d.x) max = this.d.x; 
/*  71 */     return max;
/*     */   }
/*     */   
/*     */   public double getMaxY() {
/*  75 */     double max = this.a.y;
/*  76 */     if (max < this.b.y) max = this.b.y; 
/*  77 */     if (max < this.c.y) max = this.c.y; 
/*  78 */     if (max < this.d.y) max = this.d.y; 
/*  79 */     return max;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getCenter() {
/*  84 */     return getCenter(new Vector2d());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getCenter(@Nonnull Vector2d target) {
/*  89 */     return target.assign((this.a.x + this.c.x) * 0.5D, (this.a.y + this.c.y) * 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getRandom(@Nonnull Random random) {
/*  97 */     return getRandom(random, new Vector2d());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getRandom(@Nonnull Random random, @Nonnull Vector2d vec) {
/* 102 */     double p = random.nextDouble();
/* 103 */     double q = random.nextDouble();
/* 104 */     if (p + q > 1.0D) {
/* 105 */       p = 1.0D - p;
/* 106 */       q = 1.0D - q;
/*     */     } 
/* 108 */     double pq = 1.0D - p - q;
/* 109 */     if (random.nextBoolean()) {
/* 110 */       vec.assign(-this.a.x * pq + this.b.x * p + this.c.x * q, -this.a.y * pq + this.b.y * p + this.c.y * q);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 115 */       vec.assign(-this.a.x * pq + this.c.x * p + this.d.x * q, -this.a.y * pq + this.c.y * p + this.d.y * q);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     return vec;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 126 */     return "Quad2d{a=" + String.valueOf(this.a) + ", b=" + String.valueOf(this.b) + ", c=" + String.valueOf(this.c) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Quad2d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */