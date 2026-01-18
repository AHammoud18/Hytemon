/*    */ package com.hypixel.hytale.server.worldgen.loader.container;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabContainerEntryJsonLoader
/*    */   extends JsonLoader<SeedStringResource, PrefabContainer.PrefabContainerEntry>
/*    */ {
/*    */   private final FileLoadingContext context;
/*    */   
/*    */   public PrefabContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, FileLoadingContext context) {
/* 68 */     super(seed.append(".PrefabContainerEntry"), dataFolder, json);
/* 69 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PrefabContainer.PrefabContainerEntry load() {
/* 76 */     IWeightedMap<WorldGenPrefabSupplier> prefabs = (new WeightedPrefabMapJsonLoader(this.seed, this.dataFolder, this.json, "Prefab", "Weight")).load();
/*    */     
/* 78 */     if (!has("Pattern")) throw new IllegalArgumentException("Could not find prefab pattern. Keyword: Pattern");
/*    */     
/* 80 */     PrefabPatternGenerator prefabPatternGenerator = (new PrefabPatternGeneratorJsonLoader(this.seed, this.dataFolder, get("Pattern"), this.context)).load();
/*    */     
/* 82 */     return new PrefabContainer.PrefabContainerEntry(prefabs, prefabPatternGenerator, loadEnvironment());
/*    */   }
/*    */   
/*    */   protected int loadEnvironment() {
/* 86 */     int environment = Integer.MIN_VALUE;
/* 87 */     if (has("Environment")) {
/* 88 */       String environmentId = get("Environment").getAsString();
/* 89 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 90 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*    */     } 
/* 92 */     return environment;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\PrefabContainerJsonLoader$PrefabContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */