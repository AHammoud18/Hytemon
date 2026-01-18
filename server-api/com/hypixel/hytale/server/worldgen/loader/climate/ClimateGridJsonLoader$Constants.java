/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.NormalPointEvaluator;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*    */ import com.hypixel.hytale.server.worldgen.climate.DirectGrid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Constants
/*    */ {
/*    */   public static final String KEY_SEED = "Seed";
/*    */   public static final String KEY_SCALE = "Scale";
/*    */   public static final String KEY_JITTER = "Jitter";
/* 66 */   public static final Double DEFAULT_SCALE = Double.valueOf(1.0D);
/* 67 */   public static final Double DEFAULT_JITTER = Double.valueOf(0.8D);
/*    */   
/* 69 */   public static final ClimateNoise.Grid DEFAULT_GRID = new ClimateNoise.Grid(0, 1.0D, (CellDistanceFunction)DirectGrid.INSTANCE, NormalPointEvaluator.EUCLIDEAN);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateGridJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */