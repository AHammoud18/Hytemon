/*      */ package com.hypixel.hytale.component;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*      */ import com.hypixel.hytale.common.util.ArrayUtil;
/*      */ import com.hypixel.hytale.component.data.ForEachTaskData;
/*      */ import com.hypixel.hytale.component.data.change.ChangeType;
/*      */ import com.hypixel.hytale.component.data.change.ComponentChange;
/*      */ import com.hypixel.hytale.component.data.change.DataChange;
/*      */ import com.hypixel.hytale.component.data.change.SystemChange;
/*      */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*      */ import com.hypixel.hytale.component.event.EntityEventType;
/*      */ import com.hypixel.hytale.component.event.WorldEventType;
/*      */ import com.hypixel.hytale.component.metric.ArchetypeChunkData;
/*      */ import com.hypixel.hytale.component.metric.SystemMetricData;
/*      */ import com.hypixel.hytale.component.query.ExactArchetypeQuery;
/*      */ import com.hypixel.hytale.component.query.Query;
/*      */ import com.hypixel.hytale.component.system.ArchetypeChunkSystem;
/*      */ import com.hypixel.hytale.component.system.EcsEvent;
/*      */ import com.hypixel.hytale.component.system.EntityEventSystem;
/*      */ import com.hypixel.hytale.component.system.HolderSystem;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import com.hypixel.hytale.component.system.MetricSystem;
/*      */ import com.hypixel.hytale.component.system.QuerySystem;
/*      */ import com.hypixel.hytale.component.system.RefChangeSystem;
/*      */ import com.hypixel.hytale.component.system.RefSystem;
/*      */ import com.hypixel.hytale.component.system.StoreSystem;
/*      */ import com.hypixel.hytale.component.system.WorldEventSystem;
/*      */ import com.hypixel.hytale.component.system.data.ArchetypeDataSystem;
/*      */ import com.hypixel.hytale.component.system.data.EntityDataSystem;
/*      */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*      */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*      */ import com.hypixel.hytale.component.system.tick.TickableSystem;
/*      */ import com.hypixel.hytale.component.task.ParallelRangeTask;
/*      */ import com.hypixel.hytale.component.task.ParallelTask;
/*      */ import com.hypixel.hytale.function.consumer.IntBiObjectConsumer;
/*      */ import com.hypixel.hytale.metrics.MetricResults;
/*      */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*      */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class Store<ECS_TYPE> implements ComponentAccessor<ECS_TYPE> {
/*   56 */   public static final Store[] EMPTY_ARRAY = new Store[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static final MetricsRegistry<Store<?>> METRICS_REGISTRY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private final ComponentRegistry<ECS_TYPE> registry;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private final ECS_TYPE externalData;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private final IResourceStorage resourceStorage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   90 */     METRICS_REGISTRY = (new MetricsRegistry()).register("ArchetypeChunkCount", Store::getArchetypeChunkCount, (Codec)Codec.INTEGER).register("EntityCount", Store::getEntityCount, (Codec)Codec.INTEGER).register("Systems", componentStore -> { ComponentRegistry.Data<?> data = componentStore.getRegistry().getData(); HistoricMetric[] systemMetrics = componentStore.getSystemMetrics(); SystemMetricData[] systemMetricData = new SystemMetricData[data.getSystemSize()]; for (int systemIndex = 0; systemIndex < data.getSystemSize(); systemIndex++) { ISystem<?> system = data.getSystem(systemIndex); MetricResults metrics = null; if (system instanceof MetricSystem) { MetricSystem metricSystem = (MetricSystem)system; metrics = metricSystem.toMetricResults(componentStore); }  systemMetricData[systemIndex] = new SystemMetricData(system.getClass().getName(), componentStore.getArchetypeChunkCountFor(systemIndex), componentStore.getEntityCountFor(systemIndex), (system instanceof com.hypixel.hytale.component.system.tick.TickingSystem) ? systemMetrics[systemIndex] : null, metrics); }  return (Function)systemMetricData; }(Codec)new ArrayCodec(SystemMetricData.CODEC, x$0 -> new SystemMetricData[x$0])).register("ArchetypeChunks", Store::collectArchetypeChunkData, (Codec)new ArrayCodec(ArchetypeChunkData.CODEC, x$0 -> new ArchetypeChunkData[x$0]));
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
/*  101 */   private final Deque<CommandBuffer<ECS_TYPE>> commandBuffers = new ArrayDeque<>();
/*      */   
/*  103 */   private final Thread thread = Thread.currentThread();
/*      */   @Nonnull
/*  105 */   private final ParallelTask<EntityTickingSystem.SystemTaskData<ECS_TYPE>> parallelTask = new ParallelTask(com.hypixel.hytale.component.system.tick.EntityTickingSystem.SystemTaskData::new);
/*      */   
/*      */   @Nonnull
/*  108 */   private final ParallelTask<ForEachTaskData<ECS_TYPE>> forEachTask = new ParallelTask(ForEachTaskData::new);
/*      */   
/*      */   @Nonnull
/*  111 */   private final ParallelTask<EntityDataSystem.SystemTaskData<ECS_TYPE, ?, ?>> fetchTask = new ParallelTask(com.hypixel.hytale.component.system.data.EntityDataSystem.SystemTaskData::new);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  118 */   private final ProcessingCounter processing = new ProcessingCounter();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutdown;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int storeIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int entitiesSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  139 */   private Ref<ECS_TYPE>[] refs = (Ref<ECS_TYPE>[])new Ref[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  146 */   private int[] entityToArchetypeChunk = new int[16];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  152 */   private int[] entityChunkIndex = new int[16];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  158 */   private BitSet[] systemIndexToArchetypeChunkIndexes = ArrayUtil.EMPTY_BITSET_ARRAY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  164 */   private BitSet[] archetypeChunkIndexesToSystemIndex = ArrayUtil.EMPTY_BITSET_ARRAY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  170 */   private final Object2IntMap<Archetype<ECS_TYPE>> archetypeToIndexMap = (Object2IntMap<Archetype<ECS_TYPE>>)new Object2IntOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int archetypeSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  181 */   private final BitSet archetypeChunkReuse = new BitSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  188 */   private ArchetypeChunk<ECS_TYPE>[] archetypeChunks = ArchetypeChunk.emptyArray();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  193 */   private Resource<ECS_TYPE>[] resources = (Resource<ECS_TYPE>[])Resource.EMPTY_ARRAY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  200 */   private HistoricMetric[] systemMetrics = HistoricMetric.EMPTY_ARRAY;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   private boolean disableProcessingAssert = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Store(@Nonnull ComponentRegistry<ECS_TYPE> registry, int storeIndex, @Nonnull ECS_TYPE externalData, @Nonnull IResourceStorage resourceStorage) {
/*  218 */     this.registry = registry;
/*  219 */     this.storeIndex = storeIndex;
/*  220 */     this.externalData = externalData;
/*  221 */     this.resourceStorage = resourceStorage;
/*      */     
/*  223 */     this.archetypeToIndexMap.defaultReturnValue(-2147483648);
/*  224 */     Arrays.fill(this.entityToArchetypeChunk, -2147483648);
/*  225 */     Arrays.fill(this.entityChunkIndex, -2147483648);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   CommandBuffer<ECS_TYPE> takeCommandBuffer() {
/*  235 */     assertThread();
/*      */     
/*  237 */     if (this.commandBuffers.isEmpty()) {
/*  238 */       return new CommandBuffer<>(this);
/*      */     }
/*  240 */     CommandBuffer<ECS_TYPE> buffer = this.commandBuffers.pop();
/*      */     
/*  242 */     assert buffer.setThread();
/*  243 */     return buffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void storeCommandBuffer(@Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/*  253 */     assertThread();
/*      */     
/*  255 */     commandBuffer.validateEmpty();
/*  256 */     this.commandBuffers.add(commandBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStoreIndex() {
/*  264 */     return this.storeIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ComponentRegistry<ECS_TYPE> getRegistry() {
/*  272 */     return this.registry;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ECS_TYPE getExternalData() {
/*  278 */     return this.externalData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public IResourceStorage getResourceStorage() {
/*  286 */     return this.resourceStorage;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ParallelTask<EntityTickingSystem.SystemTaskData<ECS_TYPE>> getParallelTask() {
/*  291 */     return this.parallelTask;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ParallelTask<EntityDataSystem.SystemTaskData<ECS_TYPE, ?, ?>> getFetchTask() {
/*  296 */     return this.fetchTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public HistoricMetric[] getSystemMetrics() {
/*  304 */     assertThread();
/*  305 */     return this.systemMetrics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShutdown() {
/*  312 */     return this.shutdown;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void onAdd(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/*  321 */     updateArchetypeIndexes(data);
/*      */     
/*  323 */     int resourceSize = data.getResourceSize();
/*  324 */     this.resources = Arrays.<Resource<ECS_TYPE>>copyOf(this.resources, resourceSize);
/*  325 */     for (int index = 0; index < resourceSize; index++) {
/*  326 */       ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>> resourceType = (ResourceType)data.getResourceType(index);
/*  327 */       if (resourceType != null) {
/*  328 */         this.resources[index] = this.resourceStorage.<Resource<ECS_TYPE>, ECS_TYPE>load(this, data, (ResourceType)resourceType).join();
/*      */       }
/*      */     } 
/*      */     
/*  332 */     for (int systemIndex = 0; systemIndex < data.getSystemSize(); systemIndex++) {
/*  333 */       updateData(data, data, (DataChange)new SystemChange(ChangeType.REGISTERED, data.getSystem(systemIndex)));
/*      */     }
/*      */     
/*  336 */     this.systemMetrics = Arrays.<HistoricMetric>copyOf(this.systemMetrics, data.getSystemSize());
/*      */     
/*  338 */     SystemType<ECS_TYPE, TickableSystem<ECS_TYPE>> tickingSystemType = this.registry.getTickableSystemType();
/*  339 */     BitSet systemIndexes = data.getSystemIndexesForType(tickingSystemType);
/*      */     
/*  341 */     int i = -1;
/*  342 */     while ((i = systemIndexes.nextSetBit(i + 1)) >= 0)
/*      */     {
/*      */       
/*  345 */       this.systemMetrics[i] = HistoricMetric.builder(33333333L, TimeUnit.NANOSECONDS)
/*  346 */         .addPeriod(1L, TimeUnit.SECONDS)
/*  347 */         .addPeriod(1L, TimeUnit.MINUTES)
/*  348 */         .addPeriod(5L, TimeUnit.MINUTES)
/*  349 */         .build();
/*      */     }
/*      */   }
/*      */   
/*      */   public void shutdown() {
/*  354 */     if (this.shutdown) throw new IllegalStateException("Store is already shutdown!"); 
/*  355 */     this.registry.removeStore(this);
/*      */   }
/*      */   
/*      */   void shutdown0(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/*  359 */     if (this.thread.isAlive() && !this.thread.equals(Thread.currentThread())) {
/*  360 */       throw new IllegalArgumentException("Unable to shutdown store while thread is still running!");
/*      */     }
/*      */     
/*  363 */     for (int systemIndex = data.getSystemSize() - 1; systemIndex >= 0; systemIndex--) {
/*  364 */       updateData(data, data, (DataChange)new SystemChange(ChangeType.UNREGISTERED, data.getSystem(systemIndex)));
/*      */     }
/*      */     
/*  367 */     saveAllResources0(data).join();
/*      */     
/*  369 */     this.processing.lock();
/*      */     try {
/*  371 */       for (int i = 0; i < this.entitiesSize; i++) {
/*  372 */         this.refs[i].invalidate();
/*  373 */         this.refs[i] = null;
/*      */       } 
/*      */     } finally {
/*  376 */       this.processing.unlock();
/*      */     } 
/*      */     
/*  379 */     this.shutdown = true;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> saveAllResources() {
/*  384 */     return saveAllResources0(this.registry.getData());
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private CompletableFuture<Void> saveAllResources0(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/*  390 */     int resourceSize = data.getResourceSize();
/*      */     
/*  392 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[resourceSize];
/*  393 */     int idx = 0;
/*  394 */     for (int index = 0; index < resourceSize; index++) {
/*  395 */       ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>> resourceType = (ResourceType)data.getResourceType(index);
/*  396 */       if (resourceType != null)
/*      */       {
/*  398 */         arrayOfCompletableFuture[idx++] = this.resourceStorage.save(this, data, resourceType, this.resources[index]); } 
/*      */     } 
/*  400 */     return CompletableFuture.allOf((CompletableFuture<?>[])Arrays.<CompletableFuture>copyOf(arrayOfCompletableFuture, idx));
/*      */   }
/*      */   
/*      */   public int getEntityCount() {
/*  404 */     return this.entitiesSize;
/*      */   }
/*      */   
/*      */   public int getEntityCountFor(@Nonnull Query<ECS_TYPE> query) {
/*  408 */     assertThread();
/*  409 */     if (query instanceof ExactArchetypeQuery) { ExactArchetypeQuery<ECS_TYPE> exactQuery = (ExactArchetypeQuery<ECS_TYPE>)query;
/*  410 */       int i = this.archetypeToIndexMap.getInt(exactQuery.getArchetype());
/*  411 */       if (i != Integer.MIN_VALUE) {
/*  412 */         return this.archetypeChunks[i].size();
/*      */       }
/*  414 */       return 0; }
/*      */     
/*  416 */     int count = 0;
/*  417 */     for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/*  418 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*  419 */       if (archetypeChunk != null && 
/*  420 */         query.test(archetypeChunk.getArchetype())) {
/*  421 */         count += archetypeChunk.size();
/*      */       }
/*      */     } 
/*  424 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEntityCountFor(int systemIndex) {
/*  433 */     assertThread();
/*      */     
/*  435 */     int count = 0;
/*  436 */     BitSet indexes = this.systemIndexToArchetypeChunkIndexes[systemIndex];
/*      */ 
/*      */     
/*  439 */     int index = -1;
/*  440 */     while ((index = indexes.nextSetBit(index + 1)) >= 0) {
/*  441 */       count += this.archetypeChunks[index].size();
/*      */     }
/*  443 */     return count;
/*      */   }
/*      */   
/*      */   public int getArchetypeChunkCount() {
/*  447 */     assertThread();
/*  448 */     return this.archetypeSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ArchetypeChunkData[] collectArchetypeChunkData() {
/*  458 */     assertThread();
/*  459 */     ObjectArrayList<ArchetypeChunkData> result = new ObjectArrayList(this.archetypeSize);
/*  460 */     for (int i = 0; i < this.archetypeSize; i++) {
/*  461 */       ArchetypeChunk<ECS_TYPE> chunk = this.archetypeChunks[i];
/*  462 */       if (chunk != null) {
/*      */         
/*  464 */         Archetype<ECS_TYPE> archetype = chunk.getArchetype();
/*  465 */         String[] componentTypeNames = new String[archetype.count()];
/*  466 */         int nameIndex = 0;
/*  467 */         for (int j = archetype.getMinIndex(); j < archetype.length(); j++) {
/*  468 */           ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)archetype.get(j);
/*  469 */           if (componentType != null) {
/*  470 */             componentTypeNames[nameIndex++] = componentType.getTypeClass().getName();
/*      */           }
/*      */         } 
/*  473 */         result.add(new ArchetypeChunkData(componentTypeNames, chunk.size()));
/*      */       } 
/*  475 */     }  return (ArchetypeChunkData[])result.toArray(x$0 -> new ArchetypeChunkData[x$0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArchetypeChunkCountFor(int systemIndex) {
/*  483 */     assertThread();
/*      */     
/*  485 */     return this.systemIndexToArchetypeChunkIndexes[systemIndex].cardinality();
/*      */   }
/*      */   
/*      */   protected void setEntityChunkIndex(@Nonnull Ref<ECS_TYPE> ref, int newEntityChunkIndex) {
/*  489 */     if (ref.isValid())
/*  490 */       this.entityChunkIndex[ref.getIndex()] = newEntityChunkIndex; 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Ref<ECS_TYPE> addEntity(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull AddReason reason) {
/*      */     Component[] arrayOfComponent;
/*  496 */     assertThread();
/*  497 */     assertWriteProcessing();
/*      */ 
/*      */     
/*  500 */     if (archetype.isEmpty()) {
/*      */       
/*  502 */       arrayOfComponent = Component.EMPTY_ARRAY;
/*      */     } else {
/*  504 */       ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*      */       
/*  506 */       arrayOfComponent = new Component[archetype.length()];
/*  507 */       for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/*  508 */         ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)archetype.get(i);
/*  509 */         if (componentType != null)
/*  510 */           arrayOfComponent[componentType.getIndex()] = data.createComponent((ComponentType)componentType); 
/*      */       } 
/*      */     } 
/*  513 */     return addEntity(this.registry.newHolder(archetype, (Component<ECS_TYPE>[])arrayOfComponent), new Ref<>(this), reason);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Ref<ECS_TYPE> addEntity(@Nonnull Holder<ECS_TYPE> holder, @Nonnull AddReason reason) {
/*  519 */     return addEntity(holder, new Ref<>(this), reason);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Ref<ECS_TYPE> addEntity(@Nonnull Holder<ECS_TYPE> holder, @Nonnull Ref<ECS_TYPE> ref, @Nonnull AddReason reason) {
/*  524 */     if (ref.isValid()) throw new IllegalArgumentException("EntityReference is already in use!"); 
/*  525 */     if (ref.getStore() != this) throw new IllegalArgumentException("EntityReference is not for this store!");
/*      */     
/*  527 */     assertThread();
/*  528 */     assertWriteProcessing();
/*      */     
/*  530 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/*  532 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*  533 */     this.processing.lock();
/*      */ 
/*      */     
/*  536 */     try { SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> systemType = this.registry.getHolderSystemType();
/*  537 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/*  539 */       int systemIndex = -1;
/*  540 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*  541 */         HolderSystem<ECS_TYPE> system = data.<HolderSystem<ECS_TYPE>>getSystem(systemIndex, systemType);
/*  542 */         if (system.test(this.registry, holder.getArchetype())) {
/*  543 */           system.onEntityAdd(holder, reason, this);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  548 */       int entityIndex = this.entitiesSize++;
/*      */       
/*  550 */       int oldLength = this.refs.length;
/*  551 */       if (oldLength <= entityIndex) {
/*  552 */         int newLength = ArrayUtil.grow(entityIndex);
/*  553 */         this.refs = Arrays.<Ref<ECS_TYPE>>copyOf(this.refs, newLength);
/*  554 */         this.entityToArchetypeChunk = Arrays.copyOf(this.entityToArchetypeChunk, newLength);
/*  555 */         this.entityChunkIndex = Arrays.copyOf(this.entityChunkIndex, newLength);
/*  556 */         Arrays.fill(this.entityToArchetypeChunk, oldLength, newLength, -2147483648);
/*  557 */         Arrays.fill(this.entityChunkIndex, oldLength, newLength, -2147483648);
/*      */       } 
/*      */       
/*  560 */       this.refs[entityIndex] = ref;
/*  561 */       ref.setIndex(entityIndex);
/*      */       
/*  563 */       int archetypeIndex = findOrCreateArchetypeChunk(holder.getArchetype());
/*  564 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*  565 */       int chunkEntityRef = archetypeChunk.addEntity(ref, holder);
/*      */       
/*  567 */       this.entityToArchetypeChunk[entityIndex] = archetypeIndex;
/*  568 */       this.entityChunkIndex[entityIndex] = chunkEntityRef;
/*      */       
/*  570 */       SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> systemType1 = this.registry.getRefSystemType();
/*  571 */       BitSet bitSet1 = data.getSystemIndexesForType(systemType1);
/*      */       
/*  573 */       BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*      */       
/*  575 */       commandBuffer.track(ref);
/*      */       
/*  577 */       int i = -1;
/*  578 */       while ((i = bitSet1.nextSetBit(i + 1)) >= 0) {
/*  579 */         if (entityProcessedBySystemIndexes.get(i)) {
/*  580 */           RefSystem<ECS_TYPE> system = data.<RefSystem<ECS_TYPE>>getSystem(i, systemType1);
/*      */           
/*  582 */           boolean oldDisableProcessingAssert = this.disableProcessingAssert;
/*  583 */           this.disableProcessingAssert = system instanceof DisableProcessingAssert;
/*      */           
/*  585 */           system.onEntityAdded(ref, reason, this, commandBuffer);
/*      */           
/*  587 */           this.disableProcessingAssert = oldDisableProcessingAssert;
/*      */ 
/*      */           
/*  590 */           if (commandBuffer.consumeWasTrackedRefRemoved())
/*      */             break; 
/*      */         } 
/*      */       }  }
/*  594 */     finally { this.processing.unlock(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  599 */     commandBuffer.consume();
/*      */     
/*  601 */     return ref.isValid() ? ref : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Ref<ECS_TYPE>[] addEntities(@Nonnull Holder<ECS_TYPE>[] holders, @Nonnull AddReason reason) {
/*  610 */     return addEntities(holders, 0, holders.length, reason);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Ref<ECS_TYPE>[] addEntities(@Nonnull Holder<ECS_TYPE>[] holders, int start, int length, @Nonnull AddReason reason) {
/*  620 */     Ref[] arrayOfRef = new Ref[length];
/*  621 */     for (int i = 0; i < length; i++) {
/*  622 */       arrayOfRef[i] = new Ref<>(this);
/*      */     }
/*  624 */     addEntities(holders, start, (Ref<ECS_TYPE>[])arrayOfRef, 0, length, reason);
/*  625 */     return (Ref<ECS_TYPE>[])arrayOfRef;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntities(@Nonnull Holder<ECS_TYPE>[] holders, @Nonnull Ref<ECS_TYPE>[] refs, @Nonnull AddReason reason) {
/*  632 */     if (holders.length != refs.length) throw new IllegalArgumentException("EntityHolder and EntityReference array length doesn't match!"); 
/*  633 */     addEntities(holders, 0, refs, 0, holders.length, reason);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntities(@Nonnull Holder<ECS_TYPE>[] holders, int holderStart, @Nonnull Ref<ECS_TYPE>[] refs, int refStart, int length, @Nonnull AddReason reason) {
/*  642 */     int holderEnd = holderStart + length;
/*  643 */     int refEnd = refStart + length;
/*  644 */     if (holders.length < holderEnd) throw new IllegalArgumentException("EntityHolder start and length exceed array length!"); 
/*  645 */     if (refs.length < refEnd) throw new IllegalArgumentException("EntityReference start and length exceed array length!");
/*      */     
/*  647 */     for (int i = refStart; i < refEnd; i++) {
/*  648 */       Ref<ECS_TYPE> ref = refs[i];
/*  649 */       if (ref.isValid()) throw new IllegalArgumentException("EntityReference is already in use!"); 
/*  650 */       if (ref.getStore() != this) throw new IllegalArgumentException("EntityReference is not for this store!");
/*      */     
/*      */     } 
/*  653 */     assertThread();
/*  654 */     assertWriteProcessing();
/*      */     
/*  656 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/*  658 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*  659 */     this.processing.lock();
/*      */     
/*      */     try {
/*  662 */       SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> systemType = this.registry.getHolderSystemType();
/*  663 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/*  665 */       int systemIndex = -1;
/*  666 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*  667 */         HolderSystem<ECS_TYPE> system = data.<HolderSystem<ECS_TYPE>>getSystem(systemIndex, systemType);
/*  668 */         for (int m = holderStart; m < holderEnd; m++) {
/*  669 */           if (system.test(this.registry, holders[m].getArchetype())) {
/*  670 */             system.onEntityAdd(holders[m], reason, this);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  677 */       int firstIndex = this.entitiesSize;
/*  678 */       this.entitiesSize += length;
/*      */ 
/*      */       
/*  681 */       int oldLength = this.refs.length;
/*  682 */       if (oldLength <= this.entitiesSize) {
/*  683 */         int newLength = ArrayUtil.grow(this.entitiesSize);
/*  684 */         this.refs = Arrays.<Ref<ECS_TYPE>>copyOf(this.refs, newLength);
/*  685 */         this.entityToArchetypeChunk = Arrays.copyOf(this.entityToArchetypeChunk, newLength);
/*  686 */         this.entityChunkIndex = Arrays.copyOf(this.entityChunkIndex, newLength);
/*  687 */         Arrays.fill(this.entityToArchetypeChunk, oldLength, newLength, -2147483648);
/*  688 */         Arrays.fill(this.entityChunkIndex, oldLength, newLength, -2147483648);
/*      */       } 
/*      */       
/*  691 */       System.arraycopy(refs, refStart, this.refs, firstIndex, length);
/*      */       
/*      */       int j, entityIndex;
/*  694 */       for (j = refStart, entityIndex = firstIndex; j < refEnd; j++, entityIndex++) {
/*  695 */         refs[j].setIndex(entityIndex);
/*      */       }
/*      */ 
/*      */       
/*  699 */       for (j = 0, entityIndex = firstIndex; j < length; j++, entityIndex++) {
/*  700 */         Ref<ECS_TYPE> ref = refs[refStart + j];
/*  701 */         Holder<ECS_TYPE> holder = holders[holderStart + j];
/*      */         
/*  703 */         int archetypeIndex = findOrCreateArchetypeChunk(holder.getArchetype());
/*  704 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*      */         
/*  706 */         int chunkEntityRef = archetypeChunk.addEntity(ref, holder);
/*      */         
/*  708 */         this.entityToArchetypeChunk[entityIndex] = archetypeIndex;
/*  709 */         this.entityChunkIndex[entityIndex] = chunkEntityRef;
/*      */       } 
/*      */       
/*  712 */       SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> systemType1 = this.registry.getRefSystemType();
/*  713 */       BitSet bitSet1 = data.getSystemIndexesForType(systemType1);
/*      */       
/*  715 */       int k = -1;
/*  716 */       while ((k = bitSet1.nextSetBit(k + 1)) >= 0) {
/*  717 */         for (int m = refStart; m < refEnd; m++) {
/*  718 */           Ref<ECS_TYPE> ref = refs[m];
/*  719 */           int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*      */           
/*  721 */           BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*  722 */           if (entityProcessedBySystemIndexes.get(k)) {
/*  723 */             RefSystem<ECS_TYPE> system = data.<RefSystem<ECS_TYPE>>getSystem(k, systemType1);
/*      */             
/*  725 */             boolean oldDisableProcessingAssert = this.disableProcessingAssert;
/*  726 */             this.disableProcessingAssert = system instanceof DisableProcessingAssert;
/*      */             
/*  728 */             commandBuffer.track(ref);
/*  729 */             system.onEntityAdded(ref, reason, this, commandBuffer);
/*      */ 
/*      */             
/*  732 */             if (commandBuffer.consumeWasTrackedRefRemoved()) {
/*  733 */               int remaining = refEnd - m;
/*  734 */               if (remaining > 1) {
/*      */ 
/*      */                 
/*  737 */                 System.arraycopy(refs, m + 1, refs, m, remaining - 1);
/*  738 */                 refs[refEnd - 1] = ref;
/*      */ 
/*      */                 
/*  741 */                 m--;
/*      */               } 
/*      */ 
/*      */               
/*  745 */               refEnd--;
/*  746 */               length--;
/*      */             } 
/*      */             
/*  749 */             this.disableProcessingAssert = oldDisableProcessingAssert;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  754 */       this.processing.unlock();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  759 */     commandBuffer.consume();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> copyEntity(@Nonnull Ref<ECS_TYPE> ref) {
/*  764 */     return copyEntity(ref, this.registry.newHolder());
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> copyEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> holder) {
/*  769 */     assertThread();
/*  770 */     ref.validate();
/*      */     
/*  772 */     int refIndex = ref.getIndex();
/*  773 */     int archetypeIndex = this.entityToArchetypeChunk[refIndex];
/*  774 */     return this.archetypeChunks[archetypeIndex].copyEntity(this.entityChunkIndex[refIndex], holder);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> copySerializableEntity(@Nonnull Ref<ECS_TYPE> ref) {
/*  779 */     return copySerializableEntity(ref, this.registry.newHolder());
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> copySerializableEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> holder) {
/*  784 */     assertThread();
/*  785 */     ref.validate();
/*      */     
/*  787 */     int refIndex = ref.getIndex();
/*  788 */     int archetypeIndex = this.entityToArchetypeChunk[refIndex];
/*  789 */     return this.archetypeChunks[archetypeIndex].copySerializableEntity(this.registry.getData(), this.entityChunkIndex[refIndex], holder);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Archetype<ECS_TYPE> getArchetype(@Nonnull Ref<ECS_TYPE> ref) {
/*  795 */     assertThread();
/*  796 */     ref.validate();
/*      */     
/*  798 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*  799 */     return this.archetypeChunks[archetypeIndex].getArchetype();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected Archetype<ECS_TYPE> __internal_getArchetype(@Nonnull Ref<ECS_TYPE> ref) {
/*  804 */     ref.validate();
/*      */     
/*  806 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*  807 */     return this.archetypeChunks[archetypeIndex].getArchetype();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> removeEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull RemoveReason reason) {
/*  813 */     return removeEntity(ref, this.registry.newHolder(), reason);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> removeEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> holder, @Nonnull RemoveReason reason) {
/*  819 */     return removeEntity(ref, holder, reason, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   Holder<ECS_TYPE> removeEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> holder, @Nonnull RemoveReason reason, @Nullable Throwable proxyReason) {
/*  826 */     assertThread();
/*  827 */     assertWriteProcessing();
/*  828 */     ref.validate();
/*      */     
/*  830 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/*  832 */     int entityIndex = ref.getIndex();
/*  833 */     int archetypeIndex = this.entityToArchetypeChunk[entityIndex];
/*  834 */     int chunkEntityRef = this.entityChunkIndex[entityIndex];
/*      */     
/*  836 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*  837 */     this.processing.lock();
/*      */     
/*      */     try {
/*  840 */       SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> systemType = this.registry.getRefSystemType();
/*  841 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/*  843 */       BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*      */       
/*  845 */       int systemIndex = -1;
/*  846 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*  847 */         if (entityProcessedBySystemIndexes.get(systemIndex)) {
/*  848 */           ((RefSystem)data.<RefSystem>getSystem(systemIndex, (SystemType)systemType)).onEntityRemove(ref, reason, this, commandBuffer);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  854 */       int lastIndex = this.entitiesSize - 1;
/*  855 */       if (entityIndex != lastIndex) {
/*      */         
/*  857 */         Ref<ECS_TYPE> lastEntityRef = this.refs[lastIndex];
/*  858 */         int lastSelfEntityRef = this.entityToArchetypeChunk[lastIndex];
/*  859 */         int lastEntityChunkIndex = this.entityChunkIndex[lastIndex];
/*      */ 
/*      */         
/*  862 */         lastEntityRef.setIndex(entityIndex);
/*      */         
/*  864 */         this.refs[entityIndex] = lastEntityRef;
/*  865 */         this.entityToArchetypeChunk[entityIndex] = lastSelfEntityRef;
/*  866 */         this.entityChunkIndex[entityIndex] = lastEntityChunkIndex;
/*      */       } 
/*      */       
/*  869 */       this.refs[lastIndex] = null;
/*  870 */       this.entityToArchetypeChunk[lastIndex] = Integer.MIN_VALUE;
/*  871 */       this.entityChunkIndex[lastIndex] = Integer.MIN_VALUE;
/*  872 */       this.entitiesSize = lastIndex;
/*      */       
/*  874 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*  875 */       archetypeChunk.removeEntity(chunkEntityRef, holder);
/*      */       
/*  877 */       if (archetypeChunk.size() == 0) removeArchetypeChunk(archetypeIndex);
/*      */       
/*  879 */       ref.invalidate(proxyReason);
/*      */     } finally {
/*  881 */       this.processing.unlock();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  886 */     commandBuffer.consume();
/*      */     
/*  888 */     this.processing.lock();
/*      */     try {
/*  890 */       SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> systemType = this.registry.getHolderSystemType();
/*  891 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/*  893 */       int systemIndex = -1;
/*  894 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*  895 */         HolderSystem<ECS_TYPE> system = data.<HolderSystem<ECS_TYPE>>getSystem(systemIndex, systemType);
/*  896 */         if (system.test(this.registry, holder.getArchetype())) {
/*  897 */           system.onEntityRemoved(holder, reason, this);
/*      */         }
/*      */       } 
/*      */     } finally {
/*  901 */       this.processing.unlock();
/*      */     } 
/*      */     
/*  904 */     return holder;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE>[] removeEntities(@Nonnull Ref<ECS_TYPE>[] refs, @Nonnull RemoveReason reason) {
/*  910 */     return removeEntities(refs, 0, refs.length, reason);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE>[] removeEntities(@Nonnull Ref<ECS_TYPE>[] refs, int start, int length, @Nonnull RemoveReason reason) {
/*  916 */     Holder[] arrayOfHolder = new Holder[length];
/*  917 */     for (int i = 0; i < length; i++) {
/*  918 */       arrayOfHolder[i] = this.registry.newHolder();
/*      */     }
/*  920 */     return removeEntities(refs, start, (Holder<ECS_TYPE>[])arrayOfHolder, 0, length, reason);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE>[] removeEntities(@Nonnull Ref<ECS_TYPE>[] refs, @Nonnull Holder<ECS_TYPE>[] holders, @Nonnull RemoveReason reason) {
/*  925 */     if (refs.length != holders.length) throw new IllegalArgumentException("EntityHolder and EntityReference array length doesn't match!"); 
/*  926 */     return removeEntities(refs, 0, holders, 0, refs.length, reason);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE>[] removeEntities(@Nonnull Ref<ECS_TYPE>[] refArr, int refStart, @Nonnull Holder<ECS_TYPE>[] holders, int holderStart, int length, @Nonnull RemoveReason reason) {
/*  932 */     int refEnd = refStart + length;
/*  933 */     int holderEnd = holderStart + length;
/*  934 */     if (refArr.length < refEnd) throw new IllegalArgumentException("EntityReference start and length exceed array length!"); 
/*  935 */     if (holders.length < holderEnd) throw new IllegalArgumentException("EntityHolder start and length exceed array length!");
/*      */     
/*  937 */     for (int i = refStart; i < refEnd; i++) {
/*  938 */       refArr[i].validate();
/*      */     }
/*      */     
/*  941 */     assertThread();
/*  942 */     assertWriteProcessing();
/*      */     
/*  944 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/*  946 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*  947 */     this.processing.lock();
/*      */     
/*      */     try {
/*  950 */       SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> systemType = this.registry.getRefSystemType();
/*  951 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/*  953 */       int systemIndex = -1;
/*  954 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*      */         
/*  956 */         for (int k = refStart; k < refEnd; k++) {
/*  957 */           Ref<ECS_TYPE> ref = refArr[k];
/*  958 */           int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*  959 */           BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*  960 */           if (entityProcessedBySystemIndexes.get(systemIndex)) {
/*  961 */             ((RefSystem)data.<RefSystem>getSystem(systemIndex, (SystemType)systemType)).onEntityRemove(refArr[k], reason, this, commandBuffer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*      */       int j;
/*  967 */       for (j = 0; j < length; j++) {
/*  968 */         int entityIndex = refArr[refStart + j].getIndex();
/*  969 */         int archetypeIndex = this.entityToArchetypeChunk[entityIndex];
/*  970 */         int chunkEntityRef = this.entityChunkIndex[entityIndex];
/*      */ 
/*      */         
/*  973 */         int lastIndex = this.entitiesSize - 1;
/*  974 */         if (entityIndex != lastIndex) {
/*      */           
/*  976 */           Ref<ECS_TYPE> lastEntityRef = this.refs[lastIndex];
/*  977 */           int lastSelfEntityRef = this.entityToArchetypeChunk[lastIndex];
/*  978 */           int lastEntityChunkIndex = this.entityChunkIndex[lastIndex];
/*      */ 
/*      */           
/*  981 */           lastEntityRef.setIndex(entityIndex);
/*      */           
/*  983 */           this.refs[entityIndex] = lastEntityRef;
/*  984 */           this.entityToArchetypeChunk[entityIndex] = lastSelfEntityRef;
/*  985 */           this.entityChunkIndex[entityIndex] = lastEntityChunkIndex;
/*      */         } 
/*      */         
/*  988 */         this.refs[lastIndex] = null;
/*  989 */         this.entityToArchetypeChunk[lastIndex] = Integer.MIN_VALUE;
/*  990 */         this.entityChunkIndex[lastIndex] = Integer.MIN_VALUE;
/*  991 */         this.entitiesSize = lastIndex;
/*      */         
/*  993 */         Holder<ECS_TYPE> holder = holders[holderStart + j];
/*      */         
/*  995 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*  996 */         archetypeChunk.removeEntity(chunkEntityRef, holder);
/*      */         
/*  998 */         if (archetypeChunk.size() == 0) removeArchetypeChunk(archetypeIndex);
/*      */       
/*      */       } 
/* 1001 */       for (j = refStart; j < refEnd; j++) {
/* 1002 */         refArr[j].invalidate();
/*      */       }
/*      */     } finally {
/* 1005 */       this.processing.unlock();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     commandBuffer.consume();
/*      */     
/* 1012 */     this.processing.lock();
/*      */     try {
/* 1014 */       SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> systemType = this.registry.getHolderSystemType();
/* 1015 */       BitSet systemIndexes = data.getSystemIndexesForType(systemType);
/*      */       
/* 1017 */       int systemIndex = -1;
/* 1018 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1019 */         HolderSystem<ECS_TYPE> system = data.<HolderSystem<ECS_TYPE>>getSystem(systemIndex, systemType);
/* 1020 */         for (int j = holderStart; j < holderEnd; j++) {
/* 1021 */           if (system.test(this.registry, holders[j].getArchetype())) {
/* 1022 */             system.onEntityRemoved(holders[j], reason, this);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1027 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1030 */     return holders;
/*      */   }
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void ensureComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1034 */     assertThread();
/* 1035 */     assertWriteProcessing();
/*      */     
/* 1037 */     componentType.validateRegistry(this.registry);
/* 1038 */     componentType.validate();
/*      */     
/* 1040 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1041 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*      */     
/* 1043 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1046 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1047 */       if (!archetypeChunk.getArchetype().contains(componentType)) {
/*      */         
/* 1049 */         T component = this.registry._internal_getData().createComponent(componentType);
/* 1050 */         datachunk_addComponent(ref, archetypeIndex, componentType, component, commandBuffer);
/*      */       } 
/*      */     } finally {
/* 1053 */       this.processing.unlock();
/*      */     } 
/* 1055 */     commandBuffer.consume();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Component<ECS_TYPE>> T ensureAndGetComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/*      */     T component;
/* 1061 */     assertThread();
/* 1062 */     assertWriteProcessing();
/*      */     
/* 1064 */     int refIndex = ref.getIndex();
/*      */     
/* 1066 */     componentType.validateRegistry(this.registry);
/* 1067 */     componentType.validate();
/*      */     
/* 1069 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1070 */     int archetypeIndex = this.entityToArchetypeChunk[refIndex];
/*      */ 
/*      */     
/* 1073 */     this.processing.lock();
/*      */ 
/*      */     
/*      */     try {
/* 1077 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1078 */       component = archetypeChunk.getComponent(this.entityChunkIndex[refIndex], componentType);
/* 1079 */       if (component == null) {
/*      */         
/* 1081 */         component = this.registry._internal_getData().createComponent(componentType);
/* 1082 */         datachunk_addComponent(ref, archetypeIndex, componentType, component, commandBuffer);
/*      */       } 
/*      */     } finally {
/* 1085 */       this.processing.unlock();
/*      */     } 
/* 1087 */     commandBuffer.consume();
/*      */     
/* 1089 */     return component;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Component<ECS_TYPE>> T addComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1095 */     assertThread();
/* 1096 */     assertWriteProcessing();
/*      */     
/* 1098 */     T component = this.registry._internal_getData().createComponent(componentType);
/* 1099 */     addComponent(ref, componentType, component);
/* 1100 */     return component;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void addComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 1105 */     assertThread();
/* 1106 */     assertWriteProcessing();
/*      */     
/* 1108 */     ref.validate();
/* 1109 */     componentType.validateRegistry(this.registry);
/* 1110 */     componentType.validate();
/* 1111 */     Objects.requireNonNull(component);
/*      */     
/* 1113 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1114 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*      */     
/* 1116 */     this.processing.lock();
/*      */     try {
/* 1118 */       datachunk_addComponent(ref, archetypeIndex, componentType, component, commandBuffer);
/*      */     } finally {
/* 1120 */       this.processing.unlock();
/*      */     } 
/* 1122 */     commandBuffer.consume();
/*      */   }
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void replaceComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 1126 */     assertThread();
/* 1127 */     assertWriteProcessing();
/*      */     
/* 1129 */     ref.validate();
/* 1130 */     componentType.validateRegistry(this.registry);
/* 1131 */     componentType.validate();
/* 1132 */     Objects.requireNonNull(component);
/*      */     
/* 1134 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1135 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*      */     
/* 1137 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1140 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1141 */       int chunkEntityRef = this.entityChunkIndex[ref.getIndex()];
/*      */       
/* 1143 */       T oldComponent = archetypeChunk.getComponent(chunkEntityRef, componentType);
/*      */       
/* 1145 */       archetypeChunk.setComponent(chunkEntityRef, componentType, component);
/*      */       
/* 1147 */       BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*      */       
/* 1149 */       ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1150 */       BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getRefChangeSystemType());
/* 1151 */       int systemIndex = -1;
/* 1152 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1153 */         if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 1154 */           RefChangeSystem<ECS_TYPE, T> system = (RefChangeSystem)data.getSystem(systemIndex);
/* 1155 */           if (system.componentType().getIndex() == componentType.getIndex()) {
/* 1156 */             system.onComponentSet(ref, (Component)oldComponent, (Component)component, this, commandBuffer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1161 */       this.processing.unlock();
/*      */     } 
/* 1163 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void putComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 1168 */     assertThread();
/* 1169 */     assertWriteProcessing();
/*      */     
/* 1171 */     ref.validate();
/* 1172 */     componentType.validateRegistry(this.registry);
/* 1173 */     componentType.validate();
/* 1174 */     Objects.requireNonNull(component);
/*      */     
/* 1176 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1177 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/*      */     
/* 1179 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1182 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1183 */       if (archetypeChunk.getArchetype().contains(componentType)) {
/*      */         
/* 1185 */         int chunkEntityRef = this.entityChunkIndex[ref.getIndex()];
/*      */         
/* 1187 */         T oldComponent = archetypeChunk.getComponent(chunkEntityRef, componentType);
/*      */         
/* 1189 */         archetypeChunk.setComponent(chunkEntityRef, componentType, component);
/*      */         
/* 1191 */         BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*      */         
/* 1193 */         ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1194 */         BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getRefChangeSystemType());
/* 1195 */         int systemIndex = -1;
/* 1196 */         while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1197 */           if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 1198 */             RefChangeSystem<ECS_TYPE, T> system = (RefChangeSystem)data.getSystem(systemIndex);
/* 1199 */             if (system.componentType().getIndex() == componentType.getIndex()) {
/* 1200 */               system.onComponentSet(ref, (Component)oldComponent, (Component)component, this, commandBuffer);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1205 */         datachunk_addComponent(ref, archetypeIndex, componentType, component, commandBuffer);
/*      */       } 
/*      */     } finally {
/* 1208 */       this.processing.unlock();
/*      */     } 
/* 1210 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> T getComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1215 */     assertThread();
/* 1216 */     return __internal_getComponent(ref, componentType);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected <T extends Component<ECS_TYPE>> T __internal_getComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1222 */     ref.validate();
/* 1223 */     componentType.validateRegistry(this.registry);
/* 1224 */     componentType.validate();
/*      */     
/* 1226 */     int archetypeIndex = this.entityToArchetypeChunk[ref.getIndex()];
/* 1227 */     ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1228 */     return archetypeChunk.getComponent(this.entityChunkIndex[ref.getIndex()], componentType);
/*      */   }
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void removeComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1232 */     assertThread();
/* 1233 */     assertWriteProcessing();
/*      */     
/* 1235 */     ref.validate();
/* 1236 */     componentType.validateRegistry(this.registry);
/* 1237 */     componentType.validate();
/*      */     
/* 1239 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1240 */     int entityIndex = ref.getIndex();
/* 1241 */     int fromArchetypeIndex = this.entityToArchetypeChunk[entityIndex];
/*      */     
/* 1243 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1246 */       ArchetypeChunk<ECS_TYPE> fromArchetypeChunk = this.archetypeChunks[fromArchetypeIndex];
/* 1247 */       Holder<ECS_TYPE> holder = this.registry._internal_newEntityHolder();
/* 1248 */       fromArchetypeChunk.removeEntity(this.entityChunkIndex[entityIndex], holder);
/*      */       
/* 1250 */       T component = holder.getComponent(componentType);
/* 1251 */       assert component != null;
/*      */ 
/*      */       
/* 1254 */       holder.removeComponent(componentType);
/*      */ 
/*      */       
/* 1257 */       int toArchetypeIndex = findOrCreateArchetypeChunk(holder.getArchetype());
/* 1258 */       ArchetypeChunk<ECS_TYPE> toArchetypeChunk = this.archetypeChunks[toArchetypeIndex];
/* 1259 */       int chunkEntityRef = toArchetypeChunk.addEntity(ref, holder);
/*      */       
/* 1261 */       this.entityToArchetypeChunk[entityIndex] = toArchetypeIndex;
/* 1262 */       this.entityChunkIndex[entityIndex] = chunkEntityRef;
/*      */ 
/*      */       
/* 1265 */       BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[fromArchetypeIndex];
/*      */       
/* 1267 */       ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1268 */       BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getRefChangeSystemType());
/* 1269 */       int systemIndex = -1;
/* 1270 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1271 */         if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 1272 */           RefChangeSystem<ECS_TYPE, T> system = (RefChangeSystem)data.getSystem(systemIndex);
/* 1273 */           if (system.componentType().getIndex() == componentType.getIndex()) {
/* 1274 */             system.onComponentRemoved(ref, (Component)component, this, commandBuffer);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1280 */       if (fromArchetypeChunk.size() == 0) removeArchetypeChunk(fromArchetypeIndex); 
/*      */     } finally {
/* 1282 */       this.processing.unlock();
/*      */     } 
/* 1284 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void tryRemoveComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1289 */     removeComponentIfExists(ref, componentType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> boolean removeComponentIfExists(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/*      */     boolean result;
/* 1301 */     assertThread();
/* 1302 */     assertWriteProcessing();
/*      */     
/* 1304 */     ref.validate();
/* 1305 */     componentType.validateRegistry(this.registry);
/* 1306 */     componentType.validate();
/*      */     
/* 1308 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1309 */     int entityIndex = ref.getIndex();
/* 1310 */     int fromArchetypeIndex = this.entityToArchetypeChunk[entityIndex];
/*      */ 
/*      */     
/* 1313 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1316 */       ArchetypeChunk<ECS_TYPE> fromArchetypeChunk = this.archetypeChunks[fromArchetypeIndex];
/* 1317 */       if (!fromArchetypeChunk.getArchetype().contains(componentType)) {
/* 1318 */         result = false;
/*      */       } else {
/*      */         
/* 1321 */         Holder<ECS_TYPE> holder = this.registry._internal_newEntityHolder();
/* 1322 */         fromArchetypeChunk.removeEntity(this.entityChunkIndex[entityIndex], holder);
/*      */         
/* 1324 */         T component = holder.getComponent(componentType);
/* 1325 */         assert component != null;
/*      */ 
/*      */         
/* 1328 */         holder.removeComponent(componentType);
/*      */ 
/*      */         
/* 1331 */         int toArchetypeIndex = findOrCreateArchetypeChunk(holder.getArchetype());
/* 1332 */         ArchetypeChunk<ECS_TYPE> toArchetypeChunk = this.archetypeChunks[toArchetypeIndex];
/* 1333 */         int chunkEntityRef = toArchetypeChunk.addEntity(ref, holder);
/*      */         
/* 1335 */         this.entityToArchetypeChunk[entityIndex] = toArchetypeIndex;
/* 1336 */         this.entityChunkIndex[entityIndex] = chunkEntityRef;
/*      */ 
/*      */         
/* 1339 */         BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[fromArchetypeIndex];
/*      */         
/* 1341 */         ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1342 */         BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getRefChangeSystemType());
/* 1343 */         int systemIndex = -1;
/* 1344 */         while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1345 */           if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 1346 */             RefChangeSystem<ECS_TYPE, T> system = (RefChangeSystem)data.getSystem(systemIndex);
/* 1347 */             if (system.componentType().getIndex() == componentType.getIndex()) {
/* 1348 */               system.onComponentRemoved(ref, (Component)component, this, commandBuffer);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1354 */         if (fromArchetypeChunk.size() == 0) removeArchetypeChunk(fromArchetypeIndex); 
/* 1355 */         result = true;
/*      */       } 
/*      */     } finally {
/* 1358 */       this.processing.unlock();
/*      */     } 
/* 1360 */     commandBuffer.consume();
/*      */     
/* 1362 */     return result;
/*      */   }
/*      */   
/*      */   public <T extends Resource<ECS_TYPE>> void replaceResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType, @Nonnull T resource) {
/* 1366 */     assertThread();
/* 1367 */     resourceType.validateRegistry(this.registry);
/* 1368 */     Objects.requireNonNull(resource);
/* 1369 */     this.resources[resourceType.getIndex()] = (Resource<ECS_TYPE>)resource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Resource<ECS_TYPE>> T getResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1377 */     resourceType.validateRegistry(this.registry);
/* 1378 */     return (T)this.resources[resourceType.getIndex()];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected <T extends Resource<ECS_TYPE>> T __internal_getResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1385 */     resourceType.validateRegistry(this.registry);
/* 1386 */     return (T)this.resources[resourceType.getIndex()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEachChunk(@Nonnull BiConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer) {
/* 1395 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1396 */     assertThread();
/*      */     
/* 1398 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1400 */     this.processing.lock();
/*      */     try {
/* 1402 */       for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1403 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1404 */         if (archetypeChunk != null)
/* 1405 */           consumer.accept(archetypeChunk, commandBuffer); 
/*      */       } 
/*      */     } finally {
/* 1408 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1411 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forEachChunk(@Nonnull BiPredicate<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> predicate) {
/* 1421 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1422 */     assertThread();
/*      */     
/* 1424 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1426 */     boolean result = false;
/* 1427 */     this.processing.lock();
/*      */     try {
/* 1429 */       for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1430 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1431 */         if (archetypeChunk != null && 
/* 1432 */           predicate.test(archetypeChunk, commandBuffer)) {
/* 1433 */           result = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1438 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1441 */     commandBuffer.consume();
/* 1442 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEachChunk(Query<ECS_TYPE> query, @Nonnull BiConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer) {
/* 1453 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1454 */     assertThread();
/*      */     
/* 1456 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1458 */     this.processing.lock();
/*      */     try {
/* 1460 */       if (query instanceof ExactArchetypeQuery) { ExactArchetypeQuery<ECS_TYPE> exactQuery = (ExactArchetypeQuery<ECS_TYPE>)query;
/* 1461 */         int archetypeIndex = this.archetypeToIndexMap.getInt(exactQuery.getArchetype());
/* 1462 */         if (archetypeIndex != Integer.MIN_VALUE) {
/* 1463 */           consumer.accept(this.archetypeChunks[archetypeIndex], commandBuffer);
/*      */         } }
/*      */       else
/* 1466 */       { for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1467 */           ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1468 */           if (archetypeChunk != null && 
/* 1469 */             query.test(archetypeChunk.getArchetype())) {
/* 1470 */             consumer.accept(this.archetypeChunks[archetypeIndex], commandBuffer);
/*      */           }
/*      */         }  }
/*      */     
/*      */     } finally {
/* 1475 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1478 */     commandBuffer.consume();
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
/*      */   public boolean forEachChunk(Query<ECS_TYPE> query, @Nonnull BiPredicate<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> predicate) {
/* 1490 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1491 */     assertThread();
/*      */     
/* 1493 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1495 */     boolean result = false;
/* 1496 */     this.processing.lock();
/*      */     try {
/* 1498 */       if (query instanceof ExactArchetypeQuery) { ExactArchetypeQuery<ECS_TYPE> exactQuery = (ExactArchetypeQuery<ECS_TYPE>)query;
/* 1499 */         int archetypeIndex = this.archetypeToIndexMap.getInt(exactQuery.getArchetype());
/* 1500 */         if (archetypeIndex != Integer.MIN_VALUE) {
/* 1501 */           result = predicate.test(this.archetypeChunks[archetypeIndex], commandBuffer);
/*      */         } }
/*      */       else
/* 1504 */       { for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1505 */           ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1506 */           if (archetypeChunk != null && 
/* 1507 */             query.test(archetypeChunk.getArchetype()) && 
/* 1508 */             predicate.test(this.archetypeChunks[archetypeIndex], commandBuffer)) {
/* 1509 */             result = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         }  }
/*      */     
/*      */     } finally {
/* 1516 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1519 */     commandBuffer.consume();
/* 1520 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEachChunk(int systemIndex, @Nonnull BiConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer) {
/* 1531 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1532 */     assertThread();
/*      */     
/* 1534 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1536 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1539 */       BitSet indexes = this.systemIndexToArchetypeChunkIndexes[systemIndex];
/*      */ 
/*      */       
/* 1542 */       int index = -1;
/* 1543 */       while ((index = indexes.nextSetBit(index + 1)) >= 0) {
/* 1544 */         consumer.accept(this.archetypeChunks[index], commandBuffer);
/*      */       }
/*      */     } finally {
/* 1547 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1550 */     commandBuffer.consume();
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
/*      */   public boolean forEachChunk(int systemIndex, @Nonnull BiPredicate<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> predicate) {
/* 1562 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1563 */     assertThread();
/*      */     
/* 1565 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/*      */     
/* 1567 */     boolean result = false;
/* 1568 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1571 */       BitSet indexes = this.systemIndexToArchetypeChunkIndexes[systemIndex];
/*      */ 
/*      */       
/* 1574 */       int index = -1;
/* 1575 */       while ((index = indexes.nextSetBit(index + 1)) >= 0) {
/* 1576 */         if (predicate.test(this.archetypeChunks[index], commandBuffer)) {
/* 1577 */           result = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1582 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1585 */     commandBuffer.consume();
/* 1586 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEachEntityParallel(IntBiObjectConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer) {
/* 1595 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1596 */     assertThread();
/*      */     
/* 1598 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1599 */     this.forEachTask.init();
/*      */     
/* 1601 */     this.processing.lock();
/*      */     try {
/* 1603 */       for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1604 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1605 */         if (archetypeChunk != null) {
/*      */           
/* 1607 */           int size = archetypeChunk.size();
/* 1608 */           if (size != 0) {
/* 1609 */             ParallelRangeTask<ForEachTaskData<ECS_TYPE>> systemTask = this.forEachTask.appendTask();
/* 1610 */             systemTask.init(0, size);
/* 1611 */             for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++)
/* 1612 */               ((ForEachTaskData)systemTask.get(i)).init(consumer, archetypeChunk, commandBuffer.fork()); 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1616 */       ForEachTaskData.invokeParallelTask(this.forEachTask, commandBuffer);
/*      */     } finally {
/* 1618 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1621 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEachEntityParallel(Query<ECS_TYPE> query, IntBiObjectConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer) {
/* 1631 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1632 */     assertThread();
/*      */     
/* 1634 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1635 */     this.forEachTask.init();
/*      */     
/* 1637 */     this.processing.lock();
/*      */     try {
/* 1639 */       if (query instanceof ExactArchetypeQuery) { ExactArchetypeQuery<ECS_TYPE> exactQuery = (ExactArchetypeQuery<ECS_TYPE>)query;
/* 1640 */         int archetypeIndex = this.archetypeToIndexMap.getInt(exactQuery.getArchetype());
/* 1641 */         if (archetypeIndex != Integer.MIN_VALUE) {
/*      */           
/* 1643 */           ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1644 */           int archetypeChunkSize = archetypeChunk.size();
/* 1645 */           if (archetypeChunkSize != 0) {
/* 1646 */             ParallelRangeTask<ForEachTaskData<ECS_TYPE>> systemTask = this.forEachTask.appendTask();
/* 1647 */             systemTask.init(0, archetypeChunkSize);
/* 1648 */             for (int i = 0, systemSize = systemTask.size(); i < systemSize; i++) {
/* 1649 */               ((ForEachTaskData)systemTask.get(i)).init(consumer, archetypeChunk, commandBuffer.fork());
/*      */             }
/*      */           } 
/*      */         }  }
/*      */       else
/* 1654 */       { for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 1655 */           ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1656 */           if (archetypeChunk != null)
/*      */           {
/* 1658 */             if (query.test(archetypeChunk.getArchetype())) {
/* 1659 */               int archetypeChunkSize = archetypeChunk.size();
/* 1660 */               if (archetypeChunkSize != 0) {
/* 1661 */                 ParallelRangeTask<ForEachTaskData<ECS_TYPE>> systemTask = this.forEachTask.appendTask();
/* 1662 */                 systemTask.init(0, archetypeChunkSize);
/* 1663 */                 for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++)
/* 1664 */                   ((ForEachTaskData)systemTask.get(i)).init(consumer, archetypeChunk, commandBuffer.fork()); 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         }  }
/*      */       
/* 1670 */       ForEachTaskData.invokeParallelTask(this.forEachTask, commandBuffer);
/*      */     } finally {
/* 1672 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1675 */     commandBuffer.consume();
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
/*      */   public <T extends ArchetypeDataSystem<ECS_TYPE, Q, R>, Q, R> void fetch(@Nonnull SystemType<ECS_TYPE, T> systemType, Q query, @Nonnull List<R> results) {
/* 1687 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1688 */     assertThread();
/*      */     
/* 1690 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*      */     
/* 1692 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1693 */     this.fetchTask.init();
/* 1694 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType)systemType);
/*      */     
/* 1696 */     this.processing.lock();
/*      */     try {
/* 1698 */       int systemIndex = -1;
/* 1699 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1700 */         ArchetypeDataSystem archetypeDataSystem = data.<ArchetypeDataSystem>getSystem(systemIndex, systemType);
/* 1701 */         BitSet indexes = this.systemIndexToArchetypeChunkIndexes[systemIndex];
/*      */ 
/*      */         
/* 1704 */         int index = -1;
/* 1705 */         while ((index = indexes.nextSetBit(index + 1)) >= 0) {
/* 1706 */           archetypeDataSystem.fetch(this.archetypeChunks[index], this, commandBuffer, query, results);
/*      */         }
/*      */       } 
/*      */       
/* 1710 */       EntityDataSystem.SystemTaskData.invokeParallelTask(this.fetchTask, commandBuffer, results);
/*      */     } finally {
/* 1712 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1715 */     commandBuffer.consume();
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
/*      */   public <T extends EntityDataSystem<ECS_TYPE, Q, R>, Q, R> void fetch(@Nonnull Collection<Ref<ECS_TYPE>> refs, @Nonnull SystemType<ECS_TYPE, T> systemType, Q query, @Nonnull List<R> results) {
/* 1727 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1728 */     assertThread();
/*      */     
/* 1730 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*      */     
/* 1732 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1733 */     this.fetchTask.init();
/* 1734 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType)systemType);
/*      */     
/* 1736 */     this.processing.lock();
/*      */     try {
/* 1738 */       int systemIndex = -1;
/* 1739 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1740 */         EntityDataSystem entityDataSystem = data.<EntityDataSystem>getSystem(systemIndex, systemType);
/*      */         
/* 1742 */         for (Ref<ECS_TYPE> ref : refs) {
/* 1743 */           int entityIndex = ref.getIndex();
/* 1744 */           int archetypeIndex = this.entityToArchetypeChunk[entityIndex];
/* 1745 */           BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/* 1746 */           if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 1747 */             int index = this.entityChunkIndex[entityIndex];
/* 1748 */             ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/*      */             
/* 1750 */             entityDataSystem.fetch(index, archetypeChunk, this, commandBuffer, query, results);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1755 */       EntityDataSystem.SystemTaskData.invokeParallelTask(this.fetchTask, commandBuffer, results);
/*      */     } finally {
/* 1757 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1760 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */   
/*      */   public <Event extends EcsEvent> void invoke(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Event param) {
/* 1765 */     EntityEventType<ECS_TYPE, ?> eventType = this.registry.getEntityEventTypeForClass(param.getClass());
/* 1766 */     if (eventType == null)
/*      */       return; 
/* 1768 */     invoke(eventType, ref, param);
/*      */   }
/*      */ 
/*      */   
/*      */   public <Event extends EcsEvent> void invoke(@Nonnull EntityEventType<ECS_TYPE, Event> systemType, @Nonnull Ref<ECS_TYPE> ref, @Nonnull Event param) {
/* 1773 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1774 */     assertThread();
/*      */     
/* 1776 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1777 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1778 */     commandBuffer.track(ref);
/*      */     
/* 1780 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType<ECS_TYPE, Event>)systemType);
/*      */     
/* 1782 */     this.processing.lock();
/*      */     try {
/* 1784 */       int entityIndex = ref.getIndex();
/* 1785 */       int archetypeIndex = this.entityToArchetypeChunk[entityIndex];
/* 1786 */       BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/* 1787 */       ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1788 */       int index = this.entityChunkIndex[entityIndex];
/*      */       
/* 1790 */       int systemIndex = -1;
/* 1791 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1792 */         systemIndex = entityProcessedBySystemIndexes.nextSetBit(systemIndex);
/* 1793 */         if (systemIndex < 0)
/* 1794 */           break;  if (!systemIndexes.get(systemIndex))
/*      */           continue; 
/* 1796 */         EntityEventSystem<ECS_TYPE, Event> system = data.<EntityEventSystem<ECS_TYPE, Event>>getSystem(systemIndex, (SystemType)systemType);
/* 1797 */         system.handleInternal(index, archetypeChunk, this, commandBuffer, (EcsEvent)param);
/*      */         
/* 1799 */         if (commandBuffer.consumeWasTrackedRefRemoved())
/*      */           break; 
/*      */       } 
/*      */     } finally {
/* 1803 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1806 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */   
/*      */   public <Event extends EcsEvent> void invoke(@Nonnull Event param) {
/* 1811 */     WorldEventType<ECS_TYPE, ?> eventType = this.registry.getWorldEventTypeForClass(param.getClass());
/* 1812 */     if (eventType == null)
/*      */       return; 
/* 1814 */     invoke(eventType, param);
/*      */   }
/*      */ 
/*      */   
/*      */   public <Event extends EcsEvent> void invoke(@Nonnull WorldEventType<ECS_TYPE, Event> systemType, @Nonnull Event param) {
/* 1819 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1820 */     assertThread();
/*      */     
/* 1822 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1823 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1824 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType<ECS_TYPE, Event>)systemType);
/*      */     
/* 1826 */     this.processing.lock();
/*      */     try {
/* 1828 */       int systemIndex = -1;
/* 1829 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1830 */         WorldEventSystem<ECS_TYPE, Event> system = data.<WorldEventSystem<ECS_TYPE, Event>>getSystem(systemIndex, (SystemType)systemType);
/* 1831 */         system.handleInternal(this, commandBuffer, (EcsEvent)param);
/*      */       } 
/*      */     } finally {
/*      */       
/* 1835 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 1838 */     commandBuffer.consume();
/*      */   }
/*      */   
/*      */   protected <Event extends EcsEvent> void internal_invoke(CommandBuffer<ECS_TYPE> sourceCommandBuffer, Ref<ECS_TYPE> ref, Event param) {
/* 1842 */     EntityEventType<ECS_TYPE, ?> eventType = this.registry.getEntityEventTypeForClass(param.getClass());
/* 1843 */     if (eventType == null)
/*      */       return; 
/* 1845 */     internal_invoke(sourceCommandBuffer, eventType, ref, param);
/*      */   }
/*      */   
/*      */   protected <Event extends EcsEvent> void internal_invoke(CommandBuffer<ECS_TYPE> sourceCommandBuffer, @Nonnull EntityEventType<ECS_TYPE, Event> systemType, Ref<ECS_TYPE> ref, Event param) {
/* 1849 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1850 */     CommandBuffer<ECS_TYPE> commandBuffer = sourceCommandBuffer.fork();
/* 1851 */     commandBuffer.track(ref);
/*      */     
/* 1853 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType<ECS_TYPE, Event>)systemType);
/*      */     
/* 1855 */     int entityIndex = ref.getIndex();
/* 1856 */     int archetypeIndex = this.entityToArchetypeChunk[entityIndex];
/* 1857 */     BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/* 1858 */     ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 1859 */     int index = this.entityChunkIndex[entityIndex];
/*      */     
/* 1861 */     int systemIndex = -1;
/* 1862 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1863 */       systemIndex = entityProcessedBySystemIndexes.nextSetBit(systemIndex);
/* 1864 */       if (systemIndex < 0)
/* 1865 */         break;  if (!systemIndexes.get(systemIndex))
/* 1866 */         continue;  EntityEventSystem<ECS_TYPE, Event> system = data.<EntityEventSystem<ECS_TYPE, Event>>getSystem(systemIndex, (SystemType)systemType);
/* 1867 */       system.handleInternal(index, archetypeChunk, this, commandBuffer, (EcsEvent)param);
/*      */       
/* 1869 */       if (commandBuffer.consumeWasTrackedRefRemoved())
/*      */         break; 
/*      */     } 
/* 1872 */     commandBuffer.mergeParallel(sourceCommandBuffer);
/*      */   }
/*      */   
/*      */   protected <Event extends EcsEvent> void internal_invoke(CommandBuffer<ECS_TYPE> sourceCommandBuffer, Event param) {
/* 1876 */     WorldEventType<ECS_TYPE, ?> eventType = this.registry.getWorldEventTypeForClass(param.getClass());
/* 1877 */     if (eventType == null)
/*      */       return; 
/* 1879 */     internal_invoke(sourceCommandBuffer, eventType, param);
/*      */   }
/*      */   
/*      */   protected <Event extends EcsEvent> void internal_invoke(CommandBuffer<ECS_TYPE> sourceCommandBuffer, @Nonnull WorldEventType<ECS_TYPE, Event> systemType, Event param) {
/* 1883 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 1884 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType<ECS_TYPE, Event>)systemType);
/*      */     
/* 1886 */     int systemIndex = -1;
/* 1887 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1888 */       WorldEventSystem<ECS_TYPE, Event> system = data.<WorldEventSystem<ECS_TYPE, Event>>getSystem(systemIndex, (SystemType)systemType);
/* 1889 */       system.handleInternal(this, sourceCommandBuffer, (EcsEvent)param);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick(float dt) {
/* 1897 */     tickInternal(dt, (SystemType)this.registry.getTickingSystemType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pausedTick(float dt) {
/* 1904 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1905 */     assertThread();
/*      */     
/* 1907 */     tickInternal(dt, (SystemType)this.registry.getRunWhenPausedSystemType());
/*      */   }
/*      */ 
/*      */   
/*      */   private <Tickable extends TickableSystem<ECS_TYPE>> void tickInternal(float dt, SystemType<ECS_TYPE, Tickable> tickingSystemType) {
/* 1912 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1913 */     assertThread();
/*      */     
/* 1915 */     this.registry.getDataUpdateLock().readLock().lock();
/*      */     try {
/* 1917 */       ComponentRegistry.Data<ECS_TYPE> data = this.registry.doDataUpdate();
/*      */ 
/*      */ 
/*      */       
/* 1921 */       BitSet systemIndexes = data.getSystemIndexesForType(tickingSystemType);
/*      */       
/* 1923 */       int systemIndex = -1;
/* 1924 */       while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 1925 */         TickableSystem tickableSystem = data.<TickableSystem>getSystem(systemIndex, tickingSystemType);
/* 1926 */         long start = System.nanoTime();
/* 1927 */         tickableSystem.tick(dt, systemIndex, this);
/* 1928 */         long end = System.nanoTime();
/* 1929 */         this.systemMetrics[systemIndex].add(end, end - start);
/*      */       } 
/*      */     } finally {
/* 1932 */       this.registry.getDataUpdateLock().readLock().unlock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick(ArchetypeTickingSystem<ECS_TYPE> system, float dt, int systemIndex) {
/* 1954 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!"); 
/* 1955 */     assertThread();
/*      */     
/* 1957 */     CommandBuffer<ECS_TYPE> commandBuffer = takeCommandBuffer();
/* 1958 */     this.parallelTask.init();
/*      */     
/* 1960 */     boolean oldDisableProcessingAssert = this.disableProcessingAssert;
/* 1961 */     this.disableProcessingAssert = system instanceof DisableProcessingAssert;
/*      */     
/* 1963 */     this.processing.lock();
/*      */     
/*      */     try {
/* 1966 */       BitSet indexes = this.systemIndexToArchetypeChunkIndexes[systemIndex];
/*      */ 
/*      */       
/* 1969 */       int index = -1;
/* 1970 */       while ((index = indexes.nextSetBit(index + 1)) >= 0) {
/* 1971 */         system.tick(dt, this.archetypeChunks[index], this, commandBuffer);
/*      */       }
/*      */       
/* 1974 */       EntityTickingSystem.SystemTaskData.invokeParallelTask(this.parallelTask, commandBuffer);
/*      */     } finally {
/* 1976 */       this.processing.unlock();
/*      */       
/* 1978 */       this.disableProcessingAssert = oldDisableProcessingAssert;
/*      */     } 
/*      */     
/* 1981 */     commandBuffer.consume();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateData(@Nonnull ComponentRegistry.Data<ECS_TYPE> oldData, @Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 1989 */     if (this.shutdown) throw new IllegalStateException("Store is shutdown!");
/*      */     
/* 1991 */     int resourceSize = data.getResourceSize();
/* 1992 */     this.resources = Arrays.<Resource<ECS_TYPE>>copyOf(this.resources, resourceSize);
/* 1993 */     for (int index = 0; index < this.resources.length; index++) {
/* 1994 */       ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>> resourceType = (ResourceType)data.getResourceType(index);
/* 1995 */       if (this.resources[index] == null && resourceType != null) {
/* 1996 */         this.resources[index] = this.resourceStorage.<Resource<ECS_TYPE>, ECS_TYPE>load(this, data, (ResourceType)resourceType).join();
/* 1997 */       } else if (this.resources[index] != null && resourceType == null) {
/* 1998 */         this.resources[index] = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2003 */     boolean systemChanged = false;
/* 2004 */     for (int i = 0; i < data.getDataChangeCount(); i++) {
/* 2005 */       DataChange dataChange = data.getDataChange(i);
/* 2006 */       systemChanged |= dataChange instanceof SystemChange;
/* 2007 */       updateData(oldData, data, dataChange);
/*      */     } 
/*      */     
/* 2010 */     HistoricMetric[] oldSystemMetrics = this.systemMetrics;
/* 2011 */     this.systemMetrics = new HistoricMetric[data.getSystemSize()];
/*      */     
/* 2013 */     SystemType<ECS_TYPE, TickableSystem<ECS_TYPE>> tickingSystemType = this.registry.getTickableSystemType();
/* 2014 */     BitSet systemIndexes = data.getSystemIndexesForType(tickingSystemType);
/*      */     
/* 2016 */     int systemIndex = -1;
/* 2017 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 2018 */       ISystem<ECS_TYPE> system = data.getSystem(systemIndex);
/* 2019 */       int oldSystemIndex = oldData.indexOf(system);
/* 2020 */       if (oldSystemIndex >= 0) {
/* 2021 */         this.systemMetrics[systemIndex] = oldSystemMetrics[oldSystemIndex];
/*      */         continue;
/*      */       } 
/* 2024 */       this.systemMetrics[systemIndex] = HistoricMetric.builder(33333333L, TimeUnit.NANOSECONDS)
/* 2025 */         .addPeriod(1L, TimeUnit.SECONDS)
/* 2026 */         .addPeriod(1L, TimeUnit.MINUTES)
/* 2027 */         .addPeriod(5L, TimeUnit.MINUTES)
/* 2028 */         .build();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2033 */     if (systemChanged) updateArchetypeIndexes(data); 
/*      */   }
/*      */   
/*      */   private void updateData(@Nonnull ComponentRegistry.Data<ECS_TYPE> oldData, @Nonnull ComponentRegistry.Data<ECS_TYPE> newData, DataChange dataChange) {
/* 2037 */     this.processing.lock();
/*      */     
/*      */     try {
/* 2040 */       updateData0(oldData, newData, dataChange);
/*      */     } finally {
/* 2042 */       this.processing.unlock();
/*      */     } 
/*      */     
/* 2045 */     if (dataChange instanceof SystemChange) {
/*      */       
/* 2047 */       SystemChange<ECS_TYPE> systemChange = (SystemChange<ECS_TYPE>)dataChange;
/* 2048 */       ISystem<ECS_TYPE> system = systemChange.getSystem();
/*      */       
/* 2050 */       switch (systemChange.getType()) {
/*      */         case REGISTERED:
/* 2052 */           if (system instanceof StoreSystem) {
/* 2053 */             ((StoreSystem)system).onSystemAddedToStore(this);
/*      */           }
/*      */           break;
/*      */         case UNREGISTERED:
/* 2057 */           if (system instanceof StoreSystem) {
/* 2058 */             ((StoreSystem)system).onSystemRemovedFromStore(this);
/*      */           }
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateData0(@Nonnull ComponentRegistry.Data<ECS_TYPE> oldData, @Nonnull ComponentRegistry.Data<ECS_TYPE> newData, DataChange dataChange) {
/* 2066 */     if (dataChange instanceof ComponentChange) {
/*      */       String componentId; Codec<Component<ECS_TYPE>> componentCodec; Holder<ECS_TYPE> tempInternalEntityHolder; int oldArchetypeSize, archetypeIndex, highestUsedIndex;
/* 2068 */       ComponentChange<ECS_TYPE, ? extends Component<ECS_TYPE>> componentChange = (ComponentChange<ECS_TYPE, ? extends Component<ECS_TYPE>>)dataChange;
/* 2069 */       ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = componentChange.getComponentType();
/*      */       
/* 2071 */       ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType = this.registry.getUnknownComponentType();
/*      */ 
/*      */ 
/*      */       
/* 2075 */       switch (componentChange.getType()) {
/*      */         case REGISTERED:
/* 2077 */           componentId = newData.getComponentId(componentType);
/*      */           
/* 2079 */           componentCodec = newData.getComponentCodec((ComponentType)componentType);
/*      */ 
/*      */           
/* 2082 */           if (componentCodec != null) {
/* 2083 */             Holder<ECS_TYPE> holder = this.registry._internal_newEntityHolder();
/*      */             
/* 2085 */             int i = this.archetypeSize;
/* 2086 */             for (int j = 0; j < i; j++) {
/* 2087 */               ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[j];
/* 2088 */               if (archetypeChunk != null) {
/*      */                 
/* 2090 */                 Archetype<ECS_TYPE> archetype = archetypeChunk.getArchetype();
/*      */ 
/*      */                 
/* 2093 */                 if (!archetype.contains(componentType))
/*      */                 {
/*      */                   
/* 2096 */                   if (archetype.contains(unknownComponentType)) {
/*      */ 
/*      */                     
/* 2099 */                     Archetype<ECS_TYPE> newArchetype = Archetype.add(archetype, componentType);
/* 2100 */                     int toArchetypeIndex = findOrCreateArchetypeChunk(newArchetype);
/* 2101 */                     ArchetypeChunk<ECS_TYPE> toArchetypeChunk = this.archetypeChunks[toArchetypeIndex];
/*      */                     
/* 2103 */                     archetypeChunk.transferSomeTo(holder, toArchetypeChunk, index -> {
/*      */                           UnknownComponents<ECS_TYPE> unknownComponents = (UnknownComponents<ECS_TYPE>)archetypeChunk.getComponent(index, unknownComponentType);
/*      */                           
/*      */                           assert unknownComponents != null;
/*      */                           
/*      */                           return unknownComponents.contains(componentId);
/*      */                         }entity -> {
/*      */                           UnknownComponents<ECS_TYPE> unknownComponents = (UnknownComponents<ECS_TYPE>)entity.getComponent(unknownComponentType);
/*      */                           
/*      */                           assert unknownComponents != null;
/*      */                           Component<ECS_TYPE> component = unknownComponents.removeComponent(componentId, componentCodec);
/*      */                           entity.addComponent(componentType, component);
/*      */                         }(newChunkEntityRef, ref) -> {
/*      */                           this.entityToArchetypeChunk[ref.getIndex()] = toArchetypeIndex;
/*      */                           this.entityChunkIndex[ref.getIndex()] = newChunkEntityRef;
/*      */                         });
/* 2119 */                     if (archetypeChunk.size() == 0) {
/* 2120 */                       this.archetypeToIndexMap.removeInt(this.archetypeChunks[j].getArchetype());
/* 2121 */                       this.archetypeChunks[j] = null;
/* 2122 */                       for (int systemIndex = 0; systemIndex < oldData.getSystemSize(); systemIndex++) {
/* 2123 */                         this.systemIndexToArchetypeChunkIndexes[systemIndex].clear(j);
/*      */                       }
/* 2125 */                       this.archetypeChunkIndexesToSystemIndex[j].clear();
/* 2126 */                       this.archetypeChunkReuse.set(j);
/*      */                     } 
/*      */                     
/* 2129 */                     if (toArchetypeChunk.size() == 0)
/* 2130 */                     { this.archetypeToIndexMap.removeInt(this.archetypeChunks[toArchetypeIndex].getArchetype());
/* 2131 */                       this.archetypeChunks[toArchetypeIndex] = null;
/* 2132 */                       for (int systemIndex = 0; systemIndex < oldData.getSystemSize(); systemIndex++) {
/* 2133 */                         this.systemIndexToArchetypeChunkIndexes[systemIndex].clear(toArchetypeIndex);
/*      */                       }
/* 2135 */                       this.archetypeChunkIndexesToSystemIndex[toArchetypeIndex].clear();
/* 2136 */                       this.archetypeChunkReuse.set(toArchetypeIndex); } 
/*      */                   }  } 
/*      */               } 
/*      */             } 
/*      */           }  break;
/*      */         case UNREGISTERED:
/* 2142 */           tempInternalEntityHolder = this.registry._internal_newEntityHolder();
/*      */           
/* 2144 */           componentId = oldData.getComponentId(componentType);
/*      */           
/* 2146 */           componentCodec = oldData.getComponentCodec((ComponentType)componentType);
/*      */           
/* 2148 */           oldArchetypeSize = this.archetypeSize;
/* 2149 */           for (archetypeIndex = 0; archetypeIndex < oldArchetypeSize; archetypeIndex++) {
/* 2150 */             ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 2151 */             if (archetypeChunk != null) {
/*      */               
/* 2153 */               Archetype<ECS_TYPE> archetype = archetypeChunk.getArchetype();
/* 2154 */               if (archetype.contains(componentType)) {
/*      */ 
/*      */                 
/* 2157 */                 this.archetypeToIndexMap.removeInt(this.archetypeChunks[archetypeIndex].getArchetype());
/* 2158 */                 this.archetypeChunks[archetypeIndex] = null;
/* 2159 */                 for (int systemIndex = 0; systemIndex < oldData.getSystemSize(); systemIndex++) {
/* 2160 */                   this.systemIndexToArchetypeChunkIndexes[systemIndex].clear(archetypeIndex);
/*      */                 }
/* 2162 */                 this.archetypeChunkIndexesToSystemIndex[archetypeIndex].clear();
/* 2163 */                 this.archetypeChunkReuse.set(archetypeIndex);
/*      */ 
/*      */                 
/* 2166 */                 Archetype<ECS_TYPE> newArchetype = Archetype.remove(archetype, componentType);
/* 2167 */                 if (componentCodec != null && !newArchetype.contains(unknownComponentType)) {
/* 2168 */                   newArchetype = Archetype.add(newArchetype, unknownComponentType);
/*      */                 }
/* 2170 */                 int toArchetypeIndex = findOrCreateArchetypeChunk(newArchetype);
/* 2171 */                 ArchetypeChunk<ECS_TYPE> toArchetypeChunk = this.archetypeChunks[toArchetypeIndex];
/*      */                 
/* 2173 */                 archetypeChunk.transferTo(tempInternalEntityHolder, toArchetypeChunk, entity -> {
/*      */                       if (componentCodec != null) {
/*      */                         UnknownComponents<ECS_TYPE> unknownComponents;
/*      */                         
/*      */                         if (entity.getArchetype().contains(unknownComponentType)) {
/*      */                           unknownComponents = (UnknownComponents<ECS_TYPE>)entity.getComponent(unknownComponentType);
/*      */                           
/*      */                           assert unknownComponents != null;
/*      */                         } else {
/*      */                           unknownComponents = new UnknownComponents();
/*      */                           
/*      */                           entity.addComponent(unknownComponentType, unknownComponents);
/*      */                         } 
/*      */                         
/*      */                         Component<ECS_TYPE> component = (Component<ECS_TYPE>)entity.getComponent(componentType);
/*      */                         unknownComponents.addComponent(componentId, component, componentCodec);
/*      */                       } 
/*      */                       entity.removeComponent(componentType);
/*      */                     }(newChunkEntityRef, ref) -> {
/*      */                       this.entityToArchetypeChunk[ref.getIndex()] = toArchetypeIndex;
/*      */                       this.entityChunkIndex[ref.getIndex()] = newChunkEntityRef;
/*      */                     });
/*      */               } 
/*      */             } 
/*      */           } 
/* 2198 */           highestUsedIndex = this.archetypeChunkReuse.previousClearBit(oldArchetypeSize - 1);
/* 2199 */           this.archetypeSize = highestUsedIndex + 1;
/* 2200 */           this.archetypeChunkReuse.clear(this.archetypeSize, oldArchetypeSize);
/*      */           break;
/*      */       } 
/* 2203 */     } else if (dataChange instanceof SystemChange) {
/*      */       
/* 2205 */       SystemChange<ECS_TYPE> systemChange = (SystemChange<ECS_TYPE>)dataChange;
/* 2206 */       ISystem<ECS_TYPE> system = systemChange.getSystem();
/*      */       
/* 2208 */       switch (systemChange.getType()) {
/*      */         case REGISTERED:
/* 2210 */           if (system instanceof ArchetypeChunkSystem) { ArchetypeChunkSystem<ECS_TYPE> archetypeChunkSystem = (ArchetypeChunkSystem<ECS_TYPE>)system;
/* 2211 */             for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 2212 */               ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 2213 */               if (archetypeChunk != null)
/*      */               {
/*      */                 
/* 2216 */                 if (archetypeChunkSystem.test(this.registry, archetypeChunk.getArchetype()))
/* 2217 */                   archetypeChunkSystem.onSystemAddedToArchetypeChunk(archetypeChunk); 
/*      */               }
/*      */             }  }
/*      */           
/*      */           break;
/*      */         case UNREGISTERED:
/* 2223 */           if (system instanceof ArchetypeChunkSystem) { ArchetypeChunkSystem<ECS_TYPE> archetypeChunkSystem = (ArchetypeChunkSystem<ECS_TYPE>)system;
/* 2224 */             for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 2225 */               ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 2226 */               if (archetypeChunk != null)
/*      */               {
/*      */                 
/* 2229 */                 if (archetypeChunkSystem.test(this.registry, archetypeChunk.getArchetype()))
/* 2230 */                   archetypeChunkSystem.onSystemRemovedFromArchetypeChunk(archetypeChunk); 
/*      */               }
/*      */             }  }
/*      */           
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateArchetypeIndexes(@Nonnull ComponentRegistry.Data<ECS_TYPE> data) {
/* 2240 */     int systemSize = data.getSystemSize();
/*      */ 
/*      */     
/* 2243 */     int oldLength = this.systemIndexToArchetypeChunkIndexes.length;
/* 2244 */     if (oldLength < systemSize) {
/* 2245 */       this.systemIndexToArchetypeChunkIndexes = Arrays.<BitSet>copyOf(this.systemIndexToArchetypeChunkIndexes, systemSize);
/* 2246 */       for (int j = oldLength; j < systemSize; j++) {
/* 2247 */         this.systemIndexToArchetypeChunkIndexes[j] = new BitSet(this.archetypeSize);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2252 */     for (int systemIndex = 0; systemIndex < oldLength; systemIndex++) {
/* 2253 */       this.systemIndexToArchetypeChunkIndexes[systemIndex].clear();
/*      */     }
/*      */ 
/*      */     
/* 2257 */     for (int archetypeIndex = 0; archetypeIndex < this.archetypeSize; archetypeIndex++) {
/* 2258 */       this.archetypeChunkIndexesToSystemIndex[archetypeIndex].clear();
/*      */     }
/*      */ 
/*      */     
/* 2262 */     SystemType<ECS_TYPE, QuerySystem<ECS_TYPE>> entityQuerySystemType = this.registry.getQuerySystemType();
/* 2263 */     BitSet systemIndexes = data.getSystemIndexesForType(entityQuerySystemType);
/*      */     
/* 2265 */     int i = -1;
/* 2266 */     while ((i = systemIndexes.nextSetBit(i + 1)) >= 0) {
/* 2267 */       QuerySystem<ECS_TYPE> system = data.<QuerySystem<ECS_TYPE>>getSystem(i, entityQuerySystemType);
/*      */ 
/*      */       
/* 2270 */       BitSet archetypeChunkIndexes = this.systemIndexToArchetypeChunkIndexes[i];
/*      */       
/* 2272 */       for (int j = 0; j < this.archetypeSize; j++) {
/* 2273 */         ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[j];
/* 2274 */         if (archetypeChunk != null)
/*      */         {
/*      */           
/* 2277 */           if (system.test(this.registry, archetypeChunk.getArchetype())) {
/* 2278 */             archetypeChunkIndexes.set(j);
/* 2279 */             this.archetypeChunkIndexesToSystemIndex[j].set(i);
/*      */           }  } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void assertWriteProcessing() {
/* 2286 */     if (this.processing.isHeld() && !this.disableProcessingAssert) {
/* 2287 */       throw new IllegalStateException("Store is currently processing! Ensure you aren't calling a store method from a system.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isProcessing() {
/* 2297 */     return this.processing.isHeld();
/*      */   }
/*      */   
/*      */   public void assertThread() {
/* 2301 */     Thread currentThread = Thread.currentThread();
/* 2302 */     if (!currentThread.equals(this.thread) && this.thread.isAlive()) {
/* 2303 */       throw new IllegalStateException("Assert not in thread! " + String.valueOf(this.thread) + " but was in " + String.valueOf(currentThread));
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isInThread() {
/* 2308 */     return Thread.currentThread().equals(this.thread);
/*      */   }
/*      */   
/*      */   public boolean isAliveInDifferentThread() {
/* 2312 */     return (this.thread.isAlive() && !Thread.currentThread().equals(this.thread));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/* 2319 */     return "Store{super()=" + String.valueOf(getClass()) + "@" + hashCode() + ", registry=" + 
/* 2320 */       String.valueOf(this.registry.getClass()) + "@" + this.registry.hashCode() + ", shutdown=" + this.shutdown + ", storeIndex=" + this.storeIndex + ", systemIndexToArchetypeChunkIndexes=" + 
/*      */ 
/*      */       
/* 2323 */       Arrays.toString((Object[])this.systemIndexToArchetypeChunkIndexes) + ", archetypeSize=" + this.archetypeSize + ", archetypeChunks=" + 
/*      */       
/* 2325 */       Arrays.toString((Object[])this.archetypeChunks) + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ProcessingCounter
/*      */     implements Lock
/*      */   {
/* 2336 */     private int count = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isHeld() {
/* 2344 */       return (this.count > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void lock() {
/* 2349 */       this.count++;
/*      */     }
/*      */ 
/*      */     
/*      */     public void lockInterruptibly() {
/* 2354 */       throw new UnsupportedOperationException("lockInterruptibly() is not supported");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryLock() {
/* 2359 */       throw new UnsupportedOperationException("tryLock() is not supported");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryLock(long time, @Nonnull TimeUnit unit) {
/* 2364 */       throw new UnsupportedOperationException("tryLock() is not supported");
/*      */     }
/*      */ 
/*      */     
/*      */     public void unlock() {
/* 2369 */       this.count--;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public Condition newCondition() {
/* 2375 */       throw new UnsupportedOperationException("Conditions are not supported");
/*      */     }
/*      */   }
/*      */   
/*      */   private <T extends Component<ECS_TYPE>> void datachunk_addComponent(@Nonnull Ref<ECS_TYPE> ref, int fromArchetypeIndex, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/* 2380 */     int entityIndex = ref.getIndex();
/*      */     
/* 2382 */     ArchetypeChunk<ECS_TYPE> fromArchetypeChunk = this.archetypeChunks[fromArchetypeIndex];
/* 2383 */     int oldChunkEntityRef = this.entityChunkIndex[entityIndex];
/* 2384 */     Holder<ECS_TYPE> holder = this.registry._internal_newEntityHolder();
/* 2385 */     fromArchetypeChunk.removeEntity(oldChunkEntityRef, holder);
/*      */ 
/*      */     
/* 2388 */     holder.addComponent(componentType, component);
/*      */ 
/*      */     
/* 2391 */     int toArchetypeIndex = findOrCreateArchetypeChunk(holder.getArchetype());
/* 2392 */     ArchetypeChunk<ECS_TYPE> toArchetypeChunk = this.archetypeChunks[toArchetypeIndex];
/* 2393 */     int chunkEntityRef = toArchetypeChunk.addEntity(ref, holder);
/*      */     
/* 2395 */     this.entityToArchetypeChunk[entityIndex] = toArchetypeIndex;
/* 2396 */     this.entityChunkIndex[entityIndex] = chunkEntityRef;
/*      */ 
/*      */     
/* 2399 */     BitSet entityProcessedBySystemIndexes = this.archetypeChunkIndexesToSystemIndex[toArchetypeIndex];
/*      */     
/* 2401 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 2402 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getRefChangeSystemType());
/*      */     
/* 2404 */     int systemIndex = -1;
/* 2405 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 2406 */       if (entityProcessedBySystemIndexes.get(systemIndex)) {
/* 2407 */         RefChangeSystem<ECS_TYPE, T> system = (RefChangeSystem)data.getSystem(systemIndex);
/* 2408 */         if (system.componentType().getIndex() == componentType.getIndex()) {
/* 2409 */           system.onComponentAdded(ref, (Component)component, this, commandBuffer);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2414 */     if (fromArchetypeChunk.size() == 0) removeArchetypeChunk(fromArchetypeIndex); 
/*      */   }
/*      */   
/*      */   private int findOrCreateArchetypeChunk(@Nonnull Archetype<ECS_TYPE> archetype) {
/* 2418 */     int archetypeIndex = this.archetypeToIndexMap.getInt(archetype);
/* 2419 */     if (archetypeIndex != Integer.MIN_VALUE) return archetypeIndex;
/*      */     
/* 2421 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/*      */ 
/*      */     
/* 2424 */     if (this.archetypeChunkReuse.isEmpty()) {
/* 2425 */       archetypeIndex = this.archetypeSize++;
/*      */     } else {
/* 2427 */       archetypeIndex = this.archetypeChunkReuse.nextSetBit(0);
/* 2428 */       this.archetypeChunkReuse.clear(archetypeIndex);
/*      */     } 
/*      */     
/* 2431 */     int oldLength = this.archetypeChunks.length;
/* 2432 */     if (oldLength <= archetypeIndex) {
/* 2433 */       int newLength = ArrayUtil.grow(archetypeIndex);
/* 2434 */       this.archetypeChunks = Arrays.<ArchetypeChunk<ECS_TYPE>>copyOf(this.archetypeChunks, newLength);
/* 2435 */       this.archetypeChunkIndexesToSystemIndex = Arrays.<BitSet>copyOf(this.archetypeChunkIndexesToSystemIndex, newLength);
/*      */       
/* 2437 */       int systemSize = data.getSystemSize();
/* 2438 */       for (int i = oldLength; i < newLength; i++) {
/* 2439 */         this.archetypeChunkIndexesToSystemIndex[i] = new BitSet(systemSize);
/*      */       }
/*      */     } 
/*      */     
/* 2443 */     ArchetypeChunk<ECS_TYPE> archetypeChunk = new ArchetypeChunk<>(this, archetype);
/* 2444 */     this.archetypeChunks[archetypeIndex] = archetypeChunk;
/* 2445 */     this.archetypeToIndexMap.put(archetype, archetypeIndex);
/*      */     
/* 2447 */     BitSet archetypeChunkToSystemIndex = this.archetypeChunkIndexesToSystemIndex[archetypeIndex];
/*      */ 
/*      */     
/* 2450 */     SystemType<ECS_TYPE, QuerySystem<ECS_TYPE>> entityQuerySystemType = this.registry.getQuerySystemType();
/* 2451 */     BitSet systemIndexes = data.getSystemIndexesForType(entityQuerySystemType);
/*      */     
/* 2453 */     int systemIndex = -1;
/* 2454 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/*      */ 
/*      */       
/* 2457 */       QuerySystem<ECS_TYPE> system = data.<QuerySystem<ECS_TYPE>>getSystem(systemIndex, entityQuerySystemType);
/*      */ 
/*      */       
/* 2460 */       if (system.test(this.registry, archetype)) {
/* 2461 */         this.systemIndexToArchetypeChunkIndexes[systemIndex].set(archetypeIndex);
/* 2462 */         archetypeChunkToSystemIndex.set(systemIndex);
/*      */ 
/*      */         
/* 2465 */         if (system instanceof ArchetypeChunkSystem) {
/* 2466 */           ((ArchetypeChunkSystem)system).onSystemAddedToArchetypeChunk(archetypeChunk);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2471 */     return archetypeIndex;
/*      */   }
/*      */   
/*      */   private void removeArchetypeChunk(int archetypeIndex) {
/* 2475 */     ArchetypeChunk<ECS_TYPE> archetypeChunk = this.archetypeChunks[archetypeIndex];
/* 2476 */     Archetype<ECS_TYPE> archetype = archetypeChunk.getArchetype();
/* 2477 */     this.archetypeToIndexMap.removeInt(archetype);
/* 2478 */     this.archetypeChunks[archetypeIndex] = null;
/* 2479 */     this.archetypeChunkIndexesToSystemIndex[archetypeIndex].clear();
/*      */     
/* 2481 */     ComponentRegistry.Data<ECS_TYPE> data = this.registry._internal_getData();
/* 2482 */     BitSet systemIndexes = data.getSystemIndexesForType((SystemType)this.registry.getQuerySystemType());
/*      */     
/* 2484 */     int systemIndex = -1;
/* 2485 */     while ((systemIndex = systemIndexes.nextSetBit(systemIndex + 1)) >= 0) {
/* 2486 */       this.systemIndexToArchetypeChunkIndexes[systemIndex].clear(archetypeIndex);
/*      */       
/* 2488 */       ISystem<ECS_TYPE> system = data.getSystem(systemIndex);
/* 2489 */       if (system instanceof ArchetypeChunkSystem) { ArchetypeChunkSystem<ECS_TYPE> archetypeChunkSystem = (ArchetypeChunkSystem<ECS_TYPE>)system;
/*      */ 
/*      */         
/* 2492 */         if (archetypeChunkSystem.test(this.registry, archetype))
/*      */         {
/* 2494 */           archetypeChunkSystem.onSystemRemovedFromArchetypeChunk(archetypeChunk);
/*      */         } }
/*      */     
/*      */     } 
/*      */ 
/*      */     
/* 2500 */     if (archetypeIndex == this.archetypeSize - 1) {
/* 2501 */       int highestUsedIndex = this.archetypeChunkReuse.previousClearBit(archetypeIndex - 1);
/* 2502 */       this.archetypeSize = highestUsedIndex + 1;
/* 2503 */       this.archetypeChunkReuse.clear(this.archetypeSize, archetypeIndex);
/*      */     } else {
/* 2505 */       this.archetypeChunkReuse.set(archetypeIndex);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\Store.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */