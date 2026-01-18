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
/*     */ public class Vector2i
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector2i> CODEC;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector2i.class, Vector2i::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.INTEGER), (o, i) -> o.x = i.intValue(), o -> Integer.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.INTEGER), (o, i) -> o.y = i.intValue(), o -> Integer.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  42 */   public static final Vector2i ZERO = new Vector2i(0, 0); public static final Vector2i UP;
/*  43 */   public static final Vector2i POS_Y = UP = new Vector2i(0, 1); public static final Vector2i DOWN;
/*  44 */   public static final Vector2i NEG_Y = DOWN = new Vector2i(0, -1); public static final Vector2i RIGHT;
/*  45 */   public static final Vector2i POS_X = RIGHT = new Vector2i(1, 0); public static final Vector2i LEFT;
/*  46 */   public static final Vector2i NEG_X = LEFT = new Vector2i(-1, 0);
/*  47 */   public static final Vector2i ALL_ONES = new Vector2i(1, 1);
/*     */   
/*  49 */   public static final Vector2i[] DIRECTIONS = new Vector2i[] { UP, DOWN, LEFT, RIGHT };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2i() {
/*  70 */     this(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2i(@Nonnull Vector2i v) {
/*  79 */     this(v.x, v.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2i(int x, int y) {
/*  89 */     this.x = x;
/*  90 */     this.y = y;
/*  91 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  98 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 107 */     this.x = x;
/* 108 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 115 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
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
/*     */   public Vector2i assign(@Nonnull Vector2i v) {
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
/*     */   public Vector2i assign(int v) {
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
/*     */   public Vector2i assign(@Nonnull int[] v) {
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
/*     */   public Vector2i assign(int x, int y) {
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
/*     */   public Vector2i add(@Nonnull Vector2i v) {
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
/*     */   public Vector2i add(int x, int y) {
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
/*     */   public Vector2i addScaled(@Nonnull Vector2i v, int s) {
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
/*     */   public Vector2i subtract(@Nonnull Vector2i v) {
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
/*     */   public Vector2i subtract(int x, int y) {
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
/*     */   public Vector2i negate() {
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
/*     */   public Vector2i scale(int s) {
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
/*     */   public Vector2i scale(@Nonnull Vector2i p) {
/* 293 */     this.x *= p.x;
/* 294 */     this.y *= p.y;
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
/*     */   public int dot(@Nonnull Vector2i other) {
/* 306 */     return this.x * other.x + this.y * other.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector2i v) {
/* 316 */     return Math.sqrt(distanceSquaredTo(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(int x, int y) {
/* 327 */     return Math.sqrt(distanceSquaredTo(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int distanceSquaredTo(@Nonnull Vector2i v) {
/* 338 */     int x0 = v.x - this.x;
/* 339 */     int y0 = v.y - this.y;
/* 340 */     return x0 * x0 + y0 * y0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int distanceSquaredTo(int x, int y) {
/* 351 */     x -= this.x;
/* 352 */     y -= this.y;
/* 353 */     return x * x + y * y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2i normalize() {
/* 361 */     return setLength(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 368 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squaredLength() {
/* 375 */     return (this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2i setLength(int newLen) {
/* 386 */     double scale = newLen / length();
/* 387 */     this.x = (int)(this.x * scale);
/* 388 */     this.y = (int)(this.y * scale);
/* 389 */     this.hash = 0;
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2i clampLength(int maxLength) {
/* 401 */     double length = length();
/* 402 */     if (maxLength > length) return this; 
/* 403 */     double scale = maxLength / length;
/* 404 */     this.x = (int)(this.x * scale);
/* 405 */     this.y = (int)(this.y * scale);
/* 406 */     this.hash = 0;
/* 407 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2i dropHash() {
/* 417 */     this.hash = 0;
/* 418 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector2i clone() {
/* 425 */     return new Vector2i(this.x, this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 430 */     if (this == o) return true; 
/* 431 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 433 */     Vector2i vector2i = (Vector2i)o;
/* 434 */     return (vector2i.x == this.x && vector2i.y == this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 439 */     if (this.hash == 0) {
/* 440 */       this.hash = (int)HashUtil.hash(this.x, this.y);
/*     */     }
/* 442 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 448 */     return "Vector2i{x=" + this.x + ", y=" + this.y + "}";
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
/*     */   public static Vector2i max(@Nonnull Vector2i a, @Nonnull Vector2i b) {
/* 463 */     return new Vector2i(
/* 464 */         Math.max(a.x, b.x), 
/* 465 */         Math.max(a.y, b.y));
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
/*     */   public static Vector2i min(@Nonnull Vector2i a, @Nonnull Vector2i b) {
/* 478 */     return new Vector2i(
/* 479 */         Math.min(a.x, b.x), 
/* 480 */         Math.min(a.y, b.y));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector2i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */