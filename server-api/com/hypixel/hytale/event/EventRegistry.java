/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*     */ import com.hypixel.hytale.registry.Registry;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ public class EventRegistry
/*     */   extends Registry<EventRegistration<?, ?>>
/*     */   implements IEventRegistry
/*     */ {
/*     */   @Nonnull
/*     */   private final IEventRegistry parent;
/*     */   
/*     */   public EventRegistry(@Nonnull List<BooleanConsumer> registrations, @Nonnull BooleanSupplier precondition, String preconditionMessage, @Nonnull IEventRegistry parent) {
/*  36 */     super(registrations, precondition, preconditionMessage, EventRegistration::new);
/*  37 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private IEventRegistry getParent() {
/*  45 */     return this.parent;
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
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull EventRegistration<KeyType, EventType> evt) {
/*  58 */     return (EventRegistration<KeyType, EventType>)register(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/*  64 */     checkPrecondition();
/*  65 */     return (EventRegistration)register(getParent().register(eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/*  72 */     checkPrecondition();
/*  73 */     return (EventRegistration)register(getParent().register(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IBaseEvent<Void>> EventRegistration<Void, EventType> register(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/*  80 */     checkPrecondition();
/*  81 */     return (EventRegistration)register(getParent().register(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Consumer<EventType> consumer) {
/*  88 */     checkPrecondition();
/*  89 */     return register(getParent().register(eventClass, key, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Consumer<EventType> consumer) {
/*  97 */     checkPrecondition();
/*  98 */     return register(getParent().register(priority, eventClass, key, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> register(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Consumer<EventType> consumer) {
/* 106 */     checkPrecondition();
/* 107 */     return register(getParent().register(priority, eventClass, key, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 113 */     checkPrecondition();
/* 114 */     return (EventRegistration)register(getParent().registerAsync((Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 121 */     checkPrecondition();
/* 122 */     return (EventRegistration)register(getParent().registerAsync(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <EventType extends IAsyncEvent<Void>> EventRegistration<Void, EventType> registerAsync(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 129 */     checkPrecondition();
/* 130 */     return (EventRegistration)register(getParent().registerAsync(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 137 */     checkPrecondition();
/* 138 */     return (EventRegistration)register(getParent().registerAsync((Class)eventClass, key, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(@Nonnull EventPriority priority, Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 145 */     checkPrecondition();
/* 146 */     return (EventRegistration)register(getParent().registerAsync(priority, (Class)eventClass, key, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsync(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull KeyType key, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 154 */     checkPrecondition();
/* 155 */     return (EventRegistration)register(getParent().registerAsync(priority, (Class)eventClass, key, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 161 */     checkPrecondition();
/* 162 */     return register(getParent().registerGlobal(eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 169 */     checkPrecondition();
/* 170 */     return register(getParent().registerGlobal(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerGlobal(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 177 */     checkPrecondition();
/* 178 */     return register(getParent().registerGlobal(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 184 */     checkPrecondition();
/* 185 */     return (EventRegistration)register(getParent().registerAsyncGlobal((Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 192 */     checkPrecondition();
/* 193 */     return (EventRegistration)register(getParent().registerAsyncGlobal(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncGlobal(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 200 */     checkPrecondition();
/* 201 */     return (EventRegistration)register(getParent().registerAsyncGlobal(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 207 */     checkPrecondition();
/* 208 */     return register(getParent().registerUnhandled(eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 215 */     checkPrecondition();
/* 216 */     return register(getParent().registerUnhandled(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IBaseEvent<KeyType>> EventRegistration<KeyType, EventType> registerUnhandled(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Consumer<EventType> consumer) {
/* 223 */     checkPrecondition();
/* 224 */     return register(getParent().registerUnhandled(priority, eventClass, consumer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 230 */     checkPrecondition();
/* 231 */     return (EventRegistration)register(getParent().registerAsyncUnhandled((Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(@Nonnull EventPriority priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 238 */     checkPrecondition();
/* 239 */     return (EventRegistration)register(getParent().registerAsyncUnhandled(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <KeyType, EventType extends IAsyncEvent<KeyType>> EventRegistration<KeyType, EventType> registerAsyncUnhandled(short priority, @Nonnull Class<? super EventType> eventClass, @Nonnull Function<CompletableFuture<EventType>, CompletableFuture<EventType>> function) {
/* 246 */     checkPrecondition();
/* 247 */     return (EventRegistration)register(getParent().registerAsyncUnhandled(priority, (Class)eventClass, (Function)function));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */