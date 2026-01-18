/*     */ package com.hypixel.hytale.server.npc.corecomponents.combat;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderHeadMotionAim;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HeadMotionAim extends HeadMotionBase {
/*  25 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*  26 */   protected static final ComponentType<EntityStore, ModelComponent> MODEL_COMPONENT_TYPE = ModelComponent.getComponentType();
/*  27 */   protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/*     */   
/*     */   protected final double spread;
/*     */   
/*     */   protected final boolean deflection;
/*     */   protected final double hitProbability;
/*     */   protected final double relativeTurnSpeed;
/*  34 */   protected final AimingData aimingData = new AimingData();
/*     */   
/*     */   protected Ref<EntityStore> lastTargetReference;
/*     */   protected double spreadX;
/*     */   protected double spreadY;
/*     */   protected double spreadZ;
/*     */   
/*     */   public HeadMotionAim(@Nonnull BuilderHeadMotionAim builder, @Nonnull BuilderSupport support) {
/*  42 */     super((BuilderHeadMotionBase)builder);
/*  43 */     this.spread = builder.getSpread();
/*  44 */     this.hitProbability = builder.getHitProbability();
/*  45 */     this.deflection = builder.isDeflection();
/*  46 */     this.relativeTurnSpeed = builder.getRelativeTurnSpeed(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preComputeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, @Nonnull Store<EntityStore> store) {
/*  51 */     if (sensorInfo == null)
/*  52 */       return;  sensorInfo.passExtraInfo((ExtraInfoProvider)this.aimingData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  58 */     this.aimingData.setHaveAttacked(true);
/*     */   }
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role support, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     float pitch, yaw;
/*  63 */     if (sensorInfo == null || !sensorInfo.hasPosition()) {
/*  64 */       desiredSteering.clear();
/*  65 */       return true;
/*     */     } 
/*     */     
/*  68 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  69 */     assert transformComponent != null;
/*     */     
/*  71 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, MODEL_COMPONENT_TYPE);
/*  72 */     assert modelComponent != null;
/*     */     
/*  74 */     Vector3d position = transformComponent.getPosition();
/*  75 */     IPositionProvider positionProvider = sensorInfo.getPositionProvider();
/*     */     
/*  77 */     double x = positionProvider.getX() - position.getX();
/*  78 */     double y = positionProvider.getY() - position.getY() - modelComponent.getModel().getEyeHeight();
/*  79 */     double z = positionProvider.getZ() - position.getZ();
/*  80 */     double vx = 0.0D;
/*  81 */     double vy = 0.0D;
/*  82 */     double vz = 0.0D;
/*     */     
/*  84 */     Ref<EntityStore> targetRef = positionProvider.getTarget();
/*  85 */     if (targetRef != null) {
/*  86 */       Velocity targetVelocityComponent = (Velocity)componentAccessor.getComponent(targetRef, Velocity.getComponentType());
/*  87 */       assert targetVelocityComponent != null;
/*     */       
/*  89 */       BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BOUNDING_BOX_COMPONENT_TYPE);
/*  90 */       Box boundingBox = (boundingBoxComponent != null) ? boundingBoxComponent.getBoundingBox() : null;
/*     */       
/*  92 */       if (this.aimingData.isBallistic()) {
/*     */         
/*  94 */         if (boundingBox != null) {
/*  95 */           x += (boundingBox.getMax().getX() + boundingBox.getMin().getX()) / 2.0D;
/*  96 */           y += (boundingBox.getMax().getY() + boundingBox.getMin().getY()) / 2.0D;
/*  97 */           z += (boundingBox.getMax().getZ() + boundingBox.getMin().getZ()) / 2.0D;
/*     */         } 
/*  99 */         if (this.deflection) {
/* 100 */           Vector3d steeringVelocity = targetVelocityComponent.getVelocity();
/* 101 */           vx = steeringVelocity.getX();
/* 102 */           vy = steeringVelocity.getY();
/* 103 */           vz = steeringVelocity.getZ();
/*     */         }
/*     */       
/* 106 */       } else if (boundingBox != null) {
/* 107 */         double minY = y + (boundingBox.getMin()).y;
/* 108 */         double maxY = y + (boundingBox.getMax()).y;
/*     */         
/* 110 */         if (minY > 0.0D) {
/*     */           
/* 112 */           y = minY;
/* 113 */         } else if (maxY < 0.0D) {
/*     */           
/* 115 */           y = maxY;
/*     */         } else {
/*     */           
/* 118 */           y = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 124 */     if (this.aimingData.isBallistic()) {
/* 125 */       BallisticData ballisticData = this.aimingData.getBallisticData();
/* 126 */       if (ballisticData != null) {
/*     */         
/* 128 */         y += ballisticData.getVerticalCenterShot();
/* 129 */         this.aimingData.setDepthOffset(ballisticData.getDepthShot(), ballisticData.isPitchAdjustShot());
/*     */       } else {
/* 131 */         this.aimingData.setDepthOffset(0.0D, false);
/*     */       } 
/*     */       
/* 134 */       if (targetRef != null && (this.lastTargetReference == null || !this.lastTargetReference.equals(targetRef))) {
/* 135 */         this.lastTargetReference = targetRef;
/*     */         
/* 137 */         this.aimingData.setHaveAttacked(true);
/*     */       } 
/*     */       
/* 140 */       if (this.aimingData.isHaveAttacked()) {
/* 141 */         ThreadLocalRandom random = ThreadLocalRandom.current();
/* 142 */         if (this.spread > 0.0D && random.nextDouble() > this.hitProbability) {
/*     */ 
/*     */ 
/*     */           
/* 146 */           double spread2 = 2.0D * this.spread * Math.sqrt(NPCPhysicsMath.dotProduct(x, y, z)) / 10.0D;
/* 147 */           this.spreadX += spread2 * (random.nextDouble() - 0.5D);
/* 148 */           this.spreadY += spread2 * (random.nextDouble() - 0.5D);
/* 149 */           this.spreadZ += spread2 * (random.nextDouble() - 0.5D);
/*     */         } else {
/* 151 */           this.spreadX = 0.0D;
/* 152 */           this.spreadY = 0.0D;
/* 153 */           this.spreadZ = 0.0D;
/*     */         } 
/* 155 */         this.aimingData.setHaveAttacked(false);
/*     */       } 
/* 157 */       x += this.spreadX;
/* 158 */       y += this.spreadY;
/* 159 */       z += this.spreadZ;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (this.aimingData.computeSolution(x, y, z, vx, vy, vz)) {
/* 166 */       yaw = this.aimingData.getYaw();
/* 167 */       pitch = this.aimingData.getPitch();
/* 168 */       this.aimingData.setTarget(targetRef);
/*     */     } else {
/* 170 */       HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 171 */       assert headRotationComponent != null;
/*     */       
/* 173 */       double xxzz = x * x + z * z;
/* 174 */       double xxyyzz = xxzz + y * y;
/* 175 */       Vector3f headRotation = headRotationComponent.getRotation();
/* 176 */       yaw = (xxzz >= 1.0E-4D) ? PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z)) : headRotation.getYaw();
/* 177 */       pitch = (xxyyzz >= 1.0E-4D) ? PhysicsMath.pitchFromDirection(x, y, z) : headRotation.getPitch();
/* 178 */       this.aimingData.setOrientation(yaw, pitch);
/* 179 */       this.aimingData.setTarget(null);
/*     */     } 
/* 181 */     desiredSteering.clearTranslation();
/* 182 */     desiredSteering.setYaw(yaw);
/* 183 */     desiredSteering.setPitch(pitch);
/* 184 */     desiredSteering.setRelativeTurnSpeed(this.relativeTurnSpeed);
/* 185 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\combat\HeadMotionAim.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */