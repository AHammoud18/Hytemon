/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
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
/*     */ public class Box2D
/*     */   implements Shape2D
/*     */ {
/*     */   public static final BuilderCodec<Box2D> CODEC;
/*     */   
/*     */   static {
/*  24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Box2D.class, Box2D::new).append(new KeyedCodec("Min", (Codec)Vector2d.CODEC), (shape, min) -> shape.min.assign(min), shape -> shape.min).add()).append(new KeyedCodec("Max", (Codec)Vector2d.CODEC), (shape, max) -> shape.max.assign(max), shape -> shape.max).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  32 */   public final Vector2d min = new Vector2d(); @Nonnull
/*  33 */   public final Vector2d max = new Vector2d();
/*     */ 
/*     */   
/*     */   public Box2D(@Nonnull Box2D box) {
/*  37 */     this();
/*  38 */     this.min.assign(box.min);
/*  39 */     this.max.assign(box.max);
/*     */   }
/*     */   
/*     */   public Box2D(@Nonnull Vector2d min, @Nonnull Vector2d max) {
/*  43 */     this();
/*  44 */     this.min.assign(min);
/*  45 */     this.max.assign(max);
/*     */   }
/*     */   
/*     */   public Box2D(double xMin, double yMin, double xMax, double yMax) {
/*  49 */     this();
/*  50 */     this.min.assign(xMin, yMin);
/*  51 */     this.max.assign(xMax, yMax);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D setMinMax(@Nonnull Vector2d min, @Nonnull Vector2d max) {
/*  56 */     this.min.assign(min);
/*  57 */     this.max.assign(max);
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D setMinMax(@Nonnull double[] min, @Nonnull double[] max) {
/*  63 */     this.min.assign(min);
/*  64 */     this.max.assign(max);
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D setMinMax(@Nonnull float[] min, @Nonnull float[] max) {
/*  70 */     this.min.assign(min);
/*  71 */     this.max.assign(max);
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D setEmpty() {
/*  77 */     setMinMax(Double.MAX_VALUE, -1.7976931348623157E308D);
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D setMinMax(double min, double max) {
/*  83 */     this.min.assign(min);
/*  84 */     this.max.assign(max);
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D union(@Nonnull Box2D bb) {
/*  90 */     if (this.min.x > bb.min.x) this.min.x = bb.min.x; 
/*  91 */     if (this.min.y > bb.min.y) this.min.y = bb.min.y; 
/*  92 */     if (this.max.x < bb.max.x) this.max.x = bb.max.x; 
/*  93 */     if (this.max.y < bb.max.y) this.max.y = bb.max.y; 
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D assign(@Nonnull Box2D other) {
/*  99 */     this.min.assign(other.min);
/* 100 */     this.max.assign(other.max);
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D minkowskiSum(@Nonnull Box2D bb) {
/* 106 */     this.min.subtract(bb.max);
/* 107 */     this.max.subtract(bb.min);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box2D normalize() {
/* 115 */     if (this.min.x > this.max.x) {
/* 116 */       double t = this.min.x;
/* 117 */       this.min.x = this.max.x;
/* 118 */       this.max.x = t;
/*     */     } 
/* 120 */     if (this.min.y > this.max.y) {
/* 121 */       double t = this.min.y;
/* 122 */       this.min.y = this.max.y;
/* 123 */       this.max.y = t;
/*     */     } 
/*     */     
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D offset(@Nonnull Vector2d pos) {
/* 131 */     this.min.add(pos);
/* 132 */     this.max.add(pos);
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D sweep(@Nonnull Vector2d v) {
/* 138 */     if (v.x < 0.0D) {
/* 139 */       this.min.x += v.x;
/* 140 */     } else if (v.x > 0.0D) {
/* 141 */       this.max.x += v.x;
/*     */     } 
/* 143 */     if (v.y < 0.0D) {
/* 144 */       this.min.y += v.y;
/* 145 */     } else if (v.y > 0.0D) {
/* 146 */       this.max.y += v.y;
/*     */     } 
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D extendToInt() {
/* 153 */     this.min.floor();
/* 154 */     this.max.ceil();
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Box2D extend(double extentX, double extentY) {
/* 160 */     this.min.subtract(extentX, extentY);
/* 161 */     this.max.add(extentX, extentY);
/* 162 */     return this;
/*     */   }
/*     */   
/*     */   public double width() {
/* 166 */     return this.max.x - this.min.x;
/*     */   }
/*     */   
/*     */   public double height() {
/* 170 */     return this.max.y - this.min.y;
/*     */   }
/*     */   
/*     */   public boolean isIntersecting(@Nonnull Box2D other) {
/* 174 */     return (this.min.x <= other.max.x && other.min.x <= this.max.x && this.min.y <= other.max.y && other.min.y <= this.max.y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box2D getBox(double x, double y) {
/* 181 */     return new Box2D(this.min
/* 182 */         .getX() + x, this.min.getY() + y, this.max
/* 183 */         .getX() + x, this.max.getY() + y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPosition(@Nonnull Vector2d origin, @Nonnull Vector2d position) {
/* 189 */     double x = position.getX() - origin.getX();
/* 190 */     double y = position.getY() - origin.getY();
/* 191 */     return (x >= this.min.getX() && x <= this.max.getX() && y >= this.min
/* 192 */       .getY() && y <= this.max.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPosition(@Nonnull Vector2d origin, double xx, double yy) {
/* 197 */     double x = xx - origin.getX();
/* 198 */     double y = yy - origin.getY();
/* 199 */     return (x >= this.min.getX() && x <= this.max.getX() && y >= this.min
/* 200 */       .getY() && y <= this.max.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "Box2D{min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*     */   }
/*     */   
/*     */   public Box2D() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Box2D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */