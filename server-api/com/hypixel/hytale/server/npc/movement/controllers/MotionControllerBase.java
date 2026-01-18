/*      */ package com.hypixel.hytale.server.npc.movement.controllers;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.shape.Box;
/*      */ import com.hypixel.hytale.math.util.MathUtil;
/*      */ import com.hypixel.hytale.math.vector.Vector3d;
/*      */ import com.hypixel.hytale.math.vector.Vector3f;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.protocol.MovementSettings;
/*      */ import com.hypixel.hytale.protocol.MovementStates;
/*      */ import com.hypixel.hytale.protocol.Rangef;
/*      */ import com.hypixel.hytale.protocol.VelocityThresholdStyle;
/*      */ import com.hypixel.hytale.server.core.asset.modifiers.MovementEffects;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.camera.CameraSettings;
/*      */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*      */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*      */ import com.hypixel.hytale.server.core.modules.collision.BlockCollisionData;
/*      */ import com.hypixel.hytale.server.core.modules.collision.BoxBlockIntersectionEvaluator;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionConfig;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionModuleConfig;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*      */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*      */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*      */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*      */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*      */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*      */ import com.hypixel.hytale.server.core.modules.splitvelocity.SplitVelocity;
/*      */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*      */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*      */ import com.hypixel.hytale.server.npc.movement.MotionKind;
/*      */ import com.hypixel.hytale.server.npc.movement.NavState;
/*      */ import com.hypixel.hytale.server.npc.movement.Steering;
/*      */ import com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerBase;
/*      */ import com.hypixel.hytale.server.npc.role.Role;
/*      */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*      */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.util.List;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public abstract class MotionControllerBase implements MotionController {
/*      */   public static final double FORCE_SCALE = 5.0D;
/*   63 */   protected static final HytaleLogger LOGGER = NPCPlugin.get().getLogger();
/*      */   
/*      */   public static final double BISECT_DIST = 0.05D;
/*      */   
/*      */   public static final double FILTER_COEFFICIENT = 0.7D;
/*      */   public static final double DOT_PRODUCT_EPSILON = 0.001D;
/*      */   public static final double DEFAULT_BLOCK_DRAG = 0.82D;
/*      */   public static final boolean DEBUG_APPLIED_FORCES = false;
/*      */   @Nonnull
/*      */   protected final NPCEntity entity;
/*      */   protected final String type;
/*      */   protected final double epsilonSpeed;
/*      */   protected final float epsilonAngle;
/*      */   protected final double forceVelocityDamping;
/*      */   protected final double maxHorizontalSpeed;
/*      */   protected final double fastMotionThreshold;
/*      */   protected final double fastMotionThresholdRange;
/*      */   protected final float maxHeadRotationSpeed;
/*      */   protected Role role;
/*      */   protected double inertia;
/*      */   protected double knockbackScale;
/*      */   protected double gravity;
/*      */   protected boolean debugModeSteer;
/*      */   protected boolean debugModeMove;
/*      */   protected boolean debugModeCollisions;
/*      */   protected boolean debugModeBlockCollisions;
/*      */   protected boolean debugModeProbeBlockCollisions;
/*      */   protected boolean debugModeValidatePositions;
/*      */   protected boolean debugModeOverlaps;
/*      */   protected boolean debugModeValidateMath;
/*   93 */   protected final Vector3d position = new Vector3d();
/*   94 */   protected final Box collisionBoundingBox = new Box();
/*   95 */   protected final CollisionResult collisionResult = new CollisionResult();
/*   96 */   protected final Vector3d translation = new Vector3d();
/*      */   
/*   98 */   protected final Vector3d bisectValidPosition = new Vector3d();
/*   99 */   protected final Vector3d bisectInvalidPosition = new Vector3d();
/*  100 */   protected final Vector3d lastValidPosition = new Vector3d();
/*      */   
/*  102 */   protected final Vector3d forceVelocity = new Vector3d();
/*  103 */   protected final Vector3d appliedForce = new Vector3d();
/*      */   protected boolean ignoreDamping;
/*  105 */   protected final List<AppliedVelocity> appliedVelocities = (List<AppliedVelocity>)new ObjectArrayList();
/*      */   
/*      */   protected boolean isObstructed;
/*      */   
/*      */   protected NavState navState;
/*      */   
/*      */   protected double throttleDuration;
/*      */   
/*      */   protected double targetDeltaSquared;
/*      */   
/*      */   protected boolean recomputePath;
/*  116 */   protected final Vector3d worldNormal = Vector3d.UP;
/*      */   
/*  118 */   protected final Vector3d worldAntiNormal = Vector3d.DOWN;
/*      */ 
/*      */   
/*  121 */   protected final Vector3d componentSelector = new Vector3d(1.0D, 0.0D, 1.0D);
/*  122 */   protected final Vector3d planarComponentSelector = new Vector3d(1.0D, 0.0D, 1.0D);
/*      */   protected boolean enableTriggers = true;
/*      */   protected boolean enableBlockDamage = true;
/*      */   protected boolean isReceivingBlockDamage;
/*      */   protected boolean isAvoidingBlockDamage = true;
/*      */   protected boolean requiresPreciseMovement;
/*      */   protected boolean requiresDepthProbing;
/*      */   protected boolean havePreciseMovementTarget;
/*      */   @Nonnull
/*  131 */   protected Vector3d preciseMovementTarget = new Vector3d();
/*      */   protected boolean isRelaxedMoveConstraints;
/*      */   protected boolean isBlendingHeading;
/*      */   protected double blendHeading;
/*      */   protected boolean haveBlendHeadingPosition;
/*      */   @Nonnull
/*  137 */   protected Vector3d blendHeadingPosition = new Vector3d();
/*      */   
/*  139 */   protected double blendLevelAtTargetPosition = 0.5D;
/*      */   
/*      */   protected boolean fastMotionKind;
/*      */   
/*      */   protected boolean idleMotionKind;
/*      */   
/*      */   protected boolean horizontalIdleKind;
/*      */   
/*      */   protected double moveSpeed;
/*      */   
/*      */   protected double previousSpeed;
/*      */   protected MotionKind motionKind;
/*      */   protected MotionKind lastMovementStateUpdatedMotionKind;
/*      */   protected MotionKind previousMotionKind;
/*      */   protected double effectHorizontalSpeedMultiplier;
/*      */   protected boolean cachedMovementBlocked;
/*      */   private float yaw;
/*      */   private float pitch;
/*      */   private float roll;
/*  158 */   private final Vector3d beforeTriggerForce = new Vector3d();
/*  159 */   private final Vector3d beforeTriggerPosition = new Vector3d();
/*      */   
/*      */   private boolean processTriggersHasMoved;
/*      */   protected MovementSettings movementSettings;
/*      */   
/*      */   public MotionControllerBase(@Nonnull BuilderSupport builderSupport, @Nonnull BuilderMotionControllerBase builder) {
/*  165 */     this.entity = builderSupport.getEntity();
/*  166 */     this.type = builder.getType();
/*  167 */     this.epsilonSpeed = builder.getEpsilonSpeed();
/*  168 */     this.epsilonAngle = builder.getEpsilonAngle();
/*  169 */     this.forceVelocityDamping = builder.getForceVelocityDamping();
/*  170 */     this.maxHorizontalSpeed = builder.getMaxHorizontalSpeed(builderSupport);
/*  171 */     this.fastMotionThreshold = builder.getFastHorizontalThreshold(builderSupport);
/*  172 */     this.fastMotionThresholdRange = builder.getFastHorizontalThresholdRange();
/*  173 */     this.maxHeadRotationSpeed = builder.getMaxHeadRotationSpeed(builderSupport);
/*  174 */     setInertia(1.0D);
/*  175 */     setKnockbackScale(1.0D);
/*  176 */     setGravity(10.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public Role getRole() {
/*  181 */     return this.role;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRole(Role role) {
/*  186 */     this.role = role;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInertia(double inertia) {
/*  191 */     this.inertia = Math.max(inertia, 1.0E-4D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKnockbackScale(double knockbackScale) {
/*  196 */     this.knockbackScale = Math.max(0.0D, knockbackScale);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateModelParameters(Ref<EntityStore> ref, Model model, @Nonnull Box boundingBox, ComponentAccessor<EntityStore> componentAccessor) {
/*  201 */     Objects.requireNonNull(boundingBox, "updateModelParameters: MotionController needs a bounding box");
/*      */ 
/*      */     
/*  204 */     this.collisionBoundingBox.assign(boundingBox);
/*      */   }
/*      */   
/*      */   protected void readEntityPosition(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  208 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  209 */     assert transformComponent != null;
/*      */     
/*  211 */     Vector3f bodyRotation = transformComponent.getRotation();
/*      */     
/*  213 */     this.position.assign(transformComponent.getPosition());
/*  214 */     this.yaw = bodyRotation.getY();
/*  215 */     this.pitch = bodyRotation.getPitch();
/*  216 */     this.roll = bodyRotation.getRoll();
/*  217 */     adjustReadPosition(ref, componentAccessor);
/*  218 */     postReadPosition(ref, componentAccessor);
/*      */   }
/*      */ 
/*      */   
/*      */   public void postReadPosition(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {}
/*      */ 
/*      */   
/*      */   public void moveEntity(@Nonnull Ref<EntityStore> ref, double dt, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  226 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  227 */     assert transformComponent != null;
/*      */     
/*  229 */     adjustWritePosition(ref, dt, componentAccessor);
/*      */     
/*  231 */     Vector3f bodyRotation = transformComponent.getRotation();
/*  232 */     bodyRotation.setYaw(this.yaw);
/*  233 */     bodyRotation.setPitch(this.pitch);
/*  234 */     bodyRotation.setRoll(this.roll);
/*      */     
/*  236 */     this.entity.moveTo(ref, this.position.x, this.position.y, this.position.z, componentAccessor);
/*      */   }
/*      */   
/*      */   public float getYaw() {
/*  240 */     return this.yaw;
/*      */   }
/*      */   
/*      */   public float getPitch() {
/*  244 */     return this.pitch;
/*      */   }
/*      */   
/*      */   public float getRoll() {
/*  248 */     return this.roll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean touchesWater(boolean defaultValue, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  259 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  260 */     ChunkStore chunkStore = world.getChunkStore();
/*      */ 
/*      */     
/*  263 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(this.position.getX(), this.position.getZ());
/*  264 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*      */     
/*  266 */     if (chunkRef == null || !chunkRef.isValid()) {
/*  267 */       return defaultValue;
/*      */     }
/*      */     
/*  270 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/*  271 */     assert worldChunkComponent != null;
/*      */     
/*  273 */     int blockX = MathUtil.floor(this.position.getX());
/*  274 */     int blockY = MathUtil.floor(this.position.getY() + this.collisionBoundingBox.min.y);
/*  275 */     int blockZ = MathUtil.floor(this.position.getZ());
/*  276 */     int fluidId = worldChunkComponent.getFluidId(blockX, blockY, blockZ);
/*  277 */     return (fluidId != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateMovementState(@Nonnull Ref<EntityStore> ref, @Nonnull MovementStates movementStates, @Nonnull Steering steering, @Nonnull Vector3d velocity, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  284 */     boolean lastFastMotion = movementStates.running;
/*      */ 
/*      */     
/*  287 */     movementStates.climbing = false;
/*  288 */     movementStates.swimJumping = false;
/*      */     
/*  290 */     movementStates.inFluid = touchesWater(movementStates.inFluid, componentAccessor);
/*  291 */     movementStates.onGround = this.role.isOnGround();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     double speed = waypointDistance(Vector3d.ZERO, velocity);
/*      */     
/*  298 */     speed = 0.7D * this.previousSpeed + 0.30000000000000004D * speed;
/*  299 */     this.previousSpeed = speed;
/*      */     
/*  301 */     this.fastMotionKind = isFastMotionKind(speed);
/*  302 */     this.idleMotionKind = steering.getTranslation().equals(Vector3d.ZERO);
/*  303 */     this.horizontalIdleKind = isHorizontalIdle(speed);
/*      */     
/*  305 */     if (this.motionKind != this.lastMovementStateUpdatedMotionKind || lastFastMotion != this.fastMotionKind || movementStates.idle != this.idleMotionKind || movementStates.horizontalIdle != this.horizontalIdleKind) {
/*  306 */       NPCEntity npcComponent; switch (this.motionKind) {
/*      */         case Linear:
/*  308 */           updateFlyingStates(movementStates, this.idleMotionKind, this.fastMotionKind);
/*      */           break;
/*      */         case Exp:
/*  311 */           updateSwimmingStates(movementStates, this.idleMotionKind, this.fastMotionKind, this.horizontalIdleKind);
/*      */           break;
/*      */         case null:
/*  314 */           updateSwimmingStates(movementStates, false, true, false);
/*      */           break;
/*      */         case null:
/*  317 */           updateAscendingStates(ref, movementStates, this.fastMotionKind, this.horizontalIdleKind, componentAccessor);
/*      */           break;
/*      */         case null:
/*  320 */           updateMovingStates(ref, movementStates, this.fastMotionKind, componentAccessor);
/*      */           break;
/*      */         case null:
/*  323 */           npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  324 */           assert npcComponent != null;
/*  325 */           updateDescendingStates(ref, movementStates, this.fastMotionKind, (npcComponent.getHoverHeight() > 0.0D), componentAccessor);
/*      */           break;
/*      */         
/*      */         case null:
/*  329 */           updateDroppingStates(movementStates);
/*      */           break;
/*      */         
/*      */         default:
/*  333 */           npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  334 */           assert npcComponent != null;
/*  335 */           updateStandingStates(movementStates, this.motionKind, (npcComponent.getHoverHeight() > 0.0D));
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  340 */     this.lastMovementStateUpdatedMotionKind = this.motionKind;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFlyingStates(@Nonnull MovementStates movementStates, boolean idle, boolean fastMotionKind) {
/*  346 */     movementStates.flying = true;
/*  347 */     movementStates.idle = idle;
/*  348 */     movementStates.horizontalIdle = false;
/*  349 */     movementStates.walking = !fastMotionKind;
/*  350 */     movementStates.running = fastMotionKind;
/*  351 */     movementStates.falling = false;
/*  352 */     movementStates.swimming = false;
/*  353 */     movementStates.jumping = false;
/*      */   }
/*      */   
/*      */   protected void updateSwimmingStates(@Nonnull MovementStates movementStates, boolean idle, boolean fastMotionKind, boolean horizontalIdleKind) {
/*  357 */     movementStates.flying = false;
/*  358 */     movementStates.idle = idle;
/*  359 */     movementStates.horizontalIdle = horizontalIdleKind;
/*  360 */     movementStates.walking = !fastMotionKind;
/*  361 */     movementStates.running = fastMotionKind;
/*  362 */     movementStates.falling = false;
/*  363 */     movementStates.swimming = true;
/*  364 */     movementStates.jumping = false;
/*      */   }
/*      */   
/*      */   protected static void updateMovingStates(@Nonnull Ref<EntityStore> ref, @Nonnull MovementStates movementStates, boolean fastMotionKind, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  368 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  369 */     assert npcComponent != null;
/*      */     
/*  371 */     movementStates.flying = (npcComponent.getHoverHeight() > 0.0D);
/*  372 */     movementStates.idle = false;
/*  373 */     movementStates.horizontalIdle = false;
/*  374 */     movementStates.falling = false;
/*  375 */     movementStates.walking = !fastMotionKind;
/*  376 */     movementStates.running = fastMotionKind;
/*  377 */     movementStates.swimming = false;
/*  378 */     movementStates.jumping = false;
/*      */   }
/*      */   
/*      */   protected void updateAscendingStates(@Nonnull Ref<EntityStore> ref, @Nonnull MovementStates movementStates, boolean fastMotionKind, boolean horizontalIdleKind, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  382 */     updateMovingStates(ref, movementStates, fastMotionKind, componentAccessor);
/*      */   }
/*      */   
/*      */   protected void updateDescendingStates(@Nonnull Ref<EntityStore> ref, @Nonnull MovementStates movementStates, boolean fastMotionKind, boolean hovering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  386 */     updateMovingStates(ref, movementStates, fastMotionKind, componentAccessor);
/*      */   }
/*      */   
/*      */   protected void updateDroppingStates(@Nonnull MovementStates movementStates) {
/*  390 */     movementStates.falling = true;
/*      */   }
/*      */   
/*      */   protected void updateStandingStates(@Nonnull MovementStates movementStates, @Nonnull MotionKind motionKind, boolean hovering) {
/*  394 */     movementStates.flying = hovering;
/*  395 */     movementStates.idle = true;
/*  396 */     movementStates.horizontalIdle = true;
/*  397 */     movementStates.walking = false;
/*  398 */     movementStates.running = false;
/*  399 */     movementStates.falling = false;
/*  400 */     movementStates.swimming = false;
/*  401 */     movementStates.jumping = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public double steer(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Steering bodySteering, @Nonnull Steering headSteering, double interval, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  406 */     readEntityPosition(ref, componentAccessor);
/*      */     
/*  408 */     if (this.debugModeSteer) {
/*  409 */       double dx = this.position.x;
/*  410 */       double dz = this.position.z;
/*  411 */       double st = steer0(ref, role, bodySteering, headSteering, interval, componentAccessor);
/*  412 */       double t = interval - st;
/*      */       
/*  414 */       dx = this.position.x - dx;
/*  415 */       dz = this.position.z - dz;
/*      */       
/*  417 */       double l = Math.sqrt(dx * dx + dz * dz);
/*  418 */       double v = (t > 0.0D) ? (l / t) : 0.0D;
/*      */       
/*  420 */       LOGGER.at(Level.INFO).log("==  Steer %s  = t =%.4f dt=%.4f h =%.4f l =%.4f v =%.4f motion=%s", getType(), Double.valueOf(interval), Double.valueOf(t), Float.valueOf(57.295776F * this.yaw), Double.valueOf(l), Double.valueOf(v), role.getSteeringMotionName());
/*      */       
/*  422 */       return st;
/*      */     } 
/*  424 */     return steer0(ref, role, bodySteering, headSteering, interval, componentAccessor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double steer0(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Steering bodySteering, @Nonnull Steering headSteering, double interval, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  434 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*      */     
/*  436 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  437 */     assert npcComponent != null;
/*      */     
/*  439 */     this.effectHorizontalSpeedMultiplier = npcComponent.getCurrentHorizontalSpeedMultiplier(ref, componentAccessor);
/*      */ 
/*      */     
/*  442 */     setAvoidingBlockDamage((this.isAvoidingBlockDamage && !this.isReceivingBlockDamage));
/*      */     
/*  444 */     this.translation.assign(0.0D);
/*  445 */     this.cachedMovementBlocked = isMovementBlocked(ref, componentAccessor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     computeMove(ref, role, bodySteering, interval, this.translation, componentAccessor);
/*  458 */     if (this.debugModeValidateMath)
/*      */     {
/*  460 */       if (!NPCPhysicsMath.isValid(this.translation)) throw new IllegalArgumentException(String.valueOf(this.translation)); 
/*      */     }
/*  462 */     if (this.translation.squaredLength() > 1000000.0D) {
/*      */       
/*  464 */       if (this.debugModeValidateMath) {
/*  465 */         LOGGER.at(Level.WARNING).log("NPC with role %s has abnormal high speed! (Distance=%s, MotionController=%s)", role.getRoleName(), Double.valueOf(this.translation.length()), this.type);
/*      */       }
/*  467 */       this.translation.assign(Vector3d.ZERO);
/*      */     } 
/*  469 */     executeMove(ref, role, interval, this.translation, componentAccessor);
/*      */     
/*  471 */     postExecuteMove();
/*  472 */     clearRequirePreciseMovement();
/*  473 */     clearRequireDepthProbing();
/*  474 */     clearBlendHeading();
/*  475 */     setAvoidingBlockDamage(!this.isReceivingBlockDamage);
/*  476 */     setRelaxedMoveConstraints(false);
/*      */     
/*  478 */     float maxBodyRotation = (float)(interval * getCurrentMaxBodyRotationSpeed() * bodySteering.getRelativeTurnSpeed());
/*  479 */     float maxHeadRotation = (float)(interval * this.maxHeadRotationSpeed * headSteering.getRelativeTurnSpeed() * this.effectHorizontalSpeedMultiplier);
/*  480 */     calculateYaw(ref, bodySteering, headSteering, maxHeadRotation, maxBodyRotation, componentAccessor);
/*  481 */     calculatePitch(ref, bodySteering, headSteering, maxHeadRotation, componentAccessor);
/*  482 */     calculateRoll(bodySteering, headSteering);
/*      */     
/*  484 */     moveEntity(ref, interval, componentAccessor);
/*      */     
/*  486 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  487 */     assert headRotationComponent != null;
/*      */     
/*  489 */     Vector3f headRotation = headRotationComponent.getRotation();
/*  490 */     headRotation.setYaw(headSteering.getYaw());
/*  491 */     headRotation.setPitch(headSteering.getPitch());
/*  492 */     headRotation.setRoll(headSteering.getRoll());
/*      */     
/*  494 */     if (!this.forceVelocity.equals(Vector3d.ZERO) && !this.ignoreDamping) {
/*  495 */       double movementThresholdSquared = 1.0000000000000002E-10D;
/*  496 */       if (this.forceVelocity.squaredLength() >= movementThresholdSquared) {
/*  497 */         dampForceVelocity(this.forceVelocity, this.forceVelocityDamping, interval, componentAccessor);
/*      */       } else {
/*  499 */         this.forceVelocity.assign(Vector3d.ZERO);
/*      */       } 
/*      */     } 
/*      */     
/*  503 */     double clientTps = 60.0D;
/*  504 */     int serverTps = world.getTps();
/*  505 */     double rate = clientTps / serverTps;
/*  506 */     boolean dampenY = shouldDampenAppliedVelocitiesY();
/*  507 */     boolean useGroundResistance = (shouldAlwaysUseGroundResistance() || onGround());
/*      */     
/*  509 */     for (int i = 0; i < this.appliedVelocities.size(); i++) {
/*  510 */       float min, max; AppliedVelocity entry = this.appliedVelocities.get(i);
/*      */ 
/*      */       
/*  513 */       if (useGroundResistance) {
/*  514 */         min = entry.config.getGroundResistance();
/*  515 */         max = entry.config.getGroundResistanceMax();
/*      */       } else {
/*  517 */         min = entry.config.getAirResistance();
/*  518 */         max = entry.config.getAirResistanceMax();
/*      */       } 
/*      */       
/*  521 */       float resistance = min;
/*  522 */       if (max >= 0.0F) {
/*  523 */         float len; switch (entry.config.getStyle()) { default: throw new MatchException(null, null);
/*      */           case Linear:
/*  525 */             len = (float)entry.velocity.length();
/*  526 */             if (len < entry.config.getThreshold()) {
/*  527 */               float mul = len / entry.config.getThreshold();
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case Exp:
/*  534 */             len = (float)entry.velocity.squaredLength();
/*  535 */             if (len < entry.config.getThreshold() * entry.config.getThreshold()) {
/*  536 */               float mul = len / entry.config.getThreshold() * entry.config.getThreshold();
/*      */             } }
/*      */         
/*  539 */         resistance = min;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  545 */       double resistanceScale = Math.pow(resistance, rate);
/*  546 */       entry.velocity.x *= resistanceScale;
/*  547 */       entry.velocity.z *= resistanceScale;
/*  548 */       if (dampenY) {
/*  549 */         entry.velocity.y *= resistanceScale;
/*      */       }
/*      */     } 
/*      */     
/*  553 */     this.appliedVelocities.removeIf(v -> (v.velocity.squaredLength() < 0.001D));
/*      */     
/*  555 */     return interval;
/*      */   }
/*      */   
/*      */   protected boolean shouldDampenAppliedVelocitiesY() {
/*  559 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean shouldAlwaysUseGroundResistance() {
/*  563 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateYaw(@Nonnull Ref<EntityStore> ref, @Nonnull Steering bodySteering, @Nonnull Steering headSteering, float maxHeadRotation, float maxBodyRotation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  572 */     if (bodySteering.hasYaw()) {
/*  573 */       this.yaw = bodySteering.getYaw();
/*  574 */     } else if (NPCPhysicsMath.dotProduct(this.translation.x, 0.0D, this.translation.z) > 0.001D) {
/*  575 */       this.yaw = PhysicsMath.headingFromDirection(this.translation.x, this.translation.z);
/*      */     } 
/*      */     
/*  578 */     boolean hasHeadSteering = headSteering.hasYaw();
/*  579 */     if (!hasHeadSteering)
/*      */     {
/*  581 */       headSteering.setYaw(this.yaw);
/*      */     }
/*      */ 
/*      */     
/*  585 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  586 */     assert headRotationComponent != null;
/*      */     
/*  588 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/*  589 */     assert modelComponent != null;
/*      */     
/*  591 */     Vector3f headRotation = headRotationComponent.getRotation();
/*      */ 
/*      */     
/*  594 */     float currentYaw = headRotation.getYaw();
/*  595 */     float targetYaw = headSteering.getYaw();
/*  596 */     float turnAngle = MathUtil.clamp(NPCPhysicsMath.turnAngle(currentYaw, targetYaw), -maxHeadRotation, maxHeadRotation);
/*  597 */     headSteering.setYaw(PhysicsMath.normalizeTurnAngle(currentYaw + turnAngle));
/*      */     
/*  599 */     if (hasHeadSteering) {
/*      */ 
/*      */       
/*  602 */       float yawMin, yawMax, yawOffset = MathUtil.wrapAngle(headSteering.getYaw() - this.yaw);
/*      */ 
/*      */       
/*  605 */       CameraSettings headRotationRestrictions = modelComponent.getModel().getCamera();
/*  606 */       if (headRotationRestrictions != null && headRotationRestrictions.getYaw() != null && headRotationRestrictions.getYaw().getAngleRange() != null) {
/*      */         
/*  608 */         Rangef yawRange = headRotationRestrictions.getYaw().getAngleRange();
/*  609 */         yawMin = yawRange.min * 0.017453292F;
/*  610 */         yawMax = yawRange.max * 0.017453292F;
/*      */       }
/*      */       else {
/*      */         
/*  614 */         yawMin = -0.7853982F;
/*  615 */         yawMax = 0.7853982F;
/*      */       } 
/*  617 */       if (yawOffset > yawMax) {
/*      */         
/*  619 */         float initialBodyYaw = this.yaw;
/*  620 */         if (!bodySteering.hasYaw())
/*      */         {
/*  622 */           this.yaw = blendBodyYaw(ref, yawOffset, maxBodyRotation, componentAccessor);
/*      */         }
/*  624 */         headSteering.setYaw(MathUtil.wrapAngle(initialBodyYaw + yawMax));
/*  625 */       } else if (yawOffset < yawMin) {
/*      */         
/*  627 */         float initialBodyYaw = this.yaw;
/*  628 */         if (!bodySteering.hasYaw())
/*      */         {
/*  630 */           this.yaw = blendBodyYaw(ref, yawOffset, maxBodyRotation, componentAccessor);
/*      */         }
/*  632 */         headSteering.setYaw(MathUtil.wrapAngle(initialBodyYaw + yawMin));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float blendBodyYaw(@Nonnull Ref<EntityStore> ref, float yawOffset, float maxBodyRotation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  641 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  642 */     assert transformComponent != null;
/*      */     
/*  644 */     Vector3f bodyRotation = transformComponent.getRotation();
/*  645 */     float currentBodyYaw = bodyRotation.getYaw();
/*  646 */     float targetBodyYaw = MathUtil.wrapAngle(this.yaw + yawOffset);
/*  647 */     float bodyTurnAngle = MathUtil.clamp(NPCPhysicsMath.turnAngle(currentBodyYaw, targetBodyYaw), -maxBodyRotation, maxBodyRotation);
/*  648 */     return MathUtil.wrapAngle(this.yaw + bodyTurnAngle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculatePitch(@Nonnull Ref<EntityStore> ref, @Nonnull Steering bodySteering, @Nonnull Steering headSteering, float maxHeadRotation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  656 */     if (bodySteering.hasPitch()) {
/*  657 */       this.pitch = bodySteering.getPitch();
/*  658 */     } else if (NPCPhysicsMath.dotProduct(this.translation.x, this.translation.y, this.translation.z) > 0.001D) {
/*  659 */       this.pitch = PhysicsMath.pitchFromDirection(this.translation.x, this.translation.y, this.translation.z);
/*      */     } 
/*      */     
/*  662 */     boolean hasHeadSteering = headSteering.hasPitch();
/*  663 */     if (!hasHeadSteering)
/*      */     {
/*  665 */       headSteering.setPitch(this.pitch);
/*      */     }
/*      */ 
/*      */     
/*  669 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  670 */     assert headRotationComponent != null;
/*      */     
/*  672 */     Vector3f headRotation = headRotationComponent.getRotation();
/*      */ 
/*      */     
/*  675 */     float currentPitch = headRotation.getPitch();
/*  676 */     float targetPitch = headSteering.getPitch();
/*  677 */     float turnAngle = MathUtil.clamp(NPCPhysicsMath.turnAngle(currentPitch, targetPitch), -maxHeadRotation, maxHeadRotation);
/*  678 */     headSteering.setPitch(PhysicsMath.normalizeTurnAngle(currentPitch + turnAngle));
/*      */     
/*  680 */     if (hasHeadSteering) {
/*  681 */       float pitchMin, pitchMax; ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/*  682 */       assert modelComponent != null;
/*      */ 
/*      */       
/*  685 */       float bodyPitch = this.pitch;
/*  686 */       float pitchOffset = MathUtil.wrapAngle(headSteering.getPitch() - bodyPitch);
/*      */ 
/*      */       
/*  689 */       CameraSettings headRotationRestrictions = modelComponent.getModel().getCamera();
/*  690 */       if (headRotationRestrictions != null && headRotationRestrictions.getPitch() != null && headRotationRestrictions.getPitch().getAngleRange() != null) {
/*  691 */         Rangef pitchRange = headRotationRestrictions.getPitch().getAngleRange();
/*  692 */         pitchMin = pitchRange.min * 0.017453292F;
/*  693 */         pitchMax = pitchRange.max * 0.017453292F;
/*      */       } else {
/*      */         
/*  696 */         pitchMin = -0.7853982F;
/*  697 */         pitchMax = 0.7853982F;
/*      */       } 
/*  699 */       if (pitchOffset > pitchMax) {
/*  700 */         headSteering.setPitch(MathUtil.wrapAngle(bodyPitch + pitchMax));
/*  701 */       } else if (pitchOffset < pitchMin) {
/*  702 */         headSteering.setPitch(MathUtil.wrapAngle(bodyPitch + pitchMin));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void calculateRoll(@Nonnull Steering bodySteering, @Nonnull Steering headSteering) {
/*  708 */     if (bodySteering.hasRoll()) {
/*  709 */       this.roll = bodySteering.getRoll();
/*      */     }
/*      */     
/*  712 */     if (!headSteering.hasRoll()) {
/*  713 */       headSteering.setRoll(this.roll);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void dampForceVelocity(@Nonnull Vector3d forceVelocity, double forceVelocityDamping, double interval, ComponentAccessor<EntityStore> componentAccessor) {
/*  718 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*      */     
/*  720 */     double drag = 0.0D;
/*      */     
/*  722 */     if (this.motionKind != MotionKind.FLYING) {
/*  723 */       if (!onGround() && this.motionKind != MotionKind.SWIMMING && this.motionKind != MotionKind.SWIMMING_TURNING) {
/*  724 */         double horizontalSpeed = Math.sqrt(forceVelocity.x * forceVelocity.x + forceVelocity.z * forceVelocity.z);
/*  725 */         drag = convertToNewRange(horizontalSpeed, this.movementSettings.airDragMinSpeed, this.movementSettings.airDragMaxSpeed, this.movementSettings.airDragMin, this.movementSettings.airDragMax);
/*      */       } else {
/*  727 */         drag = 0.82D;
/*      */       } 
/*      */     }
/*      */     
/*  731 */     double clientTps = 60.0D;
/*  732 */     int serverTps = world.getTps();
/*  733 */     double rate = 60.0D / serverTps;
/*  734 */     drag = Math.pow(drag, rate);
/*      */     
/*  736 */     forceVelocity.x *= drag;
/*  737 */     forceVelocity.z *= drag;
/*      */ 
/*      */     
/*  740 */     float velocityEpsilon = 0.1F;
/*      */     
/*  742 */     if (Math.abs(forceVelocity.x) <= velocityEpsilon) forceVelocity.x = 0.0D; 
/*  743 */     if (Math.abs(forceVelocity.y) <= velocityEpsilon) forceVelocity.y = 0.0D; 
/*  744 */     if (Math.abs(forceVelocity.z) <= velocityEpsilon) forceVelocity.z = 0.0D; 
/*      */   }
/*      */   
/*      */   private static double convertToNewRange(double value, double oldMinRange, double oldMaxRange, double newMinRange, double newMaxRange) {
/*  748 */     if (newMinRange == newMaxRange || oldMinRange == oldMaxRange) return newMinRange;
/*      */     
/*  750 */     double newValue = (value - oldMinRange) * (newMaxRange - newMinRange) / (oldMaxRange - oldMinRange) + newMinRange;
/*  751 */     return MathUtil.clamp(newValue, Math.min(newMinRange, newMaxRange), Math.max(newMinRange, newMaxRange));
/*      */   }
/*      */ 
/*      */   
/*      */   public double probeMove(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull Vector3d direction, @Nonnull ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  756 */     probeMoveData.setPosition(position).setDirection(direction);
/*  757 */     return probeMove(ref, probeMoveData, componentAccessor);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postExecuteMove() {}
/*      */ 
/*      */   
/*      */   protected void adjustReadPosition(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {}
/*      */ 
/*      */   
/*      */   protected void adjustWritePosition(Ref<EntityStore> ref, double dt, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {}
/*      */ 
/*      */   
/*      */   public boolean isInProgress() {
/*  771 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isObstructed() {
/*  776 */     return this.isObstructed;
/*      */   }
/*      */ 
/*      */   
/*      */   public NavState getNavState() {
/*  781 */     return this.navState;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getThrottleDuration() {
/*  786 */     return this.throttleDuration;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getTargetDeltaSquared() {
/*  791 */     return this.targetDeltaSquared;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNavState(NavState navState, double throttleDuration, double targetDeltaSquared) {
/*  796 */     this.navState = navState;
/*  797 */     this.throttleDuration = throttleDuration;
/*  798 */     this.targetDeltaSquared = targetDeltaSquared;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isForceRecomputePath() {
/*  803 */     return this.recomputePath;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setForceRecomputePath(boolean recomputePath) {
/*  808 */     this.recomputePath = recomputePath;
/*      */   }
/*      */ 
/*      */   
/*      */   public void beforeInstructionSensorsAndActions(double physicsTickDuration) {
/*  813 */     this.recomputePath = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void beforeInstructionMotion(double physicsTickDuration) {
/*  818 */     resetNavState();
/*      */   }
/*      */   
/*      */   public boolean isHorizontalIdle(double speed) {
/*  822 */     return (speed == 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canAct(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  827 */     return (isAlive(ref, componentAccessor) && this.role.couldBreatheCached() && this.forceVelocity.equals(Vector3d.ZERO) && this.appliedVelocities.isEmpty());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMovementBlocked(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  832 */     InteractionManager interactionManager = (InteractionManager)componentAccessor.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/*  833 */     if (interactionManager != null) {
/*  834 */       Boolean movementBlocked = (Boolean)interactionManager.forEachInteraction((chain, interaction, val) -> { if (val.booleanValue()) return Boolean.TRUE;  MovementEffects movementEffects = interaction.getEffects().getMovementEffects(); return (movementEffects != null) ? Boolean.valueOf(movementEffects.isDisableAll()) : Boolean.FALSE; }Boolean.FALSE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  844 */       return movementBlocked.booleanValue();
/*      */     } 
/*  846 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> double bisect(@Nonnull Vector3d validPosition, @Nonnull Vector3d invalidPosition, @Nonnull T t, @Nonnull BiPredicate<T, Vector3d> validate, @Nonnull Vector3d result) {
/*  878 */     return bisect(validPosition, invalidPosition, t, validate, 0.05D, result);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> double bisect(@Nonnull Vector3d validPosition, @Nonnull Vector3d invalidPosition, @Nonnull T t, @Nonnull BiPredicate<T, Vector3d> validate, double maxDistance, @Nonnull Vector3d result) {
/*  897 */     double validDistance = 0.0D;
/*  898 */     double invalidDistance = 1.0D;
/*      */ 
/*      */     
/*  901 */     this.bisectValidPosition.assign(validPosition);
/*  902 */     this.bisectInvalidPosition.assign(invalidPosition);
/*  903 */     maxDistance *= maxDistance;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     double validWeight = 0.1D;
/*  909 */     double invalidWeight = 0.9D;
/*      */     
/*  911 */     while (this.bisectValidPosition.distanceSquaredTo(this.bisectInvalidPosition) > maxDistance) {
/*      */       
/*  913 */       double distance = validWeight * validDistance + invalidWeight * invalidDistance;
/*  914 */       result.x = validWeight * this.bisectValidPosition.x + invalidWeight * this.bisectInvalidPosition.x;
/*  915 */       result.y = validWeight * this.bisectValidPosition.y + invalidWeight * this.bisectInvalidPosition.y;
/*  916 */       result.z = validWeight * this.bisectValidPosition.z + invalidWeight * this.bisectInvalidPosition.z;
/*  917 */       if (validate.test(t, result)) {
/*      */         
/*  919 */         validDistance = distance;
/*  920 */         this.bisectValidPosition.assign(result);
/*      */         continue;
/*      */       } 
/*  923 */       invalidDistance = distance;
/*  924 */       this.bisectInvalidPosition.assign(result);
/*      */       
/*  926 */       validWeight = 0.5D;
/*  927 */       invalidWeight = 0.5D;
/*      */     } 
/*      */     
/*  930 */     result.assign(this.bisectValidPosition);
/*  931 */     return validDistance;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3d getForce() {
/*  937 */     return this.forceVelocity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addForce(@Nonnull Vector3d force, VelocityConfig velocityConfig) {
/*  942 */     double scale = this.knockbackScale;
/*  943 */     if (!SplitVelocity.SHOULD_MODIFY_VELOCITY && velocityConfig != null) {
/*  944 */       this.appliedVelocities.add(new AppliedVelocity(new Vector3d(force.x * scale, force.y * scale, force.z * scale), velocityConfig));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  951 */     double horzMul = 0.18000000000000005D * this.movementSettings.velocityResistance;
/*  952 */     this.forceVelocity.add(force.x * scale * horzMul, force.y * scale, force.z * scale * horzMul);
/*  953 */     this.appliedForce.assign(this.forceVelocity);
/*  954 */     this.ignoreDamping = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void forceVelocity(@Nonnull Vector3d velocity, @Nullable VelocityConfig velocityConfig, boolean ignoreDamping) {
/*  959 */     if (!SplitVelocity.SHOULD_MODIFY_VELOCITY && velocityConfig != null) {
/*  960 */       this.appliedVelocities.clear();
/*  961 */       this.appliedVelocities.add(new AppliedVelocity(velocity
/*  962 */             .clone(), velocityConfig));
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  967 */     this.forceVelocity.assign(velocity);
/*  968 */     this.ignoreDamping = ignoreDamping;
/*      */   }
/*      */   
/*      */   public void clearForce() {
/*  972 */     this.forceVelocity.assign(Vector3d.ZERO);
/*      */   }
/*      */   
/*      */   protected void dumpCollisionResults() {
/*  976 */     String slideString = "";
/*      */     
/*  978 */     if (this.collisionResult.isSliding) {
/*  979 */       slideString = String.format("SLIDE: start/end=%f/%f", new Object[] { Double.valueOf(this.collisionResult.slideStart), Double.valueOf(this.collisionResult.slideEnd) });
/*      */     }
/*  981 */     LOGGER.at(Level.INFO).log("CollRes: pos=%s yaw=%f count=%d %s", Vector3d.formatShortString(this.position), Float.valueOf(57.295776F * this.yaw), Integer.valueOf(this.collisionResult.getBlockCollisionCount()), slideString);
/*  982 */     if (this.collisionResult.getBlockCollisionCount() > 0) {
/*  983 */       for (int i = 0; i < this.collisionResult.getBlockCollisionCount(); i++) {
/*  984 */         String rotation; BlockCollisionData cd = this.collisionResult.getBlockCollision(i);
/*  985 */         String materialName = (cd.blockMaterial != null) ? cd.blockMaterial.name() : "none";
/*  986 */         String typeName = (cd.blockType != null) ? cd.blockType.getId() : "none";
/*  987 */         String hitboxName = (cd.blockType != null) ? cd.blockType.getHitboxType() : "none";
/*      */         
/*  989 */         if (cd.blockType != null) {
/*  990 */           RotationTuple blockRotation = RotationTuple.get(cd.rotation);
/*  991 */           rotation = String.valueOf(blockRotation.yaw()) + " " + String.valueOf(blockRotation.yaw());
/*      */         } else {
/*  993 */           rotation = "none";
/*      */         } 
/*  995 */         LOGGER.at(Level.INFO).log("   COLL: blk=%s/%s/%s start=%f norm=%s pos=%s mat=%s block=%s hitbox=%s rot=%s", 
/*  996 */             Integer.valueOf(cd.x), Integer.valueOf(cd.y), Integer.valueOf(cd.z), Double.valueOf(cd.collisionStart), Vector3d.formatShortString(cd.collisionNormal), Vector3d.formatShortString(cd.collisionPoint), materialName, typeName, hitboxName, rotation);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void setEnableTriggers(boolean enableTriggers) {
/* 1002 */     this.enableTriggers = enableTriggers;
/*      */   }
/*      */   
/*      */   public void setEnableBlockDamage(boolean enableBlockDamage) {
/* 1006 */     this.enableBlockDamage = enableBlockDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean willReceiveBlockDamage() {
/* 1011 */     return this.isReceivingBlockDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAvoidingBlockDamage(boolean avoid) {
/* 1016 */     this.isAvoidingBlockDamage = avoid;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAvoidingBlockDamage() {
/* 1021 */     return this.isAvoidingBlockDamage;
/*      */   }
/*      */   
/*      */   public void processTriggers(@Nonnull Ref<EntityStore> ref, @Nonnull CollisionResult collisionResult, double t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1025 */     this.processTriggersHasMoved = false;
/* 1026 */     this.isReceivingBlockDamage = false;
/*      */     
/* 1028 */     if (!this.enableTriggers && !this.enableBlockDamage) {
/*      */       return;
/*      */     }
/*      */     
/* 1032 */     collisionResult.pruneTriggerBlocks(t);
/*      */     
/* 1034 */     int count = collisionResult.getTriggerBlocks().size();
/* 1035 */     if (count == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1039 */     if (this.enableTriggers) {
/* 1040 */       this.beforeTriggerForce.assign(getForce());
/* 1041 */       this.beforeTriggerPosition.assign(this.position);
/*      */     } 
/* 1043 */     moveEntity(ref, 0.0D, componentAccessor);
/*      */ 
/*      */     
/* 1046 */     InteractionManager interactionManagerComponent = (InteractionManager)componentAccessor.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/* 1047 */     assert interactionManagerComponent != null;
/*      */     
/* 1049 */     int damageToEntity = collisionResult.defaultTriggerBlocksProcessing(interactionManagerComponent, (Entity)this.entity, ref, this.enableTriggers, componentAccessor);
/*      */     
/* 1051 */     if (this.enableBlockDamage && damageToEntity > 0) {
/* 1052 */       Damage damage = new Damage(Damage.NULL_SOURCE, DamageCause.ENVIRONMENT, damageToEntity);
/* 1053 */       DamageSystems.executeDamage(ref, componentAccessor, damage);
/*      */       
/* 1055 */       this.isReceivingBlockDamage = true;
/*      */     } 
/*      */     
/* 1058 */     readEntityPosition(ref, componentAccessor);
/* 1059 */     if (this.enableTriggers) {
/* 1060 */       this.processTriggersHasMoved = (!this.beforeTriggerForce.equals(getForce()) || !this.beforeTriggerPosition.equals(this.position));
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean isDebugMode(RoleDebugFlags mode) {
/* 1065 */     return (getRole() != null && getRole().getDebugSupport().getDebugFlags().contains(mode));
/*      */   }
/*      */   
/*      */   public boolean isProcessTriggersHasMoved() {
/* 1069 */     return this.processTriggersHasMoved;
/*      */   }
/*      */   
/*      */   protected boolean isAlive(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1073 */     return !componentAccessor.getArchetype(ref).contains(DeathComponent.getComponentType());
/*      */   }
/*      */ 
/*      */   
/*      */   public void activate() {
/* 1078 */     this.debugModeSteer = isDebugMode(RoleDebugFlags.MotionControllerSteer);
/* 1079 */     this.debugModeMove = isDebugMode(RoleDebugFlags.MotionControllerMove);
/* 1080 */     this.debugModeCollisions = isDebugMode(RoleDebugFlags.Collisions);
/* 1081 */     this.debugModeBlockCollisions = isDebugMode(RoleDebugFlags.BlockCollisions);
/* 1082 */     this.debugModeProbeBlockCollisions = isDebugMode(RoleDebugFlags.ProbeBlockCollisions);
/* 1083 */     this.debugModeValidatePositions = isDebugMode(RoleDebugFlags.ValidatePositions);
/* 1084 */     this.debugModeOverlaps = isDebugMode(RoleDebugFlags.Overlaps);
/* 1085 */     this.debugModeValidateMath = isDebugMode(RoleDebugFlags.ValidateMath);
/* 1086 */     resetObstructedFlags();
/* 1087 */     resetNavState();
/*      */   }
/*      */   
/*      */   public void resetNavState() {
/* 1091 */     this.navState = NavState.AT_GOAL;
/* 1092 */     this.throttleDuration = 0.0D;
/* 1093 */     this.targetDeltaSquared = 0.0D;
/*      */   }
/*      */   
/*      */   public void resetObstructedFlags() {
/* 1097 */     this.isObstructed = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void deactivate() {}
/*      */ 
/*      */   
/*      */   public double getEpsilonSpeed() {
/* 1106 */     return this.epsilonSpeed;
/*      */   }
/*      */   
/*      */   public float getEpsilonAngle() {
/* 1110 */     return this.epsilonAngle;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3d getComponentSelector() {
/* 1116 */     return this.componentSelector;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Vector3d getPlanarComponentSelector() {
/* 1122 */     return this.planarComponentSelector;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setComponentSelector(@Nonnull Vector3d componentSelector) {
/* 1127 */     this.componentSelector.assign(componentSelector);
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector3d getWorldNormal() {
/* 1132 */     return this.worldNormal;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector3d getWorldAntiNormal() {
/* 1137 */     return this.worldAntiNormal;
/*      */   }
/*      */ 
/*      */   
/*      */   public double waypointDistance(@Nonnull Vector3d p, @Nonnull Vector3d q) {
/* 1142 */     return Math.sqrt(waypointDistanceSquared(p, q));
/*      */   }
/*      */ 
/*      */   
/*      */   public double waypointDistanceSquared(@Nonnull Vector3d p, @Nonnull Vector3d q) {
/* 1147 */     double dx = (p.x - q.x) * (getComponentSelector()).x;
/* 1148 */     double dy = (p.y - q.y) * (getComponentSelector()).y;
/* 1149 */     double dz = (p.z - q.z) * (getComponentSelector()).z;
/* 1150 */     return dx * dx + dy * dy + dz * dz;
/*      */   }
/*      */ 
/*      */   
/*      */   public double waypointDistance(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d p, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1155 */     return Math.sqrt(waypointDistanceSquared(ref, p, componentAccessor));
/*      */   }
/*      */ 
/*      */   
/*      */   public double waypointDistanceSquared(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d p, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1160 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 1161 */     assert transformComponent != null;
/*      */     
/* 1163 */     Vector3d position = transformComponent.getPosition();
/* 1164 */     double dx = (p.x - position.getX()) * (getComponentSelector()).x;
/* 1165 */     double dy = (p.y - position.getY()) * (getComponentSelector()).y;
/* 1166 */     double dz = (p.z - position.getZ()) * (getComponentSelector()).z;
/* 1167 */     return dx * dx + dy * dy + dz * dz;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isValidPosition(@Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1172 */     return isValidPosition(position, this.collisionResult, componentAccessor);
/*      */   }
/*      */   
/*      */   public boolean isValidPosition(@Nonnull Vector3d position, @Nonnull CollisionResult collisionResult, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1176 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*      */     
/* 1178 */     CollisionModule module = CollisionModule.get();
/* 1179 */     CollisionModuleConfig config = module.getConfig();
/* 1180 */     boolean saveDebugModeOverlaps = config.isDumpInvalidBlocks();
/* 1181 */     config.setDumpInvalidBlocks(this.debugModeOverlaps);
/*      */     
/* 1183 */     boolean isValid = (module.validatePosition(world, this.collisionBoundingBox, position, getInvalidOverlapMaterials(), null, (_this, collisionCode, collision, collisionConfig) -> (collisionConfig.blockId != Integer.MIN_VALUE), collisionResult) != -1);
/*      */     
/* 1185 */     config.setDumpInvalidBlocks(saveDebugModeOverlaps);
/* 1186 */     return isValid;
/*      */   }
/*      */   
/*      */   public int getInvalidOverlapMaterials() {
/* 1190 */     return 4;
/*      */   }
/*      */   
/*      */   protected void saveMotionKind() {
/* 1194 */     this.previousMotionKind = getMotionKind();
/*      */   }
/*      */   
/*      */   protected boolean switchedToMotionKind(MotionKind motionKind) {
/* 1198 */     return (getMotionKind() == motionKind && this.previousMotionKind != motionKind);
/*      */   }
/*      */   
/*      */   public MotionKind getMotionKind() {
/* 1202 */     return this.motionKind;
/*      */   }
/*      */   
/*      */   public void setMotionKind(MotionKind motionKind) {
/* 1206 */     this.motionKind = motionKind;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getGravity() {
/* 1211 */     return this.gravity;
/*      */   }
/*      */   
/*      */   public void setGravity(double gravity) {
/* 1215 */     this.gravity = gravity;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean translateToAccessiblePosition(Vector3d position, Box boundingBox, double minYValue, double maxYValue, ComponentAccessor<EntityStore> componentAccessor) {
/* 1220 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean standingOnBlockOfType(int blockSet) {
/* 1225 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void requirePreciseMovement(@Nullable Vector3d positionHint) {
/* 1230 */     this.requiresPreciseMovement = true;
/* 1231 */     this.havePreciseMovementTarget = (positionHint != null);
/* 1232 */     if (this.havePreciseMovementTarget) this.preciseMovementTarget.assign(positionHint); 
/*      */   }
/*      */   
/*      */   public void clearRequirePreciseMovement() {
/* 1236 */     this.requiresPreciseMovement = false;
/* 1237 */     this.havePreciseMovementTarget = false;
/*      */   }
/*      */   
/*      */   public boolean isRequiresPreciseMovement() {
/* 1241 */     return this.requiresPreciseMovement;
/*      */   }
/*      */ 
/*      */   
/*      */   public void requireDepthProbing() {
/* 1246 */     this.requiresDepthProbing = true;
/*      */   }
/*      */   
/*      */   public void clearRequireDepthProbing() {
/* 1250 */     this.requiresDepthProbing = false;
/*      */   }
/*      */   
/*      */   public boolean isRequiresDepthProbing() {
/* 1254 */     return this.requiresDepthProbing;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableHeadingBlending(double heading, @Nullable Vector3d targetPosition, double blendLevel) {
/* 1259 */     this.isBlendingHeading = true;
/* 1260 */     this.blendHeading = heading;
/* 1261 */     this.haveBlendHeadingPosition = (targetPosition != null);
/* 1262 */     if (this.haveBlendHeadingPosition) this.blendHeadingPosition.assign(targetPosition); 
/* 1263 */     this.blendLevelAtTargetPosition = blendLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableHeadingBlending() {
/* 1268 */     enableHeadingBlending(Double.NaN, (Vector3d)null, 0.0D);
/*      */   }
/*      */   
/*      */   public void clearBlendHeading() {
/* 1272 */     this.isBlendingHeading = false;
/* 1273 */     this.haveBlendHeadingPosition = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRelaxedMoveConstraints(boolean relax) {
/* 1278 */     this.isRelaxedMoveConstraints = relax;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRelaxedMoveConstraints() {
/* 1283 */     return this.isRelaxedMoveConstraints;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePhysicsValues(PhysicsValues values) {
/* 1288 */     this.movementSettings = MovementManager.MASTER_DEFAULT.apply(values, GameMode.Adventure);
/*      */   }
/*      */   protected abstract boolean isFastMotionKind(double paramDouble);
/*      */   protected abstract double computeMove(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, Steering paramSteering, double paramDouble, Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*      */   protected abstract double executeMove(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, double paramDouble, Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*      */   
/*      */   protected static class AppliedVelocity { protected final Vector3d velocity;
/*      */     
/*      */     public AppliedVelocity(Vector3d velocity, VelocityConfig config) {
/* 1297 */       this.velocity = velocity;
/* 1298 */       this.config = config;
/*      */     }
/*      */     
/*      */     protected final VelocityConfig config;
/*      */     protected boolean canClear; }
/*      */ 
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\controllers\MotionControllerBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */