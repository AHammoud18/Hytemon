/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.StripedMaterialProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
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
/*    */ public class StripedMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<StripedMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StripedMaterialProviderAsset.class, StripedMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Stripes", (Codec)new ArrayCodec((Codec)StripeAsset.CODEC, x$0 -> new StripeAsset[x$0]), true), (t, k) -> t.stripeAssets = k, k -> k.stripeAssets).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, k) -> t.materialProviderAsset = k, k -> k.materialProviderAsset).add()).build();
/*    */   }
/* 35 */   private StripeAsset[] stripeAssets = new StripeAsset[0];
/* 36 */   private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 41 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 43 */     ArrayList<StripedMaterialProvider.Stripe> stripes = new ArrayList<>();
/* 44 */     for (StripeAsset asset : this.stripeAssets) {
/* 45 */       if (asset == null) {
/* 46 */         LoggerUtil.getLogger().warning("Couldn't load a strip asset, will skip it.");
/*    */       } else {
/*    */         
/* 49 */         StripedMaterialProvider.Stripe stripe = new StripedMaterialProvider.Stripe(asset.topY, asset.bottomY);
/* 50 */         stripes.add(stripe);
/*    */       } 
/* 52 */     }  MaterialProvider<Material> materialProvider = this.materialProviderAsset.build(argument);
/*    */     
/* 54 */     return (MaterialProvider<Material>)new StripedMaterialProvider(materialProvider, stripes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 59 */     this.materialProviderAsset.cleanUp();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class StripeAsset
/*    */     implements JsonAssetWithMap<String, DefaultAssetMap<String, StripeAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, StripeAsset> CODEC;
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */     
/*    */     private int topY;
/*    */     
/*    */     private int bottomY;
/*    */ 
/*    */     
/*    */     static {
/* 82 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(StripeAsset.class, StripeAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("TopY", (Codec)Codec.INTEGER, true), (t, y) -> t.topY = y.intValue(), t -> Integer.valueOf(t.bottomY)).add()).append(new KeyedCodec("BottomY", (Codec)Codec.INTEGER, true), (t, y) -> t.bottomY = y.intValue(), t -> Integer.valueOf(t.bottomY)).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public String getId() {
/* 92 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\StripedMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */