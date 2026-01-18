/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ClampDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.SmoothClampDensity;
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
/*    */ public class SmoothClampDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SmoothClampDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SmoothClampDensityAsset.class, SmoothClampDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("WallA", (Codec)Codec.DOUBLE, true), (t, k) -> t.wallA = k.doubleValue(), k -> Double.valueOf(k.wallA)).add()).append(new KeyedCodec("WallB", (Codec)Codec.DOUBLE, true), (t, k) -> t.wallB = k.doubleValue(), k -> Double.valueOf(k.wallB)).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, true), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 34 */   private double wallA = -1.0D;
/* 35 */   private double wallB = 1.0D;
/* 36 */   private double range = 0.01D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 40 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 41 */     if (this.range == 0.0D) {
/* 42 */       return (Density)new ClampDensity(this.wallA, this.wallB, buildSecondInput(argument));
/*    */     }
/* 44 */     double min = Math.min(this.wallA, this.wallB);
/* 45 */     double max = Math.max(this.wallA, this.wallB);
/* 46 */     return (Density)new SmoothClampDensity(min, max, this.range, buildSecondInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 51 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SmoothClampDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */