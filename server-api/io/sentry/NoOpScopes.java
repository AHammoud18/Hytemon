/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.logger.ILoggerApi;
/*     */ import io.sentry.logger.NoOpLoggerApi;
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.transport.RateLimiter;
/*     */ import io.sentry.util.LazyEvaluator;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class NoOpScopes implements IScopes {
/*  18 */   private static final NoOpScopes instance = new NoOpScopes();
/*     */   @NotNull
/*  20 */   private final LazyEvaluator<SentryOptions> emptyOptions = new LazyEvaluator(() -> SentryOptions.empty());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoOpScopes getInstance() {
/*  26 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  36 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  42 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  47 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  53 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  59 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  64 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  69 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  75 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {}
/*     */ 
/*     */   
/*     */   public void startSession() {}
/*     */ 
/*     */   
/*     */   public void endSession() {}
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) {}
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {}
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {}
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {}
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {}
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {}
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {}
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {}
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {}
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {}
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {}
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {}
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 128 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushScope() {
/* 133 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushIsolationScope() {
/* 138 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void popScope() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void withScope(@NotNull ScopeCallback callback) {
/* 151 */     callback.run(NoOpScope.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/* 156 */     callback.run(NoOpScope.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {}
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {}
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush(long timeoutMillis) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public IHub clone() {
/* 180 */     return NoOpHub.getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedScopes(@NotNull String creator) {
/* 185 */     return getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedCurrentScope(@NotNull String creator) {
/* 190 */     return getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedRootScopes(@NotNull String creator) {
/* 195 */     return getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 200 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getScope() {
/* 206 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getIsolationScope() {
/* 212 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getGlobalScope() {
/* 218 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IScopes getParentScopes() {
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 237 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk) {
/* 242 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 249 */     return NoOpTransaction.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startProfiler() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopProfiler() {}
/*     */ 
/*     */   
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan spanContext, @NotNull String transactionName) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {}
/*     */   
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 274 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 279 */     return (SentryOptions)this.emptyOptions.getValue();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashedLastRun() {
/* 284 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportFullyDisplayed() {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 293 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryTraceHeader getTraceparent() {
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader getBaggage() {
/* 303 */     return null;
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 309 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RateLimiter getRateLimiter() {
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoOp() {
/* 319 */     return true;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 324 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ILoggerApi logger() {
/* 329 */     return (ILoggerApi)NoOpLoggerApi.getInstance();
/*     */   }
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpScopes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */