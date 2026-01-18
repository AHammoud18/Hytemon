/*     */ package com.hypixel.hytale.server.spawning.suppression.system;
/*     */ 
/*     */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromPrefab;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import com.hypixel.hytale.server.spawning.suppression.SpawnSuppressorEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionQueue;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionComponent;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class Suppressor
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, SpawnSuppressionComponent> spawnSuppressorComponentType;
/*     */   private final ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/*     */   private final ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType;
/*     */   private final ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType;
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spawnMarkerSpatialResourceType;
/* 168 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/* 169 */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */   
/*     */   public Suppressor(ComponentType<EntityStore, SpawnSuppressionComponent> spawnSuppressorComponentType, ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType, ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType, ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spawnMarkerSpatialResourceType) {
/* 177 */     this.spawnSuppressorComponentType = spawnSuppressorComponentType;
/* 178 */     this.spawnSuppressionControllerResourceType = spawnSuppressionControllerResourceType;
/* 179 */     this.spawnMarkerEntityComponentType = spawnMarkerEntityComponentType;
/* 180 */     this.chunkSuppressionQueueResourceType = chunkSuppressionQueueResourceType;
/* 181 */     this.spawnMarkerSpatialResourceType = spawnMarkerSpatialResourceType;
/*     */     
/* 183 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)spawnSuppressorComponentType, (Query)this.transformComponentType, (Query)this.uuidComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 189 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 195 */     return EntityModule.get().getPreClearMarkersGroup();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> reference, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 200 */     Archetype<EntityStore> archetype = store.getArchetype(reference);
/*     */     
/* 202 */     SpawnSuppressionComponent suppressor = (SpawnSuppressionComponent)store.getComponent(reference, this.spawnSuppressorComponentType);
/* 203 */     TransformComponent transform = (TransformComponent)commandBuffer.getComponent(reference, this.transformComponentType);
/* 204 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(reference, this.uuidComponentType);
/*     */ 
/*     */     
/* 207 */     boolean fromExternal = (archetype.contains(FromPrefab.getComponentType()) || archetype.contains(FromWorldGen.getComponentType()));
/* 208 */     if (reason != AddReason.SPAWN && !fromExternal)
/*     */       return; 
/* 210 */     SpawnSuppressionController suppressionController = (SpawnSuppressionController)store.getResource(this.spawnSuppressionControllerResourceType);
/* 211 */     SpawnSuppressorEntry entry = new SpawnSuppressorEntry(suppressor.getSpawnSuppression(), transform.getPosition().clone());
/* 212 */     UUID uuid = uuidComponent.getUuid();
/* 213 */     SpawnSuppressorEntry prev = suppressionController.getSpawnSuppressorMap().put(uuid, entry);
/* 214 */     if (prev != null) {
/* 215 */       throw new IllegalStateException(String.format("A spawn suppressor with the ID %s is already registered.", new Object[] { uuid }));
/*     */     }
/* 217 */     SpawnSuppressionSystems.suppressSpawns(this.chunkSuppressionQueueResourceType, this.spawnMarkerEntityComponentType, uuid, entry, suppressionController, store, ((EntityStore)store.getExternalData()).getWorld().getChunkStore());
/*     */     
/* 219 */     commandBuffer.ensureComponent(reference, PrefabCopyableComponent.getComponentType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> reference, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 224 */     if (reason != RemoveReason.REMOVE)
/*     */       return; 
/* 226 */     SpawnSuppressionController suppressionController = (SpawnSuppressionController)store.getResource(this.spawnSuppressionControllerResourceType);
/* 227 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(reference, this.uuidComponentType);
/* 228 */     Map<UUID, SpawnSuppressorEntry> spawnSuppressorMap = suppressionController.getSpawnSuppressorMap();
/* 229 */     UUID uuid = uuidComponent.getUuid();
/* 230 */     SpawnSuppressorEntry entry = spawnSuppressorMap.remove(uuid);
/* 231 */     String suppressionId = entry.getSuppressionId();
/* 232 */     SpawnSuppression suppression = (SpawnSuppression)SpawnSuppression.getAssetMap().getAsset(suppressionId);
/* 233 */     if (suppression == null) {
/* 234 */       SpawningPlugin.get().getLogger().at(Level.WARNING).log("Spawn suppression config '%s' does not exist", suppressionId);
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     double radius = suppression.getRadius();
/* 239 */     Vector3d position = entry.getPosition();
/* 240 */     int minChunkX = MathUtil.floor(position.x - radius) >> 5;
/* 241 */     int minChunkZ = MathUtil.floor(position.z - radius) >> 5;
/* 242 */     int maxChunkX = MathUtil.floor(position.x + radius) >> 5;
/* 243 */     int maxChunkZ = MathUtil.floor(position.z + radius) >> 5;
/*     */     
/* 245 */     ChunkStore chunkComponentStore = ((EntityStore)store.getExternalData()).getWorld().getChunkStore();
/* 246 */     Store<ChunkStore> chunkStore = chunkComponentStore.getStore();
/* 247 */     Long2ObjectConcurrentHashMap<ChunkSuppressionEntry> chunkSuppressionMap = suppressionController.getChunkSuppressionMap();
/* 248 */     for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
/* 249 */       for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
/* 250 */         long chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/*     */         
/* 252 */         ChunkSuppressionEntry chunkEntry = null;
/* 253 */         ChunkSuppressionEntry oldEntry = (ChunkSuppressionEntry)chunkSuppressionMap.get(chunkIndex);
/* 254 */         if (oldEntry.containsOnly(uuid)) {
/* 255 */           chunkSuppressionMap.remove(chunkIndex);
/*     */         } else {
/*     */           
/* 258 */           List<ChunkSuppressionEntry.SuppressionSpan> oldSpans = oldEntry.getSuppressionSpans();
/* 259 */           ObjectArrayList<ChunkSuppressionEntry.SuppressionSpan> suppressedSpans = new ObjectArrayList();
/* 260 */           for (ChunkSuppressionEntry.SuppressionSpan span : oldSpans) {
/* 261 */             if (span.getSuppressorId().equals(uuid))
/*     */               continue; 
/* 263 */             suppressedSpans.add(span);
/*     */           } 
/* 265 */           chunkEntry = new ChunkSuppressionEntry((List)suppressedSpans);
/* 266 */           chunkSuppressionMap.put(chunkIndex, chunkEntry);
/*     */         } 
/*     */ 
/*     */         
/* 270 */         Ref<ChunkStore> chunkReference = chunkComponentStore.getChunkReference(chunkIndex);
/* 271 */         if (chunkReference != null) {
/*     */           
/* 273 */           ChunkSuppressionQueue chunkSuppressionQueue = (ChunkSuppressionQueue)chunkStore.getResource(this.chunkSuppressionQueueResourceType);
/* 274 */           if (chunkEntry == null) {
/* 275 */             chunkSuppressionQueue.queueForRemove(chunkReference);
/*     */           } else {
/* 277 */             chunkSuppressionQueue.queueForAdd(chunkReference, chunkEntry);
/*     */           } 
/* 279 */           SpawningPlugin.get().getLogger().at(Level.FINEST).log("Queuing removal of suppression from chunk index %s, %s", chunkIndex, suppressionId);
/*     */         } 
/*     */       } 
/* 282 */     }  if (!suppression.isSuppressSpawnMarkers()) {
/*     */       return;
/*     */     }
/*     */     
/* 286 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 287 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.spawnMarkerSpatialResourceType);
/* 288 */     spatialResource.getSpatialStructure().collect(position, radius, (List)results);
/* 289 */     for (int i = 0; i < results.size(); i++) {
/* 290 */       Ref<EntityStore> markerRef = (Ref<EntityStore>)results.get(i);
/* 291 */       SpawnMarkerEntity marker = (SpawnMarkerEntity)commandBuffer.getComponent(markerRef, this.spawnMarkerEntityComponentType);
/* 292 */       marker.releaseSuppression(uuid);
/* 293 */       HytaleLogger.Api context = SpawningPlugin.get().getLogger().at(Level.FINEST);
/* 294 */       if (context.isEnabled())
/* 295 */         context.log("Releasing suppression of spawn marker %s", ((UUIDComponent)commandBuffer.getComponent(markerRef, this.uuidComponentType)).getUuid()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\system\SpawnSuppressionSystems$Suppressor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */