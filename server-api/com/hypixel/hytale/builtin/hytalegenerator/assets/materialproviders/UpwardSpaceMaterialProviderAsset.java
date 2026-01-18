/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.UpwardSpaceMaterialProvider;
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
/*    */ 
/*    */ public class UpwardSpaceMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<UpwardSpaceMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UpwardSpaceMaterialProviderAsset.class, UpwardSpaceMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Space", (Codec)Codec.INTEGER, true), (t, k) -> t.space = k.intValue(), k -> Integer.valueOf(k.space)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).build();
/*    */   }
/* 27 */   private int space = 0;
/* 28 */   private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 34 */     return (MaterialProvider<Material>)new UpwardSpaceMaterialProvider(this.materialProviderAsset.build(argument), this.space);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 39 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\UpwardSpaceMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */