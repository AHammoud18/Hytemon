/*     */ package com.hypixel.hytale.math.matrix;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Matrix4d
/*     */ {
/*     */   public static final int M00 = 0;
/*     */   public static final int M10 = 4;
/*     */   public static final int M20 = 8;
/*     */   public static final int M30 = 12;
/*     */   public static final int M01 = 1;
/*     */   public static final int M11 = 5;
/*     */   public static final int M21 = 9;
/*     */   public static final int M31 = 13;
/*     */   public static final int M02 = 2;
/*     */   public static final int M12 = 6;
/*     */   
/*     */   public Matrix4d() {
/*  28 */     this(new double[16]);
/*     */   }
/*     */   public static final int M22 = 10; public static final int M32 = 14; public static final int M03 = 3; public static final int M13 = 7; public static final int M23 = 11; public static final int M33 = 15;
/*     */   public static final int COLUMNS = 4;
/*     */   public static final int ROWS = 4;
/*     */   public static final int FIELDS = 16;
/*     */   private final double[] m;
/*     */   
/*     */   public Matrix4d(@Nonnull Matrix4d other) {
/*  37 */     this();
/*  38 */     assign(other);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix4d(double[] m) {
/*  47 */     this.m = m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int idx) {
/*  57 */     return this.m[idx];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int col, int row) {
/*  68 */     return get(idx(col, row));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d set(int idx, double val) {
/*  80 */     this.m[idx] = val;
/*  81 */     return this;
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
/*     */   public Matrix4d set(int col, int row, double val) {
/*  94 */     return set(idx(col, row), val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d add(int idx, double val) {
/* 106 */     this.m[idx] = this.m[idx] + val;
/* 107 */     return this;
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
/*     */   public Matrix4d add(int col, int row, double val) {
/* 120 */     return set(idx(col, row), val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d identity() {
/* 130 */     Arrays.fill(this.m, 0.0D);
/* 131 */     for (int i = 0; i < 16; ) { this.m[i] = 1.0D; i += 5; }
/* 132 */      return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d assign(@Nonnull Matrix4d other) {
/* 143 */     System.arraycopy(other.m, 0, this.m, 0, 16);
/* 144 */     return this;
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
/*     */   public Matrix4d assign(double m00, double m10, double m20, double m30, double m01, double m11, double m21, double m31, double m02, double m12, double m22, double m32, double m03, double m13, double m23, double m33) {
/* 157 */     this.m[0] = m00; this.m[1] = m01; this.m[2] = m02; this.m[3] = m03;
/* 158 */     this.m[4] = m10; this.m[5] = m11; this.m[6] = m12; this.m[7] = m13;
/* 159 */     this.m[8] = m20; this.m[9] = m21; this.m[10] = m22; this.m[11] = m23;
/* 160 */     this.m[12] = m30; this.m[13] = m31; this.m[14] = m32; this.m[15] = m33;
/*     */     
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d translate(@Nonnull Vector3d vec) {
/* 174 */     return translate(vec.x, vec.y, vec.z);
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
/*     */   public Matrix4d translate(double x, double y, double z) {
/* 187 */     for (int i = 0; i < 4; i++) {
/* 188 */       this.m[i + 12] = this.m[i + 12] + this.m[i] * x + this.m[i + 4] * y + this.m[i + 8] * z;
/*     */     }
/*     */ 
/*     */     
/* 192 */     return this;
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
/*     */   public Matrix4d scale(double x, double y, double z) {
/* 205 */     for (int i = 0; i < 4; i++) {
/* 206 */       this.m[i] = this.m[i] * x;
/* 207 */       this.m[i + 4] = this.m[i + 4] * y;
/* 208 */       this.m[i + 8] = this.m[i + 8] * z;
/*     */     } 
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d multiplyPosition(@Nonnull Vector3d vec) {
/* 221 */     return multiplyPosition(vec, vec);
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
/*     */   public Vector3d multiplyPosition(@Nonnull Vector3d vec, @Nonnull Vector3d result) {
/* 234 */     double x = this.m[0] * vec.x + this.m[4] * vec.y + this.m[8] * vec.z + this.m[12];
/* 235 */     double y = this.m[1] * vec.x + this.m[5] * vec.y + this.m[9] * vec.z + this.m[13];
/* 236 */     double z = this.m[2] * vec.x + this.m[6] * vec.y + this.m[10] * vec.z + this.m[14];
/* 237 */     double w = this.m[3] * vec.x + this.m[7] * vec.y + this.m[11] * vec.z + this.m[15];
/* 238 */     double invW = 1.0D / w;
/* 239 */     result.assign(x * invW, y * invW, z * invW);
/* 240 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d multiplyDirection(@Nonnull Vector3d vec) {
/* 252 */     double x = this.m[0] * vec.x + this.m[4] * vec.y + this.m[8] * vec.z;
/* 253 */     double y = this.m[1] * vec.x + this.m[5] * vec.y + this.m[9] * vec.z;
/* 254 */     double z = this.m[2] * vec.x + this.m[6] * vec.y + this.m[10] * vec.z;
/* 255 */     vec.assign(x, y, z);
/* 256 */     return vec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector4d multiply(@Nonnull Vector4d vec) {
/* 267 */     return multiply(vec, vec);
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
/*     */   public Vector4d multiply(@Nonnull Vector4d vec, @Nonnull Vector4d result) {
/* 280 */     double x = this.m[0] * vec.x + this.m[4] * vec.y + this.m[8] * vec.z + this.m[12] * vec.w;
/* 281 */     double y = this.m[1] * vec.x + this.m[5] * vec.y + this.m[9] * vec.z + this.m[13] * vec.w;
/* 282 */     double z = this.m[2] * vec.x + this.m[6] * vec.y + this.m[10] * vec.z + this.m[14] * vec.w;
/* 283 */     double w = this.m[3] * vec.x + this.m[7] * vec.y + this.m[11] * vec.z + this.m[15] * vec.w;
/* 284 */     result.assign(x, y, z, w);
/* 285 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d multiply(@Nonnull Matrix4d other) {
/* 296 */     double a00 = this.m[0];
/* 297 */     double a01 = this.m[1];
/* 298 */     double a02 = this.m[2];
/* 299 */     double a03 = this.m[3];
/*     */     
/* 301 */     double a10 = this.m[4];
/* 302 */     double a11 = this.m[5];
/* 303 */     double a12 = this.m[6];
/* 304 */     double a13 = this.m[7];
/*     */     
/* 306 */     double a20 = this.m[8];
/* 307 */     double a21 = this.m[9];
/* 308 */     double a22 = this.m[10];
/* 309 */     double a23 = this.m[11];
/*     */     
/* 311 */     double a30 = this.m[12];
/* 312 */     double a31 = this.m[13];
/* 313 */     double a32 = this.m[14];
/* 314 */     double a33 = this.m[15];
/*     */     
/* 316 */     double b00 = other.m[0];
/* 317 */     double b01 = other.m[1];
/* 318 */     double b02 = other.m[2];
/* 319 */     double b03 = other.m[3];
/*     */     
/* 321 */     double b10 = other.m[4];
/* 322 */     double b11 = other.m[5];
/* 323 */     double b12 = other.m[6];
/* 324 */     double b13 = other.m[7];
/*     */     
/* 326 */     double b20 = other.m[8];
/* 327 */     double b21 = other.m[9];
/* 328 */     double b22 = other.m[10];
/* 329 */     double b23 = other.m[11];
/*     */     
/* 331 */     double b30 = other.m[12];
/* 332 */     double b31 = other.m[13];
/* 333 */     double b32 = other.m[14];
/* 334 */     double b33 = other.m[15];
/*     */     
/* 336 */     this.m[0] = a00 * b00 + a10 * b01 + a20 * b02 + a30 * b03;
/* 337 */     this.m[1] = a01 * b00 + a11 * b01 + a21 * b02 + a31 * b03;
/* 338 */     this.m[2] = a02 * b00 + a12 * b01 + a22 * b02 + a32 * b03;
/* 339 */     this.m[3] = a03 * b00 + a13 * b01 + a23 * b02 + a33 * b03;
/*     */     
/* 341 */     this.m[4] = a00 * b10 + a10 * b11 + a20 * b12 + a30 * b13;
/* 342 */     this.m[5] = a01 * b10 + a11 * b11 + a21 * b12 + a31 * b13;
/* 343 */     this.m[6] = a02 * b10 + a12 * b11 + a22 * b12 + a32 * b13;
/* 344 */     this.m[7] = a03 * b10 + a13 * b11 + a23 * b12 + a33 * b13;
/*     */     
/* 346 */     this.m[8] = a00 * b20 + a10 * b21 + a20 * b22 + a30 * b23;
/* 347 */     this.m[9] = a01 * b20 + a11 * b21 + a21 * b22 + a31 * b23;
/* 348 */     this.m[10] = a02 * b20 + a12 * b21 + a22 * b22 + a32 * b23;
/* 349 */     this.m[11] = a03 * b20 + a13 * b21 + a23 * b22 + a33 * b23;
/*     */     
/* 351 */     this.m[12] = a00 * b30 + a10 * b31 + a20 * b32 + a30 * b33;
/* 352 */     this.m[13] = a01 * b30 + a11 * b31 + a21 * b32 + a31 * b33;
/* 353 */     this.m[14] = a02 * b30 + a12 * b31 + a22 * b32 + a32 * b33;
/* 354 */     this.m[15] = a03 * b30 + a13 * b31 + a23 * b32 + a33 * b33;
/*     */     
/* 356 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean invert() {
/* 365 */     double src0 = this.m[0];
/* 366 */     double src4 = this.m[1];
/* 367 */     double src8 = this.m[2];
/* 368 */     double src12 = this.m[3];
/*     */     
/* 370 */     double src1 = this.m[4];
/* 371 */     double src5 = this.m[5];
/* 372 */     double src9 = this.m[6];
/* 373 */     double src13 = this.m[7];
/*     */     
/* 375 */     double src2 = this.m[8];
/* 376 */     double src6 = this.m[9];
/* 377 */     double src10 = this.m[10];
/* 378 */     double src14 = this.m[11];
/*     */     
/* 380 */     double src3 = this.m[12];
/* 381 */     double src7 = this.m[13];
/* 382 */     double src11 = this.m[14];
/* 383 */     double src15 = this.m[15];
/*     */     
/* 385 */     double atmp0 = src10 * src15;
/* 386 */     double atmp1 = src11 * src14;
/* 387 */     double atmp2 = src9 * src15;
/* 388 */     double atmp3 = src11 * src13;
/* 389 */     double atmp4 = src9 * src14;
/* 390 */     double atmp5 = src10 * src13;
/* 391 */     double atmp6 = src8 * src15;
/* 392 */     double atmp7 = src11 * src12;
/* 393 */     double atmp8 = src8 * src14;
/* 394 */     double atmp9 = src10 * src12;
/* 395 */     double atmp10 = src8 * src13;
/* 396 */     double atmp11 = src9 * src12;
/*     */     
/* 398 */     double dst0 = atmp0 * src5 + atmp3 * src6 + atmp4 * src7 - atmp1 * src5 + atmp2 * src6 + atmp5 * src7;
/*     */     
/* 400 */     double dst1 = atmp1 * src4 + atmp6 * src6 + atmp9 * src7 - atmp0 * src4 + atmp7 * src6 + atmp8 * src7;
/*     */     
/* 402 */     double dst2 = atmp2 * src4 + atmp7 * src5 + atmp10 * src7 - atmp3 * src4 + atmp6 * src5 + atmp11 * src7;
/*     */     
/* 404 */     double dst3 = atmp5 * src4 + atmp8 * src5 + atmp11 * src6 - atmp4 * src4 + atmp9 * src5 + atmp10 * src6;
/*     */     
/* 406 */     double dst4 = atmp1 * src1 + atmp2 * src2 + atmp5 * src3 - atmp0 * src1 + atmp3 * src2 + atmp4 * src3;
/*     */     
/* 408 */     double dst5 = atmp0 * src0 + atmp7 * src2 + atmp8 * src3 - atmp1 * src0 + atmp6 * src2 + atmp9 * src3;
/*     */     
/* 410 */     double dst6 = atmp3 * src0 + atmp6 * src1 + atmp11 * src3 - atmp2 * src0 + atmp7 * src1 + atmp10 * src3;
/*     */     
/* 412 */     double dst7 = atmp4 * src0 + atmp9 * src1 + atmp10 * src2 - atmp5 * src0 + atmp8 * src1 + atmp11 * src2;
/*     */ 
/*     */     
/* 415 */     double btmp0 = src2 * src7;
/* 416 */     double btmp1 = src3 * src6;
/* 417 */     double btmp2 = src1 * src7;
/* 418 */     double btmp3 = src3 * src5;
/* 419 */     double btmp4 = src1 * src6;
/* 420 */     double btmp5 = src2 * src5;
/* 421 */     double btmp6 = src0 * src7;
/* 422 */     double btmp7 = src3 * src4;
/* 423 */     double btmp8 = src0 * src6;
/* 424 */     double btmp9 = src2 * src4;
/* 425 */     double btmp10 = src0 * src5;
/* 426 */     double btmp11 = src1 * src4;
/*     */     
/* 428 */     double dst8 = btmp0 * src13 + btmp3 * src14 + btmp4 * src15 - btmp1 * src13 + btmp2 * src14 + btmp5 * src15;
/*     */     
/* 430 */     double dst9 = btmp1 * src12 + btmp6 * src14 + btmp9 * src15 - btmp0 * src12 + btmp7 * src14 + btmp8 * src15;
/*     */     
/* 432 */     double dst10 = btmp2 * src12 + btmp7 * src13 + btmp10 * src15 - btmp3 * src12 + btmp6 * src13 + btmp11 * src15;
/*     */     
/* 434 */     double dst11 = btmp5 * src12 + btmp8 * src13 + btmp11 * src14 - btmp4 * src12 + btmp9 * src13 + btmp10 * src14;
/*     */     
/* 436 */     double dst12 = btmp2 * src10 + btmp5 * src11 + btmp1 * src9 - btmp4 * src11 + btmp0 * src9 + btmp3 * src10;
/*     */     
/* 438 */     double dst13 = btmp8 * src11 + btmp0 * src8 + btmp7 * src10 - btmp6 * src10 + btmp9 * src11 + btmp1 * src8;
/*     */     
/* 440 */     double dst14 = btmp6 * src9 + btmp11 * src11 + btmp3 * src8 - btmp10 * src11 + btmp2 * src8 + btmp7 * src9;
/*     */     
/* 442 */     double dst15 = btmp10 * src10 + btmp4 * src8 + btmp9 * src9 - btmp8 * src9 + btmp11 * src10 + btmp5 * src8;
/*     */ 
/*     */     
/* 445 */     double det = src0 * dst0 + src1 * dst1 + src2 * dst2 + src3 * dst3;
/*     */     
/* 447 */     if (det == 0.0D) return false;
/*     */     
/* 449 */     double invdet = 1.0D / det;
/* 450 */     this.m[0] = dst0 * invdet;
/* 451 */     this.m[1] = dst1 * invdet;
/* 452 */     this.m[2] = dst2 * invdet;
/* 453 */     this.m[3] = dst3 * invdet;
/*     */     
/* 455 */     this.m[4] = dst4 * invdet;
/* 456 */     this.m[5] = dst5 * invdet;
/* 457 */     this.m[6] = dst6 * invdet;
/* 458 */     this.m[7] = dst7 * invdet;
/*     */     
/* 460 */     this.m[8] = dst8 * invdet;
/* 461 */     this.m[9] = dst9 * invdet;
/* 462 */     this.m[10] = dst10 * invdet;
/* 463 */     this.m[11] = dst11 * invdet;
/*     */     
/* 465 */     this.m[12] = dst12 * invdet;
/* 466 */     this.m[13] = dst13 * invdet;
/* 467 */     this.m[14] = dst14 * invdet;
/* 468 */     this.m[15] = dst15 * invdet;
/*     */     
/* 470 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d projectionOrtho(double left, double right, double bottom, double top, double near, double far) {
/* 482 */     double r_width = 1.0D / (right + left);
/* 483 */     double r_height = 1.0D / (top + bottom);
/* 484 */     double r_depth = -1.0D / (far - near);
/* 485 */     double x = 2.0D * r_width;
/* 486 */     double y = 2.0D * r_height;
/* 487 */     double z = 2.0D * r_depth;
/*     */     
/* 489 */     this.m[3] = 0.0D; this.m[2] = 0.0D; this.m[1] = 0.0D;
/* 490 */     this.m[7] = 0.0D; this.m[6] = 0.0D; this.m[4] = 0.0D;
/* 491 */     this.m[11] = 0.0D; this.m[9] = 0.0D; this.m[8] = 0.0D;
/* 492 */     this.m[15] = 1.0D;
/*     */     
/* 494 */     this.m[0] = x;
/* 495 */     this.m[5] = y;
/* 496 */     this.m[10] = z;
/*     */     
/* 498 */     this.m[12] = -(right - left) * r_width;
/* 499 */     this.m[13] = -(top - bottom) * r_height;
/* 500 */     this.m[14] = (far + near) * r_depth;
/*     */     
/* 502 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d projectionFrustum(double left, double right, double bottom, double top, double near, double far) {
/* 514 */     double r_width = 1.0D / (right + left);
/* 515 */     double r_height = 1.0D / (top + bottom);
/* 516 */     double r_depth = 1.0D / (near - far);
/*     */     
/* 518 */     this.m[3] = 0.0D; this.m[2] = 0.0D; this.m[1] = 0.0D;
/* 519 */     this.m[7] = 0.0D; this.m[6] = 0.0D; this.m[4] = 0.0D;
/* 520 */     this.m[15] = 0.0D; this.m[13] = 0.0D; this.m[12] = 0.0D;
/* 521 */     this.m[11] = -1.0D;
/*     */     
/* 523 */     this.m[0] = 2.0D * near * r_width;
/* 524 */     this.m[5] = 2.0D * near * r_height;
/* 525 */     this.m[14] = 2.0D * far * near * r_depth;
/*     */     
/* 527 */     this.m[8] = 2.0D * (right - left) * r_width;
/* 528 */     this.m[9] = (top - bottom) * r_height;
/* 529 */     this.m[10] = (far + near) * r_depth;
/*     */     
/* 531 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d projectionCone(double fov, double aspect, double near, double far) {
/* 541 */     double f = 1.0D / Math.tan(fov * 0.5D);
/* 542 */     double r = 1.0D / (near - far);
/*     */     
/* 544 */     this.m[0] = f / aspect;
/* 545 */     this.m[3] = 0.0D; this.m[2] = 0.0D; this.m[1] = 0.0D;
/*     */     
/* 547 */     this.m[5] = f;
/* 548 */     this.m[7] = 0.0D; this.m[6] = 0.0D; this.m[4] = 0.0D;
/*     */     
/* 550 */     this.m[9] = 0.0D; this.m[8] = 0.0D;
/* 551 */     this.m[10] = (far + near) * r;
/* 552 */     this.m[11] = -1.0D;
/*     */     
/* 554 */     this.m[15] = 0.0D; this.m[13] = 0.0D; this.m[12] = 0.0D;
/* 555 */     this.m[14] = 2.0D * far * near * r;
/*     */     
/* 557 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d viewTarget(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ) {
/* 569 */     double dirX = centerX - eyeX;
/* 570 */     double dirY = centerY - eyeY;
/* 571 */     double dirZ = centerZ - eyeZ;
/*     */     
/* 573 */     return viewDirection(eyeX, eyeY, eyeZ, dirX, dirY, dirZ, upX, upY, upZ);
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
/*     */   public Matrix4d viewDirection(double eyeX, double eyeY, double eyeZ, double dirX, double dirY, double dirZ, double upX, double upY, double upZ) {
/* 587 */     double rlf = 1.0D / MathUtil.length(dirX, dirY, dirZ);
/* 588 */     dirX *= rlf;
/* 589 */     dirY *= rlf;
/* 590 */     dirZ *= rlf;
/*     */     
/* 592 */     double sx = dirY * upZ - dirZ * upY;
/* 593 */     double sy = dirZ * upX - dirX * upZ;
/* 594 */     double sz = dirX * upY - dirY * upX;
/*     */     
/* 596 */     double rls = 1.0D / MathUtil.length(sx, sy, sz);
/* 597 */     sx *= rls;
/* 598 */     sy *= rls;
/* 599 */     sz *= rls;
/*     */     
/* 601 */     double ux = sy * dirZ - sz * dirY;
/* 602 */     double uy = sz * dirX - sx * dirZ;
/* 603 */     double uz = sx * dirY - sy * dirX;
/*     */     
/* 605 */     this.m[0] = sx;
/* 606 */     this.m[1] = ux;
/* 607 */     this.m[2] = -dirX;
/* 608 */     this.m[3] = 0.0D;
/*     */     
/* 610 */     this.m[4] = sy;
/* 611 */     this.m[5] = uy;
/* 612 */     this.m[6] = -dirY;
/* 613 */     this.m[7] = 0.0D;
/*     */     
/* 615 */     this.m[8] = sz;
/* 616 */     this.m[9] = uz;
/* 617 */     this.m[10] = -dirZ;
/* 618 */     this.m[11] = 0.0D;
/*     */     
/* 620 */     this.m[12] = 0.0D;
/* 621 */     this.m[13] = 0.0D;
/* 622 */     this.m[14] = 0.0D;
/* 623 */     this.m[15] = 1.0D;
/*     */     
/* 625 */     translate(-eyeX, -eyeY, -eyeZ);
/* 626 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d rotateAxis(double a, double x, double y, double z, @Nonnull Matrix4d tmp) {
/* 636 */     return multiply(tmp.setRotateAxis(a, x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d setRotateAxis(double a, double x, double y, double z) {
/* 646 */     double sin = TrigMathUtil.sin(a);
/* 647 */     double cos = TrigMathUtil.cos(a);
/*     */     
/* 649 */     this.m[0] = cos + x * x * (1.0D - cos);
/* 650 */     this.m[1] = x * y * (1.0D - cos) - z * sin;
/* 651 */     this.m[2] = x * z * (1.0D - cos) + y * sin;
/* 652 */     this.m[3] = 0.0D;
/* 653 */     this.m[4] = y * x * (1.0D - cos) + z * sin;
/* 654 */     this.m[5] = cos + y * y * (1.0D - cos);
/* 655 */     this.m[6] = y * z * (1.0D - cos) - x * sin;
/* 656 */     this.m[7] = 0.0D;
/* 657 */     this.m[8] = z * x * (1.0D - cos) - y * sin;
/* 658 */     this.m[9] = z * y * (1.0D - cos) + x * sin;
/* 659 */     this.m[10] = cos + z * z * (1.0D - cos);
/* 660 */     this.m[11] = 0.0D;
/* 661 */     this.m[12] = 0.0D;
/* 662 */     this.m[13] = 0.0D;
/* 663 */     this.m[14] = 0.0D;
/* 664 */     this.m[15] = 1.0D;
/*     */     
/* 666 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d rotateEuler(double x, double y, double z, @Nonnull Matrix4d tmp) {
/* 676 */     return multiply(tmp.setRotateEuler(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Matrix4d setRotateEuler(double x, double y, double z) {
/* 686 */     double cx = TrigMathUtil.cos(x);
/* 687 */     double sx = TrigMathUtil.sin(x);
/* 688 */     double cy = TrigMathUtil.cos(y);
/* 689 */     double sy = TrigMathUtil.sin(y);
/* 690 */     double cz = TrigMathUtil.cos(z);
/* 691 */     double sz = TrigMathUtil.sin(z);
/* 692 */     double cxsy = cx * sy;
/* 693 */     double sxsy = sx * sy;
/*     */     
/* 695 */     this.m[0] = cy * cz;
/* 696 */     this.m[1] = -cy * sz;
/* 697 */     this.m[2] = sy;
/*     */     
/* 699 */     this.m[4] = sxsy * cz + cx * sz;
/* 700 */     this.m[5] = -sxsy * sz + cx * cz;
/* 701 */     this.m[6] = -sx * cy;
/*     */     
/* 703 */     this.m[8] = -cxsy * cz + sx * sz;
/* 704 */     this.m[9] = cxsy * sz + sx * cz;
/* 705 */     this.m[10] = cx * cy;
/*     */     
/* 707 */     this.m[11] = 0.0D; this.m[7] = 0.0D; this.m[3] = 0.0D;
/* 708 */     this.m[14] = 0.0D; this.m[13] = 0.0D; this.m[12] = 0.0D;
/* 709 */     this.m[15] = 1.0D;
/*     */     
/* 711 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getData() {
/* 718 */     return this.m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] asFloatData() {
/* 725 */     float[] data = new float[16];
/* 726 */     for (int i = 0; i < 16; i++) {
/* 727 */       data[i] = (float)this.m[i];
/*     */     }
/* 729 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 735 */     return "Matrix4d{\n  " + this.m[0] + " " + this.m[4] + " " + this.m[8] + " " + this.m[12] + "\n  " + this.m[1] + " " + this.m[5] + " " + this.m[9] + " " + this.m[13] + "\n  " + this.m[2] + " " + this.m[6] + " " + this.m[10] + " " + this.m[14] + "\n  " + this.m[3] + " " + this.m[7] + " " + this.m[11] + " " + this.m[15] + "\n}";
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
/*     */ 
/*     */   
/*     */   public static int idx(int col, int row) {
/* 751 */     return col << 2 | row;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\matrix\Matrix4d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */