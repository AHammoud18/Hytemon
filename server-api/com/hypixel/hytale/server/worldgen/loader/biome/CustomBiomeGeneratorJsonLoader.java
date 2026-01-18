/*    */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleThreshold;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.json.DoubleThresholdJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.biome.CustomBiomeGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetIntCondition;
/*    */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomBiomeGeneratorJsonLoader
/*    */   extends JsonLoader<SeedStringResource, CustomBiomeGenerator>
/*    */ {
/*    */   protected final BiomeFileContext biomeContext;
/*    */   protected final Biome[] tileBiomes;
/*    */   
/*    */   public CustomBiomeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, BiomeFileContext biomeContext, Biome[] tileBiomes) {
/* 36 */     super(seed.append(".CustomBiomeGenerator"), dataFolder, json);
/* 37 */     this.biomeContext = biomeContext;
/* 38 */     this.tileBiomes = tileBiomes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CustomBiomeGenerator load() {
/* 44 */     return new CustomBiomeGenerator(
/* 45 */         loadNoiseProperty(), 
/* 46 */         loadNoiseThreshold(), 
/* 47 */         loadBiomeMask(), 
/* 48 */         loadPriority());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected NoiseProperty loadNoiseProperty() {
/* 54 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 55 */       .load();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected IDoubleThreshold loadNoiseThreshold() {
/* 60 */     return (new DoubleThresholdJsonLoader(this.seed, this.dataFolder, get("NoiseMask").getAsJsonObject()
/* 61 */         .get("Threshold")))
/* 62 */       .load();
/*    */   }
/*    */   @Nonnull
/*    */   protected IIntCondition loadBiomeMask() {
/*    */     HashSetIntCondition hashSetIntCondition;
/* 67 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/* 68 */     if (has("BiomeMask")) {
/* 69 */       Map<String, Biome> nameBiomeMap = generateNameBiomeMapping();
/* 70 */       JsonArray biomeMaskArray = get("BiomeMask").getAsJsonArray();
/* 71 */       IntOpenHashSet intOpenHashSet = new IntOpenHashSet(biomeMaskArray.size());
/* 72 */       for (int i = 0; i < biomeMaskArray.size(); i++) {
/* 73 */         String biomeName = biomeMaskArray.get(i).getAsString();
/* 74 */         Biome biome = nameBiomeMap.get(biomeName);
/* 75 */         Objects.requireNonNull(biome, biomeName);
/* 76 */         intOpenHashSet.add(biome.getId());
/*    */       } 
/* 78 */       hashSetIntCondition = new HashSetIntCondition((IntSet)intOpenHashSet);
/*    */     } 
/* 80 */     return (IIntCondition)hashSetIntCondition;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected Map<String, Biome> generateNameBiomeMapping() {
/* 85 */     Map<String, Biome> map = new HashMap<>();
/* 86 */     for (Biome biome : this.tileBiomes) {
/* 87 */       map.put(biome.getName(), biome);
/*    */     }
/* 89 */     return map;
/*    */   }
/*    */   
/*    */   protected int loadPriority() {
/* 93 */     return has("Priority") ? get("Priority").getAsInt() : 0;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*    */     public static final String KEY_BIOME_MASK = "BiomeMask";
/*    */     public static final String KEY_PRIORITY = "Priority";
/*    */     public static final String ERROR_BIOME_ERROR_MASK = "Could not find tile biome \"%s\" for biome mask. Typo or disabled tile biome?";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\CustomBiomeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */