/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.hints.Cached;
/*     */ import io.sentry.hints.Enqueable;
/*     */ import io.sentry.hints.Flushable;
/*     */ import io.sentry.hints.Retryable;
/*     */ import io.sentry.hints.SubmissionResult;
/*     */ import io.sentry.transport.RateLimiter;
/*     */ import io.sentry.util.HintUtils;
/*     */ import java.io.File;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DirectoryProcessor
/*     */ {
/*     */   private static final long ENVELOPE_PROCESSING_DELAY = 100L;
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   @NotNull
/*     */   private final ILogger logger;
/*     */   private final long flushTimeoutMillis;
/*     */   private final Queue<String> processedEnvelopes;
/*     */   
/*     */   DirectoryProcessor(@NotNull IScopes scopes, @NotNull ILogger logger, long flushTimeoutMillis, int maxQueueSize) {
/*  32 */     this.scopes = scopes;
/*  33 */     this.logger = logger;
/*  34 */     this.flushTimeoutMillis = flushTimeoutMillis;
/*  35 */     this
/*  36 */       .processedEnvelopes = SynchronizedQueue.synchronizedQueue(new CircularFifoQueue<>(maxQueueSize));
/*     */   }
/*     */   
/*     */   public void processDirectory(@NotNull File directory) {
/*     */     try {
/*  41 */       this.logger.log(SentryLevel.DEBUG, "Processing dir. %s", new Object[] { directory.getAbsolutePath() });
/*     */       
/*  43 */       File[] filteredListFiles = directory.listFiles((d, name) -> isRelevantFileName(name));
/*  44 */       if (filteredListFiles == null) {
/*  45 */         this.logger.log(SentryLevel.ERROR, "Cache dir %s is null or is not a directory.", new Object[] { directory
/*     */ 
/*     */               
/*  48 */               .getAbsolutePath() });
/*     */         
/*     */         return;
/*     */       } 
/*  52 */       this.logger.log(SentryLevel.DEBUG, "Processing %d items from cache dir %s", new Object[] {
/*     */ 
/*     */             
/*  55 */             Integer.valueOf(filteredListFiles.length), directory
/*  56 */             .getAbsolutePath()
/*     */           });
/*  58 */       for (File file : filteredListFiles) {
/*     */         
/*  60 */         if (!file.isFile())
/*  61 */         { this.logger.log(SentryLevel.DEBUG, "File %s is not a File.", new Object[] { file.getAbsolutePath() }); }
/*     */         
/*     */         else
/*     */         
/*  65 */         { String filePath = file.getAbsolutePath();
/*     */ 
/*     */           
/*  68 */           if (this.processedEnvelopes.contains(filePath))
/*  69 */           { this.logger.log(SentryLevel.DEBUG, "File '%s' has already been processed so it will not be processed again.", new Object[] { filePath });
/*     */             
/*     */              }
/*     */           
/*     */           else
/*     */           
/*     */           { 
/*     */             
/*  77 */             RateLimiter rateLimiter = this.scopes.getRateLimiter();
/*  78 */             if (rateLimiter != null && rateLimiter.isActiveForCategory(DataCategory.All)) {
/*  79 */               this.logger.log(SentryLevel.INFO, "DirectoryProcessor, rate limiting active.", new Object[0]);
/*     */               
/*     */               return;
/*     */             } 
/*  83 */             this.logger.log(SentryLevel.DEBUG, "Processing file: %s", new Object[] { filePath });
/*     */             
/*  85 */             SendCachedEnvelopeHint cachedHint = new SendCachedEnvelopeHint(this.flushTimeoutMillis, this.logger, filePath, this.processedEnvelopes);
/*     */ 
/*     */             
/*  88 */             Hint hint = HintUtils.createWithTypeCheckHint(cachedHint);
/*  89 */             processFile(file, hint);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  94 */             Thread.sleep(100L); }  } 
/*     */       } 
/*  96 */     } catch (Throwable e) {
/*  97 */       this.logger.log(SentryLevel.ERROR, e, "Failed processing '%s'", new Object[] { directory.getAbsolutePath() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void processFile(@NotNull File paramFile, @NotNull Hint paramHint);
/*     */   
/*     */   protected abstract boolean isRelevantFileName(String paramString);
/*     */   
/*     */   private static final class SendCachedEnvelopeHint
/*     */     implements Cached, Retryable, SubmissionResult, Flushable, Enqueable
/*     */   {
/*     */     boolean retry = false;
/*     */     boolean succeeded = false;
/*     */     private final CountDownLatch latch;
/*     */     private final long flushTimeoutMillis;
/*     */     @NotNull
/*     */     private final ILogger logger;
/*     */     @NotNull
/*     */     private final String filePath;
/*     */     @NotNull
/*     */     private final Queue<String> processedEnvelopes;
/*     */     
/*     */     public SendCachedEnvelopeHint(long flushTimeoutMillis, @NotNull ILogger logger, @NotNull String filePath, @NotNull Queue<String> processedEnvelopes) {
/* 121 */       this.flushTimeoutMillis = flushTimeoutMillis;
/* 122 */       this.filePath = filePath;
/* 123 */       this.processedEnvelopes = processedEnvelopes;
/* 124 */       this.latch = new CountDownLatch(1);
/* 125 */       this.logger = logger;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRetry() {
/* 130 */       return this.retry;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setRetry(boolean retry) {
/* 135 */       this.retry = retry;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean waitFlush() {
/*     */       try {
/* 141 */         return this.latch.await(this.flushTimeoutMillis, TimeUnit.MILLISECONDS);
/* 142 */       } catch (InterruptedException e) {
/* 143 */         Thread.currentThread().interrupt();
/* 144 */         this.logger.log(SentryLevel.ERROR, "Exception while awaiting on lock.", e);
/*     */         
/* 146 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setResult(boolean succeeded) {
/* 151 */       this.succeeded = succeeded;
/* 152 */       this.latch.countDown();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSuccess() {
/* 157 */       return this.succeeded;
/*     */     }
/*     */ 
/*     */     
/*     */     public void markEnqueued() {
/* 162 */       this.processedEnvelopes.add(this.filePath);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DirectoryProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */