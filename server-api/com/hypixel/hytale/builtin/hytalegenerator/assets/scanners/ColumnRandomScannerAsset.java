/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.ValidatorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.BaseHeightReference;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.ColumnRandomScanner;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
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
/*    */ public class ColumnRandomScannerAsset
/*    */   extends ScannerAsset
/*    */ {
/*    */   public static final BuilderCodec<ColumnRandomScannerAsset> CODEC;
/*    */   
/*    */   static {
/* 53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ColumnRandomScannerAsset.class, ColumnRandomScannerAsset::new, ScannerAsset.ABSTRACT_CODEC).append(new KeyedCodec("MinY", (Codec)Codec.INTEGER, true), (t, k) -> t.minY = k.intValue(), k -> Integer.valueOf(k.minY)).add()).append(new KeyedCodec("MaxY", (Codec)Codec.INTEGER, true), (t, k) -> t.maxY = k.intValue(), k -> Integer.valueOf(k.maxY)).add()).append(new KeyedCodec("ResultCap", (Codec)Codec.INTEGER, true), (t, k) -> t.resultCap = k.intValue(), k -> Integer.valueOf(k.resultCap)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("Seed", (Codec)Codec.STRING, false), (t, k) -> t.seed = k, k -> k.seed).add()).append(new KeyedCodec("Strategy", (Codec)Codec.STRING, false), (t, k) -> t.strategyName = k, k -> k.strategyName).addValidator(ValidatorUtil.validEnumValue((Object[])ColumnRandomScanner.Strategy.values())).add()).append(new KeyedCodec("RelativeToPosition", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isRelativeToPosition = k.booleanValue(), k -> Boolean.valueOf(k.isRelativeToPosition)).add()).append(new KeyedCodec("BaseHeightName", (Codec)Codec.STRING, false), (t, k) -> t.baseHeight = k, k -> k.baseHeight).add()).build();
/*    */   }
/* 55 */   private int minY = 0;
/* 56 */   private int maxY = 1;
/* 57 */   private int resultCap = 1;
/* 58 */   private String seed = "A";
/* 59 */   private String strategyName = "DART_THROW";
/*    */   private boolean isRelativeToPosition = false;
/* 61 */   private String baseHeight = "";
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Scanner build(@Nonnull ScannerAsset.Argument argument) {
/* 66 */     if (skip()) return Scanner.noScanner(); 
/* 67 */     SeedBox childSeed = argument.parentSeed.child(this.seed);
/* 68 */     ColumnRandomScanner.Strategy strategy = ColumnRandomScanner.Strategy.valueOf(this.strategyName);
/*    */     
/* 70 */     if (this.isRelativeToPosition) {
/* 71 */       return (Scanner)new ColumnRandomScanner(this.minY, this.maxY, this.resultCap, ((Integer)childSeed.createSupplier().get()).intValue(), strategy, true, null);
/*    */     }
/* 73 */     BaseHeightReference heightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.baseHeight, BaseHeightReference.class);
/* 74 */     if (heightDataLayer == null) {
/* 75 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't find height data layer with name \"" + this.baseHeight + "\", defaulting to not using a bed.");
/* 76 */       return (Scanner)new ColumnRandomScanner(this.minY, this.maxY, this.resultCap, ((Integer)childSeed.createSupplier().get()).intValue(), strategy, false, null);
/*    */     } 
/*    */     
/* 79 */     BiDouble2DoubleFunction baseHeightFunction = heightDataLayer.getHeightFunction();
/* 80 */     return (Scanner)new ColumnRandomScanner(this.minY, this.maxY, this.resultCap, ((Integer)childSeed.createSupplier().get()).intValue(), strategy, false, baseHeightFunction);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\ColumnRandomScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */