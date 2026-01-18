/*     */ package com.hypixel.hytale.server.worldgen.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.server.worldgen.cache.CoreDataCacheEntry;
/*     */ import com.hypixel.hytale.server.worldgen.cache.InterpolatedBiomeCountList;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeightThresholdInterpolator
/*     */ {
/*     */   public static final int MAX_RADIUS = 5;
/*     */   public static final int MAX_RADIUS2 = 25;
/*     */   private final ChunkGeneratorExecution execution;
/*     */   @Nonnull
/*     */   private final CoreDataCacheEntry[] entries;
/*     */   private final int radius;
/*     */   private final int size;
/*     */   private final int totalSize;
/*     */   
/*     */   public HeightThresholdInterpolator(ChunkGeneratorExecution execution) {
/*  24 */     this.execution = execution;
/*  25 */     this.radius = 5;
/*  26 */     this.size = 32;
/*  27 */     this.totalSize = this.size + 2 * this.radius;
/*  28 */     this.entries = new CoreDataCacheEntry[this.totalSize * this.totalSize];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CoreDataCacheEntry[] getEntries() {
/*  33 */     return this.entries;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HeightThresholdInterpolator populate(int seed) {
/*  38 */     ChunkGenerator generator = this.execution.getChunkGenerator(); int cx, mx;
/*  39 */     for (cx = -this.radius, mx = this.size + this.radius; cx < mx; cx++) {
/*  40 */       for (int cz = -this.radius, mz = this.size + this.radius; cz < mz; cz++) {
/*  41 */         setTableEntry(cx, cz, generator.getCoreData(seed, this.execution
/*     */               
/*  43 */               .globalX(cx), this.execution
/*  44 */               .globalZ(cz)));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  49 */     for (cx = 0; cx < this.size; cx++) {
/*  50 */       for (int cz = 0; cz < this.size; cz++) {
/*  51 */         CoreDataCacheEntry entry = tableEntry(cx, cz);
/*  52 */         if (entry.biomeCountList == null) {
/*  53 */           InterpolatedBiomeCountList list = new InterpolatedBiomeCountList();
/*  54 */           generateInterpolatedBiomeCountAt(cx, cz, list);
/*  55 */           entry.biomeCountList = list;
/*     */         } 
/*  57 */         if (entry.heightNoise == Double.NEGATIVE_INFINITY) {
/*  58 */           entry.heightNoise = generator.generateInterpolatedHeightNoise(entry.biomeCountList);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public void generateInterpolatedBiomeCountAt(int cx, int cz, @Nonnull InterpolatedBiomeCountList biomeCountList) {
/*  67 */     ZoneBiomeResult center = (tableEntry(cx, cz)).zoneBiomeResult;
/*  68 */     biomeCountList.setCenter(center);
/*     */     
/*  70 */     int radius = center.getBiome().getInterpolation().getRadius();
/*  71 */     int radius2 = radius * radius;
/*     */ 
/*     */     
/*  74 */     for (int ix = -radius; ix <= radius; ix++) {
/*  75 */       for (int iz = -radius; iz <= radius; iz++) {
/*  76 */         if (ix != 0 || iz != 0) {
/*     */           
/*  78 */           int distance2 = ix * ix + iz * iz;
/*  79 */           if (distance2 <= radius2) {
/*     */             
/*  81 */             ZoneBiomeResult biomeResult = (tableEntry(cx + ix, cz + iz)).zoneBiomeResult;
/*  82 */             biomeCountList.add(biomeResult, distance2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  87 */     if (biomeCountList.getBiomeIds().size() == 1) {
/*  88 */       InterpolatedBiomeCountList.BiomeCountResult result = biomeCountList.get(center.getBiome());
/*  89 */       result.heightNoise = center.heightmapNoise;
/*  90 */       result.count = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getHeightNoise(int cx, int cz) {
/*  95 */     return (tableEntry(cx, cz)).heightNoise;
/*     */   }
/*     */   
/*     */   public float getHeightThreshold(int seed, int x, int z, int y) {
/*  99 */     return interpolateHeightThreshold(seed, x, z, y);
/*     */   }
/*     */   
/*     */   private float interpolateHeightThreshold(int seed, int x, int z, int y) {
/* 103 */     CoreDataCacheEntry entry = tableEntry(this.execution.localX(x), this.execution.localZ(z));
/* 104 */     return ChunkGenerator.generateInterpolatedThreshold(seed, x, z, y, entry.biomeCountList);
/*     */   }
/*     */   
/*     */   protected CoreDataCacheEntry tableEntry(int cx, int cz) {
/* 108 */     return this.entries[indexLocal(cx, cz)];
/*     */   }
/*     */   
/*     */   protected void setTableEntry(int cx, int cz, CoreDataCacheEntry entry) {
/* 112 */     this.entries[indexLocal(cx, cz)] = entry;
/*     */   }
/*     */   
/*     */   protected ZoneBiomeResult zoneBiomeResult(int cx, int cz) {
/* 116 */     return (tableEntry(cx, cz)).zoneBiomeResult;
/*     */   }
/*     */   
/*     */   public int getLowestNonOne(int cx, int cz) {
/* 120 */     return this.execution.getChunkGenerator().generateLowestThresholdDependent((tableEntry(cx, cz)).biomeCountList);
/*     */   }
/*     */   
/*     */   public int getHighestNonZero(int cx, int cz) {
/* 124 */     return this.execution.getChunkGenerator().generateHighestThresholdDependent((tableEntry(cx, cz)).biomeCountList);
/*     */   }
/*     */   
/*     */   protected int indexLocal(int x, int z) {
/* 128 */     return (x + this.radius) * this.totalSize + z + this.radius;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\HeightThresholdInterpolator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */