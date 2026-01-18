/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.CurveMapperDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class CurveMapperDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<CurveMapperDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CurveMapperDensityAsset.class, CurveMapperDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).build();
/*    */   }
/* 23 */   private CurveAsset curveAsset = (CurveAsset)new ConstantCurveAsset();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 27 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 28 */     return (Density)new CurveMapperDensity(this.curveAsset.build(), buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 33 */     cleanUpInputs();
/* 34 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\CurveMapperDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */