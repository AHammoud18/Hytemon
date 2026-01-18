/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.OffsetConstantDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OffsetConstantAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<OffsetConstantAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OffsetConstantAsset.class, OffsetConstantAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Value", (Codec)Codec.DOUBLE, true), (t, k) -> t.value = k.doubleValue(), t -> Double.valueOf(t.value)).add()).build();
/*    */   }
/* 22 */   private double value = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 27 */     return (Density)new OffsetConstantDensity(this.value, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 32 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\OffsetConstantAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */