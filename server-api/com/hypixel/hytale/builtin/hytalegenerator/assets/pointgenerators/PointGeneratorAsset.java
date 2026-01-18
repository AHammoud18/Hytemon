/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.PointProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class PointGeneratorAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, PointGeneratorAsset>> {
/* 21 */   private static final PointGeneratorAsset[] EMPTY_INPUTS = new PointGeneratorAsset[0]; public static final AssetCodecMapCodec<String, PointGeneratorAsset> CODEC;
/*    */   static {
/* 23 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   private static final Map<String, PointGeneratorAsset> exportedNodes = new HashMap<>();
/*    */   
/* 32 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(PointGeneratorAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<PointGeneratorAsset> ABSTRACT_CODEC; static {
/* 33 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/* 53 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(PointGeneratorAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported position provider asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*    */   }
/*    */   
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/* 58 */   private PointGeneratorAsset[] inputs = EMPTY_INPUTS;
/*    */   private boolean skip = false;
/* 60 */   private String exportName = "";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PointGeneratorAsset[] inputs() {
/* 67 */     return this.inputs;
/*    */   }
/*    */   
/*    */   public boolean skip() {
/* 71 */     return this.skip;
/*    */   }
/*    */   
/*    */   public static PointGeneratorAsset getExportedAsset(@Nonnull String name) {
/* 75 */     return exportedNodes.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 80 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract PointProvider build(@Nonnull SeedBox paramSeedBox);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\pointgenerators\PointGeneratorAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */