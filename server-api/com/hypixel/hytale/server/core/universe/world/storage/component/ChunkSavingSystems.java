/*     */ package com.hypixel.hytale.server.core.universe.world.storage.component;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.component.system.StoreSystem;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ecs.ChunkSaveEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ChunkSavingSystems {
/*     */   @Nonnull
/*  42 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private static final ComponentType<ChunkStore, WorldChunk> WORLD_CHUNK_COMPONENT_TYPE = WorldChunk.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   public static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)WORLD_CHUNK_COMPONENT_TYPE, (Query)Query.not((Query)ChunkStore.REGISTRY.getNonSerializedComponentType()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WorldRemoved
/*     */     extends StoreSystem<ChunkStore>
/*     */   {
/*     */     @Nonnull
/*  65 */     private final Set<Dependency<ChunkStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkStore.ChunkLoaderSaverSetupSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/*  74 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSystemAddedToStore(@Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */     
/*     */     public void onSystemRemovedFromStore(@Nonnull Store<ChunkStore> store) {
/*  83 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */       
/*  85 */       IWorldGen generator = world.getChunkStore().getGenerator();
/*  86 */       if (generator != null) {
/*  87 */         world.getLogger().at(Level.INFO).log("Shutting down chunk generator...");
/*  88 */         generator.shutdown();
/*     */       } 
/*     */       
/*  91 */       if (!world.getWorldConfig().canSaveChunks()) {
/*  92 */         world.getLogger().at(Level.INFO).log("This world has opted to disable chunk saving so it will not be saved on shutdown");
/*     */         
/*     */         return;
/*     */       } 
/*  96 */       world.getLogger().at(Level.INFO).log("Saving Chunks...");
/*  97 */       ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/*     */       
/*  99 */       data.savedCount.set(0);
/* 100 */       data.toSaveTotal.set(0);
/*     */       
/* 102 */       ChunkSavingSystems.saveChunksInWorld(store).join();
/* 103 */       world.getLogger().at(Level.INFO).log("Done Saving Chunks!");
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> saveChunksInWorld(@Nonnull Store<ChunkStore> store) {
/* 109 */     HytaleLogger logger = ((ChunkStore)store.getExternalData()).getWorld().getLogger();
/* 110 */     Data data = (Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/*     */ 
/*     */     
/* 113 */     logger.at(Level.INFO).log("Queuing Chunks...");
/* 114 */     store.forEachChunk(QUERY, (archetypeChunk, b) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             tryQueue(index, archetypeChunk, b.getStore());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 121 */     logger.at(Level.INFO).log("Saving All Chunks...");
/*     */     Ref<ChunkStore> reference;
/* 123 */     while ((reference = data.poll()) != null) {
/* 124 */       saveChunk(reference, data, true, store);
/*     */     }
/*     */ 
/*     */     
/* 128 */     logger.at(Level.INFO).log("Waiting for Saving Chunks...");
/* 129 */     return data.waitForSavingChunks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Ticking
/*     */     extends TickingSystem<ChunkStore>
/*     */     implements RunWhenPausedSystem<ChunkStore>
/*     */   {
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/* 139 */       return RootDependency.lastSet();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/* 144 */       ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/* 145 */       if (!data.isSaving || !((ChunkStore)store.getExternalData()).getWorld().getWorldConfig().canSaveChunks()) {
/*     */         return;
/*     */       }
/* 148 */       data.chunkSavingFutures.removeIf(CompletableFuture::isDone);
/*     */ 
/*     */       
/* 151 */       if (data.checkTimer(dt))
/*     */       {
/* 153 */         store.forEachChunk(ChunkSavingSystems.QUERY, ChunkSavingSystems::tryQueueSync);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 160 */       IChunkSaver saver = ((ChunkStore)store.getExternalData()).getSaver();
/*     */       
/* 162 */       int parallelSaves = ForkJoinPool.commonPool().getParallelism();
/* 163 */       for (int i = 0; i < parallelSaves; i++) {
/* 164 */         Ref<ChunkStore> reference = data.poll();
/* 165 */         if (reference == null)
/*     */           break; 
/* 167 */         if (!reference.isValid()) {
/* 168 */           ChunkSavingSystems.LOGGER.at(Level.FINEST).log("Chunk reference in queue is for a chunk that has been removed!");
/*     */           
/*     */           return;
/*     */         } 
/* 172 */         WorldChunk chunk = (WorldChunk)store.getComponent(reference, ChunkSavingSystems.WORLD_CHUNK_COMPONENT_TYPE);
/* 173 */         chunk.setSaving(true);
/*     */         
/* 175 */         Holder<ChunkStore> holder = store.copySerializableEntity(reference);
/*     */         
/* 177 */         data.toSaveTotal.getAndIncrement();
/* 178 */         data.chunkSavingFutures.add(CompletableFuture.supplyAsync(() -> saver.saveHolder(chunk.getX(), chunk.getZ(), holder))
/* 179 */             .thenCompose(Function.identity())
/* 180 */             .whenCompleteAsync((aVoid, throwable) -> {
/*     */                 if (throwable != null) {
/*     */                   ((HytaleLogger.Api)ChunkSavingSystems.LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to save chunk (%d, %d):", chunk.getX(), chunk.getZ());
/*     */                 } else {
/*     */                   chunk.setFlag(ChunkFlag.ON_DISK, true);
/*     */                   ChunkSavingSystems.LOGGER.at(Level.FINEST).log("Finished saving chunk (%d, %d)", chunk.getX(), chunk.getZ());
/*     */                 } 
/*     */                 chunk.consumeNeedsSaving();
/*     */                 chunk.setSaving(false);
/*     */               }(Executor)world));
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
/*     */ 
/*     */   
/*     */   public static void tryQueue(int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store) {
/* 204 */     WorldChunk worldChunkComponent = (WorldChunk)archetypeChunk.getComponent(index, WORLD_CHUNK_COMPONENT_TYPE);
/* 205 */     assert worldChunkComponent != null;
/*     */     
/* 207 */     if (!worldChunkComponent.getNeedsSaving() || worldChunkComponent.isSaving())
/*     */       return; 
/* 209 */     Ref<ChunkStore> chunkRef = archetypeChunk.getReferenceTo(index);
/* 210 */     ChunkSaveEvent event = new ChunkSaveEvent(worldChunkComponent);
/* 211 */     store.invoke(chunkRef, (EcsEvent)event);
/* 212 */     if (event.isCancelled())
/*     */       return; 
/* 214 */     ((Data)store.getResource(ChunkStore.SAVE_RESOURCE)).push(chunkRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tryQueueSync(@Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 224 */     Store<ChunkStore> store = commandBuffer.getStore();
/* 225 */     Data data = (Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/*     */     
/* 227 */     for (int index = 0; index < archetypeChunk.size(); index++) {
/* 228 */       WorldChunk worldChunkComponent = (WorldChunk)archetypeChunk.getComponent(index, WORLD_CHUNK_COMPONENT_TYPE);
/* 229 */       assert worldChunkComponent != null;
/*     */       
/* 231 */       if (worldChunkComponent.getNeedsSaving() && !worldChunkComponent.isSaving()) {
/*     */ 
/*     */ 
/*     */         
/* 235 */         Ref<ChunkStore> chunkRef = archetypeChunk.getReferenceTo(index);
/* 236 */         ChunkSaveEvent event = new ChunkSaveEvent(worldChunkComponent);
/* 237 */         store.invoke(chunkRef, (EcsEvent)event);
/* 238 */         if (!event.isCancelled())
/*     */         {
/* 240 */           data.push(chunkRef);
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
/*     */   
/*     */   public static void saveChunk(@Nonnull Ref<ChunkStore> reference, @Nonnull Data data, boolean report, @Nonnull Store<ChunkStore> store) {
/* 253 */     if (!reference.isValid()) {
/* 254 */       LOGGER.at(Level.FINEST).log("Chunk reference in queue is for a chunk that has been removed!");
/*     */       
/*     */       return;
/*     */     } 
/* 258 */     data.toSaveTotal.getAndIncrement();
/*     */     
/* 260 */     WorldChunk worldChunkComponent = (WorldChunk)store.getComponent(reference, WORLD_CHUNK_COMPONENT_TYPE);
/* 261 */     assert worldChunkComponent != null;
/*     */     
/* 263 */     Holder<ChunkStore> holder = worldChunkComponent.toHolder();
/*     */     
/* 265 */     ChunkStore chunkStore = (ChunkStore)store.getExternalData();
/* 266 */     World world = chunkStore.getWorld();
/* 267 */     IChunkSaver saver = chunkStore.getSaver();
/*     */ 
/*     */     
/* 270 */     CompletableFuture<Void> future = saver.saveHolder(worldChunkComponent.getX(), worldChunkComponent.getZ(), holder).whenComplete((aVoid, throwable) -> {
/*     */           if (throwable != null) {
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to save chunk (%d, %d):", worldChunkComponent.getX(), worldChunkComponent.getZ());
/*     */           } else {
/*     */             worldChunkComponent.setFlag(ChunkFlag.ON_DISK, true);
/*     */             
/*     */             LOGGER.at(Level.FINEST).log("Finished saving chunk (%d, %d)", worldChunkComponent.getX(), worldChunkComponent.getZ());
/*     */           } 
/*     */         });
/* 279 */     data.chunkSavingFutures.add(future);
/*     */ 
/*     */     
/* 282 */     if (report) {
/* 283 */       future.thenRunAsync(() -> HytaleServer.get().reportSaveProgress(world, data.savedCount.incrementAndGet(), data.toSaveTotal.get() + data.queue.size()));
/*     */     }
/*     */ 
/*     */     
/* 287 */     worldChunkComponent.consumeNeedsSaving();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Data
/*     */     implements Resource<ChunkStore>
/*     */   {
/*     */     public static final float QUEUE_UPDATE_INTERVAL = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 304 */     private final Set<Ref<ChunkStore>> set = ConcurrentHashMap.newKeySet();
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 309 */     private final Deque<Ref<ChunkStore>> queue = new ConcurrentLinkedDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 315 */     private final List<CompletableFuture<Void>> chunkSavingFutures = (List<CompletableFuture<Void>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float time;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSaving = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 334 */     private final AtomicInteger savedCount = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 342 */     private final AtomicInteger toSaveTotal = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Data() {
/* 349 */       this.time = 0.5F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Data(float time) {
/* 358 */       this.time = time;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<ChunkStore> clone() {
/* 364 */       return new Data(this.time);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearSaveQueue() {
/* 371 */       this.queue.clear();
/* 372 */       this.set.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void push(@Nonnull Ref<ChunkStore> reference) {
/* 381 */       if (this.set.add(reference)) {
/* 382 */         this.queue.push(reference);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Ref<ChunkStore> poll() {
/* 393 */       Ref<ChunkStore> reference = this.queue.poll();
/* 394 */       if (reference == null) return null; 
/* 395 */       this.set.remove(reference);
/* 396 */       return reference;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean checkTimer(float dt) {
/* 406 */       this.time -= dt;
/* 407 */       if (this.time <= 0.0F) {
/* 408 */         this.time += 0.5F;
/* 409 */         return true;
/*     */       } 
/* 411 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> waitForSavingChunks() {
/* 419 */       return CompletableFuture.allOf((CompletableFuture<?>[])this.chunkSavingFutures.toArray(x$0 -> new CompletableFuture[x$0]));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\component\ChunkSavingSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */