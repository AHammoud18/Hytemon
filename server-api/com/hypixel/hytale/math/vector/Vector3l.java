/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
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
/*     */ public class Vector3l
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector3l> CODEC;
/*     */   
/*     */   static {
/*  49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3l.class, Vector3l::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.LONG), (o, i) -> o.x = i.longValue(), o -> Long.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.LONG), (o, i) -> o.y = i.longValue(), o -> Long.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.LONG), (o, i) -> o.z = i.longValue(), o -> Long.valueOf(o.z), (o, p) -> o.z = p.z).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  51 */   public static final Vector3l ZERO = new Vector3l(0L, 0L, 0L); public static final Vector3l UP;
/*  52 */   public static final Vector3l POS_Y = UP = new Vector3l(0L, 1L, 0L); public static final Vector3l DOWN;
/*  53 */   public static final Vector3l NEG_Y = DOWN = new Vector3l(0L, -1L, 0L); public static final Vector3l FORWARD;
/*  54 */   public static final Vector3l NEG_Z = FORWARD = new Vector3l(0L, 0L, -1L), NORTH = FORWARD; public static final Vector3l BACKWARD;
/*  55 */   public static final Vector3l POS_Z = BACKWARD = new Vector3l(0L, 0L, 1L), SOUTH = BACKWARD; public static final Vector3l RIGHT;
/*  56 */   public static final Vector3l POS_X = RIGHT = new Vector3l(1L, 0L, 0L), EAST = RIGHT; public static final Vector3l LEFT;
/*  57 */   public static final Vector3l NEG_X = LEFT = new Vector3l(-1L, 0L, 0L), WEST = LEFT;
/*  58 */   public static final Vector3l ALL_ONES = new Vector3l(1L, 1L, 1L);
/*  59 */   public static final Vector3l MIN = new Vector3l(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);
/*  60 */   public static final Vector3l MAX = new Vector3l(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
/*     */   
/*  62 */   public static final Vector3l[] BLOCK_SIDES = new Vector3l[] { UP, DOWN, FORWARD, BACKWARD, LEFT, RIGHT };
/*  63 */   public static final Vector3l[] BLOCK_EDGES = new Vector3l[] { 
/*  64 */       add(UP, FORWARD), add(DOWN, FORWARD), add(UP, BACKWARD), add(DOWN, BACKWARD), 
/*  65 */       add(UP, LEFT), add(DOWN, LEFT), add(UP, RIGHT), add(DOWN, RIGHT), 
/*  66 */       add(FORWARD, LEFT), add(FORWARD, RIGHT), add(BACKWARD, LEFT), add(BACKWARD, RIGHT) };
/*     */   
/*  68 */   public static final Vector3l[] BLOCK_CORNERS = new Vector3l[] {
/*  69 */       add(UP, FORWARD, LEFT), add(UP, FORWARD, RIGHT), add(DOWN, FORWARD, LEFT), add(DOWN, FORWARD, RIGHT), 
/*  70 */       add(UP, BACKWARD, LEFT), add(UP, BACKWARD, RIGHT), add(DOWN, BACKWARD, LEFT), add(DOWN, BACKWARD, RIGHT)
/*     */     };
/*  72 */   public static final Vector3l[][] BLOCK_PARTS = new Vector3l[][] { BLOCK_SIDES, BLOCK_EDGES, BLOCK_CORNERS };
/*  73 */   public static final Vector3l[] CARDINAL_DIRECTIONS = new Vector3l[] { NORTH, SOUTH, EAST, WEST };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3l() {
/*  99 */     this(0L, 0L, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3l(@Nonnull Vector3l v) {
/* 108 */     this(v.x, v.y, v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3l(long x, long y, long z) {
/* 119 */     this.x = x;
/* 120 */     this.y = y;
/* 121 */     this.z = z;
/* 122 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getX() {
/* 129 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(long x) {
/* 138 */     this.x = x;
/* 139 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getY() {
/* 146 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(long y) {
/* 155 */     this.y = y;
/* 156 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getZ() {
/* 163 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(long z) {
/* 172 */     this.z = z;
/* 173 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l assign(@Nonnull Vector3l v) {
/* 184 */     this.x = v.x;
/* 185 */     this.y = v.y;
/* 186 */     this.z = v.z;
/* 187 */     this.hash = v.hash;
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l assign(long v) {
/* 199 */     this.x = v;
/* 200 */     this.y = v;
/* 201 */     this.z = v;
/* 202 */     this.hash = 0;
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l assign(@Nonnull long[] v) {
/* 214 */     this.x = v[0];
/* 215 */     this.y = v[1];
/* 216 */     this.z = v[2];
/* 217 */     this.hash = 0;
/* 218 */     return this;
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
/*     */   public Vector3l assign(long x, long y, long z) {
/* 231 */     this.x = x;
/* 232 */     this.y = y;
/* 233 */     this.z = z;
/* 234 */     this.hash = 0;
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l add(@Nonnull Vector3l v) {
/* 246 */     this.x += v.x;
/* 247 */     this.y += v.y;
/* 248 */     this.z += v.z;
/* 249 */     this.hash = 0;
/* 250 */     return this;
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
/*     */   public Vector3l add(long x, long y, long z) {
/* 263 */     this.x += x;
/* 264 */     this.y += y;
/* 265 */     this.z += z;
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
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l addScaled(@Nonnull Vector3l v, long s) {
/* 279 */     this.x += v.x * s;
/* 280 */     this.y += v.y * s;
/* 281 */     this.z += v.z * s;
/* 282 */     this.hash = 0;
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l subtract(@Nonnull Vector3l v) {
/* 294 */     this.x -= v.x;
/* 295 */     this.y -= v.y;
/* 296 */     this.z -= v.z;
/* 297 */     this.hash = 0;
/* 298 */     return this;
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
/*     */   public Vector3l subtract(long x, long y, long z) {
/* 311 */     this.x -= x;
/* 312 */     this.y -= y;
/* 313 */     this.z -= z;
/* 314 */     this.hash = 0;
/* 315 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l negate() {
/* 325 */     this.x = -this.x;
/* 326 */     this.y = -this.y;
/* 327 */     this.z = -this.z;
/* 328 */     this.hash = 0;
/* 329 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l scale(long s) {
/* 340 */     this.x *= s;
/* 341 */     this.y *= s;
/* 342 */     this.z *= s;
/* 343 */     this.hash = 0;
/* 344 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l scale(double s) {
/* 355 */     this.x = (long)(this.x * s);
/* 356 */     this.y = (long)(this.y * s);
/* 357 */     this.z = (long)(this.z * s);
/* 358 */     this.hash = 0;
/* 359 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l scale(@Nonnull Vector3l p) {
/* 370 */     this.x *= p.x;
/* 371 */     this.y *= p.y;
/* 372 */     this.z *= p.z;
/* 373 */     this.hash = 0;
/* 374 */     return this;
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
/*     */   public Vector3l cross(@Nonnull Vector3l v) {
/* 386 */     long x0 = this.y * v.z - this.z * v.y;
/* 387 */     long y0 = this.z * v.x - this.x * v.z;
/* 388 */     long z0 = this.x * v.y - this.y * v.x;
/* 389 */     return new Vector3l(x0, y0, z0);
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
/*     */   public Vector3l cross(@Nonnull Vector3l v, @Nonnull Vector3l res) {
/* 401 */     res.assign(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/* 402 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long dot(@Nonnull Vector3l other) {
/* 412 */     return this.x * other.x + this.y * other.y + this.z * other.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector3l v) {
/* 422 */     return Math.sqrt(distanceSquaredTo(v));
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
/*     */   public double distanceTo(long x, long y, long z) {
/* 434 */     return Math.sqrt(distanceSquaredTo(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long distanceSquaredTo(@Nonnull Vector3l v) {
/* 445 */     long x0 = v.x - this.x;
/* 446 */     long y0 = v.y - this.y;
/* 447 */     long z0 = v.z - this.z;
/* 448 */     return x0 * x0 + y0 * y0 + z0 * z0;
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
/*     */   public long distanceSquaredTo(long x, long y, long z) {
/* 460 */     long dx = x - this.x;
/* 461 */     long dy = y - this.y;
/* 462 */     long dz = z - this.z;
/* 463 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l normalize() {
/* 471 */     return setLength(1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 478 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long squaredLength() {
/* 485 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l setLength(long newLen) {
/* 496 */     return scale(newLen / length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l clampLength(long maxLength) {
/* 507 */     double length = length();
/* 508 */     if (maxLength > length) return this; 
/* 509 */     return scale(maxLength / length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l dropHash() {
/* 519 */     this.hash = 0;
/* 520 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l clone() {
/* 527 */     return new Vector3l(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i toVector3i() {
/* 535 */     return new Vector3i(MathUtil.floor(this.x), MathUtil.floor(this.y), MathUtil.floor(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d toVector3d() {
/* 543 */     return new Vector3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 548 */     if (this == o) return true; 
/* 549 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 551 */     Vector3l vector3l = (Vector3l)o;
/* 552 */     return (vector3l.x == this.x && vector3l.y == this.y && vector3l.z == this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 557 */     if (this.hash == 0) {
/* 558 */       this.hash = (int)HashUtil.hash(this.x, this.y, this.z);
/*     */     }
/* 560 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 566 */     return "Vector3l{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
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
/*     */   public static Vector3l max(@Nonnull Vector3l a, @Nonnull Vector3l b) {
/* 582 */     return new Vector3l(
/* 583 */         Math.max(a.x, b.x), 
/* 584 */         Math.max(a.y, b.y), 
/* 585 */         Math.max(a.z, b.z));
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
/*     */   public static Vector3l min(@Nonnull Vector3l a, @Nonnull Vector3l b) {
/* 598 */     return new Vector3l(
/* 599 */         Math.min(a.x, b.x), 
/* 600 */         Math.min(a.y, b.y), 
/* 601 */         Math.min(a.z, b.z));
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
/*     */   public static Vector3l directionTo(@Nonnull Vector3l from, @Nonnull Vector3l to) {
/* 614 */     return to.clone().subtract(from).normalize();
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
/*     */   public static Vector3l add(@Nonnull Vector3l one, @Nonnull Vector3l two) {
/* 626 */     return (new Vector3l()).add(one).add(two);
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
/*     */   public static Vector3l add(@Nonnull Vector3l one, @Nonnull Vector3l two, @Nonnull Vector3l three) {
/* 639 */     return (new Vector3l()).add(one).add(two).add(three);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector3l.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */