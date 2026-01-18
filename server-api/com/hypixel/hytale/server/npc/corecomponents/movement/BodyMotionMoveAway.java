/*     */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFindWithTarget;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionMoveAway;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForceEvade;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForceWithTarget;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarBase;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarNode;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarWithTarget;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BodyMotionMoveAway
/*     */   extends BodyMotionFindWithTarget
/*     */ {
/*     */   protected final double stopDistance;
/*     */   protected final double stopDistanceSquared;
/*     */   protected final double[] holdDirectionDurationRange;
/*     */   protected final float changeDirectionViewSector;
/*  37 */   protected final SteeringForceEvade evade = new SteeringForceEvade(); protected final float jitterAngle; protected final double erraticDistanceSquared; protected final float erraticJitter;
/*     */   protected final double erraticChangeDurationMultiplier;
/*     */   protected float fleeDirection;
/*     */   protected double holdDirectionTimeRemaining;
/*     */   
/*     */   public BodyMotionMoveAway(@Nonnull BuilderBodyMotionMoveAway builderMotionFind, @Nonnull BuilderSupport support) {
/*  43 */     super((BuilderBodyMotionFindWithTarget)builderMotionFind, support);
/*  44 */     this.stopDistance = builderMotionFind.getStopDistance(support);
/*  45 */     this.stopDistanceSquared = this.stopDistance * this.stopDistance;
/*  46 */     this.holdDirectionDurationRange = builderMotionFind.getHoldDirectionDurationRange(support);
/*  47 */     this.changeDirectionViewSector = builderMotionFind.getChangeDirectionViewSectorRadians(support);
/*  48 */     this.jitterAngle = builderMotionFind.getDirectionJitterRadians(support);
/*     */     
/*  50 */     double erraticDistance = builderMotionFind.getErraticDistance(support);
/*  51 */     this.erraticDistanceSquared = erraticDistance * erraticDistance;
/*     */     
/*  53 */     float erraticExtraJitter = builderMotionFind.getErraticExtraJitterRadians(support);
/*  54 */     this.erraticJitter = MathUtil.clamp(this.jitterAngle + erraticExtraJitter, 0.0F, 3.1415927F);
/*     */     
/*  56 */     this.erraticChangeDurationMultiplier = builderMotionFind.getErraticChangeDurationMultiplier(support);
/*  57 */     this.evade.setDistances(builderMotionFind.getSlowdownDistance(support), this.stopDistance);
/*  58 */     this.evade.setFalloff(builderMotionFind.getFalloff(support));
/*  59 */     this.evade.setAdhereToDirectionHint(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  64 */     super.activate(ref, role, componentAccessor);
/*  65 */     this.holdDirectionTimeRemaining = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider infoProvider, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  70 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  71 */     assert npcComponent != null;
/*     */ 
/*     */     
/*  74 */     float speedMultiplier = npcComponent.getCurrentHorizontalSpeedMultiplier(ref, componentAccessor);
/*  75 */     if (speedMultiplier == 0.0F) {
/*  76 */       desiredSteering.clear();
/*  77 */       return true;
/*     */     } 
/*  79 */     this.holdDirectionTimeRemaining -= dt * speedMultiplier;
/*  80 */     return super.computeSteering(ref, role, infoProvider, dt, desiredSteering, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, Vector3d position, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  85 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  86 */     assert transformComponent != null;
/*     */     
/*  88 */     Vector3d selfPosition = transformComponent.getPosition();
/*  89 */     Vector3f bodyRotation = transformComponent.getRotation();
/*     */     
/*  91 */     Vector3d lastTargetPosition = getLastTargetPosition();
/*  92 */     if (NPCPhysicsMath.inViewSector(selfPosition.x, selfPosition.z, bodyRotation.getYaw(), this.changeDirectionViewSector, lastTargetPosition.x, lastTargetPosition.z)) {
/*  93 */       this.holdDirectionTimeRemaining = 0.0D;
/*     */     }
/*     */     
/*  96 */     if (this.holdDirectionTimeRemaining <= 0.0D) {
/*  97 */       boolean inErraticRange = (selfPosition.distanceSquaredTo(lastTargetPosition) < this.erraticDistanceSquared);
/*  98 */       float jitter = inErraticRange ? this.erraticJitter : this.jitterAngle;
/*  99 */       this
/* 100 */         .fleeDirection = PhysicsMath.headingFromDirection(selfPosition.x - lastTargetPosition.x, selfPosition.z - lastTargetPosition.z) + RandomExtra.randomRange(-jitter, jitter);
/* 101 */       this.holdDirectionTimeRemaining = RandomExtra.randomRange(this.holdDirectionDurationRange);
/* 102 */       if (inErraticRange) this.holdDirectionTimeRemaining *= this.erraticChangeDurationMultiplier;
/*     */     
/*     */     } 
/* 105 */     this.evade.setPositions(selfPosition, lastTargetPosition);
/* 106 */     this.evade.setDirectionHint(this.fleeDirection);
/*     */     
/* 108 */     MotionController motionController = role.getActiveMotionController();
/* 109 */     double desiredAltitudeWeight = (this.desiredAltitudeWeight >= 0.0D) ? this.desiredAltitudeWeight : motionController.getDesiredAltitudeWeight();
/* 110 */     return scaleSteering(ref, role, (SteeringForceWithTarget)this.evade, desiredSteering, desiredAltitudeWeight, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGoalReached(Ref<EntityStore> ref, AStarBase aStarBase, @Nonnull AStarNode aStarNode, MotionController motionController, ComponentAccessor<EntityStore> componentAccessor) {
/* 115 */     return (aStarNode.getEstimateToGoal() <= 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isGoalReached(Ref<EntityStore> ref, @Nonnull MotionController motionController, Vector3d position, Vector3d lastTestedPosition, ComponentAccessor<EntityStore> componentAccessor) {
/* 120 */     return (motionController.waypointDistanceSquared(position, lastTestedPosition) >= this.stopDistanceSquared);
/*     */   }
/*     */ 
/*     */   
/*     */   public float estimateToGoal(@Nonnull AStarBase aStarBase, Vector3d fromPosition, @Nonnull MotionController motionController) {
/* 125 */     return Math.max(0.0F, (float)(this.stopDistance - motionController.waypointDistance(fromPosition, ((AStarWithTarget)aStarBase).getTargetPosition())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void findBestPath(@Nonnull AStarBase aStarBase, MotionController controller) {
/* 130 */     aStarBase.buildBestPath(AStarNode::getEstimateToGoal, (oldV, v) -> (v < oldV), Float.MAX_VALUE);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionMoveAway.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */