/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.EnvironmentContainer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultEnvironmentContainerEntryLoader
/*     */   extends EnvironmentContainerJsonLoader.EnvironmentContainerEntryJsonLoader
/*     */ {
/*     */   public DefaultEnvironmentContainerEntryLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 150 */     super(seed, dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EnvironmentContainer.DefaultEnvironmentContainerEntry load() {
/*     */     IWeightedMap<Integer> colorMapping;
/* 157 */     if (this.json == null || this.json.isJsonNull()) {
/* 158 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 159 */       builder.put(Integer.valueOf(0), 1.0D);
/* 160 */       colorMapping = builder.build();
/* 161 */     } else if (this.json.isJsonPrimitive()) {
/* 162 */       WeightedMap.Builder<Integer> builder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_INTEGER_ARRAY);
/* 163 */       String key = this.json.getAsString();
/* 164 */       int index = Environment.getAssetMap().getIndex(key);
/* 165 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 166 */       builder.put(Integer.valueOf(index), 1.0D);
/* 167 */       colorMapping = builder.build();
/*     */     } else {
/* 169 */       colorMapping = loadIdMapping();
/*     */     } 
/* 171 */     return new EnvironmentContainer.DefaultEnvironmentContainerEntry(colorMapping, 
/*     */         
/* 173 */         (colorMapping.size() > 1) ? loadValueNoise() : ConstantNoiseProperty.DEFAULT_ZERO);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\EnvironmentContainerJsonLoader$DefaultEnvironmentContainerEntryLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */