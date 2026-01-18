/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.NormalizerDensity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NormalizerDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<NormalizerDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NormalizerDensityAsset.class, NormalizerDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("FromMin", (Codec)Codec.DOUBLE, true), (t, k) -> t.fromMin = k.doubleValue(), k -> Double.valueOf(k.fromMin)).add()).append(new KeyedCodec("FromMax", (Codec)Codec.DOUBLE, true), (t, k) -> t.fromMax = k.doubleValue(), k -> Double.valueOf(k.fromMax)).add()).append(new KeyedCodec("ToMin", (Codec)Codec.DOUBLE, true), (t, k) -> t.toMin = k.doubleValue(), k -> Double.valueOf(k.toMin)).add()).append(new KeyedCodec("ToMax", (Codec)Codec.DOUBLE, true), (t, k) -> t.toMax = k.doubleValue(), k -> Double.valueOf(k.toMax)).add()).build();
/*    */   }
/* 37 */   private double fromMin = 0.0D;
/* 38 */   private double fromMax = 1.0D;
/* 39 */   private double toMin = 0.0D;
/* 40 */   private double toMax = 1.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 44 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 45 */     return (Density)new NormalizerDensity(this.fromMin, this.fromMax, this.toMin, this.toMax, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 50 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\NormalizerDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */