/*     */ package com.hypixel.hytale.server.core.entity.entities;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.ExplosionConfig;
/*     */ import com.hypixel.hytale.server.core.entity.ExplosionUtils;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public class ProjectileComponent
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ProjectileComponent> CODEC;
/*     */   private static final double DEFAULT_DESPAWN_SECONDS = 60.0D;
/*     */   
/*     */   static {
/*  90 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ProjectileComponent.class, ProjectileComponent::new).append(new KeyedCodec("ProjectileType", (Codec)Codec.STRING), (projectileEntity, projectileName) -> projectileEntity.projectileAssetName = projectileName, projectileEntity -> projectileEntity.projectileAssetName).add()).append(new KeyedCodec("BrokenDamageModifier", (Codec)Codec.FLOAT), (projectileEntity, brokenDamageModifier) -> projectileEntity.brokenDamageModifier = brokenDamageModifier.floatValue(), projectileEntity -> Float.valueOf(projectileEntity.brokenDamageModifier)).add()).append(new KeyedCodec("DeadTimer", (Codec)Codec.DOUBLE), (projectileEntity, deadTimer) -> projectileEntity.deadTimer = deadTimer.doubleValue(), projectileEntity -> Double.valueOf(projectileEntity.deadTimer)).add()).append(new KeyedCodec("CreatorUUID", (Codec)Codec.UUID_STRING), (projectileEntity, creatorUuid) -> projectileEntity.creatorUuid = creatorUuid, projectileEntity -> projectileEntity.creatorUuid).add()).append(new KeyedCodec("HaveHit", (Codec)Codec.BOOLEAN), (projectileEntity, haveHit) -> projectileEntity.haveHit = haveHit.booleanValue(), projectileEntity -> Boolean.valueOf(projectileEntity.haveHit)).add()).append(new KeyedCodec("LastBouncePosition", (Codec)Vector3d.CODEC), (projectileEntity, lastBouncePosition) -> projectileEntity.lastBouncePosition = lastBouncePosition, projectileEntity -> projectileEntity.lastBouncePosition).add()).append(new KeyedCodec("SppImpacted", (Codec)Codec.BOOLEAN), (projectileEntity, b) -> projectileEntity.simplePhysicsProvider.setImpacted(b.booleanValue()), projectileEntity -> Boolean.valueOf(projectileEntity.simplePhysicsProvider.isImpacted())).add()).append(new KeyedCodec("SppResting", (Codec)Codec.BOOLEAN), (projectileEntity, b) -> projectileEntity.simplePhysicsProvider.setResting(b.booleanValue()), projectileEntity -> Boolean.valueOf(projectileEntity.simplePhysicsProvider.isResting())).add()).append(new KeyedCodec("SppVelocity", (Codec)Vector3d.CODEC), (projectileEntity, v) -> projectileEntity.simplePhysicsProvider.setVelocity(v), projectileEntity -> projectileEntity.simplePhysicsProvider.getVelocity()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, ProjectileComponent> getComponentType() {
/*  97 */     return EntityModule.get().getProjectileComponentType();
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
/* 108 */   private transient SimplePhysicsProvider simplePhysicsProvider = new SimplePhysicsProvider(this::bounceHandler, this::impactHandler);
/*     */   
/* 110 */   private transient String appearance = "Boy";
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private transient Projectile projectile;
/*     */ 
/*     */ 
/*     */   
/*     */   private String projectileAssetName;
/*     */ 
/*     */ 
/*     */   
/* 123 */   private float brokenDamageModifier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private double deadTimer = -1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID creatorUuid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean haveHit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3d lastBouncePosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProjectileComponent(@Nonnull String projectileAssetName) {
/* 159 */     this.projectileAssetName = projectileAssetName;
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
/*     */   @Nonnull
/*     */   public static Holder<EntityStore> assembleDefaultProjectile(@Nonnull TimeResource time, @Nonnull String projectileAssetName, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 179 */     if (projectileAssetName.isEmpty()) throw new IllegalArgumentException("No projectile config typeName provided");
/*     */     
/* 181 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */     
/* 183 */     ProjectileComponent projectileComponent = new ProjectileComponent(projectileAssetName);
/* 184 */     holder.putComponent(getComponentType(), projectileComponent);
/*     */     
/* 186 */     holder.putComponent(DespawnComponent.getComponentType(), (Component)DespawnComponent.despawnInMilliseconds(time, 60000L));
/* 187 */     holder.putComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position.clone(), rotation));
/* 188 */     holder.ensureComponent(Velocity.getComponentType());
/* 189 */     holder.ensureComponent(UUIDComponent.getComponentType());
/*     */     
/* 191 */     return holder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean initialize() {
/* 200 */     this.projectile = (Projectile)Projectile.getAssetMap().getAsset(this.projectileAssetName);
/* 201 */     if (this.projectile == null) {
/* 202 */       return false;
/*     */     }
/*     */     
/* 205 */     String appearance = this.projectile.getAppearance();
/* 206 */     if (appearance != null && !appearance.isEmpty()) {
/* 207 */       this.appearance = appearance;
/*     */     }
/*     */ 
/*     */     
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializePhysics(@Nonnull BoundingBox boundingBox) {
/* 220 */     this.simplePhysicsProvider.setProvideCharacterCollisions(true);
/* 221 */     this.simplePhysicsProvider.initialize(this.projectile, boundingBox);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProjectileBounce(@Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 231 */     WorldParticle bounceParticles = this.projectile.getBounceParticles();
/*     */     
/* 233 */     if (bounceParticles != null) {
/* 234 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 235 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 236 */       playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*     */       
/* 238 */       ParticleUtil.spawnParticleEffect(bounceParticles, position, (List)results, componentAccessor);
/*     */     } 
/* 240 */     SoundUtil.playSoundEvent3d(this.projectile.getBounceSoundEventIndex(), SoundCategory.SFX, position, componentAccessor);
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
/*     */   private void onProjectileHitEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 255 */     WorldParticle hitParticles = this.projectile.getHitParticles();
/* 256 */     if (hitParticles != null) {
/* 257 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 258 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 259 */       playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*     */       
/* 261 */       ParticleUtil.spawnParticleEffect(hitParticles, position, (List)results, componentAccessor);
/*     */     } 
/*     */     
/* 264 */     SoundUtil.playSoundEvent3d(this.projectile.getHitSoundEventIndex(), SoundCategory.SFX, position, componentAccessor);
/*     */     
/* 266 */     Entity targetEntity = EntityUtils.getEntity(targetRef, componentAccessor);
/* 267 */     if (targetEntity instanceof com.hypixel.hytale.server.core.entity.LivingEntity) {
/* 268 */       Ref<EntityStore> shooterRef = ((EntityStore)componentAccessor.getExternalData()).getRefFromUUID(this.creatorUuid);
/* 269 */       DamageSystems.executeDamage(targetRef, componentAccessor, new Damage((Damage.Source)new Damage.ProjectileSource(
/*     */ 
/*     */               
/* 272 */               (shooterRef != null) ? shooterRef : ref, ref), DamageCause.PROJECTILE, this.projectile
/*     */             
/* 274 */             .getDamage() * this.brokenDamageModifier));
/*     */       
/* 276 */       this.haveHit = true;
/*     */     } 
/* 278 */     this.deadTimer = this.projectile.getDeadTime();
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
/*     */   public boolean consumeDeadTimer(float dt) {
/* 290 */     if (this.deadTimer < 0.0D) return false; 
/* 291 */     this.deadTimer -= dt;
/* 292 */     return (this.deadTimer <= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bounceHandler(@Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 302 */     if (this.lastBouncePosition == null) {
/* 303 */       this.lastBouncePosition = new Vector3d(position);
/* 304 */     } else if (this.lastBouncePosition.distanceSquaredTo(position) >= 0.5D) {
/* 305 */       this.lastBouncePosition.assign(position);
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/* 310 */     onProjectileBounce(position, componentAccessor);
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
/*     */   protected void impactHandler(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nullable Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 325 */     if (targetRef != null) {
/* 326 */       onProjectileHitEvent(ref, position, targetRef, componentAccessor);
/*     */     } else {
/* 328 */       onProjectileMissEvent(position, componentAccessor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onProjectileMissEvent(@Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 339 */     WorldParticle missParticles = this.projectile.getMissParticles();
/* 340 */     if (missParticles != null) {
/* 341 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 342 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 343 */       playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*     */       
/* 345 */       ParticleUtil.spawnParticleEffect(missParticles, position, (List)results, componentAccessor);
/*     */     } 
/*     */     
/* 348 */     SoundUtil.playSoundEvent3d(this.projectile.getMissSoundEventIndex(), SoundCategory.SFX, position, componentAccessor);
/*     */     
/* 350 */     this.deadTimer = this.projectile.getDeadTimeMiss();
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
/*     */   public void onProjectileDeath(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 363 */     EntityStore entityStore = (EntityStore)commandBuffer.getExternalData();
/* 364 */     World world = entityStore.getWorld();
/*     */     
/* 366 */     ExplosionConfig explosionConfig = this.projectile.getExplosionConfig();
/*     */     
/* 368 */     if (explosionConfig != null) {
/* 369 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/* 370 */       Ref<EntityStore> creatorRef = entityStore.getRefFromUUID(this.creatorUuid);
/*     */       
/* 372 */       Damage.ProjectileSource damageSource = new Damage.ProjectileSource((creatorRef != null) ? creatorRef : ref, ref);
/* 373 */       ExplosionUtils.performExplosion((Damage.Source)damageSource, position, explosionConfig, ref, commandBuffer, (ComponentAccessor)chunkStore);
/*     */     } 
/*     */     
/* 376 */     if (this.haveHit && !this.projectile.isDeathEffectsOnHit())
/*     */       return; 
/* 378 */     WorldParticle deathParticles = this.projectile.getDeathParticles();
/* 379 */     if (deathParticles != null) {
/* 380 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 381 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 382 */       playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*     */       
/* 384 */       ParticleUtil.spawnParticleEffect(deathParticles, position, (List)results, (ComponentAccessor)commandBuffer);
/*     */     } 
/*     */     
/* 387 */     SoundUtil.playSoundEvent3d(this.projectile.getDeathSoundEventIndex(), SoundCategory.SFX, position, (ComponentAccessor)commandBuffer);
/*     */   }
/*     */   
/*     */   public void shoot(@Nonnull Holder<EntityStore> holder, @Nonnull UUID creatorUuid, double x, double y, double z, float yaw, float pitch) {
/* 391 */     this.creatorUuid = creatorUuid;
/* 392 */     this.simplePhysicsProvider.setCreatorId(creatorUuid);
/*     */     
/* 394 */     Vector3d direction = new Vector3d();
/* 395 */     computeStartOffset(this.projectile.isPitchAdjustShot(), this.projectile.getVerticalCenterShot(), this.projectile.getHorizontalCenterShot(), this.projectile.getDepthShot(), yaw, pitch, direction);
/*     */ 
/*     */     
/* 398 */     x += direction.x;
/* 399 */     y += direction.y;
/* 400 */     z += direction.z;
/*     */     
/* 402 */     ((TransformComponent)holder.ensureAndGetComponent(TransformComponent.getComponentType())).setPosition(new Vector3d(x, y, z));
/*     */     
/* 404 */     PhysicsMath.vectorFromAngles(yaw, pitch, direction);
/* 405 */     direction.setLength(this.projectile.getMuzzleVelocity());
/*     */     
/* 407 */     this.simplePhysicsProvider.setVelocity(direction);
/*     */   }
/*     */   
/*     */   public static void computeStartOffset(boolean pitchAdjust, double verticalCenterShot, double horizontalCenterShot, double depthShot, float yaw, float pitch, @Nonnull Vector3d offset) {
/* 411 */     offset.assign(0.0D, 0.0D, 0.0D);
/* 412 */     if (depthShot != 0.0D) {
/* 413 */       PhysicsMath.vectorFromAngles(yaw, pitchAdjust ? pitch : 0.0F, offset);
/* 414 */       offset.setLength(depthShot);
/*     */     } else {
/* 416 */       offset.assign(0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 419 */     offset.add(horizontalCenterShot * -PhysicsMath.headingZ(yaw), -verticalCenterShot, horizontalCenterShot * PhysicsMath.headingX(yaw));
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 423 */     return this.simplePhysicsProvider.isOnGround();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Projectile getProjectile() {
/* 428 */     return this.projectile;
/*     */   }
/*     */   
/*     */   public String getAppearance() {
/* 432 */     return this.appearance;
/*     */   }
/*     */   
/*     */   public String getProjectileAssetName() {
/* 436 */     return this.projectileAssetName;
/*     */   }
/*     */   
/*     */   public SimplePhysicsProvider getSimplePhysicsProvider() {
/* 440 */     return this.simplePhysicsProvider;
/*     */   }
/*     */   
/*     */   public void applyBrokenPenalty(float penalty) {
/* 444 */     this.brokenDamageModifier = 1.0F - penalty;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProjectileComponent(@Nonnull ProjectileComponent other) {
/* 449 */     this.simplePhysicsProvider = other.simplePhysicsProvider;
/*     */     
/* 451 */     this.projectileAssetName = other.projectileAssetName;
/* 452 */     this.projectile = other.projectile;
/* 453 */     this.appearance = other.appearance;
/* 454 */     this.deadTimer = other.deadTimer;
/* 455 */     this.creatorUuid = other.creatorUuid;
/* 456 */     this.haveHit = other.haveHit;
/* 457 */     this.brokenDamageModifier = other.brokenDamageModifier;
/* 458 */     this.lastBouncePosition = other.lastBouncePosition;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 464 */     return new ProjectileComponent(this);
/*     */   }
/*     */   
/*     */   private ProjectileComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\ProjectileComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */