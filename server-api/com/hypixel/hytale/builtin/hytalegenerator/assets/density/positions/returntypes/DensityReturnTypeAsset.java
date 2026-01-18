/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density.positions.returntypes;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.CacheDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.FieldFunctionMaterialProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.MultiCacheDensity;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.DensityReturnType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes.ReturnType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.math.Range;
/*     */ import java.util.HashMap;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class DensityReturnTypeAsset
/*     */   extends ReturnTypeAsset
/*     */ {
/*     */   public static final BuilderCodec<DensityReturnTypeAsset> CODEC;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DensityReturnTypeAsset.class, DensityReturnTypeAsset::new, ReturnTypeAsset.ABSTRACT_CODEC).append(new KeyedCodec("ChoiceDensity", (Codec)DensityAsset.CODEC, true), (t, k) -> t.choiceDensityAsset = k, t -> t.choiceDensityAsset).add()).append(new KeyedCodec("Delimiters", (Codec)new ArrayCodec((Codec)DelimiterAsset.CODEC, x$0 -> new DelimiterAsset[x$0]), true), (t, k) -> t.delimiterAssets = k, t -> t.delimiterAssets).add()).append(new KeyedCodec("DefaultValue", (Codec)Codec.DOUBLE, false), (t, k) -> t.defaultValue = k.doubleValue(), t -> Double.valueOf(t.defaultValue)).add()).build();
/*     */   }
/*  47 */   private DensityAsset choiceDensityAsset = (DensityAsset)new ConstantDensityAsset();
/*  48 */   private DelimiterAsset[] delimiterAssets = new DelimiterAsset[0];
/*  49 */   private double defaultValue = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ReturnType build(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/*  56 */     DensityAsset.Argument densityArgument = new DensityAsset.Argument(parentSeed, referenceBundle, workerIndexer);
/*  57 */     Density choiceDensity = this.choiceDensityAsset.build(densityArgument);
/*  58 */     HashMap<Range, Density> delimiterMap = new HashMap<>(this.delimiterAssets.length);
/*  59 */     for (DelimiterAsset delimiter : this.delimiterAssets) {
/*  60 */       delimiterMap.put(new Range((float)delimiter.from, (float)delimiter.to), delimiter.densityAsset.build(densityArgument));
/*     */     }
/*     */     
/*  63 */     MultiCacheDensity multiCacheDensity = new MultiCacheDensity(choiceDensity, workerIndexer.getWorkerCount(), CacheDensityAsset.DEFAULT_CAPACITY);
/*  64 */     return (ReturnType)new DensityReturnType((Density)multiCacheDensity, delimiterMap, true, this.defaultValue, workerIndexer.getWorkerCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DelimiterAsset
/*     */     implements JsonAssetWithMap<String, DefaultAssetMap<String, FieldFunctionMaterialProviderAsset.DelimiterAsset>>
/*     */   {
/*     */     public static final AssetBuilderCodec<String, DelimiterAsset> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String id;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  92 */       CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DelimiterAsset.class, DelimiterAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("From", (Codec)Codec.DOUBLE, true), (t, y) -> t.from = y.doubleValue(), t -> Double.valueOf(t.from)).add()).append(new KeyedCodec("To", (Codec)Codec.DOUBLE, true), (t, out) -> t.to = out.doubleValue(), t -> Double.valueOf(t.to)).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, out) -> t.densityAsset = out, t -> t.densityAsset).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     private double from = 0.0D;
/*  98 */     private double to = 0.0D;
/*  99 */     private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*     */ 
/*     */     
/*     */     public String getId() {
/* 103 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\positions\returntypes\DensityReturnTypeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */