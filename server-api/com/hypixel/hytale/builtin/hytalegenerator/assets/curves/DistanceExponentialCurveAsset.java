/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ public class DistanceExponentialCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<DistanceExponentialCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DistanceExponentialCurveAsset.class, DistanceExponentialCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Exponent", (Codec)Codec.DOUBLE, true), (t, k) -> t.exponent = k.doubleValue(), k -> Double.valueOf(k.exponent)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE, true), (t, k) -> t.range = k.doubleValue(), k -> Double.valueOf(k.range)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).build();
/*    */   }
/* 26 */   private double exponent = 1.0D;
/* 27 */   private double range = 1.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 32 */     return in -> {
/*    */         if (in < 0.0D)
/*    */           return 1.0D; 
/*    */         if (in > this.range)
/*    */           return 0.0D; 
/*    */         in /= this.range;
/*    */         in *= -1.0D;
/*    */         in++;
/*    */         return Math.pow(in, this.exponent);
/*    */       };
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\DistanceExponentialCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */