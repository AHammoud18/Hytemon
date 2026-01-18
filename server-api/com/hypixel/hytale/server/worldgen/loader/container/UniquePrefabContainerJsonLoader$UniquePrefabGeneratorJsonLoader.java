/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
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
/*     */ public class UniquePrefabGeneratorJsonLoader
/*     */   extends JsonLoader<SeedStringResource, UniquePrefabGenerator>
/*     */ {
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public UniquePrefabGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/*  61 */     super(seed.append(".UniquePrefabGenerator"), dataFolder, json);
/*  62 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UniquePrefabGenerator load() {
/*  68 */     return new UniquePrefabGenerator(
/*  69 */         loadName(), 
/*  70 */         loadCategory(), 
/*  71 */         loadPrefabs(), 
/*  72 */         loadConfiguration(), this.zoneContext
/*  73 */         .getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public String loadName() {
/*  78 */     String name = "NO_NAME_GIVEN";
/*  79 */     if (has("Name")) {
/*  80 */       name = get("Name").getAsString();
/*     */     }
/*  82 */     return name;
/*     */   }
/*     */   
/*     */   protected PrefabCategory loadCategory() {
/*  86 */     String category = mustGetString("Category", "");
/*     */     
/*  88 */     if (category.isEmpty()) {
/*  89 */       return PrefabCategory.UNIQUE;
/*     */     }
/*     */     
/*  92 */     if (!((FileLoadingContext)this.zoneContext.getParentContext()).getPrefabCategories().contains(category)) {
/*  93 */       LogUtil.getLogger().at(Level.WARNING).log("Could not find prefab category: %s, defaulting to None", category);
/*  94 */       return PrefabCategory.UNIQUE;
/*     */     } 
/*     */     
/*  97 */     return (PrefabCategory)((FileLoadingContext)this.zoneContext.getParentContext()).getPrefabCategories().get(category);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UniquePrefabConfiguration loadConfiguration() {
/* 102 */     return (new UniquePrefabConfigurationJsonLoader(this.seed, this.dataFolder, get("Config"), this.zoneContext))
/* 103 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IWeightedMap<WorldGenPrefabSupplier> loadPrefabs() {
/* 108 */     return (new WeightedPrefabMapJsonLoader(this.seed, this.dataFolder, this.json, "Prefab", "Weights"))
/* 109 */       .load();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\UniquePrefabContainerJsonLoader$UniquePrefabGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */