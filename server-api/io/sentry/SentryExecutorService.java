/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class SentryExecutorService
/*     */   implements ISentryExecutorService
/*     */ {
/*     */   private static final int INITIAL_QUEUE_SIZE = 40;
/*     */   private static final int MAX_QUEUE_SIZE = 271;
/*     */   @NotNull
/*     */   private final ScheduledThreadPoolExecutor executorService;
/*     */   @NotNull
/*  33 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*     */   
/*     */   @NotNull
/*     */   private final Runnable dummyRunnable = () -> {
/*     */     
/*     */     };
/*     */   @Nullable
/*     */   private final SentryOptions options;
/*     */   
/*     */   @TestOnly
/*     */   SentryExecutorService(@NotNull ScheduledThreadPoolExecutor executorService, @Nullable SentryOptions options) {
/*  44 */     this.executorService = executorService;
/*  45 */     this.options = options;
/*     */   }
/*     */   
/*     */   public SentryExecutorService(@Nullable SentryOptions options) {
/*  49 */     this(new ScheduledThreadPoolExecutor(1, new SentryExecutorServiceThreadFactory(null)), options);
/*     */   }
/*     */   
/*     */   public SentryExecutorService() {
/*  53 */     this(new ScheduledThreadPoolExecutor(1, new SentryExecutorServiceThreadFactory(null)), null);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isQueueAvailable() {
/*  58 */     if (this.executorService.getQueue().size() >= 271) {
/*  59 */       this.executorService.purge();
/*     */     }
/*     */     
/*  62 */     return (this.executorService.getQueue().size() < 271);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Future<?> submit(@NotNull Runnable runnable) throws RejectedExecutionException {
/*  68 */     if (isQueueAvailable()) {
/*  69 */       return this.executorService.submit(runnable);
/*     */     }
/*  71 */     if (this.options != null) {
/*  72 */       this.options
/*  73 */         .getLogger()
/*  74 */         .log(SentryLevel.WARNING, "Task " + runnable + " rejected from " + this.executorService, new Object[0]);
/*     */     }
/*  76 */     return new CancelledFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <T> Future<T> submit(@NotNull Callable<T> callable) throws RejectedExecutionException {
/*  82 */     if (isQueueAvailable()) {
/*  83 */       return this.executorService.submit(callable);
/*     */     }
/*  85 */     if (this.options != null) {
/*  86 */       this.options
/*  87 */         .getLogger()
/*  88 */         .log(SentryLevel.WARNING, "Task " + callable + " rejected from " + this.executorService, new Object[0]);
/*     */     }
/*  90 */     return new CancelledFuture<>();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Future<?> schedule(@NotNull Runnable runnable, long delayMillis) throws RejectedExecutionException {
/*  96 */     return this.executorService.schedule(runnable, delayMillis, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(long timeoutMillis) {
/* 101 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 102 */     try { if (!this.executorService.isShutdown()) {
/* 103 */         this.executorService.shutdown();
/*     */         try {
/* 105 */           if (!this.executorService.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS)) {
/* 106 */             this.executorService.shutdownNow();
/*     */           }
/* 108 */         } catch (InterruptedException e) {
/* 109 */           this.executorService.shutdownNow();
/* 110 */           Thread.currentThread().interrupt();
/*     */         } 
/*     */       } 
/* 113 */       if (ignored != null) ignored.close();  }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 118 */      } public boolean isClosed() { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 119 */     try { boolean bool = this.executorService.isShutdown();
/* 120 */       if (ignored != null) ignored.close();  return bool; }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*     */      } public void prewarm() { try {
/* 127 */       this.executorService.submit(() -> {
/*     */             try {
/*     */               for (int i = 0; i < 40; i++) {
/*     */                 Future<?> future = this.executorService.schedule(this.dummyRunnable, 365L, TimeUnit.DAYS);
/*     */ 
/*     */ 
/*     */                 
/*     */                 future.cancel(true);
/*     */               } 
/*     */ 
/*     */               
/*     */               this.executorService.purge();
/* 139 */             } catch (RejectedExecutionException rejectedExecutionException) {}
/*     */ 
/*     */           
/*     */           });
/* 143 */     } catch (RejectedExecutionException e) {
/* 144 */       if (this.options != null)
/* 145 */         this.options
/* 146 */           .getLogger()
/* 147 */           .log(SentryLevel.WARNING, "Prewarm task rejected from " + this.executorService, e); 
/*     */     }  }
/*     */ 
/*     */   
/*     */   private static final class SentryExecutorServiceThreadFactory implements ThreadFactory { private int cnt;
/*     */     
/*     */     private SentryExecutorServiceThreadFactory() {}
/*     */     
/*     */     @NotNull
/*     */     public Thread newThread(@NotNull Runnable r) {
/* 157 */       Thread ret = new Thread(r, "SentryExecutorServiceThreadFactory-" + this.cnt++);
/* 158 */       ret.setDaemon(true);
/* 159 */       return ret;
/*     */     } }
/*     */   
/*     */   private static final class CancelledFuture<T> implements Future<T> {
/*     */     private CancelledFuture() {}
/*     */     
/*     */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 166 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 176 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get() {
/* 181 */       throw new CancellationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(long timeout, @NotNull TimeUnit unit) {
/* 186 */       throw new CancellationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */