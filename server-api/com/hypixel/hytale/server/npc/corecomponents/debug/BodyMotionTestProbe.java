/*     */ package com.hypixel.hytale.server.npc.corecomponents.debug;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.debug.builders.BuilderBodyMotionTestProbe;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.ProbeMoveData;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class BodyMotionTestProbe
/*     */   extends BodyMotionBase
/*     */ {
/*     */   protected final double adjustX;
/*     */   protected final double adjustZ;
/*     */   protected final double adjustDistance;
/*     */   protected final float snapAngle;
/*     */   protected boolean displayText;
/*  31 */   protected final Vector3d direction = new Vector3d();
/*  32 */   protected final ProbeMoveData probeMoveData = new ProbeMoveData();
/*     */   
/*     */   public BodyMotionTestProbe(@Nonnull BuilderBodyMotionTestProbe builderBodyMotionTestProbe) {
/*  35 */     super((BuilderBodyMotionBase)builderBodyMotionTestProbe);
/*  36 */     this.probeMoveData.setSaveSegments(true);
/*  37 */     this.adjustX = builderBodyMotionTestProbe.getAdjustX();
/*  38 */     this.adjustZ = builderBodyMotionTestProbe.getAdjustZ();
/*  39 */     this.adjustDistance = builderBodyMotionTestProbe.getAdjustDistance();
/*  40 */     this.snapAngle = builderBodyMotionTestProbe.getSnapAngle() * 0.017453292F;
/*  41 */     this.probeMoveData.setAvoidingBlockDamage(builderBodyMotionTestProbe.isAvoidingBlockDamage());
/*  42 */     this.probeMoveData.setRelaxedMoveConstraints(builderBodyMotionTestProbe.isRelaxedMoveConstraints());
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  47 */     this.displayText = role.getDebugSupport().isDebugFlagSet(RoleDebugFlags.DisplayCustom);
/*  48 */     if (this.adjustX < 0.0D && this.adjustZ < 0.0D)
/*     */       return; 
/*  50 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  51 */     if (transformComponent == null)
/*     */       return; 
/*  53 */     Vector3d position = transformComponent.getPosition();
/*  54 */     double x = position.x;
/*  55 */     double z = position.z;
/*     */     
/*  57 */     if (this.adjustX >= 0.0D) x = MathUtil.fastFloor(x) + this.adjustX; 
/*  58 */     if (this.adjustZ >= 0.0D) z = MathUtil.fastFloor(z) + this.adjustZ;
/*     */     
/*  60 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/*  61 */     assert npcComponent != null;
/*  62 */     npcComponent.moveTo(ref, x, position.y, z, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  67 */     desiredSteering.clear();
/*     */     
/*  69 */     if (sensorInfo == null || !sensorInfo.getPositionProvider().providePosition(this.direction)) return false;
/*     */     
/*  71 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  72 */     if (transformComponent == null) return false;
/*     */     
/*  74 */     Vector3d position = transformComponent.getPosition();
/*  75 */     this.direction.subtract(position);
/*     */     
/*  77 */     if (!this.displayText) return true;
/*     */     
/*  79 */     double length = this.direction.length();
/*  80 */     if (length <= 1.0E-6D) return true;
/*     */     
/*  82 */     if (this.adjustDistance > 0.0D) {
/*  83 */       length = this.adjustDistance;
/*  84 */       this.direction.setLength(this.adjustDistance);
/*     */     } 
/*     */     
/*  87 */     double x = this.direction.getX();
/*  88 */     double y = this.direction.getY();
/*  89 */     double z = this.direction.getZ();
/*     */     
/*  91 */     float yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/*  92 */     float pitch = PhysicsMath.pitchFromDirection(x, y, z);
/*     */     
/*  94 */     if (this.snapAngle > 0.0F && this.snapAngle < 3.1415927F) {
/*  95 */       yaw = MathUtil.fastRound(yaw / this.snapAngle) * this.snapAngle;
/*  96 */       PhysicsMath.vectorFromAngles(yaw, pitch, this.direction).setLength(length);
/*     */     } 
/*     */     
/*  99 */     desiredSteering.setYaw(yaw);
/* 100 */     desiredSteering.setPitch(pitch);
/*     */     
/* 102 */     double distance = role.getActiveMotionController().probeMove(ref, position, this.direction, this.probeMoveData, componentAccessor);
/*     */     
/* 104 */     role.getDebugSupport().setDisplayCustomString(String.format("PR: %.2f DX: %.2f DZ: %.2f", new Object[] { Double.valueOf(distance), 
/* 105 */             Double.valueOf(this.direction.x), Double.valueOf(this.direction.z) }));
/* 106 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\debug\BodyMotionTestProbe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */