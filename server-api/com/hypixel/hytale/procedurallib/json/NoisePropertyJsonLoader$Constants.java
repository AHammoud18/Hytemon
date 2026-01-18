/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.property.FractalNoiseProperty;
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
/*     */   public static final String KEY_SEED = "Seed";
/*     */   public static final String KEY_SUM_FACTORS = "Factors";
/*     */   public static final String KEY_NORMALIZE_RANGE = "Range";
/*     */   public static final String KEY_DISTORTED_RANDOMIZER = "Randomizer";
/*     */   public static final String KEY_TYPE = "Type";
/*     */   public static final String KEY_NOISE = "Noise";
/*     */   public static final String KEY_FRACTAL_MODE = "FractalMode";
/*     */   public static final String KEY_OCTAVES = "Octaves";
/*     */   public static final String KEY_LACUNARITY = "Lacunarity";
/*     */   public static final String KEY_PERSISTENCE = "Persistence";
/*     */   public static final String KEY_FORMULA = "Formula";
/*     */   public static final String KEY_CURVE = "Curve";
/*     */   public static final String KEY_SCALE = "Scale";
/*     */   public static final String KEY_NORMALIZE = "Normalize";
/*     */   public static final String KEY_OFFSET = "Offset";
/*     */   public static final String KEY_OFFSET_X = "OffsetX";
/*     */   public static final String KEY_OFFSET_Y = "OffsetY";
/*     */   public static final String KEY_OFFSET_Z = "OffsetZ";
/*     */   public static final String KEY_GRADIENT = "Gradient";
/*     */   public static final String ERROR_NO_NOISE = "Could not find noise map data. Keyword: Noise";
/*     */   public static final String ERROR_SUM_NO_FACTORS = "Could not find factors for sum composed noise map. Keyword: Factors";
/*     */   public static final String ERROR_NO_FORMULA = "Could not find formula type for noise map. Keyword: Formula";
/*     */   public static final String ERROR_NO_SCALE = "Could not find scale data for scaled noise map. Keyword: Scale";
/*     */   public static final String ERROR_DISTORTED_RANDOMIZER = "Could not find randomizer for distorted noise map. Keyword: Randomizer";
/*     */   public static final String ERROR_NORMALIZE_NO_RANGE = "Could not find range data for normalized noise map. Keyword: Range";
/*     */   public static final String ERROR_UNKOWN_TYPE = "Could not find instructions for noise property type: %s";
/* 323 */   public static final FractalNoiseProperty.FractalMode DEFAULT_FRACTAL_MODE = FractalNoiseProperty.FractalMode.FBM;
/*     */   public static final double DEFAULT_LACUNARITY = 2.0D;
/*     */   public static final double DEFAULT_PERSISTENCE = 0.5D;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\NoisePropertyJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */