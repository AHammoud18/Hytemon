/*     */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UniqueZoneEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, Zone.UniqueEntry[]>
/*     */ {
/*     */   protected final Map<String, Zone> zoneLookup;
/*     */   
/*     */   public UniqueZoneEntryJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, @Nullable JsonElement json, Map<String, Zone> zoneLookup) {
/*  24 */     super(seed, dataFolder, json);
/*  25 */     this.zoneLookup = zoneLookup;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Zone.UniqueEntry[] load() {
/*  31 */     if (this.json == null) {
/*  32 */       return Zone.UniqueEntry.EMPTY_ARRAY;
/*     */     }
/*     */     
/*  35 */     if (!this.json.isJsonArray()) {
/*  36 */       throw new Error("Unexpected type for 'UniqueZones' field, expected array");
/*     */     }
/*     */     
/*  39 */     JsonArray arrayJson = this.json.getAsJsonArray();
/*  40 */     Zone.UniqueEntry[] entries = new Zone.UniqueEntry[arrayJson.size()];
/*     */     
/*  42 */     for (int i = 0; i < arrayJson.size(); i++) {
/*  43 */       JsonElement entry = arrayJson.get(i);
/*     */       
/*  45 */       if (!entry.isJsonObject()) {
/*  46 */         throw new Error("Unexpected type for unique zone entry: #" + i);
/*     */       }
/*     */       
/*  49 */       entries[i] = loadEntry(i, entry.getAsJsonObject());
/*     */     } 
/*     */     
/*  52 */     return entries;
/*     */   }
/*     */   
/*     */   protected Zone.UniqueEntry loadEntry(int index, JsonObject json) {
/*  56 */     JsonElement zoneJson = json.get("Zone");
/*  57 */     if (zoneJson == null) {
/*  58 */       throw new Error("Missing 'Zone' field in unique zone entry: #" + index);
/*     */     }
/*     */     
/*  61 */     JsonElement colorJson = json.get("Color");
/*  62 */     if (colorJson == null) {
/*  63 */       throw new Error("Missing 'Color' field in unique zone entry: #" + index);
/*     */     }
/*     */     
/*  66 */     JsonElement parentJson = json.get("Parent");
/*  67 */     if (parentJson == null) {
/*  68 */       throw new Error("Missing 'Parent' field in unique zone entry: #" + index);
/*     */     }
/*     */     
/*  71 */     JsonElement radiusJson = json.get("Radius");
/*  72 */     JsonElement paddingJson = json.get("Padding");
/*     */     
/*  74 */     Zone zone = this.zoneLookup.get(zoneJson.getAsString());
/*  75 */     if (zone == null) {
/*  76 */       throw new Error("Unknown zone '" + zoneJson.getAsString() + "' in unique zone entry: #" + index);
/*     */     }
/*     */     
/*  79 */     int color = ColorUtil.hexString(colorJson.getAsString());
/*  80 */     int[] parent = loadParentColors(index, parentJson);
/*  81 */     int radius = (radiusJson != null) ? radiusJson.getAsInt() : 1;
/*  82 */     int padding = (paddingJson != null) ? paddingJson.getAsInt() : 0;
/*     */     
/*  84 */     return new Zone.UniqueEntry(zone, color, parent, radius, padding);
/*     */   }
/*     */   
/*     */   protected static int[] loadParentColors(int index, JsonElement json) {
/*  88 */     if (json.isJsonArray()) {
/*  89 */       JsonArray arr = json.getAsJsonArray();
/*     */       
/*  91 */       int[] colors = new int[arr.size()];
/*  92 */       for (int i = 0; i < arr.size(); i++) {
/*  93 */         colors[i] = ColorUtil.hexString(arr.get(i).getAsString());
/*     */       }
/*     */       
/*  96 */       return colors;
/*     */     } 
/*     */     
/*  99 */     if (json.isJsonPrimitive()) {
/* 100 */       return new int[] { ColorUtil.hexString(json.getAsString()) };
/*     */     }
/*     */     
/* 103 */     throw new Error("Unexpected type for 'Parent' field in unique zone entry: #" + index);
/*     */   }
/*     */   
/*     */   public static void collectZones(Set<String> zoneSet, @Nullable JsonElement json) {
/* 107 */     if (json == null) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (!json.isJsonArray()) {
/* 112 */       throw new Error("Unexpected type for 'UniqueZones' field, expected array");
/*     */     }
/*     */     
/* 115 */     JsonArray arrayJson = json.getAsJsonArray();
/* 116 */     for (int i = 0; i < arrayJson.size(); i++) {
/* 117 */       JsonElement entry = arrayJson.get(i);
/*     */       
/* 119 */       if (!entry.isJsonObject()) {
/* 120 */         throw new Error("Unexpected type for unique zone entry: #" + i);
/*     */       }
/*     */       
/* 123 */       JsonElement zone = entry.getAsJsonObject().get("Zone");
/* 124 */       if (zone == null) {
/* 125 */         throw new Error("Missing 'Zone' field in unique zone entry: #" + i);
/*     */       }
/*     */       
/* 128 */       zoneSet.add(zone.getAsString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_ZONE = "Zone";
/*     */     public static final String KEY_COLOR = "Color";
/*     */     public static final String KEY_PARENT = "Parent";
/*     */     public static final String KEY_RADIUS = "Radius";
/*     */     public static final String KEY_PADDING = "Padding";
/*     */     public static final int DEFAULT_RADIUS = 1;
/*     */     public static final int DEFAULT_PADDING = 0;
/*     */     public static final String ERROR_ENTRIES_TYPE = "Unexpected type for 'UniqueZones' field, expected array";
/*     */     public static final String ERROR_ENTRY_TYPE = "Unexpected type for unique zone entry: #";
/*     */     public static final String ERROR_PARENT_TYPE = "Unexpected type for 'Parent' field in unique zone entry: #";
/*     */     public static final String ERROR_MISSING_ZONE = "Missing 'Zone' field in unique zone entry: #";
/*     */     public static final String ERROR_MISSING_COLOR = "Missing 'Color' field in unique zone entry: #";
/*     */     public static final String ERROR_MISSING_PARENT = "Missing 'Parent' field in unique zone entry: #";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\UniqueZoneEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */