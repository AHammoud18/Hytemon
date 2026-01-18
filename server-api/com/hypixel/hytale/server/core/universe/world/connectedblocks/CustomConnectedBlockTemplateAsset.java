/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ public class CustomConnectedBlockTemplateAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, CustomConnectedBlockTemplateAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, CustomConnectedBlockTemplateAsset> CODEC;
/*     */   
/*     */   static {
/*  54 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(CustomConnectedBlockTemplateAsset.class, CustomConnectedBlockTemplateAsset::new, (Codec)Codec.STRING, (builder, id) -> builder.id = id, builder -> builder.id, (builder, data) -> builder.data = data, builder -> builder.data).append(new KeyedCodec("DontUpdateAfterInitialPlacement", (Codec)Codec.BOOLEAN, false), (o, dontUpdateAfterInitialPlacement) -> o.dontUpdateAfterInitialPlacement = dontUpdateAfterInitialPlacement.booleanValue(), o -> Boolean.valueOf(o.dontUpdateAfterInitialPlacement)).documentation("Default to false. When true, will not update the connected block after initial placement. Neighboring block updates won't affect this block when true.").add()).append(new KeyedCodec("ConnectsToOtherMaterials", (Codec)Codec.BOOLEAN, false), (o, connectsToOtherMaterials) -> o.connectsToOtherMaterials = connectsToOtherMaterials.booleanValue(), o -> Boolean.valueOf(o.connectsToOtherMaterials)).documentation("Defaults to true. If true, the material will connect to other materials of different block type sets, if false, the material will only connect to its own block types within the material").add()).append(new KeyedCodec("DefaultShape", (Codec)Codec.STRING, false), (o, defaultShapeName) -> o.defaultShapeName = defaultShapeName, o -> o.defaultShapeName).add()).append(new KeyedCodec("Shapes", (Codec)new MapCodec((Codec)ConnectedBlockShape.CODEC, java.util.HashMap::new), true), (o, connectedBlockShapes) -> o.connectedBlockShapes = connectedBlockShapes, o -> o.connectedBlockShapes).add()).build();
/*     */   }
/*  56 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(CustomConnectedBlockTemplateAsset::getAssetStore)); private static AssetStore<String, CustomConnectedBlockTemplateAsset, DefaultAssetMap<String, CustomConnectedBlockTemplateAsset>> ASSET_STORE;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   public static AssetStore<String, CustomConnectedBlockTemplateAsset, DefaultAssetMap<String, CustomConnectedBlockTemplateAsset>> getAssetStore() {
/*  61 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(CustomConnectedBlockTemplateAsset.class); 
/*  62 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, CustomConnectedBlockTemplateAsset> getAssetMap() {
/*  66 */     return (DefaultAssetMap<String, CustomConnectedBlockTemplateAsset>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean connectsToOtherMaterials = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dontUpdateAfterInitialPlacement;
/*     */ 
/*     */ 
/*     */   
/*     */   private String defaultShapeName;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, ConnectedBlockShape> connectedBlockShapes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockType(World world, Vector3i coordinate, CustomTemplateConnectedBlockRuleSet ruleSet, BlockType blockType, int rotation, Vector3i placementNormal, boolean useDefaultShapeIfNoMatch, boolean isPlacement) {
/*  93 */     for (Map.Entry<String, ConnectedBlockShape> entry : this.connectedBlockShapes.entrySet()) {
/*  94 */       ConnectedBlockShape connectedBlockShape = entry.getValue();
/*  95 */       if (connectedBlockShape == null)
/*     */         continue; 
/*  97 */       CustomTemplateConnectedBlockPattern[] patterns = connectedBlockShape.getPatternsToMatchAnyOf();
/*  98 */       if (patterns == null)
/*     */         continue;  CustomTemplateConnectedBlockPattern[] arrayOfCustomTemplateConnectedBlockPattern1; int i; byte b;
/* 100 */       for (arrayOfCustomTemplateConnectedBlockPattern1 = patterns, i = arrayOfCustomTemplateConnectedBlockPattern1.length, b = 0; b < i; ) { CustomTemplateConnectedBlockPattern connectedBlockPattern = arrayOfCustomTemplateConnectedBlockPattern1[b];
/* 101 */         Optional<ConnectedBlocksUtil.ConnectedBlockResult> blockRotationIfMatchedOptional = connectedBlockPattern.getConnectedBlockTypeKey(entry.getKey(), world, coordinate, ruleSet, blockType, rotation, placementNormal, isPlacement);
/*     */         
/* 103 */         if (blockRotationIfMatchedOptional.isEmpty()) { b++; continue; }
/* 104 */          return blockRotationIfMatchedOptional; }
/*     */     
/*     */     } 
/*     */     
/* 108 */     if (useDefaultShapeIfNoMatch) {
/* 109 */       BlockPattern defaultShapeBlockPattern = ruleSet.getShapeNameToBlockPatternMap().get(this.defaultShapeName);
/*     */       
/* 111 */       if (defaultShapeBlockPattern == null) {
/* 112 */         return Optional.empty();
/*     */       }
/*     */       
/* 115 */       BlockPattern.BlockEntry defaultBlock = defaultShapeBlockPattern.nextBlockTypeKey(ThreadLocalRandom.current());
/* 116 */       return Optional.of(new ConnectedBlocksUtil.ConnectedBlockResult(defaultBlock.blockTypeKey(), rotation));
/*     */     } 
/*     */     
/* 119 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public boolean isDontUpdateAfterInitialPlacement() {
/* 123 */     return this.dontUpdateAfterInitialPlacement;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 128 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\CustomConnectedBlockTemplateAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */