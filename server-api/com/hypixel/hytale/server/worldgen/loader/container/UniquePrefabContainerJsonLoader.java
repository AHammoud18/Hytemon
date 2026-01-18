/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.WeightedPrefabMapJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.unique.UniquePrefabConfigurationJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.unique.UniquePrefabConfiguration;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.unique.UniquePrefabGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import java.nio.file.Path;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UniquePrefabContainerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, UniquePrefabContainer>
/*     */ {
/*  32 */   public static final UniquePrefabGenerator[] EMPTY_GENERATORS = new UniquePrefabGenerator[0];
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public UniquePrefabContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/*  36 */     super(seed.append(".UniquePrefabContainer"), dataFolder, json);
/*  37 */     this.zoneContext = zoneContext;
/*     */   }
/*     */   public static interface Constants {
/*     */     public static final String KEY_ENTRIES = "Entries"; public static final String KEY_CONFIG = "Config"; public static final String KEY_PREFAB = "Prefab"; public static final String KEY_WEIGHTS = "Weights"; public static final String KEY_ENTRY_NAME = "Name"; public static final String NO_NAME = "NO_NAME_GIVEN"; public static final String SEED_INDEX_SUFFIX = "-%s"; }
/*     */   @Nonnull
/*     */   public UniquePrefabContainer load() {
/*     */     UniquePrefabGenerator[] generators;
/*  44 */     if (this.json == null || this.json.isJsonNull()) {
/*  45 */       generators = EMPTY_GENERATORS;
/*     */     } else {
/*  47 */       JsonArray jsonArray = get("Entries").getAsJsonArray();
/*  48 */       generators = new UniquePrefabGenerator[jsonArray.size()];
/*  49 */       for (int i = 0; i < jsonArray.size(); i++) {
/*  50 */         generators[i] = (new UniquePrefabGeneratorJsonLoader(this.seed.append(String.format("-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, jsonArray.get(i), this.zoneContext))
/*  51 */           .load();
/*     */       } 
/*     */     } 
/*  54 */     return new UniquePrefabContainer(this.seed.hashCode(), generators);
/*     */   }
/*     */   
/*     */   protected static class UniquePrefabGeneratorJsonLoader
/*     */     extends JsonLoader<SeedStringResource, UniquePrefabGenerator>
/*     */   {
/*     */     public UniquePrefabGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/*  61 */       super(seed.append(".UniquePrefabGenerator"), dataFolder, json);
/*  62 */       this.zoneContext = zoneContext;
/*     */     }
/*     */     protected final ZoneFileContext zoneContext;
/*     */     
/*     */     @Nonnull
/*     */     public UniquePrefabGenerator load() {
/*  68 */       return new UniquePrefabGenerator(
/*  69 */           loadName(), 
/*  70 */           loadCategory(), 
/*  71 */           loadPrefabs(), 
/*  72 */           loadConfiguration(), this.zoneContext
/*  73 */           .getId());
/*     */     }
/*     */ 
/*     */     
/*     */     public String loadName() {
/*  78 */       String name = "NO_NAME_GIVEN";
/*  79 */       if (has("Name")) {
/*  80 */         name = get("Name").getAsString();
/*     */       }
/*  82 */       return name;
/*     */     }
/*     */     
/*     */     protected PrefabCategory loadCategory() {
/*  86 */       String category = mustGetString("Category", "");
/*     */       
/*  88 */       if (category.isEmpty()) {
/*  89 */         return PrefabCategory.UNIQUE;
/*     */       }
/*     */       
/*  92 */       if (!((FileLoadingContext)this.zoneContext.getParentContext()).getPrefabCategories().contains(category)) {
/*  93 */         LogUtil.getLogger().at(Level.WARNING).log("Could not find prefab category: %s, defaulting to None", category);
/*  94 */         return PrefabCategory.UNIQUE;
/*     */       } 
/*     */       
/*  97 */       return (PrefabCategory)((FileLoadingContext)this.zoneContext.getParentContext()).getPrefabCategories().get(category);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public UniquePrefabConfiguration loadConfiguration() {
/* 102 */       return (new UniquePrefabConfigurationJsonLoader(this.seed, this.dataFolder, get("Config"), this.zoneContext))
/* 103 */         .load();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public IWeightedMap<WorldGenPrefabSupplier> loadPrefabs() {
/* 108 */       return (new WeightedPrefabMapJsonLoader(this.seed, this.dataFolder, this.json, "Prefab", "Weights"))
/* 109 */         .load();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\UniquePrefabContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */