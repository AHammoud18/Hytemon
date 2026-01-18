/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.JitterPointField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.PointField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.PointProvider;
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
/*    */ public class MeshPointGeneratorAsset
/*    */   extends PointGeneratorAsset
/*    */ {
/*    */   public static final BuilderCodec<MeshPointGeneratorAsset> CODEC;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MeshPointGeneratorAsset.class, MeshPointGeneratorAsset::new, PointGeneratorAsset.ABSTRACT_CODEC).append(new KeyedCodec("Jitter", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.jitter = v.doubleValue(), asset -> Double.valueOf(asset.jitter)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(0.5D))).add()).append(new KeyedCodec("ScaleX", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.scaleX = v.doubleValue(), asset -> Double.valueOf(asset.scaleX)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleY", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.scaleY = v.doubleValue(), asset -> Double.valueOf(asset.scaleY)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("ScaleZ", (Codec)Codec.DOUBLE, true), (asset, v) -> asset.scaleZ = v.doubleValue(), asset -> Double.valueOf(asset.scaleZ)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, seed) -> asset.seedKey = seed, asset -> asset.seedKey).add()).build();
/*    */   }
/* 46 */   private double jitter = 0.35D;
/* 47 */   private double scaleX = 1.0D;
/* 48 */   private double scaleY = 1.0D;
/* 49 */   private double scaleZ = 1.0D;
/* 50 */   private String seedKey = "A";
/*    */ 
/*    */   
/*    */   public PointProvider build(@Nonnull SeedBox parentSeed) {
/* 54 */     SeedBox childSeed = parentSeed.child(this.seedKey);
/*    */     
/* 56 */     PointField generator = (new JitterPointField(((Integer)childSeed.createSupplier().get()).intValue(), this.jitter)).setScale(this.scaleX, this.scaleY, this.scaleZ, 1.0D);
/* 57 */     return (PointProvider)generator;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\pointgenerators\MeshPointGeneratorAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */