/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.AbstractExecutorService;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.Async.Execute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractEventExecutor
/*     */   extends AbstractExecutorService
/*     */   implements EventExecutor
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEventExecutor.class);
/*     */   
/*     */   static final long DEFAULT_SHUTDOWN_QUIET_PERIOD = 2L;
/*     */   
/*     */   static final long DEFAULT_SHUTDOWN_TIMEOUT = 15L;
/*     */   private final EventExecutorGroup parent;
/*  43 */   private final Collection<EventExecutor> selfCollection = Collections.singleton(this);
/*     */   
/*     */   protected AbstractEventExecutor() {
/*  46 */     this((EventExecutorGroup)null);
/*     */   }
/*     */   
/*     */   protected AbstractEventExecutor(EventExecutorGroup parent) {
/*  50 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/*  55 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/*  65 */     return this.selfCollection.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/*  70 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
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
/*     */   @Deprecated
/*     */   public List<Runnable> shutdownNow() {
/*  86 */     shutdown();
/*  87 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submit(Runnable task) {
/*  92 */     return (Future)super.submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/*  97 */     return (Future<T>)super.<T>submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/* 102 */     return (Future<T>)super.<T>submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
/* 107 */     return new PromiseTask<>(this, runnable, value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
/* 112 */     return new PromiseTask<>(this, callable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 123 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 133 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void safeExecute(Runnable task) {
/*     */     try {
/* 141 */       runTask(task);
/* 142 */     } catch (Throwable t) {
/* 143 */       logger.warn("A task raised an exception. Task: {}", task, t);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void runTask(@Execute Runnable task) {
/* 148 */     task.run();
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
/*     */   public void lazyExecute(Runnable task) {
/* 160 */     execute(task);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public abstract void shutdown();
/*     */   
/*     */   @Deprecated
/*     */   public static interface LazyRunnable extends Runnable {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\AbstractEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */