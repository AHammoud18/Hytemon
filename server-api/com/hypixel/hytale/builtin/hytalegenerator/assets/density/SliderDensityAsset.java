/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.SliderDensity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SliderDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SliderDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SliderDensityAsset.class, SliderDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("SlideX", (Codec)Codec.DOUBLE, false), (t, k) -> t.slideX = k.doubleValue(), k -> Double.valueOf(k.slideX)).add()).append(new KeyedCodec("SlideY", (Codec)Codec.DOUBLE, false), (t, k) -> t.slideY = k.doubleValue(), k -> Double.valueOf(k.slideY)).add()).append(new KeyedCodec("SlideZ", (Codec)Codec.DOUBLE, false), (t, k) -> t.slideZ = k.doubleValue(), k -> Double.valueOf(k.slideZ)).add()).build();
/*    */   }
/* 32 */   private double slideX = 0.0D;
/* 33 */   private double slideY = 0.0D;
/* 34 */   private double slideZ = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 38 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 39 */     return (Density)new SliderDensity(this.slideX, this.slideY, this.slideZ, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 44 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SliderDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */