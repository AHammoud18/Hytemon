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
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.NoiseBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerContainerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, LayerContainer>
/*     */ {
/*     */   public LayerContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  36 */     super(seed.append(".LayerContainer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LayerContainer load() {
/*  42 */     return new LayerContainer(
/*  43 */         loadDefault(), 
/*  44 */         loadDefaultEnvironment(), 
/*  45 */         loadStaticLayers(), 
/*  46 */         loadDynamicLayers());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int loadDefault() {
/*  51 */     if (!has("Default")) throw new IllegalArgumentException("Could not find default material. Keyword: Default"); 
/*  52 */     String blockName = get("Default").getAsString();
/*  53 */     int index = BlockType.getAssetMap().getIndex(blockName);
/*  54 */     if (index == Integer.MIN_VALUE) throw new Error(String.format("Default block for LayerContainer could not be found! BlockType: %s", new Object[] { blockName })); 
/*  55 */     return index;
/*     */   }
/*     */   
/*     */   protected int loadDefaultEnvironment() {
/*  59 */     int environment = Integer.MIN_VALUE;
/*  60 */     if (has("Environment")) {
/*  61 */       String environmentId = get("Environment").getAsString();
/*  62 */       environment = Environment.getAssetMap().getIndex(environmentId);
/*  63 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/*  65 */     return environment;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected LayerContainer.StaticLayer[] loadStaticLayers() {
/*  70 */     if (has("Static")) {
/*  71 */       JsonArray array = get("Static").getAsJsonArray();
/*  72 */       LayerContainer.StaticLayer[] layers = new LayerContainer.StaticLayer[array.size()];
/*  73 */       for (int i = 0; i < layers.length; i++) {
/*     */         try {
/*  75 */           layers[i] = (new StaticLayerJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/*  76 */             .load();
/*  77 */         } catch (Throwable e) {
/*  78 */           throw new Error(String.format("Error while loading StaticLayer #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */         } 
/*     */       } 
/*  81 */       return layers;
/*     */     } 
/*  83 */     return new LayerContainer.StaticLayer[0];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected LayerContainer.DynamicLayer[] loadDynamicLayers() {
/*  89 */     if (has("Dynamic")) {
/*  90 */       JsonArray array = get("Dynamic").getAsJsonArray();
/*  91 */       LayerContainer.DynamicLayer[] layers = new LayerContainer.DynamicLayer[array.size()];
/*  92 */       for (int i = 0; i < layers.length; i++) {
/*     */         try {
/*  94 */           layers[i] = (new DynamicLayerJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/*  95 */             .load();
/*  96 */         } catch (Throwable e) {
/*  97 */           throw new Error(String.format("Error while loading DynamicLayer #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */         } 
/*     */       } 
/* 100 */       return layers;
/*     */     } 
/* 102 */     return new LayerContainer.DynamicLayer[0];
/*     */   }
/*     */   
/*     */   protected static abstract class LayerEntryJsonLoader<T extends LayerContainer.LayerEntry>
/*     */     extends JsonLoader<SeedStringResource, T>
/*     */   {
/*     */     public LayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 109 */       super(seed.append(".LayerEntry"), dataFolder, json);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected NoiseBlockArray loadBlocks() {
/* 114 */       if (!has("Blocks")) return NoiseBlockArray.EMPTY; 
/* 115 */       return (new NoiseBlockArrayJsonLoader(this.seed, this.dataFolder, get("Blocks")))
/* 116 */         .load();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected ICoordinateCondition loadMapCondition() {
/* 121 */       return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 122 */         .load();
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class DynamicLayerJsonLoader
/*     */     extends JsonLoader<SeedStringResource, LayerContainer.DynamicLayer>
/*     */   {
/*     */     public DynamicLayerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 130 */       super(seed.append(".DynamicLayer"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LayerContainer.DynamicLayer load() {
/* 136 */       return new LayerContainer.DynamicLayer(
/* 137 */           loadEntries(), 
/* 138 */           loadMapCondition(), 
/* 139 */           loadEnvironment(), 
/* 140 */           loadOffset());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected ICoordinateCondition loadMapCondition() {
/* 146 */       return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 147 */         .load();
/*     */     }
/*     */     @Nonnull
/*     */     protected IDoubleCoordinateSupplier loadOffset() {
/*     */       IDoubleRange iDoubleRange;
/* 152 */       DoubleRange.Constant constant = DoubleRange.ZERO;
/* 153 */       NoiseProperty offsetNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 154 */       if (has("Offset")) {
/*     */         
/* 156 */         iDoubleRange = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Offset"))).load();
/* 157 */         if (has("OffsetNoise"))
/*     */         {
/* 159 */           offsetNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("OffsetNoise"))).load();
/*     */         }
/*     */       } 
/* 162 */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(iDoubleRange, offsetNoise);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected LayerContainer.DynamicLayerEntry[] loadEntries() {
/* 167 */       if (this.json == null || this.json.isJsonNull())
/* 168 */         return new LayerContainer.DynamicLayerEntry[0]; 
/* 169 */       if (this.json.isJsonObject()) {
/* 170 */         if (has("Entries")) {
/* 171 */           JsonArray array = get("Entries").getAsJsonArray();
/* 172 */           LayerContainer.DynamicLayerEntry[] entries = new LayerContainer.DynamicLayerEntry[array.size()];
/* 173 */           for (int i = 0; i < entries.length; i++) {
/*     */             try {
/* 175 */               entries[i] = (new DynamicLayerEntryJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/* 176 */                 .load();
/* 177 */             } catch (Throwable e) {
/* 178 */               throw new Error(String.format("Error while loading DynamicLayerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */             } 
/*     */           } 
/* 181 */           return entries;
/*     */         } 
/*     */         try {
/* 184 */           return new LayerContainer.DynamicLayerEntry[] { (new DynamicLayerEntryJsonLoader(this.seed, this.dataFolder, this.json))
/*     */               
/* 186 */               .load() };
/*     */         }
/* 188 */         catch (Throwable e) {
/* 189 */           throw new Error(String.format("Error while loading DynamicLayerEntry #%s", new Object[] { Integer.valueOf(0) }), e);
/*     */         } 
/*     */       } 
/*     */       
/* 193 */       throw new Error("Unknown type for dynamic Layer");
/*     */     }
/*     */     
/*     */     protected int loadEnvironment() {
/* 197 */       int environment = Integer.MIN_VALUE;
/* 198 */       if (has("Environment")) {
/* 199 */         String environmentId = get("Environment").getAsString();
/* 200 */         environment = Environment.getAssetMap().getIndex(environmentId);
/* 201 */         if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */       } 
/* 203 */       return environment;
/*     */     }
/*     */     
/*     */     protected static class DynamicLayerEntryJsonLoader extends LayerContainerJsonLoader.LayerEntryJsonLoader<LayerContainer.DynamicLayerEntry> {
/*     */       public DynamicLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 208 */         super(seed, dataFolder, json);
/*     */       }
/*     */       
/*     */       @Nonnull
/*     */       public LayerContainer.DynamicLayerEntry load()
/*     */       {
/* 214 */         return new LayerContainer.DynamicLayerEntry(
/* 215 */             loadBlocks(), 
/* 216 */             loadMapCondition()); } } } protected static class DynamicLayerEntryJsonLoader extends LayerEntryJsonLoader<LayerContainer.DynamicLayerEntry> { @Nonnull public LayerContainer.DynamicLayerEntry load() { return new LayerContainer.DynamicLayerEntry(loadBlocks(), loadMapCondition()); }
/*     */ 
/*     */     
/*     */     public DynamicLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*     */       super(seed, dataFolder, json);
/*     */     } }
/*     */ 
/*     */   
/*     */   protected static class StaticLayerJsonLoader extends JsonLoader<SeedStringResource, LayerContainer.StaticLayer> {
/*     */     public StaticLayerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 226 */       super(seed.append(".StaticLayer"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LayerContainer.StaticLayer load() {
/* 232 */       return new LayerContainer.StaticLayer(
/* 233 */           loadEntries(), 
/* 234 */           loadMapCondition(), 
/* 235 */           loadEnvironment());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected ICoordinateCondition loadMapCondition() {
/* 241 */       return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 242 */         .load();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected LayerContainer.StaticLayerEntry[] loadEntries() {
/* 247 */       if (this.json == null || this.json.isJsonNull())
/* 248 */         return new LayerContainer.StaticLayerEntry[0]; 
/* 249 */       if (this.json.isJsonObject()) {
/* 250 */         if (has("Entries")) {
/* 251 */           JsonArray array = get("Entries").getAsJsonArray();
/* 252 */           LayerContainer.StaticLayerEntry[] entries = new LayerContainer.StaticLayerEntry[array.size()];
/* 253 */           for (int i = 0; i < entries.length; i++) {
/*     */             try {
/* 255 */               entries[i] = (new StaticLayerEntryJsonLoader(this.seed.append("-" + i), this.dataFolder, array.get(i)))
/* 256 */                 .load();
/* 257 */             } catch (Throwable e) {
/* 258 */               throw new Error(String.format("Error while loading StaticLayerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */             } 
/*     */           } 
/* 261 */           return entries;
/*     */         } 
/*     */         try {
/* 264 */           return new LayerContainer.StaticLayerEntry[] { (new StaticLayerEntryJsonLoader(this.seed, this.dataFolder, this.json))
/*     */               
/* 266 */               .load() };
/*     */         }
/* 268 */         catch (Throwable e) {
/* 269 */           throw new Error(String.format("Error while loading StaticLayerEntry #%s", new Object[] { Integer.valueOf(0) }), e);
/*     */         } 
/*     */       } 
/*     */       
/* 273 */       throw new Error("Unknown type for static Layer");
/*     */     }
/*     */     
/*     */     protected int loadEnvironment() {
/* 277 */       int environment = Integer.MIN_VALUE;
/* 278 */       if (has("Environment")) {
/* 279 */         String environmentId = get("Environment").getAsString();
/* 280 */         environment = Environment.getAssetMap().getIndex(environmentId);
/* 281 */         if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */       } 
/* 283 */       return environment;
/*     */     }
/*     */     
/*     */     protected static class StaticLayerEntryJsonLoader
/*     */       extends LayerContainerJsonLoader.LayerEntryJsonLoader<LayerContainer.StaticLayerEntry> {
/*     */       public StaticLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 289 */         super(seed.append(".StaticLayerEntry"), dataFolder, json);
/*     */       }
/*     */ 
/*     */       
/*     */       @Nonnull
/*     */       public LayerContainer.StaticLayerEntry load() {
/* 295 */         return new LayerContainer.StaticLayerEntry(
/* 296 */             loadBlocks(), 
/* 297 */             loadMapCondition(), 
/* 298 */             loadMin(), 
/* 299 */             loadMax());
/*     */       }
/*     */ 
/*     */       
/*     */       @Nonnull
/*     */       protected IDoubleCoordinateSupplier loadMin() {
/* 305 */         if (!has("Min")) throw new IllegalArgumentException("Could not find minimum of static layer entry.");
/*     */         
/* 307 */         IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/* 308 */         NoiseProperty minNoise = loadMinNoise();
/* 309 */         return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */       }
/*     */       
/*     */       @Nonnull
/*     */       protected IDoubleCoordinateSupplier loadMax() {
/* 314 */         if (!has("Max")) throw new IllegalArgumentException("Could not find maximum of static layer entry.");
/*     */         
/* 316 */         IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/* 317 */         NoiseProperty maxNoise = loadMaxNoise();
/* 318 */         return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */       }
/*     */       
/*     */       @Nullable
/*     */       protected NoiseProperty loadMinNoise() {
/* 323 */         NoiseProperty minNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 324 */         if (has("MinNoise"))
/*     */         {
/* 326 */           minNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MinNoise"))).load();
/*     */         }
/* 328 */         return minNoise;
/*     */       }
/*     */       
/*     */       @Nullable
/*     */       protected NoiseProperty loadMaxNoise() {
/* 333 */         NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 334 */         if (has("MaxNoise"))
/*     */         {
/* 336 */           maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MaxNoise"))).load();
/*     */         }
/* 338 */         return maxNoise; } } } protected static class StaticLayerEntryJsonLoader extends LayerEntryJsonLoader<LayerContainer.StaticLayerEntry> { @Nullable protected NoiseProperty loadMaxNoise() { NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO; if (has("MaxNoise")) maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MaxNoise"))).load();  return maxNoise; }
/*     */ 
/*     */     
/*     */     public StaticLayerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*     */       super(seed.append(".StaticLayerEntry"), dataFolder, json);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public LayerContainer.StaticLayerEntry load() {
/*     */       return new LayerContainer.StaticLayerEntry(loadBlocks(), loadMapCondition(), loadMin(), loadMax());
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected IDoubleCoordinateSupplier loadMin() {
/*     */       if (!has("Min"))
/*     */         throw new IllegalArgumentException("Could not find minimum of static layer entry."); 
/*     */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/*     */       NoiseProperty minNoise = loadMinNoise();
/*     */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected IDoubleCoordinateSupplier loadMax() {
/*     */       if (!has("Max"))
/*     */         throw new IllegalArgumentException("Could not find maximum of static layer entry."); 
/*     */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/*     */       NoiseProperty maxNoise = loadMaxNoise();
/*     */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadMinNoise() {
/*     */       NoiseProperty minNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/*     */       if (has("MinNoise"))
/*     */         minNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("MinNoise"))).load(); 
/*     */       return minNoise;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_DEFAULT = "Default";
/*     */     public static final String KEY_DYNAMIC = "Dynamic";
/*     */     public static final String KEY_STATIC = "Static";
/*     */     public static final String KEY_ENTRY_ENTRIES = "Entries";
/*     */     public static final String KEY_ENTRY_BLOCKS = "Blocks";
/*     */     public static final String KEY_ENTRY_NOISE_MASK = "NoiseMask";
/*     */     public static final String KEY_ENTRY_DYNAMIC_OFFSET = "Offset";
/*     */     public static final String KEY_ENTRY_DYNAMIC_OFFSET_NOISE = "OffsetNoise";
/*     */     public static final String KEY_ENTRY_STATIC_MIN = "Min";
/*     */     public static final String KEY_ENTRY_STATIC_MIN_NOISE = "MinNoise";
/*     */     public static final String KEY_ENTRY_STATIC_MAX = "Max";
/*     */     public static final String KEY_ENTRY_STATIC_MAX_NOISE = "MaxNoise";
/*     */     public static final String KEY_ENVIRONMENT = "Environment";
/*     */     public static final String ERROR_NO_DEFAULT = "Could not find default material. Keyword: Default";
/*     */     public static final String ERROR_DEFAULT_INVALID = "Default block for LayerContainer could not be found! BlockType: %s";
/*     */     public static final String ERROR_FAIL_DYNAMIC_LAYER = "Error while loading DynamicLayer #%s";
/*     */     public static final String ERROR_FAIL_STATIC_LAYER = "Error while loading StaticLayer #%s";
/*     */     public static final String ERROR_NO_BLOCKS = "Could not find block data for layer entry. Keyword: Blocks";
/*     */     public static final String ERROR_UNKOWN_STATIC = "Unknown type for static Layer";
/*     */     public static final String ERROR_UNKOWN_DYNAMIC = "Unknown type for dynamic Layer";
/*     */     public static final String ERROR_FAIL_DYNAMIC_ENTRY = "Error while loading DynamicLayerEntry #%s";
/*     */     public static final String ERROR_FAIL_STATIC_ENTRY = "Error while loading StaticLayerEntry #%s";
/*     */     public static final String ERROR_STATIC_NO_MIN = "Could not find minimum of static layer entry.";
/*     */     public static final String ERROR_STATIC_NO_MAX = "Could not find maximum of static layer entry.";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\LayerContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */