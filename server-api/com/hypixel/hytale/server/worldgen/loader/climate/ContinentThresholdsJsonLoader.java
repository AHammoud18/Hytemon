/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ContinentThresholdsJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateNoise.Thresholds>
/*    */ {
/*    */   public ContinentThresholdsJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 16 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimateNoise.Thresholds load() {
/* 22 */     return new ClimateNoise.Thresholds(
/* 23 */         loadLandThreshold(), 
/* 24 */         loadIslandThreshold(), 
/* 25 */         loadBeachSize(), 
/* 26 */         loadShallowOceanSize());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadLandThreshold() {
/* 31 */     return mustGetNumber("Land", Constants.DEFAULT_LAND).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadIslandThreshold() {
/* 35 */     return mustGetNumber("Island", Constants.DEFAULT_ISLAND).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadBeachSize() {
/* 39 */     return mustGetNumber("BeachSize", Constants.DEFAULT_BEACH_SIZE).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadShallowOceanSize() {
/* 43 */     return mustGetNumber("ShallowOceanSize", Constants.DEFAULT_SHALLOW_OCEAN_SIZE).doubleValue();
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_LAND = "Land";
/*    */     public static final String KEY_ISLAND = "Island";
/*    */     public static final String KEY_BEACH_SIZE = "BeachSize";
/*    */     public static final String KEY_SHALLOW_OCEAN_SIZE = "ShallowOceanSize";
/* 52 */     public static final Double DEFAULT_LAND = Double.valueOf(0.5D);
/* 53 */     public static final Double DEFAULT_ISLAND = Double.valueOf(0.8D);
/* 54 */     public static final Double DEFAULT_BEACH_SIZE = Double.valueOf(0.05D);
/* 55 */     public static final Double DEFAULT_SHALLOW_OCEAN_SIZE = Double.valueOf(0.15D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ContinentThresholdsJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */