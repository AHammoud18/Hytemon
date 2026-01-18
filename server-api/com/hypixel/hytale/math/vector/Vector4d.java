/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class Vector4d
/*     */ {
/*     */   public static final int COMPONENT_X = 0;
/*     */   public static final int COMPONENT_Y = 1;
/*     */   public static final int COMPONENT_Z = 2;
/*     */   public static final int COMPONENT_W = 3;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double w;
/*     */   
/*     */   public Vector4d() {
/*  38 */     this(0.0D, 0.0D, 0.0D, 0.0D);
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
/*     */   public Vector4d(double x, double y, double z, double w) {
/*  50 */     this.x = x;
/*  51 */     this.y = y;
/*  52 */     this.z = z;
/*  53 */     this.w = w;
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
/*     */   @Nonnull
/*     */   public static Vector4d newPosition(double x, double y, double z) {
/*  66 */     return new Vector4d(x, y, z, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector4d newPosition(@Nonnull Vector3d v) {
/*  77 */     return new Vector4d(v.x, v.y, v.z, 1.0D);
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
/*     */   @Nonnull
/*     */   public static Vector4d newDirection(double x, double y, double z) {
/*  90 */     return new Vector4d(x, y, z, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d setDirection() {
/* 100 */     this.w = 0.0D;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d setPosition() {
/* 111 */     this.w = 1.0D;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d assign(@Nonnull Vector4d v) {
/* 123 */     return assign(v.x, v.y, v.z, v.w);
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
/*     */   @Nonnull
/*     */   public Vector4d assign(double x, double y, double z, double w) {
/* 137 */     this.x = x;
/* 138 */     this.y = y;
/* 139 */     this.z = z;
/* 140 */     this.w = w;
/* 141 */     return this;
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
/*     */   @Nonnull
/*     */   public Vector4d lerp(@Nonnull Vector4d dest, double lerpFactor, @Nonnull Vector4d target) {
/* 154 */     target.assign((dest.x - this.x) * lerpFactor + this.x, (dest.y - this.y) * lerpFactor + this.y, (dest.z - this.z) * lerpFactor + this.z, (dest.w - this.w) * lerpFactor + this.w);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     return target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perspectiveTransform() {
/* 167 */     double invW = 1.0D / this.w;
/* 168 */     this.x *= invW;
/* 169 */     this.y *= invW;
/* 170 */     this.z *= invW;
/* 171 */     this.w = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideFrustum() {
/* 180 */     return (Math.abs(this.x) <= Math.abs(this.w) && 
/* 181 */       Math.abs(this.y) <= Math.abs(this.w) && 
/* 182 */       Math.abs(this.z) <= Math.abs(this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int component) {
/* 192 */     switch (component) { case 0: 
/*     */       case 1: 
/*     */       case 2:
/*     */       
/*     */       case 3:
/* 197 */        }  throw new IllegalArgumentException("Invalid component: " + component);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 204 */     return "Vector4d{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector4d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */