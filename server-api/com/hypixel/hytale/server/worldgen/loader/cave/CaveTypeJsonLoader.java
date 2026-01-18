/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightThresholdCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.FloatRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.PointGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IFloatRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveBiomeMaskFlags;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.BlockPlacementMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.flag.Int2FlagsCondition;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CaveTypeJsonLoader extends JsonLoader<SeedStringResource, CaveType> {
/*     */   protected final Path caveFolder;
/*     */   protected final String name;
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public CaveTypeJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, Path caveFolder, String name, ZoneFileContext zoneContext) {
/*  43 */     super(seed.append(".CaveType"), dataFolder, json);
/*  44 */     this.caveFolder = caveFolder;
/*  45 */     this.name = name;
/*  46 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveType load() {
/*  52 */     IPointGenerator pointGenerator = loadEntryPointGenerator();
/*  53 */     return new CaveType(this.name, 
/*     */         
/*  55 */         loadEntryNodeType(), 
/*  56 */         loadYaw(), loadPitch(), 
/*  57 */         loadDepth(), 
/*  58 */         loadHeightFactors(), pointGenerator, 
/*     */         
/*  60 */         loadBiomeMask(), 
/*  61 */         loadBlockMask(), 
/*  62 */         loadMapCondition(), 
/*  63 */         loadHeightCondition(), 
/*  64 */         loadFixedEntryHeight(), 
/*  65 */         loadFixedEntryHeightNoise(), 
/*  66 */         loadFluidLevel(), 
/*  67 */         loadEnvironment(), 
/*  68 */         loadSurfaceLimited(), 
/*  69 */         loadSubmerge(), 
/*  70 */         loadMaximumSize(pointGenerator));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IFloatRange loadYaw() {
/*  76 */     return (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("Yaw"), -180.0F, 180.0F, deg -> deg * 0.017453292F))
/*  77 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IFloatRange loadPitch() {
/*  82 */     return (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("Pitch"), -15.0F, deg -> deg * 0.017453292F))
/*  83 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IFloatRange loadDepth() {
/*  88 */     return (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("Depth"), 80.0F))
/*  89 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IHeightThresholdInterpreter loadHeightFactors() {
/*  94 */     return (new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightRadiusFactor"), 320))
/*  95 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType loadEntryNodeType() {
/* 100 */     if (!has("Entry")) throw new IllegalArgumentException("\"Entry\" is not defined. Define an entry node type"); 
/* 101 */     String entryNodeTypeString = get("Entry").getAsString();
/* 102 */     CaveNodeTypeStorage caveNodeTypeStorage = new CaveNodeTypeStorage(this.seed, this.dataFolder, this.caveFolder, this.zoneContext);
/* 103 */     return caveNodeTypeStorage.loadCaveNodeType(entryNodeTypeString);
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadHeightCondition() {
/*     */     HeightThresholdCoordinateCondition heightThresholdCoordinateCondition;
/* 108 */     DefaultCoordinateCondition defaultCoordinateCondition = DefaultCoordinateCondition.DEFAULT_TRUE;
/* 109 */     if (has("HeightThreshold")) {
/*     */       
/* 111 */       IHeightThresholdInterpreter interpreter = (new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load();
/* 112 */       heightThresholdCoordinateCondition = new HeightThresholdCoordinateCondition(interpreter);
/*     */     } 
/* 114 */     return (ICoordinateCondition)heightThresholdCoordinateCondition;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IPointGenerator loadEntryPointGenerator() {
/* 119 */     if (!has("EntryPoints")) throw new IllegalArgumentException("\"EntryPoints\" is not defined, no spawn information for caves available"); 
/* 120 */     return (new PointGeneratorJsonLoader(this.seed, this.dataFolder, get("EntryPoints")))
/* 121 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Int2FlagsCondition loadBiomeMask() {
/* 126 */     Int2FlagsCondition mask = CaveBiomeMaskFlags.DEFAULT_ALLOW;
/* 127 */     if (has("BiomeMask")) {
/*     */       
/* 129 */       ZoneFileContext context = this.zoneContext.matchContext(this.json, "BiomeMask");
/*     */ 
/*     */       
/* 132 */       mask = (new CaveBiomeMaskJsonLoader(this.seed, this.dataFolder, get("BiomeMask"), context)).load();
/*     */     } 
/* 134 */     return mask;
/*     */   }
/*     */   @Nullable
/*     */   protected BlockMaskCondition loadBlockMask() {
/*     */     BlockPlacementMask blockPlacementMask;
/* 139 */     DefaultBlockMaskCondition defaultBlockMaskCondition = DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 140 */     if (has("BlockMask"))
/*     */     {
/* 142 */       blockPlacementMask = (new BlockPlacementMaskJsonLoader(this.seed, this.dataFolder, getRaw("BlockMask"))).load();
/*     */     }
/* 144 */     return (BlockMaskCondition)blockPlacementMask;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 149 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 150 */       .load();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IDoubleRange loadFixedEntryHeight() {
/* 155 */     IDoubleRange fixedEntryHeight = null;
/* 156 */     if (has("FixedEntryHeight"))
/*     */     {
/* 158 */       fixedEntryHeight = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("FixedEntryHeight"), 0.0D)).load();
/*     */     }
/* 160 */     return fixedEntryHeight;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected NoiseProperty loadFixedEntryHeightNoise() {
/* 165 */     NoiseProperty maxNoise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 166 */     if (has("FixedEntryHeightNoise"))
/*     */     {
/* 168 */       maxNoise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("FixedEntryHeightNoise"))).load();
/*     */     }
/* 170 */     return maxNoise;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveType.FluidLevel loadFluidLevel() {
/* 175 */     CaveType.FluidLevel fluidLevel = CaveType.FluidLevel.EMPTY;
/* 176 */     if (has("FluidLevel"))
/*     */     {
/* 178 */       fluidLevel = (new FluidLevelJsonLoader(this.seed, this.dataFolder, get("FluidLevel"))).load();
/*     */     }
/* 180 */     return fluidLevel;
/*     */   }
/*     */   
/*     */   protected int loadEnvironment() {
/* 184 */     int environment = Integer.MIN_VALUE;
/* 185 */     if (has("Environment")) {
/* 186 */       String environmentId = get("Environment").getAsString();
/* 187 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 188 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/* 190 */     return environment;
/*     */   }
/*     */   
/*     */   protected boolean loadSurfaceLimited() {
/* 194 */     return (!has("SurfaceLimited") || get("SurfaceLimited").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected boolean loadSubmerge() {
/* 198 */     return mustGetBool("Submerge", Constants.DEFAULT_SUBMERGE).booleanValue();
/*     */   }
/*     */   
/*     */   protected double loadMaximumSize(@Nonnull IPointGenerator pointGenerator) {
/* 202 */     return has("MaximumSize") ? get("MaximumSize").getAsLong() : MathUtil.fastFloor(pointGenerator.getInterval());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_YAW = "Yaw";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_PITCH = "Pitch";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_DEPTH = "Depth";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_HEIGHT_RADIUS_FACTOR = "HeightRadiusFactor";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_ENTRY = "Entry";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_ENTRY_POINTS = "EntryPoints";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_HEIGHT_THRESHOLDS = "HeightThreshold";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_BIOME_MASK = "BiomeMask";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_BLOCK_MASK = "BlockMask";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_FIXED_ENTRY_HEIGHT = "FixedEntryHeight";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_FIXED_ENTRY_HEIGHT_NOISE = "FixedEntryHeightNoise";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_FLUID_LEVEL = "FluidLevel";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_SURFACE_LIMITTED = "SurfaceLimited";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_SUBMERGE = "Submerge";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_MAXIMUM_SIZE = "MaximumSize";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String KEY_ENVIRONMENT = "Environment";
/*     */ 
/*     */ 
/*     */     
/* 279 */     public static final Boolean DEFAULT_SUBMERGE = Boolean.FALSE;
/*     */     public static final String ERROR_NO_ENTRY = "\"Entry\" is not defined. Define an entry node type";
/*     */     public static final String ERROR_NO_ENTRY_POINTS = "\"EntryPoints\" is not defined, no spawn information for caves available";
/*     */     public static final String ERROR_LOADING_ENVIRONMENT = "Error while looking up environment \"%s\"!";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveTypeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */