/*     */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionMaintainDistance;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionControllerWalk;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForceEvade;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForcePursue;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.DoubleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BodyMotionMaintainDistance extends BodyMotionBase {
/*  34 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   protected static final float POSITIONING_ANGLE_THRESHOLD = 0.08726646F;
/*     */   
/*     */   protected final double[] initialDesiredDistanceRange;
/*     */   
/*     */   protected final double moveThreshold;
/*     */   
/*     */   @Nonnull
/*     */   protected final double[] thresholdDistanceRangeSquared;
/*     */   
/*     */   protected final double targetDistanceFactor;
/*     */   protected final double relativeForwardsSpeed;
/*     */   protected final double relativeBackwardsSpeed;
/*     */   protected final double moveTowardsSlowdownThreshold;
/*     */   protected final double[] strafingDurationRange;
/*     */   protected final double[] strafingFrequencyRange;
/*     */   protected final int minRangeProviderSlot;
/*     */   protected final int maxRangeProviderSlot;
/*     */   protected final int positioningAngleProviderSlot;
/*  54 */   protected final double[] desiredDistanceRange = new double[2];
/*     */   
/*     */   protected double targetDistanceSquared;
/*     */   
/*     */   protected boolean approaching;
/*     */   
/*     */   protected boolean movingAway;
/*  61 */   protected int strafingDirection = 1;
/*     */   
/*     */   protected double strafingDelay;
/*     */   protected boolean pauseStrafing;
/*  65 */   protected final SteeringForceEvade flee = new SteeringForceEvade();
/*  66 */   protected final SteeringForcePursue seek = new SteeringForcePursue();
/*     */   
/*  68 */   protected final Vector3d targetPosition = new Vector3d();
/*  69 */   protected final Vector3d toTarget = new Vector3d();
/*     */   
/*     */   protected DoubleParameterProvider cachedMinRangeProvider;
/*     */   protected DoubleParameterProvider cachedMaxRangeProvider;
/*     */   protected DoubleParameterProvider cachedPositioningAngleProvider;
/*     */   protected boolean initialised;
/*     */   
/*     */   public BodyMotionMaintainDistance(@Nonnull BuilderBodyMotionMaintainDistance builder, @Nonnull BuilderSupport support) {
/*  77 */     super((BuilderBodyMotionBase)builder);
/*     */     
/*  79 */     this.initialDesiredDistanceRange = builder.getDesiredDistanceRange(support);
/*  80 */     this.desiredDistanceRange[0] = this.initialDesiredDistanceRange[0];
/*  81 */     this.desiredDistanceRange[1] = this.initialDesiredDistanceRange[1];
/*  82 */     this.targetDistanceFactor = builder.getTargetDistanceFactor(support);
/*  83 */     this.moveThreshold = builder.getMoveThreshold(support);
/*     */     
/*  85 */     double min = Math.max(0.0D, this.initialDesiredDistanceRange[0] - this.moveThreshold);
/*  86 */     double max = this.initialDesiredDistanceRange[1] + this.moveThreshold;
/*     */     
/*  88 */     this.thresholdDistanceRangeSquared = new double[2];
/*  89 */     this.thresholdDistanceRangeSquared[0] = min * min;
/*  90 */     this.thresholdDistanceRangeSquared[1] = max * max;
/*  91 */     this.relativeForwardsSpeed = builder.getRelativeForwardsSpeed(support);
/*  92 */     this.relativeBackwardsSpeed = builder.getRelativeBackwardsSpeed(support);
/*  93 */     this.moveTowardsSlowdownThreshold = builder.getMoveTowardsSlowdownThreshold(support);
/*  94 */     this.strafingDurationRange = builder.getStrafingDurationRange(support);
/*  95 */     this.strafingFrequencyRange = builder.getStrafingFrequencyRange(support);
/*  96 */     this.minRangeProviderSlot = support.getParameterSlot("MinRange");
/*  97 */     this.maxRangeProviderSlot = support.getParameterSlot("MaxRange");
/*  98 */     this.positioningAngleProviderSlot = support.getParameterSlot("PositioningAngle");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role support, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 103 */     desiredSteering.clear();
/*     */ 
/*     */     
/* 106 */     if (!support.getActiveMotionController().matchesType(MotionControllerWalk.class)) {
/* 107 */       support.setBackingAway(false);
/* 108 */       return false;
/*     */     } 
/*     */     
/* 111 */     if (!this.initialised) {
/*     */       
/* 113 */       if (sensorInfo != null) {
/* 114 */         ParameterProvider parameterProvider = sensorInfo.getParameterProvider(this.minRangeProviderSlot);
/* 115 */         if (parameterProvider instanceof DoubleParameterProvider) {
/* 116 */           this.cachedMinRangeProvider = (DoubleParameterProvider)parameterProvider;
/*     */         }
/* 118 */         parameterProvider = sensorInfo.getParameterProvider(this.maxRangeProviderSlot);
/* 119 */         if (parameterProvider instanceof DoubleParameterProvider) {
/* 120 */           this.cachedMaxRangeProvider = (DoubleParameterProvider)parameterProvider;
/*     */         }
/* 122 */         parameterProvider = sensorInfo.getParameterProvider(this.positioningAngleProviderSlot);
/* 123 */         if (parameterProvider instanceof DoubleParameterProvider) {
/* 124 */           this.cachedPositioningAngleProvider = (DoubleParameterProvider)parameterProvider;
/*     */         }
/*     */       } 
/* 127 */       this.initialised = true;
/*     */     } 
/*     */     
/* 130 */     boolean recalculateMinThreshold = false;
/* 131 */     boolean forceNewTargetRange = false;
/* 132 */     if (this.cachedMinRangeProvider != null) {
/* 133 */       double value = this.cachedMinRangeProvider.getDoubleParameter();
/* 134 */       double before = this.desiredDistanceRange[0];
/* 135 */       if (value != -1.7976931348623157E308D) {
/* 136 */         this.desiredDistanceRange[0] = this.cachedMinRangeProvider.getDoubleParameter();
/*     */       } else {
/* 138 */         this.desiredDistanceRange[0] = this.initialDesiredDistanceRange[0];
/*     */       } 
/* 140 */       recalculateMinThreshold = true;
/* 141 */       if (before != this.desiredDistanceRange[0]) {
/* 142 */         forceNewTargetRange = true;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     if (this.cachedMaxRangeProvider != null) {
/* 147 */       double value = this.cachedMaxRangeProvider.getDoubleParameter();
/* 148 */       double before = this.desiredDistanceRange[1];
/* 149 */       if (value != -1.7976931348623157E308D) {
/* 150 */         this.desiredDistanceRange[1] = this.cachedMaxRangeProvider.getDoubleParameter();
/*     */       } else {
/* 152 */         this.desiredDistanceRange[1] = this.initialDesiredDistanceRange[1];
/*     */       } 
/* 154 */       double max = this.desiredDistanceRange[1] + this.moveThreshold;
/* 155 */       this.thresholdDistanceRangeSquared[1] = max * max;
/* 156 */       if (before != this.desiredDistanceRange[1]) {
/* 157 */         forceNewTargetRange = true;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     double positioningAngle = Double.MAX_VALUE;
/* 162 */     if (this.cachedPositioningAngleProvider != null) {
/* 163 */       positioningAngle = this.cachedPositioningAngleProvider.getDoubleParameter();
/*     */     }
/*     */     
/* 166 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 167 */     assert npcComponent != null;
/*     */ 
/*     */     
/* 170 */     if (this.desiredDistanceRange[0] > this.desiredDistanceRange[1]) {
/* 171 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().at(Level.WARNING).atMostEvery(1, TimeUnit.MINUTES))
/* 172 */         .log("Attempting to set min distance for %s to a value higher than its max distance [min=%d max=%s]", npcComponent.getRoleName(), 
/* 173 */           Double.valueOf(this.desiredDistanceRange[0]), Double.valueOf(this.desiredDistanceRange[1]));
/* 174 */       this.desiredDistanceRange[0] = this.desiredDistanceRange[1];
/* 175 */       recalculateMinThreshold = true;
/*     */     } 
/*     */     
/* 178 */     if (recalculateMinThreshold) {
/* 179 */       double min = Math.max(0.0D, this.desiredDistanceRange[0] - this.moveThreshold);
/* 180 */       this.thresholdDistanceRangeSquared[0] = min * min;
/*     */     } 
/*     */     
/* 183 */     if (sensorInfo == null || !sensorInfo.hasPosition()) {
/* 184 */       return false;
/*     */     }
/*     */     
/* 187 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 188 */     assert transformComponent != null;
/*     */     
/* 190 */     IPositionProvider positionProvider = sensorInfo.getPositionProvider();
/* 191 */     positionProvider.providePosition(this.targetPosition);
/* 192 */     Vector3d selfPosition = transformComponent.getPosition();
/* 193 */     double distanceSquared = selfPosition.distanceSquaredTo(this.targetPosition);
/*     */     
/* 195 */     if (forceNewTargetRange) {
/*     */       double targetDistance;
/*     */       
/* 198 */       if (distanceSquared > this.thresholdDistanceRangeSquared[1]) {
/* 199 */         targetDistance = MathUtil.lerp(this.desiredDistanceRange[0], this.desiredDistanceRange[1], 1.0D - this.targetDistanceFactor);
/*     */       } else {
/* 201 */         targetDistance = MathUtil.lerp(this.desiredDistanceRange[0], this.desiredDistanceRange[1], this.targetDistanceFactor);
/*     */       } 
/* 203 */       this.targetDistanceSquared = targetDistance * targetDistance;
/*     */     } 
/*     */     
/* 206 */     if (distanceSquared > this.thresholdDistanceRangeSquared[1] || (this.approaching && distanceSquared > this.targetDistanceSquared)) {
/*     */       
/* 208 */       if (!this.approaching) {
/*     */ 
/*     */         
/* 211 */         double targetDistance = MathUtil.lerp(this.desiredDistanceRange[0], this.desiredDistanceRange[1], 1.0D - this.targetDistanceFactor);
/* 212 */         this.targetDistanceSquared = targetDistance * targetDistance;
/* 213 */         this.seek.setDistances(targetDistance + this.moveTowardsSlowdownThreshold, targetDistance);
/* 214 */         this.approaching = true;
/* 215 */         this.movingAway = false;
/* 216 */         support.setBackingAway(false);
/*     */       } 
/* 218 */       this.seek.setPositions(selfPosition, this.targetPosition);
/*     */       
/* 220 */       MotionController activeMotionController = support.getActiveMotionController();
/* 221 */       this.seek.setComponentSelector(activeMotionController.getComponentSelector());
/* 222 */       this.seek.compute(desiredSteering);
/* 223 */       desiredSteering.scaleTranslation(this.relativeForwardsSpeed);
/*     */     }
/* 225 */     else if (distanceSquared < this.thresholdDistanceRangeSquared[0] || (this.movingAway && distanceSquared < this.targetDistanceSquared)) {
/*     */ 
/*     */       
/* 228 */       if (!this.movingAway) {
/*     */ 
/*     */ 
/*     */         
/* 232 */         double targetDistance = MathUtil.lerp(this.desiredDistanceRange[0], this.desiredDistanceRange[1], this.targetDistanceFactor);
/* 233 */         this.targetDistanceSquared = targetDistance * targetDistance;
/* 234 */         this.movingAway = true;
/* 235 */         this.approaching = false;
/* 236 */         support.setBackingAway(true);
/*     */       } 
/*     */       
/* 239 */       this.flee.setPositions(selfPosition, this.targetPosition);
/*     */       
/* 241 */       MotionController activeMotionController = support.getActiveMotionController();
/* 242 */       this.flee.setComponentSelector(activeMotionController.getComponentSelector());
/* 243 */       this.flee.compute(desiredSteering);
/* 244 */       desiredSteering.scaleTranslation(this.relativeBackwardsSpeed);
/*     */     }
/* 246 */     else if (this.approaching || this.movingAway) {
/*     */       
/* 248 */       this.approaching = false;
/* 249 */       this.movingAway = false;
/*     */     } 
/*     */ 
/*     */     
/* 253 */     double x = this.targetPosition.getX() - selfPosition.getX();
/* 254 */     double z = this.targetPosition.getZ() - selfPosition.getZ();
/* 255 */     float targetYaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/*     */     
/* 257 */     MotionController motionController = support.getActiveMotionController();
/* 258 */     if (this.strafingDurationRange[1] > 0.0D || positioningAngle != Double.MAX_VALUE) {
/* 259 */       if (positioningAngle == Double.MAX_VALUE) {
/* 260 */         if (!tickStrafingDelay(dt))
/*     */         {
/* 262 */           if (this.pauseStrafing) {
/*     */             
/* 264 */             this.strafingDelay = RandomExtra.randomRange(this.strafingDurationRange);
/* 265 */             this.strafingDirection = RandomExtra.randomBoolean() ? 1 : -1;
/* 266 */             this.pauseStrafing = false;
/*     */           } else {
/*     */             
/* 269 */             this.strafingDelay = RandomExtra.randomRange(this.strafingFrequencyRange);
/* 270 */             this.pauseStrafing = true;
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 275 */         Ref<EntityStore> targetRef = positionProvider.getTarget();
/* 276 */         if (targetRef != null) {
/* 277 */           TransformComponent targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 278 */           assert targetTransformComponent != null;
/*     */ 
/*     */           
/* 281 */           float selfYaw = NPCPhysicsMath.lookatHeading(selfPosition, this.targetPosition, transformComponent.getRotation().getYaw());
/* 282 */           float difference = PhysicsMath.normalizeTurnAngle(targetTransformComponent.getRotation().getYaw() - selfYaw - (float)positioningAngle);
/* 283 */           if (Math.abs(difference) > 0.08726646F) {
/* 284 */             this.strafingDirection = (difference > 0.0F) ? -1 : 1;
/* 285 */             this.pauseStrafing = false;
/*     */           } else {
/* 287 */             this.pauseStrafing = true;
/*     */           } 
/*     */         } else {
/* 290 */           this.pauseStrafing = true;
/*     */         } 
/*     */       } 
/*     */       
/* 294 */       if (!this.pauseStrafing) {
/*     */         float angle;
/* 296 */         if (!desiredSteering.hasTranslation()) {
/*     */ 
/*     */           
/* 299 */           this.toTarget.add(this.targetPosition).subtract(selfPosition).setY(0.0D);
/* 300 */           this.toTarget.normalize();
/* 301 */           desiredSteering.setTranslation(this.toTarget);
/*     */           
/* 303 */           Vector3d translation = desiredSteering.getTranslation();
/* 304 */           double newX = translation.getZ() * this.strafingDirection;
/* 305 */           double newZ = translation.getX() * -this.strafingDirection;
/* 306 */           translation.setX(newX);
/* 307 */           translation.setZ(newZ);
/* 308 */           desiredSteering.scaleTranslation(this.relativeForwardsSpeed);
/* 309 */           angle = this.strafingDirection * 0.7853982F;
/*     */         } else {
/*     */           
/* 312 */           angle = this.strafingDirection * (this.movingAway ? -0.7853982F : 0.7853982F);
/* 313 */           desiredSteering.getTranslation().rotateY(angle);
/*     */         } 
/*     */ 
/*     */         
/* 317 */         support.setBackingAway(true);
/*     */ 
/*     */         
/* 320 */         if (!motionController.isObstructed()) targetYaw += angle;
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 325 */     motionController.requireDepthProbing();
/* 326 */     desiredSteering.setYaw(targetYaw);
/* 327 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean tickStrafingDelay(double dt) {
/* 331 */     if (this.strafingDelay > 0.0D) {
/* 332 */       this.strafingDelay -= dt;
/* 333 */       return true;
/*     */     } 
/* 335 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 340 */     super.deactivate(ref, role, componentAccessor);
/*     */ 
/*     */     
/* 343 */     role.setBackingAway(false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionMaintainDistance.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */