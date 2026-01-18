/*     */ package com.hypixel.hytale.server.worldgen.chunk;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGenBenchmark;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.IWorldMapProvider;
/*     */ import com.hypixel.hytale.server.worldgen.ChunkGeneratorResource;
/*     */ import com.hypixel.hytale.server.worldgen.benchmark.ChunkWorldgenBenchmark;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.cache.CaveGeneratorCache;
/*     */ import com.hypixel.hytale.server.worldgen.cache.ChunkGeneratorCache;
/*     */ import com.hypixel.hytale.server.worldgen.cache.CoreDataCacheEntry;
/*     */ import com.hypixel.hytale.server.worldgen.cache.InterpolatedBiomeCountList;
/*     */ import com.hypixel.hytale.server.worldgen.cache.UniquePrefabCache;
/*     */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.container.FadeContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabLoadingCache;
/*     */ import com.hypixel.hytale.server.worldgen.util.ArrayUtli;
/*     */ import com.hypixel.hytale.server.worldgen.util.ChunkThreadPoolExecutor;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZoneGeneratorResult;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternGeneratorCache;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternProvider;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ChunkGenerator implements IBenchmarkableWorldGen, ValidatableWorldGen, MetricProvider, IWorldMapProvider {
/*     */   public static final int TINT_INTERPOLATION_RADIUS = 4;
/*  59 */   private static final ThreadLocal<ChunkGeneratorResource> THREAD_LOCAL = ThreadLocal.withInitial(ChunkGeneratorResource::new);
/*     */   
/*  61 */   public static final int POOL_SIZE = Math.max(2, MathUtil.fastCeil(Runtime.getRuntime().availableProcessors() * 0.75F));
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ThreadPoolExecutor executor;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final WorldGenTimingsCollector timings;
/*     */ 
/*     */   
/*     */   private final ZonePatternProvider zonePatternProvider;
/*     */ 
/*     */   
/*     */   private final ZonePatternGeneratorCache zonePatternGeneratorCache;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ChunkGeneratorCache generatorCache;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final CaveGeneratorCache caveGeneratorCache;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final PrefabLoadingCache prefabLoadingCache;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final UniquePrefabCache uniquePrefabCache;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ChunkWorldgenBenchmark benchmark;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Supplier<GeneratedChunk> generatedChunkSupplier;
/*     */ 
/*     */   
/*     */   private final Path dataFolder;
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkGenerator(ZonePatternProvider zonePatternProvider, Path dataFolder) {
/* 107 */     this.dataFolder = dataFolder;
/*     */     
/* 109 */     this.executor = (ThreadPoolExecutor)new ChunkThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), (ThreadFactory)new ChunkWorkerThreadFactory(this, "ChunkGenerator-%d-Worker-%d"), this::onExecutorShutdown);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.executor.allowCoreThreadTimeOut(true);
/*     */     
/* 118 */     this.timings = new WorldGenTimingsCollector(this.executor);
/* 119 */     this.zonePatternProvider = zonePatternProvider;
/* 120 */     this.zonePatternGeneratorCache = new ZonePatternGeneratorCache(zonePatternProvider);
/* 121 */     this.generatorCache = new ChunkGeneratorCache(this::generateZoneBiomeResultAt, this::generateInterpolatedBiomeCountAt, this::generateHeight, this::generateInterpolatedHeightNoise, 50000, 20L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     this.caveGeneratorCache = new CaveGeneratorCache(this::generateCave, 5000, 30L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.uniquePrefabCache = new UniquePrefabCache(this::generateUniquePrefabs, 50, 300L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.prefabLoadingCache = new PrefabLoadingCache();
/* 140 */     this.generatedChunkSupplier = GeneratedChunk::new;
/* 141 */     this.benchmark = new ChunkWorldgenBenchmark();
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
/*     */   public ZonePatternProvider getZonePatternProvider() {
/* 156 */     return this.zonePatternProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenTimingsCollector getTimings() {
/* 161 */     return this.timings;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldMap getGenerator(World world) throws WorldMapLoadException {
/* 167 */     return (IWorldMap)new GeneratorChunkWorldMap(this, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public Transform[] getSpawnPoints(int seed) {
/* 172 */     return CompletableFuture.<Transform[]>supplyAsync(() -> { ArrayList<Transform> list = new ArrayList<>(); for (UniquePrefabContainer.UniquePrefabEntry entry : getUniquePrefabs(seed)) { if (entry.isSpawnLocation()) { Vector3i position = entry.getPosition(); Vector3d spawnPosition = new Vector3d(entry.getSpawnOffset()); Vector3f spawnRotation = new Vector3f(Vector3f.ZERO); entry.getRotation().rotate(spawnPosition); spawnRotation.addYaw(-entry.getRotation().getYaw()); list.add(new Transform(spawnPosition.add(position).add(0.5D, 0.0D, 0.5D), spawnRotation)); }  }  if (list.isEmpty()) list.add(new Transform(16.5D, -1.0D, 16.5D));  Transform[] array = (Transform[])list.toArray(()); Random random = (getResource()).random; random.setSeed(seed * 1494360372L); ArrayUtli.shuffleArray((Object[])array, random); return array; }this.executor)
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
/* 200 */       .join();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ChunkWorldgenBenchmark getBenchmark() {
/* 206 */     return this.benchmark;
/*     */   }
/*     */   
/*     */   public Path getDataFolder() {
/* 210 */     return this.dataFolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CoreDataCacheEntry getCoreData(int seed, int x, int z) {
/* 221 */     return this.generatorCache.get(seed, x, z);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ZonePatternGenerator getZonePatternGenerator(int seed) {
/* 226 */     return this.zonePatternGeneratorCache.get(seed);
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
/*     */   public ZoneBiomeResult getZoneBiomeResultAt(int seed, int x, int z) {
/* 239 */     return this.generatorCache.getZoneBiomeResult(seed, x, z);
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
/*     */   public int getHeight(int seed, int x, int z) {
/* 252 */     return this.generatorCache.getHeight(seed, x, z);
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
/*     */   public void putHeight(int seed, int x, int z, int y) {
/* 265 */     this.generatorCache.putHeight(seed, x, z, y);
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
/*     */   @Nullable
/*     */   public InterpolatedBiomeCountList getInterpolatedBiomeCountAt(int seed, int x, int z) {
/* 279 */     return this.generatorCache.getBiomeCountResult(seed, x, z);
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
/*     */   @Nullable
/*     */   public Cave getCave(@Nonnull CaveType caveType, int seed, int x, int z) {
/* 295 */     return (Cave)this.caveGeneratorCache.get(caveType, seed, x, z);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabLoadingCache getPrefabLoadingCache() {
/* 300 */     return this.prefabLoadingCache;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UniquePrefabContainer.UniquePrefabEntry[] getUniquePrefabs(int seed) {
/* 305 */     return this.uniquePrefabCache.get(seed);
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
/*     */   @Nonnull
/*     */   public CompletableFuture<GeneratedChunk> generate(int seed, long index, int x, int z, @Nullable LongPredicate stillNeeded) {
/* 321 */     return CompletableFuture.<GeneratedChunk>supplyAsync(() -> { if (stillNeeded == null || stillNeeded.test(index)) { long start = -System.nanoTime(); GeneratedChunk generatedChunk = this.generatedChunkSupplier.get(); GeneratedBlockChunk blockChunk = generatedChunk.getBlockChunk(); blockChunk.setCoordinates(index, x, z); GeneratedBlockStateChunk blockStateChunk = generatedChunk.getBlockStateChunk(); GeneratedEntityChunk entityChunk = generatedChunk.getEntityChunk(); Holder[] arrayOfHolder = generatedChunk.getSections(); (new ChunkGeneratorExecution(seed, this, blockChunk, blockStateChunk, entityChunk, (Holder<ChunkStore>[])arrayOfHolder)).execute(seed); long end = System.nanoTime(); double time = (end + start) / 1.0E9D; double avg = this.timings.reportChunk(end + start); if (avg != this.timings.getWarmupValue()) { LogUtil.getLogger().at(Level.FINE).log("Time taken: %s (avg: %s) (%s)", Double.valueOf(time), Double.valueOf(avg), this.timings); } else { LogUtil.getLogger().at(Level.FINE).log("Time taken: %s (warming up)", Double.valueOf(time)); }  return generatedChunk; }  return null; }this.executor)
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
/* 346 */       .exceptionally(t -> {
/*     */           throw new SkipSentryException(t);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 353 */     this.executor.shutdown();
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
/*     */   @Nonnull
/*     */   public ZoneBiomeResult generateZoneBiomeResultAt(int seed, int x, int z) {
/* 367 */     return generateZoneBiomeResultAt(seed, x, z, new ZoneBiomeResult());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ZoneBiomeResult generateZoneBiomeResultAt(int seed, int x, int z, @Nonnull ZoneBiomeResult result) {
/* 372 */     long time = -System.nanoTime();
/* 373 */     ZonePatternGenerator zonePatternGenerator = getZonePatternGenerator(seed);
/* 374 */     ZoneGeneratorResult tempZoneResult = result.getZoneResult();
/* 375 */     ZoneGeneratorResult zoneResult = zonePatternGenerator.generate(seed, x, z, (tempZoneResult != null) ? tempZoneResult : new ZoneGeneratorResult());
/*     */     
/* 377 */     Biome biome = zoneResult.getZone().biomePatternGenerator().generateBiomeAt(zoneResult, seed, x, z);
/* 378 */     double heightThresholdContext = biome.getHeightmapInterpreter().getContext(seed, x, z);
/*     */     
/* 380 */     double heightmapNoise = biome.getHeightmapNoise().get(seed, x, z);
/* 381 */     FadeContainer fadeContainer = biome.getFadeContainer();
/* 382 */     if (fadeContainer.shouldFade()) {
/* 383 */       double factor = fadeContainer.getTerrainFactor(zoneResult);
/* 384 */       heightmapNoise = heightmapNoise * (1.0D - factor) + fadeContainer.getFadeHeightmap() * factor;
/*     */     } 
/*     */     
/* 387 */     result.setZoneResult(zoneResult);
/* 388 */     result.setBiome(biome);
/* 389 */     result.setHeightThresholdContext(heightThresholdContext);
/* 390 */     result.setHeightmapNoise(heightmapNoise);
/* 391 */     this.timings.reportZoneBiomeResult(time + System.nanoTime());
/* 392 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateInterpolatedBiomeCountAt(int seed, int x, int z, @Nonnull InterpolatedBiomeCountList biomeCountList) {
/* 403 */     ZoneBiomeResult center = getZoneBiomeResultAt(seed, x, z);
/* 404 */     biomeCountList.setCenter(center);
/*     */     
/* 406 */     int radius = center.getBiome().getInterpolation().getRadius();
/* 407 */     int radius2 = radius * radius;
/*     */ 
/*     */     
/* 410 */     for (int ix = -radius; ix <= radius; ix++) {
/* 411 */       for (int iz = -radius; iz <= radius; iz++) {
/* 412 */         if (ix != 0 || iz != 0) {
/*     */           
/* 414 */           int distance2 = ix * ix + iz * iz;
/* 415 */           if (distance2 <= radius2) {
/*     */             
/* 417 */             ZoneBiomeResult biomeResult = getZoneBiomeResultAt(seed, x + ix, z + iz);
/* 418 */             biomeCountList.add(biomeResult, distance2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 423 */     if (biomeCountList.getBiomeIds().size() == 1) {
/* 424 */       InterpolatedBiomeCountList.BiomeCountResult result = biomeCountList.get(center.getBiome());
/* 425 */       result.heightNoise = center.heightmapNoise;
/* 426 */       result.count = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int generateLowestThresholdDependent(@Nonnull InterpolatedBiomeCountList biomeCounts) {
/* 431 */     int lowestNonOne = 320;
/*     */     
/* 433 */     IntList biomes = biomeCounts.getBiomeIds();
/* 434 */     for (int i = 0, size = biomes.size(); i < size; i++) {
/* 435 */       int id = biomes.getInt(i); int v;
/* 436 */       if ((v = (biomeCounts.get(id)).biome.getHeightmapInterpreter().getLowestNonOne()) < lowestNonOne) {
/* 437 */         lowestNonOne = v;
/*     */       }
/*     */     } 
/* 440 */     return lowestNonOne;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int generateHighestThresholdDependent(@Nonnull InterpolatedBiomeCountList biomeCounts) {
/* 448 */     int highestNonZero = -1;
/* 449 */     IntList biomes = biomeCounts.getBiomeIds();
/* 450 */     for (int i = 0, size = biomes.size(); i < size; i++) {
/* 451 */       int id = biomes.getInt(i);
/* 452 */       int v = (biomeCounts.get(id)).biome.getHeightmapInterpreter().getHighestNonZero();
/* 453 */       if (v > highestNonZero) highestNonZero = v; 
/*     */     } 
/* 455 */     return highestNonZero;
/*     */   }
/*     */   
/*     */   public static float generateInterpolatedThreshold(int seed, int x, int z, int y, @Nonnull InterpolatedBiomeCountList biomeCounts) {
/* 459 */     float threshold = 0.0F;
/* 460 */     int counter = 0;
/* 461 */     IntList biomes = biomeCounts.getBiomeIds();
/* 462 */     for (int i = 0, size = biomes.size(); i < size; i++) {
/* 463 */       InterpolatedBiomeCountList.BiomeCountResult r = biomeCounts.get(biomes.getInt(i));
/* 464 */       threshold += r.biome.getHeightmapInterpreter().getThreshold(seed, x, z, y, r.heightThresholdContext) * r.count;
/* 465 */       counter += r.count;
/*     */     } 
/* 467 */     return threshold / counter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double generateInterpolatedHeightNoise(@Nonnull InterpolatedBiomeCountList biomeCounts) {
/* 475 */     double n = 0.0D;
/* 476 */     int counter = 0;
/* 477 */     IntList biomes = biomeCounts.getBiomeIds();
/* 478 */     for (int i = 0, size = biomes.size(); i < size; i++) {
/* 479 */       InterpolatedBiomeCountList.BiomeCountResult r = biomeCounts.get(biomes.getInt(i));
/* 480 */       n += r.heightNoise * r.count;
/* 481 */       counter += r.count;
/*     */     } 
/* 483 */     n /= counter;
/* 484 */     return n;
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
/*     */   public int generateHeight(int seed, int x, int z) {
/* 497 */     CoreDataCacheEntry entry = getCoreData(seed, x, z);
/* 498 */     this.generatorCache.ensureHeightNoise(seed, x, z, entry);
/* 499 */     InterpolatedBiomeCountList biomeCounts = entry.biomeCountList;
/* 500 */     double heightNoise = entry.heightNoise;
/* 501 */     for (int y = generateHighestThresholdDependent(biomeCounts); y > 0; y--) {
/* 502 */       float threshold = generateInterpolatedThreshold(seed, x, z, y, biomeCounts);
/* 503 */       if (threshold > heightNoise || threshold == 1.0D) {
/* 504 */         return y;
/*     */       }
/*     */     } 
/* 507 */     return 0;
/*     */   }
/*     */   
/*     */   public int generateHeightBetween(int seed, int x, int z, @Nonnull IHeightThresholdInterpreter interpreter) {
/* 511 */     CoreDataCacheEntry entry = getCoreData(seed, x, z);
/* 512 */     this.generatorCache.ensureHeightNoise(seed, x, z, entry);
/* 513 */     InterpolatedBiomeCountList biomeCounts = entry.biomeCountList;
/* 514 */     double heightNoise = entry.heightNoise;
/* 515 */     for (int y = generateHighestThresholdDependent(biomeCounts); y > 0; y--) {
/* 516 */       if (interpreter.isSpawnable(y)) {
/* 517 */         float threshold = generateInterpolatedThreshold(seed, x, z, y, biomeCounts);
/* 518 */         if (threshold > heightNoise || threshold == 1.0D)
/* 519 */           return y; 
/*     */       } 
/*     */     } 
/* 522 */     return 0;
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
/*     */   @Nullable
/*     */   public Cave generateCave(@Nonnull CaveType caveType, int seed, int x, int z) {
/* 538 */     ZoneBiomeResult zoneBiomeResult = getZoneBiomeResultAt(seed, x, z);
/* 539 */     CaveGenerator caveGenerator = zoneBiomeResult.zoneResult.getZone().caveGenerator();
/* 540 */     if (caveGenerator == null) return null; 
/* 541 */     int height = getHeight(seed, x, z);
/* 542 */     return caveGenerator.generate(seed, this, caveType, x, height, z);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UniquePrefabContainer.UniquePrefabEntry[] generateUniquePrefabs(int seed) {
/* 547 */     ZonePatternGenerator zonePatternGenerator = getZonePatternGenerator(seed);
/* 548 */     ArrayList<UniquePrefabContainer.UniquePrefabEntry> entries = new ArrayList<>();
/* 549 */     BitSet visited = new BitSet((zonePatternGenerator.getZones()).length);
/*     */     
/* 551 */     for (Zone.Unique uniqueZone : zonePatternGenerator.getUniqueZones()) {
/* 552 */       Vector2i position = uniqueZone.getPosition();
/* 553 */       UniquePrefabContainer.UniquePrefabEntry[] zoneEntries = uniqueZone.zone().uniquePrefabContainer().generate(seed, position, this);
/* 554 */       entries.addAll(Arrays.asList(zoneEntries));
/* 555 */       visited.set(uniqueZone.zone().id());
/*     */     } 
/*     */     
/* 558 */     for (Zone zone : zonePatternGenerator.getZones()) {
/* 559 */       if (!visited.get(zone.id())) {
/*     */         
/* 561 */         UniquePrefabContainer.UniquePrefabEntry[] zoneEntries = zone.uniquePrefabContainer().generate(seed, (Vector2i)null, this);
/* 562 */         entries.addAll(Arrays.asList(zoneEntries));
/*     */       } 
/*     */     } 
/* 565 */     return (UniquePrefabContainer.UniquePrefabEntry[])entries.toArray(x$0 -> new UniquePrefabContainer.UniquePrefabEntry[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onExecutorShutdown() {
/* 573 */     this.prefabLoadingCache.clear();
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
/*     */   
/*     */   public static ChunkGeneratorResource getResource() {
/* 590 */     return THREAD_LOCAL.get();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() {
/* 596 */     return !ValidationUtil.isInvalid(this.zonePatternProvider, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MetricResults toMetricResults() {
/* 602 */     return WorldGenTimingsCollector.METRICS_REGISTRY.toMetricResults(this.timings);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString(boolean timings, boolean zonePatternGenerator) {
/* 608 */     return "ChunkGenerator{timings=" + String.valueOf(timings ? this.timings : "-hidden-") + ", zonePatternProvider=" + 
/* 609 */       String.valueOf(zonePatternGenerator ? this.zonePatternProvider : "-hidden-") + ", generatorCache=" + String.valueOf(this.generatorCache) + ", caveGeneratorCache=" + String.valueOf(this.caveGeneratorCache) + ", uniquePrefabCache=" + String.valueOf(this.uniquePrefabCache) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 619 */     return toString(true, true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\ChunkGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */