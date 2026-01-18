/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.codec.Vector3dArrayCodec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector3d
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector3d> CODEC;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3d.class, Vector3d::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.DOUBLE), (o, i) -> o.x = i.doubleValue(), o -> Double.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.DOUBLE), (o, i) -> o.y = i.doubleValue(), o -> Double.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.DOUBLE), (o, i) -> o.z = i.doubleValue(), o -> Double.valueOf(o.z), (o, p) -> o.z = p.z).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  58 */   public static final Vector3dArrayCodec AS_ARRAY_CODEC = new Vector3dArrayCodec();
/*     */   public static final Vector3d UP;
/*  60 */   public static final Vector3d ZERO = new Vector3d(0.0D, 0.0D, 0.0D); public static final Vector3d DOWN;
/*  61 */   public static final Vector3d POS_Y = UP = new Vector3d(0.0D, 1.0D, 0.0D);
/*  62 */   public static final Vector3d NEG_Y = DOWN = new Vector3d(0.0D, -1.0D, 0.0D); public static final Vector3d FORWARD;
/*  63 */   public static final Vector3d NEG_Z = FORWARD = new Vector3d(0.0D, 0.0D, -1.0D), NORTH = FORWARD; public static final Vector3d BACKWARD;
/*  64 */   public static final Vector3d POS_Z = BACKWARD = new Vector3d(0.0D, 0.0D, 1.0D), SOUTH = BACKWARD; public static final Vector3d RIGHT;
/*  65 */   public static final Vector3d POS_X = RIGHT = new Vector3d(1.0D, 0.0D, 0.0D), EAST = RIGHT; public static final Vector3d LEFT;
/*  66 */   public static final Vector3d NEG_X = LEFT = new Vector3d(-1.0D, 0.0D, 0.0D), WEST = LEFT;
/*  67 */   public static final Vector3d ALL_ONES = new Vector3d(1.0D, 1.0D, 1.0D);
/*  68 */   public static final Vector3d MIN = new Vector3d(-1.7976931348623157E308D, -1.7976931348623157E308D, -1.7976931348623157E308D);
/*  69 */   public static final Vector3d MAX = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
/*     */   
/*  71 */   public static final Vector3d[] BLOCK_SIDES = new Vector3d[] { UP, DOWN, FORWARD, BACKWARD, LEFT, RIGHT };
/*  72 */   public static final Vector3d[] BLOCK_EDGES = new Vector3d[] { 
/*  73 */       add(UP, FORWARD), add(DOWN, FORWARD), add(UP, BACKWARD), add(DOWN, BACKWARD), 
/*  74 */       add(UP, LEFT), add(DOWN, LEFT), add(UP, RIGHT), add(DOWN, RIGHT), 
/*  75 */       add(FORWARD, LEFT), add(FORWARD, RIGHT), add(BACKWARD, LEFT), add(BACKWARD, RIGHT) };
/*     */   
/*  77 */   public static final Vector3d[] BLOCK_CORNERS = new Vector3d[] {
/*  78 */       add(UP, FORWARD, LEFT), add(UP, FORWARD, RIGHT), add(DOWN, FORWARD, LEFT), add(DOWN, FORWARD, RIGHT), 
/*  79 */       add(UP, BACKWARD, LEFT), add(UP, BACKWARD, RIGHT), add(DOWN, BACKWARD, LEFT), add(DOWN, BACKWARD, RIGHT)
/*     */     };
/*  81 */   public static final Vector3d[][] BLOCK_PARTS = new Vector3d[][] { BLOCK_SIDES, BLOCK_EDGES, BLOCK_CORNERS };
/*  82 */   public static final Vector3d[] CARDINAL_DIRECTIONS = new Vector3d[] { NORTH, SOUTH, EAST, WEST };
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
/*     */   public double z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d() {
/* 108 */     this(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(@Nonnull Vector3d v) {
/* 117 */     this(v.x, v.y, v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(@Nonnull Vector3i v) {
/* 126 */     this(v.x, v.y, v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(double x, double y, double z) {
/* 137 */     this.x = x;
/* 138 */     this.y = y;
/* 139 */     this.z = z;
/* 140 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(float yaw, float pitch) {
/* 150 */     this();
/* 151 */     assign(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(@Nonnull Random random, double length) {
/* 161 */     this(random.nextFloat() * 6.2831855F, random
/* 162 */         .nextFloat() * 6.2831855F);
/* 163 */     scale(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 170 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(double x) {
/* 179 */     this.x = x;
/* 180 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 187 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 196 */     this.y = y;
/* 197 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 204 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(double z) {
/* 213 */     this.z = z;
/* 214 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d assign(@Nonnull Vector3d v) {
/* 225 */     this.x = v.x;
/* 226 */     this.y = v.y;
/* 227 */     this.z = v.z;
/* 228 */     this.hash = v.hash;
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d assign(double v) {
/* 240 */     this.x = v;
/* 241 */     this.y = v;
/* 242 */     this.z = v;
/* 243 */     this.hash = 0;
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d assign(@Nonnull double[] v) {
/* 255 */     this.x = v[0];
/* 256 */     this.y = v[1];
/* 257 */     this.z = v[2];
/* 258 */     this.hash = 0;
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d assign(@Nonnull float[] v) {
/* 270 */     this.x = v[0];
/* 271 */     this.y = v[1];
/* 272 */     this.z = v[2];
/* 273 */     this.hash = 0;
/* 274 */     return this;
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
/*     */   public Vector3d assign(double yaw, double pitch) {
/* 286 */     double len = TrigMathUtil.cos(pitch);
/* 287 */     double x = len * -TrigMathUtil.sin(yaw);
/* 288 */     double y = TrigMathUtil.sin(pitch);
/* 289 */     double z = len * -TrigMathUtil.cos(yaw);
/* 290 */     return assign(x, y, z);
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
/*     */   public Vector3d assign(double x, double y, double z) {
/* 303 */     this.x = x;
/* 304 */     this.y = y;
/* 305 */     this.z = z;
/* 306 */     this.hash = 0;
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d add(@Nonnull Vector3d v) {
/* 318 */     this.x += v.x;
/* 319 */     this.y += v.y;
/* 320 */     this.z += v.z;
/* 321 */     this.hash = 0;
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d add(@Nonnull Vector3i v) {
/* 333 */     this.x += v.x;
/* 334 */     this.y += v.y;
/* 335 */     this.z += v.z;
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
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d add(double x, double y, double z) {
/* 350 */     this.x += x;
/* 351 */     this.y += y;
/* 352 */     this.z += z;
/* 353 */     this.hash = 0;
/* 354 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d add(double value) {
/* 365 */     this.x += value;
/* 366 */     this.y += value;
/* 367 */     this.z += value;
/* 368 */     this.hash = 0;
/* 369 */     return this;
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
/*     */   public Vector3d addScaled(@Nonnull Vector3d v, double s) {
/* 381 */     this.x += v.x * s;
/* 382 */     this.y += v.y * s;
/* 383 */     this.z += v.z * s;
/* 384 */     this.hash = 0;
/* 385 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d subtract(@Nonnull Vector3d v) {
/* 396 */     this.x -= v.x;
/* 397 */     this.y -= v.y;
/* 398 */     this.z -= v.z;
/* 399 */     this.hash = 0;
/* 400 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d subtract(@Nonnull Vector3i v) {
/* 411 */     this.x -= v.x;
/* 412 */     this.y -= v.y;
/* 413 */     this.z -= v.z;
/* 414 */     this.hash = 0;
/* 415 */     return this;
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
/*     */   public Vector3d subtract(double x, double y, double z) {
/* 428 */     this.x -= x;
/* 429 */     this.y -= y;
/* 430 */     this.z -= z;
/* 431 */     this.hash = 0;
/* 432 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d subtract(double value) {
/* 443 */     this.x -= value;
/* 444 */     this.y -= value;
/* 445 */     this.z -= value;
/* 446 */     this.hash = 0;
/* 447 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d negate() {
/* 457 */     this.x = -this.x;
/* 458 */     this.y = -this.y;
/* 459 */     this.z = -this.z;
/* 460 */     this.hash = 0;
/* 461 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d scale(double s) {
/* 472 */     this.x *= s;
/* 473 */     this.y *= s;
/* 474 */     this.z *= s;
/* 475 */     this.hash = 0;
/* 476 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d scale(@Nonnull Vector3d p) {
/* 487 */     this.x *= p.x;
/* 488 */     this.y *= p.y;
/* 489 */     this.z *= p.z;
/* 490 */     this.hash = 0;
/* 491 */     return this;
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
/*     */   public Vector3d cross(@Nonnull Vector3d v) {
/* 503 */     double x0 = this.y * v.z - this.z * v.y;
/* 504 */     double y0 = this.z * v.x - this.x * v.z;
/* 505 */     double z0 = this.x * v.y - this.y * v.x;
/* 506 */     return new Vector3d(x0, y0, z0);
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
/*     */   public Vector3d cross(@Nonnull Vector3d v, @Nonnull Vector3d res) {
/* 518 */     res.assign(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/* 519 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(@Nonnull Vector3d other) {
/* 529 */     return this.x * other.x + this.y * other.y + this.z * other.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector3d v) {
/* 539 */     return Math.sqrt(distanceSquaredTo(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector3i v) {
/* 549 */     return Math.sqrt(distanceSquaredTo(v));
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
/*     */   public double distanceTo(double x, double y, double z) {
/* 561 */     return Math.sqrt(distanceSquaredTo(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSquaredTo(@Nonnull Vector3d v) {
/* 572 */     double x0 = v.x - this.x;
/* 573 */     double y0 = v.y - this.y;
/* 574 */     double z0 = v.z - this.z;
/* 575 */     return x0 * x0 + y0 * y0 + z0 * z0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSquaredTo(@Nonnull Vector3i v) {
/* 586 */     double x0 = v.x - this.x;
/* 587 */     double y0 = v.y - this.y;
/* 588 */     double z0 = v.z - this.z;
/* 589 */     return x0 * x0 + y0 * y0 + z0 * z0;
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
/*     */   public double distanceSquaredTo(double x, double y, double z) {
/* 601 */     x -= this.x;
/* 602 */     y -= this.y;
/* 603 */     z -= this.z;
/* 604 */     return x * x + y * y + z * z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d normalize() {
/* 612 */     return setLength(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 619 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squaredLength() {
/* 626 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d setLength(double newLen) {
/* 637 */     return scale(newLen / length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d clampLength(double maxLength) {
/* 648 */     double length = length();
/* 649 */     if (maxLength > length) return this; 
/* 650 */     return scale(maxLength / length);
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
/*     */   public Vector3d rotateX(float angle) {
/* 662 */     double cos = TrigMathUtil.cos(angle);
/* 663 */     double sin = TrigMathUtil.sin(angle);
/* 664 */     double cy = this.y * cos - this.z * sin;
/* 665 */     double cz = this.y * sin + this.z * cos;
/* 666 */     this.y = cy;
/* 667 */     this.z = cz;
/* 668 */     return this;
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
/*     */   public Vector3d rotateY(float angle) {
/* 680 */     double cos = TrigMathUtil.cos(angle);
/* 681 */     double sin = TrigMathUtil.sin(angle);
/* 682 */     double cx = this.x * cos + this.z * sin;
/* 683 */     double cz = this.x * -sin + this.z * cos;
/* 684 */     this.x = cx;
/* 685 */     this.z = cz;
/* 686 */     return this;
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
/*     */   public Vector3d rotateZ(float angle) {
/* 698 */     double cos = TrigMathUtil.cos(angle);
/* 699 */     double sin = TrigMathUtil.sin(angle);
/* 700 */     double cx = this.x * cos - this.y * sin;
/* 701 */     double cy = this.x * sin + this.y * cos;
/* 702 */     this.x = cx;
/* 703 */     this.y = cy;
/* 704 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d floor() {
/* 714 */     this.x = MathUtil.fastFloor(this.x);
/* 715 */     this.y = MathUtil.fastFloor(this.y);
/* 716 */     this.z = MathUtil.fastFloor(this.z);
/* 717 */     this.hash = 0;
/* 718 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d ceil() {
/* 728 */     this.x = MathUtil.fastCeil(this.x);
/* 729 */     this.y = MathUtil.fastCeil(this.y);
/* 730 */     this.z = MathUtil.fastCeil(this.z);
/* 731 */     this.hash = 0;
/* 732 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d clipToZero(double epsilon) {
/* 743 */     this.x = MathUtil.clipToZero(this.x, epsilon);
/* 744 */     this.y = MathUtil.clipToZero(this.y, epsilon);
/* 745 */     this.z = MathUtil.clipToZero(this.z, epsilon);
/* 746 */     this.hash = 0;
/* 747 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closeToZero(double epsilon) {
/* 757 */     return (MathUtil.closeToZero(this.x, epsilon) && 
/* 758 */       MathUtil.closeToZero(this.y, epsilon) && 
/* 759 */       MathUtil.closeToZero(this.z, epsilon));
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
/*     */   public boolean isInside(int x, int y, int z) {
/* 771 */     double dx = this.x - x;
/* 772 */     double dy = this.y - y;
/* 773 */     double dz = this.z - z;
/* 774 */     return (dx >= 0.0D && dx < 1.0D && dy >= 0.0D && dy < 1.0D && dz >= 0.0D && dz < 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinite() {
/* 781 */     return (Double.isFinite(this.x) && Double.isFinite(this.y) && Double.isFinite(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d dropHash() {
/* 791 */     this.hash = 0;
/* 792 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d clone() {
/* 799 */     return new Vector3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 804 */     if (this == o) return true; 
/* 805 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 807 */     Vector3d vector3d = (Vector3d)o;
/* 808 */     return (vector3d.x == this.x && vector3d.y == this.y && vector3d.z == this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Vector3d o) {
/* 818 */     if (o == null) return false; 
/* 819 */     return (o.x == this.x && o.y == this.y && o.z == this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 824 */     if (this.hash == 0) {
/* 825 */       this.hash = (int)HashUtil.hash(Double.doubleToLongBits(this.x), Double.doubleToLongBits(this.y), Double.doubleToLongBits(this.z));
/*     */     }
/* 827 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 833 */     return "Vector3d{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
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
/*     */   @Nonnull
/*     */   public static Vector3d max(@Nonnull Vector3d a, @Nonnull Vector3d b) {
/* 849 */     return new Vector3d(
/* 850 */         Math.max(a.x, b.x), 
/* 851 */         Math.max(a.y, b.y), 
/* 852 */         Math.max(a.z, b.z));
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
/*     */   public static Vector3d min(@Nonnull Vector3d a, @Nonnull Vector3d b) {
/* 865 */     return new Vector3d(
/* 866 */         Math.min(a.x, b.x), 
/* 867 */         Math.min(a.y, b.y), 
/* 868 */         Math.min(a.z, b.z));
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
/*     */   public static Vector3d lerp(@Nonnull Vector3d a, @Nonnull Vector3d b, double t) {
/* 882 */     return lerpUnclamped(a, b, MathUtil.clamp(t, 0.0D, 1.0D));
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
/*     */   public static Vector3d lerpUnclamped(@Nonnull Vector3d a, @Nonnull Vector3d b, double t) {
/* 895 */     return new Vector3d(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y), a.z + t * (b.z - a.z));
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
/*     */   @Nonnull
/*     */   public static Vector3d directionTo(@Nonnull Vector3d from, @Nonnull Vector3d to) {
/* 911 */     return to.clone().subtract(from).normalize();
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
/*     */   public static Vector3d directionTo(@Nonnull Vector3i from, @Nonnull Vector3d to) {
/* 923 */     return to.clone().subtract(from).normalize();
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
/*     */   public static Vector3d add(@Nonnull Vector3d one, @Nonnull Vector3d two) {
/* 935 */     return (new Vector3d()).add(one).add(two);
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
/*     */   public static Vector3d add(@Nonnull Vector3d one, @Nonnull Vector3d two, @Nonnull Vector3d three) {
/* 948 */     return (new Vector3d()).add(one).add(two).add(three);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String formatShortString(@Nullable Vector3d v) {
/* 959 */     if (v == null) return ""; 
/* 960 */     return "" + v.x + "/" + v.x + "/" + v.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i toVector3i() {
/* 968 */     return new Vector3i(MathUtil.floor(this.x), MathUtil.floor(this.y), MathUtil.floor(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f toVector3f() {
/* 976 */     return new Vector3f((float)this.x, (float)this.y, (float)this.z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector3d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */