/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.VectorWarpDensity;
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
/*    */ public class VectorWarpDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<VectorWarpDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VectorWarpDensityAsset.class, VectorWarpDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("WarpFactor", (Codec)Codec.DOUBLE, true), (t, k) -> t.warpFactor = k.doubleValue(), t -> Double.valueOf(t.warpFactor)).add()).append(new KeyedCodec("WarpVector", (Codec)Vector3d.CODEC, true), (t, k) -> t.warpVector = k, t -> t.warpVector).add()).build();
/*    */   }
/* 28 */   private double warpFactor = 1.0D;
/* 29 */   private Vector3d warpVector = new Vector3d();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 33 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 34 */     return (Density)new VectorWarpDensity(
/* 35 */         buildFirstInput(argument), 
/* 36 */         buildSecondInput(argument), this.warpFactor, this.warpVector);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 43 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\VectorWarpDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */