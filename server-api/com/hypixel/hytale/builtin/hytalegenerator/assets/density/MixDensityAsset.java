/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MixDensity;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MixDensityAsset
/*    */   extends DensityAsset {
/* 13 */   public static final BuilderCodec<MixDensityAsset> CODEC = BuilderCodec.builder(MixDensityAsset.class, MixDensityAsset::new, DensityAsset.ABSTRACT_CODEC)
/* 14 */     .build();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 18 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 20 */     List<Density> builtInputs = buildInputs(argument, true);
/* 21 */     if (builtInputs.size() != 3) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 23 */     return (Density)new MixDensity(builtInputs.get(0), builtInputs.get(1), builtInputs.get(2));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 28 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\MixDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */