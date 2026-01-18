/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClimateNoise
/*     */ {
/*     */   @Nonnull
/*     */   public final Grid grid;
/*     */   @Nonnull
/*     */   public final NoiseProperty continent;
/*     */   @Nonnull
/*     */   public final NoiseProperty temperature;
/*     */   @Nonnull
/*     */   public final NoiseProperty intensity;
/*     */   @Nonnull
/*     */   public final Thresholds thresholds;
/*     */   
/*     */   public ClimateNoise(@Nonnull Grid grid, @Nonnull NoiseProperty continent, @Nonnull NoiseProperty temperature, @Nonnull NoiseProperty intensity, @Nonnull Thresholds thresholds) {
/*  30 */     this.grid = grid;
/*  31 */     this.temperature = temperature;
/*  32 */     this.intensity = intensity;
/*  33 */     this.continent = continent;
/*  34 */     this.thresholds = thresholds;
/*     */   }
/*     */   
/*     */   public int generate(int seed, double x, double y, @Nonnull Buffer buffer, @Nonnull ClimateGraph climate) {
/*  38 */     this.grid.eval(seed, x, y, buffer.pos);
/*     */     
/*  40 */     double c = this.continent.get(seed, x, y);
/*  41 */     double t = this.temperature.get(seed, buffer.pos.x, buffer.pos.y);
/*  42 */     double i = this.intensity.get(seed, buffer.pos.x, buffer.pos.y);
/*     */     
/*  44 */     int index = climate.indexOf(t, i);
/*     */     
/*  46 */     buffer.continent = c;
/*  47 */     buffer.temperature = t;
/*  48 */     buffer.intensity = i;
/*  49 */     buffer.fade = climate.getFade(index);
/*     */     
/*  51 */     int id = climate.getId(index);
/*  52 */     int flags = getContinentFlags(c, this.thresholds);
/*     */     
/*  54 */     return id | flags;
/*     */   }
/*     */   
/*     */   private static int getContinentFlags(double value, @Nonnull Thresholds thresholds) {
/*  58 */     boolean isLand = (value <= thresholds.landShallowOceanOuter);
/*  59 */     boolean isIsland = (value >= thresholds.islandShallowOceanOuter);
/*  60 */     boolean isOcean = (value > thresholds.land && value < thresholds.island);
/*  61 */     boolean isShore = ((isLand && value > thresholds.landShoreInner) || (isIsland && value < thresholds.islandShoreInner));
/*     */     
/*  63 */     int flags = 0;
/*  64 */     if (isOcean) flags |= Integer.MIN_VALUE; 
/*  65 */     if (isShore) flags |= 0x40000000; 
/*  66 */     if (isIsland) flags |= 0x20000000;
/*     */     
/*  68 */     return flags;
/*     */   }
/*     */   
/*     */   public static class Buffer {
/*  72 */     public double continent = 0.0D;
/*  73 */     public double temperature = 0.0D;
/*  74 */     public double intensity = 0.0D;
/*  75 */     public double fade = 0.0D;
/*  76 */     public final ResultBuffer.ResultBuffer2d pos = new ResultBuffer.ResultBuffer2d();
/*     */   }
/*     */   
/*     */   public static class Grid
/*     */   {
/*     */     public final int seedOffset;
/*     */     public final double scale;
/*     */     @Nonnull
/*     */     public final PointEvaluator evaluator;
/*     */     @Nonnull
/*     */     public final CellDistanceFunction grid;
/*     */     public final transient double invScale;
/*     */     
/*     */     public Grid(int seedOffset, double scale, @Nonnull CellDistanceFunction grid, @Nonnull PointEvaluator evaluator) {
/*  90 */       this.seedOffset = seedOffset;
/*  91 */       this.scale = scale;
/*  92 */       this.evaluator = evaluator;
/*  93 */       this.grid = grid;
/*  94 */       this.invScale = 1.0D / scale;
/*     */     }
/*     */     
/*     */     public void eval(int seed, double x, double y, ResultBuffer.ResultBuffer2d buffer) {
/*  98 */       x *= this.scale;
/*  99 */       y *= this.scale;
/* 100 */       buffer.distance = Double.MAX_VALUE;
/* 101 */       buffer.distance2 = Double.MAX_VALUE;
/* 102 */       this.grid.nearest2D(seed + this.seedOffset, x, y, MathUtil.floor(x), MathUtil.floor(y), buffer, this.evaluator);
/* 103 */       buffer.x *= this.invScale;
/* 104 */       buffer.y *= this.invScale;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Thresholds
/*     */   {
/*     */     public static final double LAND_DEFAULT = 0.5D;
/*     */     public static final double ISLAND_DEFAULT = 0.8D;
/*     */     public static final double BEACH_SIZE_DEFAULT = 0.05D;
/*     */     public static final double SHALLOW_OCEAN_SIZE_DEFAULT = 0.15D;
/*     */     public final double land;
/*     */     public final double island;
/*     */     public final double beachSize;
/*     */     public final double shallowOceanSize;
/*     */     public final transient double landShoreInner;
/*     */     public final transient double islandShoreInner;
/*     */     public final transient double landShallowOceanOuter;
/*     */     public final transient double islandShallowOceanOuter;
/*     */     
/*     */     public Thresholds(double land, double island, double beach, double shore) {
/* 125 */       this.land = land;
/* 126 */       this.island = island;
/* 127 */       this.beachSize = beach;
/* 128 */       this.shallowOceanSize = shore;
/*     */       
/* 130 */       this.landShoreInner = land - beach;
/* 131 */       this.islandShoreInner = island + beach;
/*     */       
/* 133 */       this.landShallowOceanOuter = land + shore;
/* 134 */       this.islandShallowOceanOuter = island - shore * 0.5D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */