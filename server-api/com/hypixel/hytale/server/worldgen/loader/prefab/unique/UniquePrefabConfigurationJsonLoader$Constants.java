/*     */ package com.hypixel.hytale.server.worldgen.loader.prefab.unique;
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
/*     */   public static final String KEY_PARENT = "Parent";
/*     */   public static final String KEY_HEIGHT_THRESHOLD = "HeightThreshold";
/*     */   public static final String KEY_BIOME_MASK = "BiomeMask";
/*     */   public static final String KEY_NOISE_MASK = "NoiseMask";
/*     */   public static final String KEY_MASK = "Mask";
/*     */   public static final String KEY_ANCHOR = "Anchor";
/*     */   public static final String KEY_FIT_HEIGHTMAP = "FitHeightmap";
/*     */   public static final String KEY_SUBMERGE = "Submerge";
/*     */   public static final String KEY_ENVIRONMENT = "Environment";
/*     */   public static final String KEY_ON_WATER = "OnWater";
/*     */   public static final String KEY_MAX_DISTANCE = "MaxDistance";
/*     */   public static final String KEY_MAX_ATTEMPTS = "MaxAttempts";
/*     */   public static final String KEY_EXCLUSION_RADIUS = "ExclusionRadius";
/*     */   public static final String KEY_IS_SPAWN = "IsSpawn";
/*     */   public static final String KEY_SPAWN_OFFSET = "SpawnOffset";
/*     */   public static final String KEY_BORDER_EXCLUSION = "BorderExclusion";
/*     */   public static final String KEY_SHOW_ON_MAP = "ShowOnMap";
/*     */   public static final String SEED_STRING_BIOME_MASK_TYPE = "UniquePrefab";
/*     */   public static final String ERROR_BIOME_ERROR_MASK = "Could not find tile / custom biome \"%s\" for biome mask. Typo or disabled biome?";
/*     */   public static final String ERROR_NO_ANCHOR = "Could not find anchor for Unique prefab generator";
/*     */   public static final String ERROR_LOADING_ENVIRONMENT = "Error while looking up environment \"%s\"!";
/*     */   public static final double DEFAULT_MAX_DISTANCE = 100.0D;
/*     */   public static final int DEFAULT_MAX_ATTEMPTS = 5000;
/*     */   public static final double DEFAULT_EXCLUSION_RADIUS = 50.0D;
/*     */   public static final double DEFAULT_ZONE_BORDER_EXCLUSION = 25.0D;
/* 227 */   public static final Boolean DEFAULT_SUBMERGE = Boolean.FALSE;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefa\\unique\UniquePrefabConfigurationJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */