/*     */ package com.hypixel.hytale.server.npc.movement.controllers;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.movement.MovementState;
/*     */ import com.hypixel.hytale.server.npc.movement.NavState;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface MotionController
/*     */ {
/*     */   static {
/*  28 */     if (null.$assertionsDisabled);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean matchesType(@Nonnull Class<? extends MotionController> clazz) {
/* 178 */     return clazz.isInstance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void clearOverrides() {}
/*     */ 
/*     */ 
/*     */   
/*     */   default double getSquaredDistance(@Nonnull Vector3d p1, @Nonnull Vector3d p2, boolean useProjectedDistance) {
/* 189 */     return useProjectedDistance ? waypointDistanceSquared(p1, p2) : p1.distanceSquaredTo(p2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isInMovementState(@Nonnull Ref<EntityStore> ref, @Nonnull MovementState state, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 195 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)componentAccessor.getComponent(ref, MovementStatesComponent.getComponentType());
/* 196 */     if (!null.$assertionsDisabled && movementStatesComponent == null) throw new AssertionError();
/*     */     
/* 198 */     Velocity velocityComponent = (Velocity)componentAccessor.getComponent(ref, Velocity.getComponentType());
/* 199 */     if (!null.$assertionsDisabled && velocityComponent == null) throw new AssertionError();
/*     */     
/* 201 */     MovementStates states = movementStatesComponent.getMovementStates();
/* 202 */     switch (state) { default: throw new MatchException(null, null);case CLIMBING: case FALLING: case CROUCHING: case FLYING: case JUMPING: case SPRINTING: case RUNNING: case IDLE: case WALKING: return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 211 */           (!velocityComponent.getVelocity().closeToZero(0.001D) && !states.falling && !states.climbing && !states.flying && !states.running && !states.sprinting && !states.jumping && !states.crouching);
/*     */       case ANY:
/*     */         break; }
/*     */     
/*     */     return true;
/*     */   } String getType(); Role getRole(); void setRole(Role paramRole); void setInertia(double paramDouble); void setKnockbackScale(double paramDouble); double getGravity(); void spawned(); void activate(); void deactivate(); void updateModelParameters(@Nullable Ref<EntityStore> paramRef, Model paramModel, Box paramBox, @Nullable ComponentAccessor<EntityStore> paramComponentAccessor); double steer(@Nonnull Ref<EntityStore> paramRef, @Nonnull Role paramRole, @Nonnull Steering paramSteering1, @Nonnull Steering paramSteering2, double paramDouble, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); double probeMove(@Nonnull Ref<EntityStore> paramRef, Vector3d paramVector3d1, Vector3d paramVector3d2, ProbeMoveData paramProbeMoveData, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); double probeMove(@Nonnull Ref<EntityStore> paramRef, ProbeMoveData paramProbeMoveData, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); void constrainRotations(Role paramRole, TransformComponent paramTransformComponent); double getCurrentMaxBodyRotationSpeed(); void updateMovementState(@Nonnull Ref<EntityStore> paramRef, @Nonnull MovementStates paramMovementStates, @Nonnull Steering paramSteering, @Nonnull Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); boolean isValidPosition(Vector3d paramVector3d, ComponentAccessor<EntityStore> paramComponentAccessor); boolean canAct(@Nonnull Ref<EntityStore> paramRef, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); boolean isInProgress(); boolean isObstructed(); boolean inAir(); boolean inWater(); boolean onGround(); boolean standingOnBlockOfType(int paramInt); double getMaximumSpeed(); double getCurrentSpeed(); boolean estimateVelocity(Steering paramSteering, Vector3d paramVector3d); double getCurrentTurnRadius(); double waypointDistance(Vector3d paramVector3d1, Vector3d paramVector3d2); double waypointDistanceSquared(Vector3d paramVector3d1, Vector3d paramVector3d2); double waypointDistance(@Nonnull Ref<EntityStore> paramRef, Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); double waypointDistanceSquared(@Nonnull Ref<EntityStore> paramRef, Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); float getMaxClimbAngle(); float getMaxSinkAngle(); boolean translateToAccessiblePosition(Vector3d paramVector3d, Box paramBox, double paramDouble1, double paramDouble2, ComponentAccessor<EntityStore> paramComponentAccessor); Vector3d getComponentSelector(); Vector3d getPlanarComponentSelector(); void setComponentSelector(Vector3d paramVector3d); boolean is2D(); Vector3d getWorldNormal(); Vector3d getWorldAntiNormal(); void addForce(@Nonnull Vector3d paramVector3d, @Nullable VelocityConfig paramVelocityConfig); Vector3d getForce(); void forceVelocity(@Nonnull Vector3d paramVector3d, @Nullable VelocityConfig paramVelocityConfig, boolean paramBoolean); VerticalRange getDesiredVerticalRange(@Nonnull Ref<EntityStore> paramRef, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor); double getWanderVerticalMovementRatio(); void setAvoidingBlockDamage(boolean paramBoolean); boolean isAvoidingBlockDamage(); boolean willReceiveBlockDamage(); void requirePreciseMovement(Vector3d paramVector3d); void requireDepthProbing(); void enableHeadingBlending(double paramDouble1, Vector3d paramVector3d, double paramDouble2); void enableHeadingBlending(); void setRelaxedMoveConstraints(boolean paramBoolean); boolean isRelaxedMoveConstraints(); NavState getNavState(); double getThrottleDuration();
/*     */   double getTargetDeltaSquared();
/*     */   void setNavState(NavState paramNavState, double paramDouble1, double paramDouble2);
/*     */   void setForceRecomputePath(boolean paramBoolean);
/*     */   boolean isForceRecomputePath();
/*     */   boolean canRestAtPlace();
/*     */   void beforeInstructionSensorsAndActions(double paramDouble);
/*     */   void beforeInstructionMotion(double paramDouble);
/*     */   double getDesiredAltitudeWeight();
/*     */   double getHeightOverGround();
/*     */   void updatePhysicsValues(PhysicsValues paramPhysicsValues);
/*     */   public static class VerticalRange { public double current;
/*     */     public void assign(double current, double min, double max) {
/* 229 */       this.current = current;
/* 230 */       this.min = min;
/* 231 */       this.max = max;
/*     */     }
/*     */     public double min; public double max;
/*     */     public boolean isWithinRange() {
/* 235 */       return (this.current >= this.min && this.current <= this.max);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\controllers\MotionController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */