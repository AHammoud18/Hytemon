/*     */ package io.sentry.transport;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.RequestDetails;
/*     */ import io.sentry.SentryDate;
/*     */ import io.sentry.SentryDateProvider;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.UncaughtExceptionHandlerIntegration;
/*     */ import io.sentry.cache.IEnvelopeCache;
/*     */ import io.sentry.clientreport.DiscardReason;
/*     */ import io.sentry.hints.Cached;
/*     */ import io.sentry.hints.DiskFlushNotification;
/*     */ import io.sentry.hints.Enqueable;
/*     */ import io.sentry.hints.Retryable;
/*     */ import io.sentry.hints.SubmissionResult;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.LogUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionHandler;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ public final class AsyncHttpTransport
/*     */   implements ITransport
/*     */ {
/*     */   @NotNull
/*     */   private final QueuedThreadPoolExecutor executor;
/*     */   @NotNull
/*     */   private final IEnvelopeCache envelopeCache;
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @Nullable
/*  43 */   private volatile Runnable currentRunnable = null;
/*     */   
/*     */   @NotNull
/*     */   private final RateLimiter rateLimiter;
/*     */ 
/*     */   
/*     */   public AsyncHttpTransport(@NotNull SentryOptions options, @NotNull RateLimiter rateLimiter, @NotNull ITransportGate transportGate, @NotNull RequestDetails requestDetails) {
/*  50 */     this(
/*  51 */         initExecutor(options
/*  52 */           .getMaxQueueSize(), options
/*  53 */           .getEnvelopeDiskCache(), options
/*  54 */           .getLogger(), options
/*  55 */           .getDateProvider()), options, rateLimiter, transportGate, new HttpConnection(options, requestDetails, rateLimiter));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final ITransportGate transportGate;
/*     */   
/*     */   @NotNull
/*     */   private final HttpConnection connection;
/*     */ 
/*     */   
/*     */   public AsyncHttpTransport(@NotNull QueuedThreadPoolExecutor executor, @NotNull SentryOptions options, @NotNull RateLimiter rateLimiter, @NotNull ITransportGate transportGate, @NotNull HttpConnection httpConnection) {
/*  68 */     this.executor = (QueuedThreadPoolExecutor)Objects.requireNonNull(executor, "executor is required");
/*  69 */     this
/*  70 */       .envelopeCache = (IEnvelopeCache)Objects.requireNonNull(options.getEnvelopeDiskCache(), "envelopeCache is required");
/*  71 */     this.options = (SentryOptions)Objects.requireNonNull(options, "options is required");
/*  72 */     this.rateLimiter = (RateLimiter)Objects.requireNonNull(rateLimiter, "rateLimiter is required");
/*  73 */     this.transportGate = (ITransportGate)Objects.requireNonNull(transportGate, "transportGate is required");
/*  74 */     this.connection = (HttpConnection)Objects.requireNonNull(httpConnection, "httpConnection is required");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(@NotNull SentryEnvelope envelope, @NotNull Hint hint) throws IOException {
/*  81 */     IEnvelopeCache currentEnvelopeCache = this.envelopeCache;
/*  82 */     boolean cached = false;
/*  83 */     if (HintUtils.hasType(hint, Cached.class)) {
/*  84 */       currentEnvelopeCache = NoOpEnvelopeCache.getInstance();
/*  85 */       cached = true;
/*  86 */       this.options.getLogger().log(SentryLevel.DEBUG, "Captured Envelope is already cached", new Object[0]);
/*     */     } 
/*     */     
/*  89 */     SentryEnvelope filteredEnvelope = this.rateLimiter.filter(envelope, hint);
/*     */     
/*  91 */     if (filteredEnvelope == null) {
/*  92 */       if (cached) {
/*  93 */         this.envelopeCache.discard(envelope);
/*     */       }
/*     */     } else {
/*     */       SentryEnvelope envelopeThatMayIncludeClientReport;
/*  97 */       if (HintUtils.hasType(hint, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class)) {
/*     */ 
/*     */         
/* 100 */         envelopeThatMayIncludeClientReport = this.options.getClientReportRecorder().attachReportToEnvelope(filteredEnvelope);
/*     */       } else {
/* 102 */         envelopeThatMayIncludeClientReport = filteredEnvelope;
/*     */       } 
/*     */ 
/*     */       
/* 106 */       Future<?> future = this.executor.submit(new EnvelopeSender(envelopeThatMayIncludeClientReport, hint, currentEnvelopeCache));
/*     */ 
/*     */       
/* 109 */       if (future != null && future.isCancelled()) {
/* 110 */         this.options
/* 111 */           .getClientReportRecorder()
/* 112 */           .recordLostEnvelope(DiscardReason.QUEUE_OVERFLOW, envelopeThatMayIncludeClientReport);
/*     */       } else {
/* 114 */         HintUtils.runIfHasType(hint, Enqueable.class, enqueable -> {
/*     */               enqueable.markEnqueued();
/*     */               this.options.getLogger().log(SentryLevel.DEBUG, "Envelope enqueued", new Object[0]);
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush(long timeoutMillis) {
/* 127 */     this.executor.waitTillIdle(timeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static QueuedThreadPoolExecutor initExecutor(int maxQueueSize, @NotNull IEnvelopeCache envelopeCache, @NotNull ILogger logger, @NotNull SentryDateProvider dateProvider) {
/* 136 */     RejectedExecutionHandler storeEvents = (r, executor) -> {
/*     */         if (r instanceof EnvelopeSender) {
/*     */           EnvelopeSender envelopeSender = (EnvelopeSender)r;
/*     */           
/*     */           if (!HintUtils.hasType(envelopeSender.hint, Cached.class)) {
/*     */             envelopeCache.storeEnvelope(envelopeSender.envelope, envelopeSender.hint);
/*     */           }
/*     */           
/*     */           markHintWhenSendingFailed(envelopeSender.hint, true);
/*     */           
/*     */           logger.log(SentryLevel.WARNING, "Envelope rejected", new Object[0]);
/*     */         } 
/*     */       };
/*     */     
/* 150 */     return new QueuedThreadPoolExecutor(1, maxQueueSize, new AsyncConnectionThreadFactory(), storeEvents, logger, dateProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public RateLimiter getRateLimiter() {
/* 156 */     return this.rateLimiter;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 161 */     boolean anyRateLimitActive = this.rateLimiter.isAnyRateLimitActive();
/* 162 */     boolean didRejectRecently = this.executor.didRejectRecently();
/* 163 */     return (!anyRateLimitActive && !didRejectRecently);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 168 */     close(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) throws IOException {
/* 173 */     this.rateLimiter.close();
/* 174 */     this.executor.shutdown();
/* 175 */     this.options.getLogger().log(SentryLevel.DEBUG, "Shutting down", new Object[0]);
/*     */     
/*     */     try {
/* 178 */       if (!isRestarting) {
/*     */         
/* 180 */         long timeout = this.options.getFlushTimeoutMillis();
/* 181 */         if (!this.executor.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
/* 182 */           this.options
/* 183 */             .getLogger()
/* 184 */             .log(SentryLevel.WARNING, "Failed to shutdown the async connection async sender  within " + timeout + " ms. Trying to force it now.", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 189 */           this.executor.shutdownNow();
/* 190 */           if (this.currentRunnable != null)
/*     */           {
/* 192 */             this.executor.getRejectedExecutionHandler().rejectedExecution(this.currentRunnable, this.executor);
/*     */           }
/*     */         } 
/*     */       } 
/* 196 */     } catch (InterruptedException e) {
/*     */       
/* 198 */       this.options
/* 199 */         .getLogger()
/* 200 */         .log(SentryLevel.DEBUG, "Thread interrupted while closing the connection.", new Object[0]);
/* 201 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void markHintWhenSendingFailed(@NotNull Hint hint, boolean retry) {
/* 212 */     HintUtils.runIfHasType(hint, SubmissionResult.class, result -> result.setResult(false));
/* 213 */     HintUtils.runIfHasType(hint, Retryable.class, retryable -> retryable.setRetry(retry));
/*     */   }
/*     */   
/*     */   private static final class AsyncConnectionThreadFactory implements ThreadFactory {
/*     */     private int cnt;
/*     */     
/*     */     @NotNull
/*     */     public Thread newThread(@NotNull Runnable r) {
/* 221 */       Thread ret = new Thread(r, "SentryAsyncConnection-" + this.cnt++);
/* 222 */       ret.setDaemon(true);
/* 223 */       return ret;
/*     */     }
/*     */     
/*     */     private AsyncConnectionThreadFactory() {} }
/*     */   
/*     */   private final class EnvelopeSender implements Runnable {
/*     */     @NotNull
/*     */     private final SentryEnvelope envelope;
/* 231 */     private final TransportResult failedResult = TransportResult.error();
/*     */     
/*     */     @NotNull
/*     */     private final Hint hint;
/*     */     
/*     */     EnvelopeSender(@NotNull SentryEnvelope envelope, @NotNull Hint hint, IEnvelopeCache envelopeCache) {
/* 237 */       this.envelope = (SentryEnvelope)Objects.requireNonNull(envelope, "Envelope is required.");
/* 238 */       this.hint = hint;
/* 239 */       this.envelopeCache = (IEnvelopeCache)Objects.requireNonNull(envelopeCache, "EnvelopeCache is required.");
/*     */     }
/*     */     @NotNull
/*     */     private final IEnvelopeCache envelopeCache;
/*     */     public void run() {
/* 244 */       AsyncHttpTransport.this.currentRunnable = this;
/* 245 */       TransportResult result = this.failedResult; try {
/*     */         TransportResult finalResult;
/* 247 */         result = flush();
/* 248 */         AsyncHttpTransport.this.options.getLogger().log(SentryLevel.DEBUG, "Envelope flushed", new Object[0]);
/* 249 */       } catch (Throwable e) {
/* 250 */         AsyncHttpTransport.this.options.getLogger().log(SentryLevel.ERROR, e, "Envelope submission failed", new Object[0]);
/* 251 */         throw e;
/*     */       } finally {
/* 253 */         TransportResult finalResult = result;
/* 254 */         HintUtils.runIfHasType(this.hint, SubmissionResult.class, submissionResult -> {
/*     */               AsyncHttpTransport.this.options.getLogger().log(SentryLevel.DEBUG, "Marking envelope submission result: %s", new Object[] { Boolean.valueOf(finalResult.isSuccess()) });
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               submissionResult.setResult(finalResult.isSuccess());
/*     */             });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 266 */         AsyncHttpTransport.this.currentRunnable = null;
/*     */       } 
/*     */     }
/*     */     @NotNull
/*     */     private TransportResult flush() {
/* 271 */       TransportResult result = this.failedResult;
/*     */       
/* 273 */       this.envelope.getHeader().setSentAt(null);
/* 274 */       boolean cached = this.envelopeCache.storeEnvelope(this.envelope, this.hint);
/*     */       
/* 276 */       HintUtils.runIfHasType(this.hint, DiskFlushNotification.class, diskFlushNotification -> {
/*     */             if (diskFlushNotification.isFlushable(this.envelope.getHeader().getEventId())) {
/*     */               diskFlushNotification.markFlushed();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               AsyncHttpTransport.this.options.getLogger().log(SentryLevel.DEBUG, "Disk flush envelope fired", new Object[0]);
/*     */             } else {
/*     */               AsyncHttpTransport.this.options.getLogger().log(SentryLevel.DEBUG, "Not firing envelope flush as there's an ongoing transaction", new Object[0]);
/*     */             } 
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (AsyncHttpTransport.this.transportGate.isConnected()) {
/*     */         
/* 294 */         SentryEnvelope envelopeWithClientReport = AsyncHttpTransport.this.options.getClientReportRecorder().attachReportToEnvelope(this.envelope);
/*     */         
/*     */         try {
/* 297 */           SentryDate now = AsyncHttpTransport.this.options.getDateProvider().now();
/* 298 */           envelopeWithClientReport
/* 299 */             .getHeader()
/* 300 */             .setSentAt(DateUtils.nanosToDate(now.nanoTimestamp()));
/*     */           
/* 302 */           result = AsyncHttpTransport.this.connection.send(envelopeWithClientReport);
/* 303 */           if (result.isSuccess()) {
/* 304 */             this.envelopeCache.discard(this.envelope);
/*     */           }
/*     */           else {
/*     */             
/* 308 */             String message = "The transport failed to send the envelope with response code " + result.getResponseCode();
/*     */             
/* 310 */             AsyncHttpTransport.this.options.getLogger().log(SentryLevel.ERROR, message, new Object[0]);
/*     */ 
/*     */             
/* 313 */             if (result.getResponseCode() >= 400 && result.getResponseCode() != 429 && 
/* 314 */               !cached) {
/* 315 */               HintUtils.runIfDoesNotHaveType(this.hint, Retryable.class, hint -> AsyncHttpTransport.this.options.getClientReportRecorder().recordLostEnvelope(DiscardReason.NETWORK_ERROR, envelopeWithClientReport));
/*     */             }
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
/* 327 */             throw new IllegalStateException(message);
/*     */           } 
/* 329 */         } catch (IOException e) {
/*     */           
/* 331 */           HintUtils.runIfHasType(this.hint, Retryable.class, retryable -> retryable.setRetry(true), (hint, clazz) -> {
/*     */                 if (!cached) {
/*     */                   LogUtils.logNotInstanceOf(clazz, hint, AsyncHttpTransport.this.options.getLogger());
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   AsyncHttpTransport.this.options.getClientReportRecorder().recordLostEnvelope(DiscardReason.NETWORK_ERROR, envelopeWithClientReport);
/*     */                 } 
/*     */               });
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 345 */           throw new IllegalStateException("Sending the event failed.", e);
/*     */         } 
/*     */       } else {
/*     */         
/* 349 */         HintUtils.runIfHasType(this.hint, Retryable.class, retryable -> retryable.setRetry(true), (hint, clazz) -> {
/*     */               if (!cached) {
/*     */                 LogUtils.logNotInstanceOf(clazz, hint, AsyncHttpTransport.this.options.getLogger());
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 AsyncHttpTransport.this.options.getClientReportRecorder().recordLostEnvelope(DiscardReason.NETWORK_ERROR, this.envelope);
/*     */               } 
/*     */             });
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 364 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\AsyncHttpTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */