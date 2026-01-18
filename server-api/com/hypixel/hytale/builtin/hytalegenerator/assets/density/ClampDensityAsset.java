/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ClampDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class ClampDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ClampDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClampDensityAsset.class, ClampDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("WallA", (Codec)Codec.DOUBLE, true), (t, k) -> t.wallA = k.doubleValue(), k -> Double.valueOf(k.wallA)).add()).append(new KeyedCodec("WallB", (Codec)Codec.DOUBLE, true), (t, k) -> t.wallB = k.doubleValue(), k -> Double.valueOf(k.wallB)).add()).build();
/*    */   }
/* 27 */   private double wallA = 0.0D;
/* 28 */   private double wallB = 0.0D;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 32 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 33 */     return (Density)new ClampDensity(this.wallA, this.wallB, buildFirstInput(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 38 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ClampDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */