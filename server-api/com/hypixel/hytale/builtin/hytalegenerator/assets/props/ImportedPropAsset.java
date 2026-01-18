/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedPropAsset
/*    */   extends PropAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedPropAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedPropAsset.class, ImportedPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (asset, v) -> asset.name = v, asset -> asset.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public Prop build(@Nonnull PropAsset.Argument argument) {
/* 25 */     if (skip()) return Prop.noProp(); 
/* 26 */     if (this.name == null || this.name.isEmpty()) {
/* 27 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log("An exported Pattern with the name does not exist: " + this.name);
/* 28 */       return Prop.noProp();
/*    */     } 
/* 30 */     PropAsset exportedAsset = PropAsset.getExportedAsset(this.name);
/* 31 */     if (exportedAsset == null) return Prop.noProp(); 
/* 32 */     return exportedAsset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\ImportedPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */