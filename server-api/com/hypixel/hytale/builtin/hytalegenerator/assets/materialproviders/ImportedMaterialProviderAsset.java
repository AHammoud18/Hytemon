/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedMaterialProviderAsset.class, ImportedMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.name = k, k -> k.name).add()).build();
/*    */   }
/* 22 */   private String name = "";
/*    */ 
/*    */   
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 26 */     if (skip()) return MaterialProvider.noMaterialProvider(); 
/* 27 */     if (this.name == null || this.name.isEmpty()) {
/* 28 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Material Provider with the name does not exist: " + this.name);
/* 29 */       return MaterialProvider.noMaterialProvider();
/*    */     } 
/* 31 */     MaterialProviderAsset exportedAsset = MaterialProviderAsset.getExportedAsset(this.name);
/* 32 */     if (exportedAsset == null) return MaterialProvider.noMaterialProvider(); 
/* 33 */     return exportedAsset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\ImportedMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */