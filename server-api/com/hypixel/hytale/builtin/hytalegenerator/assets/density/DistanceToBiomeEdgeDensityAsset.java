/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.DistanceToBiomeEdgeDensity;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DistanceToBiomeEdgeDensityAsset
/*    */   extends DensityAsset {
/* 12 */   public static final BuilderCodec<DistanceToBiomeEdgeDensityAsset> CODEC = BuilderCodec.builder(DistanceToBiomeEdgeDensityAsset.class, DistanceToBiomeEdgeDensityAsset::new, DensityAsset.ABSTRACT_CODEC)
/* 13 */     .build();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 17 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 18 */     return (Density)new DistanceToBiomeEdgeDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 23 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\DistanceToBiomeEdgeDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */