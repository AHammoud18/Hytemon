/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.CacheDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.CellValueReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CellValueReturnTypeAsset
/*    */   extends ReturnTypeAsset
/*    */ {
/*    */   public static final BuilderCodec<CellValueReturnTypeAsset> CODEC;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CellValueReturnTypeAsset.class, CellValueReturnTypeAsset::new, ReturnTypeAsset.ABSTRACT_CODEC).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, k) -> t.densityAsset = k, t -> t.densityAsset).add()).append(new KeyedCodec("DefaultValue", (Codec)Codec.DOUBLE, false), (t, k) -> t.defaultValue = k.doubleValue(), t -> Double.valueOf(t.defaultValue)).add()).build();
/*    */   }
/* 34 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/* 35 */   private double defaultValue = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ReturnType build(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 42 */     Density densityNode = this.densityAsset.build(new DensityAsset.Argument(parentSeed, referenceBundle, workerIndexer));
/* 43 */     MultiCacheDensity multiCacheDensity = new MultiCacheDensity(densityNode, workerIndexer.getWorkerCount(), CacheDensityAsset.DEFAULT_CAPACITY);
/* 44 */     return (ReturnType)new CellValueReturnType((Density)multiCacheDensity, this.defaultValue, workerIndexer.getWorkerCount());
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 49 */     this.densityAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\returntypes\CellValueReturnTypeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */