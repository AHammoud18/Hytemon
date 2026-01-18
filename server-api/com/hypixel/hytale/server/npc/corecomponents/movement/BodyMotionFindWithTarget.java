/*     */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFindBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFindWithTarget;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarBase;
/*     */ import com.hypixel.hytale.server.npc.navigation.AStarWithTarget;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public abstract class BodyMotionFindWithTarget
/*     */   extends BodyMotionFindBase<AStarWithTarget>
/*     */ {
/*     */   protected final double minMoveDistanceWait;
/*     */   protected final double minMoveDistanceWaitSquared;
/*     */   protected final double minMoveDistanceRecompute;
/*     */   protected final double minMoveDistanceRecomputeSquared;
/*     */   protected final float cosHalfRecomputeConeAngle;
/*     */   protected final double minMoveDistanceReproject;
/*     */   protected final double minMoveDistanceReprojectSquared;
/*     */   protected final boolean adjustRangeByHitboxSize;
/*  43 */   protected final Vector3d lastPathedPosition = new Vector3d();
/*  44 */   protected final Vector3d conePosition = new Vector3d();
/*  45 */   protected final Vector3d coneDirection = new Vector3d();
/*     */   
/*     */   @Nullable
/*     */   protected Box targetBoundingBox;
/*     */   
/*     */   protected Box selfBoundingBox;
/*     */   
/*     */   protected boolean waitForTargetMovement = false;
/*  53 */   private final Vector3d lastTargetPosition = new Vector3d();
/*  54 */   private final Vector3d lastAccessibleTargetPosition = new Vector3d();
/*     */   
/*     */   private boolean haveValidTargetPosition;
/*     */   
/*     */   private boolean haveAccessibleTargetPosition;
/*     */   private boolean lastAccessibleTargetPositionIsCurrent;
/*     */   protected String self;
/*     */   protected String other;
/*     */   
/*     */   public BodyMotionFindWithTarget(@Nonnull BuilderBodyMotionFindWithTarget builderMotionFindWithTarget, @Nonnull BuilderSupport support) {
/*  64 */     super((BuilderBodyMotionFindBase)builderMotionFindWithTarget, support, new AStarWithTarget());
/*     */     
/*  66 */     this.adjustRangeByHitboxSize = builderMotionFindWithTarget.isAdjustRangeByHitboxSize(support);
/*  67 */     this.minMoveDistanceWait = builderMotionFindWithTarget.getMinMoveDistanceWait(support);
/*  68 */     this.minMoveDistanceWaitSquared = this.minMoveDistanceWait * this.minMoveDistanceWait;
/*  69 */     this.minMoveDistanceRecompute = builderMotionFindWithTarget.getMinMoveDistanceRecompute(support);
/*  70 */     this.minMoveDistanceRecomputeSquared = this.minMoveDistanceRecompute * this.minMoveDistanceRecompute;
/*  71 */     this.minMoveDistanceReproject = builderMotionFindWithTarget.getMinMoveDistanceReproject(support);
/*  72 */     this.minMoveDistanceReprojectSquared = this.minMoveDistanceReproject * this.minMoveDistanceReproject;
/*  73 */     float cosine = TrigMathUtil.cos(builderMotionFindWithTarget.getRecomputeConeAngle(support) / 2.0D);
/*  74 */     this.cosHalfRecomputeConeAngle = (cosine == -1.0F) ? 1.0F : cosine;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  79 */     super.activate(ref, role, componentAccessor);
/*  80 */     this.haveValidTargetPosition = false;
/*  81 */     this.haveAccessibleTargetPosition = false;
/*  82 */     this.waitForTargetMovement = false;
/*  83 */     this.targetBoundingBox = null;
/*  84 */     this.lastPathedPosition.assign(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
/*  85 */     this.self = role.getRoleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canComputeMotion(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider infoProvider, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  90 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BoundingBox.getComponentType());
/*  91 */     assert boundingBoxComponent != null;
/*     */     
/*  93 */     this.targetBoundingBox = null;
/*  94 */     this.selfBoundingBox = boundingBoxComponent.getBoundingBox();
/*  95 */     this.lastAccessibleTargetPositionIsCurrent = false;
/*  96 */     if (infoProvider != null && infoProvider.hasPosition()) {
/*     */       
/*  98 */       IPositionProvider positionProvider = infoProvider.getPositionProvider();
/*  99 */       if (positionProvider.providePosition(this.lastTargetPosition)) {
/* 100 */         this.haveValidTargetPosition = true;
/*     */         
/* 102 */         Ref<EntityStore> targetEntityReference = positionProvider.getTarget();
/* 103 */         if (targetEntityReference != null) {
/* 104 */           BoundingBox targetEntityBoundingBoxComponent = (BoundingBox)componentAccessor.getComponent(targetEntityReference, BoundingBox.getComponentType());
/* 105 */           assert targetEntityBoundingBoxComponent != null;
/*     */           
/* 107 */           NPCEntity npcEntityComponent = (NPCEntity)componentAccessor.getComponent(targetEntityReference, NPCEntity.getComponentType());
/* 108 */           this.other = (npcEntityComponent != null) ? npcEntityComponent.getRoleName() : null;
/* 109 */           this.targetBoundingBox = targetEntityBoundingBoxComponent.getBoundingBox();
/*     */ 
/*     */           
/* 112 */           MovementStatesComponent movementStatesComponent = (MovementStatesComponent)componentAccessor.getComponent(targetEntityReference, MovementStatesComponent.getComponentType());
/*     */           
/* 114 */           Entity entity = EntityUtils.getEntity(targetEntityReference, (ComponentAccessor)targetEntityReference.getStore());
/* 115 */           if (entity instanceof com.hypixel.hytale.server.core.entity.LivingEntity && movementStatesComponent != null && (movementStatesComponent.getMovementStates()).onGround) {
/* 116 */             this.lastAccessibleTargetPosition.assign(this.lastTargetPosition);
/* 117 */             this.haveAccessibleTargetPosition = true;
/* 118 */             this.lastAccessibleTargetPositionIsCurrent = true;
/*     */           } 
/*     */         } else {
/* 121 */           this.targetBoundingBox = null;
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     this.targetDeltaSquared = this.haveValidTargetPosition ? role.getActiveMotionController().waypointDistanceSquared(getLastTargetPosition(), this.lastPathedPosition) : Double.MAX_VALUE;
/* 126 */     return this.haveValidTargetPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mustRecomputePath(@Nonnull MotionController activeMotionController) {
/* 131 */     if (super.mustRecomputePath(activeMotionController)) return true;
/*     */     
/* 133 */     if (this.minMoveDistanceRecomputeSquared > 0.0D && this.targetDeltaSquared > this.minMoveDistanceRecomputeSquared) {
/* 134 */       if (this.dbgStatus) NPCPlugin.get().getLogger().at(Level.INFO).log("Recomputing Path - Target moved"); 
/* 135 */       resetThrottleCount();
/* 136 */       return true;
/*     */     } 
/*     */     
/* 139 */     if (this.cosHalfRecomputeConeAngle < 1.0F) {
/* 140 */       if (activeMotionController.is2D()) {
/* 141 */         if (NPCPhysicsMath.isInViewCone(this.conePosition, this.coneDirection, this.cosHalfRecomputeConeAngle, getLastTargetPosition(), activeMotionController.getComponentSelector())) {
/* 142 */           if (this.dbgStatus) {
/* 143 */             NPCPlugin.get().getLogger().at(Level.INFO).log("Recomputing Path - Target left 2D cone");
/*     */           }
/* 145 */           return true;
/*     */         }
/*     */       
/* 148 */       } else if (NPCPhysicsMath.isInViewCone(this.conePosition, this.coneDirection, this.cosHalfRecomputeConeAngle, getLastTargetPosition())) {
/* 149 */         if (this.dbgStatus) {
/* 150 */           NPCPlugin.get().getLogger().at(Level.INFO).log("Recomputing Path - Target left cone");
/*     */         }
/* 152 */         return true;
/*     */       } 
/*     */     }
/*     */     
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceRecomputePath(MotionController activeMotionController) {
/* 161 */     super.forceRecomputePath(activeMotionController);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDeferPathComputation(@Nonnull MotionController motionController, Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 166 */     if (this.throttleCount > this.throttleIgnoreCount) {
/* 167 */       double distanceSquared = getLastTargetPosition().distanceSquaredTo(this.lastPathedPosition);
/* 168 */       if (distanceSquared < 1.0000000000000002E-10D || (this.waitForTargetMovement && distanceSquared < this.minMoveDistanceWaitSquared)) {
/* 169 */         return true;
/*     */       }
/*     */     } 
/* 172 */     this.waitForTargetMovement = false;
/* 173 */     this.lastPathedPosition.assign(getLastAccessibleTargetPosition(motionController, false, componentAccessor));
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mustAbortThrottling(MotionController motionController, Ref<EntityStore> ref) {
/* 179 */     if (super.mustAbortThrottling(motionController, ref)) return true; 
/* 180 */     if (this.minMoveDistanceRecomputeSquared > 0.0D && this.targetDeltaSquared > this.minMoveDistanceRecomputeSquared) {
/* 181 */       if (this.dbgMotionState) {
/* 182 */         NPCPlugin.get().getLogger().at(Level.INFO).log("MotionFindWithTarget: Aborting throttling - Target moved");
/*     */       }
/* 184 */       return true;
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGoalReached(Ref<EntityStore> ref, MotionController activeMotionController, Vector3d position, ComponentAccessor<EntityStore> componentAccessor) {
/* 191 */     return isGoalReached(ref, activeMotionController, position, getLastTargetPosition(), componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public AStarBase.Progress startComputePath(@Nonnull Ref<EntityStore> ref, Role role, @Nonnull MotionController activeMotionController, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 196 */     if (this.cosHalfRecomputeConeAngle < 1.0F) {
/* 197 */       this.conePosition.assign(position);
/* 198 */       this.coneDirection.assign(this.lastPathedPosition).subtract(position);
/*     */     } 
/* 200 */     return this.aStar.initComputePath(ref, position, this.lastPathedPosition, this, activeMotionController, this.probeMoveData, this.sharedNodePoolProvider, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockedPath() {
/* 205 */     super.onBlockedPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNoPathFound(MotionController motionController) {
/* 210 */     super.onNoPathFound(motionController);
/* 211 */     this.waitForTargetMovement = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSteering(MotionController activeMotionController, @Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 216 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 217 */     assert transformComponent != null;
/*     */     
/* 219 */     this.lastPathedPosition.assign(transformComponent.getPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decorateDebugString(@Nonnull StringBuilder dbgString) {
/* 224 */     dbgString.append("D:").append(MathUtil.round(Math.sqrt(this.targetDeltaSquared), 1));
/*     */   }
/*     */   
/*     */   protected abstract boolean isGoalReached(Ref<EntityStore> paramRef, MotionController paramMotionController, Vector3d paramVector3d1, Vector3d paramVector3d2, ComponentAccessor<EntityStore> paramComponentAccessor);
/*     */   
/*     */   protected Vector3d getLastTargetPosition() {
/* 230 */     return this.lastTargetPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector3d getLastAccessibleTargetPosition(@Nonnull MotionController motionController, boolean approximate, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 236 */     if (this.lastAccessibleTargetPositionIsCurrent || (approximate && this.haveAccessibleTargetPosition && motionController.waypointDistanceSquared(this.lastTargetPosition, this.lastAccessibleTargetPosition) < this.minMoveDistanceReprojectSquared)) {
/* 237 */       return this.lastAccessibleTargetPosition;
/*     */     }
/*     */     
/* 240 */     if (this.dbgMotionState && motionController.is2D()) {
/* 241 */       NPCPlugin.get().getLogger().at(Level.INFO).log("MotionFindWithTarget: Reprojecting %s -> %s", this.self, this.other);
/*     */     }
/*     */ 
/*     */     
/* 245 */     this.lastAccessibleTargetPosition.assign(this.lastTargetPosition);
/* 246 */     motionController.translateToAccessiblePosition(this.lastAccessibleTargetPosition, this.targetBoundingBox, 0.0D, 320.0D, componentAccessor);
/* 247 */     this.haveAccessibleTargetPosition = true;
/* 248 */     this.lastAccessibleTargetPositionIsCurrent = true;
/* 249 */     return this.lastAccessibleTargetPosition;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionFindWithTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */