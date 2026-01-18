/*     */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.camera.CameraSettings;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.HeadMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderHeadMotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderHeadMotionObserve;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.steeringforces.SteeringForceRotate;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HeadMotionObserve extends HeadMotionBase {
/*     */   protected final float[] angleRange;
/*     */   protected final double[] pauseTimeRange;
/*     */   protected final boolean pickRandomAngle;
/*     */   protected final int viewSegments;
/*     */   protected final double relativeTurnSpeed;
/*     */   protected double preDelay;
/*     */   protected double delay;
/*     */   protected int currentViewSegment;
/*     */   protected boolean invertedDirection;
/*     */   protected float targetBodyOffsetYaw;
/*  36 */   protected final SteeringForceRotate steeringForceRotate = new SteeringForceRotate();
/*     */   
/*     */   public HeadMotionObserve(@Nonnull BuilderHeadMotionObserve builder, @Nonnull BuilderSupport support) {
/*  39 */     super((BuilderHeadMotionBase)builder);
/*  40 */     this.angleRange = builder.getAngleRange(support);
/*  41 */     this.pauseTimeRange = builder.getPauseTimeRange(support);
/*  42 */     this.pickRandomAngle = builder.isPickRandomAngle(support);
/*  43 */     this.viewSegments = builder.getViewSegments(support);
/*  44 */     this.relativeTurnSpeed = builder.getRelativeTurnSpeed(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  49 */     this.preDelay = RandomExtra.randomRange(this.pauseTimeRange);
/*  50 */     this.currentViewSegment = 0;
/*  51 */     pickNextAngle(ref, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  56 */     if (tickPreDelay(dt)) return true;
/*     */     
/*  58 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  59 */     assert transformComponent != null;
/*     */     
/*  61 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  62 */     assert headRotationComponent != null;
/*     */     
/*  64 */     Vector3f headRotation = headRotationComponent.getRotation();
/*     */     
/*  66 */     this.steeringForceRotate.setHeading(headRotation.getYaw());
/*  67 */     this.steeringForceRotate.setDesiredHeading(transformComponent.getRotation().getYaw() + this.targetBodyOffsetYaw);
/*     */     
/*  69 */     if (this.steeringForceRotate.compute(desiredSteering)) {
/*  70 */       desiredSteering.setRelativeTurnSpeed(this.relativeTurnSpeed);
/*  71 */       return true;
/*     */     } 
/*  73 */     desiredSteering.setYaw(transformComponent.getRotation().getYaw() + this.targetBodyOffsetYaw);
/*     */ 
/*     */     
/*  76 */     if (tickDelay(dt)) return true; 
/*  77 */     pickNextAngle(ref, componentAccessor);
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean tickPreDelay(double dt) {
/*  82 */     if (this.preDelay > 0.0D) {
/*  83 */       this.preDelay -= dt;
/*  84 */       return true;
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean tickDelay(double dt) {
/*  90 */     if (this.delay > 0.0D) {
/*  91 */       this.delay -= dt;
/*  92 */       return true;
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */   protected void pickNextAngle(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     float limitMin, limitMax;
/*  98 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/*  99 */     assert modelComponent != null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     CameraSettings headRotationRestrictions = modelComponent.getModel().getCamera();
/* 105 */     if (headRotationRestrictions != null && headRotationRestrictions.getYaw() != null && headRotationRestrictions.getYaw().getAngleRange() != null) {
/* 106 */       Rangef yawRange = headRotationRestrictions.getYaw().getAngleRange();
/* 107 */       limitMin = yawRange.min;
/* 108 */       limitMax = yawRange.max;
/*     */     } else {
/*     */       
/* 111 */       limitMin = -0.7853982F;
/* 112 */       limitMax = 0.7853982F;
/*     */     } 
/* 114 */     float min = Math.max(this.angleRange[0], limitMin);
/* 115 */     float max = Math.min(this.angleRange[1], limitMax);
/*     */     
/* 117 */     if (this.pickRandomAngle) {
/* 118 */       this.targetBodyOffsetYaw = RandomExtra.randomRange(min, max);
/* 119 */     } else if (this.viewSegments > 1) {
/* 120 */       float fullSector = MathUtil.wrapAngle(max - min);
/* 121 */       float segment = fullSector / (this.viewSegments - 1);
/* 122 */       int thisSegment = this.currentViewSegment++;
/* 123 */       this.currentViewSegment %= this.viewSegments;
/* 124 */       this.targetBodyOffsetYaw = min + thisSegment * segment;
/* 125 */     } else if (!this.invertedDirection) {
/* 126 */       this.targetBodyOffsetYaw = min;
/* 127 */       this.invertedDirection = true;
/*     */     } else {
/* 129 */       this.targetBodyOffsetYaw = max;
/* 130 */       this.invertedDirection = false;
/*     */     } 
/*     */     
/* 133 */     this.delay = RandomExtra.randomRange(this.pauseTimeRange);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\HeadMotionObserve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */