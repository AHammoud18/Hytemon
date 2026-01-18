/*     */ package com.hypixel.hytale.server.core.modules.entity.hitboxcollision;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.CollisionType;
/*     */ import com.hypixel.hytale.protocol.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class HitboxCollisionConfig
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, HitboxCollisionConfig>>, NetworkSerializable<HitboxCollisionConfig>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, HitboxCollisionConfig> CODEC;
/*     */   
/*     */   static {
/*  47 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(HitboxCollisionConfig.class, HitboxCollisionConfig::new, (Codec)Codec.STRING, (hitboxCollisionConfig, s) -> hitboxCollisionConfig.id = s, hitboxCollisionConfig -> hitboxCollisionConfig.id, (hitboxCollisionConfig, data) -> hitboxCollisionConfig.data = data, hitboxCollisionConfig -> hitboxCollisionConfig.data).appendInherited(new KeyedCodec("CollisionType", (Codec)new EnumCodec(CollisionType.class)), (hitboxCollisionConfig, collisionType) -> hitboxCollisionConfig.collisionType = collisionType, hitboxCollisionConfig -> hitboxCollisionConfig.collisionType, (hitboxCollisionConfig, parent) -> hitboxCollisionConfig.collisionType = parent.collisionType).addValidator(Validators.nonNull()).documentation("The type of collision, possible values are: Hard, Soft").add()).appendInherited(new KeyedCodec("SoftCollisionOffsetRatio", (Codec)Codec.FLOAT), (hitboxCollisionConfig, aFloat) -> hitboxCollisionConfig.softOffsetRatio = aFloat.floatValue(), hitboxCollisionConfig -> Float.valueOf(hitboxCollisionConfig.softOffsetRatio), (hitboxCollisionConfig, parent) -> hitboxCollisionConfig.softOffsetRatio = parent.softOffsetRatio).documentation("The ratio for how much of the client move offset should be applied when going through a Soft HitboxCollision").add()).build();
/*     */   }
/*  49 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(HitboxCollisionConfig::getAssetStore));
/*     */   private static AssetStore<String, HitboxCollisionConfig, IndexedLookupTableAssetMap<String, HitboxCollisionConfig>> ASSET_STORE;
/*     */   public static final int NO_HITBOX = -1;
/*     */   
/*     */   public static AssetStore<String, HitboxCollisionConfig, IndexedLookupTableAssetMap<String, HitboxCollisionConfig>> getAssetStore() {
/*  54 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(HitboxCollisionConfig.class); 
/*  55 */     return ASSET_STORE;
/*     */   }
/*     */   protected AssetExtraInfo.Data data; protected String id; protected CollisionType collisionType;
/*     */   public static IndexedLookupTableAssetMap<String, HitboxCollisionConfig> getAssetMap() {
/*  59 */     return (IndexedLookupTableAssetMap<String, HitboxCollisionConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   protected float softOffsetRatio = 1.0F;
/*     */   
/*     */   public HitboxCollisionConfig(String id) {
/*  71 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public CollisionType getCollisionType() {
/*  83 */     return this.collisionType;
/*     */   }
/*     */   
/*     */   public float getSoftOffsetRatio() {
/*  87 */     return this.softOffsetRatio;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public HitboxCollisionConfig toPacket() {
/*  93 */     HitboxCollisionConfig packet = new HitboxCollisionConfig();
/*  94 */     packet.collisionType = this.collisionType;
/*  95 */     packet.softCollisionOffsetRatio = this.softOffsetRatio;
/*  96 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 102 */     return "HitboxCollisionConfig{data=" + String.valueOf(this.data) + ", id='" + this.id + "', collisionType=" + String.valueOf(this.collisionType) + ", softOffsetRatio=" + this.softOffsetRatio + "}";
/*     */   }
/*     */   
/*     */   public HitboxCollisionConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\hitboxcollision\HitboxCollisionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */