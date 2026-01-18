/*    */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import com.hypixel.hytale.server.worldgen.zone.ZoneColorMapping;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class ZoneColorMappingJsonLoader
/*    */   extends JsonLoader<SeedStringResource, ZoneColorMapping>
/*    */ {
/*    */   protected final Map<String, Zone> zoneLookup;
/*    */   
/*    */   public ZoneColorMappingJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, Map<String, Zone> zoneLookup) {
/* 26 */     super(seed, dataFolder, json);
/* 27 */     this.zoneLookup = zoneLookup;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ZoneColorMapping load() {
/* 33 */     ZoneColorMapping colorMapping = new ZoneColorMapping();
/* 34 */     JsonObject mappingObj = this.json.getAsJsonObject();
/* 35 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)mappingObj.entrySet()) {
/* 36 */       int rgb = ColorUtil.hexString(entry.getKey());
/* 37 */       if (((JsonElement)entry.getValue()).isJsonArray()) {
/* 38 */         JsonArray arr = ((JsonElement)entry.getValue()).getAsJsonArray();
/* 39 */         Zone[] zoneArr = new Zone[arr.size()];
/* 40 */         for (int i = 0; i < zoneArr.length; i++) {
/* 41 */           String str = arr.get(i).getAsString();
/* 42 */           Zone zone1 = this.zoneLookup.get(str);
/* 43 */           if (zone1 == null) throw new IllegalArgumentException(String.format("Zone with name %s was not found for color %s!", new Object[] { str, entry.getKey() })); 
/* 44 */           Objects.requireNonNull(zone1);
/* 45 */           zoneArr[i] = zone1;
/*    */         } 
/* 47 */         colorMapping.add(rgb, zoneArr); continue;
/*    */       } 
/* 49 */       String zoneName = ((JsonElement)entry.getValue()).getAsString();
/* 50 */       Zone zone = this.zoneLookup.get(zoneName);
/* 51 */       if (zone == null) throw new IllegalArgumentException(String.format("Zone with name %s was not found for color %s!", new Object[] { zoneName, entry.getKey() })); 
/* 52 */       colorMapping.add(rgb, zone);
/*    */     } 
/*    */     
/* 55 */     return colorMapping;
/*    */   }
/*    */   
/*    */   public static void collectZones(Set<String> zoneSet, @Nullable JsonElement json) {
/* 59 */     if (json == null || !json.isJsonObject()) {
/*    */       return;
/*    */     }
/*    */     
/* 63 */     JsonObject mappingObj = json.getAsJsonObject();
/* 64 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)mappingObj.entrySet()) {
/* 65 */       if (((JsonElement)entry.getValue()).isJsonArray()) {
/* 66 */         for (JsonElement zoneName : ((JsonElement)entry.getValue()).getAsJsonArray())
/* 67 */           zoneSet.add(zoneName.getAsString()); 
/*    */         continue;
/*    */       } 
/* 70 */       zoneSet.add(((JsonElement)entry.getValue()).getAsString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZoneColorMappingJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */