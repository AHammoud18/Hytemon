/*     */ package com.hypixel.hytale.server.npc.movement.controllers;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BlockCollisionData;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.movement.MotionKind;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerBase;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerFly;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import com.hypixel.hytale.server.npc.util.PositionProbeAir;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class MotionControllerFly
/*     */   extends MotionControllerBase {
/*     */   public static final String TYPE = "Fly";
/*     */   public static final double DAMPING_FACTOR = 20.0D;
/*     */   public static final int COLLISION_MATERIALS_PASSIVE = 4;
/*     */   public static final int COLLISION_MATERIALS_ACTIVE = 6;
/*     */   protected final double minAirSpeed;
/*     */   protected final double maxClimbSpeed;
/*     */   protected final double maxSinkSpeed;
/*     */   protected final double maxFallSpeed;
/*     */   protected final double maxSinkSpeedFluid;
/*     */   protected final float maxClimbAngle;
/*     */   protected final float maxSinkAngle;
/*     */   protected final double acceleration;
/*     */   protected final double deceleration;
/*  47 */   protected final double sinkRatio = 0.5D;
/*     */   
/*     */   protected final double desiredAltitudeWeight;
/*     */   
/*     */   protected final float maxTurnSpeed;
/*     */   protected final float maxRollAngle;
/*     */   protected final float maxRollSpeed;
/*     */   protected final float rollDamping;
/*     */   protected final double fastFlyThreshold;
/*     */   protected final double minHeightOverGround;
/*     */   protected final double maxHeightOverGround;
/*     */   protected final boolean autoLevel;
/*     */   protected final double sinMaxClimbAngle;
/*     */   protected final double sinMaxSinkAngle;
/*  61 */   protected final MotionController.VerticalRange verticalRange = new MotionController.VerticalRange();
/*  62 */   protected final PositionProbeAir moveProbe = new PositionProbeAir();
/*  63 */   protected final PositionProbeAir probeMoveProbe = new PositionProbeAir();
/*  64 */   protected int lastVerticalPositionX = Integer.MIN_VALUE;
/*  65 */   protected int lastVerticalPositionZ = Integer.MIN_VALUE;
/*     */   
/*  67 */   protected final Vector3d lastVelocity = new Vector3d();
/*     */   
/*     */   protected double lastSpeed;
/*     */   
/*     */   protected float lastRoll;
/*     */   protected double currentRelativeSpeed;
/*     */   protected double minSpeedAfterForceSquared;
/*     */   @Nullable
/*     */   protected double[] desiredAltitudeOverride;
/*     */   
/*     */   public MotionControllerFly(@Nonnull BuilderSupport builderSupport, @Nonnull BuilderMotionControllerFly builder) {
/*  78 */     super(builderSupport, (BuilderMotionControllerBase)builder);
/*  79 */     setGravity(builder.getGravity());
/*  80 */     this.componentSelector.assign(1.0D, 1.0D, 1.0D);
/*  81 */     this.minAirSpeed = builder.getMinAirSpeed();
/*  82 */     this.maxClimbSpeed = builder.getMaxClimbSpeed();
/*  83 */     this.maxSinkSpeed = builder.getMaxSinkSpeed();
/*  84 */     this.maxFallSpeed = builder.getMaxFallSpeed();
/*  85 */     this.maxSinkSpeedFluid = builder.getMaxSinkSpeedFluid();
/*  86 */     this.maxClimbAngle = builder.getMaxClimbAngle();
/*  87 */     this.sinMaxClimbAngle = TrigMathUtil.sin(this.maxClimbAngle);
/*  88 */     this.maxSinkAngle = builder.getMaxSinkAngle();
/*  89 */     this.sinMaxSinkAngle = -TrigMathUtil.sin(this.maxSinkAngle);
/*  90 */     this.acceleration = builder.getAcceleration();
/*  91 */     this.deceleration = builder.getDeceleration();
/*  92 */     this.maxTurnSpeed = builder.getMaxTurnSpeed();
/*  93 */     this.maxRollAngle = builder.getMaxRollAngle();
/*  94 */     this.minHeightOverGround = builder.getMinHeightOverGround(builderSupport);
/*  95 */     this.maxHeightOverGround = builder.getMaxHeightOverGround(builderSupport);
/*  96 */     this.maxRollSpeed = builder.getMaxRollSpeed();
/*  97 */     this.rollDamping = builder.getRollDamping();
/*  98 */     this.fastFlyThreshold = builder.getFastFlyThreshold();
/*  99 */     this.autoLevel = builder.isAutoLevel();
/* 100 */     this.desiredAltitudeWeight = builder.getDesiredAltitudeWeight();
/* 101 */     this.minSpeedAfterForceSquared = MathUtil.minValue(this.maxHorizontalSpeed, this.maxSinkSpeed, this.maxClimbSpeed);
/* 102 */     this.minSpeedAfterForceSquared *= this.minSpeedAfterForceSquared;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getType() {
/* 108 */     return "Fly";
/*     */   }
/*     */ 
/*     */   
/*     */   protected double computeMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Steering steering, double dt, @Nonnull Vector3d translation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 113 */     saveMotionKind();
/* 114 */     setMotionKind(inWater() ? MotionKind.MOVING : MotionKind.FLYING);
/* 115 */     this.moveProbe.probePosition(ref, this.collisionBoundingBox, this.position, this.collisionResult, componentAccessor);
/* 116 */     this.currentRelativeSpeed = steering.getSpeed();
/*     */     
/* 118 */     if (!isAlive(ref, componentAccessor)) {
/* 119 */       this.forceVelocity.assign(Vector3d.ZERO);
/* 120 */       this.appliedVelocities.clear();
/*     */     } 
/*     */     
/* 123 */     double maxFallSpeed = this.moveProbe.isInWater() ? this.maxSinkSpeedFluid : this.maxFallSpeed;
/* 124 */     boolean onGround = onGround();
/*     */ 
/*     */     
/* 127 */     if (!this.forceVelocity.equals(Vector3d.ZERO) || !this.appliedVelocities.isEmpty()) {
/* 128 */       steering.setYaw(getYaw());
/* 129 */       steering.setPitch(getPitch());
/* 130 */       steering.setRoll(getRoll());
/*     */       
/* 132 */       if (!isObstructed()) {
/* 133 */         translation.assign(this.forceVelocity);
/*     */         
/* 135 */         for (int i = 0; i < this.appliedVelocities.size(); i++) {
/* 136 */           MotionControllerBase.AppliedVelocity entry = this.appliedVelocities.get(i);
/*     */           
/* 138 */           if (entry.velocity.y + this.forceVelocity.y <= 0.0D || entry.velocity.y < 0.0D) entry.canClear = true; 
/* 139 */           if (onGround && entry.canClear) entry.velocity.y = 0.0D;
/*     */           
/* 141 */           translation.add(entry.velocity);
/*     */         } 
/*     */       } else {
/* 144 */         translation.assign(Vector3d.ZERO);
/* 145 */         this.appliedVelocities.clear();
/* 146 */         this.forceVelocity.assign(Vector3d.ZERO);
/*     */       } 
/*     */       
/* 149 */       if (!onGround) translation.y = NPCPhysicsMath.accelerateDrag(translation.y, -this.gravity, dt, maxFallSpeed);
/*     */       
/* 151 */       this.lastVelocity.assign(translation);
/* 152 */       this.lastSpeed = this.lastVelocity.length();
/*     */       
/* 154 */       translation.scale(dt);
/*     */       
/* 156 */       if (this.debugModeValidateMath && !NPCPhysicsMath.isValid(translation)) throw new IllegalArgumentException(String.valueOf(translation)); 
/* 157 */       return dt;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     if (NPCPhysicsMath.near(this.lastVelocity, Vector3d.ZERO)) {
/* 162 */       PhysicsMath.vectorFromAngles(getYaw(), getPitch(), this.lastVelocity);
/* 163 */       this.lastSpeed = 0.0D;
/*     */     } 
/*     */     
/* 166 */     if (canAct(ref, componentAccessor)) {
/* 167 */       float expYaw; translation.assign(steering.getTranslation());
/*     */       
/* 169 */       double steeringSpeed = steering.hasTranslation() ? translation.length() : 0.0D;
/*     */ 
/*     */       
/* 172 */       float yaw = PhysicsMath.normalizeAngle(getYaw());
/* 173 */       float pitch = PhysicsMath.normalizeTurnAngle(getPitch());
/* 174 */       double dirX = translation.x;
/* 175 */       double dirZ = translation.z;
/*     */ 
/*     */ 
/*     */       
/* 179 */       double dotXZ = dirX * dirX + dirZ * dirZ;
/* 180 */       if (dotXZ >= 1.0E-12D) {
/* 181 */         expYaw = PhysicsMath.headingFromDirection(dirX, dirZ);
/* 182 */         expPitch = TrigMathUtil.atan2(translation.y, Math.sqrt(dotXZ));
/*     */       } else {
/* 184 */         expYaw = steering.hasYaw() ? steering.getYaw() : yaw;
/* 185 */         expPitch = steering.hasPitch() ? steering.getPitch() : (this.autoLevel ? 0.0F : pitch);
/*     */       } 
/*     */       
/* 188 */       steering.clearYaw();
/* 189 */       steering.clearPitch();
/*     */       
/* 191 */       float expPitch = MathUtil.clamp(expPitch, -this.maxSinkAngle, this.maxClimbAngle);
/*     */       
/* 193 */       float turnYaw = NPCPhysicsMath.turnAngle(yaw, expYaw);
/* 194 */       float turnPitch = NPCPhysicsMath.turnAngle(pitch, expPitch);
/* 195 */       float maxRotationAngle = (float)(getCurrentMaxBodyRotationSpeed() * dt);
/*     */       
/* 197 */       turnYaw = NPCPhysicsMath.clampRotation(turnYaw, maxRotationAngle);
/* 198 */       turnPitch = NPCPhysicsMath.clampRotation(turnPitch, maxRotationAngle);
/*     */       
/* 200 */       float newYaw = PhysicsMath.normalizeAngle(yaw + turnYaw);
/* 201 */       float newPitch = PhysicsMath.normalizeTurnAngle(pitch + turnPitch);
/*     */ 
/*     */       
/* 204 */       double speedLimit = computeMaxSpeedFromPitch(pitch);
/*     */       
/* 206 */       steeringSpeed *= speedLimit;
/*     */ 
/*     */       
/* 209 */       double minSpeed = Math.max(this.minAirSpeed, this.lastSpeed - this.deceleration * dt);
/* 210 */       double maxSpeed = this.lastSpeed + this.acceleration * dt;
/*     */       
/* 212 */       steeringSpeed = (maxSpeed < minSpeed) ? minSpeed : MathUtil.clamp(steeringSpeed, minSpeed, maxSpeed);
/*     */       
/* 214 */       PhysicsMath.vectorFromAngles(newYaw, newPitch, translation);
/* 215 */       translation.normalize();
/*     */ 
/*     */ 
/*     */       
/* 219 */       double mX = this.lastVelocity.z;
/*     */       
/* 221 */       double mZ = -this.lastVelocity.x;
/* 222 */       double mL = Math.sqrt(mX * mX + mZ * mZ);
/* 223 */       float rollTurnCosine = (float)(NPCPhysicsMath.dotProduct(mX, 0.0D, mZ, translation.x, translation.y, translation.z) / mL);
/*     */       
/* 225 */       float maxRollTurnAngle = (float)(this.maxTurnSpeed * dt);
/* 226 */       float maxRollTurnCosine = TrigMathUtil.sin(maxRollTurnAngle);
/* 227 */       float rollTurnStrength = rollTurnCosine / maxRollTurnCosine;
/*     */       
/* 229 */       double speedFactor = steeringSpeed / speedLimit;
/*     */       
/* 231 */       float desiredRoll = this.maxRollAngle * MathUtil.clamp(rollTurnStrength, -1.0F, 1.0F) * MathUtil.clamp((float)speedFactor, 0.0F, 1.0F);
/* 232 */       float dampedRoll = MathUtil.clamp(this.rollDamping * this.lastRoll + (1.0F - this.rollDamping) * desiredRoll, -this.maxRollAngle, this.maxRollAngle);
/*     */       
/* 234 */       float deltaRoll = (float)(this.maxRollSpeed * dt);
/* 235 */       float constrainedRoll = MathUtil.clamp(dampedRoll, this.lastRoll - deltaRoll, this.lastRoll + deltaRoll);
/*     */       
/* 237 */       this.lastRoll = constrainedRoll;
/*     */       
/* 239 */       steering.setYaw(newYaw);
/* 240 */       steering.setPitch(newPitch);
/* 241 */       steering.setRoll(constrainedRoll);
/*     */       
/* 243 */       if (steeringSpeed == 0.0D) {
/* 244 */         translation.assign(Vector3d.ZERO);
/*     */       } else {
/* 246 */         translation.scale(steeringSpeed * this.effectHorizontalSpeedMultiplier);
/*     */       } 
/*     */       
/* 249 */       this.lastVelocity.assign(translation);
/* 250 */       this.lastSpeed = steeringSpeed;
/*     */ 
/*     */       
/* 253 */       translation.scale(dt);
/*     */       
/* 255 */       if (this.debugModeValidateMath && !NPCPhysicsMath.isValid(translation)) throw new IllegalArgumentException(String.valueOf(translation));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 260 */       steering.setYaw(getYaw());
/* 261 */       steering.setPitch(getPitch());
/* 262 */       steering.setRoll(getRoll());
/*     */       
/* 264 */       if (onGround) {
/* 265 */         setMotionKind(MotionKind.STANDING);
/* 266 */         this.lastVelocity.assign(Vector3d.ZERO);
/* 267 */         this.lastSpeed = 0.0D;
/* 268 */         return dt;
/*     */       } 
/* 270 */       setMotionKind(MotionKind.DROPPING);
/*     */ 
/*     */ 
/*     */       
/* 274 */       translation.y = NPCPhysicsMath.gravityDrag(this.lastVelocity.y, this.gravity, dt, maxFallSpeed);
/*     */ 
/*     */       
/* 277 */       double diffSpeed = maxFallSpeed - translation.y;
/* 278 */       if (diffSpeed <= 0.0D || isObstructed()) {
/* 279 */         translation.x = 0.0D;
/* 280 */         translation.z = 0.0D;
/*     */       } else {
/*     */         
/* 283 */         double scale = translation.x * translation.x + translation.z * translation.z;
/* 284 */         if (diffSpeed * diffSpeed < scale) {
/* 285 */           scale = Math.sqrt(scale / diffSpeed);
/* 286 */           this.lastVelocity.x *= scale;
/* 287 */           this.lastVelocity.z *= scale;
/*     */         } else {
/*     */           
/* 290 */           translation.x = this.lastVelocity.x;
/* 291 */           translation.z = this.lastVelocity.z;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 296 */       this.lastVelocity.assign(translation);
/* 297 */       this.lastSpeed = this.lastVelocity.length();
/*     */ 
/*     */       
/* 300 */       translation.scale(dt);
/*     */       
/* 302 */       if (this.debugModeValidateMath && !NPCPhysicsMath.isValid(translation)) throw new IllegalArgumentException(String.valueOf(translation)); 
/*     */     } 
/* 304 */     if (this.lastSpeed > 1.0E-6D && isAlive(ref, componentAccessor)) {
/* 305 */       setDirectionFromTranslation(steering, translation);
/*     */     }
/* 307 */     return dt;
/*     */   }
/*     */   
/*     */   private void setDirectionFromTranslation(@Nonnull Steering steering, @Nonnull Vector3d translation) {
/* 311 */     if (!steering.hasYaw()) {
/* 312 */       if (translation.x * translation.x + translation.z * translation.z > 1.0E-6D) {
/* 313 */         steering.setYaw(PhysicsMath.headingFromDirection(translation.x, translation.z));
/*     */       } else {
/* 315 */         steering.setYaw(getYaw());
/*     */       } 
/*     */     }
/* 318 */     if (!steering.hasPitch()) {
/* 319 */       steering.setPitch(PhysicsMath.pitchFromDirection(translation.x, translation.y, translation.z));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double probeMove(@Nonnull Ref<EntityStore> ref, @Nonnull ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 325 */     return probeMoveData.probeDirection.length() * doMove(ref, probeMoveData.probePosition, probeMoveData.probeDirection, this.probeMoveProbe, probeMoveData, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFastMotionKind(double speed) {
/* 331 */     return (this.lastVelocity.y < -1.0E-6D || (this.currentRelativeSpeed > this.fastFlyThreshold && this.lastVelocity.y <= 1.0E-6D));
/*     */   }
/*     */   
/*     */   public MotionController.VerticalRange getDesiredVerticalRange(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     double minHeightOverGround, maxHeightOverGround;
/* 336 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 337 */     assert transformComponent != null;
/*     */     
/* 339 */     Vector3d position = transformComponent.getPosition();
/* 340 */     int ix = MathUtil.floor(position.getX());
/* 341 */     int iz = MathUtil.floor(position.getZ());
/*     */     
/* 343 */     if (ix == this.lastVerticalPositionX && iz == this.lastVerticalPositionZ) {
/* 344 */       return this.verticalRange;
/*     */     }
/* 346 */     this.lastVerticalPositionX = ix;
/* 347 */     this.lastVerticalPositionZ = iz;
/*     */     
/* 349 */     double y = position.getY();
/*     */ 
/*     */     
/* 352 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 353 */     ChunkStore chunkStore = world.getChunkStore();
/* 354 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(ix, iz);
/* 355 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */     
/* 357 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 358 */       this.verticalRange.assign(y, y, y);
/* 359 */       return this.verticalRange;
/*     */     } 
/*     */     
/* 362 */     Store<ChunkStore> chunkStoreAccessor = chunkStore.getStore();
/* 363 */     ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStoreAccessor.getComponent(chunkRef, ChunkColumn.getComponentType());
/* 364 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreAccessor.getComponent(chunkRef, BlockChunk.getComponentType());
/*     */     
/* 366 */     if (chunkColumnComponent == null || blockChunkComponent == null) {
/* 367 */       this.verticalRange.assign(y, y, y);
/* 368 */       return this.verticalRange;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 373 */     if (this.desiredAltitudeOverride != null) {
/* 374 */       minHeightOverGround = this.desiredAltitudeOverride[0];
/* 375 */       maxHeightOverGround = this.desiredAltitudeOverride[1];
/*     */     } else {
/* 377 */       minHeightOverGround = this.minHeightOverGround;
/* 378 */       maxHeightOverGround = this.maxHeightOverGround;
/*     */     } 
/*     */     
/* 381 */     int iy = MathUtil.floor(y);
/* 382 */     double below = WorldUtil.findFarthestEmptySpaceBelow((ComponentAccessor)chunkStoreAccessor, chunkColumnComponent, blockChunkComponent, ix, iy, iz, iy) + this.collisionBoundingBox.min.y;
/* 383 */     double above = WorldUtil.findFarthestEmptySpaceAbove((ComponentAccessor)chunkStoreAccessor, chunkColumnComponent, blockChunkComponent, ix, iy, iz, iy) - this.collisionBoundingBox.max.y + 1.0D;
/* 384 */     double minY = below + minHeightOverGround;
/* 385 */     double maxY = Math.min(below + maxHeightOverGround, above);
/*     */     
/* 387 */     if (minY > maxY) {
/* 388 */       minY = y;
/* 389 */       maxY = y;
/*     */     } 
/*     */ 
/*     */     
/* 393 */     this.verticalRange.assign(y, minY, maxY);
/* 394 */     return this.verticalRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWanderVerticalMovementRatio() {
/* 399 */     return 0.5D;
/*     */   }
/*     */   protected double doMove(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull Vector3d translation, @Nonnull PositionProbeAir moveProbe, @Nullable ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     double distanceFactor;
/* 403 */     boolean probeOnly = (probeMoveData != null);
/* 404 */     boolean saveSegments = (probeOnly && probeMoveData.startProbing());
/* 405 */     boolean canAct = (probeOnly || canAct(ref, componentAccessor));
/* 406 */     String debugPrefix = probeOnly ? "Probe" : "Move";
/*     */     
/* 408 */     if (this.debugModeMove) {
/* 409 */       LOGGER.at(Level.INFO).log("%s - Fly: Execute pos=%s vel=%s onGround=%s blocked=%s ", debugPrefix, Vector3d.formatShortString(position), Vector3d.formatShortString(translation), Boolean.valueOf(onGround()), Boolean.valueOf(this.isObstructed));
/*     */     }
/* 411 */     if (this.debugModeValidatePositions && !isValidPosition(position, this.collisionResult, componentAccessor)) {
/* 412 */       throw new IllegalStateException("Invalid position");
/*     */     }
/* 414 */     if (saveSegments) probeMoveData.addStartSegment(position, false);
/*     */     
/* 416 */     if (!probeOnly) {
/* 417 */       this.isObstructed = false;
/* 418 */       if (this.debugModeBlockCollisions) {
/* 419 */         this.collisionResult.setLogger(LOGGER);
/*     */       }
/*     */     } 
/* 422 */     this.collisionResult.setCollisionByMaterial(canAct ? 6 : 4);
/* 423 */     CollisionModule.get(); CollisionModule.findCollisions(this.collisionBoundingBox, position, translation, this.collisionResult, componentAccessor);
/* 424 */     if (this.debugModeBlockCollisions) {
/* 425 */       this.collisionResult.setLogger(null);
/*     */     }
/* 427 */     if (this.debugModeCollisions) {
/* 428 */       dumpCollisionResults();
/*     */     }
/*     */     
/* 431 */     BlockCollisionData collision = this.collisionResult.getFirstBlockCollision();
/*     */ 
/*     */     
/* 434 */     this.lastValidPosition.assign(position);
/* 435 */     if (collision == null) {
/* 436 */       position.add(translation);
/* 437 */       distanceFactor = 1.0D;
/* 438 */       if (this.debugModeMove) {
/* 439 */         LOGGER.at(Level.INFO).log("%s - Fly: No collision pos=%s vel=%s onGround=%s blocked=%s ", debugPrefix, Vector3d.formatShortString(position), Vector3d.formatShortString(translation), Boolean.valueOf(onGround()), Boolean.valueOf(this.isObstructed));
/*     */       }
/* 441 */       if (this.debugModeValidatePositions && !isValidPosition(position, this.collisionResult, componentAccessor)) {
/* 442 */         throw new IllegalStateException("Invalid position");
/*     */       }
/*     */     } else {
/* 445 */       position.assign(collision.collisionPoint);
/* 446 */       distanceFactor = collision.collisionStart;
/* 447 */       if (!probeOnly) this.isObstructed = true; 
/* 448 */       if (this.debugModeMove) {
/* 449 */         LOGGER.at(Level.INFO).log("%s - Fly: Collision pos=%s collStart=%s vel=%s onGround=%s blocked=%s ", debugPrefix, Vector3d.formatShortString(position), Double.valueOf(distanceFactor), Vector3d.formatShortString(translation), Boolean.valueOf(onGround()), Boolean.valueOf(this.isObstructed));
/*     */       }
/* 451 */       if (this.debugModeValidatePositions && !isValidPosition(position, this.collisionResult, componentAccessor)) {
/* 452 */         throw new IllegalStateException("Invalid position");
/*     */       }
/*     */     } 
/*     */     
/* 456 */     if (!moveProbe.probePosition(ref, this.collisionBoundingBox, position, this.collisionResult, componentAccessor)) {
/* 457 */       double adjust = bisect(this.lastValidPosition, position, this, (_this, newPosition) -> _this.moveProbe.probePosition(ref, _this.collisionBoundingBox, newPosition, _this.collisionResult, componentAccessor), position);
/* 458 */       distanceFactor *= adjust;
/* 459 */       if (this.debugModeMove) {
/* 460 */         LOGGER.at(Level.INFO).log("%s - Fly: Bisect step pos=%s distanceFactor=%s adjust=%s", debugPrefix, Vector3d.formatShortString(position), Double.valueOf(distanceFactor), Double.valueOf(adjust));
/*     */       }
/*     */     } 
/*     */     
/* 464 */     if (!probeOnly) {
/* 465 */       processTriggers(ref, this.collisionResult, distanceFactor, componentAccessor);
/* 466 */     } else if (saveSegments) {
/* 467 */       double distance = waypointDistance(probeMoveData.initialPosition, position);
/* 468 */       if (collision == null) {
/* 469 */         probeMoveData.addMoveSegment(position, false, distance);
/* 470 */       } else if (getWorldNormal().equals(collision.collisionNormal)) {
/* 471 */         probeMoveData.addHitGroundSegment(position, distance, collision.collisionNormal, collision.blockId);
/*     */       } else {
/* 473 */         probeMoveData.addHitWallSegment(position, false, distance, collision.collisionNormal, collision.blockId);
/*     */       } 
/* 475 */       probeMoveData.addEndSegment(position, true, distance);
/*     */     } 
/* 477 */     return distanceFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double executeMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Vector3d translation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 482 */     double scale = doMove(ref, this.position, translation, this.moveProbe, (ProbeMoveData)null, componentAccessor);
/*     */ 
/*     */     
/* 485 */     if (scale < 1.0D) {
/* 486 */       dt *= scale;
/* 487 */       this.lastSpeed *= scale;
/* 488 */       this.lastVelocity.scale(scale);
/*     */     } 
/* 490 */     return dt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrainRotations(Role role, TransformComponent transform) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCurrentMaxBodyRotationSpeed() {
/* 500 */     return this.maxTurnSpeed * this.effectHorizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dampForceVelocity(@Nonnull Vector3d forceVelocity, double forceVelocityDamping, double interval, ComponentAccessor<EntityStore> componentAccessor) {
/* 505 */     if (forceVelocity.squaredLength() < this.minSpeedAfterForceSquared) {
/* 506 */       forceVelocity.assign(Vector3d.ZERO);
/*     */       return;
/*     */     } 
/* 509 */     NPCPhysicsMath.deccelerateToStop(forceVelocity, getDampingDeceleration(), interval);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDampenAppliedVelocitiesY() {
/* 514 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldAlwaysUseGroundResistance() {
/* 519 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawned() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAct(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 529 */     return (super.canAct(ref, componentAccessor) && this.moveProbe.isInAir() && this.effectHorizontalSpeedMultiplier != 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inAir() {
/* 534 */     return !onGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onGround() {
/* 539 */     return this.moveProbe.isOnGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inWater() {
/* 544 */     return this.moveProbe.isInWater();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentSpeed() {
/* 549 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentTurnRadius() {
/* 554 */     return this.lastSpeed / this.maxTurnSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxClimbAngle() {
/* 559 */     return this.maxClimbAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxSinkAngle() {
/* 564 */     return this.maxSinkAngle;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaximumSpeed() {
/* 569 */     return MathUtil.maxValue(this.maxClimbSpeed, this.maxHorizontalSpeed, this.maxSinkSpeed) * this.effectHorizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is2D() {
/* 574 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRestAtPlace() {
/* 579 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDesiredAltitudeWeight() {
/* 584 */     return this.desiredAltitudeWeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeightOverGround() {
/* 589 */     return this.probeMoveProbe.getHeightOverGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHorizontalIdle(double speed) {
/* 594 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean estimateVelocity(Steering steering, @Nonnull Vector3d velocityOut) {
/* 599 */     velocityOut.assign(Vector3d.ZERO);
/* 600 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearOverrides() {
/* 605 */     this.desiredAltitudeOverride = null;
/*     */   }
/*     */   
/*     */   public void setDesiredAltitudeOverride(double[] desiredAltitudeOverride) {
/* 609 */     this.desiredAltitudeOverride = desiredAltitudeOverride;
/*     */   }
/*     */   
/*     */   public void takeOff(@Nonnull Ref<EntityStore> ref, double speed, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 613 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 614 */     assert transformComponent != null;
/*     */     
/* 616 */     PhysicsMath.vectorFromAngles(transformComponent.getRotation().getYaw(), 0.7853982F, this.lastVelocity);
/* 617 */     this.lastSpeed = speed;
/*     */   }
/*     */   
/*     */   public double getMinSpeedAfterForceSquared() {
/* 621 */     return this.minSpeedAfterForceSquared;
/*     */   }
/*     */   
/*     */   public double getDampingDeceleration() {
/* 625 */     return this.forceVelocityDamping * 20.0D;
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
/*     */   protected double computeMaxSpeedFromPitch(double pitch) {
/* 637 */     double sinePitch = TrigMathUtil.sin(pitch);
/* 638 */     double cosinePitch = Math.sqrt(1.0D - sinePitch * sinePitch);
/* 639 */     double c = cosinePitch * this.maxHorizontalSpeed;
/* 640 */     double s = sinePitch * ((sinePitch > 0.0D) ? this.maxClimbSpeed : this.maxSinkSpeed);
/*     */     
/* 642 */     return Math.sqrt(c * c + s * s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\controllers\MotionControllerFly.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */