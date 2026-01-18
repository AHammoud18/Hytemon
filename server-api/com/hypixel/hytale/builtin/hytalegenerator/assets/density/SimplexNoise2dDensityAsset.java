/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.Noise2dDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.YOverrideDensity;
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
/*    */ public class SimplexNoise2dDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<SimplexNoise2dDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SimplexNoise2dDensityAsset.class, SimplexNoise2dDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Lacunarity", (Codec)Codec.DOUBLE, true), (asset, lacunarity) -> asset.lacunarity = lacunarity.doubleValue(), asset -> Double.valueOf(asset.lacunarity)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Persistence", (Codec)Codec.DOUBLE, true), (asset, persistence) -> asset.persistence = persistence.doubleValue(), asset -> Double.valueOf(asset.persistence)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Scale", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scale = scale.doubleValue(), asset -> Double.valueOf(asset.scale)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Octaves", (Codec)Codec.INTEGER, true), (asset, octaves) -> asset.octaves = octaves.intValue(), asset -> Integer.valueOf(asset.octaves)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, seed) -> asset.seedKey = seed, asset -> asset.seedKey).add()).build();
/*    */   }
/* 48 */   private double lacunarity = 1.0D;
/* 49 */   private double persistence = 1.0D;
/* 50 */   private double scale = 1.0D;
/* 51 */   private int octaves = 1;
/* 52 */   private String seedKey = "A";
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 56 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 57 */     SeedBox childSeed = argument.parentSeed.child(this.seedKey);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     SimplexNoiseField noise = SimplexNoiseField.builder().withAmplitudeMultiplier(this.persistence).withFrequencyMultiplier(this.lacunarity).withScale(this.scale).withSeed(((Integer)childSeed.createSupplier().get()).intValue()).withNumberOfOctaves(this.octaves).build();
/* 65 */     Noise2dDensity noiseDensity = new Noise2dDensity((NoiseField)noise);
/* 66 */     MultiCacheDensity multiCacheDensity = new MultiCacheDensity((Density)noiseDensity, argument.workerIndexer.getWorkerCount(), CacheDensityAsset.DEFAULT_CAPACITY);
/* 67 */     return (Density)new YOverrideDensity((Density)multiCacheDensity, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 72 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\SimplexNoise2dDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */