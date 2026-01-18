/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.GridCellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.JitterPointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.NormalPointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.ConstantCellJitter;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*    */ import com.hypixel.hytale.server.worldgen.climate.DirectGrid;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ClimateGridJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateNoise.Grid> {
/*    */   public ClimateGridJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 23 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimateNoise.Grid load() {
/* 29 */     if (this.json == null) {
/* 30 */       return Constants.DEFAULT_GRID;
/*    */     }
/*    */     
/* 33 */     return new ClimateNoise.Grid(
/* 34 */         loadSeed(), 
/* 35 */         loadScale(), (CellDistanceFunction)GridCellDistanceFunction.DISTANCE_FUNCTION, 
/*    */         
/* 37 */         loadEvaluator());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int loadSeed() {
/* 42 */     int seedVal = this.seed.hashCode();
/* 43 */     if (has("Seed")) {
/* 44 */       SeedString<?> overwritten = this.seed.appendToOriginal(get("Seed").getAsString());
/* 45 */       seedVal = overwritten.hashCode();
/*    */     } 
/* 47 */     return seedVal;
/*    */   }
/*    */   
/*    */   protected double loadScale() {
/* 51 */     return mustGetNumber("Scale", Constants.DEFAULT_SCALE).doubleValue();
/*    */   }
/*    */   
/*    */   protected PointEvaluator loadEvaluator() {
/* 55 */     PointEvaluator pointEvaluator = NormalPointEvaluator.EUCLIDEAN;
/* 56 */     double jitter = mustGetNumber("Jitter", Constants.DEFAULT_JITTER).doubleValue();
/* 57 */     ConstantCellJitter biomeJitter = new ConstantCellJitter(jitter, jitter, jitter);
/* 58 */     return (PointEvaluator)new JitterPointEvaluator(pointEvaluator, (CellJitter)biomeJitter);
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_SEED = "Seed";
/*    */     public static final String KEY_SCALE = "Scale";
/*    */     public static final String KEY_JITTER = "Jitter";
/* 66 */     public static final Double DEFAULT_SCALE = Double.valueOf(1.0D);
/* 67 */     public static final Double DEFAULT_JITTER = Double.valueOf(0.8D);
/*    */     
/* 69 */     public static final ClimateNoise.Grid DEFAULT_GRID = new ClimateNoise.Grid(0, 1.0D, (CellDistanceFunction)DirectGrid.INSTANCE, NormalPointEvaluator.EUCLIDEAN);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateGridJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */