/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.DistanceDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DistanceDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<DistanceDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DistanceDensityAsset.class, DistanceDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, false), (t, k) -> t.densityCurveAsset = k, k -> k.densityCurveAsset).add()).build();
/*    */   }
/* 23 */   private CurveAsset densityCurveAsset = (CurveAsset)new ConstantCurveAsset();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 27 */     if (isSkipped() || this.densityCurveAsset == null) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 29 */     return (Density)new DistanceDensity(this.densityCurveAsset.build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 34 */     cleanUpInputs();
/* 35 */     this.densityCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\DistanceDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */