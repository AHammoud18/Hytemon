/*     */ package com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition.ObjectiveLocationTriggerCondition;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.UseEntityObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.assets.TrackOrUpdateObjective;
/*     */ import com.hypixel.hytale.protocol.packets.assets.UntrackObjective;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.PlayerSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
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
/*     */ public class TickingSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ObjectiveLocationMarker> objectiveLocationMarkerComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PlayerRef> playerRefComponentType;
/*     */   @Nonnull
/* 203 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 209 */   private final ComponentType<EntityStore, WeatherTracker> weatherTrackerComponentType = WeatherTracker.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 215 */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent;
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
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TickingSystem(@Nonnull ComponentType<EntityStore, ObjectiveLocationMarker> objectiveLocationMarkerComponentType, @Nonnull ComponentType<EntityStore, PlayerRef> playerRefComponentType, @Nonnull ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent) {
/* 246 */     this.objectiveLocationMarkerComponentType = objectiveLocationMarkerComponentType;
/* 247 */     this.playerRefComponentType = playerRefComponentType;
/*     */ 
/*     */     
/* 250 */     this.playerSpatialComponent = playerSpatialComponent;
/*     */     
/* 252 */     this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { objectiveLocationMarkerComponentType, this.transformComponentType, this.uuidComponentType });
/* 253 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 259 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 265 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 275 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 276 */     if (!world.getWorldConfig().isObjectiveMarkersEnabled())
/*     */       return; 
/* 278 */     store.tick((ArchetypeTickingSystem)this, dt, systemIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 283 */     ObjectiveLocationMarker objectiveLocationMarkerComponent = (ObjectiveLocationMarker)archetypeChunk.getComponent(index, this.objectiveLocationMarkerComponentType);
/* 284 */     assert objectiveLocationMarkerComponent != null;
/*     */     
/* 286 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 287 */     assert transformComponent != null;
/*     */     
/* 289 */     Ref<EntityStore> entityReference = archetypeChunk.getReferenceTo(index);
/* 290 */     Vector3d position = transformComponent.getPosition();
/* 291 */     Objective activeObjective = objectiveLocationMarkerComponent.getActiveObjective();
/*     */     
/* 293 */     if (activeObjective == null) {
/* 294 */       UUIDComponent uuidComponent = (UUIDComponent)archetypeChunk.getComponent(index, this.uuidComponentType);
/* 295 */       assert uuidComponent != null;
/* 296 */       UUID uuid = uuidComponent.getUuid();
/* 297 */       setupMarker(store, objectiveLocationMarkerComponent, entityReference, position, uuid, commandBuffer);
/*     */     }
/* 299 */     else if (!activeObjective.isCompleted()) {
/* 300 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/* 301 */       ObjectList<Ref<EntityStore>> playerReferences = SpatialResource.getThreadLocalReferenceList();
/* 302 */       objectiveLocationMarkerComponent.area.getPlayersInExitArea(spatialResource, (List)playerReferences, position);
/*     */       
/* 304 */       HashSet<UUID> playersInExitArea = new HashSet<>(playerReferences.size());
/* 305 */       PlayerRef[] playersInEntryArea = new PlayerRef[playerReferences.size()];
/* 306 */       int playersInEntryAreaSize = 0;
/*     */       
/* 308 */       for (ObjectListIterator<Ref<EntityStore>> objectListIterator = playerReferences.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> playerReference = objectListIterator.next();
/* 309 */         PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(playerReference, this.playerRefComponentType);
/* 310 */         assert playerRefComponent != null;
/*     */         
/* 312 */         UUIDComponent playerUuidComponent = (UUIDComponent)commandBuffer.getComponent(playerReference, this.uuidComponentType);
/* 313 */         assert playerUuidComponent != null;
/*     */         
/* 315 */         TransformComponent playerTransformComponent = (TransformComponent)commandBuffer.getComponent(playerReference, this.transformComponentType);
/* 316 */         assert playerTransformComponent != null;
/*     */         
/* 318 */         WeatherTracker playerWeatherTrackerComponent = (WeatherTracker)commandBuffer.getComponent(playerReference, this.weatherTrackerComponentType);
/* 319 */         assert playerWeatherTrackerComponent != null;
/*     */         
/* 321 */         if (!isPlayerInSpecificEnvironment(objectiveLocationMarkerComponent, playerWeatherTrackerComponent, playerTransformComponent, (ComponentAccessor<EntityStore>)commandBuffer)) {
/*     */           continue;
/*     */         }
/* 324 */         playersInExitArea.add(playerUuidComponent.getUuid());
/* 325 */         if (objectiveLocationMarkerComponent.area.isPlayerInEntryArea(playerTransformComponent.getPosition(), position)) {
/* 326 */           playersInEntryArea[playersInEntryAreaSize++] = playerRefComponent;
/*     */         } }
/*     */ 
/*     */       
/* 330 */       Set<UUID> playerUUIDs = activeObjective.getPlayerUUIDs();
/* 331 */       Set<UUID> activePlayerUUIDs = activeObjective.getActivePlayerUUIDs();
/* 332 */       String objectiveId = activeObjective.getObjectiveId();
/*     */       
/* 334 */       updateIncomingPlayers(playersInEntryArea, playersInEntryAreaSize, objectiveLocationMarkerComponent, playerUUIDs, activePlayerUUIDs, objectiveId);
/* 335 */       updateOutgoingPlayers(playersInExitArea, objectiveLocationMarkerComponent, activePlayerUUIDs, objectiveId);
/*     */     } else {
/* 337 */       commandBuffer.removeEntity(entityReference, RemoveReason.REMOVE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupMarker(@Nonnull Store<EntityStore> store, @Nonnull ObjectiveLocationMarker entity, @Nonnull Ref<EntityStore> entityReference, @Nonnull Vector3d position, @Nonnull UUID uuid, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 348 */     if (entity.triggerConditions != null && entity.triggerConditions.length > 0) {
/* 349 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 354 */       if (!entity.area.hasPlayerInExitArea(spatialResource, this.playerRefComponentType, position, commandBuffer)) {
/*     */         return;
/*     */       }
/*     */       
/* 358 */       for (ObjectiveLocationTriggerCondition triggerCondition : entity.triggerConditions) {
/* 359 */         if (!triggerCondition.isConditionMet((ComponentAccessor)commandBuffer, entityReference, entity)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 365 */     ObjectiveLocationMarkerAsset objectiveLocationMarkerAsset = (ObjectiveLocationMarkerAsset)ObjectiveLocationMarkerAsset.getAssetMap().getAsset(entity.objectiveLocationMarkerId);
/* 366 */     if (objectiveLocationMarkerAsset == null) {
/* 367 */       ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Could not find ObjectiveLocationMarker '%s'. Entity removed!", entity.objectiveLocationMarkerId);
/* 368 */       commandBuffer.removeEntity(entityReference, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 372 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 373 */     Objective objective = objectiveLocationMarkerAsset.getObjectiveTypeSetup().setup(new HashSet(), world.getWorldConfig().getUuid(), uuid, store);
/* 374 */     if (objective == null) {
/* 375 */       ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Objective failed to setup for ObjectiveLocationMarker '%s'. Entity removed!", entity.objectiveLocationMarkerId);
/* 376 */       commandBuffer.removeEntity(entityReference, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 380 */     entity.setActiveObjective(objective);
/* 381 */     entity.activeObjectiveUUID = objective.getObjectiveUUID();
/* 382 */     entity.setUntrackPacket(new UntrackObjective(entity.activeObjectiveUUID));
/*     */   }
/*     */   
/*     */   private static void updateIncomingPlayers(@Nonnull PlayerRef[] playersInArea, int playersInAreaSize, @Nonnull ObjectiveLocationMarker entity, @Nonnull Set<UUID> playerUUIDs, @Nonnull Set<UUID> activePlayerUUIDs, @Nonnull String objectiveId) {
/* 386 */     if (playersInArea.length == 0)
/*     */       return; 
/* 388 */     TrackOrUpdateObjective trackPacket = null;
/* 389 */     ObjectivePlugin objectiveModule = ObjectivePlugin.get();
/* 390 */     HytaleLogger logger = objectiveModule.getLogger();
/* 391 */     ObjectiveDataStore objectiveDataStore = objectiveModule.getObjectiveDataStore();
/*     */     
/* 393 */     for (int i = 0; i < playersInAreaSize; i++) {
/* 394 */       PlayerRef playerRef = playersInArea[i];
/* 395 */       UUID playerUUID = playerRef.getUuid();
/* 396 */       if (activePlayerUUIDs.add(playerUUID)) {
/*     */         
/* 398 */         playerUUIDs.add(playerUUID);
/*     */         
/* 400 */         logger.at(Level.FINE).log("Player '%s' joined the objective area for marker '%s', current objective '%s' with UUID '%s'", playerRef
/* 401 */             .getUsername(), entity.objectiveLocationMarkerId, objectiveId, entity.activeObjectiveUUID);
/*     */         
/* 403 */         if (trackPacket == null) {
/* 404 */           trackPacket = new TrackOrUpdateObjective(entity.getActiveObjective().toPacket());
/*     */         }
/*     */         
/* 407 */         playerRef.getPacketHandler().writeNoCache((Packet)trackPacket);
/*     */         
/* 409 */         for (ObjectiveTask task : entity.getActiveObjective().getCurrentTasks()) {
/* 410 */           if (task instanceof UseEntityObjectiveTask)
/*     */           {
/*     */ 
/*     */             
/* 414 */             objectiveDataStore.addEntityTaskForPlayer(playerUUID, ((UseEntityObjectiveTask)task).getAsset().getTaskId(), entity.activeObjectiveUUID);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateOutgoingPlayers(@Nonnull Set<UUID> playersInArea, @Nonnull ObjectiveLocationMarker entity, @Nullable Set<UUID> activePlayerUUIDs, @Nonnull String objectiveId) {
/* 424 */     if (activePlayerUUIDs == null || activePlayerUUIDs.isEmpty())
/*     */       return; 
/* 426 */     HytaleLogger logger = ObjectivePlugin.get().getLogger();
/* 427 */     Iterator<UUID> iterator = activePlayerUUIDs.iterator();
/* 428 */     Universe universe = Universe.get();
/*     */     
/* 430 */     while (iterator.hasNext()) {
/* 431 */       UUID uuid = iterator.next();
/* 432 */       if (playersInArea.contains(uuid))
/*     */         continue; 
/* 434 */       iterator.remove();
/* 435 */       untrackEntityObjectiveForPlayer(entity, uuid);
/*     */       
/* 437 */       PlayerRef playerRef = universe.getPlayer(uuid);
/* 438 */       logger.at(Level.FINE).log("Player '%s' left the objective area for marker '%s', current objective '%s' with UUID '%s'", 
/* 439 */           (playerRef == null) ? uuid : playerRef.getUsername(), entity.objectiveLocationMarkerId, objectiveId, entity.activeObjectiveUUID);
/* 440 */       if (playerRef != null) playerRef.getPacketHandler().write((Packet)entity.getUntrackPacket());
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPlayerInSpecificEnvironment(@Nonnull ObjectiveLocationMarker entity, @Nonnull WeatherTracker weatherTracker, @Nonnull TransformComponent transform, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 449 */     if (entity.environmentIndexes == null) return true;
/*     */ 
/*     */     
/* 452 */     weatherTracker.updateEnvironment(transform, componentAccessor);
/* 453 */     int environmentIndex = weatherTracker.getEnvironmentId();
/* 454 */     return (Arrays.binarySearch(entity.environmentIndexes, environmentIndex) >= 0);
/*     */   }
/*     */   
/*     */   private static void untrackEntityObjectiveForPlayer(@Nonnull ObjectiveLocationMarker entity, @Nonnull UUID playerUUID) {
/* 458 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*     */     
/* 460 */     for (ObjectiveTask task : entity.getActiveObjective().getCurrentTasks()) {
/* 461 */       if (task instanceof UseEntityObjectiveTask) {
/*     */ 
/*     */ 
/*     */         
/* 465 */         String taskId = ((UseEntityObjectiveTask)task).getAsset().getTaskId();
/* 466 */         objectiveDataStore.removeEntityTaskForPlayer(entity.activeObjectiveUUID, taskId, playerUUID);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\objectivelocation\ObjectiveLocationMarkerSystems$TickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */