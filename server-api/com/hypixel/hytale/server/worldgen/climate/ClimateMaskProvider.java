/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.random.CoordinateRandomizer;
/*     */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zoom.ExactZoom;
/*     */ import com.hypixel.hytale.server.worldgen.zoom.FuzzyZoom;
/*     */ import com.hypixel.hytale.server.worldgen.zoom.PixelProvider;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ClimateMaskProvider
/*     */   extends MaskProvider {
/*  20 */   private static final FuzzyZoom EMPTY_ZOOM = new FuzzyZoom(CoordinateRandomizer.EMPTY_RANDOMIZER, new ExactZoom(new PixelProvider(new BufferedImage(1, 1, 1)), 1.0D, 1.0D, 0, 0));
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ICoordinateRandomizer randomizer;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ClimateNoise noise;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ClimateGraph graph;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final UniqueClimateGenerator uniqueGenerator;
/*     */ 
/*     */ 
/*     */   
/*     */   public ClimateMaskProvider(@Nonnull ICoordinateRandomizer randomizer, @Nonnull ClimateNoise noise, @Nonnull ClimateGraph graph, @Nonnull UniqueClimateGenerator uniqueGenerator) {
/*  41 */     super(EMPTY_ZOOM);
/*  42 */     this.randomizer = randomizer;
/*  43 */     this.noise = noise;
/*  44 */     this.graph = graph;
/*  45 */     this.uniqueGenerator = uniqueGenerator;
/*     */   }
/*     */   
/*     */   private ClimateMaskProvider(@Nonnull ClimateMaskProvider other, @Nonnull UniqueClimateGenerator uniqueGenerator) {
/*  49 */     super(EMPTY_ZOOM);
/*  50 */     this.randomizer = other.randomizer;
/*  51 */     this.noise = other.noise;
/*  52 */     this.graph = other.graph;
/*  53 */     this.uniqueGenerator = uniqueGenerator;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ClimateGraph getGraph() {
/*  58 */     return this.graph;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inBounds(double x, double y) {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX(int seed, double x, double y) {
/*  68 */     return this.randomizer.randomDoubleX(seed, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY(int seed, double x, double y) {
/*  73 */     return this.randomizer.randomDoubleY(seed, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int seed, double x, double y) {
/*  78 */     int unique = this.uniqueGenerator.generate(MathUtil.floor(x), MathUtil.floor(y));
/*     */     
/*  80 */     if (unique != -1) {
/*  81 */       return unique;
/*     */     }
/*     */     
/*  84 */     ClimateNoise.Buffer buffer = (ChunkGenerator.getResource()).climateBuffer;
/*  85 */     int id = this.noise.generate(seed, x, y, buffer, this.graph);
/*     */     
/*  87 */     return ClimateType.color(id, this.graph);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(double x, double y) {
/*  92 */     ClimateNoise.Buffer buffer = (ChunkGenerator.getResource()).climateBuffer;
/*  93 */     return buffer.fade;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaskProvider generateUniqueZones(int seed, @Nonnull Zone.UniqueCandidate[] candidates, @Nonnull FastRandom random, @Nonnull List<Zone.Unique> collector) {
/* 101 */     if (candidates.length == 0) {
/* 102 */       return this;
/*     */     }
/*     */     
/* 105 */     return new ClimateMaskProvider(this, this.uniqueGenerator.apply(seed, candidates, this.noise, this.graph, collector));
/*     */   }
/*     */ 
/*     */   
/*     */   public Zone.UniqueCandidate[] generateUniqueZoneCandidates(Zone.UniqueEntry[] entries, int maxPositions) {
/* 110 */     return Zone.UniqueCandidate.EMPTY_ARRAY;
/*     */   }
/*     */   
/*     */   public Zone.UniqueCandidate[] getUniqueZoneCandidates(Map<String, Zone> zoneLookup) {
/* 114 */     return this.uniqueGenerator.getCandidates(zoneLookup);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateMaskProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */