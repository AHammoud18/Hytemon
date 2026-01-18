/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.DistanceReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DistanceReturnTypeAsset
/*    */   extends ReturnTypeAsset {
/* 14 */   public static final BuilderCodec<DistanceReturnTypeAsset> CODEC = BuilderCodec.builder(DistanceReturnTypeAsset.class, DistanceReturnTypeAsset::new, ReturnTypeAsset.ABSTRACT_CODEC)
/* 15 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ReturnType build(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 22 */     return (ReturnType)new DistanceReturnType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\returntypes\DistanceReturnTypeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */