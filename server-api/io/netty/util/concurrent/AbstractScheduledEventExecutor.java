/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.DefaultPriorityQueue;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PriorityQueue;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractScheduledEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  32 */   private static final Comparator<ScheduledFutureTask<?>> SCHEDULED_FUTURE_TASK_COMPARATOR = new Comparator<ScheduledFutureTask<?>>()
/*     */     {
/*     */       public int compare(ScheduledFutureTask<?> o1, ScheduledFutureTask<?> o2)
/*     */       {
/*  36 */         return o1.compareTo(o2);
/*     */       }
/*     */     };
/*     */   
/*  40 */   static final Runnable WAKEUP_TASK = new Runnable()
/*     */     {
/*     */       public void run() {}
/*     */     };
/*     */ 
/*     */   
/*     */   PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue;
/*     */   
/*     */   long nextTaskId;
/*     */   
/*     */   protected AbstractScheduledEventExecutor() {}
/*     */   
/*     */   protected AbstractScheduledEventExecutor(EventExecutorGroup parent) {
/*  53 */     super(parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public Ticker ticker() {
/*  58 */     return Ticker.systemTicker();
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
/*     */   @Deprecated
/*     */   protected long getCurrentTimeNanos() {
/*  76 */     return ticker().nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static long nanoTime() {
/*  84 */     return Ticker.systemTicker().nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   static long defaultCurrentTimeNanos() {
/*  92 */     return Ticker.systemTicker().nanoTime();
/*     */   }
/*     */   
/*     */   static long deadlineNanos(long nanoTime, long delay) {
/*  96 */     long deadlineNanos = nanoTime + delay;
/*     */     
/*  98 */     return (deadlineNanos < 0L) ? Long.MAX_VALUE : deadlineNanos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static long deadlineToDelayNanos(long deadlineNanos) {
/* 110 */     return ScheduledFutureTask.deadlineToDelayNanos(defaultCurrentTimeNanos(), deadlineNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long delayNanos(long currentTimeNanos, long scheduledPurgeInterval) {
/* 117 */     currentTimeNanos -= ticker().initialNanoTime();
/*     */     
/* 119 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 120 */     if (scheduledTask == null) {
/* 121 */       return scheduledPurgeInterval;
/*     */     }
/*     */     
/* 124 */     return scheduledTask.delayNanos(currentTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected static long initialNanoTime() {
/* 134 */     return Ticker.systemTicker().initialNanoTime();
/*     */   }
/*     */   
/*     */   PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue() {
/* 138 */     if (this.scheduledTaskQueue == null) {
/* 139 */       this.scheduledTaskQueue = (PriorityQueue<ScheduledFutureTask<?>>)new DefaultPriorityQueue(SCHEDULED_FUTURE_TASK_COMPARATOR, 11);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 144 */     return this.scheduledTaskQueue;
/*     */   }
/*     */   
/*     */   private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
/* 148 */     return (queue == null || queue.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cancelScheduledTasks() {
/* 157 */     assert inEventLoop();
/* 158 */     PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue = this.scheduledTaskQueue;
/* 159 */     if (isNullOrEmpty((Queue<ScheduledFutureTask<?>>)scheduledTaskQueue)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 164 */     ScheduledFutureTask[] arrayOfScheduledFutureTask = (ScheduledFutureTask[])scheduledTaskQueue.toArray((Object[])new ScheduledFutureTask[0]);
/*     */     
/* 166 */     for (ScheduledFutureTask<?> task : arrayOfScheduledFutureTask) {
/* 167 */       task.cancelWithoutRemove(false);
/*     */     }
/*     */     
/* 170 */     scheduledTaskQueue.clearIgnoringIndexes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Runnable pollScheduledTask() {
/* 177 */     return pollScheduledTask(getCurrentTimeNanos());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fetchFromScheduledTaskQueue(Queue<Runnable> taskQueue) {
/*     */     ScheduledFutureTask scheduledTask;
/* 188 */     assert inEventLoop();
/* 189 */     Objects.requireNonNull(taskQueue, "taskQueue");
/* 190 */     if (this.scheduledTaskQueue == null || this.scheduledTaskQueue.isEmpty()) {
/* 191 */       return true;
/*     */     }
/* 193 */     long nanoTime = getCurrentTimeNanos();
/*     */     while (true) {
/* 195 */       scheduledTask = (ScheduledFutureTask)pollScheduledTask(nanoTime);
/* 196 */       if (scheduledTask == null) {
/* 197 */         return true;
/*     */       }
/* 199 */       if (scheduledTask.isCancelled()) {
/*     */         continue;
/*     */       }
/* 202 */       if (!taskQueue.offer(scheduledTask))
/*     */         break; 
/* 204 */     }  this.scheduledTaskQueue.add(scheduledTask);
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Runnable pollScheduledTask(long nanoTime) {
/* 215 */     assert inEventLoop();
/*     */     
/* 217 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 218 */     if (scheduledTask == null || scheduledTask.deadlineNanos() - nanoTime > 0L) {
/* 219 */       return null;
/*     */     }
/* 221 */     this.scheduledTaskQueue.remove();
/* 222 */     scheduledTask.setConsumed();
/* 223 */     return scheduledTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long nextScheduledTaskNano() {
/* 230 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 231 */     return (scheduledTask != null) ? scheduledTask.delayNanos() : -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long nextScheduledTaskDeadlineNanos() {
/* 239 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 240 */     return (scheduledTask != null) ? scheduledTask.deadlineNanos() : -1L;
/*     */   }
/*     */   
/*     */   final ScheduledFutureTask<?> peekScheduledTask() {
/* 244 */     PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
/* 245 */     return (priorityQueue != null) ? priorityQueue.peek() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean hasScheduledTasks() {
/* 252 */     ScheduledFutureTask<?> scheduledTask = peekScheduledTask();
/* 253 */     return (scheduledTask != null && scheduledTask.deadlineNanos() <= getCurrentTimeNanos());
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 258 */     ObjectUtil.checkNotNull(command, "command");
/* 259 */     ObjectUtil.checkNotNull(unit, "unit");
/* 260 */     if (delay < 0L) {
/* 261 */       delay = 0L;
/*     */     }
/* 263 */     validateScheduled0(delay, unit);
/*     */     
/* 265 */     return schedule(new ScheduledFutureTask(this, command, 
/*     */ 
/*     */           
/* 268 */           deadlineNanos(getCurrentTimeNanos(), unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 273 */     ObjectUtil.checkNotNull(callable, "callable");
/* 274 */     ObjectUtil.checkNotNull(unit, "unit");
/* 275 */     if (delay < 0L) {
/* 276 */       delay = 0L;
/*     */     }
/* 278 */     validateScheduled0(delay, unit);
/*     */     
/* 280 */     return schedule(new ScheduledFutureTask<>(this, callable, 
/* 281 */           deadlineNanos(getCurrentTimeNanos(), unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 286 */     ObjectUtil.checkNotNull(command, "command");
/* 287 */     ObjectUtil.checkNotNull(unit, "unit");
/* 288 */     if (initialDelay < 0L) {
/* 289 */       throw new IllegalArgumentException(
/* 290 */           String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/* 292 */     if (period <= 0L) {
/* 293 */       throw new IllegalArgumentException(
/* 294 */           String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(period) }));
/*     */     }
/* 296 */     validateScheduled0(initialDelay, unit);
/* 297 */     validateScheduled0(period, unit);
/*     */     
/* 299 */     return schedule(new ScheduledFutureTask(this, command, 
/* 300 */           deadlineNanos(getCurrentTimeNanos(), unit.toNanos(initialDelay)), unit.toNanos(period)));
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 305 */     ObjectUtil.checkNotNull(command, "command");
/* 306 */     ObjectUtil.checkNotNull(unit, "unit");
/* 307 */     if (initialDelay < 0L) {
/* 308 */       throw new IllegalArgumentException(
/* 309 */           String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/* 311 */     if (delay <= 0L) {
/* 312 */       throw new IllegalArgumentException(
/* 313 */           String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 316 */     validateScheduled0(initialDelay, unit);
/* 317 */     validateScheduled0(delay, unit);
/*     */     
/* 319 */     return schedule(new ScheduledFutureTask(this, command, 
/* 320 */           deadlineNanos(getCurrentTimeNanos(), unit.toNanos(initialDelay)), -unit.toNanos(delay)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateScheduled0(long amount, TimeUnit unit) {
/* 325 */     validateScheduled(amount, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void validateScheduled(long amount, TimeUnit unit) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void scheduleFromEventLoop(ScheduledFutureTask<?> task) {
/* 340 */     if (task.getId() == 0L) {
/* 341 */       task.setId(++this.nextTaskId);
/*     */     }
/* 343 */     scheduledTaskQueue().add(task);
/*     */   }
/*     */   
/*     */   private <V> ScheduledFuture<V> schedule(ScheduledFutureTask<V> task) {
/* 347 */     if (inEventLoop()) {
/* 348 */       scheduleFromEventLoop(task);
/*     */     } else {
/* 350 */       long deadlineNanos = task.deadlineNanos();
/*     */       
/* 352 */       if (beforeScheduledTaskSubmitted(deadlineNanos)) {
/* 353 */         execute(task);
/*     */       } else {
/* 355 */         lazyExecute(task);
/*     */         
/* 357 */         if (afterScheduledTaskSubmitted(deadlineNanos)) {
/* 358 */           execute(WAKEUP_TASK);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 363 */     return task;
/*     */   }
/*     */   
/*     */   final void removeScheduled(ScheduledFutureTask<?> task) {
/* 367 */     assert task.isCancelled();
/* 368 */     if (inEventLoop()) {
/* 369 */       scheduledTaskQueue().removeTyped(task);
/*     */     } else {
/*     */       
/* 372 */       scheduleRemoveScheduled(task);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void scheduleRemoveScheduled(ScheduledFutureTask<?> task) {
/* 378 */     lazyExecute(task);
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
/*     */   protected boolean beforeScheduledTaskSubmitted(long deadlineNanos) {
/* 395 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean afterScheduledTaskSubmitted(long deadlineNanos) {
/* 405 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\AbstractScheduledEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */