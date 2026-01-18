/*     */ package com.hypixel.hytale.procedurallib.logic;
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
/*     */ public class ResultBuffer3d
/*     */ {
/*     */   public int hash;
/*     */   public int hash2;
/*     */   public int ix;
/*     */   public int iy;
/*     */   public int iz;
/*     */   public int ix2;
/*     */   public int iy2;
/*     */   public int iz2;
/*     */   public double distance;
/*     */   public double distance2;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double x2;
/*     */   public double y2;
/*     */   public double z2;
/*     */   
/*     */   public void register(int hash, int ix, int iy, int iz, double distance, double x, double y, double z) {
/*  66 */     if (distance < this.distance) {
/*  67 */       this.hash = hash;
/*  68 */       this.ix = ix;
/*  69 */       this.iy = iy;
/*  70 */       this.iz = iz;
/*  71 */       this.distance = distance;
/*  72 */       this.x = x;
/*  73 */       this.y = y;
/*  74 */       this.z = z;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void register2(int hash, int ix, int iy, int iz, double distance, double x, double y, double z) {
/*  79 */     if (distance < this.distance) {
/*  80 */       this.distance2 = this.distance;
/*  81 */       this.x2 = this.x;
/*  82 */       this.y2 = this.y;
/*  83 */       this.z2 = this.z;
/*  84 */       this.ix2 = this.ix;
/*  85 */       this.iy2 = this.iy;
/*  86 */       this.iz2 = this.iz;
/*  87 */       this.distance = distance;
/*  88 */       this.x = x;
/*  89 */       this.y = y;
/*  90 */       this.z = z;
/*  91 */       this.ix = ix;
/*  92 */       this.iy = iy;
/*  93 */       this.iz = iz;
/*  94 */       this.hash2 = this.hash;
/*  95 */       this.hash = hash;
/*  96 */     } else if (distance < this.distance2) {
/*  97 */       this.distance2 = distance;
/*  98 */       this.x2 = x;
/*  99 */       this.y2 = y;
/* 100 */       this.z2 = z;
/* 101 */       this.ix2 = ix;
/* 102 */       this.iy2 = iy;
/* 103 */       this.iz2 = iz;
/* 104 */       this.hash2 = hash;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\ResultBuffer$ResultBuffer3d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */