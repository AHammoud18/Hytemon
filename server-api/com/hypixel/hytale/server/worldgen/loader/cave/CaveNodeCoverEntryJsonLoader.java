/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
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
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.FilteredBlockFluidCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetBlockFluidCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.RandomCoordinateCondition;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CaveNodeCoverEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, CaveNodeType.CaveNodeCoverEntry>
/*     */ {
/*  33 */   private static final IBlockFluidCondition DEFAULT_PARENT_MASK = (IBlockFluidCondition)new FilteredBlockFluidCondition(0, (IBlockFluidCondition)ConstantBlockFluidCondition.DEFAULT_TRUE);
/*     */   
/*     */   public CaveNodeCoverEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  36 */     super(seed.append(".CaveNodeCoverEntry"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeType.CaveNodeCoverEntry load() {
/*  42 */     return new CaveNodeType.CaveNodeCoverEntry(
/*  43 */         loadEntries(), 
/*  44 */         loadHeightCondition(), 
/*  45 */         loadMapCondition(), 
/*  46 */         loadDensityCondition(), 
/*  47 */         loadParentCondition(), 
/*  48 */         loadAnchorType());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<CaveNodeType.CaveNodeCoverEntry.Entry> loadEntries() {
/*  54 */     if (!has("Type")) throw new IllegalArgumentException("Could not find type array for cave cover container! Keyword: Type");
/*     */     
/*  56 */     ResolvedBlockArray types = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Type"))).load();
/*  57 */     JsonArray weights = has("Weight") ? get("Weight").getAsJsonArray() : null;
/*  58 */     if (weights != null && weights.size() != types.size()) throw new IllegalArgumentException("Weight array size does not equal size of types array"); 
/*  59 */     WeightedMap.Builder<CaveNodeType.CaveNodeCoverEntry.Entry> builder = WeightedMap.builder((Object[])CaveNodeType.CaveNodeCoverEntry.Entry.EMPTY_ARRAY);
/*  60 */     for (int i = 0; i < types.size(); i++) {
/*  61 */       BlockFluidEntry blockEntry = types.getEntries()[i];
/*  62 */       int offset = loadOffset();
/*  63 */       double weight = (weights == null) ? 1.0D : weights.get(i).getAsDouble();
/*  64 */       CaveNodeType.CaveNodeCoverEntry.Entry entry = new CaveNodeType.CaveNodeCoverEntry.Entry(blockEntry, offset);
/*  65 */       builder.put(entry, weight);
/*     */     } 
/*  67 */     if (builder.size() <= 0) throw new IllegalArgumentException("There are no blocks in this cover container!"); 
/*  68 */     return builder.build();
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateRndCondition loadHeightCondition() {
/*     */     HeightCondition heightCondition;
/*  73 */     DefaultCoordinateRndCondition defaultCoordinateRndCondition = DefaultCoordinateRndCondition.DEFAULT_TRUE;
/*  74 */     if (has("HeightThreshold"))
/*     */     {
/*  76 */       heightCondition = new HeightCondition((new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load());
/*     */     }
/*  78 */     return (ICoordinateRndCondition)heightCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/*  83 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/*  84 */       .load();
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadDensityCondition() {
/*     */     RandomCoordinateCondition randomCoordinateCondition;
/*  89 */     DefaultCoordinateCondition defaultCoordinateCondition = DefaultCoordinateCondition.DEFAULT_TRUE;
/*  90 */     if (has("Density")) {
/*  91 */       randomCoordinateCondition = new RandomCoordinateCondition(get("Density").getAsDouble());
/*     */     }
/*  93 */     return (ICoordinateCondition)randomCoordinateCondition;
/*     */   }
/*     */   @Nonnull
/*     */   protected IBlockFluidCondition loadParentCondition() {
/*     */     HashSetBlockFluidCondition hashSetBlockFluidCondition;
/*  98 */     IBlockFluidCondition parentMask = DEFAULT_PARENT_MASK;
/*  99 */     if (has("Parent")) {
/*     */       
/* 101 */       ResolvedBlockArray blockArray = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Parent"))).load();
/* 102 */       LongSet blockSet = blockArray.getEntrySet();
/* 103 */       hashSetBlockFluidCondition = new HashSetBlockFluidCondition(blockSet);
/*     */     } 
/* 105 */     return (IBlockFluidCondition)hashSetBlockFluidCondition;
/*     */   }
/*     */   
/*     */   protected int loadOffset() {
/* 109 */     int offset = 0;
/* 110 */     if (has("Offset")) {
/* 111 */       offset = get("Offset").getAsInt();
/*     */     }
/* 113 */     return offset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeCoverType loadAnchorType() {
/* 118 */     CaveNodeType.CaveNodeCoverType anchorType = CaveNodeType.CaveNodeCoverType.FLOOR;
/* 119 */     if (has("AnchorType")) {
/* 120 */       anchorType = CaveNodeType.CaveNodeCoverType.valueOf(get("AnchorType").getAsString());
/*     */     }
/* 122 */     return anchorType;
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_TYPE = "Type";
/*     */     public static final String KEY_WEIGHT = "Weight";
/*     */     public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */     public static final String KEY_DENSITY = "Density";
/*     */     public static final String KEY_OFFSET = "Offset";
/*     */     public static final String KEY_PARENT = "Parent";
/*     */     public static final String KEY_ANCHOR_TYPE = "AnchorType";
/*     */     public static final String ERROR_NO_TYPE = "Could not find type array for cave cover container! Keyword: Type";
/*     */     public static final String ERROR_NO_ENTRIES = "There are no blocks in this cover container!";
/*     */     public static final String ERROR_WEIGHTS_ARRAY_SIZE = "Weight array size does not equal size of types array";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveNodeCoverEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */