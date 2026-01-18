/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.logger.ILoggerApi;
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
/*     */ @Deprecated
/*     */ public final class HubScopesWrapper
/*     */   implements IHub {
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   
/*     */   public HubScopesWrapper(@NotNull IScopes scopes) {
/*  21 */     this.scopes = scopes;
/*     */   }
/*     */   @NotNull
/*     */   public IScopes getScopes() {
/*  25 */     return this.scopes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  30 */     return this.scopes.isEnabled();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  35 */     return this.scopes.captureEvent(event, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  41 */     return this.scopes.captureEvent(event, hint, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  46 */     return this.scopes.captureMessage(message, level);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  52 */     return this.scopes.captureMessage(message, level, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  58 */     return this.scopes.captureFeedback(feedback, hint, callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  63 */     return this.scopes.captureEnvelope(envelope, hint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  68 */     return this.scopes.captureException(throwable, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  74 */     return this.scopes.captureException(throwable, hint, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  79 */     this.scopes.captureUserFeedback(userFeedback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startSession() {
/*  84 */     this.scopes.startSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endSession() {
/*  89 */     this.scopes.endSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  94 */     this.scopes.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(boolean isRestarting) {
/*  99 */     this.scopes.close(isRestarting);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/* 104 */     this.scopes.addBreadcrumb(breadcrumb, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/* 109 */     this.scopes.addBreadcrumb(breadcrumb);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 114 */     this.scopes.setLevel(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {
/* 119 */     this.scopes.setTransaction(transaction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 124 */     this.scopes.setUser(user);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {
/* 129 */     this.scopes.setFingerprint(fingerprint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {
/* 134 */     this.scopes.clearBreadcrumbs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 139 */     this.scopes.setTag(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {
/* 144 */     this.scopes.removeTag(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {
/* 149 */     this.scopes.setExtra(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {
/* 154 */     this.scopes.removeExtra(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 159 */     return this.scopes.getLastEventId();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushScope() {
/* 164 */     return this.scopes.pushScope();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken pushIsolationScope() {
/* 169 */     return this.scopes.pushIsolationScope();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void popScope() {
/* 179 */     this.scopes.popScope();
/*     */   }
/*     */ 
/*     */   
/*     */   public void withScope(@NotNull ScopeCallback callback) {
/* 184 */     this.scopes.withScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/* 189 */     this.scopes.withIsolationScope(callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {
/* 194 */     this.scopes.configureScope(scopeType, callback);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {
/* 199 */     this.scopes.bindClient(client);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 204 */     return this.scopes.isHealthy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(long timeoutMillis) {
/* 209 */     this.scopes.flush(timeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public IHub clone() {
/* 219 */     return this.scopes.clone();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedScopes(@NotNull String creator) {
/* 224 */     return this.scopes.forkedScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedCurrentScope(@NotNull String creator) {
/* 229 */     return this.scopes.forkedCurrentScope(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScopes forkedRootScopes(@NotNull String creator) {
/* 234 */     return Sentry.forkedRootScopes(creator);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 239 */     return this.scopes.makeCurrent();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getScope() {
/* 245 */     return this.scopes.getScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getIsolationScope() {
/* 251 */     return this.scopes.getIsolationScope();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IScope getGlobalScope() {
/* 257 */     return Sentry.getGlobalScope();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IScopes getParentScopes() {
/* 262 */     return this.scopes.getParentScopes();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/* 267 */     return this.scopes.isAncestorOf(otherScopes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 277 */     return this.scopes.captureTransaction(transaction, traceContext, hint, profilingTraceData);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk) {
/* 282 */     return this.scopes.captureProfileChunk(profileChunk);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 289 */     return this.scopes.startTransaction(transactionContext, transactionOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startProfiler() {
/* 294 */     this.scopes.startProfiler();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopProfiler() {
/* 299 */     this.scopes.stopProfiler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 306 */     this.scopes.setSpanContext(throwable, span, transactionName);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/* 311 */     return this.scopes.getSpan();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {
/* 316 */     this.scopes.setActiveSpan(span);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 322 */     return this.scopes.getTransaction();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 327 */     return this.scopes.getOptions();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashedLastRun() {
/* 332 */     return this.scopes.isCrashedLastRun();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportFullyDisplayed() {
/* 337 */     this.scopes.reportFullyDisplayed();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 343 */     return this.scopes.continueTrace(sentryTrace, baggageHeaders);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryTraceHeader getTraceparent() {
/* 348 */     return this.scopes.getTraceparent();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader getBaggage() {
/* 353 */     return this.scopes.getBaggage();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 358 */     return this.scopes.captureCheckIn(checkIn);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public RateLimiter getRateLimiter() {
/* 364 */     return this.scopes.getRateLimiter();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 369 */     return this.scopes.captureReplay(replay, hint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ILoggerApi logger() {
/* 374 */     return this.scopes.logger();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 379 */     this.scopes.addFeatureFlag(flag, result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\HubScopesWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */