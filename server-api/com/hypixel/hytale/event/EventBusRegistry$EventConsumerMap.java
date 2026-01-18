/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.atomic.AtomicReference;
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
/*     */ public abstract class EventConsumerMap<EventType extends IBaseEvent, ConsumerType extends EventBusRegistry.EventConsumer, ReturnType>
/*     */   implements IEventDispatcher<EventType, ReturnType>
/*     */ {
/* 266 */   private static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   private final AtomicReference<short[]> prioritiesRef = (AtomicReference)new AtomicReference<>(EMPTY_SHORT_ARRAY);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 276 */   private final Short2ObjectConcurrentHashMap<List<ConsumerType>> map = new Short2ObjectConcurrentHashMap(true, -32768);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 283 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(@Nonnull ConsumerType eventConsumer) {
/* 292 */     short priority = eventConsumer.getPriority();
/*     */ 
/*     */     
/* 295 */     boolean[] wasPriorityAdded = { false };
/*     */     
/* 297 */     ((List<ConsumerType>)this.map.computeIfAbsent(priority, s -> {
/*     */           wasPriorityAdded[0] = true;
/*     */           return new CopyOnWriteArrayList();
/* 300 */         })).add(eventConsumer);
/*     */ 
/*     */     
/* 303 */     if (wasPriorityAdded[0]) addPriority(priority);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(@Nonnull ConsumerType consumer) {
/* 313 */     short priority = consumer.getPriority();
/*     */ 
/*     */     
/* 316 */     boolean[] wasRemoved = { false, false };
/*     */     
/* 318 */     this.map.computeIfPresent(priority, (key, obj) -> {
/*     */           wasRemoved[0] = obj.remove(consumer);
/*     */           if (!obj.isEmpty()) {
/*     */             return obj;
/*     */           }
/*     */           wasRemoved[1] = true;
/*     */           return null;
/*     */         });
/* 326 */     if (wasRemoved[1]) removePriority(priority);
/*     */     
/* 328 */     return wasRemoved[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getPriorities() {
/* 335 */     return this.prioritiesRef.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<ConsumerType> get(short priority) {
/* 346 */     return (List<ConsumerType>)this.map.get(priority);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPriority(short priority) {
/*     */     short[] currentPriorities;
/*     */     short[] newPriorities;
/*     */     do {
/* 359 */       if (!this.map.containsKey(priority))
/*     */         return; 
/* 361 */       currentPriorities = this.prioritiesRef.get();
/* 362 */       int index = Arrays.binarySearch(currentPriorities, priority);
/*     */ 
/*     */       
/* 365 */       if (index >= 0)
/*     */         return; 
/* 367 */       int insertionPoint = -(index + 1);
/*     */ 
/*     */       
/* 370 */       int newLength = currentPriorities.length + 1;
/* 371 */       newPriorities = new short[newLength];
/*     */       
/* 373 */       System.arraycopy(currentPriorities, 0, newPriorities, 0, insertionPoint);
/* 374 */       newPriorities[insertionPoint] = priority;
/* 375 */       System.arraycopy(currentPriorities, insertionPoint, newPriorities, insertionPoint + 1, currentPriorities.length - insertionPoint);
/* 376 */     } while (!this.prioritiesRef.compareAndSet(currentPriorities, newPriorities));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removePriority(short priority) {
/*     */     short[] currentPriorities;
/*     */     short[] newPriorities;
/*     */     do {
/* 389 */       if (this.map.containsKey(priority))
/*     */         return; 
/* 391 */       currentPriorities = this.prioritiesRef.get();
/* 392 */       int index = Arrays.binarySearch(currentPriorities, priority);
/*     */ 
/*     */       
/* 395 */       if (index < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 400 */       int newLength = currentPriorities.length - 1;
/* 401 */       newPriorities = new short[newLength];
/*     */       
/* 403 */       System.arraycopy(currentPriorities, 0, newPriorities, 0, index);
/* 404 */       System.arraycopy(currentPriorities, index + 1, newPriorities, index, newLength - index);
/* 405 */     } while (!this.prioritiesRef.compareAndSet(currentPriorities, newPriorities));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventBusRegistry$EventConsumerMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */