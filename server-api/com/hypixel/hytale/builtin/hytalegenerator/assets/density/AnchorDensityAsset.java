/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.AnchorDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnchorDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<AnchorDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AnchorDensityAsset.class, AnchorDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Reversed", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isReversed = k.booleanValue(), k -> Boolean.valueOf(k.isReversed)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 27 */     return (Density)new AnchorDensity(buildFirstInput(argument), this.isReversed);
/*    */   }
/*    */   private boolean isReversed = false;
/*    */   
/*    */   public void cleanUp() {
/* 32 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\AnchorDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */