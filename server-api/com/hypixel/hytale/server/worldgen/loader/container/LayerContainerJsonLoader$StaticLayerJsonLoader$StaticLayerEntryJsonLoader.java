/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRangeNoiseSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaticLayerEntryJsonLoader
/*     */   extends LayerContainerJsonLoader.LayerEntryJsonLoader<LayerContainer.StaticLayerEntry>
/*     */ {
/*     */   public StaticLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 289 */     super(seed.append(".StaticLayerEntry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LayerContainer.StaticLayerEntry load() {
/* 295 */     return new LayerContainer.StaticLayerEntry(
/* 296 */         loadBlocks(), 
/* 297 */         loadMapCondition(), 
/* 298 */         loadMin(), 
/* 299 */         loadMax());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IDoubleCoordinateSupplier loadMin() {
/* 305 */     if (!has("Min")) throw new IllegalArgumentException("Could not find minimum of static layer entry.");
/*     */     
/* 307 */     IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/* 308 */     NoiseProperty minNoise = loadMinNoise();
/* 309 */     return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IDoubleCoordinateSupplier loadMax() {
/* 314 */     if (!has("Max")) throw new IllegalArgumentException("Could not find maximum of static layer entry.");
/*     */     
/* 316 */     IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/* 317 */     NoiseProperty maxNoise = loadMaxNoise();
/* 318 */     return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadMinNoise() {
/* 323 */     NoiseProperty minNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 324 */     if (has("MinNoise"))
/*     */     {
/* 326 */       minNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MinNoise"))).load();
/*     */     }
/* 328 */     return minNoise;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadMaxNoise() {
/* 333 */     NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 334 */     if (has("MaxNoise"))
/*     */     {
/* 336 */       maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MaxNoise"))).load();
/*     */     }
/* 338 */     return maxNoise;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader$StaticLayerJsonLoader$StaticLayerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */