/*     */ package com.hypixel.hytale.server.npc.corecomponents.combat;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderBodyMotionAimCharge;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.ProbeMoveData;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.ExtraInfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.AimingData;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BodyMotionAimCharge extends BodyMotionBase {
/*  25 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   protected final double relativeTurnSpeed;
/*     */   
/*  29 */   protected final AimingData aimingData = new AimingData();
/*  30 */   protected final Vector3d direction = new Vector3d();
/*  31 */   protected final ProbeMoveData probeMoveData = new ProbeMoveData();
/*     */   
/*     */   public BodyMotionAimCharge(@Nonnull BuilderBodyMotionAimCharge builder, @Nonnull BuilderSupport support) {
/*  34 */     super((BuilderBodyMotionBase)builder);
/*  35 */     this.relativeTurnSpeed = builder.getRelativeTurnSpeed(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preComputeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, @Nonnull Store<EntityStore> store) {
/*  40 */     if (sensorInfo == null)
/*  41 */       return;  sensorInfo.passExtraInfo((ExtraInfoProvider)this.aimingData);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  46 */     if (sensorInfo == null || !sensorInfo.getPositionProvider().providePosition(this.direction)) {
/*  47 */       desiredSteering.clear();
/*  48 */       return true;
/*     */     } 
/*     */     
/*  51 */     if (this.aimingData.isHaveAttacked()) {
/*  52 */       if (role.getCombatSupport().isExecutingAttack()) {
/*     */         
/*  54 */         desiredSteering.clear();
/*  55 */         return true;
/*     */       } 
/*  57 */       this.aimingData.setHaveAttacked(false);
/*     */     } 
/*     */ 
/*     */     
/*  61 */     Vector3d selfPosition = ((TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE)).getPosition();
/*  62 */     double distanceToTarget = role.getActiveMotionController().waypointDistance(selfPosition, this.direction);
/*  63 */     if (distanceToTarget > this.aimingData.getChargeDistance()) {
/*     */       
/*  65 */       this.aimingData.clearSolution();
/*  66 */       return true;
/*     */     } 
/*     */     
/*  69 */     this.direction.subtract(selfPosition);
/*  70 */     this.direction.setLength(this.aimingData.getChargeDistance());
/*     */     
/*  72 */     double x = this.direction.getX();
/*  73 */     double y = this.direction.getY();
/*  74 */     double z = this.direction.getZ();
/*  75 */     float yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/*  76 */     float pitch = PhysicsMath.pitchFromDirection(x, y, z);
/*  77 */     desiredSteering.setYaw(yaw);
/*  78 */     desiredSteering.setPitch(pitch);
/*  79 */     desiredSteering.setRelativeTurnSpeed(this.relativeTurnSpeed);
/*     */     
/*  81 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  82 */     Vector3f bodyRotation = transformComponent.getRotation();
/*  83 */     this.aimingData.setOrientation(yaw, pitch);
/*  84 */     if (!this.aimingData.isOnTarget(bodyRotation.getYaw(), bodyRotation.getPitch(), this.aimingData.getDesiredHitAngle())) {
/*     */       
/*  86 */       this.aimingData.clearSolution();
/*  87 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  91 */     double distance = role.getActiveMotionController().probeMove(ref, selfPosition, this.direction, this.probeMoveData, componentAccessor);
/*  92 */     if (distance < distanceToTarget - 1.0E-6D) {
/*     */       
/*  94 */       this.aimingData.clearSolution();
/*  95 */       return true;
/*     */     } 
/*     */     
/*  98 */     Ref<EntityStore> target = sensorInfo.getPositionProvider().getTarget();
/*  99 */     this.aimingData.setTarget(target);
/* 100 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\combat\BodyMotionAimCharge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */