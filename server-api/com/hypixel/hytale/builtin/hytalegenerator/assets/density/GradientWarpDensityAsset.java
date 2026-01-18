/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.GradientWarpDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GradientWarpDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<GradientWarpDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GradientWarpDensityAsset.class, GradientWarpDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("SampleRange", (Codec)Codec.DOUBLE, false), (t, k) -> t.sampleRange = k.doubleValue(), t -> Double.valueOf(t.sampleRange)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("WarpFactor", (Codec)Codec.DOUBLE, false), (t, k) -> t.warpFactor = k.doubleValue(), t -> Double.valueOf(t.warpFactor)).add()).append(new KeyedCodec("2D", (Codec)Codec.BOOLEAN, false), (t, k) -> t.is2d = k.booleanValue(), t -> Boolean.valueOf(t.is2d)).add()).append(new KeyedCodec("YFor2D", (Codec)Codec.DOUBLE, false), (t, k) -> t.y2d = k.doubleValue(), t -> Double.valueOf(t.y2d)).add()).append(new KeyedCodec("CacheSizeFor2D", (Codec)Codec.INTEGER, false), (t, k) -> t._2dCacheSize = k.intValue(), t -> Integer.valueOf(t._2dCacheSize)).add()).build();
/*    */   }
/* 43 */   private double sampleRange = 1.0D;
/* 44 */   private double warpFactor = 1.0D;
/*    */   private boolean is2d = false;
/* 46 */   private double y2d = 0.0D;
/* 47 */   private int _2dCacheSize = 16;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 51 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */ 
/*    */     
/* 54 */     GradientWarpDensity node = new GradientWarpDensity(buildFirstInput(argument), buildSecondInput(argument), this.sampleRange, this.warpFactor);
/*    */ 
/*    */     
/* 57 */     return (Density)node;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 62 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\GradientWarpDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */