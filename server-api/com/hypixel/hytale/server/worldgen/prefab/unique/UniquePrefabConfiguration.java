/*     */ package com.hypixel.hytale.server.worldgen.prefab.unique;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import java.util.Random;
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
/*     */ public class UniquePrefabConfiguration
/*     */ {
/*     */   protected final ICoordinateRndCondition heightCondition;
/*     */   protected final BlockMaskCondition placementConfiguration;
/*     */   protected final PrefabRotation[] rotations;
/*     */   protected final IIntCondition biomeMask;
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   protected final IBlockFluidCondition parent;
/*     */   protected final Vector2d anchor;
/*     */   protected final Vector3d spawnOffset;
/*     */   protected final double maxDistance;
/*     */   protected final boolean fitHeightmap;
/*     */   protected final boolean submerge;
/*     */   protected final boolean onWater;
/*     */   protected final int environmentId;
/*     */   protected final int maxAttempts;
/*     */   protected final double exclusionRadiusSquared;
/*     */   protected final boolean spawnLocation;
/*     */   protected final double zoneBorderExclusion;
/*     */   protected final boolean showOnMap;
/*     */   
/*     */   public UniquePrefabConfiguration(ICoordinateRndCondition heightCondition, BlockMaskCondition placementConfiguration, PrefabRotation[] rotations, IIntCondition biomeMask, ICoordinateCondition mapCondition, IBlockFluidCondition parent, Vector2d anchor, Vector3d spawnOffset, double maxDistance, boolean fitHeightmap, boolean submerge, boolean onWater, int environmentId, int maxAttempts, double exclusionRadius, boolean spawnLocation, double zoneBorderExclusion, boolean showOnMap) {
/*  59 */     this.heightCondition = heightCondition;
/*  60 */     this.placementConfiguration = placementConfiguration;
/*  61 */     this.rotations = rotations;
/*  62 */     this.biomeMask = biomeMask;
/*  63 */     this.mapCondition = mapCondition;
/*  64 */     this.parent = parent;
/*  65 */     this.anchor = anchor;
/*  66 */     this.spawnOffset = spawnOffset;
/*  67 */     this.maxDistance = maxDistance;
/*  68 */     this.fitHeightmap = fitHeightmap;
/*  69 */     this.submerge = submerge;
/*  70 */     this.onWater = onWater;
/*  71 */     this.environmentId = environmentId;
/*  72 */     this.maxAttempts = maxAttempts;
/*  73 */     this.exclusionRadiusSquared = exclusionRadius * exclusionRadius;
/*  74 */     this.spawnLocation = spawnLocation;
/*  75 */     this.zoneBorderExclusion = zoneBorderExclusion;
/*  76 */     this.showOnMap = showOnMap;
/*     */   }
/*     */   
/*     */   public Vector2d getAnchor() {
/*  80 */     return this.anchor;
/*     */   }
/*     */   
/*     */   public double getMaxDistance() {
/*  84 */     return this.maxDistance;
/*     */   }
/*     */   
/*     */   public ICoordinateCondition getMapCondition() {
/*  88 */     return this.mapCondition;
/*     */   }
/*     */   
/*     */   public BlockMaskCondition getPlacementConfiguration() {
/*  92 */     return this.placementConfiguration;
/*     */   }
/*     */   
/*     */   public Vector3d getSpawnOffset() {
/*  96 */     return this.spawnOffset;
/*     */   }
/*     */   
/*     */   public boolean isValidParentBiome(@Nonnull Biome biome) {
/* 100 */     return this.biomeMask.eval(biome.getId());
/*     */   }
/*     */   
/*     */   public boolean isFitHeightmap() {
/* 104 */     return this.fitHeightmap;
/*     */   }
/*     */   
/*     */   public boolean isSubmerge() {
/* 108 */     return this.submerge;
/*     */   }
/*     */   
/*     */   public boolean isValidParentBlock(int block, int fluid) {
/* 112 */     return this.parent.eval(block, fluid);
/*     */   }
/*     */   
/*     */   public ICoordinateRndCondition getHeightCondition() {
/* 116 */     return this.heightCondition;
/*     */   }
/*     */   
/*     */   public PrefabRotation getRotation(@Nonnull Random random) {
/* 120 */     return (this.rotations == null || this.rotations.length == 0) ? PrefabRotation.ROTATION_0 : this.rotations[random.nextInt(this.rotations.length)];
/*     */   }
/*     */   
/*     */   public boolean isOnWater() {
/* 124 */     return this.onWater;
/*     */   }
/*     */   
/*     */   public int getEnvironmentId() {
/* 128 */     return this.environmentId;
/*     */   }
/*     */   
/*     */   public int getMaxAttempts() {
/* 132 */     return this.maxAttempts;
/*     */   }
/*     */   
/*     */   public double getExclusionRadiusSquared() {
/* 136 */     return this.exclusionRadiusSquared;
/*     */   }
/*     */   
/*     */   public boolean isSpawnLocation() {
/* 140 */     return this.spawnLocation;
/*     */   }
/*     */   
/*     */   public double getZoneBorderExclusion() {
/* 144 */     return this.zoneBorderExclusion;
/*     */   }
/*     */   
/*     */   public boolean isShowOnMap() {
/* 148 */     return this.showOnMap;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefa\\unique\UniquePrefabConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */