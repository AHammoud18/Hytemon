/*     */ package com.hypixel.hytale.server.core.asset.type.projectile.config;
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
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.entity.ExplosionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class Projectile
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, Projectile>>, BallisticData
/*     */ {
/*     */   public static final AssetBuilderCodec<String, Projectile> CODEC;
/*     */   private static AssetStore<String, Projectile, DefaultAssetMap<String, Projectile>> ASSET_STORE;
/*     */   
/*     */   static {
/* 268 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Projectile.class, Projectile::new, (Codec)Codec.STRING, (projectile, s) -> projectile.id = s, projectile -> projectile.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Appearance", (Codec)Codec.STRING), (projectile, s) -> projectile.appearance = s, projectile -> projectile.appearance, (projectile, parent) -> projectile.appearance = parent.appearance).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Radius", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.radius = s.doubleValue(), projectile -> Double.valueOf(projectile.radius), (projectile, parent) -> projectile.radius = parent.radius).add()).appendInherited(new KeyedCodec("Height", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.height = s.doubleValue(), projectile -> Double.valueOf(projectile.height), (projectile, parent) -> projectile.height = parent.height).add()).appendInherited(new KeyedCodec("VerticalCenterShot", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.verticalCenterShot = s.doubleValue(), projectile -> Double.valueOf(projectile.verticalCenterShot), (projectile, parent) -> projectile.verticalCenterShot = parent.verticalCenterShot).add()).appendInherited(new KeyedCodec("HorizontalCenterShot", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.horizontalCenterShot = s.doubleValue(), projectile -> Double.valueOf(projectile.horizontalCenterShot), (projectile, parent) -> projectile.horizontalCenterShot = parent.horizontalCenterShot).add()).appendInherited(new KeyedCodec("DepthShot", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.depthShot = s.doubleValue(), projectile -> Double.valueOf(projectile.depthShot), (projectile, parent) -> projectile.depthShot = parent.depthShot).add()).appendInherited(new KeyedCodec("PitchAdjustShot", (Codec)Codec.BOOLEAN), (projectile, s) -> projectile.pitchAdjustShot = s.booleanValue(), projectile -> Boolean.valueOf(projectile.pitchAdjustShot), (projectile, parent) -> projectile.pitchAdjustShot = parent.pitchAdjustShot).add()).appendInherited(new KeyedCodec("MuzzleVelocity", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.muzzleVelocity = s.doubleValue(), projectile -> Double.valueOf(projectile.muzzleVelocity), (projectile, parent) -> projectile.muzzleVelocity = parent.muzzleVelocity).add()).appendInherited(new KeyedCodec("TerminalVelocity", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.terminalVelocity = s.doubleValue(), projectile -> Double.valueOf(projectile.terminalVelocity), (projectile, parent) -> projectile.terminalVelocity = parent.terminalVelocity).add()).appendInherited(new KeyedCodec("Gravity", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.gravity = s.doubleValue(), projectile -> Double.valueOf(projectile.gravity), (projectile, parent) -> projectile.gravity = parent.gravity).add()).appendInherited(new KeyedCodec("Bounciness", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.bounciness = s.doubleValue(), projectile -> Double.valueOf(projectile.bounciness), (projectile, parent) -> projectile.bounciness = parent.bounciness).add()).appendInherited(new KeyedCodec("ImpactSlowdown", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.impactSlowdown = s.doubleValue(), projectile -> Double.valueOf(projectile.impactSlowdown), (projectile, parent) -> projectile.impactSlowdown = parent.impactSlowdown).add()).appendInherited(new KeyedCodec("SticksVertically", (Codec)Codec.BOOLEAN), (projectile, s) -> projectile.sticksVertically = s.booleanValue(), projectile -> Boolean.valueOf(projectile.sticksVertically), (projectile, parent) -> projectile.sticksVertically = parent.sticksVertically).add()).appendInherited(new KeyedCodec("ComputeYaw", (Codec)Codec.BOOLEAN), (projectile, s) -> projectile.computeYaw = s.booleanValue(), projectile -> Boolean.valueOf(projectile.computeYaw), (projectile, parent) -> projectile.computeYaw = parent.computeYaw).add()).appendInherited(new KeyedCodec("ComputePitch", (Codec)Codec.BOOLEAN), (projectile, s) -> projectile.computePitch = s.booleanValue(), projectile -> Boolean.valueOf(projectile.computePitch), (projectile, parent) -> projectile.computePitch = parent.computePitch).add()).appendInherited(new KeyedCodec("ComputeRoll", (Codec)Codec.BOOLEAN), (projectile, s) -> projectile.computeRoll = s.booleanValue(), projectile -> Boolean.valueOf(projectile.computeRoll), (projectile, parent) -> projectile.computeRoll = parent.computeRoll).add()).appendInherited(new KeyedCodec("TimeToLive", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.timeToLive = s.doubleValue(), projectile -> Double.valueOf(projectile.timeToLive), (projectile, parent) -> projectile.timeToLive = parent.timeToLive).add()).appendInherited(new KeyedCodec("BounceSoundEventId", (Codec)Codec.STRING), (projectile, s) -> projectile.bounceSoundEventId = s, projectile -> projectile.bounceSoundEventId, (projectile, parent) -> projectile.bounceSoundEventId = parent.bounceSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("BounceParticles", (Codec)WorldParticle.CODEC), (projectile, s) -> projectile.bounceParticles = s, projectile -> projectile.bounceParticles, (projectile, parent) -> projectile.bounceParticles = parent.bounceParticles).add()).appendInherited(new KeyedCodec("HitSoundEventId", (Codec)Codec.STRING), (projectile, s) -> projectile.hitSoundEventId = s, projectile -> projectile.hitSoundEventId, (projectile, parent) -> projectile.hitSoundEventId = parent.hitSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("HitParticles", (Codec)WorldParticle.CODEC), (projectile, s) -> projectile.hitParticles = s, projectile -> projectile.hitParticles, (projectile, parent) -> projectile.hitParticles = parent.hitParticles).add()).appendInherited(new KeyedCodec("Damage", (Codec)Codec.INTEGER), (projectile, s) -> projectile.damage = s.intValue(), projectile -> Integer.valueOf(projectile.damage), (projectile, parent) -> projectile.damage = parent.damage).add()).appendInherited(new KeyedCodec("DeadTime", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.deadTime = s.doubleValue(), projectile -> Double.valueOf(projectile.deadTime), (projectile, parent) -> projectile.deadTime = parent.deadTime).add()).appendInherited(new KeyedCodec("MissSoundEventId", (Codec)Codec.STRING), (projectile, s) -> projectile.missSoundEventId = s, projectile -> projectile.missSoundEventId, (projectile, parent) -> projectile.missSoundEventId = parent.missSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("MissParticles", (Codec)WorldParticle.CODEC), (projectile, s) -> projectile.missParticles = s, projectile -> projectile.missParticles, (projectile, parent) -> projectile.missParticles = parent.missParticles).add()).appendInherited(new KeyedCodec("DeadTimeMiss", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.deadTimeMiss = s.doubleValue(), projectile -> Double.valueOf(projectile.deadTimeMiss), (projectile, parent) -> projectile.deadTimeMiss = parent.deadTimeMiss).add()).appendInherited(new KeyedCodec("DeathSoundEventId", (Codec)Codec.STRING), (projectile, s) -> projectile.deathSoundEventId = s, projectile -> projectile.deathSoundEventId, (projectile, parent) -> projectile.deathSoundEventId = parent.deathSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("DeathParticles", (Codec)WorldParticle.CODEC), (projectile, s) -> projectile.deathParticles = s, projectile -> projectile.deathParticles, (projectile, parent) -> projectile.deathParticles = parent.deathParticles).add()).appendInherited(new KeyedCodec("DeathEffectsOnHit", (Codec)Codec.BOOLEAN), (projectile, b) -> projectile.deathEffectsOnHit = b.booleanValue(), projectile -> Boolean.valueOf(projectile.deathEffectsOnHit), (projectile, parent) -> projectile.deathEffectsOnHit = parent.deathEffectsOnHit).add()).appendInherited(new KeyedCodec("ExplosionConfig", (Codec)ExplosionConfig.CODEC), (projectile, s) -> projectile.explosionConfig = s, projectile -> projectile.explosionConfig, (projectile, parent) -> projectile.explosionConfig = parent.explosionConfig).documentation("The explosion config associated with this projectile").add()).appendInherited(new KeyedCodec("Density", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.density = s.doubleValue(), projectile -> Double.valueOf(projectile.density), (projectile, parent) -> projectile.density = parent.density).add()).appendInherited(new KeyedCodec("WaterTerminalVelocityMultiplier", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.waterTerminalVelocityMultiplier = s.doubleValue(), projectile -> Double.valueOf(projectile.waterTerminalVelocityMultiplier), (projectile, parent) -> projectile.waterTerminalVelocityMultiplier = parent.waterTerminalVelocityMultiplier).add()).appendInherited(new KeyedCodec("WaterHitImpulseLoss", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.waterHitImpulseLoss = s.doubleValue(), projectile -> Double.valueOf(projectile.waterHitImpulseLoss), (projectile, parent) -> projectile.waterHitImpulseLoss = parent.waterHitImpulseLoss).add()).appendInherited(new KeyedCodec("DampingRotation", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.dampingRotation = s.doubleValue(), projectile -> Double.valueOf(projectile.dampingRotation), (projectile, parent) -> projectile.dampingRotation = parent.dampingRotation).add()).appendInherited(new KeyedCodec("RotationSpeedVelocityRatio", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.rotationSpeedVelocityRatio = s.doubleValue(), projectile -> Double.valueOf(projectile.rotationSpeedVelocityRatio), (projectile, parent) -> projectile.rotationSpeedVelocityRatio = parent.rotationSpeedVelocityRatio).add()).appendInherited(new KeyedCodec("SwimmingDampingFactor", (Codec)Codec.DOUBLE), (projectile, s) -> projectile.swimmingDampingFactor = s.doubleValue(), projectile -> Double.valueOf(projectile.swimmingDampingFactor), (projectile, parent) -> projectile.swimmingDampingFactor = parent.swimmingDampingFactor).add()).appendInherited(new KeyedCodec("RotationMode", (Codec)new EnumCodec(SimplePhysicsProvider.ROTATION_MODE.class)), (projectile, type) -> projectile.rotationMode = type, projectile -> projectile.rotationMode, (projectile, parent) -> projectile.rotationMode = parent.rotationMode).add()).afterDecode(Projectile::processConfig)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, Projectile, DefaultAssetMap<String, Projectile>> getAssetStore() {
/* 273 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Projectile.class); 
/* 274 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, Projectile> getAssetMap() {
/* 278 */     return (DefaultAssetMap<String, Projectile>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/* 281 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Projectile::getAssetStore));
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected String appearance;
/*     */   
/*     */   protected double radius;
/*     */   
/*     */   protected double height;
/*     */   
/*     */   protected double verticalCenterShot;
/*     */   protected double horizontalCenterShot;
/*     */   protected double depthShot;
/*     */   protected boolean pitchAdjustShot;
/*     */   protected double muzzleVelocity;
/*     */   protected double terminalVelocity;
/*     */   protected double gravity;
/*     */   protected double bounciness;
/*     */   protected double impactSlowdown;
/*     */   protected boolean sticksVertically;
/*     */   protected boolean computeYaw = true;
/*     */   protected boolean computePitch = true;
/*     */   protected boolean computeRoll = true;
/* 306 */   protected SimplePhysicsProvider.ROTATION_MODE rotationMode = SimplePhysicsProvider.ROTATION_MODE.Velocity;
/*     */   
/*     */   protected double timeToLive;
/*     */   
/*     */   protected String bounceSoundEventId;
/*     */   
/*     */   protected transient int bounceSoundEventIndex;
/*     */   
/*     */   @Nullable
/*     */   protected WorldParticle bounceParticles;
/*     */   
/*     */   protected String hitSoundEventId;
/*     */   
/*     */   protected transient int hitSoundEventIndex;
/*     */   
/*     */   @Nullable
/*     */   protected WorldParticle hitParticles;
/*     */   
/*     */   protected int damage;
/*     */   
/*     */   protected double deadTime;
/*     */   
/*     */   protected String missSoundEventId;
/*     */   
/*     */   protected transient int missSoundEventIndex;
/*     */   protected WorldParticle missParticles;
/* 332 */   protected double deadTimeMiss = 10.0D;
/*     */ 
/*     */   
/*     */   protected String deathSoundEventId;
/*     */ 
/*     */   
/*     */   protected transient int deathSoundEventIndex;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected WorldParticle deathParticles;
/*     */ 
/*     */   
/*     */   protected boolean deathEffectsOnHit;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ExplosionConfig explosionConfig;
/*     */   
/* 351 */   double density = 2000.0D;
/* 352 */   double waterTerminalVelocityMultiplier = 1.0D;
/* 353 */   double waterHitImpulseLoss = 0.0D;
/* 354 */   double dampingRotation = 0.0D;
/* 355 */   double rotationSpeedVelocityRatio = 2.0D;
/* 356 */   double swimmingDampingFactor = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 363 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getAppearance() {
/* 367 */     return this.appearance;
/*     */   }
/*     */   
/*     */   public double getRadius() {
/* 371 */     return this.radius;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/* 375 */     return this.height;
/*     */   }
/*     */   
/*     */   public double getVerticalCenterShot() {
/* 379 */     return this.verticalCenterShot;
/*     */   }
/*     */   
/*     */   public double getHorizontalCenterShot() {
/* 383 */     return this.horizontalCenterShot;
/*     */   }
/*     */   
/*     */   public double getDepthShot() {
/* 387 */     return this.depthShot;
/*     */   }
/*     */   
/*     */   public boolean isPitchAdjustShot() {
/* 391 */     return this.pitchAdjustShot;
/*     */   }
/*     */   
/*     */   public boolean isSticksVertically() {
/* 395 */     return this.sticksVertically;
/*     */   }
/*     */   
/*     */   public double getMuzzleVelocity() {
/* 399 */     return this.muzzleVelocity;
/*     */   }
/*     */   
/*     */   public double getTerminalVelocity() {
/* 403 */     return this.terminalVelocity;
/*     */   }
/*     */   
/*     */   public double getGravity() {
/* 407 */     return this.gravity;
/*     */   }
/*     */   
/*     */   public double getBounciness() {
/* 411 */     return this.bounciness;
/*     */   }
/*     */   
/*     */   public double getImpactSlowdown() {
/* 415 */     return this.impactSlowdown;
/*     */   }
/*     */   
/*     */   public double getTimeToLive() {
/* 419 */     return this.timeToLive;
/*     */   }
/*     */   
/*     */   public int getDamage() {
/* 423 */     return this.damage;
/*     */   }
/*     */   
/*     */   public double getDeadTime() {
/* 427 */     return this.deadTime;
/*     */   }
/*     */   
/*     */   public double getDeadTimeMiss() {
/* 431 */     return this.deadTimeMiss;
/*     */   }
/*     */   
/*     */   public String getBounceSoundEventId() {
/* 435 */     return this.bounceSoundEventId;
/*     */   }
/*     */   
/*     */   public int getBounceSoundEventIndex() {
/* 439 */     return this.bounceSoundEventIndex;
/*     */   }
/*     */   
/*     */   public String getHitSoundEventId() {
/* 443 */     return this.hitSoundEventId;
/*     */   }
/*     */   
/*     */   public int getHitSoundEventIndex() {
/* 447 */     return this.hitSoundEventIndex;
/*     */   }
/*     */   
/*     */   public String getMissSoundEventId() {
/* 451 */     return this.missSoundEventId;
/*     */   }
/*     */   
/*     */   public int getMissSoundEventIndex() {
/* 455 */     return this.missSoundEventIndex;
/*     */   }
/*     */   
/*     */   public String getDeathSoundEventId() {
/* 459 */     return this.deathSoundEventId;
/*     */   }
/*     */   
/*     */   public int getDeathSoundEventIndex() {
/* 463 */     return this.deathSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldParticle getBounceParticles() {
/* 468 */     return this.bounceParticles;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldParticle getMissParticles() {
/* 473 */     return this.missParticles;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldParticle getDeathParticles() {
/* 478 */     return this.deathParticles;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldParticle getHitParticles() {
/* 483 */     return this.hitParticles;
/*     */   }
/*     */   
/*     */   public boolean isDeathEffectsOnHit() {
/* 487 */     return this.deathEffectsOnHit;
/*     */   }
/*     */   
/*     */   public boolean isComputeYaw() {
/* 491 */     return this.computeYaw;
/*     */   }
/*     */   
/*     */   public boolean isComputePitch() {
/* 495 */     return this.computePitch;
/*     */   }
/*     */   
/*     */   public boolean isComputeRoll() {
/* 499 */     return this.computeRoll;
/*     */   }
/*     */   
/*     */   public SimplePhysicsProvider.ROTATION_MODE getRotationMode() {
/* 503 */     return this.rotationMode;
/*     */   }
/*     */   
/*     */   public double getDensity() {
/* 507 */     return this.density;
/*     */   }
/*     */   
/*     */   public double getWaterTerminalVelocityMultiplier() {
/* 511 */     return this.waterTerminalVelocityMultiplier;
/*     */   }
/*     */   
/*     */   public double getWaterHitImpulseLoss() {
/* 515 */     return this.waterHitImpulseLoss;
/*     */   }
/*     */   
/*     */   public double getDampingRotation() {
/* 519 */     return this.dampingRotation;
/*     */   }
/*     */   
/*     */   public double getRotationSpeedVelocityRatio() {
/* 523 */     return this.rotationSpeedVelocityRatio;
/*     */   }
/*     */   
/*     */   public double getSwimmingDampingFactor() {
/* 527 */     return this.swimmingDampingFactor;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 531 */     if (this.bounceSoundEventId != null) {
/* 532 */       this.bounceSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.bounceSoundEventId);
/*     */     }
/*     */     
/* 535 */     if (this.hitSoundEventId != null) {
/* 536 */       this.hitSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.hitSoundEventId);
/*     */     }
/*     */     
/* 539 */     if (this.missSoundEventId != null) {
/* 540 */       this.missSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.missSoundEventId);
/*     */     }
/*     */     
/* 543 */     if (this.deathSoundEventId != null) {
/* 544 */       this.deathSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.deathSoundEventId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 550 */     if (this == o) return true; 
/* 551 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 553 */     Projectile that = (Projectile)o;
/*     */     
/* 555 */     if (Double.compare(that.radius, this.radius) != 0) return false; 
/* 556 */     if (Double.compare(that.height, this.height) != 0) return false; 
/* 557 */     if (Double.compare(that.verticalCenterShot, this.verticalCenterShot) != 0) return false; 
/* 558 */     if (Double.compare(that.horizontalCenterShot, this.horizontalCenterShot) != 0) return false; 
/* 559 */     if (Double.compare(that.depthShot, this.depthShot) != 0) return false; 
/* 560 */     if (this.pitchAdjustShot != that.pitchAdjustShot) return false; 
/* 561 */     if (Double.compare(that.muzzleVelocity, this.muzzleVelocity) != 0) return false; 
/* 562 */     if (Double.compare(that.terminalVelocity, this.terminalVelocity) != 0) return false; 
/* 563 */     if (Double.compare(that.gravity, this.gravity) != 0) return false; 
/* 564 */     if (Double.compare(that.bounciness, this.bounciness) != 0) return false; 
/* 565 */     if (Double.compare(that.impactSlowdown, this.impactSlowdown) != 0) return false; 
/* 566 */     if (this.sticksVertically != that.sticksVertically) return false; 
/* 567 */     if (this.computeYaw != that.computeYaw) return false; 
/* 568 */     if (this.computePitch != that.computePitch) return false; 
/* 569 */     if (this.computeRoll != that.computeRoll) return false; 
/* 570 */     if (Double.compare(that.timeToLive, this.timeToLive) != 0) return false; 
/* 571 */     if (this.bounceSoundEventIndex != that.bounceSoundEventIndex) return false; 
/* 572 */     if (this.hitSoundEventIndex != that.hitSoundEventIndex) return false; 
/* 573 */     if (this.damage != that.damage) return false; 
/* 574 */     if (Double.compare(that.deadTime, this.deadTime) != 0) return false; 
/* 575 */     if (this.missSoundEventIndex != that.missSoundEventIndex) return false; 
/* 576 */     if (Double.compare(that.deadTimeMiss, this.deadTimeMiss) != 0) return false; 
/* 577 */     if (this.deathSoundEventIndex != that.deathSoundEventIndex) return false; 
/* 578 */     if (this.deathEffectsOnHit != that.deathEffectsOnHit) return false; 
/* 579 */     if (this.explosionConfig != that.explosionConfig) return false; 
/* 580 */     if (Double.compare(that.density, this.density) != 0) return false; 
/* 581 */     if (Double.compare(that.waterTerminalVelocityMultiplier, this.waterTerminalVelocityMultiplier) != 0) return false; 
/* 582 */     if (Double.compare(that.waterHitImpulseLoss, this.waterHitImpulseLoss) != 0) return false; 
/* 583 */     if (Double.compare(that.dampingRotation, this.dampingRotation) != 0) return false; 
/* 584 */     if (Double.compare(that.rotationSpeedVelocityRatio, this.rotationSpeedVelocityRatio) != 0) return false; 
/* 585 */     if (Double.compare(that.swimmingDampingFactor, this.swimmingDampingFactor) != 0) return false; 
/* 586 */     if (!this.id.equals(that.id)) return false; 
/* 587 */     if ((this.appearance != null) ? !this.appearance.equals(that.appearance) : (that.appearance != null)) return false; 
/* 588 */     if (this.rotationMode != that.rotationMode) return false; 
/* 589 */     if ((this.bounceSoundEventId != null) ? !this.bounceSoundEventId.equals(that.bounceSoundEventId) : (that.bounceSoundEventId != null)) return false; 
/* 590 */     if ((this.bounceParticles != null) ? !this.bounceParticles.equals(that.bounceParticles) : (that.bounceParticles != null)) return false; 
/* 591 */     if ((this.hitSoundEventId != null) ? !this.hitSoundEventId.equals(that.hitSoundEventId) : (that.hitSoundEventId != null)) return false; 
/* 592 */     if ((this.hitParticles != null) ? !this.hitParticles.equals(that.hitParticles) : (that.hitParticles != null)) return false; 
/* 593 */     if ((this.missSoundEventId != null) ? !this.missSoundEventId.equals(that.missSoundEventId) : (that.missSoundEventId != null)) return false; 
/* 594 */     if ((this.missParticles != null) ? !this.missParticles.equals(that.missParticles) : (that.missParticles != null)) return false; 
/* 595 */     if ((this.deathSoundEventId != null) ? !this.deathSoundEventId.equals(that.deathSoundEventId) : (that.deathSoundEventId != null)) return false; 
/* 596 */     return (this.deathParticles != null) ? (!this.deathParticles.equals(that.deathParticles)) : ((that.deathParticles != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 603 */     int result = this.id.hashCode();
/* 604 */     result = 31 * result + ((this.appearance != null) ? this.appearance.hashCode() : 0);
/* 605 */     long temp = Double.doubleToLongBits(this.radius);
/* 606 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 607 */     temp = Double.doubleToLongBits(this.height);
/* 608 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 609 */     temp = Double.doubleToLongBits(this.verticalCenterShot);
/* 610 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 611 */     temp = Double.doubleToLongBits(this.horizontalCenterShot);
/* 612 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 613 */     temp = Double.doubleToLongBits(this.depthShot);
/* 614 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 615 */     result = 31 * result + (this.pitchAdjustShot ? 1 : 0);
/* 616 */     temp = Double.doubleToLongBits(this.muzzleVelocity);
/* 617 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 618 */     temp = Double.doubleToLongBits(this.terminalVelocity);
/* 619 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 620 */     temp = Double.doubleToLongBits(this.gravity);
/* 621 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 622 */     temp = Double.doubleToLongBits(this.bounciness);
/* 623 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 624 */     temp = Double.doubleToLongBits(this.impactSlowdown);
/* 625 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 626 */     result = 31 * result + (this.sticksVertically ? 1 : 0);
/* 627 */     result = 31 * result + (this.computeYaw ? 1 : 0);
/* 628 */     result = 31 * result + (this.computePitch ? 1 : 0);
/* 629 */     result = 31 * result + (this.computeRoll ? 1 : 0);
/* 630 */     result = 31 * result + ((this.rotationMode != null) ? this.rotationMode.hashCode() : 0);
/* 631 */     temp = Double.doubleToLongBits(this.timeToLive);
/* 632 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 633 */     result = 31 * result + ((this.bounceSoundEventId != null) ? this.bounceSoundEventId.hashCode() : 0);
/* 634 */     result = 31 * result + this.bounceSoundEventIndex;
/* 635 */     result = 31 * result + ((this.bounceParticles != null) ? this.bounceParticles.hashCode() : 0);
/* 636 */     result = 31 * result + ((this.hitSoundEventId != null) ? this.hitSoundEventId.hashCode() : 0);
/* 637 */     result = 31 * result + this.hitSoundEventIndex;
/* 638 */     result = 31 * result + ((this.hitParticles != null) ? this.hitParticles.hashCode() : 0);
/* 639 */     result = 31 * result + this.damage;
/* 640 */     temp = Double.doubleToLongBits(this.deadTime);
/* 641 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 642 */     result = 31 * result + ((this.missSoundEventId != null) ? this.missSoundEventId.hashCode() : 0);
/* 643 */     result = 31 * result + this.missSoundEventIndex;
/* 644 */     result = 31 * result + ((this.missParticles != null) ? this.missParticles.hashCode() : 0);
/* 645 */     temp = Double.doubleToLongBits(this.deadTimeMiss);
/* 646 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 647 */     result = 31 * result + ((this.deathSoundEventId != null) ? this.deathSoundEventId.hashCode() : 0);
/* 648 */     result = 31 * result + this.deathSoundEventIndex;
/* 649 */     result = 31 * result + ((this.deathParticles != null) ? this.deathParticles.hashCode() : 0);
/* 650 */     result = 31 * result + (this.deathEffectsOnHit ? 1 : 0);
/* 651 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 652 */     temp = Double.doubleToLongBits(this.density);
/* 653 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 654 */     temp = Double.doubleToLongBits(this.waterTerminalVelocityMultiplier);
/* 655 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 656 */     temp = Double.doubleToLongBits(this.waterHitImpulseLoss);
/* 657 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 658 */     temp = Double.doubleToLongBits(this.dampingRotation);
/* 659 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 660 */     temp = Double.doubleToLongBits(this.rotationSpeedVelocityRatio);
/* 661 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 662 */     temp = Double.doubleToLongBits(this.swimmingDampingFactor);
/* 663 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 664 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 670 */     return "Projectile{id='" + this.id + "', appearance='" + this.appearance + "', radius=" + this.radius + ", height=" + this.height + ", verticalCenterShot=" + this.verticalCenterShot + ", horizontalCenterShot=" + this.horizontalCenterShot + ", depthShot=" + this.depthShot + ", pitchAdjustShot=" + this.pitchAdjustShot + ", muzzleVelocity=" + this.muzzleVelocity + ", terminalVelocity=" + this.terminalVelocity + ", gravity=" + this.gravity + ", bounciness=" + this.bounciness + ", impactSlowdown=" + this.impactSlowdown + ", sticksVertically=" + this.sticksVertically + ", computeYaw=" + this.computeYaw + ", computePitch=" + this.computePitch + ", computeRoll=" + this.computeRoll + ", rotationMode=" + String.valueOf(this.rotationMode) + ", timeToLive=" + this.timeToLive + ", bounceSoundEventId='" + this.bounceSoundEventId + "', bounceParticles='" + String.valueOf(this.bounceParticles) + "', hitSoundEventId='" + this.hitSoundEventId + "', hitParticles='" + String.valueOf(this.hitParticles) + "', damage=" + this.damage + ", deadTime=" + this.deadTime + ", missSoundEventId='" + this.missSoundEventId + "', missParticles='" + String.valueOf(this.missParticles) + "', deadTimeMiss=" + this.deadTimeMiss + ", deathSoundEventId='" + this.deathSoundEventId + "', deathParticles='" + String.valueOf(this.deathParticles) + "', deathEffectsOnHit=" + this.deathEffectsOnHit + ", density=" + this.density + ", waterTerminalVelocityMultiplier=" + this.waterTerminalVelocityMultiplier + ", waterHitImpulseLoss=" + this.waterHitImpulseLoss + ", dampingRotation=" + this.dampingRotation + ", rotationSpeedVelocityRatio=" + this.rotationSpeedVelocityRatio + ", swimmingDampingFactor=" + this.swimmingDampingFactor + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ExplosionConfig getExplosionConfig() {
/* 713 */     return this.explosionConfig;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\projectile\config\Projectile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */