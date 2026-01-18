/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.FastGradientWarpDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ 
/*    */ 
/*    */ public class FastGradientWarpDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<FastGradientWarpDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FastGradientWarpDensityAsset.class, FastGradientWarpDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("WarpScale", (Codec)Codec.FLOAT, false), (t, k) -> t.warpScale = k.floatValue(), t -> Float.valueOf(t.warpScale)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).append(new KeyedCodec("WarpOctaves", (Codec)Codec.INTEGER, false), (t, k) -> t.warpOctaves = k.intValue(), t -> Integer.valueOf(t.warpOctaves)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("WarpLacunarity", (Codec)Codec.FLOAT, false), (t, k) -> t.warpLacunarity = k.floatValue(), t -> Float.valueOf(t.warpLacunarity)).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).append(new KeyedCodec("WarpPersistence", (Codec)Codec.FLOAT, false), (t, k) -> t.warpPersistence = k.floatValue(), t -> Float.valueOf(t.warpPersistence)).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).append(new KeyedCodec("WarpFactor", (Codec)Codec.FLOAT, false), (t, k) -> t.warpFactor = k.floatValue(), t -> Float.valueOf(t.warpFactor)).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, false), (t, k) -> t.seed = k, t -> t.seed).add()).build();
/*    */   }
/* 48 */   private float warpLacunarity = 2.0F;
/* 49 */   private float warpPersistence = 0.5F;
/* 50 */   private int warpOctaves = 1;
/* 51 */   private float warpScale = 1.0F;
/* 52 */   private float warpFactor = 1.0F;
/* 53 */   private String seed = "A";
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 57 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 58 */     return (Density)new FastGradientWarpDensity(
/* 59 */         buildFirstInput(argument), this.warpLacunarity, this.warpPersistence, this.warpOctaves, 1.0F / this.warpScale, this.warpFactor, ((Integer)argument.parentSeed
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 65 */         .child(this.seed).createSupplier().get()).intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 70 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\FastGradientWarpDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */