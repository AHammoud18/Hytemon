/*     */ package com.hypixel.hytale.math.iterator;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class CircleSpiralIterator
/*     */ {
/*   9 */   public static final long MAX_RADIUS_LONG = (long)Math.sqrt(9.223372036854776E18D) / 2L - 1L;
/*  10 */   public static final int MAX_RADIUS = (int)MAX_RADIUS_LONG;
/*     */   private boolean setup;
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private long maxI;
/*     */   private long i;
/*     */   private int x;
/*     */   private int z;
/*     */   private int dx;
/*     */   private int dz;
/*     */   private long radiusFromSq;
/*     */   private long radiusToSq;
/*     */   private boolean hasNext;
/*     */   private long nextChunk;
/*     */   
/*     */   public void init(int chunkX, int chunkZ, int radiusTo) {
/*  26 */     init(chunkX, chunkZ, 0, radiusTo);
/*     */   }
/*     */   
/*     */   public void init(int chunkX, int chunkZ, int radiusFrom, int radiusTo) {
/*  30 */     if (radiusFrom < 0) throw new IllegalArgumentException("radiusFrom must be >= 0: " + radiusFrom); 
/*  31 */     if (radiusTo <= 0) throw new IllegalArgumentException("radiusTo must be > 0: " + radiusTo); 
/*  32 */     if (radiusTo > MAX_RADIUS) throw new IllegalArgumentException("radiusTo must be < MAX_RADIUS " + MAX_RADIUS + ": " + radiusTo); 
/*  33 */     if (radiusFrom >= radiusTo) throw new IllegalArgumentException("radiusFrom must be < radiusTo: " + radiusFrom + " -> " + radiusTo);
/*     */     
/*  35 */     this.chunkX = chunkX;
/*  36 */     this.chunkZ = chunkZ;
/*     */     
/*  38 */     this.radiusFromSq = radiusFrom * radiusFrom;
/*  39 */     this.radiusToSq = radiusTo * radiusTo;
/*     */     
/*  41 */     long widthTo = 1L + radiusTo * 2L;
/*  42 */     this.maxI = widthTo * widthTo;
/*     */     
/*  44 */     if (radiusFrom != 0) {
/*  45 */       float halfFrom = radiusFrom / 2.0F;
/*  46 */       float sq = halfFrom * halfFrom;
/*  47 */       int diagRadius = (int)Math.sqrt((sq + sq));
/*  48 */       long widthFrom = 1L + diagRadius * 2L;
/*  49 */       this.i = widthFrom * widthFrom;
/*     */       
/*  51 */       long pos = SpiralIterator.getPosFromIndex((int)this.i);
/*  52 */       this.x = ChunkUtil.xOfChunkIndex(pos);
/*  53 */       this.z = ChunkUtil.zOfChunkIndex(pos);
/*     */       
/*  55 */       this.dx = 1;
/*  56 */       this.dz = 0;
/*     */     } else {
/*  58 */       this.i = 0L;
/*  59 */       this.x = this.z = 0;
/*     */       
/*  61 */       this.dx = 0;
/*  62 */       this.dz = -1;
/*     */     } 
/*     */     
/*  65 */     this.hasNext = false;
/*  66 */     prepareNext();
/*  67 */     this.setup = true;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  71 */     this.setup = false;
/*  72 */     this.hasNext = false;
/*     */   }
/*     */   
/*     */   public long next() {
/*  76 */     if (!this.setup) throw new IllegalStateException("SpiralIterator is not setup!"); 
/*  77 */     if (!this.hasNext) prepareNext(); 
/*  78 */     if (!this.hasNext) throw new NoSuchElementException("No more positions inside the circle!"); 
/*  79 */     this.hasNext = false;
/*  80 */     return this.nextChunk;
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/*  84 */     if (!this.setup) throw new IllegalStateException("SpiralIterator is not setup!"); 
/*  85 */     if (!this.hasNext) prepareNext(); 
/*  86 */     return this.hasNext;
/*     */   }
/*     */   
/*     */   public int getCurrentRadius() {
/*  90 */     return MathUtil.ceil((Math.sqrt(this.i) - 1.0D) / 2.0D);
/*     */   }
/*     */   
/*     */   public int getCompletedRadius() {
/*  94 */     return (int)((Math.sqrt(this.i) - 1.0D) / 2.0D);
/*     */   }
/*     */   
/*     */   private void prepareNext() {
/*  98 */     while (!this.hasNext && this.i < this.maxI) {
/*  99 */       long rx = this.x;
/* 100 */       long rz = this.z;
/* 101 */       long radiusSq = rx * rx + rz * rz;
/*     */       
/* 103 */       if (radiusSq >= this.radiusFromSq && radiusSq <= this.radiusToSq) {
/* 104 */         this.nextChunk = ChunkUtil.indexChunk(this.chunkX + this.x, this.chunkZ + this.z);
/* 105 */         this.hasNext = true;
/*     */       } 
/*     */       
/* 108 */       if (this.x == this.z || (this.x < 0 && this.x == -this.z) || (this.x > 0 && this.x == 1 - this.z)) {
/* 109 */         int tempDx = this.dx;
/* 110 */         this.dx = -this.dz;
/* 111 */         this.dz = tempDx;
/*     */       } 
/*     */       
/* 114 */       this.x += this.dx;
/* 115 */       this.z += this.dz;
/*     */       
/* 117 */       this.i++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\CircleSpiralIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */