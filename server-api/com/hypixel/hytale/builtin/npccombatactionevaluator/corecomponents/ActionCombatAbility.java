/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderActionCombatAbility;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.SingleCollector;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.BallisticData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.interactions.NPCInteractionSimulationHandler;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.DoubleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.util.AimingData;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionCombatAbility extends ActionBase {
/*  32 */   protected static final ComponentType<EntityStore, CombatActionEvaluator> COMPONENT_TYPE = CombatActionEvaluator.getComponentType();
/*  33 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   protected static final float POSITIONING_ANGLE_THRESHOLD = 0.08726646F;
/*     */   
/*     */   protected final int id;
/*     */   protected final int positioningAngleProviderSlot;
/*  39 */   protected final double meleeConeAngle = 0.2617993950843811D;
/*     */   
/*     */   @Nullable
/*     */   protected String attack;
/*     */   
/*     */   protected DoubleParameterProvider cachedPositioningAngleProvider;
/*     */   protected boolean initialised;
/*     */   
/*     */   public ActionCombatAbility(@Nonnull BuilderActionCombatAbility builder, @Nonnull BuilderSupport builderSupport) {
/*  48 */     super((BuilderActionBase)builder);
/*  49 */     this.id = builderSupport.getNextAttackIndex();
/*  50 */     this.positioningAngleProviderSlot = builderSupport.getParameterSlot("PositioningAngle");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  55 */     if (!this.initialised) {
/*     */       
/*  57 */       if (sensorInfo != null) {
/*  58 */         ParameterProvider parameterProvider = sensorInfo.getParameterProvider(this.positioningAngleProviderSlot);
/*  59 */         if (parameterProvider instanceof DoubleParameterProvider) {
/*  60 */           this.cachedPositioningAngleProvider = (DoubleParameterProvider)parameterProvider;
/*     */         }
/*     */       } 
/*  63 */       this.initialised = true;
/*     */     } 
/*     */     
/*  66 */     if (!super.canExecute(ref, role, sensorInfo, dt, store)) return false;
/*     */     
/*  68 */     CombatActionEvaluator combatActionEvaluator = (CombatActionEvaluator)ref.getStore().getComponent(ref, COMPONENT_TYPE);
/*  69 */     if (combatActionEvaluator == null) return false;
/*     */     
/*  71 */     return (combatActionEvaluator.getCurrentAttack() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  76 */     CombatActionEvaluator combatActionEvaluatorComponent = (CombatActionEvaluator)store.getComponent(ref, COMPONENT_TYPE);
/*  77 */     assert combatActionEvaluatorComponent != null;
/*     */     
/*  79 */     InteractionManager interactionManagerComponent = (InteractionManager)store.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/*  80 */     assert interactionManagerComponent != null;
/*     */     
/*  82 */     AimingData aimingDataInfo = (sensorInfo != null) ? (AimingData)sensorInfo.getPassedExtraInfo(AimingData.class) : null;
/*  83 */     AimingData aimingData = (aimingDataInfo != null && aimingDataInfo.isClaimedBy(this.id)) ? aimingDataInfo : null;
/*  84 */     boolean requireAiming = combatActionEvaluatorComponent.requiresAiming();
/*  85 */     String nextAttack = combatActionEvaluatorComponent.getCurrentAttack();
/*  86 */     if (!nextAttack.equals(this.attack)) {
/*  87 */       this.attack = nextAttack;
/*     */       
/*  89 */       if (requireAiming && aimingData != null) {
/*     */         
/*  91 */         SingleCollector<BallisticData> collector = ActionAttack.THREAD_LOCAL_COLLECTOR.get();
/*  92 */         interactionManagerComponent.walkChain(ref, (Collector)collector, InteractionType.Primary, (RootInteraction)RootInteraction.getAssetMap().getAsset(this.attack), (ComponentAccessor)store);
/*     */         
/*  94 */         BallisticData ballisticData = (BallisticData)collector.getResult();
/*  95 */         if (ballisticData != null) {
/*  96 */           aimingData.requireBallistic(ballisticData);
/*  97 */           aimingData.setUseFlatTrajectory(true);
/*     */         } else {
/*  99 */           double chargeDistance = combatActionEvaluatorComponent.getChargeDistance();
/* 100 */           if (chargeDistance > 0.0D) {
/* 101 */             aimingData.setChargeDistance(chargeDistance);
/* 102 */             aimingData.setDesiredHitAngle(0.2617993950843811D);
/*     */           } 
/* 104 */           aimingData.requireCloseCombat();
/*     */         } 
/* 106 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 111 */     assert transformComponent != null;
/*     */     
/* 113 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 114 */     assert headRotationComponent != null;
/*     */     
/* 116 */     Vector3f rotation = (aimingData != null && aimingData.getChargeDistance() > 0.0D) ? transformComponent.getRotation() : headRotationComponent.getRotation();
/* 117 */     if (aimingData != null && !aimingData.isOnTarget(rotation.getYaw(), rotation.getPitch(), 0.2617993950843811D)) {
/* 118 */       aimingData.clearSolution();
/*     */       
/* 120 */       return false;
/*     */     } 
/*     */     
/* 123 */     Ref<EntityStore> target = (aimingData != null) ? aimingData.getTarget() : null;
/*     */     
/* 125 */     if (requireAiming && (
/* 126 */       target == null || !role.getPositionCache().hasLineOfSight(ref, target, (ComponentAccessor)store))) {
/* 127 */       if (aimingData != null) aimingData.clearSolution();
/*     */       
/* 129 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (combatActionEvaluatorComponent.shouldPositionFirst()) {
/* 135 */       double positioningAngle = Double.MAX_VALUE;
/* 136 */       if (this.cachedPositioningAngleProvider != null) {
/* 137 */         positioningAngle = this.cachedPositioningAngleProvider.getDoubleParameter();
/*     */       }
/* 139 */       if (positioningAngle != Double.MAX_VALUE) {
/* 140 */         if (target == null) return false;
/*     */         
/* 142 */         TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(target, TRANSFORM_COMPONENT_TYPE);
/* 143 */         assert targetTransformComponent != null;
/*     */         
/* 145 */         Vector3d targetPosition = targetTransformComponent.getPosition();
/*     */         
/* 147 */         float selfYaw = NPCPhysicsMath.lookatHeading(transformComponent.getPosition(), targetPosition, transformComponent.getRotation().getYaw());
/* 148 */         float difference = PhysicsMath.normalizeTurnAngle(targetTransformComponent.getRotation().getYaw() - selfYaw - (float)positioningAngle);
/* 149 */         if (Math.abs(difference) > 0.08726646F)
/*     */         {
/* 151 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     boolean damageFriendlies = combatActionEvaluatorComponent.shouldDamageFriendlies();
/* 157 */     if (!damageFriendlies && 
/* 158 */       target != null && role.getPositionCache().isFriendlyBlockingLineOfSight(ref, target, (ComponentAccessor)store)) {
/* 159 */       aimingData.clearSolution();
/*     */       
/* 161 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     IInteractionSimulationHandler interactionSimulationHandler = interactionManagerComponent.getInteractionSimulationHandler();
/* 166 */     if (interactionSimulationHandler instanceof NPCInteractionSimulationHandler) { NPCInteractionSimulationHandler npcInteractionSimulationHandler = (NPCInteractionSimulationHandler)interactionSimulationHandler;
/* 167 */       npcInteractionSimulationHandler.requestChargeTime(combatActionEvaluatorComponent.getChargeFor()); }
/*     */ 
/*     */     
/* 170 */     InteractionType interactionType = combatActionEvaluatorComponent.getCurrentInteractionType();
/* 171 */     InteractionContext context = InteractionContext.forInteraction(interactionManagerComponent, ref, interactionType, (ComponentAccessor)store);
/* 172 */     context.setInteractionVarsGetter(combatActionEvaluatorComponent.getCurrentInteractionVarsGetter());
/*     */     
/* 174 */     InteractionChain chain = interactionManagerComponent.initChain(interactionType, context, RootInteraction.getRootInteractionOrUnknown(this.attack), false);
/* 175 */     interactionManagerComponent.queueExecuteChain(chain);
/* 176 */     role.getCombatSupport().setExecutingAttack(chain, damageFriendlies, 0.0D);
/* 177 */     if (aimingData != null) aimingData.setHaveAttacked(true);
/*     */ 
/*     */     
/* 180 */     combatActionEvaluatorComponent.completeCurrentAction(false, true);
/* 181 */     this.attack = null;
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(Role role, @Nullable InfoProvider infoProvider) {
/* 187 */     super.activate(role, infoProvider);
/* 188 */     if (infoProvider == null)
/*     */       return; 
/* 190 */     AimingData aimingData = (AimingData)infoProvider.getPassedExtraInfo(AimingData.class);
/* 191 */     if (aimingData != null) aimingData.tryClaim(this.id);
/*     */   
/*     */   }
/*     */   
/*     */   public void deactivate(Role role, @Nullable InfoProvider infoProvider) {
/* 196 */     super.deactivate(role, infoProvider);
/* 197 */     if (infoProvider == null)
/*     */       return; 
/* 199 */     AimingData aimingData = (AimingData)infoProvider.getPassedExtraInfo(AimingData.class);
/* 200 */     if (aimingData != null) aimingData.release(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\corecomponents\ActionCombatAbility.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */