/*     */ package com.hypixel.hytale.server.npc.movement.controllers;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BlockCollisionData;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.movement.MotionKind;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerBase;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerDive;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import com.hypixel.hytale.server.npc.util.PositionProbeWater;
/*     */ import java.util.EnumSet;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotionControllerDive
/*     */   extends MotionControllerBase
/*     */ {
/*     */   public static final String TYPE = "Dive";
/*     */   public static final int COLLISION_MATERIALS_ACTIVE = 5;
/*     */   public static final int COLLISION_MATERIALS_PASSIVE = 4;
/*     */   public static final double DEFAULT_SWIM_DEPTH = 0.5D;
/*  40 */   protected static double DAMPING_FACTOR = 20.0D;
/*     */   
/*     */   protected final double maxVerticalSpeed;
/*     */   
/*     */   protected final double acceleration;
/*     */   
/*     */   protected final double maxFallSpeed;
/*     */   protected final double maxSinkSpeed;
/*     */   protected final double maxRotationSpeed;
/*     */   protected final float maxMoveTurnAngle;
/*     */   protected final double minDiveDepth;
/*     */   protected final double maxDiveDepth;
/*     */   protected final double minWaterDepth;
/*     */   protected final double maxWaterDepth;
/*     */   protected final double minDepthAboveGround;
/*     */   protected final double minDepthBelowSurface;
/*     */   protected final double relativeSwimDepth;
/*     */   protected final double sinkRatio;
/*     */   protected final double fastDiveThreshold;
/*     */   protected final double minSpeedAfterForceSquared;
/*     */   protected final double desiredDepthWeight;
/*     */   protected double swimDepth;
/*     */   protected double climbSpeed;
/*     */   protected double currentRelativeSpeed;
/*     */   protected boolean collisionWithSolid;
/*  65 */   protected final MotionController.VerticalRange verticalRange = new MotionController.VerticalRange();
/*  66 */   protected final PositionProbeWater moveProbe = new PositionProbeWater();
/*  67 */   protected final PositionProbeWater probeMoveProbe = new PositionProbeWater();
/*  68 */   protected final Vector3d tempPosition = new Vector3d();
/*  69 */   protected final Vector3d tempDirection = new Vector3d();
/*     */   
/*  71 */   private static final EnumSet<MotionKind> VALID_MOTIONS = EnumSet.of(MotionKind.SWIMMING, MotionKind.SWIMMING_TURNING, MotionKind.MOVING);
/*     */   
/*     */   public MotionControllerDive(@Nonnull BuilderSupport builderSupport, @Nonnull BuilderMotionControllerDive builder) {
/*  74 */     super(builderSupport, (BuilderMotionControllerBase)builder);
/*  75 */     setGravity(builder.getGravity());
/*  76 */     this.componentSelector.assign(1.0D, 1.0D, 1.0D);
/*  77 */     this.maxVerticalSpeed = builder.getMaxVerticalSpeed();
/*  78 */     this.acceleration = builder.getAcceleration();
/*  79 */     this.maxSinkSpeed = builder.getMaxSinkSpeed();
/*  80 */     this.maxFallSpeed = builder.getMaxFallSpeed();
/*  81 */     this.maxRotationSpeed = builder.getMaxRotationSpeed();
/*  82 */     this.maxMoveTurnAngle = builder.getMaxMoveTurnAngle();
/*  83 */     this.minDiveDepth = builder.getMinDiveDepth();
/*  84 */     this.maxDiveDepth = builder.getMaxDiveDepth();
/*  85 */     this.minWaterDepth = builder.getMinWaterDepth();
/*  86 */     this.maxWaterDepth = builder.getMaxWaterDepth();
/*  87 */     this.minDepthAboveGround = builder.getMinDepthAboveGround();
/*  88 */     this.minDepthBelowSurface = builder.getMinDepthBelowSurface();
/*  89 */     this.relativeSwimDepth = builder.getSwimDepth();
/*  90 */     this.sinkRatio = builder.getSinkRatio();
/*  91 */     this.fastDiveThreshold = builder.getFastDiveThreshold();
/*  92 */     this.desiredDepthWeight = builder.getDesiredDepthWeight();
/*  93 */     double minSpeedAfterForceSquared = MathUtil.minValue(this.maxVerticalSpeed, this.maxSinkSpeed, this.maxFallSpeed);
/*  94 */     this.minSpeedAfterForceSquared = minSpeedAfterForceSquared * minSpeedAfterForceSquared;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate() {
/*  99 */     super.activate();
/* 100 */     this.collisionResult.disableSlides();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWanderVerticalMovementRatio() {
/* 105 */     return this.sinkRatio;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MotionController.VerticalRange getDesiredVerticalRange(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 111 */     double waterLevel = this.moveProbe.getWaterLevel() + 1.0D;
/* 112 */     double maxY = waterLevel - this.swimDepth - this.minDepthBelowSurface;
/*     */     
/* 114 */     double groundY = this.moveProbe.getGroundLevel() + this.minDepthAboveGround;
/* 115 */     double lowY = waterLevel - this.maxDiveDepth;
/* 116 */     double minY = Math.max(groundY, lowY);
/*     */     
/* 118 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 119 */     assert transformComponent != null;
/* 120 */     double y = (transformComponent.getPosition()).y;
/*     */     
/* 122 */     if (onGround()) {
/* 123 */       minY = y;
/*     */     }
/* 125 */     if (this.moveProbe.isTouchCeil()) {
/* 126 */       maxY = y;
/*     */     }
/* 128 */     if (minY > maxY) {
/* 129 */       minY = y;
/* 130 */       maxY = y;
/*     */     } 
/*     */     
/* 133 */     this.verticalRange.assign(y, minY, maxY);
/* 134 */     return this.verticalRange;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double computeMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Steering steering, double dt, @Nonnull Vector3d translation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 139 */     saveMotionKind();
/* 140 */     this.moveProbe.probePosition(ref, this.collisionBoundingBox, this.position, this.collisionResult, this.swimDepth, componentAccessor);
/* 141 */     setMotionKind((!this.moveProbe.isInWater() && this.moveProbe.isOnGround()) ? MotionKind.MOVING : MotionKind.SWIMMING);
/* 142 */     this.currentRelativeSpeed = steering.getSpeed();
/*     */     
/* 144 */     Vector3d dir = steering.getTranslation();
/*     */     
/* 146 */     float heading = getYaw();
/* 147 */     float pitch = getPitch();
/*     */     
/* 149 */     if (this.collisionWithSolid) {
/*     */       
/* 151 */       this.moveSpeed = 0.0D;
/* 152 */       this.climbSpeed = 0.0D;
/* 153 */       this.forceVelocity.assign(Vector3d.ZERO);
/* 154 */       this.appliedVelocities.clear();
/*     */     } 
/*     */     
/* 157 */     if (canAct(ref, componentAccessor)) {
/*     */       float newHeading, newPitch;
/* 159 */       this.tempDirection.assign(dir.x, 0.0D, dir.z);
/*     */ 
/*     */       
/* 162 */       double maxVerticalSpeed = this.maxVerticalSpeed * this.effectHorizontalSpeedMultiplier;
/* 163 */       double hSpeed = this.tempDirection.length() * getMaximumSpeed();
/* 164 */       double vSpeed = dir.y * maxVerticalSpeed;
/*     */       
/* 166 */       this.moveSpeed = NPCPhysicsMath.accelerateToTargetSpeed(this.moveSpeed, hSpeed, dt, this.acceleration, getMaximumSpeed());
/* 167 */       this.climbSpeed = NPCPhysicsMath.accelerateToTargetSpeed(this.climbSpeed, vSpeed, dt, this.acceleration, this.acceleration, -maxVerticalSpeed, maxVerticalSpeed);
/*     */       
/* 169 */       float maxRotation = (float)(dt * getCurrentMaxBodyRotationSpeed());
/*     */ 
/*     */       
/* 172 */       boolean isMoving = (this.moveSpeed * this.moveSpeed + this.climbSpeed * this.climbSpeed > 1.0E-12D && steering.hasTranslation());
/*     */       
/* 174 */       if (this.moveSpeed * this.moveSpeed > 1.0000000000000002E-10D && steering.hasTranslation()) {
/* 175 */         newHeading = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(dir.x, dir.z));
/* 176 */         newPitch = PhysicsMath.normalizeTurnAngle(PhysicsMath.pitchFromDirection(dir.x, dir.y, dir.z));
/*     */       } else {
/* 178 */         translation.assign(Vector3d.ZERO);
/*     */         
/* 180 */         if (steering.hasYaw()) {
/* 181 */           newHeading = steering.getYaw();
/*     */         } else {
/* 183 */           newHeading = heading;
/*     */         } 
/* 185 */         steering.clearYaw();
/*     */         
/* 187 */         if (steering.hasPitch()) {
/* 188 */           newPitch = steering.getPitch();
/*     */         } else {
/* 190 */           newPitch = pitch;
/*     */         } 
/* 192 */         steering.clearPitch();
/*     */       } 
/*     */       
/* 195 */       float turnAngle = NPCPhysicsMath.turnAngle(heading, newHeading);
/* 196 */       float inclinationAngle = NPCPhysicsMath.turnAngle(pitch, newPitch);
/*     */       
/* 198 */       if (Math.abs(turnAngle) > this.maxMoveTurnAngle) {
/* 199 */         this.moveSpeed = 0.0D;
/*     */       }
/*     */       
/* 202 */       if (isNearZero(turnAngle)) {
/* 203 */         heading = newHeading;
/* 204 */         turnAngle = 0.0F;
/*     */       } else {
/* 206 */         turnAngle = MathUtil.clamp(turnAngle, -maxRotation, maxRotation);
/* 207 */         heading += turnAngle;
/* 208 */         if (!isMoving) setMotionKind(MotionKind.SWIMMING_TURNING); 
/*     */       } 
/* 210 */       heading = PhysicsMath.normalizeTurnAngle(heading);
/*     */       
/* 212 */       if (isNearZero(inclinationAngle)) {
/* 213 */         pitch = newPitch;
/*     */       } else {
/* 215 */         inclinationAngle = MathUtil.clamp(inclinationAngle, -maxRotation, maxRotation);
/* 216 */         pitch += inclinationAngle;
/*     */       } 
/* 218 */       pitch = PhysicsMath.normalizeTurnAngle(pitch);
/*     */       
/* 220 */       if (!steering.hasYaw()) {
/* 221 */         steering.setYaw(heading);
/*     */       }
/*     */       
/* 224 */       if (!steering.hasPitch()) {
/* 225 */         steering.setPitch(pitch);
/*     */       }
/*     */       
/* 228 */       if (this.debugModeSteer) {
/* 229 */         LOGGER.at(Level.INFO).log("=== Compute = t =%.4f v =%.4f h =%.4f nh=%.4f dh=%.4f", Double.valueOf(dt), Double.valueOf(this.moveSpeed), Float.valueOf(57.295776F * heading), Float.valueOf(57.295776F * newHeading), Float.valueOf(57.295776F * turnAngle));
/*     */       }
/* 231 */       computeTranslation(translation, dt, heading, this.moveSpeed, this.climbSpeed);
/*     */       
/* 233 */       return dt;
/*     */     } 
/*     */ 
/*     */     
/* 237 */     double maxSpeed = this.moveProbe.isInWater() ? this.maxSinkSpeed : this.maxFallSpeed;
/* 238 */     boolean onGround = onGround();
/*     */     
/* 240 */     if (!isAlive(ref, componentAccessor)) {
/*     */ 
/*     */       
/* 243 */       steering.setYaw(getYaw());
/* 244 */       steering.setPitch(onGround ? 0.0F : getPitch());
/*     */       
/* 246 */       this.forceVelocity.assign(Vector3d.ZERO);
/* 247 */       this.appliedVelocities.clear();
/* 248 */       this.moveSpeed = 0.0D;
/* 249 */       this.climbSpeed = 0.0D;
/*     */       
/* 251 */       if (onGround) {
/* 252 */         translation.assign(Vector3d.ZERO);
/*     */       } else {
/* 254 */         Velocity velocityComponent = (Velocity)componentAccessor.getComponent(ref, Velocity.getComponentType());
/* 255 */         double sinkSpeed = (velocityComponent.getVelocity()).y;
/*     */ 
/*     */         
/* 258 */         sinkSpeed = NPCPhysicsMath.gravityDrag(sinkSpeed, 5.0D * this.gravity, dt, maxSpeed);
/* 259 */         translation.assign(0.0D, sinkSpeed, 0.0D).scale(dt);
/*     */       } 
/* 261 */       return dt;
/*     */     } 
/*     */     
/* 264 */     if (!this.forceVelocity.equals(Vector3d.ZERO) || !this.appliedVelocities.isEmpty()) {
/* 265 */       this.moveSpeed = 0.0D;
/* 266 */       this.climbSpeed = 0.0D;
/* 267 */       if (!isObstructed()) {
/* 268 */         translation.assign(this.forceVelocity);
/*     */         
/* 270 */         for (int i = 0; i < this.appliedVelocities.size(); i++) {
/* 271 */           MotionControllerBase.AppliedVelocity entry = this.appliedVelocities.get(i);
/*     */           
/* 273 */           if (entry.velocity.y + this.forceVelocity.y <= 0.0D || entry.velocity.y < 0.0D) entry.canClear = true; 
/* 274 */           if (onGround && entry.canClear) entry.velocity.y = 0.0D;
/*     */           
/* 276 */           translation.add(entry.velocity);
/*     */         } 
/*     */       } else {
/* 279 */         translation.assign(Vector3d.ZERO);
/* 280 */         this.appliedVelocities.clear();
/* 281 */         this.forceVelocity.assign(Vector3d.ZERO);
/*     */       } 
/*     */ 
/*     */       
/* 285 */       translation.y = NPCPhysicsMath.gravityDrag(this.forceVelocity.y, 5.0D * this.gravity, dt, maxSpeed);
/* 286 */       translation.scale(dt);
/*     */       
/* 288 */       steering.setYaw(getYaw());
/* 289 */       steering.setPitch(getPitch());
/* 290 */       return dt;
/*     */     } 
/*     */     
/* 293 */     if (!steering.hasYaw()) steering.setYaw(heading); 
/* 294 */     if (!steering.hasPitch()) {
/* 295 */       steering.setPitch(onGround() ? 0.0F : pitch);
/*     */     }
/*     */     
/* 298 */     this.climbSpeed = NPCPhysicsMath.gravityDrag(this.climbSpeed, 5.0D * this.gravity, dt, maxSpeed);
/*     */     
/* 300 */     computeTranslation(translation, dt, heading, this.moveSpeed, this.climbSpeed);
/*     */     
/* 302 */     return dt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDampenAppliedVelocitiesY() {
/* 307 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldAlwaysUseGroundResistance() {
/* 312 */     return true;
/*     */   }
/*     */   
/*     */   private void computeTranslation(@Nonnull Vector3d translation, double dt, float heading, double moveSpeed, double climbSpeed) {
/* 316 */     translation.x = moveSpeed * dt * PhysicsMath.headingX(heading);
/* 317 */     translation.z = moveSpeed * dt * PhysicsMath.headingZ(heading);
/* 318 */     translation.y = climbSpeed * dt;
/*     */     
/* 320 */     translation.clipToZero(getEpsilonSpeed());
/*     */   }
/*     */   
/*     */   private boolean isNearZero(float angle) {
/* 324 */     float epsilonAngle = getEpsilonAngle();
/* 325 */     return (angle >= -epsilonAngle && angle <= epsilonAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMotionKind(MotionKind motionKind) {
/* 331 */     if (!VALID_MOTIONS.contains(motionKind)) motionKind = MotionKind.SWIMMING; 
/* 332 */     super.setMotionKind(motionKind);
/*     */   }
/*     */ 
/*     */   
/*     */   protected double executeMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Vector3d translation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 337 */     if (this.debugModeValidatePositions && !isValidPosition(this.position, this.collisionResult, componentAccessor)) {
/* 338 */       throw new IllegalStateException("Invalid position");
/*     */     }
/*     */     
/* 341 */     boolean canAct = canAct(ref, componentAccessor);
/*     */     
/* 343 */     this.collisionResult.setCollisionByMaterial(canAct ? 5 : 4);
/* 344 */     if (this.debugModeBlockCollisions) this.collisionResult.setLogger(LOGGER); 
/* 345 */     CollisionModule.get(); CollisionModule.findCollisions(this.collisionBoundingBox, this.position, translation, this.collisionResult, componentAccessor);
/* 346 */     if (this.debugModeBlockCollisions) this.collisionResult.setLogger(null); 
/* 347 */     if (this.debugModeCollisions) dumpCollisionResults();
/*     */     
/* 349 */     this.lastValidPosition.assign(this.position);
/* 350 */     this.isObstructed = false;
/* 351 */     this.collisionWithSolid = false;
/*     */     
/* 353 */     BlockCollisionData collision = this.collisionResult.getFirstBlockCollision();
/* 354 */     if (collision == null) {
/* 355 */       double time = dt;
/*     */       
/* 357 */       this.position.add(translation);
/* 358 */       if (!this.moveProbe.probePosition(ref, this.collisionBoundingBox, this.position, this.collisionResult, this.swimDepth, componentAccessor) || (canAct && !this.moveProbe.isInWater())) {
/* 359 */         time = bisect(ref, this.lastValidPosition, 0.0D, this.position, dt, this.position, componentAccessor);
/* 360 */         this.isObstructed = true;
/* 361 */         if (this.debugModeMove) {
/* 362 */           LOGGER.at(Level.INFO).log("Move - Dive: No Collision, Bisect pos=%s, blocked=%s", Vector3d.formatShortString(this.position), this.isObstructed);
/*     */         }
/*     */       }
/* 365 */       else if (this.debugModeMove) {
/* 366 */         LOGGER.at(Level.INFO).log("Move - Dive: No collision, pos=%s, blocked=%s", Vector3d.formatShortString(this.position), this.isObstructed);
/*     */       } 
/*     */       
/* 369 */       if (this.debugModeValidatePositions && !isValidPosition(this.position, this.collisionResult, componentAccessor)) {
/* 370 */         throw new IllegalStateException("Invalid position");
/*     */       }
/* 372 */       processTriggers(ref, this.collisionResult, time / dt, componentAccessor);
/* 373 */       return time;
/*     */     } 
/*     */     
/* 376 */     if (this.debugModeValidatePositions && !isValidPosition(collision.collisionPoint, this.collisionResult, componentAccessor)) {
/* 377 */       throw new IllegalStateException("Invalid position");
/*     */     }
/*     */     
/* 380 */     double collisionStart = collision.collisionStart;
/* 381 */     this.position.assign(collision.collisionPoint);
/*     */     
/* 383 */     this.isObstructed = true;
/* 384 */     this.collisionWithSolid = (collision.blockMaterial == BlockMaterial.Solid);
/*     */     
/* 386 */     if (!this.moveProbe.probePosition(ref, this.collisionBoundingBox, this.position, this.collisionResult, this.swimDepth, componentAccessor) || (canAct && !this.moveProbe.isInWater())) {
/* 387 */       collisionStart = bisect(ref, this.lastValidPosition, 0.0D, this.position, collisionStart, this.position, componentAccessor);
/* 388 */       if (this.debugModeMove) {
/* 389 */         LOGGER.at(Level.INFO).log("Move - Dive: Collision with solid=%s, Bisect pos=%s, blocked=%s", Boolean.valueOf(this.collisionWithSolid), Vector3d.formatShortString(this.position), Boolean.valueOf(this.isObstructed));
/*     */       }
/*     */     }
/* 392 */     else if (this.debugModeMove) {
/* 393 */       LOGGER.at(Level.INFO).log("Move - Dive: Collision with solid=%s, pos=%s, blocked=%s", Boolean.valueOf(this.collisionWithSolid), Vector3d.formatShortString(this.position), Boolean.valueOf(this.isObstructed));
/*     */     } 
/*     */     
/* 396 */     if (this.debugModeValidatePositions && !isValidPosition(this.position, this.collisionResult, componentAccessor)) {
/* 397 */       throw new IllegalStateException("Invalid position");
/*     */     }
/*     */     
/* 400 */     processTriggers(ref, this.collisionResult, collisionStart, componentAccessor);
/* 401 */     return dt * collisionStart;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrainRotations(Role role, @Nonnull TransformComponent transform) {
/* 407 */     transform.getRotation().setRoll(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentMaxBodyRotationSpeed() {
/* 412 */     return this.maxRotationSpeed * this.effectHorizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAct(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 417 */     return (super.canAct(ref, componentAccessor) && this.moveProbe.isInWater());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inAir() {
/* 422 */     return !this.moveProbe.isOnGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inWater() {
/* 427 */     return this.moveProbe.isInWater();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onGround() {
/* 432 */     return this.moveProbe.isOnGround();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getType() {
/* 438 */     return "Dive";
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
/*     */   public double bisect(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d validPosition, double validDistance, @Nonnull Vector3d invalidPosition, double invalidDistance, @Nonnull Vector3d result, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 460 */     return NPCPhysicsMath.lerp(validDistance, invalidDistance, bisect(ref, validPosition, invalidPosition, result, componentAccessor));
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
/*     */   public double bisect(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d validPosition, @Nonnull Vector3d invalidPosition, @Nonnull Vector3d result, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 478 */     return bisect(validPosition, invalidPosition, this, (_this, position) -> _this.probeMoveProbe.probePosition(ref, _this.collisionBoundingBox, position, _this.collisionResult, _this.swimDepth, componentAccessor), result);
/*     */   }
/*     */ 
/*     */   
/*     */   public double probeMove(@Nonnull Ref<EntityStore> ref, @Nonnull ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 483 */     boolean saveSegments = probeMoveData.startProbing();
/*     */     
/* 485 */     this.collisionResult.setCollisionByMaterial(5);
/*     */     
/* 487 */     Vector3d probePosition = probeMoveData.probePosition;
/* 488 */     Vector3d probeMovement = probeMoveData.probeDirection;
/*     */ 
/*     */     
/* 491 */     CollisionModule collisionModule = CollisionModule.get();
/*     */     
/* 493 */     if (saveSegments) probeMoveData.addStartSegment(probePosition, false);
/*     */     
/* 495 */     if (!this.probeMoveProbe.probePosition(ref, this.collisionBoundingBox, probePosition, this.collisionResult, this.swimDepth, componentAccessor)) {
/* 496 */       if (saveSegments) probeMoveData.addEndSegment(probePosition, false, 0.0D); 
/* 497 */       return 0.0D;
/*     */     } 
/*     */     
/* 500 */     double maxDistance = probeMovement.length();
/*     */     
/* 502 */     CollisionModule.findCollisions(this.collisionBoundingBox, probePosition, probeMovement, this.collisionResult, componentAccessor);
/* 503 */     if (this.debugModeMove) {
/* 504 */       LOGGER.at(Level.INFO).log("Probe Step: pos=%s mov=%s left=%s", Vector3d.formatShortString(probePosition), Vector3d.formatShortString(probeMovement), Double.valueOf(maxDistance));
/*     */     }
/* 506 */     if (this.debugModeCollisions) dumpCollisionResults();
/*     */ 
/*     */     
/* 509 */     BlockCollisionData collision = this.collisionResult.getFirstBlockCollision();
/* 510 */     this.tempPosition.assign(probePosition);
/*     */     
/* 512 */     if (collision == null) {
/* 513 */       double d; probePosition.add(probeMovement);
/* 514 */       probeMovement.assign(Vector3d.ZERO);
/* 515 */       if (!this.probeMoveProbe.probePosition(ref, this.collisionBoundingBox, probePosition, this.collisionResult, this.swimDepth, componentAccessor) || !this.probeMoveProbe.isInWater()) {
/* 516 */         d = bisect(ref, this.tempPosition, 0.0D, probePosition, maxDistance, probePosition, componentAccessor);
/* 517 */         if (this.debugModeMove) {
/* 518 */           LOGGER.at(Level.INFO).log("Probe - Dive: No Collision, Bisect pos=%s, distanceLeft=%s", Vector3d.formatShortString(probePosition), maxDistance - d);
/*     */         }
/*     */       } else {
/* 521 */         d = maxDistance;
/* 522 */         if (this.debugModeMove) {
/* 523 */           LOGGER.at(Level.INFO).log("Probe - Dive: No Collision, valid pos=%s, distanceLeft=%s", Vector3d.formatShortString(probePosition), maxDistance - d);
/*     */         }
/*     */       } 
/* 526 */       if (this.debugModeValidatePositions && !isValidPosition(this.tempPosition, this.collisionResult, componentAccessor)) {
/* 527 */         throw new IllegalStateException("Invalid position");
/*     */       }
/* 529 */       if (saveSegments) {
/* 530 */         probeMoveData.addMoveSegment(probePosition, false, d);
/* 531 */         probeMoveData.addEndSegment(probePosition, false, d);
/*     */       } 
/* 533 */       if (this.debugModeMove) {
/* 534 */         LOGGER.at(Level.INFO).log("Probe Move done: No collision - maxDistance=%s distanceLeft=%s", maxDistance, maxDistance - d);
/*     */       }
/* 536 */       return d;
/*     */     } 
/*     */     
/* 539 */     double collisionStart = collision.collisionStart;
/* 540 */     double distanceTravelled = maxDistance * collisionStart;
/* 541 */     probePosition.assign(collision.collisionPoint);
/*     */     
/* 543 */     if (!this.probeMoveProbe.probePosition(ref, this.collisionBoundingBox, probePosition, this.collisionResult, this.swimDepth, componentAccessor) || !this.probeMoveProbe.isInWater()) {
/* 544 */       distanceTravelled = bisect(ref, this.tempPosition, 0.0D, probePosition, distanceTravelled, probePosition, componentAccessor);
/* 545 */       if (this.debugModeMove) {
/* 546 */         LOGGER.at(Level.INFO).log("Probe - Dive: Collision, Bisect pos=%s, distanceLeft=%s, collision start=%s", Vector3d.formatShortString(probePosition), Double.valueOf(maxDistance - distanceTravelled), Double.valueOf(collisionStart));
/*     */       }
/*     */     }
/* 549 */     else if (this.debugModeMove) {
/* 550 */       LOGGER.at(Level.INFO).log("Probe - Dive: Collision, valid pos=%s, distanceLeft=%s, collision start=%s", Vector3d.formatShortString(probePosition), Double.valueOf(maxDistance - distanceTravelled), Double.valueOf(collisionStart));
/*     */     } 
/*     */     
/* 553 */     if (this.debugModeValidatePositions && !isValidPosition(probePosition, this.collisionResult, componentAccessor)) {
/* 554 */       throw new IllegalStateException("Invalid position");
/*     */     }
/* 556 */     if (saveSegments) {
/* 557 */       if (getWorldNormal().equals(collision.collisionNormal)) {
/* 558 */         probeMoveData.addHitGroundSegment(probePosition, distanceTravelled, collision.collisionNormal, collision.blockId);
/*     */       } else {
/* 560 */         probeMoveData.addHitWallSegment(probePosition, false, distanceTravelled, collision.collisionNormal, collision.blockId);
/*     */       } 
/*     */     }
/*     */     
/* 564 */     if (saveSegments) probeMoveData.addEndSegment(probePosition, false, distanceTravelled);
/*     */     
/* 566 */     if (this.debugModeMove) {
/* 567 */       LOGGER.at(Level.INFO).log("Probe Move done: maxDistance=%s distanceLeft=%s", maxDistance, maxDistance - distanceTravelled);
/*     */     }
/* 569 */     return distanceTravelled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawned() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCurrentSpeed() {
/* 579 */     return this.moveSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentTurnRadius() {
/* 584 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxClimbAngle() {
/* 589 */     return 1.5707964F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxSinkAngle() {
/* 594 */     return 1.5707964F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaximumSpeed() {
/* 599 */     return this.maxHorizontalSpeed * this.effectHorizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFastMotionKind(double speed) {
/* 604 */     return (this.currentRelativeSpeed > this.fastDiveThreshold);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is2D() {
/* 609 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRestAtPlace() {
/* 614 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDesiredAltitudeWeight() {
/* 619 */     return this.desiredDepthWeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeightOverGround() {
/* 624 */     return this.probeMoveProbe.getHeightOverGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean estimateVelocity(Steering steering, @Nonnull Vector3d velocityOut) {
/* 629 */     velocityOut.assign(Vector3d.ZERO);
/* 630 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateModelParameters(Ref<EntityStore> ref, Model model, @Nonnull Box boundingBox, ComponentAccessor<EntityStore> componentAccessor) {
/* 635 */     super.updateModelParameters(ref, model, boundingBox, componentAccessor);
/* 636 */     this.swimDepth = relativeSwimDepthToHeight(ref, this.relativeSwimDepth, model, boundingBox, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dampForceVelocity(@Nonnull Vector3d forceVelocity, double forceVelocityDamping, double interval, ComponentAccessor<EntityStore> componentAccessor) {
/* 641 */     if (forceVelocity.squaredLength() < this.minSpeedAfterForceSquared) {
/* 642 */       forceVelocity.assign(Vector3d.ZERO);
/*     */       return;
/*     */     } 
/* 645 */     NPCPhysicsMath.deccelerateToStop(forceVelocity, getDampingDeceleration(), interval);
/*     */   }
/*     */   
/*     */   public static double relativeSwimDepthToBoundingBox(double swimDepth, @Nullable Box boundingBox, float eyeHeight) {
/* 649 */     if (boundingBox == null) return 0.5D;
/*     */     
/* 651 */     if (swimDepth < 0.0D) {
/* 652 */       return NPCPhysicsMath.lerp(eyeHeight, boundingBox.getMin().getY(), -swimDepth);
/*     */     }
/* 654 */     return NPCPhysicsMath.lerp(eyeHeight, boundingBox.getMax().getY(), swimDepth);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double relativeSwimDepthToHeight(double swimDepth, @Nullable Box boundingBox, float eyeHeight) {
/* 659 */     if (boundingBox == null) return 0.5D; 
/* 660 */     return relativeSwimDepthToBoundingBox(swimDepth, boundingBox, eyeHeight) - boundingBox.getMin().getY();
/*     */   }
/*     */   
/*     */   public static double relativeSwimDepthToHeight(@Nullable Ref<EntityStore> ref, double swimDepth, Model model, Box boundingBox, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 664 */     return relativeSwimDepthToHeight(swimDepth, boundingBox, (model != null) ? model.getEyeHeight(ref, componentAccessor) : 0.0F);
/*     */   }
/*     */   
/*     */   public double getDampingDeceleration() {
/* 668 */     return this.forceVelocityDamping * DAMPING_FACTOR;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\controllers\MotionControllerDive.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */