/*     */ package io.netty.channel.epoll;
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
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.collection.IntObjectMap;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EpollIoHandler
/*     */   implements IoHandler
/*     */ {
/*  55 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollIoHandler.class);
/*     */   
/*  57 */   private static final long EPOLL_WAIT_MILLIS_THRESHOLD = SystemPropertyUtil.getLong("io.netty.channel.epoll.epollWaitThreshold", 10L);
/*     */ 
/*     */   
/*     */   private long prevDeadlineNanos;
/*     */ 
/*     */   
/*     */   private FileDescriptor epollFd;
/*     */ 
/*     */   
/*     */   private FileDescriptor eventFd;
/*     */ 
/*     */   
/*     */   private FileDescriptor timerFd;
/*     */ 
/*     */   
/*     */   private final IntObjectMap<DefaultEpollIoRegistration> registrations;
/*     */ 
/*     */   
/*     */   private final boolean allowGrowing;
/*     */   
/*     */   private final EpollEventArray events;
/*     */   
/*     */   private final NativeArrays nativeArrays;
/*     */   
/*     */   private final SelectStrategy selectStrategy;
/*     */   
/*     */   private final IntSupplier selectNowSupplier;
/*     */   
/*     */   private final ThreadAwareExecutor executor;
/*     */   
/*     */   private static final long AWAKE = -1L;
/*     */   
/*     */   private static final long NONE = 9223372036854775807L;
/*     */   
/*     */   private final AtomicLong nextWakeupNanos;
/*     */   
/*     */   private boolean pendingWakeup;
/*     */   
/*     */   private int numChannels;
/*     */   
/*     */   private static final long MAX_SCHEDULED_TIMERFD_NS = 999999999L;
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory() {
/* 101 */     return newFactory(0, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IoHandlerFactory newFactory(final int maxEvents, final SelectStrategyFactory selectStrategyFactory) {
/* 109 */     Epoll.ensureAvailability();
/* 110 */     ObjectUtil.checkPositiveOrZero(maxEvents, "maxEvents");
/* 111 */     ObjectUtil.checkNotNull(selectStrategyFactory, "selectStrategyFactory");
/* 112 */     return new IoHandlerFactory()
/*     */       {
/*     */         public IoHandler newHandler(ThreadAwareExecutor executor) {
/* 115 */           return new EpollIoHandler(executor, maxEvents, selectStrategyFactory.newSelectStrategy());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isChangingThreadSupported() {
/* 120 */           return true; }
/*     */       }; } EpollIoHandler(ThreadAwareExecutor executor, int maxEvents, SelectStrategy strategy) { Epoll.ensureAvailability();
/*     */     this.prevDeadlineNanos = Long.MAX_VALUE;
/*     */     this.registrations = (IntObjectMap<DefaultEpollIoRegistration>)new IntObjectHashMap(4096);
/*     */     this.selectNowSupplier = new IntSupplier() { public int get() throws Exception { return EpollIoHandler.this.epollWaitNow(); } }
/*     */       ;
/*     */     this.nextWakeupNanos = new AtomicLong(-1L);
/* 127 */     this.executor = (ThreadAwareExecutor)ObjectUtil.checkNotNull(executor, "executor");
/* 128 */     this.selectStrategy = (SelectStrategy)ObjectUtil.checkNotNull(strategy, "strategy");
/* 129 */     if (maxEvents == 0) {
/* 130 */       this.allowGrowing = true;
/* 131 */       this.events = new EpollEventArray(4096);
/*     */     } else {
/* 133 */       this.allowGrowing = false;
/* 134 */       this.events = new EpollEventArray(maxEvents);
/*     */     } 
/* 136 */     this.nativeArrays = new NativeArrays();
/* 137 */     openFileDescriptors(); }
/*     */ 
/*     */   
/*     */   private static EpollIoHandle cast(IoHandle handle) {
/* 141 */     if (handle instanceof EpollIoHandle) {
/* 142 */       return (EpollIoHandle)handle;
/*     */     }
/* 144 */     throw new IllegalArgumentException("IoHandle of type " + StringUtil.simpleClassName(handle) + " not supported");
/*     */   }
/*     */   
/*     */   private static EpollIoOps cast(IoOps ops) {
/* 148 */     if (ops instanceof EpollIoOps) {
/* 149 */       return (EpollIoOps)ops;
/*     */     }
/* 151 */     throw new IllegalArgumentException("IoOps of type " + StringUtil.simpleClassName(ops) + " not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openFileDescriptors() {
/* 160 */     boolean success = false;
/* 161 */     FileDescriptor epollFd = null;
/* 162 */     FileDescriptor eventFd = null;
/* 163 */     FileDescriptor timerFd = null;
/*     */     try {
/* 165 */       this.epollFd = epollFd = Native.newEpollCreate();
/* 166 */       this.eventFd = eventFd = Native.newEventFd();
/*     */ 
/*     */       
/*     */       try {
/* 170 */         Native.epollCtlAdd(epollFd.intValue(), eventFd.intValue(), Native.EPOLLIN | Native.EPOLLET);
/* 171 */       } catch (IOException e) {
/* 172 */         throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", e);
/*     */       } 
/* 174 */       this.timerFd = timerFd = Native.newTimerFd();
/*     */ 
/*     */       
/*     */       try {
/* 178 */         Native.epollCtlAdd(epollFd.intValue(), timerFd.intValue(), Native.EPOLLIN | Native.EPOLLET);
/* 179 */       } catch (IOException e) {
/* 180 */         throw new IllegalStateException("Unable to add timerFd filedescriptor to epoll", e);
/*     */       } 
/* 182 */       success = true;
/*     */     } finally {
/* 184 */       if (!success) {
/* 185 */         closeFileDescriptor(epollFd);
/* 186 */         closeFileDescriptor(eventFd);
/* 187 */         closeFileDescriptor(timerFd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeFileDescriptor(FileDescriptor fd) {
/* 193 */     if (fd != null) {
/*     */       try {
/* 195 */         fd.close();
/* 196 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wakeup() {
/* 204 */     if (!this.executor.isExecutorThread(Thread.currentThread()) && this.nextWakeupNanos.getAndSet(-1L) != -1L)
/*     */     {
/* 206 */       Native.eventFdWrite(this.eventFd.intValue(), 1L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareToDestroy() {
/* 214 */     DefaultEpollIoRegistration[] copy = (DefaultEpollIoRegistration[])this.registrations.values().toArray((Object[])new DefaultEpollIoRegistration[0]);
/*     */     
/* 216 */     for (DefaultEpollIoRegistration reg : copy) {
/* 217 */       reg.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*     */     try {
/* 224 */       closeFileDescriptors();
/*     */     } finally {
/* 226 */       this.nativeArrays.free();
/* 227 */       this.events.free();
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class DefaultEpollIoRegistration implements IoRegistration {
/*     */     private final ThreadAwareExecutor executor;
/* 233 */     private final AtomicBoolean canceled = new AtomicBoolean();
/*     */     final EpollIoHandle handle;
/*     */     
/*     */     DefaultEpollIoRegistration(ThreadAwareExecutor executor, EpollIoHandle handle) {
/* 237 */       this.executor = executor;
/* 238 */       this.handle = handle;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T attachment() {
/* 244 */       return (T)EpollIoHandler.this.nativeArrays;
/*     */     }
/*     */ 
/*     */     
/*     */     public long submit(IoOps ops) {
/* 249 */       EpollIoOps epollIoOps = EpollIoHandler.cast(ops);
/*     */       try {
/* 251 */         if (!isValid()) {
/* 252 */           return -1L;
/*     */         }
/* 254 */         Native.epollCtlMod(EpollIoHandler.this.epollFd.intValue(), this.handle.fd().intValue(), epollIoOps.value);
/* 255 */         return epollIoOps.value;
/* 256 */       } catch (IOException e) {
/* 257 */         throw new UncheckedIOException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 263 */       return !this.canceled.get();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 268 */       if (!this.canceled.compareAndSet(false, true)) {
/* 269 */         return false;
/*     */       }
/* 271 */       if (this.executor.isExecutorThread(Thread.currentThread())) {
/* 272 */         cancel0();
/*     */       } else {
/* 274 */         this.executor.execute(this::cancel0);
/*     */       } 
/* 276 */       return true;
/*     */     }
/*     */     
/*     */     private void cancel0() {
/* 280 */       int fd = this.handle.fd().intValue();
/* 281 */       DefaultEpollIoRegistration old = (DefaultEpollIoRegistration)EpollIoHandler.this.registrations.remove(fd);
/* 282 */       if (old != null) {
/* 283 */         if (old != this) {
/*     */           
/* 285 */           EpollIoHandler.this.registrations.put(fd, old); return;
/*     */         } 
/* 287 */         if (old.handle instanceof AbstractEpollChannel.AbstractEpollUnsafe) {
/* 288 */           EpollIoHandler.this.numChannels--;
/*     */         }
/* 290 */         if (this.handle.fd().isOpen()) {
/*     */           
/*     */           try {
/*     */             
/* 294 */             Native.epollCtlDel(EpollIoHandler.this.epollFd.intValue(), fd);
/* 295 */           } catch (IOException e) {
/* 296 */             EpollIoHandler.logger.debug("Unable to remove fd {} from epoll {}", Integer.valueOf(fd), Integer.valueOf(EpollIoHandler.this.epollFd.intValue()));
/*     */           } 
/*     */         }
/* 299 */         this.handle.unregistered();
/*     */       } 
/*     */     }
/*     */     
/*     */     void close() {
/*     */       try {
/* 305 */         cancel();
/* 306 */       } catch (Exception e) {
/* 307 */         EpollIoHandler.logger.debug("Exception during canceling " + this, e);
/*     */       } 
/*     */       try {
/* 310 */         this.handle.close();
/* 311 */       } catch (Exception e) {
/* 312 */         EpollIoHandler.logger.debug("Exception during closing " + this.handle, e);
/*     */       } 
/*     */     }
/*     */     
/*     */     void handle(long ev) {
/* 317 */       this.handle.handle(this, EpollIoOps.eventOf((int)ev));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IoRegistration register(IoHandle handle) throws Exception {
/* 324 */     EpollIoHandle epollHandle = cast(handle);
/* 325 */     DefaultEpollIoRegistration registration = new DefaultEpollIoRegistration(this.executor, epollHandle);
/* 326 */     int fd = epollHandle.fd().intValue();
/* 327 */     Native.epollCtlAdd(this.epollFd.intValue(), fd, EpollIoOps.EPOLLERR.value);
/* 328 */     DefaultEpollIoRegistration old = (DefaultEpollIoRegistration)this.registrations.put(fd, registration);
/*     */ 
/*     */ 
/*     */     
/* 332 */     assert old == null || !old.isValid();
/*     */     
/* 334 */     if (epollHandle instanceof AbstractEpollChannel.AbstractEpollUnsafe) {
/* 335 */       this.numChannels++;
/*     */     }
/* 337 */     handle.registered();
/* 338 */     return registration;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 343 */     return EpollIoHandle.class.isAssignableFrom(handleType);
/*     */   }
/*     */   
/*     */   int numRegisteredChannels() {
/* 347 */     return this.numChannels;
/*     */   }
/*     */   
/*     */   List<Channel> registeredChannelsList() {
/* 351 */     IntObjectMap<DefaultEpollIoRegistration> ch = this.registrations;
/* 352 */     if (ch.isEmpty()) {
/* 353 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 356 */     List<Channel> channels = new ArrayList<>(ch.size());
/* 357 */     for (DefaultEpollIoRegistration registration : ch.values()) {
/* 358 */       if (registration.handle instanceof AbstractEpollChannel.AbstractEpollUnsafe) {
/* 359 */         channels.add(((AbstractEpollChannel.AbstractEpollUnsafe)registration.handle).channel());
/*     */       }
/*     */     } 
/* 362 */     return Collections.unmodifiableList(channels);
/*     */   }
/*     */   
/*     */   private long epollWait(IoHandlerContext context, long deadlineNanos) throws IOException {
/* 366 */     if (deadlineNanos == Long.MAX_VALUE) {
/* 367 */       return Native.epollWait(this.epollFd, this.events, this.timerFd, 2147483647, 0, EPOLL_WAIT_MILLIS_THRESHOLD);
/*     */     }
/*     */     
/* 370 */     long totalDelay = context.delayNanos(System.nanoTime());
/* 371 */     int delaySeconds = (int)Math.min(totalDelay / 1000000000L, 2147483647L);
/* 372 */     int delayNanos = (int)Math.min(totalDelay - delaySeconds * 1000000000L, 999999999L);
/* 373 */     return Native.epollWait(this.epollFd, this.events, this.timerFd, delaySeconds, delayNanos, EPOLL_WAIT_MILLIS_THRESHOLD);
/*     */   }
/*     */   
/*     */   private int epollWaitNoTimerChange() throws IOException {
/* 377 */     return Native.epollWait(this.epollFd, this.events, false);
/*     */   }
/*     */   
/*     */   private int epollWaitNow() throws IOException {
/* 381 */     return Native.epollWait(this.epollFd, this.events, true);
/*     */   }
/*     */   
/*     */   private int epollBusyWait() throws IOException {
/* 385 */     return Native.epollBusyWait(this.epollFd, this.events);
/*     */   }
/*     */ 
/*     */   
/*     */   private int epollWaitTimeboxed() throws IOException {
/* 390 */     return Native.epollWait(this.epollFd, this.events, 1000);
/*     */   }
/*     */ 
/*     */   
/*     */   public int run(IoHandlerContext context) {
/* 395 */     int handled = 0; try {
/*     */       long curDeadlineNanos;
/* 397 */       int strategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, !context.canBlock());
/* 398 */       switch (strategy) {
/*     */         case -2:
/* 400 */           if (context.shouldReportActiveIoTime()) {
/* 401 */             context.reportActiveIoTime(0L);
/*     */           }
/* 403 */           return 0;
/*     */         
/*     */         case -3:
/* 406 */           strategy = epollBusyWait();
/*     */           break;
/*     */         
/*     */         case -1:
/* 410 */           if (this.pendingWakeup) {
/*     */ 
/*     */             
/* 413 */             strategy = epollWaitTimeboxed();
/* 414 */             if (strategy != 0) {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 419 */             logger.warn("Missed eventfd write (not seen after > 1 second)");
/* 420 */             this.pendingWakeup = false;
/* 421 */             if (!context.canBlock()) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 427 */           curDeadlineNanos = context.deadlineNanos();
/* 428 */           if (curDeadlineNanos == -1L) {
/* 429 */             curDeadlineNanos = Long.MAX_VALUE;
/*     */           }
/* 431 */           this.nextWakeupNanos.set(curDeadlineNanos);
/*     */           try {
/* 433 */             if (context.canBlock()) {
/* 434 */               if (curDeadlineNanos == this.prevDeadlineNanos) {
/*     */                 
/* 436 */                 strategy = epollWaitNoTimerChange();
/*     */               } else {
/*     */                 
/* 439 */                 long result = epollWait(context, curDeadlineNanos);
/*     */ 
/*     */                 
/* 442 */                 strategy = Native.epollReady(result);
/* 443 */                 this.prevDeadlineNanos = Native.epollTimerWasUsed(result) ? curDeadlineNanos : Long.MAX_VALUE;
/*     */               }
/*     */             
/*     */             }
/*     */           } finally {
/*     */             
/* 449 */             if (this.nextWakeupNanos.get() == -1L || this.nextWakeupNanos.getAndSet(-1L) == -1L) {
/* 450 */               this.pendingWakeup = true;
/*     */             }
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 456 */       if (strategy > 0) {
/* 457 */         handled = strategy;
/* 458 */         if (context.shouldReportActiveIoTime()) {
/* 459 */           long activeIoStartTimeNanos = System.nanoTime();
/* 460 */           if (processReady(this.events, strategy)) {
/* 461 */             this.prevDeadlineNanos = Long.MAX_VALUE;
/*     */           }
/* 463 */           long activeIoEndTimeNanos = System.nanoTime();
/* 464 */           context.reportActiveIoTime(activeIoEndTimeNanos - activeIoStartTimeNanos);
/*     */         }
/* 466 */         else if (processReady(this.events, strategy)) {
/* 467 */           this.prevDeadlineNanos = Long.MAX_VALUE;
/*     */         }
/*     */       
/* 470 */       } else if (context.shouldReportActiveIoTime()) {
/* 471 */         context.reportActiveIoTime(0L);
/*     */       } 
/*     */       
/* 474 */       if (this.allowGrowing && strategy == this.events.length())
/*     */       {
/* 476 */         this.events.increase();
/*     */       }
/* 478 */     } catch (Error e) {
/* 479 */       throw e;
/* 480 */     } catch (Throwable t) {
/* 481 */       handleLoopException(t);
/*     */     } 
/* 483 */     return handled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleLoopException(Throwable t) {
/* 490 */     logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 495 */       Thread.sleep(1000L);
/* 496 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processReady(EpollEventArray events, int ready) {
/* 503 */     boolean timerFired = false;
/* 504 */     for (int i = 0; i < ready; i++) {
/* 505 */       int fd = events.fd(i);
/* 506 */       if (fd == this.eventFd.intValue()) {
/* 507 */         this.pendingWakeup = false;
/* 508 */       } else if (fd == this.timerFd.intValue()) {
/* 509 */         timerFired = true;
/*     */       } else {
/* 511 */         long ev = events.events(i);
/*     */         
/* 513 */         DefaultEpollIoRegistration registration = (DefaultEpollIoRegistration)this.registrations.get(fd);
/* 514 */         if (registration != null) {
/* 515 */           registration.handle(ev);
/*     */         } else {
/*     */           
/*     */           try {
/* 519 */             Native.epollCtlDel(this.epollFd.intValue(), fd);
/* 520 */           } catch (IOException iOException) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 529 */     return timerFired;
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
/*     */   public void closeFileDescriptors() {
/* 541 */     while (this.pendingWakeup) {
/*     */       try {
/* 543 */         int count = epollWaitTimeboxed();
/* 544 */         if (count == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 548 */         for (int i = 0; i < count; i++) {
/* 549 */           if (this.events.fd(i) == this.eventFd.intValue()) {
/* 550 */             this.pendingWakeup = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 554 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 559 */       this.eventFd.close();
/* 560 */     } catch (IOException e) {
/* 561 */       logger.warn("Failed to close the event fd.", e);
/*     */     } 
/*     */     try {
/* 564 */       this.timerFd.close();
/* 565 */     } catch (IOException e) {
/* 566 */       logger.warn("Failed to close the timer fd.", e);
/*     */     } 
/*     */     
/*     */     try {
/* 570 */       this.epollFd.close();
/* 571 */     } catch (IOException e) {
/* 572 */       logger.warn("Failed to close the epoll fd.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollIoHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */