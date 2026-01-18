/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.SolidityMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SolidityMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<SolidityMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SolidityMaterialProviderAsset.class, SolidityMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Solid", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.solidMaterialProvider = k, k -> k.solidMaterialProvider).add()).append(new KeyedCodec("Empty", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.emptyMaterialProvider = k, k -> k.emptyMaterialProvider).add()).build();
/*    */   }
/* 26 */   private MaterialProviderAsset solidMaterialProvider = new ConstantMaterialProviderAsset();
/* 27 */   private MaterialProviderAsset emptyMaterialProvider = new ConstantMaterialProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 32 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 34 */     return (MaterialProvider<Material>)new SolidityMaterialProvider(this.solidMaterialProvider
/* 35 */         .build(argument), this.emptyMaterialProvider
/* 36 */         .build(argument));
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 41 */     this.solidMaterialProvider.cleanUp();
/* 42 */     this.emptyMaterialProvider.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\SolidityMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */