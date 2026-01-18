/*     */ package com.hypixel.hytale.server.core.modules.entity.repulsion;
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
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.protocol.RepulsionConfig;
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
/*     */ public class RepulsionConfig
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, RepulsionConfig>>, NetworkSerializable<RepulsionConfig>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, RepulsionConfig> CODEC;
/*     */   
/*     */   static {
/*  55 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(RepulsionConfig.class, RepulsionConfig::new, (Codec)Codec.STRING, (repulsion, s) -> repulsion.id = s, repulsion -> repulsion.id, (repulsion, data) -> repulsion.data = data, repulsion -> repulsion.data).appendInherited(new KeyedCodec("Radius", (Codec)Codec.FLOAT), (repulsion, radius) -> repulsion.radius = radius.floatValue(), repulsion -> Float.valueOf(repulsion.radius), (repulsion, parent) -> repulsion.radius = parent.radius).documentation("The radius around the entity").add()).appendInherited(new KeyedCodec("MinForce", (Codec)Codec.FLOAT), (repulsion, minForce) -> repulsion.minForce = minForce.floatValue(), repulsion -> Float.valueOf(repulsion.minForce), (repulsion, parent) -> repulsion.minForce = parent.minForce).documentation("The floor of the applied force while within effective radius").add()).appendInherited(new KeyedCodec("MaxForce", (Codec)Codec.FLOAT), (repulsion, maxForce) -> repulsion.maxForce = maxForce.floatValue(), repulsion -> Float.valueOf(repulsion.maxForce), (repulsion, parent) -> repulsion.maxForce = parent.maxForce).documentation("The max force to be applied at 100% intersection").add()).build();
/*     */   }
/*  57 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(RepulsionConfig::getAssetStore)); private static AssetStore<String, RepulsionConfig, IndexedLookupTableAssetMap<String, RepulsionConfig>> ASSET_STORE;
/*     */   public static final int NO_REPULSION = -1;
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   public static AssetStore<String, RepulsionConfig, IndexedLookupTableAssetMap<String, RepulsionConfig>> getAssetStore() {
/*  62 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(RepulsionConfig.class); 
/*  63 */     return ASSET_STORE;
/*     */   }
/*     */   protected String id;
/*     */   protected float radius;
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, RepulsionConfig> getAssetMap() {
/*  69 */     return (IndexedLookupTableAssetMap<String, RepulsionConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float minForce;
/*     */ 
/*     */   
/*     */   protected float maxForce;
/*     */ 
/*     */   
/*     */   public RepulsionConfig() {}
/*     */ 
/*     */   
/*     */   public RepulsionConfig(String id) {
/*  84 */     this.id = id;
/*     */   }
/*     */   
/*     */   public RepulsionConfig(@Nonnull RepulsionConfig repulsion) {
/*  88 */     this(repulsion.radius, repulsion.minForce, repulsion.maxForce);
/*     */   }
/*     */   
/*     */   public RepulsionConfig(float radius, float maxForce) {
/*  92 */     this(radius, 0.0F, maxForce);
/*     */   }
/*     */   
/*     */   public RepulsionConfig(float radius, float minForce, float maxForce) {
/*  96 */     this.radius = radius;
/*  97 */     this.minForce = minForce;
/*  98 */     this.maxForce = maxForce;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 103 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public RepulsionConfig toPacket() {
/* 109 */     RepulsionConfig packet = new RepulsionConfig();
/* 110 */     packet.radius = this.radius;
/* 111 */     packet.minForce = this.minForce;
/* 112 */     packet.maxForce = this.maxForce;
/* 113 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 119 */     return "repulsionConfig{data=" + String.valueOf(this.data) + ", id='" + this.id + "', radius=" + this.radius + ", minForce=" + this.minForce + ", maxForce=" + this.maxForce + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\repulsion\RepulsionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */