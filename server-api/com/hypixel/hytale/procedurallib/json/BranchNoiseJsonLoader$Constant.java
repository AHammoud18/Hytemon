/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.logic.DistanceNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.BranchEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseFormulaProperty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Constant
/*     */ {
/*     */   public static final String KEY_PARENT_TYPE = "ParentType";
/*     */   public static final String KEY_PARENT_DISTANCE_CALCULATION_MODE = "ParentDistanceMode";
/*     */   public static final String KEY_PARENT_DISTANCE_TYPE = "ParentDistanceType";
/*     */   public static final String KEY_PARENT_FORMULA = "ParentFormula";
/*     */   public static final String KEY_PARENT_JITTER = "ParentJitter";
/*     */   public static final String KEY_PARENT_JITTER_X = "ParentJitterX";
/*     */   public static final String KEY_PARENT_JITTER_Y = "ParentJitterY";
/*     */   public static final String KEY_PARENT_VALUE = "ParentValue";
/*     */   public static final String KEY_PARENT_FADE = "ParentFade";
/*     */   public static final String KEY_PARENT_DENSITY = "ParentDensity";
/*     */   public static final String KEY_LINE_TYPE = "LineType";
/*     */   public static final String KEY_LINE_DIRECTION_MODE = "LineDirection";
/*     */   public static final String KEY_LINE_SCALE = "LineScale";
/*     */   public static final String KEY_LINE_JITTER = "LineJitter";
/*     */   public static final String KEY_LINE_JITTER_X = "LineJitterX";
/*     */   public static final String KEY_LINE_JITTER_Y = "LineJitterY";
/*     */   public static final String KEY_LINE_THICKNESS = "LineThickness";
/*     */   public static final double DEFAULT_JITTER = 1.0D;
/*     */   public static final double DEFAULT_PARENT_VALUE = 0.0D;
/*     */   public static final double DEFAULT_PARENT_FADE = 0.0D;
/*     */   public static final double DEFAULT_LINE_SCALE = 0.1D;
/*     */   public static final double DEFAULT_LINE_THICKNESS = 0.1D;
/* 236 */   public static final CellType DEFAULT_PARENT_CELL_TYPE = CellType.SQUARE;
/* 237 */   public static final CellType DEFAULT_LINE_CELL_TYPE = CellType.SQUARE;
/* 238 */   public static final BranchEvaluator.Direction DEFAULT_LINE_DIRECTION = BranchEvaluator.Direction.OUTWARD;
/* 239 */   public static final DistanceCalculationMode DEFAULT_PARENT_DISTANCE_CAL_MODE = DistanceCalculationMode.EUCLIDEAN;
/* 240 */   public static final DistanceNoise.Distance2Mode DEFAULT_PARENT_DISTANCE_TYPE = DistanceNoise.Distance2Mode.DIV;
/* 241 */   public static final NoiseFormulaProperty.NoiseFormula DEFAULT_PARENT_FORMULA = NoiseFormulaProperty.NoiseFormula.SQRT;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\BranchNoiseJsonLoader$Constant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */