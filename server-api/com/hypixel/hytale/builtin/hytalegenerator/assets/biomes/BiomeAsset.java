/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.biomes;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.PropField;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders.EnvironmentProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.ConstantMaterialProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.propstageiterations.PropRuntimeAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.terrains.DensityTerrainAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.terrains.TerrainAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.tintproviders.TintProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.SimpleBiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BiomeAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, BiomeAsset>>, Cleanable {
/*  38 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BiomeAsset::getAssetStore)); private static AssetStore<String, BiomeAsset, DefaultAssetMap<String, BiomeAsset>> STORE; public static final AssetBuilderCodec<String, BiomeAsset> CODEC; private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   public static AssetStore<String, BiomeAsset, DefaultAssetMap<String, BiomeAsset>> getAssetStore() {
/*  42 */     if (STORE == null) STORE = AssetRegistry.getAssetStore(BiomeAsset.class); 
/*  43 */     return STORE;
/*     */   }
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
/*     */   static {
/*  90 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BiomeAsset.class, BiomeAsset::new, (Codec)Codec.STRING, (asset, id) -> asset.id = id, config -> config.id, (config, data) -> config.data = data, config -> config.data).append(new KeyedCodec("Terrain", (Codec)TerrainAsset.CODEC, true), (asset, t) -> asset.terrainAsset = t, asset -> asset.terrainAsset).add()).append(new KeyedCodec("FloatingFunctionNodes", (Codec)new ArrayCodec((Codec)DensityAsset.CODEC, x$0 -> new DensityAsset[x$0]), true), (asset, t) -> asset.floatingFunctionNodeAssets = t, asset -> asset.floatingFunctionNodeAssets).add()).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (asset, t) -> asset.biomeName = t, asset -> asset.biomeName).add()).append(new KeyedCodec("MaterialProvider", (Codec)MaterialProviderAsset.CODEC, true), (asset, materialProvider) -> asset.materialProviderAsset = materialProvider, asset -> asset.materialProviderAsset).add()).append(new KeyedCodec("Props", (Codec)new ArrayCodec((Codec)PropRuntimeAsset.CODEC, x$0 -> new PropRuntimeAsset[x$0]), true), (asset, materialProvider) -> asset.propRuntimeAssets = materialProvider, asset -> asset.propRuntimeAssets).add()).append(new KeyedCodec("EnvironmentProvider", (Codec)EnvironmentProviderAsset.CODEC, true), (asset, environmentProvider) -> asset.environmentProviderAsset = environmentProvider, asset -> asset.environmentProviderAsset).add()).append(new KeyedCodec("TintProvider", (Codec)TintProviderAsset.CODEC, true), (asset, tintProvider) -> asset.tintProviderAsset = tintProvider, asset -> asset.tintProviderAsset).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  95 */   private TerrainAsset terrainAsset = (TerrainAsset)new DensityTerrainAsset();
/*  96 */   private MaterialProviderAsset materialProviderAsset = (MaterialProviderAsset)new ConstantMaterialProviderAsset();
/*  97 */   private PropRuntimeAsset[] propRuntimeAssets = new PropRuntimeAsset[0];
/*  98 */   private EnvironmentProviderAsset environmentProviderAsset = (EnvironmentProviderAsset)new ConstantEnvironmentProviderAsset();
/*  99 */   private TintProviderAsset tintProviderAsset = (TintProviderAsset)new ConstantTintProviderAsset();
/* 100 */   private String biomeName = "DefaultName";
/*     */   
/* 102 */   private DensityAsset[] floatingFunctionNodeAssets = new DensityAsset[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 108 */     this.terrainAsset.cleanUp();
/* 109 */     this.materialProviderAsset.cleanUp();
/* 110 */     for (PropRuntimeAsset propRuntimeAsset : this.propRuntimeAssets) {
/* 111 */       propRuntimeAsset.cleanUp();
/*     */     }
/* 113 */     this.environmentProviderAsset.cleanUp();
/* 114 */     this.tintProviderAsset.cleanUp();
/* 115 */     for (DensityAsset densityAsset : this.floatingFunctionNodeAssets) {
/* 116 */       densityAsset.cleanUp();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeType build(@Nonnull MaterialCache materialCache, @Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 125 */     MaterialProvider<Material> materialProvider = this.materialProviderAsset.build(new MaterialProviderAsset.Argument(parentSeed, materialCache, referenceBundle, workerIndexer));
/*     */ 
/*     */     
/* 128 */     Density density = this.terrainAsset.buildDensity(parentSeed, referenceBundle, workerIndexer);
/*     */     
/* 130 */     EnvironmentProvider environments = EnvironmentProvider.noEnvironmentProvider();
/* 131 */     if (this.environmentProviderAsset != null) {
/* 132 */       environments = this.environmentProviderAsset.build(new EnvironmentProviderAsset.Argument(parentSeed, materialCache, referenceBundle, workerIndexer));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 137 */     TintProvider tints = TintProvider.noTintProvider();
/* 138 */     if (this.tintProviderAsset != null) {
/* 139 */       tints = this.tintProviderAsset.build(new TintProviderAsset.Argument(parentSeed, materialCache, referenceBundle, workerIndexer));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 144 */     SimpleBiomeType biome = new SimpleBiomeType(this.biomeName, density, materialProvider, environments, tints);
/*     */     
/* 146 */     for (PropRuntimeAsset fieldAsset : this.propRuntimeAssets) {
/* 147 */       if (!fieldAsset.isSkip()) {
/*     */         
/* 149 */         PositionProvider positionProvider = fieldAsset.buildPositionProvider(parentSeed, referenceBundle, workerIndexer);
/* 150 */         Assignments distribution = fieldAsset.buildPropDistribution(parentSeed, materialCache, fieldAsset.getRuntime(), referenceBundle, workerIndexer);
/*     */         
/* 152 */         PropField field = new PropField(fieldAsset.getRuntime(), distribution, positionProvider);
/* 153 */         biome.addPropFieldTo(field);
/*     */       } 
/*     */     } 
/* 156 */     return (BiomeType)biome;
/*     */   }
/*     */   public String getBiomeName() {
/* 159 */     return this.biomeName;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 163 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\biomes\BiomeAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */