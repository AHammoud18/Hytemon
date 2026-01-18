/*    */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.biome.CustomBiome;
/*    */ import com.hypixel.hytale.server.worldgen.biome.CustomBiomeGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.loader.biome.CustomBiomeJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ZoneCustomBiomesJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CustomBiome[]> {
/*    */   private static final Comparator<CustomBiome> PRIORITY_SORTER;
/*    */   protected final ZoneFileContext zoneContext;
/*    */   protected final Biome[] tileBiomes;
/*    */   
/*    */   static {
/* 29 */     PRIORITY_SORTER = ((o1, o2) -> Integer.compare(o2.getCustomBiomeGenerator().getPriority(), o1.getCustomBiomeGenerator().getPriority()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ZoneCustomBiomesJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext, Biome[] tileBiomes) {
/* 34 */     super(seed, dataFolder, json);
/* 35 */     this.zoneContext = zoneContext;
/* 36 */     this.tileBiomes = tileBiomes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CustomBiome[] load() {
/* 42 */     int index = 0;
/* 43 */     CustomBiome[] biomes = new CustomBiome[this.zoneContext.getCustomBiomes().size()];
/*    */     
/* 45 */     for (Map.Entry<String, BiomeFileContext> biomeEntry : (Iterable<Map.Entry<String, BiomeFileContext>>)this.zoneContext.getCustomBiomes()) {
/* 46 */       BiomeFileContext biomeContext = biomeEntry.getValue(); 
/* 47 */       try { JsonReader reader = new JsonReader(Files.newBufferedReader(biomeContext.getPath())); 
/* 48 */         try { JsonElement biomeJson = JsonParser.parseReader(reader);
/*    */           
/* 50 */           CustomBiome biome = (new CustomBiomeJsonLoader(this.seed, this.dataFolder, biomeJson, biomeContext, this.tileBiomes)).load();
/*    */           
/* 52 */           CustomBiomeGenerator reference = biome.getCustomBiomeGenerator();
/* 53 */           if (reference == null) throw new NullPointerException(biomeContext.getPath().toAbsolutePath().toString());
/*    */           
/* 55 */           biomes[index++] = biome;
/* 56 */           reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 57 */       { throw new Error(String.format("Error while loading custom biome \"%s\" from \"%s\"", new Object[] { biomeContext.getName(), biomeContext.getPath().toString() }), e); }
/*    */     
/*    */     } 
/*    */     
/* 61 */     Arrays.sort(biomes, PRIORITY_SORTER);
/* 62 */     return biomes;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_BIOME_FILES_NULL = "Biome files error occured.";
/*    */     public static final String ERROR_BIOME_FAILED = "Error while loading custom biome \"%s\" from \"%s\"";
/*    */     public static final String ERROR_NO_CUSTOM_GENERATOR = "Could not find custom biome generator for custom biome \"%s\" at \"%s\"";
/*    */     public static final String FILE_CUSTOM_PREFIX = "Custom.";
/*    */     public static final String FILE_CUSTOM_SUFFIX = ".json";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZoneCustomBiomesJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */