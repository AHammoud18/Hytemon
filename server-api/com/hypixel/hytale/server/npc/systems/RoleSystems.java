/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.NewSpawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.components.StepComponent;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugDisplay;
/*     */ import com.hypixel.hytale.server.npc.role.support.EntitySupport;
/*     */ import com.hypixel.hytale.server.npc.role.support.MarkedEntitySupport;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class RoleSystems {
/*  43 */   private static final ThreadLocal<List<Ref<EntityStore>>> ENTITY_LIST = ThreadLocal.withInitial(java.util.ArrayList::new);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RoleActivateSystem
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, ModelComponent> modelComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RoleActivateSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/*  86 */       this.npcComponentType = npcComponentType;
/*  87 */       this.modelComponentType = ModelComponent.getComponentType();
/*  88 */       this.boundingBoxComponentType = BoundingBox.getComponentType();
/*  89 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)this.modelComponentType, (Query)this.boundingBoxComponentType });
/*  90 */       this.dependencies = Set.of(new SystemDependency(Order.AFTER, BalancingInitialisationSystem.class), new SystemDependency(Order.AFTER, ModelSystems.ModelSpawned.class));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  97 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 103 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 108 */       NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 109 */       assert npcComponent != null;
/*     */       
/* 111 */       Role role = npcComponent.getRole();
/* 112 */       role.getStateSupport().activate();
/* 113 */       role.getDebugSupport().activate();
/*     */       
/* 115 */       ModelComponent modelComponent = (ModelComponent)holder.getComponent(this.modelComponentType);
/* 116 */       assert modelComponent != null;
/*     */       
/* 118 */       BoundingBox boundingBoxComponent = (BoundingBox)holder.getComponent(this.boundingBoxComponentType);
/* 119 */       assert boundingBoxComponent != null;
/*     */       
/* 121 */       role.updateMotionControllers(null, modelComponent.getModel(), boundingBoxComponent.getBoundingBox(), null);
/*     */       
/* 123 */       role.clearOnce();
/* 124 */       role.getActiveMotionController().activate();
/*     */ 
/*     */       
/* 127 */       holder.ensureComponent(InteractionModule.get().getChainingDataComponent());
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 132 */       NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 133 */       assert npcComponent != null;
/*     */       
/* 135 */       Role role = npcComponent.getRole();
/* 136 */       role.getActiveMotionController().deactivate();
/* 137 */       role.getWorldSupport().resetAllBlockSensors();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PreBehaviourSupportTickSystem
/*     */     extends SteppableTickingSystem
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, Player> playerComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, DeathComponent> deathComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PreBehaviourSupportTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/* 176 */       this.npcComponentType = npcComponentType;
/* 177 */       this.playerComponentType = Player.getComponentType();
/* 178 */       this.deathComponentType = DeathComponent.getComponentType();
/* 179 */       this.dependencies = Set.of(new SystemDependency(Order.BEFORE, RoleSystems.BehaviourTickSystem.class));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 185 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 190 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 196 */       return (Query)this.npcComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 201 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 202 */       assert npcComponent != null;
/*     */       
/* 204 */       Role role = npcComponent.getRole();
/*     */       
/* 206 */       MarkedEntitySupport markedEntitySupport = role.getMarkedEntitySupport();
/* 207 */       Ref[] arrayOfRef = markedEntitySupport.getEntityTargets();
/*     */ 
/*     */       
/* 210 */       for (int i = 0; i < arrayOfRef.length; i++) {
/* 211 */         Ref<EntityStore> targetReference = arrayOfRef[i];
/* 212 */         if (targetReference == null)
/*     */           continue; 
/* 214 */         if (!targetReference.isValid()) {
/* 215 */           arrayOfRef[i] = null;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 220 */         Player playerComponent = (Player)commandBuffer.getComponent(targetReference, this.playerComponentType);
/* 221 */         if (playerComponent != null && playerComponent.getGameMode() != GameMode.Adventure)
/*     */         {
/* 223 */           if (playerComponent.getGameMode() == GameMode.Creative) {
/* 224 */             PlayerSettings playerSettingsComponent = (PlayerSettings)commandBuffer.getComponent(targetReference, PlayerSettings.getComponentType());
/* 225 */             if (playerSettingsComponent == null || !playerSettingsComponent.creativeSettings().allowNPCDetection()) {
/*     */ 
/*     */               
/* 228 */               arrayOfRef[i] = null;
/*     */               continue;
/*     */             } 
/*     */           } else {
/* 232 */             arrayOfRef[i] = null;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         }
/* 237 */         DeathComponent deathComponent = (DeathComponent)commandBuffer.getComponent(targetReference, this.deathComponentType);
/* 238 */         if (deathComponent != null) {
/* 239 */           arrayOfRef[i] = null;
/*     */         }
/*     */         continue;
/*     */       } 
/* 243 */       role.clearOnceIfNeeded();
/* 244 */       role.getBodySteering().clear();
/* 245 */       role.getHeadSteering().clear();
/* 246 */       role.getIgnoredEntitiesForAvoidance().clear();
/* 247 */       npcComponent.invalidateCachedHorizontalSpeedMultiplier();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BehaviourTickSystem
/*     */     extends TickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, StepComponent> stepComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, Frozen> frozenComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NewSpawnComponent> newSpawnComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BehaviourTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull ComponentType<EntityStore, StepComponent> stepComponentType) {
/* 287 */       this.npcComponentType = npcComponentType;
/* 288 */       this.stepComponentType = stepComponentType;
/* 289 */       this.frozenComponentType = Frozen.getComponentType();
/* 290 */       this.newSpawnComponentType = NewSpawnComponent.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 295 */       List<Ref<EntityStore>> entities = RoleSystems.ENTITY_LIST.get();
/*     */ 
/*     */       
/* 298 */       store.forEachChunk((Query)this.npcComponentType, (archetypeChunk, commandBuffer) -> {
/*     */             for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */               entities.add(archetypeChunk.getReferenceTo(index));
/*     */             }
/*     */           });
/*     */       
/* 304 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 305 */       boolean isAllNpcFrozen = world.getWorldConfig().isAllNPCFrozen();
/* 306 */       for (Ref<EntityStore> entityReference : entities) {
/* 307 */         float tickLength; if (!entityReference.isValid() || 
/* 308 */           store.getComponent(entityReference, this.newSpawnComponentType) != null) {
/*     */           continue;
/*     */         }
/* 311 */         if (store.getComponent(entityReference, this.frozenComponentType) != null || isAllNpcFrozen) {
/* 312 */           StepComponent stepComponent = (StepComponent)store.getComponent(entityReference, this.stepComponentType);
/* 313 */           if (stepComponent == null)
/* 314 */             continue;  tickLength = stepComponent.getTickLength();
/*     */         } else {
/* 316 */           tickLength = dt;
/*     */         } 
/*     */         
/* 319 */         NPCEntity npcComponent = (NPCEntity)store.getComponent(entityReference, this.npcComponentType);
/* 320 */         assert npcComponent != null;
/*     */         
/*     */         try {
/* 323 */           Role role = npcComponent.getRole();
/* 324 */           boolean benchmarking = NPCPlugin.get().isBenchmarkingRole();
/* 325 */           if (benchmarking) {
/* 326 */             long start = System.nanoTime();
/* 327 */             role.tick(entityReference, tickLength, store);
/* 328 */             NPCPlugin.get().collectRoleTick(role.getRoleIndex(), System.nanoTime() - start); continue;
/*     */           } 
/* 330 */           role.tick(entityReference, tickLength, store);
/*     */         }
/* 332 */         catch (NullPointerException|IllegalArgumentException|IllegalStateException e) {
/* 333 */           ((HytaleLogger.Api)NPCPlugin.get().getLogger().at(Level.SEVERE).withCause(e)).log("Failed to tick NPC: %s", npcComponent.getRoleName());
/* 334 */           store.removeEntity(entityReference, RemoveReason.REMOVE);
/*     */         } 
/*     */       } 
/*     */       
/* 338 */       entities.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PostBehaviourSupportTickSystem
/*     */     extends SteppableTickingSystem
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 369 */     private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, SteeringSystem.class), new SystemDependency(Order.BEFORE, TransformSystems.EntityTrackerUpdate.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PostBehaviourSupportTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/* 380 */       this.npcComponentType = npcComponentType;
/* 381 */       this.transformComponentType = TransformComponent.getComponentType();
/* 382 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)this.transformComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 388 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 393 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 399 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 404 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 405 */       assert npcComponent != null;
/*     */       
/* 407 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/* 409 */       Role role = npcComponent.getRole();
/* 410 */       MotionController activeMotionController = role.getActiveMotionController();
/* 411 */       activeMotionController.clearOverrides();
/* 412 */       activeMotionController.constrainRotations(role, (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType));
/*     */       
/* 414 */       role.getCombatSupport().tick(dt);
/* 415 */       role.getWorldSupport().tick(dt);
/*     */       
/* 417 */       EntitySupport entitySupport = role.getEntitySupport();
/* 418 */       entitySupport.tick(dt);
/* 419 */       entitySupport.handleNominatedDisplayName(ref, (ComponentAccessor)commandBuffer);
/*     */       
/* 421 */       role.getStateSupport().update((ComponentAccessor)commandBuffer);
/*     */       
/* 423 */       npcComponent.clearDamageData();
/* 424 */       role.getMarkedEntitySupport().setTargetSlotToIgnoreForAvoidance(-2147483648);
/*     */       
/* 426 */       role.setReachedTerminalAction(false);
/*     */       
/* 428 */       role.getPositionCache().clear(dt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RoleDebugSystem
/*     */     extends SteppableTickingSystem
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RoleDebugSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull Set<Dependency<EntityStore>> dependencies) {
/* 456 */       this.npcComponentType = npcComponentType;
/* 457 */       this.dependencies = dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 463 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 468 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 474 */       return (Query)this.npcComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 479 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 480 */       assert npcComponent != null;
/*     */       
/* 482 */       Role role = npcComponent.getRole();
/* 483 */       RoleDebugDisplay debugDisplay = role.getDebugSupport().getDebugDisplay();
/* 484 */       if (debugDisplay != null)
/* 485 */         debugDisplay.display(role, index, archetypeChunk, commandBuffer); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */