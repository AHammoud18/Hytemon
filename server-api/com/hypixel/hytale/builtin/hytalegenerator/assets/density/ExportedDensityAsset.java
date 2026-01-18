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
/*    */ public class ExportedDensityAsset
/*    */   extends DensityAsset
/*    */ {
/*    */   public static final BuilderCodec<ExportedDensityAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ExportedDensityAsset.class, ExportedDensityAsset::new, DensityAsset.ABSTRACT_CODEC).append(new KeyedCodec("SingleInstance", (Codec)Codec.BOOLEAN, false), (asset, value) -> asset.singleInstance = value.booleanValue(), asset -> Boolean.valueOf(asset.singleInstance)).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Density build(@Nonnull DensityAsset.Argument argument) {
/* 26 */     if (isSkipped() || (inputs()).length == 0) return (Density)new ConstantValueDensity(0.0D);
/*    */     
/* 28 */     DensityAsset.Exported exported = getExportedAsset(this.exportName);
/* 29 */     if (exported == null) {
/* 30 */       LoggerUtil.getLogger().severe("Couldn't find Density asset exported with name: '" + this.exportName + "'. This could indicate a defect in the HytaleGenerator assets.");
/* 31 */       return firstInput().build(argument);
/*    */     } 
/*    */     
/* 34 */     if (exported.singleInstance) {
/* 35 */       if (exported.builtInstance == null) {
/* 36 */         exported.builtInstance = firstInput().build(argument);
/*    */       }
/* 38 */       return exported.builtInstance;
/*    */     } 
/*    */     
/* 41 */     return firstInput().build(argument);
/*    */   }
/*    */   private boolean singleInstance = false;
/*    */   
/*    */   public void cleanUp() {
/* 46 */     cleanUpInputs();
/* 47 */     DensityAsset.Exported exported = getExportedAsset(this.exportName);
/* 48 */     if (exported == null) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     exported.builtInstance = null;
/*    */     
/* 54 */     for (DensityAsset input : inputs()) {
/* 55 */       input.cleanUp();
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isSingleInstance() {
/* 60 */     return this.singleInstance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\ExportedDensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */