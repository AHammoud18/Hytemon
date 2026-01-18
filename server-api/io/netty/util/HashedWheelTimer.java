/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.ImmediateExecutor;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashedWheelTimer
/*     */   implements Timer
/*     */ {
/*  88 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
/*     */   
/*  90 */   private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
/*  91 */   private static final AtomicBoolean WARNED_TOO_MANY_INSTANCES = new AtomicBoolean();
/*     */   private static final int INSTANCE_COUNT_LIMIT = 64;
/*  93 */   private static final long MILLISECOND_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*  94 */   private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = ResourceLeakDetectorFactory.instance()
/*  95 */     .newResourceLeakDetector(HashedWheelTimer.class, 1);
/*     */ 
/*     */   
/*  98 */   private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
/*     */   
/*     */   private final ResourceLeakTracker<HashedWheelTimer> leak;
/* 101 */   private final Worker worker = new Worker();
/*     */   
/*     */   private final Thread workerThread;
/*     */   
/*     */   public static final int WORKER_STATE_INIT = 0;
/*     */   
/*     */   public static final int WORKER_STATE_STARTED = 1;
/*     */   public static final int WORKER_STATE_SHUTDOWN = 2;
/*     */   private volatile int workerState;
/*     */   private final long tickDuration;
/*     */   private final HashedWheelBucket[] wheel;
/*     */   private final int mask;
/* 113 */   private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
/* 114 */   private final Queue<HashedWheelTimeout> timeouts = PlatformDependent.newMpscQueue();
/* 115 */   private final Queue<HashedWheelTimeout> cancelledTimeouts = PlatformDependent.newMpscQueue();
/* 116 */   private final AtomicLong pendingTimeouts = new AtomicLong(0L);
/*     */ 
/*     */   
/*     */   private final long maxPendingTimeouts;
/*     */ 
/*     */   
/*     */   private final Executor taskExecutor;
/*     */   
/*     */   private volatile long startTime;
/*     */ 
/*     */   
/*     */   public HashedWheelTimer() {
/* 128 */     this(Executors.defaultThreadFactory());
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit) {
/* 142 */     this(Executors.defaultThreadFactory(), tickDuration, unit);
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 156 */     this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory) {
/* 169 */     this(threadFactory, 100L, TimeUnit.MILLISECONDS);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
/* 185 */     this(threadFactory, tickDuration, unit, 512);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 203 */     this(threadFactory, tickDuration, unit, ticksPerWheel, true);
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
/*     */   
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection) {
/* 224 */     this(threadFactory, tickDuration, unit, ticksPerWheel, leakDetection, -1L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts) {
/* 251 */     this(threadFactory, tickDuration, unit, ticksPerWheel, leakDetection, maxPendingTimeouts, (Executor)ImmediateExecutor.INSTANCE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection, long maxPendingTimeouts, Executor taskExecutor) {
/* 282 */     ObjectUtil.checkNotNull(threadFactory, "threadFactory");
/* 283 */     ObjectUtil.checkNotNull(unit, "unit");
/* 284 */     ObjectUtil.checkPositive(tickDuration, "tickDuration");
/* 285 */     ObjectUtil.checkPositive(ticksPerWheel, "ticksPerWheel");
/* 286 */     this.taskExecutor = (Executor)ObjectUtil.checkNotNull(taskExecutor, "taskExecutor");
/*     */ 
/*     */     
/* 289 */     this.wheel = createWheel(ticksPerWheel);
/* 290 */     this.mask = this.wheel.length - 1;
/*     */ 
/*     */     
/* 293 */     long duration = unit.toNanos(tickDuration);
/*     */ 
/*     */     
/* 296 */     if (duration >= Long.MAX_VALUE / this.wheel.length) {
/* 297 */       throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[] {
/*     */               
/* 299 */               Long.valueOf(tickDuration), Long.valueOf(Long.MAX_VALUE / this.wheel.length)
/*     */             }));
/*     */     }
/* 302 */     if (duration < MILLISECOND_NANOS) {
/* 303 */       logger.warn("Configured tickDuration {} smaller than {}, using 1ms.", 
/* 304 */           Long.valueOf(tickDuration), Long.valueOf(MILLISECOND_NANOS));
/* 305 */       this.tickDuration = MILLISECOND_NANOS;
/*     */     } else {
/* 307 */       this.tickDuration = duration;
/*     */     } 
/*     */     
/* 310 */     this.workerThread = threadFactory.newThread(this.worker);
/*     */     
/* 312 */     this.leak = (leakDetection || !this.workerThread.isDaemon()) ? leakDetector.track(this) : null;
/*     */     
/* 314 */     this.maxPendingTimeouts = maxPendingTimeouts;
/*     */     
/* 316 */     if (INSTANCE_COUNTER.incrementAndGet() > 64 && WARNED_TOO_MANY_INSTANCES
/* 317 */       .compareAndSet(false, true)) {
/* 318 */       reportTooManyInstances();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 325 */       super.finalize();
/*     */     }
/*     */     finally {
/*     */       
/* 329 */       if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
/* 330 */         INSTANCE_COUNTER.decrementAndGet();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static HashedWheelBucket[] createWheel(int ticksPerWheel) {
/* 336 */     ticksPerWheel = MathUtil.findNextPositivePowerOfTwo(ticksPerWheel);
/*     */     
/* 338 */     HashedWheelBucket[] wheel = new HashedWheelBucket[ticksPerWheel];
/* 339 */     for (int i = 0; i < wheel.length; i++) {
/* 340 */       wheel[i] = new HashedWheelBucket();
/*     */     }
/* 342 */     return wheel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 353 */     int state = WORKER_STATE_UPDATER.get(this);
/* 354 */     switch (state) {
/*     */       case 0:
/* 356 */         if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
/* 357 */           this.workerThread.start();
/*     */         }
/*     */         break;
/*     */       case 1:
/*     */         break;
/*     */       case 2:
/* 363 */         throw new IllegalStateException("cannot be started once stopped");
/*     */       default:
/* 365 */         throw new Error("Invalid WorkerState: " + state);
/*     */     } 
/*     */ 
/*     */     
/* 369 */     while (this.startTime == 0L) {
/*     */       try {
/* 371 */         this.startTimeInitialized.await();
/* 372 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Timeout> stop() {
/* 380 */     if (Thread.currentThread() == this.workerThread) {
/* 381 */       throw new IllegalStateException(HashedWheelTimer.class
/* 382 */           .getSimpleName() + ".stop() cannot be called from " + TimerTask.class
/*     */           
/* 384 */           .getSimpleName());
/*     */     }
/*     */     
/* 387 */     if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
/*     */       
/* 389 */       if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
/* 390 */         INSTANCE_COUNTER.decrementAndGet();
/* 391 */         if (this.leak != null) {
/* 392 */           boolean closed = this.leak.close(this);
/* 393 */           assert closed;
/*     */         } 
/*     */       } 
/*     */       
/* 397 */       return Collections.emptySet();
/*     */     } 
/*     */     
/*     */     try {
/* 401 */       boolean interrupted = false;
/* 402 */       while (this.workerThread.isAlive()) {
/* 403 */         this.workerThread.interrupt();
/*     */         try {
/* 405 */           this.workerThread.join(100L);
/* 406 */         } catch (InterruptedException ignored) {
/* 407 */           interrupted = true;
/*     */         } 
/*     */       } 
/*     */       
/* 411 */       if (interrupted) {
/* 412 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } finally {
/* 415 */       INSTANCE_COUNTER.decrementAndGet();
/* 416 */       if (this.leak != null) {
/* 417 */         boolean closed = this.leak.close(this);
/* 418 */         assert closed;
/*     */       } 
/*     */     } 
/* 421 */     Set<Timeout> unprocessed = this.worker.unprocessedTimeouts();
/* 422 */     Set<Timeout> cancelled = new HashSet<>(unprocessed.size());
/* 423 */     for (Timeout timeout : unprocessed) {
/* 424 */       if (timeout.cancel()) {
/* 425 */         cancelled.add(timeout);
/*     */       }
/*     */     } 
/* 428 */     return cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
/* 433 */     ObjectUtil.checkNotNull(task, "task");
/* 434 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/* 436 */     long pendingTimeoutsCount = this.pendingTimeouts.incrementAndGet();
/*     */     
/* 438 */     if (this.maxPendingTimeouts > 0L && pendingTimeoutsCount > this.maxPendingTimeouts) {
/* 439 */       this.pendingTimeouts.decrementAndGet();
/* 440 */       throw new RejectedExecutionException("Number of pending timeouts (" + pendingTimeoutsCount + ") is greater than or equal to maximum allowed pending timeouts (" + this.maxPendingTimeouts + ")");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 445 */     start();
/*     */ 
/*     */ 
/*     */     
/* 449 */     long deadline = System.nanoTime() + unit.toNanos(delay) - this.startTime;
/*     */ 
/*     */     
/* 452 */     if (delay > 0L && deadline < 0L) {
/* 453 */       deadline = Long.MAX_VALUE;
/*     */     }
/* 455 */     HashedWheelTimeout timeout = new HashedWheelTimeout(this, task, deadline);
/* 456 */     this.timeouts.add(timeout);
/* 457 */     return timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long pendingTimeouts() {
/* 464 */     return this.pendingTimeouts.get();
/*     */   }
/*     */   
/*     */   private static void reportTooManyInstances() {
/* 468 */     if (logger.isErrorEnabled()) {
/* 469 */       String resourceType = StringUtil.simpleClassName(HashedWheelTimer.class);
/* 470 */       logger.error("You are creating too many " + resourceType + " instances. " + resourceType + " is a shared resource that must be reused across the JVM, so that only a few instances are created.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class Worker
/*     */     implements Runnable
/*     */   {
/* 477 */     private final Set<Timeout> unprocessedTimeouts = new HashSet<>();
/*     */ 
/*     */     
/*     */     private long tick;
/*     */ 
/*     */     
/*     */     public void run() {
/* 484 */       HashedWheelTimer.this.startTime = System.nanoTime();
/* 485 */       if (HashedWheelTimer.this.startTime == 0L)
/*     */       {
/* 487 */         HashedWheelTimer.this.startTime = 1L;
/*     */       }
/*     */ 
/*     */       
/* 491 */       HashedWheelTimer.this.startTimeInitialized.countDown();
/*     */       
/*     */       do {
/* 494 */         long deadline = waitForNextTick();
/* 495 */         if (deadline <= 0L)
/* 496 */           continue;  int idx = (int)(this.tick & HashedWheelTimer.this.mask);
/* 497 */         processCancelledTasks();
/*     */         
/* 499 */         HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[idx];
/* 500 */         transferTimeoutsToBuckets();
/* 501 */         bucket.expireTimeouts(deadline);
/* 502 */         this.tick++;
/*     */       }
/* 504 */       while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
/*     */ 
/*     */       
/* 507 */       for (HashedWheelTimer.HashedWheelBucket bucket : HashedWheelTimer.this.wheel) {
/* 508 */         bucket.clearTimeouts(this.unprocessedTimeouts);
/*     */       }
/*     */       while (true) {
/* 511 */         HashedWheelTimer.HashedWheelTimeout timeout = HashedWheelTimer.this.timeouts.poll();
/* 512 */         if (timeout == null) {
/*     */           break;
/*     */         }
/* 515 */         if (!timeout.isCancelled()) {
/* 516 */           this.unprocessedTimeouts.add(timeout);
/*     */         }
/*     */       } 
/* 519 */       processCancelledTasks();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void transferTimeoutsToBuckets() {
/* 525 */       for (int i = 0; i < 100000; i++) {
/* 526 */         HashedWheelTimer.HashedWheelTimeout timeout = HashedWheelTimer.this.timeouts.poll();
/* 527 */         if (timeout == null) {
/*     */           break;
/*     */         }
/*     */         
/* 531 */         if (timeout.state() != 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 536 */           long calculated = timeout.deadline / HashedWheelTimer.this.tickDuration;
/* 537 */           timeout.remainingRounds = (calculated - this.tick) / HashedWheelTimer.this.wheel.length;
/*     */           
/* 539 */           long ticks = Math.max(calculated, this.tick);
/* 540 */           int stopIndex = (int)(ticks & HashedWheelTimer.this.mask);
/*     */           
/* 542 */           HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[stopIndex];
/* 543 */           bucket.addTimeout(timeout);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private void processCancelledTasks() {
/*     */       while (true) {
/* 549 */         HashedWheelTimer.HashedWheelTimeout timeout = HashedWheelTimer.this.cancelledTimeouts.poll();
/* 550 */         if (timeout == null) {
/*     */           break;
/*     */         }
/*     */         
/*     */         try {
/* 555 */           timeout.removeAfterCancellation();
/* 556 */         } catch (Throwable t) {
/* 557 */           if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 558 */             HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", t);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long waitForNextTick() {
/* 571 */       long deadline = HashedWheelTimer.this.tickDuration * (this.tick + 1L);
/*     */       
/*     */       while (true) {
/* 574 */         long currentTime = System.nanoTime() - HashedWheelTimer.this.startTime;
/* 575 */         long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
/*     */         
/* 577 */         if (sleepTimeMs <= 0L) {
/* 578 */           if (currentTime == Long.MIN_VALUE) {
/* 579 */             return -9223372036854775807L;
/*     */           }
/* 581 */           return currentTime;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 590 */         if (PlatformDependent.isWindows()) {
/* 591 */           sleepTimeMs = sleepTimeMs / 10L * 10L;
/* 592 */           if (sleepTimeMs == 0L) {
/* 593 */             sleepTimeMs = 1L;
/*     */           }
/*     */         } 
/*     */         
/*     */         try {
/* 598 */           Thread.sleep(sleepTimeMs);
/* 599 */         } catch (InterruptedException ignored) {
/* 600 */           if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
/* 601 */             return Long.MIN_VALUE;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public Set<Timeout> unprocessedTimeouts() {
/* 608 */       return Collections.unmodifiableSet(this.unprocessedTimeouts);
/*     */     }
/*     */     
/*     */     private Worker() {}
/*     */   }
/*     */   
/*     */   private static final class HashedWheelTimeout implements Timeout, Runnable {
/*     */     private static final int ST_INIT = 0;
/*     */     private static final int ST_CANCELLED = 1;
/*     */     private static final int ST_EXPIRED = 2;
/* 618 */     private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
/*     */     
/*     */     private final HashedWheelTimer timer;
/*     */     
/*     */     private final TimerTask task;
/*     */     private final long deadline;
/* 624 */     private volatile int state = 0;
/*     */ 
/*     */     
/*     */     long remainingRounds;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout next;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout prev;
/*     */ 
/*     */     
/*     */     HashedWheelTimer.HashedWheelBucket bucket;
/*     */ 
/*     */     
/*     */     HashedWheelTimeout(HashedWheelTimer timer, TimerTask task, long deadline) {
/* 640 */       this.timer = timer;
/* 641 */       this.task = task;
/* 642 */       this.deadline = deadline;
/*     */     }
/*     */ 
/*     */     
/*     */     public Timer timer() {
/* 647 */       return this.timer;
/*     */     }
/*     */ 
/*     */     
/*     */     public TimerTask task() {
/* 652 */       return this.task;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 658 */       if (!compareAndSetState(0, 1)) {
/* 659 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 664 */       this.timer.cancelledTimeouts.add(this);
/* 665 */       return true;
/*     */     }
/*     */     
/*     */     private void remove() {
/* 669 */       HashedWheelTimer.HashedWheelBucket bucket = this.bucket;
/* 670 */       if (bucket != null) {
/* 671 */         bucket.remove(this);
/*     */       }
/* 673 */       this.timer.pendingTimeouts.decrementAndGet();
/*     */     }
/*     */     void removeAfterCancellation() {
/* 676 */       remove();
/* 677 */       this.task.cancelled(this);
/*     */     }
/*     */     
/*     */     public boolean compareAndSetState(int expected, int state) {
/* 681 */       return STATE_UPDATER.compareAndSet(this, expected, state);
/*     */     }
/*     */     
/*     */     public int state() {
/* 685 */       return this.state;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 690 */       return (state() == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isExpired() {
/* 695 */       return (state() == 2);
/*     */     }
/*     */     
/*     */     public void expire() {
/* 699 */       if (!compareAndSetState(0, 2)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 704 */         remove();
/* 705 */         this.timer.taskExecutor.execute(this);
/* 706 */       } catch (Throwable t) {
/* 707 */         if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 708 */           HashedWheelTimer.logger.warn("An exception was thrown while submit " + TimerTask.class.getSimpleName() + " for execution.", t);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 717 */         this.task.run(this);
/* 718 */       } catch (Throwable t) {
/* 719 */         if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 720 */           HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 727 */       long currentTime = System.nanoTime();
/* 728 */       long remaining = this.deadline - currentTime + this.timer.startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 733 */       StringBuilder buf = (new StringBuilder(192)).append(StringUtil.simpleClassName(this)).append('(').append("deadline: ");
/* 734 */       if (remaining > 0L) {
/* 735 */         buf.append(remaining)
/* 736 */           .append(" ns later");
/* 737 */       } else if (remaining < 0L) {
/* 738 */         buf.append(-remaining)
/* 739 */           .append(" ns ago");
/*     */       } else {
/* 741 */         buf.append("now");
/*     */       } 
/*     */       
/* 744 */       if (isCancelled()) {
/* 745 */         buf.append(", cancelled");
/*     */       }
/*     */       
/* 748 */       return buf.append(", task: ")
/* 749 */         .append(task())
/* 750 */         .append(')')
/* 751 */         .toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class HashedWheelBucket
/*     */   {
/*     */     private HashedWheelTimer.HashedWheelTimeout head;
/*     */ 
/*     */     
/*     */     private HashedWheelTimer.HashedWheelTimeout tail;
/*     */ 
/*     */     
/*     */     private HashedWheelBucket() {}
/*     */ 
/*     */     
/*     */     public void addTimeout(HashedWheelTimer.HashedWheelTimeout timeout) {
/* 769 */       assert timeout.bucket == null;
/* 770 */       timeout.bucket = this;
/* 771 */       if (this.head == null) {
/* 772 */         this.head = this.tail = timeout;
/*     */       } else {
/* 774 */         this.tail.next = timeout;
/* 775 */         timeout.prev = this.tail;
/* 776 */         this.tail = timeout;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void expireTimeouts(long deadline) {
/* 784 */       HashedWheelTimer.HashedWheelTimeout timeout = this.head;
/*     */ 
/*     */       
/* 787 */       while (timeout != null) {
/* 788 */         HashedWheelTimer.HashedWheelTimeout next = timeout.next;
/* 789 */         if (timeout.remainingRounds <= 0L) {
/* 790 */           if (timeout.deadline <= deadline) {
/* 791 */             timeout.expire();
/*     */           } else {
/*     */             
/* 794 */             throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", new Object[] {
/* 795 */                     Long.valueOf(HashedWheelTimer.HashedWheelTimeout.access$800(timeout)), Long.valueOf(deadline) }));
/*     */           } 
/* 797 */         } else if (!timeout.isCancelled()) {
/* 798 */           timeout.remainingRounds--;
/*     */         } 
/* 800 */         timeout = next;
/*     */       } 
/*     */     }
/*     */     
/*     */     public HashedWheelTimer.HashedWheelTimeout remove(HashedWheelTimer.HashedWheelTimeout timeout) {
/* 805 */       HashedWheelTimer.HashedWheelTimeout prev = timeout.prev;
/* 806 */       HashedWheelTimer.HashedWheelTimeout next = timeout.next;
/*     */ 
/*     */       
/* 809 */       if (prev != null) {
/* 810 */         prev.next = next;
/*     */       }
/* 812 */       if (next != null) {
/* 813 */         next.prev = prev;
/*     */       }
/*     */       
/* 816 */       if (timeout == this.head) {
/* 817 */         this.head = next;
/*     */       }
/* 819 */       if (timeout == this.tail) {
/* 820 */         this.tail = prev;
/*     */       }
/*     */       
/* 823 */       timeout.prev = null;
/* 824 */       timeout.next = null;
/* 825 */       timeout.bucket = null;
/* 826 */       return next;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearTimeouts(Set<Timeout> set) {
/*     */       while (true) {
/* 834 */         HashedWheelTimer.HashedWheelTimeout timeout = pollTimeout();
/* 835 */         if (timeout == null) {
/*     */           return;
/*     */         }
/* 838 */         if (timeout.isExpired() || timeout.isCancelled()) {
/*     */           continue;
/*     */         }
/* 841 */         set.add(timeout);
/*     */       } 
/*     */     }
/*     */     
/*     */     private HashedWheelTimer.HashedWheelTimeout pollTimeout() {
/* 846 */       HashedWheelTimer.HashedWheelTimeout head = this.head;
/* 847 */       if (head == null) {
/* 848 */         return null;
/*     */       }
/* 850 */       HashedWheelTimer.HashedWheelTimeout next = head.next;
/* 851 */       if (next == null) {
/* 852 */         this.tail = this.head = null;
/*     */       } else {
/* 854 */         this.head = next;
/* 855 */         next.prev = null;
/*     */       } 
/*     */ 
/*     */       
/* 859 */       head.next = null;
/* 860 */       head.prev = null;
/* 861 */       head.bucket = null;
/* 862 */       return head;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\HashedWheelTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */