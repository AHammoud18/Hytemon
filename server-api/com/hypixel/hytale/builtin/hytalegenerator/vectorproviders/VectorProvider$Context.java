/*    */ package com.hypixel.hytale.builtin.hytalegenerator.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.TerrainDensityProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ public class Context
/*    */ {
/*    */   @Nonnull
/*    */   public Vector3d position;
/*    */   @Nonnull
/*    */   public WorkerIndexer.Id workerId;
/*    */   @Nullable
/*    */   public TerrainDensityProvider terrainDensityProvider;
/*    */   
/*    */   public Context(@Nonnull Vector3d position, @Nonnull WorkerIndexer.Id workerId, @Nullable TerrainDensityProvider terrainDensityProvider) {
/* 29 */     this.position = position;
/* 30 */     this.workerId = workerId;
/* 31 */     this.terrainDensityProvider = terrainDensityProvider;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 35 */     this.position = other.position;
/* 36 */     this.workerId = other.workerId;
/* 37 */     this.terrainDensityProvider = other.terrainDensityProvider;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Density.Context other) {
/* 41 */     this.position = other.position;
/* 42 */     this.workerId = other.workerId;
/* 43 */     this.terrainDensityProvider = other.terrainDensityProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\vectorproviders\VectorProvider$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */