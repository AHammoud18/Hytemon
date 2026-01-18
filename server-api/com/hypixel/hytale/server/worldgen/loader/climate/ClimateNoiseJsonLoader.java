/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClimateNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateNoise>
/*    */ {
/*    */   public ClimateNoiseJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 20 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimateNoise load() {
/* 26 */     return new ClimateNoise(
/* 27 */         loadGrid(), 
/* 28 */         loadContinentNoise(), 
/* 29 */         loadTemperatureNoise(), 
/* 30 */         loadIntensityNoise(), 
/* 31 */         loadThresholds());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected ClimateNoise.Grid loadGrid() {
/* 36 */     return (new ClimateGridJsonLoader<>(this.seed, this.dataFolder, get("Grid")))
/* 37 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected NoiseProperty loadContinentNoise() {
/* 41 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, (JsonElement)mustGetObject("Continent", null)))
/* 42 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected NoiseProperty loadTemperatureNoise() {
/* 46 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, (JsonElement)mustGetObject("Temperature", null)))
/* 47 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected NoiseProperty loadIntensityNoise() {
/* 51 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, (JsonElement)mustGetObject("Intensity", null)))
/* 52 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateNoise.Thresholds loadThresholds() {
/* 56 */     return (new ContinentThresholdsJsonLoader<>(this.seed, this.dataFolder, (JsonElement)mustGetObject("Thresholds", null)))
/* 57 */       .load();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_NOISE = "Noise";
/*    */     public static final String KEY_GRID = "Grid";
/*    */     public static final String KEY_THRESHOLDS = "Thresholds";
/*    */     public static final String KEY_CONTINENT = "Continent";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */