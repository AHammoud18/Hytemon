/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResultBuffer
/*     */ {
/*   9 */   public static final Bounds2d bounds2d = new Bounds2d();
/*  10 */   public static final ResultBuffer2d buffer2d = new ResultBuffer2d();
/*  11 */   public static final ResultBuffer3d buffer3d = new ResultBuffer3d();
/*     */   public static class ResultBuffer2d { public int hash; public int hash2; public int ix; public int iy; public int ix2;
/*     */     public int iy2;
/*     */     public double distance;
/*     */     public double distance2;
/*     */     public double x;
/*     */     public double y;
/*     */     public double x2;
/*     */     public double y2;
/*     */     
/*     */     public void register(int hash, int ix, int iy, double distance, double x, double y) {
/*  22 */       if (distance < this.distance) {
/*  23 */         this.ix = ix;
/*  24 */         this.iy = iy;
/*  25 */         this.distance = distance;
/*  26 */         this.x = x;
/*  27 */         this.y = y;
/*  28 */         this.hash = hash;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void register2(int hash, int ix, int iy, double distance, double x, double y) {
/*  33 */       if (distance < this.distance) {
/*  34 */         this.distance2 = this.distance;
/*  35 */         this.x2 = this.x;
/*  36 */         this.y2 = this.y;
/*  37 */         this.ix2 = this.ix;
/*  38 */         this.iy2 = this.iy;
/*  39 */         this.distance = distance;
/*  40 */         this.x = x;
/*  41 */         this.y = y;
/*  42 */         this.ix = ix;
/*  43 */         this.iy = iy;
/*  44 */         this.hash2 = this.hash;
/*  45 */         this.hash = hash;
/*  46 */       } else if (distance < this.distance2) {
/*  47 */         this.distance2 = distance;
/*  48 */         this.x2 = x;
/*  49 */         this.y2 = y;
/*  50 */         this.ix2 = ix;
/*  51 */         this.iy2 = iy;
/*  52 */         this.hash2 = hash;
/*     */       } 
/*     */     } }
/*     */   
/*     */   public static class ResultBuffer3d { public int hash;
/*     */     public int hash2;
/*     */     public int ix;
/*     */     public int iy;
/*     */     public int iz;
/*     */     public int ix2;
/*     */     public int iy2;
/*     */     public int iz2;
/*     */     
/*     */     public void register(int hash, int ix, int iy, int iz, double distance, double x, double y, double z) {
/*  66 */       if (distance < this.distance) {
/*  67 */         this.hash = hash;
/*  68 */         this.ix = ix;
/*  69 */         this.iy = iy;
/*  70 */         this.iz = iz;
/*  71 */         this.distance = distance;
/*  72 */         this.x = x;
/*  73 */         this.y = y;
/*  74 */         this.z = z;
/*     */       } 
/*     */     }
/*     */     public double distance; public double distance2; public double x; public double y; public double z; public double x2; public double y2; public double z2;
/*     */     public void register2(int hash, int ix, int iy, int iz, double distance, double x, double y, double z) {
/*  79 */       if (distance < this.distance) {
/*  80 */         this.distance2 = this.distance;
/*  81 */         this.x2 = this.x;
/*  82 */         this.y2 = this.y;
/*  83 */         this.z2 = this.z;
/*  84 */         this.ix2 = this.ix;
/*  85 */         this.iy2 = this.iy;
/*  86 */         this.iz2 = this.iz;
/*  87 */         this.distance = distance;
/*  88 */         this.x = x;
/*  89 */         this.y = y;
/*  90 */         this.z = z;
/*  91 */         this.ix = ix;
/*  92 */         this.iy = iy;
/*  93 */         this.iz = iz;
/*  94 */         this.hash2 = this.hash;
/*  95 */         this.hash = hash;
/*  96 */       } else if (distance < this.distance2) {
/*  97 */         this.distance2 = distance;
/*  98 */         this.x2 = x;
/*  99 */         this.y2 = y;
/* 100 */         this.z2 = z;
/* 101 */         this.ix2 = ix;
/* 102 */         this.iy2 = iy;
/* 103 */         this.iz2 = iz;
/* 104 */         this.hash2 = hash;
/*     */       } 
/*     */     } }
/*     */   
/*     */   public static class Bounds2d { public double minX;
/*     */     public double minY;
/*     */     public double maxX;
/*     */     public double maxY;
/*     */     
/*     */     public void assign(double minX, double minY, double maxX, double maxY) {
/* 114 */       this.minX = minX;
/* 115 */       this.minY = minY;
/* 116 */       this.maxX = maxX;
/* 117 */       this.maxY = maxY;
/*     */     }
/*     */     
/*     */     public boolean contains(double x, double y) {
/* 121 */       return (x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\ResultBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */