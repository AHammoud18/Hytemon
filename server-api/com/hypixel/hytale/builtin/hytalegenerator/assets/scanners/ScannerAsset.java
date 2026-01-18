/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.scanners;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class ScannerAsset implements Cleanable, JsonAssetWithMap<String, DefaultAssetMap<String, ScannerAsset>> {
/*     */   public static final AssetCodecMapCodec<String, ScannerAsset> CODEC;
/*     */   
/*     */   static {
/*  25 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final Map<String, ScannerAsset> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  34 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(ScannerAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<ScannerAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/*  35 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  59 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ScannerAsset.class).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  exportedNodes.put(asset.exportName, asset); LoggerUtil.getLogger().info("Exported Scanner asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean skip = false;
/*     */   
/*  65 */   private String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean skip() {
/*  72 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static ScannerAsset getExportedAsset(@Nonnull String name) {
/*  76 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  81 */     return this.id;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument argumentFrom(@Nonnull PropAsset.Argument argument) {
/*  86 */     return new Argument(argument.parentSeed, argument.referenceBundle);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Argument
/*     */   {
/*     */     public SeedBox parentSeed;
/*     */     public ReferenceBundle referenceBundle;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle) {
/*  96 */       this.parentSeed = parentSeed;
/*  97 */       this.referenceBundle = referenceBundle;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 101 */       this.parentSeed = argument.parentSeed;
/* 102 */       this.referenceBundle = argument.referenceBundle;
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanUp() {}
/*     */   
/*     */   public abstract Scanner build(@Nonnull Argument paramArgument);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\scanners\ScannerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */