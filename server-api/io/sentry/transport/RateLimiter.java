/*     */ package io.sentry.transport;
/*     */ 
/*     */ import io.sentry.DataCategory;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryEnvelopeItem;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.clientreport.DiscardReason;
/*     */ import io.sentry.hints.DiskFlushNotification;
/*     */ import io.sentry.hints.Retryable;
/*     */ import io.sentry.hints.SubmissionResult;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.StringUtils;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ public final class RateLimiter
/*     */   implements Closeable
/*     */ {
/*     */   private static final int HTTP_RETRY_AFTER_DEFAULT_DELAY_MILLIS = 60000;
/*     */   @NotNull
/*     */   private final ICurrentDateProvider currentDateProvider;
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @NotNull
/*  42 */   private final Map<DataCategory, Date> sentryRetryAfterLimit = new ConcurrentHashMap<>();
/*     */   @NotNull
/*  44 */   private final List<IRateLimitObserver> rateLimitObservers = new CopyOnWriteArrayList<>(); @Nullable
/*  45 */   private Timer timer = null; @NotNull
/*  46 */   private final AutoClosableReentrantLock timerLock = new AutoClosableReentrantLock();
/*     */ 
/*     */ 
/*     */   
/*     */   public RateLimiter(@NotNull ICurrentDateProvider currentDateProvider, @NotNull SentryOptions options) {
/*  51 */     this.currentDateProvider = currentDateProvider;
/*  52 */     this.options = options;
/*     */   }
/*     */   
/*     */   public RateLimiter(@NotNull SentryOptions options) {
/*  56 */     this(CurrentDateProvider.getInstance(), options);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryEnvelope filter(@NotNull SentryEnvelope envelope, @NotNull Hint hint) {
/*  62 */     List<SentryEnvelopeItem> dropItems = null;
/*  63 */     for (SentryEnvelopeItem item : envelope.getItems()) {
/*     */       
/*  65 */       if (isRetryAfter(item.getHeader().getType().getItemType())) {
/*  66 */         if (dropItems == null) {
/*  67 */           dropItems = new ArrayList<>();
/*     */         }
/*     */         
/*  70 */         dropItems.add(item);
/*  71 */         this.options
/*  72 */           .getClientReportRecorder()
/*  73 */           .recordLostEnvelopeItem(DiscardReason.RATELIMIT_BACKOFF, item);
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     if (dropItems != null) {
/*  78 */       this.options
/*  79 */         .getLogger()
/*  80 */         .log(SentryLevel.WARNING, "%d envelope items will be dropped due rate limiting.", new Object[] {
/*     */ 
/*     */             
/*  83 */             Integer.valueOf(dropItems.size())
/*     */           });
/*     */       
/*  86 */       List<SentryEnvelopeItem> toSend = new ArrayList<>();
/*  87 */       for (SentryEnvelopeItem item : envelope.getItems()) {
/*  88 */         if (!dropItems.contains(item)) {
/*  89 */           toSend.add(item);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  94 */       if (toSend.isEmpty()) {
/*  95 */         this.options
/*  96 */           .getLogger()
/*  97 */           .log(SentryLevel.WARNING, "Envelope discarded due all items rate limited.", new Object[0]);
/*     */         
/*  99 */         markHintWhenSendingFailed(hint, false);
/* 100 */         return null;
/*     */       } 
/*     */       
/* 103 */       return new SentryEnvelope(envelope.getHeader(), toSend);
/*     */     } 
/* 105 */     return envelope;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActiveForCategory(@NotNull DataCategory dataCategory) {
/* 110 */     Date currentDate = new Date(this.currentDateProvider.getCurrentTimeMillis());
/*     */ 
/*     */     
/* 113 */     Date dateAllCategories = this.sentryRetryAfterLimit.get(DataCategory.All);
/* 114 */     if (dateAllCategories != null && 
/* 115 */       !currentDate.after(dateAllCategories)) {
/* 116 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (DataCategory.Unknown.equals(dataCategory)) {
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 126 */     Date dateCategory = this.sentryRetryAfterLimit.get(dataCategory);
/* 127 */     if (dateCategory != null) {
/* 128 */       return !currentDate.after(dateCategory);
/*     */     }
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnyRateLimitActive() {
/* 136 */     Date currentDate = new Date(this.currentDateProvider.getCurrentTimeMillis());
/*     */     
/* 138 */     for (DataCategory dataCategory : this.sentryRetryAfterLimit.keySet()) {
/* 139 */       Date dateCategory = this.sentryRetryAfterLimit.get(dataCategory);
/* 140 */       if (dateCategory != null && 
/* 141 */         !currentDate.after(dateCategory)) {
/* 142 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void markHintWhenSendingFailed(@NotNull Hint hint, boolean retry) {
/* 157 */     HintUtils.runIfHasType(hint, SubmissionResult.class, result -> result.setResult(false));
/* 158 */     HintUtils.runIfHasType(hint, Retryable.class, retryable -> retryable.setRetry(retry));
/* 159 */     HintUtils.runIfHasType(hint, DiskFlushNotification.class, diskFlushNotification -> {
/*     */           diskFlushNotification.markFlushed();
/*     */           this.options.getLogger().log(SentryLevel.DEBUG, "Disk flush envelope fired due to rate limit", new Object[0]);
/*     */         });
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
/*     */   private boolean isRetryAfter(@NotNull String itemType) {
/* 176 */     List<DataCategory> dataCategory = getCategoryFromItemType(itemType);
/* 177 */     for (DataCategory category : dataCategory) {
/* 178 */       if (isActiveForCategory(category)) {
/* 179 */         return true;
/*     */       }
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private List<DataCategory> getCategoryFromItemType(@NotNull String itemType) {
/* 192 */     switch (itemType) {
/*     */       case "event":
/* 194 */         return Collections.singletonList(DataCategory.Error);
/*     */       case "session":
/* 196 */         return Collections.singletonList(DataCategory.Session);
/*     */       case "attachment":
/* 198 */         return Collections.singletonList(DataCategory.Attachment);
/*     */       case "profile":
/* 200 */         return Collections.singletonList(DataCategory.Profile);
/*     */ 
/*     */ 
/*     */       
/*     */       case "profile_chunk":
/* 205 */         return Arrays.asList(new DataCategory[] { DataCategory.ProfileChunkUi, DataCategory.ProfileChunk });
/*     */       case "transaction":
/* 207 */         return Collections.singletonList(DataCategory.Transaction);
/*     */       case "check_in":
/* 209 */         return Collections.singletonList(DataCategory.Monitor);
/*     */       case "replay_video":
/* 211 */         return Collections.singletonList(DataCategory.Replay);
/*     */       case "feedback":
/* 213 */         return Collections.singletonList(DataCategory.Feedback);
/*     */       case "log":
/* 215 */         return Collections.singletonList(DataCategory.LogItem);
/*     */       case "span":
/* 217 */         return Collections.singletonList(DataCategory.Span);
/*     */       case "trace_metric":
/* 219 */         return Collections.singletonList(DataCategory.TraceMetric);
/*     */     } 
/* 221 */     return Collections.singletonList(DataCategory.Unknown);
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
/*     */   public void updateRetryAfterLimits(@Nullable String sentryRateLimitHeader, @Nullable String retryAfterHeader, int errorCode) {
/* 238 */     if (sentryRateLimitHeader != null) {
/* 239 */       for (String limit : sentryRateLimitHeader.split(",", -1)) {
/*     */ 
/*     */         
/* 242 */         limit = limit.replace(" ", "");
/*     */         
/* 244 */         String[] rateLimit = limit.split(":", -1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 250 */         if (rateLimit.length > 0) {
/* 251 */           String retryAfter = rateLimit[0];
/* 252 */           long retryAfterMillis = parseRetryAfterOrDefault(retryAfter);
/*     */           
/* 254 */           if (rateLimit.length > 1) {
/* 255 */             String allCategories = rateLimit[1];
/*     */ 
/*     */ 
/*     */             
/* 259 */             Date date = new Date(this.currentDateProvider.getCurrentTimeMillis() + retryAfterMillis);
/*     */             
/* 261 */             if (allCategories != null && !allCategories.isEmpty()) {
/* 262 */               String[] categories = allCategories.split(";", -1);
/*     */               
/* 264 */               for (String catItem : categories) {
/* 265 */                 DataCategory dataCategory = DataCategory.Unknown;
/*     */                 try {
/* 267 */                   String catItemCapitalized = StringUtils.camelCase(catItem);
/* 268 */                   if (catItemCapitalized != null) {
/* 269 */                     dataCategory = DataCategory.valueOf(catItemCapitalized);
/*     */                   } else {
/* 271 */                     this.options.getLogger().log(SentryLevel.ERROR, "Couldn't capitalize: %s", new Object[] { catItem });
/*     */                   } 
/* 273 */                 } catch (IllegalArgumentException e) {
/* 274 */                   this.options.getLogger().log(SentryLevel.INFO, e, "Unknown category: %s", new Object[] { catItem });
/*     */                 } 
/*     */                 
/* 277 */                 if (!DataCategory.Unknown.equals(dataCategory))
/*     */                 {
/*     */ 
/*     */                   
/* 281 */                   applyRetryAfterOnlyIfLonger(dataCategory, date);
/*     */                 }
/*     */               } 
/*     */             } else {
/* 285 */               applyRetryAfterOnlyIfLonger(DataCategory.All, date);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 290 */     } else if (errorCode == 429) {
/* 291 */       long retryAfterMillis = parseRetryAfterOrDefault(retryAfterHeader);
/*     */       
/* 293 */       Date date = new Date(this.currentDateProvider.getCurrentTimeMillis() + retryAfterMillis);
/* 294 */       applyRetryAfterOnlyIfLonger(DataCategory.All, date);
/*     */     } 
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
/*     */   private void applyRetryAfterOnlyIfLonger(@NotNull DataCategory dataCategory, @NotNull Date date) {
/* 307 */     Date oldDate = this.sentryRetryAfterLimit.get(dataCategory);
/*     */ 
/*     */     
/* 310 */     if (oldDate == null || date.after(oldDate)) {
/* 311 */       this.sentryRetryAfterLimit.put(dataCategory, date);
/*     */       
/* 313 */       notifyRateLimitObservers();
/*     */       
/* 315 */       ISentryLifecycleToken ignored = this.timerLock.acquire(); try {
/* 316 */         if (this.timer == null) {
/* 317 */           this.timer = new Timer(true);
/*     */         }
/*     */         
/* 320 */         this.timer.schedule(new TimerTask()
/*     */             {
/*     */               public void run()
/*     */               {
/* 324 */                 RateLimiter.this.notifyRateLimitObservers();
/*     */               }
/*     */             },  date);
/*     */         
/* 328 */         if (ignored != null) ignored.close(); 
/*     */       } catch (Throwable throwable) {
/*     */         if (ignored != null)
/*     */           try {
/*     */             ignored.close();
/*     */           } catch (Throwable throwable1) {
/*     */             throwable.addSuppressed(throwable1);
/*     */           }  
/*     */         throw throwable;
/*     */       } 
/*     */     }  } private long parseRetryAfterOrDefault(@Nullable String retryAfterHeader) {
/* 339 */     long retryAfterMillis = 60000L;
/* 340 */     if (retryAfterHeader != null) {
/*     */       
/*     */       try {
/* 343 */         retryAfterMillis = (long)(Double.parseDouble(retryAfterHeader) * 1000.0D);
/* 344 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */     
/* 348 */     return retryAfterMillis;
/*     */   }
/*     */   
/*     */   private void notifyRateLimitObservers() {
/* 352 */     for (IRateLimitObserver observer : this.rateLimitObservers) {
/* 353 */       observer.onRateLimitChanged(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addRateLimitObserver(@NotNull IRateLimitObserver observer) {
/* 358 */     this.rateLimitObservers.add(observer);
/*     */   }
/*     */   
/*     */   public void removeRateLimitObserver(@NotNull IRateLimitObserver observer) {
/* 362 */     this.rateLimitObservers.remove(observer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 367 */     ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/* 368 */     try { if (this.timer != null) {
/* 369 */         this.timer.cancel();
/* 370 */         this.timer = null;
/*     */       } 
/* 372 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/* 373 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  this.rateLimitObservers.clear();
/*     */   }
/*     */   
/*     */   public static interface IRateLimitObserver {
/*     */     void onRateLimitChanged(@NotNull RateLimiter param1RateLimiter);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\RateLimiter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */