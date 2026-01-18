/*     */ package com.hypixel.hytale.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.event.EntityEventType;
/*     */ import com.hypixel.hytale.component.event.WorldEventType;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandBuffer<ECS_TYPE>
/*     */   implements ComponentAccessor<ECS_TYPE>
/*     */ {
/*     */   @Nonnull
/*     */   private final Store<ECS_TYPE> store;
/*     */   @Nonnull
/*  42 */   private final Deque<Consumer<Store<ECS_TYPE>>> queue = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Ref<ECS_TYPE> trackedRef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean trackedRefRemoved;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private CommandBuffer<ECS_TYPE> parentBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Thread thread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CommandBuffer(@Nonnull Store<ECS_TYPE> store) {
/*  74 */     this.store = store;
/*     */     
/*  76 */     assert setThread();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Store<ECS_TYPE> getStore() {
/*  84 */     return this.store;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run(@Nonnull Consumer<Store<ECS_TYPE>> consumer) {
/*  95 */     assert Thread.currentThread() == this.thread;
/*  96 */     this.queue.add(consumer);
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
/*     */   public <T extends Component<ECS_TYPE>> T getComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 114 */     assert Thread.currentThread() == this.thread;
/* 115 */     return this.store.__internal_getComponent(ref, componentType);
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
/*     */   public Archetype<ECS_TYPE> getArchetype(@Nonnull Ref<ECS_TYPE> ref) {
/* 129 */     assert Thread.currentThread() == this.thread;
/* 130 */     return this.store.__internal_getArchetype(ref);
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
/*     */   @Nonnull
/*     */   public <T extends Resource<ECS_TYPE>> T getResource(@Nonnull ResourceType<ECS_TYPE, T> resourceType) {
/* 148 */     assert Thread.currentThread() == this.thread;
/* 149 */     return this.store.__internal_getResource(resourceType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ECS_TYPE getExternalData() {
/* 158 */     return this.store.getExternalData();
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
/*     */   @Nonnull
/*     */   public Ref<ECS_TYPE>[] addEntities(@Nonnull Holder<ECS_TYPE>[] holders, @Nonnull AddReason reason) {
/* 173 */     assert Thread.currentThread() == this.thread;
/*     */     
/* 175 */     Ref[] arrayOfRef = new Ref[holders.length];
/* 176 */     for (int i = 0; i < holders.length; i++) {
/* 177 */       arrayOfRef[i] = new Ref<>(this.store);
/*     */     }
/* 179 */     this.queue.add(chunk -> chunk.addEntities(holders, refs, reason));
/* 180 */     return (Ref<ECS_TYPE>[])arrayOfRef;
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
/*     */   @Nonnull
/*     */   public Ref<ECS_TYPE> addEntity(@Nonnull Holder<ECS_TYPE> holder, @Nonnull AddReason reason) {
/* 195 */     assert Thread.currentThread() == this.thread;
/* 196 */     Ref<ECS_TYPE> ref = new Ref<>(this.store);
/* 197 */     this.queue.add(chunk -> chunk.addEntity(holder, ref, reason));
/* 198 */     return ref;
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
/*     */   public void addEntities(@Nonnull Holder<ECS_TYPE>[] holders, int holderStart, @Nonnull Ref<ECS_TYPE>[] refs, int refStart, int length, @Nonnull AddReason reason) {
/* 215 */     assert Thread.currentThread() == this.thread;
/*     */     
/* 217 */     for (int i = refStart; i < refStart + length; i++) {
/* 218 */       refs[i] = new Ref<>(this.store);
/*     */     }
/* 220 */     this.queue.add(chunk -> chunk.addEntities(holders, holderStart, refs, refStart, length, reason));
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
/*     */   @Nonnull
/*     */   public Ref<ECS_TYPE> addEntity(@Nonnull Holder<ECS_TYPE> holder, @Nonnull Ref<ECS_TYPE> ref, @Nonnull AddReason reason) {
/* 236 */     if (ref.isValid()) throw new IllegalArgumentException("EntityReference is already in use!"); 
/* 237 */     if (ref.getStore() != this.store) throw new IllegalArgumentException("EntityReference is not for the correct store!"); 
/* 238 */     assert Thread.currentThread() == this.thread;
/* 239 */     this.queue.add(chunk -> chunk.addEntity(holder, ref, reason));
/* 240 */     return ref;
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
/*     */   @Nonnull
/*     */   public Holder<ECS_TYPE> copyEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> target) {
/* 255 */     assert Thread.currentThread() == this.thread;
/* 256 */     this.queue.add(chunk -> chunk.copyEntity(ref, target));
/* 257 */     return target;
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
/*     */   public void tryRemoveEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull RemoveReason reason) {
/* 269 */     assert Thread.currentThread() == this.thread;
/* 270 */     Throwable source = new Throwable();
/* 271 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid())
/*     */             return;  chunk.removeEntity(ref, chunk.getRegistry().newHolder(), reason, source);
/*     */         });
/* 275 */     if (ref.equals(this.trackedRef)) this.trackedRefRemoved = true; 
/* 276 */     if (this.parentBuffer != null) {
/* 277 */       this.parentBuffer.testRemovedTracked(ref);
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
/*     */   public void removeEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull RemoveReason reason) {
/* 290 */     assert Thread.currentThread() == this.thread;
/* 291 */     Throwable source = new Throwable();
/* 292 */     this.queue.add(chunk -> chunk.removeEntity(ref, chunk.getRegistry().newHolder(), reason, source));
/* 293 */     if (ref.equals(this.trackedRef)) this.trackedRefRemoved = true; 
/* 294 */     if (this.parentBuffer != null) {
/* 295 */       this.parentBuffer.testRemovedTracked(ref);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<ECS_TYPE> removeEntity(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Holder<ECS_TYPE> target, @Nonnull RemoveReason reason) {
/* 302 */     assert Thread.currentThread() == this.thread;
/* 303 */     Throwable source = new Throwable();
/* 304 */     this.queue.add(chunk -> chunk.removeEntity(ref, target, reason, source));
/* 305 */     if (ref.equals(this.trackedRef)) this.trackedRefRemoved = true; 
/* 306 */     if (this.parentBuffer != null) {
/* 307 */       this.parentBuffer.testRemovedTracked(ref);
/*     */     }
/* 309 */     return target;
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
/*     */   public <T extends Component<ECS_TYPE>> void ensureComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 322 */     assert Thread.currentThread() == this.thread;
/*     */     
/* 324 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           chunk.ensureComponent(ref, componentType);
/*     */         });
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
/*     */   public <T extends Component<ECS_TYPE>> T ensureAndGetComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 343 */     assert Thread.currentThread() == this.thread;
/* 344 */     T component = this.store.__internal_getComponent(ref, componentType);
/* 345 */     if (component != null) return component;
/*     */     
/* 347 */     T newComponent = this.store.getRegistry()._internal_getData().createComponent(componentType);
/* 348 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid())
/*     */             return;  chunk.addComponent(ref, componentType, newComponent);
/*     */         });
/* 352 */     return newComponent;
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
/*     */   @Nonnull
/*     */   public <T extends Component<ECS_TYPE>> T addComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 368 */     assert Thread.currentThread() == this.thread;
/* 369 */     T component = this.store.getRegistry()._internal_getData().createComponent(componentType);
/* 370 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid())
/*     */             return;  chunk.addComponent(ref, componentType, component);
/*     */         });
/* 374 */     return component;
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
/*     */   public <T extends Component<ECS_TYPE>> void addComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 391 */     assert Thread.currentThread() == this.thread;
/* 392 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           chunk.addComponent(ref, componentType, component);
/*     */         });
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
/*     */   public <T extends Component<ECS_TYPE>> void replaceComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 411 */     assert Thread.currentThread() == this.thread;
/* 412 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           chunk.replaceComponent(ref, componentType, component);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void removeComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 427 */     assert Thread.currentThread() == this.thread;
/* 428 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           chunk.removeComponent(ref, componentType);
/*     */         });
/*     */   }
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void tryRemoveComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 437 */     assert Thread.currentThread() == this.thread;
/* 438 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           chunk.tryRemoveComponent(ref, componentType);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Component<ECS_TYPE>> void putComponent(@Nonnull Ref<ECS_TYPE> ref, @Nonnull ComponentType<ECS_TYPE, T> componentType, @Nonnull T component) {
/* 448 */     assert Thread.currentThread() == this.thread;
/* 449 */     this.queue.add(chunk -> {
/*     */           if (!ref.isValid())
/*     */             return; 
/*     */           chunk.putComponent(ref, componentType, component);
/*     */         });
/*     */   }
/*     */   
/*     */   public <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull Ref<ECS_TYPE> ref, @Nonnull Event param) {
/* 457 */     assert Thread.currentThread() == this.thread;
/* 458 */     this.store.internal_invoke(this, ref, param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull EntityEventType<ECS_TYPE, Event> systemType, @Nonnull Ref<ECS_TYPE> ref, @Nonnull Event param) {
/* 465 */     assert Thread.currentThread() == this.thread;
/* 466 */     this.store.internal_invoke(this, systemType, ref, param);
/*     */   }
/*     */ 
/*     */   
/*     */   public <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull Event param) {
/* 471 */     assert Thread.currentThread() == this.thread;
/* 472 */     this.store.internal_invoke(this, param);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <Event extends com.hypixel.hytale.component.system.EcsEvent> void invoke(@Nonnull WorldEventType<ECS_TYPE, Event> systemType, @Nonnull Event param) {
/* 478 */     assert Thread.currentThread() == this.thread;
/* 479 */     this.store.internal_invoke(this, systemType, param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void track(@Nonnull Ref<ECS_TYPE> ref) {
/* 488 */     this.trackedRef = ref;
/*     */   }
/*     */   
/*     */   private void testRemovedTracked(@Nonnull Ref<ECS_TYPE> ref) {
/* 492 */     if (ref.equals(this.trackedRef)) {
/* 493 */       this.trackedRefRemoved = true;
/*     */     }
/* 495 */     if (this.parentBuffer != null) {
/* 496 */       this.parentBuffer.testRemovedTracked(ref);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean consumeWasTrackedRefRemoved() {
/* 506 */     if (this.trackedRef == null) throw new IllegalStateException("Not tracking any ref!"); 
/* 507 */     boolean wasRemoved = this.trackedRefRemoved;
/* 508 */     this.trackedRefRemoved = false;
/* 509 */     return wasRemoved;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void consume() {
/* 516 */     this.trackedRef = null;
/* 517 */     this.trackedRefRemoved = false;
/*     */     
/* 519 */     assert Thread.currentThread() == this.thread;
/* 520 */     for (; !this.queue.isEmpty(); ((Consumer<Store<ECS_TYPE>>)this.queue.pop()).accept(this.store));
/* 521 */     this.store.storeCommandBuffer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CommandBuffer<ECS_TYPE> fork() {
/* 531 */     CommandBuffer<ECS_TYPE> forkedBuffer = this.store.takeCommandBuffer();
/* 532 */     forkedBuffer.parentBuffer = this;
/* 533 */     return forkedBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeParallel(@Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/* 542 */     this.trackedRef = null;
/* 543 */     this.trackedRefRemoved = false;
/* 544 */     this.parentBuffer = null;
/*     */     
/* 546 */     for (; !this.queue.isEmpty(); commandBuffer.queue.add(this.queue.pop()));
/* 547 */     this.store.storeCommandBuffer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setThread() {
/* 558 */     boolean areAssertionsEnabled = false;
/*     */     
/* 560 */     assert areAssertionsEnabled = true;
/*     */     
/* 562 */     if (!areAssertionsEnabled) throw new AssertionError("setThread should only be called when assertions are enabled!");
/*     */     
/* 564 */     this.thread = Thread.currentThread();
/* 565 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateEmpty() {
/* 572 */     if (!this.queue.isEmpty()) throw new AssertionError("CommandBuffer must be empty when returned to store!"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\CommandBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */