/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SumCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<SumCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SumCurveAsset.class, SumCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curves", (Codec)new ArrayCodec((Codec)CurveAsset.CODEC, x$0 -> new CurveAsset[x$0]), true), (t, k) -> t.curveAssets = k, k -> k.curveAssets).add()).build();
/*    */   }
/* 20 */   private CurveAsset[] curveAssets = new CurveAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 25 */     if (this.curveAssets.length == 0) {
/* 26 */       return in -> 0.0D;
/*    */     }
/* 28 */     Double2DoubleFunction[] inputCurves = new Double2DoubleFunction[this.curveAssets.length];
/* 29 */     for (int i = 0; i < this.curveAssets.length; i++) {
/* 30 */       inputCurves[i] = this.curveAssets[i].build();
/*    */     }
/*    */     
/* 33 */     if (inputCurves.length == 1) {
/* 34 */       Double2DoubleFunction curve = inputCurves[0];
/* 35 */       Objects.requireNonNull(curve); return curve::applyAsDouble;
/*    */     } 
/*    */     
/* 38 */     if (inputCurves.length == 2) {
/* 39 */       Double2DoubleFunction curveA = inputCurves[0];
/* 40 */       Double2DoubleFunction curveB = inputCurves[1];
/* 41 */       return in -> curveA.applyAsDouble(in) + curveB.applyAsDouble(in);
/*    */     } 
/*    */     
/* 44 */     if (inputCurves.length == 3) {
/* 45 */       Double2DoubleFunction curveA = inputCurves[0];
/* 46 */       Double2DoubleFunction curveB = inputCurves[1];
/* 47 */       Double2DoubleFunction curveC = inputCurves[2];
/* 48 */       return in -> curveA.applyAsDouble(in) + curveB.applyAsDouble(in) + curveC.applyAsDouble(in);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 53 */     return in -> {
/*    */         double value = 0.0D;
/*    */         for (Double2DoubleFunction curve : inputCurves) {
/*    */           value += curve.applyAsDouble(in);
/*    */         }
/*    */         return value;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 64 */     for (CurveAsset curveAsset : this.curveAssets)
/* 65 */       curveAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\SumCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */