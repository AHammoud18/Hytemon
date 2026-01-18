/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.SelectorDensity;
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
/*    */ public class SelectorDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SelectorDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SelectorDensityAsset.class, SelectorDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("FromMin", (Codec)Codec.DOUBLE, true), (t, k) -> t.fromMin = k.doubleValue(), k -> Double.valueOf(k.fromMin)).add()).append(new KeyedCodec("FromMax", (Codec)Codec.DOUBLE, true), (t, k) -> t.fromMax = k.doubleValue(), k -> Double.valueOf(k.fromMax)).add()).append(new KeyedCodec("ToMin", (Codec)Codec.DOUBLE, true), (t, k) -> t.toMin = k.doubleValue(), k -> Double.valueOf(k.toMin)).add()).append(new KeyedCodec("ToMax", (Codec)Codec.DOUBLE, true), (t, k) -> t.toMax = k.doubleValue(), k -> Double.valueOf(k.toMax)).add()).append(new KeyedCodec("SmoothRange", (Codec)Codec.DOUBLE, true), (t, k) -> t.smoothRange = k.doubleValue(), k -> Double.valueOf(k.smoothRange)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 43 */   private double fromMin = -1.0D;
/* 44 */   private double fromMax = 1.0D;
/* 45 */   private double toMin = -1.0D;
/* 46 */   private double toMax = 1.0D;
/* 47 */   private double smoothRange = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 51 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 52 */     return (Density)new SelectorDensity(this.fromMin, this.fromMax, this.toMin, this.toMax, this.smoothRange, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 57 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SelectorDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */