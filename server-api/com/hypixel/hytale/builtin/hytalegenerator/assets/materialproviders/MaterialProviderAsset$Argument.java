/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
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
/*     */   public WorkerIndexer workerIndexer;
/*     */   
/*     */   public Argument(@Nonnull SeedBox parentSeed, @Nonnull MaterialCache materialCache, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 109 */     this.parentSeed = parentSeed;
/* 110 */     this.materialCache = materialCache;
/* 111 */     this.referenceBundle = referenceBundle;
/* 112 */     this.workerIndexer = workerIndexer;
/*     */   }
/*     */   
/*     */   public Argument(@Nonnull Argument argument) {
/* 116 */     this.parentSeed = argument.parentSeed;
/* 117 */     this.materialCache = argument.materialCache;
/* 118 */     this.referenceBundle = argument.referenceBundle;
/* 119 */     this.workerIndexer = argument.workerIndexer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\MaterialProviderAsset$Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */