/*    */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.TileBiome;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
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
/*    */ public class TileBiomeJsonLoader
/*    */   extends BiomeJsonLoader
/*    */ {
/*    */   public TileBiomeJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, @Nonnull BiomeFileContext biomeContext) {
/* 22 */     super(seed.append(String.format(".TileBiome-%s", new Object[] { biomeContext.getName() })), dataFolder, json, biomeContext);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public TileBiome load() {
/* 28 */     return new TileBiome(this.biomeContext
/* 29 */         .getId(), this.biomeContext
/* 30 */         .getName(), 
/* 31 */         loadInterpolation(), 
/* 32 */         loadTerrainHeightThreshold(), 
/* 33 */         loadCoverContainer(), 
/* 34 */         loadLayerContainers(), 
/* 35 */         loadPrefabContainer(), 
/* 36 */         loadTintContainer(), 
/* 37 */         loadEnvironmentContainer(), 
/* 38 */         loadWaterContainer(), 
/* 39 */         loadFadeContainer(), 
/* 40 */         loadHeightmapNoise(), 
/* 41 */         loadWeight(), 
/* 42 */         loadSizeModifier(), 
/* 43 */         loadColor());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadWeight() {
/* 48 */     double weight = 1.0D;
/* 49 */     if (has("Weight")) {
/* 50 */       weight = get("Weight").getAsDouble();
/*    */     }
/* 52 */     return weight;
/*    */   }
/*    */   
/*    */   protected double loadSizeModifier() {
/* 56 */     double sizeModifier = 1.0D;
/* 57 */     if (has("SizeModifier")) {
/* 58 */       sizeModifier = get("SizeModifier").getAsDouble();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     return sizeModifier * sizeModifier;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_WEIGHT = "Weight";
/*    */     public static final String KEY_SIZE_MODIFIER = "SizeModifier";
/*    */     public static final String SEED_PREFIX = ".TileBiome-%s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\TileBiomeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */