/*     */ package com.hypixel.hytale.server.worldgen.loader.container;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetBlockFluidCondition;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
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
/*     */ public class CoverContainerEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, CoverContainer.CoverContainerEntry>
/*     */ {
/*     */   public CoverContainerEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  60 */     super(seed.append(".CoverContainerEntry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CoverContainer.CoverContainerEntry load() {
/*  66 */     return new CoverContainer.CoverContainerEntry(
/*  67 */         loadEntries(), 
/*  68 */         loadMapCondition(), 
/*  69 */         loadHeightCondition(), 
/*  70 */         loadParents(), 
/*  71 */         loadDensity(), 
/*  72 */         loadOnWater());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<CoverContainer.CoverContainerEntry.CoverContainerEntryPart> loadEntries() {
/*  78 */     if (!has("Type")) throw new IllegalArgumentException("Could not find type array for cover container! Keyword: Type");
/*     */     
/*  80 */     ResolvedBlockArray types = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Type"))).load();
/*  81 */     int[] offsets = loadOffsetArray(types.size());
/*  82 */     JsonArray weights = has("Weight") ? get("Weight").getAsJsonArray() : null;
/*  83 */     if (weights != null && weights.size() != types.size()) throw new IllegalArgumentException("Weight array size does not equal size of types array"); 
/*  84 */     WeightedMap.Builder<CoverContainer.CoverContainerEntry.CoverContainerEntryPart> builder = WeightedMap.builder((Object[])CoverContainer.CoverContainerEntry.CoverContainerEntryPart.EMPTY_ARRAY);
/*  85 */     for (int i = 0; i < types.size(); i++) {
/*  86 */       BlockFluidEntry blockEntry = types.getEntries()[i];
/*  87 */       int offset = offsets[i];
/*  88 */       double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/*  89 */       CoverContainer.CoverContainerEntry.CoverContainerEntryPart entry = new CoverContainer.CoverContainerEntry.CoverContainerEntryPart(blockEntry, offset);
/*  90 */       builder.put(entry, weight);
/*     */     } 
/*  92 */     if (builder.size() <= 0) throw new IllegalArgumentException("There are no blocks in this cover container!"); 
/*  93 */     return builder.build();
/*     */   }
/*     */   
/*     */   protected int[] loadOffsetArray(int length) {
/*  97 */     JsonElement offsetElement = get("Offset");
/*  98 */     int[] offsets = new int[length];
/*  99 */     if (offsetElement == null || offsetElement.isJsonNull()) {
/* 100 */       Arrays.fill(offsets, 0);
/* 101 */     } else if (offsetElement.isJsonArray()) {
/* 102 */       JsonArray offsetArray = offsetElement.getAsJsonArray();
/* 103 */       if (offsetArray.size() != length) throw new IllegalArgumentException("Offset array size does not equal size of types array"); 
/* 104 */       for (int i = 0; i < length; i++) {
/* 105 */         offsets[i] = offsetArray.get(i).getAsInt();
/*     */       }
/*     */     } else {
/* 108 */       int offset = offsetElement.getAsInt();
/* 109 */       Arrays.fill(offsets, offset);
/*     */     } 
/* 111 */     return offsets;
/*     */   }
/*     */   
/*     */   protected double loadDensity() {
/* 115 */     double density = 1.0D;
/* 116 */     if (has("Density")) {
/* 117 */       density = get("Density").getAsDouble();
/*     */     }
/* 119 */     return density;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 124 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 125 */       .load();
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateRndCondition loadHeightCondition() {
/*     */     HeightCondition heightCondition;
/* 130 */     DefaultCoordinateRndCondition defaultCoordinateRndCondition = DefaultCoordinateRndCondition.DEFAULT_TRUE;
/* 131 */     if (has("HeightThreshold"))
/*     */     {
/* 133 */       heightCondition = new HeightCondition((new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load());
/*     */     }
/* 135 */     return (ICoordinateRndCondition)heightCondition;
/*     */   }
/*     */   @Nonnull
/*     */   protected IBlockFluidCondition loadParents() {
/*     */     HashSetBlockFluidCondition hashSetBlockFluidCondition;
/* 140 */     ConstantBlockFluidCondition constantBlockFluidCondition = ConstantBlockFluidCondition.DEFAULT_TRUE;
/* 141 */     if (has("Parent")) {
/*     */       
/* 143 */       ResolvedBlockArray blockArray = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Parent"))).load();
/* 144 */       LongSet biomeSet = blockArray.getEntrySet();
/* 145 */       hashSetBlockFluidCondition = new HashSetBlockFluidCondition(biomeSet);
/*     */     } 
/* 147 */     return (IBlockFluidCondition)hashSetBlockFluidCondition;
/*     */   }
/*     */   
/*     */   protected boolean loadOnWater() {
/* 151 */     return (has("OnWater") && get("OnWater").getAsBoolean());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\CoverContainerJsonLoader$CoverContainerEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */