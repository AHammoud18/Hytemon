/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.DistanceCalculationMode;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.DistortedPointGenerator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.OffsetPointGenerator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointGenerator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.ScaledPointGenerator;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PointGeneratorJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, IPointGenerator>
/*    */ {
/*    */   public PointGeneratorJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 26 */     super(seed, dataFolder, json); } public IPointGenerator load() {
/*    */     ScaledPointGenerator scaledPointGenerator;
/*    */     DistortedPointGenerator distortedPointGenerator2;
/*    */     OffsetPointGenerator offsetPointGenerator;
/*    */     DistortedPointGenerator distortedPointGenerator1;
/* 31 */     PointGenerator pointGenerator = newPointGenerator(loadSeed(), loadCellDistanceFunction());
/* 32 */     PointGenerator pointGenerator1 = pointGenerator;
/* 33 */     if (has("Scale")) {
/* 34 */       scaledPointGenerator = new ScaledPointGenerator(pointGenerator, get("Scale").getAsDouble());
/*    */     }
/* 36 */     if (has("Randomizer"))
/*    */     {
/* 38 */       distortedPointGenerator2 = new DistortedPointGenerator((IPointGenerator)scaledPointGenerator, (new CoordinateRandomizerJsonLoader<>(this.seed, this.dataFolder, get("Randomizer"))).load());
/*    */     }
/* 40 */     double offsetX = Double.NEGATIVE_INFINITY, offsetY = Double.NEGATIVE_INFINITY, offsetZ = Double.NEGATIVE_INFINITY;
/* 41 */     if (has("Offset")) offsetX = offsetY = offsetZ = get("Offset").getAsDouble(); 
/* 42 */     if (has("OffsetX")) offsetX = get("OffsetX").getAsDouble(); 
/* 43 */     if (has("OffsetY")) offsetY = get("OffsetY").getAsDouble(); 
/* 44 */     if (has("OffsetZ")) offsetZ = get("OffsetZ").getAsDouble(); 
/* 45 */     if (offsetX != Double.NEGATIVE_INFINITY || offsetY != Double.NEGATIVE_INFINITY || offsetZ != Double.NEGATIVE_INFINITY) {
/* 46 */       if (offsetX == Double.NEGATIVE_INFINITY) offsetX = 0.0D; 
/* 47 */       if (offsetY == Double.NEGATIVE_INFINITY) offsetY = 0.0D; 
/* 48 */       if (offsetZ == Double.NEGATIVE_INFINITY) offsetZ = 0.0D; 
/* 49 */       offsetPointGenerator = new OffsetPointGenerator((IPointGenerator)distortedPointGenerator2, offsetX, offsetY, offsetZ);
/*    */     } 
/* 51 */     if (has("Rotate")) {
/*    */       
/* 53 */       CoordinateRotator rotation = (new CoordinateRotatorJsonLoader<>(this.seed, this.dataFolder, get("Rotate"))).load();
/*    */       
/* 55 */       if (rotation != CoordinateRotator.NONE) distortedPointGenerator1 = new DistortedPointGenerator((IPointGenerator)offsetPointGenerator, (ICoordinateRandomizer)rotation); 
/*    */     } 
/* 57 */     return (IPointGenerator)distortedPointGenerator1;
/*    */   }
/*    */   
/*    */   protected int loadSeed() {
/* 61 */     int seedVal = this.seed.hashCode();
/* 62 */     if (has("Seed")) {
/* 63 */       SeedString<?> overwritten = this.seed.appendToOriginal(get("Seed").getAsString());
/* 64 */       seedVal = overwritten.hashCode();
/* 65 */       this.seed.get().reportSeeds(seedVal, this.seed.original, this.seed.seed, overwritten.seed);
/*    */     } else {
/* 67 */       this.seed.get().reportSeeds(seedVal, this.seed.original, this.seed.seed, null);
/*    */     } 
/* 69 */     return seedVal;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected PointGenerator newPointGenerator(int seedOffset, CellDistanceFunction cellDistanceFunction) {
/* 74 */     K seedResource = this.seed.get();
/* 75 */     PointEvaluator pointEvaluator = loadPointEvaluator();
/* 76 */     return new SeedResourcePointGenerator(seedOffset, cellDistanceFunction, pointEvaluator, (SeedResource)seedResource);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected CellDistanceFunction loadCellDistanceFunction() {
/* 81 */     return (new CellDistanceFunctionJsonLoader<>(this.seed, this.dataFolder, this.json, loadPointDistanceFunction()))
/* 82 */       .load();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected PointEvaluator loadPointEvaluator() {
/* 87 */     return (new PointEvaluatorJsonLoader<>(this.seed, this.dataFolder, this.json))
/* 88 */       .load();
/*    */   }
/*    */   
/*    */   protected PointDistanceFunction loadPointDistanceFunction() {
/* 92 */     DistanceCalculationMode distanceCalculationMode = CellNoiseJsonLoader.Constants.DEFAULT_DISTANCE_MODE;
/* 93 */     if (has("DistanceMode")) {
/* 94 */       distanceCalculationMode = DistanceCalculationMode.valueOf(get("DistanceMode").getAsString());
/*    */     }
/* 96 */     return distanceCalculationMode.getFunction();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_SEED = "Seed";
/*    */     public static final String KEY_SCALE = "Scale";
/*    */     public static final String KEY_RANDOMIZER = "Randomizer";
/*    */     public static final String KEY_OFFSET = "Offset";
/*    */     public static final String KEY_OFFSET_X = "OffsetX";
/*    */     public static final String KEY_OFFSET_Y = "OffsetY";
/*    */     public static final String KEY_OFFSET_Z = "OffsetZ";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\PointGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */