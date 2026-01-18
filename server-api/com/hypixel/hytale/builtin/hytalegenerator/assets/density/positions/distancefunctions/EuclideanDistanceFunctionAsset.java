/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.distancefunctions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.DistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.distancefunctions.EuclideanDistanceFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EuclideanDistanceFunctionAsset
/*    */   extends DistanceFunctionAsset {
/* 12 */   public static final BuilderCodec<EuclideanDistanceFunctionAsset> CODEC = BuilderCodec.builder(EuclideanDistanceFunctionAsset.class, EuclideanDistanceFunctionAsset::new, DistanceFunctionAsset.ABSTRACT_CODEC)
/* 13 */     .build();
/*    */   
/*    */   static {
/* 16 */     DistanceFunctionAsset.CODEC.register("Euclidean", EuclideanDistanceFunctionAsset.class, CODEC);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public DistanceFunction build(@Nonnull SeedBox parentSeed, double maxDistance) {
/* 22 */     return (DistanceFunction)new EuclideanDistanceFunction();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\distancefunctions\EuclideanDistanceFunctionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */