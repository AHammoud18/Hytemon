/*     */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
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
/*     */ public class DamageCause
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, DamageCause>>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, DamageCause> CODEC;
/*     */   private static AssetStore<String, DamageCause, IndexedLookupTableAssetMap<String, DamageCause>> ASSET_STORE;
/*     */   
/*     */   static {
/*  64 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(DamageCause.class, DamageCause::new, (Codec)Codec.STRING, (damageCause, s) -> damageCause.id = s, damageCause -> damageCause.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Inherits", (Codec)Codec.STRING), (builder, name) -> builder.inherits = name, builder -> builder.inherits).add()).append(new KeyedCodec("DurabilityLoss", (Codec)Codec.BOOLEAN), (builder, name) -> builder.durabilityLoss = name.booleanValue(), builder -> Boolean.valueOf(builder.durabilityLoss)).add()).append(new KeyedCodec("StaminaLoss", (Codec)Codec.BOOLEAN), (builder, name) -> builder.staminaLoss = name.booleanValue(), builder -> Boolean.valueOf(builder.staminaLoss)).add()).append(new KeyedCodec("BypassResistances", (Codec)Codec.BOOLEAN), (builder, name) -> builder.bypassResistances = name.booleanValue(), builder -> Boolean.valueOf(builder.bypassResistances)).add()).append(new KeyedCodec("DamageTextColor", (Codec)Codec.STRING), (builder, name) -> builder.damageTextColor = name, builder -> builder.damageTextColor).add()).append(new KeyedCodec("AnimationId", (Codec)Codec.STRING), (builder, name) -> builder.animationId = name, builder -> builder.animationId).add()).append(new KeyedCodec("DeathAnimationId", (Codec)Codec.STRING), (builder, name) -> builder.deathAnimationId = name, builder -> builder.deathAnimationId).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause PHYSICAL;
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause PROJECTILE;
/*     */   @Nonnull
/*  75 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(DamageCause::getAssetStore)); @Nullable @Deprecated public static DamageCause COMMAND; @Nullable @Deprecated
/*     */   public static DamageCause DROWNING; @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause ENVIRONMENT; @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause FALL; @Nonnull
/*  81 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(DamageCause.class, (AssetCodec)CODEC);
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause OUT_OF_WORLD;
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, DamageCause, IndexedLookupTableAssetMap<String, DamageCause>> getAssetStore() {
/*  88 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(DamageCause.class); 
/*  89 */     return ASSET_STORE;
/*     */   }
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static DamageCause SUFFOCATION; protected AssetExtraInfo.Data data; protected String id; protected String inherits;
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedLookupTableAssetMap<String, DamageCause> getAssetMap() {
/*  97 */     return (IndexedLookupTableAssetMap<String, DamageCause>)getAssetStore().getAssetMap();
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
/*     */   protected boolean durabilityLoss;
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
/*     */   protected boolean staminaLoss;
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
/*     */   protected boolean bypassResistances;
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
/*     */   protected String damageTextColor;
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
/* 170 */   protected String animationId = "Hurt";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   protected String deathAnimationId = "Death";
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
/*     */   public DamageCause(@Nonnull String id) {
/* 190 */     this.id = id;
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
/*     */   public DamageCause(@Nonnull String id, @Nonnull String inherits, boolean durabilityLoss, boolean staminaLoss, boolean bypassResistances) {
/* 203 */     this.id = id;
/* 204 */     this.inherits = inherits;
/* 205 */     this.durabilityLoss = durabilityLoss;
/* 206 */     this.staminaLoss = staminaLoss;
/* 207 */     this.bypassResistances = bypassResistances;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 212 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDurabilityLoss() {
/* 219 */     return this.durabilityLoss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStaminaLoss() {
/* 226 */     return this.staminaLoss;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesBypassResistances() {
/* 233 */     return this.bypassResistances;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInherits() {
/* 240 */     return this.inherits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAnimationId() {
/* 247 */     return this.animationId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeathAnimationId() {
/* 254 */     return this.deathAnimationId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public com.hypixel.hytale.protocol.DamageCause toPacket() {
/* 259 */     return new com.hypixel.hytale.protocol.DamageCause(this.id, this.damageTextColor);
/*     */   }
/*     */   
/*     */   public DamageCause() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DamageCause.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */