/*     */ package com.hypixel.hytale.server.core.modules.projectile.system;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BlockCollisionProvider;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BlockTracker;
/*     */ import com.hypixel.hytale.server.core.modules.collision.EntityContactData;
/*     */ import com.hypixel.hytale.server.core.modules.collision.EntityRefCollisionProvider;
/*     */ import com.hypixel.hytale.server.core.modules.collision.IBlockCollisionConsumer;
/*     */ import com.hypixel.hytale.server.core.modules.collision.IBlockTracker;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.RestingSupport;
/*     */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.ForceProvider;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.ForceProviderEntity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.ForceProviderStandardState;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsBodyState;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsBodyStateUpdater;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsConfig;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.StandardPhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class StandardPhysicsTickSystem extends EntityTickingSystem<EntityStore> {
/*     */   @Nonnull
/*  39 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] {
/*  40 */         (Query)StandardPhysicsProvider.getComponentType(), 
/*  41 */         (Query)TransformComponent.getComponentType(), 
/*  42 */         (Query)HeadRotation.getComponentType(), 
/*  43 */         (Query)Velocity.getComponentType(), 
/*  44 */         (Query)BoundingBox.getComponentType()
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, TangiableEntitySpatialSystem.class, OrderPriority.CLOSEST), new SystemDependency(Order.BEFORE, TransformSystems.EntityTrackerUpdate.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  59 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  65 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  70 */     TimeResource timeResource = (TimeResource)store.getResource(TimeResource.getResourceType());
/*  71 */     float timeDilationModifier = timeResource.getTimeDilationModifier();
/*     */ 
/*     */     
/*  74 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  75 */     dt = 1.0F / world.getTps();
/*  76 */     dt *= timeDilationModifier;
/*     */     
/*  78 */     StandardPhysicsProvider physicsComponent = (StandardPhysicsProvider)archetypeChunk.getComponent(index, StandardPhysicsProvider.getComponentType());
/*  79 */     assert physicsComponent != null;
/*     */     
/*  81 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/*  82 */     assert velocityComponent != null;
/*     */     
/*  84 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  85 */     assert transformComponent != null;
/*     */     
/*  87 */     BoundingBox boundingBoxComponent = (BoundingBox)archetypeChunk.getComponent(index, BoundingBox.getComponentType());
/*  88 */     assert boundingBoxComponent != null;
/*     */     
/*  90 */     StandardPhysicsConfig physicsConfig = physicsComponent.getPhysicsConfig();
/*     */     
/*  92 */     Ref<EntityStore> selfRef = archetypeChunk.getReferenceTo(index);
/*     */     
/*  94 */     if (physicsComponent.getState() == StandardPhysicsProvider.STATE.INACTIVE) {
/*  95 */       velocityComponent.setZero();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 100 */     ForceProviderStandardState forceState = physicsComponent.getForceProviderStandardState();
/* 101 */     RestingSupport restingSupport = physicsComponent.getRestingSupport();
/*     */     
/* 103 */     if (physicsComponent.getState() == StandardPhysicsProvider.STATE.RESTING) {
/* 104 */       if (forceState.externalForce.squaredLength() == 0.0D && !restingSupport.hasChanged(world)) {
/*     */         return;
/*     */       }
/* 107 */       physicsComponent.setState(StandardPhysicsProvider.STATE.ACTIVE);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     Vector3d position = physicsComponent.getPosition();
/* 116 */     Vector3d velocity = physicsComponent.getVelocity();
/* 117 */     Vector3d movement = physicsComponent.getMovement();
/* 118 */     Box boundingBox = boundingBoxComponent.getBoundingBox();
/* 119 */     PhysicsBodyState stateBefore = physicsComponent.getStateBefore();
/* 120 */     PhysicsBodyState stateAfter = physicsComponent.getStateAfter();
/* 121 */     ForceProviderEntity forceProviderEntity = physicsComponent.getForceProviderEntity();
/* 122 */     PhysicsBodyStateUpdater stateUpdater = physicsComponent.getStateUpdater();
/* 123 */     ForceProvider[] forceProviders = physicsComponent.getForceProviders();
/* 124 */     Vector3d moveOutOfSolidVelocity = physicsComponent.getMoveOutOfSolidVelocity();
/*     */ 
/*     */     
/* 127 */     double gravity = physicsConfig.getGravity();
/* 128 */     int bounceCount = physicsConfig.getBounceCount();
/* 129 */     boolean allowRolling = physicsConfig.isAllowRolling();
/*     */     
/* 131 */     physicsComponent.setWorld(world);
/* 132 */     position.assign(transformComponent.getPosition());
/* 133 */     velocityComponent.assignVelocityTo(velocity);
/*     */     
/* 135 */     double mass = forceProviderEntity.getMass(boundingBox.getVolume());
/*     */     
/* 137 */     forceState.convertToForces(dt, mass);
/*     */ 
/*     */     
/* 140 */     forceState.updateVelocity(velocity);
/*     */ 
/*     */     
/* 143 */     if (velocity.squaredLength() * dt * dt >= 1.0000000000000002E-10D || forceState.externalForce
/* 144 */       .squaredLength() >= 0.0D) {
/* 145 */       physicsComponent.setState(StandardPhysicsProvider.STATE.ACTIVE);
/*     */     } else {
/* 147 */       velocity.assign(Vector3d.ZERO);
/*     */     } 
/*     */     
/* 150 */     if (physicsComponent.getState() == StandardPhysicsProvider.STATE.RESTING && restingSupport.hasChanged(world)) {
/* 151 */       physicsComponent.setState(StandardPhysicsProvider.STATE.ACTIVE);
/*     */     }
/*     */ 
/*     */     
/* 155 */     stateBefore.position.assign(position);
/* 156 */     stateBefore.velocity.assign(velocity);
/* 157 */     forceProviderEntity.setForceProviderStandardState(forceState);
/*     */     
/* 159 */     stateUpdater.update(stateBefore, stateAfter, mass, dt, physicsComponent.isOnGround(), forceProviders);
/*     */     
/* 161 */     velocity.assign(stateAfter.velocity);
/* 162 */     movement.assign(velocity).scale(dt);
/*     */     
/* 164 */     forceState.clear();
/*     */     
/* 166 */     if (velocity.squaredLength() * dt * dt >= 1.0000000000000002E-10D) {
/* 167 */       physicsComponent.setState(StandardPhysicsProvider.STATE.ACTIVE);
/*     */     } else {
/* 169 */       velocity.assign(Vector3d.ZERO);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     EntityRefCollisionProvider entityCollisionProvider = physicsComponent.getEntityCollisionProvider();
/* 178 */     BlockCollisionProvider blockCollisionProvider = physicsComponent.getBlockCollisionProvider();
/* 179 */     BlockTracker triggerTracker = physicsComponent.getTriggerTracker();
/* 180 */     Vector3d contactPosition = physicsComponent.getContactPosition();
/* 181 */     Vector3d contactNormal = physicsComponent.getContactNormal();
/* 182 */     Vector3d nextMovement = physicsComponent.getNextMovement();
/*     */     
/* 184 */     double maxRelativeDistance = 1.0D;
/*     */     
/* 186 */     if (physicsComponent.isProvidesCharacterCollisions()) {
/* 187 */       Ref<EntityStore> creatorReference = null;
/* 188 */       if (physicsComponent.getCreatorUuid() != null) {
/* 189 */         creatorReference = ((EntityStore)store.getExternalData()).getRefFromUUID(physicsComponent.getCreatorUuid());
/*     */       }
/* 191 */       maxRelativeDistance = entityCollisionProvider.computeNearest(commandBuffer, boundingBox, position, movement, selfRef, creatorReference);
/* 192 */       if (maxRelativeDistance < 0.0D || maxRelativeDistance > 1.0D) maxRelativeDistance = 1.0D;
/*     */     
/*     */     } 
/* 195 */     physicsComponent.setBounced(false);
/* 196 */     physicsComponent.setOnGround(false);
/* 197 */     moveOutOfSolidVelocity.assign(Vector3d.ZERO);
/* 198 */     physicsComponent.setMovedInsideSolid(false);
/* 199 */     physicsComponent.setDisplacedMass(0.0D);
/* 200 */     physicsComponent.setSubSurfaceVolume(0.0D);
/* 201 */     physicsComponent.setEnterFluid(Double.MAX_VALUE);
/* 202 */     physicsComponent.setLeaveFluid(-1.7976931348623157E308D);
/*     */ 
/*     */     
/* 205 */     physicsComponent.setCollisionStart(maxRelativeDistance);
/* 206 */     contactPosition.assign(position).addScaled(movement, physicsComponent.getCollisionStart());
/* 207 */     contactNormal.assign(Vector3d.ZERO);
/*     */ 
/*     */     
/* 210 */     physicsComponent.setSliding(true);
/* 211 */     Vector3d tmpPosition = position.clone();
/* 212 */     nextMovement.assign(Vector3d.ZERO);
/* 213 */     while (physicsComponent.isSliding() && !movement.equals(Vector3d.ZERO)) {
/* 214 */       contactPosition.assign(tmpPosition).addScaled(movement, physicsComponent.getCollisionStart());
/*     */       
/* 216 */       physicsComponent.setSliding(false);
/* 217 */       blockCollisionProvider.cast(world, boundingBox, tmpPosition, movement, (IBlockCollisionConsumer)physicsComponent, (IBlockTracker)triggerTracker, maxRelativeDistance);
/* 218 */       movement.assign(nextMovement);
/* 219 */       tmpPosition.assign(contactPosition);
/*     */     } 
/* 221 */     movement.assign(tmpPosition).add(nextMovement).subtract(position);
/*     */ 
/*     */     
/* 224 */     physicsComponent.getFluidTracker().reset();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     double density = (physicsComponent.getDisplacedMass() > 0.0D) ? (physicsComponent.getDisplacedMass() / physicsComponent.getSubSurfaceVolume()) : 1.2D;
/*     */ 
/*     */     
/* 232 */     if (physicsComponent.isMovedInsideSolid()) {
/* 233 */       position.addScaled(moveOutOfSolidVelocity, dt);
/* 234 */       velocity.assign(moveOutOfSolidVelocity);
/*     */       
/* 236 */       forceState.dragCoefficient = physicsComponent.getDragCoefficient(density);
/* 237 */       forceState.displacedMass = physicsComponent.getDisplacedMass();
/* 238 */       forceState.gravity = gravity;
/* 239 */       physicsComponent.finishTick(transformComponent, velocityComponent);
/*     */       
/*     */       return;
/*     */     } 
/* 243 */     double velocityClip = physicsComponent.isBounced() ? physicsComponent.getCollisionStart() : 1.0D;
/* 244 */     boolean enteringWater = false;
/*     */     
/* 246 */     if (!physicsComponent.isInFluid() && physicsComponent.getEnterFluid() < physicsComponent.getCollisionStart()) {
/* 247 */       physicsComponent.setInFluid(true);
/* 248 */       velocityClip = physicsComponent.getEnterFluid();
/* 249 */       physicsComponent.setVelocityExtremaCount(2);
/* 250 */       enteringWater = true;
/* 251 */     } else if (physicsComponent.isInFluid() && physicsComponent.getLeaveFluid() < physicsComponent.getCollisionStart()) {
/* 252 */       physicsComponent.setInFluid(false);
/* 253 */       velocityClip = physicsComponent.getLeaveFluid();
/* 254 */       physicsComponent.setVelocityExtremaCount(2);
/*     */     } 
/*     */     
/* 257 */     if (velocityClip > 0.0D && velocityClip < 1.0D) {
/*     */       
/* 259 */       stateUpdater.update(stateBefore, stateAfter, mass, dt * velocityClip, physicsComponent.isOnGround(), forceProviders);
/* 260 */       velocity.assign(stateAfter.velocity);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 265 */     if (physicsComponent.isInFluid() && physicsComponent.getSubSurfaceVolume() < boundingBox.getVolume() && 
/* 266 */       physicsComponent.getVelocityExtremaCount() > 0) {
/* 267 */       double speedBefore = stateBefore.velocity.y;
/* 268 */       double speedAfter = stateAfter.velocity.y;
/* 269 */       if (speedBefore * speedAfter <= 0.0D) physicsComponent.decrementVelocityExtremaCount();
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 275 */     if (physicsComponent.isSwimming()) {
/* 276 */       forceState.externalForce.y -= stateAfter.velocity.y * physicsConfig.getSwimmingDampingFactor() / mass;
/*     */     }
/*     */ 
/*     */     
/* 280 */     if (enteringWater) {
/* 281 */       forceState.externalImpulse.addScaled(stateAfter.velocity, -physicsConfig.getHitWaterImpulseLoss() * mass);
/*     */     }
/*     */     
/* 284 */     forceState.displacedMass = physicsComponent.getDisplacedMass();
/* 285 */     forceState.dragCoefficient = physicsComponent.getDragCoefficient(density);
/* 286 */     forceState.gravity = gravity;
/*     */     
/* 288 */     if (entityCollisionProvider.getCount() > 0) {
/*     */ 
/*     */       
/* 291 */       EntityContactData contact = entityCollisionProvider.getContact(0);
/* 292 */       Ref<EntityStore> contactRef = contact.getEntityReference();
/*     */       
/* 294 */       position.assign(contact.getCollisionPoint());
/* 295 */       physicsComponent.setState(StandardPhysicsProvider.STATE.INACTIVE);
/* 296 */       if (physicsComponent.getImpactConsumer() != null) {
/* 297 */         physicsComponent.getImpactConsumer().onImpact(selfRef, position, contactRef, contact.getCollisionDetailName(), commandBuffer);
/*     */       }
/* 299 */       physicsComponent.rotateBody(dt, transformComponent.getRotation());
/* 300 */       physicsComponent.finishTick(transformComponent, velocityComponent);
/*     */       
/*     */       return;
/*     */     } 
/* 304 */     if (physicsComponent.isBounced()) {
/* 305 */       position.assign(contactPosition);
/* 306 */       physicsComponent.incrementBounces();
/*     */       
/* 308 */       SimplePhysicsProvider.computeReflectedVector(velocity, contactNormal, velocity);
/*     */       
/* 310 */       if (bounceCount == -1 || physicsComponent.getBounces() <= bounceCount) {
/* 311 */         velocity.scale(physicsConfig.getBounciness());
/*     */       }
/*     */       
/* 314 */       if ((bounceCount != -1 && physicsComponent.getBounces() > bounceCount) || velocity
/* 315 */         .squaredLength() * dt * dt < physicsConfig.getBounceLimit() * physicsConfig.getBounceLimit()) {
/* 316 */         boolean hitGround = contactNormal.equals(Vector3d.UP);
/* 317 */         if (!allowRolling && (physicsConfig.isSticksVertically() || hitGround)) {
/* 318 */           physicsComponent.setState(StandardPhysicsProvider.STATE.RESTING);
/* 319 */           restingSupport.rest(world, boundingBox, position);
/*     */           
/* 321 */           physicsComponent.setOnGround(hitGround);
/* 322 */           if (physicsComponent.getImpactConsumer() != null) {
/* 323 */             physicsComponent.getImpactConsumer().onImpact(selfRef, position, null, null, commandBuffer);
/*     */           }
/*     */         } 
/*     */         
/* 327 */         if (allowRolling) {
/* 328 */           velocity.y = 0.0D;
/* 329 */           velocity.scale(physicsConfig.getRollingFrictionFactor());
/* 330 */           physicsComponent.setOnGround(hitGround);
/*     */         } else {
/* 332 */           velocity.assign(Vector3d.ZERO);
/*     */         }
/*     */       
/* 335 */       } else if (physicsComponent.getBounceConsumer() != null) {
/* 336 */         physicsComponent.getBounceConsumer().onBounce(selfRef, position, commandBuffer);
/*     */       } 
/*     */       
/* 339 */       physicsComponent.rotateBody(dt, transformComponent.getRotation());
/* 340 */       physicsComponent.finishTick(transformComponent, velocityComponent);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 345 */     position.add(movement);
/*     */     
/* 347 */     physicsComponent.rotateBody(dt, transformComponent.getRotation());
/* 348 */     physicsComponent.finishTick(transformComponent, velocityComponent);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\system\StandardPhysicsTickSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */