/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.condition.DoubleThresholdCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.logic.HexMeshNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.MeshNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.DensityPointEvaluator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeshNoiseJsonLoader<K extends SeedResource>
/*    */   extends AbstractCellJitterJsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public MeshNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 24 */     super(seed.append(".MeshNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   public NoiseFunction load() {
/* 29 */     switch (loadCellType()) { default: throw new MatchException(null, null);case SQUARE: case HEX: break; }  return 
/*    */       
/* 31 */       (NoiseFunction)loadHexMeshNoise();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected MeshNoise loadGridMeshNoise() {
/* 37 */     double defaultJitter = loadDefaultJitter();
/* 38 */     return new MeshNoise(
/* 39 */         loadDensity(), 
/* 40 */         loadThickness(), 
/* 41 */         loadJitterX(defaultJitter), 
/* 42 */         loadJitterY(defaultJitter));
/*    */   }
/*    */   public static interface Constants {
/*    */     public static final String KEY_THICKNESS = "Thickness"; public static final String KEY_LINES_X = "LinesX"; public static final String KEY_LINES_Y = "LinesY"; public static final String KEY_LINES_Z = "LinesZ"; public static final String ERROR_NO_THICKNESS = "Could not find thickness. Keyword: Thickness"; public static final boolean DEFAULT_LINES_X = true; public static final boolean DEFAULT_LINES_Y = true; public static final boolean DEFAULT_LINES_Z = true; }
/*    */   @Nonnull
/*    */   protected HexMeshNoise loadHexMeshNoise() {
/* 48 */     return new HexMeshNoise(
/* 49 */         loadDensity(), 
/* 50 */         loadThickness(), 
/* 51 */         loadJitter(), 
/* 52 */         loadLinesX(), 
/* 53 */         loadLinesY(), 
/* 54 */         loadLinesZ());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CellType loadCellType() {
/* 60 */     CellType cellType = CellNoiseJsonLoader.Constants.DEFAULT_CELL_TYPE;
/* 61 */     if (has("CellType")) {
/* 62 */       cellType = CellType.valueOf(get("CellType").getAsString());
/*    */     }
/* 64 */     return cellType;
/*    */   }
/*    */   
/*    */   protected double loadThickness() {
/* 68 */     if (!has("Thickness")) throw new IllegalStateException("Could not find thickness. Keyword: Thickness"); 
/* 69 */     return get("Thickness").getAsDouble();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected IIntCondition loadDensity() {
/* 74 */     return DensityPointEvaluator.getDensityCondition(
/* 75 */         has("Density") ? (IDoubleCondition)new DoubleThresholdCondition((new DoubleThresholdJsonLoader<>(this.seed, this.dataFolder, get("Density"))).load()) : null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean loadLinesX() {
/* 80 */     return loadLinesFlag("LinesX", true);
/*    */   }
/*    */   
/*    */   protected boolean loadLinesY() {
/* 84 */     return loadLinesFlag("LinesY", true);
/*    */   }
/*    */   
/*    */   protected boolean loadLinesZ() {
/* 88 */     return loadLinesFlag("LinesZ", true);
/*    */   }
/*    */   
/*    */   protected boolean loadLinesFlag(String key, boolean defaulValue) {
/* 92 */     if (!has(key)) return defaulValue;
/*    */     
/* 94 */     return get(key).getAsBoolean();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\MeshNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */