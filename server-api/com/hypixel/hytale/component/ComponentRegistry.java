/*      */ package com.hypixel.hytale.component;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.ExtraInfo;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.common.util.ArrayUtil;
/*      */ import com.hypixel.hytale.component.data.change.ChangeType;
/*      */ import com.hypixel.hytale.component.data.change.ComponentChange;
/*      */ import com.hypixel.hytale.component.data.change.DataChange;
/*      */ import com.hypixel.hytale.component.data.change.ResourceChange;
/*      */ import com.hypixel.hytale.component.data.change.SystemChange;
/*      */ import com.hypixel.hytale.component.data.change.SystemTypeChange;
/*      */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*      */ import com.hypixel.hytale.component.dependency.Dependency;
/*      */ import com.hypixel.hytale.component.event.EntityEventType;
/*      */ import com.hypixel.hytale.component.event.WorldEventType;
/*      */ import com.hypixel.hytale.component.query.Query;
/*      */ import com.hypixel.hytale.component.query.ReadWriteArchetypeQuery;
/*      */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*      */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*      */ import com.hypixel.hytale.component.system.EcsEvent;
/*      */ import com.hypixel.hytale.component.system.EntityEventSystem;
/*      */ import com.hypixel.hytale.component.system.HolderSystem;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import com.hypixel.hytale.component.system.QuerySystem;
/*      */ import com.hypixel.hytale.component.system.RefChangeSystem;
/*      */ import com.hypixel.hytale.component.system.RefSystem;
/*      */ import com.hypixel.hytale.component.system.System;
/*      */ import com.hypixel.hytale.component.system.WorldEventSystem;
/*      */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*      */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*      */ import com.hypixel.hytale.component.system.tick.TickableSystem;
/*      */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.ReadWriteLock;
/*      */ import java.util.concurrent.locks.StampedLock;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import org.bson.BsonDocument;
/*      */ 
/*      */ public class ComponentRegistry<ECS_TYPE> implements IComponentRegistry<ECS_TYPE> {
/*   59 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass(); public static final int UNASSIGNED_INDEX = -2147483648;
/*      */   public static final int DEFAULT_INITIAL_SIZE = 16;
/*      */   @Deprecated
/*   62 */   private static final KeyedCodec<Integer> VERSION = new KeyedCodec("Version", (Codec)Codec.INTEGER);
/*      */   
/*   64 */   private static final AtomicInteger REFERENCE_THREAD_COUNTER = new AtomicInteger();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shutdown;
/*      */ 
/*      */   
/*   71 */   private final StampedLock dataLock = new StampedLock();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*   76 */   private final Object2IntMap<String> componentIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(16);
/*      */ 
/*      */   
/*   79 */   private final BitSet componentIndexReuse = new BitSet();
/*      */ 
/*      */   
/*      */   private int componentSize;
/*      */ 
/*      */   
/*      */   @Nonnull
/*   86 */   private String[] componentIds = new String[16];
/*      */   @Nonnull
/*   88 */   private BuilderCodec<? extends Component<ECS_TYPE>>[] componentCodecs = (BuilderCodec<? extends Component<ECS_TYPE>>[])new BuilderCodec[16];
/*      */   
/*      */   @Nonnull
/*   91 */   private Supplier<? extends Component<ECS_TYPE>>[] componentSuppliers = (Supplier<? extends Component<ECS_TYPE>>[])new Supplier[16];
/*      */   
/*      */   @Nonnull
/*   94 */   private ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[] componentTypes = (ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[])new ComponentType[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  101 */   private final Object2IntMap<String> resourceIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(16);
/*      */ 
/*      */   
/*  104 */   private final BitSet resourceIndexReuse = new BitSet();
/*      */ 
/*      */   
/*      */   private int resourceSize;
/*      */ 
/*      */   
/*      */   @Nonnull
/*  111 */   private String[] resourceIds = new String[16];
/*      */   @Nonnull
/*  113 */   private BuilderCodec<? extends Resource<ECS_TYPE>>[] resourceCodecs = (BuilderCodec<? extends Resource<ECS_TYPE>>[])new BuilderCodec[16];
/*      */   
/*      */   @Nonnull
/*  116 */   private Supplier<? extends Resource<ECS_TYPE>>[] resourceSuppliers = (Supplier<? extends Resource<ECS_TYPE>>[])new Supplier[16];
/*      */   
/*      */   @Nonnull
/*  119 */   private ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[] resourceTypes = (ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[])new ResourceType[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  126 */   private final Object2IntMap<Class<? extends ISystem<ECS_TYPE>>> systemTypeClassToIndex = (Object2IntMap<Class<? extends ISystem<ECS_TYPE>>>)new Object2IntOpenHashMap(16);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  132 */   private final Object2IntMap<Class<? extends EcsEvent>> entityEventTypeClassToIndex = (Object2IntMap<Class<? extends EcsEvent>>)new Object2IntOpenHashMap(16);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  138 */   private final Object2IntMap<Class<? extends EcsEvent>> worldEventTypeClassToIndex = (Object2IntMap<Class<? extends EcsEvent>>)new Object2IntOpenHashMap(16);
/*      */ 
/*      */   
/*  141 */   private final BitSet systemTypeIndexReuse = new BitSet();
/*      */ 
/*      */   
/*      */   private int systemTypeSize;
/*      */ 
/*      */   
/*      */   @Nonnull
/*  148 */   private SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[] systemTypes = (SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[])new SystemType[16];
/*      */ 
/*      */   
/*  151 */   private BitSet[] systemTypeToSystemIndex = new BitSet[16];
/*      */   
/*  153 */   private final BitSet systemGroupIndexReuse = new BitSet(); private int systemGroupSize;
/*      */   @Nonnull
/*  155 */   private SystemGroup<ECS_TYPE>[] systemGroups = (SystemGroup<ECS_TYPE>[])new SystemGroup[16];
/*      */ 
/*      */ 
/*      */   
/*      */   private int systemSize;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  164 */   private ISystem<ECS_TYPE>[] systems = (ISystem<ECS_TYPE>[])new ISystem[16];
/*      */   
/*      */   @Nonnull
/*  167 */   private ISystem<ECS_TYPE>[] sortedSystems = (ISystem<ECS_TYPE>[])new ISystem[16];
/*      */   
/*      */   @Nonnull
/*  170 */   private final Object2IntMap<Class<? extends ISystem<ECS_TYPE>>> systemClasses = (Object2IntMap<Class<? extends ISystem<ECS_TYPE>>>)new Object2IntOpenHashMap(16);
/*      */   @Nonnull
/*  172 */   private final Object2BooleanMap<Class<? extends ISystem<ECS_TYPE>>> systemBypassClassCheck = (Object2BooleanMap<Class<? extends ISystem<ECS_TYPE>>>)new Object2BooleanOpenHashMap(16);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  178 */   private final StampedLock storeLock = new StampedLock();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int storeSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  189 */   private Store<ECS_TYPE>[] stores = (Store<ECS_TYPE>[])new Store[16];
/*      */ 
/*      */ 
/*      */   
/*  193 */   private final ReadWriteLock dataUpdateLock = new ReentrantReadWriteLock();
/*      */ 
/*      */ 
/*      */   
/*      */   private Data<ECS_TYPE> data;
/*      */ 
/*      */   
/*  200 */   private final Set<Reference<Holder<ECS_TYPE>>> holders = ConcurrentHashMap.newKeySet();
/*  201 */   private final ReferenceQueue<Holder<ECS_TYPE>> holderReferenceQueue = new ReferenceQueue<>();
/*      */   
/*      */   @Nonnull
/*      */   private final Thread holderReferenceThread;
/*      */   
/*      */   @Nonnull
/*      */   private final ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> unknownComponentType;
/*      */   
/*      */   @Nonnull
/*      */   private final ComponentType<ECS_TYPE, NonTicking<ECS_TYPE>> nonTickingComponentType;
/*      */   
/*      */   @Nonnull
/*      */   private final ComponentType<ECS_TYPE, NonSerialized<ECS_TYPE>> nonSerializedComponentType;
/*      */   
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> holderSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> refSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, RefChangeSystem<ECS_TYPE, ?>> refChangeSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, QuerySystem<ECS_TYPE>> querySystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, TickingSystem<ECS_TYPE>> tickingSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, TickableSystem<ECS_TYPE>> tickableSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, RunWhenPausedSystem<ECS_TYPE>> runWhenPausedSystemType;
/*      */   @Nonnull
/*      */   private final SystemType<ECS_TYPE, ArchetypeTickingSystem<ECS_TYPE>> archetypeTickingSystemType;
/*      */   
/*      */   public ComponentRegistry() {
/*  233 */     this.componentIdToIndex.defaultReturnValue(-2147483648);
/*  234 */     this.resourceIdToIndex.defaultReturnValue(-2147483648);
/*  235 */     this.systemTypeClassToIndex.defaultReturnValue(-2147483648);
/*  236 */     this.entityEventTypeClassToIndex.defaultReturnValue(-2147483648);
/*  237 */     this.worldEventTypeClassToIndex.defaultReturnValue(-2147483648);
/*      */     
/*  239 */     for (int i = 0; i < 16; i++) {
/*  240 */       this.systemTypeToSystemIndex[i] = new BitSet();
/*      */     }
/*      */     
/*  243 */     this.data = new Data<>(this);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     this.unknownComponentType = registerComponent((Class)UnknownComponents.class, "Unknown", UnknownComponents.CODEC);
/*  249 */     this.nonTickingComponentType = registerComponent((Class)NonTicking.class, NonTicking::get);
/*  250 */     this.nonSerializedComponentType = registerComponent((Class)NonSerialized.class, NonSerialized::get);
/*      */     
/*  252 */     this.holderSystemType = registerSystemType((Class)HolderSystem.class);
/*  253 */     this.refSystemType = registerSystemType((Class)RefSystem.class);
/*  254 */     this.refChangeSystemType = registerSystemType((Class)RefChangeSystem.class);
/*  255 */     this.querySystemType = registerSystemType((Class)QuerySystem.class);
/*  256 */     this.tickingSystemType = registerSystemType((Class)TickingSystem.class);
/*  257 */     this.tickableSystemType = registerSystemType((Class)TickableSystem.class);
/*  258 */     this.runWhenPausedSystemType = registerSystemType((Class)RunWhenPausedSystem.class);
/*  259 */     this.archetypeTickingSystemType = registerSystemType((Class)ArchetypeTickingSystem.class);
/*      */ 
/*      */     
/*  262 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  268 */       .holderReferenceThread = new Thread(() -> { try { while (!Thread.interrupted()) this.holders.remove(this.holderReferenceQueue.remove());  } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }  }"EntityHolderReferenceThread-" + REFERENCE_THREAD_COUNTER.getAndIncrement());
/*  269 */     this.holderReferenceThread.setDaemon(true);
/*  270 */     this.holderReferenceThread.start();
/*      */   }
/*      */   
/*      */   public boolean isShutdown() {
/*  274 */     return this.shutdown;
/*      */   }
/*      */   
/*      */   public void shutdown() {
/*  278 */     shutdown0();
/*      */   }
/*      */   
/*      */   void shutdown0() {
/*  282 */     this.shutdown = true;
/*      */     
/*  284 */     this.holderReferenceThread.interrupt();
/*      */     
/*  286 */     long lock = this.storeLock.writeLock();
/*      */     try {
/*  288 */       for (int storeIndex = this.storeSize - 1; storeIndex >= 0; storeIndex--) {
/*  289 */         Store<ECS_TYPE> store = this.stores[storeIndex];
/*  290 */         if (store != null) store.shutdown0(this.data);
/*      */       
/*      */       } 
/*  293 */       this.stores = (Store<ECS_TYPE>[])Store.EMPTY_ARRAY;
/*      */     } finally {
/*  295 */       this.storeLock.unlockWrite(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ReadWriteLock getDataUpdateLock() {
/*  301 */     return this.dataUpdateLock;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ComponentType<ECS_TYPE, UnknownComponents<ECS_TYPE>> getUnknownComponentType() {
/*  306 */     return this.unknownComponentType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ComponentType<ECS_TYPE, NonTicking<ECS_TYPE>> getNonTickingComponentType() {
/*  311 */     return this.nonTickingComponentType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ComponentType<ECS_TYPE, NonSerialized<ECS_TYPE>> getNonSerializedComponentType() {
/*  316 */     return this.nonSerializedComponentType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, HolderSystem<ECS_TYPE>> getHolderSystemType() {
/*  321 */     return this.holderSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, RefSystem<ECS_TYPE>> getRefSystemType() {
/*  326 */     return this.refSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, RefChangeSystem<ECS_TYPE, ?>> getRefChangeSystemType() {
/*  331 */     return this.refChangeSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, QuerySystem<ECS_TYPE>> getQuerySystemType() {
/*  336 */     return this.querySystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, TickingSystem<ECS_TYPE>> getTickingSystemType() {
/*  341 */     return this.tickingSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, TickableSystem<ECS_TYPE>> getTickableSystemType() {
/*  346 */     return this.tickableSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, RunWhenPausedSystem<ECS_TYPE>> getRunWhenPausedSystemType() {
/*  351 */     return this.runWhenPausedSystemType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemType<ECS_TYPE, ArchetypeTickingSystem<ECS_TYPE>> getArchetypeTickingSystemType() {
/*  356 */     return this.archetypeTickingSystemType;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  362 */     return registerComponent(tClass, null, null, supplier, false);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/*  368 */     Objects.requireNonNull(codec); return registerComponent(tClass, id, codec, codec::getDefaultValue, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Nonnull
/*      */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec, boolean skipValidation) {
/*  379 */     Objects.requireNonNull(codec); return registerComponent(tClass, id, codec, codec::getDefaultValue, skipValidation);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier, boolean skipValidation) {
/*  384 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  386 */     if (codec != null && !skipValidation) {
/*  387 */       ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  388 */       codec.validateDefaults(extraInfo, new HashSet());
/*  389 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER, "Default Asset Validation Failed!\n");
/*      */     } 
/*      */     
/*  392 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  394 */       ComponentType<ECS_TYPE, T> componentType = registerComponent0(tClass, id, codec, supplier, new ComponentType<>());
/*      */       
/*  396 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  398 */       updateData0(new DataChange[] { (DataChange)new ComponentChange(ChangeType.REGISTERED, componentType) });
/*      */       
/*  400 */       return componentType;
/*      */     } finally {
/*  402 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> void unregisterComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/*  407 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  408 */     componentType.validateRegistry(this);
/*  409 */     componentType.validate();
/*  410 */     if (componentType.equals(this.unknownComponentType)) throw new IllegalArgumentException("UnknownComponentType can not be unregistered!");
/*      */     
/*  412 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  414 */       unregisterComponent0(componentType);
/*      */       
/*  416 */       ObjectArrayList<ComponentChange> objectArrayList = new ObjectArrayList();
/*  417 */       objectArrayList.add(new ComponentChange(ChangeType.UNREGISTERED, componentType));
/*      */ 
/*      */       
/*  420 */       for (int unsortedSystemIndex = this.systemSize - 1; unsortedSystemIndex >= 0; unsortedSystemIndex--) {
/*  421 */         ISystem<ECS_TYPE> system = this.systems[unsortedSystemIndex];
/*  422 */         if (system instanceof QuerySystem) { QuerySystem<ECS_TYPE> archetypeSystem = (QuerySystem<ECS_TYPE>)system;
/*      */           
/*  424 */           Query<ECS_TYPE> query = archetypeSystem.getQuery();
/*  425 */           if (query != null && query.requiresComponentType(componentType)) {
/*  426 */             unregisterSystem0(unsortedSystemIndex, system);
/*  427 */             objectArrayList.add(new SystemChange(ChangeType.UNREGISTERED, system));
/*      */           }  }
/*      */       
/*      */       } 
/*  431 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  433 */       updateData0((DataChange[])objectArrayList.toArray(x$0 -> new DataChange[x$0]));
/*      */       
/*  435 */       componentType.invalidate();
/*      */     } finally {
/*  437 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  444 */     return registerResource(tClass, null, null, supplier);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/*  450 */     Objects.requireNonNull(codec); return registerResource(tClass, id, codec, codec::getDefaultValue);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier) {
/*  455 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  457 */     if (codec != null) {
/*  458 */       ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  459 */       codec.validateDefaults(extraInfo, new HashSet());
/*  460 */       extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER, "Default Asset Validation Failed!\n");
/*      */     } 
/*      */     
/*  463 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  465 */       ResourceType<ECS_TYPE, T> resourceType = registerResource0(tClass, id, codec, supplier, new ResourceType<>());
/*      */       
/*  467 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  469 */       updateData0(new DataChange[] { (DataChange)new ResourceChange(ChangeType.REGISTERED, resourceType) });
/*      */       
/*  471 */       return resourceType;
/*      */     } finally {
/*  473 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends Resource<ECS_TYPE>> void unregisterResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/*  478 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  479 */     resourceType.validateRegistry(this);
/*  480 */     resourceType.validate();
/*      */     
/*  482 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  484 */       unregisterResource0(resourceType);
/*      */       
/*  486 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  488 */       updateData0(new DataChange[] { (DataChange)new ResourceChange(ChangeType.UNREGISTERED, resourceType) });
/*      */       
/*  490 */       resourceType.invalidate();
/*      */     } finally {
/*  492 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> registerSystemType(@Nonnull Class<? super T> systemTypeClass) {
/*  499 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  501 */     if (!ISystem.class.isAssignableFrom(systemTypeClass)) {
/*  502 */       throw new IllegalArgumentException("systemTypeClass must extend ComponentSystem! " + String.valueOf(systemTypeClass));
/*      */     }
/*      */     
/*  505 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  507 */       SystemType<ECS_TYPE, T> systemType = registerSystemType0(systemTypeClass);
/*      */       
/*  509 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  511 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.REGISTERED, systemType) });
/*      */       
/*  513 */       return systemType;
/*      */     } finally {
/*  515 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends ISystem<ECS_TYPE>> void unregisterSystemType(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/*  520 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  521 */     systemType.validate();
/*      */     
/*  523 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  525 */       unregisterSystemType0(systemType);
/*      */       
/*  527 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  529 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.UNREGISTERED, systemType) });
/*      */       
/*  531 */       systemType.invalidate();
/*      */     } finally {
/*  533 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends EcsEvent> EntityEventType<ECS_TYPE, T> registerEntityEventType(@Nonnull Class<? super T> eventTypeClass) {
/*  540 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  542 */     if (!EcsEvent.class.isAssignableFrom(eventTypeClass)) {
/*  543 */       throw new IllegalArgumentException("eventTypeClass must extend EcsEvent! " + String.valueOf(eventTypeClass));
/*      */     }
/*      */     
/*  546 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  548 */       EntityEventType<ECS_TYPE, T> systemType = registerEntityEventType0(eventTypeClass);
/*      */       
/*  550 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  552 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.REGISTERED, (SystemType)systemType) });
/*      */       
/*  554 */       return systemType;
/*      */     } finally {
/*  556 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public <T extends EcsEvent> WorldEventType<ECS_TYPE, T> registerWorldEventType(@Nonnull Class<? super T> eventTypeClass) {
/*  563 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  565 */     if (!EcsEvent.class.isAssignableFrom(eventTypeClass)) {
/*  566 */       throw new IllegalArgumentException("eventTypeClass must extend EcsEvent! " + String.valueOf(eventTypeClass));
/*      */     }
/*      */     
/*  569 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  571 */       WorldEventType<ECS_TYPE, T> systemType = registerWorldEventType0(eventTypeClass);
/*      */       
/*  573 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  575 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.REGISTERED, (SystemType)systemType) });
/*      */       
/*  577 */       return systemType;
/*      */     } finally {
/*  579 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends EcsEvent> void unregisterEntityEventType(@Nonnull EntityEventType<ECS_TYPE, T> eventType) {
/*  584 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  585 */     eventType.validate();
/*      */     
/*  587 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  589 */       unregisterEntityEventType0(eventType);
/*      */       
/*  591 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  593 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.UNREGISTERED, (SystemType)eventType) });
/*      */       
/*  595 */       eventType.invalidate();
/*      */     } finally {
/*  597 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends EcsEvent> void unregisterWorldEventType(@Nonnull WorldEventType<ECS_TYPE, T> eventType) {
/*  602 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  603 */     eventType.validate();
/*      */     
/*  605 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  607 */       unregisterWorldEventType0(eventType);
/*      */       
/*  609 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  611 */       updateData0(new DataChange[] { (DataChange)new SystemTypeChange(ChangeType.UNREGISTERED, (SystemType)eventType) });
/*      */       
/*  613 */       eventType.invalidate();
/*      */     } finally {
/*  615 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public SystemGroup<ECS_TYPE> registerSystemGroup() {
/*  622 */     return registerSystemGroup(Collections.emptySet());
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SystemGroup<ECS_TYPE> registerSystemGroup(Set<Dependency<ECS_TYPE>> dependencies) {
/*  627 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown");
/*      */     
/*  629 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  631 */       SystemGroup<ECS_TYPE> systemGroup = registerSystemGroup0(dependencies);
/*      */       
/*  633 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  635 */       updateData0(new DataChange[] { (DataChange)new SystemGroupChange(ChangeType.REGISTERED, systemGroup) });
/*      */       
/*  637 */       return systemGroup;
/*      */     } finally {
/*  639 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void unregisterSystemGroup(@Nonnull SystemGroup<ECS_TYPE> systemGroup) {
/*  644 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  645 */     systemGroup.validate();
/*      */     
/*  647 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  649 */       unregisterSystemGroup0(systemGroup);
/*      */       
/*  651 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  653 */       updateData0(new DataChange[] { (DataChange)new SystemGroupChange(ChangeType.UNREGISTERED, systemGroup) });
/*      */       
/*  655 */       systemGroup.invalidate();
/*      */     } finally {
/*  657 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void registerSystem(@Nonnull ISystem<ECS_TYPE> system) {
/*  663 */     registerSystem(system, false);
/*      */   }
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   public void registerSystem(@Nonnull ISystem<ECS_TYPE> system, boolean bypassClassCheck) {
/*  668 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  669 */     Class<? extends ISystem> systemClass = system.getClass();
/*      */ 
/*      */     
/*  672 */     if (system instanceof QuerySystem) { QuerySystem<ECS_TYPE> archetypeSystem = (QuerySystem<ECS_TYPE>)system;
/*  673 */       Query<ECS_TYPE> query = archetypeSystem.getQuery();
/*  674 */       query.validateRegistry(this);
/*  675 */       query.validate();
/*      */       
/*  677 */       if (query instanceof ReadWriteArchetypeQuery) { ReadWriteArchetypeQuery<ECS_TYPE> readWriteQuery = (ReadWriteArchetypeQuery<ECS_TYPE>)query;
/*  678 */         if (readWriteQuery.getReadArchetype().equals(readWriteQuery.getWriteArchetype())) {
/*  679 */           LOGGER.at(Level.WARNING).log("%s.getQuery() is using ReadWriteArchetypeEntityQuery with the same `Read` and `Modified` Archetype! This can be simplified by using the Archetype directly as the EntityQuery.", systemClass
/*  680 */               .getName());
/*      */         } }
/*      */        }
/*      */ 
/*      */     
/*  685 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  687 */       if (!bypassClassCheck) {
/*  688 */         if (this.systemClasses.containsKey(systemClass)) {
/*  689 */           throw new IllegalArgumentException("System of type " + systemClass.getName() + " is already registered!");
/*      */         }
/*      */       } else {
/*  692 */         this.systemBypassClassCheck.put(systemClass, true);
/*      */       } 
/*  694 */       if (ArrayUtil.indexOf((Object[])this.systems, system) != -1) throw new IllegalArgumentException("System is already registered!");
/*      */       
/*  696 */       for (Dependency<ECS_TYPE> dependency : (Iterable<Dependency<ECS_TYPE>>)system.getDependencies()) {
/*  697 */         dependency.validate(this);
/*      */       }
/*      */       
/*  700 */       registerSystem0(system);
/*      */       
/*  702 */       ObjectArrayList<SystemChange> objectArrayList = new ObjectArrayList();
/*  703 */       objectArrayList.add(new SystemChange(ChangeType.REGISTERED, system));
/*      */       
/*  705 */       if (system instanceof System) { System<ECS_TYPE> theSystem = (System<ECS_TYPE>)system;
/*  706 */         for (ComponentRegistration<ECS_TYPE, ?> componentRegistration : (Iterable<ComponentRegistration<ECS_TYPE, ?>>)theSystem.getComponentRegistrations()) {
/*  707 */           ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>> componentType = (ComponentType)registerComponent0(componentRegistration);
/*  708 */           objectArrayList.add(new ComponentChange(ChangeType.REGISTERED, componentType));
/*      */         } 
/*  710 */         for (ResourceRegistration<ECS_TYPE, ?> resourceRegistration : (Iterable<ResourceRegistration<ECS_TYPE, ?>>)theSystem.getResourceRegistrations()) {
/*  711 */           ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>> resourceType = (ResourceType)registerResource0(resourceRegistration);
/*  712 */           objectArrayList.add(new ResourceChange(ChangeType.REGISTERED, resourceType));
/*      */         }  }
/*      */ 
/*      */       
/*  716 */       if (system instanceof EntityEventSystem) { EntityEventSystem<ECS_TYPE, ?> eventSystem = (EntityEventSystem)system;
/*  717 */         if (!this.entityEventTypeClassToIndex.containsKey(eventSystem.getEventType())) {
/*  718 */           EntityEventType<ECS_TYPE, ?> eventType = registerEntityEventType0(eventSystem.getEventType());
/*  719 */           objectArrayList.add(new SystemTypeChange(ChangeType.REGISTERED, (SystemType)eventType));
/*      */         }  }
/*      */ 
/*      */       
/*  723 */       if (system instanceof WorldEventSystem) { WorldEventSystem<ECS_TYPE, ?> eventSystem = (WorldEventSystem)system;
/*  724 */         if (!this.worldEventTypeClassToIndex.containsKey(eventSystem.getEventType())) {
/*  725 */           WorldEventType<ECS_TYPE, ?> eventType = registerWorldEventType0(eventSystem.getEventType());
/*  726 */           objectArrayList.add(new SystemTypeChange(ChangeType.REGISTERED, (SystemType)eventType));
/*      */         }  }
/*      */ 
/*      */       
/*  730 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  732 */       updateData0((DataChange[])objectArrayList.toArray(x$0 -> new DataChange[x$0]));
/*      */     } finally {
/*  734 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void unregisterSystem(@Nonnull Class<? extends ISystem<ECS_TYPE>> systemClass) {
/*  739 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  740 */     long lock = this.dataLock.writeLock();
/*      */     try {
/*  742 */       int systemIndex = this.systemClasses.getInt(systemClass);
/*  743 */       ISystem<ECS_TYPE> system = this.systems[systemIndex];
/*      */       
/*  745 */       if (system == null) {
/*      */         return;
/*      */       }
/*  748 */       if (system instanceof QuerySystem) { QuerySystem<ECS_TYPE> archetypeSystem = (QuerySystem<ECS_TYPE>)system;
/*  749 */         Query<ECS_TYPE> query = archetypeSystem.getQuery();
/*  750 */         query.validateRegistry(this);
/*  751 */         query.validate(); }
/*      */ 
/*      */       
/*  754 */       int unsortedSystemIndex = ArrayUtil.indexOf((Object[])this.systems, system);
/*  755 */       if (unsortedSystemIndex == -1) throw new IllegalArgumentException("System is not registered!");
/*      */       
/*  757 */       unregisterSystem0(unsortedSystemIndex, system);
/*      */       
/*  759 */       ObjectArrayList<SystemChange> objectArrayList = new ObjectArrayList();
/*  760 */       objectArrayList.add(new SystemChange(ChangeType.UNREGISTERED, system));
/*      */       
/*  762 */       if (system instanceof System) { System<ECS_TYPE> theSystem = (System<ECS_TYPE>)system;
/*  763 */         for (ComponentRegistration<ECS_TYPE, ?> systemComponent : (Iterable<ComponentRegistration<ECS_TYPE, ?>>)theSystem.getComponentRegistrations()) {
/*  764 */           unregisterComponent0((ComponentType)systemComponent.componentType());
/*  765 */           objectArrayList.add(new ComponentChange(ChangeType.UNREGISTERED, systemComponent.componentType()));
/*      */         } 
/*  767 */         for (ResourceRegistration<ECS_TYPE, ?> systemResource : (Iterable<ResourceRegistration<ECS_TYPE, ?>>)theSystem.getResourceRegistrations()) {
/*  768 */           unregisterResource0((ResourceType)systemResource.resourceType());
/*  769 */           objectArrayList.add(new ResourceChange(ChangeType.UNREGISTERED, systemResource.resourceType()));
/*      */         }  }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  775 */       lock = this.dataLock.tryConvertToReadLock(lock);
/*      */       
/*  777 */       updateData0((DataChange[])objectArrayList.toArray(x$0 -> new DataChange[x$0]));
/*      */     } finally {
/*  779 */       this.dataLock.unlock(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ResourceType<ECS_TYPE, SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>> registerSpatialResource(@Nonnull Supplier<SpatialStructure<Ref<ECS_TYPE>>> supplier) {
/*  787 */     return registerResource((Class)SpatialResource.class, () -> new SpatialResource(supplier.get()));
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Store<ECS_TYPE> addStore(@Nonnull ECS_TYPE externalData, @Nonnull IResourceStorage resourceStorage) {
/*  792 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  793 */     return addStore0(externalData, resourceStorage, _store -> {
/*      */         
/*      */         });
/*      */   }
/*      */   @Nonnull
/*      */   public Store<ECS_TYPE> addStore(@Nonnull ECS_TYPE externalData, @Nonnull IResourceStorage resourceStorage, Consumer<Store<ECS_TYPE>> consumer) {
/*  799 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  800 */     return addStore0(externalData, resourceStorage, consumer);
/*      */   }
/*      */   
/*      */   public void removeStore(@Nonnull Store<ECS_TYPE> store) {
/*  804 */     if (this.shutdown) throw new IllegalStateException("Registry has been shutdown"); 
/*  805 */     if (store.isShutdown()) throw new IllegalStateException("Store is already shutdown!"); 
/*  806 */     removeStore0(store);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> newHolder() {
/*  811 */     Holder<ECS_TYPE> holder = new Holder<>(this);
/*  812 */     this.holders.add(new WeakReference<>(holder, this.holderReferenceQueue));
/*  813 */     return holder;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Holder<ECS_TYPE> newHolder(@Nonnull Archetype<ECS_TYPE> archetype, @Nonnull Component<ECS_TYPE>[] components) {
/*  818 */     Holder<ECS_TYPE> holder = new Holder<>(this, archetype, components);
/*  819 */     this.holders.add(new WeakReference<>(holder, this.holderReferenceQueue));
/*  820 */     return holder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected Holder<ECS_TYPE> _internal_newEntityHolder() {
/*  828 */     return new Holder<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Data<ECS_TYPE> _internal_getData() {
/*  835 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Data<ECS_TYPE> getData() {
/*  843 */     assertInStoreThread();
/*  844 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public BuilderCodec<Holder<ECS_TYPE>> getEntityCodec() {
/*  851 */     return this.data.getEntityCodec();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void assertInStoreThread() {
/*  858 */     long lock = this.storeLock.readLock();
/*      */     try {
/*  860 */       for (int i = 0; i < this.storeSize; i++) {
/*  861 */         if (this.stores[i].isInThread())
/*      */           return; 
/*  863 */       }  throw new AssertionError("Data can only be accessed from a store thread!");
/*      */     } finally {
/*  865 */       this.storeLock.unlockRead(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Holder<ECS_TYPE> deserialize(@Nonnull BsonDocument entityDocument) {
/*  872 */     Optional<Integer> version = VERSION.get(entityDocument);
/*  873 */     if (version.isPresent()) {
/*  874 */       return deserialize(entityDocument, ((Integer)version.get()).intValue());
/*      */     }
/*      */     
/*  877 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  878 */     Holder<ECS_TYPE> holder = (Holder<ECS_TYPE>)this.data.getEntityCodec().decode((BsonValue)entityDocument, extraInfo);
/*  879 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  880 */     return holder;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   @Deprecated
/*      */   public Holder<ECS_TYPE> deserialize(@Nonnull BsonDocument entityDocument, int version) {
/*  886 */     ExtraInfo extraInfo = new ExtraInfo(version);
/*  887 */     Holder<ECS_TYPE> holder = (Holder<ECS_TYPE>)this.data.getEntityCodec().decode((BsonValue)entityDocument, extraInfo);
/*  888 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  889 */     return holder;
/*      */   }
/*      */   
/*      */   public BsonDocument serialize(@Nonnull Holder<ECS_TYPE> holder) {
/*  893 */     ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  894 */     BsonDocument document = this.data.getEntityCodec().encode(holder, extraInfo).asDocument();
/*  895 */     extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  896 */     return document;
/*      */   }
/*      */   
/*      */   public boolean hasSystem(@Nonnull ISystem<ECS_TYPE> system) {
/*  900 */     return (ArrayUtil.indexOf((Object[])this.systems, system, 0, this.systemSize) != -1);
/*      */   }
/*      */   
/*      */   public <T extends ISystem<ECS_TYPE>> boolean hasSystemClass(@Nonnull Class<T> systemClass) {
/*  904 */     return this.systemClasses.containsKey(systemClass);
/*      */   }
/*      */   
/*      */   public <T extends ISystem<ECS_TYPE>> boolean hasSystemType(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/*  908 */     return this.systemTypeClassToIndex.containsKey(systemType.getTypeClass());
/*      */   }
/*      */   
/*      */   public boolean hasSystemGroup(@Nonnull SystemGroup<ECS_TYPE> group) {
/*  912 */     return (ArrayUtil.indexOf((Object[])this.systemGroups, group) != -1);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent0(@Nonnull ComponentRegistration<ECS_TYPE, T> registration) {
/*  917 */     return registerComponent0(registration.typeClass(), registration.id(), registration.codec(), registration.supplier(), registration.componentType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent0(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/*      */     int index;
/*  926 */     if (id != null && this.componentIdToIndex.containsKey(id)) throw new IllegalArgumentException("id '" + id + "' already exists!");
/*      */ 
/*      */     
/*  929 */     if (this.componentIndexReuse.isEmpty()) {
/*  930 */       index = this.componentSize++;
/*      */     } else {
/*  932 */       index = this.componentIndexReuse.nextSetBit(0);
/*  933 */       this.componentIndexReuse.clear(index);
/*      */     } 
/*      */     
/*  936 */     if (this.componentIds.length <= index) {
/*  937 */       int newLength = ArrayUtil.grow(index);
/*  938 */       this.componentIds = Arrays.<String>copyOf(this.componentIds, newLength);
/*  939 */       this.componentCodecs = Arrays.<BuilderCodec<? extends Component<ECS_TYPE>>>copyOf(this.componentCodecs, newLength);
/*  940 */       this.componentSuppliers = Arrays.<Supplier<? extends Component<ECS_TYPE>>>copyOf(this.componentSuppliers, newLength);
/*  941 */       this.componentTypes = Arrays.<ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>>copyOf(this.componentTypes, newLength);
/*      */     } 
/*      */     
/*  944 */     componentType.init(this, tClass, index);
/*      */     
/*  946 */     this.componentIdToIndex.put(id, index);
/*  947 */     this.componentIds[index] = id;
/*  948 */     this.componentCodecs[index] = codec;
/*  949 */     this.componentSuppliers[index] = supplier;
/*  950 */     this.componentTypes[index] = componentType;
/*      */     
/*  952 */     return componentType;
/*      */   }
/*      */   
/*      */   private <T extends Component<ECS_TYPE>> void unregisterComponent0(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/*  956 */     int componentIndex = componentType.getIndex();
/*      */ 
/*      */     
/*  959 */     if (componentIndex == this.componentSize - 1) {
/*  960 */       int highestUsedIndex = this.componentIndexReuse.previousClearBit(componentIndex - 1);
/*  961 */       this.componentSize = highestUsedIndex + 1;
/*  962 */       this.componentIndexReuse.clear(this.componentSize, componentIndex);
/*      */     } else {
/*  964 */       this.componentIndexReuse.set(componentIndex);
/*      */     } 
/*      */     
/*  967 */     this.componentIdToIndex.removeInt(this.componentIds[componentIndex]);
/*  968 */     this.componentIds[componentIndex] = null;
/*  969 */     this.componentCodecs[componentIndex] = null;
/*  970 */     this.componentSuppliers[componentIndex] = null;
/*  971 */     this.componentTypes[componentIndex] = null;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource0(@Nonnull ResourceRegistration<ECS_TYPE, T> registration) {
/*  976 */     return registerResource0(registration.typeClass(), registration.id(), registration.codec(), registration.supplier(), registration.resourceType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource0(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier, @Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/*      */     int index;
/*  985 */     if (id != null && this.resourceIdToIndex.containsKey(id)) throw new IllegalArgumentException("id '" + id + "' already exists!");
/*      */ 
/*      */     
/*  988 */     if (this.resourceIndexReuse.isEmpty()) {
/*  989 */       index = this.resourceSize++;
/*      */     } else {
/*  991 */       index = this.resourceIndexReuse.nextSetBit(0);
/*  992 */       this.resourceIndexReuse.clear(index);
/*      */     } 
/*      */     
/*  995 */     if (this.resourceIds.length <= index) {
/*  996 */       int newLength = ArrayUtil.grow(index);
/*  997 */       this.resourceIds = Arrays.<String>copyOf(this.resourceIds, newLength);
/*  998 */       this.resourceCodecs = Arrays.<BuilderCodec<? extends Resource<ECS_TYPE>>>copyOf(this.resourceCodecs, newLength);
/*  999 */       this.resourceSuppliers = Arrays.<Supplier<? extends Resource<ECS_TYPE>>>copyOf(this.resourceSuppliers, newLength);
/* 1000 */       this.resourceTypes = Arrays.<ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>>copyOf(this.resourceTypes, newLength);
/*      */     } 
/*      */     
/* 1003 */     resourceType.init(this, tClass, index);
/*      */     
/* 1005 */     this.resourceIdToIndex.put(id, index);
/* 1006 */     this.resourceIds[index] = id;
/* 1007 */     this.resourceCodecs[index] = codec;
/* 1008 */     this.resourceSuppliers[index] = supplier;
/* 1009 */     this.resourceTypes[index] = resourceType;
/*      */     
/* 1011 */     return resourceType;
/*      */   }
/*      */   
/*      */   private <T extends Resource<ECS_TYPE>> void unregisterResource0(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1015 */     int resourceIndex = resourceType.getIndex();
/*      */ 
/*      */     
/* 1018 */     if (resourceIndex == this.resourceSize - 1) {
/* 1019 */       int highestUsedIndex = this.resourceIndexReuse.previousClearBit(resourceIndex - 1);
/* 1020 */       this.resourceSize = highestUsedIndex + 1;
/* 1021 */       this.resourceIndexReuse.clear(this.resourceSize, resourceIndex);
/*      */     } else {
/* 1023 */       this.resourceIndexReuse.set(resourceIndex);
/*      */     } 
/*      */     
/* 1026 */     this.resourceIdToIndex.removeInt(this.resourceIds[resourceIndex]);
/* 1027 */     this.resourceIds[resourceIndex] = null;
/* 1028 */     this.resourceCodecs[resourceIndex] = null;
/* 1029 */     this.resourceSuppliers[resourceIndex] = null;
/* 1030 */     this.resourceTypes[resourceIndex] = null;
/*      */   }
/*      */   @Nonnull
/*      */   private <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> registerSystemType0(@Nonnull Class<? super T> systemTypeClass) {
/*      */     int systemTypeIndex;
/* 1035 */     if (this.systemTypeClassToIndex.containsKey(systemTypeClass)) throw new IllegalArgumentException("system type '" + String.valueOf(systemTypeClass) + "' already exists!");
/*      */ 
/*      */     
/* 1038 */     if (this.systemTypeIndexReuse.isEmpty()) {
/* 1039 */       systemTypeIndex = this.systemTypeSize++;
/*      */     } else {
/* 1041 */       systemTypeIndex = this.systemTypeIndexReuse.nextSetBit(0);
/* 1042 */       this.systemTypeIndexReuse.clear(systemTypeIndex);
/*      */     } 
/*      */     
/* 1045 */     if (this.systemTypes.length <= systemTypeIndex) {
/* 1046 */       this.systemTypes = Arrays.<SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>>copyOf(this.systemTypes, ArrayUtil.grow(systemTypeIndex));
/* 1047 */       this.systemTypeToSystemIndex = Arrays.<BitSet>copyOf(this.systemTypeToSystemIndex, ArrayUtil.grow(systemTypeIndex));
/*      */     } 
/*      */ 
/*      */     
/* 1051 */     Class<? super T> clazz = systemTypeClass;
/* 1052 */     SystemType<ECS_TYPE, T> systemType = new SystemType<>(this, clazz, systemTypeIndex);
/*      */     
/* 1054 */     this.systemTypeClassToIndex.put(clazz, systemTypeIndex);
/* 1055 */     this.systemTypes[systemTypeIndex] = systemType;
/*      */     
/* 1057 */     return systemType;
/*      */   }
/*      */   
/*      */   private <T extends ISystem<ECS_TYPE>> void unregisterSystemType0(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/* 1061 */     int systemTypeIndex = systemType.getIndex();
/*      */ 
/*      */     
/* 1064 */     if (systemTypeIndex == this.systemTypeSize - 1) {
/* 1065 */       int highestUsedIndex = this.systemTypeIndexReuse.previousClearBit(systemTypeIndex - 1);
/* 1066 */       this.systemTypeSize = highestUsedIndex + 1;
/* 1067 */       this.systemTypeIndexReuse.clear(this.systemTypeSize, systemTypeIndex);
/*      */     } else {
/* 1069 */       this.systemTypeIndexReuse.set(systemTypeIndex);
/*      */     } 
/*      */     
/* 1072 */     this.systemTypeClassToIndex.removeInt(systemType.getTypeClass());
/* 1073 */     this.systemTypes[systemTypeIndex] = null;
/* 1074 */     this.systemTypeToSystemIndex[systemTypeIndex].clear();
/*      */   }
/*      */   @Nonnull
/*      */   private <T extends EcsEvent> EntityEventType<ECS_TYPE, T> registerEntityEventType0(@Nonnull Class<? super T> eventTypeClass) {
/*      */     int systemTypeIndex;
/* 1079 */     if (this.entityEventTypeClassToIndex.containsKey(eventTypeClass)) throw new IllegalArgumentException("event type '" + String.valueOf(eventTypeClass) + "' already exists!");
/*      */ 
/*      */     
/* 1082 */     if (this.systemTypeIndexReuse.isEmpty()) {
/* 1083 */       systemTypeIndex = this.systemTypeSize++;
/*      */     } else {
/* 1085 */       systemTypeIndex = this.systemTypeIndexReuse.nextSetBit(0);
/* 1086 */       this.systemTypeIndexReuse.clear(systemTypeIndex);
/*      */     } 
/*      */     
/* 1089 */     if (this.systemTypes.length <= systemTypeIndex) {
/* 1090 */       this.systemTypes = Arrays.<SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>>copyOf(this.systemTypes, ArrayUtil.grow(systemTypeIndex));
/* 1091 */       this.systemTypeToSystemIndex = Arrays.<BitSet>copyOf(this.systemTypeToSystemIndex, ArrayUtil.grow(systemTypeIndex));
/*      */     } 
/*      */ 
/*      */     
/* 1095 */     Class<? super T> clazz = eventTypeClass;
/* 1096 */     EntityEventType<ECS_TYPE, T> systemType = new EntityEventType(this, EntityEventSystem.class, clazz, systemTypeIndex);
/*      */     
/* 1098 */     this.entityEventTypeClassToIndex.put(clazz, systemTypeIndex);
/* 1099 */     this.systemTypes[systemTypeIndex] = (SystemType)systemType;
/*      */     
/* 1101 */     return systemType;
/*      */   }
/*      */   
/*      */   private <T extends EcsEvent> void unregisterEntityEventType0(@Nonnull EntityEventType<ECS_TYPE, T> eventType) {
/* 1105 */     int systemTypeIndex = eventType.getIndex();
/*      */ 
/*      */     
/* 1108 */     if (systemTypeIndex == this.systemTypeSize - 1) {
/* 1109 */       int highestUsedIndex = this.systemTypeIndexReuse.previousClearBit(systemTypeIndex - 1);
/* 1110 */       this.systemTypeSize = highestUsedIndex + 1;
/* 1111 */       this.systemTypeIndexReuse.clear(this.systemTypeSize, systemTypeIndex);
/*      */     } else {
/* 1113 */       this.systemTypeIndexReuse.set(systemTypeIndex);
/*      */     } 
/*      */     
/* 1116 */     this.entityEventTypeClassToIndex.removeInt(eventType.getEventClass());
/* 1117 */     this.systemTypes[systemTypeIndex] = null;
/* 1118 */     this.systemTypeToSystemIndex[systemTypeIndex].clear();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends EcsEvent> EntityEventType<ECS_TYPE, T> getEntityEventTypeForClass(Class<T> eClass) {
/* 1123 */     int index = this.entityEventTypeClassToIndex.getInt(eClass);
/* 1124 */     if (index == Integer.MIN_VALUE) return null;
/*      */     
/* 1126 */     return (EntityEventType)this.systemTypes[index];
/*      */   }
/*      */   @Nonnull
/*      */   private <T extends EcsEvent> WorldEventType<ECS_TYPE, T> registerWorldEventType0(@Nonnull Class<? super T> eventTypeClass) {
/*      */     int systemTypeIndex;
/* 1131 */     if (this.worldEventTypeClassToIndex.containsKey(eventTypeClass)) throw new IllegalArgumentException("event type '" + String.valueOf(eventTypeClass) + "' already exists!");
/*      */ 
/*      */     
/* 1134 */     if (this.systemTypeIndexReuse.isEmpty()) {
/* 1135 */       systemTypeIndex = this.systemTypeSize++;
/*      */     } else {
/* 1137 */       systemTypeIndex = this.systemTypeIndexReuse.nextSetBit(0);
/* 1138 */       this.systemTypeIndexReuse.clear(systemTypeIndex);
/*      */     } 
/*      */     
/* 1141 */     if (this.systemTypes.length <= systemTypeIndex) {
/* 1142 */       this.systemTypes = Arrays.<SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>>copyOf(this.systemTypes, ArrayUtil.grow(systemTypeIndex));
/* 1143 */       this.systemTypeToSystemIndex = Arrays.<BitSet>copyOf(this.systemTypeToSystemIndex, ArrayUtil.grow(systemTypeIndex));
/*      */     } 
/*      */ 
/*      */     
/* 1147 */     Class<? super T> clazz = eventTypeClass;
/* 1148 */     WorldEventType<ECS_TYPE, T> systemType = new WorldEventType(this, WorldEventSystem.class, clazz, systemTypeIndex);
/*      */     
/* 1150 */     this.worldEventTypeClassToIndex.put(clazz, systemTypeIndex);
/* 1151 */     this.systemTypes[systemTypeIndex] = (SystemType)systemType;
/*      */     
/* 1153 */     return systemType;
/*      */   }
/*      */   
/*      */   private <T extends EcsEvent> void unregisterWorldEventType0(@Nonnull WorldEventType<ECS_TYPE, T> eventType) {
/* 1157 */     int systemTypeIndex = eventType.getIndex();
/*      */ 
/*      */     
/* 1160 */     if (systemTypeIndex == this.systemTypeSize - 1) {
/* 1161 */       int highestUsedIndex = this.systemTypeIndexReuse.previousClearBit(systemTypeIndex - 1);
/* 1162 */       this.systemTypeSize = highestUsedIndex + 1;
/* 1163 */       this.systemTypeIndexReuse.clear(this.systemTypeSize, systemTypeIndex);
/*      */     } else {
/* 1165 */       this.systemTypeIndexReuse.set(systemTypeIndex);
/*      */     } 
/*      */     
/* 1168 */     this.worldEventTypeClassToIndex.removeInt(eventType.getEventClass());
/* 1169 */     this.systemTypes[systemTypeIndex] = null;
/* 1170 */     this.systemTypeToSystemIndex[systemTypeIndex].clear();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends EcsEvent> WorldEventType<ECS_TYPE, T> getWorldEventTypeForClass(Class<T> eClass) {
/* 1175 */     int index = this.worldEventTypeClassToIndex.getInt(eClass);
/* 1176 */     if (index == Integer.MIN_VALUE) return null;
/*      */     
/* 1178 */     return (WorldEventType)this.systemTypes[index];
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private SystemGroup<ECS_TYPE> registerSystemGroup0(@Nonnull Set<Dependency<ECS_TYPE>> dependencies) {
/*      */     int systemGroupIndex;
/* 1184 */     if (this.systemGroupIndexReuse.isEmpty()) {
/* 1185 */       systemGroupIndex = this.systemGroupSize++;
/*      */     } else {
/* 1187 */       systemGroupIndex = this.systemGroupIndexReuse.nextSetBit(0);
/* 1188 */       this.systemGroupIndexReuse.clear(systemGroupIndex);
/*      */     } 
/*      */     
/* 1191 */     if (this.systemGroups.length <= systemGroupIndex) {
/* 1192 */       this.systemGroups = Arrays.<SystemGroup<ECS_TYPE>>copyOf(this.systemGroups, ArrayUtil.grow(systemGroupIndex));
/*      */     }
/*      */     
/* 1195 */     SystemGroup<ECS_TYPE> systemGroup = new SystemGroup<>(this, systemGroupIndex, dependencies);
/*      */     
/* 1197 */     this.systemGroups[systemGroupIndex] = systemGroup;
/*      */     
/* 1199 */     return systemGroup;
/*      */   }
/*      */   
/*      */   private void unregisterSystemGroup0(@Nonnull SystemGroup<ECS_TYPE> systemType) {
/* 1203 */     int systemGroupIndex = systemType.getIndex();
/*      */ 
/*      */     
/* 1206 */     if (systemGroupIndex == this.systemGroupSize - 1) {
/* 1207 */       int highestUsedIndex = this.systemGroupIndexReuse.previousClearBit(systemGroupIndex - 1);
/* 1208 */       this.systemGroupSize = highestUsedIndex + 1;
/* 1209 */       this.systemGroupIndexReuse.clear(this.systemGroupSize, systemGroupIndex);
/*      */     } else {
/* 1211 */       this.systemGroupIndexReuse.set(systemGroupIndex);
/*      */     } 
/*      */     
/* 1214 */     this.systemGroups[systemGroupIndex] = null;
/*      */   }
/*      */   
/*      */   private void registerSystem0(@Nonnull ISystem<ECS_TYPE> system) {
/* 1218 */     int systemIndex = this.systemSize++;
/*      */     
/* 1220 */     if (this.systems.length <= systemIndex) {
/* 1221 */       this.systems = Arrays.<ISystem<ECS_TYPE>>copyOf(this.systems, ArrayUtil.grow(systemIndex));
/*      */     }
/*      */     
/* 1224 */     this.systems[systemIndex] = system;
/*      */ 
/*      */     
/* 1227 */     this.systemClasses.put(system.getClass(), systemIndex);
/*      */     
/* 1229 */     system.onSystemRegistered();
/*      */   }
/*      */   
/*      */   private void unregisterSystem0(int systemIndex, @Nonnull ISystem<ECS_TYPE> system) {
/* 1233 */     int lastIndex = this.systemSize - 1;
/* 1234 */     if (systemIndex != lastIndex) {
/*      */       
/* 1236 */       ISystem<ECS_TYPE> lastSystem = this.systems[lastIndex];
/* 1237 */       this.systems[systemIndex] = lastSystem;
/*      */       
/* 1239 */       this.systemClasses.put(lastSystem.getClass(), systemIndex);
/*      */     } 
/* 1241 */     this.systems[lastIndex] = null;
/* 1242 */     this.systemSize = lastIndex;
/*      */     
/* 1244 */     Class<? extends ISystem> systemClass = system.getClass();
/* 1245 */     boolean bypassClassCheck = this.systemBypassClassCheck.getBoolean(systemClass);
/* 1246 */     if (!bypassClassCheck && !this.systemClasses.remove(systemClass, systemIndex)) {
/* 1247 */       throw new IllegalArgumentException("Failed to remove system " + systemClass.getName() + ", " + systemIndex);
/*      */     }
/*      */     
/* 1250 */     system.onSystemUnregistered();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private Store<ECS_TYPE> addStore0(@Nonnull ECS_TYPE externalData, @Nonnull IResourceStorage resourceStorage, Consumer<Store<ECS_TYPE>> consumer) {
/* 1255 */     long lock = this.storeLock.writeLock();
/*      */     try {
/* 1257 */       int storeIndex = this.storeSize++;
/*      */       
/* 1259 */       if (this.stores.length <= storeIndex) {
/* 1260 */         this.stores = Arrays.<Store<ECS_TYPE>>copyOf(this.stores, ArrayUtil.grow(storeIndex));
/*      */       }
/*      */       
/* 1263 */       Store<ECS_TYPE> store = new Store<>(this, storeIndex, externalData, resourceStorage);
/* 1264 */       this.stores[storeIndex] = store;
/*      */       
/* 1266 */       consumer.accept(store);
/*      */       
/* 1268 */       store.onAdd(this.data);
/*      */       
/* 1270 */       return store;
/*      */     } finally {
/* 1272 */       this.storeLock.unlockWrite(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeStore0(@Nonnull Store<ECS_TYPE> store) {
/*      */     long lock;
/* 1278 */     store.shutdown0(this.data);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/* 1284 */       Thread.onSpinWait();
/* 1285 */       doDataUpdate();
/* 1286 */       lock = this.storeLock.tryWriteLock();
/* 1287 */     } while (!this.storeLock.validate(lock));
/*      */     
/*      */     try {
/* 1290 */       int storeIndex = store.storeIndex;
/* 1291 */       int lastIndex = this.storeSize - 1;
/* 1292 */       if (storeIndex != lastIndex) {
/*      */         
/* 1294 */         Store<ECS_TYPE> lastStore = this.stores[lastIndex];
/* 1295 */         lastStore.storeIndex = storeIndex;
/* 1296 */         this.stores[storeIndex] = lastStore;
/*      */       } 
/* 1298 */       this.stores[lastIndex] = null;
/* 1299 */       this.storeSize = lastIndex;
/*      */     } finally {
/* 1301 */       this.storeLock.unlockWrite(lock);
/*      */     } 
/*      */   }
/*      */   
/*      */   Data<ECS_TYPE> doDataUpdate() {
/* 1306 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateData0(@Nonnull DataChange... dataChanges) {
/* 1313 */     boolean systemChanged = false;
/* 1314 */     boolean systemTypeChanged = false;
/* 1315 */     for (DataChange dataChange : dataChanges) {
/* 1316 */       if (dataChange instanceof SystemChange) {
/* 1317 */         systemChanged = true;
/*      */       }
/* 1319 */       if (dataChange instanceof SystemTypeChange) {
/* 1320 */         systemTypeChanged = true;
/*      */       }
/*      */     } 
/*      */     
/* 1324 */     if (systemChanged) {
/* 1325 */       if (this.sortedSystems.length < this.systems.length) {
/* 1326 */         this.sortedSystems = Arrays.<ISystem<ECS_TYPE>>copyOf(this.systems, this.systems.length);
/*      */       } else {
/* 1328 */         System.arraycopy(this.systems, 0, this.sortedSystems, 0, this.systems.length);
/*      */       } 
/*      */       
/* 1331 */       ISystem.calculateOrder(this, (ISystem[])this.sortedSystems, this.systemSize);
/*      */     } 
/*      */     
/* 1334 */     if (systemChanged || systemTypeChanged) {
/* 1335 */       for (int systemTypeIndex = 0; systemTypeIndex < this.systemTypeSize; systemTypeIndex++) {
/* 1336 */         SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>> systemType = this.systemTypes[systemTypeIndex];
/* 1337 */         if (systemType != null) {
/*      */           
/* 1339 */           BitSet bitSet = this.systemTypeToSystemIndex[systemTypeIndex] = new BitSet();
/* 1340 */           for (int systemIndex = 0; systemIndex < this.systemSize; systemIndex++) {
/* 1341 */             if (systemType.isType(this.sortedSystems[systemIndex])) {
/* 1342 */               bitSet.set(systemIndex);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/* 1348 */     long lock = this.storeLock.readLock();
/* 1349 */     this.dataUpdateLock.writeLock().lock();
/*      */     try {
/* 1351 */       Data<ECS_TYPE> oldData = this.data;
/* 1352 */       this.data = new Data<>(oldData.getVersion() + 1, this, dataChanges);
/*      */       
/* 1354 */       for (int i = 0; i < this.storeSize; i++) {
/* 1355 */         this.stores[i].updateData(oldData, this.data);
/*      */       }
/*      */ 
/*      */       
/* 1359 */       for (Reference<Holder<ECS_TYPE>> holderReference : this.holders) {
/* 1360 */         Holder<ECS_TYPE> holder = holderReference.get();
/* 1361 */         if (holder != null) holder.updateData(oldData, this.data); 
/*      */       } 
/*      */     } finally {
/* 1364 */       this.dataUpdateLock.writeLock().unlock();
/* 1365 */       this.storeLock.unlockRead(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/* 1373 */     return "ComponentRegistry{super()=" + String.valueOf(getClass()) + "@" + hashCode() + ", shutdown=" + this.shutdown + ", dataLock=" + String.valueOf(this.dataLock) + ", idToIndex=" + String.valueOf(this.componentIdToIndex) + ", componentIndexReuse=" + String.valueOf(this.componentIndexReuse) + ", componentSize=" + this.componentSize + ", componentIds=" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1379 */       Arrays.toString((Object[])this.componentIds) + ", componentCodecs=" + 
/* 1380 */       Arrays.toString((Object[])this.componentCodecs) + ", componentSuppliers=" + 
/* 1381 */       Arrays.toString((Object[])this.componentSuppliers) + ", componentTypes=" + 
/* 1382 */       Arrays.toString((Object[])this.componentTypes) + ", resourceIndexReuse=" + String.valueOf(this.resourceIndexReuse) + ", resourceSize=" + this.resourceSize + ", resourceIds=" + 
/*      */ 
/*      */       
/* 1385 */       Arrays.toString((Object[])this.resourceIds) + ", resourceCodecs=" + 
/* 1386 */       Arrays.toString((Object[])this.resourceCodecs) + ", resourceSuppliers=" + 
/* 1387 */       Arrays.toString((Object[])this.resourceSuppliers) + ", resourceTypes=" + 
/* 1388 */       Arrays.toString((Object[])this.resourceTypes) + ", systemSize=" + this.systemSize + ", systems=" + 
/*      */       
/* 1390 */       Arrays.toString((Object[])this.systems) + ", sortedSystems=" + 
/* 1391 */       Arrays.toString((Object[])this.sortedSystems) + ", storeLock=" + String.valueOf(this.storeLock) + ", storeSize=" + this.storeSize + ", stores=" + 
/*      */ 
/*      */       
/* 1394 */       Arrays.toString((Object[])this.stores) + ", data=" + String.valueOf(this.data) + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Component<ECS_TYPE>> T createComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1400 */     long lock = this.dataLock.readLock();
/*      */     try {
/* 1402 */       return (T)this.data.createComponent((ComponentType)componentType);
/*      */     } finally {
/* 1404 */       this.dataLock.unlockRead(lock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Data<ECS_TYPE>
/*      */   {
/*      */     private final int version;
/*      */     
/*      */     @Nonnull
/*      */     private final ComponentRegistry<ECS_TYPE> registry;
/*      */     
/*      */     private final Object2IntMap<String> componentIdToIndex;
/*      */     
/*      */     private final int componentSize;
/*      */     
/*      */     @Nonnull
/*      */     private final String[] componentIds;
/*      */     
/*      */     @Nonnull
/*      */     private final BuilderCodec<? extends Component<ECS_TYPE>>[] componentCodecs;
/*      */     
/*      */     @Nonnull
/*      */     private final Supplier<? extends Component<ECS_TYPE>>[] componentSuppliers;
/*      */     
/*      */     @Nonnull
/*      */     private final ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[] componentTypes;
/*      */     
/*      */     private final Object2IntMap<String> resourceIdToIndex;
/*      */     
/*      */     private final int resourceSize;
/*      */     
/*      */     @Nonnull
/*      */     private final String[] resourceIds;
/*      */     
/*      */     @Nonnull
/*      */     private final BuilderCodec<? extends Resource<ECS_TYPE>>[] resourceCodecs;
/*      */     @Nonnull
/*      */     private final Supplier<? extends Resource<ECS_TYPE>>[] resourceSuppliers;
/*      */     @Nonnull
/*      */     private final ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[] resourceTypes;
/*      */     private final Object2IntMap<Class<? extends ISystem<ECS_TYPE>>> systemTypeClassToIndex;
/*      */     private final int systemTypeSize;
/*      */     @Nonnull
/*      */     private final SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[] systemTypes;
/*      */     @Nonnull
/*      */     private final BitSet[] systemTypeToSystemIndex;
/*      */     private final int systemSize;
/*      */     @Nonnull
/*      */     private final ISystem<ECS_TYPE>[] sortedSystems;
/*      */     @Nonnull
/*      */     private final Map<String, Codec<Component<ECS_TYPE>>> codecMap;
/*      */     @Nonnull
/*      */     private final BuilderCodec<Holder<ECS_TYPE>> entityCodec;
/*      */     @Nullable
/*      */     private final DataChange[] dataChanges;
/*      */     
/*      */     private Data(@Nonnull ComponentRegistry<ECS_TYPE> registry) {
/* 1462 */       this.version = 0;
/* 1463 */       this.registry = registry;
/*      */       
/* 1465 */       this.componentIdToIndex = Object2IntMaps.emptyMap();
/* 1466 */       this.componentSize = 0;
/* 1467 */       this.componentIds = ArrayUtil.EMPTY_STRING_ARRAY;
/* 1468 */       this.componentCodecs = (BuilderCodec<? extends Component<ECS_TYPE>>[])BuilderCodec.EMPTY_ARRAY;
/* 1469 */       this.componentSuppliers = (Supplier<? extends Component<ECS_TYPE>>[])ArrayUtil.emptySupplierArray();
/* 1470 */       this.componentTypes = (ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>[])ComponentType.EMPTY_ARRAY;
/*      */       
/* 1472 */       this.resourceIdToIndex = Object2IntMaps.emptyMap();
/* 1473 */       this.resourceSize = 0;
/* 1474 */       this.resourceIds = ArrayUtil.EMPTY_STRING_ARRAY;
/* 1475 */       this.resourceCodecs = (BuilderCodec<? extends Resource<ECS_TYPE>>[])BuilderCodec.EMPTY_ARRAY;
/* 1476 */       this.resourceSuppliers = (Supplier<? extends Resource<ECS_TYPE>>[])ArrayUtil.emptySupplierArray();
/* 1477 */       this.resourceTypes = (ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>[])ResourceType.EMPTY_ARRAY;
/*      */       
/* 1479 */       this.systemTypeClassToIndex = Object2IntMaps.emptyMap();
/* 1480 */       this.systemTypeSize = 0;
/* 1481 */       this.systemTypes = (SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>[])SystemType.EMPTY_ARRAY;
/* 1482 */       this.systemTypeToSystemIndex = ArrayUtil.EMPTY_BITSET_ARRAY;
/*      */       
/* 1484 */       this.systemSize = 0;
/* 1485 */       this.sortedSystems = (ISystem<ECS_TYPE>[])ISystem.EMPTY_ARRAY;
/*      */       
/* 1487 */       this.codecMap = Collections.emptyMap();
/* 1488 */       this.entityCodec = createCodec();
/*      */       
/* 1490 */       this.dataChanges = null;
/*      */     }
/*      */     
/*      */     private Data(int version, @Nonnull ComponentRegistry<ECS_TYPE> registry, DataChange... dataChanges) {
/* 1494 */       this.version = version;
/* 1495 */       this.registry = registry;
/*      */       
/* 1497 */       this.componentIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(registry.componentIdToIndex);
/* 1498 */       this.componentIdToIndex.defaultReturnValue(-2147483648);
/* 1499 */       this.componentSize = registry.componentSize;
/* 1500 */       this.componentIds = Arrays.<String>copyOf(registry.componentIds, this.componentSize);
/* 1501 */       this.componentCodecs = Arrays.<BuilderCodec<? extends Component<ECS_TYPE>>>copyOf(registry.componentCodecs, this.componentSize);
/* 1502 */       this.componentSuppliers = Arrays.<Supplier<? extends Component<ECS_TYPE>>>copyOf(registry.componentSuppliers, this.componentSize);
/* 1503 */       this.componentTypes = Arrays.<ComponentType<ECS_TYPE, ? extends Component<ECS_TYPE>>>copyOf(registry.componentTypes, this.componentSize);
/*      */       
/* 1505 */       this.resourceIdToIndex = (Object2IntMap<String>)new Object2IntOpenHashMap(registry.resourceIdToIndex);
/* 1506 */       this.resourceIdToIndex.defaultReturnValue(-2147483648);
/* 1507 */       this.resourceSize = registry.resourceSize;
/* 1508 */       this.resourceIds = Arrays.<String>copyOf(registry.resourceIds, this.resourceSize);
/* 1509 */       this.resourceCodecs = Arrays.<BuilderCodec<? extends Resource<ECS_TYPE>>>copyOf(registry.resourceCodecs, this.resourceSize);
/* 1510 */       this.resourceSuppliers = Arrays.<Supplier<? extends Resource<ECS_TYPE>>>copyOf(registry.resourceSuppliers, this.resourceSize);
/* 1511 */       this.resourceTypes = Arrays.<ResourceType<ECS_TYPE, ? extends Resource<ECS_TYPE>>>copyOf(registry.resourceTypes, this.resourceSize);
/*      */       
/* 1513 */       this.systemTypeClassToIndex = (Object2IntMap<Class<? extends ISystem<ECS_TYPE>>>)new Object2IntOpenHashMap(registry.systemTypeClassToIndex);
/* 1514 */       this.systemTypeClassToIndex.defaultReturnValue(-2147483648);
/* 1515 */       this.systemTypeSize = registry.systemTypeSize;
/* 1516 */       this.systemTypes = Arrays.<SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>>>copyOf(registry.systemTypes, this.systemTypeSize);
/* 1517 */       this.systemTypeToSystemIndex = Arrays.<BitSet>copyOf(registry.systemTypeToSystemIndex, this.systemTypeSize);
/*      */       
/* 1519 */       this.systemSize = registry.systemSize;
/* 1520 */       this.sortedSystems = Arrays.<ISystem<ECS_TYPE>>copyOf(registry.sortedSystems, this.systemSize);
/*      */       
/* 1522 */       Object2ObjectOpenHashMap<String, Codec<Component<ECS_TYPE>>> codecMap = new Object2ObjectOpenHashMap(this.componentSize);
/* 1523 */       for (int i = 0; i < this.componentSize; i++) {
/* 1524 */         if (this.componentCodecs[i] != null)
/*      */         {
/* 1526 */           codecMap.put(this.componentIds[i], this.componentCodecs[i]); } 
/*      */       } 
/* 1528 */       this.codecMap = (Map<String, Codec<Component<ECS_TYPE>>>)codecMap;
/* 1529 */       this.entityCodec = createCodec();
/*      */       
/* 1531 */       this.dataChanges = dataChanges;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     private BuilderCodec<Holder<ECS_TYPE>> createCodec() {
/* 1539 */       Function<Codec<Component<ECS_TYPE>>, Codec<Component<ECS_TYPE>>> function = componentCodec -> (componentCodec != null) ? componentCodec : TempUnknownComponent.COMPONENT_CODEC;
/* 1540 */       Objects.requireNonNull(this.registry); return ((BuilderCodec.Builder)BuilderCodec.builder(Holder.class, this.registry::newHolder)
/* 1541 */         .addField(new KeyedCodec("Components", (Codec)new MapProvidedMapCodec(this.codecMap, function, java.util.LinkedHashMap::new, false)), (holder, map) -> holder.loadComponentsMap(this, map), holder -> holder.createComponentsMap(this)))
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1546 */         .build();
/*      */     }
/*      */     
/*      */     public int getVersion() {
/* 1550 */       return this.version;
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public ComponentRegistry<ECS_TYPE> getRegistry() {
/* 1555 */       return this.registry;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public ComponentType<ECS_TYPE, ?> getComponentType(String id) {
/* 1560 */       int index = this.componentIdToIndex.getInt(id);
/* 1561 */       if (index == Integer.MIN_VALUE) return null; 
/* 1562 */       return this.componentTypes[index];
/*      */     }
/*      */     
/*      */     public int getComponentSize() {
/* 1566 */       return this.componentSize;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public String getComponentId(@Nonnull ComponentType<ECS_TYPE, ?> componentType) {
/* 1571 */       return this.componentIds[componentType.getIndex()];
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public <T extends Component<ECS_TYPE>> Codec<T> getComponentCodec(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1577 */       return (Codec)this.componentCodecs[componentType.getIndex()];
/*      */     }
/*      */ 
/*      */     
/*      */     public <T extends Component<ECS_TYPE>> T createComponent(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 1582 */       componentType.validateRegistry(this.registry);
/* 1583 */       componentType.validate();
/* 1584 */       return (T)this.componentSuppliers[componentType.getIndex()].get();
/*      */     }
/*      */     
/*      */     public ResourceType<ECS_TYPE, ?> getResourceType(int index) {
/* 1588 */       return this.resourceTypes[index];
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public ResourceType<ECS_TYPE, ?> getResourceType(String id) {
/* 1593 */       int index = this.resourceIdToIndex.getInt(id);
/* 1594 */       if (index == Integer.MIN_VALUE) return null; 
/* 1595 */       return this.resourceTypes[index];
/*      */     }
/*      */     
/*      */     public int getResourceSize() {
/* 1599 */       return this.resourceSize;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public String getResourceId(@Nonnull ResourceType<ECS_TYPE, ?> resourceType) {
/* 1604 */       return this.resourceIds[resourceType.getIndex()];
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public <T extends Resource<ECS_TYPE>> BuilderCodec<T> getResourceCodec(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1610 */       return (BuilderCodec)this.resourceCodecs[resourceType.getIndex()];
/*      */     }
/*      */ 
/*      */     
/*      */     public <T extends Resource<ECS_TYPE>> T createResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 1615 */       resourceType.validateRegistry(this.registry);
/* 1616 */       resourceType.validate();
/* 1617 */       return (T)this.resourceSuppliers[resourceType.getIndex()].get();
/*      */     }
/*      */     
/*      */     public int getSystemTypeSize() {
/* 1621 */       return this.systemTypeSize;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> getSystemType(Class<? super T> systemTypeClass) {
/* 1626 */       int systemTypeClassToIndexInt = this.systemTypeClassToIndex.getInt(systemTypeClass);
/* 1627 */       if (systemTypeClassToIndexInt == Integer.MIN_VALUE) return null;
/*      */       
/* 1629 */       return (SystemType)this.systemTypes[systemTypeClassToIndexInt];
/*      */     }
/*      */     
/*      */     public SystemType<ECS_TYPE, ? extends ISystem<ECS_TYPE>> getSystemType(int systemTypeIndex) {
/* 1633 */       return this.systemTypes[systemTypeIndex];
/*      */     }
/*      */     
/*      */     public <T extends ISystem<ECS_TYPE>> BitSet getSystemIndexesForType(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/* 1637 */       return this.systemTypeToSystemIndex[systemType.getIndex()];
/*      */     }
/*      */     
/*      */     public int getSystemSize() {
/* 1641 */       return this.systemSize;
/*      */     }
/*      */     
/*      */     public ISystem<ECS_TYPE> getSystem(int systemIndex) {
/* 1645 */       return this.sortedSystems[systemIndex];
/*      */     }
/*      */ 
/*      */     
/*      */     public <T extends ISystem<ECS_TYPE>> T getSystem(int systemIndex, SystemType<ECS_TYPE, T> systemType) {
/* 1650 */       return (T)this.sortedSystems[systemIndex];
/*      */     }
/*      */     
/*      */     public int indexOf(ISystem<ECS_TYPE> system) {
/* 1654 */       int systemIndex = -1;
/* 1655 */       for (int i = 0; i < this.sortedSystems.length; i++) {
/* 1656 */         if (this.sortedSystems[i] == system) {
/* 1657 */           systemIndex = i;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1661 */       return systemIndex;
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public BuilderCodec<Holder<ECS_TYPE>> getEntityCodec() {
/* 1666 */       return this.entityCodec;
/*      */     }
/*      */     
/*      */     public int getDataChangeCount() {
/* 1670 */       return this.dataChanges.length;
/*      */     }
/*      */     
/*      */     public DataChange getDataChange(int index) {
/* 1674 */       return this.dataChanges[index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(@Nullable Object o) {
/* 1679 */       if (this == o) return true; 
/* 1680 */       if (o == null || getClass() != o.getClass()) return false;
/*      */       
/* 1682 */       Data<?> data = (Data)o;
/*      */       
/* 1684 */       return (this.version == data.version);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1689 */       return this.version;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public String toString() {
/* 1695 */       return "Data{version=" + this.version + ", componentSize=" + this.componentSize + ", componentSuppliers=" + 
/*      */ 
/*      */         
/* 1698 */         Arrays.toString((Object[])this.componentSuppliers) + ", resourceSize=" + this.resourceSize + ", resourceSuppliers=" + 
/*      */         
/* 1700 */         Arrays.toString((Object[])this.resourceSuppliers) + ", systemSize=" + this.systemSize + ", sortedSystems=" + 
/*      */         
/* 1702 */         Arrays.toString((Object[])this.sortedSystems) + ", dataChanges=" + 
/* 1703 */         Arrays.toString((Object[])this.dataChanges) + "}";
/*      */     }
/*      */ 
/*      */     
/*      */     public void appendDump(@Nonnull String prefix, @Nonnull StringBuilder sb) {
/* 1708 */       sb.append(prefix).append("version=").append(this.version).append("\n");
/* 1709 */       sb.append(prefix).append("componentSize=").append(this.componentSize).append("\n");
/* 1710 */       sb.append(prefix).append("componentSuppliers=").append("\n"); int i;
/* 1711 */       for (i = 0; i < this.componentSize; i++) {
/* 1712 */         sb.append(prefix).append("\t- ").append(i).append("\t").append(this.componentSuppliers[i]).append("\n");
/*      */       }
/* 1714 */       sb.append(prefix).append("resourceSuppliers=").append("\n");
/* 1715 */       for (i = 0; i < this.resourceSize; i++) {
/* 1716 */         sb.append(prefix).append("\t- ").append(i).append("\t").append(this.resourceSuppliers[i]).append("\n");
/*      */       }
/* 1718 */       sb.append(prefix).append("systemSize=").append(this.systemSize).append("\n");
/* 1719 */       sb.append(prefix).append("sortedSystems=").append("\n");
/* 1720 */       for (i = 0; i < this.systemSize; i++) {
/* 1721 */         sb.append(prefix).append("\t- ").append(i).append("\t").append(this.sortedSystems[i]).append("\n");
/*      */       }
/* 1723 */       sb.append(prefix).append("dataChanges=").append("\n");
/* 1724 */       for (i = 0; i < this.dataChanges.length; i++)
/* 1725 */         sb.append(prefix).append("\t- ").append(i).append("\t").append(this.dataChanges[i]).append("\n"); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ComponentRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */