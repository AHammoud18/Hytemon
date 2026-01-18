/*     */ package com.hypixel.hytale.builtin.hytalegenerator.plugin;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.PropField;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.AssetManager;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.SettingsAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.WorldStructureAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biomemap.BiomeMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkRequest;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.FallbackGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.NStagedChunkGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NBiomeDistanceStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NBiomeStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NEnvironmentStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NPropStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NTerrainStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NTintStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HytaleGenerator extends JavaPlugin {
/*     */   private AssetManager assetManager;
/*  46 */   private final Map<ChunkRequest.GeneratorProfile, ChunkGenerator> generators = new HashMap<>(); private Runnable assetReloadListener;
/*  47 */   private final Semaphore chunkGenerationSemaphore = new Semaphore(1);
/*     */   
/*     */   private int concurrency;
/*     */   
/*     */   private ExecutorService mainExecutor;
/*     */   private ThreadPoolExecutor concurrentExecutor;
/*     */   
/*     */   protected void start() {
/*  55 */     super.start();
/*     */     
/*  57 */     if (this.mainExecutor == null) {
/*  58 */       loadExecutors(this.assetManager.getSettingsAsset());
/*     */     }
/*  60 */     if (this.assetReloadListener == null) {
/*  61 */       this.assetReloadListener = (() -> reloadGenerators());
/*     */ 
/*     */       
/*  64 */       this.assetManager.registerReloadListener(this.assetReloadListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<GeneratedChunk> submitChunkRequest(@Nonnull ChunkRequest request) {
/*  70 */     return CompletableFuture.supplyAsync(() -> { try { this.chunkGenerationSemaphore.acquireUninterruptibly(); ChunkGenerator generator = getGenerator(request.generatorProfile()); return generator.generate(request.arguments()); } finally { this.chunkGenerationSemaphore.release(); }  }this.mainExecutor)
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
/*  83 */       .handle((r, e) -> {
/*     */           if (e == null) {
/*     */             return r;
/*     */           }
/*     */           LoggerUtil.logException("generation of the chunk with request " + String.valueOf(request), e, LoggerUtil.getLogger());
/*     */           return FallbackGenerator.INSTANCE.generate(request.arguments());
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  94 */     this.assetManager = new AssetManager(getEventRegistry(), getLogger());
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
/* 111 */     BuilderCodec<HandleProvider> generatorProvider = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HandleProvider.class, () -> new HandleProvider(this)).documentation("The standard generator for Hytale.")).append(new KeyedCodec("WorldStructure", (Codec)Codec.STRING), HandleProvider::setWorldStructureName, HandleProvider::getWorldStructureName).documentation("The world structure to be used for this world.").add()).append(new KeyedCodec("PlayerSpawn", (Codec)Transform.CODEC), HandleProvider::setPlayerSpawn, HandleProvider::getPlayerSpawn).add()).build();
/* 112 */     IWorldGenProvider.CODEC.register("HytaleGenerator", HandleProvider.class, (Codec)generatorProvider);
/*     */ 
/*     */     
/* 115 */     getCommandRegistry().registerCommand((AbstractCommand)new ViewportCommand(this.assetManager));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public NStagedChunkGenerator createStagedChunkGenerator(@Nonnull ChunkRequest.GeneratorProfile generatorProfile, @Nonnull WorldStructureAsset worldStructureAsset, @Nonnull SettingsAsset settingsAsset) {
/* 124 */     WorkerIndexer workerIndexer = new WorkerIndexer(this.concurrency);
/* 125 */     SeedBox seed = new SeedBox(generatorProfile.seed());
/* 126 */     MaterialCache materialCache = new MaterialCache();
/* 127 */     BiomeMap<SolidMaterial> biomeMap = worldStructureAsset.buildBiomeMap(new WorldStructureAsset.Argument(materialCache, seed, workerIndexer));
/* 128 */     worldStructureAsset.cleanUp();
/* 129 */     NStagedChunkGenerator.Builder generatorBuilder = new NStagedChunkGenerator.Builder();
/*     */     
/* 131 */     List<BiomeType> allBiomes = biomeMap.allPossibleValues();
/* 132 */     List<Integer> allRuntimes = new ArrayList<>(getAllPossibleRuntimeIndices(allBiomes));
/* 133 */     allRuntimes.sort(Comparator.naturalOrder());
/*     */     
/* 135 */     int bufferTypeIndexCounter = 0;
/*     */ 
/*     */ 
/*     */     
/* 139 */     NParametrizedBufferType biome_bufferType = new NParametrizedBufferType("Biome", bufferTypeIndexCounter++, NBiomeStage.bufferClass, NBiomeStage.biomeTypeClass, () -> new NCountedPixelBuffer(NBiomeStage.biomeTypeClass));
/*     */     
/* 141 */     NBiomeStage nBiomeStage = new NBiomeStage("BiomeStage", biome_bufferType, (BiCarta)biomeMap);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     generatorBuilder.appendStage((NStage)nBiomeStage);
/*     */ 
/*     */ 
/*     */     
/* 150 */     NParametrizedBufferType biomeDistance_bufferType = new NParametrizedBufferType("BiomeDistance", bufferTypeIndexCounter++, NBiomeDistanceStage.biomeDistanceBufferClass, NBiomeDistanceStage.biomeDistanceClass, () -> new NSimplePixelBuffer(NBiomeDistanceStage.biomeDistanceClass));
/*     */     
/* 152 */     int MAX_BIOME_DISTANCE_RADIUS = 512;
/* 153 */     int interpolationRadius = Math.clamp((worldStructureAsset.getBiomeTransitionDistance() / 2), 0, 512);
/* 154 */     int biomeEdgeRadius = Math.clamp(worldStructureAsset.getMaxBiomeEdgeDistance(), 0, 512);
/* 155 */     int maxDistance = Math.max(interpolationRadius, biomeEdgeRadius);
/* 156 */     NBiomeDistanceStage nBiomeDistanceStage = new NBiomeDistanceStage("BiomeDistanceStage", biome_bufferType, biomeDistance_bufferType, maxDistance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     generatorBuilder.appendStage((NStage)nBiomeDistanceStage);
/*     */ 
/*     */ 
/*     */     
/* 166 */     int materialBufferIndexCounter = 0;
/* 167 */     NParametrizedBufferType material0_bufferType = generatorBuilder.MATERIAL_OUTPUT_BUFFER_TYPE;
/* 168 */     if (!allRuntimes.isEmpty()) {
/* 169 */       material0_bufferType = new NParametrizedBufferType("Material" + materialBufferIndexCounter, bufferTypeIndexCounter++, NTerrainStage.materialBufferClass, NTerrainStage.materialClass, () -> new NVoxelBuffer(NTerrainStage.materialClass));
/*     */       
/* 171 */       materialBufferIndexCounter++;
/*     */     } 
/*     */     
/* 174 */     NTerrainStage nTerrainStage = new NTerrainStage("TerrainStage", biome_bufferType, biomeDistance_bufferType, material0_bufferType, interpolationRadius, materialCache, workerIndexer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     generatorBuilder.appendStage((NStage)nTerrainStage);
/*     */ 
/*     */ 
/*     */     
/* 187 */     NParametrizedBufferType materialInput_bufferType = material0_bufferType;
/* 188 */     NBufferType entityInput_bufferType = null;
/*     */     
/* 190 */     for (int i = 0; i < allRuntimes.size() - 1; i++) {
/* 191 */       int runtime = ((Integer)allRuntimes.get(i)).intValue();
/* 192 */       String runtimeString = Integer.toString(runtime);
/*     */       
/* 194 */       NParametrizedBufferType materialOutput_bufferType = new NParametrizedBufferType("Material" + materialBufferIndexCounter, bufferTypeIndexCounter++, NTerrainStage.materialBufferClass, NTerrainStage.materialClass, () -> new NVoxelBuffer(NTerrainStage.materialClass));
/* 195 */       NBufferType entityOutput_bufferType = new NBufferType("Entity" + materialBufferIndexCounter, bufferTypeIndexCounter++, NEntityBuffer.class, NEntityBuffer::new);
/*     */       
/* 197 */       NPropStage nPropStage = new NPropStage("PropStage" + runtimeString, biome_bufferType, biomeDistance_bufferType, materialInput_bufferType, entityInput_bufferType, materialOutput_bufferType, entityOutput_bufferType, materialCache, allBiomes, runtime);
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
/* 210 */       generatorBuilder.appendStage((NStage)nPropStage);
/*     */ 
/*     */       
/* 213 */       materialInput_bufferType = materialOutput_bufferType;
/* 214 */       entityInput_bufferType = entityOutput_bufferType;
/* 215 */       materialBufferIndexCounter++;
/*     */     } 
/*     */ 
/*     */     
/* 219 */     if (!allRuntimes.isEmpty()) {
/* 220 */       int runtime = ((Integer)allRuntimes.getLast()).intValue();
/* 221 */       String runtimeString = Integer.toString(runtime);
/*     */       
/* 223 */       NPropStage nPropStage = new NPropStage("PropStage" + runtimeString, biome_bufferType, biomeDistance_bufferType, materialInput_bufferType, entityInput_bufferType, generatorBuilder.MATERIAL_OUTPUT_BUFFER_TYPE, generatorBuilder.ENTITY_OUTPUT_BUFFER_TYPE, materialCache, allBiomes, runtime);
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
/* 236 */       generatorBuilder.appendStage((NStage)nPropStage);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 241 */     NTintStage nTintStage = new NTintStage("TintStage", biome_bufferType, generatorBuilder.TINT_OUTPUT_BUFFER_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     generatorBuilder.appendStage((NStage)nTintStage);
/*     */ 
/*     */ 
/*     */     
/* 250 */     NEnvironmentStage nEnvironmentStage = new NEnvironmentStage("EnvironmentStage", biome_bufferType, generatorBuilder.ENVIRONMENT_OUTPUT_BUFFER_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     generatorBuilder.appendStage((NStage)nEnvironmentStage);
/*     */     
/* 257 */     double bufferCapacityFactor = Math.max(0.0D, settingsAsset.getBufferCapacityFactor());
/* 258 */     double targetViewDistance = Math.max(0.0D, settingsAsset.getTargetViewDistance());
/* 259 */     double targetPlayerCount = Math.max(0.0D, settingsAsset.getTargetPlayerCount());
/* 260 */     Set<Integer> statsCheckpoints = new HashSet<>(settingsAsset.getStatsCheckpoints());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     NStagedChunkGenerator generator = generatorBuilder.withStats("WorldStructure Name: " + generatorProfile.worldStructureName(), statsCheckpoints).withMaterialCache(materialCache).withConcurrentExecutor(this.concurrentExecutor, workerIndexer).withBufferCapacity(bufferCapacityFactor, targetViewDistance, targetPlayerCount).build();
/*     */     
/* 269 */     return generator;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static Set<Integer> getAllPossibleRuntimeIndices(@Nonnull List<BiomeType> biomes) {
/* 274 */     Set<Integer> allRuntimes = new HashSet<>();
/* 275 */     for (BiomeType biome : biomes) {
/* 276 */       for (PropField propField : biome.getPropFields()) {
/* 277 */         allRuntimes.add(Integer.valueOf(propField.getRuntime()));
/*     */       }
/*     */     } 
/* 280 */     return allRuntimes;
/*     */   }
/*     */   @Nonnull
/*     */   private ChunkGenerator getGenerator(@Nonnull ChunkRequest.GeneratorProfile profile) {
/*     */     NStagedChunkGenerator nStagedChunkGenerator;
/* 285 */     ChunkGenerator generator = this.generators.get(profile);
/*     */     
/* 287 */     if (generator == null) {
/* 288 */       if (profile.worldStructureName() == null) {
/* 289 */         LoggerUtil.getLogger().warning("World Structure asset not loaded.");
/* 290 */         return (ChunkGenerator)FallbackGenerator.INSTANCE;
/*     */       } 
/*     */       
/* 293 */       WorldStructureAsset worldStructureAsset = this.assetManager.getWorldStructureAsset(profile.worldStructureName());
/* 294 */       if (worldStructureAsset == null) {
/* 295 */         LoggerUtil.getLogger().warning("World Structure asset not found: " + profile.worldStructureName());
/* 296 */         return (ChunkGenerator)FallbackGenerator.INSTANCE;
/*     */       } 
/*     */       
/* 299 */       SettingsAsset settingsAsset = this.assetManager.getSettingsAsset();
/* 300 */       if (settingsAsset == null) {
/* 301 */         LoggerUtil.getLogger().warning("Settings asset not found.");
/* 302 */         return (ChunkGenerator)FallbackGenerator.INSTANCE;
/*     */       } 
/*     */       
/* 305 */       nStagedChunkGenerator = createStagedChunkGenerator(profile, worldStructureAsset, settingsAsset);
/* 306 */       this.generators.put(profile, nStagedChunkGenerator);
/*     */     } 
/*     */     
/* 309 */     return (ChunkGenerator)nStagedChunkGenerator;
/*     */   }
/*     */   
/*     */   private void loadExecutors(@Nonnull SettingsAsset settingsAsset) {
/* 313 */     int newConcurrency = getConcurrency(settingsAsset);
/*     */     
/* 315 */     if (newConcurrency == this.concurrency && this.mainExecutor != null && this.concurrentExecutor != null)
/*     */       return; 
/* 317 */     this.concurrency = newConcurrency;
/*     */     
/* 319 */     if (this.mainExecutor == null) {
/* 320 */       this.mainExecutor = Executors.newSingleThreadExecutor();
/*     */     }
/*     */     
/* 323 */     if (this.concurrentExecutor != null && !this.concurrentExecutor.isShutdown()) {
/*     */       try {
/* 325 */         this.concurrentExecutor.shutdown();
/* 326 */         if (!this.concurrentExecutor.awaitTermination(1L, TimeUnit.MINUTES));
/*     */ 
/*     */       
/*     */       }
/* 330 */       catch (InterruptedException e) {
/* 331 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */     
/* 335 */     this.concurrentExecutor = new ThreadPoolExecutor(this.concurrency, this.concurrency, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
/*     */           Thread t = new Thread(r, "HytaleGenerator-Worker");
/*     */           
/*     */           t.setPriority(1);
/*     */           t.setDaemon(true);
/*     */           return t;
/*     */         });
/* 342 */     if (this.mainExecutor == null || this.mainExecutor.isShutdown())
/*     */     {
/* 344 */       this.mainExecutor = Executors.newSingleThreadExecutor();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getConcurrency(@Nonnull SettingsAsset settingsAsset) {
/* 349 */     int concurrencySetting = settingsAsset.getCustomConcurrency();
/* 350 */     int availableProcessors = Runtime.getRuntime().availableProcessors();
/* 351 */     int value = 1;
/*     */     
/* 353 */     if (concurrencySetting < 1) {
/* 354 */       value = Math.max(availableProcessors, 1);
/*     */     } else {
/* 356 */       if (concurrencySetting > availableProcessors) {
/* 357 */         LoggerUtil.getLogger().warning("Concurrency setting " + concurrencySetting + " exceeds available processors " + availableProcessors);
/*     */       }
/* 359 */       value = concurrencySetting;
/*     */     } 
/*     */     
/* 362 */     return value;
/*     */   }
/*     */   
/*     */   private void reloadGenerators() {
/*     */     try {
/* 367 */       this.chunkGenerationSemaphore.acquireUninterruptibly();
/*     */ 
/*     */       
/* 370 */       loadExecutors(this.assetManager.getSettingsAsset());
/* 371 */       this.generators.clear();
/*     */     } finally {
/*     */       
/* 374 */       this.chunkGenerationSemaphore.release();
/*     */     } 
/*     */     
/* 377 */     LoggerUtil.getLogger().info("Reloaded HytaleGenerator.");
/*     */   }
/*     */   
/*     */   public HytaleGenerator(@Nonnull JavaPluginInit init) {
/* 381 */     super(init);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\plugin\HytaleGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */