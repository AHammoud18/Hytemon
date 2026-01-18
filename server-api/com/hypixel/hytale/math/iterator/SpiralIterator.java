/*     */ package com.hypixel.hytale.math.iterator;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ 
/*     */ public class SpiralIterator {
/*   7 */   public static final long MAX_RADIUS_LONG = (long)Math.sqrt(9.223372036854776E18D) / 2L - 1L;
/*   8 */   public static final int MAX_RADIUS = (int)MAX_RADIUS_LONG;
/*     */   
/*     */   private boolean setup;
/*     */   
/*     */   private int chunkX;
/*     */   
/*     */   private int chunkZ;
/*     */   private long maxI;
/*     */   private long i;
/*     */   
/*     */   public SpiralIterator(int chunkX, int chunkZ, int radius) {
/*  19 */     init(chunkX, chunkZ, radius);
/*     */   } private int x; private int z; private int dx; private int dz;
/*     */   public SpiralIterator() {}
/*     */   public SpiralIterator(int chunkX, int chunkZ, int radiusFrom, int radiusTo) {
/*  23 */     init(chunkX, chunkZ, radiusFrom, radiusTo);
/*     */   }
/*     */   
/*     */   public void init(int chunkX, int chunkZ, int radiusTo) {
/*  27 */     init(chunkX, chunkZ, 0, radiusTo);
/*     */   }
/*     */   
/*     */   public void init(int chunkX, int chunkZ, int radiusFrom, int radiusTo) {
/*  31 */     if (radiusFrom < 0) throw new IllegalArgumentException("radiusFrom must be >= 0: " + radiusFrom); 
/*  32 */     if (radiusTo <= 0) throw new IllegalArgumentException("radiusTo must be > 0: " + radiusTo); 
/*  33 */     if (radiusTo > MAX_RADIUS) throw new IllegalArgumentException("radiusTo must be < MAX_RADIUS " + MAX_RADIUS + ": " + radiusTo); 
/*  34 */     if (radiusFrom >= radiusTo) throw new IllegalArgumentException("radiusFrom must be < radiusTo: " + radiusFrom + " -> " + radiusTo);
/*     */     
/*  36 */     this.chunkX = chunkX;
/*  37 */     this.chunkZ = chunkZ;
/*     */     
/*  39 */     long widthTo = 1L + radiusTo * 2L;
/*  40 */     this.maxI = widthTo * widthTo;
/*     */     
/*  42 */     if (radiusFrom != 0) {
/*  43 */       long widthFrom = 1L + radiusFrom * 2L;
/*  44 */       this.i = widthFrom * widthFrom;
/*     */       
/*  46 */       long pos = getPosFromIndex((int)this.i);
/*  47 */       this.x = ChunkUtil.xOfChunkIndex(pos);
/*  48 */       this.z = ChunkUtil.zOfChunkIndex(pos);
/*     */       
/*  50 */       this.dx = 1;
/*  51 */       this.dz = 0;
/*     */     } else {
/*  53 */       this.i = 0L;
/*  54 */       this.x = this.z = 0;
/*     */       
/*  56 */       this.dx = 0;
/*  57 */       this.dz = -1;
/*     */     } 
/*     */     
/*  60 */     this.setup = true;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  64 */     this.setup = false;
/*     */   }
/*     */   
/*     */   public long next() {
/*  68 */     if (!this.setup) throw new IllegalStateException("SpiralIterator is not setup!");
/*     */     
/*  70 */     long chunkCoordinates = ChunkUtil.indexChunk(this.chunkX + this.x, this.chunkZ + this.z);
/*     */     
/*  72 */     if (this.x == this.z || (this.x < 0 && this.x == -this.z) || (this.x > 0 && this.x == 1 - this.z)) {
/*  73 */       int tempDx = this.dx;
/*  74 */       this.dx = -this.dz;
/*  75 */       this.dz = tempDx;
/*     */     } 
/*     */     
/*  78 */     this.x += this.dx;
/*  79 */     this.z += this.dz;
/*     */     
/*  81 */     this.i++;
/*     */     
/*  83 */     return chunkCoordinates;
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/*  87 */     return (this.i < this.maxI);
/*     */   }
/*     */   
/*     */   public boolean isSetup() {
/*  91 */     return this.setup;
/*     */   }
/*     */   
/*     */   public long getIndex() {
/*  95 */     return this.i;
/*     */   }
/*     */   
/*     */   public long getMaxIndex() {
/*  99 */     return this.maxI;
/*     */   }
/*     */   
/*     */   public int getChunkX() {
/* 103 */     return this.chunkX;
/*     */   }
/*     */   
/*     */   public int getChunkZ() {
/* 107 */     return this.chunkZ;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 111 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 115 */     return this.z;
/*     */   }
/*     */   
/*     */   public int getDx() {
/* 119 */     return this.dx;
/*     */   }
/*     */   
/*     */   public int getDz() {
/* 123 */     return this.dz;
/*     */   }
/*     */   
/*     */   public int getCurrentRadius() {
/* 127 */     return MathUtil.ceil((Math.sqrt(this.i) - 1.0D) / 2.0D);
/*     */   }
/*     */   
/*     */   public int getCompletedRadius() {
/* 131 */     return (int)((Math.sqrt(this.i) - 1.0D) / 2.0D);
/*     */   }
/*     */   
/*     */   public static long getPosFromIndex(int index) {
/* 135 */     if (index < 0) throw new IllegalArgumentException("Index mus be >= 0");
/*     */     
/* 137 */     index++;
/*     */     
/* 139 */     int k = MathUtil.ceil((Math.sqrt(index) - 1.0D) / 2.0D);
/* 140 */     int t = 2 * k;
/* 141 */     int m = (int)Math.pow((1 + t), 2.0D);
/*     */     
/* 143 */     int m1 = m - t;
/* 144 */     if (index < m1) {
/* 145 */       m = m1;
/*     */     } else {
/* 147 */       return ChunkUtil.indexChunk(k - m - index, -k);
/*     */     } 
/*     */     
/* 150 */     int m2 = m - t;
/* 151 */     if (index < m2) {
/* 152 */       m = m2;
/*     */     } else {
/* 154 */       return ChunkUtil.indexChunk(-k, -k + m - index);
/*     */     } 
/*     */     
/* 157 */     if (index >= m - t) {
/* 158 */       return ChunkUtil.indexChunk(-k + m - index, k);
/*     */     }
/* 160 */     return ChunkUtil.indexChunk(k, k - m - index - t);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\SpiralIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */