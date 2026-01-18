/*     */ package com.hypixel.hytale.builtin.hytalegenerator.assets.density;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.Cleanable;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.environmentproviders.EnvironmentProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.MaterialProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders.PositionProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments.AssignmentsAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.tintproviders.TintProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.vectorproviders.VectorProviderAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.assets.worldstructures.WorldStructureAsset;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.referencebundle.ReferenceBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class DensityAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, DensityAsset>>, Cleanable {
/*  37 */   private static final DensityAsset[] EMPTY_INPUTS = new DensityAsset[0]; public static final AssetCodecMapCodec<String, DensityAsset> CODEC;
/*     */   static {
/*  39 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Exported
/*     */   {
/*     */     public boolean singleInstance;
/*     */ 
/*     */     
/*     */     public DensityAsset asset;
/*     */     
/*     */     @Nullable
/*     */     public Density builtInstance;
/*     */   }
/*     */   
/*  55 */   private static final Map<String, Exported> exportedNodes = new ConcurrentHashMap<>();
/*     */   
/*  57 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(DensityAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<DensityAsset> ABSTRACT_CODEC; private String id; private AssetExtraInfo.Data data; static {
/*  58 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
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
/*  97 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(DensityAsset.class).append(new KeyedCodec("Inputs", (Codec)new ArrayCodec((Codec)CODEC, x$0 -> new DensityAsset[x$0]), true), (t, k) -> t.inputs = k, t -> t.inputs).add()).append(new KeyedCodec("Skip", (Codec)Codec.BOOLEAN, false), (t, k) -> t.skip = k.booleanValue(), t -> Boolean.valueOf(t.skip)).add()).append(new KeyedCodec("ExportAs", (Codec)Codec.STRING, false), (t, k) -> t.exportName = k, t -> t.exportName).add()).afterDecode(asset -> { if (asset.exportName != null && !asset.exportName.isEmpty()) { if (exportedNodes.containsKey(asset.exportName)) LoggerUtil.getLogger().warning("Duplicate export name for asset: " + asset.exportName);  Exported exported = new Exported(); exported.asset = asset; if (asset instanceof ExportedDensityAsset) { ExportedDensityAsset exportedAsset = (ExportedDensityAsset)asset; exported.singleInstance = exportedAsset.isSingleInstance(); } else { exported.singleInstance = false; }  exportedNodes.put(asset.exportName, exported); LoggerUtil.getLogger().fine("Registered imported node asset with name '" + asset.exportName + "' with asset id '" + asset.id); }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 102 */   private DensityAsset[] inputs = EMPTY_INPUTS;
/*     */   private boolean skip = false;
/* 104 */   protected String exportName = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanUp() {
/* 113 */     cleanUpInputs();
/*     */   }
/*     */   
/*     */   protected void cleanUpInputs() {
/* 117 */     for (DensityAsset input : this.inputs) input.cleanUp(); 
/*     */   }
/*     */   @Nonnull
/*     */   public static DensityAsset getFallbackAsset() {
/* 121 */     return new ConstantDensityAsset();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Density buildWithInputs(@Nonnull Argument argument, @Nonnull Density[] inputs) {
/* 126 */     Density node = build(argument);
/* 127 */     node.setInputs(inputs);
/* 128 */     return node;
/*     */   }
/*     */   
/*     */   public DensityAsset[] inputs() {
/* 132 */     return this.inputs;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<Density> buildInputs(@Nonnull Argument argument, boolean excludeSkipped) {
/* 138 */     ArrayList<Density> nodes = new ArrayList<>();
/* 139 */     for (DensityAsset asset : this.inputs) {
/* 140 */       if (!excludeSkipped || !asset.isSkipped())
/* 141 */         nodes.add(asset.build(argument)); 
/*     */     } 
/* 143 */     return nodes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Density[] buildInputsArray(@Nonnull Argument argument) {
/* 148 */     Density[] nodes = new Density[this.inputs.length];
/* 149 */     int i = 0;
/* 150 */     for (DensityAsset asset : this.inputs) {
/* 151 */       nodes[i++] = asset.build(argument);
/*     */     }
/* 153 */     return nodes;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DensityAsset firstInput() {
/* 158 */     if (this.inputs.length > 0)
/* 159 */       return this.inputs[0]; 
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DensityAsset secondInput() {
/* 165 */     if (this.inputs.length > 1)
/* 166 */       return this.inputs[1]; 
/* 167 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Density buildFirstInput(@Nonnull Argument argument) {
/* 172 */     if (firstInput() == null) return null; 
/* 173 */     return firstInput().build(argument);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Density buildSecondInput(@Nonnull Argument argument) {
/* 178 */     if (secondInput() == null) return null; 
/* 179 */     return secondInput().build(argument);
/*     */   }
/*     */   
/*     */   public boolean isSkipped() {
/* 183 */     return this.skip;
/*     */   }
/*     */   
/*     */   public static Exported getExportedAsset(@Nonnull String name) {
/* 187 */     return exportedNodes.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 192 */     return this.id;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull VectorProviderAsset.Argument argument) {
/* 197 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull MaterialProviderAsset.Argument argument) {
/* 202 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull PropAsset.Argument argument) {
/* 207 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull PatternAsset.Argument argument) {
/* 212 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull PositionProviderAsset.Argument argument) {
/* 217 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull AssignmentsAsset.Argument argument) {
/* 222 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull WorldStructureAsset.Argument argument, @Nonnull ReferenceBundle referenceBundle) {
/* 227 */     return new Argument(argument.parentSeed, referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull EnvironmentProviderAsset.Argument argument) {
/* 232 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Argument from(@Nonnull TintProviderAsset.Argument argument) {
/* 237 */     return new Argument(argument.parentSeed, argument.referenceBundle, argument.workerIndexer);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public abstract Density build(@Nonnull Argument paramArgument);
/*     */   
/*     */   public static class Argument {
/*     */     public SeedBox parentSeed;
/*     */     public ReferenceBundle referenceBundle;
/*     */     public WorkerIndexer workerIndexer;
/*     */     
/*     */     public Argument(@Nonnull SeedBox parentSeed, @Nonnull ReferenceBundle referenceBundle, @Nonnull WorkerIndexer workerIndexer) {
/* 249 */       this.parentSeed = parentSeed;
/* 250 */       this.referenceBundle = referenceBundle;
/* 251 */       this.workerIndexer = workerIndexer;
/*     */     }
/*     */     
/*     */     public Argument(@Nonnull Argument argument) {
/* 255 */       this.parentSeed = argument.parentSeed;
/* 256 */       this.referenceBundle = argument.referenceBundle;
/* 257 */       this.workerIndexer = argument.workerIndexer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\density\DensityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */