/*     */ package io.sentry.transport;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.SentryDate;
/*     */ import io.sentry.SentryDateProvider;
/*     */ import io.sentry.SentryLevel;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.RejectedExecutionHandler;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QueuedThreadPoolExecutor
/*     */   extends ThreadPoolExecutor
/*     */ {
/*     */   private final int maxQueueSize;
/*     */   @Nullable
/*  30 */   private SentryDate lastRejectTimestamp = null; @NotNull
/*     */   private final ILogger logger;
/*     */   @NotNull
/*  33 */   private final ReusableCountLatch unfinishedTasksCount = new ReusableCountLatch(); @NotNull
/*  34 */   private final SentryDateProvider dateProvider; private static final long RECENT_THRESHOLD = DateUtils.millisToNanos(2000L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QueuedThreadPoolExecutor(int corePoolSize, int maxQueueSize, @NotNull ThreadFactory threadFactory, @NotNull RejectedExecutionHandler rejectedExecutionHandler, @NotNull ILogger logger, @NotNull SentryDateProvider dateProvider) {
/*  52 */     super(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory, rejectedExecutionHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.maxQueueSize = maxQueueSize;
/*  61 */     this.logger = logger;
/*  62 */     this.dateProvider = dateProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submit(@NotNull Runnable task) {
/*  67 */     if (isSchedulingAllowed()) {
/*  68 */       this.unfinishedTasksCount.increment();
/*     */       try {
/*  70 */         return super.submit(task);
/*  71 */       } catch (RejectedExecutionException e) {
/*  72 */         this.unfinishedTasksCount.decrement();
/*  73 */         this.lastRejectTimestamp = this.dateProvider.now();
/*  74 */         this.logger.log(SentryLevel.WARNING, "Submit rejected by thread pool executor", e);
/*  75 */         return new CancelledFuture();
/*     */       } 
/*     */     } 
/*  78 */     this.lastRejectTimestamp = this.dateProvider.now();
/*     */     
/*  80 */     this.logger.log(SentryLevel.WARNING, "Submit cancelled", new Object[0]);
/*  81 */     return new CancelledFuture();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void afterExecute(@NotNull Runnable r, @Nullable Throwable t) {
/*     */     try {
/*  89 */       super.afterExecute(r, t);
/*     */     } finally {
/*  91 */       this.unfinishedTasksCount.decrement();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void waitTillIdle(long timeoutMillis) {
/*     */     try {
/*  98 */       this.unfinishedTasksCount.waitTillZero(timeoutMillis, TimeUnit.MILLISECONDS);
/*  99 */     } catch (InterruptedException e) {
/* 100 */       this.logger.log(SentryLevel.ERROR, "Failed to wait till idle", e);
/* 101 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSchedulingAllowed() {
/* 106 */     return (this.unfinishedTasksCount.getCount() < this.maxQueueSize);
/*     */   }
/*     */   
/*     */   public boolean didRejectRecently() {
/* 110 */     SentryDate lastReject = this.lastRejectTimestamp;
/* 111 */     if (lastReject == null) {
/* 112 */       return false;
/*     */     }
/*     */     
/* 115 */     long diff = this.dateProvider.now().diff(lastReject);
/* 116 */     return (diff < RECENT_THRESHOLD);
/*     */   }
/*     */   
/*     */   static final class CancelledFuture<T>
/*     */     implements Future<T> {
/*     */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 122 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 127 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 132 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get() {
/* 137 */       throw new CancellationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(long timeout, @NotNull TimeUnit unit) {
/* 142 */       throw new CancellationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\QueuedThreadPoolExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */