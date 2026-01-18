/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
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
/*     */ public class Argument
/*     */ {
/*     */   public SeedBox parentSeed;
/*     */   public ReferenceBundle referenceBundle;
/*     */   
/*     */   public Argument(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle) {
/*  96 */     this.parentSeed = parentSeed;
/*  97 */     this.referenceBundle = referenceBundle;
/*     */   }
/*     */   
/*     */   public Argument(@Nonnull Argument argument) {
/* 101 */     this.parentSeed = argument.parentSeed;
/* 102 */     this.referenceBundle = argument.referenceBundle;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\ScannerAsset$Argument.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */