/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.DownwardDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
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
/*    */ public class DownwardDepthMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<DownwardDepthMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DownwardDepthMaterialProviderAsset.class, DownwardDepthMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Depth", (Codec)Codec.INTEGER, true), (t, k) -> t.depth = k.intValue(), k -> Integer.valueOf(k.depth)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).build();
/*    */   }
/* 27 */   private int depth = 0;
/* 28 */   private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 33 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 35 */     return (MaterialProvider<Material>)new DownwardDepthMaterialProvider(this.materialProviderAsset.build(argument), this.depth);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 40 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\DownwardDepthMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */