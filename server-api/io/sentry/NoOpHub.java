/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.logger.ILoggerApi;
/*     */ import io.sentry.logger.NoOpLoggerApi;
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.transport.RateLimiter;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class NoOpHub
/*     */   implements IHub
/*     */ {
/*  21 */   private static final NoOpHub instance = new NoOpHub();
/*     */   @NotNull
/*  23 */   private final SentryOptions emptyOptions = SentryOptions.empty();
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static NoOpHub getInstance() {
/*  29 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  34 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  39 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  45 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  50 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  56 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  62 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  67 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  72 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  78 */     return SentryId.EMPTY_ID;
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
/* 131 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushScope() {
/* 136 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushIsolationScope() {
/* 141 */     return NoOpScopesLifecycleToken.getInstance();
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
/* 154 */     callback.run(NoOpScope.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/* 159 */     callback.run(NoOpScope.getInstance());
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
/* 170 */     return true;
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
/* 183 */     return instance;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedScopes(@NotNull String creator) {
/* 188 */     return NoOpScopes.getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedCurrentScope(@NotNull String creator) {
/* 193 */     return NoOpScopes.getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 198 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getScope() {
/* 204 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getIsolationScope() {
/* 210 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getGlobalScope() {
/* 216 */     return NoOpScope.getInstance();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IScopes getParentScopes() {
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedRootScopes(@NotNull String creator) {
/* 231 */     return NoOpScopes.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 240 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk) {
/* 245 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 252 */     return NoOpTransaction.getInstance();
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
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {}
/*     */   
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 277 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 282 */     return this.emptyOptions;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashedLastRun() {
/* 287 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportFullyDisplayed() {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 296 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryTraceHeader getTraceparent() {
/* 301 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader getBaggage() {
/* 306 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 311 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 316 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RateLimiter getRateLimiter() {
/* 321 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoOp() {
/* 326 */     return true;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ILoggerApi logger() {
/* 331 */     return (ILoggerApi)NoOpLoggerApi.getInstance();
/*     */   }
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpHub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */