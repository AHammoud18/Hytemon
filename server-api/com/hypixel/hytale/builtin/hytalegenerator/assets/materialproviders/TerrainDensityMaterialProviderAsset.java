/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.TerrainDensityMaterialProvider;
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
/*    */ public class TerrainDensityMaterialProviderAsset
/*    */   extends MaterialProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<TerrainDensityMaterialProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TerrainDensityMaterialProviderAsset.class, TerrainDensityMaterialProviderAsset::new, MaterialProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, k -> k.delimiterAssets).add()).build();
/*    */   }
/* 30 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MaterialProvider<Material> build(@Nonnull MaterialProviderAsset.Argument argument) {
/* 35 */     if (skip()) return MaterialProvider.noMaterialProvider();
/*    */     
/* 37 */     ArrayList<TerrainDensityMaterialProvider.FieldDelimiter<Material>> delimitersList = new ArrayList<>(this.delimiterAssets.length);
/* 38 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/* 39 */       MaterialProvider<Material> materialProvider = delimiterAsset.materialProviderAsset.build(argument);
/* 40 */       TerrainDensityMaterialProvider.FieldDelimiter<Material> delimiter = new TerrainDensityMaterialProvider.FieldDelimiter(materialProvider, delimiterAsset.from, delimiterAsset.to);
/* 41 */       delimitersList.add(delimiter);
/*    */     } 
/*    */     
/* 44 */     return (MaterialProvider<Material>)new TerrainDensityMaterialProvider(delimitersList);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 49 */     for (DelimiterAsset delimiterAsset : this.delimiterAssets) {
/* 50 */       delimiterAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class DelimiterAsset
/*    */     implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, DelimiterAsset>>
/*    */   {
/*    */     public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private String id;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private AssetExtraInfo.Data data;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 79 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("From", (Codec)Codec.DOUBLE, true), (t, y) -> t.from = y.doubleValue(), t -> Double.valueOf(t.from)).add()).append(new KeyedCodec("To", (Codec)Codec.DOUBLE, true), (t, out) -> t.to = out.doubleValue(), t -> Double.valueOf(t.to)).add()).append(new KeyedCodec("Material", (Codec)MaterialProviderAsset.CODEC, true), (t, out) -> t.materialProviderAsset = out, t -> t.materialProviderAsset).add()).build();
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 84 */     private double from = 0.0D;
/* 85 */     private double to = 0.0D;
/* 86 */     private MaterialProviderAsset materialProviderAsset = new ConstantMaterialProviderAsset();
/*    */ 
/*    */     
/*    */     public String getId() {
/* 90 */       return this.id;
/*    */     }
/*    */ 
/*    */     
/*    */     public void cleanUp() {
/* 95 */       this.materialProviderAsset.cleanUp();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\TerrainDensityMaterialProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */