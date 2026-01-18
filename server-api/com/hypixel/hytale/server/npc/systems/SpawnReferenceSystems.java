/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.reference.InvalidatablePersistentRef;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.StoredFlock;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnBeaconReference;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnMarkerReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.controllers.BeaconSpawnController;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
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
/*     */ public class SpawnReferenceSystems
/*     */ {
/*     */   public static class MarkerAddRemoveSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType;
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType;
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, WorldGenId> worldGenIdComponentType;
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, UUIDComponent> uuidComponentComponentType;
/*     */     @Nonnull
/*     */     private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceResourceType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public MarkerAddRemoveSystem(@Nonnull ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType, @Nonnull ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType) {
/*  90 */       this.spawnReferenceComponentType = spawnReferenceComponentType;
/*  91 */       this.spawnMarkerEntityComponentType = spawnMarkerEntityComponentType;
/*  92 */       this.npcComponentType = NPCEntity.getComponentType();
/*  93 */       this.worldGenIdComponentType = WorldGenId.getComponentType();
/*  94 */       this.uuidComponentComponentType = UUIDComponent.getComponentType();
/*  95 */       this.worldTimeResourceResourceType = WorldTimeResource.getResourceType();
/*  96 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcComponentType, this.uuidComponentComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 102 */       return this.query; } public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnMarkerReference spawnReferenceComponent; Ref<EntityStore> markerReference; SpawnMarkerEntity markerTypeComponent;
/*     */       NPCEntity npcComponent;
/*     */       WorldGenId worldGenIdComponent;
/*     */       int worldGenId;
/*     */       HytaleLogger.Api context;
/* 107 */       switch (reason) {
/*     */ 
/*     */         
/*     */         case UNLOAD:
/* 111 */           spawnReferenceComponent = (SpawnMarkerReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 112 */           assert spawnReferenceComponent != null;
/*     */           
/* 114 */           markerReference = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 115 */           if (markerReference == null)
/*     */             return; 
/* 117 */           markerTypeComponent = (SpawnMarkerEntity)store.getComponent(markerReference, this.spawnMarkerEntityComponentType);
/* 118 */           assert markerTypeComponent != null;
/*     */           
/* 120 */           npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 121 */           assert npcComponent != null;
/*     */           
/* 123 */           spawnReferenceComponent.getReference().setEntity(markerReference, (ComponentAccessor)store);
/* 124 */           spawnReferenceComponent.refreshTimeoutCounter();
/* 125 */           markerTypeComponent.refreshTimeout();
/*     */           
/* 127 */           worldGenIdComponent = (WorldGenId)commandBuffer.getComponent(markerReference, this.worldGenIdComponentType);
/* 128 */           worldGenId = (worldGenIdComponent != null) ? worldGenIdComponent.getWorldGenId() : 0;
/*     */           
/* 130 */           commandBuffer.putComponent(markerReference, WorldGenId.getComponentType(), (Component)new WorldGenId(worldGenId));
/* 131 */           context = SpawningPlugin.get().getLogger().at(Level.FINE);
/* 132 */           if (context.isEnabled()) {
/* 133 */             UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(markerReference, this.uuidComponentComponentType);
/* 134 */             assert uuidComponent != null;
/* 135 */             UUID uuid = uuidComponent.getUuid();
/* 136 */             context.log("%s synced up with marker %s", npcComponent.getRoleName(), uuid);
/*     */           }  break;
/*     */       }  } public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnMarkerReference spawnReferenceComponent;
/*     */       Ref<EntityStore> spawnMarkerRef;
/*     */       SpawnMarkerEntity spawnMarkerComponent;
/*     */       UUIDComponent uuidComponent;
/*     */       UUID uuid;
/*     */       int spawnCount;
/*     */       SpawnMarker cachedMarker;
/* 145 */       switch (reason) {
/*     */         case REMOVE:
/* 147 */           spawnReferenceComponent = (SpawnMarkerReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 148 */           if (spawnReferenceComponent == null)
/*     */             return; 
/* 150 */           spawnMarkerRef = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 151 */           if (spawnMarkerRef == null)
/*     */             return; 
/* 153 */           spawnMarkerComponent = (SpawnMarkerEntity)store.getComponent(spawnMarkerRef, this.spawnMarkerEntityComponentType);
/* 154 */           assert spawnMarkerComponent != null;
/*     */           
/* 156 */           uuidComponent = (UUIDComponent)store.getComponent(ref, this.uuidComponentComponentType);
/* 157 */           assert uuidComponent != null;
/* 158 */           uuid = uuidComponent.getUuid();
/*     */           
/* 160 */           spawnCount = spawnMarkerComponent.decrementAndGetSpawnCount();
/* 161 */           cachedMarker = spawnMarkerComponent.getCachedMarker();
/* 162 */           if (spawnCount > 0 && cachedMarker.getDeactivationDistance() > 0.0D) {
/* 163 */             InvalidatablePersistentRef[] newReferences = new InvalidatablePersistentRef[spawnCount];
/* 164 */             int pos = 0;
/* 165 */             InvalidatablePersistentRef[] npcReferences = spawnMarkerComponent.getNpcReferences();
/* 166 */             for (InvalidatablePersistentRef npcRef : npcReferences) {
/* 167 */               if (!npcRef.getUuid().equals(uuid))
/*     */               {
/* 169 */                 newReferences[pos++] = npcRef; } 
/*     */             } 
/* 171 */             spawnMarkerComponent.setNpcReferences(newReferences);
/*     */           } 
/*     */           
/* 174 */           if (spawnCount <= 0 && !cachedMarker.isRealtimeRespawn()) {
/*     */             
/* 176 */             Instant instant = ((WorldTimeResource)store.getResource(this.worldTimeResourceResourceType)).getGameTime();
/* 177 */             Duration gameTimeRespawn = spawnMarkerComponent.pollGameTimeRespawn();
/* 178 */             if (gameTimeRespawn != null) {
/* 179 */               instant = instant.plus(gameTimeRespawn);
/*     */             }
/*     */             
/* 182 */             spawnMarkerComponent.setSpawnAfter(instant);
/* 183 */             spawnMarkerComponent.setNpcReferences(null);
/*     */             
/* 185 */             StoredFlock storedFlock = spawnMarkerComponent.getStoredFlock();
/* 186 */             if (storedFlock != null) storedFlock.clear();
/*     */           
/*     */           } 
/*     */           break;
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BeaconAddRemoveSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, LegacySpawnBeaconEntity> legacySpawnBeaconComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
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
/*     */ 
/*     */     
/*     */     public BeaconAddRemoveSystem(@Nonnull ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType, @Nonnull ComponentType<EntityStore, LegacySpawnBeaconEntity> legacySpawnBeaconComponent) {
/* 235 */       this.spawnReferenceComponentType = spawnReferenceComponentType;
/* 236 */       this.legacySpawnBeaconComponent = legacySpawnBeaconComponent;
/* 237 */       this.npcComponentType = NPCEntity.getComponentType();
/* 238 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 244 */       return this.query; } public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnBeaconReference spawnReferenceComponent;
/*     */       Ref<EntityStore> markerReference;
/*     */       LegacySpawnBeaconEntity legacySpawnBeaconComponent;
/*     */       NPCEntity npcComponent;
/*     */       BeaconSpawnController spawnController;
/* 249 */       switch (reason) {
/*     */ 
/*     */         
/*     */         case UNLOAD:
/* 253 */           spawnReferenceComponent = (SpawnBeaconReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 254 */           assert spawnReferenceComponent != null;
/*     */           
/* 256 */           markerReference = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 257 */           if (markerReference == null)
/*     */             return; 
/* 259 */           legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(markerReference, this.legacySpawnBeaconComponent);
/* 260 */           assert legacySpawnBeaconComponent != null;
/*     */           
/* 262 */           npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 263 */           assert npcComponent != null;
/*     */           
/* 265 */           spawnReferenceComponent.getReference().setEntity(markerReference, (ComponentAccessor)store);
/* 266 */           spawnReferenceComponent.refreshTimeoutCounter();
/*     */           
/* 268 */           spawnController = legacySpawnBeaconComponent.getSpawnController();
/* 269 */           if (!spawnController.hasSlots()) {
/*     */             
/* 271 */             npcComponent.setToDespawn();
/*     */             return;
/*     */           } 
/* 274 */           spawnController.notifySpawnedEntityExists(markerReference, (ComponentAccessor)commandBuffer);
/*     */           break;
/*     */       }  }
/*     */      public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */       SpawnBeaconReference spawnReference;
/*     */       Ref<EntityStore> spawnBeaconRef;
/*     */       LegacySpawnBeaconEntity legacySpawnBeaconComponent;
/* 281 */       switch (reason) {
/*     */         case REMOVE:
/* 283 */           spawnReference = (SpawnBeaconReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 284 */           if (spawnReference == null)
/*     */             return; 
/* 286 */           spawnBeaconRef = spawnReference.getReference().getEntity((ComponentAccessor)store);
/* 287 */           if (spawnBeaconRef == null)
/*     */             return; 
/* 289 */           legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(spawnBeaconRef, this.legacySpawnBeaconComponent);
/* 290 */           if (legacySpawnBeaconComponent == null)
/*     */             return; 
/* 292 */           legacySpawnBeaconComponent.getSpawnController().notifyNPCRemoval(ref, (ComponentAccessor)store);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickingSpawnMarkerSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 309 */     private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, NPCPreTickSystem.class), new SystemDependency(Order.BEFORE, DeathSystems.CorpseRemoval.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> markerTypeComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcEntityComponentType;
/*     */ 
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
/*     */     
/*     */     public TickingSpawnMarkerSystem(@Nonnull ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType, @Nonnull ComponentType<EntityStore, SpawnMarkerEntity> markerTypeComponentType) {
/* 344 */       this.spawnReferenceComponentType = spawnReferenceComponentType;
/* 345 */       this.markerTypeComponentType = markerTypeComponentType;
/* 346 */       this.npcEntityComponentType = NPCEntity.getComponentType();
/* 347 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcEntityComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 353 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 359 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 364 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 369 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcEntityComponentType);
/* 370 */       assert npcComponent != null;
/* 371 */       if (npcComponent.isDespawning() || npcComponent.isPlayingDespawnAnim())
/*     */         return; 
/* 373 */       SpawnMarkerReference spawnReferenceComponent = (SpawnMarkerReference)archetypeChunk.getComponent(index, this.spawnReferenceComponentType);
/* 374 */       assert spawnReferenceComponent != null;
/* 375 */       if (!spawnReferenceComponent.tickMarkerLostTimeoutCounter(dt))
/*     */         return; 
/* 377 */       Ref<EntityStore> spawnMarkerRef = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)commandBuffer);
/* 378 */       if (spawnMarkerRef != null) {
/*     */         
/* 380 */         SpawnMarkerEntity spawnMarkerComponent = (SpawnMarkerEntity)commandBuffer.getComponent(spawnMarkerRef, this.markerTypeComponentType);
/* 381 */         assert spawnMarkerComponent != null;
/* 382 */         spawnReferenceComponent.refreshTimeoutCounter();
/* 383 */         spawnMarkerComponent.refreshTimeout();
/* 384 */       } else if (npcComponent.getRole().getStateSupport().isInBusyState()) {
/*     */ 
/*     */         
/* 387 */         spawnReferenceComponent.refreshTimeoutCounter();
/*     */       } else {
/*     */         
/* 390 */         npcComponent.setToDespawn();
/* 391 */         HytaleLogger.Api context = SpawningPlugin.get().getLogger().at(Level.WARNING);
/* 392 */         if (context.isEnabled()) {
/* 393 */           context.log("NPCEntity despawning due to lost marker: %s", archetypeChunk.getReferenceTo(index));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickingSpawnBeaconSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 408 */     private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, NPCPreTickSystem.class), new SystemDependency(Order.BEFORE, DeathSystems.CorpseRemoval.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcEntityComponentType;
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
/*     */     
/*     */     public TickingSpawnBeaconSystem(@Nonnull ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType) {
/* 435 */       this.spawnReferenceComponentType = spawnReferenceComponentType;
/* 436 */       this.npcEntityComponentType = NPCEntity.getComponentType();
/* 437 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcEntityComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 443 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 449 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 454 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 459 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcEntityComponentType);
/* 460 */       assert npcComponent != null;
/* 461 */       if (npcComponent.isDespawning() || npcComponent.isPlayingDespawnAnim())
/*     */         return; 
/* 463 */       SpawnBeaconReference spawnReferenceComponent = (SpawnBeaconReference)archetypeChunk.getComponent(index, this.spawnReferenceComponentType);
/* 464 */       assert spawnReferenceComponent != null;
/* 465 */       if (!spawnReferenceComponent.tickMarkerLostTimeoutCounter(dt))
/*     */         return; 
/* 467 */       Ref<EntityStore> spawnBeaconRef = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)commandBuffer);
/* 468 */       if (spawnBeaconRef != null) {
/*     */         
/* 470 */         spawnReferenceComponent.refreshTimeoutCounter();
/* 471 */       } else if (npcComponent.getRole().getStateSupport().isInBusyState()) {
/*     */ 
/*     */         
/* 474 */         spawnReferenceComponent.refreshTimeoutCounter();
/*     */       } else {
/*     */         
/* 477 */         npcComponent.setToDespawn();
/* 478 */         HytaleLogger.Api context = SpawningPlugin.get().getLogger().at(Level.WARNING);
/* 479 */         if (context.isEnabled())
/* 480 */           context.log("NPCEntity despawning due to lost marker: %s", archetypeChunk.getReferenceTo(index)); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\SpawnReferenceSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */