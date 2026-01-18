/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.ConstantCurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.curves.CurveAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ShellDensity;
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
/*    */ public class ShellDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ShellDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ShellDensityAsset.class, ShellDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Axis", (Codec)Vector3d.CODEC, true), (t, k) -> t.axis = k, k -> k.axis).add()).append(new KeyedCodec("Mirror", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isMirrored = k.booleanValue(), k -> Boolean.valueOf(k.isMirrored)).add()).append(new KeyedCodec("AngleCurve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.angleCurveAsset = k, k -> k.angleCurveAsset).add()).append(new KeyedCodec("DistanceCurve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.distanceCurveAsset = k, k -> k.distanceCurveAsset).add()).build();
/*    */   }
/* 40 */   private Vector3d axis = new Vector3d(0.0D, 0.0D, 0.0D);
/*    */   private boolean isMirrored = false;
/* 42 */   private CurveAsset angleCurveAsset = (CurveAsset)new ConstantCurveAsset();
/* 43 */   private CurveAsset distanceCurveAsset = (CurveAsset)new ConstantCurveAsset();
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 47 */     if (isSkipped() || this.angleCurveAsset == null || this.distanceCurveAsset == null) {
/* 48 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 50 */     return (Density)new ShellDensity(this.angleCurveAsset.build(), this.distanceCurveAsset.build(), this.axis, this.isMirrored);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 55 */     cleanUpInputs();
/* 56 */     this.angleCurveAsset.cleanUp();
/* 57 */     this.distanceCurveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ShellDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */