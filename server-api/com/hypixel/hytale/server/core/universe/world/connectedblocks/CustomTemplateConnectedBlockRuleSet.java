/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
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
/*     */ public class CustomTemplateConnectedBlockRuleSet
/*     */   extends ConnectedBlockRuleSet
/*     */ {
/*     */   public static final BuilderCodec<CustomTemplateConnectedBlockRuleSet> CODEC;
/*     */   private String shapeAssetId;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CustomTemplateConnectedBlockRuleSet.class, CustomTemplateConnectedBlockRuleSet::new).append(new KeyedCodec("TemplateShapeAssetId", (Codec)Codec.STRING), (ruleSet, shapeAssetId) -> ruleSet.shapeAssetId = shapeAssetId, ruleSet -> ruleSet.shapeAssetId).addValidator(CustomConnectedBlockTemplateAsset.VALIDATOR_CACHE.getValidator()).documentation("The name of a ConnectedBlockTemplateAsset asset").add()).append(new KeyedCodec("TemplateShapeBlockPatterns", (Codec)new MapCodec(BlockPattern.CODEC, java.util.HashMap::new), true), (material, shapeNameToBlockPatternMap) -> material.shapeNameToBlockPatternMap = shapeNameToBlockPatternMap, material -> material.shapeNameToBlockPatternMap).documentation("You must specify all shapes as a BlockPattern. The shapes are as outlined in the keys of the ShapeTemplateAsset's map.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private Map<String, BlockPattern> shapeNameToBlockPatternMap = (Map<String, BlockPattern>)new Object2ObjectOpenHashMap();
/*  51 */   private final Int2ObjectMap<Set<String>> shapesPerBlockType = (Int2ObjectMap<Set<String>>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   public Map<String, BlockPattern> getShapeNameToBlockPatternMap() {
/*  54 */     return this.shapeNameToBlockPatternMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCachedBlockTypes(BlockType blockType, BlockTypeAssetMap<String, BlockType> assetMap) {
/*  59 */     super.updateCachedBlockTypes(blockType, assetMap);
/*     */ 
/*     */     
/*  62 */     for (Map.Entry<String, BlockPattern> entry : this.shapeNameToBlockPatternMap.entrySet()) {
/*  63 */       String name = entry.getKey();
/*  64 */       BlockPattern blockPattern = entry.getValue(); Integer[] arrayOfInteger; int i;
/*     */       byte b;
/*  66 */       for (arrayOfInteger = blockPattern.getResolvedKeys(), i = arrayOfInteger.length, b = 0; b < i; ) { int resolvedKey = arrayOfInteger[b].intValue();
/*  67 */         Set<String> shapes = (Set<String>)this.shapesPerBlockType.computeIfAbsent(resolvedKey, k -> new ObjectOpenHashSet());
/*  68 */         shapes.add(name);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Set<String> getShapesForBlockType(int blockTypeKey) {
/*  78 */     return (Set<String>)this.shapesPerBlockType.getOrDefault(blockTypeKey, Set.of());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CustomConnectedBlockTemplateAsset getShapeTemplateAsset() {
/*  83 */     return (CustomConnectedBlockTemplateAsset)CustomConnectedBlockTemplateAsset.getAssetMap().getAsset(this.shapeAssetId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyUpdateOnPlacement() {
/*  88 */     CustomConnectedBlockTemplateAsset templateAsset = getShapeTemplateAsset();
/*  89 */     return (templateAsset != null && templateAsset.isDontUpdateAfterInitialPlacement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockType(World world, Vector3i testedCoordinate, BlockType blockType, int rotation, Vector3i placementNormal, boolean isPlacement) {
/*  99 */     CustomConnectedBlockTemplateAsset shapeTemplateAsset = getShapeTemplateAsset();
/* 100 */     if (shapeTemplateAsset == null) return Optional.empty();
/*     */     
/* 102 */     return shapeTemplateAsset.getConnectedBlockType(world, testedCoordinate, this, blockType, rotation, placementNormal, true, isPlacement);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\CustomTemplateConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */