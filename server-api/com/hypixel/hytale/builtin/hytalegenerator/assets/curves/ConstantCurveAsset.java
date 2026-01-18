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
/*    */ 
/*    */ public class ConstantCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantCurveAsset.class, ConstantCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Value", (Codec)Codec.DOUBLE, true), (asset, value) -> asset.value = value.doubleValue(), asset -> Double.valueOf(asset.value)).add()).build();
/*    */   }
/* 20 */   private double value = 0.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   public ConstantCurveAsset(double value) {
/* 25 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 31 */     return in -> this.value;
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */   
/*    */   public ConstantCurveAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\ConstantCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */