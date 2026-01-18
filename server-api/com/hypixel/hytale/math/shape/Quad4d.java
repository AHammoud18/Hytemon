/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class Quad4d
/*     */ {
/*     */   private Vector4d a;
/*     */   private Vector4d b;
/*     */   private Vector4d c;
/*     */   private Vector4d d;
/*     */   
/*     */   public Quad4d(Vector4d a, Vector4d b, Vector4d c, Vector4d d) {
/*  17 */     this.a = a;
/*  18 */     this.b = b;
/*  19 */     this.c = c;
/*  20 */     this.d = d;
/*     */   }
/*     */   
/*     */   public Quad4d() {
/*  24 */     this(new Vector4d(), new Vector4d(), new Vector4d(), new Vector4d());
/*     */   }
/*     */   
/*     */   public Quad4d(@Nonnull Vector4d[] points) {
/*  28 */     this(points, 0, 1, 2, 3);
/*     */   }
/*     */   
/*     */   public Quad4d(@Nonnull Vector4d[] points, int a, int b, int c, int d) {
/*  32 */     this(points[a], points[b], points[c], points[d]);
/*     */   }
/*     */   
/*     */   public boolean isFullyInsideFrustum() {
/*  36 */     return (this.a.isInsideFrustum() && this.b.isInsideFrustum() && this.c.isInsideFrustum() && this.d.isInsideFrustum());
/*     */   }
/*     */   
/*     */   public Vector4d getA() {
/*  40 */     return this.a;
/*     */   }
/*     */   
/*     */   public Vector4d getB() {
/*  44 */     return this.b;
/*     */   }
/*     */   
/*     */   public Vector4d getC() {
/*  48 */     return this.c;
/*     */   }
/*     */   
/*     */   public Vector4d getD() {
/*  52 */     return this.d;
/*     */   }
/*     */   
/*     */   public Vector4d get(int idx) {
/*  56 */     switch (idx) { case 0: 
/*     */       case 1: 
/*     */       case 2:
/*     */       
/*     */       case 3:
/*  61 */        }  throw new IllegalArgumentException("Index must be in range of 0 to 3. Given: " + idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMin(int component) {
/*  66 */     double min = this.a.get(component);
/*  67 */     if (min > this.b.get(component)) min = this.b.get(component); 
/*  68 */     if (min > this.c.get(component)) min = this.c.get(component); 
/*  69 */     if (min > this.d.get(component)) min = this.d.get(component); 
/*  70 */     return min;
/*     */   }
/*     */   
/*     */   public double getMax(int component) {
/*  74 */     double max = this.a.get(component);
/*  75 */     if (max < this.b.get(component)) max = this.b.get(component); 
/*  76 */     if (max < this.c.get(component)) max = this.c.get(component); 
/*  77 */     if (max < this.d.get(component)) max = this.d.get(component); 
/*  78 */     return max;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Quad4d multiply(@Nonnull Matrix4d matrix) {
/*  83 */     return multiply(matrix, this);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Quad4d multiply(@Nonnull Matrix4d matrix, @Nonnull Quad4d target) {
/*  88 */     matrix.multiply(this.a, target.a);
/*  89 */     matrix.multiply(this.b, target.b);
/*  90 */     matrix.multiply(this.c, target.c);
/*  91 */     matrix.multiply(this.d, target.d);
/*  92 */     return target;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Quad2d to2d(@Nonnull Quad2d target) {
/*  97 */     target.getA().assign(this.a.x, this.a.y);
/*  98 */     target.getB().assign(this.b.x, this.b.y);
/*  99 */     target.getC().assign(this.c.x, this.c.y);
/* 100 */     target.getD().assign(this.d.x, this.d.y);
/* 101 */     return target;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getCenter() {
/* 106 */     return getCenter(new Vector4d());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getCenter(@Nonnull Vector4d target) {
/* 112 */     return target.assign(this.a.x + (this.c.x - this.a.x) * 0.5D, this.b.x + (this.b.x - this.b.x) * 0.5D, this.c.x + (this.c.x - this.c.x) * 0.5D, this.d.x + (this.d.x - this.d.x) * 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perspectiveTransform() {
/* 121 */     this.a.perspectiveTransform();
/* 122 */     this.b.perspectiveTransform();
/* 123 */     this.c.perspectiveTransform();
/* 124 */     this.d.perspectiveTransform();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getRandom(@Nonnull Random random) {
/* 129 */     return getRandom(random, new Vector4d());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d getRandom(@Nonnull Random random, @Nonnull Vector4d target) {
/* 135 */     double p = random.nextDouble();
/* 136 */     double q = random.nextDouble() * (1.0D - p);
/* 137 */     double pq = 1.0D - p - q;
/* 138 */     if (random.nextBoolean()) {
/* 139 */       target.assign(this.a.x * pq + this.b.x * p + this.c.x * q, this.a.y * pq + this.b.y * p + this.c.y * q, this.a.z * pq + this.b.z * p + this.c.z * q, this.a.w * pq + this.b.w * p + this.c.w * q);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 146 */       target.assign(this.a.x * pq + this.c.x * p + this.d.x * q, this.a.y * pq + this.c.y * p + this.d.y * q, this.a.z * pq + this.c.z * p + this.d.z * q, this.a.w * pq + this.c.w * p + this.d.w * q);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 159 */     return "Quad4d{\na=" + String.valueOf(this.a) + ",\nb=" + String.valueOf(this.b) + ",\nc=" + String.valueOf(this.c) + ",\nd=" + String.valueOf(this.d) + "\n}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Quad4d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */