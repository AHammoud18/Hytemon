/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ public class Context
/*    */ {
/*    */   public Vector3i position;
/*    */   public VoxelSpace<Material> materialSpace;
/*    */   public WorkerIndexer.Id workerId;
/*    */   
/*    */   public Context(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, WorkerIndexer.Id workerId) {
/* 64 */     this.position = position;
/* 65 */     this.materialSpace = materialSpace;
/* 66 */     this.workerId = workerId;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 70 */     this.position = other.position;
/* 71 */     this.materialSpace = other.materialSpace;
/* 72 */     this.workerId = other.workerId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\Pattern$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */