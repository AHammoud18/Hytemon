/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class MovementStatesSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType;
/*     */   @Nonnull
/*  49 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, ComputeVelocitySystem.class));
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MovementStatesSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType) {
/*  68 */     this.npcComponentType = npcComponentType;
/*  69 */     this.velocityComponentType = velocityComponentType;
/*  70 */     this.movementStatesComponentType = movementStatesComponentType;
/*  71 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)velocityComponentType, (Query)movementStatesComponentType });
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
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  87 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/*  88 */     assert npcComponent != null;
/*     */     
/*  90 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/*  91 */     assert velocityComponent != null;
/*     */     
/*  93 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, this.movementStatesComponentType);
/*  94 */     assert movementStatesComponent != null;
/*     */     
/*     */     try {
/*  97 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */       
/* 100 */       if (Objects.equals(npcComponent.getRoleName(), "Empty_Role"))
/*     */         return; 
/* 102 */       npcComponent.getRole().updateMovementState(ref, movementStatesComponent.getMovementStates(), velocityComponent.getVelocity(), (ComponentAccessor)commandBuffer);
/* 103 */     } catch (Exception e) {
/*     */       
/* 105 */       ((HytaleLogger.Api)((HytaleLogger.Api)NPCPlugin.get().getLogger().atSevere()).withCause(e)).log("Failed to update movement states for " + npcComponent.getRoleName() + ", Archetype: " + 
/* 106 */           String.valueOf(archetypeChunk.getArchetype()) + ", Spawn config index: " + npcComponent.getSpawnConfiguration() + ": ");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 113 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\MovementStatesSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */