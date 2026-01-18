/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PriorityQueue;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.ThreadExecutorMap;
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.Async.Schedule;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlobalEventExecutor
/*     */   extends AbstractScheduledEventExecutor
/*     */   implements OrderedEventExecutor
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
/*     */   
/*     */   private static final long SCHEDULE_QUIET_PERIOD_INTERVAL;
/*     */   
/*     */   static {
/*  50 */     int quietPeriod = SystemPropertyUtil.getInt("io.netty.globalEventExecutor.quietPeriodSeconds", 1);
/*  51 */     if (quietPeriod <= 0) {
/*  52 */       quietPeriod = 1;
/*     */     }
/*  54 */     logger.debug("-Dio.netty.globalEventExecutor.quietPeriodSeconds: {}", Integer.valueOf(quietPeriod));
/*     */     
/*  56 */     SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(quietPeriod);
/*     */   }
/*     */   
/*  59 */   public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
/*     */   
/*  61 */   final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
/*  62 */   final ScheduledFutureTask<Void> quietPeriodTask = new ScheduledFutureTask<>(this, 
/*  63 */       Executors.callable(new Runnable()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public void run() {}
/*     */         }, 
/*     */         
/*  71 */         null), deadlineNanos(getCurrentTimeNanos(), SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final ThreadFactory threadFactory;
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final TaskRunner taskRunner = new TaskRunner();
/*  81 */   private final AtomicBoolean started = new AtomicBoolean();
/*     */   
/*     */   volatile Thread thread;
/*     */   private final Future<?> terminationFuture;
/*     */   
/*     */   private GlobalEventExecutor() {
/*  87 */     scheduleFromEventLoop(this.quietPeriodTask);
/*  88 */     this.threadFactory = ThreadExecutorMap.apply(new DefaultThreadFactory(
/*  89 */           DefaultThreadFactory.toPoolName(getClass()), false, 5, null), this);
/*     */     
/*  91 */     UnsupportedOperationException terminationFailure = new UnsupportedOperationException();
/*  92 */     ThrowableUtil.unknownStackTrace(terminationFailure, GlobalEventExecutor.class, "terminationFuture");
/*  93 */     this.terminationFuture = new FailedFuture(this, terminationFailure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Runnable takeTask() {
/* 102 */     BlockingQueue<Runnable> taskQueue = this.taskQueue;
/*     */     while (true) {
/* 104 */       ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 105 */       if (scheduledTask == null) {
/* 106 */         Runnable runnable = null;
/*     */         try {
/* 108 */           runnable = taskQueue.take();
/* 109 */         } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */         
/* 112 */         return runnable;
/*     */       } 
/* 114 */       long delayNanos = scheduledTask.delayNanos();
/* 115 */       Runnable task = null;
/* 116 */       if (delayNanos > 0L) {
/*     */         try {
/* 118 */           task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/* 119 */         } catch (InterruptedException e) {
/*     */           
/* 121 */           return null;
/*     */         } 
/*     */       }
/* 124 */       if (task == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 129 */         fetchFromScheduledTaskQueue();
/* 130 */         task = taskQueue.poll();
/*     */       } 
/*     */       
/* 133 */       if (task != null) {
/* 134 */         return task;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fetchFromScheduledTaskQueue() {
/* 141 */     long nanoTime = getCurrentTimeNanos();
/*     */     ScheduledFutureTask scheduledTask;
/* 143 */     while ((scheduledTask = (ScheduledFutureTask)pollScheduledTask(nanoTime)) != null) {
/* 144 */       if (scheduledTask.isCancelled()) {
/*     */         continue;
/*     */       }
/* 147 */       this.taskQueue.add(scheduledTask);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int pendingTasks() {
/* 155 */     return this.taskQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTask(Runnable task) {
/* 163 */     this.taskQueue.add((Runnable)ObjectUtil.checkNotNull(task, "task"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 168 */     return (thread == this.thread);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 173 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 178 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/* 204 */     return false;
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
/*     */   public boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
/* 216 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/* 218 */     Thread thread = this.thread;
/* 219 */     if (thread == null) {
/* 220 */       throw new IllegalStateException("thread was not started");
/*     */     }
/* 222 */     thread.join(unit.toMillis(timeout));
/* 223 */     return !thread.isAlive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable task) {
/* 228 */     execute0(task);
/*     */   }
/*     */   
/*     */   private void execute0(@Schedule Runnable task) {
/* 232 */     addTask((Runnable)ObjectUtil.checkNotNull(task, "task"));
/* 233 */     if (!inEventLoop()) {
/* 234 */       startThread();
/*     */     }
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 239 */     if (this.started.compareAndSet(false, true)) {
/* 240 */       final Thread callingThread = Thread.currentThread();
/* 241 */       ClassLoader parentCCL = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */           {
/*     */             public ClassLoader run() {
/* 244 */               return callingThread.getContextClassLoader();
/*     */             }
/*     */           });
/*     */       
/* 248 */       setContextClassLoader(callingThread, (ClassLoader)null);
/*     */       try {
/* 250 */         Thread t = this.threadFactory.newThread(this.taskRunner);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 256 */         setContextClassLoader(t, (ClassLoader)null);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 261 */         this.thread = t;
/* 262 */         t.start();
/*     */       } finally {
/* 264 */         setContextClassLoader(callingThread, parentCCL);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setContextClassLoader(final Thread t, final ClassLoader cl) {
/* 270 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 273 */             t.setContextClassLoader(cl);
/* 274 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   final class TaskRunner
/*     */     implements Runnable {
/*     */     public void run() {
/*     */       while (true) {
/* 283 */         Runnable task = GlobalEventExecutor.this.takeTask();
/* 284 */         if (task != null) {
/*     */           try {
/* 286 */             AbstractEventExecutor.runTask(task);
/* 287 */           } catch (Throwable t) {
/* 288 */             GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", t);
/*     */           } 
/*     */           
/* 291 */           if (task != GlobalEventExecutor.this.quietPeriodTask) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 296 */         PriorityQueue<ScheduledFutureTask<?>> priorityQueue = GlobalEventExecutor.this.scheduledTaskQueue;
/*     */         
/* 298 */         if (GlobalEventExecutor.this.taskQueue.isEmpty() && (priorityQueue == null || priorityQueue.size() == 1)) {
/*     */ 
/*     */ 
/*     */           
/* 302 */           boolean stopped = GlobalEventExecutor.this.started.compareAndSet(true, false);
/* 303 */           assert stopped;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 308 */           if (GlobalEventExecutor.this.taskQueue.isEmpty()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 317 */           if (!GlobalEventExecutor.this.started.compareAndSet(false, true))
/*     */             break; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\GlobalEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */