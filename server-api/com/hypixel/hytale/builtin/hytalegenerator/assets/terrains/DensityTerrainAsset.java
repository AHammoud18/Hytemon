/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.terrains;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DensityTerrainAsset
/*    */   extends TerrainAsset
/*    */ {
/*    */   public static final BuilderCodec<DensityTerrainAsset> CODEC;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DensityTerrainAsset.class, DensityTerrainAsset::new, TerrainAsset.ABSTRACT_CODEC).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, k) -> t.densityAsset = k, t -> t.densityAsset).add()).build();
/*    */   } @Nonnull
/* 24 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Density buildDensity(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 32 */     return this.densityAsset.build(new DensityAsset.Argument(parentSeed, referenceBundle, workerIndexer));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 37 */     this.densityAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\terrains\DensityTerrainAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */