/*     */ package com.hypixel.hytale.server.spawning.suppression.system;
/*     */ 
/*     */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemovedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.StoreSystem;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.event.IEventRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import com.hypixel.hytale.server.spawning.suppression.SpawnSuppressorEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionQueue;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class Load
/*     */   extends StoreSystem<EntityStore>
/*     */ {
/*     */   private final ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/*     */   private final ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType;
/*     */   private final ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType;
/*     */   private final ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType;
/*     */   @Nonnull
/*     */   private final EventRegistry eventRegistry;
/*     */   
/*     */   public Load(ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType, ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType, ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType, ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType) {
/*  71 */     this.spawnSuppressionControllerResourceType = spawnSuppressionControllerResourceType;
/*  72 */     this.spawnMarkerEntityComponentType = spawnMarkerEntityComponentType;
/*  73 */     this.chunkSuppressionQueueResourceType = chunkSuppressionQueueResourceType;
/*  74 */     this.chunkSuppressionEntryComponentType = chunkSuppressionEntryComponentType;
/*     */ 
/*     */     
/*  77 */     this.eventRegistry = new EventRegistry(new CopyOnWriteArrayList(), () -> true, null, (IEventRegistry)SpawningPlugin.get().getEventRegistry());
/*  78 */     this.eventRegistry.register(LoadedAssetsEvent.class, SpawnSuppression.class, this::onSpawnSuppressionsLoaded);
/*  79 */     this.eventRegistry.register(RemovedAssetsEvent.class, SpawnSuppression.class, this::onSpawnSuppressionsRemoved);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/*  84 */     SpawnSuppressionController resource = (SpawnSuppressionController)store.getResource(this.spawnSuppressionControllerResourceType);
/*  85 */     Map<UUID, SpawnSuppressorEntry> spawnSuppressorMap = resource.getSpawnSuppressorMap();
/*  86 */     spawnSuppressorMap.forEach((id, entry) -> SpawnSuppressionSystems.suppressSpawns(this.chunkSuppressionQueueResourceType, this.spawnMarkerEntityComponentType, id, entry, resource, store, ((EntityStore)store.getExternalData()).getWorld().getChunkStore()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */   
/*     */   public void onSystemUnregistered() {
/*  95 */     this.eventRegistry.shutdown();
/*     */   }
/*     */   
/*     */   private void onSpawnSuppressionsLoaded(@Nonnull LoadedAssetsEvent<String, SpawnSuppression, IndexedAssetMap<String, SpawnSuppression>> event) {
/*  99 */     Map<String, SpawnSuppression> loadedAssets = event.getLoadedAssets();
/* 100 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onSpawnSuppressionsRemoved(@Nonnull RemovedAssetsEvent<String, SpawnSuppression, IndexedAssetMap<String, SpawnSuppression>> event) {
/* 120 */     Set<String> removedAssets = event.getRemovedAssets();
/* 121 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebuildSuppressionMap(@Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull SpawnSuppressionController suppressionController) {
/* 141 */     SpawningPlugin.get().getLogger().at(Level.INFO).log("Rebuilding spawn suppression map for world %s", world.getName());
/*     */     
/* 143 */     ChunkStore chunkComponentStore = world.getChunkStore();
/* 144 */     Store<ChunkStore> chunkStore = chunkComponentStore.getStore();
/* 145 */     Long2ObjectConcurrentHashMap<ChunkSuppressionEntry> chunkSuppressionMap = suppressionController.getChunkSuppressionMap();
/* 146 */     for (LongIterator<Long> longIterator = chunkSuppressionMap.keySet().iterator(); longIterator.hasNext(); ) { long key = ((Long)longIterator.next()).longValue();
/* 147 */       Ref<ChunkStore> chunkReference = chunkComponentStore.getChunkReference(key);
/* 148 */       if (chunkReference == null)
/* 149 */         continue;  chunkStore.tryRemoveComponent(chunkReference, this.chunkSuppressionEntryComponentType); }
/*     */     
/* 151 */     chunkSuppressionMap.clear();
/*     */ 
/*     */     
/* 154 */     store.forEachEntityParallel((Query)SpawnMarkerEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> ((SpawnMarkerEntity)archetypeChunk.getComponent(index, SpawnMarkerEntity.getComponentType())).clearAllSuppressions());
/*     */ 
/*     */     
/* 157 */     Map<UUID, SpawnSuppressorEntry> spawnSuppressorMap = suppressionController.getSpawnSuppressorMap();
/* 158 */     spawnSuppressorMap.forEach((id, entry) -> SpawnSuppressionSystems.suppressSpawns(this.chunkSuppressionQueueResourceType, this.spawnMarkerEntityComponentType, id, entry, suppressionController, store, ((EntityStore)store.getExternalData()).getWorld().getChunkStore()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\system\SpawnSuppressionSystems$Load.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */