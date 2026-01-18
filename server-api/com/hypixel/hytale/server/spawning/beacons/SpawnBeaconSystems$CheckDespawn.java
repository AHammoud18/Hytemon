/*     */ package com.hypixel.hytale.server.spawning.beacons;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.controllers.BeaconSpawnController;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import java.time.Instant;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckDespawn
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/* 211 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final ComponentType<EntityStore, LegacySpawnBeaconEntity> componentType;
/*     */   
/*     */   @Nullable
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public CheckDespawn(ComponentType<EntityStore, LegacySpawnBeaconEntity> componentType, ComponentType<EntityStore, InitialBeaconDelay> initialBeaconDelayComponentType) {
/* 221 */     this.componentType = componentType;
/* 222 */     this.npcComponentType = NPCEntity.getComponentType();
/* 223 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)Query.not((Query)initialBeaconDelayComponentType) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 229 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 234 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 239 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)archetypeChunk.getComponent(index, this.componentType);
/* 240 */     assert legacySpawnBeaconComponent != null;
/*     */     
/* 242 */     UUIDComponent uuidComponent = (UUIDComponent)archetypeChunk.getComponent(index, UUIDComponent.getComponentType());
/* 243 */     assert uuidComponent != null;
/*     */     
/* 245 */     BeaconSpawnController spawnController = legacySpawnBeaconComponent.getSpawnController();
/* 246 */     Instant despawnSelfAfter = legacySpawnBeaconComponent.getDespawnSelfAfter();
/*     */     
/* 248 */     WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 250 */     if (despawnSelfAfter != null && worldTimeResource.getGameTime().isAfter(despawnSelfAfter)) {
/* 251 */       despawnAllSpawns(spawnController.getSpawnedEntities(), commandBuffer);
/* 252 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 256 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 257 */     BeaconSpawnWrapper spawnWrapper = legacySpawnBeaconComponent.getSpawnWrapper();
/* 258 */     if (spawnWrapper.shouldDespawn(world, worldTimeResource)) {
/* 259 */       LOGGER.at(Level.FINE).log("Removing spawn beacon %s due to matching despawn parameters", uuidComponent.getUuid());
/* 260 */       despawnAllSpawns(spawnController.getSpawnedEntities(), commandBuffer);
/* 261 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void despawnAllSpawns(@Nonnull List<Ref<EntityStore>> spawnedEntities, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 266 */     for (int i = 0; i < spawnedEntities.size(); i++) {
/* 267 */       Ref<EntityStore> ref = spawnedEntities.get(i);
/* 268 */       if (ref.isValid()) {
/*     */         
/* 270 */         NPCEntity npc = (NPCEntity)commandBuffer.getComponent(ref, this.npcComponentType);
/* 271 */         if (npc != null)
/*     */         {
/*     */           
/* 274 */           if (!npc.getRole().getStateSupport().isInBusyState() && !npc.isDespawning())
/*     */           {
/* 276 */             npc.setToDespawn(); }  } 
/*     */       } 
/* 278 */     }  spawnedEntities.clear();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\SpawnBeaconSystems$CheckDespawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */