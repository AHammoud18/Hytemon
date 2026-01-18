/*     */ package com.hypixel.hytale.builtin.hytalegenerator.density;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.TerrainDensityProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Context
/*     */ {
/*     */   @Nonnull
/*     */   public Vector3d position;
/*     */   @Nonnull
/*     */   public WorkerIndexer.Id workerId;
/*     */   @Nullable
/*     */   public Vector3d densityAnchor;
/*     */   @Nullable
/*     */   public Vector3d positionsAnchor;
/*     */   public int switchState;
/*     */   public double distanceFromCellWall;
/*     */   @Nullable
/*     */   public TerrainDensityProvider terrainDensityProvider;
/*     */   public double distanceToBiomeEdge;
/*     */   
/*     */   public Context() {
/*  45 */     this.position = new Vector3d();
/*  46 */     this.workerId = WorkerIndexer.Id.UNKNOWN;
/*  47 */     this.densityAnchor = null;
/*  48 */     this.positionsAnchor = null;
/*  49 */     this.switchState = 0;
/*  50 */     this.distanceFromCellWall = Double.MAX_VALUE;
/*  51 */     this.terrainDensityProvider = null;
/*  52 */     this.distanceToBiomeEdge = Double.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id workerId, @Nullable Vector3d densityAnchor, int switchState, double distanceFromCellWall, @Nullable TerrainDensityProvider terrainDensityProvider, double distanceToBiomeEdge) {
/*  64 */     this.position = position;
/*  65 */     this.workerId = workerId;
/*  66 */     this.densityAnchor = densityAnchor;
/*  67 */     this.switchState = switchState;
/*  68 */     this.distanceFromCellWall = distanceFromCellWall;
/*  69 */     this.positionsAnchor = null;
/*  70 */     this.terrainDensityProvider = terrainDensityProvider;
/*  71 */     this.distanceToBiomeEdge = distanceToBiomeEdge;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull Context other) {
/*  75 */     this.position = other.position;
/*  76 */     this.densityAnchor = other.densityAnchor;
/*  77 */     this.switchState = other.switchState;
/*  78 */     this.distanceFromCellWall = other.distanceFromCellWall;
/*  79 */     this.positionsAnchor = other.positionsAnchor;
/*  80 */     this.workerId = other.workerId;
/*  81 */     this.terrainDensityProvider = other.terrainDensityProvider;
/*  82 */     this.distanceToBiomeEdge = other.distanceToBiomeEdge;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull VectorProvider.Context context) {
/*  86 */     this.position = context.position;
/*  87 */     this.workerId = context.workerId;
/*  88 */     this.terrainDensityProvider = context.terrainDensityProvider;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull TintProvider.Context context) {
/*  92 */     this.position = context.position.toVector3d();
/*  93 */     this.workerId = context.workerId;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull EnvironmentProvider.Context context) {
/*  97 */     this.position = context.position.toVector3d();
/*  98 */     this.workerId = context.workerId;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull MaterialProvider.Context context) {
/* 102 */     this.position = context.position.toVector3d();
/* 103 */     this.workerId = context.workerId;
/* 104 */     this.terrainDensityProvider = context.terrainDensityProvider;
/* 105 */     this.distanceToBiomeEdge = context.distanceToBiomeEdge;
/*     */   }
/*     */   
/*     */   public Context(@Nonnull Pattern.Context context) {
/* 109 */     this.position = context.position.toVector3d();
/* 110 */     this.workerId = context.workerId;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\Density$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */