/*     */ package com.hypixel.hytale.server.worldgen.prefab;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.function.ICoordinateDoubleSupplier;
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
/*     */ public class PrefabPatternGenerator
/*     */ {
/*     */   protected final int seedOffset;
/*     */   protected final PrefabCategory category;
/*     */   protected final IPointGenerator gridGenerator;
/*     */   protected final ICoordinateRndCondition heightCondition;
/*     */   protected final IHeightThresholdInterpreter heightThresholdInterpreter;
/*     */   protected final BlockMaskCondition prefabPlacementConfiguration;
/*     */   protected final ICoordinateCondition mapCondition;
/*     */   protected final IBlockFluidCondition parentCondition;
/*     */   protected final PrefabRotation[] rotations;
/*     */   protected final ICoordinateDoubleSupplier displacement;
/*     */   protected final boolean fitHeightmap;
/*     */   protected final boolean onWater;
/*     */   protected final boolean deepSearch;
/*     */   protected final boolean submerge;
/*     */   protected final int maxSize;
/*     */   protected final int exclusionRadius;
/*     */   
/*     */   public PrefabPatternGenerator(int seedOffset, PrefabCategory category, IPointGenerator gridGenerator, ICoordinateRndCondition heightCondition, IHeightThresholdInterpreter heightThresholdInterpreter, BlockMaskCondition prefabPlacementConfiguration, ICoordinateCondition mapCondition, IBlockFluidCondition parentCondition, PrefabRotation[] rotations, ICoordinateDoubleSupplier displacement, boolean fitHeightmap, boolean onWater, boolean deepSearch, boolean submerge, int maxSize, int exclusionRadius) {
/*  52 */     this.seedOffset = seedOffset;
/*  53 */     this.category = category;
/*  54 */     this.gridGenerator = gridGenerator;
/*  55 */     this.heightCondition = heightCondition;
/*  56 */     this.heightThresholdInterpreter = heightThresholdInterpreter;
/*  57 */     this.prefabPlacementConfiguration = prefabPlacementConfiguration;
/*  58 */     this.mapCondition = mapCondition;
/*  59 */     this.parentCondition = parentCondition;
/*  60 */     this.rotations = rotations;
/*  61 */     this.displacement = displacement;
/*  62 */     this.fitHeightmap = fitHeightmap;
/*  63 */     this.onWater = onWater;
/*  64 */     this.deepSearch = deepSearch;
/*  65 */     this.submerge = submerge;
/*  66 */     this.maxSize = maxSize;
/*  67 */     this.exclusionRadius = exclusionRadius;
/*     */   }
/*     */   
/*     */   public PrefabCategory getCategory() {
/*  71 */     return this.category;
/*     */   }
/*     */   
/*     */   public IPointGenerator getGridGenerator() {
/*  75 */     return this.gridGenerator;
/*     */   }
/*     */   
/*     */   public ICoordinateCondition getMapCondition() {
/*  79 */     return this.mapCondition;
/*     */   }
/*     */   
/*     */   public BlockMaskCondition getPrefabPlacementConfiguration() {
/*  83 */     return this.prefabPlacementConfiguration;
/*     */   }
/*     */   
/*     */   public boolean isFitHeightmap() {
/*  87 */     return this.fitHeightmap;
/*     */   }
/*     */   
/*     */   public IBlockFluidCondition getParentCondition() {
/*  91 */     return this.parentCondition;
/*     */   }
/*     */   
/*     */   public ICoordinateRndCondition getHeightCondition() {
/*  95 */     return this.heightCondition;
/*     */   }
/*     */   
/*     */   public IHeightThresholdInterpreter getHeightThresholdInterpreter() {
/*  99 */     return this.heightThresholdInterpreter;
/*     */   }
/*     */   
/*     */   public PrefabRotation[] getRotations() {
/* 103 */     return this.rotations;
/*     */   }
/*     */   
/*     */   public int getDisplacement(int seed, int x, int z) {
/* 107 */     return MathUtil.floor(this.displacement.apply(seed + this.seedOffset, x, z));
/*     */   }
/*     */   
/*     */   public boolean isOnWater() {
/* 111 */     return this.onWater;
/*     */   }
/*     */   
/*     */   public boolean isDeepSearch() {
/* 115 */     return this.deepSearch;
/*     */   }
/*     */   
/*     */   public boolean isSubmerge() {
/* 119 */     return this.submerge;
/*     */   }
/*     */   
/*     */   public int getMaxSize() {
/* 123 */     return this.maxSize;
/*     */   }
/*     */   
/*     */   public int getExclusionRadius() {
/* 127 */     return this.exclusionRadius;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 133 */     return "PrefabPatternGenerator{seedOffset=" + this.seedOffset + ", gridGenerator=" + String.valueOf(this.gridGenerator) + ", heightCondition=" + String.valueOf(this.heightCondition) + ", heightThresholdInterpreter=" + String.valueOf(this.heightThresholdInterpreter) + ", prefabPlacementConfiguration=" + String.valueOf(this.prefabPlacementConfiguration) + ", mapCondition=" + String.valueOf(this.mapCondition) + ", parentCondition=" + String.valueOf(this.parentCondition) + ", rotations=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       Arrays.toString((Object[])this.rotations) + ", displacement=" + String.valueOf(this.displacement) + ", fitHeightmap=" + this.fitHeightmap + ", submerge=" + this.submerge + ", maxSize=" + this.maxSize + ", onWater=" + this.onWater + ", deepSearch=" + this.deepSearch + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\PrefabPatternGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */