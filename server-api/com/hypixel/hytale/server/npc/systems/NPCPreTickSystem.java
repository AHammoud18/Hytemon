/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnBeaconReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
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
/*     */ public class NPCPreTickSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   private static final float DEFAULT_DESPAWN_CHECK_DELAY = 30.0F;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*  48 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final ComponentType<EntityStore, ModelComponent> modelComponentType = ModelComponent.getComponentType();
/*     */ 
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
/*     */   public NPCPreTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/*  74 */     this.npcComponentType = npcComponentType;
/*  75 */     this.dependencies = Set.of(new SystemDependency(Order.BEFORE, DeathSystems.CorpseRemoval.class));
/*  76 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { npcComponentType, this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  82 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  87 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  93 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  98 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/* 100 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 101 */     assert npcComponent != null;
/*     */     
/* 103 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 104 */     assert transformComponent != null;
/*     */     
/* 106 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 108 */     npcComponent.storeTickStartPosition(position);
/*     */     
/* 110 */     if (npcComponent.isPlayingDespawnAnim()) {
/* 111 */       if (npcComponent.tickDespawnAnimationRemainingSeconds(dt)) {
/* 112 */         commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 117 */     if (npcComponent.isDespawning()) {
/* 118 */       if (npcComponent.tickDespawnRemainingSeconds(dt)) {
/* 119 */         npcComponent.setDespawning(false);
/* 120 */         ModelComponent modelComponent = (ModelComponent)archetypeChunk.getComponent(index, this.modelComponentType);
/* 121 */         if (modelComponent != null && modelComponent.getModel().getAnimationSetMap().containsKey("Despawn")) {
/* 122 */           npcComponent.setPlayingDespawnAnim(true);
/* 123 */           npcComponent.setDespawnAnimationRemainingSeconds(npcComponent.getRole().getDespawnAnimationTime());
/*     */           
/* 125 */           commandBuffer.run(_store -> npcComponent.playAnimation(ref, AnimationSlot.Status, "Despawn", (ComponentAccessor)_store));
/*     */           return;
/*     */         } 
/* 128 */         commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 134 */     if (npcComponent.tickDespawnCheckRemainingSeconds(dt)) {
/* 135 */       npcComponent.setDespawnCheckRemainingSeconds(30.0F);
/*     */       
/* 137 */       if (npcComponent.getRole().getStateSupport().isInBusyState())
/*     */         return; 
/* 139 */       SpawnBeaconReference spawnBeaconReference = (SpawnBeaconReference)archetypeChunk.getComponent(index, SpawnBeaconReference.getComponentType());
/* 140 */       WorldTimeResource timeManager = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/* 141 */       if (SpawningPlugin.get().shouldNPCDespawn(store, npcComponent, timeManager, npcComponent.getSpawnConfiguration(), (spawnBeaconReference != null))) {
/* 142 */         npcComponent.setDespawning(true);
/* 143 */         npcComponent.setDespawnRemainingSeconds(0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCPreTickSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */