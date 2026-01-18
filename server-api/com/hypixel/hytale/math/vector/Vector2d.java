/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.codec.Vector2dArrayCodec;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import java.util.Random;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Vector2d
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector2d> CODEC;
/*     */   
/*     */   static {
/*  44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector2d.class, Vector2d::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.DOUBLE), (o, i) -> o.x = i.doubleValue(), o -> Double.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.DOUBLE), (o, i) -> o.y = i.doubleValue(), o -> Double.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  50 */   public static final Vector2dArrayCodec AS_ARRAY_CODEC = new Vector2dArrayCodec();
/*     */   public static final Vector2d UP;
/*  52 */   public static final Vector2d ZERO = new Vector2d(0.0D, 0.0D); public static final Vector2d DOWN;
/*  53 */   public static final Vector2d POS_Y = UP = new Vector2d(0.0D, 1.0D);
/*  54 */   public static final Vector2d NEG_Y = DOWN = new Vector2d(0.0D, -1.0D); public static final Vector2d RIGHT;
/*  55 */   public static final Vector2d POS_X = RIGHT = new Vector2d(1.0D, 0.0D); public static final Vector2d LEFT;
/*  56 */   public static final Vector2d NEG_X = LEFT = new Vector2d(-1.0D, 0.0D);
/*  57 */   public static final Vector2d ALL_ONES = new Vector2d(1.0D, 1.0D);
/*     */   
/*  59 */   public static final Vector2d[] DIRECTIONS = new Vector2d[] { UP, DOWN, LEFT, RIGHT };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d() {
/*  80 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(@Nonnull Vector2d v) {
/*  89 */     this(v.x, v.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(double x, double y) {
/*  99 */     this.x = x;
/* 100 */     this.y = y;
/* 101 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(@Nonnull Random random, double length) {
/* 111 */     float yaw = random.nextFloat() * 6.2831855F;
/* 112 */     float pitch = random.nextFloat() * 6.2831855F;
/*     */     
/* 114 */     this.x = (TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw));
/* 115 */     this.y = TrigMathUtil.cos(pitch);
/*     */     
/* 117 */     scale(length);
/* 118 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 125 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(double x) {
/* 134 */     this.x = x;
/* 135 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 142 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 151 */     this.y = y;
/* 152 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d assign(@Nonnull Vector2d v) {
/* 163 */     this.x = v.x;
/* 164 */     this.y = v.y;
/* 165 */     this.hash = v.hash;
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d assign(double v) {
/* 177 */     this.x = v;
/* 178 */     this.y = v;
/* 179 */     this.hash = 0;
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d assign(@Nonnull double[] v) {
/* 191 */     this.x = v[0];
/* 192 */     this.y = v[1];
/* 193 */     this.hash = 0;
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d assign(@Nonnull float[] v) {
/* 205 */     this.x = v[0];
/* 206 */     this.y = v[1];
/* 207 */     this.hash = 0;
/* 208 */     return this;
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
/*     */   public Vector2d assign(double x, double y) {
/* 220 */     this.x = x;
/* 221 */     this.y = y;
/* 222 */     this.hash = 0;
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d add(@Nonnull Vector2d v) {
/* 234 */     this.x += v.x;
/* 235 */     this.y += v.y;
/* 236 */     this.hash = 0;
/* 237 */     return this;
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
/*     */   public Vector2d add(double x, double y) {
/* 249 */     this.x += x;
/* 250 */     this.y += y;
/* 251 */     this.hash = 0;
/* 252 */     return this;
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
/*     */   public Vector2d addScaled(@Nonnull Vector2d v, double s) {
/* 264 */     this.x += v.x * s;
/* 265 */     this.y += v.y * s;
/* 266 */     this.hash = 0;
/* 267 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d subtract(@Nonnull Vector2d v) {
/* 278 */     this.x -= v.x;
/* 279 */     this.y -= v.y;
/* 280 */     this.hash = 0;
/* 281 */     return this;
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
/*     */   public Vector2d subtract(double x, double y) {
/* 293 */     this.x -= x;
/* 294 */     this.y -= y;
/* 295 */     this.hash = 0;
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d negate() {
/* 306 */     this.x = -this.x;
/* 307 */     this.y = -this.y;
/* 308 */     this.hash = 0;
/* 309 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d scale(double s) {
/* 320 */     this.x *= s;
/* 321 */     this.y *= s;
/* 322 */     this.hash = 0;
/* 323 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d scale(@Nonnull Vector2d p) {
/* 334 */     this.x *= p.x;
/* 335 */     this.y *= p.y;
/* 336 */     this.hash = 0;
/* 337 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(@Nonnull Vector2d other) {
/* 347 */     return this.x * other.x + this.y * other.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector2d v) {
/* 357 */     return Math.sqrt(distanceSquaredTo(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(double x, double y) {
/* 368 */     return Math.sqrt(distanceSquaredTo(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSquaredTo(@Nonnull Vector2d v) {
/* 379 */     double x0 = v.x - this.x;
/* 380 */     double y0 = v.y - this.y;
/* 381 */     return x0 * x0 + y0 * y0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSquaredTo(double x, double y) {
/* 392 */     x -= this.x;
/* 393 */     y -= this.y;
/* 394 */     return x * x + y * y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d normalize() {
/* 402 */     return setLength(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 409 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squaredLength() {
/* 416 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d setLength(double newLen) {
/* 427 */     return scale(newLen / length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d clampLength(double maxLength) {
/* 438 */     double length = length();
/* 439 */     if (maxLength > length) return this; 
/* 440 */     return scale(maxLength / length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d floor() {
/* 450 */     this.x = Math.floor(this.x);
/* 451 */     this.y = Math.floor(this.y);
/* 452 */     this.hash = 0;
/* 453 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d ceil() {
/* 463 */     this.x = Math.ceil(this.x);
/* 464 */     this.y = Math.ceil(this.y);
/* 465 */     this.hash = 0;
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d clipToZero(double epsilon) {
/* 477 */     this.x = MathUtil.clipToZero(this.x, epsilon);
/* 478 */     this.y = MathUtil.clipToZero(this.y, epsilon);
/* 479 */     this.hash = 0;
/* 480 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closeToZero(double epsilon) {
/* 490 */     return (MathUtil.closeToZero(this.x, epsilon) && 
/* 491 */       MathUtil.closeToZero(this.y, epsilon));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinite() {
/* 498 */     return (Double.isFinite(this.x) && Double.isFinite(this.y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d dropHash() {
/* 508 */     this.hash = 0;
/* 509 */     return this;
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
/*     */   public static Vector2d max(@Nonnull Vector2d a, @Nonnull Vector2d b) {
/* 521 */     return new Vector2d(
/* 522 */         Math.max(a.x, b.x), 
/* 523 */         Math.max(a.y, b.y));
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
/*     */   public static Vector2d min(@Nonnull Vector2d a, @Nonnull Vector2d b) {
/* 536 */     return new Vector2d(
/* 537 */         Math.min(a.x, b.x), 
/* 538 */         Math.min(a.y, b.y));
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
/*     */   public static Vector2d lerp(@Nonnull Vector2d a, @Nonnull Vector2d b, double t) {
/* 552 */     return lerpUnclamped(a, b, MathUtil.clamp(t, 0.0D, 1.0D));
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
/*     */   public static Vector2d lerpUnclamped(@Nonnull Vector2d a, @Nonnull Vector2d b, double t) {
/* 565 */     return new Vector2d(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y));
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
/*     */   public static double distance(double x1, double y1, double x2, double y2) {
/* 581 */     return Math.sqrt(distanceSquared(x1, y1, x2, y2));
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
/*     */   public static double distanceSquared(double x1, double y1, double x2, double y2) {
/* 594 */     x1 -= x2;
/* 595 */     y1 -= y2;
/* 596 */     return x1 * x1 + y1 * y1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2d clone() {
/* 603 */     return new Vector2d(this.x, this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 608 */     if (this == o) return true; 
/* 609 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 611 */     Vector2d vector2d = (Vector2d)o;
/* 612 */     return (vector2d.x == this.x && vector2d.y == this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 617 */     if (this.hash == 0) {
/* 618 */       this.hash = (int)HashUtil.hash(Double.doubleToLongBits(this.x), Double.doubleToLongBits(this.y));
/*     */     }
/* 620 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 626 */     return "Vector2d{x=" + this.x + ", y=" + this.y + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector2d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */