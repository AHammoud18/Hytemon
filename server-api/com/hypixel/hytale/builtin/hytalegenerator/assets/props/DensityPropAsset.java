/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.blockmask.BlockMaskAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.OriginScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.DensityProp;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
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
/*    */ public class DensityPropAsset
/*    */   extends PropAsset
/*    */ {
/*    */   public static final BuilderCodec<DensityPropAsset> CODEC;
/*    */   
/*    */   static {
/* 56 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityPropAsset.class, DensityPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("Range", (Codec)Vector3i.CODEC, true), (asset, v) -> asset.range = v, asset -> asset.range).addValidator((v, r) -> { if (v.x < 0 || v.y < 0 || v.z < 0) r.fail("Range has a value smaller than 0");  }).add()).append(new KeyedCodec("PlacementMask", (Codec)BlockMaskAsset.CODEC, true), (asset, v) -> asset.placementMaskAsset = v, asset -> asset.placementMaskAsset).add()).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.patternAsset = v, asset -> asset.patternAsset).add()).append(new KeyedCodec("Scanner", (Codec)ScannerAsset.CODEC, true), (asset, v) -> asset.scannerAsset = v, asset -> asset.scannerAsset).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (asset, v) -> asset.densityAsset = v, asset -> asset.densityAsset).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (asset, v) -> asset.materialProviderAsset = v, asset -> asset.materialProviderAsset).add()).build();
/*    */   }
/* 58 */   private Vector3i range = new Vector3i();
/* 59 */   private BlockMaskAsset placementMaskAsset = new BlockMaskAsset();
/* 60 */   private PatternAsset patternAsset = (PatternAsset)new ConstantPatternAsset();
/* 61 */   private ScannerAsset scannerAsset = (ScannerAsset)new OriginScannerAsset();
/* 62 */   private MaterialProviderAsset materialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/* 63 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Prop build(@Nonnull PropAsset.Argument argument) {
/* 68 */     if (skip()) {
/* 69 */       return Prop.noProp();
/*    */     }
/* 71 */     if (this.placementMaskAsset == null)
/* 72 */       return Prop.noProp(); 
/* 73 */     BlockMask placementMask = this.placementMaskAsset.build(argument.materialCache);
/*    */     
/* 75 */     if (this.scannerAsset == null || this.patternAsset == null || this.densityAsset == null || this.materialProviderAsset == null) {
/* 76 */       return Prop.noProp();
/*    */     }
/*    */     
/* 79 */     return (Prop)new DensityProp(this.range, this.densityAsset
/* 80 */         .build(DensityAsset.from(argument)), this.materialProviderAsset
/* 81 */         .build(MaterialProviderAsset.argumentFrom(argument)), this.scannerAsset
/* 82 */         .build(ScannerAsset.argumentFrom(argument)), this.patternAsset
/* 83 */         .build(PatternAsset.argumentFrom(argument)), placementMask, new Material(argument.materialCache.EMPTY_AIR, argument.materialCache.EMPTY_FLUID));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 90 */     this.placementMaskAsset.cleanUp();
/* 91 */     this.patternAsset.cleanUp();
/* 92 */     this.scannerAsset.cleanUp();
/* 93 */     this.materialProviderAsset.cleanUp();
/* 94 */     this.densityAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\DensityPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */