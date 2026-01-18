/*    */ package com.hypixel.hytale.builtin.hytalegenerator.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
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
/*    */ public class Context
/*    */ {
/*    */   public Vector3i position;
/*    */   public Pattern pattern;
/*    */   public VoxelSpace<Material> materialSpace;
/*    */   public WorkerIndexer.Id workerId;
/*    */   
/*    */   public Context(@Nonnull Vector3i position, @Nonnull Pattern pattern, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id workerId) {
/* 55 */     this.position = position;
/* 56 */     this.pattern = pattern;
/* 57 */     this.materialSpace = materialSpace;
/* 58 */     this.workerId = workerId;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 62 */     this.position = other.position;
/* 63 */     this.pattern = other.pattern;
/* 64 */     this.materialSpace = other.materialSpace;
/* 65 */     this.workerId = other.workerId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\scanners\Scanner$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */