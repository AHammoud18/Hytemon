/*     */ package com.hypixel.hytale.server.flock.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.Priority;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import java.util.Arrays;
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
/*     */ public abstract class FlockAsset
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, FlockAsset>>
/*     */ {
/*     */   public static final BuilderCodec<FlockAsset> ABSTRACT_CODEC;
/*     */   public static final AssetCodecMapCodec<String, FlockAsset> CODEC;
/*     */   
/*     */   static {
/*  53 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(FlockAsset.class).documentation("A flock definition.")).appendInherited(new KeyedCodec("MaxGrowSize", (Codec)Codec.INTEGER), (flock, i) -> flock.maxGrowSize = i.intValue(), flock -> Integer.valueOf(flock.maxGrowSize), (flock, parent) -> flock.maxGrowSize = parent.maxGrowSize).documentation("The maximum size a flock can possibly grow to after spawning. It is technically possible to spawn a flock without specifying a definition (e.g. via a command), in which case the maximum grow size is irrelevant.").addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).add()).appendInherited(new KeyedCodec("BlockedRoles", (Codec)Codec.STRING_ARRAY), (flock, o) -> flock.blockedRoles = o, flock -> flock.blockedRoles, (flock, parent) -> flock.blockedRoles = parent.blockedRoles).documentation("An array of roles that will not be allowed to join this flock once it has been spawned. This is used to exclude roles from the list of allowed roles in the NPC configuration of the initial leader.").addValidator(Validators.uniqueInArray()).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     CODEC = (new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data, true)).register(Priority.DEFAULT, "Default", RangeSizeFlockAsset.class, RangeSizeFlockAsset.CODEC);
/*     */   }
/*  64 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(FlockAsset.class, (AssetCodec)CODEC);
/*     */   
/*  66 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(FlockAsset::getAssetStore)); private static AssetStore<String, FlockAsset, IndexedLookupTableAssetMap<String, FlockAsset>> ASSET_STORE;
/*     */   private AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, FlockAsset, IndexedLookupTableAssetMap<String, FlockAsset>> getAssetStore() {
/*  71 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(FlockAsset.class); 
/*  72 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, FlockAsset> getAssetMap() {
/*  76 */     return (IndexedLookupTableAssetMap<String, FlockAsset>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected int maxGrowSize = 8;
/*  83 */   protected String[] blockedRoles = ArrayUtil.EMPTY_STRING_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FlockAsset(String id) {
/*  89 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  94 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxGrowSize() {
/* 102 */     return this.maxGrowSize;
/*     */   }
/*     */   
/*     */   public String[] getBlockedRoles() {
/* 106 */     return this.blockedRoles;
/*     */   }
/*     */   
/*     */   static {
/* 110 */     CODEC.register("Weighted", WeightedSizeFlockAsset.class, WeightedSizeFlockAsset.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 116 */     return "FlockAsset{id='" + this.id + "', maxGrowSize=" + this.maxGrowSize + ", blockedRoles=" + 
/*     */ 
/*     */       
/* 119 */       Arrays.toString((Object[])this.blockedRoles) + "}";
/*     */   }
/*     */   
/*     */   protected FlockAsset() {}
/*     */   
/*     */   public abstract int getMinFlockSize();
/*     */   
/*     */   public abstract int pickFlockSize();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\config\FlockAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */