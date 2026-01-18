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
/*     */ public final class ScopesAdapter implements IScopes {
/*  16 */   private static final ScopesAdapter INSTANCE = new ScopesAdapter();
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScopesAdapter getInstance() {
/*  21 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  26 */     return Sentry.isEnabled();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  31 */     return Sentry.captureEvent(event, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  37 */     return Sentry.captureEvent(event, hint, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  42 */     return Sentry.captureMessage(message, level);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  48 */     return Sentry.captureMessage(message, level, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback) {
/*  53 */     return Sentry.captureFeedback(feedback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint) {
/*  58 */     return Sentry.captureFeedback(feedback, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  64 */     return Sentry.captureFeedback(feedback, hint, callback);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  70 */     return Sentry.getCurrentScopes().captureEnvelope(envelope, hint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  75 */     return Sentry.captureException(throwable, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  81 */     return Sentry.captureException(throwable, hint, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  86 */     Sentry.captureUserFeedback(userFeedback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startSession() {
/*  91 */     Sentry.startSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endSession() {
/*  96 */     Sentry.endSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) {
/* 101 */     Sentry.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 106 */     Sentry.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/* 111 */     Sentry.addBreadcrumb(breadcrumb, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/* 116 */     addBreadcrumb(breadcrumb, new Hint());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 121 */     Sentry.setLevel(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {
/* 126 */     Sentry.setTransaction(transaction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 131 */     Sentry.setUser(user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {
/* 136 */     Sentry.setFingerprint(fingerprint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {
/* 141 */     Sentry.clearBreadcrumbs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 146 */     Sentry.setTag(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {
/* 151 */     Sentry.removeTag(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {
/* 156 */     Sentry.setExtra(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {
/* 161 */     Sentry.removeExtra(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 166 */     return Sentry.getLastEventId();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushScope() {
/* 171 */     return Sentry.pushScope();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushIsolationScope() {
/* 176 */     return Sentry.pushIsolationScope();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void popScope() {
/* 186 */     Sentry.popScope();
/*     */   }
/*     */ 
/*     */   
/*     */   public void withScope(@NotNull ScopeCallback callback) {
/* 191 */     Sentry.withScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/* 196 */     Sentry.withIsolationScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {
/* 201 */     Sentry.configureScope(scopeType, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {
/* 206 */     Sentry.bindClient(client);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 211 */     return Sentry.isHealthy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(long timeoutMillis) {
/* 216 */     Sentry.flush(timeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public IHub clone() {
/* 227 */     return Sentry.getCurrentScopes().clone();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedScopes(@NotNull String creator) {
/* 232 */     return Sentry.forkedScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedCurrentScope(@NotNull String creator) {
/* 237 */     return Sentry.forkedCurrentScope(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedRootScopes(@NotNull String creator) {
/* 242 */     return Sentry.forkedRootScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 247 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getScope() {
/* 253 */     return Sentry.getCurrentScopes().getScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getIsolationScope() {
/* 259 */     return Sentry.getCurrentScopes().getIsolationScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getGlobalScope() {
/* 265 */     return Sentry.getGlobalScope();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IScopes getParentScopes() {
/* 270 */     return Sentry.getCurrentScopes().getParentScopes();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/* 275 */     return Sentry.getCurrentScopes().isAncestorOf(otherScopes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 285 */     return Sentry.getCurrentScopes()
/* 286 */       .captureTransaction(transaction, traceContext, hint, profilingTraceData);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk) {
/* 291 */     return Sentry.getCurrentScopes().captureProfileChunk(profileChunk);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 298 */     return Sentry.startTransaction(transactionContext, transactionOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startProfiler() {
/* 303 */     Sentry.startProfiler();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopProfiler() {
/* 308 */     Sentry.stopProfiler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 317 */     Sentry.getCurrentScopes().setSpanContext(throwable, span, transactionName);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/* 322 */     return Sentry.getCurrentScopes().getSpan();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {
/* 327 */     Sentry.getCurrentScopes().setActiveSpan(span);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 333 */     return Sentry.getCurrentScopes().getTransaction();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 338 */     return Sentry.getCurrentScopes().getOptions();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashedLastRun() {
/* 343 */     return Sentry.isCrashedLastRun();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportFullyDisplayed() {
/* 348 */     Sentry.reportFullyDisplayed();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 354 */     return Sentry.continueTrace(sentryTrace, baggageHeaders);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryTraceHeader getTraceparent() {
/* 359 */     return Sentry.getTraceparent();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader getBaggage() {
/* 364 */     return Sentry.getBaggage();
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 370 */     return Sentry.captureCheckIn(checkIn);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public RateLimiter getRateLimiter() {
/* 376 */     return Sentry.getCurrentScopes().getRateLimiter();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 381 */     return Sentry.getCurrentScopes().captureReplay(replay, hint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ILoggerApi logger() {
/* 386 */     return Sentry.getCurrentScopes().logger();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 391 */     Sentry.addFeatureFlag(flag, result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ScopesAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */