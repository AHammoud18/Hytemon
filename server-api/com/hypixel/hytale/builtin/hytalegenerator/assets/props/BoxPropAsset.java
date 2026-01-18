/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.OriginScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.scanners.ScannerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.BoxProp;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
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
/*    */ public class BoxPropAsset
/*    */   extends PropAsset
/*    */ {
/*    */   public static final BuilderCodec<BoxPropAsset> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BoxPropAsset.class, BoxPropAsset::new, PropAsset.ABSTRACT_CODEC).append(new KeyedCodec("Range", (Codec)Vector3i.CODEC, true), (asset, v) -> asset.range = v, asset -> asset.range).add()).append(new KeyedCodec("Material", (Codec)MaterialAsset.CODEC, true), (asset, value) -> asset.materialAsset = value, asset -> asset.materialAsset).add()).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.patternAsset = v, asset -> asset.patternAsset).add()).append(new KeyedCodec("Scanner", (Codec)ScannerAsset.CODEC, true), (asset, v) -> asset.scannerAsset = v, asset -> asset.scannerAsset).add()).build();
/*    */   }
/* 42 */   private Vector3i range = new Vector3i();
/* 43 */   private MaterialAsset materialAsset = new MaterialAsset();
/* 44 */   private PatternAsset patternAsset = (PatternAsset)new ConstantPatternAsset();
/* 45 */   private ScannerAsset scannerAsset = (ScannerAsset)new OriginScannerAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Prop build(@Nonnull PropAsset.Argument argument) {
/* 50 */     if (skip()) {
/* 51 */       return Prop.noProp();
/*    */     }
/*    */     
/* 54 */     Material material = this.materialAsset.build(argument.materialCache);
/*    */     
/* 56 */     if (this.scannerAsset == null || this.patternAsset == null) {
/* 57 */       return Prop.noProp();
/*    */     }
/*    */     
/* 60 */     return (Prop)new BoxProp(this.range, material, this.scannerAsset.build(ScannerAsset.argumentFrom(argument)), this.patternAsset
/* 61 */         .build(PatternAsset.argumentFrom(argument)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 66 */     this.patternAsset.cleanUp();
/* 67 */     this.scannerAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\BoxPropAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */