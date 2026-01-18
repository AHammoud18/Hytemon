/*     */ package com.hypixel.hytale.builtin.hytalegenerator.bounds;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class Bounds3i
/*     */   implements MemInstrument {
/*     */   public final Vector3i min;
/*     */   public final Vector3i max;
/*     */   
/*     */   public Bounds3i() {
/*  13 */     this(Vector3i.ZERO, Vector3i.ZERO);
/*     */   }
/*     */   
/*     */   public Bounds3i(@Nonnull Vector3i min, @Nonnull Vector3i max) {
/*  17 */     this.min = min.clone();
/*  18 */     this.max = max.clone();
/*  19 */     correct();
/*     */   }
/*     */   
/*     */   public boolean contains(@Nonnull Vector3i position) {
/*  23 */     return (position.x >= this.min.x && position.y >= this.min.y && position.z >= this.min.z && position.x < this.max.x && position.y < this.max.y && position.z < this.max.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(@Nonnull Bounds3i other) {
/*  28 */     return (other.min.x >= this.min.x && other.min.y >= this.min.y && other.min.z >= this.min.z && other.max.x <= this.max.x && other.max.y <= this.max.y && other.max.z <= this.max.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(@Nonnull Bounds3i other) {
/*  33 */     return (this.min.x < other.max.x && this.min.y < other.max.y && this.min.z < other.max.z && this.max.x > other.min.x && this.max.y > other.min.y && this.max.z > other.min.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZeroVolume() {
/*  38 */     return (this.min.x >= this.max.x || this.min.y >= this.max.y || this.min.z >= this.max.z);
/*     */   }
/*     */   
/*     */   public Vector3i getSize() {
/*  42 */     return this.max.clone().subtract(this.min);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i assign(@Nonnull Bounds3i other) {
/*  47 */     this.min.assign(other.min);
/*  48 */     this.max.assign(other.max);
/*  49 */     correct();
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i assign(@Nonnull Vector3i min, @Nonnull Vector3i max) {
/*  55 */     this.min.assign(min);
/*  56 */     this.max.assign(max);
/*  57 */     correct();
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i offset(@Nonnull Vector3i vector) {
/*  63 */     this.min.add(vector);
/*  64 */     this.max.add(vector);
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i intersect(@Nonnull Bounds3i other) {
/*  70 */     if (!intersects(other)) {
/*  71 */       this.min.assign(Vector3i.ZERO);
/*  72 */       this.max.assign(Vector3i.ZERO);
/*     */     } 
/*     */     
/*  75 */     this.min.assign(Math.max(this.min.x, other.min.x), Math.max(this.min.y, other.min.y), Math.max(this.min.z, other.min.z));
/*  76 */     this.max.assign(Math.min(this.max.x, other.max.x), Math.min(this.max.y, other.max.y), Math.min(this.max.z, other.max.z));
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i encompass(@Nonnull Bounds3i other) {
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
/*     */   public Bounds3i encompass(@Nonnull Vector3i position) {
/*  97 */     this.min.assign(Math.min(this.min.x, position.x), Math.min(this.min.y, position.y), Math.min(this.min.z, position.z));
/*  98 */     this.max.assign(Math.max(this.max.x, position.x), Math.max(this.max.y, position.y), Math.max(this.max.z, position.z));
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i stack(@Nonnull Bounds3i other) {
/* 104 */     if (isZeroVolume() || other.isZeroVolume()) {
/* 105 */       return this;
/*     */     }
/* 107 */     Vector3i initialMax = this.max.clone();
/*     */     
/* 109 */     Bounds3i stamp = other.clone();
/* 110 */     stamp.offset(this.min);
/* 111 */     encompass(stamp);
/*     */     
/* 113 */     stamp = other.clone();
/* 114 */     stamp.offset(initialMax.clone().subtract(Vector3i.ALL_ONES));
/* 115 */     encompass(stamp);
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i flipOnOriginPoint() {
/* 121 */     Vector3i swap = this.min.clone();
/* 122 */     this.min.assign(this.max);
/* 123 */     this.min.scale(-1);
/* 124 */     this.max.assign(swap);
/* 125 */     this.max.scale(-1);
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i flipOnOriginVoxel() {
/* 131 */     Vector3i swap = this.min.clone();
/* 132 */     this.min.assign(Vector3i.ALL_ONES);
/* 133 */     this.min.subtract(this.max);
/* 134 */     this.max.assign(Vector3i.ALL_ONES);
/* 135 */     this.max.subtract(swap);
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3d toBounds3d() {
/* 141 */     return new Bounds3d(this.min.toVector3d(), this.max.toVector3d());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i clone() {
/* 148 */     return new Bounds3i(this.min.clone(), this.max.clone());
/*     */   }
/*     */   
/*     */   public boolean isCorrect() {
/* 152 */     return (this.min.x <= this.max.x && this.min.y <= this.max.y && this.min.z <= this.max.z);
/*     */   }
/*     */   
/*     */   public void correct() {
/* 156 */     Vector3i swap = this.min.clone();
/* 157 */     this.min.assign(Math.min(this.max.x, this.min.x), Math.min(this.max.y, this.min.y), Math.min(this.max.z, this.min.z));
/* 158 */     this.max.assign(Math.max(swap.x, this.max.x), Math.max(swap.y, this.max.y), Math.max(swap.z, this.max.z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemInstrument.Report getMemoryUsage() {
/* 164 */     long size_byte = 28L;
/* 165 */     return new MemInstrument.Report(28L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\bounds\Bounds3i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */