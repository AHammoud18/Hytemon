/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.Message;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.transport.RateLimiter;
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
/*     */ public interface ISentryClient
/*     */ {
/*     */   boolean isEnabled();
/*     */   
/*     */   @NotNull
/*     */   SentryId captureEvent(@NotNull SentryEvent paramSentryEvent, @Nullable IScope paramIScope, @Nullable Hint paramHint);
/*     */   
/*     */   void close();
/*     */   
/*     */   void close(boolean paramBoolean);
/*     */   
/*     */   void flush(long paramLong);
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEvent(@NotNull SentryEvent event) {
/*  57 */     return captureEvent(event, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEvent(@NotNull SentryEvent event, @Nullable IScope scope) {
/*  68 */     return captureEvent(event, scope, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  79 */     return captureEvent(event, null, hint);
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
/*     */   SentryId captureFeedback(@NotNull Feedback paramFeedback, @Nullable Hint paramHint, @NotNull IScope paramIScope);
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
/*     */   default SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @Nullable IScope scope) {
/* 103 */     SentryEvent event = new SentryEvent();
/* 104 */     Message sentryMessage = new Message();
/* 105 */     sentryMessage.setFormatted(message);
/* 106 */     event.setMessage(sentryMessage);
/* 107 */     event.setLevel(level);
/*     */     
/* 109 */     return captureEvent(event, scope);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/* 120 */     return captureMessage(message, level, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureException(@NotNull Throwable throwable) {
/* 130 */     return captureException(throwable, null, null);
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
/*     */   default SentryId captureException(@NotNull Throwable throwable, @Nullable IScope scope, @Nullable Hint hint) {
/* 143 */     SentryEvent event = new SentryEvent(throwable);
/* 144 */     return captureEvent(event, scope, hint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/* 155 */     return captureException(throwable, null, hint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureException(@NotNull Throwable throwable, @Nullable IScope scope) {
/* 166 */     return captureException(throwable, scope, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureReplayEvent(@NotNull SentryReplayEvent paramSentryReplayEvent, @Nullable IScope paramIScope, @Nullable Hint paramHint);
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
/*     */   void captureSession(@NotNull Session paramSession, @Nullable Hint paramHint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void captureSession(@NotNull Session session) {
/* 196 */     captureSession(session, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   SentryId captureEnvelope(@NotNull SentryEnvelope paramSentryEnvelope, @Nullable Hint paramHint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default SentryId captureEnvelope(@NotNull SentryEnvelope envelope) {
/* 216 */     return captureEnvelope(envelope, null);
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
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable IScope scope, @Nullable Hint hint) {
/* 230 */     return captureTransaction(transaction, null, scope, hint);
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
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable IScope scope, @Nullable Hint hint) {
/* 247 */     return captureTransaction(transaction, traceContext, scope, hint, null);
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
/*     */   @NotNull
/*     */   @Internal
/*     */   SentryId captureTransaction(@NotNull SentryTransaction paramSentryTransaction, @Nullable TraceContext paramTraceContext, @Nullable IScope paramIScope, @Nullable Hint paramHint, @Nullable ProfilingTraceData paramProfilingTraceData);
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
/*     */   @Internal
/*     */   @NotNull
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext) {
/* 279 */     return captureTransaction(transaction, traceContext, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default SentryId captureTransaction(@NotNull SentryTransaction transaction) {
/* 289 */     return captureTransaction(transaction, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   SentryId captureProfileChunk(@NotNull ProfileChunk paramProfileChunk, @Nullable IScope paramIScope);
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   SentryId captureCheckIn(@NotNull CheckIn paramCheckIn, @Nullable IScope paramIScope, @Nullable Hint paramHint);
/*     */ 
/*     */   
/*     */   void captureLog(@NotNull SentryLogEvent paramSentryLogEvent, @Nullable IScope paramIScope);
/*     */ 
/*     */   
/*     */   @Internal
/*     */   void captureBatchedLogEvents(@NotNull SentryLogEvents paramSentryLogEvents);
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   RateLimiter getRateLimiter();
/*     */ 
/*     */   
/*     */   @Internal
/*     */   default boolean isHealthy() {
/* 317 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISentryClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */