/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
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
/*    */ public class ClampCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<ClampCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClampCurveAsset.class, ClampCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, false), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).append(new KeyedCodec("WallA", (Codec)Codec.DOUBLE, false), (t, k) -> t.wallA = k.doubleValue(), k -> Double.valueOf(k.wallA)).add()).append(new KeyedCodec("WallB", (Codec)Codec.DOUBLE, false), (t, k) -> t.wallB = k.doubleValue(), k -> Double.valueOf(k.wallB)).add()).build();
/*    */   }
/* 31 */   private CurveAsset curveAsset = new ConstantCurveAsset();
/* 32 */   private double wallA = 1.0D;
/* 33 */   private double wallB = -1.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 38 */     double defaultValue = (this.wallA + this.wallB) / 2.0D;
/* 39 */     if (this.curveAsset == null) {
/* 40 */       return in -> defaultValue;
/*    */     }
/* 42 */     Double2DoubleFunction inputCurve = this.curveAsset.build();
/* 43 */     return in -> {
/*    */         value = inputCurve.applyAsDouble(in);
/*    */         return Calculator.clamp(this.wallA, value, this.wallB);
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 52 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\ClampCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */