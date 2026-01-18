/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.CellNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CellNoiseJsonLoader<K extends SeedResource>
/*     */   extends JsonLoader<K, NoiseFunction>
/*     */ {
/*     */   public CellNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/*  25 */     super(seed.append(".CellNoise"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public NoiseFunction load() {
/*  31 */     CellDistanceFunction cellDistanceFunction = loadCellDistanceFunction();
/*  32 */     PointEvaluator pointEvaluator = loadPointEvaluator();
/*  33 */     CellNoise.CellFunction cellFunction = loadCellFunction();
/*  34 */     NoiseProperty noiseLookup = loadNoiseLookup();
/*  35 */     return (NoiseFunction)new LoadedCellNoise(cellDistanceFunction, pointEvaluator, cellFunction, noiseLookup, (SeedResource)this.seed.get());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected CellDistanceFunction loadCellDistanceFunction() {
/*  40 */     return (new CellDistanceFunctionJsonLoader<>(this.seed, this.dataFolder, this.json, null))
/*  41 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected PointEvaluator loadPointEvaluator() {
/*  46 */     return (new PointEvaluatorJsonLoader<>(this.seed, this.dataFolder, this.json))
/*  47 */       .load();
/*     */   }
/*     */   
/*     */   protected CellNoise.CellFunction loadCellFunction() {
/*  51 */     CellNoise.CellMode cellMode = Constants.DEFAULT_CELL_MODE;
/*  52 */     if (has("CellMode")) {
/*  53 */       cellMode = CellNoise.CellMode.valueOf(get("CellMode").getAsString());
/*     */     }
/*  55 */     return cellMode.getFunction();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadNoiseLookup() {
/*  60 */     NoiseProperty noiseProperty = null;
/*  61 */     if (has("NoiseLookup"))
/*     */     {
/*  63 */       noiseProperty = (new NoisePropertyJsonLoader<>(this.seed, this.dataFolder, get("NoiseLookup"))).load();
/*     */     }
/*  65 */     return noiseProperty;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LoadedCellNoise
/*     */     extends CellNoise
/*     */   {
/*     */     private final SeedResource seedResource;
/*     */ 
/*     */     
/*     */     public LoadedCellNoise(CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator, CellNoise.CellFunction cellFunction, @Nullable NoiseProperty noiseLookup, SeedResource seedResource) {
/*  76 */       super(cellDistanceFunction, pointEvaluator, cellFunction, noiseLookup);
/*  77 */       this.seedResource = seedResource;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/*  83 */       return this.seedResource.localBuffer2d();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/*  89 */       return this.seedResource.localBuffer3d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_JITTER = "Jitter";
/*     */     
/*     */     public static final String KEY_JITTER_X = "JitterX";
/*     */     public static final String KEY_JITTER_Y = "JitterY";
/*     */     public static final String KEY_JITTER_Z = "JitterZ";
/*     */     public static final String KEY_DENSITY = "Density";
/*     */     public static final String KEY_CELL_MODE = "CellMode";
/*     */     public static final String KEY_NOISE_LOOKUP = "NoiseLookup";
/*     */     public static final String KEY_DISTANCE_MODE = "DistanceMode";
/*     */     public static final String KEY_DISTANCE_RANGE = "DistanceRange";
/*     */     public static final String KEY_CELL_TYPE = "CellType";
/*     */     public static final String KEY_SKIP_CELLS = "Skip";
/*     */     public static final String KEY_SKIP_MODE = "SkipMode";
/*     */     public static final double DEFAULT_JITTER = 1.0D;
/*     */     public static final double DEFAULT_DISTANCE_RANGE = 1.0D;
/*     */     public static final double DEFAULT_DENSITY_LOWER = 0.0D;
/*     */     public static final double DEFAULT_DENSITY_UPPER = 1.0D;
/* 113 */     public static final DistanceCalculationMode DEFAULT_DISTANCE_MODE = DistanceCalculationMode.EUCLIDEAN;
/* 114 */     public static final CellNoise.CellMode DEFAULT_CELL_MODE = CellNoise.CellMode.CELL_VALUE;
/* 115 */     public static final CellType DEFAULT_CELL_TYPE = CellType.SQUARE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CellNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */