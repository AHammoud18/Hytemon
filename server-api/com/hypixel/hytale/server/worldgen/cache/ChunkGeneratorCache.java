/*     */ package com.hypixel.hytale.server.worldgen.cache;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.util.ObjectPool;
/*     */ import com.hypixel.hytale.server.worldgen.util.cache.ConcurrentSizedTimeoutCache;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkGeneratorCache
/*     */ {
/*  20 */   private static final int CONCURRENCY_LEVEL = Math.max(1, ChunkGenerator.POOL_SIZE / 2);
/*     */   
/*     */   private final ZoneBiomeResultFunction zoneBiomeResultFunction;
/*     */   
/*     */   private final BiomeCountFunction biomeCountFunction;
/*     */   
/*     */   private final HeightFunction heightFunction;
/*     */   
/*     */   private final HeightNoiseFunction heightNoiseFunction;
/*     */   
/*     */   private final ObjectPool<CoordinateCache.CoordinateKey> keyPool;
/*     */   
/*     */   private final ConcurrentSizedTimeoutCache<CoordinateCache.CoordinateKey, CoreDataCacheEntry> cache;
/*     */ 
/*     */   
/*     */   public ChunkGeneratorCache(ZoneBiomeResultFunction zoneBiomeResultFunction, BiomeCountFunction biomeCountFunction, HeightFunction heightFunction, HeightNoiseFunction heightNoiseFunction, int maxSize, long expireAfterSeconds) {
/*  36 */     this.zoneBiomeResultFunction = zoneBiomeResultFunction;
/*  37 */     this.biomeCountFunction = biomeCountFunction;
/*  38 */     this.heightFunction = heightFunction;
/*  39 */     this.heightNoiseFunction = heightNoiseFunction;
/*  40 */     this.keyPool = new ObjectPool(maxSize, CoordinateKey::new);
/*  41 */     this.cache = new ConcurrentSizedTimeoutCache(maxSize, CONCURRENCY_LEVEL, expireAfterSeconds, TimeUnit.SECONDS, this::computeKey, this::computeValue, this::destroyEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CoreDataCacheEntry get(int seed, int x, int z) {
/*  51 */     return (CoreDataCacheEntry)this.cache.get(localKey().setLocation(seed, x, z));
/*     */   }
/*     */   
/*     */   public ZoneBiomeResult getZoneBiomeResult(int seed, int x, int z) {
/*  55 */     return (get(seed, x, z)).zoneBiomeResult;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InterpolatedBiomeCountList getBiomeCountResult(int seed, int x, int z) {
/*  60 */     CoreDataCacheEntry entry = get(seed, x, z);
/*  61 */     ensureBiomeCountList(seed, x, z, entry);
/*  62 */     return entry.biomeCountList;
/*     */   }
/*     */   
/*     */   public void putHeight(int seed, int x, int z, int height) {
/*  66 */     (get(seed, x, z)).height = height;
/*     */   }
/*     */   
/*     */   public int getHeight(int seed, int x, int z) {
/*  70 */     CoreDataCacheEntry entry = get(seed, x, z);
/*  71 */     ensureHeight(seed, x, z, entry);
/*  72 */     return entry.height;
/*     */   }
/*     */   
/*     */   public void ensureBiomeCountList(int seed, int x, int z, @Nonnull CoreDataCacheEntry entry) {
/*  76 */     if (entry.biomeCountList == null) {
/*  77 */       InterpolatedBiomeCountList list = new InterpolatedBiomeCountList();
/*  78 */       this.biomeCountFunction.compute(seed, x, z, list);
/*  79 */       entry.biomeCountList = list;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ensureHeight(int seed, int x, int z, @Nonnull CoreDataCacheEntry entry) {
/*  84 */     if (entry.height == -1) {
/*  85 */       entry.height = this.heightFunction.compute(seed, x, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void ensureHeightNoise(int seed, int x, int z, @Nonnull CoreDataCacheEntry entry) {
/*  90 */     if (entry.heightNoise == Double.NEGATIVE_INFINITY) {
/*  91 */       ensureBiomeCountList(seed, x, z, entry);
/*  92 */       entry.heightNoise = this.heightNoiseFunction.compute(entry.biomeCountList);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected final CoordinateCache.CoordinateKey computeKey(CoordinateCache.CoordinateKey key) {
/*  98 */     return (CoordinateCache.CoordinateKey)this.keyPool.apply(key);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected final CoreDataCacheEntry computeValue(@Nonnull CoordinateCache.CoordinateKey key) {
/* 103 */     int seed = key.seed();
/* 104 */     long coord = key.coord();
/* 105 */     int x = ChunkUtil.xOfChunkIndex(coord);
/* 106 */     int z = ChunkUtil.zOfChunkIndex(coord);
/* 107 */     return new CoreDataCacheEntry(this.zoneBiomeResultFunction.compute(seed, x, z));
/*     */   }
/*     */   
/*     */   protected final void destroyEntry(CoordinateCache.CoordinateKey key, CoreDataCacheEntry value) {
/* 111 */     this.keyPool.recycle(key);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static CoordinateCache.CoordinateKey localKey() {
/* 116 */     return (ChunkGenerator.getResource()).cacheCoordinateKey;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ZoneBiomeResultFunction {
/*     */     ZoneBiomeResult compute(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BiomeCountFunction {
/*     */     void compute(int param1Int1, int param1Int2, int param1Int3, InterpolatedBiomeCountList param1InterpolatedBiomeCountList);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface HeightFunction {
/*     */     int compute(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface HeightNoiseFunction {
/*     */     double compute(InterpolatedBiomeCountList param1InterpolatedBiomeCountList);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\ChunkGeneratorCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */