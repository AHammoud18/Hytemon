/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets.AlwaysTrueConditionAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets.ConditionAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.layerassets.LayerAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.ArrayList;
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
/*    */ public class SpaceAndDepthMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<SpaceAndDepthMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpaceAndDepthMaterialProviderAsset.class, SpaceAndDepthMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("LayerContext", SpaceAndDepthMaterialProvider.LayerContextType.CODEC, true), (t, k) -> t.layerContext = k, k -> k.layerContext).add()).append(new KeyedCodec("MaxExpectedDepth", (Codec)Codec.INTEGER, true), (t, k) -> t.maxDistance = k.intValue(), k -> Integer.valueOf(k.maxDistance)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("Condition", (Codec)ConditionAsset.CODEC, false), (t, k) -> t.conditionAsset = k, k -> k.conditionAsset).add()).append(new KeyedCodec("Layers", (Codec)new ArrayCodec((Codec)LayerAsset.CODEC, x$0 -> new LayerAsset[x$0]), false), (t, k) -> t.layerAssets = k, k -> k.layerAssets).addValidator((Validator)Validators.nonNullArrayElements()).add()).build();
/*    */   }
/* 51 */   private SpaceAndDepthMaterialProvider.LayerContextType layerContext = SpaceAndDepthMaterialProvider.LayerContextType.DEPTH_INTO_FLOOR;
/* 52 */   private int maxDistance = 16;
/* 53 */   private ConditionAsset conditionAsset = (ConditionAsset)new AlwaysTrueConditionAsset();
/* 54 */   private LayerAsset[] layerAssets = new LayerAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 59 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 61 */     SpaceAndDepthMaterialProvider.Condition condition = this.conditionAsset.build();
/*    */     
/* 63 */     ArrayList<SpaceAndDepthMaterialProvider.Layer<Material>> layerList = new ArrayList<>(this.layerAssets.length);
/* 64 */     for (LayerAsset asset : this.layerAssets) {
/* 65 */       layerList.add(asset.build(argument));
/*    */     }
/*    */     
/* 68 */     return (MaterialProvider<Material>)new SpaceAndDepthMaterialProvider(this.layerContext, layerList, condition, this.maxDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 73 */     for (LayerAsset layerAsset : this.layerAssets)
/* 74 */       layerAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\SpaceAndDepthMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */