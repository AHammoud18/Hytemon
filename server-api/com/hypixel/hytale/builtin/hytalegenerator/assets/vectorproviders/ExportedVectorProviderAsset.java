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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExportedVectorProviderAsset
/*    */   extends VectorProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ExportedVectorProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExportedVectorProviderAsset.class, ExportedVectorProviderAsset::new, VectorProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("SingleInstance", (Codec)Codec.BOOLEAN, true), (asset, value) -> asset.singleInstance = value.booleanValue(), asset -> Boolean.valueOf(asset.singleInstance)).add()).append(new KeyedCodec("VectorProvider", (Codec)VectorProviderAsset.CODEC, true), (asset, value) -> asset.vectorProviderAsset = value, value -> value.vectorProviderAsset).add()).build();
/*    */   }
/*    */   
/* 29 */   private VectorProviderAsset vectorProviderAsset = new ConstantVectorProviderAsset();
/*    */   private boolean singleInstance = false;
/*    */   
/*    */   public VectorProvider build(@Nonnull VectorProviderAsset.Argument argument) {
/* 33 */     if (isSkipped()) return (VectorProvider)new ConstantVectorProvider(new Vector3d());
/*    */     
/* 35 */     VectorProviderAsset.Exported exported = getExportedAsset(this.exportName);
/* 36 */     if (exported == null) {
/* 37 */       LoggerUtil.getLogger().severe("Couldn't find VectorProvider asset exported with name: '" + this.exportName + "'. This could indicate a defect in the HytaleGenerator assets.");
/* 38 */       return this.vectorProviderAsset.build(argument);
/*    */     } 
/*    */     
/* 41 */     if (exported.singleInstance) {
/* 42 */       if (exported.builtInstance == null) {
/* 43 */         exported.builtInstance = this.vectorProviderAsset.build(argument);
/*    */       }
/* 45 */       return exported.builtInstance;
/*    */     } 
/*    */     
/* 48 */     return this.vectorProviderAsset.build(argument);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 53 */     VectorProviderAsset.Exported exported = getExportedAsset(this.exportName);
/* 54 */     if (exported == null) {
/*    */       return;
/*    */     }
/*    */     
/* 58 */     exported.builtInstance = null;
/* 59 */     this.vectorProviderAsset.cleanUp();
/*    */   }
/*    */   
/*    */   public boolean isSingleInstance() {
/* 63 */     return this.singleInstance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\vectorproviders\ExportedVectorProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */