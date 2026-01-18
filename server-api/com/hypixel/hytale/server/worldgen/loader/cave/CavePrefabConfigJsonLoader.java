/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoiseMaskConditionJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.supplier.ConstantDoubleCoordinateHashSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRangeCoordinateHashSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateHashSupplier;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CavePrefabPlacement;
/*     */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.loader.biome.BiomeMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.prefab.BlockPlacementMaskJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CavePrefabConfigJsonLoader extends JsonLoader<SeedStringResource, CavePrefabContainer.CavePrefabEntry.CavePrefabConfig> {
/*     */   public CavePrefabConfigJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneContext) {
/*  37 */     super(seed.append(".CavePrefabConfig"), dataFolder, json);
/*  38 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CavePrefabContainer.CavePrefabEntry.CavePrefabConfig load() {
/*  44 */     return new CavePrefabContainer.CavePrefabEntry.CavePrefabConfig(
/*  45 */         loadRotations(), 
/*  46 */         loadPlacement(), 
/*  47 */         loadBiomeMask(), 
/*  48 */         loadBlockMask(), 
/*  49 */         loadIterations(), 
/*  50 */         loadDisplacementSupplier(), 
/*  51 */         loadNoiseCondition(), 
/*  52 */         loadHeightCondition());
/*     */   }
/*     */   private final ZoneFileContext zoneContext;
/*     */   
/*     */   @Nonnull
/*     */   protected PrefabRotation[] loadRotations() {
/*  58 */     PrefabRotation[] prefabRotations = PrefabRotation.VALUES;
/*  59 */     if (has("Rotations")) {
/*  60 */       JsonElement element = get("Rotations");
/*  61 */       if (element.isJsonArray()) {
/*  62 */         JsonArray array = element.getAsJsonArray();
/*  63 */         if (array.size() <= 0) throw new IllegalArgumentException("Array for rotations must have at least one entry or left away to allow random rotation"); 
/*  64 */         prefabRotations = new PrefabRotation[array.size()];
/*  65 */         for (int i = 0; i < prefabRotations.length; i++) {
/*  66 */           String name = array.get(i).getAsString();
/*     */           try {
/*  68 */             prefabRotations[i] = PrefabRotation.valueOf(name);
/*  69 */           } catch (Throwable e) {
/*  70 */             throw new Error(String.format(Constants.ERROR_ROTATIONS_UNKOWN, new Object[] { name }));
/*     */           } 
/*     */         } 
/*  73 */       } else if (element.isJsonPrimitive()) {
/*  74 */         prefabRotations = new PrefabRotation[1];
/*  75 */         String name = element.getAsString();
/*     */         try {
/*  77 */           prefabRotations[0] = PrefabRotation.valueOf(name);
/*  78 */         } catch (Throwable e) {
/*  79 */           throw new Error(String.format(Constants.ERROR_ROTATIONS_UNKOWN, new Object[] { name }));
/*     */         } 
/*     */       } else {
/*  82 */         throw new Error(String.format("\"Rotations\" is not an array nor a string, other types are not supported! Given: %s", new Object[] { element }));
/*     */       } 
/*     */     } 
/*  85 */     return prefabRotations;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CavePrefabPlacement loadPlacement() {
/*  90 */     CavePrefabPlacement placement = CavePrefabPlacement.DEFAULT;
/*  91 */     if (has("Placement")) {
/*  92 */       placement = CavePrefabPlacement.valueOf(get("Placement").getAsString());
/*     */     }
/*  94 */     return placement;
/*     */   }
/*     */   @Nullable
/*     */   protected IIntCondition loadBiomeMask() {
/*     */     IIntCondition iIntCondition;
/*  99 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/* 100 */     if (has("BiomeMask")) {
/*     */       
/* 102 */       ZoneFileContext context = this.zoneContext.matchContext(this.json, "BiomeMask");
/*     */ 
/*     */       
/* 105 */       iIntCondition = (new BiomeMaskJsonLoader(this.seed, this.dataFolder, get("BiomeMask"), "Prefab", context)).load();
/*     */     } 
/* 107 */     return iIntCondition;
/*     */   }
/*     */   @Nullable
/*     */   protected BlockMaskCondition loadBlockMask() {
/*     */     BlockPlacementMask blockPlacementMask;
/* 112 */     DefaultBlockMaskCondition defaultBlockMaskCondition = DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 113 */     if (has("Mask"))
/*     */     {
/* 115 */       blockPlacementMask = (new BlockPlacementMaskJsonLoader(this.seed, this.dataFolder, getRaw("Mask"))).load();
/*     */     }
/* 117 */     return (BlockMaskCondition)blockPlacementMask;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IDoubleRange loadIterations() {
/* 122 */     return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Iterations"), 5.0D))
/* 123 */       .load();
/*     */   }
/*     */   @Nonnull
/*     */   protected IDoubleCoordinateHashSupplier loadDisplacementSupplier() {
/*     */     DoubleRangeCoordinateHashSupplier doubleRangeCoordinateHashSupplier;
/* 128 */     ConstantDoubleCoordinateHashSupplier constantDoubleCoordinateHashSupplier = ConstantDoubleCoordinateHashSupplier.ZERO;
/* 129 */     if (has("Displacement"))
/*     */     {
/* 131 */       doubleRangeCoordinateHashSupplier = new DoubleRangeCoordinateHashSupplier((new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Displacement"), 0.0D)).load());
/*     */     }
/* 133 */     return (IDoubleCoordinateHashSupplier)doubleRangeCoordinateHashSupplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadNoiseCondition() {
/* 138 */     return (new NoiseMaskConditionJsonLoader(this.seed, this.dataFolder, get("NoiseMask")))
/* 139 */       .load();
/*     */   }
/*     */   @Nonnull
/*     */   protected ICoordinateRndCondition loadHeightCondition() {
/*     */     HeightCondition heightCondition;
/* 144 */     DefaultCoordinateRndCondition defaultCoordinateRndCondition = DefaultCoordinateRndCondition.DEFAULT_TRUE;
/* 145 */     if (has("HeightThreshold"))
/*     */     {
/* 147 */       heightCondition = new HeightCondition((new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load());
/*     */     }
/* 149 */     return (ICoordinateRndCondition)heightCondition;
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String KEY_ROTATIONS = "Rotations";
/*     */     public static final String KEY_PLACEMENT = "Placement";
/*     */     public static final String KEY_BIOME_MASK = "BiomeMask";
/*     */     public static final String KEY_BLOCK_MASK = "Mask";
/*     */     public static final String KEY_ITERATIONS = "Iterations";
/*     */     public static final String KEY_DISPLACEMENT = "Displacement";
/*     */     public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */     public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */     public static final String SEED_STRING_BIOME_MASK_TYPE = "Prefab";
/*     */     public static final String ERROR_ROTATIONS_MUST_POSITIVE = "Array for rotations must have at least one entry or left away to allow random rotation";
/* 165 */     public static final String ERROR_ROTATIONS_UNKOWN = "Could not find rotation \"%s\". Allowed: " + Arrays.toString((Object[])PrefabRotation.VALUES);
/*     */     public static final String ERROR_ROTATIONS_UNKOWN_TYPE = "\"Rotations\" is not an array nor a string, other types are not supported! Given: %s";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CavePrefabConfigJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */