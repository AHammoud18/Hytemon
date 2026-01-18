/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.EntityContainer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import javax.annotation.Nonnull;
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
/*    */   public ScanResult scanResult;
/*    */   public VoxelSpace<Material> materialSpace;
/*    */   public EntityContainer entityBuffer;
/*    */   public WorkerIndexer.Id workerId;
/*    */   public double distanceFromBiomeEdge;
/*    */   
/*    */   public Context(@Nonnull ScanResult scanResult, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull EntityContainer entityBuffer, WorkerIndexer.Id workerId, double distanceFromBiomeEdge) {
/* 76 */     this.scanResult = scanResult;
/* 77 */     this.materialSpace = materialSpace;
/* 78 */     this.entityBuffer = entityBuffer;
/* 79 */     this.workerId = workerId;
/* 80 */     this.distanceFromBiomeEdge = distanceFromBiomeEdge;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 84 */     this.scanResult = other.scanResult;
/* 85 */     this.materialSpace = other.materialSpace;
/* 86 */     this.entityBuffer = other.entityBuffer;
/* 87 */     this.workerId = other.workerId;
/* 88 */     this.distanceFromBiomeEdge = other.distanceFromBiomeEdge;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\Prop$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */