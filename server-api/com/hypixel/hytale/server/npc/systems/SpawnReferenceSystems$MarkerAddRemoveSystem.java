/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.reference.InvalidatablePersistentRef;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.StoredFlock;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnMarkerReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
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
/*     */ public class MarkerAddRemoveSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, WorldGenId> worldGenIdComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentComponentType;
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceResourceType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public MarkerAddRemoveSystem(@Nonnull ComponentType<EntityStore, SpawnMarkerReference> spawnReferenceComponentType, @Nonnull ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType) {
/*  90 */     this.spawnReferenceComponentType = spawnReferenceComponentType;
/*  91 */     this.spawnMarkerEntityComponentType = spawnMarkerEntityComponentType;
/*  92 */     this.npcComponentType = NPCEntity.getComponentType();
/*  93 */     this.worldGenIdComponentType = WorldGenId.getComponentType();
/*  94 */     this.uuidComponentComponentType = UUIDComponent.getComponentType();
/*  95 */     this.worldTimeResourceResourceType = WorldTimeResource.getResourceType();
/*  96 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcComponentType, this.uuidComponentComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 102 */     return this.query; } public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnMarkerReference spawnReferenceComponent; Ref<EntityStore> markerReference; SpawnMarkerEntity markerTypeComponent;
/*     */     NPCEntity npcComponent;
/*     */     WorldGenId worldGenIdComponent;
/*     */     int worldGenId;
/*     */     HytaleLogger.Api context;
/* 107 */     switch (SpawnReferenceSystems.null.$SwitchMap$com$hypixel$hytale$component$AddReason[reason.ordinal()]) {
/*     */ 
/*     */       
/*     */       case 2:
/* 111 */         spawnReferenceComponent = (SpawnMarkerReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 112 */         assert spawnReferenceComponent != null;
/*     */         
/* 114 */         markerReference = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 115 */         if (markerReference == null)
/*     */           return; 
/* 117 */         markerTypeComponent = (SpawnMarkerEntity)store.getComponent(markerReference, this.spawnMarkerEntityComponentType);
/* 118 */         assert markerTypeComponent != null;
/*     */         
/* 120 */         npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 121 */         assert npcComponent != null;
/*     */         
/* 123 */         spawnReferenceComponent.getReference().setEntity(markerReference, (ComponentAccessor)store);
/* 124 */         spawnReferenceComponent.refreshTimeoutCounter();
/* 125 */         markerTypeComponent.refreshTimeout();
/*     */         
/* 127 */         worldGenIdComponent = (WorldGenId)commandBuffer.getComponent(markerReference, this.worldGenIdComponentType);
/* 128 */         worldGenId = (worldGenIdComponent != null) ? worldGenIdComponent.getWorldGenId() : 0;
/*     */         
/* 130 */         commandBuffer.putComponent(markerReference, WorldGenId.getComponentType(), (Component)new WorldGenId(worldGenId));
/* 131 */         context = SpawningPlugin.get().getLogger().at(Level.FINE);
/* 132 */         if (context.isEnabled()) {
/* 133 */           UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(markerReference, this.uuidComponentComponentType);
/* 134 */           assert uuidComponent != null;
/* 135 */           UUID uuid = uuidComponent.getUuid();
/* 136 */           context.log("%s synced up with marker %s", npcComponent.getRoleName(), uuid);
/*     */         }  break;
/*     */     }  } public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnMarkerReference spawnReferenceComponent;
/*     */     Ref<EntityStore> spawnMarkerRef;
/*     */     SpawnMarkerEntity spawnMarkerComponent;
/*     */     UUIDComponent uuidComponent;
/*     */     UUID uuid;
/*     */     int spawnCount;
/*     */     SpawnMarker cachedMarker;
/* 145 */     switch (SpawnReferenceSystems.null.$SwitchMap$com$hypixel$hytale$component$RemoveReason[reason.ordinal()]) {
/*     */       case 1:
/* 147 */         spawnReferenceComponent = (SpawnMarkerReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 148 */         if (spawnReferenceComponent == null)
/*     */           return; 
/* 150 */         spawnMarkerRef = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 151 */         if (spawnMarkerRef == null)
/*     */           return; 
/* 153 */         spawnMarkerComponent = (SpawnMarkerEntity)store.getComponent(spawnMarkerRef, this.spawnMarkerEntityComponentType);
/* 154 */         assert spawnMarkerComponent != null;
/*     */         
/* 156 */         uuidComponent = (UUIDComponent)store.getComponent(ref, this.uuidComponentComponentType);
/* 157 */         assert uuidComponent != null;
/* 158 */         uuid = uuidComponent.getUuid();
/*     */         
/* 160 */         spawnCount = spawnMarkerComponent.decrementAndGetSpawnCount();
/* 161 */         cachedMarker = spawnMarkerComponent.getCachedMarker();
/* 162 */         if (spawnCount > 0 && cachedMarker.getDeactivationDistance() > 0.0D) {
/* 163 */           InvalidatablePersistentRef[] newReferences = new InvalidatablePersistentRef[spawnCount];
/* 164 */           int pos = 0;
/* 165 */           InvalidatablePersistentRef[] npcReferences = spawnMarkerComponent.getNpcReferences();
/* 166 */           for (InvalidatablePersistentRef npcRef : npcReferences) {
/* 167 */             if (!npcRef.getUuid().equals(uuid))
/*     */             {
/* 169 */               newReferences[pos++] = npcRef; } 
/*     */           } 
/* 171 */           spawnMarkerComponent.setNpcReferences(newReferences);
/*     */         } 
/*     */         
/* 174 */         if (spawnCount <= 0 && !cachedMarker.isRealtimeRespawn()) {
/*     */           
/* 176 */           Instant instant = ((WorldTimeResource)store.getResource(this.worldTimeResourceResourceType)).getGameTime();
/* 177 */           Duration gameTimeRespawn = spawnMarkerComponent.pollGameTimeRespawn();
/* 178 */           if (gameTimeRespawn != null) {
/* 179 */             instant = instant.plus(gameTimeRespawn);
/*     */           }
/*     */           
/* 182 */           spawnMarkerComponent.setSpawnAfter(instant);
/* 183 */           spawnMarkerComponent.setNpcReferences(null);
/*     */           
/* 185 */           StoredFlock storedFlock = spawnMarkerComponent.getStoredFlock();
/* 186 */           if (storedFlock != null) storedFlock.clear(); 
/*     */         } 
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\SpawnReferenceSystems$MarkerAddRemoveSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */