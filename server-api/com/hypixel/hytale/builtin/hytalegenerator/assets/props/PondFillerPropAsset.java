/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.MaterialSet;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockset.MaterialSetAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.OriginScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.filler.PondFillerProp;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PondFillerPropAsset
/*    */   extends PropAsset
/*    */ {
/*    */   public static final BuilderCodec<PondFillerPropAsset> CODEC;
/*    */   
/*    */   static {
/* 56 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PondFillerPropAsset.class, PondFillerPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("BoundingMin", (Codec)Vector3i.CODEC, true), (asset, v) -> asset.boundingMin = v, asset -> asset.boundingMin).add()).append(new KeyedCodec("BoundingMax", (Codec)Vector3i.CODEC, true), (asset, v) -> asset.boundingMax = v, asset -> asset.boundingMax).add()).append(new KeyedCodec("FillMaterial", (Codec)MaterialProviderAsset.CODEC, true), (asset, v) -> asset.fluidMaterialProviderAsset = v, asset -> asset.fluidMaterialProviderAsset).add()).append(new KeyedCodec("BarrierBlockSet", (Codec)MaterialSetAsset.CODEC, true), (asset, v) -> asset.solidSetAsset = v, asset -> asset.solidSetAsset).add()).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.patternAsset = v, asset -> asset.patternAsset).add()).append(new KeyedCodec("Scanner", (Codec)ScannerAsset.CODEC, true), (asset, v) -> asset.scannerAsset = v, asset -> asset.scannerAsset).add()).build();
/*    */   }
/* 58 */   private Vector3i boundingMin = new Vector3i(-10, -10, -10);
/* 59 */   private Vector3i boundingMax = new Vector3i(10, 10, 10);
/* 60 */   private MaterialProviderAsset fluidMaterialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/* 61 */   private MaterialSetAsset solidSetAsset = new MaterialSetAsset();
/* 62 */   private PatternAsset patternAsset = (PatternAsset)new ConstantPatternAsset();
/* 63 */   private ScannerAsset scannerAsset = (ScannerAsset)new OriginScannerAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Prop build(@Nonnull PropAsset.Argument argument) {
/* 68 */     if (skip()) {
/* 69 */       return Prop.noProp();
/*    */     }
/*    */     
/* 72 */     if (this.scannerAsset == null || this.patternAsset == null || this.fluidMaterialProviderAsset == null || this.solidSetAsset == null) {
/* 73 */       return Prop.noProp();
/*    */     }
/*    */     
/* 76 */     MaterialProvider<Material> materialProvider = this.fluidMaterialProviderAsset.build(MaterialProviderAsset.argumentFrom(argument));
/* 77 */     MaterialSet solidSet = this.solidSetAsset.build(argument.materialCache);
/* 78 */     Pattern pattern = this.patternAsset.build(PatternAsset.argumentFrom(argument));
/* 79 */     Scanner scanner = this.scannerAsset.build(ScannerAsset.argumentFrom(argument));
/*    */     
/* 81 */     PondFillerProp prop = new PondFillerProp(this.boundingMin, this.boundingMax, solidSet, materialProvider, scanner, pattern);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     return (Prop)prop;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 92 */     this.fluidMaterialProviderAsset.cleanUp();
/* 93 */     this.solidSetAsset.cleanUp();
/* 94 */     this.patternAsset.cleanUp();
/* 95 */     this.scannerAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\PondFillerPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */