/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.Evaluator;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateOption;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.StateSupport;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateEvaluatorSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  34 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateEvaluatorSystem(@Nonnull ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponent, @Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/*  67 */     this.stateEvaluatorComponent = stateEvaluatorComponent;
/*  68 */     this.npcComponentType = npcComponentType;
/*  69 */     this.dependencies = Set.of(new SystemDependency(Order.BEFORE, RoleSystems.BehaviourTickSystem.class), new SystemDependency(Order.AFTER, RoleSystems.PreBehaviourSupportTickSystem.class));
/*     */     
/*  71 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)stateEvaluatorComponent });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  77 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  82 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  88 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  93 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/*  94 */     assert npcComponent != null;
/*     */     
/*  96 */     UUIDComponent uuidComponent = (UUIDComponent)archetypeChunk.getComponent(index, UUIDComponent.getComponentType());
/*  97 */     assert uuidComponent != null;
/*     */     
/*  99 */     Role role = npcComponent.getRole();
/*     */     
/* 101 */     StateSupport stateSupport = role.getStateSupport();
/* 102 */     if (stateSupport.isRunningTransitionActions())
/*     */       return; 
/* 104 */     StateEvaluator stateEvaluator = (StateEvaluator)archetypeChunk.getComponent(index, this.stateEvaluatorComponent);
/* 105 */     assert stateEvaluator != null;
/* 106 */     if (!stateEvaluator.isActive() || !stateEvaluator.shouldExecute(dt))
/*     */       return; 
/* 108 */     HytaleLogger.Api logContext = LOGGER.at(Level.FINE);
/* 109 */     if (logContext.isEnabled()) {
/* 110 */       logContext.log("%s with uuid %s: Beginning state evaluation", npcComponent.getRoleName(), uuidComponent.getUuid());
/*     */     }
/*     */     
/* 113 */     EvaluationContext evaluationContext = stateEvaluator.getEvaluationContext();
/* 114 */     stateEvaluator.prepareEvaluationContext(evaluationContext);
/*     */     
/* 116 */     Evaluator<StateOption>.OptionHolder chosenOption = stateEvaluator.evaluate(index, archetypeChunk, commandBuffer, evaluationContext);
/* 117 */     evaluationContext.reset();
/* 118 */     logContext = LOGGER.at(Level.FINE);
/* 119 */     if (logContext.isEnabled()) {
/* 120 */       logContext.log("%s with uuid %s: Chose state option %s", npcComponent.getRoleName(), uuidComponent.getUuid(), chosenOption);
/*     */     }
/*     */     
/* 123 */     if (chosenOption == null)
/*     */       return; 
/* 125 */     StateOption action = (StateOption)chosenOption.getOption();
/* 126 */     int targetState = action.getStateIndex();
/* 127 */     int targetSubState = action.getSubStateIndex();
/* 128 */     if (!stateSupport.inState(targetState) || !stateSupport.inSubState(targetSubState)) {
/* 129 */       stateSupport.setState(action.getStateIndex(), action.getSubStateIndex(), true, false);
/* 130 */       logContext = LOGGER.at(Level.FINE);
/* 131 */       if (logContext.isEnabled()) {
/* 132 */         logContext.log("%s with uuid %s: Setting state", npcComponent.getRoleName(), uuidComponent.getUuid());
/*     */       }
/* 134 */       stateEvaluator.onStateSwitched();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\StateEvaluatorSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */