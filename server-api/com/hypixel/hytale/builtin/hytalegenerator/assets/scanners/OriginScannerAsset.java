/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.OriginScanner;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OriginScannerAsset
/*    */   extends ScannerAsset {
/* 11 */   public static final BuilderCodec<OriginScannerAsset> CODEC = BuilderCodec.builder(OriginScannerAsset.class, OriginScannerAsset::new, ScannerAsset.ABSTRACT_CODEC)
/* 12 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Scanner build(@Nonnull ScannerAsset.Argument argument) {
/* 17 */     if (skip()) return Scanner.noScanner(); 
/* 18 */     return (Scanner)OriginScanner.getInstance();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\OriginScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */