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
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.TintContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TintContainerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, TintContainer>
/*     */ {
/*     */   public TintContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  35 */     super(seed.append(".TintContainer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TintContainer load() {
/*  41 */     return new TintContainer(
/*  42 */         loadDefault(), 
/*  43 */         loadEntries());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected TintContainer.DefaultTintContainerEntry loadDefault() {
/*     */     JsonElement element;
/*  50 */     if (this.json == null || this.json.isJsonNull() || this.json.isJsonArray()) {
/*  51 */       element = null;
/*  52 */     } else if (this.json.isJsonObject() && has("Default")) {
/*  53 */       element = get("Default");
/*  54 */     } else if (this.json.isJsonPrimitive() || this.json.isJsonObject()) {
/*  55 */       element = this.json;
/*     */     } else {
/*  57 */       element = null;
/*     */     } 
/*  59 */     return (new DefaultTintContainerEntryJsonLoader(this.seed, this.dataFolder, element))
/*  60 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected List<TintContainer.TintContainerEntry> loadEntries() {
/*  65 */     if (has("Entries")) {
/*  66 */       JsonArray arr = get("Entries").getAsJsonArray();
/*  67 */       List<TintContainer.TintContainerEntry> entries = new ArrayList<>(arr.size());
/*  68 */       for (int i = 0; i < arr.size(); i++) {
/*     */         try {
/*  70 */           entries.add((new TintContainerEntryJsonLoader(this.seed.append(String.format("-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, arr.get(i)))
/*  71 */               .load());
/*  72 */         } catch (Throwable e) {
/*  73 */           throw new Error(String.format("Failed to load TintContainerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */         } 
/*     */       } 
/*  76 */       return entries;
/*     */     } 
/*  78 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   protected static class TintContainerEntryJsonLoader
/*     */     extends JsonLoader<SeedStringResource, TintContainer.TintContainerEntry>
/*     */   {
/*     */     public TintContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  85 */       super(seed.append(".TintContainerEntry"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public TintContainer.TintContainerEntry load() {
/*  91 */       IWeightedMap<Integer> colorMapping = loadColorMapping();
/*  92 */       return new TintContainer.TintContainerEntry(colorMapping, 
/*     */           
/*  94 */           (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO, 
/*  95 */           loadMapCondition());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected IWeightedMap<Integer> loadColorMapping() {
/* 101 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 102 */       if (this.json == null || this.json.isJsonNull()) {
/* 103 */         builder.put(Integer.valueOf(16711680), 1.0D);
/* 104 */       } else if (this.json.isJsonObject()) {
/* 105 */         if (!has("Colors")) throw new IllegalArgumentException("Could not find colors. Keyword: Colors"); 
/* 106 */         JsonElement colorsElement = get("Colors");
/* 107 */         if (colorsElement.isJsonArray()) {
/* 108 */           JsonArray colors = colorsElement.getAsJsonArray();
/* 109 */           JsonArray weights = has("Weights") ? get("Weights").getAsJsonArray() : null;
/* 110 */           if (weights != null && weights.size() != colors.size()) throw new IllegalArgumentException("Tint weights array size does not fit color array size."); 
/* 111 */           for (int i = 0; i < colors.size(); i++) {
/* 112 */             int color = ColorUtil.hexString(colors.get(i).getAsString());
/* 113 */             double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/* 114 */             builder.put(Integer.valueOf(color), weight);
/*     */           } 
/*     */         } else {
/* 117 */           int color = ColorUtil.hexString(colorsElement.getAsString());
/* 118 */           builder.put(Integer.valueOf(color), 1.0D);
/*     */         } 
/*     */       } else {
/* 121 */         builder.put(Integer.valueOf(ColorUtil.hexString(this.json.getAsString())), 1.0D);
/*     */       } 
/* 123 */       return builder.build();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadValueNoise() {
/* 128 */       if (!has("Noise")) throw new IllegalArgumentException("Could not find value noise. Keyword: Noise"); 
/* 129 */       return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise")))
/* 130 */         .load();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected ICoordinateCondition loadMapCondition() {
/* 135 */       return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 136 */         .load();
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class DefaultTintContainerEntryJsonLoader
/*     */     extends TintContainerEntryJsonLoader
/*     */   {
/*     */     public DefaultTintContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 144 */       super(seed.append(".DefaultTintContainerEntry"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public TintContainer.DefaultTintContainerEntry load() {
/*     */       IWeightedMap<Integer> colorMapping;
/* 151 */       if (this.json == null || this.json.isJsonNull()) {
/* 152 */         WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 153 */         builder.put(Integer.valueOf(16711680), 1.0D);
/* 154 */         colorMapping = builder.build();
/* 155 */       } else if (this.json.isJsonPrimitive()) {
/* 156 */         WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 157 */         builder.put(Integer.valueOf(ColorUtil.hexString(this.json.getAsString())), 1.0D);
/* 158 */         colorMapping = builder.build();
/*     */       } else {
/* 160 */         colorMapping = loadColorMapping();
/*     */       } 
/* 162 */       return new TintContainer.DefaultTintContainerEntry(colorMapping, 
/*     */           
/* 164 */           (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_DEFAULT = "Default";
/*     */     public static final String KEY_ENTRIES = "Entries";
/*     */     public static final String KEY_COLORS = "Colors";
/*     */     public static final String KEY_WEIGHTS = "Weights";
/*     */     public static final String KEY_ENTRY_NOISE = "Noise";
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */     public static final String ERROR_COLORS_NOT_FOUND = "Could not find colors. Keyword: Colors";
/*     */     public static final String ERROR_WEIGHT_SIZE = "Tint weights array size does not fit color array size.";
/*     */     public static final String ERROR_NO_VALUE_NOISE = "Could not find value noise. Keyword: Noise";
/*     */     public static final String ERROR_LOADING_ENTRY = "Failed to load TintContainerEntry #%s";
/*     */     public static final String SEED_INDEX_SUFFIX = "-%s";
/*     */     public static final int DEFAULT_TINT_COLOR = 16711680;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\TintContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */