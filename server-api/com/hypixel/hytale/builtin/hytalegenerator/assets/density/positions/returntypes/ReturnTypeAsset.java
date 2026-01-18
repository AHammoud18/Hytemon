/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ReturnTypeAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, ReturnTypeAsset>> {
/* 23 */   private static final ReturnTypeAsset[] EMPTY_INPUTS = new ReturnTypeAsset[0]; public static final AssetCodecMapCodec<String, ReturnTypeAsset> CODEC;
/*    */   static {
/* 25 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private static final Map<String, ReturnTypeAsset> exportedNodes = new HashMap<>();
/*    */   
/* 34 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(ReturnTypeAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<ReturnTypeAsset> ABSTRACT_CODEC; static {
/* 35 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/* 49 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ReturnTypeAsset.class).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*    */   }
/*    */   
/*    */   private String id;
/*    */   private AssetExtraInfo.Data data;
/* 54 */   private String exportName = "";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanUp() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean registerExportedNode(@Nonnull String name, @Nonnull ReturnTypeAsset node) {
/* 67 */     exportedNodes.put(name, node);
/* 68 */     return true;
/*    */   }
/*    */   
/*    */   public static ReturnTypeAsset getExportedAsset(@Nonnull String name) {
/* 72 */     return exportedNodes.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 77 */     return this.id;
/*    */   }
/*    */   
/*    */   public abstract ReturnType build(@Nonnull SeedBox paramSeedBox, @Nonnull ReferenceBundle paramReferenceBundle, @Nonnull WorkerIndexer paramWorkerIndexer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\returntypes\ReturnTypeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */