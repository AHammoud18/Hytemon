/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventBus
/*     */   implements IEventBus
/*     */ {
/*     */   @Nonnull
/*  24 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  29 */   private final Map<Class<? extends IBaseEvent<?>>, EventBusRegistry<?, ?, ?>> registryMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean timeEvents;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventBus(boolean timeEvents) {
/*  43 */     this.timeEvents = timeEvents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  50 */     this.registryMap.values().forEach(EventBusRegistry::shutdown);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Class<? extends IBaseEvent<?>>> getRegisteredEventClasses() {
/*  58 */     return new HashSet<>(this.registryMap.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getRegisteredEventClassNames() {
/*  66 */     Set<String> classNames = new HashSet<>();
/*  67 */     for (Class<?> aClass : this.registryMap.keySet()) {
/*  68 */       classNames.add(aClass.getSimpleName());
/*     */     }
/*  70 */     return classNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EventBusRegistry<?, ?, ?> getRegistry(@Nonnull String eventName) {
/*     */     Class<? extends IBaseEvent<?>> clazz;
/*  82 */     Class<? extends IBaseEvent> eventClass = null;
/*  83 */     for (Class<? extends IBaseEvent<?>> aClass : this.registryMap.keySet()) {
/*  84 */       if (aClass.getSimpleName().equalsIgnoreCase(eventName) || aClass.getName().equalsIgnoreCase(eventName)) {
/*  85 */         clazz = aClass;
/*     */       }
/*     */     } 
/*     */     
/*  89 */     return (clazz == null) ? null : getRegistry(clazz);
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
/*     */   @Nonnull
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventBusRegistry<KeyType, EventType, ?> getRegistry(@Nonnull Class<? super EventType> eventClass) {
/* 103 */     if (IAsyncEvent.class.isAssignableFrom(eventClass)) {
/* 104 */       return (EventBusRegistry)getAsyncRegistry((Class)eventClass);
/*     */     }
/* 106 */     return (EventBusRegistry)getSyncRegistry((Class)eventClass);
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
/*     */   @Nonnull
/*     */   public <KeyType, EventType extends IEvent<KeyType>> EventBusRegistry<KeyType, EventType, ?> getSyncRegistry(@Nonnull Class<? super EventType> eventClass) {
/* 120 */     EventBusRegistry<?, ? extends IBaseEvent<?>, ? extends EventBusRegistry.EventConsumerMap<? extends IBaseEvent<?>, ?, ?>> registry = (EventBusRegistry<?, ? extends IBaseEvent<?>, ? extends EventBusRegistry.EventConsumerMap<? extends IBaseEvent<?>, ?, ?>>)this.registryMap.computeIfAbsent(eventClass, aClass -> new SyncEventBusRegistry<>(LOGGER, aClass));
/* 121 */     if (this.timeEvents) registry.setTimeEvents(true); 
/* 122 */     return (EventBusRegistry)registry;
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
/*     */   @Nonnull
/*     */   private <KeyType, EventType extends IAsyncEvent<KeyType>> AsyncEventBusRegistry<KeyType, EventType> getAsyncRegistry(@Nonnull Class<? super EventType> eventClass) {
/* 136 */     EventBusRegistry<?, ? extends IBaseEvent<?>, ? extends EventBusRegistry.EventConsumerMap<? extends IBaseEvent<?>, ?, ?>> registry = (EventBusRegistry<?, ? extends IBaseEvent<?>, ? extends EventBusRegistry.EventConsumerMap<? extends IBaseEvent<?>, ?, ?>>)this.registryMap.computeIfAbsent(eventClass, aClass -> new AsyncEventBusRegistry<>(LOGGER, aClass));
/*     */ 
/*     */     
/* 139 */     if (this.timeEvents) {
/* 140 */       registry.setTimeEvents(true);
/*     */     }
/*     */     
/* 143 */     return (AsyncEventBusRegistry)registry;
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 148 */     return (EventRegistration)register((short)0, eventClass, (Void)null, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 153 */     return (EventRegistration)register(priority.getValue(), eventClass, (Void)null, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 158 */     return (EventRegistration)register(priority, eventClass, (Void)null, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Consumer<EventType> consumer) {
/* 163 */     return register((short)0, eventClass, key, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Consumer<EventType> consumer) {
/* 168 */     return register(priority.getValue(), eventClass, key, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(short priority, @Nonnull Class<? super EventType> eventClass, @Nullable KeyType key, @Nonnull Consumer<EventType> consumer) {
/* 173 */     return getRegistry(eventClass).register(priority, key, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 178 */     return (EventRegistration)registerAsync((short)0, eventClass, (Void)null, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 183 */     return (EventRegistration)registerAsync(priority.getValue(), eventClass, (Void)null, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(short priority, Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 188 */     return (EventRegistration)registerAsync(priority, eventClass, (Void)null, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 193 */     return registerAsync((short)0, eventClass, key, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull EventPriority priority, Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 198 */     return registerAsync(priority.getValue(), eventClass, key, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(short priority, @Nonnull Class<? super EventType> eventClass, @Nullable KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 203 */     return getAsyncRegistry(eventClass).registerAsync(priority, key, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 208 */     return registerGlobal((short)0, eventClass, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 213 */     return registerGlobal(priority.getValue(), eventClass, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 218 */     return getRegistry(eventClass).registerGlobal(priority, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 223 */     return registerAsyncGlobal((short)0, eventClass, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 228 */     return registerAsyncGlobal(priority.getValue(), eventClass, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 233 */     return getAsyncRegistry(eventClass).registerAsyncGlobal(priority, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 238 */     return registerUnhandled((short)0, eventClass, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 243 */     return registerUnhandled(priority.getValue(), eventClass, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 248 */     return getRegistry(eventClass).registerUnhandled(priority, consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 253 */     return registerAsyncUnhandled((short)0, eventClass, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 258 */     return registerAsyncUnhandled(priority.getValue(), eventClass, function);
/*     */   }
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 263 */     return getAsyncRegistry(eventClass).registerAsyncUnhandled(priority, function);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <KeyType, EventType extends IEvent<KeyType>> IEventDispatcher<EventType, EventType> dispatchFor(@Nonnull Class<? super EventType> eventClass, KeyType key) {
/* 270 */     SyncEventBusRegistry<KeyType, EventType> registry = (SyncEventBusRegistry<KeyType, EventType>)this.registryMap.get(eventClass);
/* 271 */     if (registry == null) return SyncEventBusRegistry.NO_OP; 
/* 272 */     return registry.dispatchFor(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> IEventDispatcher<EventType, CompletableFuture<EventType>> dispatchForAsync(@Nonnull Class<? super EventType> eventClass, KeyType key) {
/* 280 */     AsyncEventBusRegistry<KeyType, EventType> registry = (AsyncEventBusRegistry<KeyType, EventType>)this.registryMap.get(eventClass);
/* 281 */     if (registry == null) return AsyncEventBusRegistry.NO_OP; 
/* 282 */     return registry.dispatchFor(key);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventBus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */