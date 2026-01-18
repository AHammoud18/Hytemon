/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Triangle4d
/*     */ {
/*     */   private Vector4d a;
/*     */   private Vector4d b;
/*     */   private Vector4d c;
/*     */   
/*     */   public Triangle4d(Vector4d a, Vector4d b, Vector4d c) {
/*  17 */     this.a = a;
/*  18 */     this.b = b;
/*  19 */     this.c = c;
/*     */   }
/*     */   
/*     */   public Triangle4d() {
/*  23 */     this(new Vector4d(), new Vector4d(), new Vector4d());
/*     */   }
/*     */   
/*     */   public Triangle4d(@Nonnull Vector4d[] points) {
/*  27 */     this(points, 0, 1, 2);
/*     */   }
/*     */   
/*     */   public Triangle4d(@Nonnull Vector4d[] points, int a, int b, int c) {
/*  31 */     this(points[a], points[b], points[c]);
/*     */   }
/*     */   
/*     */   public Vector4d getA() {
/*  35 */     return this.a;
/*     */   }
/*     */   
/*     */   public Vector4d getB() {
/*  39 */     return this.b;
/*     */   }
/*     */   
/*     */   public Vector4d getC() {
/*  43 */     return this.c;
/*     */   }
/*     */   
/*     */   public double getMin(int component) {
/*  47 */     double min = this.a.get(component);
/*  48 */     if (min > this.b.get(component)) min = this.b.get(component); 
/*  49 */     if (min > this.c.get(component)) min = this.c.get(component); 
/*  50 */     return min;
/*     */   }
/*     */   
/*     */   public double getMax(int component) {
/*  54 */     double max = this.a.get(component);
/*  55 */     if (max < this.b.get(component)) max = this.b.get(component); 
/*  56 */     if (max < this.c.get(component)) max = this.c.get(component); 
/*  57 */     return max;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Triangle4d assign(@Nonnull Vector4d v1, @Nonnull Vector4d v2, @Nonnull Vector4d v3) {
/*  62 */     this.a.assign(v1);
/*  63 */     this.b.assign(v2);
/*  64 */     this.c.assign(v3);
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getRandom(@Nonnull Random random) {
/*  70 */     return getRandom(random, new Vector4d());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getRandom(@Nonnull Random random, @Nonnull Vector4d vec) {
/*  76 */     double p = random.nextDouble();
/*  77 */     double q = random.nextDouble() * (1.0D - p);
/*  78 */     double pq = 1.0D - p - q;
/*  79 */     vec.assign(this.a.x * pq + this.b.x * p + this.c.x * q, this.a.y * pq + this.b.y * p + this.c.y * q, this.a.z * pq + this.b.z * p + this.c.z * q, this.a.w * pq + this.b.w * p + this.c.w * q);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     return vec;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Triangle4d multiply(@Nonnull Matrix4d matrix) {
/*  90 */     return multiply(matrix, this);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Triangle4d multiply(@Nonnull Matrix4d matrix, @Nonnull Triangle4d target) {
/*  95 */     matrix.multiply(this.a, target.a);
/*  96 */     matrix.multiply(this.b, target.b);
/*  97 */     matrix.multiply(this.c, target.c);
/*  98 */     return target;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Triangle2d to2d(@Nonnull Triangle2d target) {
/* 103 */     target.getA().assign(this.a.x, this.a.y);
/* 104 */     target.getB().assign(this.b.x, this.b.y);
/* 105 */     target.getC().assign(this.c.x, this.c.y);
/* 106 */     return target;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Triangle4d perspectiveTransform() {
/* 111 */     this.a.perspectiveTransform();
/* 112 */     this.b.perspectiveTransform();
/* 113 */     this.c.perspectiveTransform();
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 120 */     return "Triangle4d{a=" + String.valueOf(this.a) + ", b=" + String.valueOf(this.b) + ", c=" + String.valueOf(this.c) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Triangle4d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */