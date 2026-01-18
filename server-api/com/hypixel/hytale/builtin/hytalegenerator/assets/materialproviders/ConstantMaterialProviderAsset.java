/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.ConstantMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantMaterialProviderAsset.class, ConstantMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Material", (Codec)MaterialAsset.CODEC, true), (asset, value) -> asset.materialAsset = value, asset -> asset.materialAsset).add()).build();
/*    */   }
/* 23 */   private MaterialAsset materialAsset = new MaterialAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 28 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 30 */     if (this.materialAsset == null) {
/* 31 */       return (MaterialProvider<Material>)new ConstantMaterialProvider(null);
/*    */     }
/*    */     
/* 34 */     Material material = this.materialAsset.build(argument.materialCache);
/* 35 */     return (MaterialProvider<Material>)new ConstantMaterialProvider(material);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\ConstantMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */