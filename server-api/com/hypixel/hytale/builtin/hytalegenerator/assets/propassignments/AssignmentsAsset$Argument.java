/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
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
/*     */ public class Argument
/*     */ {
/*     */   public SeedBox parentSeed;
/*     */   public MaterialCache materialCache;
/*     */   public ReferenceBundle referenceBundle;
/*     */   public int runtime;
/*     */   public WorkerIndexer workerIndexer;
/*     */   
/*     */   public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, int runtime, @Nonnull WorkerIndexer workerIndexer) {
/* 102 */     this.parentSeed = parentSeed;
/* 103 */     this.materialCache = materialCache;
/* 104 */     this.referenceBundle = referenceBundle;
/* 105 */     this.runtime = runtime;
/* 106 */     this.workerIndexer = workerIndexer;
/*     */   }
/*     */   
/*     */   public Argument(@Nonnull Argument argument) {
/* 110 */     this.parentSeed = argument.parentSeed;
/* 111 */     this.materialCache = argument.materialCache;
/* 112 */     this.referenceBundle = argument.referenceBundle;
/* 113 */     this.runtime = argument.runtime;
/* 114 */     this.workerIndexer = argument.workerIndexer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\AssignmentsAsset$Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */