/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
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
/*    */ public class Argument
/*    */ {
/*    */   public MaterialCache materialCache;
/*    */   public SeedBox parentSeed;
/*    */   public WorkerIndexer workerIndexer;
/*    */   
/*    */   public Argument(@Nonnull MaterialCache materialCache, @Nonnull SeedBox parentSeed, @Nonnull WorkerIndexer workerIndexer) {
/* 58 */     this.materialCache = materialCache;
/* 59 */     this.parentSeed = parentSeed;
/* 60 */     this.workerIndexer = workerIndexer;
/*    */   }
/*    */   
/*    */   public Argument(@Nonnull Argument argument) {
/* 64 */     this.materialCache = argument.materialCache;
/* 65 */     this.parentSeed = argument.parentSeed;
/* 66 */     this.workerIndexer = argument.workerIndexer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\WorldStructureAsset$Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */