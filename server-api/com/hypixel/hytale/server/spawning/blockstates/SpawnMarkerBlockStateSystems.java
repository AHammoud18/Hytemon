/*     */ package com.hypixel.hytale.server.spawning.blockstates;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.reference.PersistentRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnMarkerBlockStateSystems {
/*  23 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*     */   public static class AddOrRemove
/*     */     extends RefSystem<ChunkStore>
/*     */   {
/*     */     private final ComponentType<ChunkStore, SpawnMarkerBlockState> componentType;
/*     */     
/*     */     public AddOrRemove(ComponentType<ChunkStore, SpawnMarkerBlockState> componentType) {
/*  32 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/*  37 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  46 */       if (reason == RemoveReason.REMOVE) {
/*  47 */         SpawnMarkerBlockState state = (SpawnMarkerBlockState)store.getComponent(ref, this.componentType);
/*  48 */         PersistentRef markerReference = state.getSpawnMarkerReference();
/*  49 */         if (markerReference == null)
/*  50 */           return;  World world = ((ChunkStore)store.getExternalData()).getWorld();
/*  51 */         world.execute(() -> {
/*     */               Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */               Ref<EntityStore> marker = markerReference.getEntity((ComponentAccessor)entityStore);
/*     */               if (marker != null) {
/*     */                 entityStore.removeEntity(marker, RemoveReason.REMOVE);
/*     */               }
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class TickHeartbeat
/*     */     extends EntityTickingSystem<ChunkStore>
/*     */   {
/*     */     private final ComponentType<ChunkStore, SpawnMarkerBlockState> componentType;
/*     */ 
/*     */     
/*     */     public TickHeartbeat(ComponentType<ChunkStore, SpawnMarkerBlockState> componentType) {
/*  70 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  75 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/*  80 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  85 */       SpawnMarkerBlockState state = (SpawnMarkerBlockState)archetypeChunk.getComponent(index, this.componentType);
/*  86 */       if (state.getSpawnMarkerReference() == null) {
/*     */         
/*  88 */         Ref<ChunkStore> ref = archetypeChunk.getReferenceTo(index);
/*  89 */         SpawnMarkerBlockStateSystems.createMarker(ref, state, ((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore(), commandBuffer);
/*     */       } 
/*     */       
/*  92 */       if (state.getSpawnMarkerReference().getEntity((ComponentAccessor)((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore()) != null) {
/*  93 */         state.refreshMarkerLostTimeout();
/*  94 */       } else if (state.tickMarkerLostTimeout(dt)) {
/*     */ 
/*     */         
/*  97 */         Ref<ChunkStore> ref = archetypeChunk.getReferenceTo(index);
/*  98 */         SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Creating new spawn marker due to desync with entity: %s", ref);
/*  99 */         SpawnMarkerBlockStateSystems.createMarker(ref, state, ((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore(), commandBuffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SpawnMarkerAddedFromExternal
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public SpawnMarkerAddedFromExternal(ComponentType<EntityStore, SpawnMarkerBlockReference> componentType) {
/* 112 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)Query.or(new Query[] { (Query)FromWorldGen.getComponentType(), (Query)FromPrefab.getComponentType() }) });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 118 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 123 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SpawnMarkerTickHeartbeat
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, SpawnMarkerBlockReference> componentType;
/*     */ 
/*     */     
/*     */     public SpawnMarkerTickHeartbeat(ComponentType<EntityStore, SpawnMarkerBlockReference> componentType) {
/* 138 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 143 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 148 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 153 */       SpawnMarkerBlockReference marker = (SpawnMarkerBlockReference)archetypeChunk.getComponent(index, this.componentType);
/* 154 */       Vector3i pos = marker.getBlockPosition();
/* 155 */       WorldChunk chunk = ((EntityStore)store.getExternalData()).getWorld().getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(pos.x, pos.z));
/* 156 */       if (chunk != null) {
/* 157 */         BlockState state = chunk.getState(pos.x, pos.y, pos.z);
/* 158 */         if (!(state instanceof SpawnMarkerBlockState)) {
/* 159 */           Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 160 */           commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/* 161 */           SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Removing block spawn marker due to blockstate mismatch: %s", ref);
/*     */         } else {
/* 163 */           marker.refreshOriginLostTimeout();
/*     */         } 
/* 165 */       } else if (marker.tickOriginLostTimeout(dt)) {
/* 166 */         Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 167 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/* 168 */         SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Removing block spawn marker due to origin chunk being unloaded: %s", ref);
/*     */       } 
/*     */     } }
/*     */   
/*     */   private static void createMarker(@Nonnull Ref<ChunkStore> ref, @Nonnull SpawnMarkerBlockState state, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */     SpawnMarkerBlockState.Data stateData;
/* 174 */     StateData data = state.getBlockType().getState();
/* 175 */     if (data instanceof SpawnMarkerBlockState.Data) { stateData = (SpawnMarkerBlockState.Data)data; }
/*     */     else
/*     */     { return; }
/*     */     
/* 179 */     SpawnMarker marker = (SpawnMarker)SpawnMarker.getAssetMap().getAsset(stateData.getSpawnMarker());
/* 180 */     if (marker == null) {
/* 181 */       LOGGER.at(Level.SEVERE).log(String.format("Marker %s does not exist!", new Object[] { stateData.getSpawnMarker() }));
/* 182 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 186 */     Vector3i pos = state.getBlockPosition();
/* 187 */     Vector3i offset = stateData.getMarkerOffset();
/* 188 */     if (offset != null) {
/* 189 */       pos.add(offset);
/*     */     }
/*     */     
/* 192 */     SpawnMarkerEntity spawnMarker = new SpawnMarkerEntity();
/* 193 */     spawnMarker.setSpawnMarker(marker);
/*     */     
/* 195 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 196 */     holder.addComponent(SpawnMarkerEntity.getComponentType(), (Component)spawnMarker);
/*     */     
/* 198 */     holder.addComponent(SpawnMarkerBlockReference.getComponentType(), new SpawnMarkerBlockReference(state.getBlockPosition()));
/*     */     
/* 200 */     Vector3d markerPos = pos.toVector3d();
/* 201 */     markerPos.add(0.5D, 0.0D, 0.5D);
/* 202 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(marker.getId()));
/* 203 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(markerPos, Vector3f.ZERO));
/* 204 */     UUIDComponent uuidComponent = (UUIDComponent)holder.ensureAndGetComponent(UUIDComponent.getComponentType());
/* 205 */     Model model = SpawnMarkerEntity.getModel(marker);
/* 206 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 207 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 208 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/* 209 */     Ref<EntityStore> markerRef = store.addEntity(holder, AddReason.SPAWN);
/*     */     
/* 211 */     PersistentRef persistentRef = new PersistentRef();
/*     */     
/* 213 */     persistentRef.setEntity(markerRef, uuidComponent.getUuid());
/* 214 */     state.setSpawnMarkerReference(persistentRef);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockStateSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */