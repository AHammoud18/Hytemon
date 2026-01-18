/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Delayed;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionHandler;
/*     */ import java.util.concurrent.RunnableScheduledFuture;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class UnorderedThreadPoolEventExecutor
/*     */   extends ScheduledThreadPoolExecutor
/*     */   implements EventExecutor
/*     */ {
/*  52 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnorderedThreadPoolEventExecutor.class);
/*     */ 
/*     */   
/*  55 */   private final Promise<?> terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
/*  56 */   private final Set<EventExecutor> executorSet = Collections.singleton(this);
/*  57 */   private final Set<Thread> eventLoopThreads = ConcurrentHashMap.newKeySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnorderedThreadPoolEventExecutor(int corePoolSize) {
/*  64 */     this(corePoolSize, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory) {
/*  71 */     super(corePoolSize, threadFactory);
/*  72 */     setThreadFactory(new AccountingThreadFactory(threadFactory, this.eventLoopThreads));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnorderedThreadPoolEventExecutor(int corePoolSize, RejectedExecutionHandler handler) {
/*  80 */     this(corePoolSize, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class), handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnorderedThreadPoolEventExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
/*  88 */     super(corePoolSize, threadFactory, handler);
/*  89 */     setThreadFactory(new AccountingThreadFactory(threadFactory, this.eventLoopThreads));
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/* 104 */     return inEventLoop(Thread.currentThread());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 109 */     return this.eventLoopThreads.contains(thread);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Promise<V> newPromise() {
/* 114 */     return new DefaultPromise<>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise() {
/* 119 */     return new DefaultProgressivePromise<>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Future<V> newSucceededFuture(V result) {
/* 124 */     return new SucceededFuture<>(this, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Future<V> newFailedFuture(Throwable cause) {
/* 129 */     return new FailedFuture<>(this, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 134 */     return isShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Runnable> shutdownNow() {
/* 139 */     List<Runnable> tasks = super.shutdownNow();
/* 140 */     this.terminationFuture.trySuccess(null);
/* 141 */     return tasks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 146 */     super.shutdown();
/* 147 */     this.terminationFuture.trySuccess(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/* 152 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 159 */     shutdown();
/* 160 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 165 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 170 */     return this.executorSet.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
/* 175 */     return (runnable instanceof NonNotifyRunnable) ? 
/* 176 */       task : new RunnableScheduledFutureTask<>(this, task, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> task) {
/* 181 */     return new RunnableScheduledFutureTask<>(this, task, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 186 */     return (ScheduledFuture)super.schedule(command, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 191 */     return (ScheduledFuture<V>)super.<V>schedule(callable, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 196 */     return (ScheduledFuture)super.scheduleAtFixedRate(command, initialDelay, period, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 201 */     return (ScheduledFuture)super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submit(Runnable task) {
/* 206 */     return (Future)super.submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/* 211 */     return (Future<T>)super.<T>submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/* 216 */     return (Future<T>)super.<T>submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/* 221 */     super.schedule(new NonNotifyRunnable(command), 0L, TimeUnit.NANOSECONDS);
/*     */   }
/*     */   
/*     */   private static final class RunnableScheduledFutureTask<V>
/*     */     extends PromiseTask<V> implements RunnableScheduledFuture<V>, ScheduledFuture<V> {
/*     */     private final RunnableScheduledFuture<V> future;
/*     */     private final boolean wasCallable;
/*     */     
/*     */     RunnableScheduledFutureTask(EventExecutor executor, RunnableScheduledFuture<V> future, boolean wasCallable) {
/* 230 */       super(executor, future);
/* 231 */       this.future = future;
/* 232 */       this.wasCallable = wasCallable;
/*     */     }
/*     */ 
/*     */     
/*     */     V runTask() throws Throwable {
/* 237 */       V result = super.runTask();
/* 238 */       if (result == null && this.wasCallable) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         assert this.future.isDone();
/*     */         try {
/* 245 */           return this.future.get();
/* 246 */         } catch (ExecutionException e) {
/*     */           
/* 248 */           throw e.getCause();
/*     */         } 
/*     */       } 
/* 251 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 256 */       if (!isPeriodic()) {
/* 257 */         super.run();
/* 258 */       } else if (!isDone()) {
/*     */         
/*     */         try {
/* 261 */           runTask();
/* 262 */         } catch (Throwable cause) {
/* 263 */           if (!tryFailureInternal(cause)) {
/* 264 */             UnorderedThreadPoolEventExecutor.logger.warn("Failure during execution of task", cause);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isPeriodic() {
/* 272 */       return this.future.isPeriodic();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getDelay(TimeUnit unit) {
/* 277 */       return this.future.getDelay(unit);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Delayed o) {
/* 282 */       return this.future.compareTo(o);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class NonNotifyRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private final Runnable task;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NonNotifyRunnable(Runnable task) {
/* 298 */       this.task = task;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 303 */       this.task.run();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AccountingThreadFactory implements ThreadFactory {
/*     */     private final ThreadFactory delegate;
/*     */     private final Set<Thread> threads;
/*     */     
/*     */     private AccountingThreadFactory(ThreadFactory delegate, Set<Thread> threads) {
/* 312 */       this.delegate = delegate;
/* 313 */       this.threads = threads;
/*     */     }
/*     */ 
/*     */     
/*     */     public Thread newThread(@NotNull Runnable r) {
/* 318 */       return this.delegate.newThread(() -> {
/*     */             this.threads.add(Thread.currentThread());
/*     */             try {
/*     */               r.run();
/*     */             } finally {
/*     */               this.threads.remove(Thread.currentThread());
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\UnorderedThreadPoolEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */