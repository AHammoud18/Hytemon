/*    */ package com.hypixel.hytale.server.worldgen.loader.zone;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoneRequirementJsonLoader
/*    */   extends JsonLoader<SeedStringResource, Set<String>>
/*    */ {
/*    */   public ZoneRequirementJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 21 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<String> load() {
/* 27 */     if (!has("MaskMapping")) {
/* 28 */       throw new IllegalArgumentException("Could not find mappings for colors in mask file. Keyword: MaskMapping");
/*    */     }
/*    */     
/* 31 */     Set<String> zoneSet = new HashSet<>();
/* 32 */     ZoneColorMappingJsonLoader.collectZones(zoneSet, get("MaskMapping"));
/* 33 */     UniqueZoneEntryJsonLoader.collectZones(zoneSet, get("UniqueZones"));
/*    */     
/* 35 */     return zoneSet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\zone\ZoneRequirementJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */