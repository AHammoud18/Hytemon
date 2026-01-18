/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.DefaultPriorityQueue;
/*     */ import io.netty.util.internal.PriorityQueueNode;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Delayed;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ScheduledFutureTask<V>
/*     */   extends PromiseTask<V>
/*     */   implements ScheduledFuture<V>, PriorityQueueNode
/*     */ {
/*     */   private long id;
/*     */   private long deadlineNanos;
/*     */   private final long periodNanos;
/*  35 */   private int queueIndex = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Runnable runnable, long nanoTime) {
/*  40 */     super(executor, runnable);
/*  41 */     this.deadlineNanos = nanoTime;
/*  42 */     this.periodNanos = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Runnable runnable, long nanoTime, long period) {
/*  48 */     super(executor, runnable);
/*  49 */     this.deadlineNanos = nanoTime;
/*  50 */     this.periodNanos = validatePeriod(period);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable<V> callable, long nanoTime, long period) {
/*  56 */     super(executor, callable);
/*  57 */     this.deadlineNanos = nanoTime;
/*  58 */     this.periodNanos = validatePeriod(period);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ScheduledFutureTask(AbstractScheduledEventExecutor executor, Callable<V> callable, long nanoTime) {
/*  64 */     super(executor, callable);
/*  65 */     this.deadlineNanos = nanoTime;
/*  66 */     this.periodNanos = 0L;
/*     */   }
/*     */   
/*     */   private static long validatePeriod(long period) {
/*  70 */     if (period == 0L) {
/*  71 */       throw new IllegalArgumentException("period: 0 (expected: != 0)");
/*     */     }
/*  73 */     return period;
/*     */   }
/*     */   
/*     */   ScheduledFutureTask<V> setId(long id) {
/*  77 */     if (this.id == 0L) {
/*  78 */       this.id = id;
/*     */     }
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   long getId() {
/*  84 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EventExecutor executor() {
/*  89 */     return super.executor();
/*     */   }
/*     */   
/*     */   public long deadlineNanos() {
/*  93 */     return this.deadlineNanos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setConsumed() {
/*  99 */     if (this.periodNanos == 0L) {
/* 100 */       assert scheduledExecutor().getCurrentTimeNanos() >= this.deadlineNanos;
/* 101 */       this.deadlineNanos = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long delayNanos() {
/* 106 */     if (this.deadlineNanos == 0L) {
/* 107 */       return 0L;
/*     */     }
/* 109 */     return delayNanos(scheduledExecutor().getCurrentTimeNanos());
/*     */   }
/*     */   
/*     */   static long deadlineToDelayNanos(long currentTimeNanos, long deadlineNanos) {
/* 113 */     return (deadlineNanos == 0L) ? 0L : Math.max(0L, deadlineNanos - currentTimeNanos);
/*     */   }
/*     */   
/*     */   public long delayNanos(long currentTimeNanos) {
/* 117 */     return deadlineToDelayNanos(currentTimeNanos, this.deadlineNanos);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDelay(TimeUnit unit) {
/* 122 */     return unit.convert(delayNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Delayed o) {
/* 127 */     if (this == o) {
/* 128 */       return 0;
/*     */     }
/*     */     
/* 131 */     ScheduledFutureTask<?> that = (ScheduledFutureTask)o;
/* 132 */     long d = deadlineNanos() - that.deadlineNanos();
/* 133 */     if (d < 0L)
/* 134 */       return -1; 
/* 135 */     if (d > 0L)
/* 136 */       return 1; 
/* 137 */     if (this.id < that.id) {
/* 138 */       return -1;
/*     */     }
/* 140 */     assert this.id != that.id;
/* 141 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 147 */     assert executor().inEventLoop();
/*     */     try {
/* 149 */       if (delayNanos() > 0L) {
/*     */         
/* 151 */         if (isCancelled()) {
/* 152 */           scheduledExecutor().scheduledTaskQueue().removeTyped(this);
/*     */         } else {
/* 154 */           scheduledExecutor().scheduleFromEventLoop(this);
/*     */         } 
/*     */         return;
/*     */       } 
/* 158 */       if (this.periodNanos == 0L) {
/* 159 */         if (setUncancellableInternal()) {
/* 160 */           V result = runTask();
/* 161 */           setSuccessInternal(result);
/*     */         }
/*     */       
/*     */       }
/* 165 */       else if (!isCancelled()) {
/* 166 */         runTask();
/* 167 */         if (!executor().isShutdown()) {
/* 168 */           if (this.periodNanos > 0L) {
/* 169 */             this.deadlineNanos += this.periodNanos;
/*     */           } else {
/* 171 */             this.deadlineNanos = scheduledExecutor().getCurrentTimeNanos() - this.periodNanos;
/*     */           } 
/* 173 */           if (!isCancelled()) {
/* 174 */             scheduledExecutor().scheduleFromEventLoop(this);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 179 */     } catch (Throwable cause) {
/* 180 */       setFailureInternal(cause);
/*     */     } 
/*     */   }
/*     */   
/*     */   private AbstractScheduledEventExecutor scheduledExecutor() {
/* 185 */     return (AbstractScheduledEventExecutor)executor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 195 */     boolean canceled = super.cancel(mayInterruptIfRunning);
/* 196 */     if (canceled) {
/* 197 */       scheduledExecutor().removeScheduled(this);
/*     */     }
/* 199 */     return canceled;
/*     */   }
/*     */   
/*     */   boolean cancelWithoutRemove(boolean mayInterruptIfRunning) {
/* 203 */     return super.cancel(mayInterruptIfRunning);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 208 */     StringBuilder buf = super.toStringBuilder();
/* 209 */     buf.setCharAt(buf.length() - 1, ',');
/*     */     
/* 211 */     return buf.append(" deadline: ")
/* 212 */       .append(this.deadlineNanos)
/* 213 */       .append(", period: ")
/* 214 */       .append(this.periodNanos)
/* 215 */       .append(')');
/*     */   }
/*     */ 
/*     */   
/*     */   public int priorityQueueIndex(DefaultPriorityQueue<?> queue) {
/* 220 */     return this.queueIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void priorityQueueIndex(DefaultPriorityQueue<?> queue, int i) {
/* 225 */     this.queueIndex = i;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ScheduledFutureTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */