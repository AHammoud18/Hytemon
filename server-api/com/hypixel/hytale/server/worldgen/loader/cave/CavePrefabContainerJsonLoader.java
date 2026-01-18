/*    */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CavePrefabContainerJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CavePrefabContainer>
/*    */ {
/*    */   private final ZoneFileContext zoneContext;
/*    */   
/*    */   public CavePrefabContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/* 24 */     super(seed.append(".CavePrefabContainer"), dataFolder, json);
/* 25 */     this.zoneContext = zoneContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CavePrefabContainer load() {
/* 31 */     return new CavePrefabContainer(
/* 32 */         loadEntries());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CavePrefabContainer.CavePrefabEntry[] loadEntries() {
/* 38 */     if (this.json == null || this.json.isJsonNull()) {
/* 39 */       return new CavePrefabContainer.CavePrefabEntry[0];
/*    */     }
/* 41 */     if (!has("Entries")) throw new IllegalArgumentException("Could not find entries in prefab container. Keyword: Entries"); 
/* 42 */     JsonArray array = get("Entries").getAsJsonArray();
/* 43 */     CavePrefabContainer.CavePrefabEntry[] entries = new CavePrefabContainer.CavePrefabEntry[array.size()];
/* 44 */     for (int i = 0; i < entries.length; i++) {
/* 45 */       entries[i] = (new CavePrefabEntryJsonLoader(this.seed.append(String.format("-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, array.get(i), this.zoneContext))
/* 46 */         .load();
/*    */     } 
/* 48 */     return entries;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_ENTRIES = "Entries";
/*    */     public static final String SEED_ENTRY_SUFFIX = "-%s";
/*    */     public static final String ERROR_NO_ENTRIES = "Could not find entries in prefab container. Keyword: Entries";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CavePrefabContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */