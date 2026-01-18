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
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRangeNoiseSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
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
/*     */ public class DynamicLayerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, LayerContainer.DynamicLayer>
/*     */ {
/*     */   public DynamicLayerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 130 */     super(seed.append(".DynamicLayer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LayerContainer.DynamicLayer load() {
/* 136 */     return new LayerContainer.DynamicLayer(
/* 137 */         loadEntries(), 
/* 138 */         loadMapCondition(), 
/* 139 */         loadEnvironment(), 
/* 140 */         loadOffset());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 146 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 147 */       .load();
/*     */   }
/*     */   @Nonnull
/*     */   protected IDoubleCoordinateSupplier loadOffset() {
/*     */     IDoubleRange iDoubleRange;
/* 152 */     DoubleRange.Constant constant = DoubleRange.ZERO;
/* 153 */     NoiseProperty offsetNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 154 */     if (has("Offset")) {
/*     */       
/* 156 */       iDoubleRange = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Offset"))).load();
/* 157 */       if (has("OffsetNoise"))
/*     */       {
/* 159 */         offsetNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("OffsetNoise"))).load();
/*     */       }
/*     */     } 
/* 162 */     return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(iDoubleRange, offsetNoise);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected LayerContainer.DynamicLayerEntry[] loadEntries() {
/* 167 */     if (this.json == null || this.json.isJsonNull())
/* 168 */       return new LayerContainer.DynamicLayerEntry[0]; 
/* 169 */     if (this.json.isJsonObject()) {
/* 170 */       if (has("Entries")) {
/* 171 */         JsonArray array = get("Entries").getAsJsonArray();
/* 172 */         LayerContainer.DynamicLayerEntry[] entries = new LayerContainer.DynamicLayerEntry[array.size()];
/* 173 */         for (int i = 0; i < entries.length; i++) {
/*     */           try {
/* 175 */             entries[i] = (new DynamicLayerEntryJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/* 176 */               .load();
/* 177 */           } catch (Throwable e) {
/* 178 */             throw new Error(String.format("Error while loading DynamicLayerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */           } 
/*     */         } 
/* 181 */         return entries;
/*     */       } 
/*     */       try {
/* 184 */         return new LayerContainer.DynamicLayerEntry[] { (new DynamicLayerEntryJsonLoader(this.seed, this.dataFolder, this.json))
/*     */             
/* 186 */             .load() };
/*     */       }
/* 188 */       catch (Throwable e) {
/* 189 */         throw new Error(String.format("Error while loading DynamicLayerEntry #%s", new Object[] { Integer.valueOf(0) }), e);
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     throw new Error("Unknown type for dynamic Layer");
/*     */   }
/*     */   
/*     */   protected int loadEnvironment() {
/* 197 */     int environment = Integer.MIN_VALUE;
/* 198 */     if (has("Environment")) {
/* 199 */       String environmentId = get("Environment").getAsString();
/* 200 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 201 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/* 203 */     return environment;
/*     */   }
/*     */   
/*     */   protected static class DynamicLayerEntryJsonLoader extends LayerContainerJsonLoader.LayerEntryJsonLoader<LayerContainer.DynamicLayerEntry> {
/*     */     public DynamicLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 208 */       super(seed, dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LayerContainer.DynamicLayerEntry load() {
/* 214 */       return new LayerContainer.DynamicLayerEntry(
/* 215 */           loadBlocks(), 
/* 216 */           loadMapCondition());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader$DynamicLayerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */