/*     */ package com.hypixel.hytale.server.spawning.spawnmarkers;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
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
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.entity.reference.InvalidatablePersistentRef;
/*     */ import com.hypixel.hytale.server.core.entity.reference.PersistentRefCount;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.StoredFlock;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnMarkerReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class SpawnMarkerSystems {
/*  47 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class LegacyEntityMigration
/*     */     extends EntityModule.MigrationSystem
/*     */   {
/*  54 */     private final ComponentType<EntityStore, PersistentModel> persistentModelComponentType = PersistentModel.getComponentType();
/*  55 */     private final ComponentType<EntityStore, Nameplate> nameplateComponentType = Nameplate.getComponentType();
/*  56 */     private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*  57 */     private final ComponentType<EntityStore, UnknownComponents<EntityStore>> unknownComponentsComponentType = EntityStore.REGISTRY.getUnknownComponentType();
/*  58 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.unknownComponentsComponentType, (Query)Query.not((Query)AllLegacyEntityTypesQuery.INSTANCE) });
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  62 */       Map<String, BsonDocument> unknownComponents = ((UnknownComponents)holder.getComponent(this.unknownComponentsComponentType)).getUnknownComponents();
/*     */       
/*  64 */       BsonDocument spawnMarker = unknownComponents.remove("SpawnMarker");
/*  65 */       if (spawnMarker == null)
/*     */         return; 
/*  67 */       if (!holder.getArchetype().contains(this.persistentModelComponentType)) {
/*  68 */         Model.ModelReference modelReference = Entity.MODEL.get(spawnMarker).get();
/*  69 */         holder.addComponent(this.persistentModelComponentType, (Component)new PersistentModel(modelReference));
/*     */       } 
/*     */       
/*  72 */       if (!holder.getArchetype().contains(this.nameplateComponentType)) {
/*  73 */         holder.addComponent(this.nameplateComponentType, (Component)new Nameplate(Entity.DISPLAY_NAME.get(spawnMarker).get()));
/*     */       }
/*     */       
/*  76 */       if (!holder.getArchetype().contains(this.uuidComponentType)) {
/*  77 */         holder.addComponent(this.uuidComponentType, (Component)new UUIDComponent(Entity.UUID.get(spawnMarker).get()));
/*     */       }
/*     */       
/*  80 */       holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */       
/*  82 */       int worldgenId = ((Integer)Codec.INTEGER.decode(spawnMarker.get("WorldgenId"))).intValue();
/*  83 */       if (worldgenId != 0) holder.addComponent(WorldGenId.getComponentType(), (Component)new WorldGenId(worldgenId));
/*     */       
/*  85 */       SpawnMarkerEntity marker = (SpawnMarkerEntity)SpawnMarkerEntity.CODEC.decode((BsonValue)spawnMarker, new ExtraInfo(5));
/*  86 */       holder.addComponent(SpawnMarkerEntity.getComponentType(), marker);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  96 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 102 */       return RootDependency.firstSet();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EnsureNetworkSendable extends HolderSystem<EntityStore> {
/* 107 */     private final Query<EntityStore> query = (Query)SpawnMarkerEntity.getComponentType();
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 111 */       if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/* 112 */         holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */       }
/* 114 */       holder.ensureComponent(Intangible.getComponentType());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 123 */       return this.query;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CacheMarker extends RefSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> componentType;
/*     */     
/*     */     public CacheMarker(ComponentType<EntityStore, SpawnMarkerEntity> componentType) {
/* 131 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 136 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 141 */       SpawnMarkerEntity entity = (SpawnMarkerEntity)store.getComponent(ref, this.componentType);
/* 142 */       SpawnMarker marker = (SpawnMarker)SpawnMarker.getAssetMap().getAsset(entity.getSpawnMarkerId());
/* 143 */       if (marker == null) {
/*     */         
/* 145 */         SpawnMarkerSystems.LOGGER.at(Level.SEVERE).log("Marker %s removed due to missing spawn marker type: %s", ref, entity.getSpawnMarkerId());
/* 146 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */         return;
/*     */       } 
/* 149 */       entity.setCachedMarker(marker);
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */   
/*     */   public static class EntityAdded
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> componentType;
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, UUIDComponent> uuidComponentType;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public EntityAdded(ComponentType<EntityStore, SpawnMarkerEntity> componentType) {
/* 167 */       this.componentType = componentType;
/* 168 */       this.uuidComponentType = UUIDComponent.getComponentType();
/* 169 */       this.dependencies = Set.of(new SystemDependency(Order.AFTER, SpawnMarkerSystems.CacheMarker.class));
/* 170 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.uuidComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 176 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 182 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 187 */       SpawnMarkerEntity entity = (SpawnMarkerEntity)store.getComponent(ref, this.componentType);
/* 188 */       HytaleLogger.Api context = SpawnMarkerSystems.LOGGER.at(Level.FINE);
/* 189 */       if (context.isEnabled()) {
/* 190 */         context.log("Loaded marker %s", store.getComponent(ref, this.uuidComponentType));
/*     */       }
/* 192 */       if (entity.getStoredFlock() != null) entity.setTempStorageList((List<Pair<Ref<EntityStore>, NPCEntity>>)new ObjectArrayList()); 
/* 193 */       if (entity.getSpawnCount() != 0) {
/* 194 */         entity.refreshTimeout();
/*     */       }
/* 196 */       commandBuffer.ensureComponent(ref, PrefabCopyableComponent.getComponentType());
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */   
/*     */   public static class EntityAddedFromExternal
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> componentType;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public EntityAddedFromExternal(ComponentType<EntityStore, SpawnMarkerEntity> componentType) {
/* 212 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)Query.or(new Query[] { (Query)FromPrefab.getComponentType(), (Query)FromWorldGen.getComponentType() }) });
/* 213 */       this.componentType = componentType;
/* 214 */       this.dependencies = Set.of(new SystemDependency(Order.BEFORE, SpawnMarkerSystems.EntityAdded.class), new SystemDependency(Order.AFTER, SpawnMarkerSystems.CacheMarker.class));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 219 */       SpawnMarkerEntity entity = (SpawnMarkerEntity)store.getComponent(ref, this.componentType);
/* 220 */       entity.setSpawnCount(0);
/* 221 */       entity.setRespawnCounter(0.0D);
/* 222 */       entity.setSpawnAfter(null);
/* 223 */       entity.setGameTimeRespawn(null);
/* 224 */       if (entity.getCachedMarker().getDeactivationDistance() > 0.0D) entity.setStoredFlock(new StoredFlock());
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 234 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 240 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 246 */       return EntityModule.get().getPreClearMarkersGroup();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AddedFromWorldGen extends HolderSystem<EntityStore> {
/* 251 */     private final ComponentType<EntityStore, SpawnMarkerEntity> componentType = SpawnMarkerEntity.getComponentType();
/* 252 */     private final ComponentType<EntityStore, WorldGenId> worldGenIdComponentType = WorldGenId.getComponentType();
/* 253 */     private final ComponentType<EntityStore, FromWorldGen> fromWorldGenComponentType = FromWorldGen.getComponentType();
/* 254 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.componentType, (Query)this.fromWorldGenComponentType });
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 259 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 265 */       return EntityModule.get().getPreClearMarkersGroup();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 271 */       holder.putComponent(this.worldGenIdComponentType, (Component)new WorldGenId(((FromWorldGen)holder.getComponent(this.fromWorldGenComponentType)).getWorldGenId()));
/*     */     }
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */   }
/*     */   
/*     */   public static class Ticking
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, SpawnMarkerEntity> componentType;
/*     */     @Nullable
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */     private final ComponentType<EntityStore, PersistentRefCount> referenceIdComponentType;
/*     */     private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     private final Query<EntityStore> query;
/* 288 */     private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/* 289 */     private final ComponentType<EntityStore, HeadRotation> headRotationComponentType = HeadRotation.getComponentType();
/* 290 */     private final ComponentType<EntityStore, ModelComponent> modelComponentType = ModelComponent.getComponentType();
/*     */ 
/*     */     
/*     */     public Ticking(ComponentType<EntityStore, SpawnMarkerEntity> componentType, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent) {
/* 294 */       this.componentType = componentType;
/* 295 */       this.npcComponentType = NPCEntity.getComponentType();
/* 296 */       this.referenceIdComponentType = PersistentRefCount.getComponentType();
/*     */ 
/*     */       
/* 299 */       this.playerSpatialComponent = playerSpatialComponent;
/* 300 */       this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*     */       
/* 302 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { componentType, this.transformComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 308 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 313 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 318 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 323 */       SpawnMarkerEntity entity = (SpawnMarkerEntity)archetypeChunk.getComponent(index, this.componentType);
/* 324 */       TransformComponent transform = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 325 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 326 */       SpawnMarker cachedMarker = entity.getCachedMarker();
/* 327 */       if (entity.getSpawnCount() > 0) {
/* 328 */         StoredFlock storedFlock = entity.getStoredFlock();
/* 329 */         if (storedFlock != null) {
/*     */ 
/*     */ 
/*     */           
/* 333 */           SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/* 334 */           ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 335 */           spatialResource.getSpatialStructure().collect(transform.getPosition(), cachedMarker.getDeactivationDistance(), (List)results);
/*     */           
/* 337 */           boolean hasPlayersInRange = !results.isEmpty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 350 */           if (hasPlayersInRange) {
/* 351 */             if (storedFlock.hasStoredNPCs())
/*     */             {
/* 353 */               commandBuffer.run(_store -> {
/*     */                     ObjectList<Ref<EntityStore>> tempStorageList = SpatialResource.getThreadLocalReferenceList();
/*     */                     
/*     */                     storedFlock.restoreNPCs((List)tempStorageList, _store);
/*     */                     
/*     */                     entity.setSpawnCount(tempStorageList.size());
/*     */                     
/*     */                     Vector3d position = entity.getSpawnPosition();
/*     */                     
/*     */                     Vector3f rotation = transform.getRotation();
/*     */                     
/*     */                     InvalidatablePersistentRef[] npcReferences = new InvalidatablePersistentRef[tempStorageList.size()];
/*     */                     int i = 0;
/*     */                     int bound = tempStorageList.size();
/*     */                     while (i < bound) {
/*     */                       Ref<EntityStore> ref = (Ref<EntityStore>)tempStorageList.get(i);
/*     */                       NPCEntity npc = (NPCEntity)_store.getComponent(ref, this.npcComponentType);
/*     */                       TransformComponent npcTransform = (TransformComponent)_store.getComponent(ref, this.transformComponentType);
/*     */                       HeadRotation npcHeadRotation = (HeadRotation)_store.getComponent(ref, this.headRotationComponentType);
/*     */                       InvalidatablePersistentRef reference = new InvalidatablePersistentRef();
/*     */                       reference.setEntity(ref, (ComponentAccessor)_store);
/*     */                       npcReferences[i] = reference;
/*     */                       npcTransform.getPosition().assign(position);
/*     */                       npcTransform.getRotation().assign(rotation);
/*     */                       npcHeadRotation.setRotation(rotation);
/*     */                       npc.playAnimation(ref, AnimationSlot.Status, null, (ComponentAccessor)commandBuffer);
/*     */                       i++;
/*     */                     } 
/*     */                     entity.setNpcReferences(npcReferences);
/*     */                     entity.setDespawnStarted(false);
/*     */                     entity.setTimeToDeactivation(cachedMarker.getDeactivationTime());
/*     */                   });
/*     */             }
/*     */           } else {
/* 387 */             if (!storedFlock.hasStoredNPCs() && entity.tickTimeToDeactivation(dt)) {
/*     */               
/* 389 */               InvalidatablePersistentRef[] npcReferences = entity.getNpcReferences();
/* 390 */               if (npcReferences == null)
/*     */                 return; 
/* 392 */               if (!entity.isDespawnStarted()) {
/*     */ 
/*     */                 
/* 395 */                 List<Pair<Ref<EntityStore>, NPCEntity>> tempStorageList = entity.getTempStorageList();
/* 396 */                 for (InvalidatablePersistentRef reference : npcReferences) {
/*     */ 
/*     */                   
/* 399 */                   Ref<EntityStore> npcRef = reference.getEntity((ComponentAccessor)commandBuffer);
/* 400 */                   if (npcRef != null) {
/*     */                     
/* 402 */                     NPCEntity npcComponent = (NPCEntity)commandBuffer.getComponent(npcRef, this.npcComponentType);
/* 403 */                     assert npcComponent != null;
/*     */                     
/* 405 */                     tempStorageList.add(Pair.of(npcRef, npcComponent));
/*     */                     
/* 407 */                     boolean isDead = commandBuffer.getArchetype(npcRef).contains(DeathComponent.getComponentType());
/*     */ 
/*     */                     
/* 410 */                     if (isDead || npcComponent.getRole().getStateSupport().isInBusyState()) {
/* 411 */                       entity.setTimeToDeactivation(cachedMarker.getDeactivationTime());
/* 412 */                       tempStorageList.clear();
/*     */                       
/*     */                       return;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 418 */                 for (int i = 0; i < tempStorageList.size(); i++) {
/* 419 */                   Pair<Ref<EntityStore>, NPCEntity> npcPair = tempStorageList.get(i);
/* 420 */                   Ref<EntityStore> npcRef = (Ref<EntityStore>)npcPair.first();
/* 421 */                   NPCEntity npcComponent = (NPCEntity)npcPair.second();
/*     */                   
/* 423 */                   ModelComponent modelComponent = (ModelComponent)commandBuffer.getComponent(npcRef, this.modelComponentType);
/* 424 */                   if (modelComponent != null && modelComponent.getModel().getAnimationSetMap().containsKey("Despawn")) {
/* 425 */                     double despawnAnimationTime = npcComponent.getRole().getDespawnAnimationTime();
/*     */                     
/* 427 */                     if (despawnAnimationTime > entity.getTimeToDeactivation()) {
/* 428 */                       entity.setTimeToDeactivation(despawnAnimationTime);
/*     */                     }
/* 430 */                     npcComponent.playAnimation(npcRef, AnimationSlot.Status, "Despawn", (ComponentAccessor)commandBuffer);
/*     */                   } 
/*     */                 } 
/* 433 */                 entity.setDespawnStarted(true);
/* 434 */                 tempStorageList.clear();
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/* 440 */               PersistentRefCount refId = (PersistentRefCount)archetypeChunk.getComponent(index, this.referenceIdComponentType);
/* 441 */               if (refId != null) {
/* 442 */                 refId.increment();
/*     */               }
/*     */               
/* 445 */               Ref<EntityStore> ref1 = archetypeChunk.getReferenceTo(index);
/* 446 */               commandBuffer.run(_store -> {
/*     */                     ObjectList<Ref<EntityStore>> tempStorageList = SpatialResource.getThreadLocalReferenceList();
/*     */                     
/*     */                     for (InvalidatablePersistentRef reference : npcReferences) {
/*     */                       Ref<EntityStore> npcRef = reference.getEntity((ComponentAccessor)_store);
/*     */                       
/*     */                       if (npcRef == null) {
/*     */                         ((HytaleLogger.Api)SpawnMarkerSystems.LOGGER.atWarning()).log("Connection with NPC from marker at %s lost due to being invalid/already unloaded", transform.getPosition());
/*     */                       } else {
/*     */                         SpawnMarkerReference spawnMarkerReference = (SpawnMarkerReference)_store.ensureAndGetComponent(npcRef, SpawnMarkerReference.getComponentType());
/*     */                         
/*     */                         spawnMarkerReference.getReference().setEntity(ref, (ComponentAccessor)store);
/*     */                         
/*     */                         tempStorageList.add(npcRef);
/*     */                       } 
/*     */                     } 
/*     */                     storedFlock.storeNPCs((List)tempStorageList, _store);
/*     */                     entity.setNpcReferences(null);
/*     */                   });
/*     */             } 
/*     */             return;
/*     */           } 
/*     */         } 
/* 469 */         if (entity.tickSpawnLostTimeout(dt)) {
/*     */ 
/*     */           
/* 472 */           PersistentRefCount refId = (PersistentRefCount)archetypeChunk.getComponent(index, this.referenceIdComponentType);
/* 473 */           if (refId != null) {
/* 474 */             refId.increment();
/* 475 */             SpawnMarkerSystems.LOGGER.at(Level.FINE).log("Marker lost spawned NPC and changed reference ID to %s", refId.get());
/*     */           } 
/* 477 */           Ref<EntityStore> ref1 = archetypeChunk.getReferenceTo(index);
/* 478 */           commandBuffer.run(_store -> entity.spawnNPC(ref, cachedMarker, _store));
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 483 */       if (!world.getWorldConfig().isSpawnMarkersEnabled() || cachedMarker.isManualTrigger() || (entity.getSuppressedBy() != null && 
/* 484 */         !entity.getSuppressedBy().isEmpty())) {
/*     */         return;
/*     */       }
/*     */       
/* 488 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 489 */       WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*     */ 
/*     */       
/* 492 */       if (cachedMarker.isRealtimeRespawn()) {
/* 493 */         if (entity.tickRespawnTimer(dt)) {
/* 494 */           commandBuffer.run(_store -> entity.spawnNPC(ref, cachedMarker, _store));
/*     */         }
/* 496 */       } else if (entity.getSpawnAfter() == null || worldTimeResource.getGameTime().isAfter(entity.getSpawnAfter())) {
/* 497 */         commandBuffer.run(_store -> entity.spawnNPC(ref, cachedMarker, _store));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\spawnmarkers\SpawnMarkerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */