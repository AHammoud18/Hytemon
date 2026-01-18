/*     */ package com.hypixel.hytale.server.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.cache.CoordinateCache;
/*     */ import com.hypixel.hytale.server.worldgen.cache.ExtendedCoordinateCache;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.BlockPriorityChunk;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.populator.PrefabPopulator;
/*     */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ChunkGeneratorResource {
/*     */   @Nonnull
/*     */   public final Random random;
/*     */   @Nonnull
/*     */   public final Random random2;
/*     */   @Nonnull
/*     */   public final IntList coverArray;
/*     */   public final TimeoutCache<WorldGenPrefabSupplier, IPrefabBuffer> prefabs;
/*     */   @Nonnull
/*     */   public final BlockPriorityChunk priorityChunk;
/*     */   @Nonnull
/*     */   public final CoordinateCache.CoordinateKey cacheCoordinateKey;
/*     */   @Nonnull
/*     */   public final ExtendedCoordinateCache.ExtendedCoordinateKey<CaveType> cacheCaveCoordinateKey;
/*     */   @Nonnull
/*     */   public final ResultBuffer.Bounds2d bounds2d;
/*     */   
/*  35 */   public ChunkGeneratorResource() { this.prefabs = new TimeoutCache(30L, TimeUnit.SECONDS, this::getPrefab, (key, value) -> value.release());
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
/*  58 */     this.climateBuffer = new ClimateNoise.Buffer();
/*     */     
/*  60 */     this.prefabPopulator = new PrefabPopulator();
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
/*  71 */     this.random = (Random)new FastRandom(0L);
/*  72 */     this.random2 = (Random)new FastRandom(0L);
/*  73 */     this.priorityChunk = new BlockPriorityChunk();
/*  74 */     this.coverArray = (IntList)new IntArrayList(5);
/*     */     
/*  76 */     this.cacheVector2d = new Vector2d();
/*  77 */     this.cacheCoordinateKey = new CoordinateCache.CoordinateKey();
/*  78 */     this.cacheCaveCoordinateKey = new ExtendedCoordinateCache.ExtendedCoordinateKey();
/*     */     
/*  80 */     this.bounds2d = new ResultBuffer.Bounds2d();
/*  81 */     this.resultBuffer2d = new ResultBuffer.ResultBuffer2d();
/*  82 */     this.resultBuffer3d = new ResultBuffer.ResultBuffer3d();
/*     */     
/*  84 */     this.prefabBuffer = new PrefabPasteUtil.PrefabPasteBuffer();
/*     */     
/*  86 */     this.zoneBiomeResult = new ZoneBiomeResult();
/*  87 */     this.zoneBiomeResult.setZoneResult(new ZoneGeneratorResult()); } @Nonnull public final ResultBuffer.ResultBuffer2d resultBuffer2d; @Nonnull
/*     */   public final ResultBuffer.ResultBuffer3d resultBuffer3d; @Nonnull
/*     */   public final PrefabPasteUtil.PrefabPasteBuffer prefabBuffer; @Nonnull
/*     */   public final ZoneBiomeResult zoneBiomeResult; public final ClimateNoise.Buffer climateBuffer; public final PrefabPopulator prefabPopulator; @Nonnull
/*  91 */   public final Vector2d cacheVector2d; protected ChunkGenerator chunkGenerator; public void init(ChunkGenerator chunkGenerator) { this.chunkGenerator = chunkGenerator; }
/*     */ 
/*     */   
/*     */   public void release() {
/*  95 */     this.prefabs.shutdown();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Random getRandom() {
/* 100 */     return this.random;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IPrefabBuffer getPrefab(WorldGenPrefabSupplier prefabSupplier) {
/* 105 */     return this.chunkGenerator.getPrefabLoadingCache().getPrefabAccessor(prefabSupplier);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\ChunkGeneratorResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */