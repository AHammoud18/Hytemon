/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.hypixel.hytale.procedurallib.json.CoordinateRandomizerJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRandomizer;
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateGraph;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateMaskProvider;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateNoise;
/*    */ import com.hypixel.hytale.server.worldgen.climate.UniqueClimateGenerator;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClimateMaskJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateMaskProvider>
/*    */ {
/*    */   public ClimateMaskJsonLoader(SeedString<K> seed, Path dataFolder, Path maskFile) {
/* 29 */     super(seed, dataFolder, loadMaskFileJson(maskFile));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ClimateMaskProvider load() {
/* 35 */     return new ClimateMaskProvider(
/* 36 */         loadRandomizer(), 
/* 37 */         loadClimateNoise(), 
/* 38 */         loadClimateGraph(), 
/* 39 */         loadUniqueClimateGenerator());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected ICoordinateRandomizer loadRandomizer() {
/* 44 */     if (has("Randomizer")) {
/* 45 */       return (new CoordinateRandomizerJsonLoader(this.seed, this.dataFolder, get("Randomizer")))
/* 46 */         .load();
/*    */     }
/* 48 */     return CoordinateRandomizer.EMPTY_RANDOMIZER;
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateNoise loadClimateNoise() {
/* 52 */     return (new ClimateNoiseJsonLoader<>(this.seed, this.dataFolder, (JsonElement)mustGetObject("Noise", null)))
/* 53 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateGraph loadClimateGraph() {
/* 57 */     return (new ClimateGraphJsonLoader<>(this.seed, this.dataFolder, (JsonElement)mustGetObject("Climate", null)))
/* 58 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected UniqueClimateGenerator loadUniqueClimateGenerator() {
/* 62 */     return (new UniqueClimateGeneratorJsonLoader<>(this.seed, this.dataFolder, mustGetArray("UniqueZones", Constants.DEFAULT_UNIQUE)))
/* 63 */       .load();
/*    */   }
/*    */   protected static JsonElement loadMaskFileJson(Path file) {
/*    */     
/* 67 */     try { BufferedReader reader = Files.newBufferedReader(file); 
/* 68 */       try { JsonElement jsonElement = JsonParser.parseReader(reader);
/* 69 */         if (reader != null) reader.close();  return jsonElement; } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 70 */     { throw new Error("Failed to load Mask.json", e); }
/*    */   
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_RANDOMIZER = "Randomizer";
/*    */     public static final String KEY_UNIQUE_ZONES = "UniqueZones";
/* 77 */     public static final JsonArray DEFAULT_UNIQUE = new JsonArray();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateMaskJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */