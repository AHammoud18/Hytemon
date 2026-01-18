/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.Noise2dDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.YOverrideDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.CellNoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CellNoise2DDensityAsset extends DensityAsset {
/*    */   public static final BuilderCodec<CellNoise2DDensityAsset> CODEC;
/* 22 */   private static Set<String> validCellTypes = new HashSet<>(); static {
/* 23 */     for (FastNoiseLite.CellularReturnType e : FastNoiseLite.CellularReturnType.values()) {
/* 24 */       validCellTypes.add(e.toString());
/*    */     }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CellNoise2DDensityAsset.class, CellNoise2DDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("ScaleX", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleX = scale.doubleValue(), asset -> Double.valueOf(asset.scaleX)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleZ", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleZ = scale.doubleValue(), asset -> Double.valueOf(asset.scaleZ)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Jitter", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.jitter = v.doubleValue(), asset -> Double.valueOf(asset.scaleZ)).add()).append(new KeyedCodec("Octaves", (Codec)Codec.INTEGER, true), (asset, octaves) -> asset.octaves = octaves.intValue(), asset -> Integer.valueOf(asset.octaves)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, seed) -> asset.seedKey = seed, asset -> asset.seedKey).add()).append(new KeyedCodec("CellType", FastNoiseLite.CellularReturnType.CODEC, true), (asset, cellType) -> asset.cellType = cellType, asset -> asset.cellType).add()).build();
/*    */   }
/* 61 */   private double scaleX = 1.0D;
/* 62 */   private double scaleZ = 1.0D;
/* 63 */   private double jitter = 0.5D;
/* 64 */   private int octaves = 1;
/* 65 */   private String seedKey = "A";
/* 66 */   private FastNoiseLite.CellularReturnType cellType = FastNoiseLite.CellularReturnType.CellValue;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 70 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 71 */     SeedBox childSeed = argument.parentSeed.child(this.seedKey);
/*    */     
/* 73 */     CellNoiseField noise = new CellNoiseField(((Integer)childSeed.createSupplier().get()).intValue(), this.scaleX, 1.0D, this.scaleZ, this.jitter, this.octaves, this.cellType);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     Noise2dDensity noiseDensity = new Noise2dDensity((NoiseField)noise);
/* 82 */     MultiCacheDensity multiCacheDensity = new MultiCacheDensity((Density)noiseDensity, argument.workerIndexer.getWorkerCount(), CacheDensityAsset.DEFAULT_CAPACITY);
/* 83 */     return (Density)new YOverrideDensity((Density)multiCacheDensity, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 88 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\CellNoise2DDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */