/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluatorConfig;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.combatactions.CombatActionOption;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.DamageMemory;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemorySystems;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.Evaluator;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.StateSupport;
/*     */ import com.hypixel.hytale.server.npc.systems.RoleSystems;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class EvaluatorTick
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, CombatActionEvaluator> componentType;
/*     */   private final ComponentType<EntityStore, TargetMemory> targetMemoryComponentType;
/*     */   private final ComponentType<EntityStore, DamageMemory> damageMemoryComponentType;
/*     */   @Nullable
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   private final ComponentType<EntityStore, ValueStore> valueStoreComponentType;
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   
/*     */   public EvaluatorTick(ComponentType<EntityStore, CombatActionEvaluator> componentType, ComponentType<EntityStore, TargetMemory> targetMemoryComponentType, ComponentType<EntityStore, DamageMemory> damageMemoryComponentType) {
/* 116 */     this.componentType = componentType;
/* 117 */     this.targetMemoryComponentType = targetMemoryComponentType;
/* 118 */     this.damageMemoryComponentType = damageMemoryComponentType;
/* 119 */     this.npcComponentType = NPCEntity.getComponentType();
/* 120 */     this.playerComponentType = Player.getComponentType();
/* 121 */     this.valueStoreComponentType = ValueStore.getComponentType();
/* 122 */     this.transformComponentType = TransformComponent.getComponentType();
/* 123 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { componentType, targetMemoryComponentType, this.npcComponentType, this.valueStoreComponentType, this.transformComponentType });
/* 124 */     this.dependencies = Set.of(new SystemDependency(Order.BEFORE, RoleSystems.PreBehaviourSupportTickSystem.class), new SystemDependency(Order.AFTER, TargetMemorySystems.Ticking.class));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 131 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 136 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 146 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 147 */     assert npcComponent != null;
/*     */     
/* 149 */     Role role = npcComponent.getRole();
/*     */     
/* 151 */     if (archetypeChunk.getArchetype().contains(DeathComponent.getComponentType())) {
/*     */       return;
/*     */     }
/* 154 */     CombatActionEvaluator evaluatorComponent = (CombatActionEvaluator)archetypeChunk.getComponent(index, this.componentType);
/* 155 */     assert evaluatorComponent != null;
/*     */     
/* 157 */     evaluatorComponent.tickBasicAttackCoolDown(dt);
/*     */     
/* 159 */     StateSupport stateSupport = role.getStateSupport();
/* 160 */     int currentState = stateSupport.getStateIndex();
/* 161 */     if (currentState != evaluatorComponent.getRunInState()) {
/* 162 */       if (evaluatorComponent.getCurrentAction() != null) {
/*     */         
/* 164 */         evaluatorComponent.completeCurrentAction(true, true);
/* 165 */         evaluatorComponent.clearPrimaryTarget();
/*     */         
/* 167 */         role.getMarkedEntitySupport().clearMarkedEntity(evaluatorComponent.getMarkedTargetSlot());
/*     */         
/* 169 */         HytaleLogger.Api api = CombatActionEvaluatorSystems.LOGGER.at(Level.FINEST);
/* 170 */         if (api.isEnabled()) {
/* 171 */           api.log("%s: Leaving combat", archetypeChunk.getReferenceTo(index));
/*     */         }
/*     */       } 
/* 174 */       DamageMemory damageMemory1 = (DamageMemory)archetypeChunk.getComponent(index, this.damageMemoryComponentType);
/* 175 */       if (damageMemory1 != null) damageMemory1.clearTotalDamage();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/* 180 */     if (role.getCombatSupport().isExecutingAttack())
/*     */       return; 
/* 182 */     ValueStore valueStoreComponent = (ValueStore)archetypeChunk.getComponent(index, this.valueStoreComponentType);
/* 183 */     assert valueStoreComponent != null;
/*     */     
/* 185 */     double[] postExecutionDistanceRange = evaluatorComponent.consumePostExecutionDistanceRange();
/* 186 */     if (postExecutionDistanceRange != null) {
/* 187 */       valueStoreComponent.storeDouble(evaluatorComponent.getMinRangeSlot(), postExecutionDistanceRange[0]);
/* 188 */       valueStoreComponent.storeDouble(evaluatorComponent.getMaxRangeSlot(), postExecutionDistanceRange[1]);
/*     */     } 
/*     */ 
/*     */     
/* 192 */     int currentSubState = stateSupport.getSubStateIndex();
/* 193 */     CombatActionEvaluatorConfig.BasicAttacks basicAttacks = evaluatorComponent.getBasicAttacks(currentSubState);
/* 194 */     if (basicAttacks != null) {
/* 195 */       evaluatorComponent.setCurrentBasicAttackSet(currentSubState, basicAttacks);
/*     */       
/* 197 */       String currentBasicAttack = evaluatorComponent.getCurrentBasicAttack();
/* 198 */       if (currentBasicAttack != null) {
/*     */         
/* 200 */         if (!evaluatorComponent.tickBasicAttackTimeout(dt)) {
/* 201 */           role.getMarkedEntitySupport().setMarkedEntity(evaluatorComponent.getMarkedTargetSlot(), evaluatorComponent.getBasicAttackTarget());
/*     */           
/*     */           return;
/*     */         } 
/* 205 */         evaluatorComponent.clearCurrentBasicAttack();
/* 206 */         HytaleLogger.Api api = CombatActionEvaluatorSystems.LOGGER.at(Level.FINEST);
/* 207 */         if (api.isEnabled()) {
/* 208 */           api.log("%s: Basic attack timed out", archetypeChunk.getReferenceTo(index));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 213 */       if (evaluatorComponent.canUseBasicAttack(index, archetypeChunk, commandBuffer)) {
/*     */         
/* 215 */         MotionController activeMotionController = role.getActiveMotionController();
/* 216 */         TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 217 */         assert transformComponent != null;
/*     */         
/* 219 */         Vector3d position = transformComponent.getPosition();
/* 220 */         Ref<EntityStore> targetRef = null;
/* 221 */         CombatActionEvaluator.CombatOptionHolder combatOptionHolder = evaluatorComponent.getCurrentAction();
/* 222 */         if (combatOptionHolder == null || ((CombatActionOption)combatOptionHolder.getOption()).getActionTarget() != CombatActionOption.Target.Friendly) {
/*     */           
/* 224 */           Ref<EntityStore> primaryTargetRef = evaluatorComponent.getPrimaryTarget();
/* 225 */           if (primaryTargetRef != null && primaryTargetRef.isValid()) {
/*     */             
/* 227 */             TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(primaryTargetRef, this.transformComponentType);
/* 228 */             assert targetTransformComponent != null;
/*     */             
/* 230 */             Vector3d targetPosition = targetTransformComponent.getPosition();
/* 231 */             if (activeMotionController.getSquaredDistance(position, targetPosition, basicAttacks.shouldUseProjectedDistance()) < basicAttacks.getMaxRangeSquared()) {
/* 232 */               targetRef = primaryTargetRef;
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 238 */         if (targetRef == null) {
/* 239 */           TargetMemory targetMemoryComponent = (TargetMemory)archetypeChunk.getComponent(index, this.targetMemoryComponentType);
/* 240 */           assert targetMemoryComponent != null;
/*     */           
/* 242 */           targetRef = targetMemoryComponent.getClosestHostile();
/*     */         } 
/*     */         
/* 245 */         if (targetRef != null) {
/* 246 */           TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(targetRef, this.transformComponentType);
/* 247 */           assert targetTransformComponent != null;
/*     */           
/* 249 */           Vector3d targetPosition = targetTransformComponent.getPosition();
/* 250 */           if (activeMotionController.getSquaredDistance(position, targetPosition, basicAttacks.shouldUseProjectedDistance()) < basicAttacks.getMaxRangeSquared()) {
/*     */             String attack;
/*     */             
/* 253 */             evaluatorComponent.setBasicAttackTarget(targetRef);
/* 254 */             role.getMarkedEntitySupport().setMarkedEntity(evaluatorComponent.getMarkedTargetSlot(), targetRef);
/* 255 */             String[] basicAttackOptions = basicAttacks.getAttacks();
/*     */ 
/*     */             
/* 258 */             if (basicAttacks.isRandom()) {
/* 259 */               attack = basicAttackOptions[RandomExtra.randomRange(basicAttackOptions.length)];
/*     */             } else {
/* 261 */               int nextAttackIndex = evaluatorComponent.getNextBasicAttackIndex();
/* 262 */               attack = basicAttackOptions[nextAttackIndex];
/* 263 */               nextAttackIndex++;
/* 264 */               if (nextAttackIndex >= basicAttackOptions.length) {
/* 265 */                 nextAttackIndex = 0;
/*     */               }
/* 267 */               evaluatorComponent.setNextBasicAttackIndex(nextAttackIndex);
/*     */             } 
/*     */             
/* 270 */             Objects.requireNonNull(basicAttacks); evaluatorComponent.setCurrentBasicAttack(attack, basicAttacks.isDamageFriendlies(), basicAttacks::getInteractionVars);
/* 271 */             evaluatorComponent.setBasicAttackTimeout(basicAttacks.getTimeout());
/*     */             
/* 273 */             HytaleLogger.Api api = CombatActionEvaluatorSystems.LOGGER.at(Level.FINEST);
/* 274 */             if (api.isEnabled()) {
/* 275 */               api.log("%s: Started basic attack %s", archetypeChunk.getReferenceTo(index), attack);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 281 */       evaluatorComponent.setCurrentBasicAttackSet(currentSubState, null);
/*     */     } 
/*     */ 
/*     */     
/* 285 */     CombatActionEvaluator.CombatOptionHolder currentAction = evaluatorComponent.getCurrentAction();
/* 286 */     if (currentAction != null) {
/*     */ 
/*     */       
/* 289 */       if (((CombatActionOption)currentAction.getOption()).getActionTarget() == CombatActionOption.Target.Self) {
/*     */         return;
/*     */       }
/* 292 */       if (!evaluatorComponent.hasTimedOut(dt)) {
/* 293 */         Ref<EntityStore> targetRef = evaluatorComponent.getPrimaryTarget();
/*     */ 
/*     */         
/* 296 */         if (targetRef != null && targetRef.isValid())
/*     */         {
/*     */           
/* 299 */           if (!commandBuffer.getArchetype(targetRef).contains(DeathComponent.getComponentType())) {
/*     */ 
/*     */             
/* 302 */             Player targetPlayerComponent = (Player)commandBuffer.getComponent(targetRef, this.playerComponentType);
/* 303 */             if (targetPlayerComponent == null || targetPlayerComponent.getGameMode() == GameMode.Adventure) {
/* 304 */               role.getMarkedEntitySupport().setMarkedEntity(evaluatorComponent.getMarkedTargetSlot(), targetRef);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 312 */       evaluatorComponent.terminateCurrentAction();
/* 313 */       evaluatorComponent.clearPrimaryTarget();
/* 314 */       role.getMarkedEntitySupport().clearMarkedEntity(evaluatorComponent.getMarkedTargetSlot());
/* 315 */       HytaleLogger.Api api = CombatActionEvaluatorSystems.LOGGER.at(Level.FINEST);
/* 316 */       if (api.isEnabled()) {
/* 317 */         api.log("%s: Lost current action target or timed out", archetypeChunk.getReferenceTo(index));
/*     */       }
/*     */     } 
/*     */     
/* 321 */     if (!evaluatorComponent.getOptionsBySubState().containsKey(currentSubState)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 326 */     EvaluationContext evaluationContext = evaluatorComponent.getEvaluationContext();
/* 327 */     double minRunUtility = evaluatorComponent.getMinRunUtility();
/* 328 */     evaluationContext.setMinimumUtility(minRunUtility);
/* 329 */     evaluationContext.setMinimumWeightCoefficient(0.0D);
/* 330 */     evaluationContext.setLastUsedNanos(evaluatorComponent.getLastRunNanos());
/*     */ 
/*     */     
/* 333 */     CombatActionEvaluator.RunOption runOption = evaluatorComponent.getRunOption();
/* 334 */     double utility = runOption.calculateUtility(index, archetypeChunk, evaluatorComponent.getPrimaryTarget(), commandBuffer, evaluationContext);
/* 335 */     evaluationContext.reset();
/* 336 */     if (utility < minRunUtility) {
/*     */       return;
/*     */     }
/* 339 */     Int2ObjectMap<List<Evaluator<CombatActionOption>.OptionHolder>> optionLists = evaluatorComponent.getOptionsBySubState();
/* 340 */     List<Evaluator<CombatActionOption>.OptionHolder> currentStateOptions = (List<Evaluator<CombatActionOption>.OptionHolder>)optionLists.get(currentSubState);
/* 341 */     evaluatorComponent.setActiveOptions(currentStateOptions);
/*     */     
/* 343 */     evaluatorComponent.selectNextCombatAction(index, archetypeChunk, commandBuffer, role, valueStoreComponent);
/* 344 */     evaluatorComponent.setLastRunNanos(System.nanoTime());
/*     */     
/* 346 */     DamageMemory damageMemory = (DamageMemory)archetypeChunk.getComponent(index, this.damageMemoryComponentType);
/* 347 */     if (damageMemory != null) {
/* 348 */       damageMemory.clearRecentDamage();
/*     */     }
/*     */     
/* 351 */     HytaleLogger.Api context = CombatActionEvaluatorSystems.LOGGER.at(Level.FINEST);
/* 352 */     if (context.isEnabled())
/* 353 */       context.log("%s: Has run the combat action evaluator", archetypeChunk.getReferenceTo(index)); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\CombatActionEvaluatorSystems$EvaluatorTick.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */