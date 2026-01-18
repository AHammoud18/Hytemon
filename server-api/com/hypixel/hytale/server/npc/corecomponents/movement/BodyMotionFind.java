/*     */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFind;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFindWithTarget;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForcePursue;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForceWithTarget;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarBase;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarNode;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarWithTarget;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BodyMotionFind
/*     */   extends BodyMotionFindWithTarget
/*     */ {
/*     */   protected final double distance;
/*     */   protected final double distanceSquared;
/*     */   protected final boolean reachable;
/*     */   protected final double heightDifferenceMin;
/*  36 */   protected final SteeringForcePursue seek = new SteeringForcePursue(); protected final double heightDifferenceMax; protected final double abortDistance; protected final double abortDistanceSquared; protected final double switchToSteeringDistance; protected final double switchToSteeringDistanceSquared;
/*  37 */   protected final Vector3d tempDirectionVector = new Vector3d();
/*     */   
/*     */   protected double effectiveDistanceSquared;
/*     */   
/*     */   public BodyMotionFind(@Nonnull BuilderBodyMotionFind builderMotionFind, @Nonnull BuilderSupport support) {
/*  42 */     super((BuilderBodyMotionFindWithTarget)builderMotionFind, support);
/*     */     
/*  44 */     this.reachable = builderMotionFind.getReachable(support);
/*  45 */     this.distance = builderMotionFind.getStopDistance(support);
/*  46 */     this.distanceSquared = this.distance * this.distance;
/*  47 */     double[] heightDifference = builderMotionFind.getHeightDifference(support);
/*  48 */     this.heightDifferenceMin = heightDifference[0];
/*  49 */     this.heightDifferenceMax = heightDifference[1];
/*     */ 
/*     */     
/*  52 */     this.seek.setDistances(builderMotionFind.getSlowDownDistance(support), builderMotionFind.getStopDistance(support));
/*  53 */     this.seek.setFalloff(builderMotionFind.getFalloff(support));
/*     */     
/*  55 */     this.abortDistance = builderMotionFind.getAbortDistance(support);
/*  56 */     this.abortDistanceSquared = this.abortDistance * this.abortDistance;
/*  57 */     this.switchToSteeringDistance = builderMotionFind.getSwitchToSteeringDistance(support);
/*  58 */     this.switchToSteeringDistanceSquared = this.switchToSteeringDistance * this.switchToSteeringDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSwitchToSteering(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController motionController, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  63 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  64 */     assert transformComponent != null;
/*     */     
/*  66 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  68 */     if (motionController.waypointDistanceSquared(position, getLastTargetPosition()) > this.switchToSteeringDistanceSquared) {
/*  69 */       return false;
/*     */     }
/*  71 */     if (this.dbgMotionState) {
/*  72 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().at(Level.INFO).every(100)).log("MotionFind: computing canSwitchToSteering");
/*     */     }
/*  74 */     return canReachTarget(ref, motionController, position, getLastAccessibleTargetPosition(motionController, true, componentAccessor), componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldSkipSteering(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController activeMotionController, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  79 */     Vector3d targetPosition = getLastAccessibleTargetPosition(activeMotionController, true, componentAccessor);
/*  80 */     this.probeMoveData.setPosition(position).setTargetPosition(targetPosition);
/*  81 */     activeMotionController.probeMove(ref, this.probeMoveData, componentAccessor);
/*  82 */     return !isGoalReached(ref, activeMotionController, this.probeMoveData.probePosition, targetPosition, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Vector3d position, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  87 */     this.seek.setPositions(position, getLastTargetPosition());
/*  88 */     MotionController motionController = role.getActiveMotionController();
/*  89 */     this.seek.setComponentSelector(motionController.getComponentSelector());
/*  90 */     double desiredAltitudeWeight = (this.desiredAltitudeWeight >= 0.0D) ? this.desiredAltitudeWeight : motionController.getDesiredAltitudeWeight();
/*  91 */     return scaleSteering(ref, role, (SteeringForceWithTarget)this.seek, desiredSteering, desiredAltitudeWeight, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canComputeMotion(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider infoProvider, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  96 */     if (!super.canComputeMotion(ref, role, infoProvider, componentAccessor) || (this.abortDistance > 0.0D && role.getActiveMotionController().waypointDistanceSquared(ref, getLastTargetPosition(), componentAccessor) >= this.abortDistanceSquared)) {
/*  97 */       return false;
/*     */     }
/*  99 */     if (this.selfBoundingBox != null && this.adjustRangeByHitboxSize) {
/* 100 */       double effectiveDistance = this.distance + Math.max(this.selfBoundingBox.width(), this.selfBoundingBox.depth());
/* 101 */       if (this.targetBoundingBox != null) {
/* 102 */         effectiveDistance += Math.max(this.targetBoundingBox.width(), this.targetBoundingBox.depth());
/*     */       }
/* 104 */       this.effectiveDistanceSquared = effectiveDistance * effectiveDistance;
/*     */     } else {
/* 106 */       this.effectiveDistanceSquared = this.distanceSquared;
/*     */     } 
/* 108 */     return (this.selfBoundingBox != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isGoalReached(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController motionController, @Nonnull Vector3d position, @Nonnull Vector3d targetPosition, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 113 */     double differenceY = targetPosition.y - position.y;
/* 114 */     if (differenceY < this.heightDifferenceMin || differenceY > this.heightDifferenceMax) return false;
/*     */     
/* 116 */     if (motionController.waypointDistanceSquared(position, targetPosition) > this.effectiveDistanceSquared) return false;
/*     */     
/* 118 */     return (!this.reachable || canReachTarget(ref, motionController, position, targetPosition, componentAccessor));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGoalReached(@Nonnull Ref<EntityStore> ref, AStarBase aStarBase, @Nonnull AStarNode aStarNode, @Nonnull MotionController motionController, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 123 */     AStarWithTarget aStarWithTarget = (AStarWithTarget)aStarBase;
/* 124 */     return isGoalReached(ref, motionController, aStarNode.getPosition(), aStarWithTarget.getTargetPosition(), componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public float estimateToGoal(@Nonnull AStarBase aStarBase, @Nonnull Vector3d fromPosition, MotionController motionController) {
/* 129 */     return (float)((AStarWithTarget)aStarBase).getTargetPosition().distanceTo(fromPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public void findBestPath(@Nonnull AStarBase aStarBase, MotionController controller) {
/* 134 */     aStarBase.buildBestPath(AStarNode::getEstimateToGoal, (oldV, v) -> (v < oldV), Float.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onThrottling(MotionController motionController, @Nonnull Ref<EntityStore> ref, @Nonnull Steering steering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 139 */     super.onThrottling(motionController, ref, steering, componentAccessor);
/* 140 */     lookAtTarget(ref, steering, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDeferring(MotionController motionController, @Nonnull Ref<EntityStore> ref, @Nonnull Steering steering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 145 */     super.onDeferring(motionController, ref, steering, componentAccessor);
/* 146 */     lookAtTarget(ref, steering, componentAccessor);
/*     */   }
/*     */   
/*     */   protected void lookAtTarget(@Nonnull Ref<EntityStore> ref, @Nonnull Steering steering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 150 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 151 */     assert transformComponent != null;
/*     */     
/* 153 */     Vector3d position = transformComponent.getPosition();
/* 154 */     Vector3f bodyRotation = transformComponent.getRotation();
/*     */     
/* 156 */     this.tempDirectionVector.assign(getLastTargetPosition()).subtract(position);
/* 157 */     steering.setYaw(NPCPhysicsMath.headingFromDirection(this.tempDirectionVector.x, this.tempDirectionVector.z, bodyRotation.getYaw()));
/* 158 */     steering.setPitch(NPCPhysicsMath.pitchFromDirection(this.tempDirectionVector.x, this.tempDirectionVector.y, this.tempDirectionVector.z, bodyRotation.getPitch()));
/*     */   }
/*     */   
/*     */   protected boolean canReachTarget(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController motionController, @Nonnull Vector3d position, @Nonnull Vector3d targetPosition, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 162 */     if (isBoundingBoxesOverlapping(position, targetPosition)) return true;
/*     */     
/* 164 */     Vector3d direction = this.tempDirectionVector.assign(targetPosition).subtract(position);
/* 165 */     motionController.probeMove(ref, position, direction, this.probeMoveData, componentAccessor);
/* 166 */     return isBoundingBoxesOverlapping(this.probeMoveData.probePosition, targetPosition);
/*     */   }
/*     */   
/*     */   protected boolean isBoundingBoxesOverlapping(@Nonnull Vector3d position, @Nonnull Vector3d endPosition) {
/* 170 */     if (this.targetBoundingBox == null) return containsPosition(position, endPosition);
/*     */     
/* 172 */     return (containsPosition(position.x, this.selfBoundingBox.min.x - this.targetBoundingBox.max.x, this.selfBoundingBox.max.x - this.targetBoundingBox.min.x, endPosition.x) && 
/* 173 */       containsPosition(position.y, this.selfBoundingBox.min.y - this.targetBoundingBox.max.y, this.selfBoundingBox.max.y - this.targetBoundingBox.min.y, endPosition.y) && 
/* 174 */       containsPosition(position.z, this.selfBoundingBox.min.z - this.targetBoundingBox.max.z, this.selfBoundingBox.max.z - this.targetBoundingBox.min.z, endPosition.z));
/*     */   }
/*     */   
/*     */   protected boolean containsPosition(@Nonnull Vector3d position, @Nonnull Vector3d endPosition) {
/* 178 */     return (containsPosition(position.x, this.selfBoundingBox.min.x, this.selfBoundingBox.max.x, endPosition.x) && 
/* 179 */       containsPosition(position.y, this.selfBoundingBox.min.y, this.selfBoundingBox.max.y, endPosition.y) && 
/* 180 */       containsPosition(position.z, this.selfBoundingBox.min.z, this.selfBoundingBox.max.z, endPosition.z));
/*     */   }
/*     */   
/*     */   protected static boolean containsPosition(double p, double min, double max, double v) {
/* 184 */     v -= p;
/* 185 */     return (v >= min && v < max);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionFind.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */