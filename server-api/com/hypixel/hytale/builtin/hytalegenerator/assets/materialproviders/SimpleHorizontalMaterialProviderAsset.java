/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.HorizontalMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.BaseHeightReference;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.Objects;
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
/*    */ public class SimpleHorizontalMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<SimpleHorizontalMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SimpleHorizontalMaterialProviderAsset.class, SimpleHorizontalMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("TopY", (Codec)Codec.INTEGER, true), (t, k) -> t.topY = k.intValue(), k -> Integer.valueOf(k.topY)).add()).append(new KeyedCodec("BottomY", (Codec)Codec.INTEGER, true), (t, k) -> t.bottomY = k.intValue(), k -> Integer.valueOf(k.bottomY)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).append(new KeyedCodec("TopBaseHeight", (Codec)Codec.STRING, false), (t, k) -> t.topBaseHeightName = k, t -> t.topBaseHeightName).add()).append(new KeyedCodec("BottomBaseHeight", (Codec)Codec.STRING, false), (t, k) -> t.bottomBaseHeightName = k, t -> t.bottomBaseHeightName).add()).build();
/*    */   }
/* 45 */   private int topY = 0;
/* 46 */   private int bottomY = 0;
/* 47 */   private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/* 48 */   private String topBaseHeightName = "";
/* 49 */   private String bottomBaseHeightName = "";
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 54 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 56 */     BiDouble2DoubleFunction topFunction = (x, z) -> this.topY;
/* 57 */     BiDouble2DoubleFunction bottomFunction = (x, z) -> this.bottomY;
/*    */     
/* 59 */     if (!this.topBaseHeightName.isEmpty()) {
/* 60 */       BaseHeightReference topHeightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.topBaseHeightName, BaseHeightReference.class);
/* 61 */       if (topHeightDataLayer != null) {
/* 62 */         BiDouble2DoubleFunction baseHeight = topHeightDataLayer.getHeightFunction();
/* 63 */         topFunction = ((x, z) -> baseHeight.apply(x, z) + this.topY);
/*    */       } else {
/* 65 */         ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't find height data layer with name \"" + this.topBaseHeightName + "\", using a zero-constant Density node.");
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     if (!this.bottomBaseHeightName.isEmpty()) {
/* 70 */       BaseHeightReference bottomHeightDataLayer = (BaseHeightReference)argument.referenceBundle.getLayerWithName(this.bottomBaseHeightName, BaseHeightReference.class);
/* 71 */       if (bottomHeightDataLayer != null) {
/* 72 */         BiDouble2DoubleFunction baseHeight = bottomHeightDataLayer.getHeightFunction();
/* 73 */         bottomFunction = ((x, z) -> baseHeight.apply(x, z) + this.bottomY);
/*    */       } else {
/* 75 */         ((HytaleLogger.Api)HytaleLogger.getLogger().atConfig()).log("Couldn't find height data layer with name \"" + this.bottomBaseHeightName + "\", using a zero-constant Density node.");
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     Objects.requireNonNull(topFunction); Objects.requireNonNull(bottomFunction); return (MaterialProvider<Material>)new HorizontalMaterialProvider(this.materialProviderAsset.build(argument), topFunction::apply, bottomFunction::apply);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 84 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\SimpleHorizontalMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */