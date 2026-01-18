/*     */ package com.hypixel.hytale.builtin.adventure.farming.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.range.IntRange;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class FarmingCoopAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, FarmingCoopAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, FarmingCoopAsset> CODEC;
/*     */   private static AssetStore<String, FarmingCoopAsset, DefaultAssetMap<String, FarmingCoopAsset>> ASSET_STORE;
/*     */   private AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected int maxResidents;
/*     */   
/*     */   static {
/*  88 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(FarmingCoopAsset.class, FarmingCoopAsset::new, (Codec)Codec.STRING, (o, v) -> o.id = v, FarmingCoopAsset::getId, (o, data) -> o.data = data, o -> o.data).appendInherited(new KeyedCodec("MaxResidents", (Codec)Codec.INTEGER), (asset, maxResidents) -> asset.maxResidents = maxResidents.intValue(), asset -> Integer.valueOf(asset.maxResidents), (asset, parent) -> asset.maxResidents = parent.maxResidents).add()).append(new KeyedCodec("ProduceDrops", (Codec)new MapCodec(ItemDropList.CHILD_ASSET_CODEC, java.util.HashMap::new)), (asset, drops) -> asset.produceDrops = drops, asset -> asset.produceDrops).addValidator((Validator)ItemDropList.VALIDATOR_CACHE.getMapValueValidator()).add()).append(new KeyedCodec("ResidentSpawnOffset", (Codec)Vector3d.CODEC), (asset, residentSpawnOffset) -> asset.residentSpawnOffset.assign(residentSpawnOffset), asset -> asset.residentSpawnOffset).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ResidentRoamTime", (Codec)IntRange.CODEC), (asset, residentRoamTime) -> asset.residentRoamTime = residentRoamTime, asset -> asset.residentRoamTime).add()).append(new KeyedCodec("CaptureWildNPCsInRange", (Codec)Codec.BOOLEAN), (asset, captureWildNPCsInRange) -> asset.captureWildNPCsInRange = captureWildNPCsInRange.booleanValue(), asset -> Boolean.valueOf(asset.captureWildNPCsInRange)).add()).append(new KeyedCodec("WildCaptureRadius", (Codec)Codec.FLOAT), (asset, wildCaptureRadius) -> asset.wildCaptureRadius = wildCaptureRadius.floatValue(), asset -> Float.valueOf(asset.wildCaptureRadius)).add()).appendInherited(new KeyedCodec("AcceptedNpcGroups", NPCGroup.CHILD_ASSET_CODEC_ARRAY), (o, v) -> o.acceptedNpcGroupIds = v, o -> o.acceptedNpcGroupIds, (o, p) -> o.acceptedNpcGroupIds = p.acceptedNpcGroupIds).addValidator((Validator)NPCGroup.VALIDATOR_CACHE.getArrayValidator()).add()).afterDecode(captureData -> { if (captureData.acceptedNpcGroupIds != null) { captureData.acceptedNpcGroupIndexes = new int[captureData.acceptedNpcGroupIds.length]; for (int i = 0; i < captureData.acceptedNpcGroupIds.length; i++) { int assetIdx = NPCGroup.getAssetMap().getIndex(captureData.acceptedNpcGroupIds[i]); captureData.acceptedNpcGroupIndexes[i] = assetIdx; }  }  })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, FarmingCoopAsset, DefaultAssetMap<String, FarmingCoopAsset>> getAssetStore() {
/*  93 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(FarmingCoopAsset.class); 
/*  94 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, FarmingCoopAsset> getAssetMap() {
/*  98 */     return (DefaultAssetMap<String, FarmingCoopAsset>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected Map<String, String> produceDrops = Collections.emptyMap();
/*     */   protected IntRange residentRoamTime;
/* 107 */   protected Vector3d residentSpawnOffset = new Vector3d();
/*     */   
/*     */   protected String[] acceptedNpcGroupIds;
/*     */   
/*     */   protected int[] acceptedNpcGroupIndexes;
/*     */   
/*     */   protected boolean captureWildNPCsInRange;
/*     */   
/*     */   protected float wildCaptureRadius;
/*     */ 
/*     */   
/*     */   public FarmingCoopAsset(String id) {
/* 119 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 124 */     return this.id;
/*     */   }
/*     */   public Map<String, String> getProduceDrops() {
/* 127 */     return this.produceDrops;
/*     */   } public int getMaxResidents() {
/* 129 */     return this.maxResidents;
/*     */   }
/*     */   public IntRange getResidentRoamTime() {
/* 132 */     return this.residentRoamTime;
/*     */   }
/*     */   public Vector3d getResidentSpawnOffset() {
/* 135 */     return this.residentSpawnOffset;
/*     */   }
/*     */   public int[] getAcceptedNpcGroupIndexes() {
/* 138 */     return this.acceptedNpcGroupIndexes;
/*     */   }
/*     */   
/*     */   public float getWildCaptureRadius() {
/* 142 */     return this.wildCaptureRadius;
/*     */   }
/*     */   
/*     */   public boolean getCaptureWildNPCsInRange() {
/* 146 */     return this.captureWildNPCsInRange;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 152 */     return "FarmingCoopAsset{id='" + this.id + "', maxResidents=" + this.maxResidents + "}";
/*     */   }
/*     */   
/*     */   public FarmingCoopAsset() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\FarmingCoopAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */