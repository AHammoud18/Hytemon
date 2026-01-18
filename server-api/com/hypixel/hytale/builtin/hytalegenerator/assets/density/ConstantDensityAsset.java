/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantDensityAsset.class, ConstantDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Value", (Codec)Codec.DOUBLE, true), (t, k) -> t.value = k.doubleValue(), k -> Double.valueOf(k.value)).add()).build();
/*    */   }
/* 21 */   private double value = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 25 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 26 */     return (Density)new ConstantValueDensity(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 31 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ConstantDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */