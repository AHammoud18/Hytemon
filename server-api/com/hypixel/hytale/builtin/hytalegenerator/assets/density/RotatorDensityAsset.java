/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.RotatorDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class RotatorDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<RotatorDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RotatorDensityAsset.class, RotatorDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("NewYAxis", (Codec)Vector3d.CODEC, true), (t, k) -> t.newYAxis = k, t -> t.newYAxis).add()).append(new KeyedCodec("SpinAngle", (Codec)Codec.DOUBLE, true), (t, k) -> t.spinAngle = k.doubleValue(), t -> Double.valueOf(t.spinAngle)).add()).build();
/*    */   }
/* 28 */   private Vector3d newYAxis = new Vector3d();
/* 29 */   private double spinAngle = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 33 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 34 */     return (Density)new RotatorDensity(buildFirstInput(argument), this.newYAxis, this.spinAngle);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\RotatorDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */