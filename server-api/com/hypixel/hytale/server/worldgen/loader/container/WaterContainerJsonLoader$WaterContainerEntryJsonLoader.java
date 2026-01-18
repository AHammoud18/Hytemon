/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRangeNoiseSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WaterContainerEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, WaterContainer.Entry>
/*     */ {
/*     */   public WaterContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 122 */     super(seed.append(".Entry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaterContainer.Entry load() {
/*     */     try {
/* 129 */       if (has("Fluid")) {
/* 130 */         String fluidString = get("Fluid").getAsString();
/* 131 */         int index = Fluid.getAssetMap().getIndex(fluidString);
/* 132 */         if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { fluidString }));
/*     */         
/* 134 */         return new WaterContainer.Entry(0, index, 
/*     */ 
/*     */             
/* 137 */             loadMin(), loadMax(), 
/* 138 */             loadNoiseMask());
/* 139 */       }  if (has("Block")) {
/* 140 */         String blockString = get("Block").getAsString();
/* 141 */         String blockTypeKey = blockString;
/* 142 */         int index = BlockType.getAssetMap().getIndex(blockTypeKey);
/* 143 */         if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { blockTypeKey.toString() }));
/*     */         
/* 145 */         return new WaterContainer.Entry(index, 0, 
/*     */ 
/*     */             
/* 148 */             loadMin(), loadMax(), 
/* 149 */             loadNoiseMask());
/*     */       } 
/* 151 */       throw new IllegalArgumentException("Could not find fluid information. Keyword: Fluid");
/*     */     }
/* 153 */     catch (Error e) {
/* 154 */       throw new Error("Failed to load water container.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private IDoubleCoordinateSupplier loadMin() {
/* 161 */     IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/* 162 */     NoiseProperty minNoise = loadNoise("MinNoise");
/* 163 */     return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private IDoubleCoordinateSupplier loadMax() {
/* 168 */     if (!has("Max")) throw new IllegalArgumentException("Could not find maximum of water container entry.");
/*     */     
/* 170 */     IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/* 171 */     NoiseProperty maxNoise = loadNoise("MaxNoise");
/* 172 */     return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private NoiseProperty loadNoise(String key) {
/* 177 */     NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 178 */     if (has(key))
/*     */     {
/* 180 */       maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get(key))).load();
/*     */     }
/* 182 */     return maxNoise;
/*     */   }
/*     */   @Nonnull
/*     */   private ICoordinateCondition loadNoiseMask() {
/*     */     ICoordinateCondition iCoordinateCondition;
/* 187 */     DefaultCoordinateCondition defaultCoordinateCondition = DefaultCoordinateCondition.DEFAULT_TRUE;
/* 188 */     if (has("NoiseMask"))
/*     */     {
/* 190 */       iCoordinateCondition = (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask"))).load();
/*     */     }
/* 192 */     return iCoordinateCondition;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\WaterContainerJsonLoader$WaterContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */