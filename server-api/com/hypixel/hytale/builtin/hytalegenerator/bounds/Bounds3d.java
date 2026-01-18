/*     */ package com.hypixel.hytale.builtin.hytalegenerator.bounds;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class Bounds3d
/*     */   implements MemInstrument {
/*     */   public final Vector3d min;
/*     */   public final Vector3d max;
/*     */   
/*     */   public Bounds3d() {
/*  13 */     this(Vector3d.ZERO, Vector3d.ZERO);
/*     */   }
/*     */   
/*     */   public Bounds3d(@Nonnull Vector3d min, @Nonnull Vector3d max) {
/*  17 */     this.min = min.clone();
/*  18 */     this.max = max.clone();
/*  19 */     correct();
/*     */   }
/*     */   
/*     */   public boolean contains(@Nonnull Vector3d position) {
/*  23 */     return (position.x >= this.min.x && position.y >= this.min.y && position.z >= this.min.z && position.x < this.max.x && position.y < this.max.y && position.z < this.max.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(@Nonnull Bounds3d other) {
/*  28 */     return (other.min.x >= this.min.x && other.min.y >= this.min.y && other.min.z >= this.min.z && other.max.x <= this.max.x && other.max.y <= this.max.y && other.max.z <= this.max.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(@Nonnull Bounds3d other) {
/*  33 */     return (this.min.x < other.max.x && this.min.y < other.max.y && this.min.z < other.max.z && this.max.x > other.min.x && this.max.y > other.min.y && this.max.z > other.min.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZeroVolume() {
/*  38 */     return (this.min.x >= this.max.x || this.min.y >= this.max.y || this.min.z >= this.max.z);
/*     */   }
/*     */   
/*     */   public Vector3d getSize() {
/*  42 */     return this.max.clone().subtract(this.min);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d assign(@Nonnull Bounds3d other) {
/*  47 */     this.min.assign(other.min);
/*  48 */     this.max.assign(other.max);
/*  49 */     correct();
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d assign(@Nonnull Vector3d min, @Nonnull Vector3d max) {
/*  55 */     this.min.assign(min);
/*  56 */     this.max.assign(max);
/*  57 */     correct();
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d offset(@Nonnull Vector3d vector) {
/*  63 */     this.min.add(vector);
/*  64 */     this.max.add(vector);
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d intersect(@Nonnull Bounds3d other) {
/*  70 */     if (!intersects(other)) {
/*  71 */       this.min.assign(Vector3d.ZERO);
/*  72 */       this.max.assign(Vector3d.ZERO);
/*     */     } 
/*     */     
/*  75 */     this.min.assign(Math.max(this.min.x, other.min.x), Math.max(this.min.y, other.min.y), Math.max(this.min.z, other.min.z));
/*  76 */     this.max.assign(Math.min(this.max.x, other.max.x), Math.min(this.max.y, other.max.y), Math.min(this.max.z, other.max.z));
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d encompass(@Nonnull Bounds3d other) {
/*  82 */     if (other.isZeroVolume()) {
/*  83 */       return this;
/*     */     }
/*  85 */     if (isZeroVolume()) {
/*  86 */       this.min.assign(other.min);
/*  87 */       this.max.assign(other.max);
/*  88 */       return this;
/*     */     } 
/*  90 */     this.min.assign(Math.min(this.min.x, other.min.x), Math.min(this.min.y, other.min.y), Math.min(this.min.z, other.min.z));
/*  91 */     this.max.assign(Math.max(this.max.x, other.max.x), Math.max(this.max.y, other.max.y), Math.max(this.max.z, other.max.z));
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d encompass(@Nonnull Vector3d position) {
/*  97 */     this.min.assign(Math.min(this.min.x, position.x), Math.min(this.min.y, position.y), Math.min(this.min.z, position.z));
/*  98 */     this.max.assign(Math.max(this.max.x, position.x), Math.max(this.max.y, position.y), Math.max(this.max.z, position.z));
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d stack(@Nonnull Bounds3d other) {
/* 104 */     if (isZeroVolume() || other.isZeroVolume()) {
/* 105 */       return this;
/*     */     }
/* 107 */     Vector3d initialMax = this.max.clone();
/*     */     
/* 109 */     Bounds3d stamp = other.clone();
/* 110 */     stamp.offset(this.min);
/* 111 */     encompass(stamp);
/*     */     
/* 113 */     stamp = other.clone();
/* 114 */     stamp.offset(initialMax);
/* 115 */     encompass(stamp);
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d flipOnOriginPoint() {
/* 121 */     Vector3d swap = this.min.clone();
/* 122 */     this.min.assign(this.max);
/* 123 */     this.min.scale(-1.0D);
/* 124 */     this.max.assign(swap);
/* 125 */     this.max.scale(-1.0D);
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d flipOnOriginVoxel() {
/* 131 */     Vector3d swap = this.min.clone();
/* 132 */     this.min.assign(Vector3d.ALL_ONES);
/* 133 */     this.min.subtract(this.max);
/* 134 */     this.max.assign(Vector3d.ALL_ONES);
/* 135 */     this.max.subtract(swap);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d clone() {
/* 143 */     return new Bounds3d(this.min.clone(), this.max.clone());
/*     */   }
/*     */   
/*     */   public boolean isCorrect() {
/* 147 */     return (this.min.x <= this.max.x && this.min.y <= this.max.y && this.min.z <= this.max.z);
/*     */   }
/*     */   
/*     */   public void correct() {
/* 151 */     Vector3d swap = this.min.clone();
/* 152 */     this.min.assign(Math.min(this.max.x, this.min.x), Math.min(this.max.y, this.min.y), Math.min(this.max.z, this.min.z));
/* 153 */     this.max.assign(Math.max(swap.x, this.max.x), Math.max(swap.y, this.max.y), Math.max(swap.z, this.max.z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemInstrument.Report getMemoryUsage() {
/* 159 */     long size_byte = 40L;
/* 160 */     return new MemInstrument.Report(40L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\bounds\Bounds3d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */