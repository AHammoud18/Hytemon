/*     */ package com.hypixel.hytale.server.worldgen.biome;
/*     */ 
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZoneGeneratorResult;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomePatternGenerator
/*     */ {
/*     */   protected final IPointGenerator pointGenerator;
/*     */   @Nonnull
/*     */   protected final IWeightedMap<TileBiome> tileBiomes;
/*     */   @Nonnull
/*     */   protected final CustomBiome[] customBiomes;
/*     */   @Nonnull
/*     */   protected final Biome[] biomes;
/*     */   protected final int extents;
/*     */   
/*     */   public BiomePatternGenerator(IPointGenerator pointGenerator, @Nonnull IWeightedMap<TileBiome> tileBiomes, @Nonnull CustomBiome[] customBiomes) {
/*  30 */     this.pointGenerator = pointGenerator;
/*  31 */     this.tileBiomes = tileBiomes;
/*  32 */     this.customBiomes = customBiomes;
/*  33 */     this.biomes = new Biome[tileBiomes.size() + customBiomes.length];
/*  34 */     int n = 0;
/*  35 */     for (TileBiome biome : (TileBiome[])tileBiomes.internalKeys()) this.biomes[n++] = biome; 
/*  36 */     for (CustomBiome biome : customBiomes) this.biomes[n++] = biome;
/*     */ 
/*     */     
/*  39 */     this.extents = getExtents(this.biomes);
/*     */   }
/*     */   
/*     */   public int getExtents() {
/*  43 */     return this.extents;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Biome[] getBiomes() {
/*  48 */     return this.biomes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CustomBiome[] getCustomBiomes() {
/*  53 */     return this.customBiomes;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TileBiome getBiome(int seed, int x, int z) {
/*  58 */     return (TileBiome)this.tileBiomes.get(seed, x, z, (iSeed, ix, iz, generator) -> generator.getBiomeIndex(iSeed, ix, iz), this);
/*     */   }
/*     */   
/*     */   protected double getBiomeIndex(int seed, int x, int z) {
/*  62 */     ResultBuffer.ResultBuffer2d buf = this.pointGenerator.nearest2D(seed, x, z);
/*  63 */     return HashUtil.random(seed, buf.ix, buf.iy);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TileBiome getBiomeDirect(int seed, int x, int z) {
/*  68 */     return (TileBiome)this.tileBiomes.get(HashUtil.random(seed, Double.doubleToLongBits(x), Double.doubleToLongBits(z)));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Biome generateBiomeAt(@Nonnull ZoneGeneratorResult zoneResult, int seed, int x, int z) {
/*  73 */     TileBiome parentResult = getBiome(seed, x, z);
/*  74 */     CustomBiome customBiome = getCustomBiomeAt(seed, x, z, zoneResult, parentResult);
/*  75 */     return Objects.<Biome>requireNonNullElse(customBiome, parentResult);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CustomBiome getCustomBiomeAt(int seed, double x, double z, @Nonnull ZoneGeneratorResult zoneResult, @Nonnull Biome parentResult) {
/*  80 */     if (this.customBiomes.length > 0) {
/*  81 */       int parentBiomeIndex = parentResult.getId();
/*  82 */       for (CustomBiome customBiome : this.customBiomes) {
/*  83 */         CustomBiomeGenerator customBiomeGenerator = customBiome.getCustomBiomeGenerator();
/*  84 */         if (customBiomeGenerator.isValidParentBiome(parentBiomeIndex) && customBiomeGenerator
/*  85 */           .shouldGenerateAt(seed, x, z, zoneResult, customBiome)) {
/*  86 */           return customBiome;
/*     */         }
/*     */       } 
/*     */     } 
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  96 */     return "BiomePatternGenerator{pointGenerator=" + String.valueOf(this.pointGenerator) + ", tileBiomes=" + String.valueOf(this.tileBiomes) + ", customBiomes=" + 
/*     */ 
/*     */       
/*  99 */       Arrays.toString((Object[])this.customBiomes) + ", biomes=" + 
/* 100 */       Arrays.toString((Object[])this.biomes) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getExtents(@Nonnull Biome[] biomes) {
/* 105 */     int maxExtent = 0;
/* 106 */     for (Biome biome : biomes) {
/* 107 */       if (biome.getPrefabContainer() != null) {
/* 108 */         maxExtent = Math.max(maxExtent, biome.getPrefabContainer().getMaxSize());
/*     */       }
/*     */     } 
/* 111 */     return maxExtent;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\BiomePatternGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */