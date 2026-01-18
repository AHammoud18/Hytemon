/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.ConstantValueDensity;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedDensityAsset.class, ImportedDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (t, k) -> t.importedNodeName = k, k -> k.importedNodeName).add()).build();
/*    */   }
/* 22 */   private String importedNodeName = "";
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped()) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 28 */     DensityAsset.Exported asset = getExportedAsset(this.importedNodeName);
/* 29 */     if (asset == null) {
/* 30 */       LoggerUtil.getLogger().warning("Couldn't find Density asset exported with name: '" + this.importedNodeName + "'. Using empty Node instead.");
/* 31 */       return (Density)new ConstantValueDensity(0.0D);
/*    */     } 
/*    */     
/* 34 */     if (asset.singleInstance) {
/* 35 */       if (asset.builtInstance == null) {
/* 36 */         asset.builtInstance = asset.asset.build(argument);
/*    */       }
/* 38 */       return asset.builtInstance;
/*    */     } 
/*    */     
/* 41 */     return asset.asset.build(argument);
/*    */   }
/*    */ 
/*    */   
/*    */   public DensityAsset[] inputs() {
/* 46 */     DensityAsset.Exported asset = getExportedAsset(this.importedNodeName);
/* 47 */     if (asset == null) {
/* 48 */       LoggerUtil.getLogger().warning("Couldn't find Density asset exported with name: '" + this.importedNodeName + "'. Using empty Node instead.");
/* 49 */       return new DensityAsset[0];
/*    */     } 
/* 51 */     return asset.asset.inputs();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 56 */     cleanUpInputs();
/* 57 */     DensityAsset.Exported exported = getExportedAsset(this.importedNodeName);
/* 58 */     if (exported == null) {
/*    */       return;
/*    */     }
/*    */     
/* 62 */     exported.builtInstance = null;
/*    */     
/* 64 */     for (DensityAsset input : inputs())
/* 65 */       input.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ImportedDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */