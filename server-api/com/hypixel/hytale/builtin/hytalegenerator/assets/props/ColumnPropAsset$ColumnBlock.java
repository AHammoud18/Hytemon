/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.material.MaterialAsset;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnBlock
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ColumnPropAsset.ColumnBlock>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ColumnBlock> CODEC;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   static {
/* 108 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ColumnBlock.class, ColumnBlock::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Y", (Codec)Codec.INTEGER, true), (t, y) -> t.y = y.intValue(), t -> Integer.valueOf(t.y)).add()).append(new KeyedCodec("Material", (Codec)MaterialAsset.CODEC, true), (asset, value) -> asset.materialAsset = value, asset -> asset.materialAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 113 */   private int y = 1;
/* 114 */   private MaterialAsset materialAsset = new MaterialAsset("Empty", "Empty");
/*     */ 
/*     */   
/*     */   public String getId() {
/* 118 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\ColumnPropAsset$ColumnBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */