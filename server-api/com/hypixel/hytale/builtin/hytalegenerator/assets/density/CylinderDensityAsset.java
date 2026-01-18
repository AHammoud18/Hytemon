/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.CylinderDensity;
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
/*    */ public class CylinderDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<CylinderDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CylinderDensityAsset.class, CylinderDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("RadialCurve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.radialCurveAsset = k, k -> k.radialCurveAsset).add()).append(new KeyedCodec("AxialCurve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.axialCurveAsset = k, k -> k.axialCurveAsset).add()).append(new KeyedCodec("NewYAxis", (Codec)Vector3d.CODEC, false), (t, k) -> { if (k.length() != 0.0D) t.newYAxis = k;  }k -> k.newYAxis).add()).append(new KeyedCodec("Spin", (Codec)Codec.DOUBLE, false), (t, k) -> t.spinAngle = k.doubleValue(), k -> Double.valueOf(k.spinAngle)).add()).build();
/*    */   }
/* 44 */   private CurveAsset radialCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 45 */   private CurveAsset axialCurveAsset = (CurveAsset)new ConstantCurveAsset(); @Nonnull
/* 46 */   private Vector3d newYAxis = new Vector3d(0.0D, 1.0D, 0.0D);
/*    */   
/* 48 */   private double spinAngle = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 52 */     if (isSkipped() || this.axialCurveAsset == null || this.radialCurveAsset == null) {
/* 53 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 55 */     CylinderDensity cylinder = new CylinderDensity(this.radialCurveAsset.build(), this.axialCurveAsset.build());
/* 56 */     return (Density)new RotatorDensity((Density)cylinder, this.newYAxis, this.spinAngle);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 61 */     cleanUpInputs();
/* 62 */     this.radialCurveAsset.cleanUp();
/* 63 */     this.axialCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\CylinderDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */