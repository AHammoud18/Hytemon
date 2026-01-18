/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
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
/*     */ public class WaterContainerJsonLoader
/*     */   extends JsonLoader<SeedStringResource, WaterContainer>
/*     */ {
/*     */   public WaterContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  34 */     super(seed.append(".WaterContainer"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaterContainer load() {
/*  40 */     if (has("Block")) {
/*     */ 
/*     */       
/*  43 */       String blockString = get("Block").getAsString();
/*  44 */       String blockTypeKey = blockString;
/*  45 */       int index = BlockType.getAssetMap().getIndex(blockTypeKey);
/*  46 */       if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { blockTypeKey.toString() }));
/*     */ 
/*     */ 
/*     */       
/*  50 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Height"), 0.0D)).load();
/*     */       
/*  52 */       NoiseProperty heightmapNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/*  53 */       if (has("Heightmap"))
/*     */       {
/*  55 */         heightmapNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Heightmap"))).load();
/*     */       }
/*     */       
/*  58 */       DoubleRangeNoiseSupplier height = new DoubleRangeNoiseSupplier(array, heightmapNoise);
/*     */       
/*  60 */       return new WaterContainer(new WaterContainer.Entry[] { new WaterContainer.Entry(index, 0, (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier((IDoubleRange)DoubleRange.ZERO, ConstantNoiseProperty.DEFAULT_ZERO), (IDoubleCoordinateSupplier)height, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE) });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     if (has("Fluid")) {
/*     */ 
/*     */       
/*  70 */       String fluidString = get("Fluid").getAsString();
/*  71 */       int index = Fluid.getAssetMap().getIndex(fluidString);
/*  72 */       if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { fluidString }));
/*     */ 
/*     */ 
/*     */       
/*  76 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Height"), 0.0D)).load();
/*     */       
/*  78 */       NoiseProperty heightmapNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/*  79 */       if (has("Heightmap"))
/*     */       {
/*  81 */         heightmapNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Heightmap"))).load();
/*     */       }
/*     */       
/*  84 */       DoubleRangeNoiseSupplier height = new DoubleRangeNoiseSupplier(array, heightmapNoise);
/*     */       
/*  86 */       return new WaterContainer(new WaterContainer.Entry[] { new WaterContainer.Entry(0, index, (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier((IDoubleRange)DoubleRange.ZERO, ConstantNoiseProperty.DEFAULT_ZERO), (IDoubleCoordinateSupplier)height, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE) });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return new WaterContainer(
/*  95 */         loadEntries());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private WaterContainer.Entry[] loadEntries() {
/* 102 */     if (has("Entries")) {
/* 103 */       JsonArray arr = get("Entries").getAsJsonArray();
/* 104 */       if (arr.isEmpty()) return WaterContainer.Entry.EMPTY_ARRAY; 
/* 105 */       WaterContainer.Entry[] entries = new WaterContainer.Entry[arr.size()];
/* 106 */       for (int i = 0; i < arr.size(); i++) {
/*     */         try {
/* 108 */           entries[i] = (new WaterContainerEntryJsonLoader(this.seed.append(String.format("-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, arr.get(i)))
/* 109 */             .load();
/* 110 */         } catch (Throwable e) {
/* 111 */           throw new Error(String.format("Failed to load TintContainerEntry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */         } 
/*     */       } 
/* 114 */       return entries;
/*     */     } 
/* 116 */     return WaterContainer.Entry.EMPTY_ARRAY;
/*     */   }
/*     */   public static interface Constants {
/*     */     public static final String KEY_ENTRIES = "Entries"; public static final String KEY_ENTRY_BLOCK = "Block"; public static final String ERROR_ENTRY_NO_BLOCK = "Could not find block information. Keyword: Block"; public static final String ERROR_ENTRY_FLUID_BLOCK = "Could not find BlockType for block: %s"; public static final String KEY_ENTRY_FLUID = "Fluid"; public static final String ERROR_ENTRY_NO_FLUID = "Could not find fluid information. Keyword: Fluid"; public static final String ERROR_ENTRY_FLUID_TYPE = "Could not find Fluid for fluid: %s"; public static final String KEY_ENTRY_MIN = "Min"; public static final String KEY_ENTRY_MIN_NOISE = "MinNoise"; public static final String KEY_ENTRY_MAX = "Max"; public static final String KEY_ENTRY_MAX_NOISE = "MaxNoise"; public static final String KEY_ENTRY_NOISE_MASK = "NoiseMask"; public static final String ERROR_ENTRY_NO_MAX = "Could not find maximum of water container entry."; }
/*     */   
/*     */   private static class WaterContainerEntryJsonLoader extends JsonLoader<SeedStringResource, WaterContainer.Entry> { public WaterContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 122 */       super(seed.append(".Entry"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public WaterContainer.Entry load() {
/*     */       try {
/* 129 */         if (has("Fluid")) {
/* 130 */           String fluidString = get("Fluid").getAsString();
/* 131 */           int index = Fluid.getAssetMap().getIndex(fluidString);
/* 132 */           if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { fluidString }));
/*     */           
/* 134 */           return new WaterContainer.Entry(0, index, 
/*     */ 
/*     */               
/* 137 */               loadMin(), loadMax(), 
/* 138 */               loadNoiseMask());
/* 139 */         }  if (has("Block")) {
/* 140 */           String blockString = get("Block").getAsString();
/* 141 */           String blockTypeKey = blockString;
/* 142 */           int index = BlockType.getAssetMap().getIndex(blockTypeKey);
/* 143 */           if (index == Integer.MIN_VALUE) throw new Error(String.format("Could not find Fluid for fluid: %s", new Object[] { blockTypeKey.toString() }));
/*     */           
/* 145 */           return new WaterContainer.Entry(index, 0, 
/*     */ 
/*     */               
/* 148 */               loadMin(), loadMax(), 
/* 149 */               loadNoiseMask());
/*     */         } 
/* 151 */         throw new IllegalArgumentException("Could not find fluid information. Keyword: Fluid");
/*     */       }
/* 153 */       catch (Error e) {
/* 154 */         throw new Error("Failed to load water container.", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private IDoubleCoordinateSupplier loadMin() {
/* 161 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Min"), 0.0D)).load();
/* 162 */       NoiseProperty minNoise = loadNoise("MinNoise");
/* 163 */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, minNoise);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private IDoubleCoordinateSupplier loadMax() {
/* 168 */       if (!has("Max")) throw new IllegalArgumentException("Could not find maximum of water container entry.");
/*     */       
/* 170 */       IDoubleRange array = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Max"), 0.0D)).load();
/* 171 */       NoiseProperty maxNoise = loadNoise("MaxNoise");
/* 172 */       return (IDoubleCoordinateSupplier)new DoubleRangeNoiseSupplier(array, maxNoise);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private NoiseProperty loadNoise(String key) {
/* 177 */       NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 178 */       if (has(key))
/*     */       {
/* 180 */         maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get(key))).load();
/*     */       }
/* 182 */       return maxNoise;
/*     */     }
/*     */     @Nonnull
/*     */     private ICoordinateCondition loadNoiseMask() {
/*     */       ICoordinateCondition iCoordinateCondition;
/* 187 */       DefaultCoordinateCondition defaultCoordinateCondition = DefaultCoordinateCondition.DEFAULT_TRUE;
/* 188 */       if (has("NoiseMask"))
/*     */       {
/* 190 */         iCoordinateCondition = (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask"))).load();
/*     */       }
/* 192 */       return iCoordinateCondition;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\WaterContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */