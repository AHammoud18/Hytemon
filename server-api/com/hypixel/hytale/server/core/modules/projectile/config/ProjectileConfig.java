/*     */ package com.hypixel.hytale.server.core.modules.projectile.config;
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
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.PhysicsConfig;
/*     */ import com.hypixel.hytale.protocol.ProjectileConfig;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
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
/*     */ public class ProjectileConfig
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ProjectileConfig>>, NetworkSerializable<ProjectileConfig>, BallisticData
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, ProjectileConfig> CODEC;
/*     */   @Nullable
/*     */   private static AssetStore<String, ProjectileConfig, DefaultAssetMap<String, ProjectileConfig>> ASSET_STORE;
/*     */   
/*     */   static {
/* 135 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ProjectileConfig.class, ProjectileConfig::new, (Codec)Codec.STRING, (config, s) -> config.id = s, config -> config.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Physics", (Codec)PhysicsConfig.CODEC), (o, i) -> o.physicsConfig = i, o -> o.physicsConfig, (o, p) -> o.physicsConfig = p.physicsConfig).add()).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (o, i) -> o.model = i, o -> o.model, (o, p) -> o.model = p.model).addValidator(Validators.nonNull()).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("LaunchForce", (Codec)Codec.DOUBLE), (o, i) -> o.launchForce = i.doubleValue(), o -> Double.valueOf(o.launchForce), (o, p) -> o.launchForce = p.launchForce).add()).appendInherited(new KeyedCodec("SpawnOffset", (Codec)ProtocolCodecs.VECTOR3F), (o, i) -> o.spawnOffset = i, o -> o.spawnOffset, (o, p) -> o.spawnOffset = p.spawnOffset).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("SpawnRotationOffset", (Codec)ProtocolCodecs.DIRECTION), (o, i) -> { o.spawnRotationOffset = i; o.spawnRotationOffset.yaw *= 0.017453292F; o.spawnRotationOffset.pitch *= 0.017453292F; o.spawnRotationOffset.roll *= 0.017453292F; }o -> o.spawnRotationOffset, (o, p) -> o.spawnRotationOffset = p.spawnRotationOffset).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (o, i) -> o.interactions = i, o -> o.interactions, (o, p) -> o.interactions = p.interactions).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getMapValueValidator().late()).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("LaunchLocalSoundEventId", (Codec)Codec.STRING), (o, i) -> o.launchLocalSoundEventId = i, o -> o.launchLocalSoundEventId, (o, p) -> o.launchLocalSoundEventId = p.launchLocalSoundEventId).addValidator((Validator)SoundEventValidators.ONESHOT).documentation("The sound event played to the throwing player when the projectile is spawned/launched").add()).appendInherited(new KeyedCodec("LaunchWorldSoundEventId", (Codec)Codec.STRING), (o, i) -> o.launchWorldSoundEventId = i, o -> o.launchWorldSoundEventId, (o, p) -> o.launchWorldSoundEventId = p.launchWorldSoundEventId).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).documentation("The positioned sound event played to surrounding players when the projectile is spawned/launched").add()).appendInherited(new KeyedCodec("ProjectileSoundEventId", (Codec)Codec.STRING), (o, i) -> o.projectileSoundEventId = i, o -> o.projectileSoundEventId, (o, p) -> o.projectileSoundEventId = p.projectileSoundEventId).addValidator((Validator)SoundEventValidators.LOOPING).addValidator((Validator)SoundEventValidators.MONO).documentation("The looping sound event to attach to the projectile.").add()).afterDecode(ProjectileConfig::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, ProjectileConfig, DefaultAssetMap<String, ProjectileConfig>> getAssetStore() {
/* 145 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ProjectileConfig.class); 
/* 146 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static DefaultAssetMap<String, ProjectileConfig> getAssetMap() {
/* 154 */     return (DefaultAssetMap<String, ProjectileConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 161 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ProjectileConfig::getAssetStore));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 178 */   protected PhysicsConfig physicsConfig = StandardPhysicsConfig.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String model;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Model generatedModel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   protected double launchForce = 1.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 199 */   protected Vector3f spawnOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 205 */   protected Direction spawnRotationOffset = new Direction(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 212 */   protected Map<InteractionType, String> interactions = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String launchLocalSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String launchWorldSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String projectileSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   protected int launchLocalSoundEventIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   protected int launchWorldSoundEventIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   protected int projectileSoundEventIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getId() {
/* 250 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/* 257 */     if (this.launchWorldSoundEventId != null) {
/* 258 */       this.launchWorldSoundEventIndex = this.launchLocalSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.launchWorldSoundEventId);
/*     */     }
/*     */     
/* 261 */     if (this.launchLocalSoundEventId != null) {
/* 262 */       this.launchLocalSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.launchLocalSoundEventId);
/*     */     }
/*     */     
/* 265 */     if (this.projectileSoundEventId != null) {
/* 266 */       this.projectileSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.projectileSoundEventId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PhysicsConfig getPhysicsConfig() {
/* 275 */     return this.physicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Model getModel() {
/* 283 */     if (this.generatedModel != null) return this.generatedModel; 
/* 284 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.model);
/* 285 */     this.generatedModel = Model.createUnitScaleModel(modelAsset);
/* 286 */     return this.generatedModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLaunchForce() {
/* 293 */     return this.launchForce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMuzzleVelocity() {
/* 300 */     return this.launchForce;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getGravity() {
/* 305 */     return this.physicsConfig.getGravity();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getVerticalCenterShot() {
/* 310 */     return this.spawnOffset.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDepthShot() {
/* 315 */     return this.spawnOffset.z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPitchAdjustShot() {
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<InteractionType, String> getInteractions() {
/* 328 */     return this.interactions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLaunchWorldSoundEventIndex() {
/* 335 */     return this.launchWorldSoundEventIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProjectileSoundEventIndex() {
/* 342 */     return this.projectileSoundEventIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getSpawnOffset() {
/* 350 */     return this.spawnOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Direction getSpawnRotationOffset() {
/* 358 */     return this.spawnRotationOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getCalculatedOffset(float pitch, float yaw) {
/* 370 */     Vector3d offset = new Vector3d(this.spawnOffset.x, this.spawnOffset.y, this.spawnOffset.z);
/* 371 */     offset.rotateX(pitch);
/* 372 */     offset.rotateY(yaw);
/* 373 */     return offset;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ProjectileConfig toPacket() {
/* 379 */     ProjectileConfig config = new ProjectileConfig();
/* 380 */     config.physicsConfig = (PhysicsConfig)this.physicsConfig.toPacket();
/* 381 */     config.model = getModel().toPacket();
/* 382 */     config.launchForce = this.launchForce;
/* 383 */     config.spawnOffset = this.spawnOffset;
/* 384 */     config.rotationOffset = this.spawnRotationOffset;
/* 385 */     config.launchLocalSoundEventIndex = this.launchLocalSoundEventIndex;
/* 386 */     config.projectileSoundEventIndex = this.projectileSoundEventIndex;
/*     */     
/* 388 */     config.interactions = new EnumMap<>(InteractionType.class);
/* 389 */     for (Map.Entry<InteractionType, String> e : this.interactions.entrySet()) {
/* 390 */       config.interactions.put(e.getKey(), Integer.valueOf(RootInteraction.getRootInteractionIdOrUnknown(e.getValue())));
/*     */     }
/* 392 */     return config;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\config\ProjectileConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */