/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.MovementSettings;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.entities.ApplyKnockback;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SimulateKnockback
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/* 251 */   private static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/*     */   
/* 253 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] {
/* 254 */         (Query)Player.getComponentType(), 
/* 255 */         (Query)TransformComponent.getComponentType(), 
/* 256 */         (Query)KnockbackSimulation.getComponentType(), (Query)BOUNDING_BOX_COMPONENT_TYPE, 
/*     */         
/* 258 */         (Query)MovementStatesComponent.getComponentType()
/*     */       });
/* 260 */   private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, PlayerSystems.ProcessPlayerInput.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 267 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 273 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 278 */     KnockbackSimulation knockbackSimulationComponent = (KnockbackSimulation)archetypeChunk.getComponent(index, KnockbackSimulation.getComponentType());
/* 279 */     assert knockbackSimulationComponent != null;
/*     */     
/* 281 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 282 */     assert transformComponent != null;
/*     */     
/* 284 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 285 */     assert playerComponent != null;
/*     */     
/* 287 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 289 */     float time = knockbackSimulationComponent.getRemainingTime();
/* 290 */     time -= dt;
/* 291 */     knockbackSimulationComponent.setRemainingTime(time);
/* 292 */     if (time < 0.0F || archetypeChunk.getArchetype().contains(DeathComponent.getComponentType())) {
/* 293 */       commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), KnockbackSimulation.getComponentType());
/*     */       
/*     */       return;
/*     */     } 
/* 297 */     if (KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION) {
/* 298 */       Vector3d particlePosition = knockbackSimulationComponent.getClientPosition();
/* 299 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 300 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 301 */       playerSpatialResource.getSpatialStructure().collect(particlePosition, 75.0D, (List)results);
/*     */       
/* 303 */       Color color = knockbackSimulationComponent.hadWishMovement() ? new Color((byte)-1, (byte)0, (byte)0) : new Color((byte)0, (byte)0, (byte)-1);
/* 304 */       ParticleUtil.spawnParticleEffect("Example_Simple", particlePosition, 0.0F, 0.0F, 0.0F, 1.0F, color, (List)results, (ComponentAccessor)commandBuffer);
/*     */     } 
/*     */     
/* 307 */     knockbackSimulationComponent.setTickBuffer(knockbackSimulationComponent.getTickBuffer() + dt);
/*     */     
/* 309 */     MovementStates clientStates = knockbackSimulationComponent.getClientMovementStates();
/*     */     
/* 311 */     while (knockbackSimulationComponent.getTickBuffer() >= 0.016666668F) {
/* 312 */       knockbackSimulationComponent.setTickBuffer(knockbackSimulationComponent.getTickBuffer() - 0.016666668F);
/*     */ 
/*     */       
/* 315 */       Vector3d rel = knockbackSimulationComponent.getRelativeMovement();
/* 316 */       Vector3d requestedVelocity = knockbackSimulationComponent.getRequestedVelocity();
/* 317 */       Vector3d simPos = knockbackSimulationComponent.getSimPosition();
/* 318 */       Vector3d velocity = knockbackSimulationComponent.getSimVelocity();
/*     */       
/* 320 */       MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType());
/* 321 */       assert movementStatesComponent != null;
/*     */       
/* 323 */       MovementStates movementStates = movementStatesComponent.getMovementStates();
/*     */       
/* 325 */       MovementManager movementManagerComponent = (MovementManager)archetypeChunk.getComponent(index, MovementManager.getComponentType());
/* 326 */       assert movementManagerComponent != null;
/*     */       
/* 328 */       MovementSettings movementManagerSettings = movementManagerComponent.getSettings();
/*     */       
/* 330 */       BoundingBox boundingBoxComponent = (BoundingBox)archetypeChunk.getComponent(index, BOUNDING_BOX_COMPONENT_TYPE);
/* 331 */       assert boundingBoxComponent != null;
/* 332 */       Box hitBox = boundingBoxComponent.getBoundingBox();
/*     */       
/* 334 */       if (clientStates.flying) {
/*     */         
/* 336 */         knockbackSimulationComponent.setRemainingTime(0.0F); return;
/*     */       } 
/* 338 */       if (clientStates.climbing && !clientStates.onGround) {
/*     */         
/* 340 */         knockbackSimulationComponent.setRemainingTime(0.0F); return;
/*     */       } 
/* 342 */       if (clientStates.swimming) {
/*     */         
/* 344 */         knockbackSimulationComponent.setRemainingTime(0.0F);
/*     */         return;
/*     */       } 
/* 347 */       int invertedGravityModifier = movementManagerSettings.invertedGravity ? 1 : -1;
/* 348 */       double terminalVelocity = invertedGravityModifier * PhysicsMath.getTerminalVelocity(movementManagerSettings.mass, 0.0012250000145286322D, 
/*     */           
/* 350 */           Math.abs(hitBox.width() * hitBox.depth()), movementManagerSettings.dragCoefficient);
/*     */ 
/*     */       
/* 353 */       double gravityStep = invertedGravityModifier * PhysicsMath.getAcceleration(velocity.y, terminalVelocity) * 0.01666666753590107D;
/* 354 */       if (velocity.y < terminalVelocity && gravityStep > 0.0D) {
/* 355 */         velocity.y = Math.min(velocity.y + gravityStep, terminalVelocity);
/* 356 */       } else if (velocity.y > terminalVelocity && gravityStep < 0.0D) {
/* 357 */         velocity.y = Math.max(velocity.y + gravityStep, terminalVelocity);
/*     */       } 
/*     */       
/* 360 */       if (movementStates.onGround) {
/* 361 */         movementStates.falling = false;
/*     */         
/* 363 */         if (knockbackSimulationComponent.wasOnGround() && knockbackSimulationComponent.consumeWasJumping()) {
/* 364 */           velocity.y = movementManagerSettings.jumpForce;
/* 365 */           movementStates.onGround = false;
/* 366 */           knockbackSimulationComponent.setJumpCombo(Math.min(knockbackSimulationComponent.getJumpCombo() + 1, 3));
/*     */         } 
/*     */       } else {
/* 369 */         Vector3d checkPosition = simPos.clone();
/* 370 */         checkPosition.y += 0.10000000149011612D;
/* 371 */         movementStates.falling = (velocity.y < 0.0D && CollisionModule.get().validatePosition(world, hitBox, checkPosition, new CollisionResult()) != 0);
/*     */         
/* 373 */         if (movementStates.falling) {
/* 374 */           movementStates.jumping = false;
/* 375 */           movementStates.swimJumping = false;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 380 */       if (knockbackSimulationComponent.getJumpCombo() != 0 && ((movementStates.onGround && knockbackSimulationComponent.wasOnGround()) || velocity.x == 0.0D || velocity.z == 0.0D)) {
/* 381 */         knockbackSimulationComponent.setJumpCombo(0);
/*     */       }
/*     */       
/* 384 */       float friction = computeMoveForce(knockbackSimulationComponent, movementStates, movementManagerSettings);
/*     */       
/* 386 */       if (!movementStates.flying && knockbackSimulationComponent.getRequestedVelocityChangeType() != null) {
/* 387 */         switch (KnockbackPredictionSystems.null.$SwitchMap$com$hypixel$hytale$protocol$ChangeVelocityType[knockbackSimulationComponent.getRequestedVelocityChangeType().ordinal()]) { case 1:
/* 388 */             velocity.add(requestedVelocity.x * 0.18000000715255737D * movementManagerSettings.velocityResistance, requestedVelocity.y, requestedVelocity.z * 0.18000000715255737D * movementManagerSettings.velocityResistance);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 393 */             velocity.assign(requestedVelocity);
/*     */             break; }
/*     */         
/* 396 */         PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/* 397 */         assert playerRefComponent != null;
/*     */         
/* 399 */         playerRefComponent.getPacketHandler().write((Packet)new ApplyKnockback(
/* 400 */               PositionUtil.toPositionPacket(transformComponent.getPosition()), (float)requestedVelocity.x, (float)requestedVelocity.y, (float)requestedVelocity.z, knockbackSimulationComponent
/*     */ 
/*     */ 
/*     */               
/* 404 */               .getRequestedVelocityChangeType()));
/*     */       } 
/*     */       
/* 407 */       requestedVelocity.assign(0.0D);
/* 408 */       knockbackSimulationComponent.setRequestedVelocityChangeType(null);
/*     */       
/* 410 */       Vector3d movementOffset = knockbackSimulationComponent.getMovementOffset();
/* 411 */       movementOffset.assign(0.0D);
/*     */       
/* 413 */       if (knockbackSimulationComponent.hadWishMovement()) {
/* 414 */         float converter = convertWishMovement(knockbackSimulationComponent, movementStates, movementManagerSettings);
/* 415 */         velocity.addScaled(rel, converter);
/*     */       } else {
/* 417 */         movementOffset.addScaled(rel, friction);
/* 418 */         rel.assign(0.0D);
/*     */       } 
/*     */       
/* 421 */       movementOffset.addScaled(velocity, 0.01666666753590107D);
/*     */       
/* 423 */       applyMovementOffset(world, hitBox, knockbackSimulationComponent, movementStates, movementOffset);
/*     */       
/* 425 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/* 427 */       if (time < 0.2F) {
/* 428 */         Vector3d move = Vector3d.lerp(knockbackSimulationComponent.getClientPosition(), simPos, (time / 0.2F));
/* 429 */         playerComponent.moveTo(ref, move.x, move.y, move.z, (ComponentAccessor)commandBuffer); continue;
/*     */       } 
/* 431 */       playerComponent.moveTo(ref, simPos.x, simPos.y, simPos.z, (ComponentAccessor)commandBuffer);
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
/*     */   private float convertWishMovement(@Nonnull KnockbackSimulation simulation, @Nonnull MovementStates movementStates, @Nonnull MovementSettings movementSettings) {
/* 448 */     MovementStates clientStates = simulation.getClientMovementStates();
/* 449 */     if (clientStates == null) clientStates = movementStates;
/*     */     
/* 451 */     Vector3d velocity = simulation.getSimVelocity();
/* 452 */     float horizontalSpeed = (float)Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
/*     */     
/* 454 */     float drag = 0.0F;
/* 455 */     float friction = 1.0F;
/* 456 */     if (!movementStates.flying && !movementStates.climbing) {
/* 457 */       drag = (!movementStates.onGround && !movementStates.swimming) ? convertToNewRange(horizontalSpeed, movementSettings.airDragMinSpeed, movementSettings.airDragMaxSpeed, movementSettings.airDragMin, movementSettings.airDragMax) : 0.82F;
/* 458 */       friction = (!movementStates.onGround && !movementStates.swimming) ? convertToNewRange(horizontalSpeed, movementSettings.airFrictionMinSpeed, movementSettings.airFrictionMaxSpeed, movementSettings.airFrictionMax, movementSettings.airFrictionMin) : (1.0F - drag);
/*     */     } 
/*     */     
/* 461 */     float clientDrag = 0.0F;
/* 462 */     float clientFriction = 1.0F;
/* 463 */     if (!clientStates.flying && !clientStates.climbing) {
/* 464 */       clientDrag = (!clientStates.onGround && !clientStates.swimming) ? convertToNewRange(horizontalSpeed, movementSettings.airDragMinSpeed, movementSettings.airDragMaxSpeed, movementSettings.airDragMin, movementSettings.airDragMax) : 0.82F;
/* 465 */       clientFriction = (!clientStates.onGround && !clientStates.swimming) ? convertToNewRange(horizontalSpeed, movementSettings.airFrictionMinSpeed, movementSettings.airFrictionMaxSpeed, movementSettings.airFrictionMax, movementSettings.airFrictionMin) : (1.0F - clientDrag);
/*     */     } 
/*     */     
/* 468 */     return friction / clientFriction;
/*     */   }
/*     */   
/*     */   private float computeMoveForce(@Nonnull KnockbackSimulation simulation, @Nonnull MovementStates movementStates, @Nonnull MovementSettings movementSettings) {
/* 472 */     float drag = 0.0F;
/* 473 */     float friction = 1.0F;
/*     */     
/* 475 */     Vector3d velocity = simulation.getSimVelocity();
/* 476 */     float horizontalSpeed = (float)Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
/*     */ 
/*     */     
/* 479 */     if (!movementStates.flying && !movementStates.climbing) {
/* 480 */       drag = (!movementStates.onGround && !movementStates.swimming) ? convertToNewRange(horizontalSpeed, movementSettings.airDragMinSpeed, movementSettings.airDragMaxSpeed, movementSettings.airDragMin, movementSettings.airDragMax) : 0.82F;
/* 481 */       friction = (!movementStates.onGround && !movementStates.swimming) ? (convertToNewRange(horizontalSpeed, movementSettings.airFrictionMinSpeed, movementSettings.airFrictionMaxSpeed, movementSettings.airFrictionMax, movementSettings.airFrictionMin) / (1.0F - drag)) : 1.0F;
/*     */     } 
/*     */     
/* 484 */     velocity.x *= drag;
/* 485 */     velocity.z *= drag;
/*     */     
/* 487 */     return friction;
/*     */   }
/*     */   
/*     */   private static float convertToNewRange(float value, float oldMinRange, float oldMaxRange, float newMinRange, float newMaxRange) {
/* 491 */     if (newMinRange == newMaxRange || oldMinRange == oldMaxRange) return newMinRange;
/*     */     
/* 493 */     float newValue = (value - oldMinRange) * (newMaxRange - newMinRange) / (oldMaxRange - oldMinRange) + newMinRange;
/* 494 */     return MathUtil.clamp(newValue, Math.min(newMinRange, newMaxRange), Math.max(newMinRange, newMaxRange));
/*     */   }
/*     */   
/*     */   public void applyMovementOffset(@Nonnull World world, @Nonnull Box hitBox, @Nonnull KnockbackSimulation simulation, @Nonnull MovementStates movementStates, @Nonnull Vector3d movementOffset) {
/* 498 */     int moveCycles = (int)Math.ceil(movementOffset.length() / 0.25D);
/* 499 */     Vector3d cycleMovementOffset = (moveCycles == 1) ? movementOffset : movementOffset.clone().scale((1.0F / moveCycles));
/*     */     
/* 501 */     simulation.setWasOnGround(movementStates.onGround);
/*     */     
/* 503 */     for (int i = 0; i < moveCycles; i++) {
/* 504 */       doMoveCycle(world, hitBox, simulation, movementStates, cycleMovementOffset);
/*     */     }
/*     */     
/* 507 */     if (movementStates.onGround) {
/* 508 */       (simulation.getSimVelocity()).y = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doMoveCycle(@Nonnull World world, @Nonnull Box hitBox, @Nonnull KnockbackSimulation simulation, @Nonnull MovementStates movementStates, @Nonnull Vector3d offset) {
/* 515 */     Vector3d simPos = simulation.getSimPosition();
/* 516 */     Vector3d velocity = simulation.getSimVelocity();
/* 517 */     Vector3d checkPosition = simulation.getCheckPosition();
/* 518 */     checkPosition.assign(simPos);
/* 519 */     CollisionResult collisionResult = simulation.getCollisionResult();
/* 520 */     collisionResult.reset();
/*     */ 
/*     */     
/* 523 */     checkPosition.y += offset.y;
/* 524 */     boolean hasCollidedY = checkCollision(simulation, world, hitBox, checkPosition, offset, KnockbackPredictionSystems.CollisionAxis.Y, collisionResult);
/*     */     
/* 526 */     if (movementStates.onGround && offset.y < 0.0D) {
/* 527 */       if (!hasCollidedY) {
/* 528 */         movementStates.onGround = false;
/*     */       } else {
/* 530 */         checkPosition.y -= offset.y;
/*     */       } 
/* 532 */     } else if (hasCollidedY) {
/* 533 */       if (offset.y <= 0.0D) {
/* 534 */         movementStates.onGround = true;
/* 535 */         checkPosition.y -= offset.y;
/*     */       } else {
/* 537 */         movementStates.onGround = false;
/* 538 */         checkPosition.y -= offset.y;
/*     */       } 
/*     */       
/* 541 */       velocity.y = 0.0D;
/*     */     } else {
/* 543 */       movementStates.onGround = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 548 */     if (offset.x != 0.0D) {
/* 549 */       checkPosition.x += offset.x;
/* 550 */       collisionResult.reset();
/* 551 */       boolean hasCollidedX = checkCollision(simulation, world, hitBox, checkPosition, offset, KnockbackPredictionSystems.CollisionAxis.Y, collisionResult);
/* 552 */       if (hasCollidedX) {
/* 553 */         checkPosition.x -= offset.x;
/*     */       }
/*     */     } 
/*     */     
/* 557 */     if (offset.z != 0.0D) {
/* 558 */       checkPosition.z += offset.z;
/* 559 */       collisionResult.reset();
/* 560 */       boolean hasCollidedZ = checkCollision(simulation, world, hitBox, checkPosition, offset, KnockbackPredictionSystems.CollisionAxis.Y, collisionResult);
/* 561 */       if (hasCollidedZ) {
/* 562 */         checkPosition.z -= offset.z;
/*     */       }
/*     */     } 
/*     */     
/* 566 */     simPos.assign(checkPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkCollision(@Nonnull KnockbackSimulation simulation, @Nonnull World world, @Nonnull Box hitBox, @Nonnull Vector3d position, Vector3d moveOffset, KnockbackPredictionSystems.CollisionAxis axis, @Nonnull CollisionResult result) {
/* 571 */     Vector3d tempPosition = simulation.getTempPosition();
/* 572 */     tempPosition.assign(position);
/* 573 */     tempPosition.add(0.0D, 9.999999747378752E-5D, 0.0D);
/*     */     
/* 575 */     return (CollisionModule.get().validatePosition(world, hitBox, tempPosition, result) != 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\KnockbackPredictionSystems$SimulateKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */