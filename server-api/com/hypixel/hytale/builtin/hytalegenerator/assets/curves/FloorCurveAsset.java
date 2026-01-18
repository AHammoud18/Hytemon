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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloorCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<FloorCurveAsset> CODEC;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FloorCurveAsset.class, FloorCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Curve", (Codec)CurveAsset.CODEC, true), (t, k) -> t.curveAsset = k, k -> k.curveAsset).add()).append(new KeyedCodec("Floor", (Codec)Codec.DOUBLE, true), (t, k) -> t.limit = k.doubleValue(), k -> Double.valueOf(k.limit)).add()).build();
/*    */   }
/* 25 */   private CurveAsset curveAsset = new ConstantCurveAsset();
/* 26 */   private double limit = 0.0D;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Double2DoubleFunction build() {
/* 31 */     if (this.curveAsset == null) {
/* 32 */       return in -> 0.0D;
/*    */     }
/* 34 */     Double2DoubleFunction curve = this.curveAsset.build();
/*    */     
/* 36 */     return in -> Math.max(this.limit, curve.applyAsDouble(in));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 41 */     this.curveAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\FloorCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */