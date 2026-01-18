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
/*     */ public class EnvironmentContainerEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, EnvironmentContainer.EnvironmentContainerEntry>
/*     */ {
/*     */   public EnvironmentContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  84 */     super(seed.append(".EnvironmentContainer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EnvironmentContainer.EnvironmentContainerEntry load() {
/*  90 */     IWeightedMap<Integer> colorMapping = loadIdMapping();
/*  91 */     return new EnvironmentContainer.EnvironmentContainerEntry(colorMapping, 
/*     */         
/*  93 */         (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO, 
/*  94 */         loadMapCondition());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<Integer> loadIdMapping() {
/* 100 */     WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 101 */     if (this.json == null || this.json.isJsonNull()) {
/* 102 */       builder.put(Integer.valueOf(0), 1.0D);
/* 103 */     } else if (this.json.isJsonObject()) {
/* 104 */       if (!has("Names")) throw new IllegalArgumentException("Could not find names. Keyword: Names"); 
/* 105 */       JsonElement colorsElement = get("Names");
/* 106 */       if (colorsElement.isJsonArray()) {
/* 107 */         JsonArray names = colorsElement.getAsJsonArray();
/* 108 */         JsonArray weights = has("Weights") ? get("Weights").getAsJsonArray() : null;
/* 109 */         if (weights != null && weights.size() != names.size()) throw new IllegalArgumentException("Tint weights array size does not fit color array size."); 
/* 110 */         for (int i = 0; i < names.size(); i++) {
/* 111 */           String key = names.get(i).getAsString();
/* 112 */           int index = Environment.getAssetMap().getIndex(key);
/* 113 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 114 */           double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/* 115 */           builder.put(Integer.valueOf(index), weight);
/*     */         } 
/*     */       } else {
/* 118 */         String key = colorsElement.getAsString();
/* 119 */         int index = Environment.getAssetMap().getIndex(key);
/* 120 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 121 */         builder.put(Integer.valueOf(index), 1.0D);
/*     */       } 
/*     */     } else {
/* 124 */       String key = this.json.getAsString();
/* 125 */       int index = Environment.getAssetMap().getIndex(key);
/* 126 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 127 */       builder.put(Integer.valueOf(index), 1.0D);
/*     */     } 
/* 129 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadValueNoise() {
/* 134 */     if (!has("Noise")) throw new IllegalArgumentException("Could not find value noise. Keyword: Noise"); 
/* 135 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise")))
/* 136 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 141 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 142 */       .load();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\EnvironmentContainerJsonLoader$EnvironmentContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */