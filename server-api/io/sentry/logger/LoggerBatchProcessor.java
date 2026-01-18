/*     */ package io.sentry.logger;
/*     */ import io.sentry.DataCategory;
/*     */ import io.sentry.ISentryClient;
/*     */ import io.sentry.ISentryExecutorService;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.SentryExecutorService;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryLogEvent;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.clientreport.DiscardReason;
/*     */ import io.sentry.transport.ReusableCountLatch;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class LoggerBatchProcessor implements ILoggerBatchProcessor {
/*     */   public static final int FLUSH_AFTER_MS = 5000;
/*     */   public static final int MAX_BATCH_SIZE = 100;
/*     */   public static final int MAX_QUEUE_SIZE = 1000;
/*     */   @NotNull
/*     */   protected final SentryOptions options;
/*     */   @NotNull
/*     */   private final ISentryClient client;
/*     */   @NotNull
/*     */   private final Queue<SentryLogEvent> queue;
/*     */   @NotNull
/*     */   private final ISentryExecutorService executorService;
/*     */   @Nullable
/*     */   private volatile Future<?> scheduledFlush;
/*     */   @NotNull
/*  39 */   private static final AutoClosableReentrantLock scheduleLock = new AutoClosableReentrantLock();
/*     */   
/*     */   private volatile boolean hasScheduled = false;
/*     */   @NotNull
/*  43 */   private final ReusableCountLatch pendingCount = new ReusableCountLatch();
/*     */ 
/*     */   
/*     */   public LoggerBatchProcessor(@NotNull SentryOptions options, @NotNull ISentryClient client) {
/*  47 */     this.options = options;
/*  48 */     this.client = client;
/*  49 */     this.queue = new ConcurrentLinkedQueue<>();
/*  50 */     this.executorService = (ISentryExecutorService)new SentryExecutorService(options);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(@NotNull SentryLogEvent logEvent) {
/*  55 */     if (this.pendingCount.getCount() >= 1000) {
/*  56 */       this.options
/*  57 */         .getClientReportRecorder()
/*  58 */         .recordLostEvent(DiscardReason.QUEUE_OVERFLOW, DataCategory.LogItem);
/*     */       
/*  60 */       long lostBytes = JsonSerializationUtils.byteSizeOf(this.options.getSerializer(), this.options.getLogger(), (JsonSerializable)logEvent);
/*  61 */       this.options
/*  62 */         .getClientReportRecorder()
/*  63 */         .recordLostEvent(DiscardReason.QUEUE_OVERFLOW, DataCategory.Attachment, lostBytes);
/*     */       return;
/*     */     } 
/*  66 */     this.pendingCount.increment();
/*  67 */     this.queue.offer(logEvent);
/*  68 */     maybeSchedule(false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) {
/*  74 */     if (isRestarting) {
/*  75 */       maybeSchedule(true, true);
/*  76 */       this.executorService.submit(() -> this.executorService.close(this.options.getShutdownTimeoutMillis()));
/*     */     } else {
/*  78 */       this.executorService.close(this.options.getShutdownTimeoutMillis());
/*  79 */       while (!this.queue.isEmpty()) {
/*  80 */         flushBatch();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void maybeSchedule(boolean forceSchedule, boolean immediately) {
/*  86 */     if (this.hasScheduled && !forceSchedule) {
/*     */       return;
/*     */     }
/*  89 */     ISentryLifecycleToken ignored = scheduleLock.acquire(); 
/*  90 */     try { Future<?> latestScheduledFlush = this.scheduledFlush;
/*  91 */       if (forceSchedule || latestScheduledFlush == null || latestScheduledFlush
/*     */         
/*  93 */         .isDone() || latestScheduledFlush
/*  94 */         .isCancelled()) {
/*  95 */         this.hasScheduled = true;
/*  96 */         int flushAfterMs = immediately ? 0 : 5000;
/*     */         try {
/*  98 */           this.scheduledFlush = this.executorService.schedule(new BatchRunnable(), flushAfterMs);
/*  99 */         } catch (RejectedExecutionException e) {
/* 100 */           this.hasScheduled = false;
/* 101 */           this.options
/* 102 */             .getLogger()
/* 103 */             .log(SentryLevel.WARNING, "Logs batch processor flush task rejected", e);
/*     */         } 
/*     */       } 
/* 106 */       if (ignored != null) ignored.close();  }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 111 */      } public void flush(long timeoutMillis) { maybeSchedule(true, true);
/*     */     try {
/* 113 */       this.pendingCount.waitTillZero(timeoutMillis, TimeUnit.MILLISECONDS);
/* 114 */     } catch (InterruptedException e) {
/* 115 */       this.options.getLogger().log(SentryLevel.ERROR, "Failed to flush log events", e);
/* 116 */       Thread.currentThread().interrupt();
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void flush() {
/* 121 */     flushInternal();
/* 122 */     ISentryLifecycleToken ignored = scheduleLock.acquire(); 
/* 123 */     try { if (!this.queue.isEmpty()) {
/* 124 */         maybeSchedule(true, false);
/*     */       } else {
/* 126 */         this.hasScheduled = false;
/*     */       } 
/* 128 */       if (ignored != null) ignored.close();  }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 133 */      } private void flushInternal() { do { flushBatch(); }
/* 134 */     while (this.queue.size() >= 100); }
/*     */ 
/*     */   
/*     */   private void flushBatch() {
/* 138 */     List<SentryLogEvent> logEvents = new ArrayList<>(100);
/*     */     do {
/* 140 */       SentryLogEvent logEvent = this.queue.poll();
/* 141 */       if (logEvent == null)
/* 142 */         continue;  logEvents.add(logEvent);
/*     */     }
/* 144 */     while (!this.queue.isEmpty() && logEvents.size() < 100);
/*     */     
/* 146 */     if (!logEvents.isEmpty()) {
/* 147 */       this.client.captureBatchedLogEvents(new SentryLogEvents(logEvents));
/* 148 */       for (int i = 0; i < logEvents.size(); i++)
/* 149 */         this.pendingCount.decrement(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class BatchRunnable
/*     */     implements Runnable {
/*     */     private BatchRunnable() {}
/*     */     
/*     */     public void run() {
/* 158 */       LoggerBatchProcessor.this.flush();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\LoggerBatchProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */