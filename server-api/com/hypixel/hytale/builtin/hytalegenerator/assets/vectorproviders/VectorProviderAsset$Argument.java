/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
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
/*     */ 
/*     */ 
/*     */ public class Argument
/*     */ {
/*     */   public SeedBox parentSeed;
/*     */   public ReferenceBundle referenceBundle;
/*     */   public WorkerIndexer workerIndexer;
/*     */   
/*     */   public Argument(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 110 */     this.parentSeed = parentSeed;
/* 111 */     this.referenceBundle = referenceBundle;
/* 112 */     this.workerIndexer = workerIndexer;
/*     */   }
/*     */   
/*     */   public Argument(@Nonnull Argument argument) {
/* 116 */     this.parentSeed = argument.parentSeed;
/* 117 */     this.referenceBundle = argument.referenceBundle;
/* 118 */     this.workerIndexer = argument.workerIndexer;
/*     */   }
/*     */   
/*     */   public Argument(@Nonnull DensityAsset.Argument argument) {
/* 122 */     this.parentSeed = argument.parentSeed;
/* 123 */     this.referenceBundle = argument.referenceBundle;
/* 124 */     this.workerIndexer = argument.workerIndexer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\VectorProviderAsset$Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */