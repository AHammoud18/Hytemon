/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Triangle2d
/*     */ {
/*     */   private Vector2d a;
/*     */   private Vector2d b;
/*     */   private Vector2d c;
/*     */   
/*     */   public Triangle2d(Vector2d a, Vector2d b, Vector2d c) {
/*  17 */     this.a = a;
/*  18 */     this.b = b;
/*  19 */     this.c = c;
/*     */   }
/*     */   
/*     */   public Triangle2d() {
/*  23 */     this(new Vector2d(), new Vector2d(), new Vector2d());
/*     */   }
/*     */   
/*     */   public Triangle2d(@Nonnull Vector2d[] points) {
/*  27 */     this(points, 0, 1, 2);
/*     */   }
/*     */   
/*     */   public Triangle2d(@Nonnull Vector2d[] points, int a, int b, int c) {
/*  31 */     this(points[a], points[b], points[c]);
/*     */   }
/*     */   
/*     */   public Vector2d getA() {
/*  35 */     return this.a;
/*     */   }
/*     */   
/*     */   public void setA(Vector2d a) {
/*  39 */     this.a = a;
/*     */   }
/*     */   
/*     */   public Vector2d getB() {
/*  43 */     return this.b;
/*     */   }
/*     */   
/*     */   public void setB(Vector2d b) {
/*  47 */     this.b = b;
/*     */   }
/*     */   
/*     */   public Vector2d getC() {
/*  51 */     return this.c;
/*     */   }
/*     */   
/*     */   public void setC(Vector2d c) {
/*  55 */     this.c = c;
/*     */   }
/*     */   
/*     */   public double getMinX() {
/*  59 */     double min = this.a.x;
/*  60 */     if (min > this.b.x) min = this.b.x; 
/*  61 */     if (min > this.c.x) min = this.c.x; 
/*  62 */     return min;
/*     */   }
/*     */   
/*     */   public double getMinY() {
/*  66 */     double min = this.a.y;
/*  67 */     if (min > this.b.y) min = this.b.y; 
/*  68 */     if (min > this.c.y) min = this.c.y; 
/*  69 */     return min;
/*     */   }
/*     */   
/*     */   public double getMaxX() {
/*  73 */     double max = this.a.x;
/*  74 */     if (max < this.b.x) max = this.b.x; 
/*  75 */     if (max < this.c.x) max = this.c.x; 
/*  76 */     return max;
/*     */   }
/*     */   
/*     */   public double getMaxY() {
/*  80 */     double max = this.a.y;
/*  81 */     if (max < this.b.y) max = this.b.y; 
/*  82 */     if (max < this.c.y) max = this.c.y; 
/*  83 */     return max;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getRandom(@Nonnull Random random) {
/*  88 */     return getRandom(random, new Vector2d());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d getRandom(@Nonnull Random random, @Nonnull Vector2d vec) {
/*  93 */     double p = random.nextDouble();
/*  94 */     double q = random.nextDouble();
/*  95 */     if (p + q > 1.0D) {
/*  96 */       p = 1.0D - p;
/*  97 */       q = 1.0D - q;
/*     */     } 
/*  99 */     vec.assign(-this.a.x * (1.0D - p - q) + this.b.x * p + this.c.x * q, -this.a.y * (1.0D - p - q) + this.b.y * p + this.c.y * q);
/*     */ 
/*     */ 
/*     */     
/* 103 */     return vec;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Triangle2d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */