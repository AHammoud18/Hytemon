/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.TerrainDensityProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Context
/*    */ {
/*    */   @Nonnull
/*    */   public Vector3i position;
/*    */   public double density;
/*    */   public int depthIntoFloor;
/*    */   public int depthIntoCeiling;
/*    */   public int spaceAboveFloor;
/*    */   public int spaceBelowCeiling;
/*    */   @Nonnull
/*    */   public WorkerIndexer.Id workerId;
/*    */   @Nullable
/*    */   public TerrainDensityProvider terrainDensityProvider;
/*    */   public double distanceToBiomeEdge;
/*    */   
/*    */   public Context(@Nonnull Vector3i position, double density, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling, @Nonnull WorkerIndexer.Id workerId, @Nullable TerrainDensityProvider terrainDensityProvider, double distanceToBiomeEdge) {
/* 41 */     this.position = position;
/* 42 */     this.density = density;
/* 43 */     this.depthIntoFloor = depthIntoFloor;
/* 44 */     this.depthIntoCeiling = depthIntoCeiling;
/* 45 */     this.spaceAboveFloor = spaceAboveFloor;
/* 46 */     this.spaceBelowCeiling = spaceBelowCeiling;
/* 47 */     this.workerId = workerId;
/* 48 */     this.terrainDensityProvider = terrainDensityProvider;
/* 49 */     this.distanceToBiomeEdge = distanceToBiomeEdge;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 53 */     this.position = other.position;
/* 54 */     this.density = other.density;
/* 55 */     this.depthIntoFloor = other.depthIntoFloor;
/* 56 */     this.depthIntoCeiling = other.depthIntoCeiling;
/* 57 */     this.spaceAboveFloor = other.spaceAboveFloor;
/* 58 */     this.spaceBelowCeiling = other.spaceBelowCeiling;
/* 59 */     this.workerId = other.workerId;
/* 60 */     this.terrainDensityProvider = other.terrainDensityProvider;
/* 61 */     this.distanceToBiomeEdge = other.distanceToBiomeEdge;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\MaterialProvider$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */