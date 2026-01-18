/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedPatternAsset
/*    */   extends PatternAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedPatternAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedPatternAsset.class, ImportedPatternAsset::new, PatternAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.name = k, k -> k.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public Pattern build(@Nonnull PatternAsset.Argument argument) {
/* 25 */     if (isSkipped()) return Pattern.noPattern(); 
/* 26 */     if (this.name == null || this.name.isEmpty()) {
/* 27 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Pattern with the name does not exist: " + this.name);
/* 28 */       return Pattern.noPattern();
/*    */     } 
/*    */     
/* 31 */     PatternAsset exportedAsset = PatternAsset.getExportedAsset(this.name);
/* 32 */     if (exportedAsset == null) return Pattern.noPattern(); 
/* 33 */     return exportedAsset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\patterns\ImportedPatternAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */