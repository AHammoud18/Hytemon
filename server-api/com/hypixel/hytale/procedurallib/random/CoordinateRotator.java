/*     */ package com.hypixel.hytale.procedurallib.random;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordinateRotator
/*     */   implements ICoordinateRandomizer
/*     */ {
/*  11 */   public static final CoordinateRotator NONE = new CoordinateRotator(0.0D, 0.0D);
/*     */   
/*     */   public static final int X0 = 0;
/*     */   public static final int Y0 = 1;
/*     */   public static final int Z0 = 2;
/*     */   public static final int X1 = 3;
/*     */   public static final int Y1 = 4;
/*     */   public static final int Z1 = 5;
/*     */   
/*     */   public CoordinateRotator(double pitch, double yaw) {
/*  21 */     this.pitch = pitch;
/*  22 */     this.yaw = yaw;
/*  23 */     this.matrix = createRotationMatrix(pitch, yaw);
/*     */   } public static final int X2 = 6; public static final int Y2 = 7; public static final int Z2 = 8; protected final double pitch; protected final double yaw; @Nonnull
/*     */   protected final double[] matrix;
/*     */   public double rotateX(double x, double y) {
/*  27 */     return x * this.matrix[0] + y * this.matrix[2];
/*     */   }
/*     */   
/*     */   public double rotateY(double x, double y) {
/*  31 */     return x * this.matrix[6] + y * this.matrix[8];
/*     */   }
/*     */   
/*     */   public double rotateX(double x, double y, double z) {
/*  35 */     return x * this.matrix[0] + y * this.matrix[1] + z * this.matrix[2];
/*     */   }
/*     */   
/*     */   public double rotateY(double x, double y, double z) {
/*  39 */     return x * this.matrix[3] + y * this.matrix[4] + z * this.matrix[5];
/*     */   }
/*     */   
/*     */   public double rotateZ(double x, double y, double z) {
/*  43 */     return x * this.matrix[6] + y * this.matrix[7] + z * this.matrix[8];
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleX(int seed, double x, double y) {
/*  48 */     return rotateX(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleY(int seed, double x, double y) {
/*  53 */     return rotateY(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleX(int seed, double x, double y, double z) {
/*  58 */     return rotateX(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleY(int seed, double x, double y, double z) {
/*  63 */     return rotateY(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomDoubleZ(int seed, double x, double y, double z) {
/*  68 */     return rotateZ(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  74 */     return "CoordinateRotator{pitch=" + this.pitch + ", yaw=" + this.yaw + "}";
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
/*     */   
/*     */   public static double[] createRotationMatrix(double pitch, double yaw) {
/*  88 */     double sinYaw = TrigMathUtil.sin(yaw), cosYaw = TrigMathUtil.cos(yaw);
/*  89 */     double sinPitch = TrigMathUtil.sin(pitch), cosPitch = TrigMathUtil.cos(pitch);
/*     */     
/*  91 */     double px1 = 1.0D, px2 = 0.0D, px3 = 0.0D;
/*  92 */     double py1 = 0.0D, py2 = cosPitch, py3 = -sinPitch;
/*  93 */     double pz1 = 0.0D, pz2 = sinPitch, pz3 = cosPitch;
/*     */     
/*  95 */     double yx1 = cosYaw, yx2 = 0.0D, yx3 = sinYaw;
/*  96 */     double yy1 = 0.0D, yy2 = 1.0D, yy3 = 0.0D;
/*  97 */     double yz1 = -sinYaw, yz2 = 0.0D, yz3 = cosYaw;
/*  98 */     return new double[] {
/*  99 */         dot(px1, px2, px3, yx1, yy1, yz1), dot(px1, px2, px3, yx2, yy2, yz2), dot(px1, px2, px3, yx3, yy3, yz3), 
/* 100 */         dot(py1, py2, py3, yx1, yy1, yz1), dot(py1, py2, py3, yx2, yy2, yz2), dot(py1, py2, py3, yx3, yy3, yz3), 
/* 101 */         dot(pz1, pz2, pz3, yx1, yy1, yz1), dot(pz1, pz2, pz3, yx2, yy2, yz2), dot(pz1, pz2, pz3, yx3, yy3, yz3)
/*     */       };
/*     */   }
/*     */   
/*     */   private static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 106 */     return x1 * x2 + y1 * y2 + z1 * z2;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\random\CoordinateRotator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */