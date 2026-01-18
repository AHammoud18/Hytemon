/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class InverterCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<InverterCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 17 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(InverterCurveAsset.class, InverterCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).build();
/*    */   }
/* 19 */   private CurveAsset curveAsset = new ConstantCurveAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 24 */     if (this.curveAsset == null) {
/* 25 */       return in -> 0.0D;
/*    */     }
/* 27 */     Double2DoubleFunction inputCurve = this.curveAsset.build();
/* 28 */     return in -> -inputCurve.applyAsDouble(in);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 35 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\InverterCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */