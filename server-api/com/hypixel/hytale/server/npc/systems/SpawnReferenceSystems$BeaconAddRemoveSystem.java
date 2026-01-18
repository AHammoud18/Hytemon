/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnBeaconReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.controllers.BeaconSpawnController;
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
/*     */ public class BeaconAddRemoveSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, LegacySpawnBeaconEntity> legacySpawnBeaconComponent;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public BeaconAddRemoveSystem(@Nonnull ComponentType<EntityStore, SpawnBeaconReference> spawnReferenceComponentType, @Nonnull ComponentType<EntityStore, LegacySpawnBeaconEntity> legacySpawnBeaconComponent) {
/* 235 */     this.spawnReferenceComponentType = spawnReferenceComponentType;
/* 236 */     this.legacySpawnBeaconComponent = legacySpawnBeaconComponent;
/* 237 */     this.npcComponentType = NPCEntity.getComponentType();
/* 238 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { spawnReferenceComponentType, this.npcComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 244 */     return this.query; } public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { SpawnBeaconReference spawnReferenceComponent;
/*     */     Ref<EntityStore> markerReference;
/*     */     LegacySpawnBeaconEntity legacySpawnBeaconComponent;
/*     */     NPCEntity npcComponent;
/*     */     BeaconSpawnController spawnController;
/* 249 */     switch (SpawnReferenceSystems.null.$SwitchMap$com$hypixel$hytale$component$AddReason[reason.ordinal()]) {
/*     */ 
/*     */       
/*     */       case 2:
/* 253 */         spawnReferenceComponent = (SpawnBeaconReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 254 */         assert spawnReferenceComponent != null;
/*     */         
/* 256 */         markerReference = spawnReferenceComponent.getReference().getEntity((ComponentAccessor)store);
/* 257 */         if (markerReference == null)
/*     */           return; 
/* 259 */         legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(markerReference, this.legacySpawnBeaconComponent);
/* 260 */         assert legacySpawnBeaconComponent != null;
/*     */         
/* 262 */         npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 263 */         assert npcComponent != null;
/*     */         
/* 265 */         spawnReferenceComponent.getReference().setEntity(markerReference, (ComponentAccessor)store);
/* 266 */         spawnReferenceComponent.refreshTimeoutCounter();
/*     */         
/* 268 */         spawnController = legacySpawnBeaconComponent.getSpawnController();
/* 269 */         if (!spawnController.hasSlots()) {
/*     */           
/* 271 */           npcComponent.setToDespawn();
/*     */           return;
/*     */         } 
/* 274 */         spawnController.notifySpawnedEntityExists(markerReference, (ComponentAccessor)commandBuffer);
/*     */         break;
/*     */     }  }
/*     */    public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     SpawnBeaconReference spawnReference;
/*     */     Ref<EntityStore> spawnBeaconRef;
/*     */     LegacySpawnBeaconEntity legacySpawnBeaconComponent;
/* 281 */     switch (SpawnReferenceSystems.null.$SwitchMap$com$hypixel$hytale$component$RemoveReason[reason.ordinal()]) {
/*     */       case 1:
/* 283 */         spawnReference = (SpawnBeaconReference)store.getComponent(ref, this.spawnReferenceComponentType);
/* 284 */         if (spawnReference == null)
/*     */           return; 
/* 286 */         spawnBeaconRef = spawnReference.getReference().getEntity((ComponentAccessor)store);
/* 287 */         if (spawnBeaconRef == null)
/*     */           return; 
/* 289 */         legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(spawnBeaconRef, this.legacySpawnBeaconComponent);
/* 290 */         if (legacySpawnBeaconComponent == null)
/*     */           return; 
/* 292 */         legacySpawnBeaconComponent.getSpawnController().notifyNPCRemoval(ref, (ComponentAccessor)store);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\SpawnReferenceSystems$BeaconAddRemoveSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */