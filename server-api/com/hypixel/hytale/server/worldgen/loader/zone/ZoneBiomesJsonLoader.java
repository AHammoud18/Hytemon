/*    */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.TileBiome;
/*    */ import com.hypixel.hytale.server.worldgen.loader.biome.TileBiomeJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoneBiomesJsonLoader
/*    */   extends JsonLoader<SeedStringResource, IWeightedMap<TileBiome>>
/*    */ {
/*    */   protected final ZoneFileContext zoneContext;
/*    */   
/*    */   public ZoneBiomesJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zone) {
/* 31 */     super(seed, dataFolder, json);
/* 32 */     this.zoneContext = zone;
/*    */   }
/*    */ 
/*    */   
/*    */   public IWeightedMap<TileBiome> load() {
/* 37 */     WeightedMap.Builder<TileBiome> builder = WeightedMap.builder((Object[])TileBiome.EMPTY_ARRAY);
/* 38 */     for (Map.Entry<String, BiomeFileContext> biomeEntry : (Iterable<Map.Entry<String, BiomeFileContext>>)this.zoneContext.getTileBiomes()) {
/* 39 */       TileBiome biome = loadBiome(biomeEntry.getValue());
/* 40 */       builder.put(biome, biome.getWeight());
/*    */     } 
/* 42 */     if (builder.size() <= 0) throw new IllegalArgumentException("Could not find any tile biomes for this zone!"); 
/* 43 */     return builder.build();
/*    */   }
/*    */   @Nonnull
/*    */   protected TileBiome loadBiome(@Nonnull BiomeFileContext biomeContext) {
/*    */     
/* 48 */     try { JsonReader reader = new JsonReader(Files.newBufferedReader(biomeContext.getPath())); 
/* 49 */       try { JsonElement biomeJson = JsonParser.parseReader(reader);
/*    */         
/* 51 */         TileBiome tileBiome = (new TileBiomeJsonLoader(this.seed, this.dataFolder, biomeJson, biomeContext)).load();
/* 52 */         reader.close(); return tileBiome; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 53 */     { throw new Error(String.format("Error while loading tile biome \"%s\" from \"%s\"", new Object[] { biomeContext.getName(), biomeContext.getPath().toString() }), e); }
/*    */   
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_BIOME_FILES_NULL = "Biome files error occured.";
/*    */     public static final String ERROR_BIOME_FAILED = "Error while loading tile biome \"%s\" from \"%s\"";
/*    */     public static final String ERROR_NO_TILE_BIOMES = "Could not find any tile biomes for this zone!";
/*    */     public static final String FILE_TILE_PREFIX = "Tile.";
/*    */     public static final String FILE_TILE_SUFFIX = ".json";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZoneBiomesJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */