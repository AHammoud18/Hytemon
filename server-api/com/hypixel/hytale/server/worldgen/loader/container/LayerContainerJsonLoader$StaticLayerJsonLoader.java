/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
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
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
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
/*     */ public class StaticLayerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, LayerContainer.StaticLayer>
/*     */ {
/*     */   public StaticLayerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 226 */     super(seed.append(".StaticLayer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LayerContainer.StaticLayer load() {
/* 232 */     return new LayerContainer.StaticLayer(
/* 233 */         loadEntries(), 
/* 234 */         loadMapCondition(), 
/* 235 */         loadEnvironment());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 241 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 242 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected LayerContainer.StaticLayerEntry[] loadEntries() {
/* 247 */     if (this.json == null || this.json.isJsonNull())
/* 248 */       return new LayerContainer.StaticLayerEntry[0]; 
/* 249 */     if (this.json.isJsonObject()) {
/* 250 */       if (has("Entries")) {
/* 251 */         JsonArray array = get("Entries").getAsJsonArray();
/* 252 */         LayerContainer.StaticLayerEntry[] entries = new LayerContainer.StaticLayerEntry[array.size()];
/* 253 */         for (int i = 0; i < entries.length; i++) {
/*     */           try {
/* 255 */             entries[i] = (new StaticLayerEntryJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/* 256 */               .load();
/* 257 */           } catch (Throwable e) {
/* 258 */             throw new Error(String.format("Error while loading StaticLayerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */           } 
/*     */         } 
/* 261 */         return entries;
/*     */       } 
/*     */       try {
/* 264 */         return new LayerContainer.StaticLayerEntry[] { (new StaticLayerEntryJsonLoader(this.seed, this.dataFolder, this.json))
/*     */             
/* 266 */             .load() };
/*     */       }
/* 268 */       catch (Throwable e) {
/* 269 */         throw new Error(String.format("Error while loading StaticLayerEntry #%s", new Object[] { Integer.valueOf(0) }), e);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     throw new Error("Unknown type for static Layer");
/*     */   }
/*     */   
/*     */   protected int loadEnvironment() {
/* 277 */     int environment = Integer.MIN_VALUE;
/* 278 */     if (has("Environment")) {
/* 279 */       String environmentId = get("Environment").getAsString();
/* 280 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 281 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/* 283 */     return environment;
/*     */   }
/*     */   
/*     */   protected static class StaticLayerEntryJsonLoader
/*     */     extends LayerContainerJsonLoader.LayerEntryJsonLoader<LayerContainer.StaticLayerEntry> {
/*     */     public StaticLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 289 */       super(seed.append(".StaticLayerEntry"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LayerContainer.StaticLayerEntry load() {
/* 295 */       return new LayerContainer.StaticLayerEntry(
/* 296 */           loadBlocks(), 
/* 297 */           loadMapCondition(), 
/* 298 */           loadMin(), 
/* 299 */           loadMax());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected IDoubleCoordinateSupplier loadMin() {
/* 305 */       if (!has("Min")) throw new IllegalArgumentException("Could not find minimum of static layer entry.");
/*     */       
/* 307 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/* 308 */       NoiseProperty minNoise = loadMinNoise();
/* 309 */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected IDoubleCoordinateSupplier loadMax() {
/* 314 */       if (!has("Max")) throw new IllegalArgumentException("Could not find maximum of static layer entry.");
/*     */       
/* 316 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/* 317 */       NoiseProperty maxNoise = loadMaxNoise();
/* 318 */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadMinNoise() {
/* 323 */       NoiseProperty minNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 324 */       if (has("MinNoise"))
/*     */       {
/* 326 */         minNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MinNoise"))).load();
/*     */       }
/* 328 */       return minNoise;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadMaxNoise() {
/* 333 */       NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 334 */       if (has("MaxNoise"))
/*     */       {
/* 336 */         maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MaxNoise"))).load();
/*     */       }
/* 338 */       return maxNoise;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader$StaticLayerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */