/*     */ package com.hypixel.hytale.builtin.deployables.config;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.DeployablesUtils;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableProjectileComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableProjectileShooterComponent;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.protocol.Opacity;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.Knockback;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.time.Instant;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployableTurretConfig
/*     */   extends DeployableConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<DeployableTurretConfig> CODEC;
/*     */   protected float trackableRadius;
/*     */   protected float detectionRadius;
/*     */   protected float rotationSpeed;
/*     */   protected float projectileDamage;
/*     */   protected boolean preferOwnerTarget;
/*     */   protected int ammo;
/*     */   protected ProjectileConfig projectileConfig;
/*     */   protected float deployDelay;
/*     */   protected float shotInterval;
/*     */   protected int burstCount;
/*     */   protected float burstCooldown;
/*     */   protected boolean canShootOwner;
/*     */   protected Knockback projectileKnockback;
/*     */   
/*     */   static {
/* 196 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DeployableTurretConfig.class, DeployableTurretConfig::new, DeployableConfig.BASE_CODEC).appendInherited(new KeyedCodec("TrackableRadius", (Codec)Codec.FLOAT), (o, i) -> o.trackableRadius = i.floatValue(), o -> Float.valueOf(o.trackableRadius), (o, p) -> o.trackableRadius = p.trackableRadius).documentation("The radius in which a targeted entity can be tracked").add()).appendInherited(new KeyedCodec("DetectionRadius", (Codec)Codec.FLOAT), (o, i) -> o.detectionRadius = i.floatValue(), o -> Float.valueOf(o.detectionRadius), (o, p) -> o.detectionRadius = p.detectionRadius).documentation("The radius in which an entity can be targeted").add()).appendInherited(new KeyedCodec("RotationSpeed", (Codec)Codec.FLOAT), (o, i) -> o.rotationSpeed = i.floatValue(), o -> Float.valueOf(o.rotationSpeed), (o, p) -> o.rotationSpeed = p.rotationSpeed).documentation("The speed at which the turret can rotate to hit it's target").add()).appendInherited(new KeyedCodec("PreferOwnerTarget", (Codec)Codec.BOOLEAN), (o, i) -> o.preferOwnerTarget = i.booleanValue(), o -> Boolean.valueOf(o.preferOwnerTarget), (o, p) -> o.preferOwnerTarget = p.preferOwnerTarget).documentation("If true, will prefer targeting entities that the owner is attacking").add()).appendInherited(new KeyedCodec("Ammo", (Codec)Codec.INTEGER), (o, i) -> o.ammo = i.intValue(), o -> Integer.valueOf(o.ammo), (o, p) -> o.ammo = p.ammo).documentation("The total ammo the turret has, each projectile will consume one").add()).appendInherited(new KeyedCodec("DeployDelay", (Codec)Codec.FLOAT), (o, i) -> o.deployDelay = i.floatValue(), o -> Float.valueOf(o.deployDelay), (o, p) -> o.deployDelay = p.deployDelay).documentation("The delay in seconds until the deployable is ready to begin targeting logic").add()).appendInherited(new KeyedCodec("ProjectileConfig", (Codec)ProjectileConfig.CODEC), (o, i) -> o.projectileConfig = i, o -> o.projectileConfig, (o, p) -> o.projectileConfig = p.projectileConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("ShotInterval", (Codec)Codec.FLOAT), (o, i) -> o.shotInterval = i.floatValue(), o -> Float.valueOf(o.shotInterval), (o, p) -> o.shotInterval = p.shotInterval).add()).appendInherited(new KeyedCodec("BurstCount", (Codec)Codec.INTEGER), (o, i) -> o.burstCount = i.intValue(), o -> Integer.valueOf(o.burstCount), (o, p) -> o.burstCount = p.burstCount).add()).appendInherited(new KeyedCodec("BurstCooldown", (Codec)Codec.FLOAT), (o, i) -> o.burstCooldown = i.floatValue(), o -> Float.valueOf(o.burstCooldown), (o, p) -> o.burstCooldown = p.burstCooldown).add()).appendInherited(new KeyedCodec("ProjectileDamage", (Codec)Codec.FLOAT), (o, i) -> o.projectileDamage = i.floatValue(), o -> Float.valueOf(o.projectileDamage), (o, p) -> o.projectileDamage = p.projectileDamage).add()).appendInherited(new KeyedCodec("CanShootOwner", (Codec)Codec.BOOLEAN), (o, i) -> o.canShootOwner = i.booleanValue(), o -> Boolean.valueOf(o.canShootOwner), (o, p) -> o.canShootOwner = p.canShootOwner).add()).appendInherited(new KeyedCodec("Knockback", (Codec)Knockback.CODEC), (i, s) -> i.projectileKnockback = s, i -> i.projectileKnockback, (i, parent) -> i.projectileKnockback = parent.projectileKnockback).add()).appendInherited(new KeyedCodec("TargetOffset", (Codec)Vector3d.CODEC), (i, s) -> i.targetOffset = s, i -> i.targetOffset, (i, parent) -> i.targetOffset = parent.targetOffset).add()).appendInherited(new KeyedCodec("DoLineOfSightTest", (Codec)Codec.BOOLEAN), (o, i) -> o.doLineOfSightTest = i.booleanValue(), o -> Boolean.valueOf(o.doLineOfSightTest), (o, p) -> o.doLineOfSightTest = p.doLineOfSightTest).add()).appendInherited(new KeyedCodec("ProjectileHitWorldSoundEventId", (Codec)Codec.STRING), (o, i) -> o.projectileHitWorldSoundEventId = i, o -> o.projectileHitWorldSoundEventId, (o, p) -> o.projectileHitWorldSoundEventId = p.projectileHitWorldSoundEventId).documentation("The positioned sound event played to surrounding players when the projectile hits a player").add()).appendInherited(new KeyedCodec("ProjectileHitLocalSoundEventId", (Codec)Codec.STRING), (o, i) -> o.projectileHitLocalSoundEventId = i, o -> o.projectileHitLocalSoundEventId, (o, p) -> o.projectileHitLocalSoundEventId = p.projectileHitLocalSoundEventId).documentation("The positioned sound event played to a player hit by the projectile").add()).appendInherited(new KeyedCodec("RespectTeams", (Codec)Codec.BOOLEAN), (o, i) -> o.respectTeams = i.booleanValue(), o -> Boolean.valueOf(o.respectTeams), (o, p) -> o.respectTeams = p.respectTeams).add()).appendInherited(new KeyedCodec("ProjectileSpawnOffsets", (Codec)new MapCodec((Codec)Vector3d.CODEC, Object2ObjectOpenHashMap::new, true)), (o, i) -> o.projectileSpawnOffsets = i, o -> o.projectileSpawnOffsets, (o, p) -> o.projectileSpawnOffsets = p.projectileSpawnOffsets).add()).afterDecode(DeployableTurretConfig::processConfig)).build();
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
/* 211 */   protected Vector3d targetOffset = new Vector3d(0.0D, 0.0D, 0.0D);
/*     */   protected boolean doLineOfSightTest = true;
/*     */   protected String projectileHitWorldSoundEventId;
/*     */   protected String projectileHitLocalSoundEventId;
/* 215 */   protected int projectileHitLocalSoundEventIndex = 0;
/* 216 */   protected int projectileHitWorldSoundEventIndex = 0;
/*     */   protected boolean respectTeams = true;
/* 218 */   protected Map<String, Vector3d> projectileSpawnOffsets = (Map<String, Vector3d>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/* 224 */     if (this.projectileHitWorldSoundEventId != null) {
/* 225 */       this.projectileHitWorldSoundEventIndex = this.projectileHitLocalSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.projectileHitWorldSoundEventId);
/*     */     }
/*     */     
/* 228 */     if (this.projectileHitLocalSoundEventId != null) {
/* 229 */       this.projectileHitLocalSoundEventIndex = SoundEvent.getAssetMap().getIndex(this.projectileHitLocalSoundEventId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(@Nonnull DeployableComponent deployableComponent, float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 235 */     Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
/*     */     
/* 237 */     switch (deployableComponent.getFlag(DeployableComponent.DeployableFlag.STATE)) { case 0:
/* 238 */         tickInitState(entityRef, deployableComponent, store, commandBuffer); break;
/* 239 */       case 1: tickStartDeployState(entityRef, deployableComponent, store); break;
/* 240 */       case 2: tickAwaitDeployState(entityRef, deployableComponent, store); break;
/* 241 */       case 3: tickAttackState(entityRef, deployableComponent, dt, store, commandBuffer);
/*     */         break; }
/*     */   
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
/*     */   private void tickInitState(@Nonnull Ref<EntityStore> entityRef, @Nonnull DeployableComponent component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 259 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 1);
/* 260 */     commandBuffer.addComponent(entityRef, DeployableProjectileShooterComponent.getComponentType());
/* 261 */     playAnimation(store, entityRef, this, "Deploy");
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
/*     */   private void tickStartDeployState(@Nonnull Ref<EntityStore> ref, @Nonnull DeployableComponent component, @Nonnull Store<EntityStore> store) {
/* 274 */     component.setFlag(DeployableComponent.DeployableFlag.STATE, 2);
/* 275 */     playAnimation(store, ref, this, "Deploy");
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
/*     */   private void tickAwaitDeployState(@Nonnull Ref<EntityStore> ref, @Nonnull DeployableComponent component, @Nonnull Store<EntityStore> store) {
/* 287 */     Instant now = ((TimeResource)store.getResource(TimeResource.getResourceType())).getNow();
/* 288 */     Instant readyTime = component.getSpawnInstant().plus((long)this.deployDelay, ChronoUnit.SECONDS);
/* 289 */     if (now.isAfter(readyTime)) {
/* 290 */       component.setFlag(DeployableComponent.DeployableFlag.STATE, 3);
/* 291 */       playAnimation(store, ref, this, "Loop");
/*     */     } 
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
/*     */   private void tickAttackState(@Nonnull Ref<EntityStore> ref, @Nonnull DeployableComponent component, float dt, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 310 */     component.setTimeSinceLastAttack(component.getTimeSinceLastAttack() + dt);
/*     */     
/* 312 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 313 */     DeployableProjectileShooterComponent shooterComponent = (DeployableProjectileShooterComponent)store.getComponent(ref, DeployableProjectileShooterComponent.getComponentType());
/*     */     
/* 315 */     Vector3d spawnPos = Vector3d.ZERO.clone();
/* 316 */     if (this.projectileSpawnOffsets != null) {
/* 317 */       spawnPos.add(this.projectileSpawnOffsets.get(component.getSpawnFace()));
/*     */     }
/*     */ 
/*     */     
/* 321 */     if (shooterComponent == null) {
/* 322 */       world.execute(() -> {
/*     */             if (ref.isValid()) {
/*     */               DespawnComponent despawn = (DespawnComponent)store.ensureAndGetComponent(ref, DespawnComponent.getComponentType());
/*     */               
/*     */               WorldTimeResource timeManager = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*     */               
/*     */               despawn.setDespawn(timeManager.getGameTime());
/*     */             } 
/*     */           });
/*     */       return;
/*     */     } 
/* 333 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 334 */     assert transformComponent != null;
/*     */     
/* 336 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 337 */     assert headRotationComponent != null;
/*     */ 
/*     */     
/* 340 */     Vector3d pos = Vector3d.add(spawnPos, transformComponent.getPosition());
/*     */ 
/*     */     
/* 343 */     updateProjectiles(store, commandBuffer, shooterComponent);
/*     */ 
/*     */     
/* 346 */     boolean hasTarget = false;
/* 347 */     Ref<EntityStore> target = shooterComponent.getActiveTarget();
/* 348 */     if (target != null && target.isValid()) {
/* 349 */       TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(target, TransformComponent.getComponentType());
/* 350 */       assert targetTransformComponent != null;
/*     */       
/* 352 */       Vector3d vector3d1 = calculatedTargetPosition(targetTransformComponent.getPosition());
/* 353 */       Vector3d direction = Vector3d.directionTo(pos, vector3d1);
/* 354 */       if (vector3d1.distanceTo(pos) <= this.trackableRadius && testLineOfSight(pos, vector3d1, direction, commandBuffer)) {
/* 355 */         hasTarget = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 360 */     if (!hasTarget) {
/* 361 */       Ref<EntityStore> closestTarget = null;
/* 362 */       Vector3d closestTargetPos = Vector3d.MAX;
/*     */       
/* 364 */       List<Ref<EntityStore>> targetEntityRefs = TargetUtil.getAllEntitiesInSphere(pos, this.detectionRadius, (ComponentAccessor)commandBuffer);
/*     */       
/* 366 */       for (Ref<EntityStore> potentialTargetRef : targetEntityRefs) {
/* 367 */         if (potentialTargetRef == null || !potentialTargetRef.isValid())
/*     */           continue; 
/* 369 */         TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(potentialTargetRef, TransformComponent.getComponentType());
/* 370 */         assert targetTransformComponent != null;
/*     */         
/* 372 */         Vector3d targetPosition = calculatedTargetPosition(targetTransformComponent.getPosition());
/*     */         
/* 374 */         Vector3d direction = Vector3d.directionTo(pos, targetPosition);
/* 375 */         if (!testLineOfSight(pos, targetPosition, direction, commandBuffer))
/*     */           continue; 
/* 377 */         if (isValidTarget(ref, store, potentialTargetRef) && pos
/* 378 */           .distanceTo(targetPosition) < pos.distanceTo(closestTargetPos)) {
/* 379 */           closestTargetPos = targetPosition;
/* 380 */           closestTarget = potentialTargetRef;
/*     */         } 
/*     */       } 
/*     */       
/* 384 */       if (closestTarget != null) {
/* 385 */         shooterComponent.setActiveTarget(closestTarget);
/* 386 */         target = closestTarget;
/* 387 */         hasTarget = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 392 */     Vector3d targetPos = Vector3d.ZERO;
/* 393 */     Vector3f targetLookRotation = Vector3f.ZERO;
/* 394 */     Vector3f lookRotation = Vector3f.ZERO;
/*     */     
/* 396 */     if (hasTarget) {
/* 397 */       TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(target, TransformComponent.getComponentType());
/* 398 */       assert targetTransformComponent != null;
/*     */       
/* 400 */       targetPos = calculatedTargetPosition(targetTransformComponent.getPosition().clone());
/* 401 */       Vector3d relativeTargetOffset = new Vector3d(pos.x - targetPos.x, pos.y - targetPos.y, pos.z - targetPos.z);
/* 402 */       targetLookRotation = Vector3f.lookAt(relativeTargetOffset.negate());
/* 403 */       lookRotation = Vector3f.lerpAngle(headRotationComponent.getRotation(), targetLookRotation, this.rotationSpeed * dt);
/*     */     } 
/*     */     
/* 406 */     headRotationComponent.setRotation(lookRotation);
/*     */ 
/*     */     
/* 409 */     int shotsFired = component.getFlag(DeployableComponent.DeployableFlag.BURST_SHOTS);
/* 410 */     float timeSinceLastAttack = component.getTimeSinceLastAttack();
/* 411 */     boolean canFire = false;
/*     */     
/* 413 */     if (shotsFired < this.burstCount && timeSinceLastAttack >= this.shotInterval) {
/* 414 */       component.setFlag(DeployableComponent.DeployableFlag.BURST_SHOTS, shotsFired + 1);
/* 415 */       canFire = true;
/* 416 */     } else if (shotsFired >= this.burstCount && timeSinceLastAttack >= this.burstCooldown) {
/* 417 */       component.setFlag(DeployableComponent.DeployableFlag.BURST_SHOTS, 1);
/* 418 */       canFire = true;
/*     */     } 
/*     */ 
/*     */     
/* 422 */     if (canFire && hasTarget) {
/* 423 */       Vector3d fwdDirection = (new Vector3d()).assign(lookRotation.getYaw(), lookRotation.getPitch());
/* 424 */       Vector3d rootPos = transformComponent.getPosition();
/*     */       
/* 426 */       Vector3d projectileSpawnPos = Vector3d.ZERO.clone();
/* 427 */       if (this.projectileSpawnOffsets != null) {
/* 428 */         projectileSpawnPos = ((Vector3d)this.projectileSpawnOffsets.get(component.getSpawnFace())).clone();
/*     */       }
/*     */       
/* 431 */       projectileSpawnPos.add(fwdDirection.clone().normalize());
/* 432 */       projectileSpawnPos.add(rootPos);
/*     */       
/* 434 */       UUID uuid = ((UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType())).getUuid();
/*     */       
/* 436 */       shooterComponent.spawnProjectile(ref, commandBuffer, this.projectileConfig, uuid, projectileSpawnPos, fwdDirection.clone());
/* 437 */       playAnimation(store, ref, this, "Shoot");
/* 438 */       component.setTimeSinceLastAttack(0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3d calculatedTargetPosition(@Nonnull Vector3d original) {
/* 449 */     return Vector3d.add(original.clone(), this.targetOffset);
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
/*     */   private boolean isValidTarget(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> targetRef) {
/* 463 */     if (targetRef.equals(ref)) return false;
/*     */     
/* 465 */     DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType());
/* 466 */     if (deployableComponent != null)
/*     */     {
/*     */       
/* 469 */       return (this.canShootOwner || !targetRef.equals(deployableComponent.getOwner()));
/*     */     }
/*     */     
/* 472 */     return true;
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
/*     */   private boolean testLineOfSight(@Nonnull Vector3d attackerPos, @Nonnull Vector3d targetPos, @Nonnull Vector3d direction, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 488 */     if (!this.doLineOfSightTest) return true;
/*     */     
/* 490 */     Vector3f spawnOffset = this.projectileConfig.getSpawnOffset();
/* 491 */     Vector3d testFromPos = attackerPos.clone().add(spawnOffset.x, (spawnOffset.y + this.generatedModel.getEyeHeight()), spawnOffset.z);
/*     */     
/* 493 */     double distance = testFromPos.distanceTo(targetPos);
/* 494 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 495 */     Vector3f whiteColor = new Vector3f(1.0F, 1.0F, 1.0F);
/*     */ 
/*     */     
/* 498 */     if (getDebugVisuals()) {
/* 499 */       Vector3d increment = direction.scale(distance);
/* 500 */       for (int i = 0; i < 10; i++) {
/* 501 */         Vector3d pos = testFromPos.clone();
/* 502 */         pos.addScaled(increment, (i / 10.0F));
/* 503 */         DebugUtils.addSphere(world, pos, whiteColor, 0.10000000149011612D, 0.5F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 508 */     Vector3i blockPosition = TargetUtil.getTargetBlock(world, (id, fluid_id) -> { if (id == 0) return false;  BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(id); BlockMaterial material = blockType.getMaterial(); return (material == BlockMaterial.Empty) ? false : ((blockType.getOpacity() != Opacity.Transparent)); }attackerPos.x, attackerPos.y, attackerPos.z, direction.x, direction.y, direction.z, distance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 522 */     if (blockPosition == null) return true; 
/* 523 */     double entityDistance = attackerPos.distanceSquaredTo(targetPos);
/* 524 */     double blockDistance = attackerPos.distanceSquaredTo(blockPosition.x + 0.5D, blockPosition.y + 0.5D, blockPosition.z + 0.5D);
/* 525 */     return (entityDistance < blockDistance);
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
/*     */   private void updateProjectiles(@Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull DeployableProjectileShooterComponent shooterComponent) {
/* 538 */     List<Ref<EntityStore>> projectiles = shooterComponent.getProjectiles();
/* 539 */     List<Ref<EntityStore>> projectilesForRemoval = shooterComponent.getProjectilesForRemoval();
/*     */     
/* 541 */     projectiles.removeAll(Collections.singleton(null));
/*     */     
/* 543 */     for (Ref<EntityStore> projectile : projectiles) {
/* 544 */       updateProjectile(projectile, shooterComponent, store, commandBuffer);
/*     */     }
/*     */ 
/*     */     
/* 548 */     for (Ref<EntityStore> projectile : projectilesForRemoval) {
/* 549 */       if (projectile.isValid()) {
/* 550 */         commandBuffer.removeEntity(projectile, RemoveReason.REMOVE);
/*     */       }
/*     */       
/* 553 */       projectiles.remove(projectile);
/*     */     } 
/*     */     
/* 556 */     projectilesForRemoval.clear();
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
/*     */   private void updateProjectile(@Nonnull Ref<EntityStore> projectileRef, @Nonnull DeployableProjectileShooterComponent shooterComponent, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 572 */     if (!projectileRef.isValid()) {
/* 573 */       shooterComponent.getProjectilesForRemoval().add(projectileRef);
/*     */       
/*     */       return;
/*     */     } 
/* 577 */     TransformComponent projTransformComponent = (TransformComponent)store.getComponent(projectileRef, TransformComponent.getComponentType());
/* 578 */     assert projTransformComponent != null;
/* 579 */     Vector3d projPos = projTransformComponent.getPosition();
/*     */ 
/*     */     
/* 582 */     AtomicReference<Boolean> hit = new AtomicReference<>(Boolean.FALSE);
/* 583 */     DeployableProjectileComponent dProjComponent = (DeployableProjectileComponent)store.getComponent(projectileRef, DeployableProjectileComponent.getComponentType());
/* 584 */     assert dProjComponent != null;
/*     */     
/* 586 */     Vector3d prevPos = dProjComponent.getPreviousTickPosition();
/*     */     
/* 588 */     Vector3d increment = new Vector3d((projPos.x - prevPos.x) * 0.10000000149011612D, (projPos.y - prevPos.y) * 0.10000000149011612D, (projPos.z - prevPos.z) * 0.10000000149011612D);
/*     */ 
/*     */ 
/*     */     
/* 592 */     for (int j = 0; j < 10; j++) {
/* 593 */       if (!((Boolean)hit.get()).booleanValue()) {
/* 594 */         Vector3d scanPos = dProjComponent.getPreviousTickPosition().clone();
/* 595 */         scanPos.x += increment.x * j;
/* 596 */         scanPos.y += increment.y * j;
/* 597 */         scanPos.z += increment.z * j;
/*     */         
/* 599 */         if (getDebugVisuals()) {
/* 600 */           DebugUtils.addSphere(((EntityStore)store.getExternalData()).getWorld(), scanPos, new Vector3f(1.0F, 1.0F, 1.0F), 0.10000000149011612D, 5.0F);
/*     */         }
/*     */         
/* 603 */         List<Ref<EntityStore>> targetEntityRefs = TargetUtil.getAllEntitiesInSphere(scanPos, 0.1D, (ComponentAccessor)store);
/*     */         
/* 605 */         for (Ref<EntityStore> targetEntityRef : targetEntityRefs) {
/* 606 */           if (((Boolean)hit.get()).booleanValue())
/* 607 */             return;  projectileHit(targetEntityRef, projectileRef, shooterComponent, store, commandBuffer);
/* 608 */           hit.set(Boolean.TRUE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 613 */     dProjComponent.setPreviousTickPosition(projPos);
/*     */ 
/*     */     
/* 616 */     if (!((Boolean)hit.get()).booleanValue()) {
/* 617 */       StandardPhysicsProvider physics = (StandardPhysicsProvider)store.getComponent(projectileRef, StandardPhysicsProvider.getComponentType());
/* 618 */       if (physics != null && 
/* 619 */         physics.getState() != StandardPhysicsProvider.STATE.ACTIVE) {
/* 620 */         shooterComponent.getProjectilesForRemoval().add(projectileRef);
/*     */       }
/*     */     } 
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
/*     */   private void projectileHit(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> projectileRef, @Nonnull DeployableProjectileShooterComponent shooterComponent, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 642 */     Damage damageEntry = new Damage((Damage.Source)new Damage.EntitySource(ref), DamageCause.PHYSICAL, this.projectileDamage);
/* 643 */     DamageSystems.executeDamage(ref, commandBuffer, damageEntry);
/*     */     
/* 645 */     TransformComponent projectileTransformComponent = (TransformComponent)store.getComponent(projectileRef, TransformComponent.getComponentType());
/* 646 */     assert projectileTransformComponent != null;
/*     */     
/* 648 */     Vector3d projectilePosition = projectileTransformComponent.getPosition().clone();
/*     */ 
/*     */     
/* 651 */     if (this.projectileKnockback != null) {
/* 652 */       float projectileRotationYaw = projectileTransformComponent.getRotation().getYaw();
/*     */       
/* 654 */       ((EntityStore)store.getExternalData()).getWorld().execute(() -> {
/*     */             if (ref.isValid()) {
/*     */               applyKnockback(ref, projectilePosition, projectileRotationYaw, store);
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 662 */     DeployablesUtils.playSoundEventsAtEntity(ref, (ComponentAccessor)commandBuffer, this.projectileHitLocalSoundEventIndex, this.projectileHitWorldSoundEventIndex, projectilePosition);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 667 */     shooterComponent.getProjectilesForRemoval().add(projectileRef);
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
/*     */   private void applyKnockback(@Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d attackerPos, float attackerYaw, @Nonnull Store<EntityStore> store) {
/* 679 */     KnockbackComponent knockbackComponent = (KnockbackComponent)store.ensureAndGetComponent(targetRef, KnockbackComponent.getComponentType());
/*     */     
/* 681 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(targetRef, TransformComponent.getComponentType());
/* 682 */     assert transformComponent != null;
/*     */     
/* 684 */     knockbackComponent.setVelocity(this.projectileKnockback.calculateVector(attackerPos, attackerYaw, transformComponent.getPosition()));
/* 685 */     knockbackComponent.setVelocityType(this.projectileKnockback.getVelocityType());
/* 686 */     knockbackComponent.setVelocityConfig(this.projectileKnockback.getVelocityConfig());
/* 687 */     knockbackComponent.setDuration(this.projectileKnockback.getDuration());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 692 */     return "DeployableTurretConfig{}" + super
/* 693 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\config\DeployableTurretConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */