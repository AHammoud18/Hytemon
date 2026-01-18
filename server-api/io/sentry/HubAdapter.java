/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.logger.ILoggerApi;
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.transport.RateLimiter;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class HubAdapter
/*     */   implements IHub
/*     */ {
/*  20 */   private static final HubAdapter INSTANCE = new HubAdapter();
/*     */ 
/*     */ 
/*     */   
/*     */   public static HubAdapter getInstance() {
/*  25 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  30 */     return Sentry.isEnabled();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  35 */     return Sentry.captureEvent(event, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  41 */     return Sentry.captureEvent(event, hint, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  46 */     return Sentry.captureMessage(message, level);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  52 */     return Sentry.captureMessage(message, level, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback) {
/*  57 */     return Sentry.captureFeedback(feedback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint) {
/*  62 */     return Sentry.captureFeedback(feedback, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  68 */     return Sentry.captureFeedback(feedback, hint, callback);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  74 */     return Sentry.getCurrentScopes().captureEnvelope(envelope, hint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  79 */     return Sentry.captureException(throwable, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  85 */     return Sentry.captureException(throwable, hint, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  90 */     Sentry.captureUserFeedback(userFeedback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startSession() {
/*  95 */     Sentry.startSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endSession() {
/* 100 */     Sentry.endSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) {
/* 105 */     Sentry.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 110 */     Sentry.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/* 115 */     Sentry.addBreadcrumb(breadcrumb, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/* 120 */     addBreadcrumb(breadcrumb, new Hint());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 125 */     Sentry.setLevel(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {
/* 130 */     Sentry.setTransaction(transaction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 135 */     Sentry.setUser(user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {
/* 140 */     Sentry.setFingerprint(fingerprint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {
/* 145 */     Sentry.clearBreadcrumbs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 150 */     Sentry.setTag(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {
/* 155 */     Sentry.removeTag(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {
/* 160 */     Sentry.setExtra(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {
/* 165 */     Sentry.removeExtra(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 170 */     return Sentry.getLastEventId();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushScope() {
/* 175 */     return Sentry.pushScope();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushIsolationScope() {
/* 180 */     return Sentry.pushIsolationScope();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void popScope() {
/* 190 */     Sentry.popScope();
/*     */   }
/*     */ 
/*     */   
/*     */   public void withScope(@NotNull ScopeCallback callback) {
/* 195 */     Sentry.withScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/* 200 */     Sentry.withIsolationScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {
/* 205 */     Sentry.configureScope(scopeType, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {
/* 210 */     Sentry.bindClient(client);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 215 */     return Sentry.isHealthy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(long timeoutMillis) {
/* 220 */     Sentry.flush(timeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public IHub clone() {
/* 230 */     return Sentry.getCurrentScopes().clone();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedScopes(@NotNull String creator) {
/* 235 */     return Sentry.forkedScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedCurrentScope(@NotNull String creator) {
/* 240 */     return Sentry.forkedCurrentScope(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedRootScopes(@NotNull String creator) {
/* 245 */     return Sentry.forkedRootScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 250 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getScope() {
/* 256 */     return Sentry.getCurrentScopes().getScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getIsolationScope() {
/* 262 */     return Sentry.getCurrentScopes().getIsolationScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getGlobalScope() {
/* 268 */     return Sentry.getGlobalScope();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IScopes getParentScopes() {
/* 273 */     return Sentry.getCurrentScopes().getParentScopes();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/* 278 */     return Sentry.getCurrentScopes().isAncestorOf(otherScopes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 287 */     return Sentry.getCurrentScopes()
/* 288 */       .captureTransaction(transaction, traceContext, hint, profilingTraceData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 295 */     return Sentry.startTransaction(transactionContext, transactionOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startProfiler() {
/* 300 */     Sentry.startProfiler();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopProfiler() {
/* 305 */     Sentry.stopProfiler();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureProfileChunk(@NotNull ProfileChunk profilingContinuousData) {
/* 311 */     return Sentry.getCurrentScopes().captureProfileChunk(profilingContinuousData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 319 */     Sentry.getCurrentScopes().setSpanContext(throwable, span, transactionName);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/* 324 */     return Sentry.getCurrentScopes().getSpan();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {
/* 329 */     Sentry.getCurrentScopes().setActiveSpan(span);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 335 */     return Sentry.getCurrentScopes().getTransaction();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 340 */     return Sentry.getCurrentScopes().getOptions();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashedLastRun() {
/* 345 */     return Sentry.isCrashedLastRun();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportFullyDisplayed() {
/* 350 */     Sentry.reportFullyDisplayed();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 356 */     return Sentry.continueTrace(sentryTrace, baggageHeaders);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryTraceHeader getTraceparent() {
/* 361 */     return Sentry.getTraceparent();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader getBaggage() {
/* 366 */     return Sentry.getBaggage();
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 372 */     return Sentry.captureCheckIn(checkIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 378 */     return Sentry.getCurrentScopes().captureReplay(replay, hint);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public RateLimiter getRateLimiter() {
/* 384 */     return Sentry.getCurrentScopes().getRateLimiter();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ILoggerApi logger() {
/* 389 */     return Sentry.getCurrentScopes().logger();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 394 */     Sentry.addFeatureFlag(flag, result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\HubAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */