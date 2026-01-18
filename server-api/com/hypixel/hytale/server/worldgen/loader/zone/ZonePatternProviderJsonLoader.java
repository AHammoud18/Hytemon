/*     */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.PointGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.MaskProvider;
/*     */ import com.hypixel.hytale.server.worldgen.climate.ClimateColor;
/*     */ import com.hypixel.hytale.server.worldgen.climate.ClimateMaskProvider;
/*     */ import com.hypixel.hytale.server.worldgen.climate.ClimateType;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZoneColorMapping;
/*     */ import com.hypixel.hytale.server.worldgen.zone.ZonePatternProvider;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ZonePatternProviderJsonLoader
/*     */   extends JsonLoader<SeedStringResource, ZonePatternProvider>
/*     */ {
/*     */   protected final MaskProvider maskProvider;
/*     */   protected Zone[] zones;
/*  35 */   protected Map<String, Zone> zoneLookup = Map.of();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZonePatternProviderJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, MaskProvider maskProvider) {
/*  41 */     super(seed.append(".ZonePatternGenerator"), dataFolder, json);
/*  42 */     this.maskProvider = maskProvider;
/*     */   }
/*     */   
/*     */   public void setZones(Zone[] zones) {
/*  46 */     this.zones = zones;
/*  47 */     this.zoneLookup = new HashMap<>();
/*  48 */     for (Zone zone : zones) {
/*  49 */       this.zoneLookup.put(zone.name(), zone);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ZonePatternProvider load() {
/*  56 */     return new ZonePatternProvider(
/*  57 */         loadGridGenerator(), this.zones, 
/*     */         
/*  59 */         loadUniqueZoneCandidates(), this.maskProvider, 
/*     */         
/*  61 */         loadColorMapping());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected IPointGenerator loadGridGenerator() {
/*  67 */     return (new PointGeneratorJsonLoader(this.seed, this.dataFolder, get("GridGenerator")))
/*  68 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ZoneColorMapping loadColorMapping() {
/*  73 */     if (!has("MaskMapping")) throw new IllegalArgumentException("Could not find mappings for colors in mask file. Keyword: MaskMapping");
/*     */     
/*  75 */     ZoneColorMapping colorMapping = (new ZoneColorMappingJsonLoader(this.seed, this.dataFolder, get("MaskMapping"), this.zoneLookup)).load();
/*  76 */     ensureMaskIntegrity(colorMapping);
/*  77 */     return colorMapping;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> loadZoneRequirement() {
/*  82 */     return (new ZoneRequirementJsonLoader(this.seed, this.dataFolder, this.json))
/*  83 */       .load();
/*     */   }
/*     */   
/*     */   protected void ensureMaskIntegrity(@Nonnull ZoneColorMapping zoneColorMapping) {
/*  87 */     MaskProvider maskProvider = this.maskProvider; if (maskProvider instanceof ClimateMaskProvider) { ClimateMaskProvider climateMask = (ClimateMaskProvider)maskProvider;
/*  88 */       for (ClimateType parent : climateMask.getGraph().getParents()) {
/*  89 */         if (parent.children.length == 0) {
/*  90 */           validateMapping(parent, parent, parent.color, zoneColorMapping, "");
/*  91 */           validateMapping(parent, parent, parent.island, zoneColorMapping, "Island");
/*     */         } else {
/*  93 */           for (ClimateType child : parent.children) {
/*  94 */             validateMapping(parent, child, child.color, zoneColorMapping, "");
/*  95 */             validateMapping(parent, child, child.island, zoneColorMapping, "Island.");
/*     */           } 
/*     */         } 
/*     */       }  }
/*     */     else
/* 100 */     { this.maskProvider.getFuzzyZoom().getExactZoom().getDistanceProvider().getColors().forEach(rgb -> {
/*     */             if (zoneColorMapping.get(rgb) == null)
/*     */               throw new NullPointerException(Integer.toHexString(rgb)); 
/*     */           }); }
/*     */   
/*     */   }
/*     */   protected Zone.UniqueCandidate[] loadUniqueZoneCandidates() {
/* 107 */     MaskProvider maskProvider = this.maskProvider; if (maskProvider instanceof ClimateMaskProvider) { ClimateMaskProvider climateMask = (ClimateMaskProvider)maskProvider;
/* 108 */       return climateMask.getUniqueZoneCandidates(this.zoneLookup); }
/*     */ 
/*     */ 
/*     */     
/* 112 */     Zone.UniqueEntry[] uniqueZones = (new UniqueZoneEntryJsonLoader(this.seed, this.dataFolder, get("UniqueZones"), this.zoneLookup)).load();
/*     */     
/* 114 */     return this.maskProvider.generateUniqueZoneCandidates(uniqueZones, 100);
/*     */   }
/*     */   
/*     */   protected static void validateMapping(@Nullable ClimateType parent, @Nonnull ClimateType type, @Nonnull ClimateColor color, ZoneColorMapping mapping, String prefix) {
/* 118 */     if (mapping.get(color.land) == null) {
/* 119 */       throw new Error(prefix + "Color is not mapped in climate type: " + prefix);
/*     */     }
/*     */     
/* 122 */     if (mapping.get(color.shore) == null) {
/* 123 */       throw new Error(prefix + "Shore is not mapped in climate type: " + prefix);
/*     */     }
/*     */     
/* 126 */     if (mapping.get(color.ocean) == null) {
/* 127 */       throw new Error(prefix + "Ocean is not mapped in climate type: " + prefix);
/*     */     }
/*     */     
/* 130 */     if (mapping.get(color.shallowOcean) == null)
/* 131 */       throw new Error(prefix + "ShallowOcean is not mapped in climate type: " + prefix); 
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_GRID_GENERATOR = "GridGenerator";
/*     */     public static final String KEY_UNIQUE_ZONES = "UniqueZones";
/*     */     public static final String KEY_MASK_MAPPING = "MaskMapping";
/*     */     public static final String ERROR_UNMAPPED_COLOR = "Mask image contains unmapped color! #%s";
/*     */     public static final String ERROR_NO_MAPPING = "Could not find mappings for colors in mask file. Keyword: MaskMapping";
/*     */     public static final int UNIQUE_ZONE_CANDIDATE_POS_LIMIT = 100;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZonePatternProviderJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */