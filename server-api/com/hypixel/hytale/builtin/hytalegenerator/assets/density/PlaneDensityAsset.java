/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.PlaneDensity;
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
/*    */ public class PlaneDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<PlaneDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlaneDensityAsset.class, PlaneDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.distanceCurveAsset = k, k -> k.distanceCurveAsset).add()).append(new KeyedCodec("IsAnchored", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isAnchored = k.booleanValue(), k -> Boolean.valueOf(k.isAnchored)).add()).append(new KeyedCodec("PlaneNormal", (Codec)Vector3d.CODEC, false), (t, k) -> t.planeNormal = k, k -> k.planeNormal).addValidator((v, r) -> { if (v.length() == 0.0D) r.fail("Plane normal can't be a zero vector.");  }).add()).build();
/*    */   }
/* 39 */   private CurveAsset distanceCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 40 */   private Vector3d planeNormal = new Vector3d(0.0D, 1.0D, 0.0D);
/*    */   private boolean isAnchored = false;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 45 */     if (isSkipped() || this.distanceCurveAsset == null) {
/* 46 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 48 */     return (Density)new PlaneDensity(this.distanceCurveAsset.build(), this.planeNormal, this.isAnchored);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 53 */     cleanUpInputs();
/* 54 */     this.distanceCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\PlaneDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */