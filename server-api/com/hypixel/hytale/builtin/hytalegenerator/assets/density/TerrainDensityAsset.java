/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.TerrainDensity;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TerrainDensityAsset
/*    */   extends DensityAsset {
/* 12 */   public static final BuilderCodec<TerrainDensityAsset> CODEC = BuilderCodec.builder(TerrainDensityAsset.class, TerrainDensityAsset::new, DensityAsset.ABSTRACT_CODEC)
/* 13 */     .build();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 17 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 18 */     return (Density)new TerrainDensity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 23 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\TerrainDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */