/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.Noise3dDensity;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.CellNoiseField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CellNoise3DDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<CellNoise3DDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CellNoise3DDensityAsset.class, CellNoise3DDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("ScaleX", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleX = scale.doubleValue(), asset -> Double.valueOf(asset.scaleX)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleY", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleY = scale.doubleValue(), asset -> Double.valueOf(asset.scaleY)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleZ", (Codec)Codec.DOUBLE, true), (asset, scale) -> asset.scaleZ = scale.doubleValue(), asset -> Double.valueOf(asset.scaleZ)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Jitter", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.jitter = v.doubleValue(), asset -> Double.valueOf(asset.scaleZ)).add()).append(new KeyedCodec("Octaves", (Codec)Codec.INTEGER, true), (asset, octaves) -> asset.octaves = octaves.intValue(), asset -> Integer.valueOf(asset.octaves)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, seed) -> asset.seedKey = seed, asset -> asset.seedKey).add()).append(new KeyedCodec("CellType", FastNoiseLite.CellularReturnType.CODEC, true), (asset, cellType) -> asset.cellType = cellType, asset -> asset.cellType).add()).build();
/*    */   }
/* 55 */   private double scaleX = 1.0D;
/* 56 */   private double scaleY = 1.0D;
/* 57 */   private double scaleZ = 1.0D;
/* 58 */   private double jitter = 0.5D;
/* 59 */   private int octaves = 1;
/* 60 */   private String seedKey = "A";
/* 61 */   private FastNoiseLite.CellularReturnType cellType = FastNoiseLite.CellularReturnType.CellValue;
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 65 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D); 
/* 66 */     SeedBox childSeed = argument.parentSeed.child(this.seedKey);
/*    */     
/* 68 */     CellNoiseField noise = new CellNoiseField(((Integer)childSeed.createSupplier().get()).intValue(), this.scaleX, this.scaleY, this.scaleZ, this.jitter, this.octaves, this.cellType);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     return (Density)new Noise3dDensity((NoiseField)noise);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 81 */     cleanUpInputs();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\CellNoise3DDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */