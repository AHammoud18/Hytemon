/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.AbstractScheduledEventExecutor;
/*     */ import io.netty.util.concurrent.DefaultPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.concurrent.Ticker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ThreadExecutorMap;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManualIoEventLoop
/*     */   extends AbstractScheduledEventExecutor
/*     */   implements IoEventLoop
/*     */ {
/*     */   private static final Runnable WAKEUP_TASK = () -> {
/*     */     
/*     */     };
/*     */   private static final int ST_STARTED = 0;
/*     */   private static final int ST_SHUTTING_DOWN = 1;
/*     */   private static final int ST_SHUTDOWN = 2;
/*     */   private static final int ST_TERMINATED = 3;
/*     */   private final AtomicInteger state;
/*  60 */   private final Promise<?> terminationFuture = (Promise<?>)new DefaultPromise((EventExecutor)GlobalEventExecutor.INSTANCE);
/*  61 */   private final Queue<Runnable> taskQueue = PlatformDependent.newMpscQueue();
/*  62 */   private final IoHandlerContext nonBlockingContext = new IoHandlerContext()
/*     */     {
/*     */       public boolean canBlock() {
/*  65 */         assert ManualIoEventLoop.this.inEventLoop();
/*  66 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public long delayNanos(long currentTimeNanos) {
/*  71 */         assert ManualIoEventLoop.this.inEventLoop();
/*  72 */         return 0L;
/*     */       }
/*     */ 
/*     */       
/*     */       public long deadlineNanos() {
/*  77 */         assert ManualIoEventLoop.this.inEventLoop();
/*  78 */         return -1L;
/*     */       }
/*     */     };
/*  81 */   private final BlockingIoHandlerContext blockingContext = new BlockingIoHandlerContext();
/*     */   
/*     */   private final IoEventLoopGroup parent;
/*     */   
/*     */   private final AtomicReference<Thread> owningThread;
/*     */   
/*     */   private final IoHandler handler;
/*     */   
/*     */   private final Ticker ticker;
/*     */   
/*     */   private volatile long gracefulShutdownQuietPeriod;
/*     */   private volatile long gracefulShutdownTimeout;
/*     */   private long gracefulShutdownStartTime;
/*     */   private long lastExecutionTime;
/*     */   private boolean initialized;
/*     */   
/*     */   protected boolean canBlock() {
/*  98 */     return true;
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
/*     */   public ManualIoEventLoop(Thread owningThread, IoHandlerFactory factory) {
/* 113 */     this((IoEventLoopGroup)null, owningThread, factory);
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
/*     */   public ManualIoEventLoop(IoEventLoopGroup parent, Thread owningThread, IoHandlerFactory factory) {
/* 130 */     this(parent, owningThread, factory, Ticker.systemTicker());
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
/*     */ 
/*     */   
/*     */   public ManualIoEventLoop(IoEventLoopGroup parent, Thread owningThread, IoHandlerFactory factory, Ticker ticker) {
/* 150 */     this.parent = parent;
/* 151 */     this.owningThread = new AtomicReference<>(owningThread);
/* 152 */     this.handler = factory.newHandler((ThreadAwareExecutor)this);
/* 153 */     this.ticker = Objects.<Ticker>requireNonNull(ticker, "ticker");
/* 154 */     this.state = new AtomicInteger(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Ticker ticker() {
/* 159 */     return this.ticker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int runNonBlockingTasks(long timeoutNanos) {
/* 169 */     return runAllTasks(timeoutNanos, true);
/*     */   }
/*     */   
/*     */   private int runAllTasks(long timeoutNanos, boolean setCurrentExecutor) {
/* 173 */     assert inEventLoop();
/* 174 */     Queue<Runnable> taskQueue = this.taskQueue;
/*     */     
/* 176 */     boolean alwaysTrue = fetchFromScheduledTaskQueue(taskQueue);
/* 177 */     assert alwaysTrue;
/* 178 */     Runnable task = taskQueue.poll();
/* 179 */     if (task == null) {
/* 180 */       return 0;
/*     */     }
/* 182 */     EventExecutor old = setCurrentExecutor ? ThreadExecutorMap.setCurrentExecutor((EventExecutor)this) : null;
/*     */     try {
/* 184 */       long lastExecutionTime, deadline = (timeoutNanos > 0L) ? (getCurrentTimeNanos() + timeoutNanos) : 0L;
/* 185 */       int runTasks = 0;
/*     */       
/* 187 */       Ticker ticker = this.ticker;
/*     */       while (true) {
/* 189 */         safeExecute(task);
/*     */         
/* 191 */         runTasks++;
/*     */         
/* 193 */         if (timeoutNanos > 0L) {
/* 194 */           lastExecutionTime = ticker.nanoTime();
/* 195 */           if (lastExecutionTime - deadline >= 0L) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 200 */         task = taskQueue.poll();
/* 201 */         if (task == null) {
/* 202 */           lastExecutionTime = ticker.nanoTime();
/*     */           break;
/*     */         } 
/*     */       } 
/* 206 */       this.lastExecutionTime = lastExecutionTime;
/* 207 */       return runTasks;
/*     */     } finally {
/* 209 */       if (setCurrentExecutor) {
/* 210 */         ThreadExecutorMap.setCurrentExecutor(old);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int run(IoHandlerContext context, long runAllTasksTimeoutNanos) {
/* 216 */     if (!this.initialized) {
/* 217 */       if (this.owningThread.get() == null) {
/* 218 */         throw new IllegalStateException("Owning thread not set");
/*     */       }
/* 220 */       this.initialized = true;
/* 221 */       this.handler.initialize();
/*     */     } 
/* 223 */     EventExecutor old = ThreadExecutorMap.setCurrentExecutor((EventExecutor)this);
/*     */     try {
/* 225 */       if (isShuttingDown()) {
/* 226 */         if (this.terminationFuture.isDone())
/*     */         {
/* 228 */           return 0;
/*     */         }
/* 230 */         return runAllTasksBeforeDestroy();
/*     */       } 
/* 232 */       int ioTasks = this.handler.run(context);
/*     */       
/* 234 */       if (runAllTasksTimeoutNanos < 0L) {
/* 235 */         return ioTasks;
/*     */       }
/* 237 */       assert runAllTasksTimeoutNanos >= 0L;
/* 238 */       return ioTasks + runAllTasks(runAllTasksTimeoutNanos, false);
/*     */     } finally {
/* 240 */       ThreadExecutorMap.setCurrentExecutor(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int runAllTasksBeforeDestroy() {
/* 246 */     int run = runAllTasks(-1L, false);
/* 247 */     this.handler.prepareToDestroy();
/* 248 */     if (confirmShutdown()) {
/*     */       try {
/*     */         int r;
/* 251 */         this.handler.destroy();
/*     */         do {
/* 253 */           r = runAllTasks(-1L, false);
/* 254 */           run += r;
/* 255 */         } while (r != 0);
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 260 */         this.state.set(3);
/* 261 */         this.terminationFuture.setSuccess(null);
/*     */       } 
/*     */     }
/* 264 */     return run;
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
/*     */   public final int runNow(long runAllTasksTimeoutNanos) {
/* 281 */     checkCurrentThread();
/* 282 */     return run(this.nonBlockingContext, runAllTasksTimeoutNanos);
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
/*     */   public final int runNow() {
/* 295 */     checkCurrentThread();
/* 296 */     return run(this.nonBlockingContext, 0L);
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
/*     */   public final int run(long waitNanos, long runAllTasksTimeoutNanos) {
/*     */     IoHandlerContext context;
/* 314 */     checkCurrentThread();
/*     */ 
/*     */     
/* 317 */     if (waitNanos < 0L) {
/* 318 */       context = this.nonBlockingContext;
/*     */     } else {
/* 320 */       context = this.blockingContext;
/* 321 */       this.blockingContext.maxBlockingNanos = (waitNanos == 0L) ? Long.MAX_VALUE : waitNanos;
/*     */     } 
/* 323 */     return run(context, runAllTasksTimeoutNanos);
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
/*     */   public final int run(long waitNanos) {
/* 339 */     return run(waitNanos, 0L);
/*     */   }
/*     */   
/*     */   private void checkCurrentThread() {
/* 343 */     if (!inEventLoop(Thread.currentThread())) {
/* 344 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void wakeup() {
/* 352 */     if (isShuttingDown()) {
/*     */       return;
/*     */     }
/* 355 */     this.handler.wakeup();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ManualIoEventLoop next() {
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final IoEventLoopGroup parent() {
/* 365 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ChannelFuture register(Channel channel) {
/* 371 */     return register(new DefaultChannelPromise(channel, (EventExecutor)this));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ChannelFuture register(ChannelPromise promise) {
/* 377 */     ObjectUtil.checkNotNull(promise, "promise");
/* 378 */     promise.channel().unsafe().register(this, promise);
/* 379 */     return promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<IoRegistration> register(IoHandle handle) {
/* 384 */     Promise<IoRegistration> promise = newPromise();
/* 385 */     if (inEventLoop()) {
/* 386 */       registerForIo0(handle, promise);
/*     */     } else {
/* 388 */       execute(() -> registerForIo0(handle, promise));
/*     */     } 
/*     */     
/* 391 */     return (Future<IoRegistration>)promise;
/*     */   }
/*     */   private void registerForIo0(IoHandle handle, Promise<IoRegistration> promise) {
/*     */     IoRegistration registration;
/* 395 */     assert inEventLoop();
/*     */     
/*     */     try {
/* 398 */       registration = this.handler.register(handle);
/* 399 */     } catch (Exception e) {
/* 400 */       promise.setFailure(e);
/*     */       return;
/*     */     } 
/* 403 */     promise.setSuccess(registration);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 409 */     ObjectUtil.checkNotNull(promise, "promise");
/* 410 */     ObjectUtil.checkNotNull(channel, "channel");
/* 411 */     channel.unsafe().register(this, promise);
/* 412 */     return promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 417 */     return this.handler.isCompatible(handleType);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isIoType(Class<? extends IoHandler> handlerType) {
/* 422 */     return this.handler.getClass().equals(handlerType);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean inEventLoop(Thread thread) {
/* 427 */     return (this.owningThread.get() == thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setOwningThread(Thread owningThread) {
/* 437 */     Objects.requireNonNull(owningThread, "owningThread");
/* 438 */     if (!this.owningThread.compareAndSet(null, owningThread))
/* 439 */       throw new IllegalStateException("Owning thread already set"); 
/*     */   }
/*     */   private void shutdown0(long quietPeriod, long timeout, int shutdownState) {
/*     */     boolean wakeup;
/*     */     int oldState, newState;
/* 444 */     boolean inEventLoop = inEventLoop();
/*     */ 
/*     */     
/*     */     do {
/* 448 */       if (isShuttingDown()) {
/*     */         return;
/*     */       }
/*     */       
/* 452 */       wakeup = true;
/* 453 */       oldState = this.state.get();
/* 454 */       if (inEventLoop) {
/* 455 */         newState = shutdownState;
/* 456 */       } else if (oldState == 0) {
/* 457 */         newState = shutdownState;
/*     */       } else {
/* 459 */         newState = oldState;
/* 460 */         wakeup = false;
/*     */       }
/*     */     
/* 463 */     } while (!this.state.compareAndSet(oldState, newState));
/*     */ 
/*     */ 
/*     */     
/* 467 */     if (quietPeriod != -1L) {
/* 468 */       this.gracefulShutdownQuietPeriod = quietPeriod;
/*     */     }
/* 470 */     if (timeout != -1L) {
/* 471 */       this.gracefulShutdownTimeout = timeout;
/*     */     }
/*     */     
/* 474 */     if (wakeup) {
/*     */       
/* 476 */       this.taskQueue.offer(WAKEUP_TASK);
/* 477 */       this.handler.wakeup();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 483 */     ObjectUtil.checkPositiveOrZero(quietPeriod, "quietPeriod");
/* 484 */     if (timeout < quietPeriod) {
/* 485 */       throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
/*     */     }
/*     */     
/* 488 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/* 490 */     shutdown0(unit.toNanos(quietPeriod), unit.toNanos(timeout), 1);
/* 491 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final void shutdown() {
/* 497 */     shutdown0(-1L, -1L, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<?> terminationFuture() {
/* 502 */     return (Future<?>)this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isShuttingDown() {
/* 507 */     return (this.state.get() >= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isShutdown() {
/* 512 */     return (this.state.get() >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isTerminated() {
/* 517 */     return (this.state.get() == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 522 */     return this.terminationFuture.await(timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void execute(Runnable command) {
/* 527 */     Objects.requireNonNull(command, "command");
/* 528 */     boolean inEventLoop = inEventLoop();
/* 529 */     if (inEventLoop && 
/* 530 */       isShutdown()) {
/* 531 */       throw new RejectedExecutionException("event executor terminated");
/*     */     }
/*     */     
/* 534 */     this.taskQueue.add(command);
/* 535 */     if (!inEventLoop) {
/* 536 */       if (isShutdown()) {
/* 537 */         boolean reject = false;
/*     */         try {
/* 539 */           if (this.taskQueue.remove(command)) {
/* 540 */             reject = true;
/*     */           }
/* 542 */         } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 547 */         if (reject) {
/* 548 */           throw new RejectedExecutionException("event executor terminated");
/*     */         }
/*     */       } 
/* 551 */       this.handler.wakeup();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasTasks() {
/* 556 */     return !this.taskQueue.isEmpty();
/*     */   }
/*     */   
/*     */   private boolean confirmShutdown() {
/* 560 */     if (!isShuttingDown()) {
/* 561 */       return false;
/*     */     }
/*     */     
/* 564 */     if (!inEventLoop()) {
/* 565 */       throw new IllegalStateException("must be invoked from an event loop");
/*     */     }
/*     */     
/* 568 */     cancelScheduledTasks();
/*     */     
/* 570 */     if (this.gracefulShutdownStartTime == 0L) {
/* 571 */       this.gracefulShutdownStartTime = this.ticker.nanoTime();
/*     */     }
/*     */     
/* 574 */     if (runAllTasks(-1L, false) > 0) {
/* 575 */       if (isShutdown())
/*     */       {
/* 577 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 583 */       if (this.gracefulShutdownQuietPeriod == 0L) {
/* 584 */         return true;
/*     */       }
/* 586 */       return false;
/*     */     } 
/*     */     
/* 589 */     long nanoTime = this.ticker.nanoTime();
/*     */     
/* 591 */     if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
/* 592 */       return true;
/*     */     }
/*     */     
/* 595 */     if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
/*     */       try {
/* 597 */         Thread.sleep(100L);
/* 598 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */       
/* 602 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 607 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 614 */     throwIfInEventLoop("invokeAny");
/* 615 */     return (T)super.invokeAny(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 622 */     throwIfInEventLoop("invokeAny");
/* 623 */     return (T)super.invokeAny(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/* 630 */     throwIfInEventLoop("invokeAll");
/* 631 */     return super.invokeAll(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/* 638 */     throwIfInEventLoop("invokeAll");
/* 639 */     return super.invokeAll(tasks, timeout, unit);
/*     */   }
/*     */   
/*     */   private void throwIfInEventLoop(String method) {
/* 643 */     if (inEventLoop()) {
/* 644 */       throw new RejectedExecutionException("Calling " + method + " from within the EventLoop is not allowed as it would deadlock");
/*     */     }
/*     */   }
/*     */   
/*     */   private class BlockingIoHandlerContext
/*     */     implements IoHandlerContext
/*     */   {
/* 651 */     long maxBlockingNanos = Long.MAX_VALUE;
/*     */ 
/*     */     
/*     */     public boolean canBlock() {
/* 655 */       assert ManualIoEventLoop.this.inEventLoop();
/* 656 */       return (!ManualIoEventLoop.this.hasTasks() && !ManualIoEventLoop.this.hasScheduledTasks() && ManualIoEventLoop.this.canBlock());
/*     */     }
/*     */ 
/*     */     
/*     */     public long delayNanos(long currentTimeNanos) {
/* 661 */       assert ManualIoEventLoop.this.inEventLoop();
/* 662 */       return Math.min(this.maxBlockingNanos, ManualIoEventLoop.this.delayNanos(currentTimeNanos, this.maxBlockingNanos));
/*     */     }
/*     */ 
/*     */     
/*     */     public long deadlineNanos() {
/* 667 */       assert ManualIoEventLoop.this.inEventLoop();
/* 668 */       long next = ManualIoEventLoop.this.nextScheduledTaskDeadlineNanos();
/* 669 */       if (this.maxBlockingNanos == Long.MAX_VALUE)
/*     */       {
/* 671 */         return next;
/*     */       }
/* 673 */       long now = ManualIoEventLoop.this.ticker.nanoTime();
/*     */       
/* 675 */       if (next == -1L || next - now > this.maxBlockingNanos) {
/* 676 */         return now + this.maxBlockingNanos;
/*     */       }
/* 678 */       return next;
/*     */     }
/*     */     
/*     */     private BlockingIoHandlerContext() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ManualIoEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */