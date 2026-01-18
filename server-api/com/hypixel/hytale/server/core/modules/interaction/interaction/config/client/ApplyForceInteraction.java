/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.math.range.FloatRange;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.AppliedForce;
/*     */ import com.hypixel.hytale.protocol.ApplyForceState;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.protocol.FloatRange;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.RaycastMode;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplyForceInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ApplyForceInteraction> CODEC;
/*     */   private static final int LABEL_COUNT = 3;
/*     */   private static final int NEXT_LABEL_INDEX = 0;
/*     */   private static final int GROUND_LABEL_INDEX = 1;
/*     */   private static final int COLLISION_LABEL_INDEX = 2;
/*     */   private static final float SPATIAL_STRUCTURE_RADIUS = 1.5F;
/*     */   
/*     */   static {
/* 195 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ApplyForceInteraction.class, ApplyForceInteraction::new, SimpleInteraction.CODEC).documentation("Applies a force to the user, optionally waiting for a condition to met before continuing.")).appendInherited(new KeyedCodec("Direction", (Codec)Vector3d.CODEC), (o, i) -> (o.forces[0]).direction = i.normalize(), o -> null, (o, p) -> {  }).documentation("The direction of the force to apply.").add()).appendInherited(new KeyedCodec("AdjustVertical", (Codec)Codec.BOOLEAN), (o, i) -> (o.forces[0]).adjustVertical = i.booleanValue(), o -> null, (o, p) -> {  }).documentation("Whether the force should be adjusted based on the vertical look of the user.").add()).appendInherited(new KeyedCodec("Force", (Codec)Codec.DOUBLE), (o, i) -> (o.forces[0]).force = i.doubleValue(), o -> null, (o, p) -> {  }).documentation("The size of the force to apply.").add()).appendInherited(new KeyedCodec("Forces", (Codec)new ArrayCodec((Codec)Force.CODEC, x$0 -> new Force[x$0])), (o, i) -> o.forces = i, o -> o.forces, (o, p) -> o.forces = p.forces).documentation("A collection of forces to apply to the user.\nReplaces `Direction`, `AdjustVertical` and `Force` if used.").add()).appendInherited(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (o, f) -> o.duration = f.floatValue(), o -> Float.valueOf(o.duration), (o, p) -> o.duration = p.duration).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).documentation("The duration for which force should be continuously applied. If 0, force is applied on first run.").add()).appendInherited(new KeyedCodec("VerticalClamp", (Codec)FloatRange.CODEC), (o, i) -> o.verticalClamp = i, o -> o.verticalClamp, (o, p) -> o.verticalClamp = p.verticalClamp).documentation("The angles in degrees to clamp the look angle to when adjusting the force").add()).appendInherited(new KeyedCodec("WaitForGround", (Codec)Codec.BOOLEAN), (o, i) -> o.waitForGround = i.booleanValue(), o -> Boolean.valueOf(o.waitForGround), (o, p) -> o.waitForGround = p.waitForGround).documentation("Determines whether or not on ground should be checked").add()).appendInherited(new KeyedCodec("WaitForCollision", (Codec)Codec.BOOLEAN), (o, i) -> o.waitForCollision = i.booleanValue(), o -> Boolean.valueOf(o.waitForCollision), (o, p) -> o.waitForCollision = p.waitForCollision).documentation("Determines whether or not collision should be checked").add()).appendInherited(new KeyedCodec("RaycastDistance", (Codec)Codec.FLOAT), (o, i) -> o.raycastDistance = i.floatValue(), o -> Float.valueOf(o.raycastDistance), (o, p) -> o.raycastDistance = p.raycastDistance).documentation("The distance of the raycast for the collision check").add()).appendInherited(new KeyedCodec("RaycastHeightOffset", (Codec)Codec.FLOAT), (o, i) -> o.raycastHeightOffset = i.floatValue(), o -> Float.valueOf(o.raycastHeightOffset), (o, p) -> o.raycastHeightOffset = p.raycastHeightOffset).documentation("The height offset of the raycast for the collision check (default 0)").add()).appendInherited(new KeyedCodec("RaycastMode", (Codec)new EnumCodec(RaycastMode.class)), (o, i) -> o.raycastMode = i, o -> o.raycastMode, (o, p) -> o.raycastMode = p.raycastMode).documentation("The type of raycast performed for the collision check").add()).appendInherited(new KeyedCodec("GroundCheckDelay", (Codec)Codec.FLOAT), (o, i) -> o.groundCheckDelay = i.floatValue(), o -> Float.valueOf(o.groundCheckDelay), (o, p) -> o.groundCheckDelay = p.groundCheckDelay).documentation("The delay in seconds before checking if on ground").add()).appendInherited(new KeyedCodec("CollisionCheckDelay", (Codec)Codec.FLOAT), (o, i) -> o.collisionCheckDelay = i.floatValue(), o -> Float.valueOf(o.collisionCheckDelay), (o, p) -> o.collisionCheckDelay = p.collisionCheckDelay).documentation("The delay in seconds before checking entity collision").add()).appendInherited(new KeyedCodec("ChangeVelocityType", (Codec)ProtocolCodecs.CHANGE_VELOCITY_TYPE_CODEC), (o, i) -> o.changeVelocityType = i, o -> o.changeVelocityType, (o, p) -> o.changeVelocityType = p.changeVelocityType).documentation("Configures how the velocity gets applied to the user.").add()).appendInherited(new KeyedCodec("VelocityConfig", (Codec)VelocityConfig.CODEC), (o, i) -> o.velocityConfig = i, o -> o.velocityConfig, (o, p) -> o.velocityConfig = p.velocityConfig).documentation("Specific configuration options that control how the velocity is affected.").add()).appendInherited(new KeyedCodec("GroundNext", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.groundInteraction = s, interaction -> interaction.groundInteraction, (interaction, parent) -> interaction.groundInteraction = parent.groundInteraction).documentation("The interaction to run if on-ground is apparent.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("CollisionNext", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.collisionInteraction = s, interaction -> interaction.collisionInteraction, (interaction, parent) -> interaction.collisionInteraction = parent.collisionInteraction).documentation("The interaction to run if collision is apparent.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).build();
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
/*     */   @Nonnull
/* 225 */   private ChangeVelocityType changeVelocityType = ChangeVelocityType.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 231 */   private Force[] forces = new Force[] { new Force() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   private float duration = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean waitForGround = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean waitForCollision = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   private float groundCheckDelay = 0.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   private float collisionCheckDelay = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   private float raycastDistance = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   private float raycastHeightOffset = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 276 */   private RaycastMode raycastMode = RaycastMode.FollowMotion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/* 282 */   private VelocityConfig velocityConfig = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/* 288 */   private FloatRange verticalClamp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/* 294 */   private String groundInteraction = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/* 300 */   private String collisionInteraction = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 306 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 311 */     InteractionSyncData contextState = context.getState();
/*     */     
/* 313 */     if (firstRun) {
/* 314 */       contextState.state = InteractionState.NotFinished;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 319 */     InteractionSyncData clientState = context.getClientState();
/* 320 */     assert clientState != null;
/*     */     
/* 322 */     ApplyForceState applyForceState = clientState.applyForceState;
/*     */     
/* 324 */     switch (applyForceState) {
/*     */       case Add:
/* 326 */         contextState.state = InteractionState.Finished;
/* 327 */         context.jump(context.getLabel(1));
/*     */         break;
/*     */       case Set:
/* 330 */         contextState.state = InteractionState.Finished;
/* 331 */         context.jump(context.getLabel(2));
/*     */         break;
/*     */       case null:
/* 334 */         contextState.state = InteractionState.Finished;
/* 335 */         context.jump(context.getLabel(0)); break;
/*     */       default:
/* 337 */         contextState.state = InteractionState.NotFinished;
/*     */         break;
/*     */     } 
/* 340 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 345 */     InteractionSyncData contextState = context.getState();
/* 346 */     Ref<EntityStore> entityRef = context.getEntity();
/*     */     
/* 348 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 349 */     assert commandBuffer != null;
/*     */     
/* 351 */     Store<EntityStore> entityStore = commandBuffer.getStore();
/*     */     
/* 353 */     if (firstRun || (this.duration > 0.0F && time < this.duration)) {
/* 354 */       HeadRotation headRotationComponent = (HeadRotation)entityStore.getComponent(entityRef, HeadRotation.getComponentType());
/* 355 */       assert headRotationComponent != null;
/*     */       
/* 357 */       Velocity velocityComponent = (Velocity)entityStore.getComponent(entityRef, Velocity.getComponentType());
/* 358 */       assert velocityComponent != null;
/*     */       
/* 360 */       Vector3f entityHeadRotation = headRotationComponent.getRotation();
/*     */       
/* 362 */       ChangeVelocityType velocityType = this.changeVelocityType;
/*     */       
/* 364 */       for (Force force : this.forces) {
/* 365 */         Vector3d forceDirection = force.direction.clone();
/* 366 */         if (force.adjustVertical) {
/* 367 */           float lookX = entityHeadRotation.x;
/* 368 */           if (this.verticalClamp != null) {
/* 369 */             lookX = MathUtil.clamp(lookX, this.verticalClamp
/*     */                 
/* 371 */                 .getInclusiveMin() * 0.017453292F, this.verticalClamp
/* 372 */                 .getInclusiveMax() * 0.017453292F);
/*     */           }
/*     */           
/* 375 */           forceDirection.rotateX(lookX);
/*     */         } 
/*     */         
/* 378 */         forceDirection.scale(force.force);
/* 379 */         forceDirection.rotateY(entityHeadRotation.y);
/*     */         
/* 381 */         switch (velocityType) { case Add:
/* 382 */             velocityComponent.addInstruction(forceDirection, null, ChangeVelocityType.Add); break;
/* 383 */           case Set: velocityComponent.addInstruction(forceDirection, null, ChangeVelocityType.Set);
/*     */             break; }
/*     */         
/* 386 */         velocityType = ChangeVelocityType.Add;
/*     */       } 
/* 388 */       contextState.state = InteractionState.NotFinished;
/*     */       
/*     */       return;
/*     */     } 
/* 392 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)entityStore.getComponent(entityRef, MovementStatesComponent.getComponentType());
/* 393 */     assert movementStatesComponent != null;
/*     */     
/* 395 */     TransformComponent transformComponent = (TransformComponent)entityStore.getComponent(entityRef, TransformComponent.getComponentType());
/* 396 */     assert transformComponent != null;
/*     */     
/* 398 */     MovementStates entityMovementStates = movementStatesComponent.getMovementStates();
/*     */ 
/*     */ 
/*     */     
/* 402 */     SpatialResource<Ref<EntityStore>, EntityStore> networkSendableSpatialComponent = (SpatialResource<Ref<EntityStore>, EntityStore>)entityStore.getResource(EntityModule.get().getNetworkSendableSpatialResourceType());
/* 403 */     SpatialStructure<Ref<EntityStore>> spatialStructure = networkSendableSpatialComponent.getSpatialStructure();
/* 404 */     ObjectList<Ref<EntityStore>> entities = SpatialResource.getThreadLocalReferenceList();
/* 405 */     spatialStructure.collect(transformComponent.getPosition(), 1.5D, (List)entities);
/*     */     
/* 407 */     boolean checkGround = (time >= this.groundCheckDelay);
/* 408 */     boolean onGround = (checkGround && this.waitForGround && (entityMovementStates.onGround || entityMovementStates.inFluid || entityMovementStates.climbing));
/*     */     
/* 410 */     boolean checkCollision = (time >= this.collisionCheckDelay);
/* 411 */     boolean collided = (checkCollision && this.waitForCollision && entities.size() > 1);
/*     */     
/* 413 */     boolean instantlyComplete = (this.runTime <= 0.0F && !this.waitForGround && !this.waitForCollision);
/* 414 */     boolean timerFinished = (instantlyComplete || (this.runTime > 0.0F && time >= this.runTime));
/*     */     
/* 416 */     contextState.applyForceState = ApplyForceState.Waiting;
/*     */     
/* 418 */     if (onGround) {
/* 419 */       contextState.applyForceState = ApplyForceState.Ground;
/* 420 */       contextState.state = InteractionState.Finished;
/* 421 */       context.jump(context.getLabel(1));
/* 422 */     } else if (collided) {
/* 423 */       contextState.applyForceState = ApplyForceState.Collision;
/* 424 */       contextState.state = InteractionState.Finished;
/* 425 */       context.jump(context.getLabel(2));
/* 426 */     } else if (timerFinished) {
/* 427 */       contextState.applyForceState = ApplyForceState.Timer;
/* 428 */       contextState.state = InteractionState.Finished;
/* 429 */       context.jump(context.getLabel(0));
/*     */     } else {
/* 431 */       contextState.state = InteractionState.NotFinished;
/*     */     } 
/*     */     
/* 434 */     super.simulateTick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 439 */     Label[] labels = new Label[3];
/*     */     
/* 441 */     for (int i = 0; i < labels.length; i++) {
/* 442 */       labels[i] = builder.createUnresolvedLabel();
/*     */     }
/*     */     
/* 445 */     builder.addOperation((Operation)this, labels);
/*     */     
/* 447 */     Label endLabel = builder.createUnresolvedLabel();
/*     */     
/* 449 */     resolve(builder, this.next, labels[0], endLabel);
/* 450 */     resolve(builder, (this.groundInteraction == null) ? this.next : this.groundInteraction, labels[1], endLabel);
/* 451 */     resolve(builder, (this.collisionInteraction == null) ? this.next : this.collisionInteraction, labels[2], endLabel);
/*     */     
/* 453 */     builder.resolveLabel(endLabel);
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
/*     */   private static void resolve(@Nonnull OperationsBuilder builder, @Nullable String id, @Nonnull Label label, @Nonnull Label endLabel) {
/* 465 */     builder.resolveLabel(label);
/*     */     
/* 467 */     if (id != null) {
/* 468 */       Interaction interaction = Interaction.getInteractionOrUnknown(id);
/* 469 */       interaction.compile(builder);
/*     */     } 
/*     */     
/* 472 */     builder.jump(endLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 477 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 483 */     return (Interaction)new com.hypixel.hytale.protocol.ApplyForceInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 488 */     super.configurePacket(packet);
/* 489 */     com.hypixel.hytale.protocol.ApplyForceInteraction p = (com.hypixel.hytale.protocol.ApplyForceInteraction)packet;
/* 490 */     p.changeVelocityType = this.changeVelocityType;
/* 491 */     p.forces = (AppliedForce[])Arrays.<Force>stream(this.forces).map(Force::toPacket).toArray(x$0 -> new AppliedForce[x$0]);
/* 492 */     p.duration = this.duration;
/* 493 */     p.waitForGround = this.waitForGround;
/* 494 */     p.waitForCollision = this.waitForCollision;
/* 495 */     p.groundCheckDelay = this.groundCheckDelay;
/* 496 */     p.collisionCheckDelay = this.collisionCheckDelay;
/* 497 */     p.velocityConfig = (this.velocityConfig == null) ? null : this.velocityConfig.toPacket();
/* 498 */     if (this.verticalClamp != null) {
/* 499 */       p.verticalClamp = new FloatRange(this.verticalClamp.getInclusiveMin() * 0.017453292F, this.verticalClamp.getInclusiveMax() * 0.017453292F);
/*     */     }
/* 501 */     p.collisionNext = Interaction.getInteractionIdOrUnknown((this.collisionInteraction == null) ? this.next : this.collisionInteraction);
/* 502 */     p.groundNext = Interaction.getInteractionIdOrUnknown((this.groundInteraction == null) ? this.next : this.groundInteraction);
/* 503 */     p.raycastDistance = this.raycastDistance;
/* 504 */     p.raycastHeightOffset = this.raycastHeightOffset;
/* 505 */     p.raycastMode = this.raycastMode;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 511 */     return "ApplyForceInteraction{changeVelocityType=" + String.valueOf(this.changeVelocityType) + ", waitForGround=" + this.waitForGround + "} " + super
/*     */ 
/*     */       
/* 514 */       .toString();
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
/*     */   public static class Force
/*     */     implements NetworkSerializable<AppliedForce>
/*     */   {
/*     */     public static final BuilderCodec<Force> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 551 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Force.class, Force::new).appendInherited(new KeyedCodec("Direction", (Codec)Vector3d.CODEC), (o, i) -> o.direction = i, o -> o.direction, (o, p) -> o.direction = p.direction).documentation("The direction of the force to apply.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("AdjustVertical", (Codec)Codec.BOOLEAN), (o, i) -> o.adjustVertical = i.booleanValue(), o -> Boolean.valueOf(o.adjustVertical), (o, p) -> o.adjustVertical = p.adjustVertical).documentation("Whether the force should be adjusted based on the vertical look of the user.").add()).appendInherited(new KeyedCodec("Force", (Codec)Codec.DOUBLE), (o, i) -> o.force = i.doubleValue(), o -> Double.valueOf(o.force), (o, p) -> o.force = p.force).documentation("The size of the force to apply.").add()).afterDecode(o -> o.direction.normalize())).build();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/* 556 */     private Vector3d direction = Vector3d.UP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean adjustVertical = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 567 */     private double force = 1.0D;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public AppliedForce toPacket() {
/* 572 */       return new AppliedForce(new Vector3f((float)this.direction.x, (float)this.direction.y, (float)this.direction.z), this.adjustVertical, (float)this.force);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ApplyForceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */