/*     */ package com.hypixel.hytale.math.iterator;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineIterator
/*     */   implements Iterator<Vector3i>
/*     */ {
/*     */   private final int x_inc;
/*     */   private final int y_inc;
/*     */   private final int z_inc;
/*     */   private final int l;
/*     */   private final int m;
/*     */   private final int n;
/*     */   private final int dx2;
/*     */   
/*     */   public LineIterator(int x1, int y1, int z1, int x2, int y2, int z2) {
/*  24 */     this.pointX = x1;
/*  25 */     this.pointY = y1;
/*  26 */     this.pointZ = z1;
/*  27 */     int dx = x2 - x1;
/*  28 */     int dy = y2 - y1;
/*  29 */     int dz = z2 - z1;
/*  30 */     this.x_inc = (dx < 0) ? -1 : 1;
/*  31 */     this.y_inc = (dy < 0) ? -1 : 1;
/*  32 */     this.z_inc = (dz < 0) ? -1 : 1;
/*  33 */     this.l = Math.abs(dx);
/*  34 */     this.m = Math.abs(dy);
/*  35 */     this.n = Math.abs(dz);
/*  36 */     this.dx2 = this.l << 1;
/*  37 */     this.dy2 = this.m << 1;
/*  38 */     this.dz2 = this.n << 1;
/*     */     
/*  40 */     if (this.l >= this.m && this.l >= this.n) {
/*  41 */       this.err1 = this.dy2 - this.l;
/*  42 */       this.err2 = this.dz2 - this.l;
/*  43 */     } else if (this.m >= this.l && this.m >= this.n) {
/*  44 */       this.err1 = this.dx2 - this.m;
/*  45 */       this.err2 = this.dz2 - this.m;
/*     */     } else {
/*  47 */       this.err1 = this.dx2 - this.n;
/*  48 */       this.err2 = this.dy2 - this.n;
/*     */     } 
/*     */   }
/*     */   private final int dy2; private final int dz2; private int i; private int err1; private int err2; private int pointX; private int pointY; private int pointZ;
/*     */   
/*     */   public boolean hasNext() {
/*  54 */     if (this.l >= this.m && this.l >= this.n)
/*  55 */       return (this.i <= this.l); 
/*  56 */     if (this.m >= this.l && this.m >= this.n) {
/*  57 */       return (this.i <= this.m);
/*     */     }
/*  59 */     return (this.i <= this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i next() {
/*  66 */     if (this.l >= this.m && this.l >= this.n) {
/*  67 */       if (this.i == this.l) {
/*  68 */         this.i++;
/*  69 */         return new Vector3i(this.pointX, this.pointY, this.pointZ);
/*     */       } 
/*  71 */       if (this.i > this.l) {
/*  72 */         throw new NoSuchElementException();
/*     */       }
/*  74 */       Vector3i vector3i1 = new Vector3i(this.pointX, this.pointY, this.pointZ);
/*  75 */       this.pointX += this.x_inc;
/*  76 */       if (this.err1 > 0) {
/*  77 */         this.pointY += this.y_inc;
/*  78 */         this.err1 -= this.dx2;
/*     */       } 
/*  80 */       this.err1 += this.dy2;
/*  81 */       if (this.err2 > 0) {
/*  82 */         this.pointZ += this.z_inc;
/*  83 */         this.err2 -= this.dx2;
/*     */       } 
/*  85 */       this.err2 += this.dz2;
/*  86 */       this.i++;
/*  87 */       return vector3i1;
/*  88 */     }  if (this.m >= this.l && this.m >= this.n) {
/*  89 */       if (this.i == this.m) {
/*  90 */         this.i++;
/*  91 */         return new Vector3i(this.pointX, this.pointY, this.pointZ);
/*     */       } 
/*  93 */       if (this.i > this.m) {
/*  94 */         throw new NoSuchElementException();
/*     */       }
/*  96 */       Vector3i vector3i1 = new Vector3i(this.pointX, this.pointY, this.pointZ);
/*  97 */       if (this.err1 > 0) {
/*  98 */         this.pointX += this.x_inc;
/*  99 */         this.err1 -= this.dy2;
/*     */       } 
/* 101 */       this.err1 += this.dx2;
/* 102 */       this.pointY += this.y_inc;
/* 103 */       if (this.err2 > 0) {
/* 104 */         this.pointZ += this.z_inc;
/* 105 */         this.err2 -= this.dy2;
/*     */       } 
/* 107 */       this.err2 += this.dz2;
/* 108 */       this.i++;
/* 109 */       return vector3i1;
/*     */     } 
/* 111 */     if (this.i == this.n) {
/* 112 */       this.i++;
/* 113 */       return new Vector3i(this.pointX, this.pointY, this.pointZ);
/*     */     } 
/* 115 */     if (this.i > this.n) {
/* 116 */       throw new NoSuchElementException();
/*     */     }
/* 118 */     Vector3i vector3i = new Vector3i(this.pointX, this.pointY, this.pointZ);
/* 119 */     if (this.err1 > 0) {
/* 120 */       this.pointX += this.x_inc;
/* 121 */       this.err1 -= this.dz2;
/*     */     } 
/* 123 */     this.err1 += this.dx2;
/* 124 */     if (this.err2 > 0) {
/* 125 */       this.pointY += this.y_inc;
/* 126 */       this.err2 -= this.dz2;
/*     */     } 
/* 128 */     this.err2 += this.dy2;
/* 129 */     this.pointZ += this.z_inc;
/* 130 */     this.i++;
/* 131 */     return vector3i;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\LineIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */