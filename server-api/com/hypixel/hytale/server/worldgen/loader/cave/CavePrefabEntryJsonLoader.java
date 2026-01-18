/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.prefab.WeightedPrefabMapJsonLoader;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CavePrefabEntryJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CavePrefabContainer.CavePrefabEntry>
/*    */ {
/*    */   private final ZoneFileContext zoneContext;
/*    */   
/*    */   public CavePrefabEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/* 28 */     super(seed.append(".CavePrefabEntry"), dataFolder, json);
/* 29 */     this.zoneContext = zoneContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CavePrefabContainer.CavePrefabEntry load() {
/* 35 */     return new CavePrefabContainer.CavePrefabEntry(
/* 36 */         loadPrefabs(), 
/* 37 */         loadConfig());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected IWeightedMap<WorldGenPrefabSupplier> loadPrefabs() {
/* 43 */     return (new WeightedPrefabMapJsonLoader(this.seed, this.dataFolder, this.json, "Prefab", "Weight"))
/* 44 */       .load();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CavePrefabContainer.CavePrefabEntry.CavePrefabConfig loadConfig() {
/* 49 */     return (new CavePrefabConfigJsonLoader(this.seed, this.dataFolder, get("Config"), this.zoneContext))
/* 50 */       .load();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_PREFAB = "Prefab";
/*    */     public static final String KEY_WEIGHT = "Weight";
/*    */     public static final String KEY_CONFIG = "Config";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CavePrefabEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */