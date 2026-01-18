/*    */ package com.hypixel.hytale.server.core.asset.type.buildertool.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ public class BlockTypeListAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BlockTypeListAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, BlockTypeListAsset> CODEC;
/*    */   private static AssetStore<String, BlockTypeListAsset, DefaultAssetMap<String, BlockTypeListAsset>> ASSET_STORE;
/*    */   
/*    */   static {
/* 49 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockTypeListAsset.class, BlockTypeListAsset::new, (Codec)Codec.STRING, (builder, id) -> builder.id = id, builder -> builder.id, (builder, data) -> builder.data = data, builder -> builder.data).append(new KeyedCodec("Blocks", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0]), true), (builder, blockTypeKeys) -> { if (blockTypeKeys == null) return;  Collections.addAll(builder.blockTypeKeys, blockTypeKeys); }builder -> (String[])builder.blockTypeKeys.toArray(())).add()).afterDecode(blockTypeListAsset -> { if (blockTypeListAsset.blockTypeKeys == null) return;  WeightedMap.Builder<String> weightedMapBuilder = WeightedMap.builder((Object[])ArrayUtil.EMPTY_STRING_ARRAY); for (String blockTypeKey : blockTypeListAsset.blockTypeKeys) weightedMapBuilder.put(blockTypeKey, 1.0D);  blockTypeListAsset.blockPattern = new BlockPattern(weightedMapBuilder.build()); })).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public static AssetStore<String, BlockTypeListAsset, DefaultAssetMap<String, BlockTypeListAsset>> getAssetStore() {
/* 54 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockTypeListAsset.class); 
/* 55 */     return ASSET_STORE;
/*    */   }
/*    */   
/*    */   public static DefaultAssetMap<String, BlockTypeListAsset> getAssetMap() {
/* 59 */     return (DefaultAssetMap<String, BlockTypeListAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */   
/* 62 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockTypeListAsset::getAssetStore));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String id;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   private final HashSet<String> blockTypeKeys = new HashSet<>();
/*    */   
/*    */   private BlockPattern blockPattern;
/*    */   
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   public BlockPattern getBlockPattern() {
/* 79 */     return this.blockPattern;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public HashSet<String> getBlockTypeKeys() {
/* 84 */     return this.blockTypeKeys;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 89 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\buildertool\config\BlockTypeListAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */