/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.XOverrideDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XOverrideDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<XOverrideDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(XOverrideDensityAsset.class, XOverrideDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Value", (Codec)Codec.DOUBLE, true), (t, k) -> t.value = k.doubleValue(), t -> Double.valueOf(t.value)).add()).build();
/*    */   }
/* 22 */   private double value = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 28 */     Density input = buildFirstInput(argument);
/* 29 */     if (input == null) {
/* 30 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     }
/* 32 */     return (Density)new XOverrideDensity(input, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 37 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\XOverrideDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */