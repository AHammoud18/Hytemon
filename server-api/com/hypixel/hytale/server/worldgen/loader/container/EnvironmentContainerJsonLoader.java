/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.EnvironmentContainer;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import java.nio.file.Path;
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
/*     */ public class EnvironmentContainerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, EnvironmentContainer>
/*     */ {
/*     */   public EnvironmentContainerJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  33 */     super(seed, dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EnvironmentContainer load() {
/*  39 */     return new EnvironmentContainer(
/*  40 */         loadDefault(), 
/*  41 */         loadEntries());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected EnvironmentContainer.DefaultEnvironmentContainerEntry loadDefault() {
/*     */     JsonElement element;
/*  48 */     if (this.json == null || this.json.isJsonNull() || this.json.isJsonArray()) {
/*  49 */       element = null;
/*  50 */     } else if (this.json.isJsonObject() && has("Default")) {
/*  51 */       element = get("Default");
/*  52 */     } else if (this.json.isJsonPrimitive() || this.json.isJsonObject()) {
/*  53 */       element = this.json;
/*     */     } else {
/*  55 */       element = null;
/*     */     } 
/*  57 */     return (new DefaultEnvironmentContainerEntryLoader(this.seed, this.dataFolder, element))
/*  58 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected EnvironmentContainer.EnvironmentContainerEntry[] loadEntries() {
/*  63 */     if (this.json == null || !this.json.isJsonObject() || !has("Entries")) {
/*  64 */       return EnvironmentContainer.EnvironmentContainerEntry.EMPTY_ARRAY;
/*     */     }
/*  66 */     JsonArray arr = get("Entries").getAsJsonArray();
/*  67 */     if (arr.isEmpty()) return EnvironmentContainer.EnvironmentContainerEntry.EMPTY_ARRAY; 
/*  68 */     EnvironmentContainer.EnvironmentContainerEntry[] entries = new EnvironmentContainer.EnvironmentContainerEntry[arr.size()];
/*  69 */     for (int i = 0; i < arr.size(); i++) {
/*     */       try {
/*  71 */         entries[i] = (new EnvironmentContainerEntryJsonLoader(this.seed.append(String.format("-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, arr.get(i)))
/*  72 */           .load();
/*  73 */       } catch (Throwable e) {
/*  74 */         throw new Error(String.format("Failed to load TintContainerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */       } 
/*     */     } 
/*  77 */     return entries;
/*     */   }
/*     */   
/*     */   protected static class EnvironmentContainerEntryJsonLoader
/*     */     extends JsonLoader<SeedStringResource, EnvironmentContainer.EnvironmentContainerEntry>
/*     */   {
/*     */     public EnvironmentContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  84 */       super(seed.append(".EnvironmentContainer"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public EnvironmentContainer.EnvironmentContainerEntry load() {
/*  90 */       IWeightedMap<Integer> colorMapping = loadIdMapping();
/*  91 */       return new EnvironmentContainer.EnvironmentContainerEntry(colorMapping, 
/*     */           
/*  93 */           (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO, 
/*  94 */           loadMapCondition());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected IWeightedMap<Integer> loadIdMapping() {
/* 100 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 101 */       if (this.json == null || this.json.isJsonNull()) {
/* 102 */         builder.put(Integer.valueOf(0), 1.0D);
/* 103 */       } else if (this.json.isJsonObject()) {
/* 104 */         if (!has("Names")) throw new IllegalArgumentException("Could not find names. Keyword: Names"); 
/* 105 */         JsonElement colorsElement = get("Names");
/* 106 */         if (colorsElement.isJsonArray()) {
/* 107 */           JsonArray names = colorsElement.getAsJsonArray();
/* 108 */           JsonArray weights = has("Weights") ? get("Weights").getAsJsonArray() : null;
/* 109 */           if (weights != null && weights.size() != names.size()) throw new IllegalArgumentException("Tint weights array size does not fit color array size."); 
/* 110 */           for (int i = 0; i < names.size(); i++) {
/* 111 */             String key = names.get(i).getAsString();
/* 112 */             int index = Environment.getAssetMap().getIndex(key);
/* 113 */             if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 114 */             double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/* 115 */             builder.put(Integer.valueOf(index), weight);
/*     */           } 
/*     */         } else {
/* 118 */           String key = colorsElement.getAsString();
/* 119 */           int index = Environment.getAssetMap().getIndex(key);
/* 120 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 121 */           builder.put(Integer.valueOf(index), 1.0D);
/*     */         } 
/*     */       } else {
/* 124 */         String key = this.json.getAsString();
/* 125 */         int index = Environment.getAssetMap().getIndex(key);
/* 126 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 127 */         builder.put(Integer.valueOf(index), 1.0D);
/*     */       } 
/* 129 */       return builder.build();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadValueNoise() {
/* 134 */       if (!has("Noise")) throw new IllegalArgumentException("Could not find value noise. Keyword: Noise"); 
/* 135 */       return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise")))
/* 136 */         .load();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected ICoordinateCondition loadMapCondition() {
/* 141 */       return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 142 */         .load();
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class DefaultEnvironmentContainerEntryLoader
/*     */     extends EnvironmentContainerEntryJsonLoader
/*     */   {
/*     */     public DefaultEnvironmentContainerEntryLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 150 */       super(seed, dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public EnvironmentContainer.DefaultEnvironmentContainerEntry load() {
/*     */       IWeightedMap<Integer> colorMapping;
/* 157 */       if (this.json == null || this.json.isJsonNull()) {
/* 158 */         WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 159 */         builder.put(Integer.valueOf(0), 1.0D);
/* 160 */         colorMapping = builder.build();
/* 161 */       } else if (this.json.isJsonPrimitive()) {
/* 162 */         WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 163 */         String key = this.json.getAsString();
/* 164 */         int index = Environment.getAssetMap().getIndex(key);
/* 165 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 166 */         builder.put(Integer.valueOf(index), 1.0D);
/* 167 */         colorMapping = builder.build();
/*     */       } else {
/* 169 */         colorMapping = loadIdMapping();
/*     */       } 
/* 171 */       return new EnvironmentContainer.DefaultEnvironmentContainerEntry(colorMapping, 
/*     */           
/* 173 */           (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_DEFAULT = "Default";
/*     */     public static final String KEY_ENTRIES = "Entries";
/*     */     public static final String KEY_NAMES = "Names";
/*     */     public static final String KEY_WEIGHTS = "Weights";
/*     */     public static final String KEY_ENTRY_NOISE = "Noise";
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */     public static final String ERROR_NAMES_NOT_FOUND = "Could not find names. Keyword: Names";
/*     */     public static final String ERROR_WEIGHT_SIZE = "Tint weights array size does not fit color array size.";
/*     */     public static final String ERROR_NO_VALUE_NOISE = "Could not find value noise. Keyword: Noise";
/*     */     public static final String ERROR_LOADING_ENTRY = "Failed to load TintContainerEntry #%s";
/*     */     public static final String SEED_INDEX_SUFFIX = "-%s";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\EnvironmentContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */