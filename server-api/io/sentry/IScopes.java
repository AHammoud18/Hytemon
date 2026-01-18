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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IScopes
/*     */ {
/*     */   boolean isEnabled();
/*     */   
/*     */   @NotNull
/*     */   SentryId captureEvent(@NotNull SentryEvent paramSentryEvent, @Nullable Hint paramHint);
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEvent(@NotNull SentryEvent event) {
/*  40 */     return captureEvent(event, new Hint());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEvent(@NotNull SentryEvent event, @NotNull ScopeCallback callback) {
/*  52 */     return captureEvent(event, new Hint(), callback);
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
/*     */   @NotNull
/*     */   SentryId captureEvent(@NotNull SentryEvent paramSentryEvent, @Nullable Hint paramHint, @NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureMessage(@NotNull String message) {
/*  76 */     return captureMessage(message, SentryLevel.INFO);
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
/*     */   @NotNull
/*     */   SentryId captureMessage(@NotNull String paramString, @NotNull SentryLevel paramSentryLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureMessage(@NotNull String paramString, @NotNull SentryLevel paramSentryLevel, @NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureMessage(@NotNull String message, @NotNull ScopeCallback callback) {
/* 110 */     return captureMessage(message, SentryLevel.INFO, callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureFeedback(@NotNull Feedback feedback) {
/* 120 */     return captureFeedback(feedback, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint) {
/* 132 */     return captureFeedback(feedback, hint, null);
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
/*     */   @NotNull
/*     */   SentryId captureFeedback(@NotNull Feedback paramFeedback, @Nullable Hint paramHint, @Nullable ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureEnvelope(@NotNull SentryEnvelope paramSentryEnvelope, @Nullable Hint paramHint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEnvelope(@NotNull SentryEnvelope envelope) {
/* 166 */     return captureEnvelope(envelope, new Hint());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureException(@NotNull Throwable paramThrowable, @Nullable Hint paramHint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureException(@NotNull Throwable throwable) {
/* 186 */     return captureException(throwable, new Hint());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureException(@NotNull Throwable throwable, @NotNull ScopeCallback callback) {
/* 198 */     return captureException(throwable, new Hint(), callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureException(@NotNull Throwable paramThrowable, @Nullable Hint paramHint, @NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void captureUserFeedback(@NotNull UserFeedback paramUserFeedback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startSession();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void endSession();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void close(boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addBreadcrumb(@NotNull Breadcrumb paramBreadcrumb, @Nullable Hint paramHint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addBreadcrumb(@NotNull Breadcrumb paramBreadcrumb);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void addBreadcrumb(@NotNull String message) {
/* 259 */     addBreadcrumb(new Breadcrumb(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void addBreadcrumb(@NotNull String message, @NotNull String category) {
/* 270 */     Breadcrumb breadcrumb = new Breadcrumb(message);
/* 271 */     breadcrumb.setCategory(category);
/* 272 */     addBreadcrumb(breadcrumb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setLevel(@Nullable SentryLevel paramSentryLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTransaction(@Nullable String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setUser(@Nullable User paramUser);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setFingerprint(@NotNull List<String> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearBreadcrumbs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTag(@Nullable String paramString1, @Nullable String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeTag(@Nullable String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setExtra(@Nullable String paramString1, @Nullable String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeExtra(@Nullable String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId getLastEventId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ISentryLifecycleToken pushScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ISentryLifecycleToken pushIsolationScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   void popScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void withScope(@NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void withIsolationScope(@NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void configureScope(@NotNull ScopeCallback callback) {
/* 392 */     configureScope(null, callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void configureScope(@Nullable ScopeType paramScopeType, @NotNull ScopeCallback paramScopeCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bindClient(@NotNull ISentryClient paramISentryClient);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isHealthy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void flush(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   IHub clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   IScopes forkedScopes(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   IScopes forkedCurrentScope(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   IScopes forkedRootScopes(@NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ISentryLifecycleToken makeCurrent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   IScope getScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   IScope getIsolationScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   IScope getGlobalScope();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   IScopes getParentScopes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   boolean isAncestorOf(@Nullable IScopes paramIScopes);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   SentryId captureTransaction(@NotNull SentryTransaction paramSentryTransaction, @Nullable TraceContext paramTraceContext, @Nullable Hint paramHint, @Nullable ProfilingTraceData paramProfilingTraceData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint) {
/* 546 */     return captureTransaction(transaction, traceContext, hint, null);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable Hint hint) {
/* 552 */     return captureTransaction(transaction, null, hint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext) {
/* 565 */     return captureTransaction(transaction, traceContext, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   SentryId captureProfileChunk(@NotNull ProfileChunk paramProfileChunk);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default ITransaction startTransaction(@NotNull TransactionContext transactionContexts) {
/* 585 */     return startTransaction(transactionContexts, new TransactionOptions());
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
/*     */   @NotNull
/*     */   default ITransaction startTransaction(@NotNull String name, @NotNull String operation) {
/* 599 */     return startTransaction(name, operation, new TransactionOptions());
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
/*     */   @NotNull
/*     */   default ITransaction startTransaction(@NotNull String name, @NotNull String operation, @NotNull TransactionOptions transactionOptions) {
/* 616 */     return startTransaction(new TransactionContext(name, operation), transactionOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ITransaction startTransaction(@NotNull TransactionContext paramTransactionContext, @NotNull TransactionOptions paramTransactionOptions);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startProfiler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stopProfiler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   void setSpanContext(@NotNull Throwable paramThrowable, @NotNull ISpan paramISpan, @NotNull String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   ISpan getSpan();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   void setActiveSpan(@Nullable ISpan paramISpan);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   ITransaction getTransaction();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryOptions getOptions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Boolean isCrashedLastRun();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void reportFullyDisplayed();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   TransactionContext continueTrace(@Nullable String paramString, @Nullable List<String> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   SentryTraceHeader getTraceparent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   BaggageHeader getBaggage();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureCheckIn(@NotNull CheckIn paramCheckIn);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   RateLimiter getRateLimiter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isNoOp() {
/* 738 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   SentryId captureReplay(@NotNull SentryReplayEvent paramSentryReplayEvent, @Nullable Hint paramHint);
/*     */   
/*     */   @NotNull
/*     */   ILoggerApi logger();
/*     */   
/*     */   void addFeatureFlag(@Nullable String paramString, @Nullable Boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IScopes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */