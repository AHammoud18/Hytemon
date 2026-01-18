/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.AreaScanner;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
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
/*    */ public class AreaScannerAsset
/*    */   extends ScannerAsset
/*    */ {
/*    */   public static final BuilderCodec<AreaScannerAsset> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AreaScannerAsset.class, AreaScannerAsset::new, ScannerAsset.ABSTRACT_CODEC).append(new KeyedCodec("ResultCap", (Codec)Codec.INTEGER, true), (t, k) -> t.resultCap = k.intValue(), k -> Integer.valueOf(k.resultCap)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("ScanShape", AreaScanner.ScanShape.CODEC, false), (t, k) -> t.scanShape = k, t -> t.scanShape).add()).append(new KeyedCodec("ScanRange", (Codec)Codec.INTEGER, false), (t, k) -> t.scanRange = k.intValue(), t -> Integer.valueOf(t.scanRange)).addValidator(Validators.greaterThan(Integer.valueOf(-1))).add()).append(new KeyedCodec("ChildScanner", (Codec)ScannerAsset.CODEC, false), (t, k) -> t.childScannerAsset = k, t -> t.childScannerAsset).add()).build();
/*    */   }
/* 38 */   private int resultCap = 1;
/* 39 */   private AreaScanner.ScanShape scanShape = AreaScanner.ScanShape.CIRCLE;
/* 40 */   private int scanRange = 0;
/* 41 */   private ScannerAsset childScannerAsset = new OriginScannerAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Scanner build(@Nonnull ScannerAsset.Argument argument) {
/* 46 */     if (skip() || this.childScannerAsset == null) return Scanner.noScanner(); 
/* 47 */     return (Scanner)new AreaScanner(this.resultCap, this.scanShape, this.scanRange, this.childScannerAsset
/*    */ 
/*    */ 
/*    */         
/* 51 */         .build(argument));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 57 */     this.childScannerAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\AreaScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */