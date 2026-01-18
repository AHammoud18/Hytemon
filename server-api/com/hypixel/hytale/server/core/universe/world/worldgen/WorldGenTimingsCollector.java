/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicLongArray;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class WorldGenTimingsCollector
/*     */ {
/*     */   public static final MetricsRegistry<WorldGenTimingsCollector> METRICS_REGISTRY;
/*     */   private static final double NANOS_TO_SECONDS = 1.0E-9D;
/*     */   private static final int WARMUP = 100;
/*     */   private static final double WARMUP_VALUE = -InfinityD;
/*     */   private static final int CHUNKS = 0;
/*     */   private static final int ZONE_BIOME_RESULT = 1;
/*     */   private static final int PREPARE = 2;
/*     */   private static final int BLOCKS = 3;
/*     */   private static final int CAVES = 4;
/*     */   private static final int PREFABS = 5;
/*     */   
/*     */   static {
/*  25 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Chunks", worldGenTimingsCollector -> Long.valueOf(worldGenTimingsCollector.chunkCounter.get()), (Codec)Codec.LONG).register("ChunkTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.getChunkTime()), (Codec)Codec.DOUBLE).register("ZoneBiomeResultTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.zoneBiomeResult()), (Codec)Codec.DOUBLE).register("PrepareTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.prepare()), (Codec)Codec.DOUBLE).register("BlocksTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.blocksGeneration()), (Codec)Codec.DOUBLE).register("CaveTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.caveGeneration()), (Codec)Codec.DOUBLE).register("PrefabTime", worldGenTimingsCollector -> Double.valueOf(worldGenTimingsCollector.prefabGeneration()), (Codec)Codec.DOUBLE).register("QueueLength", WorldGenTimingsCollector::getQueueLength, (Codec)Codec.INTEGER).register("GeneratingCount", WorldGenTimingsCollector::getGeneratingCount, (Codec)Codec.INTEGER);
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
/*     */   
/*  40 */   private final AtomicLong chunkCounter = new AtomicLong();
/*  41 */   private final AtomicLongArray times = new AtomicLongArray(6);
/*  42 */   private final AtomicLongArray counts = new AtomicLongArray(6);
/*     */ 
/*     */   
/*     */   private final ThreadPoolExecutor threadPoolExecutor;
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenTimingsCollector(ThreadPoolExecutor threadPoolExecutor) {
/*  50 */     this.threadPoolExecutor = threadPoolExecutor;
/*     */   }
/*     */   
/*     */   public double reportChunk(long nanos) {
/*  54 */     if (this.chunkCounter.incrementAndGet() > 100L) {
/*  55 */       return addAndGet(0, nanos);
/*     */     }
/*  57 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double reportZoneBiomeResult(long nanos) {
/*  61 */     if (this.chunkCounter.get() > 100L) {
/*  62 */       return addAndGet(1, nanos);
/*     */     }
/*  64 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double reportPrepare(long nanos) {
/*  68 */     if (this.chunkCounter.get() > 100L) {
/*  69 */       return addAndGet(2, nanos);
/*     */     }
/*  71 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double reportBlocksGeneration(long nanos) {
/*  75 */     if (this.chunkCounter.get() > 100L) {
/*  76 */       return addAndGet(3, nanos);
/*     */     }
/*  78 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double reportCaveGeneration(long nanos) {
/*  82 */     if (this.chunkCounter.get() > 100L) {
/*  83 */       return addAndGet(4, nanos);
/*     */     }
/*  85 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double reportPrefabGeneration(long nanos) {
/*  89 */     if (this.chunkCounter.get() > 100L) {
/*  90 */       return addAndGet(5, nanos);
/*     */     }
/*  92 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double getWarmupValue() {
/*  96 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   public double zoneBiomeResult() {
/* 100 */     return get(1);
/*     */   }
/*     */   
/*     */   public double prepare() {
/* 104 */     return get(2);
/*     */   }
/*     */   
/*     */   public double blocksGeneration() {
/* 108 */     return get(3);
/*     */   }
/*     */   
/*     */   public double caveGeneration() {
/* 112 */     return get(4);
/*     */   }
/*     */   
/*     */   public double prefabGeneration() {
/* 116 */     return get(5);
/*     */   }
/*     */   
/*     */   public long getChunkCounter() {
/* 120 */     return this.chunkCounter.get();
/*     */   }
/*     */   
/*     */   public double getChunkTime() {
/* 124 */     return get(0);
/*     */   }
/*     */   
/*     */   public int getQueueLength() {
/* 128 */     return this.threadPoolExecutor.getQueue().size();
/*     */   }
/*     */   
/*     */   public int getGeneratingCount() {
/* 132 */     return this.threadPoolExecutor.getActiveCount();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 138 */     return String.format("cnt: %s, zbr: %s, pp: %s, b: %s, c: %s, pf: %s", new Object[] {
/* 139 */           Long.valueOf(getChunkCounter()), 
/* 140 */           Double.valueOf(zoneBiomeResult()), 
/* 141 */           Double.valueOf(prepare()), 
/* 142 */           Double.valueOf(blocksGeneration()), 
/* 143 */           Double.valueOf(caveGeneration()), 
/* 144 */           Double.valueOf(prefabGeneration())
/*     */         });
/*     */   }
/*     */   
/*     */   protected double get(int index) {
/* 149 */     long sum = this.times.get(index);
/* 150 */     long count = this.counts.get(index);
/* 151 */     return getAvgSeconds(sum, count);
/*     */   }
/*     */   
/*     */   protected double addAndGet(int index, long nanos) {
/* 155 */     long sum = this.times.addAndGet(index, nanos);
/* 156 */     long count = this.counts.incrementAndGet(index);
/* 157 */     return getAvgSeconds(sum, count);
/*     */   }
/*     */   
/*     */   protected static double getAvgSeconds(long nanos, long count) {
/* 161 */     return (count == 0L) ? 0.0D : (nanos * 1.0E-9D / count);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\WorldGenTimingsCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */