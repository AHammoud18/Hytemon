/*      */ package io.netty.util.concurrent;
/*      */ 
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.ThreadExecutorMap;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.BlockingQueue;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.ThreadFactory;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.TimeoutException;
/*      */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*      */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*      */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import org.jetbrains.annotations.Async.Schedule;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SingleThreadEventExecutor
/*      */   extends AbstractScheduledEventExecutor
/*      */   implements OrderedEventExecutor
/*      */ {
/*   57 */   static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, 
/*   58 */       SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", 2147483647));
/*      */ 
/*      */   
/*   61 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
/*      */   
/*      */   private static final int ST_NOT_STARTED = 1;
/*      */   private static final int ST_SUSPENDING = 2;
/*      */   private static final int ST_SUSPENDED = 3;
/*      */   private static final int ST_STARTED = 4;
/*      */   private static final int ST_SHUTTING_DOWN = 5;
/*      */   private static final int ST_SHUTDOWN = 6;
/*      */   private static final int ST_TERMINATED = 7;
/*      */   
/*   71 */   private static final Runnable NOOP_TASK = new Runnable()
/*      */     {
/*      */       public void run() {}
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
/*      */   
/*   81 */   private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> PROPERTIES_UPDATER = AtomicReferenceFieldUpdater.newUpdater(SingleThreadEventExecutor.class, ThreadProperties.class, "threadProperties");
/*      */ 
/*      */   
/*   84 */   private static final AtomicLongFieldUpdater<SingleThreadEventExecutor> ACCUMULATED_ACTIVE_TIME_NANOS_UPDATER = AtomicLongFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "accumulatedActiveTimeNanos");
/*      */   
/*   86 */   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> CONSECUTIVE_IDLE_CYCLES_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "consecutiveIdleCycles");
/*      */   
/*   88 */   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> CONSECUTIVE_BUSY_CYCLES_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "consecutiveBusyCycles");
/*      */   
/*      */   private final Queue<Runnable> taskQueue;
/*      */   
/*      */   private volatile Thread thread;
/*      */   
/*      */   private volatile ThreadProperties threadProperties;
/*      */   private final Executor executor;
/*      */   private volatile boolean interrupted;
/*   97 */   private final Lock processingLock = new ReentrantLock();
/*   98 */   private final CountDownLatch threadLock = new CountDownLatch(1);
/*   99 */   private final Set<Runnable> shutdownHooks = new LinkedHashSet<>();
/*      */ 
/*      */   
/*      */   private final boolean addTaskWakesUp;
/*      */ 
/*      */   
/*      */   private final int maxPendingTasks;
/*      */ 
/*      */   
/*      */   private final RejectedExecutionHandler rejectedExecutionHandler;
/*      */   
/*      */   private final boolean supportSuspension;
/*      */   
/*      */   private volatile long accumulatedActiveTimeNanos;
/*      */   
/*      */   private volatile long lastActivityTimeNanos;
/*      */   
/*      */   private volatile int consecutiveIdleCycles;
/*      */   
/*      */   private volatile int consecutiveBusyCycles;
/*      */   
/*      */   private long lastExecutionTime;
/*      */   
/*  122 */   private volatile int state = 1;
/*      */   
/*      */   private volatile long gracefulShutdownQuietPeriod;
/*      */   
/*      */   private volatile long gracefulShutdownTimeout;
/*      */   
/*      */   private long gracefulShutdownStartTime;
/*  129 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/*  141 */     this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp);
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
/*  157 */     this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp, maxPendingTasks, rejectedHandler);
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, boolean supportSuspension, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
/*  175 */     this(parent, new ThreadPerTaskExecutor(threadFactory), addTaskWakesUp, supportSuspension, maxPendingTasks, rejectedHandler);
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp) {
/*  188 */     this(parent, executor, addTaskWakesUp, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
/*  204 */     this(parent, executor, addTaskWakesUp, false, maxPendingTasks, rejectedHandler);
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
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, boolean supportSuspension, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
/*  221 */     super(parent);
/*  222 */     this.addTaskWakesUp = addTaskWakesUp;
/*  223 */     this.supportSuspension = supportSuspension;
/*  224 */     this.maxPendingTasks = Math.max(16, maxPendingTasks);
/*  225 */     this.executor = ThreadExecutorMap.apply(executor, this);
/*  226 */     this.taskQueue = newTaskQueue(this.maxPendingTasks);
/*  227 */     this.rejectedExecutionHandler = (RejectedExecutionHandler)ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
/*  228 */     this.lastActivityTimeNanos = ticker().nanoTime();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, Queue<Runnable> taskQueue, RejectedExecutionHandler rejectedHandler) {
/*  234 */     this(parent, executor, addTaskWakesUp, false, taskQueue, rejectedHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SingleThreadEventExecutor(EventExecutorGroup parent, Executor executor, boolean addTaskWakesUp, boolean supportSuspension, Queue<Runnable> taskQueue, RejectedExecutionHandler rejectedHandler) {
/*  240 */     super(parent);
/*  241 */     this.addTaskWakesUp = addTaskWakesUp;
/*  242 */     this.supportSuspension = supportSuspension;
/*  243 */     this.maxPendingTasks = DEFAULT_MAX_PENDING_EXECUTOR_TASKS;
/*  244 */     this.executor = ThreadExecutorMap.apply(executor, this);
/*  245 */     this.taskQueue = (Queue<Runnable>)ObjectUtil.checkNotNull(taskQueue, "taskQueue");
/*  246 */     this.rejectedExecutionHandler = (RejectedExecutionHandler)ObjectUtil.checkNotNull(rejectedHandler, "rejectedHandler");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected Queue<Runnable> newTaskQueue() {
/*  254 */     return newTaskQueue(this.maxPendingTasks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
/*  264 */     return new LinkedBlockingQueue<>(maxPendingTasks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void interruptThread() {
/*  271 */     Thread currentThread = this.thread;
/*  272 */     if (currentThread == null) {
/*  273 */       this.interrupted = true;
/*      */     } else {
/*  275 */       currentThread.interrupt();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Runnable pollTask() {
/*  283 */     assert inEventLoop();
/*  284 */     return pollTaskFrom(this.taskQueue);
/*      */   }
/*      */   
/*      */   protected static Runnable pollTaskFrom(Queue<Runnable> taskQueue) {
/*      */     while (true) {
/*  289 */       Runnable task = taskQueue.poll();
/*  290 */       if (task != WAKEUP_TASK) {
/*  291 */         return task;
/*      */       }
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
/*      */   protected Runnable takeTask() {
/*  306 */     assert inEventLoop();
/*  307 */     if (!(this.taskQueue instanceof BlockingQueue)) {
/*  308 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*  311 */     BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue;
/*      */     while (true) {
/*  313 */       ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/*  314 */       if (scheduledTask == null) {
/*  315 */         Runnable runnable = null;
/*      */         try {
/*  317 */           runnable = taskQueue.take();
/*  318 */           if (runnable == WAKEUP_TASK) {
/*  319 */             runnable = null;
/*      */           }
/*  321 */         } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */         
/*  324 */         return runnable;
/*      */       } 
/*  326 */       long delayNanos = scheduledTask.delayNanos();
/*  327 */       Runnable task = null;
/*  328 */       if (delayNanos > 0L) {
/*      */         try {
/*  330 */           task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/*  331 */         } catch (InterruptedException e) {
/*      */           
/*  333 */           return null;
/*      */         } 
/*      */       }
/*  336 */       if (task == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  341 */         fetchFromScheduledTaskQueue();
/*  342 */         task = taskQueue.poll();
/*      */       } 
/*      */       
/*  345 */       if (task != null) {
/*  346 */         if (task == WAKEUP_TASK) {
/*  347 */           return null;
/*      */         }
/*  349 */         return task;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fetchFromScheduledTaskQueue() {
/*  356 */     return fetchFromScheduledTaskQueue(this.taskQueue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean executeExpiredScheduledTasks() {
/*  363 */     if (this.scheduledTaskQueue == null || this.scheduledTaskQueue.isEmpty()) {
/*  364 */       return false;
/*      */     }
/*  366 */     long nanoTime = getCurrentTimeNanos();
/*  367 */     Runnable scheduledTask = pollScheduledTask(nanoTime);
/*  368 */     if (scheduledTask == null) {
/*  369 */       return false;
/*      */     }
/*      */     do {
/*  372 */       safeExecute(scheduledTask);
/*  373 */     } while ((scheduledTask = pollScheduledTask(nanoTime)) != null);
/*  374 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Runnable peekTask() {
/*  381 */     assert inEventLoop();
/*  382 */     return this.taskQueue.peek();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasTasks() {
/*  389 */     assert inEventLoop();
/*  390 */     return !this.taskQueue.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int pendingTasks() {
/*  397 */     return this.taskQueue.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTask(Runnable task) {
/*  405 */     ObjectUtil.checkNotNull(task, "task");
/*  406 */     if (!offerTask(task)) {
/*  407 */       reject(task);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean offerTask(Runnable task) {
/*  412 */     if (isShutdown()) {
/*  413 */       reject();
/*      */     }
/*  415 */     return this.taskQueue.offer(task);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean removeTask(Runnable task) {
/*  422 */     return this.taskQueue.remove(ObjectUtil.checkNotNull(task, "task"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean runAllTasks() {
/*      */     boolean fetchedAll;
/*  431 */     assert inEventLoop();
/*      */     
/*  433 */     boolean ranAtLeastOne = false;
/*      */     
/*      */     do {
/*  436 */       fetchedAll = fetchFromScheduledTaskQueue(this.taskQueue);
/*  437 */       if (!runAllTasksFrom(this.taskQueue))
/*  438 */         continue;  ranAtLeastOne = true;
/*      */     }
/*  440 */     while (!fetchedAll);
/*      */     
/*  442 */     if (ranAtLeastOne) {
/*  443 */       this.lastExecutionTime = getCurrentTimeNanos();
/*      */     }
/*  445 */     afterRunningAllTasks();
/*  446 */     return ranAtLeastOne;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean runScheduledAndExecutorTasks(int maxDrainAttempts) {
/*      */     int i;
/*  458 */     assert inEventLoop();
/*      */     
/*  460 */     int drainAttempt = 0;
/*      */ 
/*      */     
/*      */     do {
/*  464 */       i = runExistingTasksFrom(this.taskQueue) | executeExpiredScheduledTasks();
/*  465 */     } while (i != 0 && ++drainAttempt < maxDrainAttempts);
/*      */     
/*  467 */     if (drainAttempt > 0) {
/*  468 */       this.lastExecutionTime = getCurrentTimeNanos();
/*      */     }
/*  470 */     afterRunningAllTasks();
/*      */     
/*  472 */     return (drainAttempt > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean runAllTasksFrom(Queue<Runnable> taskQueue) {
/*  483 */     Runnable task = pollTaskFrom(taskQueue);
/*  484 */     if (task == null) {
/*  485 */       return false;
/*      */     }
/*      */     while (true) {
/*  488 */       safeExecute(task);
/*  489 */       task = pollTaskFrom(taskQueue);
/*  490 */       if (task == null) {
/*  491 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean runExistingTasksFrom(Queue<Runnable> taskQueue) {
/*  502 */     Runnable task = pollTaskFrom(taskQueue);
/*  503 */     if (task == null) {
/*  504 */       return false;
/*      */     }
/*  506 */     int remaining = Math.min(this.maxPendingTasks, taskQueue.size());
/*  507 */     safeExecute(task);
/*      */ 
/*      */     
/*  510 */     while (remaining-- > 0 && (task = taskQueue.poll()) != null) {
/*  511 */       safeExecute(task);
/*      */     }
/*  513 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean runAllTasks(long timeoutNanos) {
/*      */     long lastExecutionTime;
/*  522 */     fetchFromScheduledTaskQueue(this.taskQueue);
/*  523 */     Runnable task = pollTask();
/*  524 */     if (task == null) {
/*  525 */       afterRunningAllTasks();
/*  526 */       return false;
/*      */     } 
/*      */     
/*  529 */     long deadline = (timeoutNanos > 0L) ? (getCurrentTimeNanos() + timeoutNanos) : 0L;
/*  530 */     long runTasks = 0L;
/*      */ 
/*      */     
/*  533 */     long workStartTime = ticker().nanoTime();
/*      */     while (true) {
/*  535 */       safeExecute(task);
/*      */       
/*  537 */       runTasks++;
/*      */ 
/*      */ 
/*      */       
/*  541 */       if ((runTasks & 0x3FL) == 0L) {
/*  542 */         lastExecutionTime = getCurrentTimeNanos();
/*  543 */         if (lastExecutionTime >= deadline) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  548 */       task = pollTask();
/*  549 */       if (task == null) {
/*  550 */         lastExecutionTime = getCurrentTimeNanos();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  555 */     long workEndTime = ticker().nanoTime();
/*  556 */     this.accumulatedActiveTimeNanos += workEndTime - workStartTime;
/*  557 */     this.lastActivityTimeNanos = workEndTime;
/*      */     
/*  559 */     afterRunningAllTasks();
/*  560 */     this.lastExecutionTime = lastExecutionTime;
/*  561 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void afterRunningAllTasks() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long delayNanos(long currentTimeNanos) {
/*  573 */     currentTimeNanos -= ticker().initialNanoTime();
/*      */     
/*  575 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/*  576 */     if (scheduledTask == null) {
/*  577 */       return SCHEDULE_PURGE_INTERVAL;
/*      */     }
/*      */     
/*  580 */     return scheduledTask.delayNanos(currentTimeNanos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long deadlineNanos() {
/*  588 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/*  589 */     if (scheduledTask == null) {
/*  590 */       return getCurrentTimeNanos() + SCHEDULE_PURGE_INTERVAL;
/*      */     }
/*  592 */     return scheduledTask.deadlineNanos();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLastExecutionTime() {
/*  603 */     long now = getCurrentTimeNanos();
/*  604 */     this.lastExecutionTime = now;
/*  605 */     this.lastActivityTimeNanos = now;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNumOfRegisteredChannels() {
/*  615 */     return -1;
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
/*      */   protected void reportActiveIoTime(long nanos) {
/*  628 */     assert inEventLoop();
/*  629 */     if (nanos > 0L) {
/*  630 */       this.accumulatedActiveTimeNanos += nanos;
/*  631 */       this.lastActivityTimeNanos = ticker().nanoTime();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long getAndResetAccumulatedActiveTimeNanos() {
/*  639 */     return ACCUMULATED_ACTIVE_TIME_NANOS_UPDATER.getAndSet(this, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected long getLastActivityTimeNanos() {
/*  646 */     return this.lastActivityTimeNanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getAndIncrementIdleCycles() {
/*  656 */     return CONSECUTIVE_IDLE_CYCLES_UPDATER.getAndIncrement(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetIdleCycles() {
/*  664 */     CONSECUTIVE_IDLE_CYCLES_UPDATER.set(this, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getAndIncrementBusyCycles() {
/*  674 */     return CONSECUTIVE_BUSY_CYCLES_UPDATER.getAndIncrement(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetBusyCycles() {
/*  682 */     CONSECUTIVE_BUSY_CYCLES_UPDATER.set(this, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSuspensionSupported() {
/*  689 */     return this.supportSuspension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cleanup() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void wakeup(boolean inEventLoop) {
/*  705 */     if (!inEventLoop)
/*      */     {
/*      */       
/*  708 */       this.taskQueue.offer(WAKEUP_TASK);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean inEventLoop(Thread thread) {
/*  714 */     return (thread == this.thread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addShutdownHook(final Runnable task) {
/*  721 */     if (inEventLoop()) {
/*  722 */       this.shutdownHooks.add(task);
/*      */     } else {
/*  724 */       execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  727 */               SingleThreadEventExecutor.this.shutdownHooks.add(task);
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeShutdownHook(final Runnable task) {
/*  737 */     if (inEventLoop()) {
/*  738 */       this.shutdownHooks.remove(task);
/*      */     } else {
/*  740 */       execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*  743 */               SingleThreadEventExecutor.this.shutdownHooks.remove(task);
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean runShutdownHooks() {
/*  750 */     boolean ran = false;
/*      */     
/*  752 */     while (!this.shutdownHooks.isEmpty()) {
/*  753 */       List<Runnable> copy = new ArrayList<>(this.shutdownHooks);
/*  754 */       this.shutdownHooks.clear();
/*  755 */       for (Runnable task : copy) {
/*      */         try {
/*  757 */           runTask(task);
/*  758 */         } catch (Throwable t) {
/*  759 */           logger.warn("Shutdown hook raised an exception.", t);
/*      */         } finally {
/*  761 */           ran = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  766 */     if (ran) {
/*  767 */       this.lastExecutionTime = getCurrentTimeNanos();
/*      */     }
/*      */     
/*  770 */     return ran;
/*      */   } private void shutdown0(long quietPeriod, long timeout, int shutdownState) {
/*      */     boolean wakeup;
/*      */     int oldState, newState;
/*  774 */     if (isShuttingDown()) {
/*      */       return;
/*      */     }
/*      */     
/*  778 */     boolean inEventLoop = inEventLoop();
/*      */ 
/*      */     
/*      */     do {
/*  782 */       if (isShuttingDown()) {
/*      */         return;
/*      */       }
/*      */       
/*  786 */       wakeup = true;
/*  787 */       oldState = this.state;
/*  788 */       if (inEventLoop) {
/*  789 */         newState = shutdownState;
/*      */       } else {
/*  791 */         switch (oldState) {
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*  796 */             newState = shutdownState;
/*      */             break;
/*      */           default:
/*  799 */             newState = oldState;
/*  800 */             wakeup = false; break;
/*      */         } 
/*      */       } 
/*  803 */     } while (!STATE_UPDATER.compareAndSet(this, oldState, newState));
/*      */ 
/*      */ 
/*      */     
/*  807 */     if (quietPeriod != -1L) {
/*  808 */       this.gracefulShutdownQuietPeriod = quietPeriod;
/*      */     }
/*  810 */     if (timeout != -1L) {
/*  811 */       this.gracefulShutdownTimeout = timeout;
/*      */     }
/*      */     
/*  814 */     if (ensureThreadStarted(oldState)) {
/*      */       return;
/*      */     }
/*      */     
/*  818 */     if (wakeup) {
/*  819 */       this.taskQueue.offer(WAKEUP_TASK);
/*  820 */       if (!this.addTaskWakesUp) {
/*  821 */         wakeup(inEventLoop);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  828 */     ObjectUtil.checkPositiveOrZero(quietPeriod, "quietPeriod");
/*  829 */     if (timeout < quietPeriod) {
/*  830 */       throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
/*      */     }
/*      */     
/*  833 */     ObjectUtil.checkNotNull(unit, "unit");
/*      */     
/*  835 */     shutdown0(unit.toNanos(quietPeriod), unit.toNanos(timeout), 5);
/*  836 */     return terminationFuture();
/*      */   }
/*      */ 
/*      */   
/*      */   public Future<?> terminationFuture() {
/*  841 */     return this.terminationFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void shutdown() {
/*  847 */     shutdown0(-1L, -1L, 6);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShuttingDown() {
/*  852 */     return (this.state >= 5);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShutdown() {
/*  857 */     return (this.state >= 6);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTerminated() {
/*  862 */     return (this.state == 7);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSuspended() {
/*  867 */     int currentState = this.state;
/*  868 */     return (currentState == 3 || currentState == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean trySuspend() {
/*  873 */     if (this.supportSuspension) {
/*  874 */       if (STATE_UPDATER.compareAndSet(this, 4, 2)) {
/*  875 */         wakeup(inEventLoop());
/*  876 */         return true;
/*  877 */       }  if (STATE_UPDATER.compareAndSet(this, 1, 3)) {
/*  878 */         return true;
/*      */       }
/*  880 */       int currentState = this.state;
/*  881 */       return (currentState == 3 || currentState == 2);
/*      */     } 
/*  883 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canSuspend() {
/*  893 */     return canSuspend(this.state);
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
/*      */   protected boolean canSuspend(int state) {
/*  906 */     assert inEventLoop();
/*  907 */     return (this.supportSuspension && (state == 3 || state == 2) && 
/*  908 */       !hasTasks() && nextScheduledTaskDeadlineNanos() == -1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean confirmShutdown() {
/*  915 */     if (!isShuttingDown()) {
/*  916 */       return false;
/*      */     }
/*      */     
/*  919 */     if (!inEventLoop()) {
/*  920 */       throw new IllegalStateException("must be invoked from an event loop");
/*      */     }
/*      */     
/*  923 */     cancelScheduledTasks();
/*      */     
/*  925 */     if (this.gracefulShutdownStartTime == 0L) {
/*  926 */       this.gracefulShutdownStartTime = getCurrentTimeNanos();
/*      */     }
/*      */     
/*  929 */     if (runAllTasks() || runShutdownHooks()) {
/*  930 */       if (isShutdown())
/*      */       {
/*  932 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  938 */       if (this.gracefulShutdownQuietPeriod == 0L) {
/*  939 */         return true;
/*      */       }
/*  941 */       this.taskQueue.offer(WAKEUP_TASK);
/*  942 */       return false;
/*      */     } 
/*      */     
/*  945 */     long nanoTime = getCurrentTimeNanos();
/*      */     
/*  947 */     if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
/*  948 */       return true;
/*      */     }
/*      */     
/*  951 */     if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
/*      */ 
/*      */       
/*  954 */       this.taskQueue.offer(WAKEUP_TASK);
/*      */       try {
/*  956 */         Thread.sleep(100L);
/*  957 */       } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */       
/*  961 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  966 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/*  971 */     ObjectUtil.checkNotNull(unit, "unit");
/*  972 */     if (inEventLoop()) {
/*  973 */       throw new IllegalStateException("cannot await termination of the current thread");
/*      */     }
/*      */     
/*  976 */     this.threadLock.await(timeout, unit);
/*      */     
/*  978 */     return isTerminated();
/*      */   }
/*      */ 
/*      */   
/*      */   public void execute(Runnable task) {
/*  983 */     execute0(task);
/*      */   }
/*      */ 
/*      */   
/*      */   public void lazyExecute(Runnable task) {
/*  988 */     lazyExecute0(task);
/*      */   }
/*      */   
/*      */   private void execute0(@Schedule Runnable task) {
/*  992 */     ObjectUtil.checkNotNull(task, "task");
/*  993 */     execute(task, wakesUpForTask(task));
/*      */   }
/*      */   
/*      */   private void lazyExecute0(@Schedule Runnable task) {
/*  997 */     execute((Runnable)ObjectUtil.checkNotNull(task, "task"), false);
/*      */   }
/*      */ 
/*      */   
/*      */   void scheduleRemoveScheduled(final ScheduledFutureTask<?> task) {
/* 1002 */     ObjectUtil.checkNotNull(task, "task");
/* 1003 */     int currentState = this.state;
/* 1004 */     if (this.supportSuspension && currentState == 3) {
/*      */ 
/*      */ 
/*      */       
/* 1008 */       execute(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1011 */               task.run();
/* 1012 */               if (SingleThreadEventExecutor.this.canSuspend(3))
/*      */               {
/*      */                 
/* 1015 */                 SingleThreadEventExecutor.this.trySuspend();
/*      */               }
/*      */             }
/*      */           }, 
/*      */           true);
/*      */     } else {
/* 1021 */       execute(task, false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void execute(Runnable task, boolean immediate) {
/* 1026 */     boolean inEventLoop = inEventLoop();
/* 1027 */     addTask(task);
/* 1028 */     if (!inEventLoop) {
/* 1029 */       startThread();
/* 1030 */       if (isShutdown()) {
/* 1031 */         boolean reject = false;
/*      */         try {
/* 1033 */           if (removeTask(task)) {
/* 1034 */             reject = true;
/*      */           }
/* 1036 */         } catch (UnsupportedOperationException unsupportedOperationException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1041 */         if (reject) {
/* 1042 */           reject();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1047 */     if (!this.addTaskWakesUp && immediate) {
/* 1048 */       wakeup(inEventLoop);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 1054 */     throwIfInEventLoop("invokeAny");
/* 1055 */     return super.invokeAny(tasks);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 1061 */     throwIfInEventLoop("invokeAny");
/* 1062 */     return super.invokeAny(tasks, timeout, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/* 1068 */     throwIfInEventLoop("invokeAll");
/* 1069 */     return super.invokeAll(tasks);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/* 1075 */     throwIfInEventLoop("invokeAll");
/* 1076 */     return super.invokeAll(tasks, timeout, unit);
/*      */   }
/*      */   
/*      */   private void throwIfInEventLoop(String method) {
/* 1080 */     if (inEventLoop()) {
/* 1081 */       throw new RejectedExecutionException("Calling " + method + " from within the EventLoop is not allowed");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ThreadProperties threadProperties() {
/* 1091 */     ThreadProperties threadProperties = this.threadProperties;
/* 1092 */     if (threadProperties == null) {
/* 1093 */       Thread thread = this.thread;
/* 1094 */       if (thread == null) {
/* 1095 */         assert !inEventLoop();
/* 1096 */         submit(NOOP_TASK).syncUninterruptibly();
/* 1097 */         thread = this.thread;
/* 1098 */         assert thread != null;
/*      */       } 
/*      */       
/* 1101 */       threadProperties = new DefaultThreadProperties(thread);
/* 1102 */       if (!PROPERTIES_UPDATER.compareAndSet(this, null, threadProperties)) {
/* 1103 */         threadProperties = this.threadProperties;
/*      */       }
/*      */     } 
/*      */     
/* 1107 */     return threadProperties;
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
/*      */   protected boolean wakesUpForTask(Runnable task) {
/* 1121 */     return true;
/*      */   }
/*      */   
/*      */   protected static void reject() {
/* 1125 */     throw new RejectedExecutionException("event executor terminated");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void reject(Runnable task) {
/* 1134 */     this.rejectedExecutionHandler.rejected(task, this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1139 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*      */   
/*      */   private void startThread() {
/* 1142 */     int currentState = this.state;
/* 1143 */     if ((currentState == 1 || currentState == 3) && 
/* 1144 */       STATE_UPDATER.compareAndSet(this, currentState, 4)) {
/* 1145 */       resetIdleCycles();
/* 1146 */       resetBusyCycles();
/* 1147 */       boolean success = false;
/*      */       try {
/* 1149 */         doStartThread();
/* 1150 */         success = true;
/*      */       } finally {
/* 1152 */         if (!success) {
/* 1153 */           STATE_UPDATER.compareAndSet(this, 4, 1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean ensureThreadStarted(int oldState) {
/* 1161 */     if (oldState == 1 || oldState == 3) {
/*      */       try {
/* 1163 */         doStartThread();
/* 1164 */       } catch (Throwable cause) {
/* 1165 */         STATE_UPDATER.set(this, 7);
/* 1166 */         this.terminationFuture.tryFailure(cause);
/*      */         
/* 1168 */         if (!(cause instanceof Exception))
/*      */         {
/* 1170 */           PlatformDependent.throwException(cause);
/*      */         }
/* 1172 */         return true;
/*      */       } 
/*      */     }
/* 1175 */     return false;
/*      */   }
/*      */   
/*      */   private void doStartThread() {
/* 1179 */     this.executor.execute(new Runnable()
/*      */         {
/*      */           public void run() {
/* 1182 */             SingleThreadEventExecutor.this.processingLock.lock();
/* 1183 */             assert SingleThreadEventExecutor.this.thread == null;
/* 1184 */             SingleThreadEventExecutor.this.thread = Thread.currentThread();
/* 1185 */             if (SingleThreadEventExecutor.this.interrupted) {
/* 1186 */               SingleThreadEventExecutor.this.thread.interrupt();
/* 1187 */               SingleThreadEventExecutor.this.interrupted = false;
/*      */             } 
/* 1189 */             boolean success = false;
/* 1190 */             Throwable unexpectedException = null;
/* 1191 */             SingleThreadEventExecutor.this.updateLastExecutionTime();
/* 1192 */             boolean suspend = false;
/*      */             try {
/*      */               while (true) {
/* 1195 */                 SingleThreadEventExecutor.this.run();
/* 1196 */                 success = true;
/*      */                 
/* 1198 */                 int currentState = SingleThreadEventExecutor.this.state;
/* 1199 */                 if (SingleThreadEventExecutor.this.canSuspend(currentState)) {
/* 1200 */                   if (!SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, 2, 3)) {
/*      */                     continue;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/* 1206 */                   if (!SingleThreadEventExecutor.this.canSuspend(3) && SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, 3, 4)) {
/*      */                     continue;
/*      */                   }
/*      */ 
/*      */ 
/*      */                   
/* 1212 */                   suspend = true;
/*      */                 } 
/*      */                 break;
/*      */               } 
/* 1216 */             } catch (Throwable t) {
/* 1217 */               unexpectedException = t;
/* 1218 */               SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", t);
/*      */             } finally {
/* 1220 */               boolean shutdown = !suspend;
/* 1221 */               if (shutdown) {
/*      */                 int oldState;
/*      */                 do {
/* 1224 */                   oldState = SingleThreadEventExecutor.this.state;
/* 1225 */                 } while (oldState < 5 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 5));
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1230 */                 if (success && SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L)
/*      */                 {
/* 1232 */                   if (SingleThreadEventExecutor.logger.isErrorEnabled()) {
/* 1233 */                     SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class
/* 1234 */                         .getSimpleName() + ".confirmShutdown() must be called before run() implementation terminates.");
/*      */                   }
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*      */               try {
/* 1241 */                 if (shutdown) {
/*      */                   int currentState;
/*      */                   
/*      */                   do {
/*      */                   
/* 1246 */                   } while (!SingleThreadEventExecutor.this.confirmShutdown());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   do {
/* 1254 */                     currentState = SingleThreadEventExecutor.this.state;
/* 1255 */                   } while (currentState < 6 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, currentState, 6));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1263 */                   SingleThreadEventExecutor.this.confirmShutdown();
/*      */                 } 
/*      */               } finally {
/*      */                 try {
/* 1267 */                   if (shutdown) {
/*      */                     try {
/* 1269 */                       SingleThreadEventExecutor.this.cleanup();
/*      */                     
/*      */                     }
/*      */                     finally {
/*      */ 
/*      */                       
/* 1275 */                       FastThreadLocal.removeAll();
/*      */                       
/* 1277 */                       SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 7);
/* 1278 */                       SingleThreadEventExecutor.this.threadLock.countDown();
/* 1279 */                       int numUserTasks = SingleThreadEventExecutor.this.drainTasks();
/* 1280 */                       if (numUserTasks > 0 && SingleThreadEventExecutor.logger.isWarnEnabled()) {
/* 1281 */                         SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + numUserTasks + ')');
/*      */                       }
/*      */                       
/* 1284 */                       if (unexpectedException == null) {
/* 1285 */                         SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
/*      */                       } else {
/* 1287 */                         SingleThreadEventExecutor.this.terminationFuture.setFailure(unexpectedException);
/*      */                       } 
/*      */                     } 
/*      */                   } else {
/*      */                     
/* 1292 */                     FastThreadLocal.removeAll();
/*      */ 
/*      */                     
/* 1295 */                     SingleThreadEventExecutor.this.threadProperties = null;
/*      */                   } 
/*      */                 } finally {
/* 1298 */                   SingleThreadEventExecutor.this.thread = null;
/*      */                   
/* 1300 */                   SingleThreadEventExecutor.this.processingLock.unlock();
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   protected abstract void run();
/*      */   final int drainTasks() {
/* 1309 */     int numTasks = 0;
/*      */     while (true) {
/* 1311 */       Runnable runnable = this.taskQueue.poll();
/* 1312 */       if (runnable == null) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 1317 */       if (WAKEUP_TASK != runnable) {
/* 1318 */         numTasks++;
/*      */       }
/*      */     } 
/* 1321 */     return numTasks;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   protected static interface NonWakeupRunnable extends AbstractEventExecutor.LazyRunnable {}
/*      */   
/*      */   private static final class DefaultThreadProperties implements ThreadProperties { DefaultThreadProperties(Thread t) {
/* 1328 */       this.t = t;
/*      */     }
/*      */     private final Thread t;
/*      */     
/*      */     public Thread.State state() {
/* 1333 */       return this.t.getState();
/*      */     }
/*      */ 
/*      */     
/*      */     public int priority() {
/* 1338 */       return this.t.getPriority();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInterrupted() {
/* 1343 */       return this.t.isInterrupted();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDaemon() {
/* 1348 */       return this.t.isDaemon();
/*      */     }
/*      */ 
/*      */     
/*      */     public String name() {
/* 1353 */       return this.t.getName();
/*      */     }
/*      */ 
/*      */     
/*      */     public long id() {
/* 1358 */       return this.t.getId();
/*      */     }
/*      */ 
/*      */     
/*      */     public StackTraceElement[] stackTrace() {
/* 1363 */       return this.t.getStackTrace();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isAlive() {
/* 1368 */       return this.t.isAlive();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\SingleThreadEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */