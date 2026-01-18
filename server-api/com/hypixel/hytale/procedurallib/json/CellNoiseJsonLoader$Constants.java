/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.logic.CellNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Constants
/*     */ {
/*     */   public static final String KEY_JITTER = "Jitter";
/*     */   public static final String KEY_JITTER_X = "JitterX";
/*     */   public static final String KEY_JITTER_Y = "JitterY";
/*     */   public static final String KEY_JITTER_Z = "JitterZ";
/*     */   public static final String KEY_DENSITY = "Density";
/*     */   public static final String KEY_CELL_MODE = "CellMode";
/*     */   public static final String KEY_NOISE_LOOKUP = "NoiseLookup";
/*     */   public static final String KEY_DISTANCE_MODE = "DistanceMode";
/*     */   public static final String KEY_DISTANCE_RANGE = "DistanceRange";
/*     */   public static final String KEY_CELL_TYPE = "CellType";
/*     */   public static final String KEY_SKIP_CELLS = "Skip";
/*     */   public static final String KEY_SKIP_MODE = "SkipMode";
/*     */   public static final double DEFAULT_JITTER = 1.0D;
/*     */   public static final double DEFAULT_DISTANCE_RANGE = 1.0D;
/*     */   public static final double DEFAULT_DENSITY_LOWER = 0.0D;
/*     */   public static final double DEFAULT_DENSITY_UPPER = 1.0D;
/* 113 */   public static final DistanceCalculationMode DEFAULT_DISTANCE_MODE = DistanceCalculationMode.EUCLIDEAN;
/* 114 */   public static final CellNoise.CellMode DEFAULT_CELL_MODE = CellNoise.CellMode.CELL_VALUE;
/* 115 */   public static final CellType DEFAULT_CELL_TYPE = CellType.SQUARE;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CellNoiseJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */