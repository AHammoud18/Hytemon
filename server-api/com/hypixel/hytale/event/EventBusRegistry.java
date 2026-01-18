/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.metrics.metric.Metric;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
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
/*     */ public abstract class EventBusRegistry<KeyType, EventType extends IBaseEvent<KeyType>, ConsumerMapType extends EventBusRegistry.EventConsumerMap<EventType, ?, ?>>
/*     */ {
/*     */   @Nonnull
/*  31 */   protected static final Object NULL = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final HytaleLogger logger;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final Class<EventType> eventClass;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   protected final Map<KeyType, ConsumerMapType> map = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final ConsumerMapType global;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final ConsumerMapType unhandled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean timeEvents;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shutdown;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventBusRegistry(@Nonnull HytaleLogger logger, @Nonnull Class<EventType> eventClass, @Nonnull ConsumerMapType global, @Nonnull ConsumerMapType unhandled) {
/*  85 */     this.logger = logger;
/*  86 */     this.eventClass = eventClass;
/*  87 */     this.global = global;
/*  88 */     this.unhandled = unhandled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Class<EventType> getEventClass() {
/*  96 */     return this.eventClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimeEvents() {
/* 103 */     return this.timeEvents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeEvents(boolean timeEvents) {
/* 112 */     this.timeEvents = timeEvents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 119 */     this.shutdown = true;
/* 120 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlive() {
/* 128 */     return !this.shutdown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EventRegistration<KeyType, EventType> register(short paramShort, @Nullable KeyType paramKeyType, @Nonnull Consumer<EventType> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EventRegistration<KeyType, EventType> registerGlobal(short paramShort, @Nonnull Consumer<EventType> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EventRegistration<KeyType, EventType> registerUnhandled(short paramShort, @Nonnull Consumer<EventType> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract IEventDispatcher<EventType, ?> dispatchFor(KeyType paramKeyType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class EventConsumer
/*     */   {
/*     */     @Nonnull
/* 176 */     protected static final AtomicInteger consumerIndex = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final int index;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final short priority;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected final String consumerString;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 197 */     protected final Metric timer = new Metric();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EventConsumer(short priority, @Nonnull String consumerString) {
/* 207 */       this.priority = priority;
/* 208 */       this.consumerString = consumerString;
/* 209 */       this.index = consumerIndex.getAndIncrement();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndex() {
/* 216 */       return this.index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short getPriority() {
/* 223 */       return this.priority;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String getConsumerString() {
/* 231 */       return this.consumerString;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Metric getTimer() {
/* 239 */       return this.timer;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 245 */       return "EventConsumer{index=" + this.index + ", priority=" + this.priority + ", consumerString='" + this.consumerString + "', timer=" + String.valueOf(this.timer) + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class EventConsumerMap<EventType extends IBaseEvent, ConsumerType extends EventConsumer, ReturnType>
/*     */     implements IEventDispatcher<EventType, ReturnType>
/*     */   {
/* 266 */     private static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     private final AtomicReference<short[]> prioritiesRef = (AtomicReference)new AtomicReference<>(EMPTY_SHORT_ARRAY);
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 276 */     private final Short2ObjectConcurrentHashMap<List<ConsumerType>> map = new Short2ObjectConcurrentHashMap(true, -32768);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 283 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(@Nonnull ConsumerType eventConsumer) {
/* 292 */       short priority = eventConsumer.getPriority();
/*     */ 
/*     */       
/* 295 */       boolean[] wasPriorityAdded = { false };
/*     */       
/* 297 */       ((List<ConsumerType>)this.map.computeIfAbsent(priority, s -> {
/*     */             wasPriorityAdded[0] = true;
/*     */             return new CopyOnWriteArrayList();
/* 300 */           })).add(eventConsumer);
/*     */ 
/*     */       
/* 303 */       if (wasPriorityAdded[0]) addPriority(priority);
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(@Nonnull ConsumerType consumer) {
/* 313 */       short priority = consumer.getPriority();
/*     */ 
/*     */       
/* 316 */       boolean[] wasRemoved = { false, false };
/*     */       
/* 318 */       this.map.computeIfPresent(priority, (key, obj) -> {
/*     */             wasRemoved[0] = obj.remove(consumer);
/*     */             if (!obj.isEmpty()) {
/*     */               return obj;
/*     */             }
/*     */             wasRemoved[1] = true;
/*     */             return null;
/*     */           });
/* 326 */       if (wasRemoved[1]) removePriority(priority);
/*     */       
/* 328 */       return wasRemoved[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short[] getPriorities() {
/* 335 */       return this.prioritiesRef.get();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public List<ConsumerType> get(short priority) {
/* 346 */       return (List<ConsumerType>)this.map.get(priority);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void addPriority(short priority) {
/*     */       short[] currentPriorities;
/*     */       short[] newPriorities;
/*     */       do {
/* 359 */         if (!this.map.containsKey(priority))
/*     */           return; 
/* 361 */         currentPriorities = this.prioritiesRef.get();
/* 362 */         int index = Arrays.binarySearch(currentPriorities, priority);
/*     */ 
/*     */         
/* 365 */         if (index >= 0)
/*     */           return; 
/* 367 */         int insertionPoint = -(index + 1);
/*     */ 
/*     */         
/* 370 */         int newLength = currentPriorities.length + 1;
/* 371 */         newPriorities = new short[newLength];
/*     */         
/* 373 */         System.arraycopy(currentPriorities, 0, newPriorities, 0, insertionPoint);
/* 374 */         newPriorities[insertionPoint] = priority;
/* 375 */         System.arraycopy(currentPriorities, insertionPoint, newPriorities, insertionPoint + 1, currentPriorities.length - insertionPoint);
/* 376 */       } while (!this.prioritiesRef.compareAndSet(currentPriorities, newPriorities));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void removePriority(short priority) {
/*     */       short[] currentPriorities;
/*     */       short[] newPriorities;
/*     */       do {
/* 389 */         if (this.map.containsKey(priority))
/*     */           return; 
/* 391 */         currentPriorities = this.prioritiesRef.get();
/* 392 */         int index = Arrays.binarySearch(currentPriorities, priority);
/*     */ 
/*     */         
/* 395 */         if (index < 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 400 */         int newLength = currentPriorities.length - 1;
/* 401 */         newPriorities = new short[newLength];
/*     */         
/* 403 */         System.arraycopy(currentPriorities, 0, newPriorities, 0, index);
/* 404 */         System.arraycopy(currentPriorities, index + 1, newPriorities, index, newLength - index);
/* 405 */       } while (!this.prioritiesRef.compareAndSet(currentPriorities, newPriorities));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventBusRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */