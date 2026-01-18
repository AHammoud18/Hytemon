/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.BasicHeightThresholdInterpreter;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicHeightThresholdInterpreterJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, BasicHeightThresholdInterpreter>
/*    */ {
/*    */   protected final int length;
/*    */   
/*    */   public BasicHeightThresholdInterpreterJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, int length) {
/* 20 */     super(seed.append(".BasicHeightThresholdInterpreter"), dataFolder, json);
/* 21 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BasicHeightThresholdInterpreter load() {
/* 27 */     return new BasicHeightThresholdInterpreter(
/* 28 */         loadPositions(), 
/* 29 */         loadValues(), this.length);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int[] loadPositions() {
/* 35 */     if (!has("Positions")) throw new IllegalStateException("Could not find position data in HeightThresholdInterpreter. Keyword: Positions"); 
/* 36 */     JsonArray terrainNoiseKeyPositionsJson = get("Positions").getAsJsonArray();
/* 37 */     int[] positions = new int[terrainNoiseKeyPositionsJson.size()];
/* 38 */     for (int i = 0; i < positions.length; i++) {
/* 39 */       positions[i] = terrainNoiseKeyPositionsJson.get(i).getAsInt();
/*    */     }
/* 41 */     return positions;
/*    */   }
/*    */   
/*    */   protected float[] loadValues() {
/* 45 */     if (!has("Values")) throw new IllegalStateException("Could not find value data in HeightThresholdInterpreter. Keyword: Values"); 
/* 46 */     JsonArray terrainNoiseKeyThresholdsJson = get("Values").getAsJsonArray();
/* 47 */     float[] thresholds = new float[terrainNoiseKeyThresholdsJson.size()];
/* 48 */     for (int i = 0; i < thresholds.length; i++) {
/* 49 */       thresholds[i] = terrainNoiseKeyThresholdsJson.get(i).getAsFloat();
/*    */     }
/* 51 */     return thresholds;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_POSITIONS = "Positions";
/*    */     public static final String KEY_VALUES = "Values";
/*    */     public static final String ERROR_NO_POSITIONS = "Could not find position data in HeightThresholdInterpreter. Keyword: Positions";
/*    */     public static final String ERROR_NO_VALUES = "Could not find value data in HeightThresholdInterpreter. Keyword: Values";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\BasicHeightThresholdInterpreterJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */