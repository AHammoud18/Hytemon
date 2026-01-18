/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.BaseHeightReference;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.ColumnLinearScanner;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
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
/*    */ public class ColumnLinearScannerAsset
/*    */   extends ScannerAsset
/*    */ {
/*    */   public static final BuilderCodec<ColumnLinearScannerAsset> CODEC;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ColumnLinearScannerAsset.class, ColumnLinearScannerAsset::new, ScannerAsset.ABSTRACT_CODEC).append(new KeyedCodec("MinY", (Codec)Codec.INTEGER, true), (t, k) -> t.minY = k.intValue(), k -> Integer.valueOf(k.minY)).add()).append(new KeyedCodec("MaxY", (Codec)Codec.INTEGER, true), (t, k) -> t.maxY = k.intValue(), k -> Integer.valueOf(k.maxY)).add()).append(new KeyedCodec("ResultCap", (Codec)Codec.INTEGER, true), (t, k) -> t.resultCap = k.intValue(), k -> Integer.valueOf(k.resultCap)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("TopDownOrder", (Codec)Codec.BOOLEAN, false), (t, k) -> t.topDownOrder = k.booleanValue(), k -> Boolean.valueOf(k.topDownOrder)).add()).append(new KeyedCodec("RelativeToPosition", (Codec)Codec.BOOLEAN, false), (t, k) -> t.isRelativeToPosition = k.booleanValue(), k -> Boolean.valueOf(k.isRelativeToPosition)).add()).append(new KeyedCodec("BaseHeightName", (Codec)Codec.STRING, false), (t, k) -> t.baseHeight = k, k -> k.baseHeight).add()).build();
/*    */   }
/* 49 */   private int minY = 0;
/* 50 */   private int maxY = 1;
/* 51 */   private int resultCap = 1;
/*    */   private boolean topDownOrder = true;
/*    */   private boolean isRelativeToPosition = false;
/* 54 */   private String baseHeight = "";
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Scanner build(@Nonnull ScannerAsset.Argument argument) {
/* 59 */     if (skip()) return Scanner.noScanner();
/*    */     
/* 61 */     if (this.isRelativeToPosition) {
/* 62 */       return (Scanner)new ColumnLinearScanner(this.minY, this.maxY, this.resultCap, this.topDownOrder, true, null);
/*    */     }
/* 64 */     BaseHeightReference heightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.baseHeight, BaseHeightReference.class);
/* 65 */     if (heightDataLayer == null) {
/* 66 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't find height data layer with name \"" + this.baseHeight + "\", defaulting to not using a bed.");
/* 67 */       return (Scanner)new ColumnLinearScanner(this.minY, this.maxY, this.resultCap, this.topDownOrder, false, null);
/*    */     } 
/*    */     
/* 70 */     BiDouble2DoubleFunction baseHeightFunction = heightDataLayer.getHeightFunction();
/* 71 */     return (Scanner)new ColumnLinearScanner(this.minY, this.maxY, this.resultCap, this.topDownOrder, false, baseHeightFunction);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\ColumnLinearScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */