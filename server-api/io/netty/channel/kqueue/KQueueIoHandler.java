/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.DefaultSelectStrategyFactory;
/*     */ import io.netty.channel.IoHandle;
/*     */ import io.netty.channel.IoHandler;
/*     */ import io.netty.channel.IoHandlerContext;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.IoOps;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.SelectStrategy;
/*     */ import io.netty.channel.SelectStrategyFactory;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.util.IntSupplier;
/*     */ import io.netty.util.collection.LongObjectHashMap;
/*     */ import io.netty.util.collection.LongObjectMap;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KQueueIoHandler
/*     */   implements IoHandler
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(KQueueIoHandler.class);
/*     */   
/*  55 */   private static final AtomicIntegerFieldUpdater<KQueueIoHandler> WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(KQueueIoHandler.class, "wakenUp");
/*     */   
/*     */   private static final int KQUEUE_WAKE_UP_IDENT = 0;
/*     */   
/*     */   private static final int KQUEUE_MAX_TIMEOUT_SECONDS = 86399;
/*     */   
/*     */   private final boolean allowGrowing;
/*     */   
/*     */   private final FileDescriptor kqueueFd;
/*     */   
/*     */   private final KQueueEventArray changeList;
/*     */   
/*     */   private final KQueueEventArray eventList;
/*     */   
/*     */   private final SelectStrategy selectStrategy;
/*     */   
/*     */   private final NativeArrays nativeArrays;
/*     */   
/*     */   private final IntSupplier selectNowSupplier;
/*     */   
/*     */   private final ThreadAwareExecutor executor;
/*     */   
/*     */   private final Queue<DefaultKqueueIoRegistration> cancelledRegistrations;
/*     */   
/*     */   private final LongObjectMap<DefaultKqueueIoRegistration> registrations;
/*     */   
/*     */   private int numChannels;
/*     */   
/*     */   private long nextId;
/*     */   private volatile int wakenUp;
/*     */   
/*     */   private long generateNextId() {
/*  87 */     boolean reset = false;
/*     */     while (true) {
/*  89 */       if (this.nextId == Long.MAX_VALUE) {
/*  90 */         if (reset) {
/*  91 */           throw new IllegalStateException("All possible ids in use");
/*     */         }
/*  93 */         reset = true;
/*     */       } 
/*  95 */       this.nextId++;
/*  96 */       if (this.nextId == 0L) {
/*     */         continue;
/*     */       }
/*  99 */       if (!this.registrations.containsKey(this.nextId)) {
/* 100 */         return this.nextId;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory() {
/* 109 */     return newFactory(0, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory(final int maxEvents, final SelectStrategyFactory selectStrategyFactory) {
/* 117 */     KQueue.ensureAvailability();
/* 118 */     ObjectUtil.checkPositiveOrZero(maxEvents, "maxEvents");
/* 119 */     ObjectUtil.checkNotNull(selectStrategyFactory, "selectStrategyFactory");
/* 120 */     return new IoHandlerFactory()
/*     */       {
/*     */         public IoHandler newHandler(ThreadAwareExecutor executor) {
/* 123 */           return new KQueueIoHandler(executor, maxEvents, selectStrategyFactory.newSelectStrategy());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isChangingThreadSupported() {
/* 128 */           return true; }
/*     */       }; } private KQueueIoHandler(ThreadAwareExecutor executor, int maxEvents, SelectStrategy strategy) { KQueue.ensureAvailability();
/*     */     this.selectNowSupplier = new IntSupplier() { public int get() throws Exception { return KQueueIoHandler.this.kqueueWaitNow(); } }
/*     */       ;
/*     */     this.cancelledRegistrations = new ArrayDeque<>();
/*     */     this.registrations = (LongObjectMap<DefaultKqueueIoRegistration>)new LongObjectHashMap(4096);
/* 134 */     this.executor = (ThreadAwareExecutor)ObjectUtil.checkNotNull(executor, "executor");
/* 135 */     this.selectStrategy = (SelectStrategy)ObjectUtil.checkNotNull(strategy, "strategy");
/* 136 */     this.kqueueFd = Native.newKQueue();
/* 137 */     if (maxEvents == 0) {
/* 138 */       this.allowGrowing = true;
/* 139 */       maxEvents = 4096;
/*     */     } else {
/* 141 */       this.allowGrowing = false;
/*     */     } 
/* 143 */     this.changeList = new KQueueEventArray(maxEvents);
/* 144 */     this.eventList = new KQueueEventArray(maxEvents);
/* 145 */     this.nativeArrays = new NativeArrays();
/* 146 */     int result = Native.keventAddUserEvent(this.kqueueFd.intValue(), 0);
/* 147 */     if (result < 0) {
/* 148 */       destroy();
/* 149 */       throw new IllegalStateException("kevent failed to add user event with errno: " + -result);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void wakeup() {
/* 155 */     if (!this.executor.isExecutorThread(Thread.currentThread()) && WAKEN_UP_UPDATER
/* 156 */       .compareAndSet(this, 0, 1)) {
/* 157 */       wakeup0();
/*     */     }
/*     */   }
/*     */   
/*     */   private void wakeup0() {
/* 162 */     Native.keventTriggerUserEvent(this.kqueueFd.intValue(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int kqueueWait(IoHandlerContext context, boolean oldWakeup) throws IOException {
/* 172 */     if (oldWakeup && !context.canBlock()) {
/* 173 */       return kqueueWaitNow();
/*     */     }
/*     */     
/* 176 */     long totalDelay = context.delayNanos(System.nanoTime());
/* 177 */     int delaySeconds = (int)Math.min(totalDelay / 1000000000L, 86399L);
/* 178 */     int delayNanos = (int)(totalDelay % 1000000000L);
/* 179 */     return kqueueWait(delaySeconds, delayNanos);
/*     */   }
/*     */   
/*     */   private int kqueueWaitNow() throws IOException {
/* 183 */     return kqueueWait(0, 0);
/*     */   }
/*     */   
/*     */   private int kqueueWait(int timeoutSec, int timeoutNs) throws IOException {
/* 187 */     int numEvents = Native.keventWait(this.kqueueFd.intValue(), this.changeList, this.eventList, timeoutSec, timeoutNs);
/* 188 */     this.changeList.clear();
/* 189 */     return numEvents;
/*     */   }
/*     */   
/*     */   private void processReady(int ready) {
/* 193 */     for (int i = 0; i < ready; i++) {
/* 194 */       short filter = this.eventList.filter(i);
/* 195 */       short flags = this.eventList.flags(i);
/* 196 */       int ident = this.eventList.ident(i);
/* 197 */       if (filter == Native.EVFILT_USER || (flags & Native.EV_ERROR) != 0) {
/*     */ 
/*     */         
/* 200 */         assert filter != Native.EVFILT_USER || (filter == Native.EVFILT_USER && ident == 0);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 205 */         long id = this.eventList.udata(i);
/* 206 */         DefaultKqueueIoRegistration registration = (DefaultKqueueIoRegistration)this.registrations.get(id);
/* 207 */         if (registration == null) {
/*     */ 
/*     */ 
/*     */           
/* 211 */           logger.warn("events[{}]=[{}, {}, {}] had no registration!", new Object[] { Integer.valueOf(i), Integer.valueOf(ident), Long.valueOf(id), Short.valueOf(filter) });
/*     */         } else {
/*     */           
/* 214 */           registration.handle(ident, filter, flags, this.eventList.fflags(i), this.eventList.data(i), id);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public int run(IoHandlerContext context) {
/* 220 */     int handled = 0;
/*     */     try {
/* 222 */       int strategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, !context.canBlock());
/* 223 */       switch (strategy) {
/*     */         case -2:
/* 225 */           if (context.shouldReportActiveIoTime()) {
/* 226 */             context.reportActiveIoTime(0L);
/*     */           }
/* 228 */           return 0;
/*     */ 
/*     */ 
/*     */         
/*     */         case -3:
/*     */         case -1:
/* 234 */           strategy = kqueueWait(context, (WAKEN_UP_UPDATER.getAndSet(this, 0) == 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 264 */           if (this.wakenUp == 1) {
/* 265 */             wakeup0();
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 271 */       if (strategy > 0) {
/* 272 */         handled = strategy;
/* 273 */         if (context.shouldReportActiveIoTime()) {
/*     */           
/* 275 */           long activeIoStartTimeNanos = System.nanoTime();
/* 276 */           processReady(strategy);
/* 277 */           long activeIoEndTimeNanos = System.nanoTime();
/* 278 */           context.reportActiveIoTime(activeIoEndTimeNanos - activeIoStartTimeNanos);
/*     */         } else {
/* 280 */           processReady(strategy);
/*     */         } 
/* 282 */       } else if (context.shouldReportActiveIoTime()) {
/* 283 */         context.reportActiveIoTime(0L);
/*     */       } 
/*     */       
/* 286 */       if (this.allowGrowing && strategy == this.eventList.capacity())
/*     */       {
/* 288 */         this.eventList.realloc(false);
/*     */       }
/* 290 */     } catch (Error e) {
/* 291 */       throw e;
/* 292 */     } catch (Throwable t) {
/* 293 */       handleLoopException(t);
/*     */     } finally {
/* 295 */       processCancelledRegistrations();
/*     */     } 
/* 297 */     return handled;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processCancelledRegistrations() {
/*     */     while (true) {
/* 303 */       DefaultKqueueIoRegistration cancelledRegistration = this.cancelledRegistrations.poll();
/* 304 */       if (cancelledRegistration == null) {
/*     */         return;
/*     */       }
/* 307 */       DefaultKqueueIoRegistration removed = (DefaultKqueueIoRegistration)this.registrations.remove(cancelledRegistration.id);
/* 308 */       assert removed == cancelledRegistration;
/* 309 */       if (removed.isHandleForChannel()) {
/* 310 */         this.numChannels--;
/*     */       }
/* 312 */       removed.handle.unregistered();
/*     */     } 
/*     */   }
/*     */   
/*     */   int numRegisteredChannels() {
/* 317 */     return this.numChannels;
/*     */   }
/*     */   
/*     */   List<Channel> registeredChannelsList() {
/* 321 */     LongObjectMap<DefaultKqueueIoRegistration> ch = this.registrations;
/* 322 */     if (ch.isEmpty()) {
/* 323 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 326 */     List<Channel> channels = new ArrayList<>(ch.size());
/*     */     
/* 328 */     for (DefaultKqueueIoRegistration registration : ch.values()) {
/* 329 */       if (registration.handle instanceof AbstractKQueueChannel.AbstractKQueueUnsafe) {
/* 330 */         channels.add(((AbstractKQueueChannel.AbstractKQueueUnsafe)registration.handle).channel());
/*     */       }
/*     */     } 
/* 333 */     return Collections.unmodifiableList(channels);
/*     */   }
/*     */   
/*     */   private static void handleLoopException(Throwable t) {
/* 337 */     logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 342 */       Thread.sleep(1000L);
/* 343 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareToDestroy() {
/*     */     try {
/* 351 */       kqueueWaitNow();
/* 352 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     DefaultKqueueIoRegistration[] copy = (DefaultKqueueIoRegistration[])this.registrations.values().toArray((Object[])new DefaultKqueueIoRegistration[0]);
/*     */     
/* 360 */     for (DefaultKqueueIoRegistration reg : copy) {
/* 361 */       reg.close();
/*     */     }
/*     */     
/* 364 */     processCancelledRegistrations();
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*     */     try {
/*     */       try {
/* 371 */         this.kqueueFd.close();
/* 372 */       } catch (IOException e) {
/* 373 */         logger.warn("Failed to close the kqueue fd.", e);
/*     */       } 
/*     */     } finally {
/*     */       
/* 377 */       this.nativeArrays.free();
/* 378 */       this.changeList.free();
/* 379 */       this.eventList.free();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IoRegistration register(IoHandle handle) {
/* 385 */     KQueueIoHandle kqueueHandle = cast(handle);
/* 386 */     if (kqueueHandle.ident() == 0) {
/* 387 */       throw new IllegalArgumentException("ident 0 is reserved for internal usage");
/*     */     }
/*     */     
/* 390 */     DefaultKqueueIoRegistration registration = new DefaultKqueueIoRegistration(this.executor, kqueueHandle);
/*     */     
/* 392 */     DefaultKqueueIoRegistration old = (DefaultKqueueIoRegistration)this.registrations.put(registration.id, registration);
/* 393 */     if (old != null) {
/*     */       
/* 395 */       this.registrations.put(old.id, old);
/* 396 */       throw new IllegalStateException();
/*     */     } 
/* 398 */     if (registration.isHandleForChannel()) {
/* 399 */       this.numChannels++;
/*     */     }
/* 401 */     handle.registered();
/* 402 */     return registration;
/*     */   }
/*     */   
/*     */   private static KQueueIoHandle cast(IoHandle handle) {
/* 406 */     if (handle instanceof KQueueIoHandle) {
/* 407 */       return (KQueueIoHandle)handle;
/*     */     }
/* 409 */     throw new IllegalArgumentException("IoHandle of type " + StringUtil.simpleClassName(handle) + " not supported");
/*     */   }
/*     */   
/*     */   private static KQueueIoOps cast(IoOps ops) {
/* 413 */     if (ops instanceof KQueueIoOps) {
/* 414 */       return (KQueueIoOps)ops;
/*     */     }
/* 416 */     throw new IllegalArgumentException("IoOps of type " + StringUtil.simpleClassName(ops) + " not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 421 */     return KQueueIoHandle.class.isAssignableFrom(handleType);
/*     */   }
/*     */   
/*     */   private final class DefaultKqueueIoRegistration implements IoRegistration {
/*     */     private boolean cancellationPending;
/* 426 */     private final AtomicBoolean canceled = new AtomicBoolean();
/* 427 */     private final KQueueIoEvent event = new KQueueIoEvent();
/*     */     
/*     */     final KQueueIoHandle handle;
/*     */     final long id;
/*     */     private final ThreadAwareExecutor executor;
/*     */     
/*     */     DefaultKqueueIoRegistration(ThreadAwareExecutor executor, KQueueIoHandle handle) {
/* 434 */       this.executor = executor;
/* 435 */       this.handle = handle;
/* 436 */       this.id = KQueueIoHandler.this.generateNextId();
/*     */     }
/*     */     
/*     */     boolean isHandleForChannel() {
/* 440 */       return this.handle instanceof AbstractKQueueChannel.AbstractKQueueUnsafe;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T attachment() {
/* 446 */       return (T)KQueueIoHandler.this.nativeArrays;
/*     */     }
/*     */ 
/*     */     
/*     */     public long submit(IoOps ops) {
/* 451 */       KQueueIoOps kQueueIoOps = KQueueIoHandler.cast(ops);
/* 452 */       if (!isValid()) {
/* 453 */         return -1L;
/*     */       }
/* 455 */       short filter = kQueueIoOps.filter();
/* 456 */       short flags = kQueueIoOps.flags();
/* 457 */       int fflags = kQueueIoOps.fflags();
/* 458 */       if (this.executor.isExecutorThread(Thread.currentThread())) {
/* 459 */         evSet(filter, flags, fflags);
/*     */       } else {
/* 461 */         this.executor.execute(() -> evSet(filter, flags, fflags));
/*     */       } 
/* 463 */       return 0L;
/*     */     }
/*     */     
/*     */     void handle(int ident, short filter, short flags, int fflags, long data, long udata) {
/* 467 */       if (this.cancellationPending) {
/*     */         return;
/*     */       }
/*     */       
/* 471 */       this.event.update(ident, filter, flags, fflags, data, udata);
/* 472 */       this.handle.handle(this, this.event);
/*     */     }
/*     */     
/*     */     private void evSet(short filter, short flags, int fflags) {
/* 476 */       if (this.cancellationPending) {
/*     */         return;
/*     */       }
/*     */       
/* 480 */       KQueueIoHandler.this.changeList.evSet(this.handle.ident(), filter, flags, fflags, 0L, this.id);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 485 */       return !this.canceled.get();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 490 */       if (!this.canceled.compareAndSet(false, true)) {
/* 491 */         return false;
/*     */       }
/* 493 */       if (this.executor.isExecutorThread(Thread.currentThread())) {
/* 494 */         cancel0();
/*     */       } else {
/* 496 */         this.executor.execute(this::cancel0);
/*     */       } 
/* 498 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void cancel0() {
/* 505 */       this.cancellationPending = true;
/* 506 */       KQueueIoHandler.this.cancelledRegistrations.offer(this);
/*     */     }
/*     */     
/*     */     void close() {
/* 510 */       cancel();
/*     */       try {
/* 512 */         this.handle.close();
/* 513 */       } catch (Exception e) {
/* 514 */         KQueueIoHandler.logger.debug("Exception during closing " + this.handle, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueIoHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */