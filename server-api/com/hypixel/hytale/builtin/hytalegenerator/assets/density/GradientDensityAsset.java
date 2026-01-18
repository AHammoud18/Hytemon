/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.GradientDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ public class GradientDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<GradientDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GradientDensityAsset.class, GradientDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Axis", (Codec)Vector3d.CODEC, false), (t, k) -> t.axis = k, k -> k.axis).addValidator((v, r) -> { if (v.x == 0.0D && v.y == 0.0D && v.z == 0.0D) r.fail("Axis can't be zero.");  }).add()).append(new KeyedCodec("SampleRange", (Codec)Codec.DOUBLE, false), (t, k) -> t.sampleRange = k.doubleValue(), t -> Double.valueOf(t.sampleRange)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 32 */   private Vector3d axis = new Vector3d(0.0D, 1.0D, 0.0D);
/* 33 */   private double sampleRange = 1.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 37 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 38 */     return (Density)new GradientDensity(buildFirstInput(argument), this.sampleRange, this.axis.clone());
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 43 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\GradientDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */