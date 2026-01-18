/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Constants
/*     */ {
/*     */   public static final String KEY_ROTATIONS = "Rotations";
/*     */   public static final String KEY_PLACEMENT = "Placement";
/*     */   public static final String KEY_BIOME_MASK = "BiomeMask";
/*     */   public static final String KEY_BLOCK_MASK = "Mask";
/*     */   public static final String KEY_ITERATIONS = "Iterations";
/*     */   public static final String KEY_DISPLACEMENT = "Displacement";
/*     */   public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */   public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */   public static final String SEED_STRING_BIOME_MASK_TYPE = "Prefab";
/*     */   public static final String ERROR_ROTATIONS_MUST_POSITIVE = "Array for rotations must have at least one entry or left away to allow random rotation";
/* 165 */   public static final String ERROR_ROTATIONS_UNKOWN = "Could not find rotation \"%s\". Allowed: " + Arrays.toString((Object[])PrefabRotation.VALUES);
/*     */   public static final String ERROR_ROTATIONS_UNKOWN_TYPE = "\"Rotations\" is not an array nor a string, other types are not supported! Given: %s";
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CavePrefabConfigJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */