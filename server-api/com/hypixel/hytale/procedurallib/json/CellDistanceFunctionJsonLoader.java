/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellType;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.HexCellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.MeasurementMode;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CellDistanceFunctionJsonLoader<K extends SeedResource> extends JsonLoader<K, CellDistanceFunction> {
/*    */   protected final MeasurementMode measurementMode;
/*    */   protected final PointDistanceFunction pointDistanceFunction;
/*    */   
/*    */   public CellDistanceFunctionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, @Nullable PointDistanceFunction pointDistanceFunction) {
/* 18 */     this(seed, dataFolder, json, MeasurementMode.CENTRE_DISTANCE, pointDistanceFunction);
/*    */   }
/*    */   
/*    */   public CellDistanceFunctionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, MeasurementMode measurementMode, @Nullable PointDistanceFunction pointDistanceFunction) {
/* 22 */     super(seed.append(".CellDistanceFunction"), dataFolder, json);
/* 23 */     this.measurementMode = measurementMode;
/* 24 */     this.pointDistanceFunction = pointDistanceFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public CellDistanceFunction load() {
/* 29 */     CellDistanceFunction distanceFunction = loadDistanceFunction();
/* 30 */     switch (this.measurementMode) { default: throw new MatchException(null, null);case SQUARE: case HEX: break; }  return 
/*    */       
/* 32 */       (CellDistanceFunction)(new CellBorderDistanceFunctionJsonLoader<>(this.seed, this.dataFolder, this.json, distanceFunction))
/* 33 */       .load();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CellType loadCellType() {
/* 39 */     CellType cellType = CellNoiseJsonLoader.Constants.DEFAULT_CELL_TYPE;
/* 40 */     if (has("CellType")) {
/* 41 */       cellType = CellType.valueOf(get("CellType").getAsString());
/*    */     }
/* 43 */     return cellType;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CellDistanceFunction loadDistanceFunction() {
/* 48 */     switch (loadCellType()) { default: throw new MatchException(null, null);case SQUARE: case HEX: break; }  return 
/*    */       
/* 50 */       (CellDistanceFunction)HexCellDistanceFunction.DISTANCE_FUNCTION;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CellDistanceFunctionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */