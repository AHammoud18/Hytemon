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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector3i
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Vector3i> CODEC;
/*     */   
/*     */   static {
/*  48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3i.class, Vector3i::new).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.INTEGER), (o, i) -> o.x = i.intValue(), o -> Integer.valueOf(o.x), (o, p) -> o.x = p.x).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.INTEGER), (o, i) -> o.y = i.intValue(), o -> Integer.valueOf(o.y), (o, p) -> o.y = p.y).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.INTEGER), (o, i) -> o.z = i.intValue(), o -> Integer.valueOf(o.z), (o, p) -> o.z = p.z).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  50 */   public static final Vector3i ZERO = new Vector3i(0, 0, 0); public static final Vector3i UP;
/*  51 */   public static final Vector3i POS_Y = UP = new Vector3i(0, 1, 0); public static final Vector3i DOWN;
/*  52 */   public static final Vector3i NEG_Y = DOWN = new Vector3i(0, -1, 0); public static final Vector3i FORWARD;
/*  53 */   public static final Vector3i NEG_Z = FORWARD = new Vector3i(0, 0, -1), NORTH = FORWARD; public static final Vector3i BACKWARD;
/*  54 */   public static final Vector3i POS_Z = BACKWARD = new Vector3i(0, 0, 1), SOUTH = BACKWARD; public static final Vector3i RIGHT;
/*  55 */   public static final Vector3i POS_X = RIGHT = new Vector3i(1, 0, 0), EAST = RIGHT; public static final Vector3i LEFT;
/*  56 */   public static final Vector3i NEG_X = LEFT = new Vector3i(-1, 0, 0), WEST = LEFT;
/*  57 */   public static final Vector3i ALL_ONES = new Vector3i(1, 1, 1);
/*  58 */   public static final Vector3i MIN = new Vector3i(-2147483648, -2147483648, -2147483648);
/*  59 */   public static final Vector3i MAX = new Vector3i(2147483647, 2147483647, 2147483647);
/*     */   
/*  61 */   public static final Vector3i[] BLOCK_SIDES = new Vector3i[] { UP, DOWN, FORWARD, BACKWARD, LEFT, RIGHT };
/*  62 */   public static final Vector3i[] BLOCK_EDGES = new Vector3i[] { 
/*  63 */       add(UP, FORWARD), add(DOWN, FORWARD), add(UP, BACKWARD), add(DOWN, BACKWARD), 
/*  64 */       add(UP, LEFT), add(DOWN, LEFT), add(UP, RIGHT), add(DOWN, RIGHT), 
/*  65 */       add(FORWARD, LEFT), add(FORWARD, RIGHT), add(BACKWARD, LEFT), add(BACKWARD, RIGHT) };
/*     */   
/*  67 */   public static final Vector3i[] BLOCK_CORNERS = new Vector3i[] {
/*  68 */       add(UP, FORWARD, LEFT), add(UP, FORWARD, RIGHT), add(DOWN, FORWARD, LEFT), add(DOWN, FORWARD, RIGHT), 
/*  69 */       add(UP, BACKWARD, LEFT), add(UP, BACKWARD, RIGHT), add(DOWN, BACKWARD, LEFT), add(DOWN, BACKWARD, RIGHT)
/*     */     };
/*  71 */   public static final Vector3i[][] BLOCK_PARTS = new Vector3i[][] { BLOCK_SIDES, BLOCK_EDGES, BLOCK_CORNERS };
/*  72 */   public static final Vector3i[] CARDINAL_DIRECTIONS = new Vector3i[] { NORTH, SOUTH, EAST, WEST };
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
/*     */   public int z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3i() {
/*  98 */     this(0, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3i(@Nonnull Vector3i v) {
/* 107 */     this(v.x, v.y, v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3i(int x, int y, int z) {
/* 118 */     this.x = x;
/* 119 */     this.y = y;
/* 120 */     this.z = z;
/* 121 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 128 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 137 */     this.x = x;
/* 138 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 145 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/* 154 */     this.y = y;
/* 155 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 162 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZ(int z) {
/* 171 */     this.z = z;
/* 172 */     this.hash = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i assign(@Nonnull Vector3i v) {
/* 183 */     this.x = v.x;
/* 184 */     this.y = v.y;
/* 185 */     this.z = v.z;
/* 186 */     this.hash = v.hash;
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i assign(int v) {
/* 198 */     this.x = v;
/* 199 */     this.y = v;
/* 200 */     this.z = v;
/* 201 */     this.hash = 0;
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i assign(@Nonnull int[] v) {
/* 213 */     this.x = v[0];
/* 214 */     this.y = v[1];
/* 215 */     this.z = v[2];
/* 216 */     this.hash = 0;
/* 217 */     return this;
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
/*     */   public Vector3i assign(int x, int y, int z) {
/* 230 */     this.x = x;
/* 231 */     this.y = y;
/* 232 */     this.z = z;
/* 233 */     this.hash = 0;
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i add(@Nonnull Vector3i v) {
/* 245 */     this.x += v.x;
/* 246 */     this.y += v.y;
/* 247 */     this.z += v.z;
/* 248 */     this.hash = 0;
/* 249 */     return this;
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
/*     */   public Vector3i add(int x, int y, int z) {
/* 262 */     this.x += x;
/* 263 */     this.y += y;
/* 264 */     this.z += z;
/* 265 */     this.hash = 0;
/* 266 */     return this;
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
/*     */   public Vector3i addScaled(@Nonnull Vector3i v, int s) {
/* 278 */     this.x += v.x * s;
/* 279 */     this.y += v.y * s;
/* 280 */     this.z += v.z * s;
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
/*     */   public Vector3i subtract(@Nonnull Vector3i v) {
/* 293 */     this.x -= v.x;
/* 294 */     this.y -= v.y;
/* 295 */     this.z -= v.z;
/* 296 */     this.hash = 0;
/* 297 */     return this;
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
/*     */   public Vector3i subtract(int x, int y, int z) {
/* 310 */     this.x -= x;
/* 311 */     this.y -= y;
/* 312 */     this.z -= z;
/* 313 */     this.hash = 0;
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i negate() {
/* 324 */     this.x = -this.x;
/* 325 */     this.y = -this.y;
/* 326 */     this.z = -this.z;
/* 327 */     this.hash = 0;
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i scale(int s) {
/* 339 */     this.x *= s;
/* 340 */     this.y *= s;
/* 341 */     this.z *= s;
/* 342 */     this.hash = 0;
/* 343 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i scale(double s) {
/* 354 */     this.x = (int)(this.x * s);
/* 355 */     this.y = (int)(this.y * s);
/* 356 */     this.z = (int)(this.z * s);
/* 357 */     this.hash = 0;
/* 358 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i scale(@Nonnull Vector3i p) {
/* 369 */     this.x *= p.x;
/* 370 */     this.y *= p.y;
/* 371 */     this.z *= p.z;
/* 372 */     this.hash = 0;
/* 373 */     return this;
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
/*     */   public Vector3i cross(@Nonnull Vector3i v) {
/* 385 */     int x0 = this.y * v.z - this.z * v.y;
/* 386 */     int y0 = this.z * v.x - this.x * v.z;
/* 387 */     int z0 = this.x * v.y - this.y * v.x;
/* 388 */     return new Vector3i(x0, y0, z0);
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
/*     */   public Vector3i cross(@Nonnull Vector3i v, @Nonnull Vector3i res) {
/* 400 */     res.assign(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int dot(@Nonnull Vector3i other) {
/* 411 */     return this.x * other.x + this.y * other.y + this.z * other.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(@Nonnull Vector3i v) {
/* 421 */     return Math.sqrt(distanceSquaredTo(v));
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
/*     */   public double distanceTo(int x, int y, int z) {
/* 433 */     return Math.sqrt(distanceSquaredTo(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int distanceSquaredTo(@Nonnull Vector3i v) {
/* 444 */     int x0 = v.x - this.x;
/* 445 */     int y0 = v.y - this.y;
/* 446 */     int z0 = v.z - this.z;
/* 447 */     return x0 * x0 + y0 * y0 + z0 * z0;
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
/*     */   public int distanceSquaredTo(int x, int y, int z) {
/* 459 */     int dx = x - this.x;
/* 460 */     int dy = y - this.y;
/* 461 */     int dz = z - this.z;
/* 462 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i normalize() {
/* 470 */     return setLength(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/* 477 */     return Math.sqrt(squaredLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int squaredLength() {
/* 484 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i setLength(int newLen) {
/* 495 */     return scale(newLen / length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i clampLength(int maxLength) {
/* 506 */     double length = length();
/* 507 */     if (maxLength > length) return this; 
/* 508 */     return scale(maxLength / length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i dropHash() {
/* 518 */     this.hash = 0;
/* 519 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i clone() {
/* 526 */     return new Vector3i(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d toVector3d() {
/* 534 */     return new Vector3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f toVector3f() {
/* 542 */     return new Vector3f(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3l toVector3l() {
/* 550 */     return new Vector3l(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 555 */     if (this == o) return true; 
/* 556 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 558 */     Vector3i vector3i = (Vector3i)o;
/* 559 */     return (vector3i.x == this.x && vector3i.y == this.y && vector3i.z == this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 564 */     if (this.hash == 0) {
/* 565 */       this.hash = (int)HashUtil.hash(this.x, this.y, this.z);
/*     */     }
/* 567 */     return this.hash;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 573 */     return "Vector3i{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
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
/*     */   public static Vector3i max(@Nonnull Vector3i a, @Nonnull Vector3i b) {
/* 589 */     return new Vector3i(
/* 590 */         Math.max(a.x, b.x), 
/* 591 */         Math.max(a.y, b.y), 
/* 592 */         Math.max(a.z, b.z));
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
/*     */   public static Vector3i min(@Nonnull Vector3i a, @Nonnull Vector3i b) {
/* 605 */     return new Vector3i(
/* 606 */         Math.min(a.x, b.x), 
/* 607 */         Math.min(a.y, b.y), 
/* 608 */         Math.min(a.z, b.z));
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
/*     */   public static Vector3i directionTo(@Nonnull Vector3i from, @Nonnull Vector3i to) {
/* 621 */     return to.clone().subtract(from).normalize();
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
/*     */   public static Vector3i add(@Nonnull Vector3i one, @Nonnull Vector3i two) {
/* 633 */     return (new Vector3i()).add(one).add(two);
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
/*     */   public static Vector3i add(@Nonnull Vector3i one, @Nonnull Vector3i two, @Nonnull Vector3i three) {
/* 646 */     return (new Vector3i()).add(one).add(two).add(three);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector3i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */