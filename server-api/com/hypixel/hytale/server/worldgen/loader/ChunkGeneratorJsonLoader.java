/*     */ package com.hypixel.hytale.server.worldgen.loader;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.procedurallib.json.Loader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*     */ import com.hypixel.hytale.server.worldgen.loader.climate.ClimateMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileContextLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.zone.ZonePatternProviderJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabStoreRoot;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkGeneratorJsonLoader
/*     */   extends Loader<SeedStringResource, ChunkGenerator>
/*     */ {
/*     */   public ChunkGeneratorJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder) {
/*  38 */     super(seed, dataFolder);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ChunkGenerator load() {
/*  44 */     Path worldFile = this.dataFolder.resolve("World.json").toAbsolutePath();
/*  45 */     if (!Files.exists(worldFile, new java.nio.file.LinkOption[0])) throw new IllegalArgumentException(String.valueOf(worldFile)); 
/*  46 */     if (!Files.isReadable(worldFile)) throw new IllegalArgumentException(String.valueOf(worldFile)); 
/*  47 */     JsonObject worldJson = loadWorldJson(worldFile);
/*     */     
/*  49 */     Vector2i worldSize = loadWorldSize(worldJson), worldOffset = loadWorldOffset(worldJson);
/*  50 */     MaskProvider maskProvider = loadMaskProvider(worldJson, worldSize, worldOffset);
/*  51 */     PrefabStoreRoot prefabStore = loadPrefabStore(worldJson);
/*  52 */     Path overrideDataFolder = loadOverrideDataFolderPath(worldJson, this.dataFolder);
/*     */     
/*  54 */     ((SeedStringResource)this.seed.get()).setPrefabStore(prefabStore);
/*  55 */     ((SeedStringResource)this.seed.get()).setDataFolder(overrideDataFolder);
/*     */     
/*  57 */     ZonePatternProviderJsonLoader loader = loadZonePatternGenerator(maskProvider);
/*     */     
/*  59 */     FileLoadingContext loadingContext = (new FileContextLoader(overrideDataFolder, loader.loadZoneRequirement())).load();
/*     */     
/*  61 */     Zone[] zones = (new ZonesJsonLoader(this.seed, overrideDataFolder, loadingContext)).load();
/*  62 */     loader.setZones(zones);
/*  63 */     return new ChunkGenerator(loader.load(), overrideDataFolder);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Path loadOverrideDataFolderPath(@Nonnull JsonObject worldJson, @Nonnull Path dataFolder) {
/*  68 */     if (worldJson.has("OverrideDataFolder")) {
/*  69 */       Path overrideFolder = dataFolder.resolve(worldJson.get("OverrideDataFolder").getAsString()).normalize();
/*  70 */       Path parent = dataFolder.getParent();
/*     */       
/*  72 */       if (!overrideFolder.startsWith(parent) || !Files.exists(overrideFolder, new java.nio.file.LinkOption[0])) {
/*  73 */         throw new Error(String.format("Override folder '%s' must exist within: '%s'", new Object[] { overrideFolder.getFileName(), parent }));
/*     */       }
/*     */       
/*  76 */       return overrideFolder;
/*     */     } 
/*  78 */     return dataFolder;
/*     */   }
/*     */   @Nonnull
/*     */   protected JsonObject loadWorldJson(@Nonnull Path file) {
/*     */     JsonObject worldJson;
/*     */     
/*  84 */     try { JsonReader reader = new JsonReader(Files.newBufferedReader(file)); 
/*  85 */       try { worldJson = JsonParser.parseReader(reader).getAsJsonObject();
/*  86 */         reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/*  87 */     { throw new Error(String.format("Could not read JSON configuration for world. File: %s", new Object[] { file }), e); }
/*     */     
/*  89 */     return worldJson;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector2i loadWorldSize(@Nonnull JsonObject worldJson) {
/*  94 */     int width = 0, height = 0;
/*  95 */     if (worldJson.has("Width")) {
/*  96 */       width = worldJson.get("Width").getAsInt();
/*     */     }
/*  98 */     if (worldJson.has("Height")) {
/*  99 */       height = worldJson.get("Height").getAsInt();
/*     */     }
/* 101 */     return new Vector2i(width, height);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector2i loadWorldOffset(@Nonnull JsonObject worldJson) {
/* 106 */     int offsetX = 0, offsetY = 0;
/* 107 */     if (worldJson.has("OffsetX")) {
/* 108 */       offsetX = worldJson.get("OffsetX").getAsInt();
/*     */     }
/* 110 */     if (worldJson.has("OffsetY")) {
/* 111 */       offsetY = worldJson.get("OffsetY").getAsInt();
/*     */     }
/* 113 */     return new Vector2i(offsetX, offsetY);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected MaskProvider loadMaskProvider(@Nonnull JsonObject worldJson, Vector2i worldSize, Vector2i worldOffset) {
/* 118 */     WeightedMap.Builder<String> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_STRING_ARRAY);
/* 119 */     JsonElement masks = worldJson.get("Masks");
/* 120 */     if (masks == null) {
/* 121 */       builder.put("Mask.png", 1.0D);
/* 122 */     } else if (masks.isJsonPrimitive()) {
/* 123 */       builder.put(masks.getAsString(), 1.0D);
/* 124 */     } else if (masks.isJsonArray()) {
/* 125 */       JsonArray arr = masks.getAsJsonArray();
/* 126 */       if (arr.isEmpty()) {
/* 127 */         builder.put("Mask.png", 1.0D);
/*     */       } else {
/* 129 */         for (int i = 0; i < arr.size(); i++) {
/* 130 */           builder.put(arr.get(i).getAsString(), 1.0D);
/*     */         }
/*     */       } 
/* 133 */     } else if (masks.isJsonObject()) {
/* 134 */       JsonObject obj = masks.getAsJsonObject();
/* 135 */       if (obj.size() == 0) {
/* 136 */         builder.put("Mask.png", 1.0D);
/*     */       } else {
/* 138 */         for (String key : obj.keySet()) {
/* 139 */           builder.put(key, obj.get(key).getAsDouble());
/*     */         }
/*     */       } 
/*     */     } 
/* 143 */     IWeightedMap<String> weightedMap = builder.build();
/* 144 */     Path maskFile = this.dataFolder.resolve((String)weightedMap.get((Random)new FastRandom(this.seed.hashCode())));
/* 145 */     if (maskFile.getFileName().endsWith("Mask.json")) {
/* 146 */       return (MaskProvider)(new ClimateMaskJsonLoader(this.seed, this.dataFolder, maskFile))
/* 147 */         .load();
/*     */     }
/*     */     
/* 150 */     return (new MaskProviderJsonLoader(this.seed, this.dataFolder, worldJson.get("Randomizer"), maskFile, worldSize, worldOffset))
/* 151 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected PrefabStoreRoot loadPrefabStore(@Nonnull JsonObject worldJson) {
/* 156 */     if (worldJson.has("PrefabStore")) {
/* 157 */       JsonElement storeJson = worldJson.get("PrefabStore");
/* 158 */       if (!storeJson.isJsonPrimitive() || !storeJson.getAsJsonPrimitive().isString()) {
/* 159 */         throw new Error("Expected 'PrefabStore' to be a string");
/*     */       }
/*     */       
/* 162 */       String store = storeJson.getAsString();
/*     */       try {
/* 164 */         return PrefabStoreRoot.valueOf(store);
/* 165 */       } catch (IllegalArgumentException e) {
/* 166 */         throw new Error("Invalid PrefabStore name: " + store, e);
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     return PrefabStoreRoot.DEFAULT;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ZonePatternProviderJsonLoader loadZonePatternGenerator(MaskProvider maskProvider) {
/* 175 */     Path zoneFile = this.dataFolder.resolve("Zones.json"); 
/* 176 */     try { JsonReader reader = new JsonReader(Files.newBufferedReader(zoneFile)); 
/* 177 */       try { JsonObject zoneJson = JsonParser.parseReader(reader).getAsJsonObject();
/* 178 */         ZonePatternProviderJsonLoader zonePatternProviderJsonLoader = new ZonePatternProviderJsonLoader(this.seed, this.dataFolder, (JsonElement)zoneJson, maskProvider);
/* 179 */         reader.close(); return zonePatternProviderJsonLoader; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 180 */     { throw new Error(String.format("Failed to read zone configuration file! File: %s", new Object[] { zoneFile.toString() }), e); }
/*     */   
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_WIDTH = "Width";
/*     */     public static final String KEY_HEIGHT = "Height";
/*     */     public static final String KEY_OFFSET_X = "OffsetX";
/*     */     public static final String KEY_OFFSET_Y = "OffsetY";
/*     */     public static final String KEY_RANDOMIZER = "Randomizer";
/*     */     public static final String KEY_MASKS = "Masks";
/*     */     public static final String KEY_PREFAB_STORE = "PrefabStore";
/*     */     public static final String OVERRIDE_DATA_FOLDER = "OverrideDataFolder";
/*     */     public static final String FILE_WORLD_JSON = "World.json";
/*     */     public static final String FILE_ZONES_JSON = "Zones.json";
/*     */     public static final String FILE_MASK_JSON = "Mask.json";
/*     */     public static final String FILE_MASK_PNG = "Mask.png";
/*     */     public static final String ERROR_WORLD_FILE_EXIST = "World configuration file does NOT exist! File not found: %s";
/*     */     public static final String ERROR_WORLD_FILE_READ = "World configuration file is NOT readable! File: %s";
/*     */     public static final String ERROR_WORLD_JSON_CORRUPT = "Could not read JSON configuration for world. File: %s";
/*     */     public static final String ERROR_ZONE_FILE = "Failed to read zone configuration file! File: %s";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\ChunkGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */