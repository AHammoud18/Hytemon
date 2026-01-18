/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.DoubleThresholdCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IDoubleThreshold;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.MeasurementMode;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.BorderPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.JitterPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.SkipCellPointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.DefaultCellJitter;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PointEvaluatorJsonLoader<T extends SeedResource>
/*     */   extends AbstractCellJitterJsonLoader<T, PointEvaluator> {
/*     */   @Nonnull
/*     */   protected final MeasurementMode measurementMode;
/*     */   protected final PointDistanceFunction pointDistanceFunction;
/*     */   
/*     */   public PointEvaluatorJsonLoader(@Nonnull SeedString<T> seed, Path dataFolder, JsonElement json) {
/*  28 */     this(seed, dataFolder, json, (PointDistanceFunction)null);
/*     */   }
/*     */   
/*     */   public PointEvaluatorJsonLoader(@Nonnull SeedString<T> seed, Path dataFolder, JsonElement json, @Nullable PointDistanceFunction pointDistanceFunction) {
/*  32 */     this(seed, dataFolder, json, MeasurementMode.CENTRE_DISTANCE, pointDistanceFunction);
/*     */   }
/*     */   
/*     */   public PointEvaluatorJsonLoader(@Nonnull SeedString<T> seed, Path dataFolder, JsonElement json, @Nonnull MeasurementMode measurementMode, @Nullable PointDistanceFunction pointDistanceFunction) {
/*  36 */     super(seed.append(".PointEvaluator"), dataFolder, json);
/*  37 */     this.measurementMode = measurementMode;
/*  38 */     this.pointDistanceFunction = pointDistanceFunction;
/*     */   }
/*     */ 
/*     */   
/*     */   public PointEvaluator load() {
/*  43 */     switch (this.measurementMode) { default: throw new MatchException(null, null);case CENTRE_DISTANCE: case BORDER_DISTANCE: break; }  return 
/*     */       
/*  45 */       loadBorderPointEvaluator();
/*     */   }
/*     */ 
/*     */   
/*     */   public PointEvaluator loadCentrePointEvaluator() {
/*  50 */     return PointEvaluator.of(
/*  51 */         loadPointDistanceFunction(), 
/*  52 */         loadDensity(), 
/*  53 */         loadDistanceRange(), 
/*  54 */         loadSkipCount(), 
/*  55 */         loadSkipMode(), 
/*  56 */         loadJitter());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PointEvaluator loadBorderPointEvaluator() {
/*  62 */     BorderPointEvaluator pointEvaluator = BorderPointEvaluator.INSTANCE;
/*  63 */     CellJitter jitter = loadJitter();
/*  64 */     if (jitter == DefaultCellJitter.DEFAULT_ONE) return (PointEvaluator)pointEvaluator;
/*     */     
/*  66 */     return (PointEvaluator)new JitterPointEvaluator((PointEvaluator)pointEvaluator, jitter);
/*     */   }
/*     */   
/*     */   public PointDistanceFunction loadPointDistanceFunction() {
/*  70 */     if (this.pointDistanceFunction != null) return this.pointDistanceFunction;
/*     */     
/*  72 */     DistanceCalculationMode distanceCalculationMode = CellNoiseJsonLoader.Constants.DEFAULT_DISTANCE_MODE;
/*  73 */     if (has("DistanceMode")) {
/*  74 */       distanceCalculationMode = DistanceCalculationMode.valueOf(get("DistanceMode").getAsString());
/*     */     }
/*     */     
/*  77 */     return distanceCalculationMode.getFunction();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IDoubleRange loadDistanceRange() {
/*  82 */     if (!has("DistanceRange")) return null;
/*     */     
/*  84 */     return (new DoubleRangeJsonLoader<>(this.seed, this.dataFolder, get("DistanceRange")))
/*  85 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IDoubleCondition loadDensity() {
/*  90 */     if (!has("Density")) return null;
/*     */ 
/*     */     
/*  93 */     IDoubleThreshold threshold = (new DoubleThresholdJsonLoader<>(this.seed, this.dataFolder, get("Density"))).load();
/*     */     
/*  95 */     return (IDoubleCondition)new DoubleThresholdCondition(threshold);
/*     */   }
/*     */   
/*     */   public int loadSkipCount() {
/*  99 */     return mustGetNumber("Skip", Integer.valueOf(0)).intValue();
/*     */   }
/*     */   
/*     */   public SkipCellPointEvaluator.Mode loadSkipMode() {
/* 103 */     String name = mustGetString("SkipMode", SkipCellPointEvaluator.DEFAULT_MODE.name());
/* 104 */     return SkipCellPointEvaluator.Mode.valueOf(name);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\PointEvaluatorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */