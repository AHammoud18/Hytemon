/*     */ package com.hypixel.hytale.server.worldgen.prefab.unique;
/*     */ 
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class UniquePrefabGenerator
/*     */ {
/*     */   private static final int UNIQUE_ZONE_PLACEMENT_HEURISTIC_ITERATIONS = 8;
/*     */   protected final String name;
/*     */   protected final PrefabCategory category;
/*     */   protected final IWeightedMap<WorldGenPrefabSupplier> prefabs;
/*     */   protected final UniquePrefabConfiguration configuration;
/*     */   protected final int zoneIndex;
/*     */   
/*     */   public UniquePrefabGenerator(String name, PrefabCategory category, IWeightedMap<WorldGenPrefabSupplier> prefabs, UniquePrefabConfiguration configuration, int zoneIndex) {
/*  46 */     this.name = name;
/*  47 */     this.category = category;
/*  48 */     this.prefabs = prefabs;
/*  49 */     this.configuration = configuration;
/*  50 */     this.zoneIndex = zoneIndex;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  54 */     return this.name;
/*     */   }
/*     */   
/*     */   public PrefabCategory getCategory() {
/*  58 */     return this.category;
/*     */   }
/*     */   
/*     */   public IWeightedMap<WorldGenPrefabSupplier> getPrefabs() {
/*  62 */     return this.prefabs;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldGenPrefabSupplier generatePrefab(Random random) {
/*  67 */     return (WorldGenPrefabSupplier)this.prefabs.get(random);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i generate(int seed, @Nullable Vector2i position, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, int maxFailed, @Nonnull UniquePrefabContainer.UniquePrefabEntry[] entries) {
/*  72 */     if (position != null) {
/*  73 */       return forceUniqueZonePlacement(seed, position, chunkGenerator);
/*     */     }
/*     */ 
/*     */     
/*  77 */     int failed = 0; Vector3i vec; do {  }
/*  78 */     while ((vec = tryPlacement(seed, chunkGenerator, random, entries)) == null && 
/*  79 */       ++failed <= maxFailed);
/*     */ 
/*     */     
/*  82 */     if (vec == null) {
/*  83 */       LogUtil.getLogger().at(Level.SEVERE).log("Failed to generate Unique-Prefab '%s' with anchor '%s', maxDistance: %s", this.name, this.configuration.getAnchor(), Double.valueOf(this.configuration.getMaxDistance()));
/*  84 */       vec = forceGeneration(seed, chunkGenerator);
/*  85 */       LogUtil.getLogger().at(Level.WARNING).log("FORCED Unique-Prefab '%s' at %s after %s attempts!", this.name, vec, Integer.valueOf(failed));
/*     */     } else {
/*  87 */       LogUtil.getLogger().at(Level.FINE).log("Generated Unique-Prefab '%s' at %s after %s attempts!", this.name, vec, Integer.valueOf(failed));
/*     */     } 
/*  89 */     return vec;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Vector3i tryPlacement(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random random, @Nonnull UniquePrefabContainer.UniquePrefabEntry[] entries) {
/*  94 */     double x = this.configuration.getAnchor().getX();
/*  95 */     double z = this.configuration.getAnchor().getY();
/*     */ 
/*     */     
/*  98 */     double distance = random.nextDouble() * this.configuration.getMaxDistance();
/*  99 */     float angle = random.nextFloat() * 6.2831855F;
/* 100 */     x += TrigMathUtil.cos(angle) * distance;
/* 101 */     z += TrigMathUtil.sin(angle) * distance;
/*     */ 
/*     */     
/* 104 */     int lx = MathUtil.floor(x);
/* 105 */     int lz = MathUtil.floor(z);
/*     */     
/* 107 */     for (UniquePrefabContainer.UniquePrefabEntry entry : entries) {
/* 108 */       if (entry != null) {
/* 109 */         double dx = (entry.getPosition()).x - x;
/* 110 */         double dz = (entry.getPosition()).z - z;
/* 111 */         double d1 = dx * dx + dz * dz;
/* 112 */         if (d1 <= entry.getExclusionRadiusSquared() || d1 <= this.configuration.getExclusionRadiusSquared()) {
/* 113 */           return null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     if (!isMatchingNoiseDensity(seed, lx, lz)) return null;
/*     */     
/* 120 */     ZoneBiomeResult result = chunkGenerator.getZoneBiomeResultAt(seed, lx, lz);
/* 121 */     if (result.getZoneResult().getZone().id() != this.zoneIndex) return null; 
/* 122 */     if (!this.configuration.isValidParentBiome(result.getBiome())) return null; 
/* 123 */     if (result.zoneResult.getBorderDistance() < this.configuration.getZoneBorderExclusion()) return null;
/*     */ 
/*     */     
/* 126 */     int height = getHeight(seed, chunkGenerator, result.getBiome(), lx, lz);
/* 127 */     if (!isMatchingHeight(seed, lx, lz, random, height)) return null;
/*     */ 
/*     */     
/* 130 */     if (!isMatchingParentBlock(seed, lx, height, lz, random, result)) return null;
/*     */     
/* 132 */     return new Vector3i(lx, height, lz);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3i forceGeneration(int seed, @Nonnull ChunkGenerator chunkGenerator) {
/* 137 */     double x = this.configuration.getAnchor().getX();
/* 138 */     double z = this.configuration.getAnchor().getY();
/*     */     
/* 140 */     int lx = MathUtil.floor(x);
/* 141 */     int lz = MathUtil.floor(z);
/*     */     
/* 143 */     ZoneBiomeResult result = chunkGenerator.getZoneBiomeResultAt(seed, lx, lz);
/* 144 */     int height = getHeight(seed, chunkGenerator, result.getBiome(), lx, lz);
/* 145 */     return new Vector3i(lx, height, lz);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3i forceUniqueZonePlacement(int seed, @Nonnull Vector2i position, @Nonnull ChunkGenerator chunkGenerator) {
/* 150 */     MaskProvider maskProvider = chunkGenerator.getZonePatternProvider().getMaskProvider();
/*     */     
/* 152 */     int x = position.x;
/* 153 */     int z = position.y;
/*     */ 
/*     */ 
/*     */     
/* 157 */     for (int i = 0; i < 8; i++) {
/*     */       
/* 159 */       int px = MathUtil.floor(maskProvider.getX(seed, x, z));
/* 160 */       int pz = MathUtil.floor(maskProvider.getY(seed, x, z));
/*     */ 
/*     */       
/* 163 */       int dx = px - position.x;
/* 164 */       int dz = pz - position.y;
/*     */ 
/*     */       
/* 167 */       x -= dx / 2;
/* 168 */       z -= dz / 2;
/*     */     } 
/*     */     
/* 171 */     ZoneBiomeResult result = chunkGenerator.getZoneBiomeResultAt(seed, x, z);
/* 172 */     int height = getHeight(seed, chunkGenerator, result.getBiome(), x, z);
/*     */     
/* 174 */     return new Vector3i(x, height, z);
/*     */   }
/*     */   
/*     */   protected int getHeight(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Biome biome, int x, int z) {
/* 178 */     WaterContainer waterContainer = biome.getWaterContainer();
/* 179 */     if (waterContainer.hasEntries() && this.configuration.isOnWater()) {
/* 180 */       return waterContainer.getMaxHeight(seed, x, z);
/*     */     }
/* 182 */     return chunkGenerator.getHeight(seed, x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isMatchingHeight(int seed, int x, int z, Random random, int y) {
/* 187 */     ICoordinateRndCondition heightCondition = this.configuration.getHeightCondition();
/* 188 */     return (heightCondition == null || heightCondition.eval(seed, x, z, y, random));
/*     */   }
/*     */   
/*     */   protected boolean isMatchingNoiseDensity(int seed, int x, int z) {
/* 192 */     return this.configuration.getMapCondition().eval(seed, x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isMatchingParentBlock(int seed, int x, int y, int z, @Nonnull Random random, @Nonnull ZoneBiomeResult zoneAndBiomeResult) {
/* 197 */     BlockFluidEntry groundCover = getCoverInGroundAt(seed, x, y, z, random, zoneAndBiomeResult.getBiome());
/* 198 */     if (!groundCover.equals(BlockFluidEntry.EMPTY) && !this.configuration.isValidParentBlock(groundCover.blockId(), groundCover.fluidId()))
/*     */     {
/* 200 */       return false;
/*     */     }
/* 202 */     BlockFluidEntry block = zoneAndBiomeResult.getBiome().getLayerContainer().getTopBlockAt(seed, x, z);
/* 203 */     return this.configuration.isValidParentBlock(block
/* 204 */         .blockId(), block
/* 205 */         .fluidId());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockFluidEntry getCoverInGroundAt(int seed, int x, int y, int z, @Nonnull Random random, @Nonnull Biome biome) {
/* 210 */     CoverContainer.CoverContainerEntry[] coverContainerEntries = biome.getCoverContainer().getEntries();
/* 211 */     for (CoverContainer.CoverContainerEntry coverContainerEntry : coverContainerEntries) {
/* 212 */       if (y < 320 && isMatchingCover(seed, coverContainerEntry, random, x, y, z)) {
/* 213 */         CoverContainer.CoverContainerEntry.CoverContainerEntryPart part = coverContainerEntry.get(random);
/* 214 */         if (part.getOffset() == -1) {
/* 215 */           return part.getEntry();
/*     */         }
/*     */       } 
/*     */     } 
/* 219 */     return BlockFluidEntry.EMPTY;
/*     */   }
/*     */   
/*     */   protected boolean isMatchingCover(int seed, @Nonnull CoverContainer.CoverContainerEntry coverContainerEntry, @Nonnull Random random, int x, int y, int z) {
/* 223 */     return (random.nextDouble() < coverContainerEntry.getCoverDensity() && coverContainerEntry
/* 224 */       .getMapCondition().eval(seed, x, z) && coverContainerEntry
/* 225 */       .getHeightCondition().eval(seed, x, z, y, random));
/*     */   }
/*     */   
/*     */   public UniquePrefabConfiguration getConfiguration() {
/* 229 */     return this.configuration;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefa\\unique\UniquePrefabGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */