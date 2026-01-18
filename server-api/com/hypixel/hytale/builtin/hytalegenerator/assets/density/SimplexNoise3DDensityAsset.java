/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.Noise3dDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.SimplexNoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
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
/*    */ public class SimplexNoise3DDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SimplexNoise3DDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SimplexNoise3DDensityAsset.class, SimplexNoise3DDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Lacunarity", (Codec)Codec.DOUBLE, true), (asset, lacunarity) -> asset.lacunarity = lacunarity.doubleValue(), asset -> Double.valueOf(asset.lacunarity)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Persistence", (Codec)Codec.DOUBLE, true), (asset, persistence) -> asset.persistence = persistence.doubleValue(), asset -> Double.valueOf(asset.persistence)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleXZ", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleXZ = scale.doubleValue(), asset -> Double.valueOf(asset.scaleXZ)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleY", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleY = scale.doubleValue(), asset -> Double.valueOf(asset.scaleY)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Octaves", (Codec)Codec.INTEGER, true), (asset, octaves) -> asset.octaves = octaves.intValue(), asset -> Integer.valueOf(asset.octaves)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, seed) -> asset.seedKey = seed, asset -> asset.seedKey).add()).build();
/*    */   }
/* 49 */   private double lacunarity = 1.0D;
/* 50 */   private double persistence = 1.0D;
/* 51 */   private double scaleXZ = 1.0D;
/* 52 */   private double scaleY = 1.0D;
/* 53 */   private int octaves = 1;
/* 54 */   private String seedKey = "A";
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 58 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 59 */     SeedBox childSeed = argument.parentSeed.child(this.seedKey);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     SimplexNoiseField noise = SimplexNoiseField.builder().withAmplitudeMultiplier(this.persistence).withFrequencyMultiplier(this.lacunarity).withScale(this.scaleXZ, this.scaleY, this.scaleXZ, this.scaleXZ).withSeed(((Integer)childSeed.createSupplier().get()).intValue()).withNumberOfOctaves(this.octaves).build();
/* 67 */     return (Density)new Noise3dDensity((NoiseField)noise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 72 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SimplexNoise3DDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */