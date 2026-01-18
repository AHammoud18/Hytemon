/*    */ package com.hypixel.hytale.server.worldgen.loader.container;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.container.PrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*    */ import com.hypixel.hytale.server.worldgen.loader.prefab.PrefabPatternGeneratorJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.prefab.WeightedPrefabMapJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPatternGenerator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabContainerJsonLoader
/*    */   extends JsonLoader<SeedStringResource, PrefabContainer>
/*    */ {
/*    */   private final FileLoadingContext context;
/*    */   
/*    */   public PrefabContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, FileLoadingContext context) {
/* 33 */     super(seed.append(".PrefabContainer"), dataFolder, json);
/* 34 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PrefabContainer load() {
/* 40 */     return new PrefabContainer(
/* 41 */         loadEntries());
/*    */   }
/*    */   public static interface Constants {
/*    */     public static final String KEY_ENTRIES = "Entries"; public static final String KEY_ENTRY_PREFAB = "Prefab"; public static final String KEY_ENTRY_WEIGHT = "Weight"; public static final String KEY_ENTRY_PATTERN = "Pattern"; public static final String KEY_ENVIRONMENT = "Environment"; public static final String ERROR_FAIL_ENTRY = "Failed to load prefab container entry #%s."; public static final String ERROR_LOADING_ENVIRONMENT = "Error while looking up environment \"%s\"!"; public static final String ERROR_ENTRY_NO_PATTERN = "Could not find prefab pattern. Keyword: Pattern"; }
/*    */   @Nonnull
/*    */   protected PrefabContainer.PrefabContainerEntry[] loadEntries() {
/* 47 */     if (has("Entries")) {
/* 48 */       JsonArray entryArray = get("Entries").getAsJsonArray();
/* 49 */       PrefabContainer.PrefabContainerEntry[] entries = new PrefabContainer.PrefabContainerEntry[entryArray.size()];
/* 50 */       for (int i = 0; i < entries.length; i++) {
/*    */         try {
/* 52 */           entries[i] = (new PrefabContainerEntryJsonLoader(this.seed.append("-" + i), this.dataFolder, entryArray.get(i), this.context))
/* 53 */             .load();
/* 54 */         } catch (Throwable e) {
/* 55 */           throw new Error(String.format("Failed to load prefab container entry #%s.", new Object[] { Integer.valueOf(i) }), e);
/*    */         } 
/*    */       } 
/* 58 */       return entries;
/*    */     } 
/* 60 */     return new PrefabContainer.PrefabContainerEntry[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public static class PrefabContainerEntryJsonLoader
/*    */     extends JsonLoader<SeedStringResource, PrefabContainer.PrefabContainerEntry>
/*    */   {
/*    */     public PrefabContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, FileLoadingContext context) {
/* 68 */       super(seed.append(".PrefabContainerEntry"), dataFolder, json);
/* 69 */       this.context = context;
/*    */     }
/*    */     
/*    */     private final FileLoadingContext context;
/*    */     
/*    */     @Nonnull
/*    */     public PrefabContainer.PrefabContainerEntry load() {
/* 76 */       IWeightedMap<WorldGenPrefabSupplier> prefabs = (new WeightedPrefabMapJsonLoader(this.seed, this.dataFolder, this.json, "Prefab", "Weight")).load();
/*    */       
/* 78 */       if (!has("Pattern")) throw new IllegalArgumentException("Could not find prefab pattern. Keyword: Pattern");
/*    */       
/* 80 */       PrefabPatternGenerator prefabPatternGenerator = (new PrefabPatternGeneratorJsonLoader(this.seed, this.dataFolder, get("Pattern"), this.context)).load();
/*    */       
/* 82 */       return new PrefabContainer.PrefabContainerEntry(prefabs, prefabPatternGenerator, loadEnvironment());
/*    */     }
/*    */     
/*    */     protected int loadEnvironment() {
/* 86 */       int environment = Integer.MIN_VALUE;
/* 87 */       if (has("Environment")) {
/* 88 */         String environmentId = get("Environment").getAsString();
/* 89 */         environment = Environment.getAssetMap().getIndex(environmentId);
/* 90 */         if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*    */       } 
/* 92 */       return environment;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\PrefabContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */