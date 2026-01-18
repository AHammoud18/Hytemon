/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ScaleDensity;
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
/*    */ public class ScaleDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ScaleDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ScaleDensityAsset.class, ScaleDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("ScaleX", (Codec)Codec.DOUBLE, false), (t, k) -> t.scaleX = k.doubleValue(), k -> Double.valueOf(k.scaleX)).add()).append(new KeyedCodec("ScaleY", (Codec)Codec.DOUBLE, false), (t, k) -> t.scaleY = k.doubleValue(), k -> Double.valueOf(k.scaleY)).add()).append(new KeyedCodec("ScaleZ", (Codec)Codec.DOUBLE, false), (t, k) -> t.scaleZ = k.doubleValue(), k -> Double.valueOf(k.scaleZ)).add()).build();
/*    */   }
/* 32 */   private double scaleX = 1.0D;
/* 33 */   private double scaleY = 1.0D;
/* 34 */   private double scaleZ = 1.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 38 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 39 */     return (Density)new ScaleDensity(this.scaleX, this.scaleY, this.scaleZ, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 44 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ScaleDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */