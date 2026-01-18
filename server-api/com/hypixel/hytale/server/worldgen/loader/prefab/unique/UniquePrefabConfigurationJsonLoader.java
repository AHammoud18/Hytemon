/*     */ package com.hypixel.hytale.server.worldgen.loader.prefab.unique;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.loader.biome.BiomeMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.BlockPlacementMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.PrefabPatternGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.Vector2dJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.Vector3dJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.unique.UniquePrefabConfiguration;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetBlockFluidCondition;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UniquePrefabConfigurationJsonLoader
/*     */   extends JsonLoader<SeedStringResource, UniquePrefabConfiguration>
/*     */ {
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public UniquePrefabConfigurationJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/*  46 */     super(seed, dataFolder, json);
/*  47 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UniquePrefabConfiguration load() {
/*  53 */     return new UniquePrefabConfiguration(
/*  54 */         loadHeightThresholds(), 
/*  55 */         loadMask(), 
/*  56 */         loadRotations(), 
/*  57 */         loadBiomeMask(), 
/*  58 */         loadMapCondition(), 
/*  59 */         loadParent(), 
/*  60 */         loadAnchor(), 
/*  61 */         loadSpawnOffset(), 
/*  62 */         loadMaxDistance(), 
/*  63 */         loadFitHeightmap(), 
/*  64 */         loadSubmerge(), 
/*  65 */         loadOnWater(), 
/*  66 */         loadEnvironment(), 
/*  67 */         loadMaxAttempts(), 
/*  68 */         loadExclusionRadius(), 
/*  69 */         loadIsSpawn(), 
/*  70 */         loadZoneBorderExclusion(), 
/*  71 */         loadShowOnMap());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IBlockFluidCondition loadParent() {
/*     */     HashSetBlockFluidCondition hashSetBlockFluidCondition;
/*  77 */     ConstantBlockFluidCondition constantBlockFluidCondition = ConstantBlockFluidCondition.DEFAULT_TRUE;
/*  78 */     if (has("Parent")) {
/*     */       
/*  80 */       ResolvedBlockArray blockArray = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, get("Parent"))).load();
/*  81 */       LongSet biomeSet = blockArray.getEntrySet();
/*  82 */       hashSetBlockFluidCondition = new HashSetBlockFluidCondition(biomeSet);
/*     */     } 
/*  84 */     return (IBlockFluidCondition)hashSetBlockFluidCondition;
/*     */   }
/*     */   @Nullable
/*     */   protected ICoordinateRndCondition loadHeightThresholds() {
/*     */     HeightCondition heightCondition1;
/*  89 */     ICoordinateRndCondition heightCondition = null;
/*  90 */     if (has("HeightThreshold")) {
/*  91 */       JsonObject heightThresholdObject = get("HeightThreshold").getAsJsonObject();
/*     */       
/*  93 */       heightCondition1 = new HeightCondition((new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, (JsonElement)heightThresholdObject, 320)).load());
/*     */     } 
/*  95 */     return (ICoordinateRndCondition)heightCondition1;
/*     */   }
/*     */   @Nullable
/*     */   protected IIntCondition loadBiomeMask() {
/*     */     IIntCondition iIntCondition;
/* 100 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/* 101 */     if (has("BiomeMask"))
/*     */     {
/* 103 */       iIntCondition = (new BiomeMaskJsonLoader(this.seed, this.dataFolder, get("BiomeMask"), "UniquePrefab", this.zoneContext)).load();
/*     */     }
/* 105 */     return iIntCondition;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected PrefabRotation[] loadRotations() {
/* 110 */     PrefabRotation[] prefabRotations = null;
/* 111 */     if (has("Rotations")) {
/* 112 */       prefabRotations = PrefabPatternGeneratorJsonLoader.loadRotations(get("Rotations"));
/*     */     }
/* 114 */     return prefabRotations;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadMapCondition() {
/* 119 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 120 */       .load();
/*     */   }
/*     */   @Nullable
/*     */   protected BlockMaskCondition loadMask() {
/*     */     BlockPlacementMask blockPlacementMask;
/* 125 */     DefaultBlockMaskCondition defaultBlockMaskCondition = DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 126 */     if (has("Mask"))
/*     */     {
/* 128 */       blockPlacementMask = (new BlockPlacementMaskJsonLoader(this.seed, this.dataFolder, getRaw("Mask"))).load();
/*     */     }
/* 130 */     return (BlockMaskCondition)blockPlacementMask;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector2d loadAnchor() {
/* 135 */     if (!has("Anchor")) throw new IllegalArgumentException("Could not find anchor for Unique prefab generator"); 
/* 136 */     return (new Vector2dJsonLoader(this.seed, this.dataFolder, get("Anchor")))
/* 137 */       .load();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3d loadSpawnOffset() {
/* 143 */     Vector3d offset = new Vector3d(0.0D, -5000.0D, 0.0D);
/* 144 */     if (has("SpawnOffset"))
/*     */     {
/* 146 */       offset = (new Vector3dJsonLoader(this.seed, this.dataFolder, get("SpawnOffset"))).load();
/*     */     }
/* 148 */     return offset;
/*     */   }
/*     */   
/*     */   protected int loadEnvironment() {
/* 152 */     int environment = Integer.MIN_VALUE;
/* 153 */     if (has("Environment")) {
/* 154 */       String environmentId = get("Environment").getAsString();
/* 155 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 156 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/* 158 */     return environment;
/*     */   }
/*     */   
/*     */   protected boolean loadFitHeightmap() {
/* 162 */     return (has("FitHeightmap") && get("FitHeightmap").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected boolean loadSubmerge() {
/* 166 */     return mustGetBool("Submerge", Constants.DEFAULT_SUBMERGE).booleanValue();
/*     */   }
/*     */   
/*     */   protected boolean loadOnWater() {
/* 170 */     return (has("OnWater") && get("OnWater").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected double loadMaxDistance() {
/* 174 */     return has("MaxDistance") ? get("MaxDistance").getAsDouble() : 100.0D;
/*     */   }
/*     */   
/*     */   protected int loadMaxAttempts() {
/* 178 */     return has("MaxAttempts") ? get("MaxAttempts").getAsInt() : 5000;
/*     */   }
/*     */   
/*     */   protected double loadExclusionRadius() {
/* 182 */     return has("ExclusionRadius") ? get("ExclusionRadius").getAsDouble() : 50.0D;
/*     */   }
/*     */   
/*     */   protected boolean loadIsSpawn() {
/* 186 */     return (has("IsSpawn") && get("IsSpawn").getAsBoolean());
/*     */   }
/*     */   
/*     */   protected double loadZoneBorderExclusion() {
/* 190 */     return has("BorderExclusion") ? get("BorderExclusion").getAsDouble() : 25.0D;
/*     */   }
/*     */   
/*     */   protected boolean loadShowOnMap() {
/* 194 */     return (has("ShowOnMap") && get("ShowOnMap").getAsBoolean());
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_PARENT = "Parent";
/*     */     
/*     */     public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */     
/*     */     public static final String KEY_BIOME_MASK = "BiomeMask";
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */     public static final String KEY_MASK = "Mask";
/*     */     public static final String KEY_ANCHOR = "Anchor";
/*     */     public static final String KEY_FIT_HEIGHTMAP = "FitHeightmap";
/*     */     public static final String KEY_SUBMERGE = "Submerge";
/*     */     public static final String KEY_ENVIRONMENT = "Environment";
/*     */     public static final String KEY_ON_WATER = "OnWater";
/*     */     public static final String KEY_MAX_DISTANCE = "MaxDistance";
/*     */     public static final String KEY_MAX_ATTEMPTS = "MaxAttempts";
/*     */     public static final String KEY_EXCLUSION_RADIUS = "ExclusionRadius";
/*     */     public static final String KEY_IS_SPAWN = "IsSpawn";
/*     */     public static final String KEY_SPAWN_OFFSET = "SpawnOffset";
/*     */     public static final String KEY_BORDER_EXCLUSION = "BorderExclusion";
/*     */     public static final String KEY_SHOW_ON_MAP = "ShowOnMap";
/*     */     public static final String SEED_STRING_BIOME_MASK_TYPE = "UniquePrefab";
/*     */     public static final String ERROR_BIOME_ERROR_MASK = "Could not find tile / custom biome \"%s\" for biome mask. Typo or disabled biome?";
/*     */     public static final String ERROR_NO_ANCHOR = "Could not find anchor for Unique prefab generator";
/*     */     public static final String ERROR_LOADING_ENVIRONMENT = "Error while looking up environment \"%s\"!";
/*     */     public static final double DEFAULT_MAX_DISTANCE = 100.0D;
/*     */     public static final int DEFAULT_MAX_ATTEMPTS = 5000;
/*     */     public static final double DEFAULT_EXCLUSION_RADIUS = 50.0D;
/*     */     public static final double DEFAULT_ZONE_BORDER_EXCLUSION = 25.0D;
/* 227 */     public static final Boolean DEFAULT_SUBMERGE = Boolean.FALSE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefa\\unique\UniquePrefabConfigurationJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */