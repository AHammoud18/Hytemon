/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.BranchNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.DistanceNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellPointFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.HexCellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.BranchEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.DensityPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseFormulaProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.nio.file.Path;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BranchNoiseJsonLoader<T extends SeedResource> extends AbstractCellJitterJsonLoader<T, BranchNoise> {
/*     */   public BranchNoiseJsonLoader(SeedString<T> seed, Path dataFolder, JsonElement json) {
/*  27 */     super(seed, dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BranchNoise load() {
/*  33 */     T resource = this.seed.get();
/*     */     
/*  35 */     CellType parentCellType = loadParentCellType();
/*  36 */     CellDistanceFunction parentFunction = getCellDistanceFunction(parentCellType);
/*  37 */     PointEvaluator parentEvaluator = loadParentEvaluator();
/*  38 */     double parentValue = loadDouble("ParentValue", 0.0D);
/*  39 */     IDoubleRange parentFade = loadRange("ParentFade", 0.0D);
/*  40 */     IIntCondition parentDensity = loadParentDensity();
/*  41 */     DistanceNoise.Distance2Function parentDistanceType = loadParentDistance2Function();
/*  42 */     NoiseFormulaProperty.NoiseFormula.Formula parentFormula = loadParentFormula();
/*     */     
/*  44 */     CellType lineCellType = loadLineCellType();
/*  45 */     CellPointFunction linePointFunction = getCellPointFunction(lineCellType);
/*  46 */     double lineScale = loadDouble("LineScale", 0.1D);
/*  47 */     IDoubleRange lineThickness = loadRange("LineThickness", 0.1D);
/*  48 */     CellDistanceFunction lineFunction = getCellDistanceFunction(lineCellType);
/*  49 */     PointEvaluator lineEvaluator = loadLineEvaluator(parentFunction, linePointFunction, lineScale);
/*     */     
/*  51 */     return new LoadedBranchNoise(parentFunction, parentEvaluator, parentValue, parentFade, parentDensity, parentDistanceType, parentFormula, lineFunction, lineEvaluator, lineScale, lineThickness, (SeedResource)resource);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CellType loadParentCellType() {
/*  68 */     return loadEnum("ParentType", CellType::valueOf, Constant.DEFAULT_PARENT_CELL_TYPE);
/*     */   }
/*     */   
/*     */   protected CellType loadLineCellType() {
/*  72 */     return loadEnum("LineType", CellType::valueOf, Constant.DEFAULT_LINE_CELL_TYPE);
/*     */   }
/*     */   
/*     */   protected PointEvaluator loadParentEvaluator() {
/*  76 */     double defaultJitter = loadDouble("ParentJitter", 1.0D);
/*  77 */     double jitterX = loadDouble("ParentJitterX", defaultJitter);
/*  78 */     double jitterY = loadDouble("ParentJitterY", defaultJitter);
/*  79 */     CellJitter jitter = CellJitter.of(jitterX, jitterY, 1.0D);
/*     */     
/*  81 */     DistanceCalculationMode distanceMode = loadEnum("ParentDistanceMode", DistanceCalculationMode::valueOf, Constant.DEFAULT_PARENT_DISTANCE_CAL_MODE);
/*     */     
/*  83 */     return PointEvaluator.of(distanceMode.getFunction(), null, null, jitter);
/*     */   }
/*     */   @Nonnull
/*     */   protected IIntCondition loadParentDensity() {
/*     */     IIntCondition iIntCondition;
/*  88 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/*  89 */     if (has("ParentDensity")) {
/*     */       
/*  91 */       IDoubleCondition densityRange = (new DoubleConditionJsonLoader<>(this.seed, this.dataFolder, get("ParentDensity"))).load();
/*     */       
/*  93 */       iIntCondition = DensityPointEvaluator.getDensityCondition(densityRange);
/*     */     } 
/*  95 */     return iIntCondition;
/*     */   }
/*     */   
/*     */   protected DistanceNoise.Distance2Function loadParentDistance2Function() {
/*  99 */     return ((DistanceNoise.Distance2Mode)loadEnum("ParentDistanceType", DistanceNoise.Distance2Mode::valueOf, Constant.DEFAULT_PARENT_DISTANCE_TYPE)).getFunction();
/*     */   }
/*     */   
/*     */   protected NoiseFormulaProperty.NoiseFormula.Formula loadParentFormula() {
/* 103 */     return ((NoiseFormulaProperty.NoiseFormula)loadEnum("ParentFormula", NoiseFormulaProperty.NoiseFormula::valueOf, Constant.DEFAULT_PARENT_FORMULA)).getFormula();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected PointEvaluator loadLineEvaluator(@Nonnull CellDistanceFunction parentFunction, @Nonnull CellPointFunction linePointFunction, double lineScale) {
/* 110 */     double defaultJitter = loadDouble("LineJitter", 1.0D);
/* 111 */     double jitterX = loadDouble("LineJitterX", defaultJitter);
/* 112 */     double jitterY = loadDouble("LineJitterY", defaultJitter);
/* 113 */     CellJitter jitter = CellJitter.of(jitterX, jitterY, 1.0D);
/*     */     
/* 115 */     BranchEvaluator.Direction direction = loadEnum("LineDirection", BranchEvaluator.Direction::valueOf, Constant.DEFAULT_LINE_DIRECTION);
/*     */     
/* 117 */     return (PointEvaluator)new BranchEvaluator(parentFunction, linePointFunction, direction, jitter, lineScale);
/*     */   }
/*     */   
/*     */   protected double loadDouble(String key, double def) {
/* 121 */     if (!has(key)) return def;
/*     */     
/* 123 */     return get(key).getAsDouble();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IDoubleRange loadRange(String key, double def) {
/* 128 */     if (!has(key)) return (IDoubleRange)new DoubleRange.Constant(def);
/*     */     
/* 130 */     return (new DoubleRangeJsonLoader<>(this.seed, this.dataFolder, get(key)))
/* 131 */       .load();
/*     */   }
/*     */   
/*     */   protected <E extends Enum<E>> E loadEnum(String key, @Nonnull Function<String, E> valueOf, E def) {
/* 135 */     if (!has(key)) return def;
/*     */     
/* 137 */     return valueOf.apply(get(key).getAsString());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static CellDistanceFunction getCellDistanceFunction(@Nonnull CellType cellType) {
/* 142 */     switch (cellType) { default: throw new MatchException(null, null);case SQUARE: case HEX: break; }  return 
/*     */       
/* 144 */       (CellDistanceFunction)HexCellDistanceFunction.DISTANCE_FUNCTION;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static CellPointFunction getCellPointFunction(@Nonnull CellType cellType) {
/* 150 */     switch (cellType) { default: throw new MatchException(null, null);case SQUARE: case HEX: break; }  return 
/*     */       
/* 152 */       HexCellDistanceFunction.POINT_FUNCTION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class LoadedBranchNoise
/*     */     extends BranchNoise
/*     */   {
/*     */     protected final SeedResource seedResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LoadedBranchNoise(CellDistanceFunction parentFunction, PointEvaluator parentEvaluator, double parentValue, IDoubleRange parentFade, IIntCondition parentDensity, DistanceNoise.Distance2Function distance2Function, NoiseFormulaProperty.NoiseFormula.Formula noiseFormula, CellDistanceFunction lineFunction, PointEvaluator lineEvaluator, double lineScale, IDoubleRange lineThickness, SeedResource seedResource) {
/* 171 */       super(parentFunction, parentEvaluator, parentValue, parentFade, parentDensity, distance2Function, noiseFormula, lineFunction, lineEvaluator, lineScale, lineThickness);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       this.seedResource = seedResource;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 188 */       return this.seedResource.localBuffer2d();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 194 */       return "LoadedBranchNoise{seedResource=" + String.valueOf(this.seedResource) + ", parentFunction=" + String.valueOf(this.parentFunction) + ", parentEvaluator=" + String.valueOf(this.parentEvaluator) + ", parentValue=" + this.parentValue + ", parentFade=" + String.valueOf(this.parentFade) + ", distance2Function=" + String.valueOf(this.distance2Function) + ", noiseFormula=" + String.valueOf(this.noiseFormula) + ", lineFunction=" + String.valueOf(this.lineFunction) + ", lineEvaluator=" + String.valueOf(this.lineEvaluator) + ", lineScale=" + this.lineScale + ", lineThickness=" + String.valueOf(this.lineThickness) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Constant
/*     */   {
/*     */     public static final String KEY_PARENT_TYPE = "ParentType";
/*     */     
/*     */     public static final String KEY_PARENT_DISTANCE_CALCULATION_MODE = "ParentDistanceMode";
/*     */     
/*     */     public static final String KEY_PARENT_DISTANCE_TYPE = "ParentDistanceType";
/*     */     
/*     */     public static final String KEY_PARENT_FORMULA = "ParentFormula";
/*     */     
/*     */     public static final String KEY_PARENT_JITTER = "ParentJitter";
/*     */     
/*     */     public static final String KEY_PARENT_JITTER_X = "ParentJitterX";
/*     */     
/*     */     public static final String KEY_PARENT_JITTER_Y = "ParentJitterY";
/*     */     
/*     */     public static final String KEY_PARENT_VALUE = "ParentValue";
/*     */     
/*     */     public static final String KEY_PARENT_FADE = "ParentFade";
/*     */     
/*     */     public static final String KEY_PARENT_DENSITY = "ParentDensity";
/*     */     
/*     */     public static final String KEY_LINE_TYPE = "LineType";
/*     */     
/*     */     public static final String KEY_LINE_DIRECTION_MODE = "LineDirection";
/*     */     
/*     */     public static final String KEY_LINE_SCALE = "LineScale";
/*     */     
/*     */     public static final String KEY_LINE_JITTER = "LineJitter";
/*     */     public static final String KEY_LINE_JITTER_X = "LineJitterX";
/*     */     public static final String KEY_LINE_JITTER_Y = "LineJitterY";
/*     */     public static final String KEY_LINE_THICKNESS = "LineThickness";
/*     */     public static final double DEFAULT_JITTER = 1.0D;
/*     */     public static final double DEFAULT_PARENT_VALUE = 0.0D;
/*     */     public static final double DEFAULT_PARENT_FADE = 0.0D;
/*     */     public static final double DEFAULT_LINE_SCALE = 0.1D;
/*     */     public static final double DEFAULT_LINE_THICKNESS = 0.1D;
/* 236 */     public static final CellType DEFAULT_PARENT_CELL_TYPE = CellType.SQUARE;
/* 237 */     public static final CellType DEFAULT_LINE_CELL_TYPE = CellType.SQUARE;
/* 238 */     public static final BranchEvaluator.Direction DEFAULT_LINE_DIRECTION = BranchEvaluator.Direction.OUTWARD;
/* 239 */     public static final DistanceCalculationMode DEFAULT_PARENT_DISTANCE_CAL_MODE = DistanceCalculationMode.EUCLIDEAN;
/* 240 */     public static final DistanceNoise.Distance2Mode DEFAULT_PARENT_DISTANCE_TYPE = DistanceNoise.Distance2Mode.DIV;
/* 241 */     public static final NoiseFormulaProperty.NoiseFormula DEFAULT_PARENT_FORMULA = NoiseFormulaProperty.NoiseFormula.SQRT;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\BranchNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */