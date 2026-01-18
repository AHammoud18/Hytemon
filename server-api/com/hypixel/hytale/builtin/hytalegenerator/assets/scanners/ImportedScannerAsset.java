/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedScannerAsset
/*    */   extends ScannerAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedScannerAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedScannerAsset.class, ImportedScannerAsset::new, ScannerAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, false), (t, k) -> t.name = k, k -> k.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public Scanner build(@Nonnull ScannerAsset.Argument argument) {
/* 25 */     if (skip()) return Scanner.noScanner(); 
/* 26 */     if (this.name == null || this.name.isEmpty()) {
/* 27 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Pattern with the name does not exist: " + this.name);
/* 28 */       return Scanner.noScanner();
/*    */     } 
/* 30 */     ScannerAsset exportedAsset = ScannerAsset.getExportedAsset(this.name);
/* 31 */     if (exportedAsset == null) return Scanner.noScanner(); 
/* 32 */     return exportedAsset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\ImportedScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */