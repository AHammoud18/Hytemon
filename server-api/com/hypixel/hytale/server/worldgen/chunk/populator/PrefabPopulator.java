/*     */ package com.hypixel.hytale.server.worldgen.chunk.populator;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.biome.BiomePatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.biome.CustomBiome;
/*     */ import com.hypixel.hytale.server.worldgen.biome.TileBiome;
/*     */ import com.hypixel.hytale.server.worldgen.cache.CoreDataCacheEntry;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.PrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPasteUtil;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternGenerator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PrefabPopulator {
/*  42 */   private static final UniquePrefabContainer.UniquePrefabEntry[] EMPTY_UNIQUE_PREFABS = new UniquePrefabContainer.UniquePrefabEntry[0]; private static final int BIOME_SAMPLE_STEP_SIZE = 8; private int worldSeed; private long prefabSeed;
/*     */   
/*     */   public static void populate(int seed, @Nonnull ChunkGeneratorExecution execution) {
/*  45 */     (ChunkGenerator.getResource()).prefabPopulator.run(seed, execution);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private int minPriority = Integer.MAX_VALUE;
/*     */   @Nullable
/*     */   private Biome biome;
/*     */   @Nullable
/*     */   private PrefabContainer.PrefabContainerEntry entry;
/*     */   @Nullable
/*     */   private ChunkGeneratorExecution execution;
/*     */   @Nonnull
/*  60 */   private UniquePrefabContainer.UniquePrefabEntry[] uniquePrefabs = EMPTY_UNIQUE_PREFABS;
/*     */ 
/*     */   
/*  63 */   private final FastRandom random = new FastRandom(0L);
/*  64 */   private final ObjectArrayList<Biome> biomes = new ObjectArrayList();
/*  65 */   private final ObjectArrayList<Candidate> prefabs = new ObjectArrayList();
/*  66 */   private final BitSet conflicts = new BitSet();
/*     */   
/*     */   public void run(int seed, @Nonnull ChunkGeneratorExecution execution) {
/*  69 */     this.worldSeed = seed;
/*  70 */     this.minPriority = Integer.MAX_VALUE;
/*  71 */     this.uniquePrefabs = Objects.<UniquePrefabContainer.UniquePrefabEntry[]>requireNonNullElse(execution.getChunkGenerator().getUniquePrefabs(seed), EMPTY_UNIQUE_PREFABS);
/*     */     
/*  73 */     collectBiomes(seed, execution);
/*  74 */     collectPrefabs(seed, execution);
/*  75 */     collectConflicts();
/*  76 */     generatePrefabs(seed, execution);
/*  77 */     generateUniquePrefabs(seed, execution);
/*     */     
/*  79 */     this.biome = null;
/*  80 */     this.entry = null;
/*  81 */     this.execution = null;
/*  82 */     this.uniquePrefabs = EMPTY_UNIQUE_PREFABS;
/*  83 */     this.biomes.clear();
/*  84 */     this.prefabs.clear();
/*  85 */     this.conflicts.clear();
/*     */   }
/*     */   
/*     */   private void collectBiomes(int seed, ChunkGeneratorExecution execution) {
/*  89 */     for (CoreDataCacheEntry entry : execution.getCoreDataEntries()) {
/*  90 */       Biome biome = entry.zoneBiomeResult.getBiome();
/*  91 */       if (biome.getPrefabContainer() != null) {
/*  92 */         collectBiome(biome);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  97 */     int chunkX = execution.getX();
/*  98 */     int chunkZ = execution.getZ();
/*  99 */     int chunkMinX = ChunkUtil.minBlock(chunkX) - 5;
/* 100 */     int chunkMinZ = ChunkUtil.minBlock(chunkZ) - 5;
/* 101 */     int chunkMaxX = ChunkUtil.maxBlock(chunkX) + 5;
/* 102 */     int chunkMaxZ = ChunkUtil.maxBlock(chunkZ) + 5;
/*     */ 
/*     */     
/* 105 */     int extents = execution.getChunkGenerator().getZonePatternProvider().getMaxExtent();
/* 106 */     int regionMinX = chunkMinX - extents;
/* 107 */     int regionMinZ = chunkMinZ - extents;
/* 108 */     int regionMaxX = chunkMaxX + extents;
/* 109 */     int regionMaxZ = chunkMaxZ + extents;
/*     */     
/* 111 */     ZoneGeneratorResult zoneResult = (ChunkGenerator.getResource()).zoneBiomeResult.zoneResult;
/* 112 */     ZonePatternGenerator zoneGenerator = execution.getChunkGenerator().getZonePatternGenerator(seed);
/*     */     
/* 114 */     for (int z = regionMinZ; z <= regionMaxZ; z += 8) {
/* 115 */       for (int x = regionMinX; x <= regionMaxX; x += 8) {
/*     */         
/* 117 */         if (x < chunkMinX || z < chunkMinZ || x > chunkMaxX || z > chunkMaxZ) {
/*     */ 
/*     */ 
/*     */           
/* 121 */           Zone zone = zoneGenerator.generate(seed, x, z, zoneResult).getZone();
/* 122 */           BiomePatternGenerator biomeGenerator = zone.biomePatternGenerator();
/*     */ 
/*     */           
/* 125 */           int minX = chunkMinX - biomeGenerator.getExtents();
/* 126 */           int minZ = chunkMinZ - biomeGenerator.getExtents();
/* 127 */           int maxX = chunkMaxX + biomeGenerator.getExtents();
/* 128 */           int maxZ = chunkMaxZ + biomeGenerator.getExtents();
/* 129 */           if (x >= minX && z >= minZ && x <= maxX && z <= maxZ) {
/*     */ 
/*     */ 
/*     */             
/* 133 */             TileBiome biome = biomeGenerator.getBiome(seed, x, z);
/* 134 */             if (biome != null) {
/*     */ 
/*     */ 
/*     */               
/* 138 */               if (biome.getPrefabContainer() != null) {
/* 139 */                 collectBiome((Biome)biome);
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 144 */               for (CustomBiome customBiome : biomeGenerator.getCustomBiomes()) {
/* 145 */                 if (customBiome.getPrefabContainer() != null && customBiome
/* 146 */                   .getCustomBiomeGenerator().isValidParentBiome(biome.getId()))
/* 147 */                   collectBiome((Biome)customBiome); 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void collectPrefabs(int seed, ChunkGeneratorExecution execution) {
/* 155 */     for (int i = 0; i < this.biomes.size(); i++) {
/* 156 */       Biome biome = (Biome)this.biomes.get(i);
/* 157 */       PrefabContainer container = biome.getPrefabContainer();
/*     */       
/* 159 */       if (container != null) {
/*     */ 
/*     */ 
/*     */         
/* 163 */         int id = 0;
/* 164 */         for (PrefabContainer.PrefabContainerEntry entry : container.getEntries()) {
/* 165 */           this.biome = biome;
/* 166 */           this.entry = entry;
/* 167 */           this.execution = execution;
/* 168 */           this.prefabSeed = HashUtil.hash(seed, biome.getId(), ++id);
/*     */           
/* 170 */           entry.getPrefabPatternGenerator().getGridGenerator().collect(seed, (
/* 171 */               ChunkUtil.minBlock(execution.getX()) - entry.getExtents()), (
/* 172 */               ChunkUtil.minBlock(execution.getZ()) - entry.getExtents()), (
/* 173 */               ChunkUtil.maxBlock(execution.getX()) + entry.getExtents()), (
/* 174 */               ChunkUtil.maxBlock(execution.getZ()) + entry.getExtents()), this::collectPrefab);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generatePrefabs(int seed, @Nonnull ChunkGeneratorExecution execution) {
/* 181 */     for (int i = 0; i < this.prefabs.size(); i++) {
/* 182 */       if (!this.conflicts.get(i)) {
/*     */ 
/*     */ 
/*     */         
/* 186 */         Candidate prefab = (Candidate)this.prefabs.get(i);
/* 187 */         int x = prefab.x;
/* 188 */         int y = prefab.y;
/* 189 */         int z = prefab.z;
/* 190 */         PrefabRotation rotation = prefab.rotation;
/* 191 */         WorldGenPrefabSupplier supplier = prefab.supplier;
/*     */         
/* 193 */         if (isMatchingChunkBounds(x, z, execution, rotation, supplier.getBounds(prefab.buffer))) {
/*     */ 
/*     */ 
/*     */           
/* 197 */           BlockMaskCondition config = prefab.generator.getPrefabPlacementConfiguration();
/* 198 */           ICoordinateRndCondition heightCondition = prefab.generator.getHeightCondition();
/* 199 */           int environment = prefab.entry.getEnvironmentId();
/* 200 */           boolean fitHeightmap = prefab.generator.isFitHeightmap();
/* 201 */           boolean submerge = prefab.generator.isSubmerge();
/*     */           
/* 203 */           generatePrefabAt(seed, x, z, y, execution, supplier, config, rotation, heightCondition, environment, fitHeightmap, submerge);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void generateUniquePrefabs(int seed, @Nonnull ChunkGeneratorExecution execution) {
/* 208 */     for (UniquePrefabContainer.UniquePrefabEntry entry : this.uniquePrefabs) {
/* 209 */       if (isMatchingChunkBounds(execution, entry.getLowBoundX(), entry.getLowBoundZ(), entry.getHighBoundX(), entry.getHighBoundZ())) {
/*     */ 
/*     */         
/* 212 */         Vector3i v = entry.getPosition();
/* 213 */         generatePrefabAt(seed, v
/* 214 */             .getX(), v.getZ(), v.getY(), execution, entry
/*     */             
/* 216 */             .getPrefabSupplier(), entry
/* 217 */             .getConfiguration(), entry
/* 218 */             .getRotation(), (ICoordinateRndCondition)DefaultCoordinateRndCondition.DEFAULT_TRUE, entry
/*     */             
/* 220 */             .getEnvironmentId(), entry
/* 221 */             .isFitHeightmap(), entry
/* 222 */             .isSubmerge());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void collectBiome(@Nonnull Biome biome) {
/* 228 */     Biome insert = biome;
/*     */     
/* 230 */     for (int i = 0; i < this.biomes.size(); i++) {
/* 231 */       int id = ((Biome)this.biomes.get(i)).getId();
/*     */ 
/*     */       
/* 234 */       if (insert.getId() == id) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (insert.getId() < id) {
/* 241 */         insert = (Biome)this.biomes.set(i, insert);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 246 */     this.biomes.add(insert);
/*     */   }
/*     */   
/*     */   private void collectPrefab(double px, double pz) {
/* 250 */     Objects.requireNonNull(this.biome);
/* 251 */     Objects.requireNonNull(this.entry);
/* 252 */     Objects.requireNonNull(this.execution);
/*     */     
/* 254 */     int x = (int)MathUtil.fastFloor(px);
/* 255 */     int z = (int)MathUtil.fastFloor(pz);
/*     */     
/* 257 */     this.random.setSeed(HashUtil.hash(this.prefabSeed, x, z) * 1609272495L);
/*     */     
/* 259 */     PrefabPatternGenerator patternGenerator = this.entry.getPrefabPatternGenerator();
/* 260 */     if (!isMatchingNoiseDensity(this.worldSeed, x, z, patternGenerator)) {
/*     */       return;
/*     */     }
/* 263 */     if (isWithinUniquePrefabExclusionRange(x, z, patternGenerator, this.uniquePrefabs)) {
/*     */       return;
/*     */     }
/* 266 */     ZoneBiomeResult result = this.execution.getChunkGenerator().getZoneBiomeResultAt(this.worldSeed, x, z);
/* 267 */     if (!isMatchingBiome(this.biome, result))
/*     */       return; 
/* 269 */     IWeightedMap<WorldGenPrefabSupplier> prefabs = this.entry.getPrefabs();
/* 270 */     WorldGenPrefabSupplier supplier = (WorldGenPrefabSupplier)prefabs.get((Random)this.random);
/* 271 */     if (supplier == null)
/*     */       return; 
/* 273 */     IPrefabBuffer prefab = supplier.get();
/* 274 */     if (prefab == null)
/*     */       return; 
/* 276 */     PrefabRotation rotation = generateRotation(x, z, (Random)this.random, patternGenerator);
/*     */ 
/*     */     
/* 279 */     int y = getHeight(this.worldSeed, x, z, this.execution, result.getBiome(), patternGenerator, (Random)this.random);
/*     */     
/* 281 */     if (!isMatchingHeight(this.worldSeed, x, z, y, (Random)this.random, patternGenerator)) {
/*     */       return;
/*     */     }
/* 284 */     if (!isMatchingParentBlock(this.worldSeed, x, z, y, (Random)this.random, result, this.entry))
/*     */       return; 
/* 286 */     PrefabCategory category = patternGenerator.getCategory();
/*     */     
/* 288 */     this.prefabs.add(new Candidate(x, y, z, category.priority(), rotation, prefab, supplier, this.entry, patternGenerator));
/* 289 */     this.minPriority = Math.min(this.minPriority, category.priority());
/*     */   }
/*     */   
/*     */   private void collectConflicts() {
/* 293 */     for (int i = 0; i < this.prefabs.size(); i++) {
/* 294 */       Candidate candidate = (Candidate)this.prefabs.get(i);
/* 295 */       int minY = candidate.y + candidate.buffer.getMinY();
/* 296 */       int maxY = candidate.y + candidate.buffer.getMaxY();
/* 297 */       int minX = candidate.x + candidate.buffer.getMinX(candidate.rotation);
/* 298 */       int minZ = candidate.z + candidate.buffer.getMinZ(candidate.rotation);
/* 299 */       int maxX = candidate.x + candidate.buffer.getMaxX(candidate.rotation);
/* 300 */       int maxZ = candidate.z + candidate.buffer.getMaxZ(candidate.rotation);
/*     */ 
/*     */       
/* 303 */       if (candidate.priority > this.minPriority && !this.conflicts.get(i))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 308 */         for (int j = 0; j < this.prefabs.size(); j++) {
/* 309 */           Candidate other = (Candidate)this.prefabs.get(j);
/*     */           
/* 311 */           if (candidate.priority > other.priority)
/*     */           {
/*     */ 
/*     */             
/* 315 */             if (intersects(minX, minY, minZ, maxX, maxY, maxZ, other.x + other.buffer
/*     */ 
/*     */ 
/*     */                 
/* 319 */                 .getMinX(other.rotation), other.y + other.buffer
/* 320 */                 .getMinY(), other.z + other.buffer
/* 321 */                 .getMinZ(other.rotation), other.x + other.buffer
/*     */                 
/* 323 */                 .getMaxX(other.rotation), other.y + other.buffer
/* 324 */                 .getMaxY(), other.z + other.buffer
/* 325 */                 .getMaxZ(other.rotation)))
/*     */             {
/* 327 */               this.conflicts.set(j);
/*     */             }
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean intersects(int minX1, int minY1, int minZ1, int maxX1, int maxY1, int maxZ1, int minX2, int minY2, int minZ2, int maxX2, int maxY2, int maxZ2) {
/* 339 */     return (maxX1 >= minX2 && minX1 <= maxX2 && maxY1 >= minY2 && minY1 <= maxY2 && maxZ1 >= minZ2 && minZ1 <= maxZ2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isWithinUniquePrefabExclusionRange(int x, int z, @Nonnull PrefabPatternGenerator generator, @Nonnull UniquePrefabContainer.UniquePrefabEntry[] uniquePrefabs) {
/* 345 */     long radius = generator.getExclusionRadius();
/*     */     
/* 347 */     if (radius <= 0L) {
/* 348 */       return false;
/*     */     }
/*     */     
/* 351 */     long radius2 = radius * radius;
/* 352 */     int priority = generator.getCategory().priority();
/*     */     
/* 354 */     for (UniquePrefabContainer.UniquePrefabEntry unique : uniquePrefabs) {
/* 355 */       if (priority < unique.getCategory().priority()) {
/*     */ 
/*     */ 
/*     */         
/* 359 */         long dx = (x - unique.getPosition().getX());
/* 360 */         long dz = (z - unique.getPosition().getZ());
/* 361 */         if (dx * dx + dz * dz <= radius2) {
/* 362 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHeight(int seed, int x, int z, @Nonnull ChunkGeneratorExecution execution, @Nonnull Biome biome, @Nonnull PrefabPatternGenerator prefabPatternGenerator, Random random) {
/*     */     int height;
/* 375 */     if (prefabPatternGenerator.isOnWater() && prefabPatternGenerator.isDeepSearch()) {
/* 376 */       height = Integer.MIN_VALUE;
/* 377 */       for (WaterContainer.Entry waterContainer : biome.getWaterContainer().getEntries()) {
/* 378 */         int max = waterContainer.getMax(seed, x, z);
/* 379 */         if (max != Integer.MIN_VALUE && prefabPatternGenerator.getHeightCondition().eval(seed, x, z, max, random)) {
/* 380 */           height = max;
/*     */           break;
/*     */         } 
/*     */       } 
/* 384 */     } else if (prefabPatternGenerator.isOnWater()) {
/* 385 */       height = biome.getWaterContainer().getMaxHeight(seed, x, z);
/* 386 */     } else if (prefabPatternGenerator.isDeepSearch()) {
/* 387 */       height = execution.getChunkGenerator().generateHeightBetween(seed, x, z, prefabPatternGenerator.getHeightThresholdInterpreter());
/*     */     } else {
/* 389 */       height = execution.getChunkGenerator().getHeight(seed, x, z);
/*     */     } 
/* 391 */     height += prefabPatternGenerator.getDisplacement(seed, x, z);
/* 392 */     return height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static PrefabRotation generateRotation(int x, int z, @Nonnull Random random, @Nonnull PrefabPatternGenerator patternGenerator) {
/* 398 */     PrefabRotation[] prefabRotations = patternGenerator.getRotations();
/* 399 */     if (prefabRotations == null) {
/* 400 */       prefabRotations = PrefabRotation.VALUES;
/*     */     }
/*     */     
/* 403 */     return prefabRotations[random.nextInt(prefabRotations.length)];
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
/*     */   private static void generatePrefabAt(int seed, int x, int z, int height, @Nonnull ChunkGeneratorExecution execution, @Nonnull WorldGenPrefabSupplier supplier, BlockMaskCondition configuration, PrefabRotation rotation, ICoordinateRndCondition heightCondition, int environmentId, boolean fitHeightmap, boolean submerge) {
/* 415 */     int cx = x - ChunkUtil.minBlock(execution.getX());
/* 416 */     int cz = z - ChunkUtil.minBlock(execution.getZ());
/* 417 */     long externalSeed = HashUtil.hash(x, z) * -1058827062L;
/*     */     
/* 419 */     PrefabPasteUtil.PrefabPasteBuffer buffer = (ChunkGenerator.getResource()).prefabBuffer;
/* 420 */     buffer.setSeed(seed, externalSeed);
/* 421 */     buffer.execution = execution;
/* 422 */     buffer.blockMask = configuration;
/* 423 */     buffer.environmentId = environmentId;
/* 424 */     buffer.fitHeightmap = fitHeightmap;
/* 425 */     buffer.priority = submerge ? 41 : 9;
/* 426 */     buffer.spawnCondition = heightCondition;
/*     */     
/* 428 */     if (execution.getChunkGenerator().getBenchmark().isEnabled() && ChunkUtil.isInsideChunkRelative(cx, cz)) {
/* 429 */       ZoneBiomeResult zb = execution.zoneBiomeResult(cx, cz);
/* 430 */       String zoneName = zb.zoneResult.getZone().name();
/* 431 */       String biomeName = zb.biome.getName();
/* 432 */       execution.getChunkGenerator().getBenchmark().registerPrefab(zoneName + "\t" + zoneName + "\t" + biomeName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 437 */     PrefabPasteUtil.generate(buffer, rotation, supplier, x, height, z, cx, cz);
/*     */   }
/*     */   
/*     */   private static boolean isMatchingBiome(Biome biome, @Nonnull ZoneBiomeResult zoneAndBiomeResult) {
/* 441 */     return (zoneAndBiomeResult.getBiome() == biome);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMatchingChunkBounds(int x, int z, @Nonnull ChunkGeneratorExecution execution, @Nonnull PrefabRotation rotation, @Nonnull IChunkBounds bounds) {
/* 448 */     int minX = x + bounds.getLowBoundX(rotation);
/* 449 */     int minZ = z + bounds.getLowBoundZ(rotation);
/* 450 */     int maxX = x + bounds.getHighBoundX(rotation);
/* 451 */     int maxZ = z + bounds.getHighBoundZ(rotation);
/* 452 */     return isMatchingChunkBounds(execution, minX, minZ, maxX, maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMatchingChunkBounds(@Nonnull ChunkGeneratorExecution execution, int lowBoundX, int lowBoundZ, int highBoundX, int highBoundZ) {
/* 458 */     return (ChunkUtil.maxBlock(execution.getX()) >= lowBoundX && 
/* 459 */       ChunkUtil.minBlock(execution.getX()) <= highBoundX && 
/* 460 */       ChunkUtil.maxBlock(execution.getZ()) >= lowBoundZ && 
/* 461 */       ChunkUtil.minBlock(execution.getZ()) <= highBoundZ);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isMatchingHeight(int seed, int x, int z, int y, Random random, @Nonnull PrefabPatternGenerator prefabPatternGenerator) {
/* 466 */     return prefabPatternGenerator.getHeightCondition().eval(seed, x, z, y, random);
/*     */   }
/*     */   
/*     */   private static boolean isMatchingNoiseDensity(int seed, int x, int z, @Nonnull PrefabPatternGenerator prefabPatternGenerator) {
/* 470 */     return prefabPatternGenerator.getMapCondition().eval(seed, x, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMatchingParentBlock(int seed, int x, int z, int y, @Nonnull Random random, @Nonnull ZoneBiomeResult zoneAndBiomeResult, @Nonnull PrefabContainer.PrefabContainerEntry containerEntry) {
/* 477 */     IBlockFluidCondition parentCondition = containerEntry.getPrefabPatternGenerator().getParentCondition();
/* 478 */     if (parentCondition == ConstantIntCondition.DEFAULT_TRUE) return true; 
/* 479 */     if (parentCondition == ConstantIntCondition.DEFAULT_FALSE) return false;
/*     */     
/* 481 */     BlockFluidEntry groundCover = getCoverInGroundAt(seed, x, z, y, random, zoneAndBiomeResult.getBiome());
/* 482 */     if (!groundCover.equals(BlockFluidEntry.EMPTY) && !parentCondition.eval(groundCover.blockId(), groundCover.fluidId()))
/*     */     {
/* 484 */       return false;
/*     */     }
/*     */     
/* 487 */     BlockFluidEntry topBlock = zoneAndBiomeResult.getBiome().getLayerContainer().getTopBlockAt(seed, x, z);
/* 488 */     return parentCondition.eval(topBlock.blockId(), topBlock.fluidId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockFluidEntry getCoverInGroundAt(int seed, int x, int z, int y, @Nonnull Random random, @Nonnull Biome biome) {
/* 494 */     for (CoverContainer.CoverContainerEntry coverContainerEntry : biome.getCoverContainer().getEntries()) {
/* 495 */       if (y < 320 && isMatchingCover(seed, x, z, y, random, coverContainerEntry)) {
/* 496 */         CoverContainer.CoverContainerEntry.CoverContainerEntryPart coverEntry = coverContainerEntry.get(random);
/* 497 */         if (coverEntry != null && coverEntry.getOffset() == -1) {
/* 498 */           return coverEntry.getEntry();
/*     */         }
/*     */       } 
/*     */     } 
/* 502 */     return BlockFluidEntry.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMatchingCover(int seed, int x, int z, int y, @Nonnull Random random, @Nonnull CoverContainer.CoverContainerEntry coverContainerEntry) {
/* 508 */     return (random.nextDouble() < coverContainerEntry.getCoverDensity() && coverContainerEntry
/* 509 */       .getMapCondition().eval(seed, x, z) && coverContainerEntry
/* 510 */       .getHeightCondition().eval(seed, x, z, y, random));
/*     */   }
/*     */   private static final class Candidate extends Record { private final int x; private final int y; private final int z; private final int priority; private final PrefabRotation rotation; private final IPrefabBuffer buffer; private final WorldGenPrefabSupplier supplier; private final PrefabContainer.PrefabContainerEntry entry; private final PrefabPatternGenerator generator;
/* 513 */     private Candidate(int x, int y, int z, int priority, PrefabRotation rotation, IPrefabBuffer buffer, WorldGenPrefabSupplier supplier, PrefabContainer.PrefabContainerEntry entry, PrefabPatternGenerator generator) { this.x = x; this.y = y; this.z = z; this.priority = priority; this.rotation = rotation; this.buffer = buffer; this.supplier = supplier; this.entry = entry; this.generator = generator; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #513	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 513 */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate; } public int x() { return this.x; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #513	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #513	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/chunk/populator/PrefabPopulator$Candidate;
/* 513 */       //   0	8	1	o	Ljava/lang/Object; } public int y() { return this.y; } public int z() { return this.z; } public int priority() { return this.priority; } public PrefabRotation rotation() { return this.rotation; } public IPrefabBuffer buffer() { return this.buffer; } public WorldGenPrefabSupplier supplier() { return this.supplier; } public PrefabContainer.PrefabContainerEntry entry() { return this.entry; } public PrefabPatternGenerator generator() { return this.generator; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\populator\PrefabPopulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */