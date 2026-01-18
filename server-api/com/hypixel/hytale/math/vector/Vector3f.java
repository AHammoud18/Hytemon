/*      */ package com.hypixel.hytale.math.vector;
/*      */ 
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*      */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDisplayMode;
/*      */ import com.hypixel.hytale.math.Axis;
/*      */ import com.hypixel.hytale.math.util.HashUtil;
/*      */ import com.hypixel.hytale.math.util.MathUtil;
/*      */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Vector3f
/*      */ {
/*      */   @Nonnull
/*      */   public static final BuilderCodec<Vector3f> CODEC;
/*      */   @Nonnull
/*      */   public static final BuilderCodec<Vector3f> ROTATION;
/*      */   
/*      */   static {
/*   48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3f.class, () -> new Vector3f(Float.NaN, Float.NaN, Float.NaN)).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("X", (Codec)Codec.FLOAT), (o, i) -> o.x = i.floatValue(), o -> Float.isNaN(o.x) ? null : Float.valueOf(o.x), (o, p) -> o.x = p.x).add()).appendInherited(new KeyedCodec("Y", (Codec)Codec.FLOAT), (o, i) -> o.y = i.floatValue(), o -> Float.isNaN(o.y) ? null : Float.valueOf(o.y), (o, p) -> o.y = p.y).add()).appendInherited(new KeyedCodec("Z", (Codec)Codec.FLOAT), (o, i) -> o.z = i.floatValue(), o -> Float.isNaN(o.z) ? null : Float.valueOf(o.z), (o, p) -> o.z = p.z).add()).build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     ROTATION = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Vector3f.class, () -> new Vector3f(Float.NaN, Float.NaN, Float.NaN)).metadata((Metadata)UIDisplayMode.COMPACT)).appendInherited(new KeyedCodec("Pitch", (Codec)Codec.FLOAT), (o, i) -> o.x = i.floatValue(), o -> Float.isNaN(o.x) ? null : Float.valueOf(o.x), (o, p) -> o.x = p.x).add()).appendInherited(new KeyedCodec("Yaw", (Codec)Codec.FLOAT), (o, i) -> o.y = i.floatValue(), o -> Float.isNaN(o.y) ? null : Float.valueOf(o.y), (o, p) -> o.y = p.y).add()).appendInherited(new KeyedCodec("Roll", (Codec)Codec.FLOAT), (o, i) -> o.z = i.floatValue(), o -> Float.isNaN(o.z) ? null : Float.valueOf(o.z), (o, p) -> o.z = p.z).add()).build();
/*      */   }
/*   79 */   public static final Vector3f ZERO = new Vector3f(0.0F, 0.0F, 0.0F); public static final Vector3f UP;
/*   80 */   public static final Vector3f POS_Y = UP = new Vector3f(0.0F, 1.0F, 0.0F); public static final Vector3f DOWN;
/*   81 */   public static final Vector3f NEG_Y = DOWN = new Vector3f(0.0F, -1.0F, 0.0F); public static final Vector3f FORWARD;
/*   82 */   public static final Vector3f NEG_Z = FORWARD = new Vector3f(0.0F, 0.0F, -1.0F), NORTH = FORWARD; public static final Vector3f BACKWARD;
/*   83 */   public static final Vector3f POS_Z = BACKWARD = new Vector3f(0.0F, 0.0F, 1.0F), SOUTH = BACKWARD; public static final Vector3f RIGHT;
/*   84 */   public static final Vector3f POS_X = RIGHT = new Vector3f(1.0F, 0.0F, 0.0F), EAST = RIGHT; public static final Vector3f LEFT;
/*   85 */   public static final Vector3f NEG_X = LEFT = new Vector3f(-1.0F, 0.0F, 0.0F), WEST = LEFT;
/*   86 */   public static final Vector3f ALL_ONES = new Vector3f(1.0F, 1.0F, 1.0F);
/*   87 */   public static final Vector3f MIN = new Vector3f(-3.4028235E38F, -3.4028235E38F, -3.4028235E38F);
/*   88 */   public static final Vector3f MAX = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
/*   89 */   public static final Vector3f NaN = new Vector3f(Float.NaN, Float.NaN, Float.NaN);
/*      */   
/*   91 */   public static final Vector3f[] BLOCK_SIDES = new Vector3f[] { UP, DOWN, FORWARD, BACKWARD, LEFT, RIGHT };
/*   92 */   public static final Vector3f[] BLOCK_EDGES = new Vector3f[] { 
/*   93 */       add(UP, FORWARD), add(DOWN, FORWARD), add(UP, BACKWARD), add(DOWN, BACKWARD), 
/*   94 */       add(UP, LEFT), add(DOWN, LEFT), add(UP, RIGHT), add(DOWN, RIGHT), 
/*   95 */       add(FORWARD, LEFT), add(FORWARD, RIGHT), add(BACKWARD, LEFT), add(BACKWARD, RIGHT) };
/*      */   
/*   97 */   public static final Vector3f[] BLOCK_CORNERS = new Vector3f[] {
/*   98 */       add(UP, FORWARD, LEFT), add(UP, FORWARD, RIGHT), add(DOWN, FORWARD, LEFT), add(DOWN, FORWARD, RIGHT), 
/*   99 */       add(UP, BACKWARD, LEFT), add(UP, BACKWARD, RIGHT), add(DOWN, BACKWARD, LEFT), add(DOWN, BACKWARD, RIGHT)
/*      */     };
/*  101 */   public static final Vector3f[][] BLOCK_PARTS = new Vector3f[][] { BLOCK_SIDES, BLOCK_EDGES, BLOCK_CORNERS };
/*  102 */   public static final Vector3f[] CARDINAL_DIRECTIONS = new Vector3f[] { NORTH, SOUTH, EAST, WEST };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float x;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float y;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float z;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int hash;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f() {
/*  128 */     this(0.0F, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f(@Nonnull Vector3f v) {
/*  137 */     this(v.x, v.y, v.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f(@Nonnull Vector3i v) {
/*  146 */     this(v.x, v.y, v.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f(float x, float y, float z) {
/*  157 */     this.x = x;
/*  158 */     this.y = y;
/*  159 */     this.z = z;
/*  160 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f(float yaw, float pitch) {
/*  170 */     this();
/*  171 */     assign(yaw, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3f(@Nonnull Random random, float length) {
/*  181 */     this(random.nextFloat() * 6.2831855F, random
/*  182 */         .nextFloat() * 6.2831855F);
/*  183 */     scale(length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getX() {
/*  190 */     return this.x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPitch() {
/*  197 */     return this.x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setX(float x) {
/*  206 */     this.x = x;
/*  207 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPitch(float pitch) {
/*  216 */     this.x = pitch;
/*  217 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getY() {
/*  224 */     return this.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getYaw() {
/*  231 */     return this.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setY(float y) {
/*  240 */     this.y = y;
/*  241 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setYaw(float yaw) {
/*  250 */     this.y = yaw;
/*  251 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getZ() {
/*  258 */     return this.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRoll() {
/*  265 */     return this.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZ(float z) {
/*  274 */     this.z = z;
/*  275 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRoll(float roll) {
/*  284 */     this.z = roll;
/*  285 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f assign(@Nonnull Vector3f v) {
/*  296 */     this.x = v.x;
/*  297 */     this.y = v.y;
/*  298 */     this.z = v.z;
/*  299 */     this.hash = v.hash;
/*  300 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f assign(float v) {
/*  311 */     this.x = v;
/*  312 */     this.y = v;
/*  313 */     this.z = v;
/*  314 */     this.hash = 0;
/*  315 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f assign(@Nonnull float[] v) {
/*  326 */     this.x = v[0];
/*  327 */     this.y = v[1];
/*  328 */     this.z = v[2];
/*  329 */     this.hash = 0;
/*  330 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f assign(float yaw, float pitch) {
/*  342 */     float len = TrigMathUtil.cos(pitch);
/*  343 */     float x = len * -TrigMathUtil.sin(yaw);
/*  344 */     float y = TrigMathUtil.sin(pitch);
/*  345 */     float z = len * -TrigMathUtil.cos(yaw);
/*  346 */     return assign(x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f assign(float x, float y, float z) {
/*  359 */     this.x = x;
/*  360 */     this.y = y;
/*  361 */     this.z = z;
/*  362 */     this.hash = 0;
/*  363 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f add(@Nonnull Vector3f v) {
/*  374 */     this.x += v.x;
/*  375 */     this.y += v.y;
/*  376 */     this.z += v.z;
/*  377 */     this.hash = 0;
/*  378 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f add(@Nonnull Vector3i v) {
/*  389 */     this.x += v.x;
/*  390 */     this.y += v.y;
/*  391 */     this.z += v.z;
/*  392 */     this.hash = 0;
/*  393 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f add(float x, float y, float z) {
/*  406 */     this.x += x;
/*  407 */     this.y += y;
/*  408 */     this.z += z;
/*  409 */     this.hash = 0;
/*  410 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPitch(float pitch) {
/*  419 */     this.x += pitch;
/*  420 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addYaw(float yaw) {
/*  429 */     this.y += yaw;
/*  430 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRoll(float roll) {
/*  439 */     this.z += roll;
/*  440 */     this.hash = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f addScaled(@Nonnull Vector3f v, float s) {
/*  452 */     this.x += v.x * s;
/*  453 */     this.y += v.y * s;
/*  454 */     this.z += v.z * s;
/*  455 */     this.hash = 0;
/*  456 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f subtract(@Nonnull Vector3f v) {
/*  467 */     this.x -= v.x;
/*  468 */     this.y -= v.y;
/*  469 */     this.z -= v.z;
/*  470 */     this.hash = 0;
/*  471 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f subtract(@Nonnull Vector3i v) {
/*  482 */     this.x -= v.x;
/*  483 */     this.y -= v.y;
/*  484 */     this.z -= v.z;
/*  485 */     this.hash = 0;
/*  486 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f subtract(float x, float y, float z) {
/*  499 */     this.x -= x;
/*  500 */     this.y -= y;
/*  501 */     this.z -= z;
/*  502 */     this.hash = 0;
/*  503 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRotationOnAxis(@Nonnull Axis axis, int angle) {
/*  513 */     float rad = 0.017453292F * angle;
/*  514 */     switch (axis) {
/*      */       case X:
/*  516 */         setPitch(getPitch() + rad);
/*      */         break;
/*      */       case Y:
/*  519 */         setYaw(getYaw() + rad);
/*      */         break;
/*      */       case Z:
/*  522 */         setRoll(getRoll() + rad);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flipRotationOnAxis(@Nonnull Axis axis) {
/*  533 */     addRotationOnAxis(axis, 180);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f negate() {
/*  543 */     this.x = -this.x;
/*  544 */     this.y = -this.y;
/*  545 */     this.z = -this.z;
/*  546 */     this.hash = 0;
/*  547 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f scale(float s) {
/*  558 */     this.x *= s;
/*  559 */     this.y *= s;
/*  560 */     this.z *= s;
/*  561 */     this.hash = 0;
/*  562 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f scale(@Nonnull Vector3f p) {
/*  573 */     this.x *= p.x;
/*  574 */     this.y *= p.y;
/*  575 */     this.z *= p.z;
/*  576 */     this.hash = 0;
/*  577 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f cross(@Nonnull Vector3f v) {
/*  589 */     float x0 = this.y * v.z - this.z * v.y;
/*  590 */     float y0 = this.z * v.x - this.x * v.z;
/*  591 */     float z0 = this.x * v.y - this.y * v.x;
/*  592 */     return new Vector3f(x0, y0, z0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f cross(@Nonnull Vector3f v, @Nonnull Vector3f res) {
/*  604 */     res.assign(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/*  605 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float dot(@Nonnull Vector3f other) {
/*  615 */     return this.x * other.x + this.y * other.y + this.z * other.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceTo(@Nonnull Vector3f v) {
/*  625 */     return (float)Math.sqrt(distanceSquaredTo(v));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceTo(@Nonnull Vector3i v) {
/*  635 */     return (float)Math.sqrt(distanceSquaredTo(v));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceTo(float x, float y, float z) {
/*  647 */     return (float)Math.sqrt(distanceSquaredTo(x, y, z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceSquaredTo(@Nonnull Vector3f v) {
/*  658 */     float x0 = v.x - this.x;
/*  659 */     float y0 = v.y - this.y;
/*  660 */     float z0 = v.z - this.z;
/*  661 */     return x0 * x0 + y0 * y0 + z0 * z0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceSquaredTo(@Nonnull Vector3i v) {
/*  672 */     float x0 = v.x - this.x;
/*  673 */     float y0 = v.y - this.y;
/*  674 */     float z0 = v.z - this.z;
/*  675 */     return x0 * x0 + y0 * y0 + z0 * z0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float distanceSquaredTo(float x, float y, float z) {
/*  687 */     float dx = x - this.x;
/*  688 */     float dy = y - this.y;
/*  689 */     float dz = z - this.z;
/*  690 */     return dx * dx + dy * dy + dz * dz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f normalize() {
/*  698 */     return setLength(1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float length() {
/*  705 */     return (float)Math.sqrt(squaredLength());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float squaredLength() {
/*  712 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f setLength(float newLen) {
/*  723 */     return scale(newLen / length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f clampLength(float maxLength) {
/*  734 */     float length = length();
/*  735 */     if (maxLength > length) return this; 
/*  736 */     return scale(maxLength / length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f rotateX(float angle) {
/*  748 */     float cos = TrigMathUtil.cos(angle);
/*  749 */     float sin = TrigMathUtil.sin(angle);
/*  750 */     float cy = this.y * cos - this.z * sin;
/*  751 */     float cz = this.y * sin + this.z * cos;
/*  752 */     this.y = cy;
/*  753 */     this.z = cz;
/*  754 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f rotateY(float angle) {
/*  766 */     float cos = TrigMathUtil.cos(angle);
/*  767 */     float sin = TrigMathUtil.sin(angle);
/*  768 */     float cx = this.x * cos + this.z * sin;
/*  769 */     float cz = this.x * -sin + this.z * cos;
/*  770 */     this.x = cx;
/*  771 */     this.z = cz;
/*  772 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f rotateZ(float angle) {
/*  784 */     float cos = TrigMathUtil.cos(angle);
/*  785 */     float sin = TrigMathUtil.sin(angle);
/*  786 */     float cx = this.x * cos - this.y * sin;
/*  787 */     float cy = this.x * sin + this.y * cos;
/*  788 */     this.x = cx;
/*  789 */     this.y = cy;
/*  790 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f floor() {
/*  800 */     this.x = MathUtil.fastFloor(this.x);
/*  801 */     this.y = MathUtil.fastFloor(this.y);
/*  802 */     this.z = MathUtil.fastFloor(this.z);
/*  803 */     this.hash = 0;
/*  804 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f ceil() {
/*  814 */     this.x = MathUtil.fastCeil(this.x);
/*  815 */     this.y = MathUtil.fastCeil(this.y);
/*  816 */     this.z = MathUtil.fastCeil(this.z);
/*  817 */     this.hash = 0;
/*  818 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f clipToZero(float epsilon) {
/*  829 */     this.x = MathUtil.clipToZero(this.x, epsilon);
/*  830 */     this.y = MathUtil.clipToZero(this.y, epsilon);
/*  831 */     this.z = MathUtil.clipToZero(this.z, epsilon);
/*  832 */     this.hash = 0;
/*  833 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean closeToZero(float epsilon) {
/*  843 */     return (MathUtil.closeToZero(this.x, epsilon) && 
/*  844 */       MathUtil.closeToZero(this.y, epsilon) && 
/*  845 */       MathUtil.closeToZero(this.z, epsilon));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInside(int x, int y, int z) {
/*  857 */     float dx = this.x - x;
/*  858 */     float dy = this.y - y;
/*  859 */     float dz = this.z - z;
/*  860 */     return (dx >= 0.0F && dx < 1.0F && dy >= 0.0F && dy < 1.0F && dz >= 0.0F && dz < 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFinite() {
/*  867 */     return (Float.isFinite(this.x) && Float.isFinite(this.y) && Float.isFinite(this.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f dropHash() {
/*  877 */     this.hash = 0;
/*  878 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3f clone() {
/*  885 */     return new Vector3f(this.x, this.y, this.z);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object o) {
/*  890 */     if (this == o) return true; 
/*  891 */     if (o == null || getClass() != o.getClass()) return false;
/*      */     
/*  893 */     Vector3f vector3f = (Vector3f)o;
/*  894 */     if (vector3f.x == this.x && vector3f.y == this.y && vector3f.z == this.z) {
/*  895 */       return true;
/*      */     }
/*  897 */     return (Float.isNaN(vector3f.x) && Float.isNaN(this.x) && 
/*  898 */       Float.isNaN(vector3f.y) && Float.isNaN(this.y) && 
/*  899 */       Float.isNaN(vector3f.z) && Float.isNaN(this.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Vector3f o) {
/*  910 */     if (o == null) return false;
/*      */     
/*  912 */     if (o.x == this.x && o.y == this.y && o.z == this.z) {
/*  913 */       return true;
/*      */     }
/*  915 */     return (Float.isNaN(o.x) && Float.isNaN(this.x) && 
/*  916 */       Float.isNaN(o.y) && Float.isNaN(this.y) && 
/*  917 */       Float.isNaN(o.z) && Float.isNaN(this.z));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  923 */     if (this.hash == 0) {
/*  924 */       this.hash = (int)HashUtil.hash(Float.floatToIntBits(this.x), Float.floatToIntBits(this.y), Float.floatToIntBits(this.z));
/*      */     }
/*  926 */     return this.hash;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/*  932 */     return "Vector3f{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3d toVector3d() {
/*  944 */     return new Vector3d(this.x, this.y, this.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f max(@Nonnull Vector3f a, @Nonnull Vector3f b) {
/*  956 */     return new Vector3f(
/*  957 */         Math.max(a.x, b.x), 
/*  958 */         Math.max(a.y, b.y), 
/*  959 */         Math.max(a.z, b.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f min(@Nonnull Vector3f a, @Nonnull Vector3f b) {
/*  972 */     return new Vector3f(
/*  973 */         Math.min(a.x, b.x), 
/*  974 */         Math.min(a.y, b.y), 
/*  975 */         Math.min(a.z, b.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lerp(@Nonnull Vector3f a, @Nonnull Vector3f b, float t) {
/*  989 */     return lerpUnclamped(a, b, MathUtil.clamp(t, 0.0F, 1.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lerpUnclamped(@Nonnull Vector3f a, @Nonnull Vector3f b, float t) {
/* 1002 */     return new Vector3f(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y), a.z + t * (b.z - a.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lerpAngle(@Nonnull Vector3f a, @Nonnull Vector3f b, float t) {
/* 1019 */     return lerpAngle(a, b, t, new Vector3f());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lerpAngle(@Nonnull Vector3f a, @Nonnull Vector3f b, float t, @Nonnull Vector3f target) {
/* 1033 */     target.assign(
/* 1034 */         MathUtil.lerpAngle(a.x, b.x, t), 
/* 1035 */         MathUtil.lerpAngle(a.y, b.y, t), 
/* 1036 */         MathUtil.lerpAngle(a.z, b.z, t));
/*      */     
/* 1038 */     return target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f directionTo(@Nonnull Vector3f from, @Nonnull Vector3f to) {
/* 1050 */     return to.clone().subtract(from).normalize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f add(@Nonnull Vector3f one, @Nonnull Vector3f two) {
/* 1062 */     return (new Vector3f()).add(one).add(two);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f add(@Nonnull Vector3f one, @Nonnull Vector3f two, @Nonnull Vector3f three) {
/* 1075 */     return (new Vector3f()).add(one).add(two).add(three);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lookAt(@Nonnull Vector3d relative) {
/* 1086 */     return lookAt(relative, new Vector3f());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Vector3f lookAt(@Nonnull Vector3d relative, @Nonnull Vector3f result) {
/* 1098 */     if (!MathUtil.closeToZero(relative.x) || !MathUtil.closeToZero(relative.z)) {
/* 1099 */       float yaw = TrigMathUtil.atan2((float)-relative.x, (float)-relative.z);
/* 1100 */       result.setY(MathUtil.wrapAngle(yaw));
/*      */     } 
/*      */     
/* 1103 */     double length = relative.squaredLength();
/* 1104 */     if (length > 0.0D) {
/* 1105 */       float pitch = 1.5707964F - (float)Math.acos(relative.y / Math.sqrt(length));
/* 1106 */       result.setX(MathUtil.clamp(pitch, -1.5707964F + MathUtil.PITCH_EDGE_PADDING, 1.5707964F - MathUtil.PITCH_EDGE_PADDING));
/*      */     } 
/*      */     
/* 1109 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\Vector3f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */