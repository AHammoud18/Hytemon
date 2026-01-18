/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.DistanceNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.MeasurementMode;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistanceNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public DistanceNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 23 */     super(seed.append(".DistanceNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseFunction load() {
/* 29 */     CellDistanceFunction cellDistanceFunction = loadCellDistanceFunction();
/* 30 */     PointEvaluator pointEvaluator = loadPointEvaluator();
/* 31 */     DistanceNoise.Distance2Function distance2Function = loadDistance2Function();
/* 32 */     return (NoiseFunction)new LoadedDistanceNoise(cellDistanceFunction, pointEvaluator, distance2Function, (SeedResource)this.seed.get());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected CellDistanceFunction loadCellDistanceFunction() {
/* 37 */     MeasurementMode measurementMode = loadMeasurementMode();
/* 38 */     return (new CellDistanceFunctionJsonLoader<>(this.seed, this.dataFolder, this.json, measurementMode, null))
/* 39 */       .load();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected PointEvaluator loadPointEvaluator() {
/* 44 */     return (new PointEvaluatorJsonLoader<>(this.seed, this.dataFolder, this.json))
/* 45 */       .load();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected MeasurementMode loadMeasurementMode() {
/* 50 */     if (has("Measurement")) {
/* 51 */       return MeasurementMode.valueOf(get("Measurement").getAsString());
/*    */     }
/* 53 */     return Constants.DEFAULT_MEASUREMENT;
/*    */   }
/*    */   
/*    */   protected DistanceNoise.Distance2Function loadDistance2Function() {
/* 57 */     DistanceNoise.Distance2Mode distance2Mode = Constants.DEFAULT_DISTANCE_2_MODE;
/* 58 */     if (has("Distance2Mode")) {
/* 59 */       distance2Mode = DistanceNoise.Distance2Mode.valueOf(get("Distance2Mode").getAsString());
/*    */     }
/* 61 */     return distance2Mode.getFunction();
/*    */   }
/*    */ 
/*    */   
/*    */   private static class LoadedDistanceNoise
/*    */     extends DistanceNoise
/*    */   {
/*    */     private final SeedResource seedResource;
/*    */     
/*    */     public LoadedDistanceNoise(CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator, DistanceNoise.Distance2Function distance2Function, SeedResource seedResource) {
/* 71 */       super(cellDistanceFunction, pointEvaluator, distance2Function);
/* 72 */       this.seedResource = seedResource;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 78 */       return this.seedResource.localBuffer2d();
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 84 */       return this.seedResource.localBuffer3d();
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_MEASUREMENT = "Measurement";
/*    */     public static final String KEY_DISTANCE_2_MODE = "Distance2Mode";
/* 91 */     public static final MeasurementMode DEFAULT_MEASUREMENT = MeasurementMode.CENTRE_DISTANCE;
/* 92 */     public static final DistanceNoise.Distance2Mode DEFAULT_DISTANCE_2_MODE = DistanceNoise.Distance2Mode.SUB;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\DistanceNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */