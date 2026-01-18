/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedDirectionalityAsset
/*    */   extends DirectionalityAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedDirectionalityAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedDirectionalityAsset.class, ImportedDirectionalityAsset::new, DirectionalityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (asset, v) -> asset.name = v, asset -> asset.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public Directionality build(@Nonnull DirectionalityAsset.Argument argument) {
/* 25 */     if (this.name == null || this.name.isEmpty()) {
/* 26 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Pattern with the name does not exist: " + this.name);
/* 27 */       return Directionality.noDirectionality();
/*    */     } 
/* 29 */     DirectionalityAsset exportedAsset = DirectionalityAsset.getExportedAsset(this.name);
/* 30 */     if (exportedAsset == null) return Directionality.noDirectionality(); 
/* 31 */     return exportedAsset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\directionality\ImportedDirectionalityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */