/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.TintContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultTintContainerEntryJsonLoader
/*     */   extends TintContainerJsonLoader.TintContainerEntryJsonLoader
/*     */ {
/*     */   public DefaultTintContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 144 */     super(seed.append(".DefaultTintContainerEntry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TintContainer.DefaultTintContainerEntry load() {
/*     */     IWeightedMap<Integer> colorMapping;
/* 151 */     if (this.json == null || this.json.isJsonNull()) {
/* 152 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 153 */       builder.put(Integer.valueOf(16711680), 1.0D);
/* 154 */       colorMapping = builder.build();
/* 155 */     } else if (this.json.isJsonPrimitive()) {
/* 156 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 157 */       builder.put(Integer.valueOf(ColorUtil.hexString(this.json.getAsString())), 1.0D);
/* 158 */       colorMapping = builder.build();
/*     */     } else {
/* 160 */       colorMapping = loadColorMapping();
/*     */     } 
/* 162 */     return new TintContainer.DefaultTintContainerEntry(colorMapping, 
/*     */         
/* 164 */         (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\TintContainerJsonLoader$DefaultTintContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */