/*     */ package com.hypixel.hytale.server.flock.corecomponents;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderBodyMotionFlock;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.movement.GroupSteeringAccumulator;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BodyMotionFlock extends BodyMotionBase {
/*  23 */   private static final ComponentType<EntityStore, FlockMembership> FLOCK_MEMBERSHIP_COMPONENT_TYPE = FlockMembership.getComponentType();
/*  24 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*  25 */   private static final ComponentType<EntityStore, EntityGroup> ENTITY_GROUP_COMPONENT_TYPE = EntityGroup.getComponentType();
/*     */   
/*  27 */   protected final GroupSteeringAccumulator groupSteeringAccumulator = new GroupSteeringAccumulator();
/*     */   
/*     */   public BodyMotionFlock(@Nonnull BuilderBodyMotionFlock builderBodyMotionFlock) {
/*  30 */     super((BuilderBodyMotionBase)builderBodyMotionFlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  35 */     FlockMembership flockMembership = (FlockMembership)componentAccessor.getComponent(ref, FLOCK_MEMBERSHIP_COMPONENT_TYPE);
/*  36 */     Ref<EntityStore> flockReference = (flockMembership != null) ? flockMembership.getFlockRef() : null;
/*  37 */     if (flockReference == null || !flockReference.isValid()) return false;
/*     */     
/*  39 */     EntityGroup entityGroup = (EntityGroup)componentAccessor.getComponent(flockReference, ENTITY_GROUP_COMPONENT_TYPE);
/*  40 */     Vector3d componentSelector = role.getActiveMotionController().getComponentSelector();
/*  41 */     this.groupSteeringAccumulator.setComponentSelector(componentSelector);
/*  42 */     this.groupSteeringAccumulator.setMaxRange(role.getFlockInfluenceRange());
/*  43 */     this.groupSteeringAccumulator.begin(ref, componentAccessor);
/*     */     
/*  45 */     entityGroup.forEachMemberExcludingSelf((iGroupEntity, _entity, _groupSteeringAccumulator, _store) -> _groupSteeringAccumulator.processEntity(iGroupEntity, _store), ref, this.groupSteeringAccumulator, componentAccessor);
/*     */ 
/*     */     
/*  48 */     this.groupSteeringAccumulator.end();
/*     */ 
/*     */ 
/*     */     
/*  52 */     double weightCohesion = 1.0D;
/*  53 */     double weightSeparation = 1.0D;
/*  54 */     Ref<EntityStore> leaderRef = entityGroup.getLeaderRef();
/*  55 */     if (!leaderRef.isValid()) {
/*  56 */       return false;
/*     */     }
/*  58 */     Vector3d sumOfPositions = this.groupSteeringAccumulator.getSumOfPositions();
/*  59 */     Vector3d sumOfVelocities = this.groupSteeringAccumulator.getSumOfVelocities();
/*  60 */     Vector3d sumOfDistances = this.groupSteeringAccumulator.getSumOfDistances();
/*     */     
/*  62 */     TransformComponent leaderTransformComponent = (TransformComponent)componentAccessor.getComponent(leaderRef, TRANSFORM_COMPONENT_TYPE);
/*  63 */     assert leaderTransformComponent != null;
/*     */     
/*  65 */     Vector3d position = leaderTransformComponent.getPosition();
/*     */     
/*  67 */     Vector3d toLeader = new Vector3d(position.getX(), position.getY(), position.getZ());
/*     */     
/*  69 */     if (sumOfVelocities.squaredLength() > 1.0E-4D) {
/*  70 */       desiredSteering.setYaw(PhysicsMath.headingFromDirection(sumOfVelocities.getX(), sumOfVelocities.getZ()));
/*     */     } else {
/*  72 */       TransformComponent parentEntityTransformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  73 */       assert parentEntityTransformComponent != null;
/*     */       
/*  75 */       desiredSteering.setYaw(parentEntityTransformComponent.getRotation().getYaw());
/*     */     } 
/*     */     
/*  78 */     sumOfPositions.subtract(position.getX(), position.getY(), position.getZ()).scale(componentSelector);
/*  79 */     if (sumOfPositions.squaredLength() > 1.0E-4D) {
/*  80 */       sumOfPositions.normalize().scale(weightCohesion);
/*     */     } else {
/*  82 */       sumOfPositions.assign(0.0D);
/*     */     } 
/*  84 */     if (sumOfDistances.squaredLength() > 1.0E-4D) {
/*  85 */       sumOfDistances.normalize().scale(-weightSeparation);
/*     */     } else {
/*  87 */       sumOfDistances.assign(0.0D);
/*     */     } 
/*     */     
/*  90 */     toLeader.subtract(position.getX(), position.getY(), position.getZ()).scale(componentSelector);
/*  91 */     toLeader.normalize().scale(0.5D);
/*     */     
/*  93 */     sumOfPositions.add(sumOfDistances).add(toLeader);
/*  94 */     if (sumOfPositions.squaredLength() > 1.0E-4D) {
/*  95 */       sumOfPositions.normalize();
/*     */     } else {
/*  97 */       sumOfPositions.assign(0.0D);
/*     */     } 
/*     */     
/* 100 */     desiredSteering.setTranslation(sumOfPositions);
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\BodyMotionFlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */