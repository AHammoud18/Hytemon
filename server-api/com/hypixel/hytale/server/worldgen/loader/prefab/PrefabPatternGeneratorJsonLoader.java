/*     */ package com.hypixel.hytale.server.worldgen.loader.prefab;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.PointGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPatternGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetBlockFluidCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.function.ConstantCoordinateDoubleSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.function.ICoordinateDoubleSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.function.RandomCoordinateDoubleSupplier;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PrefabPatternGeneratorJsonLoader extends JsonLoader<SeedStringResource, PrefabPatternGenerator> {
/*     */   public PrefabPatternGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, FileLoadingContext context) {
/*  42 */     super(seed.append(".PrefabPatternGenerator"), dataFolder, json);
/*  43 */     this.context = context;
/*     */   }
/*     */   private final FileLoadingContext context;
/*     */   
/*     */   @Nonnull
/*     */   public PrefabPatternGenerator load() {
/*  49 */     IHeightThresholdInterpreter heightThresholds = loadHeightThresholds();
/*  50 */     return new PrefabPatternGenerator(this.seed
/*  51 */         .hashCode(), 
/*  52 */         loadCategory(), 
/*  53 */         loadPattern(), 
/*  54 */         loadHeightCondition(heightThresholds), heightThresholds, 
/*     */         
/*  56 */         loadMask(), 
/*  57 */         loadMapCondition(), 
/*  58 */         loadParent(), 
/*  59 */         loadRotations(), 
/*  60 */         loadDisplacement(), 
/*  61 */         loadFitHeightmap(), 
/*  62 */         loadOnWater(), 
/*  63 */         loadDeepSearch(heightThresholds), 
/*  64 */         loadSubmerge(), 
/*  65 */         loadMaxSize(), 
/*  66 */         loadExclusionRadius());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected IPointGenerator loadPattern() {
/*  72 */     if (!has("GridGenerator")) throw new IllegalArgumentException("Could not find point generator to place prefabs at! Keyword: GridGenerator"); 
/*  73 */     return (new PointGeneratorJsonLoader(this.seed, this.dataFolder, get("GridGenerator"))).load();
/*     */   }
/*     */   
/*     */   protected PrefabCategory loadCategory() {
/*  77 */     String category = mustGetString("Category", "");
/*     */     
/*  79 */     if (category.isEmpty()) {
/*  80 */       return PrefabCategory.NONE;
/*     */     }
/*     */     
/*  83 */     if (!this.context.getPrefabCategories().contains(category)) {
/*  84 */       LogUtil.getLogger().at(Level.WARNING).log("Could not find prefab category: %s, defaulting to None", category);
/*  85 */       return PrefabCategory.NONE;
/*     */     } 
/*     */     
/*  88 */     return (PrefabCategory)this.context.getPrefabCategories().get(category);
/*     */   }
/*     */   @Nonnull
/*     */   protected IBlockFluidCondition loadParent() {
/*     */     HashSetBlockFluidCondition hashSetBlockFluidCondition;
/*  93 */     ConstantBlockFluidCondition constantBlockFluidCondition = ConstantBlockFluidCondition.DEFAULT_TRUE;
/*  94 */     if (has("Parent")) {
/*     */       
/*  96 */       ResolvedBlockArray blockArray = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Parent"))).load();
/*  97 */       LongSet biomeSet = blockArray.getEntrySet();
/*  98 */       hashSetBlockFluidCondition = new HashSetBlockFluidCondition(biomeSet);
/*     */     } 
/* 100 */     return (IBlockFluidCondition)hashSetBlockFluidCondition;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IHeightThresholdInterpreter loadHeightThresholds() {
/* 105 */     IHeightThresholdInterpreter heightThreshold = null;
/* 106 */     if (has("HeightThreshold"))
/*     */     {
/* 108 */       heightThreshold = (new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load();
/*     */     }
/* 110 */     return heightThreshold;
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateRndCondition loadHeightCondition(@Nullable IHeightThresholdInterpreter thresholdInterpreter) {
/*     */     HeightCondition heightCondition;
/* 115 */     DefaultCoordinateRndCondition defaultCoordinateRndCondition = DefaultCoordinateRndCondition.DEFAULT_TRUE;
/* 116 */     if (thresholdInterpreter != null) {
/* 117 */       heightCondition = new HeightCondition(thresholdInterpreter);
/*     */     }
/* 119 */     return (ICoordinateRndCondition)heightCondition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 124 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 125 */       .load();
/*     */   }
/*     */   @Nullable
/*     */   protected BlockMaskCondition loadMask() {
/*     */     BlockPlacementMask blockPlacementMask;
/* 130 */     DefaultBlockMaskCondition defaultBlockMaskCondition = DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 131 */     if (has("Mask"))
/*     */     {
/* 133 */       blockPlacementMask = (new BlockPlacementMaskJsonLoader(this.seed, this.dataFolder, getRaw("Mask"))).load();
/*     */     }
/* 135 */     return (BlockMaskCondition)blockPlacementMask;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected PrefabRotation[] loadRotations() {
/* 140 */     PrefabRotation[] prefabRotations = null;
/* 141 */     if (has("Rotations")) {
/* 142 */       prefabRotations = loadRotations(get("Rotations"));
/*     */     }
/* 144 */     return prefabRotations;
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateDoubleSupplier loadDisplacement() {
/*     */     RandomCoordinateDoubleSupplier randomCoordinateDoubleSupplier;
/* 149 */     ConstantCoordinateDoubleSupplier constantCoordinateDoubleSupplier = ConstantCoordinateDoubleSupplier.DEFAULT_ZERO;
/* 150 */     if (has("Displacement"))
/*     */     {
/* 152 */       randomCoordinateDoubleSupplier = new RandomCoordinateDoubleSupplier((new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Displacement"), 0.0D)).load());
/*     */     }
/* 154 */     return (ICoordinateDoubleSupplier)randomCoordinateDoubleSupplier;
/*     */   }
/*     */   
/*     */   protected boolean loadFitHeightmap() {
/* 158 */     return (has("FitHeightmap") && get("FitHeightmap").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected boolean loadOnWater() {
/* 162 */     return (has("OnWater") && get("OnWater").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected boolean loadDeepSearch(@Nonnull IHeightThresholdInterpreter interpreter) {
/* 166 */     boolean deepSearch = (has("DeepSearch") && get("DeepSearch").getAsBoolean());
/* 167 */     if (deepSearch && interpreter == null) throw new IllegalArgumentException("DeepSearch is enabled but HeightThreshold is not set!"); 
/* 168 */     return deepSearch;
/*     */   }
/*     */   
/*     */   protected boolean loadSubmerge() {
/* 172 */     return mustGetBool("Submerge", Constants.DEFAULT_SUBMERGE).booleanValue();
/*     */   }
/*     */   
/*     */   protected int loadMaxSize() {
/* 176 */     return mustGetNumber("MaxSize", Constants.DEFAULT_MAX_SIZE).intValue();
/*     */   }
/*     */   
/*     */   protected int loadExclusionRadius() {
/* 180 */     return mustGetNumber("ExclusionRadius", Constants.DEFAULT_EXCLUSION_RADIUS).intValue();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static PrefabRotation[] loadRotations(@Nullable JsonElement element) {
/* 185 */     if (element == null) {
/* 186 */       return null;
/*     */     }
/*     */     
/* 189 */     PrefabRotation[] prefabRotations = null;
/*     */     
/* 191 */     if (element.isJsonArray()) {
/* 192 */       JsonArray array = element.getAsJsonArray();
/* 193 */       if (array.size() <= 0) throw new IllegalArgumentException("Array for rotations must be greater than 0 or left away to allow random rotation."); 
/* 194 */       prefabRotations = new PrefabRotation[array.size()];
/* 195 */       for (int i = 0; i < prefabRotations.length; i++) {
/* 196 */         String name = array.get(i).getAsString();
/*     */         try {
/* 198 */           prefabRotations[i] = PrefabRotation.valueOf(name);
/* 199 */         } catch (Throwable e) {
/* 200 */           throw new Error("Could not find rotation \"" + name + "\". Allowed: " + Arrays.toString(PrefabRotation.VALUES));
/*     */         } 
/*     */       } 
/* 203 */     } else if (element.isJsonPrimitive()) {
/* 204 */       prefabRotations = new PrefabRotation[1];
/* 205 */       String name = element.getAsString();
/*     */       try {
/* 207 */         prefabRotations[0] = PrefabRotation.valueOf(name);
/* 208 */       } catch (Throwable e) {
/* 209 */         throw new Error("Could not find rotation \"" + name + "\". Allowed: " + Arrays.toString(PrefabRotation.VALUES));
/*     */       } 
/*     */     } else {
/* 212 */       throw new IllegalArgumentException("rotations is not an array nor a string, other types are not supported! Given: " + String.valueOf(element));
/*     */     } 
/*     */     
/* 215 */     return prefabRotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_GRID_GENERATOR = "GridGenerator";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_PARENT = "Parent";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_MASK = "Mask";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_ROTATIONS = "Rotations";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_DISPLACEMENT = "Displacement";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_FIT_HEIGHTMAP = "FitHeightmap";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_ON_WATER = "OnWater";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_DEEP_SEARCH = "DeepSearch";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_SUBMERGE = "Submerge";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_MAX_SIZE = "MaxSize";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_EXCLUSION_RADIUS = "ExclusionRadius";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_CATEGORY = "Category";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String ERROR_NO_GRID_GENERATOR = "Could not find point generator to place prefabs at! Keyword: GridGenerator";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String ERROR_DEEP_SEARCH = "DeepSearch is enabled but HeightThreshold is not set!";
/*     */ 
/*     */ 
/*     */     
/* 289 */     public static final Boolean DEFAULT_SUBMERGE = Boolean.FALSE;
/* 290 */     public static final Integer DEFAULT_MAX_SIZE = Integer.valueOf(5);
/* 291 */     public static final Integer DEFAULT_EXCLUSION_RADIUS = Integer.valueOf(0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefab\PrefabPatternGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */