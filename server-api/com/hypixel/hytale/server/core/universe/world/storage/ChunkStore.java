/*      */ package com.hypixel.hytale.server.core.universe.world.storage;
/*      */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.store.CodecKey;
/*      */ import com.hypixel.hytale.common.util.FormatUtil;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.ComponentRegistry;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.component.IResourceStorage;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.RemoveReason;
/*      */ import com.hypixel.hytale.component.ResourceType;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.component.SystemGroup;
/*      */ import com.hypixel.hytale.component.SystemType;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import com.hypixel.hytale.component.system.data.EntityDataSystem;
/*      */ import com.hypixel.hytale.event.IEventDispatcher;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.util.ChunkUtil;
/*      */ import com.hypixel.hytale.metrics.MetricProvider;
/*      */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkUnloadingSystem;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.IChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*      */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.longs.LongSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import java.io.IOException;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.StampedLock;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class ChunkStore implements WorldProvider {
/*      */   @Nonnull
/*   53 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final MetricsRegistry<ChunkStore> METRICS_REGISTRY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   64 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Store", ChunkStore::getStore, (Codec)Store.METRICS_REGISTRY).register("ChunkLoader", MetricProvider.maybe(ChunkStore::getLoader)).register("ChunkSaver", MetricProvider.maybe(ChunkStore::getSaver)).register("WorldGen", MetricProvider.maybe(ChunkStore::getGenerator)).register("TotalGeneratedChunkCount", chunkComponentStore -> Long.valueOf(chunkComponentStore.totalGeneratedChunksCount.get()), (Codec)Codec.LONG).register("TotalLoadedChunkCount", chunkComponentStore -> Long.valueOf(chunkComponentStore.totalLoadedChunksCount.get()), (Codec)Codec.LONG);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final long MAX_FAILURE_BACKOFF_NANOS = TimeUnit.SECONDS.toNanos(10L);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static final long FAILURE_BACKOFF_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public static final ComponentRegistry<ChunkStore> REGISTRY = new ComponentRegistry();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   public static final CodecKey<Holder<ChunkStore>> HOLDER_CODEC_KEY = new CodecKey("ChunkHolder");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   90 */     Objects.requireNonNull(REGISTRY); CodecStore.STATIC.putCodecSupplier(HOLDER_CODEC_KEY, REGISTRY::getEntityCodec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*   97 */   public static final SystemType<ChunkStore, LoadPacketDataQuerySystem> LOAD_PACKETS_DATA_QUERY_SYSTEM_TYPE = REGISTRY.registerSystemType(LoadPacketDataQuerySystem.class);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  103 */   public static final SystemType<ChunkStore, LoadFuturePacketDataQuerySystem> LOAD_FUTURE_PACKETS_DATA_QUERY_SYSTEM_TYPE = REGISTRY.registerSystemType(LoadFuturePacketDataQuerySystem.class);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  109 */   public static final SystemType<ChunkStore, UnloadPacketDataQuerySystem> UNLOAD_PACKETS_DATA_QUERY_SYSTEM_TYPE = REGISTRY.registerSystemType(UnloadPacketDataQuerySystem.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  116 */   public static final ResourceType<ChunkStore, ChunkUnloadingSystem.Data> UNLOAD_RESOURCE = REGISTRY.registerResource(ChunkUnloadingSystem.Data.class, com.hypixel.hytale.server.core.universe.world.storage.component.ChunkUnloadingSystem.Data::new);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  122 */   public static final ResourceType<ChunkStore, ChunkSavingSystems.Data> SAVE_RESOURCE = REGISTRY.registerResource(ChunkSavingSystems.Data.class, com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems.Data::new);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   public static final SystemGroup<ChunkStore> INIT_GROUP = REGISTRY.registerSystemGroup();
/*      */   
/*      */   @Nonnull
/*      */   private final World world;
/*      */   
/*      */   static {
/*  133 */     REGISTRY.registerSystem((ISystem)new ChunkLoaderSaverSetupSystem());
/*  134 */     REGISTRY.registerSystem((ISystem)new ChunkUnloadingSystem());
/*      */     
/*  136 */     REGISTRY.registerSystem((ISystem)new ChunkSavingSystems.WorldRemoved());
/*  137 */     REGISTRY.registerSystem((ISystem)new ChunkSavingSystems.Ticking());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  149 */   private final Long2ObjectConcurrentHashMap<ChunkLoadState> chunks = new Long2ObjectConcurrentHashMap(true, ChunkUtil.NOT_FOUND);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Store<ChunkStore> store;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private IChunkLoader loader;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private IChunkSaver saver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private IWorldGen generator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  180 */   private CompletableFuture<Void> generatorLoaded = new CompletableFuture<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   private final AtomicInteger totalGeneratedChunksCount = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  195 */   private final AtomicInteger totalLoadedChunksCount = new AtomicInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkStore(@Nonnull World world) {
/*  203 */     this.world = world;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public World getWorld() {
/*  209 */     return this.world;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Store<ChunkStore> getStore() {
/*  217 */     return this.store;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IChunkLoader getLoader() {
/*  225 */     return this.loader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IChunkSaver getSaver() {
/*  233 */     return this.saver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IWorldGen getGenerator() {
/*  241 */     return this.generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGenerator(@Nullable IWorldGen generator) {
/*  250 */     if (this.generator != null) {
/*  251 */       this.generator.shutdown();
/*      */     }
/*      */     
/*  254 */     this.totalGeneratedChunksCount.set(0);
/*  255 */     this.generator = generator;
/*      */     
/*  257 */     if (generator != null) {
/*      */       
/*  259 */       this.generatorLoaded.complete(null);
/*  260 */       this.generatorLoaded = new CompletableFuture<>();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public LongSet getChunkIndexes() {
/*  269 */     return LongSets.unmodifiable((LongSet)this.chunks.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLoadedChunksCount() {
/*  276 */     return this.chunks.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalGeneratedChunksCount() {
/*  283 */     return this.totalGeneratedChunksCount.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalLoadedChunksCount() {
/*  290 */     return this.totalLoadedChunksCount.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(@Nonnull IResourceStorage resourceStorage) {
/*  299 */     this.store = REGISTRY.addStore(this, resourceStorage, store -> this.store = store);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void waitForLoadingChunks() {
/*  307 */     long start = System.nanoTime();
/*      */     do {
/*  309 */       this.world.consumeTaskQueue();
/*      */       
/*  311 */       Thread.yield();
/*      */       
/*  313 */       boolean hasLoadingChunks = false;
/*  314 */       for (ObjectIterator<Long2ObjectMap.Entry<ChunkLoadState>> objectIterator = this.chunks.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<ChunkLoadState> entry = objectIterator.next();
/*  315 */         ChunkLoadState chunkState = (ChunkLoadState)entry.getValue();
/*  316 */         long stamp = chunkState.lock.readLock();
/*      */         
/*  318 */         try { CompletableFuture<Ref<ChunkStore>> future = chunkState.future;
/*  319 */           if (future != null && !future.isDone())
/*  320 */           { hasLoadingChunks = true;
/*      */ 
/*      */ 
/*      */             
/*  324 */             chunkState.lock.unlockRead(stamp); break; }  } finally { chunkState.lock.unlockRead(stamp); }
/*      */          }
/*      */ 
/*      */       
/*  328 */       if (!hasLoadingChunks) {
/*      */         break;
/*      */       }
/*  331 */     } while (System.nanoTime() - start <= 5000000000L);
/*      */     
/*  333 */     this.world.consumeTaskQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/*  340 */     this.store.shutdown();
/*  341 */     this.chunks.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private Ref<ChunkStore> add(@Nonnull Holder<ChunkStore> holder) {
/*  355 */     this.world.debugAssertInTickingThread();
/*      */     
/*  357 */     WorldChunk worldChunkComponent = (WorldChunk)holder.getComponent(WorldChunk.getComponentType());
/*  358 */     assert worldChunkComponent != null;
/*      */     
/*  360 */     ChunkLoadState chunkState = (ChunkLoadState)this.chunks.get(worldChunkComponent.getIndex());
/*  361 */     if (chunkState == null) {
/*  362 */       throw new IllegalStateException("Expected the ChunkLoadState to exist!");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  369 */     Ref<ChunkStore> oldReference = null;
/*  370 */     long stamp = chunkState.lock.writeLock();
/*      */     try {
/*  372 */       if (chunkState.future == null) {
/*  373 */         throw new IllegalStateException("Expected the ChunkLoadState to have a future!");
/*      */       }
/*      */       
/*  376 */       if (chunkState.reference != null) {
/*  377 */         oldReference = chunkState.reference;
/*  378 */         chunkState.reference = null;
/*      */       } 
/*      */     } finally {
/*  381 */       chunkState.lock.unlockWrite(stamp);
/*      */     } 
/*      */     
/*  384 */     if (oldReference != null) {
/*  385 */       WorldChunk oldWorldChunkComponent = (WorldChunk)this.store.getComponent(oldReference, WorldChunk.getComponentType());
/*  386 */       assert oldWorldChunkComponent != null;
/*      */       
/*  388 */       oldWorldChunkComponent.setFlag(ChunkFlag.TICKING, false);
/*  389 */       this.store.removeEntity(oldReference, RemoveReason.REMOVE);
/*      */ 
/*      */       
/*  392 */       this.world.getNotificationHandler().updateChunk(worldChunkComponent.getIndex());
/*      */     } 
/*      */ 
/*      */     
/*  396 */     Ref<ChunkStore> ref = this.store.addEntity(holder, AddReason.SPAWN);
/*  397 */     if (ref == null) throw new UnsupportedOperationException("Unable to add the chunk to the world!");
/*      */     
/*  399 */     worldChunkComponent.setReference(ref);
/*      */     
/*  401 */     stamp = chunkState.lock.writeLock();
/*      */     try {
/*  403 */       chunkState.reference = ref;
/*      */ 
/*      */       
/*  406 */       chunkState.flags = 0;
/*  407 */       chunkState.future = null;
/*      */ 
/*      */       
/*  410 */       chunkState.throwable = null;
/*  411 */       chunkState.failedWhen = 0L;
/*  412 */       chunkState.failedCounter = 0;
/*      */       
/*  414 */       return ref;
/*      */     } finally {
/*  416 */       chunkState.lock.unlockWrite(stamp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(@Nonnull Ref<ChunkStore> reference, @Nonnull RemoveReason reason) {
/*  427 */     this.world.debugAssertInTickingThread();
/*      */     
/*  429 */     WorldChunk worldChunkComponent = (WorldChunk)this.store.getComponent(reference, WorldChunk.getComponentType());
/*  430 */     assert worldChunkComponent != null;
/*      */     
/*  432 */     long index = worldChunkComponent.getIndex();
/*  433 */     ChunkLoadState chunkState = (ChunkLoadState)this.chunks.get(index);
/*  434 */     long stamp = chunkState.lock.readLock();
/*      */     try {
/*  436 */       worldChunkComponent.setFlag(ChunkFlag.TICKING, false);
/*  437 */       this.store.removeEntity(reference, reason);
/*      */ 
/*      */       
/*  440 */       if (chunkState.future != null) {
/*  441 */         chunkState.reference = null;
/*      */       } else {
/*  443 */         this.chunks.remove(index, chunkState);
/*      */       } 
/*      */     } finally {
/*  446 */       chunkState.lock.unlockRead(stamp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Ref<ChunkStore> getChunkReference(long index) {
/*  458 */     ChunkLoadState chunkState = (ChunkLoadState)this.chunks.get(index);
/*  459 */     if (chunkState == null) return null;
/*      */     
/*  461 */     long stamp = chunkState.lock.tryOptimisticRead();
/*  462 */     Ref<ChunkStore> reference = chunkState.reference;
/*  463 */     if (chunkState.lock.validate(stamp)) {
/*  464 */       return reference;
/*      */     }
/*      */     
/*  467 */     stamp = chunkState.lock.readLock();
/*      */     try {
/*  469 */       return chunkState.reference;
/*      */     } finally {
/*  471 */       chunkState.lock.unlockRead(stamp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Ref<ChunkStore> getChunkSectionReference(int x, int y, int z) {
/*  486 */     Ref<ChunkStore> ref = getChunkReference(ChunkUtil.indexChunk(x, z));
/*  487 */     if (ref == null) return null;
/*      */     
/*  489 */     ChunkColumn chunkColumnComponent = (ChunkColumn)this.store.getComponent(ref, ChunkColumn.getComponentType());
/*  490 */     if (chunkColumnComponent == null) return null;
/*      */     
/*  492 */     return chunkColumnComponent.getSection(y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Ref<ChunkStore> getChunkSectionReference(@Nonnull ComponentAccessor<ChunkStore> commandBuffer, int x, int y, int z) {
/*  507 */     Ref<ChunkStore> ref = getChunkReference(ChunkUtil.indexChunk(x, z));
/*  508 */     if (ref == null) return null;
/*      */     
/*  510 */     ChunkColumn chunkColumnComponent = (ChunkColumn)commandBuffer.getComponent(ref, ChunkColumn.getComponentType());
/*  511 */     if (chunkColumnComponent == null) return null;
/*      */     
/*  513 */     return chunkColumnComponent.getSection(y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Ref<ChunkStore>> getChunkSectionReferenceAsync(int x, int y, int z) {
/*  528 */     if (y < 0 || y >= 10) {
/*  529 */       return CompletableFuture.failedFuture(new IndexOutOfBoundsException("Invalid y: " + y));
/*      */     }
/*      */     
/*  532 */     return getChunkReferenceAsync(ChunkUtil.indexChunk(x, z))
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  537 */       .thenApplyAsync(ref -> {
/*      */           if (ref == null || !ref.isValid()) {
/*      */             return null;
/*      */           }
/*      */           
/*      */           Store<ChunkStore> store = ref.getStore();
/*      */           ChunkColumn chunkColumnComponent = (ChunkColumn)store.getComponent(ref, ChunkColumn.getComponentType());
/*      */           return (chunkColumnComponent == null) ? null : chunkColumnComponent.getSection(y);
/*  545 */         }(Executor)((ChunkStore)this.store.getExternalData()).getWorld());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public <T extends com.hypixel.hytale.component.Component<ChunkStore>> T getChunkComponent(long index, @Nonnull ComponentType<ChunkStore, T> componentType) {
/*  558 */     Ref<ChunkStore> reference = getChunkReference(index);
/*  559 */     return (reference == null || !reference.isValid()) ? null : (T)this.store.getComponent(reference, componentType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Ref<ChunkStore>> getChunkReferenceAsync(long index) {
/*  573 */     return getChunkReferenceAsync(index, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Ref<ChunkStore>> getChunkReferenceAsync(long index, int flags) {
/*      */     ChunkLoadState chunkState;
/*  586 */     if (this.store.isShutdown()) return CompletableFuture.completedFuture(null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  593 */     if ((flags & 0x3) == 3) {
/*  594 */       chunkState = (ChunkLoadState)this.chunks.get(index);
/*      */ 
/*      */       
/*  597 */       if (chunkState == null) return CompletableFuture.completedFuture(null);
/*      */ 
/*      */       
/*  600 */       long l = chunkState.lock.readLock();
/*      */ 
/*      */       
/*      */       try {
/*  604 */         if ((flags & 0x4) == 0 || (chunkState.flags & 0x4) != 0) {
/*  605 */           if (chunkState.reference != null) return CompletableFuture.completedFuture(chunkState.reference); 
/*  606 */           if (chunkState.future == null) return (CompletableFuture)CompletableFuture.completedFuture(null); 
/*  607 */           return chunkState.future;
/*      */         }
/*      */       
/*      */       } finally {
/*      */         
/*  612 */         chunkState.lock.unlockRead(l);
/*      */       } 
/*      */     } else {
/*      */       
/*  616 */       chunkState = (ChunkLoadState)this.chunks.computeIfAbsent(index, l -> new ChunkLoadState());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  621 */     long stamp = chunkState.lock.writeLock();
/*      */ 
/*      */     
/*  624 */     if (chunkState.future == null && chunkState.reference != null && (flags & 0x8) == 0) {
/*  625 */       Ref<ChunkStore> reference = chunkState.reference;
/*      */       
/*  627 */       if ((flags & 0x4) == 0) {
/*  628 */         chunkState.lock.unlockWrite(stamp);
/*  629 */         return CompletableFuture.completedFuture(reference);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  634 */       if (this.world.isInThread() && (flags & Integer.MIN_VALUE) == 0) {
/*  635 */         chunkState.lock.unlockWrite(stamp);
/*      */ 
/*      */ 
/*      */         
/*  639 */         WorldChunk worldChunkComponent = (WorldChunk)this.store.getComponent(reference, WorldChunk.getComponentType());
/*  640 */         assert worldChunkComponent != null;
/*      */         
/*  642 */         worldChunkComponent.setFlag(ChunkFlag.TICKING, true);
/*  643 */         return CompletableFuture.completedFuture(reference);
/*      */       } 
/*      */       
/*  646 */       chunkState.lock.unlockWrite(stamp);
/*  647 */       return CompletableFuture.supplyAsync(() -> { WorldChunk worldChunkComponent = (WorldChunk)this.store.getComponent(reference, WorldChunk.getComponentType()); assert worldChunkComponent != null; worldChunkComponent.setFlag(ChunkFlag.TICKING, true); return reference; }(Executor)this.world);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  659 */       if (chunkState.throwable != null) {
/*  660 */         long nanosSince = System.nanoTime() - chunkState.failedWhen;
/*  661 */         int count = chunkState.failedCounter;
/*  662 */         if (nanosSince < Math.min(MAX_FAILURE_BACKOFF_NANOS, (count * count) * FAILURE_BACKOFF_NANOS)) {
/*  663 */           return (CompletableFuture)CompletableFuture.failedFuture(new RuntimeException("Chunk failure backoff", chunkState.throwable));
/*      */         }
/*      */ 
/*      */         
/*  667 */         chunkState.throwable = null;
/*  668 */         chunkState.failedWhen = 0L;
/*      */       } 
/*      */       
/*  671 */       boolean isNew = (chunkState.future == null);
/*  672 */       if (isNew) chunkState.flags = flags;
/*      */       
/*  674 */       int x = ChunkUtil.xOfChunkIndex(index);
/*  675 */       int z = ChunkUtil.zOfChunkIndex(index);
/*      */ 
/*      */ 
/*      */       
/*  679 */       if ((isNew || (chunkState.flags & 0x1) != 0) && (flags & 0x1) == 0)
/*      */       {
/*      */         
/*  682 */         if (chunkState.future == null) {
/*  683 */           chunkState
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  690 */             .future = this.loader.loadHolder(x, z).thenApplyAsync(holder -> { if (holder == null || this.store.isShutdown()) return null;  this.totalLoadedChunksCount.getAndIncrement(); return preLoadChunkAsync(index, holder, false); }).thenApplyAsync(this::postLoadChunk, (Executor)this.world).exceptionally(throwable -> {
/*      */                 ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to load chunk! %s, %s", x, z);
/*      */                 chunkState.fail(throwable);
/*      */                 throw SneakyThrow.sneakyThrow(throwable);
/*      */               });
/*      */         } else {
/*  696 */           chunkState.flags &= 0xFFFFFFFE;
/*  697 */           chunkState.future = chunkState.future.thenCompose(reference -> (reference != null) ? CompletableFuture.completedFuture(reference) : this.loader.loadHolder(x, z).thenApplyAsync(()).thenApplyAsync(this::postLoadChunk, (Executor)this.world).exceptionally(()));
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  718 */       if ((isNew || (chunkState.flags & 0x2) != 0) && (flags & 0x2) == 0) {
/*      */         
/*  720 */         int seed = (int)this.world.getWorldConfig().getSeed();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  725 */         if (chunkState.future == null) {
/*      */           CompletableFuture<GeneratedChunk> future;
/*  727 */           if (this.generator == null) {
/*  728 */             future = this.generatorLoaded.thenCompose(aVoid -> this.generator.generate(seed, index, x, z, ((flags & 0x10) != 0) ? this::isChunkStillNeeded : null));
/*      */           } else {
/*  730 */             future = this.generator.generate(seed, index, x, z, ((flags & 0x10) != 0) ? this::isChunkStillNeeded : null);
/*      */           } 
/*  732 */           chunkState
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  739 */             .future = future.thenApplyAsync(generatedChunk -> { if (generatedChunk == null || this.store.isShutdown()) return null;  this.totalGeneratedChunksCount.getAndIncrement(); return preLoadChunkAsync(index, generatedChunk.toHolder(this.world), true); }).thenApplyAsync(this::postLoadChunk, (Executor)this.world).exceptionally(throwable -> {
/*      */                 ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to generate chunk! %s, %s", x, z);
/*      */                 chunkState.fail(throwable);
/*      */                 throw SneakyThrow.sneakyThrow(throwable);
/*      */               });
/*      */         } else {
/*  745 */           chunkState.flags &= 0xFFFFFFFD;
/*  746 */           chunkState.future = chunkState.future.thenCompose(reference -> {
/*      */                 CompletableFuture<GeneratedChunk> future;
/*      */ 
/*      */ 
/*      */                 
/*      */                 if (reference != null) {
/*      */                   return CompletableFuture.completedFuture(reference);
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/*      */                 if (this.generator == null) {
/*      */                   future = this.generatorLoaded.thenCompose(());
/*      */                 } else {
/*      */                   future = this.generator.generate(seed, index, x, z, null);
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/*      */                 return future.thenApplyAsync(()).thenApplyAsync(this::postLoadChunk, (Executor)this.world).exceptionally(());
/*      */               });
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  772 */       if ((isNew || (chunkState.flags & 0x4) == 0) && (flags & 0x4) != 0) {
/*  773 */         chunkState.flags |= 0x4;
/*      */         
/*  775 */         if (chunkState.future != null) {
/*  776 */           chunkState
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  786 */             .future = chunkState.future.<Ref<ChunkStore>>thenApplyAsync(reference -> { if (reference != null) { WorldChunk worldChunkComponent = (WorldChunk)this.store.getComponent(reference, WorldChunk.getComponentType()); assert worldChunkComponent != null; worldChunkComponent.setFlag(ChunkFlag.TICKING, true); }  return reference; }(Executor)this.world).exceptionally(throwable -> {
/*      */                 ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to set chunk ticking! %s, %s", x, z);
/*      */                 
/*      */                 chunkState.fail(throwable);
/*      */                 throw SneakyThrow.sneakyThrow(throwable);
/*      */               });
/*      */         }
/*      */       } 
/*  794 */       if (chunkState.future == null) {
/*  795 */         return (CompletableFuture)CompletableFuture.completedFuture(null);
/*      */       }
/*  797 */       return chunkState.future;
/*      */     } finally {
/*  799 */       chunkState.lock.unlockWrite(stamp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isChunkStillNeeded(long index) {
/*  810 */     for (PlayerRef playerRef : this.world.getPlayerRefs()) {
/*  811 */       if (playerRef.getChunkTracker().shouldBeVisible(index)) {
/*  812 */         return true;
/*      */       }
/*      */     } 
/*  815 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChunkOnBackoff(long index, long maxFailureBackoffNanos) {
/*  826 */     ChunkLoadState chunkState = (ChunkLoadState)this.chunks.get(index);
/*  827 */     if (chunkState == null) return false;
/*      */     
/*  829 */     long stamp = chunkState.lock.readLock();
/*      */     try {
/*  831 */       if (chunkState.throwable == null) return false;
/*      */       
/*  833 */       long nanosSince = System.nanoTime() - chunkState.failedWhen;
/*  834 */       int count = chunkState.failedCounter;
/*  835 */       return (nanosSince < Math.min(maxFailureBackoffNanos, (count * count) * FAILURE_BACKOFF_NANOS));
/*      */     } finally {
/*  837 */       chunkState.lock.unlockRead(stamp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private Holder<ChunkStore> preLoadChunkAsync(long index, @Nonnull Holder<ChunkStore> holder, boolean newlyGenerated) {
/*  851 */     WorldChunk worldChunkComponent = (WorldChunk)holder.getComponent(WorldChunk.getComponentType());
/*      */     
/*  853 */     if (worldChunkComponent == null) {
/*  854 */       throw new IllegalStateException(String.format("Holder missing WorldChunk component! (%d, %d)", new Object[] {
/*  855 */               Integer.valueOf(ChunkUtil.xOfChunkIndex(index)), Integer.valueOf(ChunkUtil.zOfChunkIndex(index))
/*      */             }));
/*      */     }
/*  858 */     if (worldChunkComponent.getIndex() != index) {
/*  859 */       throw new IllegalStateException(String.format("Incorrect chunk index! Got (%d, %d) expected (%d, %d)", new Object[] {
/*  860 */               Integer.valueOf(worldChunkComponent.getX()), Integer.valueOf(worldChunkComponent.getZ()), 
/*  861 */               Integer.valueOf(ChunkUtil.xOfChunkIndex(index)), Integer.valueOf(ChunkUtil.zOfChunkIndex(index))
/*      */             }));
/*      */     }
/*  864 */     BlockChunk blockChunk = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/*  865 */     if (blockChunk == null) {
/*  866 */       throw new IllegalStateException(String.format("Holder missing BlockChunk component! (%d, %d)", new Object[] {
/*  867 */               Integer.valueOf(ChunkUtil.xOfChunkIndex(index)), Integer.valueOf(ChunkUtil.zOfChunkIndex(index))
/*      */             }));
/*      */     }
/*  870 */     blockChunk.loadFromHolder(holder);
/*      */     
/*  872 */     worldChunkComponent.setFlag(ChunkFlag.NEWLY_GENERATED, newlyGenerated);
/*  873 */     worldChunkComponent.setLightingUpdatesEnabled(false);
/*      */     
/*  875 */     if (newlyGenerated && this.world.getWorldConfig().shouldSaveNewChunks()) {
/*  876 */       worldChunkComponent.markNeedsSaving();
/*      */     }
/*      */     
/*      */     try {
/*  880 */       long start = System.nanoTime();
/*      */       
/*  882 */       IEventDispatcher<ChunkPreLoadProcessEvent, ChunkPreLoadProcessEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(ChunkPreLoadProcessEvent.class, this.world.getName());
/*  883 */       if (dispatcher.hasListener()) {
/*  884 */         ChunkPreLoadProcessEvent event = (ChunkPreLoadProcessEvent)dispatcher.dispatch((IBaseEvent)new ChunkPreLoadProcessEvent(holder, worldChunkComponent, newlyGenerated, start));
/*  885 */         if (!event.didLog()) {
/*  886 */           long end = System.nanoTime();
/*  887 */           long diff = end - start;
/*  888 */           if (diff > this.world.getTickStepNanos()) {
/*  889 */             LOGGER.at(Level.SEVERE).log("Took too long to pre-load process chunk: %s > TICK_STEP, Has GC Run: %s, %s", FormatUtil.nanosToString(diff), Boolean.valueOf(this.world.consumeGCHasRun()), worldChunkComponent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  894 */       worldChunkComponent.setLightingUpdatesEnabled(true);
/*      */     } 
/*      */     
/*  897 */     return holder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Ref<ChunkStore> postLoadChunk(@Nullable Holder<ChunkStore> holder) {
/*  908 */     this.world.debugAssertInTickingThread();
/*  909 */     if (holder == null || this.store.isShutdown()) return null;
/*      */     
/*  911 */     long start = System.nanoTime();
/*      */     
/*  913 */     WorldChunk worldChunkComponent = (WorldChunk)holder.getComponent(WorldChunk.getComponentType());
/*  914 */     assert worldChunkComponent != null;
/*      */     
/*  916 */     worldChunkComponent.setFlag(ChunkFlag.START_INIT, true);
/*      */ 
/*      */     
/*  919 */     if (worldChunkComponent.is(ChunkFlag.TICKING)) {
/*  920 */       holder.tryRemoveComponent(REGISTRY.getNonTickingComponentType());
/*      */     } else {
/*  922 */       holder.ensureComponent(REGISTRY.getNonTickingComponentType());
/*      */     } 
/*      */     
/*  925 */     Ref<ChunkStore> reference = add(holder);
/*      */     
/*  927 */     worldChunkComponent.initFlags();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  932 */     this.world.getChunkLighting().init(worldChunkComponent);
/*      */     
/*  934 */     long end = System.nanoTime();
/*  935 */     long diff = end - start;
/*  936 */     if (diff > this.world.getTickStepNanos()) {
/*  937 */       LOGGER.at(Level.SEVERE).log("Took too long to post-load process chunk: %s > TICK_STEP, Has GC Run: %s, %s", FormatUtil.nanosToString(diff), Boolean.valueOf(this.world.consumeGCHasRun()), worldChunkComponent);
/*      */     }
/*      */     
/*  940 */     return reference;
/*      */   }
/*      */   
/*      */   private static class ChunkLoadState {
/*  944 */     private final StampedLock lock = new StampedLock();
/*      */     
/*  946 */     private int flags = 0;
/*      */     
/*      */     @Nullable
/*      */     private CompletableFuture<Ref<ChunkStore>> future;
/*      */     @Nullable
/*      */     private Ref<ChunkStore> reference;
/*      */     @Nullable
/*      */     private Throwable throwable;
/*      */     private long failedWhen;
/*      */     private int failedCounter;
/*      */     
/*      */     private void fail(Throwable throwable) {
/*  958 */       long stamp = this.lock.writeLock();
/*      */       try {
/*  960 */         this.flags = 0;
/*  961 */         this.future = null;
/*      */ 
/*      */         
/*  964 */         this.throwable = throwable;
/*  965 */         this.failedWhen = System.nanoTime();
/*  966 */         this.failedCounter++;
/*      */       } finally {
/*  968 */         this.lock.unlockWrite(stamp);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class LoadPacketDataQuerySystem
/*      */     extends EntityDataSystem<ChunkStore, PlayerRef, Packet> {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class LoadFuturePacketDataQuerySystem
/*      */     extends EntityDataSystem<ChunkStore, PlayerRef, CompletableFuture<Packet>> {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class UnloadPacketDataQuerySystem
/*      */     extends EntityDataSystem<ChunkStore, PlayerRef, Packet> {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ChunkLoaderSaverSetupSystem
/*      */     extends StoreSystem<ChunkStore>
/*      */   {
/*      */     @Nullable
/*      */     public SystemGroup<ChunkStore> getGroup() {
/*  999 */       return ChunkStore.INIT_GROUP;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void onSystemAddedToStore(@Nonnull Store<ChunkStore> store) {
/* 1009 */       ChunkStore data = (ChunkStore)store.getExternalData();
/* 1010 */       World world = data.getWorld();
/* 1011 */       IChunkStorageProvider chunkStorageProvider = world.getWorldConfig().getChunkStorageProvider();
/*      */       try {
/* 1013 */         data.loader = chunkStorageProvider.getLoader(store);
/* 1014 */         data.saver = chunkStorageProvider.getSaver(store);
/* 1015 */       } catch (IOException e) {
/* 1016 */         throw SneakyThrow.sneakyThrow(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSystemRemovedFromStore(@Nonnull Store<ChunkStore> store) {
/* 1022 */       ChunkStore data = (ChunkStore)store.getExternalData();
/*      */       try {
/* 1024 */         if (data.loader != null) {
/* 1025 */           IChunkLoader oldLoader = data.loader;
/* 1026 */           data.loader = null;
/* 1027 */           oldLoader.close();
/*      */         } 
/* 1029 */         if (data.saver != null) {
/* 1030 */           IChunkSaver oldSaver = data.saver;
/* 1031 */           data.saver = null;
/* 1032 */           oldSaver.close();
/*      */         } 
/* 1034 */       } catch (IOException e) {
/* 1035 */         ((HytaleLogger.Api)ChunkStore.LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to close storage!");
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\ChunkStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */