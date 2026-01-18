/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
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
/*     */ public class Vector2l
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector2l> CODEC;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector2l.class, Vector2l::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.LONG), (o, i) -> o.x = i.longValue(), o -> Long.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.LONG), (o, i) -> o.y = i.longValue(), o -> Long.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  42 */   public static final Vector2l ZERO = new Vector2l(0L, 0L); public static final Vector2l UP;
/*  43 */   public static final Vector2l POS_Y = UP = new Vector2l(0L, 1L); public static final Vector2l DOWN;
/*  44 */   public static final Vector2l NEG_Y = DOWN = new Vector2l(0L, -1L); public static final Vector2l RIGHT;
/*  45 */   public static final Vector2l POS_X = RIGHT = new Vector2l(1L, 0L); public static final Vector2l LEFT;
/*  46 */   public static final Vector2l NEG_X = LEFT = new Vector2l(-1L, 0L);
/*  47 */   public static final Vector2l ALL_ONES = new Vector2l(1L, 1L);
/*     */   
/*  49 */   public static final Vector2l[] DIRECTIONS = new Vector2l[] { UP, DOWN, LEFT, RIGHT };
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
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2l() {
/*  70 */     this(0L, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2l(@Nonnull Vector2l v) {
/*  79 */     this(v.x, v.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2l(long x, long y) {
/*  89 */     this.x = x;
/*  90 */     this.y = y;
/*  91 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getX() {
/*  98 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(long x) {
/* 107 */     this.x = x;
/* 108 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getY() {
/* 115 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(long y) {
/* 124 */     this.y = y;
/* 125 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l assign(@Nonnull Vector2l v) {
/* 136 */     this.x = v.x;
/* 137 */     this.y = v.y;
/* 138 */     this.hash = v.hash;
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l assign(long v) {
/* 150 */     this.x = v;
/* 151 */     this.y = v;
/* 152 */     this.hash = 0;
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l assign(@Nonnull long[] v) {
/* 164 */     this.x = v[0];
/* 165 */     this.y = v[1];
/* 166 */     this.hash = 0;
/* 167 */     return this;
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
/*     */   public Vector2l assign(long x, long y) {
/* 179 */     this.x = x;
/* 180 */     this.y = y;
/* 181 */     this.hash = 0;
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l add(@Nonnull Vector2l v) {
/* 193 */     this.x += v.x;
/* 194 */     this.y += v.y;
/* 195 */     this.hash = 0;
/* 196 */     return this;
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
/*     */   public Vector2l add(long x, long y) {
/* 208 */     this.x += x;
/* 209 */     this.y += y;
/* 210 */     this.hash = 0;
/* 211 */     return this;
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
/*     */   public Vector2l addScaled(@Nonnull Vector2l v, long s) {
/* 223 */     this.x += v.x * s;
/* 224 */     this.y += v.y * s;
/* 225 */     this.hash = 0;
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l subtract(@Nonnull Vector2l v) {
/* 237 */     this.x -= v.x;
/* 238 */     this.y -= v.y;
/* 239 */     this.hash = 0;
/* 240 */     return this;
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
/*     */   public Vector2l subtract(long x, long y) {
/* 252 */     this.x -= x;
/* 253 */     this.y -= y;
/* 254 */     this.hash = 0;
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l negate() {
/* 265 */     this.x = -this.x;
/* 266 */     this.y = -this.y;
/* 267 */     this.hash = 0;
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l scale(long s) {
/* 279 */     this.x *= s;
/* 280 */     this.y *= s;
/* 281 */     this.hash = 0;
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l scale(double s) {
/* 293 */     this.x = (long)(this.x * s);
/* 294 */     this.y = (long)(this.y * s);
/* 295 */     this.hash = 0;
/* 296 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l scale(@Nonnull Vector2l p) {
/* 307 */     this.x *= p.x;
/* 308 */     this.y *= p.y;
/* 309 */     this.hash = 0;
/* 310 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long dot(@Nonnull Vector2l other) {
/* 320 */     return this.x * other.x + this.y * other.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector2l v) {
/* 330 */     return Math.sqrt(distanceSquaredTo(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(long x, long y) {
/* 341 */     return Math.sqrt(distanceSquaredTo(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long distanceSquaredTo(@Nonnull Vector2l v) {
/* 352 */     long x0 = v.x - this.x;
/* 353 */     long y0 = v.y - this.y;
/* 354 */     return x0 * x0 + y0 * y0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long distanceSquaredTo(long x, long y) {
/* 365 */     long dx = x - this.x;
/* 366 */     long dy = y - this.y;
/* 367 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l normalize() {
/* 375 */     return setLength(1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 382 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long squaredLength() {
/* 389 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l setLength(long newLen) {
/* 400 */     return scale(newLen / length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l clampLength(long maxLength) {
/* 411 */     double length = length();
/* 412 */     if (maxLength > length) return this; 
/* 413 */     return scale(maxLength / length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l dropHash() {
/* 423 */     this.hash = 0;
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2l clone() {
/* 431 */     return new Vector2l(this.x, this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 436 */     if (this == o) return true; 
/* 437 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 439 */     Vector2l vector2l = (Vector2l)o;
/* 440 */     return (vector2l.x == this.x && vector2l.y == this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 445 */     if (this.hash == 0) {
/* 446 */       this.hash = (int)HashUtil.hash(this.x, this.y);
/*     */     }
/* 448 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 454 */     return "Vector2l{x=" + this.x + ", y=" + this.y + "}";
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
/*     */   @Nonnull
/*     */   public static Vector2l max(@Nonnull Vector2l a, @Nonnull Vector2l b) {
/* 469 */     return new Vector2l(
/* 470 */         Math.max(a.x, b.x), 
/* 471 */         Math.max(a.y, b.y));
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
/*     */   public static Vector2l min(@Nonnull Vector2l a, @Nonnull Vector2l b) {
/* 484 */     return new Vector2l(
/* 485 */         Math.min(a.x, b.x), 
/* 486 */         Math.min(a.y, b.y));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector2l.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */