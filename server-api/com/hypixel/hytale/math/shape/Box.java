/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*     */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Supplier;
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
/*     */ public class Box
/*     */   implements Shape
/*     */ {
/*     */   public static final Codec<Box> CODEC;
/*     */   
/*     */   static {
/*  35 */     CODEC = (Codec<Box>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Box.class, Box::new).append(new KeyedCodec("Min", (Codec)Vector3d.CODEC), (box, v) -> box.min.assign(v), box -> box.min).add()).append(new KeyedCodec("Max", (Codec)Vector3d.CODEC), (box, v) -> box.max.assign(v), box -> box.max).add()).validator((box, results) -> { if (box.width() <= 0.0D) results.fail("Width is <= 0! Given: " + box.width());  if (box.height() <= 0.0D) results.fail("Height is <= 0! Given: " + box.height());  if (box.depth() <= 0.0D) results.fail("Depth is <= 0! Given: " + box.depth());  })).build();
/*     */   }
/*     */   @Nonnull
/*     */   public static Box horizontallyCentered(double width, double height, double depth) {
/*  39 */     return new Box(-width / 2.0D, 0.0D, -depth / 2.0D, width / 2.0D, height, depth / 2.0D);
/*     */   }
/*     */   
/*  42 */   public static final Box UNIT = new Box(Vector3d.ZERO, Vector3d.ALL_ONES);
/*     */   
/*     */   @Nonnull
/*     */   public final Vector3d min;
/*     */   @Nonnull
/*     */   public final Vector3d max;
/*     */   
/*     */   public Box() {
/*  50 */     this.min = new Vector3d();
/*  51 */     this.max = new Vector3d();
/*     */   }
/*     */   
/*     */   public Box(@Nonnull Box box) {
/*  55 */     this();
/*  56 */     this.min.assign(box.min);
/*  57 */     this.max.assign(box.max);
/*     */   }
/*     */   
/*     */   public Box(@Nonnull Vector3d min, @Nonnull Vector3d max) {
/*  61 */     this();
/*  62 */     this.min.assign(min);
/*  63 */     this.max.assign(max);
/*     */   }
/*     */   
/*     */   public Box(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
/*  67 */     this();
/*  68 */     this.min.assign(xMin, yMin, zMin);
/*  69 */     this.max.assign(xMax, yMax, zMax);
/*     */   }
/*     */   
/*     */   public static Box cube(@Nonnull Vector3d min, double side) {
/*  73 */     return new Box(min.x, min.y, min.z, min.x + side, min.y + side, min.z + side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Box centeredCube(@Nonnull Vector3d center, double inradius) {
/*  80 */     return new Box(center.x - inradius, center.y - inradius, center.z - inradius, center.x + inradius, center.y + inradius, center.z + inradius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box setMinMax(@Nonnull Vector3d min, @Nonnull Vector3d max) {
/*  88 */     this.min.assign(min);
/*  89 */     this.max.assign(max);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box setMinMax(@Nonnull double[] min, @Nonnull double[] max) {
/*  95 */     this.min.assign(min);
/*  96 */     this.max.assign(max);
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box setMinMax(@Nonnull float[] min, @Nonnull float[] max) {
/* 102 */     this.min.assign(min);
/* 103 */     this.max.assign(max);
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box setEmpty() {
/* 109 */     setMinMax(Double.MAX_VALUE, -1.7976931348623157E308D);
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box setMinMax(double min, double max) {
/* 115 */     this.min.assign(min);
/* 116 */     this.max.assign(max);
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box union(@Nonnull Box bb) {
/* 122 */     if (this.min.x > bb.min.x) this.min.x = bb.min.x; 
/* 123 */     if (this.min.y > bb.min.y) this.min.y = bb.min.y; 
/* 124 */     if (this.min.z > bb.min.z) this.min.z = bb.min.z; 
/* 125 */     if (this.max.x < bb.max.x) this.max.x = bb.max.x; 
/* 126 */     if (this.max.y < bb.max.y) this.max.y = bb.max.y; 
/* 127 */     if (this.max.z < bb.max.z) this.max.z = bb.max.z; 
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box assign(@Nonnull Box other) {
/* 133 */     this.min.assign(other.min);
/* 134 */     this.max.assign(other.max);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box assign(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
/* 141 */     this.min.assign(minX, minY, minZ);
/* 142 */     this.max.assign(maxX, maxY, maxZ);
/* 143 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box minkowskiSum(@Nonnull Box bb) {
/* 148 */     this.min.subtract(bb.max);
/* 149 */     this.max.subtract(bb.min);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box scale(float scale) {
/* 155 */     this.min.scale(scale);
/* 156 */     this.max.scale(scale);
/*     */     
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box normalize() {
/* 165 */     if (this.min.x > this.max.x) {
/* 166 */       double t = this.min.x;
/* 167 */       this.min.x = this.max.x;
/* 168 */       this.max.x = t;
/*     */     } 
/* 170 */     if (this.min.y > this.max.y) {
/* 171 */       double t = this.min.y;
/* 172 */       this.min.y = this.max.y;
/* 173 */       this.max.y = t;
/*     */     } 
/* 175 */     if (this.min.z > this.max.z) {
/* 176 */       double t = this.min.z;
/* 177 */       this.min.z = this.max.z;
/* 178 */       this.max.z = t;
/*     */     } 
/*     */     
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box rotateX(float angleInRadians) {
/* 186 */     this.min.rotateX(angleInRadians);
/* 187 */     this.max.rotateX(angleInRadians);
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box rotateY(float angleInRadians) {
/* 193 */     this.min.rotateY(angleInRadians);
/* 194 */     this.max.rotateY(angleInRadians);
/* 195 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box rotateZ(float angleInRadians) {
/* 200 */     this.min.rotateZ(angleInRadians);
/* 201 */     this.max.rotateZ(angleInRadians);
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box offset(double x, double y, double z) {
/* 207 */     this.min.add(x, y, z);
/* 208 */     this.max.add(x, y, z);
/* 209 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box offset(@Nonnull Vector3d pos) {
/* 214 */     this.min.add(pos);
/* 215 */     this.max.add(pos);
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box sweep(@Nonnull Vector3d v) {
/* 221 */     if (v.x < 0.0D) {
/* 222 */       this.min.x += v.x;
/* 223 */     } else if (v.x > 0.0D) {
/* 224 */       this.max.x += v.x;
/*     */     } 
/* 226 */     if (v.y < 0.0D) {
/* 227 */       this.min.y += v.y;
/* 228 */     } else if (v.y > 0.0D) {
/* 229 */       this.max.y += v.y;
/*     */     } 
/* 231 */     if (v.z < 0.0D) {
/* 232 */       this.min.z += v.z;
/* 233 */     } else if (v.z > 0.0D) {
/* 234 */       this.max.z += v.z;
/*     */     } 
/* 236 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box extend(double extentX, double extentY, double extentZ) {
/* 241 */     this.min.subtract(extentX, extentY, extentZ);
/* 242 */     this.max.add(extentX, extentY, extentZ);
/* 243 */     return this;
/*     */   }
/*     */   
/*     */   public double width() {
/* 247 */     return this.max.x - this.min.x;
/*     */   }
/*     */   
/*     */   public double height() {
/* 251 */     return this.max.y - this.min.y;
/*     */   }
/*     */   
/*     */   public double depth() {
/* 255 */     return this.max.z - this.min.z;
/*     */   }
/*     */   
/*     */   public double dimension(@Nonnull Axis axis) {
/* 259 */     switch (axis) { default: throw new MatchException(null, null);case X: case Y: case Z: break; }  return 
/*     */ 
/*     */       
/* 262 */       depth();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getThickness() {
/* 267 */     return MathUtil.minValue(width(), height(), depth());
/*     */   }
/*     */   
/*     */   public double getMaximumThickness() {
/* 271 */     return MathUtil.maxValue(width(), height(), depth());
/*     */   }
/*     */   
/*     */   public double getVolume() {
/* 275 */     double w = width();
/* 276 */     if (w <= 0.0D) return 0.0D;
/*     */     
/* 278 */     double h = height();
/* 279 */     if (h <= 0.0D) return 0.0D;
/*     */     
/* 281 */     double d = depth();
/* 282 */     if (d <= 0.0D) return 0.0D;
/*     */     
/* 284 */     return w * h * d;
/*     */   }
/*     */   
/*     */   public boolean hasVolume() {
/* 288 */     return (this.min.x <= this.max.x && this.min.y <= this.max.y && this.min.z <= this.max.z);
/*     */   }
/*     */   
/*     */   public boolean isIntersecting(@Nonnull Box other) {
/* 292 */     return (this.min.x <= other.max.x && other.min.x <= this.max.x && this.min.y <= other.max.y && other.min.y <= this.max.y && this.min.z <= other.max.z && other.min.z <= this.max.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnitBox() {
/* 298 */     return (this.min.equals(Vector3d.ZERO) && this.max.equals(Vector3d.ALL_ONES));
/*     */   }
/*     */   
/*     */   public double middleX() {
/* 302 */     return (this.min.x + this.max.x) / 2.0D;
/*     */   }
/*     */   
/*     */   public double middleY() {
/* 306 */     return (this.min.y + this.max.y) / 2.0D;
/*     */   }
/*     */   
/*     */   public double middleZ() {
/* 310 */     return (this.min.z + this.max.z) / 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box clone() {
/* 316 */     Box box = new Box();
/* 317 */     box.assign(this);
/* 318 */     return box;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getMin() {
/* 323 */     return this.min;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getMax() {
/* 328 */     return this.max;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box getBox(double x, double y, double z) {
/* 334 */     return new Box(this.min
/* 335 */         .getX() + x, this.min.getY() + y, this.min.getZ() + z, this.max
/* 336 */         .getX() + x, this.max.getY() + y, this.max.getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPosition(double x, double y, double z) {
/* 342 */     return (x >= this.min.getX() && x <= this.max.getX() && y >= this.min
/* 343 */       .getY() && y <= this.max.getY() && z >= this.min
/* 344 */       .getZ() && z <= this.max.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void expand(double radius) {
/* 349 */     extend(radius, radius, radius);
/*     */   }
/*     */   
/*     */   public boolean containsBlock(int x, int y, int z) {
/* 353 */     int minX = MathUtil.floor(this.min.getX());
/* 354 */     int minY = MathUtil.floor(this.min.getY());
/* 355 */     int minZ = MathUtil.floor(this.min.getZ());
/* 356 */     int maxX = MathUtil.ceil(this.max.getX());
/* 357 */     int maxY = MathUtil.ceil(this.max.getY());
/* 358 */     int maxZ = MathUtil.ceil(this.max.getZ());
/* 359 */     return (x >= minX && x < maxX && y >= minY && y < maxY && z >= minZ && z < maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsBlock(@Nonnull Vector3i origin, int x, int y, int z) {
/* 365 */     return containsBlock(x - origin.getX(), y - origin.getY(), z - origin.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean forEachBlock(double x, double y, double z, double epsilon, @Nonnull TriIntPredicate consumer) {
/* 370 */     int minX = MathUtil.floor(x + this.min.getX() - epsilon);
/* 371 */     int minY = MathUtil.floor(y + this.min.getY() - epsilon);
/* 372 */     int minZ = MathUtil.floor(z + this.min.getZ() - epsilon);
/* 373 */     int maxX = MathUtil.floor(x + this.max.getX() + epsilon);
/* 374 */     int maxY = MathUtil.floor(y + this.max.getY() + epsilon);
/* 375 */     int maxZ = MathUtil.floor(z + this.max.getZ() + epsilon);
/*     */     
/* 377 */     for (int _x = minX; _x <= maxX; _x++) {
/* 378 */       for (int _y = minY; _y <= maxY; _y++) {
/* 379 */         for (int _z = minZ; _z <= maxZ; _z++) {
/* 380 */           if (!consumer.test(_x, _y, _z)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 384 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean forEachBlock(double x, double y, double z, double epsilon, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 389 */     int minX = MathUtil.floor(x + this.min.getX() - epsilon);
/* 390 */     int minY = MathUtil.floor(y + this.min.getY() - epsilon);
/* 391 */     int minZ = MathUtil.floor(z + this.min.getZ() - epsilon);
/* 392 */     int maxX = MathUtil.floor(x + this.max.getX() + epsilon);
/* 393 */     int maxY = MathUtil.floor(y + this.max.getY() + epsilon);
/* 394 */     int maxZ = MathUtil.floor(z + this.max.getZ() + epsilon);
/*     */     
/* 396 */     for (int _x = minX; _x <= maxX; _x++) {
/* 397 */       for (int _y = minY; _y <= maxY; _y++) {
/* 398 */         for (int _z = minZ; _z <= maxZ; _z++) {
/* 399 */           if (!consumer.test(_x, _y, _z, t)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 403 */     return true;
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
/*     */   public double getMaximumExtent() {
/* 416 */     double maximumExtent = 0.0D;
/* 417 */     if (-this.min.x > maximumExtent) maximumExtent = -this.min.x; 
/* 418 */     if (-this.min.y > maximumExtent) maximumExtent = -this.min.y; 
/* 419 */     if (-this.min.z > maximumExtent) maximumExtent = -this.min.z; 
/* 420 */     if (this.max.x - 1.0D > maximumExtent) maximumExtent = this.max.x - 1.0D; 
/* 421 */     if (this.max.y - 1.0D > maximumExtent) maximumExtent = this.max.y - 1.0D; 
/* 422 */     if (this.max.z - 1.0D > maximumExtent) maximumExtent = this.max.z - 1.0D; 
/* 423 */     return maximumExtent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsLine(@Nonnull Vector3d start, @Nonnull Vector3d end) {
/* 434 */     Vector3d direction = end.clone().subtract(start);
/*     */ 
/*     */     
/* 437 */     double tmin = 0.0D;
/* 438 */     double tmax = 1.0D;
/*     */ 
/*     */     
/* 441 */     if (Math.abs(direction.x) < 1.0E-10D) {
/*     */       
/* 443 */       if (start.x < this.min.x || start.x > this.max.x) {
/* 444 */         return false;
/*     */       }
/*     */     } else {
/* 447 */       double d1 = (this.min.x - start.x) / direction.x;
/* 448 */       double d2 = (this.max.x - start.x) / direction.x;
/*     */       
/* 450 */       if (d1 > d2) {
/* 451 */         double temp = d1;
/* 452 */         d1 = d2;
/* 453 */         d2 = temp;
/*     */       } 
/*     */       
/* 456 */       tmin = Math.max(tmin, d1);
/* 457 */       tmax = Math.min(tmax, d2);
/*     */       
/* 459 */       if (tmin > tmax) return false;
/*     */     
/*     */     } 
/*     */     
/* 463 */     if (Math.abs(direction.y) < 1.0E-10D) {
/* 464 */       if (start.y < this.min.y || start.y > this.max.y) {
/* 465 */         return false;
/*     */       }
/*     */     } else {
/* 468 */       double d1 = (this.min.y - start.y) / direction.y;
/* 469 */       double d2 = (this.max.y - start.y) / direction.y;
/*     */       
/* 471 */       if (d1 > d2) {
/* 472 */         double temp = d1;
/* 473 */         d1 = d2;
/* 474 */         d2 = temp;
/*     */       } 
/*     */       
/* 477 */       tmin = Math.max(tmin, d1);
/* 478 */       tmax = Math.min(tmax, d2);
/*     */       
/* 480 */       if (tmin > tmax) return false;
/*     */     
/*     */     } 
/*     */     
/* 484 */     if (Math.abs(direction.z) < 1.0E-10D) {
/* 485 */       return (start.z >= this.min.z && start.z <= this.max.z);
/*     */     }
/* 487 */     double t1 = (this.min.z - start.z) / direction.z;
/* 488 */     double t2 = (this.max.z - start.z) / direction.z;
/*     */     
/* 490 */     if (t1 > t2) {
/* 491 */       double temp = t1;
/* 492 */       t1 = t2;
/* 493 */       t2 = temp;
/*     */     } 
/*     */     
/* 496 */     tmin = Math.max(tmin, t1);
/* 497 */     tmax = Math.min(tmax, t2);
/*     */     
/* 499 */     return (tmin <= tmax);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 506 */     return "Box{min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Box.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */