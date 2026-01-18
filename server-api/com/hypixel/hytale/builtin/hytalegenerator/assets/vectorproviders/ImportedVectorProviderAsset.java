/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.ConstantVectorProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedVectorProviderAsset
/*    */   extends VectorProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedVectorProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedVectorProviderAsset.class, ImportedVectorProviderAsset::new, VectorProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.importedNodeName = k, k -> k.importedNodeName).add()).build();
/*    */   }
/* 23 */   private String importedNodeName = "";
/*    */ 
/*    */   
/*    */   public VectorProvider build(@Nonnull VectorProviderAsset.Argument argument) {
/* 27 */     if (isSkipped()) return (VectorProvider)new ConstantVectorProvider(new Vector3d());
/*    */     
/* 29 */     VectorProviderAsset.Exported exported = getExportedAsset(this.importedNodeName);
/* 30 */     if (exported == null) {
/* 31 */       LoggerUtil.getLogger().warning("Couldn't find VectorProvider asset exported with name: '" + this.importedNodeName + "'. Using empty Node instead.");
/* 32 */       return (VectorProvider)new ConstantVectorProvider(new Vector3d());
/*    */     } 
/*    */     
/* 35 */     if (exported.singleInstance) {
/* 36 */       if (exported.builtInstance == null) {
/* 37 */         exported.builtInstance = exported.asset.build(argument);
/*    */       }
/* 39 */       return exported.builtInstance;
/*    */     } 
/*    */     
/* 42 */     return exported.asset.build(argument);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 47 */     VectorProviderAsset.Exported exported = getExportedAsset(this.importedNodeName);
/* 48 */     if (exported == null) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     exported.builtInstance = null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\ImportedVectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */