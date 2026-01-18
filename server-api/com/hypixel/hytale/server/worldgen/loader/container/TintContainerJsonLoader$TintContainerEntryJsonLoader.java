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
/*     */ 
/*     */ public class TintContainerEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, TintContainer.TintContainerEntry>
/*     */ {
/*     */   public TintContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  85 */     super(seed.append(".TintContainerEntry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TintContainer.TintContainerEntry load() {
/*  91 */     IWeightedMap<Integer> colorMapping = loadColorMapping();
/*  92 */     return new TintContainer.TintContainerEntry(colorMapping, 
/*     */         
/*  94 */         (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO, 
/*  95 */         loadMapCondition());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<Integer> loadColorMapping() {
/* 101 */     WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 102 */     if (this.json == null || this.json.isJsonNull()) {
/* 103 */       builder.put(Integer.valueOf(16711680), 1.0D);
/* 104 */     } else if (this.json.isJsonObject()) {
/* 105 */       if (!has("Colors")) throw new IllegalArgumentException("Could not find colors. Keyword: Colors"); 
/* 106 */       JsonElement colorsElement = get("Colors");
/* 107 */       if (colorsElement.isJsonArray()) {
/* 108 */         JsonArray colors = colorsElement.getAsJsonArray();
/* 109 */         JsonArray weights = has("Weights") ? get("Weights").getAsJsonArray() : null;
/* 110 */         if (weights != null && weights.size() != colors.size()) throw new IllegalArgumentException("Tint weights array size does not fit color array size."); 
/* 111 */         for (int i = 0; i < colors.size(); i++) {
/* 112 */           int color = ColorUtil.hexString(colors.get(i).getAsString());
/* 113 */           double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/* 114 */           builder.put(Integer.valueOf(color), weight);
/*     */         } 
/*     */       } else {
/* 117 */         int color = ColorUtil.hexString(colorsElement.getAsString());
/* 118 */         builder.put(Integer.valueOf(color), 1.0D);
/*     */       } 
/*     */     } else {
/* 121 */       builder.put(Integer.valueOf(ColorUtil.hexString(this.json.getAsString())), 1.0D);
/*     */     } 
/* 123 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadValueNoise() {
/* 128 */     if (!has("Noise")) throw new IllegalArgumentException("Could not find value noise. Keyword: Noise"); 
/* 129 */     return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Noise")))
/* 130 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 135 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 136 */       .load();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\TintContainerJsonLoader$TintContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */