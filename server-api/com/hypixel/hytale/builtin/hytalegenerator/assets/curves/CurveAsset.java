/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.curves;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class CurveAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, CurveAsset>>, Cleanable {
/* 22 */   private static final CurveAsset[] EMPTY_INPUTS = new CurveAsset[0]; public static final AssetCodecMapCodec<String, CurveAsset> CODEC;
/*    */   static {
/* 24 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   private static final Map<String, CurveAsset> exportedNodes = new ConcurrentHashMap<>();
/*    */   
/* 33 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(CurveAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<CurveAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/* 34 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/* 52 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(CurveAsset.class).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 57 */   private String exportName = "";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CurveAsset getExportedAsset(@Nonnull String name) {
/* 64 */     return exportedNodes.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 69 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract Double2DoubleFunction build();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\curves\CurveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */