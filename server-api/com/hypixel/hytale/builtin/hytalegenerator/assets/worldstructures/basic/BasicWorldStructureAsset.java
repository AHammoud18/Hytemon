/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.basic;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.biomes.BiomeAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.WorldStructureAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.mapcontentfield.BaseHeightContentFieldAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.mapcontentfield.ContentFieldAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biomemap.BiomeMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biomemap.SimpleBiomeMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.cartas.SimpleNoiseCarta;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiCarta;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.rangemaps.DoubleRange;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.BaseHeightReference;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.Reference;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.HashMap;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class BasicWorldStructureAsset
/*     */   extends WorldStructureAsset
/*     */ {
/*     */   public static final BuilderCodec<BasicWorldStructureAsset> CODEC;
/*     */   
/*     */   static {
/*  61 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BasicWorldStructureAsset.class, BasicWorldStructureAsset::new, WorldStructureAsset.ABSTRACT_CODEC).append(new KeyedCodec("Biomes", (Codec)new ArrayCodec((Codec)BiomeRangeAsset.CODEC, x$0 -> new BiomeRangeAsset[x$0]), true), (t, k) -> t.biomeRangeAssets = k, t -> t.biomeRangeAssets).add()).append(new KeyedCodec("Density", (Codec)DensityAsset.CODEC, true), (t, k) -> t.densityAsset = k, t -> t.densityAsset).add()).append(new KeyedCodec("DefaultBiome", (Codec)new ContainedAssetCodec(BiomeAsset.class, (AssetCodec)BiomeAsset.CODEC), true), (t, k) -> t.defaultBiomeId = k, t -> t.defaultBiomeId).addValidatorLate(() -> BiomeAsset.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("DefaultTransitionDistance", (Codec)Codec.INTEGER, true), (t, k) -> t.biomeTransitionDistance = k.intValue(), t -> Integer.valueOf(t.biomeTransitionDistance)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).append(new KeyedCodec("MaxBiomeEdgeDistance", (Codec)Codec.INTEGER, true), (t, k) -> t.maxBiomeEdgeDistance = k.intValue(), t -> Integer.valueOf(t.maxBiomeEdgeDistance)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).append(new KeyedCodec("ContentFields", (Codec)new ArrayCodec((Codec)ContentFieldAsset.CODEC, x$0 -> new ContentFieldAsset[x$0]), false), (t, k) -> t.contentFieldAssets = k, t -> t.contentFieldAssets).add()).build();
/*     */   }
/*  63 */   private BiomeRangeAsset[] biomeRangeAssets = new BiomeRangeAsset[0];
/*  64 */   private int biomeTransitionDistance = 32;
/*  65 */   private int maxBiomeEdgeDistance = 0;
/*  66 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/*  67 */   private String defaultBiomeId = "";
/*  68 */   private ContentFieldAsset[] contentFieldAssets = new ContentFieldAsset[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BiomeMap buildBiomeMap(@Nonnull WorldStructureAsset.Argument argument) {
/*  75 */     ReferenceBundle referenceBundle = new ReferenceBundle();
/*     */     
/*  77 */     for (int i = this.contentFieldAssets.length - 1; i >= 0; i--) {
/*  78 */       ContentFieldAsset contentFieldAsset = this.contentFieldAssets[i]; if (contentFieldAsset instanceof BaseHeightContentFieldAsset) { BaseHeightContentFieldAsset bedAsset = (BaseHeightContentFieldAsset)contentFieldAsset;
/*     */ 
/*     */ 
/*     */         
/*  82 */         String name = bedAsset.getName();
/*  83 */         double y = bedAsset.getY();
/*  84 */         BaseHeightReference bedLayer = new BaseHeightReference((x, z) -> y);
/*  85 */         referenceBundle.put(name, (Reference)bedLayer, bedLayer.getClass()); }
/*     */     
/*     */     } 
/*  88 */     HashMap<BiomeAsset, BiomeType> biomeAssetToBiomeType = new HashMap<>();
/*     */ 
/*     */     
/*  91 */     BiomeAsset defaultBiomeAsset = (BiomeAsset)((DefaultAssetMap)BiomeAsset.getAssetStore().getAssetMap()).getAsset(this.defaultBiomeId);
/*  92 */     if (defaultBiomeAsset == null) {
/*  93 */       LoggerUtil.getLogger().warning("Couldn't find Biome asset with id: " + this.defaultBiomeId);
/*  94 */       return null;
/*     */     } 
/*     */     
/*  97 */     BiomeType defaultBiome = defaultBiomeAsset.build(argument.materialCache, argument.parentSeed, referenceBundle, argument.workerIndexer);
/*  98 */     biomeAssetToBiomeType.put(defaultBiomeAsset, defaultBiome);
/*     */     
/* 100 */     Density noise = this.densityAsset.build(DensityAsset.from(argument, referenceBundle));
/* 101 */     SimpleNoiseCarta<BiomeType> carta = new SimpleNoiseCarta(noise, defaultBiome);
/*     */     
/* 103 */     for (BiomeRangeAsset asset : this.biomeRangeAssets) {
/*     */       
/* 105 */       DoubleRange range = asset.getRange();
/* 106 */       BiomeAsset biomeAsset = asset.getBiomeAsset();
/* 107 */       if (biomeAsset == null) {
/* 108 */         LoggerUtil.getLogger().warning("Couldn't find biome asset with name " + asset.getBiomeAssetId());
/*     */       } else {
/*     */         BiomeType biome;
/*     */ 
/*     */         
/* 113 */         if (biomeAssetToBiomeType.containsKey(biomeAsset)) {
/* 114 */           biome = biomeAssetToBiomeType.get(biomeAsset);
/*     */         } else {
/* 116 */           biome = biomeAsset.build(argument.materialCache, argument.parentSeed, referenceBundle, argument.workerIndexer);
/* 117 */           biomeAssetToBiomeType.put(biomeAsset, biome);
/*     */         } 
/* 119 */         carta.put(range, biome);
/*     */       } 
/*     */     } 
/* 122 */     SimpleBiomeMap<Object> biomeMap = new SimpleBiomeMap((BiCarta)carta);
/* 123 */     int defaultRadius = Math.max(1, this.biomeTransitionDistance / 2);
/* 124 */     biomeMap.setDefaultRadius(defaultRadius);
/*     */     
/* 126 */     return (BiomeMap)biomeMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBiomeTransitionDistance() {
/* 131 */     return this.biomeTransitionDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxBiomeEdgeDistance() {
/* 136 */     return this.maxBiomeEdgeDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 141 */     this.densityAsset.cleanUp();
/* 142 */     for (ContentFieldAsset contentFieldAsset : this.contentFieldAssets) {
/* 143 */       contentFieldAsset.cleanUp();
/*     */     }
/*     */     
/* 146 */     BiomeAsset defaultBiomeAsset = (BiomeAsset)((DefaultAssetMap)BiomeAsset.getAssetStore().getAssetMap()).getAsset(this.defaultBiomeId);
/* 147 */     if (defaultBiomeAsset != null) {
/* 148 */       defaultBiomeAsset.cleanUp();
/*     */     }
/*     */     
/* 151 */     for (BiomeRangeAsset asset : this.biomeRangeAssets) {
/* 152 */       BiomeAsset biomeAsset = asset.getBiomeAsset();
/* 153 */       if (biomeAsset != null)
/* 154 */         biomeAsset.cleanUp(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\worldstructures\basic\BasicWorldStructureAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */