/*     */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.biome.BiomePatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.biome.CustomBiome;
/*     */ import com.hypixel.hytale.server.worldgen.biome.TileBiome;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.biome.BiomePatternGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.cave.CaveGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.container.UniquePrefabContainerJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZoneDiscoveryConfig;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneJsonLoader
/*     */   extends JsonLoader<SeedStringResource, Zone>
/*     */ {
/*     */   @Nonnull
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public ZoneJsonLoader(@Nonnull SeedString<SeedStringResource> seed, @Nonnull Path dataFolder, @Nonnull JsonElement json, @Nonnull ZoneFileContext zoneContext) {
/*  47 */     super(seed.append(String.format(".Zone-%s", new Object[] { zoneContext.getName() })), dataFolder, json);
/*  48 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Zone load() {
/*  54 */     return new Zone(this.zoneContext
/*  55 */         .getId(), this.zoneContext
/*  56 */         .getName(), 
/*  57 */         loadDiscoveryConfig(), 
/*  58 */         loadCaveGenerator(), 
/*  59 */         loadBiomePatternGenerator(), 
/*  60 */         loadUniquePrefabContainer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ZoneDiscoveryConfig loadDiscoveryConfig() {
/*  71 */     JsonElement discoveryElement = get("Discovery");
/*  72 */     if (discoveryElement == null || !discoveryElement.isJsonObject()) {
/*  73 */       return ZoneDiscoveryConfig.DEFAULT;
/*     */     }
/*     */     
/*  76 */     JsonObject discoveryObject = discoveryElement.getAsJsonObject();
/*     */     
/*  78 */     Boolean display = null;
/*  79 */     JsonElement displayElement = discoveryObject.get("Display");
/*  80 */     if (displayElement != null && !displayElement.isJsonNull()) {
/*  81 */       display = Boolean.valueOf(displayElement.getAsBoolean());
/*     */     }
/*     */     
/*  84 */     String zoneName = null;
/*  85 */     JsonElement zoneNameElement = discoveryObject.get("ZoneName");
/*  86 */     if (zoneNameElement != null && !zoneNameElement.isJsonNull()) {
/*  87 */       zoneName = zoneNameElement.getAsString();
/*     */     }
/*     */     
/*  90 */     String soundEventId = null;
/*  91 */     JsonElement soundElement = discoveryObject.get("SoundEventId");
/*  92 */     if (soundElement != null && !soundElement.isJsonNull()) {
/*  93 */       soundEventId = soundElement.getAsString();
/*     */     }
/*     */     
/*  96 */     String icon = null;
/*  97 */     JsonElement iconElement = discoveryObject.get("Icon");
/*  98 */     if (iconElement != null && !iconElement.isJsonNull()) {
/*  99 */       icon = iconElement.getAsString();
/*     */     }
/*     */     
/* 102 */     Boolean major = null;
/* 103 */     JsonElement majorElement = discoveryObject.get("Major");
/* 104 */     if (majorElement != null && !majorElement.isJsonNull()) {
/* 105 */       major = Boolean.valueOf(majorElement.getAsBoolean());
/*     */     }
/*     */     
/* 108 */     Float duration = null;
/* 109 */     JsonElement durationElement = discoveryObject.get("Duration");
/* 110 */     if (durationElement != null && !durationElement.isJsonNull()) {
/* 111 */       duration = Float.valueOf(durationElement.getAsFloat());
/*     */     }
/*     */     
/* 114 */     Float fadeInDuration = null;
/* 115 */     JsonElement fadeInElement = discoveryObject.get("FadeInDuration");
/* 116 */     if (fadeInElement != null && !fadeInElement.isJsonNull()) {
/* 117 */       fadeInDuration = Float.valueOf(fadeInElement.getAsFloat());
/*     */     }
/*     */     
/* 120 */     Float fadeOutDuration = null;
/* 121 */     JsonElement fadeOutElement = discoveryObject.get("FadeOutDuration");
/* 122 */     if (fadeOutElement != null && !fadeOutElement.isJsonNull()) {
/* 123 */       fadeOutDuration = Float.valueOf(fadeOutElement.getAsFloat());
/*     */     }
/*     */     
/* 126 */     return ZoneDiscoveryConfig.of(display, zoneName, soundEventId, icon, major, duration, fadeInDuration, fadeOutDuration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected BiomePatternGenerator loadBiomePatternGenerator() {
/* 136 */     IWeightedMap<TileBiome> tileBiomes = loadBiomes();
/* 137 */     TileBiome[] biomes = (TileBiome[])tileBiomes.toArray();
/* 138 */     CustomBiome[] customBiomes = loadCustomBiomes((Biome[])biomes);
/*     */     try {
/* 140 */       return (new BiomePatternGeneratorJsonLoader(this.seed, this.dataFolder, get("BiomeGenerator"), tileBiomes, customBiomes))
/*     */         
/* 142 */         .load();
/* 143 */     } catch (Throwable e) {
/* 144 */       throw new Error("Error while loading biome generator.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected IWeightedMap<TileBiome> loadBiomes() {
/*     */     try {
/* 156 */       return (new ZoneBiomesJsonLoader(this.seed, this.dataFolder, get("BiomeGenerator"), this.zoneContext))
/* 157 */         .load();
/* 158 */     } catch (Throwable e) {
/* 159 */       throw new Error("Error while loading tile biomes.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CustomBiome[] loadCustomBiomes(@Nonnull Biome[] tileBiomes) {
/*     */     try {
/* 172 */       return (new ZoneCustomBiomesJsonLoader(this.seed, this.dataFolder, get("BiomeGenerator"), this.zoneContext, tileBiomes))
/* 173 */         .load();
/* 174 */     } catch (Throwable e) {
/* 175 */       throw new Error("Error while loading custom biomes.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected CaveGenerator loadCaveGenerator() {
/*     */     try {
/* 187 */       return (new CaveGeneratorJsonLoader(this.seed, this.dataFolder, this.json, this.zoneContext.getPath().resolve("Cave"), this.zoneContext))
/* 188 */         .load();
/* 189 */     } catch (Throwable e) {
/* 190 */       throw new Error("Error while loading cave generator.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected UniquePrefabContainer loadUniquePrefabContainer() {
/*     */     try {
/* 202 */       return (new UniquePrefabContainerJsonLoader(this.seed, this.dataFolder, get("UniquePrefabs"), this.zoneContext))
/* 203 */         .load();
/* 204 */     } catch (Throwable e) {
/* 205 */       throw new Error("Error while loading unique prefabs.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_BIOME_GENERATOR = "BiomeGenerator";
/*     */     public static final String KEY_UNIQUE_PREFABS = "UniquePrefabs";
/*     */     public static final String KEY_DISCOVERY = "Discovery";
/*     */     public static final String KEY_DISCOVERY_DISPLAY = "Display";
/*     */     public static final String KEY_DISCOVERY_ZONE = "ZoneName";
/*     */     public static final String KEY_DISCOVERY_SOUND_EVENT_ID = "SoundEventId";
/*     */     public static final String KEY_DISCOVERY_ICON = "Icon";
/*     */     public static final String KEY_DISCOVERY_MAJOR = "Major";
/*     */     public static final String KEY_DISCOVERY_DURATION = "Duration";
/*     */     public static final String KEY_DISCOVERY_FADE_IN_DURATION = "FadeInDuration";
/*     */     public static final String KEY_DISCOVERY_FADE_OUT_DURATION = "FadeOutDuration";
/*     */     public static final String PATH_CAVE = "Cave";
/*     */     public static final String SEED_ZONE_SUFFIX = ".Zone-%s";
/*     */     public static final String ERROR_BIOME_GENERATOR = "Error while loading biome generator.";
/*     */     public static final String ERROR_TILE_BIOMES = "Error while loading tile biomes.";
/*     */     public static final String ERROR_CUSTOM_BIOMES = "Error while loading custom biomes.";
/*     */     public static final String ERROR_CAVE_GENERATOR = "Error while loading cave generator.";
/*     */     public static final String ERROR_UNIQUE_PREFABS = "Error while loading unique prefabs.";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZoneJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */