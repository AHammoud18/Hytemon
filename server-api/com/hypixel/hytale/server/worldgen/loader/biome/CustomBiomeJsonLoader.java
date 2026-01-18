/*    */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.biome.CustomBiome;
/*    */ import com.hypixel.hytale.server.worldgen.biome.CustomBiomeGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
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
/*    */ public class CustomBiomeJsonLoader
/*    */   extends BiomeJsonLoader
/*    */ {
/*    */   protected final Biome[] tileBiomes;
/*    */   
/*    */   public CustomBiomeJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, @Nonnull BiomeFileContext biomeContext, Biome[] tileBiomes) {
/* 26 */     super(seed.append(String.format("TileBiome-%s", new Object[] { biomeContext.getName() })), dataFolder, json, biomeContext);
/* 27 */     this.tileBiomes = tileBiomes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CustomBiome load() {
/* 33 */     return new CustomBiome(this.biomeContext
/* 34 */         .getId(), this.biomeContext
/* 35 */         .getName(), 
/* 36 */         loadInterpolation(), 
/* 37 */         loadCustomBiomeGenerator(), 
/* 38 */         loadTerrainHeightThreshold(), 
/* 39 */         loadCoverContainer(), 
/* 40 */         loadLayerContainers(), 
/* 41 */         loadPrefabContainer(), 
/* 42 */         loadTintContainer(), 
/* 43 */         loadEnvironmentContainer(), 
/* 44 */         loadWaterContainer(), 
/* 45 */         loadFadeContainer(), 
/* 46 */         loadHeightmapNoise(), 
/* 47 */         loadColor());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected CustomBiomeGenerator loadCustomBiomeGenerator() {
/* 53 */     CustomBiomeGenerator customBiomeGenerator = null;
/* 54 */     if (has("CustomBiomeGenerator"))
/*    */     {
/* 56 */       customBiomeGenerator = (new CustomBiomeGeneratorJsonLoader(this.seed, this.dataFolder, get("CustomBiomeGenerator"), this.biomeContext, this.tileBiomes)).load();
/*    */     }
/* 58 */     return customBiomeGenerator;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_CUSTOM_BIOME_GENERATOR = "CustomBiomeGenerator";
/*    */     public static final String SEED_PREFIX = "TileBiome-%s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\CustomBiomeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */