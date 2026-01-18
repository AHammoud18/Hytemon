/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.DistanceDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.RotatorDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ScaleDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
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
/*    */ public class EllipsoidDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<EllipsoidDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EllipsoidDensityAsset.class, EllipsoidDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.densityCurveAsset = k, k -> k.densityCurveAsset).add()).append(new KeyedCodec("Scale", (Codec)Vector3d.CODEC, false), (t, k) -> t.scaleVector = k, k -> k.scaleVector).addValidator((v, r) -> { if (v.x == 0.0D || v.y == 0.0D || v.z == 0.0D) r.fail("scale vector contains 0.0");  }).add()).append(new KeyedCodec("NewYAxis", (Codec)Vector3d.CODEC, false), (t, k) -> { if (k.length() != 0.0D) t.newYAxis = k;  }k -> k.newYAxis).add()).append(new KeyedCodec("Spin", (Codec)Codec.DOUBLE, false), (t, k) -> t.spinAngle = k.doubleValue(), k -> Double.valueOf(k.spinAngle)).add()).build();
/*    */   }
/* 48 */   private CurveAsset densityCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 49 */   private Vector3d scaleVector = new Vector3d(1.0D, 1.0D, 1.0D); @Nonnull
/* 50 */   private Vector3d newYAxis = new Vector3d(0.0D, 1.0D, 0.0D);
/*    */   
/* 52 */   private double spinAngle = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 56 */     if (isSkipped() || this.densityCurveAsset == null) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 58 */     DistanceDensity sphere = new DistanceDensity(this.densityCurveAsset.build());
/* 59 */     ScaleDensity scale = new ScaleDensity(this.scaleVector.x, this.scaleVector.y, this.scaleVector.z, (Density)sphere);
/* 60 */     return (Density)new RotatorDensity((Density)scale, this.newYAxis, this.spinAngle);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 65 */     cleanUpInputs();
/* 66 */     this.densityCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\EllipsoidDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */