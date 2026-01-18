/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class ImportedCurveAsset
/*    */   extends CurveAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedCurveAsset> CODEC;
/*    */   private String name;
/*    */   
/*    */   static {
/* 17 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedCurveAsset.class, ImportedCurveAsset::new, CurveAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.name = k, k -> k.name).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public Double2DoubleFunction build() {
/* 22 */     if (this.name == null || this.name.isEmpty()) {
/* 23 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Curve with the name does not exist: " + this.name);
/* 24 */       return in -> 0.0D;
/*    */     } 
/* 26 */     CurveAsset exportedAsset = CurveAsset.getExportedAsset(this.name);
/* 27 */     if (exportedAsset == null) return in -> 0.0D; 
/* 28 */     return exportedAsset.build();
/*    */   }
/*    */   
/*    */   public void cleanUp() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\ImportedCurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */