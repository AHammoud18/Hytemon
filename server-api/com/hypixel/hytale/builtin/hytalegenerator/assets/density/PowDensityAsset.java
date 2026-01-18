/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.PowDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PowDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<PowDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PowDensityAsset.class, PowDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Exponent", (Codec)Codec.DOUBLE, true), (t, k) -> t.exponent = k.doubleValue(), t -> Double.valueOf(t.exponent)).add()).build();
/*    */   }
/* 22 */   private double exponent = 1.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 27 */     return (Density)new PowDensity(this.exponent, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 32 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\PowDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */